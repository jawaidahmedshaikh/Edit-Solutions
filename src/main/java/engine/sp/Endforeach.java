/**
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: Jul 14, 2003
 * Time: 3:13:37 PM
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement..
 */

package engine.sp;

/**
 * This is the implementation for the endforeach instruction.
 * The endforeach instruction signals the end of a foreach loop.
 * Endforeach sends execution back to the foreach loop.  If no more
 * looping to be done, execution goes to the instruction after the endforeach.
 * <p>
 * For Example the script for the endforeach would be:
 * foreach clientDetailVO
 * push param:clientDetailVO.gender
 * endforeach
 */
public class Endforeach  extends Inst {


    /**
     * Compiles the instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor)  throws SPException {

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

         int returnPoint = 0;

        //Get line number of foreach instruction
        if (sp.loopStackIsEmpty()) {

            sp.incrementInstPtr();
        }
        else
        {
            returnPoint = (Integer) sp.pop(ScriptProcessor.LOOP_STACK);

            //Foreach instruction needs the end pointer
            sp.push(ScriptProcessor.LOOP_STACK, new Integer(sp.getInstPtr()));

            // Reposition instruction pointer
            sp.setInstPtr(returnPoint);
        }
    }
}
