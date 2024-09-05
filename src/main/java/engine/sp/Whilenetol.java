/*
 * Whilenetol.java      Version 1.1  06/04/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of 
 * Systems Engineering Group, LLC ("Confidential Information"). 
 */

package engine.sp;

import edit.common.EDITBigDecimal;


/**
 * This is the implementation for the "Whilenetol" instruction.
 * The "Whilenetol" instruction is used for the looping process
 * and is associated with the "Do" instruction  statement. 
 * <p> 
 * A sample script: 
 * Do 
 *   some instruction... 
 *   some instruction... 
 *   Push num:10000 - number
 *   Push num:12000 - number being compared to 
 *   Push num:1000  - tolerance level
 * Whilenetol
 */
public final class Whilenetol extends Inst {

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
        // Pop the top two items from the data stack and do a comparision

//        double operand3 = Double.parseDouble( sp.popFromStack());
//        double operand2 = Double.parseDouble( sp.popFromStack());
//        double operand1 = Double.parseDouble( sp.popFromStack());
        EDITBigDecimal operand3 = new EDITBigDecimal(sp.popFromStack());
        EDITBigDecimal operand2 = new EDITBigDecimal(sp.popFromStack());
        EDITBigDecimal operand1 = new EDITBigDecimal(sp.popFromStack());

        // build tolerance comparison values
//        double resultAdd = operand2 + operand3;
//        double resultSub = operand2 - operand3;
        EDITBigDecimal resultAdd = operand2.addEditBigDecimal(operand3);
        EDITBigDecimal resultSub = operand2.subtractEditBigDecimal(operand3);

        // Perform comparison based upon numbers
        if (operandCompare(resultAdd, resultSub, operand1)) {
        
            // Get the instruction pointer to the "Do" instruction
            int returnPoint = ((Integer)
                sp.peek(ScriptProcessor.LOOP_STACK)).intValue();
       
            // Reposition instruction pointer 
            sp.setInstPtr(returnPoint);
        
        } else  {
            // Remove instruction pointer from Loop Stack
            sp.pop(ScriptProcessor.LOOP_STACK);
        }
    
        // increment instruction pointer
        sp.incrementInstPtr();
    }

    protected boolean operandCompare(EDITBigDecimal resultAdd, EDITBigDecimal resultSub, EDITBigDecimal operand1){


        if (operand1.isGT(resultAdd))
        {

            return true;
        }

        else if (operand1.isLT(resultAdd))
        {

            return true;
        }

        return false;
    }
}