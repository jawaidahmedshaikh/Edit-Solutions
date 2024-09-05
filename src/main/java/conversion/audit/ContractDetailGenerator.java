/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conversion.audit;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.services.config.ServicesConfig;
import edit.services.db.hibernate.SessionHelper;
import fission.utility.Util;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;

/**
 *
 * @author sprasad
 */
public class ContractDetailGenerator extends ConversionAudit
{
    private static String FILE_NAME = "ContractDetail";

    private static String FILE_EXTENSION = ".txt";

    private static final String FILE_HEADER = "ContractNumber" + DEMARCATOR + "Status" + DEMARCATOR + "CaseNumber" + DEMARCATOR +
                                              "GroupNumber" + DEMARCATOR + "DomicileState" + DEMARCATOR + "Units" + DEMARCATOR +
                                              "PaidToDate" + DEMARCATOR + "BucketSource" + DEMARCATOR + "UnearnedInterest" + DEMARCATOR +
                                              "BillMethod";

    private File file = null;

    public ContractDetailGenerator()
    {
        file = new File(ServicesConfig.getEDITExport("ExportDirectory1").getDirectory() + FILE_NAME + FILE_EXTENSION);
    }

    @Override
    public void generate() throws Exception
    {
        String hql = " select segment.ContractNumber, segment.SegmentStatusCT, caseContractGroup.ContractGroupNumber," +
                     " groupContractGroup.ContractGroupNumber, caseContractGroup.DomicileStateCT, segment.Units," +
                     " life.PaidToDate, bucket.BucketSourceCT, bucket.UnearnedInterest, billSchedule.BillMethodCT" +
                     " from Segment segment" +
                     " left join segment.Investments investment" +
                     " left join investment.Buckets bucket" +
                     " left join segment.Lifes life" +
                     " left join segment.ContractGroup groupContractGroup" +
                     " left join groupContractGroup.ContractGroup caseContractGroup" +
                     " left join groupContractGroup.BillSchedule billSchedule" +
                     " where segment.SegmentFK is null";

        Map params = new HashMap();

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

                String contractNumber = Util.initString((String) columnValues[0], "");

                String status = Util.initString((String) columnValues[1], "");

                String caseNumber = Util.initString((String) columnValues[2], "");

                String groupNumber = Util.initString((String) columnValues[3], "");

                String domicileState = Util.initString((String) columnValues[4], "");

                EDITBigDecimal units = (EDITBigDecimal) columnValues[5];

                EDITDate paidToDate = (EDITDate) columnValues[6];

                String bucketSource = Util.initString((String) columnValues[7], "");

                EDITBigDecimal unearnedInterest = (EDITBigDecimal) columnValues[8];

                String billMethod = Util.initString((String) columnValues[9], "");

                String aLine = contractNumber + DEMARCATOR + status + DEMARCATOR + caseNumber + DEMARCATOR +
                               groupNumber + DEMARCATOR + domicileState + DEMARCATOR + units.trim() + DEMARCATOR +
                               (paidToDate == null ? "" : paidToDate.getFormattedDate()) + DEMARCATOR + bucketSource + DEMARCATOR + unearnedInterest.trim() + DEMARCATOR +
                               billMethod;

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
