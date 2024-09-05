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
 * This is the implementation for the foreach instruction.
 * The foreach instruction will loop through the document model for each
 * of the type requested and execute a set of instructions within a loop.
 * Endforeach sends execution back to the foreach loop.  If no more
 * looping to be done, execution goes to the instruction after the endforeach.
 * <p>
 * For Example the script for the endforeach would be:
 * foreach clientDetailVO
 * push param:clientDetailVO.gender
 * endforeach
 */
public final class Foreach extends InstOneOperand
{

    public Foreach() throws SPException
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
        boolean lastActiveSet = false;

        //determine if there is an alias
        operandName = checkForAlias(operandName, sp);

        boolean forEachFoundInTable = false;

        //stopLoop process needs to know if there ever was any of the type requested
        if (sp.forEachHTContainsKey(operandName))
        {
            forEachFoundInTable = true;
        }

        lastActiveSet = sp.setLastActiveElement(operandName);

        if (!lastActiveSet)
        {
            if (forEachFoundInTable)
            {
                sp.removeForeachEntry(operandName);
            }
            
            stopLoop(operandName, forEachFoundInTable);
        }
        else
        {
            if (forEachFoundInTable)
            {
                int returnPtr = (Integer) sp.pop(ScriptProcessor.LOOP_STACK);
            }

            sp.push(ScriptProcessor.LOOP_STACK, new Integer(sp.getInstPtr()));

            //Increment Instruction Pointer
            sp.incrementInstPtr();
        }
    }

    protected void stopLoop(String operandName, boolean requestFoundInTable)
    {


        int returnPoint = 0;

        //Get the pointer for the end instruction or look for the endfor instruction

        if (sp.loopStackIsEmpty() || !requestFoundInTable)
        {
            int pointer = sp.getInstPtr() + 1;

            for (int i = pointer; i < sp.getScriptSize(); i++)
            {

                String scriptLine = sp.getScriptElementAt(i);

                if (scriptLine.startsWith("endforeach"))
                {

                    returnPoint = i + 1;
                    break;
                }
            }
        }
        else
        {
            returnPoint = (Integer) sp.pop(ScriptProcessor.LOOP_STACK);

            returnPoint = returnPoint + 1;
        }

        // Reposition instruction pointer
        sp.setInstPtr(returnPoint);
    }
}
