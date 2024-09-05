package security.jaas;

//import test.security.principal.*;

import edit.common.exceptions.EDITLoginException;
import edit.common.exceptions.EDITSecurityAccessException;
import edit.common.EDITDate;

import edit.services.db.hibernate.SessionHelper;

import java.util.HashMap;

import security.Operator;
import security.Password;
import security.Role;
import security.SecuritySession;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import security.Mask;
import security.SecurityProfile;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Dec 15, 2003
 * Time: 3:27:15 PM
 * To change this template use Options | File Templates.
 */
public class SEGLoginModule implements LoginModule
{
    private Subject subject; // client has same reference
    private CallbackHandler callbackHandler;
    private boolean loginSuccessful = false;
    private boolean commitSuccessful = false;
    private Operator operator;
    private SecurityProfile securityProfile;
    private static Map<String, Integer> numberOfUnsuccessfulLoginsByUsername = new HashMap<String, Integer>();

    public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options)
    {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
    }
    
    /**
     * Part of the login life-cycle of the JAAS. A successful authentication establishes a subject and its principals.
     * @return
     * @throws LoginException
     */
    public boolean login() throws LoginException
    {
        try
        {
            NameCallback nameCallback = new NameCallback("name: ");

            PasswordCallback passwordCallback = new PasswordCallback("password: ", false);

            callbackHandler.handle(new Callback[]{nameCallback, passwordCallback});

            String username = nameCallback.getName();

            String password = new String(passwordCallback.getPassword());

            String encryptedPassword = Password.encryptPassword(password);

            if (!isOperatorLocked(username))
            {
                operator = Operator.findByOperatorNameAndEncryptedPassword(username, encryptedPassword);
    
                if (operator != null)
                {
                    EDITDate terminationDate = operator.getTerminationDate();

                    if (terminationDate != null && terminationDate.before(new EDITDate()))
                    {
                        EDITSecurityAccessException e = new EDITSecurityAccessException("Operator Has Been Terminated");

                        e.setErrorType(EDITSecurityAccessException.TERMINATED_OPERATOR_EXCEPTION);

                        throw e;
                    }

                    Password currentPassword = operator.getCurrentPassword();
    
    //                PasswordVO passwordVO = (PasswordVO) currentPassword.getVO();
    
                    String currentEncryptedPassword = currentPassword.getEncryptedPassword();
    
                    if (encryptedPassword.equals(currentEncryptedPassword))
                    {
                        resetNumberOfUnsuccessfulLoginAttempts(username);
                    
                        if (currentPassword.getStatus().equals(Password.STATUS_TEMPORARY) ||
                                currentPassword.getStatus().equals(Password.STATUS_INACTIVE) || currentPassword.hasExpired())
                        {
                            EDITSecurityAccessException e = new EDITSecurityAccessException("Password Has Expired");
    
                            e.setErrorType(EDITSecurityAccessException.EXPIRED_PASSWORD_EXCEPTION);
    
                            throw e;
                        }
                        else if (operator.getCurrentPassword().getStatus().equals(Password.STATUS_ACTIVE))
                        {
                            loginSuccessful = true;
                        }
                    }
                    else
                    {
                        EDITSecurityAccessException e = new EDITSecurityAccessException("Invalid Username / Password");
    
                        e.setErrorType(EDITSecurityAccessException.LOGIN_ERROR);
    
                        throw e;
                    }
                }
                else
                {
                    // Only keep track of unsuccessful logins for  valid operator.
                    if (isUsernameExists(username))
                    {
                        updateNumberOfUnsuccessfulLogins(username);
                    
                        if (isMaxLoginAttemptsReached(username))
                        {
                            updateLockedIndicator();
                        
                            EDITSecurityAccessException e = new EDITSecurityAccessException(username + " has been locked out");
    
                            e.setErrorType(EDITSecurityAccessException.USER_LOCKED_EXCEPTION);
    
                            throw e;
                        }
                    }
                
                    EDITSecurityAccessException e = new EDITSecurityAccessException("Invalid Username / Password");
    
                    e.setErrorType(EDITSecurityAccessException.LOGIN_ERROR);
    
                    throw e;
                }
            }
            else
            {
                EDITSecurityAccessException e = new EDITSecurityAccessException(username + " has been locked out");
                
                e.setErrorType(EDITSecurityAccessException.USER_LOCKED_EXCEPTION);
                
                throw e;
            }
        }
        catch (EDITSecurityAccessException e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            loginSuccessful = false;

            throw new EDITLoginException(e);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            loginSuccessful = false;

            throw new RuntimeException(e);
        }

        return loginSuccessful;
    }

    public boolean commit() throws LoginException
    {
        if (!loginSuccessful)
        {
            operator = null;

            commitSuccessful = false;
        }
        else
        {
            try
            {
                Role[] roles = operator.getRoles();

                if (roles == null)
                {
                    commitSuccessful = false;

                    EDITSecurityAccessException e = new EDITSecurityAccessException("Operator Has Not Been Mapped To Any Role(s)");

                    e.setErrorType(EDITSecurityAccessException.AUTHORIZATION_ERROR);

                    throw e;
                }
                else
                {
                    // Map Roles
                    for (Role role : roles)
                    {
                        String roleName =  role.getName();

                        subject.getPrincipals().add(new SEGPrincipal(roleName, SEGPrincipal.TYPE_ROLE));
                    }

                    // Map PK
                    subject.getPrincipals().add(new SEGPrincipal(operator.getOperatorPK() + "", SEGPrincipal.TYPE_PK));

                    // Map Session Id
                    SecuritySession securitySession = new SecuritySession();

                    securitySession.setSubject(subject);

                    securitySession.setOperatorPK(operator.getOperatorPK());

                    subject.getPrincipals().add(new SEGPrincipal(securitySession.getSessionId(), SEGPrincipal.TYPE_SESSION_ID));

                    subject.setReadOnly();

                    SecuritySession.registerSecuritySession(securitySession.getSessionId(), securitySession);

                    commitSuccessful = true;
                }
            }
            catch (EDITSecurityAccessException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use Options | File Templates.

                throw new EDITLoginException(e);
            }
            finally
            {
                operator = null;
            }
        }

        return commitSuccessful;
    }
    
    public boolean abort() throws LoginException
    {
        return false;
    }

    /**
     * Removes the user's session from the business layer
     * @return
     * @throws LoginException
     */
    public boolean logout() throws LoginException
    {
        Set principals = subject.getPrincipals();

        for (Iterator i = principals.iterator(); i.hasNext();)
        {
            SEGPrincipal segPrincipal = (SEGPrincipal) i.next();

            if (segPrincipal.getType() == SEGPrincipal.TYPE_SESSION_ID)
            {
                SecuritySession.unregisterSecuritySession(segPrincipal.getName());
            }
        }

        return true;
    }
    
    /**
     * Returns true if operator is locked.
     * @param username
     * @return
     */
    private boolean isOperatorLocked(String username) 
    {
        boolean isLocked = false;
        
        Operator operator = Operator.findByOperatorName(username);
        
        if (operator != null)
        {
            String lockedIndicator = operator.getLockedIndicator();
            
            if (lockedIndicator != null && lockedIndicator.equals("Y")) 
            {
                isLocked = true;    
            }
        }
        
        return isLocked;
    }
    
    /**
     * Verifies if the operator by username exists in the system.
     * @param username
     * @return
     */
    private boolean isUsernameExists(String username)
    {
        boolean isExists = false;
    
        operator = Operator.findByOperatorName(username);
        
        if (operator != null)
        {
            isExists = true;
        }
        
        return isExists;
    }
    
    /**
     * Getter.
     * @return
     */
    private SecurityProfile getSecurityProfile()
    {
        // to avoid retrieving security profile repeatedly.
        if (securityProfile == null)
        {
            if (operator != null)
            {
                Mask operatorMask = operator.getMask();
                
                securityProfile = operatorMask.getSecurityProfile();
            }
        }
        
        return securityProfile;
    }
    
    /**
     * Verifies if the operator has reached max number of unsuccessful logins.
     * @return
     */
    private boolean isMaxLoginAttemptsReached(String username)
    {
        boolean isMaxLoginAttemptsReached = false;

        int maxLoginAttempts = getSecurityProfile().getMaxLoginAttempts();

        int numberOfUnsuccessfulLogins = numberOfUnsuccessfulLoginsByUsername.get(username).intValue();

        // maxLoginAttempts = 0 the value is not set and that means unlimited number of unsuccessful login attempts 
        if (maxLoginAttempts != 0 && numberOfUnsuccessfulLogins >= maxLoginAttempts)
        {
            isMaxLoginAttemptsReached = true;
        }

        return isMaxLoginAttemptsReached;
    }
    
    /**
     * Updates the number of unsuccessful logins.
     * @param username
     */
    private void updateNumberOfUnsuccessfulLogins(String username)
    {
        if (numberOfUnsuccessfulLoginsByUsername.containsKey(username))
        {
            // increment the unsuccessful counter.
            int numberOfUnsuccessfulLogins = numberOfUnsuccessfulLoginsByUsername.get(username).intValue();
            
            numberOfUnsuccessfulLogins = numberOfUnsuccessfulLogins + 1;
            
            numberOfUnsuccessfulLoginsByUsername.put(username, new Integer(numberOfUnsuccessfulLogins));
        }
        else
        {
            // first unsuccesful login.
            numberOfUnsuccessfulLoginsByUsername.put(username, new Integer(1));
        }
    }
    
    /**
     * Updates the LockedIndicator.
     */
    private void updateLockedIndicator()
    {
        operator.setLockedIndicator("Y");
        
        operator.hSave();
    }
    
    /**
     * After successful login removes the corresponding entry.
     * @param username
     */
    private void resetNumberOfUnsuccessfulLoginAttempts(String username)
    {
        if (numberOfUnsuccessfulLoginsByUsername.containsKey(username))
        {
            numberOfUnsuccessfulLoginsByUsername.remove(username);
        }
    }
}
