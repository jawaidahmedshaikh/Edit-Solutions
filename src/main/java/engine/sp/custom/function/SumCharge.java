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
 * Sums the ChargeHistory.ChargeAmount for the specified Working Storage
 * parameters:
 * SegmentPK (compare to ContractSetup.SegmentFK)
 * FromDate (compare to EDITTrx.EffectiveDate)
 * ToDate (compare to EDITTrx.EffectiveDate)
 * ChargeTypeCT (compare to ChargeHistory.ChargeTypeCT)
 */
public class SumCharge implements FunctionCommand
{
  private ScriptProcessor scriptProcessor;
  
  /**
   * The target transaction type; e.g. 'PY'.
   */
  public static final String PARAM_CHARGETYPECT = "ChargeTypeCT";
  
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
  public SumCharge(ScriptProcessor scriptProcessor)
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
   *  Sums the ChargeHistory.ChargeAmount from WS entries.
   * The WS will contain the following paramter entries:
   * 
   * 1. SegmentPK
   * 2. FromDate
   * 3. ToDate
   * 4. ChargeTypeCT
   * 
   * Should there be no result (i.e. there are no FinancialHistory records to date), then #NULL is
   * returned instead.
   * 
   * @return the result as a String, or #NULL if there are no records of interest
   */
  public void exec()
  {
    Object summedAmount = null; 
  
    String chargeTypeCT = getWSValue(PARAM_CHARGETYPECT);
    
    EDITDate fromDate = new EDITDate(getWSValue(PARAM_FROMDATE));
    
    EDITDate toDate = new EDITDate(getWSValue(PARAM_TODATE));
    
    Long segmentPK = new Long(getWSValue(PARAM_SEGMENTPK));
  
    String hql = " select sum(chargeHistory.ChargeAmount)" +
                " from ChargeHistory chargeHistory" +
                " join chargeHistory.EDITTrxHistory editTrxHistory" +
                " join editTrxHistory.EDITTrx editTrx" +
                " join editTrx.ClientSetup clientSetup" +
                " join clientSetup.ContractSetup contractSetup" +
                " where contractSetup.SegmentFK = :segmentPK" +
                " and chargeHistory.ChargeTypeCT = :chargeTypeCT" +
                " and editTrx.EffectiveDate >= :fromDate" +
                " and editTrx.EffectiveDate <= :toDate" +
                " and editTrx.Status in ('N', 'A')";
                
    Map params = new EDITMap("segmentPK", segmentPK)
                    .put("chargeTypeCT", chargeTypeCT)
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
