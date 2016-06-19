package entity.web;

public class WebSessions {

    private String username;
    private String sessionId;

    public WebSessions( String username, String sessionId ) {
        this.username = username;
        this.sessionId = sessionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername( String username ) {
        this.username = username;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId( String sessionId ) {
        this.sessionId = sessionId;
    }

}
