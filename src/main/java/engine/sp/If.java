/**
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: Jul 14, 2003
 * Time: 2:40:38 PM
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import fission.utility.Util;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This is the implementation for the If instruction.
 * The If instruction evaluates an expression for true or false.
 * When true the next instruction(s) execute until an else is encountered.
 * When false instructions are skipped until an else is encountered.
 * Execution begins with the statement after the else and continues
 * to the endif instruction.
 * <p>
 * For Example the script for the else would be:
 * if y < x
 * push val:12
 * push val:6
 * else
 * push val:14
 * push val:8
 * endif
 */
public final class If extends InstOneOperand
{

    private String relationalOperator = null;
    private List variableOne = null;
    private List variableTwo = null;
    private boolean childrenRequested = false;

    int elseEndIFPtr = 0;


    public If() throws SPException
    {

        super();
    }

    /**
     * Compiles the Instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     * @exception SPException   If there is an error while compiling
     */
    public void compile(ScriptProcessor aScriptProcessor) throws SPException
    {

        //Get instance of ScriptProcessor
        sp = aScriptProcessor;


        //Does common tasks in superclass such as
        //setting member variables and some operand editting
        commonCompileTasks();
    }

    /**
     * Executes Instruction
     * <p>
     * @exception SPException  If there is an error while
     *      executing the Instruction
     */
    public void exec(ScriptProcessor execSP) throws SPException
    {
        sp = execSP;

        boolean result = false;

        childrenRequested = getChildrenRequested();

        if (childrenRequested)
        {
            result = findRequestedChild();
        }
        else
        {
            relationalOperator = getRelationalOperator();
            variableOne = getVariableOne();
            variableTwo = getVariableTwo();

            result = execCondition();
        }
                
        findCorrespondingElseEndif(result);

        if (result)
        {
            //condition if true, execute at next instruction
            sp.setConditionSwitch(result);

            //Increment Instruction Pointer
            sp.incrementInstPtr();
        }
        else
        {
            //condition is false, look for else instruction
            sp.setInstPtr(elseEndIFPtr);
            sp.incrementInstPtr();
        }
    }

    private void findCorrespondingElseEndif(boolean result)
    {
        int scriptSize = sp.getScriptSize();
        int saveInstPtr = sp.getInstPtr();
        int ifCounter = 0;
        boolean ifFound = false;
        boolean elseFound = false;
        boolean endIfFound = false;
        sp.incrementInstPtr();
        
        for (int i = sp.getInstPtr(); i < scriptSize; i++)
        {
            String operator = (Inst.extractOperator(sp.getScriptElementAt(i)));
            
            if (operator.equalsIgnoreCase("if"))
            {
                ifCounter++; 
            }
            if (operator.equalsIgnoreCase("else") || operator.equalsIgnoreCase("endif"))
            {
                if (ifCounter > 0)     //nested if found must look past it
                {
                    if (operator.equalsIgnoreCase("endif"))
                    	ifCounter--;
                    	
                }
                else
                {
                    if (operator.equalsIgnoreCase("endif"))
                    {
                        endIfFound = true;
                    }
                    else
                    {
                        elseFound = true;
                    }
                    elseEndIFPtr = i;
                    break;
                }
            }
        }
        if (elseFound)
        {
            if (result){
                sp.push(ScriptProcessor.IF_STACK, new Boolean(result));
            }
            
        }

        sp.setInstPtr(saveInstPtr);
    }

    private void setIfStackValue(boolean result, int scriptSize)
    {
        int saveInstPtr = sp.getInstPtr();

        for (int i = saveInstPtr; i < scriptSize; i++)
        {

            sp.incrementInstPtr();
            String operator = (Inst.extractOperator(sp.getScriptElementAt(sp.getInstPtr())));

            if (operator.equalsIgnoreCase("endif"))
            {
                break;
            }

            if (operator.equalsIgnoreCase("else"))
            {

            }
        }

        sp.setInstPtr(saveInstPtr + 1);
        sp.incrementInstPtr();
    }

