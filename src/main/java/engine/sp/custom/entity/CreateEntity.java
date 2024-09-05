package engine.sp.custom.entity;

import edit.services.db.hibernate.HibernateEntity;

import edit.services.db.hibernate.SessionHelper;

import engine.common.Constants;

import engine.sp.Activateentity;
import engine.sp.InstOneOperand;
import engine.sp.SPException;
import engine.sp.SPParams;
import engine.sp.ScriptProcessor;

import fission.utility.Util;

import java.text.SimpleDateFormat;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * Maps the specified last-active-element (VO) to its
 * corresponding Entity equivalent and then stores it
 * in the associated Session for the current ScriptProcessor.
 * 
 * Syntax:
 * 
 * Activateentity(Create, a.b.c.FooVO)
 * 
 * This would map the specified a.b.c.FooVO to its corresponding Entity.
 */
public class CreateEntity extends ActivateEntityCommand
{
    public CreateEntity()
    {
    }

    /**
     * Examines the parameters to determine which last-active-element is
     * to be converted to a Hibernate equivalent and then saves it to the Hibernate
     * session associated with the currently running ScriptProcessor.
     * 
     * @param sp
     * @param parameterTokens
     */
     @Override
     public void execute(ScriptProcessor sp, String[] parameterTokens) throws SPException
     {
        String lastActiveElementPath = getElementPath(parameterTokens);
        
        Element lastActiveElement = sp.getSPParams().getLastActiveElement(InstOneOperand.checkForAlias(lastActiveElementPath, sp));
        
        HibernateEntity hibernateEntity = mapToHibernateEntity(sp, lastActiveElement);
        
        synchronizePK(lastActiveElement, hibernateEntity);
    }
    
    /**
     * Gets the fully qualified path for the targeted last active element.
     * @return
     */
    private String getElementPath(String[] parameterTokens)
    {
        return parameterTokens[1];        
    }

    /**
     * A convenience method to map the specified last active element to its Hibernate equivalent.
     * 
     * This entity has automatically saved to the Hibernate Session as part of the
     * SessionHelper.mapToHibernateEntity method.
     * @return
     */
    private HibernateEntity mapToHibernateEntity(ScriptProcessor sp, Element lastActiveElement)
    {
        String elementName = lastActiveElement.getName();
        
        String tableName = elementName.substring(0, elementName.length() - 2);
        
        Class hibernateClass = SessionHelper.getHibernateClass(tableName, SessionHelper.EDITSOLUTIONS);
        
        HibernateEntity hibernateEntity = SessionHelper.mapToHibernateEntity(hibernateClass, lastActiveElement, sp.getHibernateSession(), false);
        
        return hibernateEntity;
    }

    /**
     * To keep the DOM model in sync with the Hibenate model, set the newly created PK back on the DOM Element.
     * @param lastActiveElement
     * @param hibernateEntity
     */
    private void synchronizePK(Element lastActiveElement, HibernateEntity hibernateEntity)
    {
        String pkFieldName = Util.getClassName(Util.getFullyQualifiedClassName(hibernateEntity.getClass())) + "PK";
        
        Long pkValue = SessionHelper.getPKValue(hibernateEntity);
        
        Element pkElement = lastActiveElement.element(pkFieldName);
        
        if (pkElement == null)
        {
            pkElement = new DefaultElement(pkFieldName);
            
            lastActiveElement.add(pkElement);
        }
        
        pkElement.setText(pkValue.toString());
    }
}
