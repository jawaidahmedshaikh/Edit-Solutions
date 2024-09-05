/*
 * Ifnetol.java      06/04/2001
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;

import edit.common.EDITBigDecimal;


/**
 * This is the implementation for the Ifnetol instruction.
 * The Ifnetol intsruction is a conditional call instruction which:
 * will change the instruction pointer to the first instruction of the
 * subroutine indentified by the label within the operands if and only if
 * the first value pushed onto the stack is less than the second operand
 * plus the third operand AND the first operand is greater than the
 * second operand minus the third.
 * Sample script:
 * push num:10000
 * push num:12000
 * push num:1000
 * ifnetol
 * In this case it does not fall within tolerance
 */
public final class Ifnetol extends InstOneOperand {

    /**
     * Pop constructor 
     *<p>
     * @exception SPException 
     */
    public Ifnetol() throws SPException  {

        super();
    }
    
    /**
     * Compiles the instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor) 
            throws SPException {
    
        sp = aScriptProcessor;  //Save instance of ScriptProcessor
   
        // Does common tasks in superclass such as
        // setting member variables and some operand editting
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

        // Pop the top two items from the data stack and do a comparision
        EDITBigDecimal operand3 = new EDITBigDecimal(sp.popFromStack());
        EDITBigDecimal operand2 = new EDITBigDecimal(sp.popFromStack());
        EDITBigDecimal operand1 = new EDITBigDecimal(sp.popFromStack());

        EDITBigDecimal resultAdd = operand2.addEditBigDecimal(operand3);
        EDITBigDecimal resultSub = operand2.subtractEditBigDecimal(operand3);

        // Perform comparison based upon numbers
        if (operandCompare(resultAdd, resultSub, operand1)) {
         
            // Push current Instruction pointer onto the CallReturn Stack
            sp.push(ScriptProcessor.CALLRETURN_STACK,
                new Integer(sp.getInstPtr()));

            // Get instruction pointer for the specified label
            // (starting point of subroutine)
            String op = getOperand();
            String label = op.substring(0, op.length() - 1);
            int subStart = sp.getLabelEntry(label);

            // Set instruction pointer to starting point of subroutine
            sp.setInstPtr(subStart);

        } else  {
        // increment instruction pointer
            sp.incrementInstPtr();
        }    
    }

   protected boolean operandCompare(EDITBigDecimal resultAdd, EDITBigDecimal resultSub, EDITBigDecimal operand1){


        if (operand1.isGT(resultAdd))
        {

            return true;
        }

        else if (operand1.isLT(resultSub))
        {

            return true;
        }

        return false;
    }
}