package entity.web;

import entity.Article;
import java.util.Date;

public class ArticleAuthor extends Article {

    private String author;

    public ArticleAuthor( int articleId, String author, String title, String text, Date creationDate ) {
        this.id = articleId;
        this.author = author;
        this.title = title;
        this.text = text;
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "ArticleAuthor{" + "author=" + author + '}' + "id: " + this.id
                + "> Article{" + ", title=" + this.title + ", text=" + this.text
                + ", creationDate=" + this.creationDate + '}';
    }

}
