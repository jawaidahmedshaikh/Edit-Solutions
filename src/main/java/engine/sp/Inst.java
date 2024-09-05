/*
 * Inst.java      Version 2.00  06/04/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may not be copied in whole or in part
 * without the written permission of Systems Engineering Group, LLC.
 */

package engine.sp;

import edit.common.exceptions.*;
import edit.common.vo.*;

import java.io.Serializable;

import org.dom4j.*;
import org.dom4j.tree.*;
import fission.utility.*;

/**
 * This is the base (parent) class for all ScriptProcessor Instructions
 */

public abstract class Inst implements Serializable,Cloneable {
   
    /**
     * Used to set the Instruction class 
     */
   	public static final int NOOP   = 1;
	public static final int ZEROOP = 2;
	public static final int ONEOP  = 3;
     
    // Identifies a comment instruction
    public static final String COMMENT_IDENTIFIER = "//";
    public static final String LABEL_IDENTIFIER   = ":";
    
	// Instruction data elements
	private   String instAsEntered;
	private   int    instType = 0;
	private   String operator = "";
	//private   String operand  = "";
    private   int    instLineNr = 0;
    private   String externalName = null;
    private   String typeName     = null;

    //Reference to instance of ScriptProcessor
	protected transient ScriptProcessor sp;

	// Instruction Class
	private int instClass;

    /**
     * This method needs to be implemented by each Instruction class (ex. Push).
     * This method will be used to compile the instruction.
     * <p>
     * @param theProcessor  Pointer to instance of ScriptProcessor 
     * @exception SPException  If there is a problem in the compile.
     */
    protected abstract void compile(ScriptProcessor theProcessor) 
        throws SPException;

    /**
     * Determines the operator type of the instruction
     * <p>
     * @exception SPException  If there is a problem with the operator type
     */
//    private void determineOperatorType() throws SPException {
//
//	    int type;
//	    String name = this.getOperator().toUpperCase();
//	    try	{
//	        java.lang.reflect.Field f = this.getClass().getField(name);
//            type = ((Integer) f.get(this)).intValue();
//	        setInstType(type);
//	    } catch (NoSuchFieldException   e2) {
//		    throw new SPException("Invalid Operator Name:" + name, SPException.INSTRUCTION_SYNTAX_ERROR);
//	    } 
//    }

    /**
     * This method needs to be implemented by each instruction class.
     * This method will be used to execute the instruction.
     */
    protected abstract void exec(ScriptProcessor execSP) throws SPException;

    /**
     * Extract the operand data from the instruction that was entered
     * <p>
     * @param s  Instruction in string format
     * @return   Returns operand
     */
    public static final String extractOperand(String s) {

        s = s.trim();
        // Find the operator and remove it
        int i = s.indexOf(' ');
        s = (s.substring(i, s.length())).trim();

        // Find comments and remove them and any spaces that exposes
        i = s.indexOf(COMMENT_IDENTIFIER);
        if (i > -1) {
            s = (s.substring(0, i)).trim();
        }
        return s;
    }
    
    /**
     * Extract the operand Type
     */
    public static final String extractOperandType(String s)  {
    
        if (!containsOperand(s)) {
            return " ";
        }
        
        //Operand is a label
        if (s.endsWith(LABEL_IDENTIFIER)) {
            return " ";
        }
        
        String opd = extractOperand(s);
        int i = opd.indexOf(":");
        return opd.substring(0, i);
        
    }
    
    /**
     * Extract the operand name
     */
    public static final String extractOperandName(String s)  {
    
        //Operand is a comment
        if (s.startsWith(COMMENT_IDENTIFIER)) {
            if (s.length() == 2)  {
                return " ";
            } else  {
                return s.substring(2, s.length());
            }    
        }
        
        //Operand is a label
        if (s.endsWith(LABEL_IDENTIFIER)) {
            int i = s.indexOf(' ');
            if (i == -1)  {
                return s;  //Label instruction
            } else  {
                return s.substring(i + 1, s.length()); //Ifxx instruction
            }
        }
        
        // No operand
        if (!containsOperand(s)) {
            return " ";
        }
       
        String opd = extractOperand(s);
        int i = opd.indexOf(":");
        return opd.substring(i + 1, opd.length());
        
    }


