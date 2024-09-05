package engine.sp.custom.document;

import contract.Segment;

import edit.common.*;
import edit.common.vo.*;
import edit.services.config.ServicesConfig;
import edit.services.db.hibernate.*;

import event.Charge;
import event.ChargeHistory;
import event.ClientSetup;
import event.CommissionHistory;
import event.ContractClientAllocationOvrd;
import event.ContractSetup;
import event.EDITTrx;
import event.EDITTrxHistory;
import event.FinancialHistory;
import event.GroupSetup;
import event.InSuspense;
import event.InvestmentAllocationOverride;
import event.OutSuspense;
import event.ScheduledEvent;
import event.SegmentHistory;
import event.WithholdingHistory;
import event.WithholdingOverride;

import fission.utility.DOMUtil;
import fission.utility.Util;
import fission.utility.XMLUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import reinsurance.ReinsuranceHistory;

/**
 * GroupSetup is driven by the contained EDITTrx. The basic structure
 * is that of:
 * GroupSetup.
 * GroupSetup.ContractSetup
 * GroupSetup.ContractSetup.ClientSetup
 * GroupSetup.ContractSetup.ClientSetup.EDITTrx.
 * 
 * Additionally (and optionally)
 * 
 * GroupSetup.Charges
 * GroupSetup.ScheduledEvent
 * GroupSetup.ContractSetup.InvestmentAllocationOverride
 * 
 * The following rules apply:
 * 
 * 1. There are no conditional building rules known at this time.
 */
public class GroupSetupDocument extends PRASEDocBuilder
{

    /**
     * The PK from which this Document is built and based upon.
     */
    protected Long editTrxPK;
    private String editTrxHistoryIndforREDO;
    private Long oldReapplyEditTrxPK;
    /**
     * The core entity upon which this document structure is based.
     */
    protected GroupSetup groupSetup;
    public static final String BUILDING_PARAMETER_NAME_EDITTRXPK = "EDITTrxPK";
    public static final String BUILDING_PARAMETER_NAME_INCLUDEHISTORYIND = "IncludeHistoryInd";
    public static final String PARAMETER_NAME_OLDREAPPLYEDITTRPK = "oldReapplyEditTrxPK";
    /**
     * The parameters that will be extracted from working storage to build this document.
     */
    private static final String[] buildingParameterNames =
    {
        BUILDING_PARAMETER_NAME_EDITTRXPK
    };

    public GroupSetupDocument()
    {
    }

    public GroupSetupDocument(Long editTrxPK)
    {
        super(new EDITMap(BUILDING_PARAMETER_NAME_EDITTRXPK, editTrxPK.toString()));

        this.editTrxPK = editTrxPK;
    }

    /**
     * Constructor. The TrxEffectiveDate is expected to be in the
     * specified building params keyed by the BUILDING_PARAM.
     * @see #BUILDING_PARAMETER_NAME_EDITTRXPK
     * @param buildingParams
     */
    public GroupSetupDocument(Map<String, String> buildingParams)
    {
        super(buildingParams);

        this.editTrxPK = new Long(buildingParams.get(BUILDING_PARAMETER_NAME_EDITTRXPK));

        if (buildingParams.get(BUILDING_PARAMETER_NAME_INCLUDEHISTORYIND) != null && buildingParams.get(BUILDING_PARAMETER_NAME_INCLUDEHISTORYIND) != "")
        {
            this.editTrxHistoryIndforREDO = buildingParams.get(BUILDING_PARAMETER_NAME_INCLUDEHISTORYIND).toString();
            this.oldReapplyEditTrxPK = new Long(buildingParams.get(PARAMETER_NAME_OLDREAPPLYEDITTRPK));
        }
    }

