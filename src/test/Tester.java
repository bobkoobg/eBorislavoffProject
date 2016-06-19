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

        //Insert
//        ArrayList<User> usersToInsert = new ArrayList();
//        usersToInsert.add( new User( 0, "B", "B", "B", "B", new Date(), new Date() ) );
//        boolean result = controller.insertAbstract( "users", usersToInsert );
//        System.out.println( "The result form the insert is : " + result );
        //Delete
//        boolean result = controller.deleteAbstract("users", 2 );
//        System.out.println( "The result form the delete is : " + result );
        //Update
//        ArrayList<User> usersToUpdate = new ArrayList();
//        usersToUpdate.add( new User( 3, "Bobanka", "Hackva", "Vashiqt", "Svqt", new Date(), new Date() ) );
//        boolean result = controller.updateAbstract( "users", usersToUpdate );
//        System.out.println( "The result form the insert is : " + result );
        //SELECT ALL
        List<User> users = controller.getAllAbstract( "users" );

        for ( int i = 0; i < users.size(); i++ ) {
            System.out.println( users.get( i ).toString() );
        }

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
        List<Article> articles = controller.getAllAbstract( "articles" );

        for ( int i = 0; i < articles.size(); i++ ) {
            System.out.println( articles.get( i ).toString() );
        }
    }

}
