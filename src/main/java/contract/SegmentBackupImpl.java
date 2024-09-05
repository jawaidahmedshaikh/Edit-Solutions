/*
 * User: cgleason
 * Date: Oct 27, 2003
 * Time: 12:21:10 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;

import contract.dm.dao.DAOFactory;
import contract.dm.composer.SegmentComposer;

import edit.common.vo.*;

import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;

import fission.utility.Util;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import event.dm.composer.*;


public class SegmentBackupImpl extends CRUDEntityImpl
{
    protected void load(SegmentBackup segmentBackup, long segmentBackupPK) throws Exception
    {
        super.load(segmentBackup, segmentBackupPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void save(SegmentBackup segmentBackup) throws Exception
    {
        super.save(segmentBackup, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    protected void delete(SegmentBackup segmentBackup)
    {
        super.delete(segmentBackup, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void backupContract(long segmentFK, String segmentName, String quoteDate) throws Exception
    {
        //clear memory
        SegmentVO segmentVO = null;
        ContractSetupVO[] contractSetupVOs = null;

        List inclusionList = new ArrayList();
        inclusionList.add(InvestmentVO.class);
        inclusionList.add(BucketVO.class);
        inclusionList.add(LifeVO.class);

        //The tradtion quote process will possibly undo transaction, causing database updates
        //for the following.  They need to be backed up.
        if (segmentName.equalsIgnoreCase(Segment.SEGMENTNAMECT_TRADITIONAL))
        {
            inclusionList.add(AgentHierarchyVO.class);
            inclusionList.add(AgentSnapshotVO.class);
            inclusionList.add(SegmentVO.class);
            inclusionList.add(DepositsVO.class);
            inclusionList.add(ContractClientVO.class);
            inclusionList.add(ValueAtIssueVO.class);
        }

        SegmentComposer segmentComposer = new SegmentComposer(inclusionList);

        segmentVO = segmentComposer.compose(segmentFK);

        if (segmentName.equalsIgnoreCase(Segment.SEGMENTNAMECT_TRADITIONAL))
        {
            contractSetupVOs = getContractSetup_HistoryForBackup(segmentFK, quoteDate);
            segmentVO.setContractSetupVO(contractSetupVOs);
        }

        String segmentVOAsXML = Util.marshalVO(segmentVO);

        Pattern p = Pattern.compile("<.+?>[^<]*");

        Matcher m = p.matcher(segmentVOAsXML);

        String backupEntry = "";

        int lineNumber = 0;

        while (m.find())
        {
            String element = m.group().trim();

            String previousBackupEntry = backupEntry;

            backupEntry += element;

            if (backupEntry.length() > 1999)
            {
                saveBackupEntry(segmentFK, lineNumber, previousBackupEntry);

                backupEntry = element;

                lineNumber++;
            }

        }

        saveBackupEntry(segmentFK, lineNumber, backupEntry);
    }

    private ContractSetupVO[] getContractSetup_HistoryForBackup(long segmentFK, String quoteDate)   throws Exception
    {
        ContractSetupVO[] contractSetupVOs = null;

        List voExclusionList = new ArrayList();
        voExclusionList.add(CommissionInvestmentHistoryVO.class);
        voExclusionList.add(InvestmentAllocationOverrideVO.class);
        voExclusionList.add(OutSuspenseVO.class);
        voExclusionList.add(OverdueChargeVO.class);
        voExclusionList.add(OverdueChargeSettledVO.class);
        voExclusionList.add(OverdueChargeRemainingVO.class);
        voExclusionList.add(WithholdingHistoryVO.class);
        voExclusionList.add(ChargeHistoryVO.class);
        voExclusionList.add(BucketHistoryVO.class);
        voExclusionList.add(InvestmentHistoryVO.class);
        voExclusionList.add(BonusCommissionHistoryVO.class);
        voExclusionList.add(ContractClientAllocationOvrdVO.class);
        voExclusionList.add(WithholdingOverrideVO.class);
        voExclusionList.add(TransactionCorrespondenceVO.class);

        contractSetupVOs = event.dm.dao.DAOFactory.getContractSetupDAO().findThruHistoryBySegmentPK(segmentFK, quoteDate, true, voExclusionList);

        return contractSetupVOs;
    }


    /**
     * Stores an entry (a piece of the XML document that makes up the Segment and child elements).
     * @param segmentFK
     * @param lineNumber
     * @param previousBackupEntry
     * @throws Exception
     */
    private void saveBackupEntry(long segmentFK, int lineNumber, String previousBackupEntry)
            throws Exception
    {
        SegmentBackupVO segmentBackupVO = new SegmentBackupVO();

        segmentBackupVO.setSegmentBackupPK(0);
        segmentBackupVO.setSegmentFK(segmentFK);
        segmentBackupVO.setLineNumber(lineNumber);
        segmentBackupVO.setLineText(previousBackupEntry);

        SegmentBackup segmentBackup = new SegmentBackup();
        segmentBackup.setVO(segmentBackupVO);
        save(segmentBackup);
    }

    protected void restoreContract(long segmentFK) throws Exception
    {
        SegmentBackupVO[] segmentBackupVOs = DAOFactory.getSegmentBackupDAO().findBySegmentFK(segmentFK);

        String textLinesAsXML = "";

        if (segmentBackupVOs != null)
        {
            for (int i = 0; i < segmentBackupVOs.length; i++)
            {
                String textLine = segmentBackupVOs[i].getLineText();
                textLinesAsXML = textLinesAsXML + textLine;
            }

            SegmentVO segmentVO = (SegmentVO) Util.unmarshalVO(SegmentVO.class, textLinesAsXML);

           // There are no business rules to enforce - going to bypass any such rules by going directly to CRUD.
            CRUD crud = null;

            try
            {
                crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

                crud.createOrUpdateVOInDBRecursively(segmentVO, false);
            }
            finally
            {
                if (crud != null) crud.close();

                for (int i = 0; i < segmentBackupVOs.length; i++)
                {
                    SegmentBackup segmentBackup = new SegmentBackup(segmentBackupVOs[i].getSegmentBackupPK());
                    segmentBackup.delete();
                }
            }
        }
    }
}