    /**
     * Builds a composite of the GroupSetup from the specified EDITTrxPK
     * according to the following rules:
     * 1. GroupSetup.ContractSetup.ContractSetup.EDITTrx is assumed to be always present.
     * 2. GroupSetup.ScheduledEvents is built IFF exists.
     * 3. GroupSetup.Charges is built IFF exists.
     * 4. ContractSetup.InvestmentAllocationOverrides is built IFF exists.
     * 5. ContractSetup.Segment (only as a convenience to NaturalDoc which requires this entity).
     * @return
     */
    public void build()
    {
        if (!isDocumentBuilt())
        {
            // The data source for the document.
            if (editTrxHistoryIndforREDO != null && editTrxHistoryIndforREDO.equalsIgnoreCase("Y"))
            {
                groupSetup = GroupSetup.findSeparateBy_EDITTrxPK_V3_includeHistoryForReDo(this.oldReapplyEditTrxPK);
            }
            else
            {
                groupSetup = GroupSetup.findSeparateBy_EDITTrxPK_V2(getEditTrxPK());
            }

            Element groupSetupDocument = buildGroupSetupDocument();

            buildGroupSetupElement(groupSetup, groupSetupDocument);

            setRootElement(groupSetupDocument);

            setDocumentBuilt(true);
        }
    }

    /**
     * The EDITTrxPK that is the driving element of this Document.
     * @return
     */
    public Long getEditTrxPK()
    {
        return editTrxPK;
    }

    /**
     * Begins the building of the root of this document. The building of
     * Charges, ScheduledEvents, and ConractSetups (children of GroupSetup)
     * is delegated to their corresponding builder methods.
     * @param groupSetup the datasource for the Document
     * @return the containing Element
     */
    private void buildGroupSetupElement(GroupSetup groupSetup, Element groupSetupDocumentElement)
    {
        Element groupSetupElement = groupSetup.getAsElement();

        buildChargeElement(groupSetup.getCharges(), groupSetupElement);

        buildScheduledEventElement(groupSetup.getScheduledEvents(), groupSetupElement);

        buildContractSetupElement((ContractSetup) groupSetup.getContractSetups().iterator().next(), groupSetupElement);

        groupSetupDocumentElement.add(groupSetupElement);
    }

    /**
     * Builds the related Charge Elements as specified by the charges. Each Charge
     * Element is added as a child Element to the specified GroupSetup Element.
     * @param charges
     * @param groupSetupElement
     */
    private void buildChargeElement(Set<Charge> charges, Element groupSetupElement)
    {
        EDITDate effectiveDate = getEDITTrxEffectiveDate();

        for (Charge charge : charges)
        {
            EDITDate oneTimeOnlyDate = charge.getOneTimeOnlyDate();

            if (oneTimeOnlyDate == null || oneTimeOnlyDate.afterOREqual(effectiveDate))
            {
                Element chargeElement = charge.getAsElement();

                groupSetupElement.add(chargeElement);
            }
        }
    }

    /**
     * Builds the related ScheduledEvent Elements as specified by the ScheduledEvents. Each ScheduledEvent
     * Element is added as a child Element to the specified GroupSetup Element.
     * @param scheduledEvents
     * @param groupSetupElement
     */
    private void buildScheduledEventElement(Set<ScheduledEvent> scheduledEvents, Element groupSetupElement)
    {
        for (ScheduledEvent scheduledEvent : scheduledEvents)
        {
            Element scheduledEventElement = scheduledEvent.getAsElement();

            groupSetupElement.add(scheduledEventElement);
        }
    }

