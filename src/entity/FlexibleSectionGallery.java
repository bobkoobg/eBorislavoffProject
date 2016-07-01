package entity;

import java.util.Date;

public class FlexibleSectionGallery extends AbstractEntity {

    protected int fs_id;
    private int user_id;
    protected String imagepath;
    protected Date creationdate;

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

    public Date getCreationdate() {
        return creationdate;
    }

    public int getFs_id() {
        return fs_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath( String imagepath ) {
        this.imagepath = imagepath;
    }

    @Override
    public String toString() {
        return "id: " + this.id + "> FlexibleSectionGallery{" + "fs_id=" + fs_id
                + ", user_id=" + user_id + ", imagepath=" + imagepath + ", creationdate="
                + creationdate + '}';
    }

}
