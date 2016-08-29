package utilities;

import entity.web.UserSession;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

public class SessionIDsGenerator {

    private SecureRandom random;
    private Map<String, UserSession> sessionsList;
    private long MAX_DURATION = MILLISECONDS.convert( 30, MINUTES );

    private static String errorMessage = "Empty essential parameters";

    public SessionIDsGenerator() {
        random = new SecureRandom();
        sessionsList = new HashMap();
    }

    public void setMAX_DURATION( long MAX_DURATION ) {
        this.MAX_DURATION = MAX_DURATION;
    }

    private String nextSessionId() {
        return new BigInteger( 130, random ).toString( 32 );
    }

    public String registerSession( String ipAddress, String username ) {
        if ( ipAddress == null || ipAddress.isEmpty() || username == null
                || username.isEmpty() ) {
            return errorMessage;
        }

        Date now = new Date();

        for ( Map.Entry<String, UserSession> entry : sessionsList.entrySet() ) {

            String key = entry.getKey();
            UserSession value = entry.getValue();

            if ( now.getTime() - value.getSessionTime().getTime() >= MAX_DURATION ) {
                sessionsList.remove( key );
            } else if ( ipAddress.equals( value.getIpAddress() ) && username.equals( value.getUsername() ) ) {
                return key;
            }
        }

        String newKey = nextSessionId();
        sessionsList.put( newKey, new UserSession( ipAddress, username ) );
        return newKey;
    }

    public boolean checkSession( String sessionId, String ipAddress ) {
        if ( ipAddress == null || ipAddress.isEmpty() || sessionId == null
                || sessionId.isEmpty() ) {
            return false;
        }

        Date now = new Date();

        for ( Map.Entry<String, UserSession> entry : sessionsList.entrySet() ) {

            String key = entry.getKey();
            UserSession value = entry.getValue();

            if ( now.getTime() - value.getSessionTime().getTime() >= MAX_DURATION ) {
                sessionsList.remove( key );
                return false;
            } else if ( sessionId.equals( key ) && ipAddress.equals( value.getIpAddress() ) ) {
                return true;
            }
        }
        return false;
    }

    public String findUserBySession( String sessionId, String ipAddress ) {
        if ( ipAddress == null || ipAddress.isEmpty() || sessionId == null
                || sessionId.isEmpty() ) {
            return "";
        }

        for ( Map.Entry<String, UserSession> entry : sessionsList.entrySet() ) {

            String key = entry.getKey();
            UserSession value = entry.getValue();

            if ( key.equals( sessionId ) && value.getIpAddress().equals( ipAddress ) ) {
                return value.getUsername();
            }
        }
        return "";
    }
}
