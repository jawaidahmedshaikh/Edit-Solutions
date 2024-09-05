/*
 * User: unknown
 * Date: Jun 4, 2001
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.sp;

import edit.common.EDITDate;

import engine.common.Constants;

import fission.utility.Util;

import java.util.ArrayList;
import java.util.List;


/**
 * This class is the base class for instructions with
 * one operand.
 */
public abstract class InstOneOperand extends Inst
{

    // Member variables:

    // Operand Types:
    // Working Storage
    protected final String OPERANDTYPE_WS = "ws";
    
    // Results Document (for query results)
    protected final String OPERTANDTYPE_RD = "rd";
    
    // Constants
    protected final String OPERANDTYPE_VAL = "val";
    protected final String OPERANDTYPE_NUMBER = "num";
    protected final String OPERANDTYPE_STRING = "str";
    protected final String OPERANDTYPE_PARAM = "param";
    protected final String varConstant = "1";
    protected final String varWS = "2";
    protected final String varPM = "3";

    // Used to store operand (Ex. For instruction "pop num:8"
    // the operand would be "num:8")
    private String operand = "";

    // Used to store operand type (Ex. For instruction  "pop num:8"
    // the operand type would be "num")
    private String operandType = "";

    // Used to store operand Data Name (Ex. For instruction "pop num:8"
    // the operand data name would be "8")
    private String operandDataName = "";

    // Used to store the subscript (if any) (Ex. For instruction "pop ws:CumDollars; ws:FundCtr"
    // the subscript would be the value of the working storage field named FundCtr)
    private String subscriptName = "";

    private int count = 0;

    // Used to store constant if it is part of the operand
    protected String constantString = null;

    // These flags are set based on the operand type:
    private boolean constant = false;
    private boolean constantStr = false;
    private boolean pmReference = false;
    private boolean wsReference = false;
    private boolean rdReference = false; // Results Document
    private boolean childrenRequested = false;
    public boolean dynamic = false;

    private List variableOne = null;
    private List variableTwo = null;
    private String relationalOperator = null;
    private String fieldPath = null;
    private String fieldName = null;
    private String parentPath = null;

    /**
     * InstOneOperand constructor
     * <p>
     * @exception SPException
     */
    public InstOneOperand()
    {

        super();
    }

    /**
     * Used to preform common compiling for instructions
     * with one operand.
     * <p>
     * @exception SPException  If the operator is invalid
     */
    protected void commonCompileTasks() throws SPException
    {

        // Get the operand from the instruction
        String instruction = getInstAsEntered();
        String operand = extractOperand(instruction);
        setOperand(operand);
        String operator = extractOperator(instruction);

        // Bypass Aliases
        if (operator.equalsIgnoreCase("alias") || operator.equalsIgnoreCase("identify") || operator.equalsIgnoreCase("counter"))
        {
            return;
        }
        // Bypass Labels
        if (operand.endsWith(":"))
        {
            return;
        }


        if (operator.equalsIgnoreCase("if"))
        {

            parseCondition(operand);
        }
        else
        {

            // Get the data name (or constant data) from the operand
            setOperandDataName(extractOperandDataName());

            // Get the operand type
            setOperandType(extractOperandType());

            // Get the subscript (if any)
            setSubscriptName(extractSubscriptName());

            // Edit operand type and assign member variables
            processOperandType();
        }
    }


    /**
     * Used to perform common recompiling for instructions
     * with one operand.  This is used when the operand needs
     * to change.  For example if the instruction contains
     * an alias then the operand needs to be replaced with
     * the data name associated with the alias.
     * <p>
     * @param newOperand  The new operand to recompile
     * @exception SPException  If the operator is invalid
     */
    protected void commonReCompileTasks(String newOperand) throws SPException
    {

        // Get the operand from the instruction
        setOperand(newOperand);

        // Get the data name (or constant data) from the operand
        setOperandDataName(extractOperandDataName());

        // Get the operand type
        setOperandType(extractOperandType());

        // Get the subscript (if any)
        setSubscriptName(extractSubscriptName());

        // Edit operand type and assign member variables
        processOperandType();
    }

