package engine.sp.custom.entity;

import edit.services.db.hibernate.HibernateEntity;

import edit.services.db.hibernate.SessionHelper;

import engine.sp.SPException;
import engine.sp.ScriptProcessor;

import java.util.List;

import org.dom4j.Element;

/**
 * If the scripter finds that too many results have been placed in the ResultDocVO, 
 * then the user may clear its contents. This includes the purging of the
 * associated entity in the current Hibernate Session.
 * 
 * The syntax is as follows:
 * 
 * Activateentity(Clear)
 */
public class ClearEntity extends ActivateEntityCommand
{
    public ClearEntity()
    {
    }

    @Override
    public void execute(ScriptProcessor sp, String[] parameterTokens) throws SPException
    {
//        List<Element> resultDocVOElements = sp.getResultDocVO().getRootElement().elements();
//        
//        for (Element resultDocVOElement:resultDocVOElements)
//        {
//            HibernateEntity hibernateEntity = SessionHelper.getHibernateEntity(resultDocVOElement, sp.getHibernateSession());
//            
//            sp.getHibernateSession().evict(hibernateEntity);
//        }
//        
//        sp.clearResultDocVO();
//        
//        sp.incrementInstPtr();
        
        throw new SPException("This instruction [ClearEntity] is no longer in use", SPException.INSTRUCTION_PROCESSING_ERROR);
    }
}