    /**
     * Builds the related ContractSetup Element as specified by the ContractSetup. The ContractSetup
     * Element is added as a child Element to the specified GroupSetup Element. The process continues
     * as building is then passed to InvestmentAllocationOverrides and ClientSetups which are child
     * entities to ContractsSetup.
     * @param contractSetup
     * @param groupSetupElement
     */
    private void buildContractSetupElement(ContractSetup contractSetup, Element groupSetupElement)
    {
        Element contractSetupElement = contractSetup.getAsElement();

        Segment segment = contractSetup.getSegment();

        Long segmentPK = segment.getSegmentPK();

        // Hibernate is not setting FK values while building the document using HQL query,
        // need to set the SegmentFK value explicitly.
        setSegmentFKForContractSetup(contractSetupElement, segmentPK);

        groupSetupElement.add(contractSetupElement);

        //    buildInvestmentAllocationOverrideElement(contractSetup.getInvestmentAllocationOverrides(), contractSetupElement);

        buildClientSetupElement((ClientSetup) contractSetup.getClientSetups().iterator().next(), contractSetupElement, segmentPK);

        buildOutSuspenseElement(contractSetup.getOutSuspenses(), contractSetupElement);
    }

    /**
     * Builds the OutSuspenses (if any) an attaches them as children to the specified ContractSetup Element.
     * @param outSuspenses
     * @param contractSetupElement
     */
    private void buildOutSuspenseElement(Set<OutSuspense> outSuspenses, Element contractSetupElement)
    {
        for (OutSuspense outSuspense : outSuspenses)
        {
            Element outSuspenseElement = outSuspense.getAsElement();

            contractSetupElement.add(outSuspenseElement);
        }
    }

    /**
     * Builds the related InvestmentAllocationOverride Elements as specified by the InvestmentAllocationOverrides.
     * Each InvestmentAllocationOverride is added as a child Element to the specified ContractSetup Element.
     * @param investmentAllocationOverrides
     * @param contractSetupElement
     */
    private void buildInvestmentAllocationOverrideElement(Set<InvestmentAllocationOverride> investmentAllocationOverrides, Element contractSetupElement)
    {
        // The InvestmentAllocationOverride elements should be sorted based on "ToFromStatus" because
        // The Overrides with 'F' status should be processed first.
        InvestmentAllocationOverride[] investmentAllocationOverrideArray = investmentAllocationOverrides.toArray(new InvestmentAllocationOverride[investmentAllocationOverrides.size()]);

        investmentAllocationOverrideArray = (InvestmentAllocationOverride[]) Util.sortObjects(investmentAllocationOverrideArray, new String[]
                {
                    "getToFromStatus"
                });

        for (InvestmentAllocationOverride investmentAllocationOverride : investmentAllocationOverrideArray)
        {
            Element investmentAllocationOverrideElement = investmentAllocationOverride.getAsElement();

            contractSetupElement.add(investmentAllocationOverrideElement);
        }
    }

    /**
     * Builds the related ClientSetup Element as specified by the ClientSetup.
     * Each ClientSetup Element is added as a child Element to the specified ContractSetup Element.
     * The processing continues with EDITTrx which is a child entity of ClientSetup.
     * @param clientSetup
     * @param contractSetupElement
     */
    private void buildClientSetupElement(ClientSetup clientSetup, Element contractSetupElement, Long segmentPK)
    {
        Element clientSetupElement = clientSetup.getAsElement();

        contractSetupElement.add(clientSetupElement);

        buildEDITTrxElement((EDITTrx) clientSetup.getEDITTrxs().iterator().next(), clientSetupElement, segmentPK);

        buildWithholdingOverridesElement(clientSetup.getWithholdingOverrides(), clientSetupElement);

        buildContractClientAllocationOvrdsElement(clientSetup.getContractClientAllocationOvrds(), clientSetupElement);
    }

    private void buildContractClientAllocationOvrdsElement(Set<ContractClientAllocationOvrd> contractClientAllocationOvrds, Element clientSetupElement)
    {
        for (ContractClientAllocationOvrd contractClientAllocationOvrd : contractClientAllocationOvrds)
        {
            Element contractClientAllocationOvrdElement = contractClientAllocationOvrd.getAsElement();

            clientSetupElement.add(contractClientAllocationOvrdElement);
        }
    }

    private void buildWithholdingOverridesElement(Set<WithholdingOverride> withholdingOverrides, Element clientSetupElement)
    {
        for (WithholdingOverride withholdingOverride : withholdingOverrides)
        {
            Element WithholdingOverrideElement = withholdingOverride.getAsElement();

            clientSetupElement.add(WithholdingOverrideElement);
        }
    }

