package entity;

import java.util.Date;

public class Ticket extends AbstractEntity {

    protected String title;
    private int ticketTypeId;
    protected String message;
    private int handlerId;
    private String status;
    protected String sender_email;
    protected String sender_name;
    private Date creationDate;

    public Ticket() {
    }

    public Ticket( int ticketId, String title, int ticketTypeId, String message,
            int handlerId, String status, String sender_email, String sender_name,
            Date creationDate ) {
        this.id = ticketId;
        this.title = title;
        this.ticketTypeId = ticketTypeId;
        this.message = message;
        this.handlerId = handlerId;
        this.status = status;
        this.sender_email = sender_email;
        this.sender_name = sender_name;
        this.creationDate = creationDate;
    }

    public Ticket( int ticketId, String title, int ticketTypeId, String message,
            String status, String sender_email, String sender_name, Date creationDate ) {
        this.id = ticketId;
        this.title = title;
        this.ticketTypeId = ticketTypeId;
        this.message = message;
        this.status = status;
        this.sender_email = sender_email;
        this.sender_name = sender_name;
        this.creationDate = creationDate;
    }

    public void setHandlerId( int handlerId ) {
        this.handlerId = handlerId;
    }

    @Override
    public String toString() {
        return "id: " + this.id + "> Ticket{" + "title=" + title + ", ticketTypeId="
                + ticketTypeId + ", message=" + message + ", handlerId=" + handlerId
                + ", creationDate=" + creationDate + ", sender_email=" + sender_email
                + ", sender_name=" + sender_name + '}';
    }

}
