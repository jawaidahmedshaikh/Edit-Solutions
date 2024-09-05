/*
 * Power.java      Version 1.1  06/04/2001
 * 
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved. 
 * 
 * This software is the confidential and proprietary information of  
 * Systems Engineering Group, LLC ("Confidential Information").  
 */

package engine.sp;

import edit.common.EDITBigDecimal;


/** 
* This is the implementation for the Power instruction.
* The Power instruction will return  the result of the first number
* passed in raised to the power of the second number
* (i.e. PassedInValue ** PassedinValue2)
* <p> 
* Sample script for formula (5 * 6):
* push num:5
* push num:6
* power
*/ 
public final class Power extends Inst {

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

        // Remove the top two items from the data stack
//        double operand1 = Double.parseDouble( sp.popFromStack());
//        double operand2 = Double.parseDouble( sp.popFromStack());

        EDITBigDecimal operand1 = new EDITBigDecimal(sp.popFromStack());
        EDITBigDecimal operand2 = new EDITBigDecimal(sp.popFromStack());

        // calculate  operand2 ** operand1
		EDITBigDecimal result = new EDITBigDecimal(Math.pow(operand2.doubleValue(), operand1.doubleValue()) + "");

        // Push the result back onto the data stack
		sp.push(result.toString());

        // Increment instruction pointer
		sp.incrementInstPtr(); 
	}
}
		