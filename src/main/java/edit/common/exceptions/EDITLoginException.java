package edit.common.exceptions;

import javax.security.auth.login.LoginException;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Feb 27, 2004
 * Time: 12:08:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class EDITLoginException extends LoginException
{
    private EDITSecurityAccessException e;

    public EDITLoginException(EDITSecurityAccessException e)
    {
        this.e = e;
    }

    public EDITSecurityAccessException getEDITSecurityAccessException()
    {
        return e;
    }
}
