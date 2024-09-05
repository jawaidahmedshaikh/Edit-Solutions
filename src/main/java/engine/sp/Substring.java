/*
 * Substring.java
 */

package engine.sp;

import fission.utility.Util;


/**
 * This is the implementation for the Substring instruction.
 * The starting index of the substring is INCLUSIVE of that index
 * The ending index of the substring is EXCLUSIVE of that index
 * 
 * The Substring instruction does the following:
 * 1.) Removes the top 3 items from the stack (first index of substring inclusive, last index of substring exclusive, string).
 * 2.) Runs the java substring command
 * 3.) Place the result back onto the stack.
 * <p>
 * Sample scripts for getting a substring:
 * push val:6
 * push val:toEnd
 * push val:StringToTest
 * substring
 * RESULT: ToTest
 *
 * push val:0
 * push val:3
 * push val:abcde
 * substring
 * RESULT: abc
 */
public final class Substring extends Inst {

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
    protected void exec(ScriptProcessor execSP) throws SPException
    {

        sp = execSP;
        // Remove the top 3 items from the data stack
        String str = sp.popFromStack();
        String endIndexString = sp.popFromStack();
        Integer startIndex = new Integer(sp.popFromStack());

        if (endIndexString.equalsIgnoreCase("toEnd"))
        {
        	str = str.substring(startIndex);
        } else if (Util.isANumber(endIndexString)) 
        {
        	Integer endIndex = new Integer(endIndexString);
        	str = str.substring(startIndex, endIndex);
        }

        // Push the appended result back onto the stack
        sp.push(str);

        //Increment instruction pointer
        sp.incrementInstPtr();
    }
}