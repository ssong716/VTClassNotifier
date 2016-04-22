package cs3714.finalproject.vtclassnotifier;

import android.webkit.JavascriptInterface;

/**
 * Created by AJ on 4/20/2016.
 */
public class JavaScriptInterface {
    TestActivity testActivity;
    public JavaScriptInterface(TestActivity t)
    {
        testActivity = t;
    }
    @JavascriptInterface
    public void showHTML(String html)
    {
        System.out.println(html);
        testActivity.setCookie(html);
//        new AlertDialog.Builder(myApp)
//                .setTitle("HTML")
//                .setMessage(html)
//                .setPositiveButton(android.R.string.ok, null)
//                .setCancelable(false)
//                .create()
//                .show();
    }

}
