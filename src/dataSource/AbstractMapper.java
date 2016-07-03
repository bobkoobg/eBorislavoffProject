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

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( SQLFieldNamesString );

            ResultSet resultSet = preparedStatement.executeQuery();

            while ( resultSet.next() ) {
                listOfFieldNames.add( resultSet.getString( 1 ) );
            }

            resultSet.close();
            preparedStatement.close();
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

            if ( rs.next() ) {
                sequenceId = rs.getInt( 1 );
            }
            rs.close();
            preparedStatement.close();

        } catch ( Exception e ) {
            logger.warning( "Exception - during retrieval of next duel id : " + e );
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
    public <T> ArrayList<T> overpoweredAbstractMethod( PreparedStatement preparedStatement,
            Class<T> typeOfCurrentClass, List<Field> fieldsInClasses, Connection connect,
            Logger logger ) {

        List<T> listOfAllRequested = new ArrayList();
        try {
            T currentRequest;
            ResultSet rs = preparedStatement.executeQuery();
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
            rs.close();
            preparedStatement.close();
        } catch ( SQLException | InstantiationException | IllegalAccessException ex ) {
            logger.severe( "overpoweredAbstractMethod (SELECT *) : " + ex );
            return null;
        }
        return ( ArrayList<T> ) listOfAllRequested;
    }
}