     private boolean findRequestedChild() throws SPException
    {
        String operandName = getOperand().substring(getOperand().lastIndexOf(":") + 1, getOperand().length());
        operandName = checkForAlias(operandName, sp);

        boolean pathExists =  sp.verifyPathExistence(operandName);

        return pathExists;
    }

    protected boolean execCondition() throws SPException
    {

        boolean result = false;
        String value1 = getVariable1();
        String value2 = getVariable2();

        if (Util.isANumber(value1) && Util.isANumber(value2))
        {
            result = compareNumbers(value1, value2);
        }
        else if (EDITDate.isACandidateDate(value1) && EDITDate.isACandidateDate(value2))
        {
            result = compareDates(value1, value2);
        }
        else
        {
            result = compareStrings(value1, value2);
        }

        return result;
    }


    private String getVariable1() throws SPException
    {
        String value1 = null;
        String type = (String) variableOne.get(0);
        String operandName = null;

        if (type.equals(varConstant))
        {

            value1 = (String) variableOne.get(1);

        }
        else if (type.equals(varWS))
        {
            operandName = (String) variableOne.get(1);
            value1 = getValueFromWS(operandName);
        }
        else if (type.equals(varPM))
        {
            operandName = (String) variableOne.get(1);
            setFieldPathAndName(operandName);
            String fieldPath = getFieldPath();
            String fieldName = getFieldName();

            value1 = sp.getElementValue(fieldPath, fieldName);
        }
        else
        {
            throw new SPException("If.exec() - Invalid Operand", SPException.INSTRUCTION_SYNTAX_ERROR);
        }

        return value1;
    }

    private String getVariable2() throws SPException
    {

        String value2 = null;
        String type = (String) variableTwo.get(0);
        String operandName = null;

        if (type.equals(varConstant))
        {

            value2 = (String) variableTwo.get(1);

        }
        else if (type.equals(varWS))
        {
            operandName = (String) variableTwo.get(1);
            value2 = getValueFromWS(operandName);
        }
        else if (type.equals(varPM))
        {
            operandName = (String) variableTwo.get(1);
            setFieldPathAndName(operandName);
            String fieldPath = getFieldPath();
            String fieldName = getFieldName();

            value2 = sp.getElementValue(fieldPath, fieldName);
        }
        else
        {
            throw new SPException("If.exec() - Invalid Operand", SPException.INSTRUCTION_SYNTAX_ERROR);
        }

        return value2;
    }


    private boolean compareNumbers(String value1, String value2)
    {

        EDITBigDecimal operandOne = new EDITBigDecimal(value1);
        EDITBigDecimal operandTwo = new EDITBigDecimal(value2);

        if (relationalOperator.equals("="))
        {
            return (operandOne.isEQ(operandTwo));
        }
        else if (relationalOperator.equals("!="))
        {
            return (!operandOne.isEQ(operandTwo));
        }
        else if (relationalOperator.equals(">"))
        {
            return (operandOne.isGT(operandTwo));
        }
        else if (relationalOperator.equals("<"))
        {
            return (operandOne.isLT(operandTwo));
        }
        else if (relationalOperator.equals(">="))
        {
            return (operandOne.isGTE(operandTwo));
        }
        else if (relationalOperator.equals("<="))
        {
            return (operandOne.isLTE(operandTwo));
        }

        return false;
    }

