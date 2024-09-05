/*
 * User: sdorman
 * Date: Jul 18, 2008
 * Time: 10:38:04 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package engine.sp;

import org.dom4j.*;
import org.dom4j.tree.*;

/**
 * Data structure to define the script line recorded by SPRecorder as a result of processing scripts in ScriptProcessor.
 */
public class SPRecordedScriptLine
{
    /**
     * The type of the script line that has been recorded.  Values would be defined by SCRIPT_LINE_TYPE_... variables.
     */
    private String type;

    /**
     * The resultant text associated with the script line
     */
    private String result;


    public static final String SCRIPT_LINE_TYPE_INSTRUCTION = "Instruction";
    public static final String SCRIPT_LINE_TYPE_PUSH = "Push";
    public static final String SCRIPT_LINE_TYPE_POP = "Pop";


    public SPRecordedScriptLine()
    {
    }

    public SPRecordedScriptLine(String type, String result)
    {
        this.type = type;
        this.result = result;
    }

    /**
     * Getter
     * @see SPRecordedScriptLine#type
     * @return
     */
    public String getType()
    {
        return type;
    }

    /**
     * Setter
     * @see SPRecordedScriptLine#type
     * @param type
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * Getter
     * @see SPRecordedScriptLine#result
     * @return
     */
    public String getResult()
    {
        return result;
    }

    /**
     * Setter
     * @see SPRecordedScriptLine#result
     * @param result
     */
    public void setResult(String result)
    {
        this.result = result;
    }

    /**
     * Maps this object to an Element
     *
     * @return  Element populated with data from this object
     */
    public Element getAsElement()
    {
        Element element = new DefaultElement("SPRecordedScriptLine");

        Element typeElement = new DefaultElement("Type");
        Element resultElement = new DefaultElement("Result");

        typeElement.setText(this.getType());
        resultElement.setText((this.getResult()));

        element.add(typeElement);
        element.add(resultElement);

        return element;
    }

    /**
     * Overload Object.toString() to create a useful string containing this object's information
     * @return
     */
    public String toString()
    {
        return this.type + ": " + result;
    }
}
