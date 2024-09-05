/*
 * User: gfrosti
 * Date: Jan 9, 2004
 * Time: 11:01:16 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package security;

import edit.common.EDITDateTime;
import edit.common.exceptions.EDITSecurityException;
import edit.services.db.CRUDEntityImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import engine.*;


public class OperatorImpl extends CRUDEntityImpl
{
    protected void load(Operator operator, long operatorPK)
    {
//        super.load(operator, operatorPK, ConnectionFactory.SECURITY_POOL);
    }

    /**
     * Saves the operator after validating it against the Mask.  If the operator is new, generates a temporary password
     * and saves it.  The generated unencrypted temporary password is stored in the Operator for return to caller.
     * @param operator
     * @throws EDITSecurityException
     */
    protected void save(Operator operator) throws EDITSecurityException
    {
        Password password = null;
        String passwordString = null;

        if (operator.isNew()) // Don't allow duplicate usernames
        {
            Operator prevOperator = Operator.findByOperatorName(operator.getName());

            if (prevOperator != null)
            {
                throw new EDITSecurityException("Error - Duplicate Username");
            }

            //  Validate the username against the mask
            validateAgainstMask(operator);

            // Generate the necessary temporary passwords
            // NOTE: temporary passwords are not validated against the SecurityProfile masks
            if ((operator.getName().equals("admin")))
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

            operator.setUnencryptedTemporaryPassword(passwordString);
        }

//        OperatorVO operatorVO = (OperatorVO) operator.getVO();

        operator.setMaintDateTime(new EDITDateTime());
        operator.setMaskFK(SecurityProfile.getSingleton().getOperatorMask().getMaskPK());
        operator.hSave();
//        super.save(operator, ConnectionFactory.SECURITY_POOL, false);

        if (password != null)
        {
            password.setOperatorFK(operator.getOperatorPK());

            password.hSave();
        }
    }


    protected void delete(Operator operator) throws EDITSecurityException
    {
        OperatorRole[] operatorRole = OperatorRole.findByOperatorPK(operator.getOperatorPK());

        if (operatorRole != null)
        {
            throw new EDITSecurityException("Error - Operator is Attached to Role(s) and Can Not Be Deleted");
        }
        else
        {
            // Delete associated SecuritySession (if any)
            SecuritySession securitySession = SecuritySession.getSecuritySession(operator.getOperatorPK());

            if (securitySession != null)
            {
                SecuritySession.unregisterSecuritySession(securitySession.getSessionId());
            }

            // Delete associated Password(s) (if any)
            Password[] password = Password.findByOperator(operator);

            if (password != null)
            {
                for (int i = 0; i < password.length; i++)
                {
                    password[i].hDelete();
                }
            }
            operator.hDelete();
//            super.delete(operator, ConnectionFactory.SECURITY_POOL);
        }
    }

    public FilteredRole[] getFilteredRoles(Operator operator)
    {
        Role[] roles = this.getRoles(operator);

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
     * has a Filtered Role.  Includes implied roles too.
     * <p>
     * Convenience method.
     * @see ProductStructure#findAllProductTypeForOperator(Operator)
     *
     * @return
     */
    public ProductStructure[] getProductTypeProductStructures(Operator operator)
    {
         return ProductStructure.findAllProductTypeForOperator(operator);
    }


    protected Role[] getRoles(Operator operator)
    {
        List allRoles = new ArrayList(); // Unique Roles only

        Role[] directRoles = Role.findByOperatorPK(operator.getOperatorPK());

        if (directRoles != null)
        {
            for (int i = 0; i < directRoles.length; i++)
            {
                if (! allRoles.contains(directRoles[i]))
                {
                    directRoles[i].setRoleImplication(Role.DIRECTLY_IMPLIED);

                    allRoles.add(directRoles[i]);
                }

                Role[] impliedRoles = directRoles[i].getImpliedRoles();

                if (impliedRoles != null)
                {
                    for (int j = 0; j < impliedRoles.length; j++)
                    {
                        if (! allRoles.contains(impliedRoles[j]))
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

    protected Password getCurrentPassword(Operator operator)
    {
        Password password = Password.findByLatestOrderOfCreation(operator);

        return password;
    }

//    protected boolean updatePassword(Operator operator, String newPasswordString) throws EDITSecurityException
//    {
//        //  Create an old Password object
//        Password oldPassword = operator.getCurrentPassword();
//
//        //  Create a new Password object
//        Password newPassword = new Password();
//        newPassword.setPassword(newPasswordString);  // encrypts
//        newPassword.setStatus("ACTIVE");
//        newPassword.setOperatorFK(operator.getOperatorPK());
//
//        //  Validate the new password against the mask
//        PasswordMask passwordMask = SecurityProfile.getSingleton().getPasswordMask();
//        passwordMask.validate(newPassword, newPasswordString);
//
//        //  Set old password to INACTIVE
//        oldPassword.setStatus("INACTIVE");
//
//        //  Update the old password, save the new
//        oldPassword.hSave();
//        newPassword.hSave();
//
//        //  Maintain password history
//        updatePasswordHistory(operator, passwordMask);
//
//        return true;
//    }

//    protected static Operator findByOperatorNameAndPasswordAndPasswordStatus(String operatorName, String password)
//    {
//        Operator operator = null;
//
//        OperatorVO[] operatorVO = DAOFactory.getOperatorDAO().findByOperatorNameAndPassword(operatorName, password);
//
//        if (operatorVO != null)
//        {
//            operator = new Operator(operatorVO[0]);
//        }
//
//        return operator;
//    }

    protected static Operator findByOperatorNameAndEncryptedPassword(String operatorName, String encryptedPassword)
    {
//        Operator operator = null;
//
//        OperatorVO[] operatorVO = DAOFactory.getOperatorDAO().findByOperatorNameAndEncryptedPassword(operatorName, encryptedPassword);
//
//        if (operatorVO != null)
//        {
//            operator = new Operator(operatorVO[0]);
//        }
//
//        return operator;
    
        return Operator.findByOperatorNameAndEncryptedPassword(operatorName, encryptedPassword);
    }

//    protected static Operator findByOperatorName(String operatorName)
//    {
//        Operator operator = null;
//
//        OperatorVO[] operatorVO = DAOFactory.getOperatorDAO().findByOperatorName(operatorName);
//
//        if (operatorVO != null)
//        {
//            operator = new Operator(operatorVO[0]);
//        }
//
//        return operator;
//    }

//    protected static Operator[] findAll()
//    {
//        OperatorVO[] operatorVOs = DAOFactory.getOperatorDAO().findAll();
//
//        return Operator.mapVOToEntity(operatorVOs);
//    }

//    protected static Operator[] findByRolePK(long rolePK)
//    {
//        OperatorVO[] operatorVOs = DAOFactory.getOperatorDAO().findByRolePK(rolePK);
//
//        return Operator.mapVOToEntity(operatorVOs);
//    }

    //              Private methods

//    private void updatePasswordHistory(Operator operator, PasswordMask passwordMask) throws EDITSecurityException
//    {
////        OperatorVO operatorVO = (OperatorVO) operator.getVO();
////        PasswordMaskVO passwordMaskVO = (PasswordMaskVO) passwordMask.getVO();
//
//        if (passwordMask.getRestrictRepeats().equalsIgnoreCase("Y"))
//        {
//            //  Get all the passwords for a given operator
//            PasswordVO[] passwordVOs = DAOFactory.getPasswordDAO().findAllByOperatorName(operator.getName(), false, null);
//
//            if (passwordVOs != null)
//            {
//                if ((passwordVOs.length > passwordMask.getNumberOfRepeatCycles()))
//                {
//                    //  There are more passwords than are allowed for repeat cycles (we only maintain a history for the number of cycles)
//
//                    int numberToDelete = passwordVOs.length - passwordMask.getNumberOfRepeatCycles();
//
//                    PasswordVO[] sortedPasswordVOs = (PasswordVO[]) Util.sortObjects(passwordVOs, new String[]{"getOrderOfCreation"});
//
//                    //  Delete the oldest ones
//                    for (int i = 0; i < numberToDelete; i++)
//                    {
//                        Password oldestPassword = new Password(sortedPasswordVOs[i]);
//                        oldestPassword.hDelete();
//                    }
//
//                    //  Reorder the creation order for the remaining ones
//                    for (int i = numberToDelete; i < passwordVOs.length; i++)
//                    {
//                        passwordVOs[i].setOrderOfCreation(passwordVOs[i].getOrderOfCreation() - numberToDelete);
//                        Password password = new Password(passwordVOs[i]);
//                        password.hSave();
//                    }
//                }
//            }
//        }
//    }

    private void validateAgainstMask(Operator operator) throws EDITSecurityException
    {
        SecurityProfile securityProfile = SecurityProfile.getSingleton();

        Mask operatorMask = securityProfile.getOperatorMask();

//        OperatorVO operatorVO = (OperatorVO) operator.getVO();

        operatorMask.validate(operator.getName());
    }
   /**
    * sramamurthy 08/19/2004 the password reset funtionality for existing users
    * @param operator
    * @throws EDITSecurityException
    */
    protected void pwdReset(Operator operator) throws EDITSecurityException{
        Password password = null;
        String passwordString = null;

        passwordString = Password.generateRandomPassword();
        password = new Password();
        password.setPassword(passwordString);
        password.setStatus(Password.STATUS_TEMPORARY);
        operator.setUnencryptedTemporaryPassword(passwordString);

        Password[] oldPasswords = Password.findByOperator(operator);

        if (password != null)
        {
            for (int i = 0; i < oldPasswords.length; i++)
            {
                oldPasswords[i].hDelete();
            }
        }

        if( password != null){
            password.setOperatorFK(operator.getOperatorPK());
            password.hSave();
        }
    }
}


