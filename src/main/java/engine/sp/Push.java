/*
 * Push.java     06/04/2001
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;

import engine.common.Constants;

import java.util.*;

import edit.common.EDITDate;
import edit.common.EDITDateTime;


/**
 * This is the implementation of the Push Instruction.
 * This instruction is used to place objects onto the 
 * data stack (Ex. Push val:10)
 */
public final class Push extends InstOneOperand {

    /**
     * Push constructor 
     *<p>
     * @exception SPException 
     */
    public Push() throws SPException {
    
        super();
    }
	
    /**
     * Compiles the Instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     * @exception SPException   If there is an error while compiling
     */
    public void compile(ScriptProcessor aScriptProcessor) throws SPException {
		
        //Get instance of ScriptProcessor
        sp = aScriptProcessor;
		
        //Does common tasks in superclass such as
        //setting member variables and some operand editting
        commonCompileTasks();
		
        //Creates a Constant
        if (isConstant())
        {
            generateStringConstant();
        }

    }

    /**
     * Executes Instruction
     * <p>
     * @exception SPException  If there is an error while 
     *      executing the Instruction
     */
    public void exec(ScriptProcessor execSP) throws SPException {

        sp = execSP;
        // Use different push logic based on the operand type
        if (isConstant())
        {
            sp.push(constantString);   
        }
        else if (isDynamic())
        {
            sp.push(generateDynamicConstant());
        }
        else if (isWSReference())
        {
            execWS();
        }
        else if (isPMReference())
        {
            getParam();
        }
        else
        {
            throw new SPException("Push.exec() - Invalid Operand", SPException.INSTRUCTION_SYNTAX_ERROR);
        }
		
        //Increment Instruction Pointer
        sp.incrementInstPtr();
    }

    private void getParam() throws SPException
    {
        String operandName = getOperandDataName();

        setFieldPathAndName(operandName);
        String fieldPath = getFieldPath();
        String fieldName = getFieldName();

        String result = sp.getElementValue(fieldPath, fieldName);

            sp.push(result);
    }

    /**
     * Generates a Dynamic constant
     */
    public String generateDynamicConstant()
    {
        String cString = getOperandDataName();

        if (cString.equalsIgnoreCase(Constants.ScriptKeyword.SYSTEM_DATE))
        {
            cString = new EDITDate().getFormattedDate();
        }
        else if (cString.equalsIgnoreCase(Constants.ScriptKeyword.SYSTEM_DATE_TIME))
        {
            cString = new EDITDateTime().getFormattedDateTime();
        }


        return cString;
    }

