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

import edit.common.exceptions.EDITSecurityException;
import edit.services.db.hibernate.*;

import java.util.*;

import fission.utility.*;

/**
 * For the entity objects, PasswordMask is instantiated and has a Mask.  For VO objects, PasswordMask is an extension
 * of Mask.  That makes the parent/child relationships exactly opposite between entity and VO objects.
 */

public class PasswordMask extends HibernateEntity
{
    private Long passwordMaskPK;

    private int expirationInDays;
    private String restrictRepeats;
    private int numberOfRepeatCycles;

    //  Parent
    private Long maskFK;
    private Mask mask;

    //  Children
    private Set<Password> passwords;

    public static final String DATABASE = SessionHelper.SECURITY;


    public static final int DEFAULT_EXPIRATION_IN_DAYS = 30;

    public static final String DEFAULT_RESTRICT_REPEATS = "N";

    public static final int DEFAULT_NUM_REPEAT_CYCLES = 0;


    public PasswordMask()
    {
        init();
    }

    private void init()
    {
        if (passwords == null)
        {
            passwords = new HashSet<Password>();
        }

        this.mask = new Mask(Mask.PASSWORD_MASKTYPE);

        this.setDefaults();
    }

    private final void setDefaults()
    {
        this.setExpirationInDays(PasswordMask.DEFAULT_EXPIRATION_IN_DAYS);
        this.setRestrictRepeats(PasswordMask.DEFAULT_RESTRICT_REPEATS);
        this.setNumberOfRepeatCycles(PasswordMask.DEFAULT_NUM_REPEAT_CYCLES);
    }

    /**
     * Returns the value of field 'expirationInDays'.
     *
     * @return the value of field 'expirationInDays'.
     */
    public int getExpirationInDays()
    {
        return this.expirationInDays;
    }

    /**
     * Returns the value of field 'restrictRepeats'.
     *
     * @return the value of field 'restrictRepeats'.
     */
    public String getRestrictRepeats()
    {
        return this.restrictRepeats;
    }

    /**
     * Returns the value of field
     * 'numberOfRepeatCycles'.
     *
     * @return the value of field 'numberOfRepeatCycles'.
     */
    public int getNumberOfRepeatCycles()
    {
        return this.numberOfRepeatCycles;
    }

    /**
     * Returns the value of field 'passwordMaskPK'.
     *
     * @return the value of field 'passwordMaskPK'.
     */
    public Long getPasswordMaskPK()
    {
        return this.passwordMaskPK;
    }

    /**
     * Returns the value of field 'maskFK'.
     *
     * @return the value of field 'maskFK'.
     */
    public Long getMaskFK()
    {
        return this.maskFK;
    }

    public Mask getMask()
    {
        return this.mask;
    }

    public void setMask(Mask mask)
    {
        this.mask = mask;
    }

    /**
     * Sets the value of field 'expirationInDays'.
     *
     * @param expirationInDays the value of field 'expirationInDays'
     */
    public void setExpirationInDays(int expirationInDays)
    {
        this.expirationInDays = expirationInDays;
    }

    /**
     * Sets the value of field 'restrictRepeats'.
     *
     * @param restrictRepeats the value of field 'restrictRepeats'.
     */
    public void setRestrictRepeats(String restrictRepeats)
    {
        this.restrictRepeats = restrictRepeats;
    }

    /**
     * Sets the value of field 'numberOfRepeatCycles'.
     *
     * @param numberOfRepeatCycles the value of field 'numberOfRepeatCycles'.
     */
    public void setNumberOfRepeatCycles(int numberOfRepeatCycles)
    {
        this.numberOfRepeatCycles = numberOfRepeatCycles;
    }

    /**
     * Sets the value of field 'passwordMaskPK'.
     *
     * @param passwordMaskPK the value of field 'passwordMaskPK'.
     */
    public void setPasswordMaskPK(Long passwordMaskPK)
    {
        this.passwordMaskPK = passwordMaskPK;
    }

    /**
     * Sets the value of field 'maskFK'.
     *
     * @param maskFK the value of field 'maskFK'.
     */
    public void setMaskFK(Long maskFK)
    {
        this.maskFK = maskFK;
    }

    /**
     * Getter
     * @return  set of policyGroupInvestments
     */
    public Set<Password> getPasswords()
    {
        return this.passwords;
    }

    /**
     * Setter
     * @param passwords      set of passwords
     */
    public void setPasswords(Set<Password> passwords)
    {
        this.passwords = passwords;
    }

    /**
     * Adds a Password to the set of children
     * @param password
     */
    public void addPassword(Password password)
    {
        this.getPasswords().add(password);

        password.setPasswordMask(this);

        SessionHelper.saveOrUpdate(password, PasswordMask.DATABASE);
    }

