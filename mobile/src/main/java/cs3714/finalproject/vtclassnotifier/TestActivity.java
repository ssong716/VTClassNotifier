package cs3714.finalproject.vtclassnotifier;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.TextView;


//import com.gargoylesoftware.htmlunit.WebClient;
//import com.gargoylesoftware.htmlunit.html.HtmlPage;

//import com.gargoylesoftware.htmlunit.WebClient;
//import com.gargoylesoftware.htmlunit.html.HtmlPage;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;
//import org.w3c.dom.Document;

import java.io.IOException;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by AJ on 4/19/2016.
 */
public class TestActivity extends AppCompatActivity {

    TextView text;
    WebView webView;
    String url;
    WebHelper webHelper;
    HttpRequestHandler requestHandler;
    HTMLGetter htmlGetter;
    int term, crn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        webHelper = new WebHelper(this);
        webView.setWebViewClient(webHelper);
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.addJavascriptInterface(new JavaScriptInterface(this), "HTMLOUT");
        url = "https://banweb.banner.vt.edu/ssomanager_prod/c/SSB";
        Intent i = getIntent();
        int temp = -1;
        if(i != null)
        {
            temp = i.getIntExtra("Term", -1);
            if(temp != -1)
            {
                term = temp;
                url = "https://banweb.banner.vt.edu/ssb/prod/bwskfreg.P_AddDropCrse?term_in=" + temp;
            }
            String str = i.getStringExtra("Url");
            if(str != null && !str.equalsIgnoreCase("") )
            {
                url = str;
                Log.d("TestActivity", str);
            }
            String s = i.getStringExtra("Cookie");
            if(s != null && !s.equalsIgnoreCase(""))
            {
                CookieManager.getInstance().setCookie(url, s);
            }
            temp = i.getIntExtra("CRN", -1);
            if(temp != -1)
            {
                crn = temp;
            }
        }
        Log.d("TESTACTIVITY", url);
        webView.loadUrl(url);
        if(temp == -1) {
            setContentView(webView);
        }
//        requestHandler = new HttpRequestHandler();
//        htmlGetter = new HTMLGetter();
//        text = (TextView) findViewById(R.id.textview);
//        if(text != null) {
//            text.setText("Sample");
//        }
//        text.setMovementMethod(new ScrollingMovementMethod());


//        text.setText(cookies);
//        HTMLGetter getter = new HTMLGetter();
//        getter.execute("https://banweb.banner.vt.edu/ssb/prod/HZSKVTSC.P_ProcRequest");
    }

    public void setContentView(View v)
    {
        super.setContentView(v);
    }
    public void setCookie(String cookies)
    {
        Intent i = new Intent();
        i.putExtra("COOKIE", cookies);
        setResult(RESULT_OK, i);
        finish();
//        setContentView(R.layout.test_activity);
//        text = (TextView) findViewById(R.id.textview);
//        text.setMovementMethod(new ScrollingMovementMethod());
//        text.setText(cookies);
//
//        requestHandler.setCookie(cookies);
//        Query query = new Query();
//        query.setCampus(2);
//        htmlGetter.execute(query);

//        try {
//            requestHandler.setCookie(cookies);
//            requestHandler.sendPostForClasses();
//        }
//        catch (Exception e)
//        {
//            text.setText("Exception caught from request handler\n" + e.toString());
//            return;
//        }
//        text.setText(cookies+"\nResponse\n" + requestHandler.response);

    }

    public void setText(String s)
    {
        setContentView(R.layout.test_activity);
        text = (TextView) findViewById(R.id.textview);
        text.setMovementMethod(new ScrollingMovementMethod());
        text.setText(s);
//        Toast t = Toast.makeText(this, s, Toast.LENGTH_SHORT);
//        t.show();
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
            setText(s);
            HtmlParser parser = new HtmlParser();
            try {
                HashMap<Integer, CourseInfo> hashMap = parser.parseTable(s);
                String str = "";
                for (CourseInfo c : hashMap.values())
                {
                    str += c.toString() + "\n\n";
                }
                setText(str);
            }
            catch(Exception e)
            {
                setText(e.getMessage());
                super.onPostExecute(s);
            }
        }


    }
}
