/*
 * User: gfrosti
 * Date: Sep 29, 2004
 * Time: 1:56:25 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package engine.sp;

import fission.utility.*;
import org.dom4j.*;
import edit.common.*;
import engine.common.*;

public class Sum extends Inst
{
    private ScriptProcessor sp;

    private SumSyntax sumSyntax;

    public static final int EQ = 0;

    public static final int NEQ = 1;

    public static final int LT = 2;

    public static final int LTE = 3;

    public static final int GT = 4;

    public static final int GTE = 5;

    public Sum()
    {

    }

    protected void compile(ScriptProcessor theProcessor) throws SPException
    {
        this.sp = theProcessor;

        sumSyntax = new SumSyntax(getInstAsEntered());
    }

    /**
     * Conditionally sums a set of values across DOM Elements.
     * @param execSP
     * @throws SPException
     */
    protected void exec(ScriptProcessor execSP) throws SPException
    {
        EDITBigDecimal sum = null;

        if (!sumSyntax.hasSumConditions())
        {
            EDITBigDecimal[] valuesToSum = findFieldValuesByPath(sumSyntax.getFieldPath(), sumSyntax.getLastActiveAncestorPath());

            if (valuesToSum != null)
            {
                sum = new EDITBigDecimal("0.0");

                for (int i = 0; i < valuesToSum.length; i++)
                {
                    sum = sum.addEditBigDecimal(valuesToSum[i]);
                }
            }
        }

        sp.push(sum.toString());

        sp.incrementInstPtr();
    }


    /**
     *
     * @param fullyQualifiedFieldName
     * @param lastActiveAncestorPath
     * @return
     * @throws SPException
     */
    private EDITBigDecimal[] findFieldValuesByPath(String fullyQualifiedFieldName, String lastActiveAncestorPath) throws SPException
    {
        EDITBigDecimal[] fieldValues = null;

        SPParams spParams = null;

        spParams = sp.getSPParams();

        String fieldPath = spParams.getFieldPath(fullyQualifiedFieldName);

        Element[] elements =  spParams.findElementsByPath(fieldPath, lastActiveAncestorPath);

        if (elements != null)
        {
            String fieldName = spParams.getFieldName(fullyQualifiedFieldName);

            fieldValues = new EDITBigDecimal[elements.length];

            for (int i = 0; i < elements.length; i++)
            {
                fieldValues[i] = new EDITBigDecimal(spParams.getValue(elements[i], fieldName));
            }
        }

        return fieldValues;
    }

    /****************************************************************************************************************/

    /**
     * Abstracts the target field name, whether or not to use the last active ancestor, and the [optional] set of sum
     * conditions.
     */
    private class SumSyntax
    {
        private String fieldPath;

        private String lastActiveAncestorPath;

        private SumCondition[] sumConditions;

        public SumSyntax(String instruction)
        {
            parseInstruction(instruction);
        }

        /**
         * Parses the instruction line for its target fieldName, last-active state, and (optional) sum conditions. The
         * instruction is of the form sum(String targetField, boolean useLastActiveAncestor, String[] sumConditions)
         */
        private void parseInstruction(String instruction)
        {
            String line = getInstAsEntered();

            final int openingParenthesis = line.indexOf("(");
            final int closingParenthesis = line.indexOf(")", openingParenthesis);

            line = line.substring(openingParenthesis + 1, closingParenthesis);

            String[] tokens = Util.fastTokenizer(line, ",");

            // Field name
            setFieldName(tokens[0].trim());

            // Use last active
            String lastActiveValue = tokens[1].trim();
            String lastActiveAncestorPath = (lastActiveValue.equals(Constants.ScriptKeyword.NULL)) ? null : lastActiveValue;
            setLastActiveAncestorPath(lastActiveAncestorPath);

            // Sum conditions
            String sumConditionsValue = tokens[2].trim();

            if (!sumConditionsValue.equals(Constants.ScriptKeyword.NULL))
            {
                sumConditions = new SumCondition[tokens.length - 2];

                for (int i = 2; i < tokens.length; i++)
                {
                    sumConditions[i] = new SumCondition(tokens[i]);
                }
            }
        }

        /**
         * Getter.
         * @return
         */
        public String getFieldPath()
        {
            return fieldPath;
        }

        /**
         * Setter.
         * @param fieldPath
         */
        public void setFieldName(String fieldPath)
        {
            this.fieldPath = fieldPath;
        }

        /**
         * Getter.
         * @return
         */
        public String getLastActiveAncestorPath()
        {
            return lastActiveAncestorPath;
        }

        /**
         * Setter.
         * @param lastActiveAncestorPath
         */
        public void setLastActiveAncestorPath(String lastActiveAncestorPath)
        {
            this.lastActiveAncestorPath = lastActiveAncestorPath;
        }

        /**
         * Getter.
         * @return true if there are sum conditions
         */
        public boolean hasSumConditions()
        {
            return (sumConditions != null);
        }

        /**
         * Getter.
         * @return
         */
        public SumCondition[] getSumConditions()
        {
            return sumConditions;
        }
    }

    /**
     * *************************************************************************************************************
     */

    private class SumCondition
    {
        public SumCondition(String condition)
        {

        }
    }
}
