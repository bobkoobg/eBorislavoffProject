package test;

import controller.Controller;
import entity.Article;
import entity.ArticleType;
import entity.Gallery;
import entity.Guestbook;
import entity.Ticket;
import entity.TicketType;
import entity.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tester {

    public static void main( String[] args ) {
        new Tester().tester();
    }

    private void tester() {

        Controller controller = new Controller();

        //Insert
//        ArrayList<User> usersToInsert = new ArrayList();
//        usersToInsert.add( new User( 0, "B", "B", "B", "B", new Date(), new Date() ) );
//        boolean result = controller.insertAbstract( "users", usersToInsert );
//        System.out.println( "The result form the insert is : " + result );
        //Delete
//        boolean result = controller.deleteAbstract("users", 3 );
//        System.out.println( "The result form the delete is : " + result );
        //Update
//        ArrayList<User> usersToUpdate = new ArrayList();
//        usersToUpdate.add( new User( 4, "Bobanka", "Hackva", "Vashiqt", "Svqt", new Date(), new Date() ) );
//        boolean result = controller.updateAbstract( "users", usersToUpdate );
//        System.out.println( "The result form the insert is : " + result );
        //SELECT ALL
        List<User> users = controller.getAbstract( "users", 0 );

        for ( int i = 0; i < users.size(); i++ ) {
            System.out.println( users.get( i ).toString() );
        }
        List<Article> articles = controller.getAbstract( "articles", 0 );

        for ( int i = 0; i < articles.size(); i++ ) {
            System.out.println( articles.get( i ).toString() );
        }
        List<ArticleType> articletypes = controller.getAbstract( "articletypes", 0 );

        for ( int i = 0; i < articletypes.size(); i++ ) {
            System.out.println( articletypes.get( i ).toString() );
        }
        List<Gallery> gallery = controller.getAbstract( "gallery", 0 );

        for ( int i = 0; i < gallery.size(); i++ ) {
            System.out.println( gallery.get( i ).toString() );
        }
        List<Guestbook> guestbook = controller.getAbstract( "guestbook", 0 );

        for ( int i = 0; i < guestbook.size(); i++ ) {
            System.out.println( guestbook.get( i ).toString() );
        }
        List<Ticket> ticket = controller.getAbstract( "tickets", 0 );

        for ( int i = 0; i < ticket.size(); i++ ) {
            System.out.println( ticket.get( i ).toString() );
        }
        List<TicketType> tickettypes = controller.getAbstract( "tickettypes", 0 );

        for ( int i = 0; i < tickettypes.size(); i++ ) {
            System.out.println( tickettypes.get( i ).toString() );
        }
        System.out.println( "End" );
        List<User> usersSpecific = controller.getAbstract( "users", 2 );
        System.out.println( usersSpecific.get( 0 ).toString() );

        List<Article> articleSpecific = controller.getAbstract( "articles", 2 );
        System.out.println( articleSpecific.get( 0 ).toString() );

        List<ArticleType> articleTypeSpecific = controller.getAbstract( "articletypes", 2 );
        System.out.println( articleTypeSpecific.get( 0 ).toString() );

        List<Gallery> gallerySpecific = controller.getAbstract( "gallery", 2 );
        System.out.println( gallerySpecific.get( 0 ).toString() );

        List<Guestbook> guestbookSpecific = controller.getAbstract( "guestbook", 2 );
        System.out.println( guestbookSpecific.get( 0 ).toString() );

        List<Ticket> ticketSpecific = controller.getAbstract( "tickets", 2 );
        System.out.println( ticketSpecific.get( 0 ).toString() );

        List<TicketType> ticketTypeSpecific = controller.getAbstract( "tickettypes", 2 );
        System.out.println( ticketTypeSpecific.get( 0 ).toString() );

        //Insert
//        ArrayList<Article> articleToadd = new ArrayList();
//        articleToadd.add( new Article( 0, 1, "Who let the dogs out", 2, "Who who who who", new Date() ) );
//        boolean result = controller.insertAbstract( "articles", articleToadd );
//        System.out.println( "The result form the insert is : " + result );
        //Delete
//        boolean result = controller.deleteAbstract( "articles", 3 );
//        System.out.println( "The result form the delete is : " + result );
        //Update
//        ArrayList<Article> articlesToUpdate = new ArrayList();
//        articlesToUpdate.add( new Article( 5, 1, "Hack it", 3, "til you can213123", new Date() ) );
//
//        boolean result = controller.updateAbstract( "articles", articlesToUpdate );
//        System.out.println( "The result form the insert is : " + result );
        //SELECT ALL
//        List<Article> articles = controller.getAbstract( "articles" );
//
//        for ( int i = 0; i < articles.size(); i++ ) {
//            System.out.println( articles.get( i ).toString() );
//        }
//        System.out.println( "End" );
//        List<Article> articleSpecific = controller.getSpecificAbstract( "articles", 5 );
//
//        for ( int i = 0; i < articleSpecific.size(); i++ ) {
//            System.out.println( articleSpecific.get( i ).toString() );
//        }
    }

}
