package edit.common.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Feb 18, 2004
 * Time: 11:28:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class EDITSecurityAccessException extends EDITRuntimeException
{
    public static final int LOGIN_ERROR = 0;
    public static final int AUTHORIZATION_ERROR = 1;
    public static final int SESSION_TIMEOUT_ERROR = 2;
    public static final int EXPIRED_PASSWORD_EXCEPTION = 3;
    public static final int COMPANY_STRUCTURE_NOT_SET_EXCEPTION = 4;
    public static final int USER_LOCKED_EXCEPTION = 5;
    public static final int TERMINATED_OPERATOR_EXCEPTION = 6;

    private static int errorType;

    public EDITSecurityAccessException()
    {
        super();
    }

    public EDITSecurityAccessException(String message)
    {
        super(message);
    }

    public EDITSecurityAccessException(String message, int errorType)
    {
        super(message);

        EDITSecurityAccessException.errorType = errorType;
    }

    public void setErrorType(int errorType)
    {
        EDITSecurityAccessException.errorType = errorType;
    }

    public int getErrorType()
    {
        return errorType;
    }
}
