package entity.web;

import entity.User;

public class UserWebSession extends User {

    private String sessionId;

    public UserWebSession( String username, String sessionId ) {
        this.username = username;
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId( String sessionId ) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "UserWebSession{" + "sessionId=" + sessionId + '}' + "> User{"
                + "username=" + username + '}';
    }

}
