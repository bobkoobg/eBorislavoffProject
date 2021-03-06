package utilities;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

/*
 * Js implementation found here : http://webdesign.tutsplus.com/tutorials/how-to-integrate-no-captcha-recaptcha-in-your-website--cms-23024
 * Js imp also here : (user imjosh) http://stackoverflow.com/questions/27902539/how-can-i-validate-google-recaptcha-v2-using-javascript-jquery
 * Solution found here : http://www.journaldev.com/7133/how-to-integrate-google-recaptcha-in-java-web-application
 * Jar found here : http://www.java2s.com/Code/Jar/j/Downloadjavaxjson10jar.htm
 * ps: this is shit - https://developers.google.com/recaptcha/old/docs/java#server-side-how-to-test-if-the-user-entered-the-right-answer
 */
public class VerifyRecaptcha {

    private static final String url = "https://www.google.com/recaptcha/api/siteverify";
    private final static String USER_AGENT = "Mozilla/5.0";



    public static boolean verify( String gRecaptchaResponse, String secret ) throws IOException {
        if ( gRecaptchaResponse == null || "".equals( gRecaptchaResponse ) ) {
            return false;
        }

        try {
            URL obj = new URL( url );
            HttpsURLConnection con = ( HttpsURLConnection ) obj.openConnection();

            // add reuqest header
            con.setRequestMethod( "POST" );
            con.setRequestProperty( "User-Agent", USER_AGENT );
            con.setRequestProperty( "Accept-Language", "en-US,en;q=0.5" );

            String postParams = "secret=" + secret + "&response="
                    + gRecaptchaResponse;

            // Send post request
            con.setDoOutput( true );
            DataOutputStream wr = new DataOutputStream( con.getOutputStream() );
            wr.writeBytes( postParams );
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println( "\nSending 'POST' request to URL : " + url );
            System.out.println( "Post parameters : " + postParams );
            System.out.println( "Response Code : " + responseCode );

            BufferedReader in = new BufferedReader( new InputStreamReader(
                    con.getInputStream() ) );
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ( (inputLine = in.readLine()) != null ) {
                response.append( inputLine );
            }
            in.close();

            // print result
            System.out.println( response.toString() );

            //parse JSON response and return 'success' value
            JsonReader jsonReader = Json.createReader( new StringReader( response.toString() ) );
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            return jsonObject.getBoolean( "success" );
        } catch ( Exception e ) {
            e.printStackTrace();
            return false;
        }
    }
}
