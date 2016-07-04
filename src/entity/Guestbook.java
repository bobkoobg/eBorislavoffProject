package entity;

import java.util.Date;

public class Guestbook extends AbstractEntity {

    protected String guestName;
    protected String message;
    private String imagePath;
    protected String ip;
    protected Date creationDate;

    public Guestbook() {
    }

    public Guestbook( int guestbookId, String guestName, String message,
            String imagePath, String ip, Date creationDate ) {
        this.id = guestbookId;
        this.guestName = guestName;
        this.message = message;
        this.imagePath = imagePath;
        this.ip = ip;
        this.creationDate = creationDate;
    }

    public Guestbook( int guestbookId, String guestName, String message, String ip,
            Date creationDate ) {
        this.id = guestbookId;
        this.guestName = guestName;
        this.message = message;
        this.ip = ip;
        this.creationDate = creationDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath( String imagePath ) {
        this.imagePath = imagePath;
    }

    public void setCreationDate( Date creationDate ) {
        this.creationDate = creationDate;
    }

    public void setIp( String ip ) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "id: " + this.id + "> Guestbook{" + "guestName=" + guestName
                + ", message=" + message + ", imagePath=" + imagePath
                + ", ip=" + ip + ", creationDate=" + creationDate + '}';
    }

}
