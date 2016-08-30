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

        boolean decisionMade = false;

        switch ( method ) {
            case "GET":

                /*
                 * Startup page
                 * URL : http://localhost:8084/emkobarona OR /index OR /index.html
                 */
                boolean isIndex = parts.length == 2 && "emkobarona".equals( parts[ 1 ] ) ? true : false;
                boolean isIndex2 = parts.length == 3 && parts[ 2 ] != null && "index".equals( parts[ 2 ] ) ? true : false;
                boolean isIndex3 = parts.length == 3 && parts[ 2 ] != null && "index.html".equals( parts[ 2 ] ) ? true : false;
                if ( !decisionMade && (isIndex || isIndex2 || isIndex3) ) {
                    mime = utilities.getMime( ".html" );
                    file = new File( pagesDirectory + "index.html" );
                    decisionMade = true;
                }

                /*
                 * Login page
                 * URL : http://localhost:8084/emkobarona/login OR /login.html
                 */
                boolean isLogin = parts.length == 3 && parts[ 2 ] != null && "login".equals( parts[ 2 ] ) ? true : false;
                boolean isLogin2 = parts.length == 3 && parts[ 2 ] != null && "login.html".equals( parts[ 2 ] ) ? true : false;
                if ( !decisionMade && (isLogin || isLogin2) ) {
                    mime = utilities.getMime( ".html" );
                    file = new File( pagesDirectory + "login.html" );
                    decisionMade = true;
                }

                /*
                 * Register page
                 * URL : http://localhost:8084/emkobarona/register OR /register.html
                 */
                boolean isRegister = parts.length == 3 && parts[ 2 ] != null && "register".equals( parts[ 2 ] ) ? true : false;
                boolean isRegister2 = parts.length == 3 && parts[ 2 ] != null && "register.html".equals( parts[ 2 ] ) ? true : false;
                if ( !decisionMade && (isRegister || isRegister2) ) {
                    mime = utilities.getMime( ".html" );
                    file = new File( pagesDirectory + "register.html" );
                    decisionMade = true;
                }

                /*
                 * Articles page
                 * URL : http://localhost:8084/emkobarona/articles OR /articles.html
                 */
                boolean isArticles = parts.length == 3 && parts[ 2 ] != null && "articles".equals( parts[ 2 ] ) ? true : false;
                boolean isArticles2 = parts.length == 3 && parts[ 2 ] != null && "articles.html".equals( parts[ 2 ] ) ? true : false;
                if ( !decisionMade && (isArticles || isArticles2) ) {
                    mime = utilities.getMime( ".html" );
                    file = new File( pagesDirectory + "articles.html" );
                    decisionMade = true;
                }

                /*
                 * Specific Articles page - Edit article
                 * URL : http://localhost:8084/emkobarona/articles/edit/#ID#
                 * OR Create article
                 * URL : http://localhost:8084/emkobarona/articles/create
                 */
                boolean isEditingArticle = parts.length == 5 && parts[ 2 ] != null
                        && "articles".equals( parts[ 2 ] ) && parts[ 3 ] != null
                        && "edit".equals( parts[ 3 ] ) && utilities.isNumeric( parts[ 4 ] ) ? true : false;
                boolean isCreatingArticle = parts.length == 4 && parts[ 2 ] != null
                        && "articles".equals( parts[ 2 ] ) && parts[ 3 ] != null
                        && "create".equals( parts[ 3 ] ) ? true : false;
                if ( !decisionMade && (isEditingArticle || isCreatingArticle) ) {
                    mime = utilities.getMime( ".html" );
                    file = new File( pagesDirectory + "articlesSpecific.html" );
                    decisionMade = true;
                }

                /*
                 * Gallery page
                 * URL : http://localhost:8084/emkobarona/gallery OR /gallery.html
                 */
                boolean isGallery = parts.length == 3 && parts[ 2 ] != null && "gallery".equals( parts[ 2 ] ) ? true : false;
                boolean isGallery2 = parts.length == 3 && parts[ 2 ] != null && "gallery.html".equals( parts[ 2 ] ) ? true : false;
                if ( !decisionMade && (isGallery || isGallery2) ) {
                    mime = utilities.getMime( ".html" );
                    file = new File( pagesDirectory + "gallery.html" );
                    decisionMade = true;
                }

                /*
                 * Tickets page
                 * URL : http://localhost:8084/emkobarona/tickets OR /tickets.html
                 */
                boolean isTickets = parts.length == 3 && parts[ 2 ] != null && "tickets".equals( parts[ 2 ] ) ? true : false;
                boolean isTickets2 = parts.length == 3 && parts[ 2 ] != null && "tickets.html".equals( parts[ 2 ] ) ? true : false;
                if ( !decisionMade && (isTickets || isTickets2) ) {
                    mime = utilities.getMime( ".html" );
                    file = new File( pagesDirectory + "tickets.html" );
                    decisionMade = true;
                }

                /*
                 * Guestbook page
                 * URL : http://localhost:8084/emkobarona/guestbook OR /guestbook.html
                 */
                boolean isGuestbook = parts.length == 3 && parts[ 2 ] != null && "guestbook".equals( parts[ 2 ] ) ? true : false;
                boolean isGuestbook2 = parts.length == 3 && parts[ 2 ] != null && "guestbook.html".equals( parts[ 2 ] ) ? true : false;
                if ( !decisionMade && (isGuestbook || isGuestbook2) ) {
                    mime = utilities.getMime( ".html" );
                    file = new File( pagesDirectory + "guestbook.html" );
                    decisionMade = true;
                }
                
                                /*
                 * TODO : Specific Articles page - Edit guestbook
                 * URL : http://localhost:8084/emkobarona/articles/edit/#ID#
                 * OR Create guestbook entry (Added)
                 * URL : http://localhost:8084/emkobarona/guestbook/create
                 */
//                boolean isEditingGuestbook = parts.length == 5 && parts[ 2 ] != null
//                        && "articles".equals( parts[ 2 ] ) && parts[ 3 ] != null
//                        && "edit".equals( parts[ 3 ] ) && utilities.isNumeric( parts[ 4 ] ) ? true : false;
                boolean isCreatingGuestbook = parts.length == 4 && parts[ 2 ] != null
                        && "guestbook".equals( parts[ 2 ] ) && parts[ 3 ] != null
                        && "create".equals( parts[ 3 ] ) ? true : false;
                if ( !decisionMade && isCreatingGuestbook ) {
                    mime = utilities.getMime( ".html" );
                    file = new File( pagesDirectory + "guestbookSpecific.html" );
                    decisionMade = true;
                }


                /*
                 * FlexibleSections page
                 * URL : http://localhost:8084/emkobarona/flexibleSections OR /flexibleSections.html
                 */
                boolean isFS = parts.length == 3 && parts[ 2 ] != null && "flexibleSections".equals( parts[ 2 ] ) ? true : false;
                boolean isFS2 = parts.length == 3 && parts[ 2 ] != null && "flexibleSections.html".equals( parts[ 2 ] ) ? true : false;
                if ( !decisionMade && (isFS || isFS2) ) {
                    mime = utilities.getMime( ".html" );
                    file = new File( pagesDirectory + "flexibleSections.html" );
                    decisionMade = true;
                }

                /*
                 * Users page
                 * URL : http://localhost:8084/emkobarona/users OR /users.html
                 */
                boolean isUsers = parts.length == 3 && parts[ 2 ] != null && "users".equals( parts[ 2 ] ) ? true : false;
                boolean isUsers2 = parts.length == 3 && parts[ 2 ] != null && "users.html".equals( parts[ 2 ] ) ? true : false;
                if ( !decisionMade && (isUsers || isUsers2) ) {
                    mime = utilities.getMime( ".html" );
                    file = new File( pagesDirectory + "users.html" );
                    decisionMade = true;
                }

                /*
                 * Any other js, html, css, ico file
                 */
                if ( !decisionMade ) {
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
