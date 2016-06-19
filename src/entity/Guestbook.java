package entity;

import java.util.Date;

public class Guestbook extends AbstractEntity {

    private String guestName;
    private String message;
    private String imagePath;
    private Date creationDate;

    public Guestbook() {
    }

    public Guestbook( int guestbookId, String guestName, String message,
            String imagePath, Date creationDate ) {
        this.id = guestbookId;
        this.guestName = guestName;
        this.message = message;
        this.imagePath = imagePath;
        this.creationDate = creationDate;
    }

    public Guestbook( int guestbookId, String guestName, String message,
            Date creationDate ) {
        this.id = guestbookId;
        this.guestName = guestName;
        this.message = message;
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "id: " + this.id + "> Guestbook{" + "guestName=" + guestName
                + ", message=" + message + ", imagePath=" + imagePath
                + ", creationDate=" + creationDate + '}';
    }

}
