package entity;

import java.util.Date;

public class Article extends AbstractEntity {

    private int userId;
    private String title;
    private int typeId;
    private String text;
    private Date creationDate;

    public Article() {
    }

    public Article( int articleId, int userId, String title, int typeId, String text, Date creationDate ) {
        this.id = articleId;
        this.userId = userId;
        this.title = title;
        this.typeId = typeId;
        this.text = text;
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "id: " + this.id + "> Article{" + "userId=" + userId + ", title=" + title + ", typeId="
                + typeId + ", text=" + text + ", creationDate=" + creationDate + '}';
    }

}
