/*
 * Log.java      Version 1.1  06/04/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of 
 * Systems Engineering Group, LLC ("Confidential Information"). 
 */

package engine.sp;

import edit.common.EDITBigDecimal;


/**
 * This is the implementation for the Log instruction.
 * The Log instruction will return the natural logarithm (base e) 
 * of the passedInValue value. The passedInValue will be the top item on
 * the data stack
 */
public final class Log extends Inst {

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
        // Remove the top item from the data stack
//        double num = Double.parseDouble( sp.popFromStack());
        EDITBigDecimal number = new EDITBigDecimal(sp.popFromStack());

        // calculate  log(num)
//        Double operand = new Double(Math.log(number.doubleValue()));
        EDITBigDecimal operand = new EDITBigDecimal(Math.log(number.doubleValue()) + "");

        // Push the result back onto the data stack
        sp.push(operand.toString());

        // Increment instruction pointer
        sp.incrementInstPtr();
    }
}