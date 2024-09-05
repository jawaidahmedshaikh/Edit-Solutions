package engine.sp.custom.entity;

import edit.services.db.hibernate.SessionHelper;

import engine.sp.Activateentity;
import engine.sp.ScriptProcessor;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

/**
 * Commits whatever has been placed withing the Hibernate session associated with 
 * the currently running ScriptProcessor to the DB.
 */
public class CommitEntity extends ActivateEntityCommand
{
    public CommitEntity()
    {
    }

    public void execute(ScriptProcessor sp, String[] parameterTokens)
    {
        Transaction t = null;

        try
        {
            SessionHelper.putInThreadLocal(SessionHelper.DISABLE_NF_FRAMEWORK, Boolean.TRUE);

            t = sp.getHibernateSession().beginTransaction();

            t.commit();
        }
        catch (HibernateException e)
        {
            System.out.println(e);
            
            e.printStackTrace();
            
            if (t != null)
            {
                t.rollback();
            }
            
            throw e;
        }
        finally
        {
            SessionHelper.removeFromThreadLocal(SessionHelper.DISABLE_NF_FRAMEWORK);

            t = null;
            
            sp.getHibernateSession().clear();
        }
    }
}
