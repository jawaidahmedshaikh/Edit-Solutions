/*
 * User: dlataill
 * Date: Apr 1, 2002
 * Time: 1:18:13 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine.sp;

import edit.common.*;

public class Monthdiff extends Inst {

    protected void compile(ScriptProcessor aScriptProcessor) {

	    sp = aScriptProcessor;  // Save instance of ScriptProcessor

		// Note: No compiling is required for this instruction
	}

	/**
	 *  Execute the instruction
	 *  <p>
	 *  @exception SPException If there is an error while
	 *       executing the instruction
	 */
	protected void exec(ScriptProcessor execSP) throws SPException {

	    sp = execSP;
        //Remove the top two items from the data stack

        String operand1 = sp.popFromStack();
        String operand2 = sp.popFromStack();
        String result = null;

        //Create an integer for the datediff method
//        result = (EDITDate.getMonthsBetweenDates(operand1, operand2)) + "";
        EDITDate dateOperand1 = new EDITDate(operand1);
        EDITDate dateOperand2 = new EDITDate(operand2);

        result = dateOperand1.getElapsedMonths(dateOperand2) + "";

		// Push the result back onto the data stack
		sp.push(result);

		// Increment instruction pointer
		sp.incrementInstPtr();
	}
}
