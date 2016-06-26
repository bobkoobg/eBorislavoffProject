package entity.web;

import entity.User;
import java.util.Date;

public class UserSession extends User {

    private String ipAddress;
    private Date sessionTime;

    public UserSession( String ipAddress, String username ) {
        this.ipAddress = ipAddress;
        this.username = username;
        sessionTime = new Date();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Date getSessionTime() {
        return sessionTime;
    }

    public String getUsername() {
        return username;
    }

}
