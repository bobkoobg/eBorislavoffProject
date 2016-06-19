package entity;

import java.util.Date;

public class Ticket extends AbstractEntity {

    private String title;
    private int ticketTypeId;
    private String message;
    private int handlerId;
    private Date creationDate;

    public Ticket( int ticketId, String title, int ticketTypeId, String message,
            int handlerId, Date creationDate ) {
        this.id = ticketId;
        this.title = title;
        this.ticketTypeId = ticketTypeId;
        this.message = message;
        this.handlerId = handlerId;
        this.creationDate = creationDate;
    }

    public void setHandlerId( int handlerId ) {
        this.handlerId = handlerId;
    }

    @Override
    public String toString() {
        return "id: " + this.id + ">Ticket{" + "title=" + title + ", ticketTypeId="
                + ticketTypeId + ", message=" + message + ", handlerId=" + handlerId
                + ", creationDate=" + creationDate + '}';
    }

}
