package server;

import com.sun.net.httpserver.HttpServer;
import controller.Controller;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class BobkooHttpServer implements Runnable {

    private static int port = 8084;
    private static String ip = "127.0.0.1";

    private Logger logger = null;

    private static Controller controller;

    public static void main( String[] args ) throws IOException {
        if ( args.length >= 3 ) {
            port = Integer.parseInt( args[ 0 ] );
            ip = args[ 1 ];
        }
        new BobkooHttpServer().run();
    }

    @Override
    public void run() {
        try {
            controller = Controller.getInstance();
            logger = controller.getLogger();

            HttpServer server = HttpServer.create( new InetSocketAddress( ip, port ), 0 );

            //REST Routes
            server.createContext( "/emkobaronaAPI", new BackendServerAPIHandler( controller ) );
            server.createContext( "/api", new FrontendServerAPIHandler( controller ) );

            //HTTP Server Routes
            server.createContext( "/emkobarona", new BackendServerHandler() );
            server.createContext( "/", new FrontendServerHandler() );

            server.start();
            logger.info( "Java HTTP Server Started ! IP: " + ip + ", PORT: " + port );
        } catch ( IOException e ) {
            logger.severe( "Java HTTP Server IO Exception : " + e );
        }
    }
}
