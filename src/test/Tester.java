package test;

import controller.Controller;
import entity.Article;
import entity.ArticleType;
import entity.FlexibleSection;
import entity.FlexibleSectionGallery;
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

    private <T> void tester() {

        Controller controller = Controller.getInstance();

        //Insert
//        ArrayList<User> usersToInsert = new ArrayList();
//        usersToInsert.add( new User( 0, "B", "B", "B", "B", new Date(), new Date() ) );
//        boolean result = controller.insertAbstract( "users", usersToInsert );
//        System.out.println( "The result form the insert is : " + result );
//        
//        ArrayList<Article> articleToadd = new ArrayList();
//        articleToadd.add( new Article( 0, 1, "Who let the dogs out", 2, "Who who who who", new Date() ) );
//        boolean result2 = controller.insertAbstract( "articles", articleToadd );
//        System.out.println( "The result form the insert is : " + result2 );
//                
//        ArrayList<ArticleType> insertAT = new ArrayList();
//        insertAT.add( new ArticleType(0, "PRIZOVI ME V tuMNINA") );
//        boolean result3 = controller.insertAbstract( "articletypes", insertAT );
//        System.out.println( "The result form the insert is : " + result3 );
//
//        ArrayList<Gallery> insertG = new ArrayList();
//        insertG.add( new Gallery( 0, 2, "path/boykorocks.jpg", new Date() ) );
//        boolean result4 = controller.insertAbstract( "gallery", insertG );
//        System.out.println( "The result form the insert is : " + result4 );
//
//        ArrayList<Guestbook> insertGB = new ArrayList();
//        insertGB.add( new Guestbook( 0, "Imam snimka guy", "MNOO QKOO", "image23.jpg", new Date() ) );
//        boolean result5 = controller.insertAbstract( "guestbook", insertGB );
//        System.out.println( "The result form the insert is : " + result5 );
//
//        ArrayList<Guestbook> insertGB2 = new ArrayList();
//        insertGB2.add( new Guestbook( 0, "Nqmam snimka", "MNOO QKOO", new Date() ) );
//        boolean result6 = controller.insertAbstract( "guestbook", insertGB );
//        System.out.println( "The result form the insert is : " + result6 );
//
//        ArrayList<Ticket> insertT = new ArrayList();
//        insertT.add( new Ticket( 0, "Trudno", 1, "namiram", 2, new Date() ) );
//        boolean result7 = controller.insertAbstract( "tickets", insertT );
//        System.out.println( "The result form the insert is : " + result7 );
        //******
//        ArrayList<Ticket> insertT = new ArrayList();
//        insertT.add( new Ticket( 0, "Nqmam ", 2, "adminche", new Date() ) );
//        boolean result = controller.insertAbstract( "tickets", insertT );
//        System.out.println( "The result form the insert is : " + result );
        //******
//        ArrayList<TicketType> insertTT = new ArrayList();
//        insertTT.add( new TicketType( 0, "mnoo gotentype" ) );
//        boolean result8 = controller.insertAbstract( "tickettypes", insertTT );
//        System.out.println( "The result form the insert is : " + result8 );
        //Delete
//        boolean result9 = controller.deleteAbstract("users", 3 );
//        System.out.println( "The result form the delete is : " + result9 );
//        boolean result10 = controller.deleteAbstract( "articles", 3 );
//        System.out.println( "The result form the delete is : " + result10 );
//        boolean result11 = controller.deleteAbstract( "articletypes", 3 );
//        System.out.println( "The result form the delete is : " + result11 );
//        boolean result12 = controller.deleteAbstract( "gallery", 3 );
//        System.out.println( "The result form the delete is : " + result12 );
//        boolean result13 = controller.deleteAbstract( "guestbook", 4 );
//        System.out.println( "The result form the delete is : " + result13 );
//        boolean result14 = controller.deleteAbstract( "tickets", 3 );
//        System.out.println( "The result form the delete is : " + result14 );
//        boolean result15 = controller.deleteAbstract( "tickettypes", 3 );
//        System.out.println( "The result form the delete is : " + result15 );
        //Update
//        ArrayList<User> usersToUpdate = new ArrayList();
//        usersToUpdate.add( new User( 1, "Bobanka", "Hackva", "Vashiqt", "Svqt", new Date(), new Date() ) );
//        boolean result16 = controller.updateAbstract( "users", usersToUpdate );
//        System.out.println( "The result form the insert is : " + result16 );
//
//        ArrayList<Article> articlesToUpdate = new ArrayList();
//        articlesToUpdate.add( new Article( 1, 1, "Hack it", 1, "til you can213123", new Date() ) );
//        boolean result17 = controller.updateAbstract( "articles", articlesToUpdate );
//        System.out.println( "The result form the insert is : " + result17 );
//
//        ArrayList<ArticleType> atUpdate = new ArrayList();
//        atUpdate.add( new ArticleType( 1, "HACKEDDDD" ) );
//        boolean result17 = controller.updateAbstract( "articletypes", atUpdate );
//        System.out.println( "The result form the insert is : " + result17 );
//
//        ArrayList<Gallery> gUpdate = new ArrayList();
//        gUpdate.add( new Gallery( 1, 1, "NO IMAGE", new Date() ) );
//        boolean result17 = controller.updateAbstract( "gallery", gUpdate );
//        System.out.println( "The result form the insert is : " + result17 );
//
//        ArrayList<Guestbook> gbUpdate = new ArrayList();
//        gbUpdate.add( new Guestbook( 1, "guestName?", "No message", new Date() ) );
//        boolean result17 = controller.updateAbstract( "guestbook", gbUpdate );
//        System.out.println( "The result form the insert is : " + result17 );
//
//        ArrayList<Ticket> tUpdate = new ArrayList();
//        tUpdate.add( new Ticket( 1, "hacked ticket", 2, "no message", 1, new Date() ) );
//        boolean result17 = controller.updateAbstract( "tickets", tUpdate );
//        System.out.println( "The result form the insert is : " + result17 );
//
//        ArrayList<TicketType> ttUpdate = new ArrayList();
//        ttUpdate.add( new TicketType( 1, "hackedTYPEZZZZ" ) );
//        boolean result17 = controller.updateAbstract( "tickettypes", ttUpdate );
//        System.out.println( "The result form the insert is : " + result17 );
//        //SELECT ALL
//        List<User> users = controller.getAbstract( "users", 0, "id" );
//
//        for ( int i = 0; i < users.size(); i++ ) {
//            System.out.println( users.get( i ).toString() );
//        }
//        List<Article> articles = controller.getAbstract( "articles", 0 );
//
//        for ( int i = 0; i < articles.size(); i++ ) {
//            System.out.println( articles.get( i ).toString() );
//        }
//        List<ArticleType> articletypes = controller.getAbstract( "articletypes", 0 );
//
//        for ( int i = 0; i < articletypes.size(); i++ ) {
//            System.out.println( articletypes.get( i ).toString() );
//        }
//        List<Gallery> gallery = controller.getAbstract( "gallery", 0 );
//
//        for ( int i = 0; i < gallery.size(); i++ ) {
//            System.out.println( gallery.get( i ).toString() );
//        }
//        List<Guestbook> guestbook = controller.getAbstract( "guestbook", 0 );
//
//        for ( int i = 0; i < guestbook.size(); i++ ) {
//            System.out.println( guestbook.get( i ).toString() );
//        }
//        List<Ticket> ticket = controller.getAbstract( "tickets", 0 );
//
//        for ( int i = 0; i < ticket.size(); i++ ) {
//            System.out.println( ticket.get( i ).toString() );
//        }
//        List<TicketType> tickettypes = controller.getAbstract( "tickettypes", 0 );
//
//        for ( int i = 0; i < tickettypes.size(); i++ ) {
//            System.out.println( tickettypes.get( i ).toString() );
//        }
//        System.out.println( "End" );
        //SELECT SPECIFIC
//        String obj2 = "emko";
//        List<User> usersSpecific = controller.getAbstract( "users", obj2, "username" );
//        System.out.println( usersSpecific.get( 0 ).toString() );
//
//        List<Article> articleSpecific = controller.getAbstract( "articles", 2 );
//        System.out.println( articleSpecific.get( 0 ).toString() );
//
//        List<ArticleType> articleTypeSpecific = controller.getAbstract( "articletypes", 2 );
//        System.out.println( articleTypeSpecific.get( 0 ).toString() );
//
//        List<Gallery> gallerySpecific = controller.getAbstract( "gallery", 2 );
//        System.out.println( gallerySpecific.get( 0 ).toString() );
//
//        List<Guestbook> guestbookSpecific = controller.getAbstract( "guestbook", 2 );
//        System.out.println( guestbookSpecific.get( 0 ).toString() );
//
//        List<Ticket> ticketSpecific = controller.getAbstract( "tickets", 2 );
//        System.out.println( ticketSpecific.get( 0 ).toString() );
//
//        List<TicketType> ticketTypeSpecific = controller.getAbstract( "tickettypes", 2 );
//        System.out.println( ticketTypeSpecific.get( 0 ).toString() );
//        List<ArticleType> articletypes = controller.getAbstract( "articletypes", "News", "articletypename" );
//
//        for ( int i = 0; i < articletypes.size(); i++ ) {
//            System.out.println( articletypes.get( i ).toString() );
//        }
//
//        List<Article> articles = controller.getAbstract( "articles", articletypes.get( 0 ).getId(), "type_id" );
//
//        for ( int i = 0; i < articles.size(); i++ ) {
//            System.out.println( articles.get( i ).toString() );
//        }
//        List<TicketType> specificTicketType = controller.getAbstract( "tickettypes", "Contacts", "tickettypename" );
//        
//        for ( int i = 0; i < specificTicketType.size(); i++ ) {
//            System.out.println( specificTicketType.get( i ).toString() );
//        }
        List<FlexibleSection> flexibleSections = controller.getAbstract( "flexiblesections", 0, "" );

        for ( int i = 0; i < flexibleSections.size(); i++ ) {
            System.out.println( flexibleSections.get( i ).toString() );
        }

        List<FlexibleSectionGallery> flexibleSectionsGallery = controller.getAbstract( "flexiblesectionsgallery", 0, "" );

        for ( int i = 0; i < flexibleSectionsGallery.size(); i++ ) {
            System.out.println( flexibleSectionsGallery.get( i ).toString() );
        }

    }

}
