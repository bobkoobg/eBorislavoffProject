package controller;

import com.google.gson.Gson;
import facade.Facade;
import entity.Article;
import entity.ArticleType;
import entity.EntityClassExplorer;
import entity.Gallery;
import entity.Guestbook;
import entity.Ticket;
import entity.TicketType;
import entity.User;
import entity.web.SessionIdentifier;
import entity.web.UserWebSession;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;
import utilities.PerformanceLogger;
import utilities.SessionIDsGenerator;

public class Controller {

    private static Controller instance = null;

    private String loggerName = "chillMaster";
    private String loggerPath = "/MyLogFile.log";

    private PerformanceLogger pl = null;
    private Logger logger = null;
    private Facade facade = null;

    private EntityClassExplorer entityClassExplorer;

    private Gson gson = null;
    private List<SessionIdentifier> userIdentifiers;
    private SessionIDsGenerator sessionIdsGen = null;

    private Controller() {
        // Exists only to defeat instantiation.

        //Logger functionality
        pl = new PerformanceLogger();
        logger = pl.initLogger( loggerName, loggerPath );

        facade = Facade.getInstance();
        facade.initializeConnection( logger );

        entityClassExplorer = new EntityClassExplorer();

        userIdentifiers = new ArrayList();
        sessionIdsGen = new SessionIDsGenerator();
        gson = new Gson();

    }

    public static Controller getInstance() {
        if ( instance == null ) {
            instance = new Controller();
        }
        return instance;
    }

    public Logger getLogger() {
        return logger;
    }

    /*
     * User authentication start
     */
    public boolean registerUser( String clientReqIP, String jQueryObject ) {
        User jsonObject = gson.fromJson( jQueryObject, User.class );

        long MAX_DURATION = MILLISECONDS.convert( 1, MINUTES );
        Date now = new Date();

        String clientSHA256PlusIdsPW = jsonObject.getPassword();

        if ( clientSHA256PlusIdsPW.length() != (64 + 2) ) {
            return false;
        }

        //decompose password
        for ( int i = 0; i < userIdentifiers.size(); i++ ) {
            if ( userIdentifiers.get( i ).getClientReqIP().equals( clientReqIP )
                    && "register".equals( userIdentifiers.get( i ).getType() ) ) {
                clientSHA256PlusIdsPW = clientSHA256PlusIdsPW.substring( 1, clientSHA256PlusIdsPW.length() - 1 );
                jsonObject.setPassword( clientSHA256PlusIdsPW );
            }
            if ( now.getTime() - userIdentifiers.get( i ).getCurDate().getTime() >= MAX_DURATION ) {
                userIdentifiers.remove( i );
            }
        }

        ArrayList<User> insertT = new ArrayList();
        insertT.add( new User( 0, jsonObject.getUsername(),
                               BCrypt.hashpw( jsonObject.getPassword(), BCrypt.gensalt() ),
                               jsonObject.getEmail(), jsonObject.getUserAlias(),
                               new Date(), new Date() ) );

        return insertAbstract( "users", insertT );
    }

    public boolean createUserIdentifierObj( String clientReqIP, int curServerID, String type ) {
        boolean found = false;
        long MAX_DURATION = MILLISECONDS.convert( 1, MINUTES );
        Date now = new Date();

        for ( int i = 0; i < userIdentifiers.size(); i++ ) {
            if ( userIdentifiers.get( i ).getClientReqIP().equals( clientReqIP )
                    && userIdentifiers.get( i ).getType().equals( type ) ) {

                userIdentifiers.remove( i );
                userIdentifiers.add( new SessionIdentifier( clientReqIP, curServerID, new Date(), type ) );
                found = true;
            }
            if ( now.getTime() - userIdentifiers.get( i ).getCurDate().getTime() >= MAX_DURATION ) {
                userIdentifiers.remove( i );
            }
        }
        if ( !found ) {
            userIdentifiers.add( new SessionIdentifier( clientReqIP, curServerID, new Date(), type ) );
        }
        return true;
    }

    public boolean addClientId( String clientReqIP, int curClientID ) {
        long MAX_DURATION = MILLISECONDS.convert( 1, MINUTES );
        Date now = new Date();
        boolean isFound = false;
        for ( int i = 0; i < userIdentifiers.size(); i++ ) {
            if ( userIdentifiers.get( i ).getClientReqIP().equals( clientReqIP ) ) {
                userIdentifiers.get( i ).setCurClientId( curClientID );
                isFound = true;
            }
            if ( now.getTime() - userIdentifiers.get( i ).getCurDate().getTime() >= MAX_DURATION ) {
                userIdentifiers.remove( i );
            }
        }
        if ( isFound ) {
            return true;
        }
        return false;
    }

