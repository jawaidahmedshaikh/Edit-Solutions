/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jan 14, 2005
 * Time: 8:02:33 AM
 * To change this template use File | Settings | File Templates.
 */
package engine.sp;

import fission.utility.Util;


/**
 * Offers user-specified information about the script currently being processed. This is most useful should the
 * script error. Usage of the Context instruction is shown below:
 * Context(Policy Number:&ContractNumber, Event:#Adding A Client, Effective Date:SegmentVO.EffectiveDate).
 * This shows the usage of three Context headers: "Policy Number", "Event", and "Effective Date". The Policy Number
 * header is associated with the alias value &ContractNumber, the Event header is associated with the literal (notice
 * the #) Adding A Client, and the Effective Date header is associated with the unaliased value of SegmentVO.EffectiveDate.
 * In general, the syntax is Context (header1:value1, header2:value2, ... headern, valuen). Values may be aliases,
 * unaliased values, or literals (prefaced with a #).
 */
public class Context extends InstOneOperand
{
    public static final int ALIAS_VALUE = 0;
    public static final int LITERAL_VALUE = 1;
    public static final int COMPOUND_VALUE = 2;
    public static final String CONTEXT_NAME = "CONTEXT.NAME";


    /**
     * @param scriptProcessor
     * @throws SPException
     * @see Inst#compile(ScriptProcessor)
     */
    protected void compile(ScriptProcessor scriptProcessor) throws SPException
    {
        sp = scriptProcessor;
    }

    /**
     * @param execSP
     * @throws SPException
     * @see Inst#exec(ScriptProcessor)
     */
    protected void exec(ScriptProcessor execSP) throws SPException
    {
        try
        {
            sp = execSP;

            populateContext();
        }
        finally
        {
            sp.incrementInstPtr();
        }
    }

    /**
     * Parses the instruction Context(header1:value1, ... headern:valuen) for the header/value pairs and returns
     * a corresponding Map.
     *
     * @return
     */
    private void populateContext() throws SPException
    {
        String[] instructionTokens = getInstructionTokens();

        // The first token is the context name.
        String contextName = instructionTokens[0].trim();

        sp.addToContext("CONTEXT.NAME", contextName);

        for (int i = 1; i < instructionTokens.length; i++)
        {
            String token = instructionTokens[i];

            String[] headerValue = getHeaderValueEntry(token);

            String header = headerValue[0];

            String valueToken = headerValue[1];

            String actualValue = getActualValue(valueToken);

            sp.addToContext(header, actualValue);
        }
    }

    /**
     * The value is either an &alias, a #literal, or a compound value a.b.c. It needs to be evaluated appropriately.
     *
     * @param valueToken
     * @return
     */
    private String getActualValue(String valueToken) throws SPException
    {
        String actualValue = null;

        int valueType = getValueType(valueToken);

        switch (valueType)
        {
        case ALIAS_VALUE:
        {
            String operandFullyQualifiedName = sp.getAliasFullyQualifiedName(valueToken);
            String operandPath = SPParams.getFieldPath(operandFullyQualifiedName);
            String operandName = SPParams.getFieldName(operandFullyQualifiedName);

            actualValue = sp.getElementValue(operandPath, operandName);

            break;
        }

        case LITERAL_VALUE:
        {
            actualValue = valueToken.substring(1, valueToken.length());

            break;
        }

        default:
        {
            String operandPath = SPParams.getFieldPath(valueToken);
            String operandName = SPParams.getFieldName(valueToken);

            actualValue = sp.getElementValue(operandPath, operandName);

            break;
        }
        }

        return actualValue;
    }

    /**
     * Returns:
     * ALIAS_VALUE if the valueToken begins with '&'
     * LITERAL_VALUE if the valueToken begins with "#"
     * COMPOUND_VALUE otherise.
     *
     * @param valueToken
     * @return
     */
    private int getValueType(String valueToken)
    {
        if (valueToken.startsWith("&"))
        {
            return ALIAS_VALUE;
        }
        else if (valueToken.startsWith("#"))
        {
            return LITERAL_VALUE;
        }
        else
        {
            return COMPOUND_VALUE;
        }
    }

    /**
     * Splits the token on the ":" character and trims the results for whitespace.
     *
     * @param headerValueToken
     * @return
     */
    private String[] getHeaderValueEntry(String headerValueToken)
    {
        String[] tokens = Util.fastTokenizer(headerValueToken, ":");

        String header = tokens[0].trim();

        String value = tokens[1].trim();

        return new String[]
        {
            header,
            value
        };
    }

    /**
     * Finds the "header:value" tokens.
     *
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
}
