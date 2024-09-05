/*
 * Peek.java      Version 1.1  06/04/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of 
 * Systems Engineering Group, LLC ("Confidential Information"). 
 */

package engine.sp;

/**
 * This is the implementation of the Peek Instruction.
 * This instruction is used to look at the top object from
 * the data stack.
 */
public final class Peek extends InstOneOperand
{

    /**
     * Peek constructor
     * <p/>
     * @throws SPException
     */
    public Peek() throws SPException
    {

        super();
    }

    /**
     * Compiles the Instruction
     * <p/>
     * @param aScriptProcessor Instance of ScriptProcessor
     * @throws SPException If there is an error while compiling
     */
    public void compile(ScriptProcessor aScriptProcessor) throws SPException
    {

        //Get instance of ScriptProcessor
        sp = aScriptProcessor;

        //Does common tasks in superclass such as
        //setting member variables and some operand editting
        commonCompileTasks();

        // Only Allow Peek to Working Storage (WS) or Indexed Storage (IS)
        if (!isWSReference())
        {
            throw new SPException("Peek.compile() - Invalid Operand: " +
                    getOperand(), SPException.INSTRUCTION_SYNTAX_ERROR);
        }
    }

    /**
     * Executes Instruction
     * <p/>
     * @throws SPException If there is an error
     *                     while executing the Instruction
     */
    public void exec(ScriptProcessor execSP) throws SPException
    {

        sp = execSP;
        String ot = getOperandType();

        if (ot.equalsIgnoreCase(OPERANDTYPE_WS))
        {
            execWS();
        }
        else
        {
            throw new SPException("Peek.exec() - Invalid Operand", SPException.INSTRUCTION_SYNTAX_ERROR);
        }

        //Increment Instruction Pointer
        sp.incrementInstPtr();
    }

    /**
     * Support method for exec().
     * Executes instruction with Working Storage
     */
    private void execWS() throws SPException
    {

        String odn = getOperandDataName();
        String subscriptName = getSubscriptName();
        String data = null;
        data = sp.peekFromStack();

        if (odn == null || odn.length() == 0)
        {
            sp.addWSEntry(getExternalName(), data);
        }
        else
        {
            // Change name of DD to operand data name
            setExternalName(odn);

            if (subscriptName != null && subscriptName.length() > 0)
            {

                int subscript = Integer.parseInt(sp.getWSEntry(subscriptName));
                sp.addWSEntry(getExternalName() + subscript, data);
            }

            else
            {

                sp.addWSEntry(getExternalName(), data);
            }
        }
    }
}