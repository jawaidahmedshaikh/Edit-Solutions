/*
 * User: gfrosti
 * Date: Aug 26, 2004
 * Time: 11:31:58 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package engine.sp;

import edit.common.vo.*;
import fission.utility.*;
import org.dom4j.*;
import org.dom4j.io.*;
import org.dom4j.tree.*;

import java.io.*;

/**
 * Parent class for all Validate instructions. Provides common utility methods.
 */
public abstract class ValidateInst extends Inst
{
    private ValidationVO validationVO;

    public static final String SEVERITY_HARD = "H";

    public static final String SEVERITY_WARNING = "W";
    
    public static final String SEVERITY_SUCCESS = "S";

    public static final String REPORTING_YES = "Y";

    public static final String REPORTING_NO = "N";

    /**
     * Default constructor.
     */
    protected ValidateInst()
    {
        super();
    }

    /**
     * @throws SPException
     * @see Inst#compile(engine.sp.ScriptProcessor)
     */
    protected void compile(ScriptProcessor scriptProcessor) throws SPException
    {
        sp = scriptProcessor;
    }

    /**
     * Finds the Alias Name, Severity, and Reporting for the current validation script line. These are expected
     * parameters for all validation instructions.
     */
    private void parseForInstructionParameters()
    {
        String message = null;
        String severity = null;
        String reporting = null;

        String[] tokens = getInstructionTokens();

        message = tokens[0].trim();
        severity = tokens[1].trim();
        reporting = tokens[2].trim();

        validationVO.setMessage(message);
        validationVO.setSeverity(severity);
        validationVO.setReporting(reporting);
    }

    /**
     * Extracts the expected tokens of aliasName, severity, reporting from the validation instruction of
     * Foo(aliasName, severity, reporting).
     * @return
     */
    private String[] getInstructionTokens()
    {
        String line = super.getInstAsEntered();

        int openingParenthesis = line.indexOf("(");
        int closingParenthesis = line.indexOf(")", openingParenthesis);

        line = line.substring(openingParenthesis + 1, closingParenthesis);

        String[] tokens = Util.fastTokenizer(line, ",");

        return tokens;
    }

    /**
     * Parses for the instruction name.
     */
    private void parseForInstructionName()
    {
        String instructionName = null;

        final Class aClass = getClass();
        String className = aClass.getName();

        final Package aPackage = aClass.getPackage();
        final String packageName = aPackage.getName();

        final int packageLength = packageName.length();
        final int classLength = className.length();
        instructionName = className.substring(packageLength + 1, classLength);

        validationVO.setInstructionName(instructionName);
    }

    /**
     * Template method to get the state of the stack to execute the instruction.
     */
    private void parseForStack()
    {
        final String message = getStack();

        validationVO.setStack(message);
    }

    /**
     * Returns "NA" by default. Otherwise, the implementing subclass should return the formatted set of values
     * that were placed on the stack (in the order that they were placed on the stack] to satisfy the instruction.
     * A typical return value might look like "actual:[foo1] expected:[foo2] tolerance:[foo3]".
     * @return "NA" unless overridden by a subclass.
     */
    String getStack()
    {
        return "NA";
    }

    /**
     * Returns the validation instruction at its current state. The state includes the Instruction Name,
     * Alias Name, Severity, Reporting, Expected Value, Actual Value, and Tolerance.
     * @return the fully populated ValidationVO
     */
    public ValidationVO getValidationVO()
    {
        validationVO = new ValidationVO();

        parseForStack();
        parseForInstructionParameters();
        parseForInstructionName();

        return validationVO;
    }

    /**
     * @return
     * @see Inst#getErrorOutput()
     */
    public Element getErrorOutput()
    {
        Element element = null;

        ValidationVO validationVO = getValidationVO();

        String xml = Util.marshalVO(validationVO);

        SAXReader reader = new SAXReader();

        Document document = null;

        try
        {
            document = reader.read(new StringReader(xml));

            element = document.getRootElement();
        }
        catch (DocumentException e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }

        return element;
    }

    /**
     * Getter.
     * @return
     */
    public String getMessage()
    {
        return validationVO.getMessage();
    }

    /**
     * Getter.
     * @return
     */
    public String getInstructionName()
    {
        return validationVO.getInstructionName();
    }

    /**
     * Getter.
     * @return
     */
    public String getReporting()
    {
        return validationVO.getReporting();
    }

    /**
     * Getter.
     * @return
     */
    public String getSeverity()
    {
        return validationVO.getSeverity();
    }
}
