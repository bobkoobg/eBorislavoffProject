package entity;

public class ArticleType extends AbstractEntity {

    String articletypename;

    public ArticleType() {
    }

    public ArticleType( int articleTypeId, String articletypename ) {
        this.id = articleTypeId;
        this.articletypename = articletypename;
    }

    @Override
    public String toString() {
        return "id: " + this.id + "> ArticleType{" + "articletypename=" + articletypename + '}';
    }
}
