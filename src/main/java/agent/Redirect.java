/*
 * User: cgleason
 * Date: Dec 5, 2003
 * Time: 11:24:16 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package agent;

import edit.common.vo.RedirectVO;
import edit.common.vo.VOObject;
import edit.common.*;
import edit.services.db.CRUDEntity;
import edit.services.db.hibernate.*;
import role.*;

public class Redirect extends HibernateEntity implements CRUDEntity
{
    public static final String REDIRECTYPE_REVERSION = "Reversion";
    public static final String REDIRECTYPE_ASSIGNEE = "Assignee";

    private RedirectVO redirectVO;

    private RedirectImpl redirectImpl;

    private Agent agent;
    
    private ClientRole clientRole;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public Redirect()
    {
        this.redirectVO = new RedirectVO();
        this.redirectImpl = new RedirectImpl();
    }

    public Redirect(long redirectPK) throws Exception
    {
        this();
        this.redirectImpl.load(this, redirectPK);
    }

    public Redirect(RedirectVO redirectVO)
    {
        this();
        this.redirectVO = redirectVO;
    }

    public void save()
    {
        this.redirectImpl.save(this);
    }

    public void delete() throws Exception
    {
        this.redirectImpl.delete(this);
    }

    public void associateAgent(Agent agent)
    {
//        this.redirectVO.setAgentFK(agent.getPK());
        this.setAgent(agent);

        ClientRole clientRole = ClientRole.findByPK(this.getClientRoleFK());
        if (clientRole != null)
        {
            this.setClientRole(clientRole);
        }

        hSave();
    }


    public VOObject getVO()
    {
        return redirectVO;
    }

    public long getPK()
    {
        return redirectVO.getRedirectPK();
    }

    public void setVO(VOObject voObject)
    {
        this.redirectVO = (RedirectVO) voObject;
    }

    public boolean isNew()
    {
        return this.redirectImpl.isNew(this);
    }

    public CRUDEntity cloneCRUDEntity()
    {
        return this.redirectImpl.cloneCRUDEntity(this);
    }

     /**
     * Getter/Hibernate
     * @return
     */
    public Agent getAgent()
    {
        return agent;
    }

   /**
     * Setter.
     * @param agent
     */
    public void setAgent(Agent agent)
    {
        this.agent = agent;
    }

    /**
    * Getter/Hibernate
    * @return
    */
   public ClientRole getClientRole()
   {
       return clientRole;
   }

  /**
    * Setter.
    * @param agent
    */
   public void setClientRole(ClientRole clientRole)
   {
       this.clientRole = clientRole;
   }


    /**
     * Getter
      * @return
     */
    public Long getAgentFK()
    {
        return SessionHelper.getPKValue(redirectVO.getAgentFK());
    }

    /**
     * Setter
     * @param agentFK
     */
    public void setAgentFK(Long agentFK)
    {
        redirectVO.setAgentFK(SessionHelper.getPKValue(agentFK));
    }

    /**
     * Getter
      * @return
     */
    public Long getClientRoleFK()
    {
        return SessionHelper.getPKValue(redirectVO.getClientRoleFK());
    }

    /**
     * Setter
     * @param clientRoleFK
     */
    public void setClientRoleFK(Long clientRoleFK)
    {
        redirectVO.setClientRoleFK(SessionHelper.getPKValue(clientRoleFK));
    }

    /**
     * Getter
     * @return
     */
    public Long getRedirectPK()
    {
        return SessionHelper.getPKValue(redirectVO.getRedirectPK());
    } //-- long getRedirectPK()

    /**
     * Getter
     * @return
     */
    public String getRedirectTypeCT()
    {
        return redirectVO.getRedirectTypeCT();
    } //-- java.lang.String getRedirectTypeCT()

    /**
     * Getter
     * @return
     */
    public EDITDate getStartDate()
    {
        return SessionHelper.getEDITDate(redirectVO.getStartDate());
    } //-- java.lang.String getStartDate()

    /**
     * Getter
     * @return
     */
    public EDITDate getStopDate()
    {
        return SessionHelper.getEDITDate(redirectVO.getStopDate());
    } //-- java.lang.String getStopDate()

    /**
     * Setter
     * @param redirectPK
     */
    public void setRedirectPK(Long redirectPK)
    {
        redirectVO.setRedirectPK(SessionHelper.getPKValue(redirectPK));
    } //-- void setRedirectPK(long)

    /**
     * Setter
     * @param redirectTypeCT
     */
    public void setRedirectTypeCT(String redirectTypeCT)
    {
        redirectVO.setRedirectTypeCT(redirectTypeCT);
    } //-- void setRedirectTypeCT(java.lang.String)

    /**
     * Setter
     * @param startDate
     */
    public void setStartDate(EDITDate startDate)
    {
        redirectVO.setStartDate(SessionHelper.getEDITDate(startDate));
    } //-- void setStartDate(java.lang.String)

    /**
     * Setter
     * @param stopDate
     */
    public void setStopDate(EDITDate stopDate)
    {
        redirectVO.setStopDate(SessionHelper.getEDITDate(stopDate));
    } //-- void setStopDate(java.lang.String)

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return Redirect.DATABASE;
    }
}
