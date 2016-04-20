package cs3714.finalproject.vtclassnotifier;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
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
import java.net.URL;

/**
 * Created by AJ on 4/19/2016.
 */
public class TestActivity extends AppCompatActivity {

    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        text = (TextView) findViewById(R.id.textview);
        if(text != null) {
            text.setText("Sample");
        }
        text.setMovementMethod(new ScrollingMovementMethod());
        HTMLGetter getter = new HTMLGetter();
        getter.execute("https://banweb.banner.vt.edu/ssb/prod/HZSKVTSC.P_ProcRequest");
    }
    protected void setText(String s)
    {
        text.setText(s);
//        Toast t = Toast.makeText(this, s, Toast.LENGTH_SHORT);
//        t.show();
    }

    private class HTMLGetter extends AsyncTask<String,  Void, String>
    {
//        private String LOGIN_URL = "https://login.vt.edu";
        @Override
        protected String doInBackground(String... params) {
            Document doc = null;
            try {
                doc = Jsoup.connect(params[0]).get();

            Connection.Response loginForm = null;
                loginForm = Jsoup.connect("https://banweb.banner.vt.edu/ssomanager_prod/c/SSB")
                            .method(Connection.Method.POST)
                            .execute();
            doc = loginForm.parse();
//                 doc = Jsoup.connect("https://login.vt.edu/profile/cas/login?execution=e1s2")
//                        .cookies(loginForm.cookies())
//                         .get();
                Element username = doc.getElementById("username");
                username.val("bousquet");

                Element password = doc.getElementById("password");
                password.val("Pikapp'17");

                Element elem = doc.getElementById("loginform");
                FormElement form = new FormElement(elem.tag(), doc.baseUri(), elem.attributes() );
                form.addElement(username);
                form.addElement(password);
                Connection submit = form.submit();
                submit.header("header", "GET /profile/cas/login?execution=e1s2 HTTP/1.1\n" +
                        "Host: login.vt.edu\n" +
                        "Connection: keep-alive\n" +
                        "Pragma: no-cache\n" +
                        "Cache-Control: no-cache\n" +
                        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n" +
                        "Upgrade-Insecure-Requests: 1\n" +
                        "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.75 Safari/537.36\n" +
                        "Referer: https://login.vt.edu/profile/cas/login?execution=e1s1\n" +
                        "Accept-Encoding: gzip, deflate, sdch\n" +
                        "Accept-Language: en-US,en;q=0.8");
                submit.cookie("JSESSIONID", loginForm.cookie("JSESSIONID"));
                Document submitDoc = submit.get();
//                Connection.Request request = submit.request();
                String retVal = "";
                retVal += submitDoc.location() + "\n" + submitDoc.toString();
//                Connection.Response login = submit.execute();
//                for (String str : login.headers().values())
//                {
//                    retVal += str + "\n";
//                }
//                retVal += submit.get().toString();
//                retVal += request.requestBody();
//                retVal += loginForm.cookie("_ga") + "\n" + loginForm.cookie("JSESSIONID");
                return retVal;
//                Document loginDoc = submit.get();
//                for (Element key : form.elements())
//                {
//                    retVal += key.toString() + "\n";
//                }
//                return loginDoc.location() + "\n" + loginDoc.toString();
//                return "Status Code "+ login.statusCode() + "\nStatus Message " + login.statusMessage()
//                        +"\nContent Type: "+login.contentType() +"\nBody: " + login.body()/*+ "\nLocation: " + loginDoc.location() + "\n" + loginDoc.toString()*/;
            } catch (IOException e) {
                e.printStackTrace();
            }
                System.out.println(doc);
//                makeToast(doc);
                // Create a new instance of the Firefox driver
                // Notice that the remainder of the code relies on the interface,
                // not the implementation.
//                WebDriver driver = new RemoteWebDriver(DesiredCapabilities.android());
//
//            driver.get("http://google.com");
//                 And now use this to visit Google
//                driver.get("https://banweb.banner.vt.edu/ssomanager_prod/c/SSB");
                // Alternatively the same thing can be done like this
                // driver.navigate().to("http://www.google.com");

                // Find the text input element by its name
//                WebElement username = driver.findElement(By.name("j_username"));

                // Enter something to search for
//                username.sendKeys("bousquet");
//                WebElement password = driver.findElement(By.name("j_password"));
//                password.sendKeys("Pikapp'17");
                // Now submit the form. WebDriver will find the form for us from the element
//                password.submit();

                // Check the title of the page
//                System.out.println("Page title is: " + driver.getTitle());

//                 Google's search is rendered dynamically with JavaScript.
//                 Wait for the page to load, timeout after 10 seconds
//                (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
//                    public Boolean apply(WebDriver d) {
//                        return d.getTitle().toLowerCase().startsWith("cheese!");
//                    }
//                });

                // Should see: "cheese! - Google Search"
//                System.out.println("Page title is: " + driver.getTitle());


//            final WebClient webClient = new WebClient();
//            try {
//                final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
//                return page.getTitleText();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            String retVal = driver.getCurrentUrl() + "\n" + driver.getPageSource();
//                Close the browser
//                driver.quit();
            if(doc != null)
            {
                return doc.location() +"\n"+doc.toString();
            }
            return "Error Loading page";

//            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            setText(s);
            super.onPostExecute(s);
        }


    }
}
