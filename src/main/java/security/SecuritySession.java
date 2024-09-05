package security;

import edit.common.exceptions.EDITSecurityAccessException;
import edit.common.vo.SecurityProfileVO;
import security.jaas.SEGPrincipal;

import javax.security.auth.Subject;
import java.rmi.dgc.VMID;
import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jan 12, 2004
 * Time: 3:33:53 PM
 * To change this template use Options | File Templates.
 */
public class SecuritySession
{
    private Subject subject;

    private static Map activeSecuritySessions = new HashMap();  // uses sessionId as key

    private Long operatorPK;

    private int sessionTimeoutInMinutes;

    private long lastAccess;

    private String sessionId;

    private FilteredRole[] filteredRolesAllowed;

    public SecuritySession()
    {
        this.sessionId = new VMID().toString();

        updateLastAccess();
    }

    public String getSessionId()
    {
        return this.sessionId;
    }

    public Subject getSubject()
    {
        return subject;
    }

    /**
     * Return array of FilteredRole's for this principal.
     * @return
     */
    public FilteredRole[] getFilteredRoles()
    {
        if (this.filteredRolesAllowed == null)
        {
            Set segPrincipals = getSubject().getPrincipals();

            List filteredRoles = new ArrayList();

            Iterator i = segPrincipals.iterator();

            while (i.hasNext())
            {
                SEGPrincipal currentPrincipal = (SEGPrincipal) i.next();

                if (currentPrincipal.getType() == SEGPrincipal.TYPE_ROLE)
                {
                    List currentFilteredRoles = currentPrincipal.getFilteredRoles();
                    filteredRoles.addAll(currentFilteredRoles);
                }
            }
            this.filteredRolesAllowed = (FilteredRole[]) filteredRoles.toArray(new FilteredRole[0]);
        }

        return this.filteredRolesAllowed;
    }


    public String[] getRoleNames()
    {
        Set segPrincipals = getSubject().getPrincipals();

        List roleNames = new ArrayList();

        Iterator i = segPrincipals.iterator();

        while (i.hasNext())
        {
            SEGPrincipal currentPrincipal = (SEGPrincipal) i.next();

            if (currentPrincipal.getType() == SEGPrincipal.TYPE_ROLE)
            {
                roleNames.add(currentPrincipal.getName());
            }
        }

        return (String[]) roleNames.toArray(new String[roleNames.size()]);
    }

    public void setSubject(Subject subject)
    {
        this.subject = subject;
    }

    public Long getOperatorPK()
    {
        return this.operatorPK;
    }

    public void setOperatorPK(Long operatorPK)
    {
        this.operatorPK = operatorPK;
    }

    public long getLastAccess()
    {
        return lastAccess;
    }

    public void setLastAccess(int lastAccess)
    {
        this.lastAccess = lastAccess;
    }

    public void setSessionId(String sessionId)
    {
        this.sessionId = sessionId;
    }

    public static void registerSecuritySession(String sessionId, SecuritySession securitySession)
    {
        if (! activeSecuritySessions.containsKey(sessionId))
        {
            activeSecuritySessions.put(sessionId, securitySession);
        }
    }

    public static void unregisterSecuritySession(String sessionId)
    {
        SecuritySession securitySession = (SecuritySession) activeSecuritySessions.remove(sessionId);
    }

    public static SecuritySession getSecuritySession(String sessionId)
    {
        SecuritySession securitySession = (SecuritySession) activeSecuritySessions.get(sessionId);

        return securitySession;
    }

    public static SecuritySession getSecuritySession(Long operatorPK)
    {
        SecuritySession securitySession = null;

        Iterator enumer = activeSecuritySessions.values().iterator();

        while (enumer.hasNext())
        {
            securitySession = (SecuritySession) enumer.next();

            if (securitySession.getOperatorPK() == operatorPK)
            {
                break;
            }
            else
            {
                securitySession = null;
            }
        }

        return securitySession;
    }

    public static void updateLastAccess(String sessionId) throws EDITSecurityAccessException
    {
        SecuritySession securitySession = getSecuritySession(sessionId);

        if (securitySession == null)
        {
            EDITSecurityAccessException securityException =
                    new EDITSecurityAccessException("Session Has Expired - Inactivity > " + securitySession.getSessionTimeoutInMinutes() + " Minutes",
                            EDITSecurityAccessException.SESSION_TIMEOUT_ERROR);

            throw securityException;
        }
        else
        {
            securitySession.updateLastAccess();
        }
    }

    int getSessionTimeoutInMinutes()
    {
        if (sessionTimeoutInMinutes == 0)
        {
            Operator operator = Operator.findByPK(this.operatorPK);

            Mask mask = operator.getMask();

            SecurityProfile securityProfile = mask.getSecurityProfile();

            sessionTimeoutInMinutes = securityProfile.getSessionTimeoutInMinutes();
        }

        return sessionTimeoutInMinutes;
    }

    static
    {
//        Thread timeoutManager = new Thread(new Runnable()
//        {
//            public void run()
//            {
//                try
//                {
//                    while (true)
//                    {
//                        Thread.sleep(1000 * 60 * 10); // Wait for 10 minutes, and then check for stale sessions
//
//                        Iterator sessionIds = activeSecuritySessions.keySet().iterator();
//
//                        while (sessionIds.hasNext())
//                        {
//                            String currentSessionId = (String) sessionIds.next();
//
//                            SecuritySession currentSecuritySession = (SecuritySession) activeSecuritySessions.get(currentSessionId);
//
//                            if (! currentSecuritySession.sessionIsActive())
//                            {
//                                SecuritySession.unregisterSecuritySession(currentSessionId);
//                            }
//                        }
//                    }
//                }
//                catch (InterruptedException e)
//                {
//                    e.printStackTrace();  //To change body of catch statement use Options | File Templates.
//
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//
//        timeoutManager.setPriority(Thread.MIN_PRIORITY);
//
//        timeoutManager.setDaemon(true);
//
//        timeoutManager.start();
    }

    public boolean sessionIsActive()
    {
        boolean sessionIsActive = true;

        try
        {
            long now = System.currentTimeMillis();

            double minutesSinceLastAccess = (now - this.lastAccess) / 1000.0 / 60.0;

            if (minutesSinceLastAccess > this.getSessionTimeoutInMinutes())
            {
                sessionIsActive = false;
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }

        return sessionIsActive;
    }

    private void updateLastAccess()
    {
        this.lastAccess = System.currentTimeMillis();
    }
}
