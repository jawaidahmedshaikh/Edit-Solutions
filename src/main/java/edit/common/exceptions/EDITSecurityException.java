/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jan 1, 2003
 * Time: 7:49:03 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package edit.common.exceptions;

public class EDITSecurityException extends EDITException
{

    public static final int AUTHENTICATION_ERROR = 0;
    public static final int AUTHORIZATION_ERROR = 1;
    public static final int EXPIRED_PASSWORD = 2;
    public static final int DUPLICATE_OPERATOR_NAME = 3;
    public static final int INVALID_PASSWORD = 4;
    public static final int INVALID_USERNAME = 5;
//    public static final int SESSION_TIMEOUT_ERROR =  6;
    public static final int REPEATED_PASSWORD =  7;

    private static int errorType;

    public EDITSecurityException()
    {
        super();
    }
                                                 
    public EDITSecurityException(String message)
    {
        super(message);
    }

    public EDITSecurityException(String message, int errorType)
    {
        super(message);
        EDITSecurityException.errorType = errorType;
    }

    public void setErrorType(int errorType)
    {
        EDITSecurityException.errorType = errorType;
    }

    public int getErrorType()
    {
        return errorType;
    }
}
