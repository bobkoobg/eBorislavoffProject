package entity;

import java.util.Date;

public class Article extends AbstractEntity {

    private int userId;
    protected String title;
    private int type_id;
    protected String text;
    protected Date creationDate;

    public Article() {
    }

    public Article( int articleId, int userId, String title, int type_id, String text, Date creationDate ) {
        this.id = articleId;
        this.userId = userId;
        this.title = title;
        this.type_id = type_id;
        this.text = text;
        this.creationDate = creationDate;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public int getType_id() {
        return type_id;
    }

    @Override
    public String toString() {
        return "id: " + this.id + "> Article{" + "userId=" + userId + ", title=" + title + ", type_id="
                + type_id + ", text=" + text + ", creationDate=" + creationDate + '}';
    }

}
