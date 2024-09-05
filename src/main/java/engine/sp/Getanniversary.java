package engine.sp;

import edit.common.EDITDate;

/**
 * This is the implementation for the Getanniversary instruction.
 * The Getanniversary instruction will do the following:
 * 1.) Remove the top two items from the data stack (they will both be dates)
 *      The first entry popped from the stack will be the date from which you need to calculate the specified anniversary
 *      The second entry popped from the stack will be the date which identifies the anniversary
 * 2.) Retrieve the working storage value "Direction" - this value will determine if you are to calculate the prior
 *      anniversary or the next anniversary - valid values are "Next" and "Prior"
 * 3.) Based on the value of "Direction" (from working storage), calculate the specified anniversary
 * 4.) Placed the anniversary date on the data stack
 * <p>
 */
public class Getanniversary extends Inst
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

        EDITDate anniversary = null;

        if (direction.equalsIgnoreCase("Next"))
        {
        	anniversary = anniversaryDate.getNextAnniversaryDate(startingDate);
        }
        else if (direction.equalsIgnoreCase("Prior"))
        {
        	anniversary = anniversaryDate.getPriorAnniversaryDate(startingDate);
        }
        else
        {
        	anniversary = startingDate;
        }

        sp.push(anniversary.getFormattedDate());

        // Increment instruction pointer
        sp.incrementInstPtr();
    }
}

