/*
 * User: cgleason
 * Date: Jun 16, 2004
 * Time: 2:57:24 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.sp;

import edit.common.*;

/**
 * This is the implementation for the Getdaysinmonth instruction.
 * The Getdaysinmonth instruction is used to get the days in the month
 * ffor the date pushed on the stack.
 *
 * <p/>
 * Example:
 * push ws:2004/01/01
 * Getdaysinmonth
 */

public class Getdaysinmonth  extends Inst
{
    /**
       * Compiles the instruction
       * <p>
       * @param aScriptProcessor  Instance of ScriptProcessor
       */

    protected void compile(ScriptProcessor aScriptProcessor)
    {

          sp = aScriptProcessor;  // Save instance of ScriptProcessor

          // Note: No compiling is required for this instruction
    }

	/**
	 *  Execute the instruction
	 *  <p>
	 *  @exception SPException If there is an error while
	 *       executing the instruction
	 */
	protected void exec(ScriptProcessor execSP) throws SPException
    {

        sp = execSP;

        EDITDate editDate = new EDITDate(sp.popFromStack());

        int daysInMonth = editDate.getNumberOfDaysInMonth();
//        int daysInMonth = EDITDate.getDaysInMonth(editDate.getYear(), editDate.getMonth());

 		// Push the result back onto the data stack
		sp.push(daysInMonth + "");

		// Increment instruction pointer
		sp.incrementInstPtr();

    }
}