    /**
     * This method will edit the operand type and set
     * the appropriate member variables
     * <p>
     * @exception SPException  If the operator is invalid
     */
    private void processOperandType() throws SPException
    {

        String ot = getOperandType();

        setPMReference(false);
        setWSReference(false);
        setConstant(false);

        // Determine if operand is a constant

        if (ot.equalsIgnoreCase(OPERANDTYPE_VAL) || ot.equalsIgnoreCase((OPERANDTYPE_STRING)))
        {
            String cString = getOperandDataName();

            if (cString.equalsIgnoreCase(Constants.ScriptKeyword.SYSTEM_DATE) ||
                cString.equalsIgnoreCase(Constants.ScriptKeyword.SYSTEM_DATE_TIME))
            {
                setDynamic(true);
            }
            else
            {
                setConstant(true);
            }
        }

        // Determine if the operand references logical storage
        else if (ot.equalsIgnoreCase(OPERANDTYPE_WS))
        {

            setWSReference(true);
            return;
        }

        else if (ot.equalsIgnoreCase(OPERANDTYPE_PARAM))
        {
            setPMReference(true);
            return;
        }
        
        else if (ot.equalsIgnoreCase(OPERTANDTYPE_RD))
        {
            setRDReference(true);
            return;
        }

        else
            throw new SPException("Invalid Operand Type for instruction: " +
                    getInstAsEntered(), 999);

    }

    /**
     * Extracts operand data name
     * <p>
     * @return Returns operand data name
     * @exception SPException  If operand type is missing
     */
    public String extractOperandDataName() throws SPException
    {

        String od = null;
        String st = getOperand();

        // Remove comment if present and then any trailing blanks
        int commentStart = st.indexOf("//");
        if (commentStart != -1)
        {
            st = (st.substring(0, commentStart)).trim();
        }

        // Find the end of the operandType and get the operand data
        int colonPos = st.indexOf(':');
        int semiColonPos = st.indexOf(';');
        if (colonPos == -1)
        {
            throw new SPException("Operand type missing for instruction: " +
                    getInstAsEntered(), 999);
        }

        if (semiColonPos == -1 ||
                semiColonPos < colonPos)
        {

            od = st.substring(colonPos + 1, st.length());
        }

        else
        {

            od = st.substring(colonPos + 1, semiColonPos);
        }

        if (od.equals(""))
        {
            od = st.substring(0, st.length() - 1);
        }

        return od;
    }

    /**
     * Extracts operand type.  For example the instruction:
     * "pop num:8" the operand type wouold be "num".
     * <p>
     * @return Returns operator type
     * @exception SPException  If missing operand type
     */
    private String extractOperandType() throws SPException
    {

        String ot = null;
        String[] tokens = Util.fastTokenizer(getOperand(), ":");
        ot = tokens[0];
        if (ot == null)
        {
            throw new SPException("Missing operand type for instruction: " +
                    getInstAsEntered(), 999);
        }
        return ot;
    }

    private String extractSubscriptName() throws SPException
    {

        String sn = null;
        String[] tokens = Util.fastTokenizer(getOperand(), ":");

        //If only one token then no subscript in the instruction
//        String firstToken = tokens[0];

        for (int i = 2; i < tokens.length; i++)
        {

            String secondToken = tokens[i];

            if (secondToken.startsWith("ws:"))
            {

                int colonPos = secondToken.indexOf(':');
                if (colonPos == -1)
                {
                    throw new SPException("Subscript name missing for instruction: " +
                            getInstAsEntered(), 999);
                }
                sn = secondToken.substring(colonPos + 1, secondToken.length());
            }
        }

        return sn;
    }

    /**
     * Generates and stores a String constant
     */
    public void generateStringConstant()
    {
        String cString = getOperandDataName();

        if (cString.charAt(0) == '"')
        {
            cString = cString.substring(1, cString.length() - 1);
        }

        constantString = cString;
    }


    /**
     * Returns an operand
     * <p>
     * @return Returns an operand
     */
    protected String getOperand()
    {

        return operand;
    }

    /**
     * Returns the operand data name
     * <p>
     * @return Returns the operand data name
     */
    public String getOperandDataName()
    {

        return operandDataName;
    }

    /**
     * Returns the operand type
     * <p>
     * @return Returns the operand type
     */
    public String getOperandType()
    {

        return operandType;
    }

    public String getSubscriptName()
    {

        return subscriptName;
    }

