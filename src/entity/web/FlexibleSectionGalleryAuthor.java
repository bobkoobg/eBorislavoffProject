package entity.web;

import entity.FlexibleSectionGallery;
import java.util.Date;

public class FlexibleSectionGalleryAuthor extends FlexibleSectionGallery {

    private String author;

    public FlexibleSectionGalleryAuthor( int fsGallery_id, int fs_id, String author,
            String imagepath, Date creationdate ) {
        this.id = fsGallery_id;
        this.fs_id = fs_id;
        this.author = author;
        this.imagepath = imagepath;
        this.creationdate = creationdate;
    }

    @Override
    public String toString() {
        return "FlexibleSectionGalleryAuthor{" + "author=" + author + '}' + "id: "
                + this.id + "> FlexibleSectionGallery{" + "fs_id=" + fs_id
                + ", imagepath=" + imagepath + ", creationdate=" + creationdate + '}';
    }
}