    /**
     * Removes a Password from the set of children
     * @param policyGroupInvestment
     */
    public void removePassword(Password password)
    {
        this.getPasswords().remove(password);

        password.setPasswordMask(null);

        SessionHelper.saveOrUpdate(password, PasswordMask.DATABASE);
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, PasswordMask.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {

        SessionHelper.delete(this, PasswordMask.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return PasswordMask.DATABASE;
    }

    /**
     * Finds the "Password" mask for a given SecurityProfile
     * There should only be 1
     *
     * @param securityProfileFK
     *
     * @return
     */
    public static PasswordMask findBySecurityProfileFK(Long securityProfileFK)
    {
        PasswordMask passwordMask = null;

        String hql = "select passwordMask from PasswordMask passwordMask " +
                " join passwordMask.Mask mask " +
                " where mask.SecurityProfileFK = :securityProfileFK " +
                " and mask.MaskTypeCT = :maskTypeCT";

        Map params = new HashMap();
        params.put("securityProfileFK", securityProfileFK);
        params.put("maskTypeCT", Mask.PASSWORD_MASKTYPE);

        List results = SessionHelper.executeHQL(hql, params, PasswordMask.DATABASE);

        if (!results.isEmpty())
        {
            passwordMask = (PasswordMask) results.get(0);
        }

        return passwordMask;
    }

    /**
     * Validates the newPassword against the password mask.
     *
     * @param oldPassword       -
     * @param newPassword       - contains encrypted password
     * @param newPasswordString - unencrypted password
     *
     * @return
     *
     * @throws EDITSecurityException
     */
    public boolean validate(Password newPassword, String newPasswordString) throws EDITSecurityException
    {
        //  First, validate the password string against the Mask
        Mask mask = this.getMask();
        mask.validate(newPasswordString);    // will throw exception if it fails

        //  Then validate the Password against the PasswordMask
        return validatePasswordMask(newPassword);
    }

    /**
     * Validates the password against the mask
     *
     * @param passwordMask - mask to be validated against
     * @param password     - Password object containing which will be validated
     *
     * @return true if passes validation test
     *
     * @throws EDITSecurityException
     */
    private boolean validatePasswordMask(Password password) throws EDITSecurityException
    {
        if (this.getRestrictRepeats().equalsIgnoreCase("Y"))
        {
            hasRepeated(password);
        }

        return true;
    }

    /**
     * If the new password has already been used within the last so many cycles (defined by the PasswordMask), throws
     * an error
     *
     * @param passwordMask
     * @param password
     * @param operator
     *
     * @return false if not repeated
     *
     * @throws EDITSecurityException
     */
    private boolean hasRepeated(Password password) throws EDITSecurityException
    {
        int numberOfRepeatCycles = this.getNumberOfRepeatCycles();

        //  Get all the passwords for given operator
        Password[] passwordsForOperator = Password.findByOperator(password.getOperator());


        if (passwordsForOperator != null)
        {
            //  Sort the passwords in descending order by orderOfCreation (newest has highest orderOfCreation and will
            //  be first in sorted array
            Password[] ascendingPasswords = (Password[]) Util.sortObjects(passwordsForOperator, new String[]{"getOrderOfCreation"});
            Password[] descendingPasswords = (Password[]) Util.invertObjects(ascendingPasswords);


            //  The numberOfRepeatCycles could have been changed after the passwords were already placed in database
            //  (the list of passwords does not get "updated" - usually don't have more passwords than numberOfRepeatCycles.)
            //  Only compare the new password to the newest ones in the database and up to the numberOfRepeatCycles (or
            //  up to the number of passwords in the database if that is less)

            int nloops = descendingPasswords.length;
            if (descendingPasswords.length > numberOfRepeatCycles)
            {
                nloops = numberOfRepeatCycles;
            }

            //  Check for repeats
            for (int i = 0; i < nloops; i++)
            {
                if (password.getEncryptedPassword().equals(descendingPasswords[i].getEncryptedPassword()))

                {
                    //  Password has been repeated, throw error
                    throw new EDITSecurityException("Password has already been used.  Cannot be repeated within " +
                            numberOfRepeatCycles + " changes", EDITSecurityException.REPEATED_PASSWORD);
                }
            }
        }

        return false;
    }

//    /**
//     * Tests to see if this password has expired
//     * @param passwordMask
//     * @param password
//     * @return true if expired
//     * @throws EDITSecurityException
//     */
//    public boolean hasExpired(PasswordMask passwordMask, Password password)
//    {
//        PasswordMaskVO passwordMaskVO = (PasswordMaskVO) passwordMask.getVO();
//
//        int expirationInDays = passwordMaskVO.getExpirationInDays();
//
//        PasswordVO passwordVO = (PasswordVO) password.getVO();
//
//        EDITDate creationDate = new EDITDate(passwordVO.getCreationDate());
//        EDITDate todaysDate = new EDITDate();
//        EDITDate expirationDate = creationDate.addDays(expirationInDays);
//
//        if (todaysDate.ifGreater(expirationDate))
//        {
////            throw new EDITSecurityException("The password expired on " + expirationDate.toStringYYYYMMDD(),
////                    EDITSecurityException.EXPIRED_PASSWORD);
//            return true;
//        }
//
//        return false;
//    }
}