    /**
     * Builds the related EDITTrx Element as specified by the EDITTrx.
     * Each EDITTrx Element is added as a child Element to the specified ClientSetup Element.
     * @param editTrx
     * @param clientSetupElement
     */
    private void buildEDITTrxElement(EDITTrx editTrx, Element clientSetupElement, Long segmentPK)
    {


        if (editTrxHistoryIndforREDO != null
                && editTrxHistoryIndforREDO.equalsIgnoreCase("Y")) //build EditTrxHistory elements for REDO process only
        {
            //get the redo trx
            EDITTrx newEDITTrx = EDITTrx.findBy_PK(this.editTrxPK);

            if (!editTrx.getPendingStatus().equalsIgnoreCase("F") && !editTrx.getPendingStatus().equalsIgnoreCase("B"))
            {
                Element editTrxElement = newEDITTrx.getAsElement();

                clientSetupElement.add(editTrxElement);

                //All history from the undo transaction
                buildEDITTrxHistoryElement((EDITTrxHistory) editTrx.getEDITTrxHistories().iterator().next(), editTrxElement, newEDITTrx, segmentPK);
            }
        }
        else
        {
            Element editTrxElement = editTrx.getAsElement();

            clientSetupElement.add(editTrxElement);
        }
    }

    private void buildEDITTrxHistoryElement(EDITTrxHistory editTrxHistory, Element editTrxElement, EDITTrx redoEDITTrx, Long segmentPK)
    {
        EDITTrxHistory newEditTrxHistory = (EDITTrxHistory) SessionHelper.shallowCopy(editTrxHistory, SessionHelper.EDITSOLUTIONS, false);
        newEditTrxHistory.setEDITTrx(redoEDITTrx);

        Element editTrxHistoryElement = newEditTrxHistory.getAsElement();

        buildFinancialHistoryElement(editTrxHistory.getFinancialHistories(), newEditTrxHistory, editTrxHistoryElement);
        buildSegmentHistoryElement(editTrxHistory.getSegmentHistories(), newEditTrxHistory, editTrxHistoryElement, segmentPK);
        buildReinsuranceHistoryElement(editTrxHistory.getReinsuranceHistories(), newEditTrxHistory, editTrxHistoryElement);
        buildChargeHistoryElement(editTrxHistory.getChargeHistories(), newEditTrxHistory, editTrxHistoryElement);
        buildCommissionHistoryElement(editTrxHistory.getCommissionHistories(), newEditTrxHistory, editTrxHistoryElement, redoEDITTrx.getTransactionTypeCT());
        buildInSuspenseElement(editTrxHistory.getInSuspenses(), newEditTrxHistory, editTrxHistoryElement);
        buildWithholdingHistoryElement(editTrxHistory.getWithholdingHistories(), newEditTrxHistory, editTrxHistoryElement);

        editTrxElement.add(editTrxHistoryElement);
    }

    private void buildFinancialHistoryElement(Set<FinancialHistory> financialHistorys, EDITTrxHistory newEditTrxHistory, Element editTrxHistoryElement)
    {
        for (FinancialHistory financialHistory : financialHistorys)
        {
            FinancialHistory newFinancialHistory = (FinancialHistory) SessionHelper.shallowCopy(financialHistory, SessionHelper.EDITSOLUTIONS, false);
//            newEditTrxHistory.addFinancialHistory(newFinancialHistory);

            Element financialHistoryElement = SessionHelper.mapToElementWithoutKeyLogic(newFinancialHistory, SessionHelper.EDITSOLUTIONS, false, false);
            ;

            editTrxHistoryElement.add(financialHistoryElement);
        }
    }

