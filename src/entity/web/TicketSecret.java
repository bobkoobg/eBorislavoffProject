package entity.web;

import entity.Ticket;

public class TicketSecret extends Ticket {

    private String secret;

    public TicketSecret( String sender_name, String sender_email, String title, String message,
            String secret ) {
        this.sender_name = sender_name;
        this.sender_email = sender_email;
        this.title = title;
        this.message = message;
        this.secret = secret;
    }

    public String getMessage() {
        return message;
    }

    public String getSender_email() {
        return sender_email;
    }

    public String getSender_name() {
        return sender_name;
    }

    public String getTitle() {
        return title;
    }

    public String getSecret() {
        return secret;
    }

    @Override
    public String toString() {
        return "TicketSecret{" + "secret=" + secret + "}" + "> Ticket{" + "title="
                + title + ", message=" + message + ", sender_email=" + sender_email
                + ", sender_name=" + sender_name + '}';
    }
}
