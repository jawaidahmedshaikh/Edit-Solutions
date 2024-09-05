/*
 * Do.java       06/04/2001
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;



/**
 * This is the implementation for the Do instruction.
 * The "Do" instruction is used for the looping process
 * and is associated with the "WhileXX" statement.
 * <p>
 * A sample script:
 * Do
 *   some instruction...
 *   some instruction...
 *   Push ws:MonthCounter
 *   Push num:12
 * WhileLE
 */
public final class Do extends Inst {

 	/**
     * Compiles the instruction
	 * <p>
	 * @param aScriptProcessor  Instance of ScriptProcessor
	 */
	protected void compile(ScriptProcessor aScriptProcessor)  {

    	sp = aScriptProcessor;  //Save instance of ScriptProcessor
   
   		//Note: No compiling is required for this instruction
   	}


   	/**
     * Executes the instruction
	 * <p>
	 * @exception SPException If there is an error while
     *      executing the instruction
     */
	protected void exec(ScriptProcessor execSP) throws SPException {

    	sp = execSP;
        // Push the instruction pointer onto the loop stack
        sp.push(ScriptProcessor.LOOP_STACK, new Integer(sp.getInstPtr()));
	
   		// Increment instruction pointer
        sp.incrementInstPtr();
  	}

}