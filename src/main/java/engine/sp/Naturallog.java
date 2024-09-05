/*
 * 
 * User: dlataille
 * Date: May 16, 2007
 * Time: 9:26:30 AM
 * 
 * (c) 2000 - 2007 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 */
package engine.sp;

import edit.common.EDITBigDecimal;
import java.lang.Object;

/**
 * This is the implementation for the Naturallog instruction.
 * The Naturallog instruction will do the following:
 * 1.) Remove the top number from the data stack
 * 2.) Utilize java function log()
 * 3.) Place the result back onto the data stack 
 * <p>
 * Sample script:
 * push num:1.05
 * naturallog
 */
public final class Naturallog extends Inst
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

        // Remove the top item from the data stack and get the naturallog
        double operand1 = new Double(sp.popFromStack()).doubleValue();
        operand1 = Math.log(operand1);
        sp.push(operand1 + "");
        // Increment instruction pointer
        sp.incrementInstPtr();

    }
}