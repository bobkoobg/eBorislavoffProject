package entity.web;

import entity.FlexibleSection;
import java.util.Date;

public class FlexibleSectionAuthor extends FlexibleSection {

    private String author;

    public FlexibleSectionAuthor( int flexibleSection_id, String fs_purpose, String title,
            String message, String author, Date creationdate ) {
        this.id = flexibleSection_id;
        this.fs_purpose = fs_purpose;
        this.title = title;
        this.message = message;
        this.author = author;
        this.creationdate = creationdate;
    }

    @Override
    public String toString() {
        return "FlexibleSectionAuthor{" + "author=" + author + '}' + "id: "
                + this.id + "> FlexibleSection{" + "fs_purpose=" + fs_purpose
                + ", title=" + title + ", message=" + message + ", creationdate="
                + creationdate + '}';
    }
}
