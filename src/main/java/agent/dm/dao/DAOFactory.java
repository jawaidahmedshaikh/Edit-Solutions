package agent.dm.dao;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jun 25, 2003
 * Time: 8:41:40 AM
 * To change this template use Options | File Templates.
 */
public class DAOFactory
{
    private static AgentDAO agentDAO;
    private static PlacedAgentDAO placedAgentDAO;
    private static CommissionProfileDAO commissionProfileDAO;
    private static AgentContractDAO agentContractDAO;
    private static AdditionalCompensationDAO additionalCompensationDAO;
    private static AgentLicenseDAO agentLicenseDAO;
    private static RedirectDAO redirectDAO;

    static
    {
        agentDAO = new AgentDAO();
        placedAgentDAO = new PlacedAgentDAO();
        commissionProfileDAO = new CommissionProfileDAO();
        agentContractDAO = new AgentContractDAO();
        additionalCompensationDAO = new AdditionalCompensationDAO();
        agentLicenseDAO = new AgentLicenseDAO();
        redirectDAO = new RedirectDAO();
    }

    public static AdditionalCompensationDAO getAdditionalCompensationDAO()
    {
        return additionalCompensationDAO;
    }

    public static AgentContractDAO getAgentContractDAO()
    {
        return agentContractDAO;
    }

    public static PlacedAgentDAO getPlacedAgentDAO()
    {
        return placedAgentDAO;
    }

    public static AgentDAO getAgentDAO()
    {
        return agentDAO;
    }

    public static CommissionProfileDAO getCommissionProfileDAO() {

        return commissionProfileDAO;
    }

    public static AgentLicenseDAO getAgentLicenseDAO() {

        return agentLicenseDAO;
    }

    public static RedirectDAO getRedirectDAO()
    {
        return redirectDAO;
    }
}