    private void buildSegmentHistoryElement(Set<SegmentHistory> segmentHistorys, EDITTrxHistory newEditTrxHistory, Element editTrxHistoryElement, Long segmentPK)
    {
        for (SegmentHistory segmentHistory : segmentHistorys)
        {
            SegmentHistory newSegmentHistory = (SegmentHistory) SessionHelper.shallowCopy(segmentHistory, SessionHelper.EDITSOLUTIONS, false);
            newSegmentHistory.setSegmentFK(segmentPK);
            newSegmentHistory.setSegmentHistoryPK(new Long(0));
            newSegmentHistory.setEDITTrxHistoryFK(new Long(0));

            //mapToElementWithoutKeyLogic removes too many FK keys
            Element segmentHistoryElement = newSegmentHistory.getAsElement();

            editTrxHistoryElement.add(segmentHistoryElement);
        }
    }

    private void buildReinsuranceHistoryElement(Set<ReinsuranceHistory> reinsuranceHistorys, EDITTrxHistory newEditTrxHistory, Element groupSetupElement)
    {
        for (ReinsuranceHistory reinsuranceHistory : reinsuranceHistorys)
        {
            if (reinsuranceHistory.getUndoRedoStatus() == null || !reinsuranceHistory.getUndoRedoStatus().equalsIgnoreCase("undo"))
            {
                ReinsuranceHistory newReinsuranceHistory = (ReinsuranceHistory) SessionHelper.shallowCopy(reinsuranceHistory, SessionHelper.EDITSOLUTIONS, false);
                newReinsuranceHistory.setReinsuranceHistoryPK(0L);
                newReinsuranceHistory.setEDITTrxHistoryFK(0L);
                newReinsuranceHistory.setUpdateStatus("U");
                newReinsuranceHistory.setAccountingPendingStatus("Y");
                newReinsuranceHistory.setUndoRedoStatus("redo");

                Element reinsuranceHistoryElement = newReinsuranceHistory.getAsElement();

                groupSetupElement.add(reinsuranceHistoryElement);
            }
        }
    }

    private void buildChargeHistoryElement(Set<ChargeHistory> chargeHistorys, EDITTrxHistory newEditTrxHistory, Element editTrxHistoryElement)
    {
        for (ChargeHistory chargeHistory : chargeHistorys)
        {
            ChargeHistory newChargeHistory = (ChargeHistory) SessionHelper.shallowCopy(chargeHistory, SessionHelper.EDITSOLUTIONS, false);
//            newEditTrxHistory.addChargeHistory(newChargeHistory);

            Element chargeHistoryElement = SessionHelper.mapToElementWithoutKeyLogic(newChargeHistory, SessionHelper.EDITSOLUTIONS, false, false);
            editTrxHistoryElement.add(chargeHistoryElement);
        }
    }

    private void buildCommissionHistoryElement(Set<CommissionHistory> commissionHistorys, EDITTrxHistory newEditTrxHistory, Element groupSetupElement, String trxType)
    {
        for (CommissionHistory commissionHistory : commissionHistorys)
        {
            if (commissionHistory.getUndoRedoStatus() == null || !commissionHistory.getUndoRedoStatus().equalsIgnoreCase("undo"))
            {
                CommissionHistory newCommissionHistory = (CommissionHistory) SessionHelper.shallowCopy(commissionHistory, SessionHelper.EDITSOLUTIONS, false);

                //Initialize the following for the redo trx
                initializeCopiedCommissionHistory(newCommissionHistory, trxType, commissionHistory);

                //mapToElementWithoutKeyLogic removes too many FK keys
                Element commissionHistoryElement = newCommissionHistory.getAsElement();

                groupSetupElement.add(commissionHistoryElement);
            }
        }
    }

