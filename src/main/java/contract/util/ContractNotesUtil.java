/*
 * Created by IntelliJ IDEA.
 * User: sprasad
 * Date: Aug 19, 2004
 * Time: 1:56:16 PM
 * To change this template use File | Settings | File Templates.
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract.util;

import contract.NoteReminder;

import edit.common.vo.NoteReminderVO;

import edit.portal.common.session.UserSession;

import fission.beans.PageBean;
import fission.beans.SessionBean;

import fission.global.AppReqBlock;

import fission.utility.Util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ContractNotesUtil
{
    public ContractNotesUtil()
    {
        // default constructor
    }

    public static HashMap loadNotesReminderVOsFromSession(AppReqBlock appReqBlock, boolean sequencehandler)
    {
        HashMap notesVOs = new HashMap();
        SessionBean contractNotesSessionBean = appReqBlock.getSessionBean("contractNotesSessionBean");
        contractNotesSessionBean.removePageBean("formBean");

        Map notesPageBeans = contractNotesSessionBean.getPageBeans();
        Iterator itN = notesPageBeans.values().iterator();
        int highSeqNo = 0;
        boolean missingSequence = false;

        UserSession userSession = appReqBlock.getUserSession();
        long segmentPkey = userSession.getSegmentPK();

        //System.out.println("##SegmentPkey:"+segmentPkey);
        while (itN.hasNext())
        {
            PageBean noteBean = (PageBean) itN.next();

            String noteReminderPK = noteBean.getValue("noteReminderPK");
            String segmentFK = noteBean.getValue("segmentFK");
            String noteTypeId = noteBean.getValue("noteTypeId");
            String sequence = noteBean.getValue("sequence");
            String noteQualifierId = noteBean.getValue("noteQualifierId");
            String note = noteBean.getValue("note");
            String maintDate = noteBean.getValue("maintDate");
            String operator = noteBean.getValue("operator");
            String key = noteBean.getValue("key");
            String isNoteReminderChanged = noteBean.getValue("isNoteReminderChanged");

            NoteReminderVO noteReminderVO = new NoteReminderVO();

            if (!noteReminderPK.equals(""))
            {
                noteReminderVO.setNoteReminderPK(Long.parseLong(noteReminderPK));
            }

            else
            {
                noteReminderVO.setNoteReminderPK(0);
            }

            if (!segmentFK.equals(""))
            {
                noteReminderVO.setSegmentFK(Long.parseLong(segmentFK));
            }
            else
            {
                if (key.startsWith("-1"))
                {
                    noteReminderVO.setSegmentFK(segmentPkey);
                }
                else
                {
                    noteReminderVO.setSegmentFK(0);
                }
            }

            noteReminderVO.setNoteTypeCT(Util.initString(noteTypeId, null));

            if (!sequence.equals(""))
            {
                noteReminderVO.setSequence(Integer.parseInt(sequence));

                if (noteReminderVO.getSequence() > highSeqNo)
                {
                    highSeqNo = noteReminderVO.getSequence();
                }
            }
            else
            {
                noteReminderVO.setSequence(0);
                missingSequence = true;
            }

            noteReminderVO.setNoteQualifierCT(Util.initString(noteQualifierId, null));
            noteReminderVO.setNote(Util.initString(note, null));
            noteReminderVO.setMaintDateTime(maintDate);
            noteReminderVO.setOperator(Util.initString(operator, null));

            if (isNoteReminderChanged.equals("true"))
            {
                noteReminderVO.setVoChanged(true);
            }
            else
            {
                noteReminderVO.setVoChanged(false);
            }

            notesVOs.put(key, noteReminderVO);
        }
         // end notes while

        if (sequencehandler == true)
        {
            notesVOs.put("HIGHSEQNO", new Integer(highSeqNo));
            notesVOs.put("MISSINGSEQ", new Boolean(missingSequence));
        }

        return notesVOs;
    }

    public static HashMap getDeletedNoteReminderVOS(AppReqBlock appReqBlock)
    {
        HashMap deletedNotesVOs = new HashMap();
        SessionBean contractNotesDeletedSessionBean = appReqBlock.getSessionBean("contractNotesDeletedSessionBean");
        Map notesPageBeans = null;

        if (contractNotesDeletedSessionBean == null)
        {
            return deletedNotesVOs;
        }

        notesPageBeans = contractNotesDeletedSessionBean.getPageBeans();

        Iterator itN = notesPageBeans.values().iterator();

        UserSession userSession = appReqBlock.getUserSession();
        long segmentPkey = userSession.getSegmentPK();

        //System.out.println("##SegmentPkey:"+segmentPkey);
        while (itN.hasNext())
        {
            PageBean noteBean = (PageBean) itN.next();
            String noteReminderPK = noteBean.getValue("noteReminderPK");
            String segmentFK = noteBean.getValue("segmentFK");
            String noteTypeId = noteBean.getValue("noteTypeId");
            String sequence = noteBean.getValue("sequence");
            String noteQualifierId = noteBean.getValue("noteQualifierId");
            String note = noteBean.getValue("note");
            String maintDate = noteBean.getValue("maintDate");
            String operator = noteBean.getValue("operator");
            String key = noteBean.getValue("key");

            NoteReminderVO noteReminderVO = new NoteReminderVO();

            if (!noteReminderPK.equals(""))
            {
                noteReminderVO.setNoteReminderPK(Long.parseLong(noteReminderPK));
            }

            else
            {
                noteReminderVO.setNoteReminderPK(0);
            }

            if (!segmentFK.equals(""))
            {
                noteReminderVO.setSegmentFK(Long.parseLong(segmentFK));
            }
            else
            {
                if (key.startsWith("-1"))
                {
                    noteReminderVO.setSegmentFK(segmentPkey);
                }
                else
                {
                    noteReminderVO.setSegmentFK(0);
                }
            }

            noteReminderVO.setNoteTypeCT(noteTypeId);

            if (!sequence.equals(""))
            {
                noteReminderVO.setSequence(Integer.parseInt(sequence));
            }
            else
            {
                noteReminderVO.setSequence(0);
            }

            noteReminderVO.setNoteQualifierCT(noteQualifierId);
            noteReminderVO.setNote(note);
            noteReminderVO.setMaintDateTime(maintDate);
            noteReminderVO.setOperator(operator);
            noteReminderVO.setVoShouldBeDeleted(true);
            deletedNotesVOs.put(key, noteReminderVO);
        }
         // end notes while

        return deletedNotesVOs;
    }

    public static void clearNotesDeleteSessionBean(AppReqBlock appReqBlock)
    {
        SessionBean contractNotesDeletedSessionBean = appReqBlock.getSessionBean("contractNotesDeletedSessionBean");

        if (contractNotesDeletedSessionBean != null)
        {
            contractNotesDeletedSessionBean.clearState();
        }
    }

    /**
     * save notes to database
     * @param appReqBlock
     * @throws Exception
     */
    public static void saveNotesOnly(AppReqBlock appReqBlock) throws Exception
    {
        HashMap noteReminderVOs = getDeletedNoteReminderVOS(appReqBlock);
        java.util.Set noteReminderSet = noteReminderVOs.keySet();
        Iterator itr = noteReminderSet.iterator();
        NoteReminderVO noteReminderVO;
        String notesVoKey = "";
        clearNotesDeleteSessionBean(appReqBlock);

        while (itr.hasNext())
        {
            notesVoKey = (String) itr.next();
            noteReminderVO = (NoteReminderVO) noteReminderVOs.get(notesVoKey);
            noteReminderVO.setVoShouldBeDeleted(true);

            //System.out.println("DEL###noteReminderVO.getNoteReminderPK():"+noteReminderVO.getNoteReminderPK());
            if (noteReminderVO.getNoteReminderPK() != 0)
            {
                (new NoteReminder(noteReminderVO.getNoteReminderPK())).delete();
            }
        }

        noteReminderVOs = loadNotesReminderVOsFromSession(appReqBlock, true);

        int highSeqNo = 0;
        boolean isSeqMissing = false;

        if (noteReminderVOs != null)
        {
            if (noteReminderVOs.containsKey("HIGHSEQNO"))
            {
                highSeqNo = ((Integer) noteReminderVOs.get("HIGHSEQNO")).intValue();
                noteReminderVOs.remove("HIGHSEQNO");
            }

            if (noteReminderVOs.containsKey("MISSINGSEQ"))
            {
                isSeqMissing = ((Boolean) noteReminderVOs.get("MISSINGSEQ")).booleanValue();
                noteReminderVOs.remove("MISSINGSEQ");
            }
        }

        noteReminderSet = noteReminderVOs.keySet();
        itr = noteReminderSet.iterator();

        while (itr.hasNext())
        {
            notesVoKey = (String) itr.next();
            noteReminderVO = (NoteReminderVO) noteReminderVOs.get(notesVoKey);

            if (isSeqMissing && (noteReminderVO.getSequence() == 0))
            {
                noteReminderVO.setSequence(++highSeqNo);
            }

            //System.out.println("SAVE###noteReminderVO.getNoteReminderPK():"+noteReminderVO.getNoteReminderPK());
            (new NoteReminder(noteReminderVO)).save();
        }

        SessionBean contractNotesSessionBean = appReqBlock.getSessionBean("contractNotesSessionBean");
        contractNotesSessionBean.removePageBean("formBean");

        UserSession userSession = appReqBlock.getUserSession();
        long segmentPkey = userSession.getSegmentPK();
        loadNotesAndReloadSession(appReqBlock, segmentPkey);
    }

    /**
     * deletes current note from session
     * @param appReqBlock
     * @throws Exception
     */
    public static void deleteCurrentNote(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean contractNotesSessionBean = appReqBlock.getSessionBean("contractNotesSessionBean");
        PageBean formBean = appReqBlock.getFormBean();
        String key = formBean.getValue("key");

        //SessionBean contractNotesDeletedSessionBean = new SessionBean();
        SessionBean contractNotesDeletedSessionBean;

        if (appReqBlock.getSessionBean("contractNotesDeletedSessionBean") != null)
        {
            contractNotesDeletedSessionBean = appReqBlock.getSessionBean("contractNotesDeletedSessionBean");
        }
        else
        {
            contractNotesDeletedSessionBean = new SessionBean();
        }

        contractNotesDeletedSessionBean.putPageBean(key, contractNotesSessionBean.getPageBean(key));
        contractNotesSessionBean.removePageBean(key);
        contractNotesSessionBean.removePageBean("formBean");

        //      contractNotesSessionBean.putPageBean("formBean", new PageBean());
        appReqBlock.addSessionBean("contractNotesDeletedSessionBean", contractNotesDeletedSessionBean);

        SessionBean contractMainSessionBean = appReqBlock.getSessionBean("contractMainSessionBean");
        formBean = contractMainSessionBean.getPageBean("formBean");

        if (contractNotesSessionBean.hasPageBeans())
        {
            formBean.putValue("notesIndStatus", "checked");
        }
        else
        {
            formBean.putValue("notesIndStatus", "");
        }
    }

    public static void loadNotesAndReloadSession(AppReqBlock appReqBlock, long segmentFK) throws Exception
    {
        contract.business.Lookup contractLookup = new contract.component.LookupComponent();
        NoteReminderVO[] noteReminderVOs = (NoteReminderVO[]) contractLookup.findAllNotes(segmentFK);

        if (noteReminderVOs != null)
        {
            SessionBean contractNotesSessionBean = appReqBlock.getSessionBean("contractNotesSessionBean");
            contractNotesSessionBean.clearState();

            for (int n = 0; n < noteReminderVOs.length; n++)
            {
                PageBean noteBean = new PageBean();

                String noteReminderPK = noteReminderVOs[n].getNoteReminderPK() + "";
                String noteTypeId = noteReminderVOs[n].getNoteTypeCT();
                String sequence = noteReminderVOs[n].getSequence() + "";
                String noteQualifierId = noteReminderVOs[n].getNoteQualifierCT();
                String key = noteReminderPK + sequence + noteTypeId + noteQualifierId;
                String notes = noteReminderVOs[n].getNote();

                noteBean.putValue("noteReminderPK", noteReminderPK);
                noteBean.putValue("segmentFK", noteReminderVOs[n].getSegmentFK() + "");
                noteBean.putValue("noteTypeId", noteTypeId);
                noteBean.putValue("sequence", sequence);
                noteBean.putValue("noteQualifierId", noteQualifierId);
                noteBean.putValue("note", notes);
                noteBean.putValue("maintDate", noteReminderVOs[n].getMaintDateTime());
                noteBean.putValue("operator", noteReminderVOs[n].getOperator());
                noteBean.putValue("key", key);
                noteBean.putValue("isNoteReminderChanged", "false");

                contractNotesSessionBean.putPageBean(key, noteBean);
            }

            appReqBlock.addSessionBean("contractNotesSessionBean", contractNotesSessionBean);

            SessionBean contractMainSessionBean = appReqBlock.getSessionBean("contractMainSessionBean");
            PageBean formBean = contractMainSessionBean.getPageBean("formBean");

            if (contractNotesSessionBean.hasPageBeans())
            {
                formBean.putValue("notesIndStatus", "checked");
            }
            else
            {
                formBean.putValue("notesIndStatus", "");
            }
        }
    }

    public static void cancelNotes(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean contractNotes = appReqBlock.getSessionBean("contractNotes");
        appReqBlock.addSessionBean("contractNotesSessionBean", contractNotes);

        clearNotesDeleteSessionBean(appReqBlock);

        SessionBean contractNotesSessionBean = appReqBlock.getSessionBean("contractNotesSessionBean");
        contractNotesSessionBean.removePageBean("formBean");

        SessionBean contractMainSessionBean = appReqBlock.getSessionBean("contractMainSessionBean");
        PageBean formBean = contractMainSessionBean.getPageBean("formBean");

        if (contractNotesSessionBean.hasPageBeans())
        {
            formBean.putValue("notesIndStatus", "checked");
        }
        else
        {
            formBean.putValue("notesIndStatus", "");
        }
    }
}
