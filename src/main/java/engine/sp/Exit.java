/*
 * Exit.java       06/04/2001
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;



/**
 * This is the implementation for the Exit instruction.
 * The Exit instruction will terminate the script processing.
 * If the Exit is in a subroutine then the instruction pointer
 * will be positioned after the calling instruction (similar to Return)
 */
public final class Exit extends Inst {

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
	 */
   	protected void exec(ScriptProcessor execSP) throws SPException {

        sp = execSP;
        if (sp.stackEmpty(ScriptProcessor.CALLRETURN_STACK)) {
            sp.setStopRun(true);
           //throw new SPException("End of Script Processing"); 
        } else  {
            
            // Pop top entry from callReturn Stack
            int returnPoint = 
                (Integer)sp.pop(ScriptProcessor.CALLRETURN_STACK);
       
            // Set instruction pointer 
			int line = sp.getInstPtr();
            sp.setInstPtr(returnPoint);
			       
            // increment instruction pointer to position after 
            // the call instruction
            sp.incrementInstPtr(line);
			
        }
	}
}