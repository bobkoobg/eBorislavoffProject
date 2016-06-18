package dataSource;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AbstractMapper<T> {

    /*
     * Get the column names of any table
     */
    public List<String> getColumnNamesByTableName( String tableName,
            Connection connection, Logger logger ) {

        List<String> listOfFieldNames = new ArrayList<>();

        String SQLFieldNamesString = "Select column_name "
                + "from user_tab_cols "
                + "where table_name=upper('" + tableName + "')";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement( SQLFieldNamesString );

            ResultSet resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                listOfFieldNames.add( resultSet.getString( 1 ) );
            }

            statement.close();
        } catch ( SQLException ex ) {

            logger.severe( "getColumnNamesByTableName : " + ex );
            return null;
        }
        return listOfFieldNames;
    }

    /*
     * Get next sequence id from any sequence
     */
    public int getNextSequenceID( String sequenceName, Connection connection,
            Logger logger ) {
        PreparedStatement preparedStatement = null;
        int sequenceId = 0;

        String SQLString = "select " + sequenceName + ".nextval from dual";
        try {
            preparedStatement = connection.prepareStatement( SQLString );
            ResultSet rs = preparedStatement.executeQuery();
            try {
                if ( rs.next() ) {
                    sequenceId = rs.getInt( 1 );
                }
            } finally {
                try {
                    rs.close();
                } catch ( Exception e ) {
                    logger.warning( "Exception - during retrieval of next duel id : " + e );
                }
            }
        } catch ( SQLException e ) {
            logger.severe( "Method insertDuel (Part1) - Execution SQL Exception : [ " + e + " ]" );
            return 0;
        } finally {
            try {
                if ( preparedStatement != null ) {
                    preparedStatement.close();
                }
            } catch ( SQLException e ) {
                logger.severe( "Method insertDuel (Part1) - Closing SQL Exception : [ " + e + " ]" );
                return 0;
            }
        }
        return sequenceId;
    }


    /*
     * INSERT, UPDATE, DELETE from any table based on prepared statement
     */
    public boolean overpoweredAbstractMethod( PreparedStatement actualStatement,
            Connection connect, Logger logger ) {

        try {
            actualStatement.executeUpdate();
            actualStatement.close();
        } catch ( SQLException ex ) {
            logger.severe( "overpoweredAbstractMethod (INSERT, UPDATE, DELETE) : " + ex );
            return false;
        }
        return true;
    }

    /*
     * SELECT * (ALL) from any table based on entity class type and list of entity
     * class fields
     */
    public <T> ArrayList<T> overpoweredAbstractMethod( PreparedStatement statement,
            Class<T> typeOfCurrentClass, List<Field> fieldsInClasses, Connection connect,
            Logger logger ) {

        List<T> listOfAllRequested = new ArrayList();
        try {
            T currentRequest;
            ResultSet rs = statement.executeQuery();
            while ( rs.next() ) {
                currentRequest = typeOfCurrentClass.newInstance();

                for ( int j = 0; j < fieldsInClasses.size(); j++ ) {
                    fieldsInClasses.get( j ).setAccessible( true );
                    int columnIndex = j + 1;

                    if ( fieldsInClasses.get( j ).getType() == Integer.class
                            || fieldsInClasses.get( j ).getType() == int.class ) {

                        fieldsInClasses.get( j ).set( currentRequest, rs.getInt( columnIndex ) );
                    } else if ( fieldsInClasses.get( j ).getType() == String.class ) {

                        fieldsInClasses.get( j ).set( currentRequest, rs.getString( columnIndex ) );
                    } else if ( fieldsInClasses.get( j ).getType() == java.util.Date.class ) {

                        fieldsInClasses.get( j ).set( currentRequest, new java.util.Date( rs.getDate( columnIndex ).getTime() ) );
                    } else if ( fieldsInClasses.get( j ).getType() == Boolean.class ) {

                        fieldsInClasses.get( j ).set( currentRequest, ( Boolean ) "Y".equals( rs.getString( columnIndex ) ) );
                    }
                }
                listOfAllRequested.add( currentRequest );
            }
        } catch ( SQLException | InstantiationException | IllegalAccessException ex ) {
            logger.severe( "overpoweredAbstractMethod (SELECT *) : " + ex );
            return null;
        }
        return ( ArrayList<T> ) listOfAllRequested;
    }
}
