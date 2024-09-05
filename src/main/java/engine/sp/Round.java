/*
 * Round.java      Version 1.1  06/04/2001
 * 
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved. 
 * 
 * This software is the confidential and proprietary information of  
 * Systems Engineering Group, LLC ("Confidential Information").  
 */

package engine.sp;

import fission.utility.Util;
import edit.common.EDITBigDecimal;

/** 
* This is the implementation for the Round instruction.
* The Round instruction will take the number on top of the stack,
* add .5 to it, then truncate the result.
* (i.e. 5.68 will return 6)
* <p> 
* Sample script for rounding 7.8:
* push num:7.8
* round
*/ 
public final class Round extends InstOneOperand {

    /**
     * Activate constructor
     * <p>
     * @exception SPException
     */
    public Round() throws SPException {

       super();
    }

    /**
	* Compiles the instruction 
	* <p> 
	* @param aScriptProcessor  Instance of ScriptProcessor 
	*/ 
	protected void compile(ScriptProcessor aScriptProcessor) throws SPException {
    
        sp = aScriptProcessor;  //Save instance of ScriptProcessor
   
        //Does common tasks in superclass such as
        //setting member variables and some operand editting
        commonCompileTasks();
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
        String op = getOperand();
        int ind =  op.indexOf(":");
        int decimals = Integer.parseInt(op.substring(0, ind));
//        double operand1 = Double.parseDouble( sp.popFromStack());
        EDITBigDecimal operand1 = new EDITBigDecimal(sp.popFromStack());

        // round operand
//		double roundedResult = Util.roundDouble(operand1, decimals);
        operand1.round(decimals);
//        String result   = roundedResult + "";

        // Push the result back onto the data stack
//		sp.push(result);
        sp.push(operand1.toString());

        // Increment instruction pointer
		sp.incrementInstPtr();
		}
    
}