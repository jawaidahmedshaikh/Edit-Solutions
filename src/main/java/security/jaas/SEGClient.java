package security.jaas;

//import test.security.callbackhandler.*;
//import test.security.action.*;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jan 13, 2004
 * Time: 10:26:44 AM
 * To change this template use Options | File Templates.
 */
public class SEGClient
{
    public static void main(String[] args) throws Exception
    {
        System.setProperty("java.security.auth.login.config","C:\\Tomcat5\\webapps\\PORTAL\\WEB-INF\\EDITServices.jaas");

        SEGCallbackHandler callbackHandler = new SEGCallbackHandler("SEG", "SEG");

        LoginContext lc = new LoginContext("EDITSolutionsJAAS", callbackHandler);

        lc.login();

        Subject subject =lc.getSubject();

        System.out.println("pause");
    }

}
