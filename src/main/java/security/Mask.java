/*
 * User: gfrosti
 * Date: Jan 9, 2004
 * Time: 10:54:06 AM
 * To change this template use Options | File Templates.
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


public class Mask extends HibernateEntity
{
    private Long maskPK;

    private String maskTypeCT;
    private int minLength;
    private int maxLength;
    private String mixedCaseCT;
    private String alphaNumericCT;
    private String specialCharsCT;

    //  Parent
    private Long securityProfileFK;
    private SecurityProfile securityProfile;

    //  Children
    private Set<Operator> operators;
    private Set<PasswordMask> passwordMasks;

    public static final String DATABASE = SessionHelper.SECURITY;

    public static final String OPERATOR_MASKTYPE = "Operator";
    public static final String PASSWORD_MASKTYPE = "Password";

    public static final int DEFAULT_MIN_LENGTH = 4;

    public static final int DEFAULT_MAX_LENGTH = 8;

    public static final String DEFAULT_MIXED_CASE_CT = "AllowedNR";

    public static final String DEFAULT_ALPHANUMERIC_CT = "AllowedNR";

    public static final String DEFAULT_SPECIAL_CHARS_CT = "AllowedNR";


    /**
     * Constructor which takes no arguments
     */
    // hibernate needs atleast package visibility.
    // treat this as private for usage.
    Mask()
    {
        this.init();
        this.setDefaults();
    }

    /**
     * Constructor which takes a maskTypeCT
     *
     * @param maskTypeCT
     */
    public Mask(String maskTypeCT)
    {
        this();
        this.setMaskTypeCT(maskTypeCT);
    }

    /**
     * Guarantees that the objects are properly instantiated.
     */
    private final void init()
    {
        if (operators == null)
        {
            operators = new HashSet();
        }

        if (passwordMasks == null)
        {
            passwordMasks = new HashSet();
        }
    }

    /**
     * Sets all default values
     */
    private final void setDefaults()
    {
        this.setMinLength(Mask.DEFAULT_MIN_LENGTH);
        this.setMaxLength(Mask.DEFAULT_MAX_LENGTH);
        this.setMixedCaseCT(Mask.DEFAULT_MIXED_CASE_CT);
        this.setAlphaNumericCT(Mask.DEFAULT_ALPHANUMERIC_CT);
        this.setSpecialCharsCT(Mask.DEFAULT_SPECIAL_CHARS_CT);
    }

    /**
     * Returns the value of field 'alphaNumericCT'.
     *
     * @return the value of field 'alphaNumericCT'.
     */
    public String getAlphaNumericCT()
    {
        return this.alphaNumericCT;
    }

    /**
     * Returns the value of field 'maskPK'.
     *
     * @return the value of field 'maskPK'.
     */
    public Long getMaskPK()
    {
        return this.maskPK;
    }

    /**
     * Returns the value of field 'maskTypeCT'.
     *
     * @return the value of field 'maskTypeCT'.
     */
    public String getMaskTypeCT()
    {
        return this.maskTypeCT;
    }

    /**
     * Returns the value of field 'maxLength'.
     *
     * @return the value of field 'maxLength'.
     */
    public int getMaxLength()
    {
        return this.maxLength;
    }

    /**
     * Returns the value of field 'minLength'.
     *
     * @return the value of field 'minLength'.
     */
    public int getMinLength()
    {
        return this.minLength;
    }

    /**
     * Returns the value of field 'mixedCaseCT'.
     *
     * @return the value of field 'mixedCaseCT'.
     */
    public String getMixedCaseCT()
    {
        return this.mixedCaseCT;
    }

    /**
     * Returns the value of field 'securityProfileFK'.
     *
     * @return the value of field 'securityProfileFK'.
     */
    public Long getSecurityProfileFK()
    {
        return this.securityProfileFK;
    }

    /**
     * Returns the parent securityProfile
     *
     * @return
     */
    public SecurityProfile getSecurityProfile()
    {
        return this.securityProfile;
    }

    /**
     * Returns the value of field 'specialCharsCT'.
     *
     * @return the value of field 'specialCharsCT'.
     */
    public String getSpecialCharsCT()
    {
        return this.specialCharsCT;
    }

    /**
     * Sets the value of field 'alphaNumericCT'.
     *
     * @param alphaNumericCT the value of field 'alphaNumericCT'.
     */
    public void setAlphaNumericCT(String alphaNumericCT)
    {
        this.alphaNumericCT = alphaNumericCT;
    }

    /**
     * Sets the value of field 'maskPK'.
     *
     * @param maskPK the value of field 'maskPK'.
     */
    public void setMaskPK(Long maskPK)
    {
        this.maskPK = maskPK;
    }

    /**
     * Sets the value of field 'maskTypeCT'.
     *
     * @param maskTypeCT the value of field 'maskTypeCT'.
     */
    public void setMaskTypeCT(String maskTypeCT)
    {
        this.maskTypeCT = maskTypeCT;
    }

    /**
     * Sets the value of field 'maxLength'.
     *
     * @param maxLength the value of field 'maxLength'.
     */
    public void setMaxLength(int maxLength)
    {
        this.maxLength = maxLength;
    }

    /**
     * Sets the value of field 'minLength'.
     *
     * @param minLength the value of field 'minLength'.
     */
    public void setMinLength(int minLength)
    {
        this.minLength = minLength;
    }

    /**
     * Sets the value of field 'mixedCaseCT'.
     *
     * @param mixedCaseCT the value of field 'mixedCaseCT'.
     */
    public void setMixedCaseCT(java.lang.String mixedCaseCT)
    {
        this.mixedCaseCT = mixedCaseCT;
    }

    /**
     * Sets the value of field 'securityProfileFK'.
     *
     * @param securityProfileFK the value of field 'securityProfileFK'.
     */
    public void setSecurityProfileFK(Long securityProfileFK)
    {
        this.securityProfileFK = securityProfileFK;
    }

    /**
     * Sets the value of field 'specialCharsCT'.
     *
     * @param specialCharsCT the value of field 'specialCharsCT'.
     */
    public void setSpecialCharsCT(String specialCharsCT)
    {
        this.specialCharsCT = specialCharsCT;
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return Mask.DATABASE;
    }

    //  CHILDREN

    /**
     * Getter
     *
     * @return set of operators
     */
    public Set<Operator> getOperators()
    {
        return this.operators;
    }

    /**
     * Setter
     *
     * @param operators set of operators
     */
    public void setOperators(Set<Operator> operators)
    {
        this.operators = operators;
    }

    /**
     * Adds an Operator to the set of children
     *
     * @param operator
     */
    public void addOperator(Operator operator)
    {
        this.getOperators().add(operator);

        operator.setMask(this);

        SessionHelper.saveOrUpdate(operator, Mask.DATABASE);
    }

    /**
     * Removes an Operator from the set of children
     *
     * @param operator
     */
    public void removeOperator(Operator operator)
    {
        this.getOperators().remove(operator);

        operator.setMask(null);

        SessionHelper.saveOrUpdate(operator, Mask.DATABASE);
    }

    /**
     * Getter
     *
     * @return set of passwordMasks
     */
    public Set<PasswordMask> getPasswordMasks()
    {
        return this.passwordMasks;
    }

    /**
     * Setter
     *
     * @param passwordMasks      set of passwordMasks
     */
    public void setPasswordMasks(Set<PasswordMask> passwordMasks)
    {
        this.passwordMasks = passwordMasks;
    }

    /**
     * Adds a PasswordMask to the set of children
     *
     * @param passwordMask
     */
    public void addPasswordMask(PasswordMask passwordMask)
    {
        this.getPasswordMasks().add(passwordMask);

        passwordMask.setMask(this);

        SessionHelper.saveOrUpdate(passwordMask, Mask.DATABASE);
    }

    /**
     * Removes a PasswordMask from the set of children
     *
     * @param passwordMask
     */
    public void removePasswordMask(PasswordMask passwordMask)
    {
        this.getPasswordMasks().remove(passwordMask);

        passwordMask.setMask(null);

        SessionHelper.saveOrUpdate(passwordMask, Mask.DATABASE);
    }
    
    public void setSecurityProfile(SecurityProfile securityProfile)
    {
        this.securityProfile = securityProfile;
    }
    

    //  HIBERNATE

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, Mask.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, Mask.DATABASE);
    }

