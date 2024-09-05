/*
 * User: unknown
 * Date: Feb 19, 2002
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.sp;

import edit.common.*;

/**
 * This is the implementation for the Compare instruction.
 * The Compare instruction is used to get the values of the two
 * arguments listed and push their values to the stack.
 * <p/>
 * <p/>
 * Example:
 * compare ws:xxxxx;str:yyyy
 */
public final class Compare extends InstOneOperand
{

    /**
     * Compare constructor
     * <p/>
     * @throws SPException
     */
    public Compare() throws SPException
    {

        super();
    }

    /**
     * Compiles the instruction
     * <p/>
     * @param aScriptProcessor Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor) throws SPException
    {

        sp = aScriptProcessor;  //Save instance of ScriptProcessor

        //Does common tasks in superclass such as
        //setting member variables and some operand editting
        commonCompileTasks();
    }

    /**
     * Executes the instruction
     * <p/>
     * @throws SPException If there is an error while
     *                     executing the instruction
     */
    protected void exec(ScriptProcessor execSP) throws SPException
    {

        sp = execSP;
        //the ws fields are in a hashtable
        //Map ws = sp.getWSdata();

        String arg1 = null;
        String arg2 = null;
        String result = null;

        //get the operand, the whole string
        String op = getOperand();

        //parse the string into the separate variables to compare
        int ind = op.indexOf(";");
        String var1 = op.substring(0, ind);
        String var2 = op.substring(ind + 1);

        //parse each variable into type of variable and variable name
        ind = var1.indexOf(":");
        arg1 = var1.substring(ind + 1);

        ind = var2.indexOf(":");
        arg2 = var2.substring(ind + 1);

        if (var1.startsWith("str:"))
        {

            result = arg1;
            sp.push(result);
        }
        else if (var1.startsWith("ws:"))
        {

            result = (String) sp.getWSEntry(arg1);
            sp.push(result);
        }
        else if (var1.startsWith("num:"))
        {

//                 result = Double.parseDouble(arg1) + "";
            result = arg1;
            sp.push(result);
        }
        else if (var1.startsWith("param:"))
        {

            setFieldPathAndName(arg1);
            String fieldPath = getFieldPath();
            String fieldName = getFieldName();

            result = sp.getElementValue(fieldPath, fieldName);

            sp.push(result);
        }
        else if (var1.startsWith("date:"))
        {

            EDITDate ceDate = new EDITDate(arg1);
            result = ceDate.getFormattedDate();
            sp.push(result);
        }

        if (var2.startsWith("str:"))
        {

            result = arg2;
            sp.push(result);
        }
        else if (var2.startsWith("ws:"))
        {

            result = (String) sp.getWSEntry(arg2);
            sp.push(result);
        }
        else if (var2.startsWith("num:"))
        {

//                result = Double.parseDouble(arg2) + "";
            result = arg2;
            sp.push(result);
        }
        else if (var2.startsWith("param:"))
        {

            setFieldPathAndName(arg2);
            String fieldPath = getFieldPath();
            String fieldName = getFieldName();

            result = sp.getElementValue(fieldPath, fieldName);

            sp.push(result);
        }
        else if (var2.startsWith("date:"))
        {

            EDITDate ceDate = new EDITDate(arg2);
            result = ceDate.getFormattedDate();
            sp.push(result);
        }

        // Increment instruction pointer
        sp.incrementInstPtr();
    }
}