/**
 * User: dlataill
 * Date: Aug 11, 2006
 * Time: 10:16:56 AM
 * <p/>
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package engine.sp;

public class Clearlastactiveelement extends InstOneOperand
{
    public Clearlastactiveelement()
    {
        super();
    }

    /**
     * Compiles the Instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     * @exception SPException   If there is an error while compiling
     */
    public void compile(ScriptProcessor aScriptProcessor) throws SPException
    {
        //Get instance of ScriptProcessor
        sp = aScriptProcessor;

        //Does common tasks in superclass such as
        //setting member variables and some operand editting
        commonCompileTasks();

    }

    /**
     * Executes Instruction
     * <p>
     * @exception SPException  If there is an error while
     *      executing the Instruction
     */
    public void exec(ScriptProcessor execSP) throws SPException
    {
        sp = execSP;

        String operandName = getOperand().substring(0, getOperand().lastIndexOf(":"));

        //determine if there is an alias
        operandName = checkForAlias(operandName, sp);

        sp.removeLastActiveElement(operandName);

        //Increment Instruction Pointer
        sp.incrementInstPtr();
    }
}
