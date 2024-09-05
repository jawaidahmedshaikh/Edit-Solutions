/*
 * User: gfrosti
 * Date: Jul 30, 2003
 * Time: 2:09:32 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.financial.client.strategy;

import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.vo.*;
import edit.common.exceptions.*;
import edit.services.db.*;
import event.financial.client.trx.ClientTrx;
import event.*;
import fission.utility.*;


public class Undo extends Reversal
{
    private static final String REVERSAL_REASON_BACKDATED_FULLREMOVAL = "BackdatedFullRemoval";
    private static final String REVERSAL_REASON_CLAIM_PAYOUT = "ClaimPayout";


    public Undo(ClientTrx clientTrx)
    {
        super(clientTrx);
    }

    public Undo(ClientTrx clientTrx, boolean quoteInProcess)
    {
        super(clientTrx, quoteInProcess);
    }

     public Undo(ClientTrx clientTrx, String sortStatus)
    {
        super(clientTrx);
        super.setSortStatus(sortStatus);
    }


    protected void updateEDITTrxVO(CRUD crud) throws Exception
    {
       // editTrxVO.setStatus("U");
        super.getClientTrx().getEDITTrxVO().setStatus("U");
        super.getClientTrx().getEDITTrxVO().setPendingStatus("H");
        super.getClientTrx().getEDITTrxVO().setMaintDateTime(new EDITDateTime().getFormattedDateTime());
        super.getClientTrx().getEDITTrxVO().setAccountingPeriod(DateTimeUtil.buildAccountingPeriod(new EDITDate(getClientTrx().getCycleDate())));
        String drivingBackdatedTrxCode = super.getClientTrx().getDrivingBackdatedTrxCode();

        if (drivingBackdatedTrxCode != null)
        {
            if (drivingBackdatedTrxCode.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_DEATH) ||
                drivingBackdatedTrxCode.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_NOTTAKEN) ||
                drivingBackdatedTrxCode.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_FULLSURRENDER))
            {
                super.getClientTrx().getEDITTrxVO().setReversalReasonCodeCT(REVERSAL_REASON_BACKDATED_FULLREMOVAL);
            }
            else if (drivingBackdatedTrxCode.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_RIDER_CLAIM))
            {
                super.getClientTrx().getEDITTrxVO().setReversalReasonCodeCT(REVERSAL_REASON_CLAIM_PAYOUT);
            }
        }
        super.getClientTrx().save();
       // crud.createOrUpdateVOInDB(editTrxVO);

        ClientTrx clientTrx = super.getClientTrx();
        EDITTrx editTrx = new EDITTrx(clientTrx.getEDITTrxVO());
        if (editTrx.isRemovalTransaction())
        {
            SuspenseVO[] suspenseVOs = event.dm.dao.DAOFactory.getSuspenseDAO().findBy_EDITTrxFK(clientTrx.getEDITTrxVO().getEDITTrxPK(), crud);
            if (suspenseVOs != null)
            {
                SuspenseVO suspenseVO = suspenseVOs[0];
                suspenseVO.setMaintenanceInd(Suspense.MAINTENANCE_IND_U);
                crud.createOrUpdateVOInDB(suspenseVO);
            }
        }
    }


}
