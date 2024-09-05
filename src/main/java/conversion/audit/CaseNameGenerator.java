/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package conversion.audit;

import java.util.*;
import edit.services.config.ServicesConfig;
import edit.services.db.hibernate.SessionHelper;
import fission.utility.Util;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import org.hibernate.Session;

/**
 *
 * @author sprasad
 */
public class CaseNameGenerator extends ConversionAudit
{
    private static String FILE_NAME = "CaseNames";

    private static String FILE_EXTENSION = ".txt";

    private static final String header = "CaseNumber" + DEMARCATOR + "Name" + DEMARCATOR + "Status" + DEMARCATOR +
                                         "CaseType";

    private File file = null;

    public CaseNameGenerator()
    {
        file = new File(ServicesConfig.getEDITExport("ExportDirectory1").getDirectory() + FILE_NAME + FILE_EXTENSION);
    }

    @Override
    public void generate() throws Exception
    {
        String hql = " select caseContractGroup.ContractGroupNumber, caseClientDetail.CorporateName, caseContractGroup.CaseStatusCT," +
                     " caseContractGroup.CaseTypeCT" +
                     " from ContractGroup caseContractGroup" +
                     " join caseContractGroup.ClientRole caseClientRole" +
                     " join caseClientRole.ClientDetail caseClientDetail" +
                     " where caseContractGroup.ContractGroupTypeCT = :contractGroupTypeCT" +
                     " and caseClientRole.RoleTypeCT = :roleTypeCT" +
                     " order by caseContractGroup.ContractGroupNumber";

        Map params = new HashMap();

        params.put("contractGroupTypeCT", "Case");
        params.put("roleTypeCT", "Case");

        Session session = null;

        int count = 0;

        try
        {
            session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

            List results = SessionHelper.executeHQL(session, hql, params, -1);

            FileWriter fw = new FileWriter(file);

            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(header);

            bw.newLine();

            for (int i = 0; i < results.size(); i++)
            {
                Object[] columnValues = (Object[]) results.get(i);

                String caseNumber = Util.initString((String) columnValues[0], "");

                String name = Util.initString((String) columnValues[1], "");

                String caseStatus = Util.initString((String) columnValues[2], "");

                String caseType = Util.initString((String) columnValues[3], "");

                String aLine = caseNumber + DEMARCATOR + name + DEMARCATOR + caseStatus + DEMARCATOR +
                               caseType;

                bw.write(aLine);

                bw.newLine();

                count += 1;
            }

            bw.write("TOTAL: " + count);

            bw.close();

            fw.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (session != null) session.close();
        }
    }
}