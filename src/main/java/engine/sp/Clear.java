package engine.sp;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;

import engine.common.Constants;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import org.dom4j.tree.DefaultDocument;

import org.dom4j.tree.DefaultElement;

/**
 * Clears the named parameter from the map of WS entries or
 * from the RD (Results Document).
 * 
 * Syntax:
 * 
 * clear ws:FooWSEntry
 * 
 * or
 * 
 * clear rd:FooRDEntry
 * 
 * or 
 * 
 * clear ws:#ALL // clears the entire ws
 * 
 * or
 * 
 * clear rd:#ALL // clears the entire ResultDocVO
 * 
 * where FooWSEntry is a name/value pair in WS or an Element name in RD.
 */
public class Clear extends InstOneOperand
{
    public Clear()
    {
    }
    /**
     * @throws SPException
     * @see Inst#compile(engine.sp.ScriptProcessor)
     */
    protected void compile(ScriptProcessor scriptProcessor) throws SPException
    {
        sp = scriptProcessor;
        
        //Does common tasks in superclass such as
        //setting member variables and some operand editting
        commonCompileTasks();        
    }

    protected void exec(ScriptProcessor currentSP)
    {
        try
        {
            String operandDataName = getOperandDataName();
            
            // Remove named value from WS
            if (isWSReference())
            {
                if (operandDataName.equals(Constants.ScriptKeyword.ALL))
                {
                    currentSP.getWS().clear();                    
                }
                else
                {
                    currentSP.getWS().remove(operandDataName);
                }
            }
            
            // Remove named Element(s) from RD including any Hibernate entities that are backing the ResultDoc.
            else if (isRDReference())
            {
                List<Element> elementsToClear = null;
                
                if (operandDataName.equals(Constants.ScriptKeyword.ALL))
                {
                    elementsToClear = currentSP.getResultDocVO().getRootElement().elements();
                }
                else
                {
                    elementsToClear = currentSP.getResultDocVO().getRootElement().elements(operandDataName);
                }
                
                if (elementsToClear != null)
                {
                    for (Element resultDocElement:elementsToClear)
                    {
                        // 1st from the resultDoc itself...
                        currentSP.getResultDocVO().getRootElement().remove(resultDocElement);
                    }  
                    
                    // The for loop above should have removed everything from the ResultDoc - but let's be sure.
                    if (operandDataName.equals(Constants.ScriptKeyword.ALL))
                    {
                        currentSP.getResultDocVO().getRootElement().clearContent();
                    }
                }
            }
        }
        finally
        {
            currentSP.incrementInstPtr();
        }
    }
}