    /**
     * Returns true if this instruction contains a constant
     * <p>
     * @return Returns true if this instruction contains a constant
     */
    public boolean isConstant()
    {

        return constant;
    }

    /**
     * Returns true if this instruction contains a dynamic constant
     * <p>
     * @return Returns true if this instruction contains a dynamic constant
     */
    public boolean isDynamic()
    {

        return dynamic;
    }

    /**
     * Returns true if this instruction contains a constant string
     * <p>
     * @return Returns true if this instruction contains a constant string
     */
    public boolean isConstantString()
    {

        return constantStr;
    }


    /**
     * Returns true if this instruction contains a DataManger reference.
     * This is used for table lookups (ex plan data, client data, ...)
     * <p>
     * @return Returns true if this instruction contains a DataManger reference
     */
    public boolean isPMReference()
    {

        return pmReference;
    }

    /**
     * Returns true if this instruction contains a Working Storage (WS)
     * reference.
     * <p>
     * @return Returns true if this instruction contains a
     *     Working Storage (WS) reference.
     */
    public boolean isWSReference()
    {

        return wsReference;
    }
    
    /**
     * Returns true if this instruction contains a Results Document (RD)
     * reference.
     * @return
     */
    public boolean isRDReference()
    {
        return rdReference;
    }


    public boolean isParamOperandType()
    {

        String ot = getOperandType();
        return ot.equalsIgnoreCase(OPERANDTYPE_PARAM);
    }

    public boolean isStringOperandType()
    {

        String ot = getOperandType();
        return ot.equalsIgnoreCase(OPERANDTYPE_STRING);
    }


    /**
     * Used to set a constant boolean value to true
     * if this instruction contains a constant
     * <p>
     * @param b  New value of constant variable
     */
    public void setConstant(boolean b)
    {

        constant = b;
    }

    /**
     * Used to set a dynamic boolean value to true
     * if this instruction contains a specific dynamic constant
     * <p>
     * @param b  New value of dynamic constant
     */
    public void setDynamic(boolean b)
    {

        dynamic = b;
    }

    /**
     * Used to set a constant string boolean value to true
     * if this instruction contains a constant string
     * <p>
     * @param b  New value of constant variable
     */
    public void setConstantString(boolean b)
    {

        constantStr = b;
    }


    /**
     * Used to set a pMReference boolean value to true
     * if this instruction contains a reference to params
     *
     * <p>
     * @param b  New value of PMReference variable
     */
    public void setPMReference(boolean b)
    {

        pmReference = b;
    }

    /**
     * Used to set WSReference boolean value to true
     * if this instruction contains an WS reference
     * <p>
     * @param b  New value of WsReference variable
     */
    public void setWSReference(boolean b)
    {

        wsReference = b;
    }

    /**
     * Used to set the operand member variable
     * <p>
     * @param s  New value of operand variable
     */
    protected void setOperand(String s)
    {

        operand = s;
    }

    /**
     * Used to set the operand data name
     * <p>
     * @param s  New value of operand data name variable
     */
    public void setOperandDataName(String s)
    {

        operandDataName = s;
    }

    /**
     * Used to set the operand type
     * <p>
     * @param s  New value of operand type variable
     */
    public void setOperandType(String s)
    {

        operandType = s;
    }

    public void setSubscriptName(String s)
    {

        subscriptName = s;
    }

    private void parseCondition(String operand) throws SPException
    {

        if (operand.startsWith("children"))
        {
            childrenRequested = true;
        }
        else
        {

            String[] stringOperand = Util.fastTokenizer(operand, " ");
            String variable2 = null;
            String variable1 = stringOperand[0];

            if (stringOperand[1].equals(""))
            {

                relationalOperator = stringOperand[2];

                if (stringOperand[3].equals(""))
                {

                    variable2 = stringOperand[4];
                }
                else
                {

                    variable2 = stringOperand[3];
                }
            }//end outer if

            else
            {

                relationalOperator = stringOperand[1];

                if (stringOperand[2].equals(""))
                {

                    variable2 = stringOperand[3];
                }
                else
                {

                    variable2 = stringOperand[2];
                }
            }//end outer else

            if (variable2.startsWith("'"))
            {

                variable2 = variable2.substring(1, variable2.length() - 1);
            }

            // Process each variable
            setOperand(variable1);
            setOperandType(extractOperandType());
            setOperandDataName(extractOperandDataName());
            processOperandType();
            setVariable1Results(variable1);

            setOperand(variable2);
            int colonPos = variable2.indexOf(':');
            if (colonPos == -1)
            {
                setConstant(true);
                setPMReference(false);
                setWSReference(false);
            }
            else
            {
                setOperandType(extractOperandType());
                setOperandDataName(extractOperandDataName());
                processOperandType();
            }

            setVariable2Results(variable2);
        }
    }

