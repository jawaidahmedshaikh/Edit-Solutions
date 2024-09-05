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
 * This is the implementation for the Min instruction.
 * The Min instruction does the following:
 * 1.) Removes and Compares the top two items
 *     (numbers, strings, or dates) on the stack
 * 2.) Places the smallest of the two items onto the data stack.
 * <p>
 * Sample script for formula min(12,6):
 * push num:12
 * push num:6
 * min
 */
public final class Min extends Inst {

    /**
     * Compiles the instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor) {

        sp = aScriptProcessor;  //Save instance of ScriptProcessor

        //Note: No compiling is required for this instruction
    }


    /**
     *  Executes the instruction
     *  <p>
     *  @exception SPException If there is an error while
     *        executing the instruction
     */
    protected void exec(ScriptProcessor execSP) throws SPException {

        sp = execSP;
        // Remove the top two items from the data stack

        String operand1 = sp.popFromStack();
        String operand2 = sp.popFromStack();
        String result = null;

        // Determine the type (number,date, string) and
        // Determine the minimum of the two items
        if (Util.isANumber(operand1)) {
//            double doubleOne = Double.parseDouble(operand1);
//            double doubleTwo = Double.parseDouble(operand2);
//            result = minNumber(doubleOne, doubleTwo);
            EDITBigDecimal operandOne = new EDITBigDecimal(operand1);
            EDITBigDecimal operandTwo = new EDITBigDecimal(operand2);
            result = minNumber(operandOne, operandTwo);
        }
        else if (EDITDate.isACandidateDate(operand1)) {

            result = minDate(operand1,operand2);
        }
        else  {

            result = minString(operand1, operand2);
        }

        // Push the addition result back onton the stack
        sp.push(result);

        //Increment instruction pointer
        sp.incrementInstPtr();

        }

    /**
     *  Returns the smallest of the two passed in numbers
     *  <p>
     *  @param operand1 First operand to be compared
     *  @param operand2 Second operand to be compared
     *  @return Returns the smallest of the two operands
     *  @exception SPException If there is an error during the compare
     */
    private String minNumber(EDITBigDecimal operand1, EDITBigDecimal operand2)
            throws SPException {

        if (operand1.isLTE(operand2)) {

            return operand1.toString();
        }

        else {

            return operand2.toString();
        }
    }

    /**
     *  Returns the smallest of the two passed in strings
     *  <p>
     *  @param operand1 First operand to be compared
     *  @param operand2 Second operand to be compared
     *  @return Returns the smallest of the two operands
     *  @exception SPException If there is an error during the compare
     */
    private String minString(String operand1, String operand2)
            throws SPException {

        int i = (operand1.compareTo(operand2));

        if ((i <= 0))
        {

            return operand1;
        }

        else
        {

            return operand2;
        }

    }

    /**
      *  Returns the smallest of the two passed in Dates
      *  <p>
      *  @param operand1 First operand to be compared
      *  @param operand2 Second operand to be compared
      *  @return Returns the smallest of the two operands
      */
    private String minDate(String operand1, String operand2)
             throws SPException {


        EDITDate interimdate1 = new EDITDate(operand1);
        EDITDate interimdate2 = new EDITDate(operand2);

        if ((interimdate1.equals(interimdate2)) ||
               (interimdate1.before(interimdate2))) {

            return operand1;

        } else {

            return operand2;

        }
     }


}