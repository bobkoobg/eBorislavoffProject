package entity;

import java.util.Date;

public class Gallery extends AbstractEntity {

    private int userId;
    private String imagePath;
    private Date creationDate;

    public Gallery() {
    }

    public Gallery( int galleryId, int userId, String imagePath, Date creationDate ) {
        this.id = galleryId;
        this.userId = userId;
        this.imagePath = imagePath;
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "id: " + this.id + ">Gallery{" + "userId=" + userId + ", imagePath="
                + imagePath + ", creationDate=" + creationDate + '}';
    }

}
