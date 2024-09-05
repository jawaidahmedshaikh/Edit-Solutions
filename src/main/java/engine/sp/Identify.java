/*
 * Identify.java     06/04/2001
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;

/**
 * This is the implementation for the Identify instruction.
 * The Identify instruction will associate a field path
 * with its key field.
 * For example the fieldPath might be "SegmentVO.ClientRelationshipVO"
 * and its key identifier is assinged as ClientRelationshipPK .
 *<p>
 * The Identify instruction will store the name and associated key
 * on the identifier Map.
 */
public final class Identify extends InstOneOperand {

	/**
 	 * Identify constructor
	 *<p>
 	 * @exception SPException 
 	 */


    private String fieldPath = null;
    private String fieldKey  = null;

	public Identify() throws SPException {
    
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
        fieldKey = dataName.substring(lastIndexOfColon + 1, dataName.length());
        if (fieldKey.equals(""))
        {
            throw new SPException("Identify.compile() - Key Field Must Be Defined", SPException.INSTRUCTION_SYNTAX_ERROR);
        }
	}

   	/**
   	 *  Executes the instruction
	 *  <p>
     */
   	protected void exec(ScriptProcessor execSP) throws SPException{

		sp = execSP;

        fieldPath = checkForAlias(fieldPath, sp);

		// Place alias and data name on alias table
		sp.addIdentifierEntry(fieldPath, fieldKey);
				
		//Increment instruction pointer
        sp.incrementInstPtr();
	}
    
}