    private boolean compareDates(String value1, String value2)
    {
        EDITDate interimdate1 = new EDITDate(value1);
        EDITDate interimdate2 = new EDITDate(value2);

        if (relationalOperator.equals("="))
        {
            return (interimdate1.equals(interimdate2));
        }
        else if (relationalOperator.equals("!="))
        {
            return (! interimdate1.equals(interimdate2));
        }
        else if (relationalOperator.equals(">"))
        {
            return (interimdate1.after(interimdate2));
        }
        else if (relationalOperator.equals("<"))
        {
            return (interimdate1.before(interimdate2));
        }
        else if (relationalOperator.equals(">="))
        {
            return (interimdate1.after(interimdate2) || interimdate1.equals(interimdate2));
        }
        else if (relationalOperator.equals("<="))
        {
            return (interimdate1.before (interimdate2) || interimdate1.equals(interimdate2));
        }

        return false;

    }

    private boolean compareStrings(String value1, String value2)
    {

        int i = (value1.compareTo(value2));

        if (relationalOperator.equals("="))
        {
            return (i == 0);
        }
        else if (relationalOperator.equals("!="))
        {
            return (i != 0);
        }
        else if (relationalOperator.equals(">"))
        {
            return (i > 0);
        }
        else if (relationalOperator.equals("<"))
        {
            return (i < 0);
        }
        else if (relationalOperator.equals(">="))
        {
            return (i >= 0);
        }
        else if (relationalOperator.equals("<="))
        {
            return (i <= 0);
        }
        else if (relationalOperator.equals("~"))
        {
            return evaluateRegularExpression(value1, value2);
        }
        else if (relationalOperator.equals("=="))
        {
            return evaluateIgnoreCase(value1, value2);
        }
        

        return false;
    }

//    private SPNodeKey getParentSpNodeKey(int index)
//    {
//        // get the next parent if there is one
//        String parentPath = getParentPath();
//
//        int indexOfDot = parentPath.lastIndexOf('.');
//        String nextParentPath = (indexOfDot > 0) ? parentPath.substring(0, indexOfDot) : parentPath;
//
//        int parentIndex = getParentIndex(nextParentPath);
//
//        SPNodeKey parentSPNodeKey = new SPNodeKey(parentPath, index, parentIndex);
//
//        return parentSPNodeKey;
//    }

    private String getValueFromWS(String operandName)
    {
        String value = null;
        if (operandName.equalsIgnoreCase("CPDCommissionPhasePK") ||
            operandName.equalsIgnoreCase("CPDCommissionPhaseID") ||
            operandName.equalsIgnoreCase("CPDEffectiveDate") ||
            operandName.equalsIgnoreCase("CPDExpectedMonthlyPremium") ||
            operandName.equalsIgnoreCase("CPDCommissionTarget") ||
            operandName.equalsIgnoreCase("CPDPrevCumExpectedMonthlyPremium"))
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

             value = findRequestedCommissionPhase(operandName, commPhaseCounter, commissionPhaseVector, totalCommPhases);

        }
        else if (operandName.equalsIgnoreCase("PDPremiumDuePK") ||
                 operandName.equalsIgnoreCase("PDEDITTrxFK") ||
                 operandName.equalsIgnoreCase("PDEffectiveDate") ||
                 operandName.equalsIgnoreCase("PDPendingExtractIndicator") ||
                 operandName.equalsIgnoreCase("PDBillAmount") ||
                 operandName.equalsIgnoreCase("PDAdjustmentAmount") ||
                 operandName.equalsIgnoreCase("PDNumberCommPhases") ||
                 operandName.equalsIgnoreCase("PDNumberOfDeductions"))
        {
            double ctr = Double.parseDouble((String) (sp.getWSEntry("PDCounter")));

            int premiumDueCounter = (int) ctr;

            List premiumDueDetail = (List) sp.getWSVector("PremiumDueDetail");

            Map dataRow = (Map) premiumDueDetail.get(premiumDueCounter - 1);
            value = findRequestedPremiumDue(operandName, premiumDueCounter, dataRow);
        }
        else
        {
            value = (String) (sp.getWSEntry(operandName));
        }

