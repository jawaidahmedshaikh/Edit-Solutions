package engine.sp.custom.entity;

import edit.services.db.hibernate.HibernateEntity;

import edit.services.db.hibernate.SessionHelper;

import engine.sp.Activateentity;
import engine.sp.InstOneOperand;
import engine.sp.SPException;
import engine.sp.ScriptProcessor;

import org.dom4j.Element;

/**
 * Deletes the associated Hibernate Entity identified by the 
 * specified last-active Element.
 * 
 * The syntax is as follows:
 * 
 * Activateentity(Delete, a.b.c.Foo)
 */
public class DeleteEntity extends ActivateEntityCommand
{
    public DeleteEntity()
    {
    }

    public void execute(ScriptProcessor sp, String[] parameterTokens) throws SPException
    {
        Element lastActiveElement = sp.getSPParams().getLastActiveElement(InstOneOperand.checkForAlias(parameterTokens[1], sp)); // according to the syntax of the instruction
        
        HibernateEntity hibernateEntity = SessionHelper.getHibernateEntity(lastActiveElement, sp.getHibernateSession());
        
        SessionHelper.delete(hibernateEntity, sp.getHibernateSession());
    }
}
