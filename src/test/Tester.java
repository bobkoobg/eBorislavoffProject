package test;

import controller.Controller;
import entity.Article;
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

//        ArrayList<User> usersToInsert = new ArrayList();
//        usersToInsert.add( new User( 0, "B", "B", "B", "B", new Date(), new Date() ) );
//
//        boolean result = controller.insertUsers( usersToInsert );
//        System.out.println( "The result form the insert is : " + result );
//        boolean result = controller.deleteUser( 0 );
//        System.out.println( "The result form the insert is : " + result );
//
//        ArrayList<User> usersToInsert = new ArrayList();
//        usersToInsert.add( new User( 2, "Bobanka", "Hackva", "Vashiqt", "Svqt", new Date(), new Date() ) );
//
//        boolean result = controller.updateUsers( usersToInsert );
//        System.out.println( "The result form the insert is : " + result );
//        List<User> users = controller.getAllUsers();
//
//        for ( int i = 0; i < users.size(); i++ ) {
//            System.out.println( users.get( i ).toString() );
//        }
//***END USER/BEGIN ARTICLE
//        ArrayList<Article> articleToadd = new ArrayList();
//        articleToadd.add( new Article( 0, 1, "Who let the dogs out", 2, "Who who who who", new Date() ) );
//
//        boolean result = controller.insertArticles( articleToadd );
//        System.out.println( "The result form the insert is : " + result );        
//        boolean result = controller.deleteArticle( 2 );
//        System.out.println( "The result form the insert is : " + result );
//        ArrayList<Article> articlesToUpdate = new ArrayList();
//        articlesToUpdate.add( new Article( 1, 1, "Hack it", 3, "til you can", new Date() ) );
//
//        boolean result = controller.updateArticles( articlesToUpdate );
//        System.out.println( "The result form the insert is : " + result );
//        List<Article> articles = controller.getAllArticles();
//
//        for ( int i = 0; i < articles.size(); i++ ) {
//            System.out.println( articles.get( i ).toString() );
//        }
    }

}
