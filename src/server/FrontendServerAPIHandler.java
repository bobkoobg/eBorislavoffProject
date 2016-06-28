package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import controller.Controller;
import entity.Article;
import entity.User;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FrontendServerAPIHandler implements HttpHandler {

    private Controller controller;
    private Random random;
    private static String frontendPagesDIR = "src/pages/frontend/";

    public FrontendServerAPIHandler( Controller controller ) {
        this.controller = controller;
        random = new Random();
    }

    @Override
    public void handle( HttpExchange he ) throws IOException {
        //ALL
        String response = "";
        int status = 200;
        String method = he.getRequestMethod().toUpperCase();
        String path = he.getRequestURI().getPath();
        String[] parts = path.split( "/" );
        String ipport = he.getRemoteAddress().toString();
        String address = ipport.substring( 0, ipport.indexOf( ":" ) );

        //POST, PUT, (DELETE?)
        InputStreamReader isr;
        BufferedReader br;
        String jsonQuery;

        //In case of request for components such as "nav"
        File file = null;
        byte[] bytesToSend = null;

        //Extract mime out of getRequestURI path
        String lastElemStr = parts[ (parts.length - 1) ];
        String mime = lastElemStr.lastIndexOf( "." ) > -1
                ? getMime( lastElemStr.substring( lastElemStr.lastIndexOf( "." ) ) )
                : getMime( ".json" );

        //Debug START
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat( "HH:mm:ss" );
        String dateFormatted = formatter.format( date );
        System.out.println( "FrontendServerAPIHandler DEBUG: # " + dateFormatted + " # #Request method: " + method + ", Request path : " + path + ", path Length: " + parts.length + " / 2nd elem : " + (parts.length > 0 ? parts[ 1 ] : "NO") );
        //Debug END

        switch ( method ) {
            case "GET":
                boolean decisionMade = false;

                /*
                 * Generate server identifier and send to client
                 * URL : http://localhost:8084/api/nav
                 */
                if ( parts.length == 3 && parts[ 2 ] != null && "nav".equals( parts[ 2 ] ) ) {
                    mime = getMime( ".html" );
                    file = new File( frontendPagesDIR + "nav.html" );
                    decisionMade = true;
                }

                if ( !decisionMade && (parts.length == 3 && parts[ 2 ] != null && "cool".equals( parts[ 2 ] )) ) {
                    User user = new User( 23, "bobkoo", "rocks", "mail@mail.dk", "SwaggerBoy", new Date(), new Date() );
                    response = new Gson().toJson( user );
                    status = 200;
                    decisionMade = true;
                }

                if ( !decisionMade && (parts.length == 3 && parts[ 2 ] != null && "news".equals( parts[ 2 ] )) ) {
                    System.out.println( "Hello" );
                    List<Article> articles = controller.getAbstract( "articles", 0, "type_id" );
                    System.out.println( "size ? " + articles.size() );
                    response = new Gson().toJson( articles );
                    System.out.println( "Response is : " + response );
                    status = 200;
                    decisionMade = true;
                }

                break;
            case "POST":
                //use PUT to create resources, or use POST to update resources.

                isr = new InputStreamReader( he.getRequestBody(), "utf-8" );
                br = new BufferedReader( isr );
                jsonQuery = br.readLine();
                /*
                 * Save Client Identifier 
                 * URL : http://localhost:8084/api/loginId
                 * JSON : {"clientRN": 8 }
                 */
//                if ( parts.length > 2 && parts[ 2 ] != null && "clientId".equals( parts[ 2 ] ) ) {
//                    //response = new Gson().toJson( controller.createSongAPI( jsonQuery ) );
//                    int curClientId = Integer.parseInt( jsonQuery );
//                    if ( controller.addClientId( address, curClientId ) ) {
//                        response = new Gson().toJson( curClientId );
//                        status = 201;
//                    }
//                } 
                /*
                 * Evaluate username and password from user
                 * URL : http://localhost:8084/api/login
                 * JSON : {"username": "adminuser", "password":"$2a$05$zOsBcOSp9gpn1np..." }
                 */
//                else if ( parts.length > 2 && parts[ 2 ] != null && "login".equals( parts[ 2 ] ) ) {
//                    User user = controller.loginUser( address, jsonQuery );
//                    if ( user != null ) {
//                        response = new Gson().toJson( user );
//                        status = 200;
//                    } else {
//                        response = "{\"error\":\"Incorrect login info, Unauthorized\"}";
//                        status = 401;
//                    }
//                } 
                /*
                 * Evaluate username and password from user
                 * URL : http://localhost:8084/api/register
                 * JSON : {"username": "adminuser", "password":"$2a$05$zOsBcOSp9gpn1np..." }
                 */
//                 else if ( parts.length > 2 && parts[ 2 ] != null && "register".equals( parts[ 2 ] ) ) {
//                    boolean dbStatus = controller.registerUser( address, jsonQuery );
//                    if ( dbStatus ) {
//                        response = "{\"response\":\"Successfull registration\"}";
//                        status = 201;
//                    } else {
//                        response = "{\"error\":\"Internal server error\"}";
//                        status = 500;
//                    }
//                }
                /*
                 * Evaluate session user token
                 * URL : http://localhost:8084/api/session
                 * JSON : { qwerty12345 }
                 */
//                 else if ( parts.length > 2 && parts[ 2 ] != null && "session".equals( parts[ 2 ] ) ) {
//                    boolean dbStatus = controller.authenticateSession( address, jsonQuery );
//                    if ( dbStatus ) {
//                        response = "{\"response\":\"Successfull session id authentication\"}";
//                        status = 200;
//                    } else {
//                        response = "{\"error\":\"Expired, incorrect or non-existing session id, please relog.\"}";
//                        status = 401;
//                    }
//                } else {
//                    response = "404 Not found";
//                    status = 404;
//                }
                break;
            case "PUT":
                status = 500;
                response = "not supported";
                break;
            case "DELETE":
                status = 500;
                response = "not supported";
                break;
            default:
                status = 500;
                response = "not supported";
                break;
        }

        if ( file != null ) {

            he.sendResponseHeaders( 200, 0 );
            OutputStream os = he.getResponseBody();
            FileInputStream fs = new FileInputStream( file );
            final byte[] buffer = new byte[ 0x10000 ];
            int count = 0;
            while ( (count = fs.read( buffer )) >= 0 ) {
                os.write( buffer, 0, count );
            }
            fs.close();
            os.close();
        } else {
            he.getResponseHeaders().add( "Content-Type", "application/json" );
            he.sendResponseHeaders( status, 0 );
            try ( OutputStream os = he.getResponseBody() ) {
                os.write( response.getBytes() );
            }
        }
    }

    private String getMime( String extension ) {
        String mime = "";
        switch ( extension ) {
//            case ".pdf":
//                mime = "application/pdf";
//                break;
//            case ".png":
//                mime = "image/png";
//                break;
//            case ".jar":
//                mime = "application/java-archive";
//                break;
            case ".js":
                mime = "text/javascript";
                break;
            case ".html":
                mime = "text/html";
                break;
            case ".css":
                mime = "text/css";
                break;
            case ".ico":
                mime = "image/x-icon";
                break;
            case ".json":
                mime = "application/json";
            default:
                mime = "application/json";
                break;
        }
        return mime;
    }
}
