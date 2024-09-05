/*
 * Comment.java      06/04/2001
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;

/**
 * This is the implementation for the Comment instruction.
 * The comment instruction was created so users can incorporate
 * comments into their scripts.
 */
public final class Comment extends Inst {

	/**
 	 * Compiles the instruction 
	 * <p>
	 * @param theScriptProcessor  Instance of ScriptProcessor
 	 */
	protected void compile(ScriptProcessor theProcessor) {
    
		sp = theProcessor; //Save instance of ScriptProcessor
	}

	/**
 	 * Executes the instruction
	 * <p>
	 * @exception SPException If there is an error while 
     *      executing the instruction 
 	 */
	protected void exec(ScriptProcessor execSP) throws SPException
    {

        sp = execSP;
	    //Increment the instruction pointer by one
		sp.incrementInstPtr(); 
	}
}