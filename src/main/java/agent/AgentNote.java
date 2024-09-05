package agent;

import edit.common.vo.AgentNoteVO;
import edit.common.vo.VOObject;
import edit.common.*;
import edit.services.db.CRUDEntity;
import edit.services.db.hibernate.*;

/**
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: Dec 5, 2003
 * Time: 11:24:16 AM
 * To change this template use Options | File Templates.
 */
public class AgentNote extends HibernateEntity implements CRUDEntity
{
    private AgentNoteVO agentNoteVO;

    private AgentNoteImpl agentNoteImpl;

    private Agent agent;
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public AgentNote()
    {
        this.agentNoteVO = new AgentNoteVO();
        this.agentNoteImpl = new AgentNoteImpl();
    }

    public AgentNote(long agentNotePK) throws Exception
    {
        this();
        this.agentNoteImpl.load(this, agentNotePK);
    }

    public AgentNote(AgentNoteVO agentNoteVO)
    {
        this();
        this.agentNoteVO = agentNoteVO;
    }

    /**
     * Getter
     * @return
     */
    public Long getAgentFK()
    {
        return agentNoteVO.getAgentFK();
    } //-- long getAgentFK()

    /**
     * Getter
     * @return
     */
    public Long getAgentNotePK()
    {
        return agentNoteVO.getAgentNotePK();
    } //-- long getAgentNotePK()

    /**
     * Getter
     * @return
     */
    public EDITDateTime getMaintDateTime()
    {
        return SessionHelper.getEDITDateTime(agentNoteVO.getMaintDateTime());
    } //-- java.lang.String getMaintDateTime()

    /**
     * Getter
     * @return
     */
    public String getNote()
    {
        return agentNoteVO.getNote();
    } //-- java.lang.String getNote()

    /**
     * Getter
     * @return
     */
    public String getNoteQualifierCT()
    {
        return agentNoteVO.getNoteQualifierCT();
    } //-- java.lang.String getNoteQualifierCT()

    /**
     * Getter
     * @return
     */
    public String getNoteTypeCT()
    {
        return agentNoteVO.getNoteTypeCT();
    } //-- java.lang.String getNoteTypeCT()

    /**
     * Getter
     * @return
     */
    public String getOperator()
    {
        return agentNoteVO.getOperator();
    } //-- java.lang.String getOperator()

    /**
     * Getter
     * @return
     */
    public int getSequence()
    {
        return agentNoteVO.getSequence();
    } //-- int getSequence()

    /**
     * Setter
     * @param agentFK
     */
    public void setAgentFK(Long agentFK)
    {
        agentNoteVO.setAgentFK(agentFK);
    } //-- void setAgentFK(long)

    /**
     * Setter
     * @param agentNotePK
     */
    public void setAgentNotePK(Long agentNotePK)
    {
        agentNoteVO.setAgentNotePK(agentNotePK);
    } //-- void setAgentNotePK(long)

    /**
     * Setter
     * @param maintDateTime
     */
    public void setMaintDateTime(EDITDateTime maintDateTime)
    {
        agentNoteVO.setMaintDateTime(SessionHelper.getEDITDateTime(maintDateTime));
    } //-- void setMaintDateTime(java.lang.String)

    /**
     * Setter
     * @param note
     */
    public void setNote(String note)
    {
        agentNoteVO.setNote(note);
    } //-- void setNote(java.lang.String)

    /**
     * Setter
     * @param noteQualifierCT
     */
    public void setNoteQualifierCT(String noteQualifierCT)
    {
        agentNoteVO.setNoteQualifierCT(noteQualifierCT);
    } //-- void setNoteQualifierCT(java.lang.String)

    /**
     * Setter
     * @param noteTypeCT
     */
    public void setNoteTypeCT(String noteTypeCT)
    {
        agentNoteVO.setNoteTypeCT(noteTypeCT);
    } //-- void setNoteTypeCT(java.lang.String)

    /**
     * Setter
     * @param operator
     */
    public void setOperator(String operator)
    {
        agentNoteVO.setOperator(operator);
    } //-- void setOperator(java.lang.String)

    /**
     * Setter
     * @param sequence
     */
    public void setSequence(int sequence)
    {
        agentNoteVO.setSequence(sequence);
    } //-- void setSequence(int)

    public void save()
    {
        this.agentNoteImpl.save(this);
    }

    public void delete() throws Exception
    {
        this.agentNoteImpl.delete(this);
    }

    public void associateAgent(Agent agent)
    {
//        this.agentNoteVO.setAgentFK(agent.getPK());
//        save();
        
        this.setAgent(agent);
        hSave();
    }

    public VOObject getVO()
    {
        return agentNoteVO;
    }

    public long getPK()
    {
        return agentNoteVO.getAgentNotePK();
    }

    public void setVO(VOObject voObject)
    {
        this.agentNoteVO = (AgentNoteVO) voObject;
    }

    public boolean isNew()
    {
        return this.agentNoteImpl.isNew(this);
    }

    public CRUDEntity cloneCRUDEntity()
    {
        return this.agentNoteImpl.cloneCRUDEntity(this);
    }

    /**
     * sets sequence numbers for the missing agent notes, if any or
     * for new agent notes for which sequence number is set to zero temporarily
     * @param agentNoteVOs
     * @throws Exception
     */
    public static void setAgentNoteSequenceNumbers(AgentNoteVO[] agentNoteVOs) throws Exception
    {
        int highestSequence = 0;
        boolean seqNumberMissing = false;
        int i;

        int sequenceNumber;
        AgentNoteVO agentNoteVO;

        if ( agentNoteVOs != null && agentNoteVOs.length > 0 ) {
            for ( i = 0; i < agentNoteVOs.length; i++ ) {
                agentNoteVO = agentNoteVOs[i];

                if ( agentNoteVO != null ) {
                    sequenceNumber = agentNoteVO.getSequence();
                    if ( sequenceNumber > highestSequence ) {

                        highestSequence = sequenceNumber;
                    }
                    else if ( sequenceNumber == 0 ) {
                        seqNumberMissing = true;
                    } // end if
                } // end if
            } // end for
        } // end if

        if (seqNumberMissing) {
            for (i = 0; i < agentNoteVOs.length; i++) {
                if (agentNoteVOs[i].getSequence() == 0) {
                    highestSequence += 1;
                    agentNoteVOs[i].setSequence(highestSequence);
                }  // end if
            } // end for
        }  // end if
    } // end function


    /**
     * returns new sequence number
     * @return
     */
    public static int getNextSequenceNumber(AgentNoteVO[] agentNoteVOs) throws Exception {

        int highestSequence = 0;
        AgentNoteVO agentNoteVO;
        int sequenceNumber;

        if ( agentNoteVOs != null && agentNoteVOs.length > 0 ) {
            for (int i = 0; i < agentNoteVOs.length; i++) {
                agentNoteVO = (AgentNoteVO) agentNoteVOs[i];

                if ( agentNoteVO != null ) {
                    sequenceNumber = agentNoteVO.getSequence();
                    if ( highestSequence < sequenceNumber ) {

                        highestSequence = sequenceNumber;
                    } // end if
                } // end if
            } // end for
        } // end if

        return highestSequence + 1;
    }

    /**
     * Getter.
     * @return
     */
    public Agent getAgent()
    {
        return agent;
    }

    /**
     * Setter.
     */
    public void setAgent(Agent agent)
    {
        this.agent = agent;
    }

   /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return AgentNote.DATABASE;
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, AgentNote.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, AgentNote.DATABASE);
    }

}