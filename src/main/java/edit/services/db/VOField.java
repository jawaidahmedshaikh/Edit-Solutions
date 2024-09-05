/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jun 17, 2002
 * Time: 11:02:28 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package edit.services.db;

public class VOField {

    private String  fieldName;
    private Class   fieldType;

    public VOField(String fieldName, Class fieldType){

        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    public String getFieldName(){

        return fieldName;
    }

    public Class getFieldType(){

        return fieldType;
    }
}
