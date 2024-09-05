/*
 * User: unknown
 * Date: Jun 4, 2001
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.sp;

import edit.common.*;
import fission.utility.Util;

/**
 * This is the implementation for the "Whilene" instruction.
 * The "Whilene" instruction is used for the looping process
 * and is associated with the "Do" instruction  statement.
 * <p>
 * A sample script:
 * Do
 *   some instruction...
 *   some instruction...
 *   Push ws:Level
 *   Push str:"Y"
 * Whilene
 */
public final class Whilene extends Inst {

    /**
     * Compiles the instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor) {

        sp = aScriptProcessor;  //Save instance of ScriptProcessor

        // Note: No compiling is required for this instruction
    }


    /**
     *  Executes the instruction
     *  <p>
     *  @exception SPException If there is an error while
     *       executing the instruction
     */
   	protected void exec(ScriptProcessor execSP) throws SPException {

        sp = execSP;

        // Pop the top two items from the data stack and do a comparision
        String operand2 = sp.popFromStack();
        String operand1 = sp.popFromStack();

        if (operandsNE (operand1, operand2)) {
            // Get the instruction pointer to the "Do" instruction
            int returnPoint = ((Integer)
                sp.peek(ScriptProcessor.LOOP_STACK)).intValue();

            // Reposition instruction pointer
            sp.setInstPtr(returnPoint);

        } else  {
            // Remove instruction pointer from Loop Stack
            sp.pop(ScriptProcessor.LOOP_STACK);
        }

        // increment instruction pointer
        sp.incrementInstPtr();
    }


    private boolean operandsNE(String operandx, String operandy) throws SPException {


        if (Util.isANumber(operandx))
        {
//            double doubleOne = Double.parseDouble(operandx);
//            double doubleTwo = Double.parseDouble(operandy);
//            return (doubleOne != doubleTwo);
            EDITBigDecimal operandONE = new EDITBigDecimal(operandx);
            EDITBigDecimal operandTWO = new EDITBigDecimal(operandy);
            return (!operandONE.isEQ(operandTWO));
        }
        else if (EDITDate.isACandidateDate(operandx))
        {

            EDITDate interimdate1 = new EDITDate(operandx);
            EDITDate interimdate2 = new EDITDate(operandy);
            return (! interimdate1.equals(interimdate2));
        }
        else
        {
            int i = (operandx.compareTo(operandy));
            return (i != 0);
        }
    }
}