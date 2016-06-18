package entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EntityClassExplorer {

    /*
     * Gather all fields by class name and return a list of Fields.
     */
    public <T> List<Field> retrieveFieldsFromEntity( Class<T> typeOfCurrentClass ) {

        if ( typeOfCurrentClass != null ) {

            Class superClass = typeOfCurrentClass.getSuperclass();

            List<Field> fieldsInClasses = new ArrayList();

            while ( superClass != null ) {

                //If Class exists and super class exists get fields
                List<Field> reversing = Arrays.asList( superClass.getDeclaredFields() );

                Collections.reverse( reversing );
                fieldsInClasses.addAll( reversing );

                //Check for next super class and continue for loop until all fields 
                //are fetched.
                superClass = superClass.getSuperclass();
            }

            Collections.reverse( fieldsInClasses );

            fieldsInClasses.addAll( Arrays.asList( typeOfCurrentClass.getDeclaredFields() ) );
            return fieldsInClasses;
        } else {
            return null;
        }
    }
}
