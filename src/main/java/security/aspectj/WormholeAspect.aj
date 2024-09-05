package security.aspectj;

import fission.ui.servlet.RequestManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edit.common.exceptions.EDITSecurityException;
import edit.common.exceptions.EDITSecurityAccessException;

import edit.portal.common.session.UserSession;

import security.aspectj.WormholeAspectImpl;

import org.aspectj.lang.Signature;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.SourceLocation;

import java.lang.reflect.Method;

import edit.services.component.IUseCase;

import security.sp.SecurityProcessor;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Feb 17, 2004
 * Time: 1:46:31 PM
 * To change this template use File | Settings | File Templates.
 */
public aspect WormholeAspect
{
    private WormholeAspectImpl wormholeAspectImpl = new WormholeAspectImpl();

    /**
     * Specifies the starting point for all client calls to business services. The union of this starting point, with
     * the ending point (the callee space) defines the set of target jointpoints.
     */
    pointcut callerSpace(HttpServletRequest request) :
            execution(void RequestManager.processRequest(..))
            && args(request, HttpServletResponse);

    /**
     * Specifies the ending point for all client calls to business services.
     */
    pointcut calleeSpace() :

                execution(public * edit.services.component.IUseCase+.*(..))

            ||  execution(public * accounting.business.Accounting+.*(..)) || execution(public * client.business.Lookup+.*(..))

            ||  execution(public * agent.business.Agent+.*(..))

            ||  execution(public * client.business.Client+.*(..)) || execution(public * client.business.Lookup+.*(..))

            ||  execution(public * contract.business.Contract+.*(..)) || execution(public * contract.business.Lookup+.*(..))

            ||  execution(public * engine.business.Calculator+.*(..)) || execution(public * engine.business.Lookup+.*(..)) || execution(public * engine.business.Analyzer+.*(..))

            ||  execution(public * event.business.Event+.*(..))

            ||  execution(public * reporting.business.Reporting+.*(..))

            ||  execution(public * role.business.Role+.*(..)) || execution(public * role.business.Lookup+.*(..))

            ||  ( execution(public * security.business.Security+.*(..)) &&  ! execution(public * security.business.Security.login(..)) &&  ! execution(public * security.business.Security.authorize(..)) &&  ! execution(public * security.business.Security.securityIsInitialized(..)) &&  ! execution(public * security.business.Security.initializeSecurity(..)) &&  ! execution(public * security.business.Security.updatePassword(..)) && ! execution(public * security.business.Security.logout(..)))

            && ! execution(public * edit.services.component.ILockableElement+.*(..));

    /**
     * Specifies the union of the callee space and the caller space
     */
    pointcut wormhole(HttpServletRequest request) :
            cflow(callerSpace(request))
            &&  calleeSpace();

    /**
     * Establishes the (set) of security steps when a proper pointcut is reached
     */
    before(HttpServletRequest request) : wormhole(request)
            {
                SecurityProcessor.setSecurityIsEnabled(true);

                if (wormholeAspectImpl.isValidPointcut(thisJoinPoint))
                {
                    HttpSession session = request.getSession();

                    wormholeAspectImpl.validateUserSession(session);

                    wormholeAspectImpl.updateLastAccess(session);

                    wormholeAspectImpl.authorizeUser(session, thisJoinPoint);
                }
            }
}

