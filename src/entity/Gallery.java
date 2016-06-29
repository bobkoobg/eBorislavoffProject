package entity;

import java.util.Date;

public class Gallery extends AbstractEntity {

    private int user_id;
    protected String imagePath;
    protected Date creationDate;

    public Gallery() {
    }

    public Gallery( int galleryId, int user_id, String imagePath, Date creationDate ) {
        this.id = galleryId;
        this.user_id = user_id;
        this.imagePath = imagePath;
        this.creationDate = creationDate;
    }

    public int getUser_id() {
        return user_id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath( String imagePath ) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "id: " + this.id + "> Gallery{" + "user_id=" + user_id + ", imagePath="
                + imagePath + ", creationDate=" + creationDate + '}';
    }

}
