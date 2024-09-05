/*
 * User: sprasad
 * Date: Apr 5, 2005
 * Time: 12:38:38 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package casetracking;

import client.*;
import edit.services.db.hibernate.*;
import edit.common.*;

import java.util.*;

public class CasetrackingNote extends HibernateEntity
{
    private Long casetrackingNotePK;
    private int sequence;
    private String note;
    private String noteTypeCT;
    private String noteQualifierCT;
    private String operator;
    private EDITDateTime maintDateTime;
    private Long clientDetailFK;
    private ClientDetail clientDetail;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Generic Constructor.
     */
    public CasetrackingNote()
    {
    }

    public Long getClientDetailFK()
    {
        return clientDetailFK;
    }

    public void setClientDetailFK(Long clientDetailFK)
    {
        this.clientDetailFK = clientDetailFK;
    }

    /**
     * Getter.
     * @return
     */
    public Long getCasetrackingNotePK()
    {
        return casetrackingNotePK;
    }

    /**
     * Setter.
     * @param casetrackingNotePK
     */
    public void setCasetrackingNotePK(Long casetrackingNotePK)
    {
        this.casetrackingNotePK = casetrackingNotePK;
    }

    /**
     * Getter.
     * @return
     */
    public int getSequence()
    {
        return sequence;
    }

    /**
     * Setter.
     * @param sequence
     */
    public void setSequence(int sequence)
    {
        this.sequence = sequence;
    }

    /**
     * Getter.
     * @return
     */
    public String getNote()
    {
        return note;
    }

    /**
     * Setter.
     * @param note
     */
    public void setNote(String note)
    {
        this.note = note;
    }

    /**
     * Getter.
     * @return
     */
    public String getNoteTypeCT()
    {
        return noteTypeCT;
    }

    /**
     * Setter.
     * @param noteTypeCT
     */
    public void setNoteTypeCT(String noteTypeCT)
    {
        this.noteTypeCT = noteTypeCT;
    }

    /**
     * Getter.
     * @return
     */
    public String getNoteQualifierCT()
    {
        return noteQualifierCT;
    }

    /**
     * Setter.
     * @param noteQualifierCT
     */
    public void setNoteQualifierCT(String noteQualifierCT)
    {
        this.noteQualifierCT = noteQualifierCT;
    }

    /**
     * Getter.
     * @return
     */
    public String getOperator()
    {
        return operator;
    }

    /**
     * Setter.
     * @param operator
     */
    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    /**
     * Getter.
     * @return
     */
    public EDITDateTime getMaintDateTime()
    {
        return maintDateTime;
    }

    /**
     * Setter.
     * @param maintDateTime
     */
    public void setMaintDateTime(EDITDateTime maintDateTime)
    {
        this.maintDateTime = maintDateTime;
    }

    /**
     * Getter.
     * @return
     */
    public ClientDetail getClientDetail()
    {
        return clientDetail;
    }

    /**
     * Setter.
     * @param clientDetail
     */
    public void setClientDetail(ClientDetail clientDetail)
    {
        this.clientDetail = clientDetail;
    }

    /**
     * Finder by ClientDetail
     * @param clientDetailFK
     * @return
     */
    public static final CasetrackingNote[] findBy_ClientDetailFK(Long clientDetailFK)
    {
        String hql = "select cn from CasetrackingNote cn where cn.ClientDetail = :clientDetailFK";

        Map params = new HashMap();

        params.put("clientDetailFK", clientDetailFK);

        List results = SessionHelper.executeHQL(hql, params, CasetrackingNote.DATABASE);

        return (CasetrackingNote[]) results.toArray(new CasetrackingNote[results.size()]);
    }

    /**
     * Finder by PK.
     * @param casetrackingNotePK
     * @return
     */
    public static final CasetrackingNote findByPK(Long casetrackingNotePK)
    {
        return (CasetrackingNote) SessionHelper.get(CasetrackingNote.class, casetrackingNotePK, CasetrackingNote.DATABASE);
    }

    public static final int findHighestSequenceNumber_ByClientDetail(ClientDetail clientDetail)
    {
        int highestSequenceNumber = 0;

        String hql = "select max(cn.Sequence) from CasetrackingNote cn where cn.ClientDetail = :clientDetail";

        Map params = new HashMap();

        params.put("clientDetail", clientDetail);

        List results = SessionHelper.executeHQL(hql, params, CasetrackingNote.DATABASE);

        if (results.get(0) != null)
        {
            highestSequenceNumber = ((Integer) results.get(0)).intValue();
        }

        return highestSequenceNumber;
    }

    /**
     * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, CasetrackingNote.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, CasetrackingNote.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return CasetrackingNote.DATABASE;
    }
}
