/*
 * Getscript.java      06/04/2001
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;



/**
 * This is the implementation for the Getscript instruction.
 * The Getscript instruction will change the instruction pointer
 * to the first instruction of the subroutine,
 * identified by the label found in the operand.
 */
public final class Getscript extends InstOneOperand {

    /**
     * Pop constructor 
     *<p>
     * @exception SPException 
     */
    public Getscript() throws SPException  {

        super();
    }
    
    /**
     * Compiles the instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor) 
            throws SPException {
    
        sp = aScriptProcessor;  //Save instance of ScriptProcessor
   
        // Does common tasks in superclass such as
        // setting member variables and some operand editting
        commonCompileTasks();        
    }


    /**
     *  Executes the instruction
     *  <p>
     *  @exception SPException If there is an error while 
     *       executing the instruction
     */
   	protected void exec(ScriptProcessor execSP) throws SPException {

        sp = execSP;
        // Push current Instruction pointer onto the CallReturn Stack
        sp.push(ScriptProcessor.CALLRETURN_STACK,
            new Integer(sp.getInstPtr()));
        
        // Get instruction pointer for the specified label 
        // (starting point of subroutine)
        String op = getOperand();
        String label = op.substring(0, op.length() - 1);  
        int subStart = sp.getLabelEntry(label);
        
        // Set instruction pointer to starting point of subroutine
        sp.setInstPtr(subStart);
        
    }
    
}