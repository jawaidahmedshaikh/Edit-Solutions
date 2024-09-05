/*
 * User: sprasad
 * Date: Mar 14, 2007
 * Time: 11:34:00 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine.sp.custom.function;

import edit.common.*;

import edit.services.db.hibernate.SessionHelper;

import engine.common.Constants;

import engine.sp.ScriptProcessor;

import java.util.*;

import org.hibernate.Session;
import event.*;

/**
 * Scripts invoke functions with commands such as the following:
 * 
 * activatefunction (FooFunction).
 * 
 * The 'FooFunction' actually defines the name of a class which will
 * execute the requested function. SumTransactionBackdatedFullRemoval is such a class amongst
 * many such classes designed to execute a specified function.
 * 
 * The function class sums (either) FinancialHistory.GrossAmount or
 * FinancialHistory.NetAmount.
 */
public class SumTransactionBackdatedFullRemoval implements FunctionCommand
{
    private ScriptProcessor scriptProcessor;
    
    /**
     * The target transaction type; e.g. 'PY'.
     */
    public static final String PARAM_TRANSACTIONTYPECT = "TransactionTypeCT";
    
    /**
     * The starting date of the summation.
     */
    public static final String PARAM_FROMDATE = "FromDate";
    
    /**
     * Ending date for the summation.
     */
    public static final String PARAM_TODATE = "ToDate";
    
    /**
     * The identifier for whether to target the FinancialHistory.GrossAmount or the
     * FinancialHistory.NetAmount field.
     * @see #PARAM_GROSS
     * @see #PARAM_NET
     */
    public static final String PARAM_GROSSNET = "GrossNet";

    /**
     * The ReversalReason for the summation.
     */
    public static final String PARAM_REVERSAL_REASON = "ReversalReason";

    /**
     * Define which status value is to be used in the summation.
     */
    public static final String PARAM_TRANSACTIONS_STATUS = "TransactionStatus";

    /**
     * One of two parameter values for the "GrossNet" parameter names.
     */
    public static final String PARAM_GROSS = "Gross";
    
    /**
     * One of two parameter values for the "GrossNet" parameter names.
     */
    public static final String PARAM_NET = "Net";
    
    /**
     * The driving SegmentPK.
     */
    public static final String PARAM_SEGMENTPK = "SegmentPK";
    


    /**
     * Constructor.
     * @param scriptProcessor ScriptProcessor
     */
    public SumTransactionBackdatedFullRemoval(ScriptProcessor scriptProcessor)
    {
      this.scriptProcessor = scriptProcessor;
    }
    
    /**
     * Getter.
     * @return
     */
    private ScriptProcessor getScriptProcessor()
    {
        return this.scriptProcessor;
    }
    
    /**
     * Getter.
     * @return
     */
    private Map<String, String> getWorkingStorage()
    {
      return getScriptProcessor().getWS();
    }
    
    /**
     * Return the working storage value for the specified parameter name.
     * @param paramName
     * @return
     */
    private String getWSValue(String paramName)
    {
      return getWorkingStorage().get(paramName); 
    }

    /**
     * Sums (either) FinancialHistory.GrossAmount or FinancialHistory.NetAmount depending
     * on the specified WS field of "GrossNet". Either way, the summation rule is the same, 
     * and is as follows:
     * 
     * The WS will contain the following paramter entries:
     * 
     * 1. TransactionTypeCT
     * 2. FromDate
     * 3. ToDate
     * 4. GrossNet (to identify whether FinancialHistory.GrossAmount or FinancialHistory.NetAmount should be summed)
     * 5. SegmentPK
     * 6. ReversalReason
     * 7. TransactionStatus
     * 
     * Should there be no result (i.e. there are no FinancialHistory records to date), then #NULL is
     * returned instead.
     * 
     * @return the result as a String, or #NULL if there are no records of interest
     */
    public void exec()
    {
        Object summedAmount = null;

        String transactionTypeCT = getWSValue(PARAM_TRANSACTIONTYPECT);

        EDITDate fromDate = new EDITDate(getWSValue(PARAM_FROMDATE));

        EDITDate toDate = new EDITDate(getWSValue(PARAM_TODATE));

        String targetFieldName = getTargetFieldName();

        Long segmentPK = new Long(getWSValue(PARAM_SEGMENTPK));

        String reversalReason = getWSValue(PARAM_REVERSAL_REASON);

        String transactionStatus = getWSValue(PARAM_TRANSACTIONS_STATUS);

        FinancialHistory[] financialHistories = getCandidateFinacialHistories(segmentPK, transactionTypeCT, fromDate, toDate, reversalReason, transactionStatus);

        if (financialHistories != null)
        {
            summedAmount = accumForTargetType(targetFieldName, financialHistories);
        }

        if (summedAmount == null)
        {
            summedAmount = Constants.ScriptKeyword.NULL;
        }

        getScriptProcessor().push(summedAmount.toString());
    }

