/*
 * Swap.java      Version 1.1  06/04/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of 
 * Systems Engineering Group, LLC ("Confidential Information"). 
 */

package engine.sp;


/**
 * This is the implementation for the Swap instruction.
 * The Swap instruction does the following:
 * 1.) Removes the top two items on the stack.
 * 2.) Inverts the position of the two items.
 * 2.) Places the items back onto the stack.
 * <p>
 * Sample script for swapping 12, 4 to 4, 12:
 * push num:12
 * push num:4
 * swap
 */
public final class Swap extends Inst {

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
        // Remove the top two items from the data stack
        String operand1 = sp.popFromStack();
        String operand2 = sp.popFromStack();

        String result1 = operand1;
		String result2 = operand2;
		  
		sp.push(result1);
		sp.push(result2);

        //Increment instruction pointer
        sp.incrementInstPtr();
        }
}