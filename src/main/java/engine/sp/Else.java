/**
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: Jul 16, 2003
 * Time: 11:01:28 AM
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;


/**
 * This is the implementation for the Else instruction.
 * The Else instruction signals the end of the true processing
 * for an if instruction.  Then looks for the endif to set execution
 * to the statement after the endif.
 * <p>
 * For Example the script for the else would be:
 * if y < x
 * push val:12
 * push val:6
 * else
 * push val:14
 * push val:8
 * endif
 */
public class Else extends Inst  {


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


        boolean condition = sp.getConditionSwitch();

        boolean ifResult = false;

        
        
        if(sp.ifStackIsEmpty())
        {
            sp.incrementInstPtr();
        }
        else
        {
            ifResult = (Boolean) sp.pop(ScriptProcessor.IF_STACK);
            
        }
                
        if (ifResult)
        {
           //condition is true, look for endif instruction
            int scriptSize = sp.getScriptSize();
            int ifCounter = 0;
            
            for (int i = 0; i < scriptSize; i++)
            {

                sp.incrementInstPtr();
                String operator = (Inst.extractOperator(sp.getScriptElementAt(sp.getInstPtr())));
                
                // account for if/endif pairs within else
                if (operator.equalsIgnoreCase("if"))
                {
                    ifCounter++; 
                }
                
                if (operator.equalsIgnoreCase("endif"))
                {
                	if (ifCounter > 0)
                    {
                    	ifCounter--;
                    }
                    else
                    {
                    	sp.incrementInstPtr();
                    	break;
                    }
                }
            }
        }
        else
        {
            //condition if false, execute at next instruction and reset condition switch
            //sp.setConditionSwitch(false);

            //Increment Instruction Pointer
            sp.incrementInstPtr();
        }
    }
}
