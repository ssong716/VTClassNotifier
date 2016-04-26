package cs3714.finalproject.vtclassnotifier;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText crn;
    Button submit;
    Button classCart;
    Button searchClasses;
    Button startService;
    String cookie;
    MyService myService;
    HashMap<Integer, CourseInfo> hashMap;
    HttpRequestHandler requestHandler;
    Query query;
    Intent serviceIntent;
    public static final String CLASS_QUERY_MESSAGE = "CLASS QUERY";
    public static final String QUERY_COOKIE = "QUERY COOKIE";
    public static final String INITIALIZE_STATE = "initialization state";
    boolean isInitialized = false;
    boolean isBounded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submit = (Button) findViewById(R.id.submitButton);
        classCart = (Button) findViewById(R.id.cart);
        searchClasses = (Button) findViewById(R.id.searchClasses);
        startService = (Button) findViewById(R.id.startService);
        submit.setOnClickListener(this);
        classCart.setOnClickListener(this);
        searchClasses.setOnClickListener(this);
        startService.setOnClickListener(this);
        crn = (EditText) findViewById(R.id.crnNumber);
        serviceIntent = new Intent(this, MyService.class);
//        queries = new ArrayList<>();
        hashMap = new HashMap<>();
        requestHandler = new HttpRequestHandler();
        cookie = null;
        if(savedInstanceState != null) {
            isInitialized = savedInstanceState.getBoolean(INITIALIZE_STATE);
            cookie = savedInstanceState.getString("COOKIE");
        }
        if(cookie == null)
        {
            //launch webview to get cookie
            getCookie();
        }
        else {
            requestHandler.setCookie(cookie);
        }
        query = new Query();
        query.setSubject("%");


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("COOKIE", cookie);
        outState.putBoolean(INITIALIZE_STATE, isInitialized);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isBounded) {
            unbindService(myServiceConnection);
            isBounded = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isInitialized && !isBounded) {
            bindService(serviceIntent, myServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SUNGHA", "onDestroy IS CALLED AND SERVICES SHOULD STOP");
        stopService(serviceIntent);
    }

    private ServiceConnection myServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyBinder b = (MyService.MyBinder)service;
            myService = b.getService();
            isBounded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService = null;
            isBounded = false;
        }
    };

    static final int GET_COOKIE = 1;  // The request code
    private void getCookie() {
        Intent getCookieIntent = new Intent(this, TestActivity.class);
        startActivityForResult(getCookieIntent, GET_COOKIE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == GET_COOKIE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                cookie = data.getStringExtra("COOKIE");
                requestHandler.setCookie(cookie);
            }
        }
        else if(requestCode == SEARCH_CLASSES)
        {
            if(resultCode == RESULT_OK)
            {
                Query temp = new Query();
                CourseInfo c = new CourseInfo(data.getStringArrayListExtra("COURSE"));
                temp.setSubject(c.getDepartment());
                temp.setCrn(c.getCrn());
//                Toast.makeText(this, c.toString() + " added to query list", Toast.LENGTH_LONG).show();
                makeToast("CRN " + c.getCrn() + " added to query list");
                hashMap.put(c.getCrn(), c);
//                queries.add(temp);
            }
        }
        else if(requestCode == CLASS_CART)
        {
            if(resultCode == RESULT_OK)
            {
                //there was an edit made to the hash map
                Serializable temp = data.getSerializableExtra("CART");
                if(temp != null && temp.getClass() == HashMap.class)
                {
                    hashMap = (HashMap) temp;
                }
            }
        }
    }

    static final int SEARCH_CLASSES = 2;  // The request code
    static final int CLASS_CART = 3;
    @Override
    public void onClick(View v) {
        if (v.getId() == submit.getId()) {
            //TODO Check for values some possible error check to remove from the list if it's not valid
            if(crn.getText().toString() == null || crn.getText().toString().equals("")) {
                makeToast("Please enter a CRN");
                return;
            }
            int c = Integer.parseInt(crn.getText().toString());
            query.setCrn(c);
            HTMLGetter getter = new HTMLGetter();
            getter.execute(query);



        } else if (v.getId() == classCart.getId()) {
            Intent classCart = new Intent(this, ClassCartActivity.class);
            HashMap<Integer, ArrayList<String>> h = new HashMap<>();
            for(CourseInfo c: hashMap.values())
            {
                h.put(c.getCrn(), c.toArrayList());
            }
            classCart.putExtra("CART", h);
            startActivityForResult(classCart, CLASS_CART);

        } else if (v.getId() == searchClasses.getId()) {
            Intent searchClass = new Intent(this, SearchClasses.class);
            searchClass.putExtra("COOKIE", cookie);
            startActivityForResult(searchClass, SEARCH_CLASSES);
        }
        else if(v.getId() == startService.getId())
        {
            //Start the Intent
//            serviceIntent = new Intent(this, MyService.class);
//            ArrayList<String> crnList = new ArrayList<String>();
            HashMap<Integer, ArrayList<String>> h = new HashMap<>();
            for(CourseInfo c : hashMap.values())
            {
                h.put(c.getCrn(), c.toArrayList());
            }
            serviceIntent.putExtra(CLASS_QUERY_MESSAGE, h);
            serviceIntent.putExtra(QUERY_COOKIE, cookie);

            if(!isInitialized) {
                startService(serviceIntent);
                startService.setText("Stop Service");
                isInitialized = true;
                makeToast("Service Started");
            }
            else
            {
                stopService(serviceIntent);
                startService.setText("Start Service");
                isInitialized = false;
                makeToast("Service Stopped");
            }
        }
    }

    private void makeToast(String s)
    {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private class HTMLGetter extends AsyncTask<Query,  Void, String>
    {
        //        private String LOGIN_URL = "https://login.vt.edu";
        @Override
        protected String doInBackground(Query... params) {
            try {

                requestHandler.sendPostForClasses(params[0]);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return "Exception caught: " + e.toString();
            }
            return requestHandler.response;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s.contains("Exception caught: "))
            {
                Log.e("SearchClasses doInBG", s);
                return;
            }
            ArrayList<CourseInfo> arrayList = new ArrayList<>();
            HtmlParser parser = new HtmlParser();
            try {
                HashMap<Integer, CourseInfo> h = parser.parseTable(s);
                if(h.values().size() == 1)
                {
                    for(CourseInfo c : h.values()) {
                        makeToast("CRN " + c.getCrn() + " added to query list");
                        hashMap.put(c.getCrn(), c);
                    }
                }
                else
                {
                    makeToast("Your search returned the wrong number of results");
                }
                super.onPostExecute(s);
            }
            catch(Exception e)
            {
                e.printStackTrace();
                Log.e("SearchClasses PostExec",  e.getMessage());
//                arrayList.add("Error parsing table: "+ e.getMessage());
//                populateListView(arrayList);
                super.onPostExecute(s);
                return;
            }

        }


    }

}
