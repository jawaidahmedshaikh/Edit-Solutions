/*
 * User: gfrosti
 * Date: Jan 9, 2004
 * Time: 1:44:23 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Ostermiller.util.Base64;
import com.Ostermiller.util.RandPass;

import edit.common.EDITDate;
import edit.common.exceptions.EDITSecurityException;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;


public class Password extends HibernateEntity
{
    private Long passwordPK;

    private String encryptedPassword;
    private EDITDate creationDate;
    private String status;
    private int orderOfCreation;

    //  Parents
    private Long passwordMaskFK;
    private Long operatorFK;
    private PasswordMask passwordMask;
    private Operator operator;

    public static final String DATABASE = SessionHelper.SECURITY;

    public static final String STATUS_INACTIVE = "INACTIVE";

    public static final String STATUS_ACTIVE = "ACTIVE";

    public static final String STATUS_TEMPORARY = "TEMPORARY";


    public Password()
    {
    }

    /**
     * Returns the value of field 'creationDate'.
     *
     * @return the value of field 'creationDate'.
     */
    public EDITDate getCreationDate()
    {
        return this.creationDate;
    }

    /**
     * Returns the value of field 'encryptedPassword'.
     *
     * @return the value of field 'encryptedPassword'.
     */
    public String getEncryptedPassword()
    {
        return this.encryptedPassword;
    }

    /**
     * Returns the value of field 'operatorFK'.
     *
     * @return the value of field 'operatorFK'.
     */
    public Long getOperatorFK()
    {
        return this.operatorFK;
    }

    public Operator getOperator()
    {
        return this.operator;
    }

    /**
     * Returns the value of field 'orderOfCreation'.
     *
     * @return the value of field 'orderOfCreation'.
     */
    public int getOrderOfCreation()
    {
        return this.orderOfCreation;
    }

    /**
     * Returns the value of field 'passwordMaskFK'.
     *
     * @return the value of field 'passwordMaskFK'.
     */
    public Long getPasswordMaskFK()
    {
        return this.passwordMaskFK;
    }

    public PasswordMask getPasswordMask()
    {
        return this.passwordMask;
    }

    /**
     * Returns the value of field 'passwordPK'.
     *
     * @return the value of field 'passwordPK'.
     */
    public Long getPasswordPK()
    {
        return this.passwordPK;
    }

    /**
     * Returns the value of field 'status'.
     *
     * @return the value of field 'status'.
     */
    public String getStatus()
    {
        return this.status;
    }

    /**
     * Sets the value of field 'creationDate'.
     *
     * @param creationDate the value of field 'creationDate'.
     */
    public void setCreationDate(EDITDate creationDate)
    {
        this.creationDate = creationDate;
    }

    /**
     * Sets the value of field 'encryptedPassword'.
     *
     * @param encryptedPassword the value of field 'encryptedPassword'.
     */
    public void setEncryptedPassword(java.lang.String encryptedPassword)
    {
        this.encryptedPassword = encryptedPassword;
    }

    /**
     * Sets the value of field 'operatorFK'.
     *
     * @param operatorFK the value of field 'operatorFK'.
     */
    public void setOperatorFK(Long operatorFK)
    {
        this.operatorFK = operatorFK;
    }

    public void setOperator(Operator operator)
    {
        this.operator = operator;
    }

    /**
     * Sets the value of field 'orderOfCreation'.
     *
     * @param orderOfCreation the value of field 'orderOfCreation'.
     */
    public void setOrderOfCreation(int orderOfCreation)
    {
        this.orderOfCreation = orderOfCreation;
    }

    /**
     * Sets the value of field 'passwordMaskFK'.
     *
     * @param passwordMaskFK the value of field 'passwordMaskFK'.
     */
    public void setPasswordMaskFK(Long passwordMaskFK)
    {
        this.passwordMaskFK = passwordMaskFK;
    }

    public void setPasswordMask(PasswordMask passwordMask)
    {
        this.passwordMask = passwordMask;
    }

    /**
     * Sets the value of field 'passwordPK'.
     *
     * @param passwordPK the value of field 'passwordPK'.
     */
    public void setPasswordPK(Long passwordPK)
    {
        this.passwordPK = passwordPK;
    }

    /**
     * Sets the value of field 'status'.
     *
     * @param status the value of field 'status'.
     */
    public void setStatus(String status)
    {
        this.status = status;
    }

    /**
     * Encrypts the passwords then sets it in the VO
     *
     * @param password
     *
     * @throws EDITSecurityException
     */
    public void setPassword(String password) throws EDITSecurityException
    {
        this.setEncryptedPassword(Password.encryptPassword(password));
    }

    public static String encryptPassword(String password)
    {
        String encryptedPassword;

        try
        {
            MessageDigest algorithm = null;

            algorithm = MessageDigest.getInstance("SHA-1");

            byte[] origBytes = algorithm.digest(password.getBytes());

            byte[] encodedBytes = Base64.encode(origBytes);

            encryptedPassword = new String(encodedBytes);
        }
        catch (NoSuchAlgorithmException e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }

        return encryptedPassword;
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        if (this.isNew())
        {
            this.setPasswordMask(SecurityProfile.getSingleton().getPasswordMask());
            this.setCreationDate(new EDITDate());

            //  Get the Password with the latest order of creation
            Password latestPassword = Password.findByLatestOrderOfCreation(this.getOperator());

            if (latestPassword != null)
            {
                this.setOrderOfCreation(latestPassword.getOrderOfCreation() + 1);   // up the count
            }
            else
            {
                // 1st password for this operator
                this.setOrderOfCreation(1);
            }
        }

        SessionHelper.saveOrUpdate(this, Password.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, Password.DATABASE);
    }

    public boolean isNew()
    {
        return (this.getPasswordPK() == null);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return Password.DATABASE;
    }

    public static Password findByOperatorNameAndEncryptedPasswordAndStatus(String operatorName, String encryptedPassword, String status)
    {
        Password password = null;

        String hql = "select password from Password password " +
                     " join password.Operator operator " +
                     " where operator.Name = :operatorName " +
                     " and password.EncryptedPassword = :encryptedPassword" +
                     " and password.Status = :status";

        Map params = new HashMap();
        params.put("operatorName", operatorName);
        params.put("encryptedPassword", encryptedPassword);
        params.put("status", status);

        List results = SessionHelper.executeHQL(hql, params, Password.DATABASE);

        if (! results.isEmpty())
        {
            password = (Password) results.get(0);
        }

        return password;
    }

    public static Password findByOperatorNameAndEncryptedPassword(String operatorName, String encryptedPassword)
    {
        Password password = null;

        String hql = "select password from Password password " +
                     " join password.Operator operator " +
                     " where operator.Name = :operatorName " +
                     " and password.EncryptedPassword = :encryptedPassword " +
                     " order by password.CreationDate ASC";

        Map params = new HashMap();
        params.put("operatorName", operatorName);
        params.put("encryptedPassword", encryptedPassword);

        List results = SessionHelper.executeHQL(hql, params, Password.DATABASE);

        if (! results.isEmpty())
        {
            password = (Password) results.get(0);
        }

        return password;
    }

    public static Password findByOperatorPKAndStatus(Long operatorPK, String status)
    {
        Password password = null;

        String hql = "select password from Password password " +
                     " where password.OperatorFK = :operatorPK " +
                     " and password.Status = :status";

        Map params = new HashMap();
        params.put("operatorPK", operatorPK);
        params.put("status", status);

        List results = SessionHelper.executeHQL(hql, params, Password.DATABASE);

        if (! results.isEmpty())
        {
            password = (Password) results.get(0);
        }

        return password;
    }

    public static Password[] findByOperator(Operator operator)
    {
        Password[] passwords = null;

        String hql = "select password from Password password " +
                     " where password.Operator = :operator";

        Map params = new HashMap();
        params.put("operator", operator);

        List results = SessionHelper.executeHQL(hql, params, Password.DATABASE);

        if (! results.isEmpty())
        {
            passwords = (Password[]) results.toArray(new Password[results.size()]);
        }

        return passwords;
    }
    
    public static Password[] findByOperatorName(String operatorName)
    {
        String hql = "select password from Password password " +
                     " join password.Operator operator " +
                     " where operator.Name = :operatorName";

        Map params = new HashMap();
        params.put("operatorName", operatorName);
        
        List results = SessionHelper.executeHQL(hql, params, Password.DATABASE);
        
        return (Password[]) results.toArray(new Password[results.size()]);
    }

    public static Password findByLatestOrderOfCreation(Operator operator)
    {
        Password password = null;

        String hql = "select password from Password password " +
                     " where password.Operator = :operator " +
                     " and password.OrderOfCreation = (select max(p2.OrderOfCreation) from Password p2 " +
                                                       "where p2.Operator = :operator)";

        Map params = new HashMap();
        params.put("operator", operator);

        List results = SessionHelper.executeHQL(hql, params, Password.DATABASE);

        if (! results.isEmpty())
        {
            password = (Password) results.get(0);
        }

        return password;
    }

    public static String generateRandomPassword()
    {
        PasswordMask passwordMask = SecurityProfile.getSingleton().getPasswordMask();

        Mask mask = passwordMask.getMask();

        RandPass generator = new RandPass();
        generator.setAlphabet(new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'});
        generator.addRequirement(new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}, 1);
        String passwordString = generator.getPass(mask.getMinLength());

        return passwordString;
    }

    /**
     * Tests to see if this password has expired
     *
     * @param password
     *
     * @return true if expired
     */
    public boolean hasExpired()
    {
        SecurityProfile securityProfile = SecurityProfile.getSingleton();
        PasswordMask passwordMask = securityProfile.getPasswordMask();

        int expirationInDays = passwordMask.getExpirationInDays();

        EDITDate todaysDate = new EDITDate();
        EDITDate expirationDate = this.getCreationDate().addDays(expirationInDays);

        if (todaysDate.after(expirationDate))
        {
//            throw new EDITSecurityException("The password expired on " + expirationDate.toStringYYYYMMDD(),
//                    EDITSecurityException.EXPIRED_PASSWORD);
            return true;
        }

        return false;
    }
}

