/**

 * User: dlataill

 * Date: Jun 11, 2007

 * Time: 10:38:45 AM

 *

 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved

 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is

 * subject to the license agreement. 

 */
package engine.sp.custom.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.hibernate.Session;

import contract.Segment;
import edit.common.EDITDate;
import edit.common.EDITMap;
import edit.services.db.hibernate.SessionHelper;
import engine.sp.SPParams;
import engine.sp.ScriptProcessor;
import engine.sp.custom.document.PRASEDocBuilder;
import event.InvestmentAllocationOverride;
import fission.utility.DOMUtil;

public class GetPhaseEffectiveDate implements FunctionCommand
{
    private ScriptProcessor scriptProcessor;

    /**
     * The driving SegmentPK.
     */
    public static final String PARAM_CONTRACT_NUMBER = "ContractNumber";
    public static final String PARAM_COMM_PHASE_ID = "CPDCommPhaseID";
    // public static final String PARAM_COMM_PHASE_COUNTER = "CommPhaseCounter";

    /**
     * Constructor.
     * @param scriptProcessor ScriptProcessor
     */
    public GetPhaseEffectiveDate(ScriptProcessor scriptProcessor)
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
    protected String getWSValue(String paramName)
    {
      return getWorkingStorage().get(paramName);
    }

    /**
     * Get the earliest segment effective date for the given Commission Phase (defined by CommPhaseId
     * in working storage) for the given contract defined by the contractNumber in working storage -
     * there will be at least one effective date - which will be found on the base segment.
     *
     * @return the result as a String
     */
    public void exec()
    {
      Object phaseEffDate = null;

      String contractNumber = new String(getWSValue(PARAM_CONTRACT_NUMBER));
      String commPhaseId = new String(getWSValue(PARAM_COMM_PHASE_ID));
      
	  EDITDate minEffectiveDate = null;
      
      List<Segment> segments = getScriptProcessor().getSPParams().getAllSegments_AsSegments();
      if (segments != null) {
	      for (Segment segment : segments) {
	    	  
			  if (segment.getCommissionPhaseID() == Integer.parseInt(commPhaseId) || 
					  (segment.getCommissionPhaseOverride() != null && segment.getCommissionPhaseOverride().equals(commPhaseId))) {
				  
				  if (minEffectiveDate == null || segment.getEffectiveDate().before(minEffectiveDate)) {
					  minEffectiveDate = segment.getEffectiveDate();
				  }
			  }
		  }
      }

	  phaseEffDate = minEffectiveDate;

      if (phaseEffDate == null) {
    	  
	      String hql = " select min(segment.EffectiveDate)" +
	                  " from Segment segment" +
	                  " where segment.ContractNumber = :contractNumber" +
	                  " and (segment.CommissionPhaseID = :commPhaseId" +
	                  " or segment.CommissionPhaseOverride = :commPhaseId)";
	
	      Map params = new EDITMap("contractNumber", contractNumber)
	                        .put("commPhaseId", Integer.parseInt(commPhaseId));
	
	      Session session = null;
	
	      try
	      {
	        session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);
	
	        List results = SessionHelper.executeHQL(session, hql, params, 0);
	
	        if (!results.isEmpty())
	        {
	          phaseEffDate = results.get(0);
	        }
	      }
	      finally
	      {
	        if (session != null) session.close();
	      }
      }

      getScriptProcessor().push(phaseEffDate.toString());
    }
}