    private Object accumForTargetType(String targetFieldName, FinancialHistory[] financialHistories)
    {
        Object summedAmount = null;
        EDITBigDecimal amount = new EDITBigDecimal();

        for (int i = 0; i < financialHistories.length; i++)
        {
            FinancialHistory financialHistory = financialHistories[i];
            EDITTrx editTrx = financialHistory.getEDITTrxHistory().getEDITTrx();

            EDITTrx reapplyEditTrx = getReapplyTrx(editTrx.getEDITTrxPK());
            if (reapplyEditTrx != null)
            {
                if (targetFieldName.equalsIgnoreCase("Gross"))
                {
                    amount = amount.addEditBigDecimal(financialHistory.getGrossAmount());
                }
                else
                {
                    amount = amount.addEditBigDecimal(financialHistory.getNetAmount());
                }
            }
        }

        if (amount.isEQ("0"))
        {
            summedAmount = Constants.ScriptKeyword.NULL;
        }
        else
        {
            summedAmount = amount;
        }

        return summedAmount;
    }

    private FinancialHistory[] getCandidateFinacialHistories(Long segmentPK, String transactionTypeCT, EDITDate fromDate,
                                                             EDITDate toDate, String reversalReason, String transactionStatus)
    {
        FinancialHistory[]  financialHistories = null;

        String hql = " select financialHistory" +
                  " from FinancialHistory financialHistory" +
                  " left join fetch financialHistory.EDITTrxHistory editTrxHistory" +
                  " left join fetch editTrxHistory.EDITTrx editTrx" +
                  " left join editTrx.ClientSetup clientSetup" +
                  " left join clientSetup.ContractSetup contractSetup" +
                  " left join contractSetup.GroupSetup groupSetup" +
                  " where contractSetup.SegmentFK = :segmentPK" +
                  " and editTrx.TransactionTypeCT = :transactionTypeCT" +
                  " and editTrx.EffectiveDate >= :fromDate" +
                  " and editTrx.EffectiveDate <= :toDate" +
                  " and editTrx.ReversalReasonCodeCT = :reversalReason" +
                  " and editTrx.Status = :transactionStatus" +
                  " and ((editTrx.EDITTrxPK = editTrx.ReapplyEDITTrxFK" +
                  " and editTrx.PendingStatus != :pendingStatus) or editTrx.ReapplyEDITTrxFK = null)";

        Map params = new EDITMap("segmentPK", segmentPK)
                      .put("transactionTypeCT", transactionTypeCT)
                      .put("fromDate", fromDate)
                      .put("toDate", toDate)
                      .put("reversalReason", reversalReason)
                      .put("transactionStatus", transactionStatus)
                      .put("pendingStatus", "H");

        Session session = null;

        try
        {
          session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

          List results = SessionHelper.executeHQL(session, hql, params, 0);

          if (!results.isEmpty())
          {
             financialHistories = (FinancialHistory[])results.toArray(new FinancialHistory[results.size()]);
          }
        }
        finally
        {
          if (session != null) session.close();
        }

        return financialHistories;
    }

    private EDITTrx getReapplyTrx(Long editTrxPK)
    {
        EDITTrx reappplyEditTrx = null;

        String hql = "select editTrx from EDITTrx editTrx" +
                     " where editTrx.ReapplyEDITTrxFK = :reapplyEDITTrxFK" +
                     " and editTrx.PendingStatus != :pendingStatus";

        Map params = new HashMap();
        params.put("reapplyEDITTrxFK", editTrxPK);
        params.put("pendingStatus", "H");

        Session session = null;

        try
        {
          session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

          List results = SessionHelper.executeHQL(session, hql, params, 0);

          if (!results.isEmpty())
          {
             reappplyEditTrx = (EDITTrx)results.get(0);
          }
        }
        finally
        {
          if (session != null) session.close();
        }

        return reappplyEditTrx;
    }

    /**
     * Using the GrossNet WS parameter, determined whether the targeted field is
     * FinancialHistory.GrossAmount or FinancialHistory.NetAmount.
     * @return either 'financialHistory.GrossAmount' either 'financialHistory.NetAmount' 
     */
    private String getTargetFieldName()
    {
      String targetFieldName = null;
    
      String grossNet = getWSValue(PARAM_GROSSNET);
      
      if (grossNet.equalsIgnoreCase(PARAM_GROSS))
      {
    	  // targetFieldName = "financialHistory.GrossAmount";
    	  targetFieldName = "Gross";
      }
      else if (grossNet.equalsIgnoreCase(PARAM_NET))
      {
    	  // targetFieldName = "financialHistory.NetAmount";
    	  targetFieldName = "Net";
      }
      
      return targetFieldName;
    }
}