    private void buildInSuspenseElement(Set<InSuspense> inSuspenses, EDITTrxHistory newEditTrxHistory, Element editTrxHistoryElement)
    {
        for (InSuspense inSuspense : inSuspenses)
        {
            InSuspense newInSuspense = (InSuspense) SessionHelper.shallowCopy(inSuspense, SessionHelper.EDITSOLUTIONS, false);
            newInSuspense.setInSuspensePK(new Long(0));
            newInSuspense.setEDITTrxHistoryFK(new Long(0));

            //mapToElementWithoutKeyLogic removes too many FK keys
            Element inSuspenseElement = newInSuspense.getAsElement();

            editTrxHistoryElement.add(inSuspenseElement);
        }
    }

    private void buildWithholdingHistoryElement(Set<WithholdingHistory> withholdingHistorys, EDITTrxHistory newEditTrxHistory,
            Element editTrxHistoryElement)
    {
        for (WithholdingHistory withholdingHistory : withholdingHistorys)
        {
            WithholdingHistory newWithholdingHistory = (WithholdingHistory) SessionHelper.shallowCopy(withholdingHistory, SessionHelper.EDITSOLUTIONS, false);
//            newEditTrxHistory.addWithholdingHistory(newWithholdingHistory);

            Element withholdingHistoryElement = SessionHelper.mapToElementWithoutKeyLogic(newWithholdingHistory, SessionHelper.EDITSOLUTIONS, false, false);
            editTrxHistoryElement.add(withholdingHistoryElement);
        }
    }

