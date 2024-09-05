package security.jaas;

import javax.security.auth.callback.*;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Dec 15, 2003
 * Time: 4:14:55 PM
 * To change this template use Options | File Templates.
 */
public class SEGCallbackHandler implements CallbackHandler
{
    private String username;

    private String password;

    public SEGCallbackHandler(String username, String password)
    {
        this.username = username;

        this.password = password;
    }

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException
    {
        for (int i = 0; i < callbacks.length; i++)
        {
            if (callbacks[i] instanceof NameCallback)
            {
                ((NameCallback) callbacks[i]).setName(username);
            }

            if (callbacks[i] instanceof PasswordCallback)
            {
                ((PasswordCallback) callbacks[i]).setPassword(password.toCharArray());
            }
        }
    }
}
