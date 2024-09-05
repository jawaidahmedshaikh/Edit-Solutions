/*
 * Return.java      Version 1.1  06/04/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of 
 * Systems Engineering Group, LLC ("Confidential Information"). 
 */

package engine.sp;



/**
 * This is the implementation for the Return instruction.
 * The Return instruction will remove the top entry from
 * the callReturn stack and sets the instruction pointer
 * to that address.
 */
public final class Return extends Inst {

    /**
     * Compiles the instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor) {
    
        sp = aScriptProcessor;  //Save instance of ScriptProcessor
   
        // Note: No compiling is required for this instruction
    }


    /**
     *  Executes the instruction
     *  <p>
     *  @exception SPException If there is an error while 
     *       executing the instruction
     */
   	protected void exec(ScriptProcessor execSP) throws SPException {

       sp = execSP;
       // Pop top entry from callReturn Stack
       int returnPoint = (Integer) sp.pop(ScriptProcessor.CALLRETURN_STACK);
       
       // Set instruction pointer 
       sp.setInstPtr(returnPoint);
       
       // increment instruction pointer to position after the call instruction
       sp.incrementInstPtr();
    }
    
}