    private void initializeCopiedCommissionHistory(CommissionHistory newCommissionHistory, String trxType, CommissionHistory commissionHistory)
    {
        newCommissionHistory.setCommissionHistoryPK(new Long(0));
        newCommissionHistory.setEDITTrxHistoryFK(new Long(0));
        newCommissionHistory.setPlacedAgentFK(commissionHistory.getPlacedAgentFK());
        newCommissionHistory.setSourcePlacedAgentFK(commissionHistory.getSourcePlacedAgentFK());
        newCommissionHistory.setAgentSnapshotFK(commissionHistory.getAgentSnapshotFK());

        newCommissionHistory.setUpdateStatus(CommissionHistory.UPDATESTATUS_U);

        newCommissionHistory.setUpdateDateTime(null);

        newCommissionHistory.setStatementInd("Y");

        newCommissionHistory.setUndoRedoStatus("redo");

        if (trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LUMPSUM))
        {
            newCommissionHistory.setAccountingPendingStatus("N");
            newCommissionHistory.setMaintDateTime(new EDITDateTime());
        }
        else
        {
            newCommissionHistory.setAccountingPendingStatus("Y");
        }
    }

    /**
     * Builds the root element of this GroupSetup Document.
     * @return
     */
    private Element buildGroupSetupDocument()
    {
        Element groupSetupDocumentElement = new DefaultElement(getRootElementName());

        return groupSetupDocumentElement;
    }

    /**
     * Avaible [after] calling the build() method.
     *
     * @see #groupSetup
     * @return
     */
    public GroupSetup getGroupSetup()
    {
        return groupSetup;
    }

    /**
     *
     * @return
     */
    public String getRootElementName()
    {
        return "GroupSetupDocVO";
    }

    /**
     * Legacy code will want to work with this document as a VO in some circumstances.
     * It is assumed that the document has been built, and that is not just a matter of
     * manually constructing the VO model. Child VO entities will have a reference to their
     * parent VO entities via the VOObject.get/setParentVO() method.
     *
     * Since this VO model is undesirable, it will only contain (known) required VOs.
     *
     * To date, this is GroupSetupVO, ContractSetupVO, ClientSetupVO, EDITTrxVO.
     * Also includes EDITTrxHistoryVO,ReinsuranceVO,ChargeVO,CommissionVo,FinancialVo,SegmentVO,Insuspense &WithholdingHistoryVO
     * @see #build()
     * @return
     */
    public GroupSetupVO getAsGroupSetupVODocument(boolean includeHistories)
    {
        GroupSetupVO groupSetupVO = getGroupSetup().getAsVO();

        for (Charge charge : getGroupSetup().getCharges())
        {
            ChargeVO chargeVO = (ChargeVO) SessionHelper.map(charge, SessionHelper.EDITSOLUTIONS);
            groupSetupVO.addChargeVO(chargeVO);
        }

        for (ScheduledEvent scheduledEvent : getGroupSetup().getScheduledEvents())
        {
            ScheduledEventVO scheduledEventVO = scheduledEvent.getVO();
            groupSetupVO.addScheduledEventVO(scheduledEventVO);
        }

        for (ContractSetup contractSetup : getGroupSetup().getContractSetups())
        {
            ContractSetupVO contractSetupVO = (ContractSetupVO) contractSetup.getVO();

            groupSetupVO.addContractSetupVO(contractSetupVO);

            contractSetupVO.setParentVO(GroupSetupVO.class, groupSetupVO);

            for (ClientSetup clientSetup : contractSetup.getClientSetups())
            {
                ClientSetupVO clientSetupVO = (ClientSetupVO) clientSetup.getVO();

                contractSetupVO.addClientSetupVO(clientSetupVO);

                clientSetupVO.setParentVO(ContractSetupVO.class, contractSetupVO);

                for (WithholdingOverride withholdingOverride : clientSetup.getWithholdingOverrides())
                {
                    WithholdingOverrideVO withholdingOverrideVO =
                            (WithholdingOverrideVO) withholdingOverride.getAsVO();

                    clientSetupVO.addWithholdingOverrideVO(withholdingOverrideVO);

                    withholdingOverrideVO.setParentVO(ClientSetupVO.class, clientSetupVO);
                }

                for (ContractClientAllocationOvrd contractClientAllocationOvrd :
                        clientSetup.getContractClientAllocationOvrds())
                {
                    ContractClientAllocationOvrdVO contractClientAllocationOvrdVO =
                            (ContractClientAllocationOvrdVO) contractClientAllocationOvrd.getAsVO();

                    clientSetupVO.addContractClientAllocationOvrdVO(contractClientAllocationOvrdVO);

                    contractClientAllocationOvrdVO.setParentVO(ClientSetupVO.class, clientSetupVO);
                }

                for (EDITTrx editTrx : clientSetup.getEDITTrxs())
                {
                    EDITTrxVO editTrxVO = (EDITTrxVO) editTrx.getAsVO();

                    clientSetupVO.addEDITTrxVO(editTrxVO);

                    editTrxVO.setParentVO(ClientSetupVO.class, clientSetupVO);
                    //??added history VOs for REDO process
                    if (includeHistories)
                    {
                        for (EDITTrxHistory editTrxHistory : editTrx.getEDITTrxHistories())
                        {
                            EDITTrxHistoryVO editTrxHistoryVO = (EDITTrxHistoryVO) editTrxHistory.getAsVO();

                            editTrxVO.addEDITTrxHistoryVO(editTrxHistoryVO);

                            editTrxHistoryVO.setParentVO(EDITTrx.class, editTrxVO);

                            for (ReinsuranceHistory reinsuranceHistory : editTrxHistory.getReinsuranceHistories())
                            {
                                ReinsuranceHistoryVO reinsuranceHistoryVO =
                                        (ReinsuranceHistoryVO) reinsuranceHistory.getAsVO();

                                editTrxHistoryVO.addReinsuranceHistoryVO(reinsuranceHistoryVO);

                                reinsuranceHistoryVO.setParentVO(EDITTrxHistory.class, editTrxHistoryVO);
                            }
                            for (ChargeHistory chargeHistory : editTrxHistory.getChargeHistories())
                            {
                                ChargeHistoryVO chargeHistoryVO =
                                        (ChargeHistoryVO) chargeHistory.getAsVO();

                                editTrxHistoryVO.addChargeHistoryVO(chargeHistoryVO);

                                chargeHistoryVO.setParentVO(EDITTrxHistory.class, editTrxHistoryVO);
                            }
                            for (FinancialHistory financialHistory : editTrxHistory.getFinancialHistories())
                            {
                                FinancialHistoryVO financialHistoryVO =
                                        (FinancialHistoryVO) financialHistory.getAsVO();

                                editTrxHistoryVO.addFinancialHistoryVO(financialHistoryVO);

                                financialHistoryVO.setParentVO(EDITTrxHistory.class, editTrxHistoryVO);
                            }
                            for (CommissionHistory commissionHistory : editTrxHistory.getCommissionHistories())
                            {
                                CommissionHistoryVO commissionHistoryVO =
                                        (CommissionHistoryVO) commissionHistory.getVO();

                                editTrxHistoryVO.addCommissionHistoryVO(commissionHistoryVO);

                                commissionHistoryVO.setParentVO(EDITTrxHistory.class, editTrxHistoryVO);
                            }
                            for (SegmentHistory segmentHistory : editTrxHistory.getSegmentHistories())
                            {
                                SegmentHistoryVO segmentHistoryVO =
                                        (SegmentHistoryVO) segmentHistory.getAsVO();

                                editTrxHistoryVO.addSegmentHistoryVO(segmentHistoryVO);

                                segmentHistoryVO.setParentVO(EDITTrxHistory.class, editTrxHistoryVO);
                            }
                            for (InSuspense inSuspense : editTrxHistory.getInSuspenses())
                            {
                                InSuspenseVO inSuspenseVO = (InSuspenseVO) inSuspense.getAsVO();

                                editTrxHistoryVO.addInSuspenseVO(inSuspenseVO);

                                inSuspenseVO.setParentVO(EDITTrxHistory.class, editTrxHistoryVO);
                            }
                            for (WithholdingHistory withholdingHistory : editTrxHistory.getWithholdingHistories())
                            {
                                WithholdingHistoryVO withholdingHistoryVO =
                                        (WithholdingHistoryVO) withholdingHistory.getAsVO();

                                editTrxHistoryVO.addWithholdingHistoryVO(withholdingHistoryVO);

                                withholdingHistoryVO.setParentVO(EDITTrxHistory.class, editTrxHistoryVO);
                            }
                        }
                    }
                }
            }
        }

        return groupSetupVO;
    }

    /**
     * The EDITTrx needs to be within the GroupSetup composition itself - 
     * it just requires some drilling down. From there we can get
     * the EffectiveDate.
     * 
     * @return
     */
    private EDITDate getEDITTrxEffectiveDate()
    {
        // If you don't have the EDITTrx here, then you have a problem.
        EDITTrx editTrx = getGroupSetup().getContractSetups().iterator().next().getClientSetups().iterator().next().getEDITTrxs().iterator().next();

        return editTrx.getEffectiveDate();
    }

    /**
     * Utility method to set the SegmentFK value for ContractSetup element explicitly.
     * @param contractSetupElement
     * @param segmentFK
     */
    private void setSegmentFKForContractSetup(Element contractSetupElement, Long segmentFK)
    {
        List<Element> segmentFKElements = DOMUtil.getChildren("SegmentFK", contractSetupElement);

        // Should have only one SegmentFK element in ContractSetup.
        Element segmentFKElement = segmentFKElements.get(0);

        segmentFKElement.setText(segmentFK.toString());
    }

    /**
     * The document is built from VO passed in.
     * @param groupSetupVO
     * @return
     * @throws Exception
     */
    public Element buildGroupSetupElementFromVO(GroupSetupVO groupSetupVO) throws Exception
    {
        Document document = null;

        try
        {
            document = XMLUtil.parse(Util.marshalVO(groupSetupVO));
        }
        catch (DocumentException e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }

        Element groupSetupElement = document.getRootElement();

        Element groupSetupDocElement = buildGroupSetupDocument();

        groupSetupDocElement.add(groupSetupElement);

        setRootElement(groupSetupDocElement);

        return groupSetupDocElement;
    }

    public String[] getBuildingParameterNames()
    {
        return buildingParameterNames;
    }
}
