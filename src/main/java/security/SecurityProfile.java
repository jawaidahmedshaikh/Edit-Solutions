/*
 * User: gfrosti
 * Date: Jan 9, 2004
 * Time: 10:54:06 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package security;

import edit.services.db.hibernate.*;

import java.util.*;


public class SecurityProfile extends HibernateEntity
{
    private Long securityProfilePK;
    private int sessionTimeoutInMinutes;
    private int maxLoginAttempts;

    private static SecurityProfile securityProfile;   // singleton

    //  Children - up to 2 masks, 1 PasswordMask, 1 operator mask
    private Set<Mask> masks;

    public static int DEFAULT_SESSION_TIMEOUT = 20;
    public static int DEFAULT_MAX_LOGIN_ATTEMPTS = 0; // 0 = unlimited unsuccessful login attempts

    public static final String DATABASE = SessionHelper.SECURITY;


    public SecurityProfile()
    {
        this.init();
    }

    private void init()
    {
        this.setSessionTimeoutInMinutes(DEFAULT_SESSION_TIMEOUT);
        
        this.setMaxLoginAttempts(DEFAULT_MAX_LOGIN_ATTEMPTS);
        
        masks = new HashSet<Mask>();
    }

    /**
     * Returns the value of field 'securityProfilePK'.
     *
     * @return the value of field 'securityProfilePK'.
     */
    public Long getSecurityProfilePK()
    {
        return this.securityProfilePK;
    }

    /**
     * Returns the value of field 'sessionTimeoutInMinutes'.
     *
     * @return the value of field 'sessionTimeoutInMinutes'.
     */
    public int getSessionTimeoutInMinutes()
    {
        return this.sessionTimeoutInMinutes;
    }

    /**
     * Sets the value of field 'securityProfilePK'.
     *
     * @param securityProfilePK the value of field 'securityProfilePK'.
     */
    public void setSecurityProfilePK(Long securityProfilePK)
    {
        this.securityProfilePK = securityProfilePK;
    }

    /**
     * Sets the value of field 'sessionTimeoutInMinutes'.
     *
     * @param sessionTimeoutInMinutes the value of field 'sessionTimeoutInMinutes'.
     */
    public void setSessionTimeoutInMinutes(int sessionTimeoutInMinutes)
    {
        this.sessionTimeoutInMinutes = sessionTimeoutInMinutes;
    }
    
    /**
     * Setter.
     * @param maxLoginAttempts
     */
    public void setMaxLoginAttempts(int maxLoginAttempts)
    {
        this.maxLoginAttempts = maxLoginAttempts;
    }

    /**
     * Getter.
     * @return
     */
    public int getMaxLoginAttempts()
    {
        return maxLoginAttempts;
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, SecurityProfile.DATABASE);
        
        // forces the reload of singleton object.
        this.securityProfile = null;
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, SecurityProfile.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return SecurityProfile.DATABASE;
    }

    /**
     * Getter.
     * @return
     */
    public Set<Mask> getMasks()
    {
        return masks;
    }
    
    /**
     * Setter.
     * @param masks
     */
    public void setMasks(Set<Mask> masks)
    {
        this.masks = masks;
    }

    public Mask getOperatorMask()
    {
        return Mask.findOperatorMaskBySecurityProfileFK(this.getSecurityProfilePK());
    }

    public PasswordMask getPasswordMask()
    {
        return PasswordMask.findBySecurityProfileFK(this.getSecurityProfilePK());
    }
    
    public static SecurityProfile getSingleton()
    {
        if (securityProfile == null)
        {
            securityProfile = SecurityProfile.findSecurityProfile();
        }

        return securityProfile;
    }

//    public void associate(Mask mask)
//    {
//        mask.associate(this);
//    }
//
//    public void associate(PasswordMask passwordMask)
//    {
//        passwordMask.associate(this);
//    }

    private static SecurityProfile findSecurityProfile()
    {
        //   Only 1 SecurityProfile should exist
        return SecurityProfile.find();
    }
    
    private static SecurityProfile find()
    {
        SecurityProfile securityProfile = null;
        
        // left join Operator type Mask will not have PasswordMask.

        String hql = " select securityProfile " +
                     " from SecurityProfile securityProfile" +
                     " join fetch securityProfile.Masks mask" +
                     " left join fetch mask.PasswordMasks passwordMask";

        Map params = new HashMap();

        List results = SessionHelper.executeHQL(hql, params, SecurityProfile.DATABASE);

        //  There should only be 1
        if (!results.isEmpty())
        {
            securityProfile = (SecurityProfile) results.get(0);
        }

        return securityProfile;
    }
}
