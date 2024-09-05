/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package conversion.audit;

import edit.common.EDITDate;
import java.util.*;
import edit.services.config.ServicesConfig;
import edit.services.db.hibernate.SessionHelper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import org.hibernate.Session;

/**
 *
 * @author sprasad
 */
public class TotalBilledAmountByCaseGroupDueDateGenerator extends ConversionAudit
{
    private static String FILE_NAME = "TotalBilledAmountByCaseGroupDueDate";

    private static String FILE_EXTENSION = ".txt";

    private static final String FILE_HEADER = "CaseNumber" + DEMARCATOR + "GroupNumber" + DEMARCATOR + "DueDate" + DEMARCATOR +
                                              "TotalBilledAmount";

    private File file = null;

    public TotalBilledAmountByCaseGroupDueDateGenerator()
    {
        file = new File(ServicesConfig.getEDITExport("ExportDirectory1").getDirectory() + FILE_NAME + FILE_EXTENSION);
    }

    @Override
    public void generate() throws Exception
    {
        String hql = " select caseContractGroup.ContractGroupNumber, groupContractGroup.ContractGroupNumber," +
                     " billGroup.DueDate, sum(billGroup.TotalBilledAmount)" +
                     " from ContractGroup caseContractGroup" +
                     " join caseContractGroup.ContractGroups groupContractGroup" +
                     " join groupContractGroup.BillSchedule billSchedule" +
                     " join billSchedule.BillGroups billGroup" +
                     " group by caseContractGroup.ContractGroupNumber, groupContractGroup.ContractGroupNumber," +
                     " billGroup.DueDate";

        Session session = null;

        int count = 0;

        try
        {
            session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

            List results = SessionHelper.executeHQL(session, hql, null, -1);

            FileWriter fw = new FileWriter(file);

            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(FILE_HEADER);

            bw.newLine();

            for (int i = 0; i < results.size(); i++)
            {
                Object[] columnValues = (Object[]) results.get(i);

                String caseNumber = (String) columnValues[0];

                String groupNumber = (String) columnValues[1];

                EDITDate dueDate = (EDITDate) columnValues[2];

                Double totalBilledAmount = (Double) columnValues[3];

                String aLine = caseNumber + DEMARCATOR + groupNumber + DEMARCATOR + dueDate.getFormattedDate() + DEMARCATOR +
                               new DecimalFormat("0.##").format(totalBilledAmount);

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