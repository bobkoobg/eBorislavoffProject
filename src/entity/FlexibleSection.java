package entity;

import java.util.Date;

public class FlexibleSection extends AbstractEntity {

    protected String fs_purpose;
    protected String title;
    protected String message;
    private int user_id;
    protected Date creationdate;

    public FlexibleSection() {
    }

    public FlexibleSection( int flexibleSection_id, String fs_purpose, String title,
            String message, int user_id, Date creationdate ) {
        this.id = flexibleSection_id;
        this.fs_purpose = fs_purpose;
        this.title = title;
        this.message = message;
        this.user_id = user_id;
        this.creationdate = creationdate;
    }

    public String getFs_purpose() {
        return fs_purpose;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public int getUser_id() {
        return user_id;
    }

    @Override
    public String toString() {
        return "id: " + this.id + "> FlexibleSection{" + "fs_purpose=" + fs_purpose
                + ", title=" + title + ", message=" + message + ", user_id=" + user_id
                + ", creationdate=" + creationdate + '}';
    }

}
