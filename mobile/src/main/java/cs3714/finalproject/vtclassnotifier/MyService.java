package cs3714.finalproject.vtclassnotifier;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MyService extends Service {
    int count = 0;
    int rerunTimer = 60000;
    int mId = 0;
    HttpRequestHandler requestHandler;
    ArrayList<Query> queryList;
    Query q;
    String cookie;
    ConcurrentHashMap<Integer, CourseInfo> hashMap;
    AtomicInteger openClasses;
    AtomicInteger executedTasksCount;
    AtomicInteger finishedCount;
    CourseInfo lastCourseOpen;

    public MyService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        HashMap<Integer, ArrayList<String>> h = (HashMap)intent.getSerializableExtra(MainActivity.CLASS_QUERY_MESSAGE);
        queryList = new ArrayList<>();
        for(ArrayList<String> a : h.values())
        {
            CourseInfo c = new CourseInfo(a);
            queryList.add(c.toQuery());
        }
        Log.d("SUNGHA", "RECEIVED " + queryList.size() + " classes");
        cookie = intent.getStringExtra(MainActivity.QUERY_COOKIE);
        openClasses = new AtomicInteger(0);
        executedTasksCount = new AtomicInteger(0);
        finishedCount = new AtomicInteger(0);
        requestHandler = new HttpRequestHandler();
        q = new Query();
        requestHandler.setCookie(cookie);
        hashMap = new ConcurrentHashMap<Integer, CourseInfo>();

        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                for(int i = 0; i < queryList.size(); i++) {
                    if(queryList.size() > 1) {
                        startTask(i, true);
                    }
                    else startTask(i, false);
                }
            }
        }, 0, rerunTimer);//This is every 5 seconds
        //Start AsyncHere


        return Service.START_NOT_STICKY;
    }

    public class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
    private final IBinder iBinder = new MyBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    public void checkOpenAndNotify() {
        int count = 0;
        Log.d("SUNGHA", "There are " + hashMap.values().size() + " classes to query");
        for(CourseInfo c: hashMap.values()) {
            Log.d("SUNGHA", "Crn " + c.crn + " has " + c.openSeats + " open!");
            if(c.isOpen()) {
                lastCourseOpen = c;
                notifyMe(c, count++, c.term.toInt(c.year));
                openClasses.incrementAndGet();
            }
        }
//        if(openClasses.get() == 1) {
//            Log.d("SUNGHA", "1 Open class");
//            notifyMe("CRN " + lastCourseOpen.crn + " is now open!");
//        }
//        if(openClasses.get() > 1) {
//            Log.d("SUNGHA", "> 1 open class");
//            notifyMe("There are multiple classes open!");
//        }
        hashMap.clear();
        openClasses.set(0);
        executedTasksCount.set(0);
        finishedCount.set(0);
    }

    public void checkClasses(String s) {
        HtmlParser parser = new HtmlParser();
        if(s.contains("Exception caught: "))
        {
            makeToast(s);
        }
        try {
            HashMap<Integer, CourseInfo> hashMap = parser.parseTable(s);
            String str = "";

            if(hashMap.values().size() > 0)
            {
                for(Map.Entry<Integer, CourseInfo> entry : hashMap.entrySet()) {
                    CourseInfo c = entry.getValue();
//                    makeToast(c.toString() + " added to query list");
                    this.hashMap.put(c.crn, c);
                }
            }
            else if(hashMap.values().size() == 0)
            {
//                makeToast("No Results To Show");
            }
            else
            {
//                makeToast("Too many results, use search for classes");
            }
        }
        catch(Exception e)
        {
            makeToast("CheckClasses " + e.getMessage());
        }
    }

    private void startTask(int taskId, boolean useParallel) {
        HTMLGetter req = new HTMLGetter(taskId);
        if(useParallel) {
            req.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else {
            req.execute();
        }
    }

    private void makeToast (String s)
    {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
    //Sends a notification to the phone
    private void notifyMe(CourseInfo c, int mId, int term) {
        String s = "CRN " + c.crn + " is now open";
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_cast_dark)
                        .setContentTitle("VTClassNotifier")
                        .setContentText(s);
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, TestActivity.class);
        //Send this back to TestActivity
        //Include cookie, url
        resultIntent.putExtra("Cookie", cookie);
        resultIntent.putExtra("Term", term);
        resultIntent.putExtra("Url", "https://banweb.banner.vt.edu/ssb/prod/hzskstat.P_DispRegStatPage");
        resultIntent.putExtra("CRN", c.getCrn());

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(mId, mBuilder.build());
        //mId can be different to push multiple

        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        long[] vibrationPattern = {0, 500, 50, 300};
        //-1 - don't repeat
        final int indexInPatternToRepeat = -1;
        vibrator.vibrate(vibrationPattern, indexInPatternToRepeat);
    }


    private class HTMLGetter extends AsyncTask<Query,  Void, String>
    {

        private final int id;

        HTMLGetter(int id) {
            this.id = id;
        }
        //        private String LOGIN_URL = "https://login.vt.edu";
        //We are going to make multiple threads
        @Override
        protected String doInBackground(Query... params) {
            int taskExecutionNumber = executedTasksCount.getAndIncrement();
            Log.d("SUNGHA", "Executed " + taskExecutionNumber + " task(s)");
            try {
//                for(int i = 0; i < crnList.size(); i++) {
//                    q.setCrn(crnList.get(taskExecutionNumber));
                    try {
                        requestHandler.sendPostForClasses(queryList.get(taskExecutionNumber));

                    } catch (Exception e) {
                        Log.d("SUNGHA", "EXCEPTION");
                        e.printStackTrace();
                    }
                    Log.d("SUNGHA", "Query for crn " + queryList.get(taskExecutionNumber).getCrn());
//                }

//                requestHandler.sendPostForClasses
                Log.d("SUNGHA", "Ran " + count++ + " times!");
            }
            catch (Exception e)
            {
                return "Exception caught: " + e.toString();
            }
            return requestHandler.response;
        }

        @Override
        protected void onPostExecute(String s) {
            checkClasses(s);
            if(finishedCount.incrementAndGet() >= queryList.size()) {
                checkOpenAndNotify();
            }
        }
    }
}
