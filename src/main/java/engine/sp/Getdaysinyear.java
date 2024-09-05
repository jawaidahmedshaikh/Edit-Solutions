package engine.sp;

import edit.common.*;

/**
 * This is the implementation for the Getdaysinyear instruction.
 * The Getdaysinyear instruction is used to get the days in the year
 * for the date pushed on the stack.
 *
 * <p/>
 * Example:
 * push ws:2004/01/01
 * Getdaysinyear
 */

public class Getdaysinyear  extends Inst
{
	private static final long serialVersionUID = 1L;

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
        
        if (editDate.isLeapYear()) {
        	sp.push(366 + "");
        } else {
        	sp.push(365 + "");
        }

		// Increment instruction pointer
		sp.incrementInstPtr();

    }
}
