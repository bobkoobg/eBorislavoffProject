package controller;

import facade.Facade;
import entity.Article;
import entity.EntityClassExplorer;
import entity.User;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import utilities.PerformanceLogger;

public class Controller {

    private String loggerName = "chillMaster";
    private String loggerPath = "/MyLogFile.log";

    private PerformanceLogger pl = null;
    private Logger logger = null;
    private Facade facade = null;

    private EntityClassExplorer entityClassExplorer;

    //private Gson gson = null;
    //private List<UserIdentifiers> userIdentifiers;
    //private SessionIDsGenerator sessionIdsGen = null;
    public Controller() {
        // Exists only to defeat instantiation.

        //Logger functionality
        pl = new PerformanceLogger();
        logger = pl.initLogger( loggerName, loggerPath );

        facade = Facade.getInstance();
        facade.initializeConnection( logger );

        entityClassExplorer = new EntityClassExplorer();

    }

    //TODO : MAKE THOSE 4 TYPES OF METHODS GENERIC
    public List<User> getAllUsers() {
        Class c = User.class;
        List<Field> fields = entityClassExplorer.retrieveFieldsFromEntity( c );

        return facade.getAllUsers( fields, logger );
    }

    public boolean insertUsers( ArrayList<User> users ) {
        Class c = User.class;
        List<Field> fields = entityClassExplorer.retrieveFieldsFromEntity( c );

        return facade.insertUser( users, fields, logger );
    }

    public boolean deleteUser( int userId ) {
        return facade.deleteUser( userId, logger );
    }

    public boolean updateUsers( ArrayList<User> users ) {
        Class c = User.class;
        List<Field> fields = entityClassExplorer.retrieveFieldsFromEntity( c );

        return facade.updateUsers( users, fields, logger );
    }

    public List<Article> getAllArticles() {
        Class c = Article.class;
        List<Field> fields = entityClassExplorer.retrieveFieldsFromEntity( c );

        return facade.getAllArticles( fields, logger );
    }

    public boolean insertArticles( ArrayList<Article> articles ) {
        Class c = Article.class;
        List<Field> fields = entityClassExplorer.retrieveFieldsFromEntity( c );

        return facade.insertArticles( articles, fields, logger );
    }

    public boolean deleteArticle( int articleId ) {

        return facade.deleteArticle( articleId, logger );
    }

    public boolean updateArticles( ArrayList<Article> articles ) {
        Class c = Article.class;
        List<Field> fields = entityClassExplorer.retrieveFieldsFromEntity( c );

        return facade.updateArticle( articles, fields, logger );
    }

}
