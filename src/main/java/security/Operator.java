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
import edit.common.*;
import edit.services.db.hibernate.*;
import engine.*;

import java.util.*;

import fission.utility.*;


public class Operator extends HibernateEntity
{
    public static final String CASE_REP = "CaseRep";

    private Long operatorPK;

    private String name;
    private String lastName;
    private String firstName;
    private String middleInitial;
    private String title;
    private String dept;
    private EDITDateTime maintDateTime;
    private String maintOperator;
    private String eMail;
    private String lockedIndicator;
    private String loggedInIndicator;
    private EDITDate terminationDate;
    private String telephoneNumber;
    private String telephoneExtension;
    private String operatorTypeCT;
    private EDITBigDecimal applyMax;
    private EDITBigDecimal removeMax;
    private EDITBigDecimal transferMax;

    //  Parent
    private Long maskFK;
    private Mask mask;

    //  Children
    private Set<OperatorRole> operatorRoles;
    private Set<Password> passwords;

    private String unencryptedTemporaryPassword;

    public static final String DATABASE = SessionHelper.SECURITY;

    public static final String OPERATOR_SYSTEM = "System";

    public Operator()
    {
        this.init();
    }

    /**
     * Guarantees that the objects are properly instantiated.
     */
    private final void init()
    {
        if (operatorRoles == null)
        {
            operatorRoles = new HashSet<OperatorRole>();
        }
        
        if (passwords == null)
        {
            passwords = new HashSet<Password>();
        }
    }

    public Long getOperatorPK()
    {
        return this.operatorPK;
    }

