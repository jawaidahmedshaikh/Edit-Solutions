/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package conversion.audit;

import java.util.*;
import edit.common.EDITDate;
import edit.services.config.ServicesConfig;
import edit.services.db.hibernate.SessionHelper;
import engine.ProductStructure;
import fission.utility.Util;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import org.hibernate.Session;

/**
 *
 * @author sprasad
 */
public class ActiveCaseCountGenerator extends ConversionAudit
{
    private static String FILE_NAME = "ActiveCaseCount";

    private static String FILE_EXTENSION = ".txt";

    private static final String FILE_HEADER = "BusinessName" + DEMARCATOR + "ActiveCaseCount";

    private File file = null;

    public ActiveCaseCountGenerator()
    {
        file = new File(ServicesConfig.getEDITExport("ExportDirectory1").getDirectory() + FILE_NAME + FILE_EXTENSION);
    }

    @Override
    public void generate() throws Exception
    {
        String hql = " select filteredProduct.ProductStructureFK, count(filteredProduct.ProductStructureFK)" +
                     " from ContractGroup caseContractGroup" +
                     " join caseContractGroup.FilteredProducts filteredProduct" +
                     " where caseContractGroup.ContractGroupTypeCT = :contractGroupTypeCT" +
                     " and caseContractGroup.TerminationDate = :terminationDate" +
                     " group by filteredProduct.ProductStructureFK";

        Map params = new HashMap();

        params.put("contractGroupTypeCT", "Case");
        params.put("terminationDate", new EDITDate(EDITDate.DEFAULT_MAX_DATE));

        Session session = null;

        int count = 0;

        try
        {
            session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

            List results = SessionHelper.executeHQL(session, hql, params, -1);

            FileWriter fw = new FileWriter(file);

            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(FILE_HEADER);

            bw.newLine();

            for (int i = 0; i < results.size(); i++)
            {
                Object[] columnValues = (Object[]) results.get(i);

                Long productStructureFK = (Long) columnValues[0];

                ProductStructure productStructure = ProductStructure.findByPK(productStructureFK);

                String businessContractName = Util.initString(productStructure.getBusinessContractName(), "");

                Long activeCaseCount = (Long) columnValues[1];

                String aLine = businessContractName + DEMARCATOR + activeCaseCount;

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