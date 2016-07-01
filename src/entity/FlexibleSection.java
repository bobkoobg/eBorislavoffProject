package entity;

import java.util.Date;

public class FlexibleSection extends AbstractEntity {

    private String fs_purpose;
    private String title;
    private String message;
    private int user_id;
    private Date creationdate;

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

    @Override
    public String toString() {
        return "id: " + this.id + "> FlexibleSection{" + "fs_purpose=" + fs_purpose
                + ", title=" + title + ", message=" + message + ", user_id=" + user_id
                + ", creationdate=" + creationdate + '}';
    }

}
