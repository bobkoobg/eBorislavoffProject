package entity.web;

import entity.Gallery;
import java.util.Date;

public class GalleryAuthor extends Gallery {

    private String author;

    public GalleryAuthor( int galleryId, String author, String imagePath, Date creationDate ) {
        this.id = galleryId;
        this.author = author;
        this.imagePath = imagePath;
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "GalleryAuthor{" + "author=" + author + '}' + "id: " + this.id
                + "> Gallery{ imagePath=" + this.imagePath + ", creationDate="
                + this.creationDate + '}';
    }

}
