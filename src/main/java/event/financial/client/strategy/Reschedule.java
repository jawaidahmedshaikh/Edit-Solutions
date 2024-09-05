/*
 * User: gfrosti
 * Date: Aug 14, 2003
 * Time: 9:43:39 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.financial.client.strategy;

import edit.common.*;
import edit.common.exceptions.*;
import edit.common.vo.*;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import event.EDITTrx;
import event.ScheduledEvent;
import event.dm.composer.VOComposer;
import event.dm.dao.DAOFactory;
import event.financial.client.trx.*;
import event.financial.group.strategy.SaveGroup;

import java.util.ArrayList;
import java.util.List;

import businesscalendar.BusinessCalendar;
import businesscalendar.BusinessDay;


public class Reschedule extends ClientStrategy
{
    private List scheduledEDITTrxVOs;

    public Reschedule()
    {

    }

    public Reschedule(ClientTrx clientTrx)
    {
        super(clientTrx);

        scheduledEDITTrxVOs = new ArrayList();
    }

    public ClientStrategy[] execute() throws EDITEventException
    {
        int executionMode = super.getClientTrx().getExecutionMode();

        EDITTrxVO editTrxVO = super.getClientTrx().getEDITTrxVO();

        boolean isBackdated = super.getClientTrx().isBackdated();

        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            EDITDate nextEffectiveDate = new EDITDate(editTrxVO.getEffectiveDate());
            String dueDate = editTrxVO.getDueDate();
            EDITDate nextDueDate = new EDITDate(dueDate);

            EDITDate systemDate = new EDITDate(super.getClientTrx().getCycleDate());

            ScheduledEventVO[] scheduledEventVO = DAOFactory.getScheduledEventDAO().findByEDITTrxPK(editTrxVO.getEDITTrxPK());
            EDITDate startDate = new EDITDate(scheduledEventVO[0].getStartDate());

            EDITDate stopDate = new EDITDate(scheduledEventVO[0].getStopDate());

            String frequencyCT = scheduledEventVO[0].getFrequencyCT();
            if ((frequencyCT == null || frequencyCT.equals("") || frequencyCT.equalsIgnoreCase("Please Select")) && 
            		editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_POLICYYEAREND)) {
            	frequencyCT = ScheduledEvent.FREQUENCYCT_ANNUAL;
            }

            if (executionMode == ClientTrx.REALTIME_MODE && !isBackdated)
            {
                systemDate = systemDate.subtractDays(1);
            }

            // start with the current effective date to get into the loop and calculate the next date
            //There is always one more put into the List in order to update the DB for the next schedule event after this cycle
            while ((nextEffectiveDate.before(systemDate) || nextEffectiveDate.equals(systemDate))
                    && (nextDueDate.before(stopDate))
                    && (nextDueDate.after(startDate) || nextDueDate.equals(startDate)))
            {
                nextDueDate = getNextModalDate(nextDueDate,
                                               frequencyCT,
                                               scheduledEventVO[0].getLastDayOfMonthInd(),
                                               editTrxVO.getTransactionTypeCT());

                if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("PO"))
                {
                    nextEffectiveDate = adjustNextEffectiveDate(nextDueDate, editTrxVO);
                }
                else
                {
                    nextEffectiveDate = nextDueDate;
                }

                if (startDate.getDay() > 28 && !scheduledEventVO[0].getLastDayOfMonthInd().equalsIgnoreCase("Y"))
                {
                    if (nextEffectiveDate.getDay() != startDate.getDay())
                    {
                        EDITDate endOfMonthDate = nextEffectiveDate.getEndOfMonthDate();

                        if (endOfMonthDate.getDay() > startDate.getDay() ||
                            endOfMonthDate.getDay() == startDate.getDay())
                        {
                            nextEffectiveDate = new EDITDate(nextEffectiveDate.getYear(), nextEffectiveDate.getMonth(), startDate.getDay());
                        }
                        else
                        {
                            nextEffectiveDate = new EDITDate(nextEffectiveDate.getYear(), nextEffectiveDate.getMonth(), endOfMonthDate.getDay());
                        }
                    }
                }

                // Capture all EDITTrxVOs and their EffectiveDate
                EDITTrxVO scheduledEDITTrxVO = (EDITTrxVO) editTrxVO.cloneVO();

                scheduledEDITTrxVO.setEffectiveDate(nextEffectiveDate.getFormattedDate());
                scheduledEDITTrxVO.setTaxYear(nextEffectiveDate.getYear());
                scheduledEDITTrxVO.setDueDate(nextDueDate.getFormattedDate());
                scheduledEDITTrxVO.setMaintDateTime(new EDITDateTime().getFormattedDateTime());

                scheduledEDITTrxVOs.add(scheduledEDITTrxVO);
            }

            // Set indicators and Save.
            for (int i = 0; i < scheduledEDITTrxVOs.size(); i++)
            {
                EDITTrxVO scheduledEDITTrxVO = (EDITTrxVO) scheduledEDITTrxVOs.get(i);

                long origEditTrxPK = scheduledEDITTrxVO.getEDITTrxPK();

                scheduledEDITTrxVO.setEDITTrxPK(0);

                //Check for final prices for MF transaction to set pending status
                if (scheduledEDITTrxVO.getTransactionTypeCT().equalsIgnoreCase("MF"))
                {
                    boolean allForwardPricesFound = checkForFinalPrices(origEditTrxPK, scheduledEDITTrxVO.getEffectiveDate());
                    if (allForwardPricesFound)
                    {
                        scheduledEDITTrxVO.setPendingStatus("P");
                    }
                    else
                    {
                        scheduledEDITTrxVO.setPendingStatus("M");
                    }
                }

                if (i < scheduledEDITTrxVOs.size() - 1)
                {
                    scheduledEDITTrxVO.setTrxIsRescheduledInd("Y");
                }
                else
                {
                    scheduledEDITTrxVO.setTrxIsRescheduledInd("N");
                    scheduledEDITTrxVOs.remove(i);
                }

                crud.createOrUpdateVOInDB(scheduledEDITTrxVO);

                ClientTrxImpl clientTrxImpl = new ClientTrxImpl();
                if (clientTrxImpl.shouldSetupCorrespondence(scheduledEDITTrxVO))
                {
                    clientTrxImpl.setupCorrespondence(scheduledEDITTrxVO);
                }
            }
            //update the natural that caused reschedule to happen
            editTrxVO.setTrxIsRescheduledInd("Y");

            crud.createOrUpdateVOInDB(editTrxVO);

        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new EDITEventException(e.getMessage());
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }

        return null;
    }


    /**
     * Checks for the final prices for all investments defined on the segment specified by the given
     * transaction
     * @param editTrxPK
     * @param effectiveDate
     * @return
     * @throws Exception
     */
    private boolean checkForFinalPrices(long editTrxPK, String effectiveDate) throws Exception
    {
        event.business.Event eventComponent = new event.component.EventComponent();

        List voInclusionList = new ArrayList();
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(SegmentVO.class);
        voInclusionList.add(InvestmentVO.class);

        EDITTrxVO fluffyEditTrx = eventComponent.composeEDITTrxVOByEDITTrxPK(editTrxPK, voInclusionList);

        ClientSetupVO clientSetupVO = (ClientSetupVO) fluffyEditTrx.getParentVO(ClientSetupVO.class);

        ContractSetupVO contractSetupVO = (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);

        SegmentVO segmentVO = (SegmentVO) contractSetupVO.getParentVO(SegmentVO.class);

        InvestmentVO[] investmentVOs = segmentVO.getInvestmentVO();

        return eventComponent.checkForForwardPrices(investmentVOs, effectiveDate);
    }

    private EDITDate adjustNextEffectiveDate(EDITDate nextEffectiveDate, EDITTrxVO editTrxVO) throws Exception
    {
        List voInclusionList = new ArrayList();
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(ClientSetupVO.class);

        GroupSetupVO groupSetupVO = new VOComposer().composeGroupSetupVOByEDITTrxPK(editTrxVO.getEDITTrxPK(), voInclusionList);

        ContractSetupVO[] contractSetupVO = groupSetupVO.getContractSetupVO();
        long contractClientFK = (contractSetupVO[0].getClientSetupVO()[0]).getContractClientFK();

        //params into save group: groupSetupVO, trxEffectiveDate, taxYear, trxType, processName, optionCOde, CompanyStructureFK
        EDITTrxVO dummyEditTrxVO = new EDITTrxVO();
        dummyEditTrxVO.setTransactionTypeCT("PO");

        SaveGroup savegroup = new SaveGroup(groupSetupVO, dummyEditTrxVO, "Payout", null);

        return savegroup.calcScheduledEventDates(contractClientFK, nextEffectiveDate);
    }

    public ClientTrx[] getRescheduledClientTrx()
    {
        List rescheduledClientTrx = new ArrayList();

        int editTrxVOSize = scheduledEDITTrxVOs.size();

        for (int i = 0; i < editTrxVOSize; i++)
        {
            ClientTrx currentClientTrx = new ClientTrx((EDITTrxVO) scheduledEDITTrxVOs.get(i));

            currentClientTrx.setCycleDate(super.getClientTrx().getCycleDate());
            currentClientTrx.setExecutionMode(super.getClientTrx().getExecutionMode());

            rescheduledClientTrx.add(currentClientTrx);
        }

        return (ClientTrx[]) rescheduledClientTrx.toArray(new ClientTrx[rescheduledClientTrx.size()]);
    }

     public EDITDate getNextModalDate(EDITDate currentDueDate, String frequencyCT, String lastDayOfMonthInd, String transactionType)
    {
        EDITDate nextEffectiveDate = currentDueDate.addMode(frequencyCT);

        if (lastDayOfMonthInd.equalsIgnoreCase("Y"))
        {
            nextEffectiveDate = nextEffectiveDate.getEndOfMonthDate();

            if (transactionType.equalsIgnoreCase("MF"))
            {
                BusinessCalendar businessCalendar = new BusinessCalendar();
                BusinessDay businessDay = businessCalendar.getBestBusinessDay(nextEffectiveDate);
                nextEffectiveDate = businessDay.getBusinessDate();
            }
        }

        return nextEffectiveDate;
    }
}
