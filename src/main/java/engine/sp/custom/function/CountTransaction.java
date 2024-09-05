package engine.sp.custom.function;

import edit.common.EDITDate;
import edit.common.EDITMap;

import edit.services.command.Command;
import edit.services.db.hibernate.SessionHelper;

import engine.common.Constants;

import engine.sp.ScriptProcessor;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.StatelessSession;

/**
 * Counts the number of transactions that satisfy the supplied
 * Working Storage values of:
 * SegmentFK
 * FromDate
 * ToDate
 * TransactionTypeCT
 */
public class CountTransaction implements FunctionCommand
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
   * The driving SegmentPK.
   */
  public static final String PARAM_SEGMENTPK = "SegmentPK";

  /**
   * Contructor.
   * @param scriptProcessor ScriptProcessor
   */
  public CountTransaction(ScriptProcessor scriptProcessor)
  {
    this.scriptProcessor = scriptProcessor;
  }
  
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
   * Counts the number of transactions that satisfy the supplied WS values;
   *
   * The WS will contain the following paramter entries:
   * 
   * 1. TransactionTypeCT
   * 2. FromDate
   * 3. ToDate
   * 4. SegmentPK
   * 
   * Should there be no result (i.e. there are no EDITTrx records to date), then #NULL is
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
    
    Long segmentPK = new Long(getWSValue(PARAM_SEGMENTPK));
  
    String hql = " select count(editTrx.EDITTrxPK)" +
                " from EDITTrx editTrx" +
                " join editTrx.ClientSetup clientSetup" +
                " join clientSetup.ContractSetup contractSetup" +
                " where contractSetup.SegmentFK = :segmentPK" +
                " and editTrx.TransactionTypeCT = :transactionTypeCT" +
                " and editTrx.EffectiveDate >= :fromDate" +
                " and editTrx.EffectiveDate <= :toDate" +
                " and editTrx.Status in ('N', 'A')";
                
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
}