    private void setVariable1Results(String variable1)
    {

        variableOne = new ArrayList();
        String type = null;

        if (isConstant())
        {
            type = varConstant;
            String cString = getOperandDataName();

            if (cString.charAt(0) == '"')
            {
                cString = cString.substring(1, cString.length() - 1);
            }
            variableOne.add(type);
            variableOne.add(cString);

        }

        else if (isWSReference())
        {
            type = varWS;
            variableOne.add(type);
            variableOne.add(operandDataName);
        }

        else if (isPMReference())
        {
            type = varPM;
            variableOne.add(type);
            variableOne.add(operandDataName);
        }
    }

    private void setVariable2Results(String variable2)
    {

        variableTwo = new ArrayList();
        String type = null;

        if (isConstant())
        {
            type = varConstant;
            String cString = getOperandDataName();

            if (cString.equalsIgnoreCase("null"))
            {
                cString = "";
            }
            variableTwo.add(type);
            variableTwo.add(cString);
//            variableTwo.add(operandDataName);

        }

        else if (isWSReference())
        {
            type = varWS;
            variableTwo.add(type);
            variableTwo.add(operandDataName);
        }

        else if (isPMReference())
        {
            type = varPM;
            variableTwo.add(type);
            variableTwo.add(operandDataName);
        }
    }

    protected List getVariableOne()
    {

        return variableOne;
    }

    protected List getVariableTwo()
    {

        return variableTwo;
    }

    protected String getRelationalOperator()
    {

        return relationalOperator;
    }

    protected boolean getChildrenRequested()
    {

        return childrenRequested;
    }

    public static String checkForAlias(String fieldPath, ScriptProcessor scriptProcessor) throws SPException
    {
        String[] names = Util.fastTokenizer(fieldPath, ".");

        String operandName = "";

        for (int i = 0; i < names.length; i++)
        {
            if (i > 0)
            {
                operandName = operandName + ".";
            }

            if (names[i].startsWith("&"))
            {

                operandName = operandName + scriptProcessor.getAliasFullyQualifiedName(names[i]);
            }
            else
            {

                operandName = operandName + names[i];
            }
        }

        return operandName;
    }

//    protected int getParentIndex(String fieldPath)
//    {
//
//        int parentIndex = 0;
//
//        if (fieldPath != null)
//        {
//            Integer fieldIndex = (Integer) sp.getForeachEntry(fieldPath);
//
//            if (fieldIndex != null)
//            {
//
//                parentIndex = fieldIndex.intValue();
//            }
//        }
//
//        return parentIndex;
//    }

    protected void setParentPath(String fieldPath)
    {

        int indexOfDot = fieldPath.lastIndexOf('.');

        parentPath = (indexOfDot > 0) ? fieldPath.substring(0, indexOfDot) : fieldPath;
    }

    protected String getParentPath()
    {

        return parentPath;
    }

    protected void setFieldPathAndName(String operandName) throws SPException
    {
        operandName = checkForAlias(operandName, this.sp);

        fieldPath = setFieldPath(operandName);

        fieldName = operandName.substring(operandName.lastIndexOf(".") + 1, operandName.length());

    }

    protected String setFieldPath(String operandName)
    {

        int lastIndexOfDot = 0;
        lastIndexOfDot = operandName.lastIndexOf(".");

        if (lastIndexOfDot == -1)
        {

            fieldPath = operandName;
        }

        else
        {

            fieldPath = operandName.substring(0, lastIndexOfDot);
        }

        return fieldPath;
    }


    protected String getFieldName()
    {

        return fieldName;
    }

    protected String getFieldPath()
    {

        return fieldPath;
    }
    
    /**
     * @see #rdReference
     * @param b
     */
    private void setRDReference(boolean b)
    {
        this.rdReference = b;
    }
}
