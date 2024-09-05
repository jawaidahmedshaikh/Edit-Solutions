/*
 * 
 * User: cgleason
 * Date: Oct 30, 2007
 * Time: 10:56:30 AM
 * 
 * (c) 2000 - 2007 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 */
package group;

import edit.services.db.hibernate.*;
import edit.common.*;

import java.util.*;

public class ContractGroupNote  extends HibernateEntity
{

    private Long contractGroupNotePK;
    private Long contractGroupFK;
    private String noteTypeCT;
    private String noteQualifierCT;
    private int sequence;
    private String note;
    private String operator;
    private EDITDateTime maintDateTime;
    private ContractGroup contractGroup;

    public static final String DATABASE = SessionHelper.EDITSOLUTIONS;

    public ContractGroupNote()
    {

    }

   /**
    * Getter
    * @return
    */
    public Long getContractGroupNotePK()
    {
        return contractGroupNotePK;
    }

   /**
    * Setter
    * @param contractGroupNotePK
    */
    public void setContractGroupNotePK(Long contractGroupNotePK)
    {
        this.contractGroupNotePK = contractGroupNotePK;
    }

   /**
    * Getter
    * @return
    */
    public Long getContractGroupFK()
    {
        return contractGroupFK;
    }

    /**
     * Setter
     * @param contractGroupFK
     */
    public void setContractGroupFK(Long contractGroupFK)
    {
        this.contractGroupFK = contractGroupFK;
    }

    /**
     * Getter
     * @return
     */
    public int getSequence()
    {
        return sequence;
    }

    /**
     * Setter
     * @param sequence
     */
    public void setSequence(int sequence)
    {
        this.sequence = sequence;
    }

    /**
     * Getter
     * @return
     */
    public String getNoteTypeCT()
    {
        return noteTypeCT;
    }

    /**
     * Setter
     * @param noteTypeCT
     */
    public void setNoteTypeCT(String noteTypeCT)
    {
        this.noteTypeCT = noteTypeCT;
    }

 /**
     * Getter
     * @return
     */
    public String getNote()
    {
        return note;
    }

    /**
     * Setter
     * @param note
     */
    public void setNote(String note)
    {
        this.note = note;
    }

    /**
     * Getter
     * @return
     */
    public String getNoteQualifierCT()
    {
        return noteQualifierCT;
    }

    /**
     * Setter
     * @param noteQualifierCT
     */
    public void setNoteQualifierCT(String noteQualifierCT)
    {
        this.noteQualifierCT = noteQualifierCT;
    }

    /**
     * Getter
     * @return
     */
    public String getOperator()
    {
        return operator;
    }

    /**
     * Setter
     * @param operator
     */
    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    /**
     * Getter
     * @return
     */
    public EDITDateTime getMaintDateTime()
    {
        return maintDateTime;
    }


    /**
     * Setter
     * @param maintDateTime
     */
    public void setMaintDateTime(EDITDateTime maintDateTime)
    {
        this.maintDateTime = maintDateTime;
    }

   /**
    * @see #contractGroup
    * @return ContractGroup
    */
    public ContractGroup getContractGroup()
    {
        return contractGroup;
    }

    /**
     * @see #contractGroup
     * @param contractGroup
     */
    public void setContractGroup(ContractGroup contractGroup)
    {
        this.contractGroup = contractGroup;
    }

    public void hDelete()
    {
        SessionHelper.delete(this, ContractGroupNote.DATABASE);
    }

    public String getDatabase()
    {
        return ContractGroupNote.DATABASE;
    }

    public int calculateSequence(ContractGroup contractGroup)
    {
        int sequence = 0;

        Set contractGroupNotes = contractGroup.getContractGroupNotes();

        if (!contractGroupNotes.isEmpty())
        {
            sequence = contractGroupNotes.size() + 1;
        }
        else
            sequence = 1;

        return sequence;
    }

    /**
     * Finder.
     * @param contractGroupNotePK
     * @return
     */
    public static ContractGroupNote findByPK(Long contractGroupNotePK)
    {
        return (ContractGroupNote) SessionHelper.get(ContractGroupNote.class, contractGroupNotePK, ContractGroupNote.DATABASE);
    }

    public static ContractGroupNote[] findBy_ContractGroupPK(Long contractGroupPK)
    {
        String hql = " from ContractGroupNote contractGroupNote" +
                    " where contractGroupNote.ContractGroupFK = :contractGroupFK" +
                    " order by contractGroupNote.Sequence";

        EDITMap params = new EDITMap("contractGroupFK", contractGroupPK);

        List<ContractGroupNote> results = SessionHelper.executeHQL(hql, params, ContractGroupNote.DATABASE);

        return results.toArray(new ContractGroupNote[results.size()]);

    }
}
