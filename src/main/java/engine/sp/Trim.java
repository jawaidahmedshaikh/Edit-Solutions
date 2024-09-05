/*
 * Trim.java      Version 1.1  06/04/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of 
 * Systems Engineering Group, LLC ("Confidential Information"). 
 */

package engine.sp;



/**
 * This is the implementation for the Trim instruction.
 * The Trim instruction does the following:
 * 1.) Removes the top string from the stack.
 * 2.) Removes any spaces from the beginning or end of string.
 * 3.) Place the new string back onto the stack.
 * <p>
 * Sample script for trimming  ABC  :
 * push str:   ABC
 * trim
 */
public final class Trim extends Inst {

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

        // Trim the result
        String result = operand1.trim();
		
        // Push the addition result back onton the stack
		sp.push(result);

        //Increment instruction pointer
        sp.incrementInstPtr();
      
        }

}