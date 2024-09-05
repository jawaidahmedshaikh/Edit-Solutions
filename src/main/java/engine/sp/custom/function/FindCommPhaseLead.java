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
import engine.common.Constants;

public class FindCommPhaseLead implements FunctionCommand {
	private ScriptProcessor scriptProcessor;

	/**
	 * The driving SegmentPK.
	 */
	public static final String PARAM_CONTRACT_NUMBER = "ContractNumber";
	public static final String PARAM_COMM_PHASE_ID = "CommPhaseID";

	/**
	 * Constructor.
	 * 
	 * @param scriptProcessor ScriptProcessor
	 */
	public FindCommPhaseLead(ScriptProcessor scriptProcessor) {
		this.scriptProcessor = scriptProcessor;
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	private ScriptProcessor getScriptProcessor() {
		return this.scriptProcessor;
	}

	/**
	 * Getter.
	 * 
	 * @return
	 */
	private Map<String, String> getWorkingStorage() {
		return getScriptProcessor().getWS();
	}

	/**
	 * Return the working storage value for the specified parameter name.
	 * 
	 * @param paramName
	 * @return
	 */
	protected String getWSValue(String paramName) {
		return getWorkingStorage().get(paramName);
	}

	/**
	 * Get the lead segmentPK for a particular commissionPhaseId.
	 *
	 * @return the result as a String
	 */
	public void exec() {
		try {
			Long leadSegmentPK = null;

			String contractNumber = new String(getWSValue(PARAM_CONTRACT_NUMBER));
			String commPhaseId = new String(getWSValue(PARAM_COMM_PHASE_ID));

			EDITDate minEffectiveDate = null;

			List<Segment> segments = getScriptProcessor().getSPParams().getAllSegments_AsSegments();
			if (segments != null) {

				for (Segment segment : segments) {

					if (segment.getCommissionPhaseID() == Integer.parseInt(commPhaseId)
							&& (segment.getOptionCodeCT().equalsIgnoreCase("UL")
									|| segment.getOptionCodeCT().equalsIgnoreCase("ULIncrease")
									|| segment.getOptionCodeCT().equalsIgnoreCase("FI"))) {

						leadSegmentPK = segment.getSegmentPK();
						break;
					}
				}
			}

			if (leadSegmentPK != null) {
				getScriptProcessor().push(leadSegmentPK.toString());
			} else {
				getScriptProcessor().push(Constants.ScriptKeyword.NULL);
			}

		} catch (Exception e) {
			System.out.println();
		}
	}
}
