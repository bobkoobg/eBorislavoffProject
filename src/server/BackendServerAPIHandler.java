package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.Controller;
import entity.Article;
import entity.ArticleType;
import entity.FlexibleSection;
import entity.Guestbook;
import entity.User;
import entity.web.HttpResponseObject;
import entity.web.UserWebSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import utilities.HttpServerGeneralUtils;

/*
 * This is the Client-Side Back-end (Admin) Handler.
 * It handles any request which "falls" under URL : http://localhost:8084/api/
 */
public class BackendServerAPIHandler implements HttpHandler {

    private Controller controller;
    private Random random;
    private HttpServerGeneralUtils utilities;
    private Gson gson;
    private HttpResponseObject httpResponseObj;

    private static String backendPagesDIR = "src/pages/backend/";

    public BackendServerAPIHandler( Controller controller ) {
        this.controller = controller;
        random = new Random();
        utilities = new HttpServerGeneralUtils();
        gson = new Gson();
    }

    @Override
    public void handle( HttpExchange httpExchange ) throws IOException {
        //General components
        String response = "";
        int status = 404;

        //Get clients request method
        String method = httpExchange.getRequestMethod().toUpperCase();

        //Get the whole url path and split it based on "/"
        String path = httpExchange.getRequestURI().getPath();
        String[] parts = path.split( "/" );

        //Get clients ip address
        String literalIPAddress = httpExchange.getRemoteAddress().toString();
        String address = literalIPAddress.
                substring( literalIPAddress.indexOf( "/" ) + 1, literalIPAddress.indexOf( ":" ) );

        //Response codes : https://developer.mozilla.org/en/docs/Web/HTTP/Response_codes
        //100	Continue	This interim response indicates that everything so far is OK and that the client should continue with the request or ignore it if it is already finished.
        //200	OK              The request has succeeded. The meaning of a success varies depending on the HTTP method:
        //201	Created         The request has succeeded and a new resource has been created as a result of it. This is typically the response sent after a PUT request.
        //400	Bad Request	This response means that server could not understand the request due to invalid syntax.
        //401	Unauthorized	Authentication is needed to get requested response. This is similar to 403, but in this case, authentication is possible.
        //404	Not Found	Server can not find requested resource. This response code probably is most famous one due to its frequency to occur in web.
        //411	Length Required	Server rejected the request because the Content-Length header field is not defined and the server requires it.
        //414	URI Too Long	The URI requested by the client is longer than the server is willing to interpret.
        //429	Too Many Requests	The user has sent too many requests in a given amount of time ("rate limiting").
        //500	Internal Server Error	The server has encountered a situation it doesn't know how to handle.
        //501	Not Implemented	The request method is not supported by the server and cannot be handled. The only methods that servers are required to support (and therefore that must not return this code) are GET and HEAD.
        //        
        //POST, PUT and DELETE components
        InputStreamReader isr = new InputStreamReader( httpExchange.getRequestBody(), "utf-8" );
        BufferedReader br = new BufferedReader( isr );
        String jsonQuery = br.readLine();

        //In case of request for components such as "nav"
        File file = null;
        byte[] bytesToSend = null;

        //Debug START
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat( "HH:mm:ss" );
        String dateFormatted = formatter.format( date );
        System.out.println( "BackendServerAPIHandler DEBUG: # " + dateFormatted + " # #Request method: " + method + ", Request path : " + path + ", path Length: " + parts.length + " / 2nd elem : " + (parts.length > 0 ? parts[ 1 ] : "NO") );
        //Debug END

        boolean decisionMade = false;

        //Only working with method types : "GET","POST","PUT","DELETE"
        //Use PUT to create resources, or use POST to update resources.
        switch ( method ) {
            case "GET":
                /*
                 * Generate server identifier and send to client
                 * URL : http://localhost:8084/emkobaronaAPI/articles/#cookie#
                 */
                if ( !decisionMade && (parts.length == 4 && parts[ 2 ] != null
                        && "articles".equals( parts[ 2 ] ) && parts[ 3 ] != null) ) {
                    if ( controller.authenticateSession( address, parts[ 3 ] ) ) {
                        status = 200;
                        List<Article> articles = controller.getAbstract( "articles", 0, "id" );
                        response = gson.toJson( articles );
                    } else {
                        status = 401;
                        httpResponseObj = new HttpResponseObject(
                                status, "Unauthorized - Expired, incorrect or non-existing session id, please relog." );
                        response = gson.toJson( httpResponseObj );
                    }
                    decisionMade = true;
                }
                /*
                 * Generate server identifier and send to client
                 * URL : http://localhost:8084/emkobaronaAPI/articletypes/#cookie#
                 */
                if ( !decisionMade && (parts.length == 4 && parts[ 2 ] != null
                        && "articletypes".equals( parts[ 2 ] ) && parts[ 3 ] != null) ) {
                    if ( controller.authenticateSession( address, parts[ 3 ] ) ) {
                        status = 200;
                        List<ArticleType> atList = controller.getAbstract( "articletypes", 0, "id" );
                        response = gson.toJson( atList );
                    } else {
                        status = 401;
                        httpResponseObj = new HttpResponseObject(
                                status, "Unauthorized - Expired, incorrect or non-existing session id, please relog." );
                        response = gson.toJson( httpResponseObj );
                    }
                    decisionMade = true;
                }
                /*
                 * Generate server identifier and send to client
                 * URL : http://localhost:8084/emkobaronaAPI/guestbook/#cookie#
                 */
                if ( !decisionMade && (parts.length == 4 && parts[ 2 ] != null
                        && "guestbook".equals( parts[ 2 ] ) && parts[ 3 ] != null) ) {
                    if ( controller.authenticateSession( address, parts[ 3 ] ) ) {
                        status = 200;
                        List<Guestbook> gbList = controller.getAbstract( "guestbook", 0, "id" );
                        response = gson.toJson( gbList );
                    } else {
                        status = 401;
                        httpResponseObj = new HttpResponseObject(
                                status, "Unauthorized - Expired, incorrect or non-existing session id, please relog." );
                        response = gson.toJson( httpResponseObj );
                    }
                    decisionMade = true;
                }
                /*
                 * Generate server identifier and send to client
                 * URL : http://localhost:8084/emkobaronaAPI/flexibleSections/#cookie#
                 */
                if ( !decisionMade && (parts.length == 4 && parts[ 2 ] != null
                        && "flexibleSections".equals( parts[ 2 ] ) && parts[ 3 ] != null) ) {
                    if ( controller.authenticateSession( address, parts[ 3 ] ) ) {
                        status = 200;
                        List<FlexibleSection> fsList = controller.getAbstract( "flexiblesections", 0, "id" );
                        response = gson.toJson( fsList );
                    } else {
                        status = 401;
                        httpResponseObj = new HttpResponseObject(
                                status, "Unauthorized - Expired, incorrect or non-existing session id, please relog." );
                        response = gson.toJson( httpResponseObj );
                    }
                    decisionMade = true;
                }
                /*
                 * URL : http://localhost:8084/emkobaronaAPI/article/{{ID}}/{{Cookie}}
                 */
                if ( !decisionMade && (parts.length == 5 && parts[ 2 ] != null
                        && "article".equals( parts[ 2 ] ) && parts[ 3 ] != null
                        && utilities.isNumeric( parts[ 3 ] ) && parts[ 4 ] != null) ) {
                    if ( controller.authenticateSession( address, parts[ 4 ] ) ) {
                        status = 200;
                        List<Article> articles = controller.getAbstract( "articles", parts[ 3 ], "id" );
                        response = gson.toJson( articles );
                    } else {
                        status = 401;
                        httpResponseObj = new HttpResponseObject(
                                status, "Unauthorized - Expired, incorrect or non-existing session id, please relog." );
                        response = gson.toJson( httpResponseObj );
                    }
                    decisionMade = true;
                }

                /*
                 * URL : http://localhost:8084/emkobaronaAPI/guestbook/{{ID}}/{{Cookie}}
                 */
                if ( !decisionMade && (parts.length == 5 && parts[ 2 ] != null
                        && "guestbook".equals( parts[ 2 ] ) && parts[ 3 ] != null
                        && utilities.isNumeric( parts[ 3 ] ) && parts[ 4 ] != null) ) {
                    if ( controller.authenticateSession( address, parts[ 4 ] ) ) {
                        status = 200;
                        List<Guestbook> guestbook = controller.getAbstract( "guestbook", parts[ 3 ], "id" );
                        response = gson.toJson( guestbook );
                    } else {
                        status = 401;
                        httpResponseObj = new HttpResponseObject(
                                status, "Unauthorized - Expired, incorrect or non-existing session id, please relog." );
                        response = gson.toJson( httpResponseObj );
                    }
                    decisionMade = true;
                }

                /*
                 * URL : http://localhost:8084/emkobaronaAPI/flexibleSections/{{ID}}/{{Cookie}}
                 */
                if ( !decisionMade && (parts.length == 5 && parts[ 2 ] != null
                        && "flexibleSections".equals( parts[ 2 ] ) && parts[ 3 ] != null
                        && utilities.isNumeric( parts[ 3 ] ) && parts[ 4 ] != null) ) {
                    if ( controller.authenticateSession( address, parts[ 4 ] ) ) {
                        status = 200;
                        List<FlexibleSection> fs = controller.getAbstract( "flexiblesections", parts[ 3 ], "id" );
                        response = gson.toJson( fs );
                    } else {
                        status = 401;
                        httpResponseObj = new HttpResponseObject(
                                status, "Unauthorized - Expired, incorrect or non-existing session id, please relog." );
                        response = gson.toJson( httpResponseObj );
                    }
                    decisionMade = true;
                }
                /*
                 * Generate server identifier and send to client
                 * URL : http://localhost:8084/emkobaronaAPI/nav
                 */
                if ( !decisionMade && (parts.length == 3 && parts[ 2 ] != null
                        && "nav".equals( parts[ 2 ] )) ) {
                    file = new File( backendPagesDIR + "nav.html" );
                    status = 200;
                    decisionMade = true;
                }

                break;
            case "POST":

                /*
                 * Save Client Identifier 
                 * URL : http://localhost:8084/emkobaronaAPI/clientId
                 * JSON : {"message": 8 }
                 */
                if ( !decisionMade && (parts.length == 3 && parts[ 2 ] != null
                        && "clientId".equals( parts[ 2 ] )) ) {

                    httpResponseObj = gson.fromJson( jsonQuery, HttpResponseObject.class );
                    int curClientId = Integer.parseInt( httpResponseObj.getMessage() );

                    if ( controller.addClientId( address, curClientId ) ) {
                        status = 200;
                        httpResponseObj.setResponseCode( status );
                        response = gson.toJson( httpResponseObj );
                    } else {
                        status = 401;
                        httpResponseObj = new HttpResponseObject(
                                401, "Unauthorized - You are not eligible to post a client id at this point." );
                        response = gson.toJson( httpResponseObj );
                    }
                    httpResponseObj = null;
                }

                /*
                 * Evaluate username and password from user
                 * URL : http://localhost:8084/emkobaronaAPI/login
                 * JSON : {"username": "adminuser", "password":"$2a$05$zOsBcOSp9gpn1np..." }
                 */
                if ( !decisionMade && (parts.length == 3 && parts[ 2 ] != null
                        && "login".equals( parts[ 2 ] )) ) {
                    UserWebSession userSessionObj = ( UserWebSession ) controller.loginUser( address, jsonQuery );

                    System.out.println( "DEBUG: # My user # " + userSessionObj.toString() );
                    if ( userSessionObj != null ) {
                        response = gson.toJson( userSessionObj );
                        status = 200;
                    } else {
                        httpResponseObj = new HttpResponseObject(
                                401, "Unauthorized - Please provide correct username and password" );
                        response = gson.toJson( httpResponseObj );
                        status = 401;
                    }
                    httpResponseObj = null;
                }

                /*
                 * Evaluate session user token
                 * URL : http://localhost:8084/aemkobaronaAPIpi/session
                 * JSON : { qwerty12345 }
                 */
                if ( !decisionMade && (parts.length == 3 && parts[ 2 ] != null
                        && "session".equals( parts[ 2 ] )) ) {

                    if ( controller.authenticateSession( address, jsonQuery ) ) {
                        status = 200;
                        httpResponseObj = new HttpResponseObject(
                                status, "OK - Successfull session id authentication" );
                        response = gson.toJson( httpResponseObj );
                    } else {
                        status = 401;
                        httpResponseObj = new HttpResponseObject(
                                status, "Unauthorized - Expired, incorrect or non-existing session id, please relog." );
                        response = gson.toJson( httpResponseObj );
                    }
                }

                /*
                 * Save an update of a article
                 * URL : http://localhost:8084/emkobaronaAPI/article/save/#ID#/#session#
                 */
                if ( !decisionMade && (parts.length == 6 && parts[ 2 ] != null
                        && "articles".equals( parts[ 2 ] ) && parts[ 3 ] != null
                        && "save".equals( parts[ 3 ] ) && parts[ 4 ] != null
                        && utilities.isNumeric( parts[ 4 ] ) && parts[ 5 ] != null) ) {
                    if ( controller.authenticateSession( address, parts[ 5 ] ) ) {

                        Article article = gson.fromJson( jsonQuery, Article.class );
                        article.setCreationDate( new Date() );

                        String username = controller.findUserBySessionAndIP( parts[ 5 ], address );
                        List<User> usersSpecific = controller.getAbstract( "users", username, "username" );

                        article.setUserId( usersSpecific.get( 0 ).getId() );

                        ArrayList<Article> articlesToUpdate = new ArrayList();
                        articlesToUpdate.add( article );
                        boolean result = controller.updateAbstract( "articles", articlesToUpdate );

                        if ( result ) {
                            status = 200;
                            response = gson.toJson( article );
                        } else {
                            status = 500;
                            httpResponseObj = new HttpResponseObject(
                                    status, "Internal server error - something went wrong." );
                            response = gson.toJson( httpResponseObj );
                        }

                    } else {
                        status = 401;
                        httpResponseObj = new HttpResponseObject(
                                status, "Unauthorized - Expired, incorrect or non-existing session id, please relog." );
                        response = gson.toJson( httpResponseObj );
                    }
                    decisionMade = true;
                }

                /*
                 * Save an update of a guestbook
                 * URL : http://localhost:8084/emkobaronaAPI/guestbook/save/#ID#/#session#
                 */
                if ( !decisionMade && (parts.length == 6 && parts[ 2 ] != null
                        && "guestbook".equals( parts[ 2 ] ) && parts[ 3 ] != null
                        && "save".equals( parts[ 3 ] ) && parts[ 4 ] != null
                        && utilities.isNumeric( parts[ 4 ] ) && parts[ 5 ] != null) ) {
                    if ( controller.authenticateSession( address, parts[ 5 ] ) ) {

                        Guestbook gb = gson.fromJson( jsonQuery, Guestbook.class );
                        gb.setCreationDate( new Date() );
                        gb.setIp( address );
                        gb.setId( Integer.parseInt( parts[ 4 ] ) );

                        System.out.println( "gb to update ? " + gb.toString() );
                        ArrayList<Guestbook> gbToUpdate = new ArrayList();
                        gbToUpdate.add( gb );
                        boolean result = controller.updateAbstract( "guestbook", gbToUpdate );

                        if ( result ) {
                            System.out.println( "Yes??" );
                            status = 200;
                            response = gson.toJson( gb );
                        } else {
                            status = 500;
                            httpResponseObj = new HttpResponseObject(
                                    status, "Internal server error - something went wrong." );
                            response = gson.toJson( httpResponseObj );
                        }

                    } else {
                        status = 401;
                        httpResponseObj = new HttpResponseObject(
                                status, "Unauthorized - Expired, incorrect or non-existing session id, please relog." );
                        response = gson.toJson( httpResponseObj );
                    }
                    decisionMade = true;
                }

                /*
                 * Save an update of a flexible sections
                 * URL : http://localhost:8084/emkobaronaAPI/flexibleSections/save/#ID#/#session#
                 */
                if ( !decisionMade && (parts.length == 6 && parts[ 2 ] != null
                        && "flexibleSections".equals( parts[ 2 ] ) && parts[ 3 ] != null
                        && "save".equals( parts[ 3 ] ) && parts[ 4 ] != null
                        && utilities.isNumeric( parts[ 4 ] ) && parts[ 5 ] != null) ) {
                    if ( controller.authenticateSession( address, parts[ 5 ] ) ) {

                        FlexibleSection fs = gson.fromJson( jsonQuery, FlexibleSection.class );
                        fs.setCreationdate( new Date() );
                        
                        String username = controller.findUserBySessionAndIP( parts[ 5 ], address );
                        List<User> usersSpecific = controller.getAbstract( "users", username, "username" );
                        fs.setUser_id( usersSpecific.get( 0 ).getId() );

                        List<FlexibleSection> previousFSobj = controller.getAbstract( "flexiblesections", fs.getId(), "id" );
                        fs.setFs_purpose( previousFSobj.get( 0 ).getFs_purpose() );

                        ArrayList<FlexibleSection> fsToUpdate = new ArrayList();
                        fsToUpdate.add( fs );
                        boolean result = controller.updateAbstract( "flexiblesections", fsToUpdate );
                        
                        if ( result ) {
                            status = 200;
                            response = gson.toJson( fs );
                        } else {
                            status = 500;
                            httpResponseObj = new HttpResponseObject(
                                    status, "Internal server error - something went wrong." );
                            response = gson.toJson( httpResponseObj );
                        }

                    } else {
                        status = 401;
                        httpResponseObj = new HttpResponseObject(
                                status, "Unauthorized - Expired, incorrect or non-existing session id, please relog." );
                        response = gson.toJson( httpResponseObj );
                    }
                    decisionMade = true;
                }
                break;
            case "PUT":
                /*
                 * Create (generate) server identifier for logging OR registering 
                 * and send it back to the client
                 * URL : http://localhost:8084/api/loginServerId
                 * URL : http://localhost:8084/api/registerServerId
                 */
                if ( parts.length == 3 && parts[ 2 ] != null
                        && ("loginServerId".equals( parts[ 2 ] )
                        || "registerServerId".equals( parts[ 2 ] )) ) {

                    int curServerId = random.nextInt( 10 - 1 ) + 1;
                    boolean isLogin = "loginServerId".equals( parts[ 2 ] ) ? true : false;
                    if ( controller.createUserIdentifierObj(
                            address, curServerId, isLogin ? "login" : "register" ) ) {
                        httpResponseObj = new HttpResponseObject( 201, Integer.toString( curServerId ) );
                        response = gson.toJson( httpResponseObj );
                        status = 201;
                        decisionMade = true;
                    }
                    httpResponseObj = null;
                }

                /*
                 * Create new user and add to database username and password from user
                 * URL : http://localhost:8084/emkobaronaAPI/register
                 * JSON : {"username": "adminuser", "password":"$2a$05$zOsBcOSp9gpn1np..." }
                 */
                if ( !decisionMade && (parts.length == 3 && parts[ 2 ] != null
                        && "register".equals( parts[ 2 ] )) ) {

                    if ( controller.registerUser( address, jsonQuery ) ) {
                        status = 201;
                        httpResponseObj = new HttpResponseObject(
                                status, "Created - Successfull registration" );
                        response = gson.toJson( httpResponseObj );
                    } else {
                        status = 500;
                        httpResponseObj = new HttpResponseObject(
                                status, "Internal Server Error - Internal server error" );
                        response = gson.toJson( httpResponseObj );
                    }
                    httpResponseObj = null;
                }

                /*
                 * Create an article
                 * URL : http://localhost:8084/emkobaronaAPI/articles/create/#Cookie#
                 */
                if ( !decisionMade && (parts.length == 5 && parts[ 2 ] != null
                        && "articles".equals( parts[ 2 ] ) && parts[ 3 ] != null
                        && "create".equals( parts[ 3 ] ) && parts[ 4 ] != null) ) {
                    if ( controller.authenticateSession( address, parts[ 4 ] ) ) {

                        Article article = gson.fromJson( jsonQuery, Article.class );
                        article.setCreationDate( new Date() );

                        String username = controller.findUserBySessionAndIP( parts[ 4 ], address );
                        List<User> usersSpecific = controller.getAbstract( "users", username, "username" );

                        article.setUserId( usersSpecific.get( 0 ).getId() );

                        System.out.println( "Finally the article is : " + article.toString() );
                        ArrayList<Article> articlesToAdd = new ArrayList();
                        articlesToAdd.add( article );
                        boolean result = controller.insertAbstract( "articles", articlesToAdd );
                        System.out.println( "Hey the result is " + result );
                        if ( result ) {
                            status = 201;
                            response = gson.toJson( article );
                        } else {
                            status = 500;
                            httpResponseObj = new HttpResponseObject(
                                    status, "Internal server error - something went wrong." );
                            response = gson.toJson( httpResponseObj );
                        }
                    } else {
                        status = 401;
                        httpResponseObj = new HttpResponseObject(
                                status, "Unauthorized - Expired, incorrect or non-existing session id, please relog." );
                        response = gson.toJson( httpResponseObj );
                    }
                    decisionMade = true;
                }

                /*
                 * Uplaod new image for a guestbook entry
                 * URL : http://localhost:8084/emkobaronaAPI/guestbook/create/#Cookie#
                 */
                if ( !decisionMade && (parts.length == 5 && parts[ 2 ] != null
                        && "guestbook".equals( parts[ 2 ] ) && parts[ 3 ] != null
                        && "create".equals( parts[ 3 ] ) && parts[ 4 ] != null) ) {
                    if ( controller.authenticateSession( address, parts[ 4 ] ) ) {

                        Guestbook gb = gson.fromJson( jsonQuery, Guestbook.class );
                        gb.setCreationDate( new Date() );
                        gb.setIp( address );

                        ArrayList<Guestbook> gbToAdd = new ArrayList();
                        gbToAdd.add( gb );
                        boolean result = controller.insertAbstract( "guestbook", gbToAdd );
                        System.out.println( "Hey the result is " + result );
                        if ( result ) {
                            status = 201;
                            response = gson.toJson( gb );
                        } else {
                            status = 500;
                            httpResponseObj = new HttpResponseObject(
                                    status, "Internal server error - something went wrong." );
                            response = gson.toJson( httpResponseObj );
                        }
                    } else {
                        status = 401;
                        httpResponseObj = new HttpResponseObject(
                                status, "Unauthorized - Expired, incorrect or non-existing session id, please relog." );
                        response = gson.toJson( httpResponseObj );
                    }
                    decisionMade = true;
                }
                break;
            case "DELETE":
                /*
                 * Delete an article
                 * URL : http://localhost:8084/emkobaronaAPI/articles/delete/#ID#/#Cookie#
                 */
                if ( !decisionMade && (parts.length == 6 && parts[ 2 ] != null
                        && "articles".equals( parts[ 2 ] ) && parts[ 3 ] != null
                        && "delete".equals( parts[ 3 ] ) && parts[ 4 ] != null
                        && utilities.isNumeric( parts[ 4 ] ) && parts[ 5 ] != null) ) {
                    if ( controller.authenticateSession( address, parts[ 5 ] ) ) {
                        boolean result = controller.deleteAbstract( "articles", Integer.parseInt( parts[ 4 ] ), "id" );

                        if ( result ) {
                            status = 200;
                            response = gson.toJson( result );
                        } else {
                            status = 500;
                            httpResponseObj = new HttpResponseObject(
                                    status, "Internal server error - something went wrong." );
                            response = gson.toJson( httpResponseObj );
                        }
                    } else {
                        status = 401;
                        httpResponseObj = new HttpResponseObject(
                                status, "Unauthorized - Expired, incorrect or non-existing session id, please relog." );
                        response = gson.toJson( httpResponseObj );
                    }
                    decisionMade = true;
                }

                /*
                 * Delete an guestbook
                 * URL : http://localhost:8084/emkobaronaAPI/guestbook/delete/#ID#/#Cookie#
                 */
                if ( !decisionMade && (parts.length == 6 && parts[ 2 ] != null
                        && "guestbook".equals( parts[ 2 ] ) && parts[ 3 ] != null
                        && "delete".equals( parts[ 3 ] ) && parts[ 4 ] != null
                        && utilities.isNumeric( parts[ 4 ] ) && parts[ 5 ] != null) ) {
                    if ( controller.authenticateSession( address, parts[ 5 ] ) ) {
                        boolean result = controller.deleteAbstract( "guestbook", Integer.parseInt( parts[ 4 ] ), "id" );

                        if ( result ) {
                            status = 200;
                            response = gson.toJson( result );
                        } else {
                            status = 500;
                            httpResponseObj = new HttpResponseObject(
                                    status, "Internal server error - something went wrong." );
                            response = gson.toJson( httpResponseObj );
                        }
                    } else {
                        status = 401;
                        httpResponseObj = new HttpResponseObject(
                                status, "Unauthorized - Expired, incorrect or non-existing session id, please relog." );
                        response = gson.toJson( httpResponseObj );
                    }
                    decisionMade = true;
                }
                break;
            default:
                httpResponseObj = new HttpResponseObject(
                        500, "Not supported - Your method : " + method );
                status = 500;
                response = gson.toJson( httpResponseObj );
                break;
        }

        if ( file != null ) {

            httpExchange.sendResponseHeaders( 200, 0 );
            OutputStream os = httpExchange.getResponseBody();
            FileInputStream fs = new FileInputStream( file );
            final byte[] buffer = new byte[ 0x10000 ];
            int count = 0;
            while ( (count = fs.read( buffer )) >= 0 ) {
                os.write( buffer, 0, count );
            }
            fs.close();
            os.close();
        } else {
            //getResponseHeaders should always be application/json because of the
            //communication with the client sides Ajax.
            httpExchange.getResponseHeaders().add( "Content-Type", "application/json" );
            //status, response.length() are must for the sendResponseHeaders, so that
            //we can send the actual status and response *Yes, I do stupid mistakes*
            httpExchange.sendResponseHeaders( status, response.length() );
            //Spit it out.
            try ( OutputStream os = httpExchange.getResponseBody() ) {
                os.write( response.getBytes() );
            }
        }
    }
}
