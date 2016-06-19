package controller;

import facade.Facade;
import entity.Article;
import entity.ArticleType;
import entity.EntityClassExplorer;
import entity.Gallery;
import entity.Guestbook;
import entity.Ticket;
import entity.TicketType;
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

    /*
     * This abstract method provides SELECT database functionality on any table
     * from the database.
     * Parameters :
     * Type - Identifiying string for specific table
     * EntryId - Used for SELECTing specific entry based on Id, 0 (or NULL) if ALL
     */
    public <T> List<T> getAbstract( String type, int entryId ) {

        String tN = "";
        Class c = null;
        List<Field> f = null;

        if ( null != type ) {
            switch ( type ) {
                case "users":
                    tN = getTableName( type );
                    c = User.class;
                    break;
                case "articles":
                    tN = getTableName( type );
                    c = Article.class;
                    break;
                case "articletypes":
                    tN = getTableName( type );
                    c = ArticleType.class;
                    break;
                case "gallery":
                    tN = getTableName( type );
                    c = Gallery.class;
                    break;
                case "guestbook":
                    tN = getTableName( type );
                    c = Guestbook.class;
                    break;
                case "tickets":
                    tN = getTableName( type );
                    c = Ticket.class;
                    break;
                case "tickettypes":
                    tN = getTableName( type );
                    c = TicketType.class;
                    break;
            }
        }

        if ( c != null ) {
            f = entityClassExplorer.retrieveFieldsFromEntity( c );
        }

        if ( !tN.isEmpty() && c != null && f != null ) {
            if ( entryId == 0 ) {
                return facade.getAllAbstract( tN, c, f, logger );
            } else {
                return facade.getSpecificAbstract( tN, c, f, entryId, logger );
            }

        }
        return null;
    }

    public <T> boolean insertAbstract( String type, ArrayList<T> toInsert ) {

        String tN = "";
        Class c = null;
        List<Field> f = null;

        if ( null != type ) {
            switch ( type ) {
                case "users":
                    tN = getTableName( type );
                    c = User.class;
                    break;
                case "articles":
                    tN = getTableName( type );
                    c = Article.class;
                    break;
                case "articletypes":
                    tN = getTableName( type );
                    c = ArticleType.class;
                    break;
                case "gallery":
                    tN = getTableName( type );
                    c = Gallery.class;
                    break;
                case "guestbook":
                    tN = getTableName( type );
                    c = Guestbook.class;
                    break;
                case "tickets":
                    tN = getTableName( type );
                    c = Ticket.class;
                    break;
                case "tickettypes":
                    tN = getTableName( type );
                    c = TicketType.class;
                    break;
            }
        }

        if ( c != null ) {
            f = entityClassExplorer.retrieveFieldsFromEntity( c );
        }

        //insElem - Element to be inserted into the database (X.X)
        for ( T insElem : toInsert ) {
            if ( insElem instanceof User ) {
                (( User ) insElem).setId( facade
                        .getNextSequenceIdAbstract( getSequenceName( type ), logger ) );
            } else if ( insElem instanceof Article ) {
                (( Article ) insElem).setId( facade
                        .getNextSequenceIdAbstract( getSequenceName( type ), logger ) );
            } else if ( insElem instanceof ArticleType ) {
                (( ArticleType ) insElem).setId( facade
                        .getNextSequenceIdAbstract( getSequenceName( type ), logger ) );
            } else if ( insElem instanceof Gallery ) {
                (( Gallery ) insElem).setId( facade
                        .getNextSequenceIdAbstract( getSequenceName( type ), logger ) );
            } else if ( insElem instanceof Guestbook ) {
                (( Guestbook ) insElem).setId( facade
                        .getNextSequenceIdAbstract( getSequenceName( type ), logger ) );
            } else if ( insElem instanceof Ticket ) {
                (( Ticket ) insElem).setId( facade
                        .getNextSequenceIdAbstract( getSequenceName( type ), logger ) );
            } else if ( insElem instanceof TicketType ) {
                (( TicketType ) insElem).setId( facade
                        .getNextSequenceIdAbstract( getSequenceName( type ), logger ) );
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
            case "articletypes":
                tableName = "EMKO_ARTICLETYPES_TBL";
                break;
            case "gallery":
                tableName = "EMKO_GALLERY_TBL";
                break;
            case "guestbook":
                tableName = "EMKO_GUESTBOOK_TBL";
                break;
            case "tickets":
                tableName = "EMKO_TICKETS_TBL";
                break;
            case "tickettypes":
                tableName = "EMKO_TICKETTYPES_TBL";
                break;
        }
        return tableName;
    }

    private String getSequenceName( String type ) {
        String sequenceName = "";
        switch ( type ) {
            case "users":
                sequenceName = "EMKO_USERS_ID_SEQ";
                break;
            case "articles":
                sequenceName = "EMKO_ARTICLES_ID_SEQ";
                break;
            case "articletypes":
                sequenceName = "EMKO_ARTICLETYPES_ID_SEQ";
                break;
            case "gallery":
                sequenceName = "EMKO_GALLERY_ID_SEQ";
                break;
            case "guestbook":
                sequenceName = "EMKO_GUESTBOOK_ID_SEQ";
                break;
            case "tickets":
                sequenceName = "EMKO_TICKETS_ID_SEQ";
                break;
            case "tickettypes":
                sequenceName = "EMKO_TICKETTYPES_ID_SEQ";
                break;
        }
        return sequenceName;
    }

}
