package facade;

import dataSource.AbstractMapper;
import entity.Article;
import entity.Article;
import entity.User;
import entity.User;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import utilities.DatabaseConnector;
import utilities.StatementCreator;

public class Facade {

    private static Facade instance = null;
    private Connection connection;
    private DatabaseConnector databaseConnector;

    //Database authentication
    private static String[] dbHosts = { "jdbc:oracle:thin:@127.0.0.1:1521:XE",
        "jdbc:oracle:thin:@datdb.cphbusiness.dk:1521:dat" };
    private static String[] dbUsers = { "bobkoo", "cphbs96" };
    private static String[] dbPasses = { "qwerty12345", "cphbs96" };

    private StatementCreator statementCreator;

    private AbstractMapper abstractMapper;

    private Facade() {
        // Exists only to defeat instantiation.
        databaseConnector = new DatabaseConnector(
                dbHosts[ 0 ], dbUsers[ 0 ], dbPasses[ 0 ], null );

        statementCreator = new StatementCreator();

        abstractMapper = new AbstractMapper();
    }

    public static Facade getInstance() {
        if ( instance == null ) {
            instance = new Facade();
        }
        return instance;
    }

    public Boolean initializeConnection( Logger logger ) {
        if ( connection != null ) {
            System.out.println( "Connection already existing" );
            logger.info( "Connection with database is already existing!" );
            return true;
        } else {
            connection = databaseConnector.getConnection( logger );

            try {
                connection.setAutoCommit( true );
                // termination by the garbage collector
            } catch ( SQLException ex ) {
                logger.severe( "SQL Exception while trying to connect to db " + ex );
                return false;
            }
            logger.info( "Connection with database initialized" );
        }
        return true;
    }

    //TODO : MAKE THOSE 4 TYPES OF METHODS GENERIC
    //TODO : Error handling
    //***TO BE ADDED - Check if fields in Classes types == DB Types(down here)
    //ArrayList<String> listOfSQLFieldTypes = getSQTableInformation("data_type", tableName,
    //       builder, connect);
    //System.out.println("listOfSQLFieldTypes : " + listOfSQLFieldTypes.toString());
    //***>>>
    public List<User> getAllUsers( List<Field> fields, Logger logger ) {

        String tableName = "EMKO_USERS_TBL";

        PreparedStatement statement = statementCreator.generateSQLString( "SELECT *", tableName, connection, logger );

        return abstractMapper.overpoweredAbstractMethod( statement, User.class, fields, connection, logger );
    }

    public boolean insertUser( ArrayList<User> users, List<Field> fields, Logger logger ) {

        String tableName = "EMKO_USERS_TBL";

        for ( int i = 0; i < users.size(); i++ ) {
            users.get( i ).setId( abstractMapper.getNextSequenceID( "EMKO_USERS_ID_SEQ", connection, logger ) );
        }

        PreparedStatement statement = statementCreator.generateSQLStringInsert( "INSERT", tableName, fields, users, connection, logger );

        return abstractMapper.overpoweredAbstractMethod( statement, connection, logger );
    }

    public boolean deleteUser( int userId, Logger logger ) {
        String tableName = "EMKO_USERS_TBL";

        List<String> columnNames = abstractMapper.getColumnNamesByTableName( tableName, connection, logger );

        PreparedStatement statement = statementCreator.generatesQLStringDelete( "DELETE", tableName, userId, columnNames, connection, logger );

        return abstractMapper.overpoweredAbstractMethod( statement, connection, logger );
    }

    public boolean updateUsers( ArrayList<User> users, List<Field> fields, Logger logger ) {
        String tableName = "EMKO_USERS_TBL";

        List<String> columnNames = abstractMapper.getColumnNamesByTableName( tableName, connection, logger );

        PreparedStatement statement = statementCreator.generateSQLStringUpdate( "UPDATE", tableName, users, fields, columnNames, connection, logger );

        return abstractMapper.overpoweredAbstractMethod( statement, connection, logger );
    }

    public List<Article> getAllArticles( List<Field> fields, Logger logger ) {

        String tableName = "EMKO_ARTICLES_TBL";

        PreparedStatement statement = statementCreator.generateSQLString( "SELECT *", tableName, connection, logger );

        return abstractMapper.overpoweredAbstractMethod( statement, Article.class, fields, connection, logger );
    }

    public boolean insertArticles( ArrayList<Article> articles, List<Field> fields, Logger logger ) {

        String tableName = "EMKO_ARTICLES_TBL";

        for ( int i = 0; i < articles.size(); i++ ) {
            articles.get( i ).setId( abstractMapper.getNextSequenceID( "EMKO_ARTICLES_ID_SEQ", connection, logger ) );
        }

        PreparedStatement statement = statementCreator.generateSQLStringInsert( "INSERT", tableName, fields, articles, connection, logger );

        return abstractMapper.overpoweredAbstractMethod( statement, connection, logger );
    }

    public boolean deleteArticle( int articleId, Logger logger ) {
        String tableName = "EMKO_ARTICLES_TBL";

        List<String> columnNames = abstractMapper.getColumnNamesByTableName( tableName, connection, logger );

        PreparedStatement statement = statementCreator.generatesQLStringDelete( "DELETE", tableName, articleId, columnNames, connection, logger );

        return abstractMapper.overpoweredAbstractMethod( statement, connection, logger );
    }

    public boolean updateArticle( ArrayList<Article> articles, List<Field> fields, Logger logger ) {
        String tableName = "EMKO_ARTICLES_TBL";

        List<String> columnNames = abstractMapper.getColumnNamesByTableName( tableName, connection, logger );

        PreparedStatement statement = statementCreator.generateSQLStringUpdate( "UPDATE", tableName, articles, fields, columnNames, connection, logger );

        return abstractMapper.overpoweredAbstractMethod( statement, connection, logger );
    }

}
