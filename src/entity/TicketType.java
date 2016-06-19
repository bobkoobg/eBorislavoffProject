package entity;

public class TicketType extends AbstractEntity {

    private String name;

    public TicketType() {
    }

    public TicketType( int typeTypeId, String name ) {
        this.id = typeTypeId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "id: " + this.id + ">TicketType{" + "name=" + name + '}';
    }

}
