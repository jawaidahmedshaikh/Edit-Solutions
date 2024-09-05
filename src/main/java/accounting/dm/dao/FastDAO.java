package accounting.dm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edit.services.db.ConnectionFactory;
import edit.services.db.DBTable;

/**
 * Created by IntelliJ IDEA.
 * User: sprasad
 * Date: Apr 21, 2008
 * Time: 5:05:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class FastDAO
{
    public static final String POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;

    private final DBTable ACCOUNT_DBTABLE;
    private final String ACCOUNT_TABLENAME;

    private final DBTable CONTRACTGROUP_DBTABLE;
    private final DBTable CLIENTROLE_DBTABLE;
    private final DBTable CLIENTDETAIL_DBTABLE;

    private final String CONTRACTGROUP_TABLENAME;
    private final String CLIENTROLE_TABLENAME;
    private final String CLIENTDETAIL_TABLENAME;

//    select cg.contractgroupnumber, cd.corporatename
//    from contractgroup cg, clientrole cr, clientdetail cd
//    where cg.clientrolefk=cr.clientrolepk
//    and cr.roletypect='group'
//    and cr.clientdetailfk=cd.clientdetailpk

    public FastDAO()
    {
        ACCOUNT_DBTABLE = DBTable.getDBTableForTable("AccountingDetail");
        ACCOUNT_TABLENAME = ACCOUNT_DBTABLE.getFullyQualifiedTableName();

        CONTRACTGROUP_DBTABLE = DBTable.getDBTableForTable("ContractGroup");
        CLIENTROLE_DBTABLE = DBTable.getDBTableForTable("ClientRole");
        CLIENTDETAIL_DBTABLE = DBTable.getDBTableForTable("ClientDetail");

        CONTRACTGROUP_TABLENAME = CONTRACTGROUP_DBTABLE.getFullyQualifiedTableName();
        CLIENTROLE_TABLENAME = CLIENTROLE_DBTABLE.getFullyQualifiedTableName();
        CLIENTDETAIL_TABLENAME = CLIENTDETAIL_DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Finds All Account Numbers and Account Names separated by '-' (hyphen).
     * AccountNumber - AccountName Ex: 11112222 - General Account
     * This method is used to display Account Numer - Acccount Name combination on Journal Adjustment page.
     * @return
     */
    public Map<String, ArrayList<String>> findAllAccountNumbersWithAccountNames()
    {
        String accountNumberCol = ACCOUNT_DBTABLE.getDBColumn("AccountNumber").getFullyQualifiedColumnName();
        String accountNameCol = ACCOUNT_DBTABLE.getDBColumn("AccountName").getFullyQualifiedColumnName();
        String companyNameCol = ACCOUNT_DBTABLE.getDBColumn("CompanyName").getFullyQualifiedColumnName();

        Map<String, ArrayList<String>> companiesWithAccountNumbersAndNames = new HashMap<String, ArrayList<String>>();

        String sql = " SELECT DISTINCT " + companyNameCol + ", " + accountNumberCol + ", " + accountNameCol +
                     " FROM " + ACCOUNT_TABLENAME +
                     " ORDER BY " + companyNameCol + ", " + accountNumberCol + ", " + accountNameCol;

        Connection conn = null;

        PreparedStatement ps = null;

        ResultSet rs = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next())
            {
            	String accountNameNumCombo = rs.getString("AccountNumber") + " - " + rs.getString("AccountName").replace("'", "\\'"); 
            	String companyName = rs.getString("CompanyName");
            	
            	if (!companiesWithAccountNumbersAndNames.containsKey(companyName))
            	{
            		ArrayList<String> companyNameSet = new ArrayList<>();
            		companyNameSet.add(accountNameNumCombo);
            		companiesWithAccountNumbersAndNames.put(companyName, companyNameSet);
            	}
            	else
            	{
            		companiesWithAccountNumbersAndNames.get(companyName).add(accountNameNumCombo);
            	}
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();

                if (ps != null) ps.close();

                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return companiesWithAccountNumbersAndNames;
    }
    
    /**
     * Finds All Group Numbers and Group Names and return them as a map.
     * This method is used to display Group Number and Group Name combination on Journal Adjustment page.
     * @return
     */
    public HashMap<String, String> findAllGroupNumbersWithNames()
    {
        String contractGroupNumber = CONTRACTGROUP_DBTABLE.getDBColumn("ContractGroupNumber").getFullyQualifiedColumnName();
        String clientRoleFK = CONTRACTGROUP_DBTABLE.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();
        String corporateName = CLIENTDETAIL_DBTABLE.getDBColumn("CorporateName").getFullyQualifiedColumnName();
        String clientDetailPK = CLIENTDETAIL_DBTABLE.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();
        String clientRolePK = CLIENTROLE_DBTABLE.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String roleTypeCT = CLIENTROLE_DBTABLE.getDBColumn("RoleTypeCT").getFullyQualifiedColumnName();
        String clientDetailFK = CLIENTROLE_DBTABLE.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();

        HashMap<String, String> groupNumbersAndNames = new HashMap<>();

        String sql = "   SELECT DISTINCT " + contractGroupNumber + ", " + corporateName + 
                     "     FROM " + CONTRACTGROUP_TABLENAME + ", " + CLIENTROLE_TABLENAME + ", " + CLIENTDETAIL_TABLENAME +
                     "    WHERE " + clientRoleFK + " = " + clientRolePK +
                     "      AND " + clientDetailFK + " = " + clientDetailPK +
                     "      AND " + roleTypeCT + " = 'group'" +
                     " ORDER BY " + contractGroupNumber + ", " + corporateName;

        Connection conn = null;

        PreparedStatement ps = null;

        ResultSet rs = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next())
            {
            	String groupNum = rs.getString("ContractGroupNumber");
            	String companyName = rs.getString("CorporateName").replaceAll("[^a-zA-Z0-9\\s+]", "");
        		groupNumbersAndNames.put(groupNum, companyName);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();

                if (ps != null) ps.close();

                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return groupNumbersAndNames;
    }
}
