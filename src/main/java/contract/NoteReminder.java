/*
 * User: sramamurthy
 * Date: Aug 12, 2004
 * Time: 9:58:25 AM
 *
 * 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package contract;

import edit.common.EDITDateTime;

import edit.services.db.*;
import edit.services.db.hibernate.*;
import edit.common.vo.VOObject;
import edit.common.vo.NoteReminderVO;

import java.util.*;


public class NoteReminder extends HibernateEntity implements CRUDEntity
{
    private CRUDEntityI crudEntityImpl;

    private NoteReminderVO noteReminderVO;

    private Segment segment;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    /**
     * Instantiates a NoteReminder entity with a default NoteReminderVO.
     */
    public NoteReminder() {
        init();
    }

    /**
     * Instantiates a NoteReminder entity with a NoteReminderVO retrieved from persistence.
     * 
     * @param noteReminderPK 
     */
    public NoteReminder(long noteReminderPK) {
        init();

        crudEntityImpl.load(this, noteReminderPK, ConnectionFactory.EDITSOLUTIONS_POOL );
    }

    /**
     * Instantiates a NoteReminder entity with a supplied NoteReminderVO.
     * 
     * @param noteReminderVO 
     */
    public NoteReminder(NoteReminderVO noteReminderVO) {
        init();

        this.noteReminderVO = noteReminderVO;
    }

    /**
     * Guarantees that a VO and CRUDEntityImpl are properly instantiated.
     */
    private final void init() {
        if (noteReminderVO == null) {
            noteReminderVO = new NoteReminderVO();
        }

        if (crudEntityImpl == null) {
            crudEntityImpl = new CRUDEntityImpl();
        }
    }

    /**
     * @see edit.services.db.CRUDEntity#save()
     */
    public void save(){
        try {
            crudEntityImpl.save(this, ConnectionFactory.EDITSOLUTIONS_POOL, false);
        } catch (Throwable t) {
            System.out.println(t);

            t.printStackTrace();
        }
    }

    /**
     * @see edit.services.db.CRUDEntity#delete()
     */
    public void delete() {
        try {
            crudEntityImpl.delete(this, ConnectionFactory.EDITSOLUTIONS_POOL);
        } catch (Throwable e) {
            System.out.println(e);

            e.printStackTrace();
        }
    }

    /**
     * @return 
     * @see edit.services.db.CRUDEntity#getVO()
     */
    public VOObject getVO() {
        return noteReminderVO;
    }

    /**
     * @return 
     * @see edit.services.db.CRUDEntity#getPK()
     */
    public long getPK() {
        return noteReminderVO.getNoteReminderPK();
    }

    /**
     * @param voObject 
     */
    public void setVO(VOObject voObject) {
        this.noteReminderVO = (NoteReminderVO) voObject;
    }

    /**
     * @return 
     * @see edit.services.db.CRUDEntity#isNew()
     */
    public boolean isNew() {
        return crudEntityImpl.isNew(this);
    }

    /**
     * @return 
     * @see edit.services.db.CRUDEntity#cloneCRUDEntity()
     */
    public CRUDEntity cloneCRUDEntity() {
        return crudEntityImpl.cloneCRUDEntity(this);
    }

    /**
     * Getter.
     * @return
     */
    public EDITDateTime getMaintDateTime()
    {
        return SessionHelper.getEDITDateTime(noteReminderVO.getMaintDateTime());
    } //-- java.lang.String getMaintDateTime()

    /**
     * Getter.
     * @return
     */
    public String getNote()
    {
        return noteReminderVO.getNote();
    } //-- java.lang.String getNote()

    /**
     * Getter.
     * @return
     */
    public String getNoteQualifierCT()
    {
        return noteReminderVO.getNoteQualifierCT();
    } //-- java.lang.String getNoteQualifierCT()

    /**
     * Getter.
     * @return
     */
    public long getNoteReminderPK()
    {
        return noteReminderVO.getNoteReminderPK();
    } //-- long getNoteReminderPK()

    /**
     * Getter.
     * @return
     */
    public String getNoteTypeCT()
    {
        return noteReminderVO.getNoteTypeCT();
    } //-- java.lang.String getNoteTypeCT()

    /**
     * Getter.
     * @return
     */
    public String getOperator()
    {
        return noteReminderVO.getOperator();
    } //-- java.lang.String getOperator()

    /**
     * Getter.
     * @return
     */
    public long getSegmentFK()
    {
        return noteReminderVO.getSegmentFK();
    } //-- long getSegmentFK()

    /**
     * Getter.
     * @return
     */
    public int getSequence()
    {
        return noteReminderVO.getSequence();
    } //-- int getSequence()

    /**
     * Setter.
     * @param maintDateTime
     */
    public void setMaintDateTime(EDITDateTime maintDateTime)
    {
        noteReminderVO.setMaintDateTime(SessionHelper.getEDITDateTime(maintDateTime));
    } //-- void setMaintDateTime(java.lang.String)

    /**
     * Setter.
     * @param note
     */
    public void setNote(String note)
    {
        noteReminderVO.setNote(note);
    } //-- void setNote(java.lang.String)

    /**
     * Setter.
     * @param noteQualifierCT
     */
    public void setNoteQualifierCT(String noteQualifierCT)
    {
        noteReminderVO.setNoteQualifierCT(noteQualifierCT);
    } //-- void setNoteQualifierCT(java.lang.String)

    /**
     * Setter.
     * @param noteReminderPK
     */
    public void setNoteReminderPK(long noteReminderPK)
    {
        noteReminderVO.setNoteReminderPK(noteReminderPK);
    } //-- void setNoteReminderPK(long)

    /**
     * Setter.
     * @param noteTypeCT
     */
    public void setNoteTypeCT(String noteTypeCT)
    {
        noteReminderVO.setNoteTypeCT(noteTypeCT);
    } //-- void setNoteTypeCT(java.lang.String)

    /**
     * Setter.
     * @param operator
     */
    public void setOperator(String operator)
    {
        noteReminderVO.setOperator(operator);
    } //-- void setOperator(java.lang.String)

    /**
     * Setter.
     * @param segmentFK
     */
    public void setSegmentFK(long segmentFK)
    {
        noteReminderVO.setSegmentFK(segmentFK);
    } //-- void setSegmentFK(long)

    /**
     * Setter.
     * @param sequence
     */
    public void setSequence(int sequence)
    {
        noteReminderVO.setSequence(sequence);
    } //-- void setSequence(int)

    /**
     * Getter.
     * @return
     */
    public Segment getSegment()
    {
        return segment;
    }

    /**
     * Setter.
     * @param segment
     */
    public void setSegment(Segment segment)
    {
        this.segment = segment;
    }

	/**
	 * Save the entity using Hibernate
     */
    public void hSave()
    {
        SessionHelper.saveOrUpdate(this, NoteReminder.DATABASE);
    }

    /**
     * Delete the entity using Hibernate
     */
    public void hDelete()
    {
        SessionHelper.delete(this, NoteReminder.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return NoteReminder.DATABASE;
    }

    /**
     * Originally in NoteReminderDAO.findBySegmentPK
     * @param segmentPK
     * @return
     */
     public static NoteReminder[] findBy_SegmentPK(Long segmentPK)
    {
        String hql = " select noteReminder from NoteReminder noteReminder" +
                    " where noteReminder.SegmentFK = :segmentPK";

        Map params = new HashMap();

        params.put("segmentPK", segmentPK);

        List results = SessionHelper.executeHQL(hql, params, NoteReminder.DATABASE);

        return (NoteReminder[]) results.toArray(new NoteReminder[results.size()]);
	}
}
