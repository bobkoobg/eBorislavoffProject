package entity.web;

import entity.Guestbook;
import java.util.Date;

public class GuestbookSecret extends Guestbook {

    private String secret;

    public GuestbookSecret( String guestName, String message, String ip, Date creationDate,
            String secret ) {
        this.guestName = guestName;
        this.message = message;
        this.ip = ip;
        this.creationDate = creationDate;
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getMessage() {
        return message;
    }

    public String getIp() {
        return ip;
    }

    @Override
    public String toString() {
        return "GuestbookSecret{" + "secret=" + secret + '}' + "> Guestbook{"
                + "guestName=" + guestName + ", message=" + message + ", ip=" + ip
                + ", creationDate=" + creationDate + '}';
    }

}
