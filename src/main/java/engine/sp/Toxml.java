/*
 * ToXML.java      Version 1.1  06/04/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may not be copied in whole or in part
 * without the written permission of Systems Engineering Group, LLC.
 */

package engine.sp;

import fission.dm.SMException;

/**
 * This is the implementation for the ToXML instruction.
 * The Toxml instruction will be used to dump all data
 * in the output and parameter hashtables to XML.
 * <p>
 * The instruction will loop through the outputData hashtable to determine
 * the elements which are single output parms and the outputs which 
 * are array outputs. It will build DDGroupItems for the array elements
 * and use each elements toXML method to dump the contents to an XML file.
 * The name and location of the XML file will be determined
 * by the one operand pushed into this function.
 * <p>
 * Example 1:
 * toxml str:"c:\xmlres.xml"
 * <p>
 * Example 2:
 * push str:"c:\xmlres.xml"
 * toxml
 */
public final class Toxml extends InstOneOperand {

    /**
     * Constructor
     */
    public Toxml() throws SPException  {
        super();
    }    
        
    /**
     * Compiles the instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor) throws SPException{
    
        sp = aScriptProcessor;  //Save instance of ScriptProcessor
   
        // Get the operand from the instruction
        String s = getInstAsEntered();
        if (Inst.containsOperand(s))  {
           commonCompileTasks();
           if (! isStringOperandType() ) {
                throw new SPException(
                    "Error in toxml instruction - String operand (output file name) required", SPException.INSTRUCTION_SYNTAX_ERROR);
					
           }
        }    
    }


    /**
     *  Executes the instruction
     *  <p>
     *  @exception SPException If there is an error while 
     *        executing the instruction
     */
    protected void exec(ScriptProcessor execSP) throws SPException {

        sp = execSP;
            String topItem = null;
            if (getOperand() == null)
               topItem = execWithNoOperand();
            else if (getOperand().length() > 0) 
               topItem = execWithOperand();
            else
               topItem = execWithNoOperand();
            
            // Execute script processor method to send data
			//  in parameter/output hashtables to XML file named in 
			//  operand
//            sp.sendOutputToXML(topItem);

        //Increment instruction pointer
        sp.incrementInstPtr();
      
    }

    // toXML instruction with operand
    private String execWithOperand() throws SPException {
    
        return getOperandDataName();
    }

    // toXML instruction with no operand
    private String execWithNoOperand() throws  SPException  {

        // Get the data

        String topItem = sp.popFromStack();
        return topItem;
    }

}
