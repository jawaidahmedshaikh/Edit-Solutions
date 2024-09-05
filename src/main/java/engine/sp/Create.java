/**
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: Jul 14, 2003
 * Time: 2:40:38 PM
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;

/**
 * This is the implementation for the Create instruction.
 * The Create instruction is used to add data elements to the document module.
 *
 * <p>
 * Example:
 *  create EDITTrxHistoryVO
 */
public final class Create extends InstOneOperand {

    public Create() throws SPException {

        super();
    }

    /**
     * Compiles the Instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     * @exception SPException   If there is an error while compiling
     */
    public void compile(ScriptProcessor aScriptProcessor) throws SPException {

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
    public void exec(ScriptProcessor execSP) throws SPException {

        sp = execSP;

        String operandName = getOperand().substring(0, getOperand().lastIndexOf(":"));

        //determine if there is an alias
        operandName = checkForAlias(operandName, sp);

        sp.addNewElement(operandName);

        //Increment Instruction Pointer
        sp.incrementInstPtr();
   }
}
