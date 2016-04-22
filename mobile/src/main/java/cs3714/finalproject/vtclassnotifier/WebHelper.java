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
    public WebHelper(TestActivity t)
    {
        testActivity = t;
    }
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if(url.contains("https://banweb.banner.vt.edu/ssb/prod/twbkwbis.P_GenMenu?name=bmenu.P_MainMnu"))
        {
            String cookies = CookieManager.getInstance().getCookie(url);
            testActivity.setCookie(cookies);
//            view.loadUrl("https://banweb.banner.vt.edu/ssb/prod/HZSKVTSC.P_DispRequest");

        }
        else if( url.contains("https://banweb.banner.vt.edu/ssb/prod/HZSKVTSC.P_DispRequest"))
        {
//            view.loadUrl("javascript:window.HTMLOUT.showHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
//            view.loadUrl("javascript: document.getElementsByName(\"CRSE_NUMBER\")[0].value = 2104;");

//            view.loadUrl("javascript: document.getElementsByName(\"subj_code\")[0].value = \"AAEC\";");
//            view.loadUrl("javascript: document.ttform.submit();");
        }
    }
}
