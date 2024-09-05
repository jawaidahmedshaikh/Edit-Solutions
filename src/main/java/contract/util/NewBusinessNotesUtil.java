package contract.util;

import contract.NoteReminder;
import contract.Segment;
import contract.ui.servlet.QuoteDetailTran;

import edit.common.vo.NoteReminderVO;

import edit.portal.common.session.UserSession;

import fission.beans.PageBean;
import fission.beans.SessionBean;

import fission.global.AppReqBlock;

import fission.utility.Util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/*
 * User: sramamurthy
 * Date: Aug 11, 2004
 * Time: 3:23:36 PM
 *
 * 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
public class NewBusinessNotesUtil
{
    public static HashMap loadNoteReminderVOsFromSession(AppReqBlock appReqBlock, boolean sequencehandler)
    {
        HashMap notesVOs = new HashMap();
        SessionBean quoteNotesSessionBean = appReqBlock.getSessionBean("quoteNotesSessionBean");
        quoteNotesSessionBean.removePageBean("formBean");

        Map notesPageBeans = quoteNotesSessionBean.getPageBeans();
        Iterator itN = notesPageBeans.values().iterator();
        int highSeqNo = 0;
        boolean missingSequence = false;

        UserSession userSession = appReqBlock.getUserSession();
        long segmentPkey = userSession.getSegmentPK();

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

            if (sequence.equals(""))
            {
                noteReminderVO.setSequence(0);
                missingSequence = true;
            }
            else
            {
                noteReminderVO.setSequence(Integer.parseInt(sequence));

                if (noteReminderVO.getSequence() > highSeqNo)
                {
                    highSeqNo = noteReminderVO.getSequence();
                }
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
        SessionBean quoteNotesDeletedSessionBean = appReqBlock.getSessionBean("quoteNotesDeletedSessionBean");
        Map notesPageBeans = null;

        if (quoteNotesDeletedSessionBean == null)
        {
            return deletedNotesVOs;
        }

        notesPageBeans = quoteNotesDeletedSessionBean.getPageBeans();

        Iterator itN = notesPageBeans.values().iterator();

        UserSession userSession = appReqBlock.getUserSession();
        long segmentPkey = userSession.getSegmentPK();

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
        SessionBean quoteNotesDeletedSessionBean = appReqBlock.getSessionBean("quoteNotesDeletedSessionBean");

        if (quoteNotesDeletedSessionBean != null)
        {
            quoteNotesDeletedSessionBean.clearState();
        }
    }

    public static String saveNotesOnly(AppReqBlock appReqBlock) throws Exception
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

            if (noteReminderVO.getNoteReminderPK() != 0)
            {
                (new NoteReminder(noteReminderVO.getNoteReminderPK())).delete();
            }
        }

        noteReminderVOs = loadNoteReminderVOsFromSession(appReqBlock, true);

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

            (new NoteReminder(noteReminderVO)).save();
        }

        PageBean quoteMainFormBean = appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean");
        String optionId = quoteMainFormBean.getValue("optionId");

        SessionBean quoteNotesSessionBean = appReqBlock.getSessionBean("quoteNotesSessionBean");

        quoteNotesSessionBean.removePageBean("formBean");

        UserSession userSession = appReqBlock.getUserSession();
        long segmentPkey = userSession.getSegmentPK();
        loadNotesAndReloadSession(appReqBlock, segmentPkey);

        
        if (optionId.equalsIgnoreCase("VL") || optionId.equalsIgnoreCase("UL") || optionId.equalsIgnoreCase("TL"))
        {
            return QuoteDetailTran.QUOTE_LIFE_MAIN;
        }
        else if (optionId.equalsIgnoreCase("Traditional"))
        {
            return QuoteDetailTran.QUOTE_TRAD_MAIN;
        }
        else if (Segment.OPTIONCODES_AH.contains(optionId.toUpperCase())) 
        {
        	return QuoteDetailTran.QUOTE_AH_MAIN;
        }
        else if (optionId.equalsIgnoreCase("DFA"))
        {
            return QuoteDetailTran.QUOTE_DEFERRED_ANNUITY_MAIN;
        }
        else
        {
            return QuoteDetailTran.QUOTE_COMMIT_MAIN;
        }
    }

    public static String deleteCurrentNote(AppReqBlock appReqBlock) throws Exception
    {
        SessionBean quoteNotesSessionBean = appReqBlock.getSessionBean("quoteNotesSessionBean");
        PageBean formBean = appReqBlock.getFormBean();
        String key = formBean.getValue("key");
        SessionBean quoteNotesDeletedSessionBean;

        if (appReqBlock.getSessionBean("quoteNotesDeletedSessionBean") != null)
        {
            quoteNotesDeletedSessionBean = appReqBlock.getSessionBean("quoteNotesDeletedSessionBean");
        }
        else
        {
            quoteNotesDeletedSessionBean = new SessionBean();
        }

        quoteNotesDeletedSessionBean.putPageBean(key, quoteNotesSessionBean.getPageBean(key));
        quoteNotesSessionBean.removePageBean(key);
        appReqBlock.addSessionBean("quoteNotesDeletedSessionBean", quoteNotesDeletedSessionBean);

        return QuoteDetailTran.QUOTE_COMMIT_NOTES_DIALOG;
    }

    public static void loadNotesAndReloadSession(AppReqBlock appReqBlock, long segmentFK) throws Exception
    {
        contract.business.Lookup contractLookup = new contract.component.LookupComponent();
        NoteReminderVO[] noteReminderVOs = (NoteReminderVO[]) contractLookup.findAllNotes(segmentFK);

        if (noteReminderVOs != null)
        {
            SessionBean quoteNotesSessionBean = appReqBlock.getSessionBean("quoteNotesSessionBean");
            quoteNotesSessionBean.clearState();

            /*          if (noteReminderVOs.length > 0)
                      {
                          formBean.putValue("notesIndStatus", "checked");
                      }
            */
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

                quoteNotesSessionBean.putPageBean(key, noteBean);
            }

            appReqBlock.addSessionBean("quoteNotesSessionBean", quoteNotesSessionBean);

            if (noteReminderVOs.length > 0)
            {
                appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean").putValue("notesIndStatus", "checked");
            }
            else
            {
                appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean").putValue("notesIndStatus", "unchecked");
            }
        }
        else
        {
            appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean").putValue("notesIndStatus", "unchecked");
        }
    }

    public static void setNotesIndicator(AppReqBlock appReqBlock)
    {
        SessionBean quoteNotesSessionBean = appReqBlock.getSessionBean("quoteNotesSessionBean");
        quoteNotesSessionBean.removePageBean("formBean");

        if (quoteNotesSessionBean.hasPageBeans())
        {
            appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean").putValue("notesIndStatus", "checked");
        }
        else
        {
            appReqBlock.getSessionBean("quoteMainSessionBean").getPageBean("formBean").putValue("notesIndStatus", "unchecked");
        }
    }
}
