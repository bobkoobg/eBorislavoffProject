package entity;

public class TicketType extends AbstractEntity {

    private String tickettypename;

    public TicketType() {
    }

    public TicketType( int typeTypeId, String tickettypename ) {
        this.id = typeTypeId;
        this.tickettypename = tickettypename;
    }

    @Override
    public String toString() {
        return "id: " + this.id + "> TicketType{" + "tickettypename=" + tickettypename + '}';
    }

}
