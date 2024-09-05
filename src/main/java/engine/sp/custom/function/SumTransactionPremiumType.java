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

import edit.common.EDITDate;
import edit.common.EDITMap;

import edit.services.db.hibernate.SessionHelper;

import engine.common.Constants;

import engine.sp.ScriptProcessor;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

/**
 * Scripts invoke functions with commands such as the following:
 * 
 * activatefunction (FooFunction).
 * 
 * The 'FooFunction' actually defines the name of a class which will
 * execute the requested function. SumTransactionPremiumType is such a class amongst
 * many such classes designed to execute a specified function.
 * 
 * The function class sums (either) FinancialHistory.GrossAmount or
 * FinancialHistory.NetAmount.
 */
public class SumTransactionPremiumType implements FunctionCommand
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
     * The GroupeSetup.PremiumTypeCT
     */
    public static final String PARAM_PREMIUMTYPE = "PremiumType";

    /**
     * Constructor.
     * @param scriptProcessor ScriptProcessor
     */
    public SumTransactionPremiumType(ScriptProcessor scriptProcessor)
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
     * 6. PremiumType
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
      
      String premiumType = getWSValue(PARAM_PREMIUMTYPE);
    
      String hql = " select sum(" + targetFieldName + ")" +
                  " from FinancialHistory financialHistory" +
                  " join financialHistory.EDITTrxHistory editTrxHistory" +
                  " join editTrxHistory.EDITTrx editTrx" +
                  " join editTrx.ClientSetup clientSetup" +
                  " join clientSetup.ContractSetup contractSetup" +
                  " join contractSetup.GroupSetup groupSetup" + 
                  " where contractSetup.SegmentFK = :segmentPK" +
                  " and editTrx.TransactionTypeCT = :transactionTypeCT" +
                  " and editTrx.EffectiveDate >= :fromDate" +
                  " and editTrx.EffectiveDate <= :toDate" +
                  " and groupSetup.PremiumTypeCT = :premiumType" +
                  " and editTrx.Status in ('N', 'A')";
                  
      Map params = new EDITMap("segmentPK", segmentPK)
                      .put("transactionTypeCT", transactionTypeCT)
                      .put("fromDate", fromDate)
                      .put("toDate", toDate)
                      .put("premiumType", premiumType);
                  
      Session session = null;                
                  
      try
      {
        session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);
      
        List results = SessionHelper.executeHQL(session, hql, params, 0);
        
        if (!results.isEmpty())
        {
          summedAmount = results.get(0);
          
          if (summedAmount == null)
          {
            summedAmount = Constants.ScriptKeyword.NULL;
          }
        }
      }
      finally
      {
        if (session != null) session.close();      
      }

      getScriptProcessor().push(summedAmount.toString());
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
        targetFieldName = "financialHistory.GrossAmount";
      }
      else if (grossNet.equalsIgnoreCase(PARAM_NET))
      {
        targetFieldName = "financialHistory.NetAmount";
      }
      
      return targetFieldName;
    }
}