/*
 * Pop.java      Version 1.1  06/04/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may not be copied in whole or in part
 * without the written permission of Systems Engineering Group, LLC.
 */

package engine.sp;

/**
 * This is the implementation of the Pop Instruction.
 * This instruction is used to remove the top object from
 * the data stack and place it in the location specified
 * by the operand (Ex. Pop WS:).
 */
public final class Pop extends InstOneOperand
{

    /**
     * Pop constructor
     * <p/>
     * @throws SPException
     */
    public Pop() throws SPException
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

        // Only Allow Pop to Working Storage (WS) or Indexed Storage (IS)
        if (!isWSReference())
        {
            throw new SPException("Pop.compile() - Invalid Operand: " +
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
            throw new SPException("Pop.exec() - Invalid Operand", SPException.INSTRUCTION_SYNTAX_ERROR);
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
        data = sp.popFromStack();

        if (odn == null || odn.length() == 0)
        {
            sp.addWSEntry(getExternalName(), data);
        }

        else
        {
            // Change external name  to operand data name
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