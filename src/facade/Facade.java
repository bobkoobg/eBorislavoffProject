package facade;

import dataSource.AbstractMapper;
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

    //TODO : Error handling
    //***TO BE ADDED - Check if fields in Classes types == DB Types(down here)
    //ArrayList<String> listOfSQLFieldTypes = getSQTableInformation("data_type", tableName,
    //       builder, connect);
    //System.out.println("listOfSQLFieldTypes : " + listOfSQLFieldTypes.toString());
    //***>>>
    public <T> List<User> getAllAbstract( String tableName, Class<T> entityType,
            List<Field> fields, Logger logger ) {

        PreparedStatement statement = statementCreator
                .generateSQLString( tableName, connection, logger );
        return abstractMapper
                .overpoweredAbstractMethod( statement, entityType, fields, connection, logger );
    }

    public <T> List<T> getSpecificAbstract( String tableName, Class<T> entityType,
            List<Field> fields, int entryId, Logger logger ) {
        List<String> columnNames = abstractMapper
                .getColumnNamesByTableName( tableName, connection, logger );

        PreparedStatement statement = statementCreator
                .generateSQLString( "SELECT *", tableName, entryId, columnNames, connection, logger );

        return abstractMapper.overpoweredAbstractMethod( statement, entityType, fields, connection, logger );
    }

    public <T> boolean insertAbstract( String tableName, Class<T> entityType,
            ArrayList<T> toInsert, List<Field> fields, Logger logger ) {

        PreparedStatement statement = statementCreator
                .generateSQLStringInsert( tableName, fields, toInsert, connection, logger );

        return abstractMapper.overpoweredAbstractMethod( statement, connection, logger );
    }

    public int getNextSequenceIdAbstract( String sequenceName, Logger logger ) {
        return abstractMapper.getNextSequenceID( sequenceName, connection, logger );
    }

    public boolean deleteAbstract( String tableName, int entryId, Logger logger ) {
        List<String> columnNames = abstractMapper
                .getColumnNamesByTableName( tableName, connection, logger );

        PreparedStatement statement = statementCreator
                .generateSQLString( "DELETE", tableName, entryId, columnNames, connection, logger );

        return abstractMapper.overpoweredAbstractMethod( statement, connection, logger );
    }

    public <T> boolean updateAbstract( String tableName, ArrayList<T> toUpdate, List<Field> fields, Logger logger ) {
        List<String> columnNames = abstractMapper
                .getColumnNamesByTableName( tableName, connection, logger );

        PreparedStatement statement = statementCreator
                .generateSQLStringUpdate( tableName, toUpdate, fields, columnNames, connection, logger );

        return abstractMapper.overpoweredAbstractMethod( statement, connection, logger );
    }

}
