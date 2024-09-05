/*
 * Label.java      Version 1.1  06/04/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of 
 * Systems Engineering Group, LLC ("Confidential Information"). 
 */

package engine.sp;

/**
 * This is the implementation for the Label instruction.
 * This instruction is used to position the instruction
 * pointer at a certain place in a script. For example
 * a Label can be defined at the beginning of the script.
 * A Label can also be used to define the location of
 * a subroutine (Ex. Call labelname).
 */
public final class Label extends Inst {

    /**
     * Compiles the instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor theProcessor) {
    
        //Get instance of ScriptProcessor
        sp = theProcessor;
		
        //Place Label on the ScripProcessor Label Map
        String op = getInstAsEntered();
        String label = op.substring(0, op.length() - 1);
        sp.putLabelEntry(label, getInstLineNr());
        //theProcessor.putLabelEntry(label, theProcessor.getInstPtr() + 1);
    }
	
    /**
     * Executes the instruction
     * <p>
     * @exception SPException If there is an error while executing instruction
     */
    protected void exec(ScriptProcessor execSP) throws SPException {

        sp = execSP;
        //Increments the instruction pointer by one
        sp.incrementInstPtr();
    }

}