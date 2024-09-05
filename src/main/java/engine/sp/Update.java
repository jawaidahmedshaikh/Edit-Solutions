package engine.sp;

import fission.utility.Util;

/**
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: Jul 21, 2003
 * Time: 3:05:36 PM
 * To change this template use Options | File Templates.
 * 
 * Modifies the values of the last-active Element identified by the
 * specified Element path with the value currently on the Stack. e.g.
 * 
 * update param:a.b.c.FooVO.LapseDate // would update a.b.c.FooVO.LapseDate with the value on the Stack.
 * 
 * With the addition of the Conversion Framework (CF), we have enhanced this instruction with the ability
 * to update/create attributes as well. e.g.
 * 
 * update param:a.b.c.FooVO[@MyAttribute1=ws:MyWSValue1,@MyAttribute2=val:abc]
 * 
 * The above would update the current last-active Element FooVO as follows (preseneted in its XML version):
 * 
 * <FooVO MyAttribute1="FooValue1" MyAttribute2="abc"></FooVO>
 * 
 * 
 */
public class Update extends InstOneOperand
{
    public Update() throws SPException
    {
        super();
    }

    /**
     * Compiles the instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor) throws SPException
    {
        sp = aScriptProcessor; //Save instance of ScriptProcessor

        //Does common tasks in superclass such as
        //setting member variables and some operand editting
        commonCompileTasks();

        String odn = extractOperandDataName();

        if (odn.endsWith(":"))
        {
            odn = odn.substring(0, odn.length() - 1);
        }

        setOperandDataName(odn);
    }

    protected void exec(ScriptProcessor execSP) throws SPException
    {
        try
        {
            sp = execSP;

            if (!odnContainsAttributes())
            {
                String data = sp.peekFromStack();

                if (data != null)
                {
                    //String operandName = checkForAlias(getOperandDataName(), sp); // Not necessary since it's done in the setFieldPathAndName() method

                    setFieldPathAndName(getOperandDataName());

                    String fieldPath = getFieldPath();

                    String fieldName = getFieldName();

                    sp.addNewElementValue(fieldPath, fieldName, data);
                }
            }
            else
            {
                String parsedOdn = parseOutAttributes();

                String elementPath = checkForAlias(parsedOdn, sp);

                String[] attributeTokens = getAttributeTokens();

                for (String attributeToken: attributeTokens)
                {
                    String attributeName = getAttributeName(attributeToken);

                    String attributeValue = getAttributeValue(attributeToken);

                    sp.addNewElementAttribute(elementPath, attributeName, attributeValue);
                }
            }
        }
        finally
        {
            sp.incrementInstPtr();
        }
    }

    /**
     * The odn = Operand Data Name = everything after the :
     * in the expression.
     * 
     * e.g.
     * 
     * update param:a.b.c.FooVO // a.b.c.FooVO is the Operand Data Name.
     * 
     * The above line may also be written as:
     * 
     * update param:a.b.c.FooVO[@MyAttribute1=ws:FooName1,@MyAttribute2=val:abc]
     * 
     * The above states that the last-active FooVO is going to be updated/modified to 
     * contain the attributes MyAttribute1 (populated by the ws entry of FooName1) and
     * MyAttribute2 (populated by the literal value abc).
     * 
     * All said, this method is trivial in that is simply checks for basic syntax to
     * see if attributes exist.
     * @return
     */
    private boolean odnContainsAttributes()
    {
        boolean containsAttributes = false;

        String odn = getOperandDataName();

        containsAttributes = odn.indexOf("[") > 0;

        return containsAttributes;
    }

    /**
     * Removes the attribute part of the expression.
     * 
     * e.g.
     * 
     * update param:a.b.c.FooVO[@Attribute1....]
     * 
     * After being parsed, the above expression would parse-out
     * the [@Attribute1 ...] section and return a.b.c.FooVO.
     * @return a.b.c.FooV from the expression update param:a.b.c.FooVO[@Attribute1....]
     */
    private String parseOutAttributes()
    {
        int indexOfLeftBrace = getOperandDataName().indexOf("[");

        String odn = getOperandDataName();

        String parsedOdn = odn.substring(0, indexOfLeftBrace);

        return parsedOdn;
    }

    /**
     * When the expression contains an attribute subsection, we extract all
     * name/value pairs for attributeName=attributeValue where the attributeValue
     * will be of the form ws:FooName or val:FooLiteralValue from the [@FooAttribute1=ws:FooName,@FooLiteralValue=val:FooLiteralValue]
     * @return
     */
    private String[] getAttributeTokens()
    {
        String odn = getOperandDataName();

        int leftBraceIndex = odn.indexOf("[");

        int rightBraceIndex = odn.indexOf("]");

        String attributeTokensString = odn.substring(leftBraceIndex + 1, rightBraceIndex);

        String[] attributeTokens = Util.fastTokenizer(attributeTokensString, ",");

        return attributeTokens;
    }

    /**
     * From the supplied token of '@FooName=ws:FooValue' or
     * '@FooName=val:FooValue', it extracts the name of 'FooName'.
     * @param attributeToken
     * @return FooName from @FooName=ws:FooValue or from @FooName=val:FooValue
     */
    private String getAttributeName(String attributeToken)
    {
        int indexOfEqualSign = attributeToken.indexOf("=");

        String attributeName = attributeToken.substring(1, indexOfEqualSign).trim();

        return attributeName;
    }

    /**
     * From the supplied token of '@FooName=ws:FooValue' or
     * '@FooName=val:FooValue', it determines the final value of 'FooValue'.
     * @param attributeToken
     * @return FooValue from @FooName=ws:FooValue or from @FooName=val:FooValue
     */
    private String getAttributeValue(String attributeToken)
    {
        String attributeValue = null;

        int indexOfEqualSign = attributeToken.indexOf("=");

        int indexOfColon = attributeToken.indexOf(":");

        String tokenValue = attributeToken.substring(indexOfColon + 1).trim();

        if (attributeToken.indexOf(OPERANDTYPE_WS + ":") >= 0) // look for ws:
        {
            attributeValue = sp.getWSEntry(tokenValue);
        }
        else if (attributeToken.indexOf(OPERANDTYPE_VAL + ":") >= 0) // look for val:
        {
            attributeValue = tokenValue;
        }

        return attributeValue;
    }
}
