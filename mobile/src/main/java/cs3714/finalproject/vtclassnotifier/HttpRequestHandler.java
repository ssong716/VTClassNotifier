package cs3714.finalproject.vtclassnotifier;

import android.app.ProgressDialog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Song on 4/22/2016.
 */
public class HttpRequestHandler {
    private HttpsURLConnection conn;
    private ProgressDialog progress;

    //Request specific fields
    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36";
    private final String ACCEPT_LANGUAGE = "en-US,en;q=0.8,ko;q=0.6";
    private final String COOKIES_HEADER = "Set-Cookie";
    private List<String> cookies;
    private String cookie;

    //URLs to make requests to
    String timeTableUrl = "https://banweb.banner.vt.edu/ssb/prod/HZSKVTSC.P_ProcRequest";
    String canvasUrl = "https://canvas.vt.edu";

    //You need to fill out your own username and password.
    private String password = "";
    private String username = "";
    private String formDataParam = "j_username=" + username +"&j_password=" + password + "&_eventId_proceed=";
    //These are the parameters to look for classes.
    int campus = 0;
    int termyear = 201609;
    String subject = "CS";
    String courseNum = "2104";
    String crn = "";
    //Complete the request paremeters from the fields above
    String courseRequest = "CAMPUS=" + campus + "&TERMYEAR=" + termyear + "&CORE_CODE=AR%25&subj_code="
            + subject + "&SCHDTYPE=%25&CRSE_NUMBER=" + courseNum + "&crn=" + crn + "&open_only=&disp_comments_in=Y&BTN_PRESSED=FIND+class+sections&inst_name=";

    private String currUrl;
    String response;

    private String formatCookies(ArrayList<String> cookies) {
        String cookieRequest;
        for(int i = 0; i < cookies.size(); i++) {

        }
        return null;
    }

    public void setCookie(String c)
    {
        cookie = c;
    }
    public void useJsoup() {
        Document doc = Jsoup.parse(response);
        System.out.println(doc.html());
    }
    /**
     * Campus   :   select name=CAMPUS
     * Term     :   select name=TERMYEAR
     * Subject  :   select name=subj_code
     * Course # :   input name=CRSE_NUMBER
     * CRN      :   input name=crn
     * Submit   :   input name=BTN_PRESSED
     */
    // HTTP POST request
    public void sendPostForClasses() throws Exception {
        String url = timeTableUrl;
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", ACCEPT_LANGUAGE);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Cookie", cookie);

//        String urlParameters = "CAMPUS=0&TERMYEAR=201609&CORE_CODE=AR%25&subj_code=CS&SCHDTYPE=%25&CRSE_NUMBER=&crn=&open_only=&disp_comments_in=Y&BTN_PRESSED=FIND+class+sections&inst_name=";
        String urlParameters = courseRequest;
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine + "\n");
        }
        in.close();

        //print result
        this.response = response.toString();
//        System.out.println(response.toString());
    }

    // HTTP POST request
    public void sendPostSignin() throws Exception {

        String url = currUrl;
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", ACCEPT_LANGUAGE);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        String urlParameters = formDataParam;

        // Send post request
        con.setDoOutput(true);

        System.out.println(con.getHeaderFields());
        System.out.println(con.getHeaderField("Set-Cookie"));
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            if(inputLine.contains("Set-Cookie")) {
                System.out.println("Cookie : " + inputLine);
            }
            response.append(inputLine + "\n");
        }
        in.close();

        //print result
//        System.out.println(response.toString());
    }

    public void sendPost(String url, String urlParams) throws Exception {

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", ACCEPT_LANGUAGE);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        String urlParameters = urlParams;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine + "\n");
        }
        in.close();

        //print result
        System.out.println(response.toString());
    }

    // HTTP GET request
    public void sendGetLogin() throws Exception {

        String url = canvasUrl;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        boolean redirect = false;

        // normally, 3xx is redirect
        int status = con.getResponseCode();
        if (status != HttpURLConnection.HTTP_OK) {
            if (status == HttpURLConnection.HTTP_MOVED_TEMP
                    || status == HttpURLConnection.HTTP_MOVED_PERM
                    || status == HttpURLConnection.HTTP_SEE_OTHER)
                redirect = true;
        }

        System.out.println("Response Code ... " + status);

        if (redirect) {

            // get redirect url from "location" header field
            String newUrl = con.getHeaderField("Location");

            // get the cookie if need, for login
            String cookies = con.getHeaderField("Set-Cookie");

            // open the new connnection again
            con = (HttpURLConnection) new URL(newUrl).openConnection();
            con.setRequestProperty("Cookie", cookies);
            con.addRequestProperty("Accept-Language", ACCEPT_LANGUAGE);
            con.addRequestProperty("User-Agent", USER_AGENT);
            con.addRequestProperty("Referer", "google.com");

            System.out.println("Redirect to URL : " + newUrl);
        }

        int responseCode = con.getResponseCode();
        currUrl = con.getURL().toString();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine + "\n");
        }
        in.close();

        //print result
//        System.out.println(response.toString());
    }
}
