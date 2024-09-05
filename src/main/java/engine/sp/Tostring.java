/*
 * Tostring.java      Version 1.1  06/04/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of 
 * Systems Engineering Group, LLC ("Confidential Information"). 
 */

package engine.sp;



/**
 * This is the implementation for the Tostring instruction.
 * The Tostring instruction will convert the top item on the
 * stack to a String.
 */
public final class Tostring extends Inst {

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
        String op = sp.popFromStack();

        // Push the result onto the data stack
        sp.push(op);

        //Increment instruction pointer
        sp.incrementInstPtr();
      
        }

}