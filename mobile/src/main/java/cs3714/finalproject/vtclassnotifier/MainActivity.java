package cs3714.finalproject.vtclassnotifier;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText crn;
    Button submit;
    Button classCart;
    Button searchClasses;
    String cookie;
    HashMap<Integer, CourseInfo> hashMap;
    HttpRequestHandler requestHandler;
    Query query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submit = (Button) findViewById(R.id.submitButton);
        classCart = (Button) findViewById(R.id.cart);
        searchClasses = (Button) findViewById(R.id.searchClasses);
        submit.setOnClickListener(this);
        classCart.setOnClickListener(this);
        searchClasses.setOnClickListener(this);
        crn = (EditText) findViewById(R.id.crnNumber);
//        queries = new ArrayList<>();
        hashMap = new HashMap<>();
        requestHandler = new HttpRequestHandler();
        cookie = null;
        if(savedInstanceState != null) {
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
        super.onSaveInstanceState(outState);
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
        else if(requestCode == SEARCH_CLASSES)
        {
            if(resultCode == RESULT_OK)
            {
                Query temp = new Query();
                CourseInfo c = new CourseInfo(data.getStringArrayListExtra("COURSE"));
                temp.setSubject(c.getDepartment());
                temp.setCrn(c.getCrn());
                Toast.makeText(this, c.toString() + " added to query list", Toast.LENGTH_LONG).show();
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

    }

    private void makeToast (String s)
    {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    private void putInHashmap(CourseInfo c)
    {
        hashMap.put(c.getCrn(), c);
        crn.setText("");
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
                return "Exception caught: " + e.toString();
            }
            return requestHandler.response;
        }

        @Override
        protected void onPostExecute(String s) {
            HtmlParser parser = new HtmlParser();
            if(s.contains("Exception caught: "))
            {
                makeToast(s);
            }
            try {
                HashMap<Integer, CourseInfo> hashMap = parser.parseTable(s);
                String str = "";
                if(hashMap.values().size() == 1)
                {
                    for (CourseInfo c : hashMap.values())
                    {
                        makeToast(c.toString() + " added to query list");
                        putInHashmap(c);
                    }
                }
                else if(hashMap.values().size() == 0)
                {
                    makeToast("No Results To Show");
                }
                else
                {
                    makeToast("Too many results, use search for classes");
                }
            }
            catch(Exception e)
            {
                makeToast(e.getMessage());
                super.onPostExecute(s);
            }
        }


    }
}
