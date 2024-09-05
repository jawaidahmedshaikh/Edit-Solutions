/**
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: Jul 14, 2003
 * Time: 3:13:37 PM
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;


/**
 * This is the implementation for the endif instruction.
 * The endif instruction signals the end of the whole if
 * instruction. 
 * <p>
 * For Example the script for the endif would be:
 * if y < x
 * push val:12
 * push val:6
 * else
 * push val:14
 * push val:8
 * endif
 */
public class Endif  extends Inst {


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

         if(sp.ifStackIsEmpty())
        {
            sp.setConditionSwitch(false);
        }

         //Increment Instruction Pointer
         sp.incrementInstPtr();

    }
}
