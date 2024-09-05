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

/**
 * This is the implementation of the Datediff instruction.
 * This will return the number of days between two dates. The
 * order of the numbers is immaterial since it will return the 
 * absolute value.
 * <p>
 * Sample script for difference between 1/1/97 and 1/7/97:
 * push date:1997/01/01
 * push date:1997/07/01
 * datediff
 */
public final class Datediff extends Inst {

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
        String result = null;
        EDITDate editDate1 = new EDITDate(sp.popFromStack());

        EDITDate editDate2 = new EDITDate(sp.popFromStack());

		//Create an integer for the datediff method
        int days = editDate1.getElapsedDays(editDate2);

        result = days + "";

		// Push the result back onto the data stack
		sp.push(result);
		
		// Increment instruction pointer
		sp.incrementInstPtr();
	}

}