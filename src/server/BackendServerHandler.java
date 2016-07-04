package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import utilities.HttpServerGeneralUtils;

public class BackendServerHandler implements HttpHandler {

    private HttpServerGeneralUtils utilities;

    private static String pagesDirectory = "src/pages/backend/";
    private static String scriptsDirectory = "src/scripts/backend/";
    private static String componentsDirectory = "src/scripts/";

    public BackendServerHandler() {
        utilities = new HttpServerGeneralUtils();
    }

    @Override
    public void handle( HttpExchange he ) throws IOException {
        //ALL
        int status = 200;
        String method = he.getRequestMethod().toUpperCase();
        String address = he.getRemoteAddress().toString();

        String mime = null;

        String path = he.getRequestURI().getPath();
        String[] parts = path.split( "/" );

        File file = null;
        byte[] bytesToSend = null;

        //Debug START
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat( "HH:mm:ss" );
        String dateFormatted = formatter.format( date );
        System.out.println( "BackendServerHandler DEBUG: # " + dateFormatted + " # #Request method: " + method + ", Request path : " + path + ", path Length: " + parts.length + " / 2nd elem : " + (parts.length > 0 ? parts[ 1 ] : "NO") );
        //Debug END

        switch ( method ) {
            case "GET":
                /*
                 * Startup page
                 * URL : http://localhost:8084/emkobarona OR http://localhost:8084/emkobarona/index
                 */
                if ( parts.length == 2
                        || (parts.length == 3 && parts[ 2 ] != null && "index".equals( parts[ 2 ] )) ) {

                    mime = utilities.getMime( ".html" );
                    file = new File( pagesDirectory + "index.html" );

                } /*
                 * Register page
                 * URL : http://localhost:8084/emkobarona/register
                 */ else if ( parts.length == 3 && parts[ 2 ] != null && "register".equals( parts[ 2 ] ) ) {

                    mime = utilities.getMime( ".html" );
                    file = new File( pagesDirectory + "register.html" );

                } else if ( parts.length == 3 && parts[ 2 ] != null && "articles".equals( parts[ 2 ] ) ) {

                    mime = utilities.getMime( ".html" );
                    file = new File( pagesDirectory + "articles.html" );

                }/*
                 * Any other js, html, css, ico file
                 */ else {
                    String lastElemStr = parts[ (parts.length - 1) ];
                    int lastElemIndex = lastElemStr.lastIndexOf( "." );

                    if ( lastElemIndex > -1 ) {
                        mime = utilities.getMime( lastElemStr.substring( lastElemStr.lastIndexOf( "." ) ) );
                    } else {
                        mime = utilities.getMime( ".html" );
                    }

                    if ( "text/javascript".equals( mime ) || "text/css".equals( mime ) ) {
                        file = new File( scriptsDirectory + lastElemStr );
                        if ( !file.exists() || file.isDirectory() ) {
                            file = new File( componentsDirectory + lastElemStr );
                        }
                    } else {
                        file = new File( pagesDirectory + lastElemStr );
                    }
                    /*
                     * If error on file show index page
                     */
                    if ( !file.exists() || file.isDirectory() ) {
                        status = 404;
                        file = new File( pagesDirectory + "notfound.html" );
                    }
                }
                break;
            case "POST":
                status = 500;
                break;
            case "PUT":
                status = 500;
                break;
            case "DELETE":
                status = 500;
                break;
            default:
                status = 500;
                break;
        }

        if ( status == 200 || status == 404 ) {
            bytesToSend = new byte[ ( int ) file.length() ];
            if ( "text/css".equals( mime ) ) {
                he.getResponseHeaders().add( "Content-Type", "text/css" );
            }
            he.sendResponseHeaders( status, bytesToSend.length );

        } else if ( status == 500 ) {

            bytesToSend = "<h1>500 Not supported</h1>".getBytes();
            he.getResponseHeaders().add( "Content-Type", "application/json" );
            he.sendResponseHeaders( status, 0 );
        }

        //***BLACK MAGIC - START ***
        BufferedInputStream bis = new BufferedInputStream( new FileInputStream( file ) );
        bis.read( bytesToSend, 0, bytesToSend.length );
        //***BLACK MAGIC - END ***

        try ( OutputStream os = he.getResponseBody() ) {
            os.write( bytesToSend, 0, bytesToSend.length );
        }
    }

}
