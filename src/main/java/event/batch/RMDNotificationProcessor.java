/*
 * User: dlataill
 * Date: Dec 22, 2004
 * Time: 9:49:54 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package event.batch;

import contract.Segment;
import contract.dm.composer.SegmentComposer;

import edit.common.*;

import edit.common.vo.ProductStructureVO;
import edit.common.vo.RequiredMinDistributionVO;
import edit.common.vo.SegmentVO;
import edit.services.logging.Logging;
import edit.services.*;
import edit.services.db.*;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import logging.*;
import org.apache.logging.log4j.Logger;
import batch.business.*;


public class RMDNotificationProcessor implements Serializable
{

    public void generateRmdNotifications(String productStructure, String startRmdAnnualDate, String endRmdAnnualDate)
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_GENERATE_RMD_NOTIFICATIONS).tagBatchStart(Batch.BATCH_JOB_GENERATE_RMD_NOTIFICATIONS, "RMD Notification");

        Segment segment = null;

        try
        {
            EDITDate edStartDate = new EDITDate(startRmdAnnualDate);
            EDITDate edEndDate = new EDITDate(endRmdAnnualDate);

            long[] productStructurePKs = null;

            if (productStructure.equalsIgnoreCase("All"))
            {
                engine.business.Lookup engineLookup = new engine.component.LookupComponent();
                ProductStructureVO[] productStructureVOs = engineLookup.findAllProductTypeStructureVOs(false, null);
                productStructurePKs = new long[productStructureVOs.length];

                for (int i = 0; i < productStructureVOs.length; i++)
                {
                    productStructurePKs[i] = productStructureVOs[i].getProductStructurePK();
                }
            }
            else
            {
                productStructurePKs = new long[]{Long.parseLong(productStructure)};
            }


            contract.business.Lookup contractLookup = new contract.component.LookupComponent();

            // Only get the list of PKs instead of all segments in memory.
            // RMD batch was running out of memory.
            // To further speed this up, only get segments PKs that have
            // a RequiredMinDistritribution row corresponding to it.
            List segmentPKsList =
                    new event.dm.dao.FastDAO().
                    findAllSegmentPKsWithRequiredMinDistribution(productStructurePKs);

            List voInclusionList = new ArrayList();
            voInclusionList.add(RequiredMinDistributionVO.class);

            for (int i = 0; i < segmentPKsList.size(); i++)
            {
                Long segmentPKLong = (Long) segmentPKsList.get(i);
                long currentSegmentPK = segmentPKLong.longValue();

                segment = new Segment(currentSegmentPK);
                SegmentVO theSegmentVO = (SegmentVO) segment.getVO();

                SegmentComposer composer = new SegmentComposer(voInclusionList);
                composer.compose(theSegmentVO);

                if (theSegmentVO.getRequiredMinDistributionVOCount() > 0)
                {
                    RequiredMinDistributionVO rmdVO = theSegmentVO.getRequiredMinDistributionVO(0);

                    if ((rmdVO.getElectionCT() != null) && !rmdVO.getElectionCT().equalsIgnoreCase("Opt1"))
                    {
                        EDITDate edAnnualDate = new EDITDate(rmdVO.getAnnualDate());

                        if ( (edAnnualDate.after(edStartDate) || edAnnualDate.equals(edStartDate)) &&
                             (edAnnualDate.before(edEndDate) || edAnnualDate.equals(edEndDate)) )
                        {
                            boolean rcTrxExists = checkForPendingRCTrx(currentSegmentPK);

                            if (!rcTrxExists)
                            {
                                Segment newSegment = new Segment(theSegmentVO);
                                newSegment.createRmdNotificationTransaction();

                    			EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_GENERATE_RMD_NOTIFICATIONS).updateSuccess();

                            }
                        }
                    }
                }
            }

        }
        catch (Exception e)
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_GENERATE_RMD_NOTIFICATIONS).updateFailure();

          System.out.println(e);

            e.printStackTrace();

            LogEvent logEvent = new LogEvent("RMD Notification Errored", e);

            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

            logger.error(logEvent);

            //  Log error to database
            EDITMap columnInfo = new EDITMap ("ProcessDate", new EDITDate().getFormattedDate());
            columnInfo.put("ContractNumber", segment.getContractNumber());

            Log.logToDatabase(Log.RMD_NOTIFICATIONS, "RMD Notification Errored" + e.getMessage(), columnInfo);
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_GENERATE_RMD_NOTIFICATIONS).tagBatchStop();
        }
    }

    private boolean checkForPendingRCTrx(long segmentPK) throws Exception
    {
        boolean rcTrxExists = false;

        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            long[] editTrxPKs = new event.dm.dao.FastDAO().findBySegmentPK_TrxType_PendingStatus(segmentPK, "RC", "P", crud);


            if (editTrxPKs != null && editTrxPKs.length > 0)
            {
                rcTrxExists = true;
            }
        }
        finally
        {
            if (crud != null)
            {
                crud.close();

                crud = null;
            }
        }

        return rcTrxExists;
    }
}
