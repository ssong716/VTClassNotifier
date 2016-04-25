package cs3714.finalproject.vtclassnotifier;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchClasses extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    Spinner spinnerSubj;
    Button search;
    EditText courseNumber;
    ListView results;
    String[] subjectArray;
    String [] classArray;
    HttpRequestHandler requestHandler;
    ArrayAdapter<CourseInfo> listAdapter;
    String cookie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_classes);

        spinnerSubj = (Spinner) findViewById(R.id.spinnerSubjects);
//        spinnerClass = (Spinner) findViewById(R.id.spinnerClasses);
        courseNumber = (EditText) findViewById(R.id.editText);
        search = (Button) findViewById(R.id.sendQuery);
        results = (ListView) findViewById(R.id.results);
        search.setOnClickListener(this);
        subjectArray = new String[]{
                "Computer Science",
                "Mechanical Engineering",
                "Testing",
                "Test2",
                "Test3"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, subjectArray);
        spinnerSubj.setAdapter(adapter);

//        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, classArray);
//        spinnerClass.setAdapter(adapter2);
//        ArrayList <String> temp = new ArrayList<>();
//        temp.add("Search for Classes");
        listAdapter = new ArrayAdapter<CourseInfo>(this, android.R.layout.simple_list_item_1);
        results.setAdapter(listAdapter);
        requestHandler = new HttpRequestHandler();
        if(savedInstanceState != null) {
            cookie = savedInstanceState.getString("COOKIE");
        }
        if(cookie == null)
        {
            //launch webview to get cookie
            getCookie();
        }
        results.setOnItemClickListener(this);
    }
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
    }

    private void populateListView(ArrayList<CourseInfo> list)
    {
//        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, list);
        listAdapter.clear();
        listAdapter.addAll(list);
        results.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
//        results.setAdapter(listAdapter);
    }

    private void queryError(String s)
    {
        ArrayList<String> t = new ArrayList<>();
        t.add(s);
        ArrayAdapter<String> temp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, t);
        results.setAdapter(temp);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == search.getId())
        {
            //start query
            Query query = new Query();
           String s =  spinnerSubj.getSelectedItem().toString();
            if(s.equalsIgnoreCase("Computer Science"))
            {
                query.setSubject("CS");
            }
            if(courseNumber.getText().toString().matches("")) {
                Toast.makeText(this, "Please enter a course number", Toast.LENGTH_SHORT).show();
            }
            else
            {
                int cn = Integer.parseInt(courseNumber.getText().toString());
                query.setCourseNum(cn);
                HTMLGetter htmlGetter = new HTMLGetter();
                htmlGetter.execute(query);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent();
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == results.getId())
        {
            if(parent.getAdapter().getItem(position).getClass() == CourseInfo.class)
            {
                CourseInfo info = (CourseInfo) parent.getAdapter().getItem(position);
                System.out.println(info.toString());
                Intent i = new Intent();
                i.putStringArrayListExtra("COURSE", info.toArrayList());
                setResult(RESULT_OK, i);
                finish();
            }
        }
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
                queryError(s);
                return;
            }
            ArrayList<CourseInfo> arrayList = new ArrayList<>();
            HtmlParser parser = new HtmlParser();
            try {
                HashMap<Integer, CourseInfo> hashMap = parser.parseTable(s);
                for (CourseInfo c : hashMap.values())
                {
                    arrayList.add(c);
                }
                populateListView(arrayList);
                super.onPostExecute(s);
            }
            catch(Exception e)
            {
                e.printStackTrace();
                Log.e("SearchClasses PostExec",  e.getMessage());
//                arrayList.add("Error parsing table: "+ e.getMessage());
//                populateListView(arrayList);
                queryError(e.getMessage());
                super.onPostExecute(s);
                return;
            }

        }


    }
}
