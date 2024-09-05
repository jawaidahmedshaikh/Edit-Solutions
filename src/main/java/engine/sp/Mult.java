/*
 * Mult.java      Version 1.1  06/04/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of 
 * Systems Engineering Group, LLC ("Confidential Information"). 
 */

package engine.sp;

import edit.common.EDITBigDecimal;


/**
 * This is the implementation for the Mult instruction.
 * The Multiply (Mult) instruction does the following:
 * 1.) Removes the top two numbers from the data stack
 * 2.) Multiplies the two numbers
 * 3.) Places the result onto the data stack
 * <p>
 * Sample script for formula 12 * 6:
 * push num:12
 * push num:6
 * mult
 */
public final class Mult extends Inst {

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
     *  @exception SPException  If there is an error while 
     *      executing the instruction
     */
    protected void exec(ScriptProcessor execSP) throws SPException  {

        sp = execSP;

        // Remove and Multiply the top two items from the data stack
//        double operand1 = Double.parseDouble( sp.popFromStack());
//        double operand2 = Double.parseDouble( sp.popFromStack());
//        String result = ((operand1 * operand2) + "");

        EDITBigDecimal operand1 = new EDITBigDecimal(sp.popFromStack());
        EDITBigDecimal operand2 = new EDITBigDecimal(sp.popFromStack());

        operand1 = operand1.multiplyEditBigDecimal(operand2);
        //Push the multiplication result back onton the stack
        sp.push(operand1.toString());

        //Increment instruction pointer
        sp.incrementInstPtr();
    }

}