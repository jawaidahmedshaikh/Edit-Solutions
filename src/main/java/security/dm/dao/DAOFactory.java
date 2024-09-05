/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Dec 31, 2002
 * Time: 4:03:25 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package security.dm.dao;

import java.io.Serializable;

public class DAOFactory  implements Serializable{

//*******************************
//          Variables
//*******************************

    private static OperatorDAO operatorDAO;
    private static RoleDAO roleDAO;
    private static PasswordDAO passwordDAO;
    private static OperatorRoleDAO operatorRoleDAO;
    private static SecurityProfileDAO securityProfileDAO;
    private static MaskDAO maskDAO;
    private static PasswordMaskDAO passwordMaskDAO;
    private static ComponentMethodDAO componentMethodDAO;
    private static SecuredMethodDAO accessibleMethodDAO;
    private static ImpliedRoleDAO impliedRoleDAO;
    private static SecurityLogDAO securityLogDAO;
    private static FilteredRoleDAO filteredRoleDAO;


    static {

        operatorDAO = new OperatorDAO();
        roleDAO = new RoleDAO();
        passwordDAO = new PasswordDAO();
        operatorRoleDAO = new OperatorRoleDAO();
        securityProfileDAO = new SecurityProfileDAO();
        maskDAO = new MaskDAO();
        passwordMaskDAO = new PasswordMaskDAO();
        componentMethodDAO = new ComponentMethodDAO();
        accessibleMethodDAO = new SecuredMethodDAO();
        impliedRoleDAO = new ImpliedRoleDAO();
        securityLogDAO = new SecurityLogDAO();
        filteredRoleDAO = new FilteredRoleDAO();
    }

//*******************************
//          Public Methods
//*******************************

	/**
	 * Factory Method
	 *
	 */

    public static SecurityLogDAO getSecurityLogDAO()
    {
        return securityLogDAO;
    }

    public static ImpliedRoleDAO getImpliedRoleDAO()
    {
        return impliedRoleDAO;
    }

    public static SecuredMethodDAO getSecuredMethodDAO()
    {
        return accessibleMethodDAO;
    }

    public static ComponentMethodDAO getComponentMethodDAO()
    {
        return componentMethodDAO;
    }

	public static OperatorDAO getOperatorDAO() {

		return operatorDAO;
	}

    public static PasswordDAO getPasswordDAO(){

        return passwordDAO;
    }

    public static RoleDAO getRoleDAO(){

        return roleDAO;
    }

    public static OperatorRoleDAO getOperatorRoleDAO(){

        return operatorRoleDAO;
    }


    public static SecurityProfileDAO getSecurityProfileDAO()
    {
        return securityProfileDAO;
    }

    public static MaskDAO getMaskDAO()
    {
        return maskDAO;
    }

    public static PasswordMaskDAO getPasswordMaskDAO()
    {
        return passwordMaskDAO;
    }

    public static FilteredRoleDAO getFilteredRoleDAO()
    {
        return filteredRoleDAO;
    }
}
