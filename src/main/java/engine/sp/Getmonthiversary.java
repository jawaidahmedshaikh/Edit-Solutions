/**
 * User: dlataill
 * Date: May 16, 2007
 * Time: 9:41:53 AM
 * <p/>
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package engine.sp;

import edit.common.EDITDate;

/**
 * This is the implementation for the Getmonthiversary instruction.
 * The Getmonthiversary instruction will do the following:
 * 1.) Remove the top two items from the data stack (they will both be dates)
 *      The first entry popped from the stack will be the date from which you need to calculate the specified mothiversary
 *      The second entry popped from the stack will be the date which identifies the monthly anniversary
 * 2.) Retrieve the working storage value "Direction" - this value will determine if you are to calculate the prior
 *      monthiversary or the next mothiversary - valid values are "Next" and "Prior"
 * 3.) Based on the value of "Direction" (from working storage), calculate the specified monthiversary
 * 4.) Placed the monthiversary date on the data stack
 * <p>
 */
public class Getmonthiversary extends Inst
{
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

        EDITDate startingDate = new EDITDate(sp.popFromStack());
        EDITDate anniversaryDate = new EDITDate(sp.popFromStack());
        String direction = (String) sp.getWSEntry("Direction");

        EDITDate monthiversary = null;

        if (direction.equalsIgnoreCase("Next"))
        {
            monthiversary = anniversaryDate.getNextMonthiversaryDate(startingDate);
        }
        else if (direction.equalsIgnoreCase("Prior"))
        {
            monthiversary = anniversaryDate.getPriorMonthiversaryDate(startingDate);
        }
        else
        {
            monthiversary = startingDate;
        }

        sp.push(monthiversary.getFormattedDate());

        // Increment instruction pointer
        sp.incrementInstPtr();
    }
}
