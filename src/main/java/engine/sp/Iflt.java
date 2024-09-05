/*
 * User: unknown
 * Date: Jun 4, 2001
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.sp;

import edit.common.*;
import fission.utility.Util;

/**
 * This is the implementation for the Iflt instruction.
 * The Iflt intsruction is a conditional call instruction which:
 * will change the instruction pointer to the first instruction of the
 * subroutine indentified by the label within the operands if and only if
 * the first value pushed onto the stack is less than the second.
 */
public final class Iflt extends InstOneOperand {

    /**
     * Pop constructor 
     *<p>
     * @exception SPException 
     */
    public Iflt() throws SPException  {

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

        String label1 = null;
        String label2 = null;
        String label  = null;
        int subStart  = 0;


        // Pop the top two items from the data stack and do a comparision
        String operand2 = sp.popFromStack();
        String operand1 = sp.popFromStack();
        String op = getOperand();

        int ind =  op.indexOf(";");

        if (ind == -1) {
            label1 = op.substring(0, op.length());
        }
        else{
            label1 = op.substring(0, ind);
            label2 = op.substring(ind + 1);
        }

        if (operandsLT(operand1, operand2)) {
       
            // Push current Instruction pointer onto the CallReturn Stack
            sp.push(ScriptProcessor.CALLRETURN_STACK,
                 new Integer(sp.getInstPtr()));
            // Get the label for true condition
            label = label1.substring(0, label1.length() - 1);

            // Get instruction pointer for the specified label
            // (starting point of subroutine)
            subStart = sp.getLabelEntry(label);

            // Set instruction pointer to starting point of subroutine
            sp.setInstPtr(subStart);

        } else  {

             if (ind == -1) {

                 //increment instruction pointer
                 sp.incrementInstPtr();
            }
            else {
                // Push current Instruction pointer onto the CallReturn Stack
                sp.push(ScriptProcessor.CALLRETURN_STACK,
                    new Integer(sp.getInstPtr()));

                // Get the label for false condition
                label = label2.substring(0, label2.length() - 1);

                // Get instruction pointer for the specified label
                // (starting point of subroutine)
                subStart = sp.getLabelEntry(label);

                // Set instruction pointer to starting point of subroutine
                sp.setInstPtr(subStart);
            }
        }
    }
  
    private boolean operandsLT(String operandx, String operandy) throws SPException {
    

        if (Util.isANumber(operandx))
        {
            EDITBigDecimal operandOne = new EDITBigDecimal(operandx);
            EDITBigDecimal operandTwo = new EDITBigDecimal(operandy);
            return operandOne.isLT(operandTwo);             
        }
        else if (EDITDate.isACandidateDate(operandx))
        {
            EDITDate interimdate1 = new EDITDate(operandx);
            EDITDate interimdate2 = new EDITDate(operandy);
            return (interimdate1.before(interimdate2));
        }
        else
        {
            int i = (operandx.compareTo(operandy));
            return (i < 0);
        }
    }
}