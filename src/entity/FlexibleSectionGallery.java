package entity;

import java.util.Date;

public class FlexibleSectionGallery extends AbstractEntity {

    private int fs_id;
    private int user_id;
    private String imagepath;
    private Date creationdate;

    public FlexibleSectionGallery() {
    }

    public FlexibleSectionGallery( int fsGallery_id, int fs_id, int user_id, String imagepath,
            Date creationdate ) {
        this.id = fsGallery_id;
        this.fs_id = fs_id;
        this.user_id = user_id;
        this.imagepath = imagepath;
        this.creationdate = creationdate;
    }

    @Override
    public String toString() {
        return "id: " + this.id + "> FlexibleSectionGallery{" + "fs_id=" + fs_id
                + ", user_id=" + user_id + ", imagepath=" + imagepath + ", creationdate="
                + creationdate + '}';
    }

}
