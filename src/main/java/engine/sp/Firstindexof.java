package engine.sp;

//import engine.sp.custom.function.FunctionCommand;

/**
 * This is the implementation for the Firstindexof instruction.
 * 
 * The firstindexof instruction does the following:
 * 1.) Removes the top 2 items from the stack (text to search for, string to search in).
 * 2.) Runs the java indexof command
 * 3.) Place the result back onto the stack.
 * <p>
 * Sample scripts for getting firstindexof a string:
 * push val:225
 * push val:1001225
 * firstindexof
 * RESULT: 4
 */

public class Firstindexof extends Inst {

    /**
     * Compiles the instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor) {
    
        sp = aScriptProcessor;  //Save instance of ScriptProcessor
   
        //Note: No compiling is required for this instruction
    }
    
    /**
     *  Executes the instruction
     *  <p>
     *  @exception SPException If there is an error while 
     *        executing the instruction
     */
    protected void exec(ScriptProcessor execSP) throws SPException {

        sp = execSP;
        
       // String searchToken = (String) sp.getWS().get("SearchToken");
		//String searchText = (String) sp.getWS().get("SearchText");
		
        String searchText = sp.popFromStack();
		String searchToken = sp.popFromStack();
		
		Integer position = null;
		
		if(searchText == null || searchToken == null) {
			position = -1;
		} else {
			position = searchText.indexOf(searchToken);
		}
				
		 // Push the result back onto the stack
        sp.push(position.toString());

        //Increment instruction pointer
        sp.incrementInstPtr();
       
    }

}
