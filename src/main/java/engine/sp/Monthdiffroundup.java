package engine.sp;

import edit.common.*;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Feb 28, 2006
 * Time: 9:07:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class Monthdiffroundup extends Inst
{
    protected void compile(ScriptProcessor aScriptProcessor)
    {

        sp = aScriptProcessor;  // Save instance of ScriptProcessor

        // Note: No compiling is required for this instruction
    }

    /**
     * Finds the "rounded-up" difference in months between the two specified dates. Partial months are rounded up.
     * The stack order does matter. For example, if the lesser date is pushed on the stack first, then the end result
     * will be positive. However, if the greater date is pushed on the stack first, the end result will be negative.
     * For example:
     * Stack Push 2004/01/01, and then Stack Push 2004/02/01 -> 1
     * Stack Push 2004/02/01, and then Stack Push 2004/01/01 -> -1
     * Stack Push 2004/01/31, and then Stack Push 2004/03/01 -> 2 even though there are only less than 30 days
     * between the two dates.
     * <p/>
     * <p/>
     *
     * @throws SPException If there is an error while
     *                     executing the instruction
     */
    protected void exec(ScriptProcessor execSP) throws SPException
    {

        sp = execSP;
        //Remove the top two items from the data stack

        String operand1 = sp.popFromStack();
        String operand2 = sp.popFromStack();
        String result = null;

        //Create an integer for the datediff method
//        result = (EDITDate.getMonthsBetweenDatesRoundedUp(operand1, operand2)) + "";
        
        EDITDate dateOperand1 = new EDITDate(operand1);
        EDITDate dateOperand2 = new EDITDate(operand2);

        result = dateOperand1.getElapsedMonthsRoundedUp(dateOperand2) + "";

        // Push the result back onto the data stack
        sp.push(result);

        // Increment instruction pointer
        sp.incrementInstPtr();
    }
}