//    public void associate(SecurityProfile securityProfile)
//    {
//        this.setSecurityProfileFK(securityProfile.getSecurityProfilePK());
//
//        save();
//    }


    //  FINDS

    public static Mask[] findBySecurityProfileFK(Long securityProfileFK)
    {
        Mask[] masks = null;

        String hql = "select mask from Mask mask " +
                " where mask.SecurityProfileFK = :securityProfileFK";

        Map params = new HashMap();
        params.put("securityProfileFK", securityProfileFK);

        List results = SessionHelper.executeHQL(hql, params, Mask.DATABASE);

        if (!results.isEmpty())
        {
            masks = (Mask[]) results.toArray(new Mask[results.size()]);
        }

        return masks;
    }

    /**
     * Finds the Operator mask for a given security profile
     *
     * @param securityProfileFK
     *
     * @return
     */
    public static Mask findOperatorMaskBySecurityProfileFK(Long securityProfileFK)
    {
        return Mask.findByMaskTypeCT_AND_SecurityProfileFK(Mask.OPERATOR_MASKTYPE, securityProfileFK);
    }

    /**
     * Finds the Password mask for a given security profile
     *
     * @param securityProfileFK
     *
     * @return
     */
    public static Mask findPasswordMaskBySecurityProfileFK(Long securityProfileFK)
    {
        return Mask.findByMaskTypeCT_AND_SecurityProfileFK(Mask.PASSWORD_MASKTYPE, securityProfileFK);
    }

    /**
     * Finds the mask for a given mask type and security profile
     * <p/>
     * Only 1 Mask should exist for a given mask type
     *
     * @param securityProfileFK
     *
     * @return
     */
    private static Mask findByMaskTypeCT_AND_SecurityProfileFK(String maskTypeCT, Long securityProfileFK)
    {
        Mask mask = null;

        String hql = "select mask from Mask mask " +
                "where mask.SecurityProfileFK = :securityProfileFK " +
                "and mask.MaskTypeCT = :maskTypeCT";

        Map params = new HashMap();
        params.put("securityProfileFK", securityProfileFK);
        params.put("maskTypeCT", maskTypeCT);

        List results = SessionHelper.executeHQL(hql, params, Mask.DATABASE);

        if (!results.isEmpty())
        {
            mask = (Mask) results.get(0);
        }

        return mask;
    }

    /**
     * Validate the "quality" of the string against the defined Mask
     *
     * @param str
     *
     * @return true if the string met the requirements defined in the Mask
     *
     * @throws Exception
     */
    public boolean validate(String str) throws EDITSecurityException
    {
        String maskType = "";
        int errorType = EDITSecurityException.INVALID_PASSWORD; // just to initialize to make compiler happy


        if (this.getMaskTypeCT().equalsIgnoreCase(OPERATOR_MASKTYPE))
        {
            maskType = "Username";
            errorType = EDITSecurityException.INVALID_USERNAME;
        }
        else if (this.getMaskTypeCT().equalsIgnoreCase(PASSWORD_MASKTYPE))
        {
            maskType = "Password";
            errorType = EDITSecurityException.INVALID_PASSWORD;
        }


        if (!Util.isGEMinLength(str, this.getMinLength()))
        {
            throw new EDITSecurityException("Minimum length of " + this.getMinLength() + " is required for " + maskType, errorType);
        }
        if (!Util.isLEMaxLength(str, this.getMaxLength()))
        {
            throw new EDITSecurityException("Maximum length of " + this.getMaxLength() + " is required for " + maskType,
                    errorType);
        }
        if (!this.meetsMixedCase(str, this.getMixedCaseCT()))
        {
            throw new EDITSecurityException(maskType + " " + str + " does not meet the mixed case restriction of " + this.getMixedCaseCT(),
                    errorType);
        }
        if (!this.meetsAlphaNumeric(str, this.getAlphaNumericCT()))
        {
            throw new EDITSecurityException(maskType + " " + str + " does not meet the alphanumeric restriction of " + this.getAlphaNumericCT(),
                    errorType);
        }
        if (!this.meetsSpecialChars(str, this.getSpecialCharsCT()))
        {
            throw new EDITSecurityException(maskType + " " + str + " does not meet the special character restriction of " + this.getSpecialCharsCT(),
                    errorType);
        }

        return true;
    }

    /**
     * Checks the string to see if it meets the imposed restrictions for mixed case characters.
     *
     * @param str
     * @param restriction - valid values are "AllowedNR" (Allowed/Not Required), "Required", and "NotAllowed"
     *
     * @return true if the string meets the restriction
     *
     * @throws Exception
     */
    private boolean meetsMixedCase(String str, String restriction)
    {
        if (restriction.equalsIgnoreCase("AllowedNR"))
        {
            //  Anything is allowed, no need to check
            return true;
        }
        else if (restriction.equalsIgnoreCase("Required"))
        {
            //  At least 2 chars must be of different case (i.e. at least 1 upper and 1 lower)
            return Util.isMixedCase(str);
        }
        else if (restriction.equalsIgnoreCase("NotAllowed"))
        {
            //  All characters must have the same case
            if (Util.isMixedCase(str))
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks the string to see if it meets the imposed restrictions for alphanumeric characters
     *
     * @param str
     * @param restriction - valid values are "AllowedNR" (Allowed/Not Required), "Required", and "NotAllowed"
     *
     * @return true if the string meets the restriction
     *
     * @throws Exception
     */
    private boolean meetsAlphaNumeric(String str, String restriction)
    {
        if (restriction.equalsIgnoreCase("AllowedNR"))
        {
            //  Anything is allowed, no need to check
            return true;
        }
        else if (restriction.equalsIgnoreCase("Required"))
        {
            //  Must contain at least one letter and one number
            if (Util.hasAlphabeticChar(str))
            {
                return Util.hasNumericChar(str);
            }
            else
            {
                return false;
            }
        }
        else if (restriction.equalsIgnoreCase("NotAllowed"))
        {
            //  Cannot be alphnumeric - all chars must be alphabetic
            return Util.isAlphabetic(str);
        }

        return false;
    }

    /**
     * Checks the string to see if it meets the imposed restrictions for special characters
     *
     * @param str
     * @param restriction - valid values are "AllowedNR" (Allowed/Not Required), "Required", and "NotAllowed"
     *
     * @return true if the string meets the restriction
     *
     * @throws Exception
     */
    private boolean meetsSpecialChars(String str, String restriction)
    {
        if (restriction.equalsIgnoreCase("AllowedNR"))
        {
            //  Anything is allowed, no need to check
            return true;
        }
        else if (restriction.equalsIgnoreCase("Required"))
        {
            //  Must have at least one special character
            return Util.hasSpecialCharacter(str);
        }
        else if (restriction.equalsIgnoreCase("NotAllowed"))
        {
            //  No special characters are allowed (just letters and numbers - alphanumeric)
            return Util.isAlphaNumeric(str);
        }

        return false;
    }
}
