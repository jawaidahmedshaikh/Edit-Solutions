/*
 * Alias.java      06/04/2001
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;

/**
 * This is the implementation for the Alias instruction.
 * The Alias instruction will associate a string that
 * is usually a data compound name with an alias.
 * For example the string might be "contract.Client.BasicCovg"
 * that can be assigned to an alias such as &BasicCovg.
 *<p>
 * The Alias instruction stores the alias name and associated string
 * on the alias Map.
 */
public final class Alias extends InstOneOperand {

	/**
 	 * Alias constructor
	 *<p>
 	 * @exception SPException 
 	 */


    private String fieldPath = null;
    private String alias = null;


	public Alias() throws SPException {
    
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
   		
        //Does common tasks in superclass such as
        //setting member variables and some operand editting
        commonCompileTasks();


        int lastIndexOfColon = 0;

        String dataName = getOperand();
        fieldPath = dataName.substring(0, lastIndexOfColon = dataName.indexOf(":"));
        alias = dataName.substring(lastIndexOfColon + 1, dataName.length());
        if (!alias.startsWith("&"))
        {
            throw new SPException("Alias.compile() - Alias Must Begin With An '&'", SPException.INSTRUCTION_SYNTAX_ERROR);
        }
	}

   	/**
   	 *  Executes the instruction
	 *  <p>
	 *  @exception SPException If there is an error while executing the    
     *      instruction
     */
   	protected void exec(ScriptProcessor execSP) throws SPException {

		sp = execSP;

		// Place alias and data name on alias table
		sp.addAliasEntry(alias, fieldPath);
				
		//Increment instruction pointer
        sp.incrementInstPtr();
	}
}