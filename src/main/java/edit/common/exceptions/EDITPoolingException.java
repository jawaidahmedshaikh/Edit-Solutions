/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Nov 4, 2002
 * Time: 2:04:22 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package edit.common.exceptions;

public class EDITPoolingException extends Exception {

    public EDITPoolingException(){

        super();
    }

    public EDITPoolingException(String message){

        super(message);
    }

    public EDITPoolingException(Exception e)
    {
        super(e);
    }
}

