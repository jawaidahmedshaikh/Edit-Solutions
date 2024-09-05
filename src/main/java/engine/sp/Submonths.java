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

import java.util.StringTokenizer;

/**
 * This is the implementation of the Submonths instruction.
 * This will subtract a certain number of months from the date. The
 * number of months must be the top entry on the stack.
 * <p>
 * Sample script for subtracting 8 months from 1997/01/04
 * push num:8
 * push dat:1997/01/04
 * submonths
 */
public final class Submonths extends Inst {

    /**
	 * Compiles the instruction
	 * <p>
	 * @param aScriptProcessor  Instance of ScriptProcessor
	 */
	 
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
        //The first item is a date, the second is an integer
        EDITDate calcEngDate = new EDITDate(sp.popFromStack());

        String number = sp.popFromStack();
        StringTokenizer st = new StringTokenizer(number, ".");
        int operand1 = Integer.parseInt(st.nextToken());

		//Subtract months from the date
		EDITDate date = calcEngDate.subtractMonths(operand1);

        String result = date.getFormattedDate();

		sp.push(result);

		// Increment instruction pointer
		sp.incrementInstPtr();
	}

}
		