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

public class FrontendServerHandler implements HttpHandler {

    private static String frontendPagesDIR = "src/pages/frontend/";
    private static String frontendScriptsDIR = "src/scripts/frontend/";
    private static String componentsDirectory = "src/scripts/";

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
        System.out.println( "FrontendServerHandler DEBUG: # " + dateFormatted + " # #Request method: " + method + ", Request path : " + path + ", path Length: " + parts.length + " / 2nd elem : " + (parts.length > 0 ? parts[ 1 ] : "NO") );
        //Debug END

        switch ( method ) {
            case "GET":
                boolean decisionMade = false;

                boolean isIndex = parts.length == 0 ? true : false;
                boolean isIndex2 = parts.length == 2 && "index".equals( parts[ 1 ] ) ? true : false;
                boolean isIndex3 = parts.length == 2 && "index.html".equals( parts[ 1 ] ) ? true : false;
                if ( isIndex || isIndex2 || isIndex3 ) {

                    mime = getMime( ".html" );
                    file = new File( frontendPagesDIR + "index.html" );
                    decisionMade = true;
                }

                boolean isNews = parts.length == 2 && "news".equals( parts[ 1 ] ) ? true : false;
                boolean isNews2 = parts.length == 2 && "news.html".equals( parts[ 1 ] ) ? true : false;
                if ( !decisionMade && (isNews || isNews2) ) {

                    mime = getMime( ".html" );
                    file = new File( frontendPagesDIR + "news.html" );
                    decisionMade = true;
                }

                boolean isNewsSpecific = parts.length == 3 && "news".equals( parts[ 1 ] )
                        && parts[ 2 ] != null && isNumeric( parts[ 2 ] ) ? true : false;
                if ( !decisionMade && isNewsSpecific ) {

                    mime = getMime( ".html" );
                    file = new File( frontendPagesDIR + "newsSpecific.html" );
                    decisionMade = true;
                }

                boolean isExercises = parts.length == 2 && "exercises".equals( parts[ 1 ] ) ? true : false;
                boolean isExercises2 = parts.length == 2 && "exercises.html".equals( parts[ 1 ] ) ? true : false;
                if ( !decisionMade && (isExercises || isExercises2) ) {

                    mime = getMime( ".html" );
                    file = new File( frontendPagesDIR + "exercises.html" );
                    decisionMade = true;
                }
                
                boolean isExercisesSpecific = parts.length == 3 && "exercises".equals( parts[ 1 ] )
                        && parts[ 2 ] != null && isNumeric( parts[ 2 ] ) ? true : false;
                if ( !decisionMade && isExercisesSpecific ) {

                    mime = getMime( ".html" );
                    file = new File( frontendPagesDIR + "exercisesSpecific.html" );
                    decisionMade = true;
                }

                boolean isGallery = parts.length == 2 && "gallery".equals( parts[ 1 ] ) ? true : false;
                boolean isGallery2 = parts.length == 2 && "gallery.html".equals( parts[ 1 ] ) ? true : false;
                if ( !decisionMade && (isGallery || isGallery2) ) {

                    mime = getMime( ".html" );
                    file = new File( frontendPagesDIR + "gallery.html" );
                    decisionMade = true;
                }

                boolean isServices = parts.length == 2 && "services".equals( parts[ 1 ] ) ? true : false;
                boolean isServices2 = parts.length == 2 && "services.html".equals( parts[ 1 ] ) ? true : false;
                if ( !decisionMade && (isServices || isServices2) ) {

                    mime = getMime( ".html" );
                    file = new File( frontendPagesDIR + "services.html" );
                    decisionMade = true;
                }

                boolean isContacts = parts.length == 2 && "contacts".equals( parts[ 1 ] ) ? true : false;
                boolean isContacts2 = parts.length == 2 && "contacts.html".equals( parts[ 1 ] ) ? true : false;
                if ( !decisionMade && (isContacts || isContacts2) ) {

                    mime = getMime( ".html" );
                    file = new File( frontendPagesDIR + "contacts.html" );
                    decisionMade = true;
                }

                boolean isFeedback = parts.length == 2 && "feedback".equals( parts[ 1 ] ) ? true : false;
                boolean isFeedback2 = parts.length == 2 && "feedback.html".equals( parts[ 1 ] ) ? true : false;
                if ( !decisionMade && (isFeedback || isFeedback2) ) {

                    mime = getMime( ".html" );
                    file = new File( frontendPagesDIR + "feedback.html" );
                    decisionMade = true;
                }

                boolean isBiography = parts.length == 2 && "biography".equals( parts[ 1 ] ) ? true : false;
                boolean isBiography2 = parts.length == 2 && "biography.html".equals( parts[ 1 ] ) ? true : false;
                if ( !decisionMade && (isBiography || isBiography2) ) {

                    mime = getMime( ".html" );
                    file = new File( frontendPagesDIR + "biography.html" );
                    decisionMade = true;
                }
                //General "file searcher"
                if ( !decisionMade || (!file.exists() || file.isDirectory()) ) {

                    //Extract mime out of getRequestURI path
                    String lastElemStr = parts[ (parts.length - 1) ];
                    mime = lastElemStr.lastIndexOf( "." ) > -1
                            ? getMime( lastElemStr.substring( lastElemStr.lastIndexOf( "." ) ) )
                            : getMime( ".html" );

                    if ( "text/javascript".equals( mime ) || "text/css".equals( mime ) ) {
                        file = new File( frontendScriptsDIR + lastElemStr );
                    } else {
                        file = new File( frontendPagesDIR + lastElemStr );
                    }

                    //Display 404 Not found in case of non-existing file
                    if ( !file.exists() || file.isDirectory() ) {

                        status = 404;
                        file = new File( frontendPagesDIR + "notfound.html" );

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
            default:
                mime = "text/html";
                break;
        }
        return mime;
    }

    private static Boolean isNumeric( String str ) {
        try {
            Integer d = Integer.parseInt( str );
        } catch ( NumberFormatException nfe ) {
            return false;
        }
        return true;
    }

}
