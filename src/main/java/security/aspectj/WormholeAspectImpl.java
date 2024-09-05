package security.aspectj;

import edit.common.exceptions.EDITSecurityAccessException;
import edit.common.exceptions.EDITSecurityException;
import edit.common.vo.*;
import edit.portal.common.session.UserSession;
import edit.services.component.IUseCase;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import security.SecuritySession;
import security.ComponentMethod;
import security.business.Security;
import security.component.SecurityComponent;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

import engine.*;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Feb 25, 2004
 * Time: 2:40:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class WormholeAspectImpl
{
    /**
     * Guarantees that a user has established a properly authenticated session with the business services layer.
     * @param session
     */
    protected void validateUserSession(HttpSession session)
    {
        UserSession userSession = (UserSession) session.getAttribute("userSession");

        if (!userSession.userLoggedIn())
        {
            EDITSecurityAccessException e = new EDITSecurityAccessException("User Not Logged In");

            e.setErrorType(EDITSecurityAccessException.LOGIN_ERROR);

            throw e;
        }

        SecuritySession securitySession = SecuritySession.getSecuritySession(userSession.getSessionId());

        if (securitySession == null)
        {
            EDITSecurityAccessException e = new EDITSecurityAccessException("Expired Session");

            e.setErrorType(EDITSecurityAccessException.SESSION_TIMEOUT_ERROR);

            throw e;
        }

        if (!securitySession.sessionIsActive())
        {
            EDITSecurityAccessException e = new EDITSecurityAccessException("Expired Session");

            e.setErrorType(EDITSecurityAccessException.SESSION_TIMEOUT_ERROR);

            throw e;
        }
    }

    /**
     * Checks the roles mapped to the authenticated user determining whether they are authorized or not.
     * @param session
     * @param joinPoint
     */
    protected void authorizeUser(HttpSession session, JoinPoint joinPoint)
    {
        boolean userHasAuthorization = false;

        if (joinPoint.getThis() instanceof IUseCase) // We enforce authorization for UseCaseComponents only
        {
            Signature s = joinPoint.getSignature();

            String className = s.getDeclaringType().getName();

            String methodName = s.getName();

            if (methodName.equals("<init>")) // If it's a constructor, get the actual constructor name
            {
                try
                {
                    methodName = parseCompoundName(Class.forName(className).getConstructor(null).getName());
                }
                catch (Exception e)
                {
                    System.out.println(e);

                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                    throw new RuntimeException(e);
                }
            }

            UserSession userSession = (UserSession) session.getAttribute("userSession");

            String sessionId = userSession.getSessionId();


            ProductStructure currentProductStructure =
                        userSession.getCurrentProductStructure();

            Security security = new SecurityComponent();

            userHasAuthorization = security.authorize(sessionId, className, methodName, currentProductStructure);

            if (!userHasAuthorization)
            {
                String detailedMessage =
                    getDetailedMessage(
                        className, methodName, currentProductStructure);

                EDITSecurityAccessException e = new EDITSecurityAccessException(
                    detailedMessage);

                e.setErrorType(EDITSecurityAccessException.AUTHORIZATION_ERROR);

                throw e;

            }
        }
        else
        {
            userHasAuthorization = true;
        }
    }

    /**
     * Format an explicit error message.
     * @param className
     * @param methodName
     * @param currentProductStructure
     * @return
     */
    private String getDetailedMessage(
            String className, String methodName, ProductStructure currentProductStructure)
    {

        String detailedMessage;

        try  // we are doing to throw an exception - don't let this message mess up for any reason
        {
            ComponentMethod componentMethod =
                    ComponentMethod.findByComponentClassNameAndMethodName(className, methodName);

            String componentNameCT = "";
            if (componentMethod != null)
            {
                    componentNameCT = componentMethod.getComponentNameCT();
            }

            detailedMessage = "No authorization for action " +
                        componentNameCT + "-" + methodName;
        }
        catch(Exception ex)
        {
            detailedMessage = "No Authorization for Requested Action";
        }

        return detailedMessage;
    }

    private static String parseCompoundName(String name)
    {
        return name.substring(name.lastIndexOf(".") + 1, name.length());
    }

    /**
     * Only methods from the business interface and the constructor of the business component are to be targeted.
     * The ComponentMethods stored in the ComponentMethod table respect this as well.
     *
     * @param joinPoint
     * @return
     */
    protected boolean isValidPointcut(JoinPoint joinPoint)
    {
        boolean validPointcut = false;

        Object targetObject = joinPoint.getThis();

        Class[] interfaces = targetObject.getClass().getInterfaces();

        if (interfaces == null || interfaces.length > 1)
        {
            throw new RuntimeException("A Business Component Must Implement Exactly One Business Interface");
        }

        String targetMethodName = getTargetMethodName(joinPoint);

        if (targetMethodName.equals("<init>")) // A constructor is always a valid pointcut
        {
            validPointcut = true;
        }
        else
        {
            Method[] interfaceMethods = interfaces[0].getDeclaredMethods();

            for (int i = 0; i < interfaceMethods.length; i++)
            {
                String interfaceMethodName = interfaceMethods[i].getName();

                if (interfaceMethodName.equals(targetMethodName))
                {
                    validPointcut = true;

                    break;
                }
            }
        }

        return validPointcut;
    }

    protected String getTargetMethodName(JoinPoint joinPoint)
    {
        String targetMethodName = null;

        targetMethodName = joinPoint.getSignature().getName();

        return targetMethodName;
    }

    protected void updateLastAccess(HttpSession session)
    {
        UserSession userSession = (UserSession) session.getAttribute("userSession");

        SecuritySession.updateLastAccess(userSession.getSessionId());
    }
}