    // Support method for exec().
    // Executes instruction containing Working Storage (WS)
    private void execWS() throws SPException {

        String operandName = getOperandDataName();
        String subscriptName = getSubscriptName();

        if (subscriptName != null && subscriptName.length() > 0) {

            int subscript =  Integer.parseInt((String)sp.getWSEntry(subscriptName));
            sp.push(sp.getWSEntry(getOperandDataName() + subscript));
        }

		else if ((operandName.equalsIgnoreCase("Date")) ||
                 (operandName.equalsIgnoreCase("Rate")) ||
                 (operandName.equalsIgnoreCase("RateEffectiveDate")))
		{
            double ctr = Double.parseDouble((String)(sp.getWSEntry("DtCtr")));
            int dtCtr = (int) ctr;

            if (operandName.equalsIgnoreCase("Date"))
            {
                List rateDateData = (List)sp.getWSVector("RateDateData");
                Map dataRow = (Map)rateDateData.get(dtCtr-1);

                sp.push((String) dataRow.get("Date"));
            }
            else if(operandName.equalsIgnoreCase("Rate"))
            {
				List rateDateData = (List)sp.getWSVector("RateDateData");
				Map dataRow = (Map)rateDateData.get(dtCtr-1);

               	sp.push((String) dataRow.get("Rate"));
			}
            else if (operandName.equalsIgnoreCase("RateEffectiveDate"))
            {
                List rateDateData = (List)sp.getWSVector("RateDateData");
                Map dataRow = (Map)rateDateData.get(dtCtr-1);

                sp.push((String) dataRow.get("RateEffectiveDate"));
            }
		}
        //GetCommissionPhaseDetail function populates the CommissionPhaseDetail vector
        //  AAAAHHHHHH!  Why on earth is this being done in a simple push instruction?!?
        //  All I know is that when I had to add a new parameter to the GetCommissionPhaseDetail instruction, I
        //  had to add it here too, otherwise, the script was getting a null back.  Why?  The subscriptName at the
        //  top of this method comes back as null so it doesn't match the operandName.  But fix that problem, don't
        //  add hardcoded checks in a push instruction!  Now I have to compound the problem by adding my new variable
        //  here.  Something desperately needs to be done with this instruction.  It's doing the same thing for rate
        //  data.  - S.D. 10/14/2008
        else if (operandName.equalsIgnoreCase("CPDCommissionPhasePK") ||
                 operandName.equalsIgnoreCase("CPDCommissionPhaseID") ||
                 operandName.equalsIgnoreCase("CPDEffectiveDate") ||
                 operandName.equalsIgnoreCase("CPDExpectedMonthlyPremium") ||
                 operandName.equalsIgnoreCase("CPDPrevCumExpectedMonthlyPremium") ||
                 operandName.equalsIgnoreCase("CPDCommissionTarget") ||
                 operandName.equalsIgnoreCase("CPDPriorExpectedMonthlyPremium"))
        {

            double ctr = Double.parseDouble((String) (sp.getWSEntry("PDCounter")));
            double cpCtr = Double.parseDouble((String) (sp.getWSEntry("CommPhaseCounter")));

            int premiumDueCounter = (int) ctr;
            int commPhaseCounter = (int) cpCtr;

            List<Map> premiumDueDetail = (List<Map>) sp.getWSVector("PremiumDueDetail");

            Map dataRow = (Map) premiumDueDetail.get(premiumDueCounter - 1);
            List<Map> commissionPhaseVector = (List<Map>)dataRow.get("CommissionPhaseDetail");

            double totalCommissionPhases = Double.parseDouble((String)dataRow.get("PDNumberCommPhases"));
            int totalCommPhases = (int) totalCommissionPhases;

            findRequestedCommissionPhase(operandName, commPhaseCounter, commissionPhaseVector, totalCommPhases);

        }
        else if (operandName.equalsIgnoreCase("PDPremiumDuePK") ||
                 operandName.equalsIgnoreCase("PDEffectiveDate") ||
                 operandName.equalsIgnoreCase("PDPendingExtractIndicator") ||
                 operandName.equalsIgnoreCase("PDBillAmount") ||
                 operandName.equalsIgnoreCase("PDAdjustmentAmount") ||
                 operandName.equalsIgnoreCase("PDNumberCommPhases") ||
                 operandName.equalsIgnoreCase("PDDeductionAmount") ||
                 operandName.equalsIgnoreCase("PDNumberOfDeductions"))
        {
            double ctr = Double.parseDouble((String) (sp.getWSEntry("PDCounter")));

            int premiumDueCounter = (int) ctr;

            List premiumDueDetail = (List) sp.getWSVector("PremiumDueDetail");

            Map dataRow = (Map) premiumDueDetail.get(premiumDueCounter - 1);
            findRequestedPremiumDue(operandName, premiumDueCounter, dataRow);
        }
        //gettierrate builds this arrayList, access by BandCtr
        else if (operandName.equalsIgnoreCase("Band"))
        {
            int counter = (int) Double.parseDouble((String) sp.getWSEntry("BandCtr"));
            List band = (ArrayList)sp.getWSVector("bandAmount");
//            sp.push(((ArrayList)sp.getVectorEntry("bandAmount")).get(counter - 1));
            sp.push(band.get(counter - 1).toString());
        }
        //gettierrate builds this arrayList, access by BandCtr
        else if (operandName.equalsIgnoreCase("TierRate"))
        {
            int counter = (int) Double.parseDouble((String) sp.getWSEntry("BandCtr"));
            List tierRate = (ArrayList)sp.getWSVector("tierRate");
//            sp.push(((ArrayList)sp.getVectorEntry("tierRate")).get(counter - 1));
            sp.push(tierRate.get(counter - 1).toString());
        }
        else
        {
            sp.push(sp.getWSEntry(getOperandDataName()));
        }
    }

