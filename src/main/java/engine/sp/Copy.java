/*
 * Copy.java      06/04/2001
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;


/**
 * This is the implementation for the Copy instruction.
 * The Copy instruction does the following:
 * 1.) Removes the top an item from the stack.
 * 2.) Copies the the item removed from the stack.
 * 2.) Places the item back onto the stack with copied item.
 * <p>
 * Sample script for copying 14:
 * push val:14
 * copy
 */
public final class Copy extends Inst {

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

        // Remove the the item from the data stack
        String operand1 = sp.popFromStack();

        //Set variable to the stack value
        String result = operand1;

		// Push both items back onton the stack
		sp.push(operand1);
        sp.push(result);

        //Increment instruction pointer
        sp.incrementInstPtr();
      
        }
}