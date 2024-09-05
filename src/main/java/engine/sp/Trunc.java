/*
 * Trunc.java      Version 1.1  06/04/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of 
 * Systems Engineering Group, LLC ("Confidential Information"). 
 */

package engine.sp;



import fission.utility.Util;

/**
 * This is the implementation for the Trunc instruction.
 * The Trunc instruction does the following:
 * 1.) Removes the top item from the stack.
 * 2.) Determines the integer part.
 * 3.) Place the integer portion back onto the stack.
 * <p>
 * Sample script for truncating 12.6342:
 * push num:12.6342
 * trunc
 */
public final class Trunc extends Inst {

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

        // Remove the top item from the data stack
        String operand1 = sp.popFromStack();
        String[] tokens = Util.fastTokenizer(operand1, ".");

        String result = tokens[0];

        // Push the addition result back onton the stack
		sp.push(result);

        //Increment instruction pointer
        sp.incrementInstPtr();
      
    }

}