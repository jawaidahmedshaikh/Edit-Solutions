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
public class PremiumDetailGenerator extends ConversionAudit
{
    private static String FILE_NAME = "PremiumDetail";

    private static String FILE_EXTENSION = ".txt";

    private static final String FILE_HEADER = "ContractNumber" + DEMARCATOR + "TransactionType" + DEMARCATOR + "EffectiveDate" + DEMARCATOR +
                                              "TransactionAmount" + DEMARCATOR + "DeductionAmount";

    private File file = null;

    public PremiumDetailGenerator()
    {
        file = new File(ServicesConfig.getEDITExport("ExportDirectory1").getDirectory() + FILE_NAME + FILE_EXTENSION);
    }

    @Override
    public void generate() throws Exception
    {
        String hql = " select segment.ContractNumber, editTrx.TransactionTypeCT, editTrx.EffectiveDate, financialHistory.GrossAmount, " +
                     " premiumDue.DeductionAmount" +
                     " from Segment segment" +
                     " join segment.ContractSetups contractSetup" +
                     " join contractSetup.ClientSetups clientSetup" +
                     " join clientSetup.EDITTrxs editTrx" +
                     " join editTrx.EDITTrxHistories editTrxHistory" +
                     " join editTrxHistory.FinancialHistories financialHistory" +
                     " left join editTrx.PremiumDues premiumDue" +
                     " where editTrx.TransactionTypeCT = :transactionTypeCT" +
                     " order by segment.ContractNumber, editTrx.EffectiveDate";

        Map params = new HashMap();

        params.put("transactionTypeCT", "PY");

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

                String transactionType = Util.initString((String) columnValues[1], "");

                EDITDate effectiveDate = (EDITDate) columnValues[2];

                EDITBigDecimal grossAmount = (EDITBigDecimal) columnValues[3];

                EDITBigDecimal deductionAmount = (EDITBigDecimal) columnValues[4];

                String aLine = contractNumber + DEMARCATOR + transactionType + DEMARCATOR + effectiveDate.getFormattedDate() + DEMARCATOR +
                               grossAmount.trim() + DEMARCATOR + deductionAmount.trim();

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
