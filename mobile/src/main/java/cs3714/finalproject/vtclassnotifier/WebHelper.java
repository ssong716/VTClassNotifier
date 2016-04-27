package cs3714.finalproject.vtclassnotifier;

import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import junit.framework.Test;

/**
 * Created by AJ on 4/20/2016.
 */
public class WebHelper extends WebViewClient {
    TestActivity testActivity;

    public WebHelper(TestActivity t) {
        testActivity = t;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (url.contains("https://banweb.banner.vt.edu/ssb/prod/twbkwbis.P_GenMenu?name=bmenu.P_MainMnu")) {
            view.loadUrl("https://banweb.banner.vt.edu/ssb/prod/HZSKVTSC.P_DispRequest");
            testActivity.setText("");
//            String cookies = CookieManager.getInstance().getCookie(url);
//            testActivity.setCookie(cookies);

        } else if (url.contains("https://banweb.banner.vt.edu/ssb/prod/HZSKVTSC.P_DispRequest")) {
            String cookies = CookieManager.getInstance().getCookie(url);
            testActivity.setCookie(cookies);
//            view.loadUrl("javascript:window.HTMLOUT.showHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
//            view.loadUrl("javascript: document.getElementsByName(\"CRSE_NUMBER\")[0].value = 2104;");

//            view.loadUrl("javascript: document.getElementsByName(\"subj_code\")[0].value = \"AAEC\";");
//            view.loadUrl("javascript: document.ttform.submit();");

/*var array = document.querySelectorAll("table.dataentrytable td.dedefault a");
var i = 0;
for(; i < array.length; i++)
{
if(array[i].getAttribute("href").lastIndexOf(201607) != -1)
{
array[i].click();}
}*/
        }
        else if (url.contains("https://banweb.banner.vt.edu/ssb/prod/hzskstat.P_DispRegStatPage"))
        {
            String js = "javascript: var array = document.querySelectorAll(\"table.dataentrytable td.dedefault a\");\n" +
                    "var i = 0;\n" +
                    "for(; i < array.length; i++)\n" +
                    "{\n"  +
                    "if(array[i].getAttribute(\"href\").lastIndexOf("+String.valueOf(testActivity.term)+") != -1)\n" +
                    "{\n"  +
                    "array[i].click();}\n" +
                    "}";
            view.loadUrl(js);

        }
        else if(url.contains("https://banweb.banner.vt.edu/ssb/prod/bwskfreg.P_AddDropCrse?term_in="))
        {
            testActivity.setContentView(view);
            String js = "javascript: document.getElementById(\"crn_id1\").value = "+ String.valueOf(testActivity.crn)+";";
            view.loadUrl(js);
        }
    }
}
