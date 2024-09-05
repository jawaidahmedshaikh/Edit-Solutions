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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;

/**
 *
 * @author sprasad
 */
public class CommissionDetailGenerator extends ConversionAudit
{
    private static String FILE_NAME = "CommissionDetail";

    private static String FILE_EXTENSION = ".txt";

    private static final String FILE_HEADER = "ContractNumber" + DEMARCATOR + "EffectiveDate" + DEMARCATOR + "CommissionAmount" + DEMARCATOR +
                                              "DeductionAmount";

    private File file = null;

    public CommissionDetailGenerator()
    {
        file = new File(ServicesConfig.getEDITExport("ExportDirectory1").getDirectory() + FILE_NAME + FILE_EXTENSION);
    }

    @Override
    public void generate() throws Exception
    {
        String hql = " select segment.ContractNumber, editTrx.EffectiveDate, commissionHistory.CommissionAmount, premiumDue.DeductionAmount" +
                     " from Segment segment" +
                     " join segment.ContractSetups contractSetup" +
                     " join contractSetup.ClientSetups clientSetup" +
                     " join clientSetup.EDITTrxs editTrx" +
                     " join editTrx.EDITTrxHistories editTrxHistory" +
                     " join editTrxHistory.FinancialHistories financialHistory" +
                     " join editTrxHistory.CommissionHistories commissionHistory" +
                     " left join editTrx.PremiumDues premiumDue" +
                     " where editTrx.TransactionTypeCT = :transactionTypeCT" +
                     " and financialHistory.Duration = :duration" +
                     " and commissionHistory.CommissionTypeCT in (:commissionTypes)" +
                     " order by segment.ContractNumber, editTrx.EffectiveDate";

        Map params = new HashMap();

        params.put("transactionTypeCT", "PY");
        params.put("duration", new Integer(1));
        params.put("commissionTypes", new String[] {"FirstYear", "AdvanceRecovery", "Renewal"});

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

                EDITDate effectiveDate = (EDITDate) columnValues[1];

                EDITBigDecimal grossAmount = (EDITBigDecimal) columnValues[2];

                EDITBigDecimal deductionAmount = (EDITBigDecimal) columnValues[3];

                String aLine = contractNumber + DEMARCATOR + effectiveDate.getFormattedDate() + DEMARCATOR + grossAmount.trim() + DEMARCATOR +
                               deductionAmount.trim();

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