    public String getName()
    {
        return this.name;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public String getFirstName()
    {
        return this.firstName;
    }

    public String getMiddleInitial()
    {
        return this.middleInitial;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getDept()
    {
        return this.dept;
    }

    public EDITDateTime getMaintDateTime()
    {
        return this.maintDateTime;
    }

    public String getMaintOperator()
    {
        return this.maintOperator;
    }

    public String getEMail()
    {
        return this.eMail;
    }

    public String getLockedIndicator()
    {
        return this.lockedIndicator;
    }

    public String getLoggedInIndicator()
    {
        return this.loggedInIndicator;
    }

    public EDITDate getTerminationDate()
    {
        return this.terminationDate;
    }

    public String getTelephoneNumber()
    {
        return this.telephoneNumber;
    }

    public String getTelephoneExtension()
    {
        return this.telephoneExtension;
    }

    public String getOperatorTypeCT()
    {
        return this.operatorTypeCT;
    }

    public EDITBigDecimal getApplyMax()
    {
        return this.applyMax;
    }

    public EDITBigDecimal getRemoveMax()
    {
        return this.removeMax;
    }

    public EDITBigDecimal getTransferMax()
    {
        return this.transferMax;
    }

    public Long getMaskFK()
    {
        return this.maskFK;
    }

    public void setMaskFK(Long maskFK)
    {
        this.maskFK = maskFK;
    }
    public Mask getMask()
    {
        return this.mask;
    }

    public void setMask(Mask mask)
    {
        this.mask = mask;
    }

    public Set<OperatorRole> getOperatorRoles()
    {
        return this.operatorRoles;
    }

    public void setOperatorPK(Long operatorPK)
    {
        this.operatorPK = operatorPK;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setMiddleInitial(String middleInitial)
    {
        this.middleInitial = middleInitial;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDept(String dept)
    {
        this.dept = dept;
    }

    public void setMaintDateTime(EDITDateTime maintDateTime)
    {
        this.maintDateTime = maintDateTime;
    }

    public void setMaintOperator(String maintOperator)
    {
        this.maintOperator = maintOperator;
    }

    public void setEMail(String eMail)
    {
        this.eMail = eMail;
    }

    public void setLockedIndicator(String lockedIndicator)
    {
        this.lockedIndicator = lockedIndicator;
    }

    public void setLoggedInIndicator(String loggedInIndicator)
    {
        this.loggedInIndicator = loggedInIndicator;
    }

    public void setTerminationDate(EDITDate terminationDate)
    {
        this.terminationDate = terminationDate;
    }

    public void setTelephoneNumber(String telephoneNumber)
    {
        this.telephoneNumber = telephoneNumber;
    }

    public void setTelephoneExtension(String telephoneExtension)
    {
        this.telephoneExtension = telephoneExtension;
    }

    public void setOperatorTypeCT(String operatorTypeCT)
    {
        this.operatorTypeCT = operatorTypeCT;
    }

    public void setApplyMax(EDITBigDecimal applyMax)
    {
        this.applyMax = applyMax;
    }

    public void setRemoveMax(EDITBigDecimal removeMax)
    {
        this.removeMax = removeMax;
    }

    public void setTransferMax(EDITBigDecimal transferMax)
    {
        this.transferMax = transferMax;
    }
    
    /**
     * Setter.
     * @param passwords
     */
    public void setPasswords(Set<Password> passwords)
    {
        this.passwords = passwords;
    }

    /**
     * Getter.
     * @return
     */
    public Set<Password> getPasswords()
    {
        return passwords;
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return Operator.DATABASE;
    }

    /**
     * Setter
     *
     * @param operatorRoles set of operatorRoles
     */
    public void setOperatorRoles(Set<OperatorRole> operatorRoles)
    {
        this.operatorRoles = operatorRoles;
    }

    /**
     * Adds an Operator to the set of children
     *
     * @param operatorRole
     */
    public void addOperatorRole(OperatorRole operatorRole)
    {
        this.getOperatorRoles().add(operatorRole);

        operatorRole.setOperator(this);

        SessionHelper.saveOrUpdate(operatorRole, Operator.DATABASE);
    }

    /**
     * Removes an Operator from the set of children
     *
     * @param operatorRole
     */
    public void removeOperator(OperatorRole operatorRole)
    {
        this.getOperatorRoles().remove(operatorRole);

        operatorRole.setOperator(null);

        SessionHelper.saveOrUpdate(operatorRole, Operator.DATABASE);
    }

    public void save(boolean operatorIsNew) throws EDITSecurityException
    {
        Password password = null;
        String passwordString = null;

        if (operatorIsNew) // Don't allow duplicate usernames
        {
            Operator prevOperator = Operator.findByOperatorName(this.getName());

            if (prevOperator != null)
            {
                throw new EDITSecurityException("Error - Duplicate Username");
            }

            //  Validate the username against the mask
            validateAgainstMask(this);

            // Generate the necessary temporary passwords
            // NOTE: temporary passwords are not validated against the SecurityProfile masks
            if ((this.getName().equals("admin")))
            {
                passwordString = "admin";
            }
            else
            {
                passwordString = Password.generateRandomPassword();
            }

            password = new Password();
            password.setPassword(passwordString);
            password.setStatus(Password.STATUS_TEMPORARY);

            this.setUnencryptedTemporaryPassword(passwordString);
        }

        this.setMaintDateTime(new EDITDateTime());
        this.setMaskFK(SecurityProfile.getSingleton().getOperatorMask().getMaskPK());
        this.hSave();

        if (password != null)
        {
//            password.setOperatorFK(this.getOperatorPK());
            password.setOperator(this);

            password.hSave();
        }
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, Operator.DATABASE);
    }

    private void validateAgainstMask(Operator operator) throws EDITSecurityException
    {
        SecurityProfile securityProfile = SecurityProfile.getSingleton();

        Mask operatorMask = securityProfile.getOperatorMask();

//        OperatorVO operatorVO = (OperatorVO) operator.getVO();

        operatorMask.validate(operator.getName());
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, Operator.DATABASE);
    }


    public Role[] getRoles()
    {
        List allRoles = new ArrayList(); // Unique Roles only

        Role[] directRoles = Role.findByOperatorPK(this.getOperatorPK());

        if (directRoles != null)
        {
            for (int i = 0; i < directRoles.length; i++)
            {
                if (!allRoles.contains(directRoles[i]))
                {
                    directRoles[i].setRoleImplication(Role.DIRECTLY_IMPLIED);

                    allRoles.add(directRoles[i]);
                }

                Role[] impliedRoles = directRoles[i].getImpliedRoles();

                if (impliedRoles != null)
                {
                    for (int j = 0; j < impliedRoles.length; j++)
                    {
                        if (!allRoles.contains(impliedRoles[j]))
                        {
                            impliedRoles[j].setRoleImplication(Role.INDIRECTLY_IMPLIED);

                            allRoles.add(impliedRoles[j]);
                        }
                    }
                }
            }
        }

        return (allRoles.size() == 0) ? null : (Role[]) allRoles.toArray(new Role[allRoles.size()]);
    }

    /**
     * Get all allowable FilteredRoles for this operator.
     *
     * @return
     */
    public FilteredRole[] getFilteredRoles()
    {
        Role[] roles = this.getRoles();

        List allFilteredRoles = new ArrayList();

        for (int i = 0; i < roles.length; i++)
        {
            Role role = roles[i];

            FilteredRole[] filteredRoles =
                    FilteredRole.findByRole(role.getRolePK());

            allFilteredRoles.addAll(Arrays.asList(filteredRoles));

        }

        return (FilteredRole[]) allFilteredRoles.toArray(new FilteredRole[0]);
        // OK to use empty template
    }

    /**
     * Get all ProductStructure's for which this operator
     * has a Filtered Role.  Only Product type are returned.
     *
     * @return
     *
     * @see CompanyStructure#findAllProductTypeForOperator(Operator)
     */
    public ProductStructure[] getProductTypeProductStructures()
    {
        return ProductStructure.findAllProductTypeForOperator(this);
    }

    public boolean isNew()
    {
        return (this.getOperatorPK() == new Long(0));
    }

    public Password getCurrentPassword()
    {
        Password password = Password.findByLatestOrderOfCreation(this);

        return password;
    }

    public String getUnencryptedTemporaryPassword()
    {
        return this.unencryptedTemporaryPassword;
    }

    public void setUnencryptedTemporaryPassword(String unencryptedTemporaryPassword)
    {
        this.unencryptedTemporaryPassword = unencryptedTemporaryPassword;
    }

    public boolean updatePassword(String newPasswordString) throws EDITSecurityException
    {
        //  Create an old Password object
        Password oldPassword = this.getCurrentPassword();

        //  Create a new Password object
        Password newPassword = new Password();
        newPassword.setPassword(newPasswordString);  // encrypts
        newPassword.setStatus(Password.STATUS_ACTIVE);
        newPassword.setOperator(this);

        //  Validate the new password against the mask
        PasswordMask passwordMask = SecurityProfile.getSingleton().getPasswordMask();
        passwordMask.validate(newPassword, newPasswordString);

        //  Set old password to INACTIVE
        oldPassword.setStatus(Password.STATUS_INACTIVE);

        //  Update the old password, save the new
        oldPassword.hSave();
        newPassword.hSave();

        //  Maintain password history
        updatePasswordHistory(passwordMask);

        return true;
    }
    
    /**
     * Returns all active operators.
     * active = either termination date is not set i.e. null or termination date is later than current date.
     * @return
     */
    public static Operator[] getActiveOperators()
    {
        return findByTerminationDateGreaterThan(new EDITDate());
    }

    public static Operator findByOperatorNameAndEncryptedPassword(String operatorName, String encryptedPassword)
    {
        Operator operator = null;

        String hql = "select operator from Operator operator " +
                "join operator.Passwords password " +
                "where operator.Name = :operatorName " +
                "and password.EncryptedPassword = :encryptedPassword";

        Map params = new HashMap();
        params.put("operatorName", operatorName);
        params.put("encryptedPassword", encryptedPassword);

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.SECURITY);

        if (!results.isEmpty())
        {
            operator = (Operator) results.get(0);
        }

        return operator;
    }

    public static Operator[] findByOperatorType(String operatorType)
    {
        String hql = "select operator from Operator operator " +
                "where operator.OperatorTypeCT = :operatorType ";

        Map params = new HashMap();
        params.put("operatorType", operatorType);

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.SECURITY);

        return (Operator[]) results.toArray(new Operator[results.size()]);
    }

