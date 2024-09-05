/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Nov 4, 2002
 * Time: 2:04:22 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package edit.common.exceptions;

public class EDITDeleteException extends EDITException {

    public EDITDeleteException(){

        super();
    }
    
    public EDITDeleteException(String message)
    {
      super(message);     
    }
}