    private void findRequestedPremiumDue(String operandName, int premiumDueCounter, Map dataRow)
    {
        if (operandName.equalsIgnoreCase("PDPremiumDuePK"))
        {
            sp.push((String) dataRow.get("PDPremiumDuePK"));
        }
        else if (operandName.equalsIgnoreCase("PDEffectiveDate"))
        {
            sp.push((String) dataRow.get("PDEffectiveDate"));
        }
        else if (operandName.equalsIgnoreCase("PDPendingExtractIndicator"))
        {
            sp.push((String) dataRow.get("PDPendingExtractIndicator"));
        }
        else if (operandName.equalsIgnoreCase("PDBillAmount"))
        {
            sp.push((String) dataRow.get("PDBillAmount"));
        }
        else if (operandName.equalsIgnoreCase("PDAdjustmentAmount"))
        {
            sp.push((String) dataRow.get("PDAdjustmentAmount"));
        }
        else if (operandName.equalsIgnoreCase("PDNumberCommPhases"))
        {
            sp.push((String) dataRow.get("PDNumberCommPhases"));
        }
        else if (operandName.equalsIgnoreCase("PDDeductionAmount"))
        {
            sp.push((String) dataRow.get("PDDeductionAmount"));
        }
        else if (operandName.equalsIgnoreCase("PDNumberOfDeductions"))
        {
            sp.push((String) dataRow.get("PDNumberOfDeductions"));
        }
    }

    /**
     * Parse through the Commission Phase data rows to find the requested value.
     * @param operandName
     * @param commPhaseCounter
     * @param commissionPhaseVector
     * @param totalCommPhases
     */
    private void findRequestedCommissionPhase(String operandName, int commPhaseCounter, List<Map> commissionPhaseVector, int totalCommPhases)
    {
        for (int i = 0; i < totalCommPhases; i++)
        {
            Map dataRow = (Map) commissionPhaseVector.get(i);

            if ((commPhaseCounter - 1) == i)
            {
                if (operandName.equalsIgnoreCase("CPDCommissionPhasePK"))
                {
                    sp.push((String) dataRow.get("CPDCommissionPhasePK"));
                }
                else if (operandName.equalsIgnoreCase("CPDCommissionPhaseID"))
                {
                    sp.push((String) dataRow.get("CPDCommissionPhaseID"));
                }
                else if (operandName.equalsIgnoreCase("CPDEffectiveDate"))
                {
                    sp.push((String) dataRow.get("CPDEffectiveDate"));
                }
                else if (operandName.equalsIgnoreCase("CPDExpectedMonthlyPremium"))
                {
                    sp.push((String) dataRow.get("CPDExpectedMonthlyPremium"));
                }
                else if (operandName.equalsIgnoreCase("CPDPrevCumExpectedMonthlyPremium"))
                {
                    sp.push((String) dataRow.get("CPDPrevCumExpectedMonthlyPremium"));
                }
                else if (operandName.equalsIgnoreCase("CPDPriorExpectedMonthlyPremium"))
                {
                    sp.push((String) dataRow.get("CPDPriorExpectedMonthlyPremium"));
                }
                else if (operandName.equalsIgnoreCase("CPDCommissionTarget"))
                {
                    sp.push((String) dataRow.get("CPDCommissionTarget"));
                }
            }
        }
    }
}