    public <T> User loginUser( String clientReqIP, String jQueryObject ) {
        User jsonObject = gson.fromJson( jQueryObject, User.class );

        long MAX_DURATION = MILLISECONDS.convert( 1, MINUTES );
        Date now = new Date();

        String clientSHA256PlusIdsPW = jsonObject.getPassword();

        if ( clientSHA256PlusIdsPW.length() != (64 + 2) ) {
            return null;
        }

        //decompose password
        for ( int i = 0; i < userIdentifiers.size(); i++ ) {
            if ( userIdentifiers.get( i ).getClientReqIP().equals( clientReqIP )
                    && "login".equals( userIdentifiers.get( i ).getType() ) ) {
                clientSHA256PlusIdsPW = clientSHA256PlusIdsPW.substring( 1, clientSHA256PlusIdsPW.length() - 1 );
                jsonObject.setPassword( clientSHA256PlusIdsPW );
            }
            if ( now.getTime() - userIdentifiers.get( i ).getCurDate().getTime() >= MAX_DURATION ) {
                userIdentifiers.remove( i );
            }
        }

        List<User> currUser = getAbstract( "users", jsonObject.getUsername(), "username" );

        boolean isCorrectPassword = false;
        String hashedPassword = currUser.get( 0 ).getPassword();
        String password = jsonObject.getPassword();
        if ( hashedPassword != null ) {
            isCorrectPassword = BCrypt.checkpw( password, hashedPassword );
        }

        if ( !isCorrectPassword ) {
            return null;
        }

        UserWebSession uws = null;
        if ( currUser.get( 0 ) != null ) {
            uws = new UserWebSession( currUser.get( 0 ).getUsername(),
                                      sessionIdsGen.registerSession( clientReqIP, currUser.get( 0 ).getUsername() ) );
        } else {
            return null;
        }

        return uws;
    }

    public boolean authenticateSession( String address, String sessionId ) {
        sessionId = sessionId.replaceAll( "^\"|\"$", "" );
        return sessionIdsGen.checkSession( sessionId, address );
    }

    /*
     * User authentication end
     */

    /*
     * This abstract method provides SELECT database functionality on any table
     * from the database.
     * Parameters :
     * Type - Identifiying string for specific table
     * EntryId - Used for SELECTing specific entry based on Id, 0 (or NULL) if ALL
     */
    public <T> List<T> getAbstract( String type, Object comparisonValue, String filterBy ) {

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
            boolean isInteger = comparisonValue instanceof Integer ? true : false;

            if ( isInteger && ( Integer ) comparisonValue == 0 ) {

                return facade.getAllAbstract( tN, c, f, logger );
            } else {

                String actualFilter = "id";
                for ( int i = 0; i < f.size(); i++ ) {
                    if ( f.get( i ).getName().equals( filterBy ) ) {
                        actualFilter = filterBy;
                        break;
                    }
                }
                return facade.getSpecificAbstract( tN, c, f, comparisonValue, actualFilter, logger );
            }

        }
        return null;
    }


    /*
     * This abstract method provides INSERT database functionality on any table
     * from the database.
     * Parameters :
     * Type - Identifiying string for specific table
     * ArrayList<T> - Which should include a list with the elements which should
     * be inserted (NB: They should be from the same type)
     */
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

    /*
     * This abstract method provides DELETE database functionality on any table
     * from the database.
     * Parameters :
     * Type - Identifiying string for specific table
     * EntryID - Refers to the id of the element which should be deleted
     */
    public boolean deleteAbstract( String type, int entryId, String filterBy ) {
        return facade.deleteAbstract( getTableName( type ), entryId, filterBy, logger );
    }

    /*
     * This abstract method provides UPDATE database functionality on any table
     * from the database.
     * Parameters :
     * Type - Identifiying string for specific table
     * ArrayList<T> - Which should include a list with the elements which should
     * be updated (NB: They should be from the same type)
     */
    public <T> boolean updateAbstract( String type, ArrayList<T> toUpdate ) {

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
            return facade.updateAbstract( tN, toUpdate, f, logger );
        }
        return false;
    }

    /*
     * This method provides EXACT names of database tables based on a simplified
     * string.
     * Parameters :
     * Type - Identifiying string for specific table
     */
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

    /*
     * This method provides EXACT names of database sequences based on a simplified
     * string.
     * Parameters :
     * Type - Identifiying string for specific sequence
     */
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
