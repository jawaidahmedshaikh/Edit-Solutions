/*
 * Counter.java     02/19/2002
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;


/**
 * This is the implementation for the Counter instruction.
 * The Counter instruction is used to add or subtract from the variable
 * defined within the instruction.
 *
 * <p>
 * Example:
 *  counter add;ws:xxxxx
 *  counter sub;ws:xxxxx
 */
public final class Counter extends InstOneOperand {

    /**
     * Activate constructor
     * <p>
     * @exception SPException
     */
     public Counter() throws SPException {

        super();
     }

    /**
     * Compiles the instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor) throws SPException{

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
        //the ws fields are in a hashtable
		//Map ws = sp.getWSdata();

        String arg2 = null;

        //get the operand, the whole string
        String op = getOperand();

        //parse the string into the separate activity and ws field
        int ind =  op.indexOf(";");
        String var1 = op.substring(0, ind);
        String var2 = op.substring(ind + 1);

        //parse each variable into type of variable and variable name
        ind =  var2.indexOf(":");
   		arg2 = var2.substring(ind + 1);

        String counter = ((String)(sp.getWSEntry(arg2)));
//        int number = Integer.parseInt((String)(sp.getWSEntry(arg2)));

        if (counter.endsWith(".0"))
        {
            counter = counter.substring(0, counter.lastIndexOf("."));
        }

        int number = Integer.parseInt(counter);
        if (var1.equalsIgnoreCase("add")) {

            number++;
        }
        else if (var1.equalsIgnoreCase("sub")) {

            number--;
        }

        sp.addWSEntry(arg2, (number + ""));

        // Increment instruction pointer
        sp.incrementInstPtr();
    }
}