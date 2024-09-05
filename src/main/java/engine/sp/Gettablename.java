/*
 * Gettablename.java       06/04/2001
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;


/**
 * This is the implementation for the Gettablename instruction.
 * The Gettablename instruction is used to get the tablename for the
 * rule specified.  This rule was placed in the ws hashtable by Gettable
 * instruction. Place the name retrieved onto the stack.
 *
 * Ex.
 * 
 *	rule = Expense1
 *	tablename = Per1000Expense
 *
 * Description,string=Per1000Expense
 *
 */
public final class Gettablename extends InstOneOperand {

    /**
     * Gettablename constructor
     * <p> 
     * @exception SPException  
     */ 
     public Gettablename() throws SPException {
    
        super(); 
     }
     
    /**
     * Compiles the instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor) throws SPException {
    
        sp = aScriptProcessor;  //Save instance of ScriptProcessor
   
        //Does common tasks in superclass such as
        //setting member variables and some operand editting
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
	    
	    String operand = this.getOperand();
        
        if (operand.startsWith("ws:")) {
            int colonIndex = operand.indexOf(":");
            String operandDataName = operand.substring(colonIndex + 1);
            operand = (String) sp.getWSEntry(operandDataName);
        } else {
        	operand = extractOperandDataName();
        }
        
		// Get the specific rule out of ws for the rulename
        String data = (String) sp.getWSEntry(operand);

		// Put the rule to the stack
        sp.push(data);

        // Increment instruction pointer
        sp.incrementInstPtr();
    }
}