    /**
     * Usually the first token in a string is the operator. However, if the
     * first two non-blank characters are a // then the line is a comment.
     * Also, if the frist token ends with a colon then the token is a label.
     * <p>
     * @param s  Instruction in String format
     * @return   Returns operator
     */
    public static final String extractOperator(String s) {
	
	
	
        String firstWord;
//        s = s.trim();
//        if ((s == null) || (s.length() == 0))  {
//
//            return " ";
//        }
//

        if (s.startsWith(COMMENT_IDENTIFIER))  {
            return "Comment";
        }
        //firstWord = st.nextToken();
		
		int index = s.indexOf(" ");
		
		if (index > -1)  {
		
			firstWord = s.substring(0, index);
		}
		else {
		
			firstWord = s;
		}
		
        if (firstWord.endsWith(LABEL_IDENTIFIER)) {
            return "Label";
        }
        return firstWord;
    }

    /**
     * Returns true if the passed in string contains an operand
     */
    public static final boolean containsOperand(String s)  {
    
        if (s.startsWith(COMMENT_IDENTIFIER))  {
            return false;
            
        } else  {
            return ( (s.trim()).indexOf(' ') > -1 );
        }    
    }
    
    /**
     * Returns the instruction as entered (Ex. pop num:9)
     * <p>
     * @return  Returns Instruction in string format
     */
    public String getInstAsEntered() {
    
        return instAsEntered;
    }

    /**
     * Returns instClass member variable
     * <p>
     * @return  Returns value of instClass
     */
    private int getInstClass() {
	
        return instClass;
    }

    /**
     * Returns the position of this instruction in the script
     * <p>
     * @return  Returns value of instLineNr
     */
    public int getInstLineNr() {
	
        return instLineNr;
    }
    
    /**
     * Returns instType member variable
     * <p>
     * @return  Returns value of instType
     */
    private int getInstType() {
    
        return instType;
    }

    /**
     * Returns operator member variable
     * <p>
     * @return  Returns operator in String format
     */
    public String getOperator() {
    
        return operator;
    }

    /**
     * Sets instAsEntered member variable
     * <p>
     * @param s  New value of instAsEntered
     */
    public void setInstAsEntered(String s) {

        instAsEntered = s;
    }

    /**
     * Sets instClass member variable
     * <p>
     * @param i  New Value of instClass
     */
    private void setInstClass(int i) {
        
        instClass = i;
    }

    /**
     * Sets instLineNr member variable
     * <p>
     * @param i  New Value of instLineNr
     */
    protected void setInstLineNr(int i) {
        
        instLineNr = i;
    }

    /**
     * Sets instType member variable
     * <p>
     * @param i  New Value of instType
     */
    private void setInstType(int i) {
    
        instType = i;
    }

    /**
     * Sets operator member variable
     * <p>
     * @param s  New Value of operator
     */
    protected void setOperator(String s) {
    
        operator = s;
    }

    /**
      * Returns the external name
      * <p>
      * @return Returns the external name
      */
     public String getExternalName() {
         return externalName;
     }

   /**
     * Used to set externalName
     * <p>
     * @param typeNameIn New value of externalName
     */
    public void setExternalName(String externalNameIn) {

        if (externalNameIn == null)  {
            externalName = typeName;
        } else 	{
            this.externalName = externalNameIn;
        }
    }

  /**
     * Returns the type name
     * <p>
     * @return Returns the type name
     */
    public String getTypeName() {
        return typeName;
    }

    /**
      * Used to set typeName and externalName member variables
      * <p>
      * @param typeNameIn  New value of typeName and externalName
      * variables
      */
     public void setTypeName(String typeNameIn) {

         this.typeName = typeNameIn;
         if (externalName == null) {
             externalName = typeName;
         }
     }

    /**
     * Returns error information as a DOM Element.
     * @return
     */
    public Element getErrorOutput()
    {
        String className = getClass().getName();

        Element element = new DefaultElement(className);

        return element;
    }

    protected Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }    
}