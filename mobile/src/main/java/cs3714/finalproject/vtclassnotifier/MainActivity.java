package cs3714.finalproject.vtclassnotifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button submit;
    Button classCart;
    Button searchClasses;
    Button options;
    String cookie;
    ArrayList<Query> queries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submit = (Button) findViewById(R.id.submitButton);
        classCart = (Button) findViewById(R.id.cart);
        searchClasses = (Button) findViewById(R.id.searchClasses);
        options = (Button) findViewById(R.id.options);
        submit.setOnClickListener(this);
        classCart.setOnClickListener(this);
        searchClasses.setOnClickListener(this);
        options.setOnClickListener(this);
        queries = new ArrayList<>();
        if(savedInstanceState != null) {
            cookie = savedInstanceState.getString("COOKIE");
        }
        if(cookie == null)
        {
            //launch webview to get cookie
            getCookie();
        }
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
                Toast.makeText(this, cookie, Toast.LENGTH_SHORT);
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
                queries.add(temp);
            }
        }
    }

    static final int SEARCH_CLASSES = 2;  // The request code
    @Override
    public void onClick(View v) {
        if (v.getId() == submit.getId()) {

        } else if (v.getId() == classCart.getId()) {

        } else if (v.getId() == searchClasses.getId()) {
            Intent searchClass = new Intent(this, SearchClasses.class);
            searchClass.putExtra("COOKIE", cookie);
            startActivityForResult(searchClass, SEARCH_CLASSES);

        } else if (v.getId() == options.getId()) {

        }


    }
}
