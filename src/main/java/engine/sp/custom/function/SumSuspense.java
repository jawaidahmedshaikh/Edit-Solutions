/*
 * User: cgleason
 * Date: Feb 26, 2008
 * Time: 9:15
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
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
import org.hibernate.StatelessSession;


/**
 * Scripts invoke functions with commands such as the following:
 * 
 * activatefunction (FooFunction).
 * 
 * The 'FooFunction' actually defines the name of a class which will
 * execute the requested function. SumSuspense is such a class amongst
 * many such classes designed to execute a specified function.
 * 
 * The function class sums Suspense.SuspenseAmount
 */
public class SumSuspense implements FunctionCommand
{
  private ScriptProcessor scriptProcessor;
  
  /**
   * The target contract number.
   */
  public static final String PARAM_CONTRACTNUMBER = "ContractNumber";
  

  /**
   * Contructor.
   * @param scriptProcessor ScriptProcessor
   */
  public SumSuspense(ScriptProcessor scriptProcessor)
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
   * Sums Suspense.SuspenseAmount for the ContractNumber set where
   * Suspense.PendingSuspenseAmount = 0
   * 
   * The WS will contain the following paramter:
   * 
   * 1. ContractNumber
   *
   * Should there be no result then #NULL is returned instead.
   * 
   * @return the result as a String, or #NULL if there are no records of interest
   */
  public void exec()
  {
    Object summedAmount = null;

    String contractNumber = getWSValue(PARAM_CONTRACTNUMBER);

    String hql = " select sum(SuspenseAmount)" +
                " from Suspense suspense" +
                " where suspense.UserDefNumber = :contractNumber" +
                " and suspense.PendingSuspenseAmount = 0" +
                " and suspense.DirectionCT = 'Apply'" +
                " and suspense.MaintenanceInd <> 'V'";


    Map params = new EDITMap("contractNumber", contractNumber) ;

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
