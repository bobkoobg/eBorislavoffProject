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

    public <T> List<T> getAllAbstract( String type ) {

        String tN = "";
        Class c = null;
        List<Field> f = null;

        if ( "users".equals( type ) ) {
            tN = getTableName( type );
            c = User.class;
            f = entityClassExplorer.retrieveFieldsFromEntity( c );
        } else if ( "articles".equals( type ) ) {
            tN = getTableName( type );
            c = Article.class;
            f = entityClassExplorer.retrieveFieldsFromEntity( c );
        }

        if ( !tN.isEmpty() && c != null && f != null ) {
            return facade.getAllAbstract( tN, c, f, logger );
        }
        return null;
    }

    public <T> boolean insertAbstract( String type, ArrayList<T> toInsert ) {

        String tN = "";
        Class c = null;
        List<Field> f = null;

        if ( "users".equals( type ) ) {
            tN = getTableName( type );
            c = User.class;
            f = entityClassExplorer.retrieveFieldsFromEntity( c );
        } else if ( "articles".equals( type ) ) {
            tN = getTableName( type );
            c = Article.class;
            f = entityClassExplorer.retrieveFieldsFromEntity( c );
        }

        for ( int i = 0; i < toInsert.size(); i++ ) {
            if ( toInsert.get( i ) instanceof User ) {
                (( User ) toInsert.get( i )).setId(
                        facade.getNextSequenceIdAbstract( getSequenceName( type ), logger ) );
            } else if ( toInsert.get( i ) instanceof Article ) {
                (( Article ) toInsert.get( i )).setId(
                        facade.getNextSequenceIdAbstract( getSequenceName( type ), logger ) );
            }
        }

        if ( !tN.isEmpty() && c != null && f != null ) {
            return facade.insertAbstract( tN, c, toInsert, f, logger );
        }
        return false;
    }

    public boolean deleteAbstract( String type, int entryId ) {
        return facade.deleteAbstract( getTableName( type ), entryId, logger );
    }

    public <T> boolean updateAbstract( String type, ArrayList<T> toUpdate ) {

        String tN = "";
        Class c = null;
        List<Field> f = null;

        if ( "users".equals( type ) ) {
            tN = getTableName( type );
            c = User.class;
            f = entityClassExplorer.retrieveFieldsFromEntity( c );
        } else if ( "articles".equals( type ) ) {
            tN = getTableName( type );
            c = Article.class;
            f = entityClassExplorer.retrieveFieldsFromEntity( c );
        }

        if ( !tN.isEmpty() && c != null && f != null ) {
            return facade.updateAbstract( tN, toUpdate, f, logger );
        }
        return false;
    }

    private String getTableName( String type ) {
        String tableName = "";
        switch ( type ) {
            case "users":
                tableName = "EMKO_USERS_TBL";
                break;
            case "articles":
                tableName = "EMKO_ARTICLES_TBL";
                break;
            default:
                tableName = "EMKO_ARTICLES_TBL";
                break;
        }
        return tableName;
    }

    private String getSequenceName( String type ) {
        String tableName = "";
        switch ( type ) {
            case "users":
                tableName = "EMKO_USERS_ID_SEQ";
                break;
            case "articles":
                tableName = "EMKO_ARTICLES_ID_SEQ";
                break;
            default:
                tableName = "EMKO_ARTICLES_ID_SEQ";
                break;
        }
        return tableName;
    }

}
