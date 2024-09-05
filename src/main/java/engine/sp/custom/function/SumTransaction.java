package engine.sp.custom.function;

import edit.common.EDITDate;
import edit.common.EDITMap;

import edit.services.db.hibernate.SessionHelper;

import engine.common.Constants;

import engine.sp.ScriptProcessor;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.StatelessSession;
import event.*;


/**
 * Scripts invoke functions with commands such as the following:
 * 
 * activatefunction (FooFunction).
 * 
 * The 'FooFunction' actually defines the name of a class which will
 * execute the requested function. SumTransaction is such a class amongst
 * many such classes designed to execute a specified function.
 * 
 * The function class sums (either) FinancialHistory.GrossAmount or
 * FinancialHistory.NetAmount.
 */
public class SumTransaction implements FunctionCommand
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
   * One of the parameter values for the "GrossNet" parameter names.
   */
  public static final String PARAM_GROSS = "Gross";
  
  /**
   * One of the parameter values for the "GrossNet" parameter names.
   */
  public static final String PARAM_NET = "Net";
  
  /**
   * One of the parameter values for the "Accum" parameter names.
   */ 
  public static final String PARAM_ACCUM = "Accum";
  
  /**
   * One of the parameter values for the "Accum" parameter names.
   */ 
  public static final String PARAM_TAXABLE = "Taxable";
  
  /**
   * The driving SegmentPK.
   */
  public static final String PARAM_SEGMENTPK = "SegmentPK";

  /**
   * Contructor.
   * @param scriptProcessor ScriptProcessor
   */
  public SumTransaction(ScriptProcessor scriptProcessor)
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

      //sql also used for page display of inforce contract
    String hql = FinancialHistory.setSumTransactionHQL(targetFieldName);

    Map params = new EDITMap("segmentPK", segmentPK)
                    .put("transactionTypeCT", transactionTypeCT)
                    .put("fromDate", fromDate)
                    .put("toDate", toDate);
                
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
    else if (grossNet.equalsIgnoreCase(PARAM_ACCUM))
    {
        targetFieldName = "financialHistory.AccumulatedValue";
    }
    else if (grossNet.equalsIgnoreCase(PARAM_TAXABLE))
    {
        targetFieldName = "financialHistory.TaxableBenefit";
    }
    
    return targetFieldName;
  }
}