        return value;
    }

    private String findRequestedCommissionPhase(String operandName, int commPhaseCounter, List<Map> commissionPhaseVector, int totalCommPhases)
    {
        String value = null;

        for (int i = 0; i < totalCommPhases; i++)
        {
            Map dataRow = (Map) commissionPhaseVector.get(i);

            if ((commPhaseCounter - 1) == i)
            {
                if (operandName.equalsIgnoreCase("CPDCommissionPhasePK"))
                {
                    value = (String) dataRow.get("CPDCommissionPhasePK");
                }
                else if (operandName.equalsIgnoreCase("CPDCommissionPhaseID"))
                {
                    value = (String) dataRow.get("CPDCommissionPhaseID");
                }
                else if (operandName.equalsIgnoreCase("CPDEffectiveDate"))
                {
                    value = (String) dataRow.get("CPDEffectiveDate");
                }
                else if (operandName.equalsIgnoreCase("CPDExpectedMonthlyPremium"))
                {
                    value = (String) dataRow.get("CPDExpectedMonthlyPremium");
                }
                else if (operandName.equalsIgnoreCase("CPDPrevCumExpectedMonthlyPremium"))
                {
                    value = (String) dataRow.get("CPDPrevCumExpectedMonthlyPremium");
                }
                else if (operandName.equalsIgnoreCase("CPDCommissionTarget"))
                {
                    value = (String) dataRow.get("CPDCommissionTarget");
                }
            }
        }

        return value;
    }

    private String findRequestedPremiumDue(String operandName, int premiumDueCounter, Map dataRow)
    {
        String value = null;
        if (operandName.equalsIgnoreCase("PDPremiumDuePK"))
        {
            value = ((String) dataRow.get("PDPremiumDuePK"));
        }
        else if (operandName.equalsIgnoreCase("PDEDITTrxFK"))
        {
            value = ((String) dataRow.get("PDEDITTrxFK"));
        }
        else if (operandName.equalsIgnoreCase("PDEffectiveDate"))
        {
            value = ((String) dataRow.get("PDEffectiveDate"));
        }
        else if (operandName.equalsIgnoreCase("PDPendingExtractIndicator"))
        {
            value = ((String) dataRow.get("PDPendingExtractIndicator"));
        }
        else if (operandName.equalsIgnoreCase("PDBillAmount"))
        {
            value = ((String) dataRow.get("PDBillAmount"));
        }
        else if (operandName.equalsIgnoreCase("PDAdjustmentAmount"))
        {
            value = ((String) dataRow.get("PDAdjustmentAmount"));
        }
        else if (operandName.equalsIgnoreCase("PDNumberCommPhases"))
        {
            value = ((String) dataRow.get("PDNumberCommPhases"));
        }
        else if (operandName.equalsIgnoreCase("PDNumberOfDeductions"))
        {
            value = ((String) dataRow.get("PDNumberOfDeductions"));
        }

        return value;
    }

    /**
     * The ability
     * @param value2
     * @return
     */
    private boolean isRegularExpression(String value2)
    {
        return false;
    }

    /**
     * For String comparisons, a scripter may not be looking for exact
     * equality, inequality, but "similar" to. We added the tilde ~ operator
     * to represent "similar" to. To implement this, we merely interpret the ~
     * as a request for a regular expression match. It's doubtful that the
     * scripters will ever use this feature extensively. From their perspective,
     * they will likel only care about finding a substring in a case-insensitive
     * manner.
     * @param value
     * @param regularExpression
     * @return
     */
    private boolean evaluateRegularExpression(String value, String regularExpression)
    {
        Pattern p = Pattern.compile(regularExpression, Pattern.CASE_INSENSITIVE);

        Matcher m = p.matcher(value);
        
        boolean matches = m.find();
        
        return matches;
    }
    
    /**
     * To evaluate two strings for equality while disregarding case
     */
    private boolean evaluateIgnoreCase(String value1, String value2)
    {
    	if(value1.equalsIgnoreCase(value2))
    	{
    		return true;
    	} 
    	else 
    	{
    		return false;
    	}
    		
    }
}
