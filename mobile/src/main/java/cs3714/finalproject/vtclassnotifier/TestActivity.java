package cs3714.finalproject.vtclassnotifier;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        webHelper = new WebHelper(this);
        webView.setWebViewClient(webHelper);
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.addJavascriptInterface(new JavaScriptInterface(this), "HTMLOUT");
        url = "https://banweb.banner.vt.edu/ssomanager_prod/c/SSB";
        webView.loadUrl(url);
        setContentView(webView);
        requestHandler = new HttpRequestHandler();
        htmlGetter = new HTMLGetter();
//        text = (TextView) findViewById(R.id.textview);
//        if(text != null) {
//            text.setText("Sample");
//        }
//        text.setMovementMethod(new ScrollingMovementMethod());


//        text.setText(cookies);
//        HTMLGetter getter = new HTMLGetter();
//        getter.execute("https://banweb.banner.vt.edu/ssb/prod/HZSKVTSC.P_ProcRequest");
    }
    public void setCookie(String cookies)
    {

        setContentView(R.layout.test_activity);
        text = (TextView) findViewById(R.id.textview);
        text.setMovementMethod(new ScrollingMovementMethod());
        text.setText(cookies);

        requestHandler.setCookie(cookies);
        htmlGetter.execute(cookies);

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

    private class HTMLGetter extends AsyncTask<String,  Void, String>
    {
//        private String LOGIN_URL = "https://login.vt.edu";
        @Override
        protected String doInBackground(String... params) {
            try {
                Query query = new Query();
                query.setCampus(2);
                requestHandler.sendPostForClasses(query);
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
