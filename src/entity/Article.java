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
    public void setId( int id ) {
        super.setId( id ); //To change body of generated methods, choose Tools | Templates.
    }

    public void setUserId( int userId ) {
        this.userId = userId;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public void setTypeId( int typeId ) {
        this.typeId = typeId;
    }

    public void setText( String text ) {
        this.text = text;
    }

    public void setCreationDate( Date creationDate ) {
        this.creationDate = creationDate;
    }

    @Override
    public int getId() {
        return super.getId(); //To change body of generated methods, choose Tools | Templates.
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getText() {
        return text;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        return "id: " + this.id + "> Article{" + "userId=" + userId + ", title=" + title + ", typeId="
                + typeId + ", text=" + text + ", creationDate=" + creationDate + '}';
    }

}
