/*
 * Length.java      Version 1.1  06/04/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of 
 * Systems Engineering Group, LLC ("Confidential Information"). 
 */

package engine.sp;


/**
 * This is the implementation for the Length instruction.
 * The Length instruction does the following:
 * 1.) Removes the top item from the stack(must be a string).
 * 2.) Determines the number of characters in the string.
 * 3.) Place the number back onto the stack.
 * <p>
 * Sample script for finding length of ABCDEFG:
 * push str:ABCDEFG
 * length
 */
public final class Length extends Inst {

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

        int integer = operand1.length();
		
		// Move the integer to the result
        String result = integer + "";
        
        // Push the addition result back onton the stack
		sp.push(result);

        //Increment instruction pointer
        sp.incrementInstPtr();
      
        }

}