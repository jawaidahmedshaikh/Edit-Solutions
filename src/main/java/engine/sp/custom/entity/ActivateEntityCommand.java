package engine.sp.custom.entity;

import edit.services.db.hibernate.HibernateEntity;

import edit.services.db.hibernate.SessionHelper;

import engine.sp.SPException;
import engine.sp.ScriptProcessor;

import org.dom4j.Element;

/**
 * The parent class to all Instructions related to the management of the Hibernate model
 * and its relationship to the corresponding DOM model.
 */
public abstract class ActivateEntityCommand
{
    public ActivateEntityCommand()
    {
    }
    
    public abstract void execute(ScriptProcessor sp, String[] parameterTokens) throws SPException;
    
    /**
     * Most times we identify the target Element by an element expression such as:
     * ParentVO.ChildVO.ChildChildVO. This tells as that the target Element is ChildChildVO.
     * There are times when we don't want to be bound to using the last-active Element, but we
     * want to execute an XPath expression to "find" the VOs we want to target. An example might be:
     * 
     * /ParentVO.ChildVO.ChildChildVO[@ContractNumber=101].
     * 
     * In the above example, we actually execute an XPath against the ParentVO Document and
     * find ChildChildVOs with a ContractNumber attribute value of 101.
     * 
     * @param elementExpression
     * @return
     */
    public boolean isXPathExpression(String elementExpression)
    {
        return elementExpression.startsWith("/");        
    }
}
