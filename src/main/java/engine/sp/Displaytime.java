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
 * This is the implementation of the Displaytime command. This 
 * command will output the time to the system log. It also allows the
 * user to place a string on the stack which can be used as a label 
 * to help index where in the script this time was taken. Sample script:
 * push str: "Beginning of loop"
 * displaytime 
 */
 
public final class Displaytime extends Inst {

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
        // Get String from top of stack
        String operand = sp.popFromStack();

        // Set result
        String theTime = new EDITDateTime().getFormattedDateTime();

        // Print time to system
        System.out.println(operand);
        System.out.println(theTime);

        // Increment instruction pointer
        sp.incrementInstPtr();

    }

}
 