    public static Operator findByOperatorName(String operatorName)
    {
        Operator operator = null;

        String hql = "select operator from Operator operator " +
                " where operator.Name = :operatorName";

        Map params = new HashMap();
        params.put("operatorName", operatorName);

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.SECURITY);

        if (!results.isEmpty())
        {
            operator = (Operator) results.get(0);
        }

        return operator;
    }

   /**
     * Finder.
     * @param operatorPK
     * @return
     */
    public static Operator findByPK(Long operatorPK)
    {
        return (Operator) SessionHelper.get(Operator.class, operatorPK, SessionHelper.SECURITY);
    }

    public static Operator[] findAll()
    {
        String hql = "select operator from Operator operator";

        Map params = new HashMap();

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.SECURITY);

        return (Operator[]) results.toArray(new Operator[results.size()]);
    }

    public static Operator[] findByRolePK(Role role)
    {
        Operator[] operators = null;

        String hql = "select operator from Operator operator " +
                " join operator.OperatorRoles operatorRoles " +
                " where operatorRoles.Role = :role";

        Map params = new HashMap();
        params.put("role", role);

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.SECURITY);

        if (!results.isEmpty())
        {
            operators = (Operator[]) results.toArray(new Operator[results.size()]);
        }

        return operators;
    }

    /**
     * Returns all operators whose termination date is null or later than given date.
     * @param terminationDate
     * @return
     */
    public static Operator[] findByTerminationDateGreaterThan(EDITDate terminationDate)
    {
        String hql = " select operator" +
                     " from Operator operator " +
                     " where operator.TerminationDate is null" +
                     " or operator.TerminationDate >= :terminationDate";
                     
        Map params = new HashMap();
        params.put("terminationDate", terminationDate);
        
        List results = SessionHelper.executeHQL(hql, params, SessionHelper.SECURITY);
        
        return (Operator[]) results.toArray(new Operator[results.size()]);
    }

    /**
     * sramamurthy 08/19/2004 the password reset funtionality for existing users
     *
     * @throws EDITSecurityException
     */
    public void passwordReset() throws EDITSecurityException
    {
        String passwordString = Password.generateRandomPassword();
        Password password = new Password();
        password.setPassword(passwordString);
        password.setStatus(Password.STATUS_TEMPORARY);

        this.setLockedIndicator("N");
        this.setUnencryptedTemporaryPassword(passwordString);

        this.hSave();

        Password[] oldPasswords = Password.findByOperator(this);

        if (oldPasswords != null)
        {
            for (int i = 0; i < oldPasswords.length; i++)
            {
                oldPasswords[i].hDelete();
            }
        }

        password.setOperator(this);
        password.hSave();
    }

    private void updatePasswordHistory(PasswordMask passwordMask) throws EDITSecurityException
    {
        if (passwordMask.getRestrictRepeats().equalsIgnoreCase("Y"))
        {
            //  Get all the passwords for a given operator
            Password[] passwords = Password.findByOperatorName(this.getName());

            if (passwords != null)
            {
                if ((passwords.length > passwordMask.getNumberOfRepeatCycles()))
                {
                    //  There are more passwords than are allowed for repeat cycles (we only maintain a history for the number of cycles)

                    int numberToDelete = passwords.length - passwordMask.getNumberOfRepeatCycles();

                    Password[] sortedPasswords = (Password[]) Util.sortObjects(passwords, new String[]{"getOrderOfCreation"});

                    //  Delete the oldest ones
                    for (int i = 0; i < numberToDelete; i++)
                    {
                        Password oldestPassword = sortedPasswords[i];
                        oldestPassword.hDelete();
                    }

                    //  Reorder the creation order for the remaining ones
                    for (int i = numberToDelete; i < passwords.length; i++)
                    {
                        passwords[i].setOrderOfCreation(passwords[i].getOrderOfCreation() - numberToDelete);
                        Password password = passwords[i];
                        password.hSave();
                    }
                }
            }
        }
    }

    public boolean checkViewAllAuthorization(Long securityProductStructurePK, String viewAllType)
    {
        boolean viewAll = false;

        Set operatorRoles = this.getOperatorRoles();

        if (!operatorRoles.isEmpty())
        {
            Iterator it = operatorRoles.iterator();
            while (it.hasNext())
            {
                OperatorRole operatorRole = (OperatorRole) it.next();

                FilteredRole filteredRole = FilteredRole.findByRoleAndProductStructure(operatorRole.getRoleFK(), securityProductStructurePK);
                Set securedMethods = filteredRole.getSecuredMethods();

                if (!securedMethods.isEmpty())
                {
                    Iterator it2 = securedMethods.iterator();
                    while (it2.hasNext())
                    {
                        SecuredMethod securedMethod = (SecuredMethod) it2.next();

                        String methodName = securedMethod.getComponentMethod().getMethodName();
                        if (viewAllType.equalsIgnoreCase("Clients") &&
                            methodName.equalsIgnoreCase(ComponentMethod.VIEW_ALL_CLIENTS))
                        {
                            viewAll = true;
                        }
                        else if (viewAllType.equalsIgnoreCase("Agents") &&
                                 methodName.equalsIgnoreCase(ComponentMethod.VIEW_ALL_AGENTS))
                        {
                            viewAll = true;
                        }
                        else if (viewAllType.equalsIgnoreCase("Suspense") &&
                                 methodName.equalsIgnoreCase(ComponentMethod.VIEW_ALL_SUSPENSE))
                        {
                            viewAll = true;
                        }
                    }
                }
            }
        }

        return viewAll;
    }
}
