package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.Controller;
import entity.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import utilities.HttpServerGeneralUtils;

public class BackendServerAPIHandler implements HttpHandler {

    private Controller controller;
    private Random random;
    private HttpServerGeneralUtils utilities;

    public BackendServerAPIHandler( Controller controller ) {
        this.controller = controller;
        random = new Random();
        utilities = new HttpServerGeneralUtils();
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

        //Debug START
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat( "HH:mm:ss" );
        String dateFormatted = formatter.format( date );
        System.out.println( "BackendServerAPIHandler DEBUG: # " + dateFormatted + " # #Request method: " + method + ", Request path : " + path + ", path Length: " + parts.length + " / 2nd elem : " + (parts.length > 0 ? parts[ 1 ] : "NO") );
        //Debug END

        switch ( method ) {
            case "GET":
                /*
                 * Generate server identifier and send to client
                 * URL : http://localhost:8084/api/loginId
                 */
                if ( parts.length > 2 && parts[ 2 ] != null && "loginServerId".equals( parts[ 2 ] ) ) {

                    int curServerId = random.nextInt( 10 - 1 ) + 1;
                    if ( controller.createUserIdentifierObj( address, curServerId, "login" ) ) {
                        response = new Gson().toJson( curServerId );
                        status = 201;
                    }
                } else if ( parts.length > 2 && parts[ 2 ] != null && "registerServerId".equals( parts[ 2 ] ) ) {

                    int curServerId = random.nextInt( 10 - 1 ) + 1;
                    if ( controller.createUserIdentifierObj( address, curServerId, "register" ) ) {
                        response = new Gson().toJson( curServerId );
                        status = 201;
                    }
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
                if ( parts.length > 2 && parts[ 2 ] != null && "clientId".equals( parts[ 2 ] ) ) {
                    //response = new Gson().toJson( controller.createSongAPI( jsonQuery ) );
                    int curClientId = Integer.parseInt( jsonQuery );
                    if ( controller.addClientId( address, curClientId ) ) {
                        response = new Gson().toJson( curClientId );
                        status = 201;
                    }
                } /*
                 * Evaluate username and password from user
                 * URL : http://localhost:8084/api/login
                 * JSON : {"username": "adminuser", "password":"$2a$05$zOsBcOSp9gpn1np..." }
                 */ else if ( parts.length > 2 && parts[ 2 ] != null && "login".equals( parts[ 2 ] ) ) {
                     System.out.println( "HEllo ?" );
                    User user = controller.loginUser( address, jsonQuery );
                    System.out.println( "User > " + user.toString() );
                    if ( user != null ) {
                        System.out.println( "fine!" );
                        response = new Gson().toJson( user );
                        status = 200;
                    } else {
                        System.out.println( "not fine!" );
                        response = "{\"error\":\"Incorrect login info, Unauthorized\"}";
                        status = 401;
                    }
                } /*
                 * Evaluate username and password from user
                 * URL : http://localhost:8084/api/register
                 * JSON : {"username": "adminuser", "password":"$2a$05$zOsBcOSp9gpn1np..." }
                 */ else if ( parts.length > 2 && parts[ 2 ] != null && "register".equals( parts[ 2 ] ) ) {
                    boolean dbStatus = controller.registerUser( address, jsonQuery );
                    if ( dbStatus ) {
                        response = "{\"response\":\"Successfull registration\"}";
                        status = 201;
                    } else {
                        response = "{\"error\":\"Internal server error\"}";
                        status = 500;
                    }
                }/*
                 * Evaluate session user token
                 * URL : http://localhost:8084/api/session
                 * JSON : { qwerty12345 }
                 */ else if ( parts.length > 2 && parts[ 2 ] != null && "session".equals( parts[ 2 ] ) ) {
                    boolean dbStatus = controller.authenticateSession( address, jsonQuery );
                    if ( dbStatus ) {
                        response = "{\"response\":\"Successfull session id authentication\"}";
                        status = 200;
                    } else {
                        response = "{\"error\":\"Expired, incorrect or non-existing session id, please relog.\"}";
                        status = 401;
                    }
                } else {
                    response = "404 Not found";
                    status = 404;
                }
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

        he.getResponseHeaders().add( "Content-Type", "application/json" );
        he.sendResponseHeaders( status, 0 );
        try ( OutputStream os = he.getResponseBody() ) {
            os.write( response.getBytes() );
        }
    }
}
