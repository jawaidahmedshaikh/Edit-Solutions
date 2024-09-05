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

import fission.utility.Util;
import edit.common.*;


/**
 * This is the implementation of the Set instruction.
 * This will set a specific value into an existing date
 * string of year/month/day.
 * <p>
 * Sample script for settin 12 into days field of 1997/01/04
 * push val:day
 * push val:12
 * push val:1997/01/04
 * set
 */
public final class Set extends Inst {

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

	    //Remove the top three items from the data stack
        //The first item is a date
        String dateToModify = sp.popFromStack();

        //The second item is an integer
       String modifyToValue = sp.popFromStack();

        //The third item is the field type to be modified
        String fieldType = sp.popFromStack();

        if (fieldType.equalsIgnoreCase("month") ||
            fieldType.equalsIgnoreCase("day")   ||
            fieldType.equalsIgnoreCase("year"))
        {
            dateToModify = modifyDate(dateToModify, modifyToValue, fieldType);
        }

		// Push the result back onto the data stack
		sp.push(dateToModify);

		// Increment instruction pointer
		sp.incrementInstPtr();
	}

    private String modifyDate(String dateToModify, String modifyToValue, String fieldType)
    {
        EDITDate date = new EDITDate(dateToModify);

        if (fieldType.equalsIgnoreCase("year"))
        {
            date = new EDITDate(modifyToValue, date.getFormattedMonth(), date.getFormattedDay());
        }
        else if (fieldType.equalsIgnoreCase("month"))
        {
            date = new EDITDate(date.getFormattedYear(), modifyToValue, date.getFormattedDay());
        }
        else if (fieldType.equalsIgnoreCase("day"))
        {
            date = new EDITDate(date.getFormattedYear(), date.getFormattedMonth(), modifyToValue);
        }

        return date.getFormattedDate();
    }
}