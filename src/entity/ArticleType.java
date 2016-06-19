package entity;

public class ArticleType extends AbstractEntity {

    String name;

    public ArticleType() {
    }

    public ArticleType( int articleTypeId, String name ) {
        this.id = articleTypeId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "id: " + this.id + "> ArticleType{" + "name=" + name + '}';
    }
}
