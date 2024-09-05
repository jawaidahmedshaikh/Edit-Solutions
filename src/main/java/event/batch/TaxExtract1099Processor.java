/*
 * User: SRamamurthy
 * Date: Oct 27, 2004
 * Time: 12:43:07 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC. All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential. Any use is
 * subject to the license agreement.
 */
package event.batch;

import edit.common.*;

import edit.common.vo.*;

import edit.services.db.*;
import edit.services.*;

import event.*;

import event.dm.composer.*;

import event.dm.dao.*;

import fission.utility.*;

import java.io.*;

import java.util.*;

import logging.*;


import role.*;
import contract.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import client.*;
import batch.business.*;

public class TaxExtract1099Processor extends TaxExtractProcessor implements Serializable
{
    private String taxReportFilter = null;
    private String TRAN_1099R = "1099-R";
    private String TRAN_1099MISC = "1099-Misc";
    private String TRAN_1099R_DE = "DE";

    private String[] TRAN_TYPE_CT_FOR_1099R = new String[]{"WI", "FS", "SW", "LS", "RW", "NT", "CC", EDITTrx.TRANSACTIONTYPECT_SURRENDER_OVERLOAN, "PO"};
    //private String[] TRAN_TYPE_CT_FOR_1099R = new String[]{"WI", "PY"};
    private String[] TRAN_TYPE_CT_FOR_1099MISC = new String[]{"CK", "CA"};
    private String COMMISSION_ADJUSTMENT = "CA";
    private String DEATHSTAT_DEATH_PENDING = "DeathPending";
    private String REDUCE_TAXABLE_YES = "Y";
    private String DEFAULT_CONTRACT_CLIENT_ALLOCATION_PCT = "1.0";
    private String TRUST_TYPE_CT_INDIVDUAL = "Individual";
    private EDITBigDecimal accumValue = new EDITBigDecimal("0.00");

    public TaxExtract1099Processor(ProductStructureVO[] productStructureVOs)
    {
        super();
        setupFieldNamesForRounding();
        setProductStructures(productStructureVOs);
    }

    public String[] getTranTypeCTFORTaxRpt()
    {
        if ((taxReportFilter != null) && taxReportFilter.equals(TRAN_1099R))
        {
            return TRAN_TYPE_CT_FOR_1099R;
        }
        else if ((taxReportFilter != null) && taxReportFilter.equals(TRAN_1099MISC))
        {
            return TRAN_TYPE_CT_FOR_1099MISC;
        }
        else
        {
            return new String[]{};
        }
    }

    public void setTaxReportFilter(String taxReportFilter)
    {
        this.taxReportFilter = taxReportFilter;
    }

    /**
     * For the parameters entered, get the contracts that satisfy the request.  Then by contract key get all qualifying
     * transactions.  Only one extract per contract will be created, the transactions will be summerized.
     *
     * @param startDate
     * @param endDate
     */
    public void extract(String startDate, String endDate, String taxYear, String fileType) throws Exception
    {
        // 1. Get segmentPKs for the input parameters
        FastDAO fastDAO = new FastDAO();
        long[] segmentPKs = null;
        ArrayList arrSegmentPKs = new ArrayList();

        if ((productStructureVOs != null) && (productStructureVOs.length > 0))
        {
            for (int lc = 0; lc < productStructureVOs.length; lc++)
            {
                arrSegmentPKs.addAll(fastDAO.getTEUniqueSegmentPKsByDateRangeAndProductStructure(startDate, endDate, productStructureVOs[lc].getProductStructurePK(), getTranTypeCTFORTaxRpt(), taxYear));
            }
        }

        //initiate the output file
        File exportFile = getExportFile("1099", fileType);

        if (fileType.equalsIgnoreCase("X"))
        {
            insertStartTaxExtracts(exportFile, "<TaxExtractsVO>");
        }

        if (arrSegmentPKs != null)
        {
            segmentPKs = (Util.convertLongToPrim((Long[]) arrSegmentPKs.toArray(new Long[arrSegmentPKs.size()])));
        }

        if ((segmentPKs != null) && !is1099Misc())
        {
            for (int i = 0; i < segmentPKs.length; i++)
            {
                //Get all Premium, Withdrawal, Full Surrender and Not Taken edit trx records in history with status of "N" or "R"
                //  within the dates selected and for the contract segmentPK
                EditTrxKeyAndTranType editTrxKeyAndTranType = null;
                ArrayList editTrxPKs = null;
                if (is1099R())
                {
                    editTrxPKs = fastDAO.findEditTrxFor1099R(startDate, endDate, segmentPKs[i], getTranTypeCTFORTaxRpt(), taxYear);
                }
                else
                {
                    editTrxPKs = fastDAO.findTaxExtByDateRangeSegmentPK(startDate, endDate, segmentPKs[i], getTranTypeCTFORTaxRpt(), taxYear);
                }

                if (editTrxPKs != null)
                {
                    Iterator itrx = editTrxPKs.iterator();

                    if (editTrxPKs != null)
                    {
                        //process the transactions for each contract
                        for (int j = 0; j < editTrxPKs.size(); j++)
                        {
                            try
                            {
                                editTrxKeyAndTranType = (EditTrxKeyAndTranType) editTrxPKs.get(j);

                                //1099R
                                processEachEditTrx(editTrxKeyAndTranType.getEditTrxPK(), productStructureVOs, exportFile, fileType);
                            }
                            catch (Throwable e)
                            {
                                System.out.println(e);

                                e.printStackTrace();

                                logErrorToDatabase(e, editTrxKeyAndTranType.getEditTrxPK());
                            }
                        }
                    }
                }

                EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_TAX_RESERVES_EXTRACTS).updateSuccess();
            }
        }
        else if ((segmentPKs != null) && is1099Misc())
        {
            long[] editTrxPKs = fastDAO.findTaxExtByDateRangeFor1099Misc(startDate, endDate, getTranTypeCTFORTaxRpt(), taxYear);

            if (editTrxPKs != null)
            {
                //process the transactions for each contract
                for (int j = 0; j < editTrxPKs.length; j++)
                {
                    try
                    {
                        processEachEditTrx(editTrxPKs[j], productStructureVOs, exportFile, fileType);

                        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_TAX_RESERVES_EXTRACTS).updateSuccess();
                    }
                    catch (Throwable e)
                    {
                        System.out.println(e);

                        e.printStackTrace();

                        logErrorToDatabase(e, editTrxPKs[j]);
                    }
                }
            }
        }

        if (fileType.equalsIgnoreCase("X"))
        {
            insertEndTaxExtracts(exportFile, "</TaxExtractsVO>");
        }
    }

    public boolean is1099Misc()
    {
        return ((taxReportFilter != null) && taxReportFilter.equalsIgnoreCase(TRAN_1099MISC));
    }

    public boolean is1099R()
    {
        return ((taxReportFilter != null) && taxReportFilter.equalsIgnoreCase(TRAN_1099R));
    }

    /**
     * For each contract get clients, rates from PRASE and partially build the extract. Each edit trx key will be used to
     * compose an editTrxVO with its specifed parents and children.
     *
     * @param editTrxPK
     * @param productStructureVOs
     * @param exportFile
     * @param fileType
     *
     * @throws Exception
     */
    private void processEachEditTrx(long editTrxPK, ProductStructureVO[] productStructureVOs, File exportFile, String fileType) throws Exception
    {
        TaxExtractDetailVO taxExtractDetailVO = null;
        EDITTrxVO editTrxVO = composeEDITTrxVO(editTrxPK);

        taxExtractDetailVO = new TaxExtractDetailVO();
        taxExtractDetailVO.setTaxExtractPK(CRUD.getNextAvailableKey());
        taxExtractDetailVO.setTaxForm(taxReportFilter);
        taxExtractDetailVO = loadVOFromEditTrx(editTrxVO, taxExtractDetailVO);

        //sramam each edittrx is assumed to have only one editTrxHistory object
        EDITTrxHistoryVO editTrxHstryVO = editTrxVO.getEDITTrxHistoryVO(0);
        long placedAgentFK = 0;

        if (taxReportFilter.equalsIgnoreCase(TRAN_1099MISC) && (editTrxHstryVO != null))
        {
            CommissionHistoryVO commHstryVO = editTrxHstryVO.getCommissionHistoryVO(0);

            if (commHstryVO != null)
            {
                placedAgentFK = commHstryVO.getPlacedAgentFK();
            }

            if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(COMMISSION_ADJUSTMENT) && (commHstryVO != null))
            {
                if ((commHstryVO.getReduceTaxable() != null) && !commHstryVO.getReduceTaxable().equalsIgnoreCase(REDUCE_TAXABLE_YES))
                {
                    return;
                }
                else if (commHstryVO.getReduceTaxable() == null)
                {
                    return;
                }
            }
            else if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(COMMISSION_ADJUSTMENT) && (commHstryVO == null))
            {
                return;
            }
        }

        ClientSetupVO clientSetupVO = (ClientSetupVO)editTrxVO.getParentVO(ClientSetupVO.class);
        ContractSetupVO contractSetupVO = (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);

        if ((contractSetupVO != null) && taxReportFilter.equalsIgnoreCase(TRAN_1099R) && editTrxVO.getTransactionTypeCT().equals(TRAN_1099R_DE) && (contractSetupVO.getDeathStatusCT() != null) && contractSetupVO.getDeathStatusCT().equals(DEATHSTAT_DEATH_PENDING))
        {
            return;
        }

        if (is1099Misc())
        {
            taxExtractDetailVO.setGrossAmount(editTrxVO.getTrxAmount() + "");
        }
        else
        {
            BucketHistoryVO[] bucketHistoryVOs = editTrxVO.getEDITTrxHistoryVO(0).getBucketHistoryVO();
            sumCumDollars(bucketHistoryVOs);
            taxExtractDetailVO.setAccumValue(accumValue.toString());
        }

        taxExtractDetailVO = loadVOFromHistoryTrx(editTrxHstryVO, taxExtractDetailVO);

        SegmentVO segmentVO = (SegmentVO) editTrxVO.getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class).getParentVO(SegmentVO.class);
        long cmpnyStructureKey = 0;

        if (segmentVO != null)
        {
            taxExtractDetailVO = loadVOFromSegment(segmentVO, taxExtractDetailVO);
            cmpnyStructureKey = segmentVO.getProductStructureFK();
        }

        for (int lc = 0; lc < productStructureVOs.length; lc++)
        {
            if (cmpnyStructureKey == productStructureVOs[lc].getProductStructurePK())
            {
                taxExtractDetailVO.setMarketingPackageName(productStructureVOs[lc].getMarketingPackageName());
                taxExtractDetailVO.setBusinessContractName(productStructureVOs[lc].getBusinessContractName());
                break;
            }
        }

        ClientRoleVO clientRoleVO = (ClientRoleVO) clientSetupVO.getParentVO(ClientRoleVO.class);
        ClientDetailVO clientDetailVO = (ClientDetailVO) clientRoleVO.getParentVO(ClientDetailVO.class);

        if (clientDetailVO != null)
        {
            String trustTypeCT = clientDetailVO.getTrustTypeCT();

            if ((trustTypeCT != null) && trustTypeCT.equalsIgnoreCase(TRUST_TYPE_CT_INDIVDUAL))
            {
                taxExtractDetailVO.setPayeeFirstName(clientDetailVO.getFirstName());
                taxExtractDetailVO.setPayeeLastName(clientDetailVO.getLastName());
            }
            else
            {
                taxExtractDetailVO.setPayeeCorporateName(clientDetailVO.getCorporateName());
            }
        }

        if (segmentVO != null)
        {
            addOWNRoleClientInformation(segmentVO, taxExtractDetailVO, clientRoleVO);
        }

        if (placedAgentFK != 0)
        {
            addAgentInformation(placedAgentFK, taxExtractDetailVO);
        }

        GroupSetupVO groupSetupVO = (GroupSetupVO) editTrxVO.getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class).getParentVO(GroupSetupVO.class);

        if ((groupSetupVO != null) && is1099R())
        {
            FinancialHistoryVO[] finHistoryVOs = editTrxHstryVO.getFinancialHistoryVO();
            if (groupSetupVO.getDistributionCodeCT() != null)
            {
                taxExtractDetailVO.setDistributionCode(groupSetupVO.getDistributionCodeCT());
            }
            else
            {
                if (finHistoryVOs[0].getDistributionCodeCT() != null)
                {
                    taxExtractDetailVO.setDistributionCode(finHistoryVOs[0].getDistributionCodeCT());
                }
                else
                {
                    taxExtractDetailVO.setDistributionCode("7_NormalDistrib");
                }
            }
        }

        ContractClientVO contractClientVO = (ContractClientVO) editTrxVO.getParentVO(ClientSetupVO.class).getParentVO(ContractClientVO.class);

        if (contractClientVO != null)
        {
            if ((contractClientVO.getContractClientAllocationVO() != null) && (contractClientVO.getContractClientAllocationVO().length > 0))
            {
                ContractClientAllocationVO contractClientAllocVO = (ContractClientAllocationVO) contractClientVO.getContractClientAllocationVO(0);

                if (contractClientAllocVO != null)
                {
                    taxExtractDetailVO.setContractClientAllocationPct(contractClientAllocVO.getAllocationPercent() + "");
                }
                else
                {
                    taxExtractDetailVO.setContractClientAllocationPct(this.DEFAULT_CONTRACT_CLIENT_ALLOCATION_PCT);
                }
            }
            else
            {
                taxExtractDetailVO.setContractClientAllocationPct(this.DEFAULT_CONTRACT_CLIENT_ALLOCATION_PCT);
            }
        }

        if (is1099R())
        {
            EDITBigDecimal netAmount = new EDITBigDecimal(taxExtractDetailVO.getNetAmount());
            EDITBigDecimal taxableBenefit = new EDITBigDecimal(taxExtractDetailVO.getTaxableBenefit());
            EDITBigDecimal amount5 = netAmount.subtractEditBigDecimal(taxableBenefit);
            taxExtractDetailVO.setAmount51099R(amount5.toString());
        }

        exportTaxExtract(taxExtractDetailVO, exportFile, fileType);
    }

    public TaxExtractDetailVO loadVOFromEditTrx(EDITTrxVO editTrxVO, TaxExtractDetailVO taxExtractDetailVO)
    {
        taxExtractDetailVO.setTransactionType(editTrxVO.getTransactionTypeCT());
        taxExtractDetailVO.setEffectiveDate(editTrxVO.getEffectiveDate());
        taxExtractDetailVO.setTaxYear(editTrxVO.getTaxYear() + "");

        return taxExtractDetailVO;
    }

    public TaxExtractDetailVO loadVOFromSegment(SegmentVO segmentVO, TaxExtractDetailVO taxExtractDetailVO)
    {
        taxExtractDetailVO.setQualNonQualIndicator(segmentVO.getQualNonQualCT());
        taxExtractDetailVO.setQualifiedType(segmentVO.getQualifiedTypeCT());
        taxExtractDetailVO.setContractNumber(segmentVO.getContractNumber());

        return taxExtractDetailVO;
    }

    public TaxExtractDetailVO loadVOFromHistoryTrx(EDITTrxHistoryVO editTrxHstryVO, TaxExtractDetailVO taxExtractDetailVO)
    {
        taxExtractDetailVO.setProcessDate(editTrxHstryVO.getProcessDateTime());

        CommissionHistoryVO commHstryVO = null;

        if ((editTrxHstryVO.getCommissionHistoryVO() != null) && (editTrxHstryVO.getCommissionHistoryVO().length > 0))
        {
            commHstryVO = editTrxHstryVO.getCommissionHistoryVO(0);
        }

        if (commHstryVO != null)
        {
            taxExtractDetailVO.setReduceTaxable(commHstryVO.getReduceTaxable());
        }

        FinancialHistoryVO finHistoryVO = null;
        FinancialHistoryVO[] finHistoryVOs = editTrxHstryVO.getFinancialHistoryVO();

        if (finHistoryVOs.length > 0)
        {
            finHistoryVO = editTrxHstryVO.getFinancialHistoryVO(0);
        }

        if (finHistoryVO != null)
        {
            if (is1099R())
            {
                taxExtractDetailVO.setGrossAmount(finHistoryVO.getGrossAmount() + "");
            }

            String taxableBenefit = finHistoryVO.getTaxableBenefit() + "";
            taxExtractDetailVO.setTaxableBenefit(taxableBenefit);

            if (is1099Misc())
            {
                taxExtractDetailVO.setAccumValue(finHistoryVO.getAccumulatedValue() + "");
            }

            taxExtractDetailVO.setNetAmount(finHistoryVO.getNetAmount() + "");
        }

        WithholdingHistoryVO[] withHldingArray = editTrxHstryVO.getWithholdingHistoryVO();
        WithholdingHistoryVO withhldHstryVO = null;

        if (withHldingArray.length > 0)
        {
            withhldHstryVO = editTrxHstryVO.getWithholdingHistoryVO(0);
        }

        if (withhldHstryVO != null)
        {
            taxExtractDetailVO.setFederalWithholdingAmount(withhldHstryVO.getFederalWithholdingAmount() + "");
            taxExtractDetailVO.setStateWithholdingAmount(withhldHstryVO.getStateWithholdingAmount() + "");
            taxExtractDetailVO.setLocalWithholdingAmount(withhldHstryVO.getCityWithholdingAmount() + "");
        }

        return taxExtractDetailVO;
    }

    /**
     * The editTrxVO is built according to the define list.
     *
     * @param editTrxPK
     *
     * @return EDITTrxVO
     *
     * @throws Exception
     */
    private EDITTrxVO composeEDITTrxVO(long editTrxPK) throws Exception
    {
        List voInclusionList = new ArrayList();
        voInclusionList.add(EDITTrxHistoryVO.class);
        voInclusionList.add(FinancialHistoryVO.class);
        voInclusionList.add(BucketHistoryVO.class);
        voInclusionList.add(ClientSetupVO.class);
        voInclusionList.add(ContractSetupVO.class);
        voInclusionList.add(SegmentVO.class);
        voInclusionList.add(DepositsVO.class);
        voInclusionList.add(ContractClientVO.class);
        voInclusionList.add(InvestmentVO.class);
        voInclusionList.add(InvestmentAllocationVO.class);
        voInclusionList.add(BucketVO.class);
        voInclusionList.add(ClientDetailVO.class);
        voInclusionList.add(GroupSetupVO.class);
        voInclusionList.add(ContractClientAllocationVO.class);
        voInclusionList.add(CommissionHistoryVO.class);
        voInclusionList.add(ClientRoleVO.class);
        voInclusionList.add(WithholdingHistoryVO.class);

        EDITTrxComposer editTrxComposer = new EDITTrxComposer(voInclusionList);
        EDITTrxVO editTrxVO = editTrxComposer.compose(editTrxPK);

        return editTrxVO;
    }

    /**
     * For the contract clients defined on the contract get their defined roles.  If the role is the owner or joint owner
     * get the client detail for the gender.  Issueage of the owner and joint owner are captured into the extract record
     * along with the genders.
     *
     * @param segmentVO
     * @param taxExtractDetailVO
     *
     * @throws Exception
     */
    private void addOWNRoleClientInformation(SegmentVO segmentVO, TaxExtractDetailVO taxExtractDetailVO, ClientRoleVO clientRoleVO) throws Exception
    {
        client.business.Lookup clientLookup = new client.component.LookupComponent();

        List voInclusionList = new ArrayList();
        voInclusionList.add(TaxInformationVO.class);
//        voInclusionList.add(ClientAddressVO.class);

        ClientDetailVO clientDetailVO = null;

        List contractClients = new ArrayList();
        ContractClientVO[] contractClientVOs = segmentVO.getContractClientVO();

        //get the client data for the contractClient of the transaction
        if (clientRoleVO != null)
        {
            String roleType = clientRoleVO.getRoleTypeCT();
            clientDetailVO = clientLookup.composeClientDetailVO(clientRoleVO.getClientDetailFK(), voInclusionList);

            boolean payeeRoleFound = false;
            if (roleType.equalsIgnoreCase(ClientRole.ROLETYPECT_PAYEE))
            {
                taxExtractDetailVO.setPayeeLastName(clientDetailVO.getLastName());
                taxExtractDetailVO.setPayeeFirstName(clientDetailVO.getFirstName());
                payeeRoleFound = true;

                //always use owner when payee override found
                clientDetailVO = getContractOwnerInfo(contractClientVOs);
            }

            //Can be own or sow role
            if (clientDetailVO != null)
            {
                taxExtractDetailVO.setOwnerLastName(clientDetailVO.getLastName());
                taxExtractDetailVO.setOwnerFirstName(clientDetailVO.getFirstName());
                taxExtractDetailVO.setTaxID(clientDetailVO.getTaxIdentification());
                taxExtractDetailVO.setOwnerDateOfBirth(clientDetailVO.getBirthDate());

                if (!payeeRoleFound)
                {
                    taxExtractDetailVO.setPayeeLastName(clientDetailVO.getLastName());
                    taxExtractDetailVO.setPayeeFirstName(clientDetailVO.getFirstName());
                }

                ClientAddress clientAddress = ClientAddress.findCurrentAddress(new Long(clientDetailVO.getClientDetailPK()));

//                if ((clientDetailVO.getClientAddressVO() != null) && (clientDetailVO.getClientAddressVO().length > 0))
//                {
//                    clientAddress = clientDetailVO.getClientAddressVO(0);
//                }

                if (clientAddress != null)
                {
                    taxExtractDetailVO.setAddressLine1(clientAddress.getAddressLine1());
                    taxExtractDetailVO.setAddressLine2(clientAddress.getAddressLine2());
                    taxExtractDetailVO.setAddressLine3(clientAddress.getAddressLine3());
                    taxExtractDetailVO.setAddressLine4(clientAddress.getAddressLine4());
                    taxExtractDetailVO.setCity(clientAddress.getCity());
                    taxExtractDetailVO.setState(clientAddress.getStateCT());
                    taxExtractDetailVO.setZip(clientAddress.getZipCode());
                }

                TaxInformationVO taxInformation = null;
                if ((clientDetailVO.getTaxInformationVO() != null) && (clientDetailVO.getTaxInformationVO().length > 0))
                {
                    taxInformation = clientDetailVO.getTaxInformationVO(0);

                    if (taxInformation != null)
                    {
                        taxExtractDetailVO.setTaxIdType(taxInformation.getTaxIdTypeCT());
                    }
                }
            }
        }
        else
        {
            getClientInformationUsingContractClients(contractClientVOs, taxExtractDetailVO);
        }
    }

    private ClientDetailVO getContractOwnerInfo(ContractClientVO[] contractClientVOs) throws Exception
    {
        client.business.Lookup clientLookup = new client.component.LookupComponent();
        ContractClient contractClient = new ContractClient();

        ContractClientVO contractClientVO = contractClient.getOwnerContractClient(contractClientVOs);

        List voInclusionList = new ArrayList();
        voInclusionList.add(TaxInformationVO.class);
        voInclusionList.add(ClientAddressVO.class);
        ClientDetailVO clientDetailVO = null;

        ClientRoleVO clientRoleVO = (ClientRoleVO)contractClientVO.getParentVO(ClientRoleVO.class);
        long clientDetailPK = clientRoleVO.getClientDetailFK();
        clientDetailVO = clientLookup.composeClientDetailVO(clientDetailPK, voInclusionList);

        return clientDetailVO;
    }

    private void getClientInformationUsingContractClients(ContractClientVO[] contractClientVOs , TaxExtractDetailVO taxExtractDetailVO) throws Exception
    {
        role.business.Lookup roleLookup = new role.component.LookupComponent();
        String roleTypeCT = null;
        client.business.Lookup clientLookup = new client.component.LookupComponent();
        EDITDate currentDate = new EDITDate();

        for (int i = 0; i < contractClientVOs.length; i++)
        {
            // eliminate using terminated contractClients
            int compareValue = contractClientVOs[i].getTerminationDate().compareTo(currentDate.getFormattedDate());

            if (compareValue >= 0)
            {
                ClientRoleVO[] clientRoleVO = roleLookup.getRoleByClientRolePK(contractClientVOs[i].getClientRoleFK());
                ClientDetailVO clientDetailVO = null;

                if (clientRoleVO != null)
                {
                    TaxInformationVO taxInformation = null;
                    roleTypeCT = clientRoleVO[0].getRoleTypeCT();

                    if (roleTypeCT.equalsIgnoreCase("OWN") || roleTypeCT.equalsIgnoreCase("SOW") || roleTypeCT.equalsIgnoreCase("PAY"))
                    {
                        long clientDetailPK = clientRoleVO[0].getClientDetailFK();

                        //clientDetailVO = clientLookup.findByClientPK(clientDetailPK, false, null);
                        List voInclusionList = new ArrayList();
                        voInclusionList.add(TaxInformationVO.class);
                        voInclusionList.add(ClientAddressVO.class);
                        clientDetailVO = clientLookup.composeClientDetailVO(clientDetailPK, voInclusionList);

                        if (clientDetailVO != null)
                        {
                            if (roleTypeCT.equalsIgnoreCase("OWN") || roleTypeCT.equalsIgnoreCase("SOW"))
                            {
                                taxExtractDetailVO.setOwnerLastName(clientDetailVO.getLastName());
                                taxExtractDetailVO.setOwnerFirstName(clientDetailVO.getFirstName());
                                taxExtractDetailVO.setTaxID(clientDetailVO.getTaxIdentification());
                                taxExtractDetailVO.setOwnerDateOfBirth(clientDetailVO.getBirthDate());

                                ClientAddressVO clientAddress = null;

                                if ((clientDetailVO.getClientAddressVO() != null) && (clientDetailVO.getClientAddressVO().length > 0))
                                {
                                    clientAddress = clientDetailVO.getClientAddressVO(0);
                                }

                                if (clientAddress != null)
                                {
                                    taxExtractDetailVO.setAddressLine1(clientAddress.getAddressLine1());
                                    taxExtractDetailVO.setAddressLine2(clientAddress.getAddressLine2());
                                    taxExtractDetailVO.setAddressLine3(clientAddress.getAddressLine3());
                                    taxExtractDetailVO.setAddressLine4(clientAddress.getAddressLine4());
                                    taxExtractDetailVO.setCity(clientAddress.getCity());
                                    taxExtractDetailVO.setState(clientAddress.getStateCT());
                                    taxExtractDetailVO.setZip(clientAddress.getZipCode());
                                }

                                if ((clientDetailVO.getTaxInformationVO() != null) && (clientDetailVO.getTaxInformationVO().length > 0))
                                {
                                    taxInformation = clientDetailVO.getTaxInformationVO(0);

                                    if (taxInformation != null)
                                    {
                                        taxExtractDetailVO.setTaxIdType(taxInformation.getTaxIdTypeCT());
                                    }
                                }
                            }
                            else if (roleTypeCT.equalsIgnoreCase("PAY"))
                            {
                                taxExtractDetailVO.setPayeeLastName(clientDetailVO.getLastName());
                                taxExtractDetailVO.setPayeeFirstName(clientDetailVO.getFirstName());
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * For the contract clients defined on the contract get their defined roles.  If the role is the owner or joint owner
     * get the client detail for the gender.  Issueage of the owner and joint owner are captured into the extract record
     * along with the genders.
     *
     * @param placedAgentFK
     * @param taxExtractDetailVO
     *
     * @throws Exception
     */
    private void addAgentInformation(long placedAgentFK, TaxExtractDetailVO taxExtractDetailVO) throws Exception
    {
        List voInclusionList = new ArrayList();
        voInclusionList.add(AgentContractVO.class);
        voInclusionList.add(AgentVO.class);
        voInclusionList.add(ClientRoleVO.class);
        voInclusionList.add(ClientDetailVO.class);
        voInclusionList.add(ClientAddressVO.class);
        voInclusionList.add(TaxInformationVO.class);

        agent.business.Agent agentComponent = new agent.component.AgentComponent();

        PlacedAgentVO placedAgentVO = agentComponent.composePlacedAgentVOByPlacedAgentPK(placedAgentFK, voInclusionList);

        if (placedAgentVO != null)
        {
            AgentVO agentVO = (AgentVO) placedAgentVO.getParentVO(AgentContractVO.class).getParentVO(AgentVO.class);
            String disbursementAddressTypeCT = agentVO.getDisbursementAddressTypeCT();
            taxExtractDetailVO.setAgentNumber(agentVO.getAgentNumber());

            ClientDetailVO clientDetailVO = (ClientDetailVO) agentVO.getParentVO(ClientRoleVO.class).getParentVO(ClientDetailVO.class);
            taxExtractDetailVO.setTaxID(clientDetailVO.getTaxIdentification());

            TaxInformationVO[] taxInformationVO = clientDetailVO.getTaxInformationVO();

            if ((taxInformationVO != null) && (taxInformationVO.length > 0))
            {
                taxExtractDetailVO.setTaxIdType(taxInformationVO[0].getTaxIdTypeCT());
            }

            ClientAddressVO[] clientAddressVOs = clientDetailVO.getClientAddressVO();

            if ((disbursementAddressTypeCT == null) || disbursementAddressTypeCT.equals(""))
            {
                disbursementAddressTypeCT = "Primary Address";
            }

            boolean addressTypeFound = loopThroughClientAddresses(disbursementAddressTypeCT, clientAddressVOs, taxExtractDetailVO);

            if (!addressTypeFound)
            {
                disbursementAddressTypeCT = "PrimaryAddress";
                loopThroughClientAddresses(disbursementAddressTypeCT, clientAddressVOs, taxExtractDetailVO);
            }
        }
    }

    private boolean loopThroughClientAddresses(String disbursementAddressTypeCT, ClientAddressVO[] clientAddressVOs, TaxExtractDetailVO taxExtractDetailVO)
    {
        EDITDate edCurrentDate = new EDITDate();

        boolean addressTypeFound = false;

        ClientAddressVO clientAddressVO = null;

        for (int i = 0; i < clientAddressVOs.length; i++)
        {
            if (clientAddressVOs[i].getAddressTypeCT().equalsIgnoreCase(disbursementAddressTypeCT))
            {
                addressTypeFound = true;

                EDITDate edAddressTermDate = new EDITDate(clientAddressVOs[i].getTerminationDate());

                // The default value of effectiveDate on the ClientAddress is 'NULL' if the user doesn't enter one.
                // When the effectiveDate is not 'NULL' compare with both effectiveDate and terminationDate
                if (clientAddressVOs[i].getEffectiveDate() != null)
                {
                    EDITDate edAddressEffDate = new EDITDate(clientAddressVOs[i].getEffectiveDate());

                    if ( (edAddressEffDate.before(edCurrentDate) || edAddressEffDate.equals(edCurrentDate)) &&
                          edAddressTermDate.after(edCurrentDate) )
                    {
                        clientAddressVO = clientAddressVOs[i];
                    }
                }

                // otherwise compare only with terminationDate.
                else if (edAddressTermDate.after(edCurrentDate))
                {
                    clientAddressVO = clientAddressVOs[i];
                }
            }
        }

        if (clientAddressVO != null)
        {
            taxExtractDetailVO.setAddressLine1(clientAddressVO.getAddressLine1());
            taxExtractDetailVO.setAddressLine2(clientAddressVO.getAddressLine2());
            taxExtractDetailVO.setAddressLine3(clientAddressVO.getAddressLine3());
            taxExtractDetailVO.setAddressLine4(clientAddressVO.getAddressLine4());
            taxExtractDetailVO.setCity(clientAddressVO.getCity());
            taxExtractDetailVO.setState(clientAddressVO.getStateCT());
            taxExtractDetailVO.setZip(clientAddressVO.getZipCode());
        }

        return addressTypeFound;
    }

    private void sumCumDollars(BucketHistoryVO[] bucketHistoryVOs)
    {
        accumValue = new EDITBigDecimal("0.00");

        for (int i = 0; i < bucketHistoryVOs.length; i++)
        {
            accumValue = accumValue.addEditBigDecimal(bucketHistoryVOs[i].getCumDollars());
        }
    }

    /**
     * Write extract record to the export file
     *
     * @param taxExtractDetailVO
     * @param exportFile
     *
     * @throws Exception
     */
    private void exportTaxExtract(TaxExtractDetailVO taxExtractDetailVO, File exportFile, String fileType) throws Exception
    {
        if (fileType.equalsIgnoreCase("X"))
        {
            String parsedXML = roundDollarFields(taxExtractDetailVO);
            
            parsedXML = XMLUtil.parseOutXMLDeclaration(parsedXML);
 
            appendToFile(exportFile, parsedXML);
        }
        else
        {
            outputTSV(exportFile, taxExtractDetailVO);
        }
    }

    private void outputTSV(File exportFile, TaxExtractDetailVO taxExtractDetailVO) throws Exception
    {
        StringBuffer fileData = new StringBuffer();
        fileData.append(taxExtractDetailVO.getTaxExtractPK());
        fileData.append("\t");
        fileData.append(taxExtractDetailVO.getTransactionType());
        fileData.append("\t");
        fileData.append(taxExtractDetailVO.getProcessDate());
        fileData.append("\t");
        fileData.append(taxExtractDetailVO.getEffectiveDate());
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getQualNonQualIndicator(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getQualifiedType(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getMarketingPackageName(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getBusinessContractName(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getOwnerDateOfBirth(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getPayeeLastName(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getPayeeFirstName(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getPayeeCorporateName(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getOwnerLastName(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getOwnerFirstName(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getNetAmount(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getTaxForm(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getAddressLine1(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getAddressLine2(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getAddressLine3(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getAddressLine4(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getCity(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getState(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getZip(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getTaxYear(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getTaxIdType(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getTaxID(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getContractNumber(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getGrossAmount(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getTaxableBenefit(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getFederalWithholdingAmount(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getStateWithholdingAmount(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getLocalWithholdingAmount(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getAccumValue(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getDistributionCode(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getContractClientAllocationPct(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getReduceTaxable(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getDepositType(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getAgentNumber(), ""));
        fileData.append("\t");
        fileData.append(Util.initString(taxExtractDetailVO.getAmount51099R(), ""));
        fileData.append("\n");

        appendToFile(exportFile, fileData.toString());
    }

    /**
     * List field names for rounding
     */
    private void setupFieldNamesForRounding()
    {
        List fieldNames = new ArrayList();

        this.fieldNames = (String[]) fieldNames.toArray(new String[fieldNames.size()]);
    }

    /**
     * Log an error to the database.
     *
     * @param e                     exception whose message will be logged
     * @param editTrxPK
     */
    private void logErrorToDatabase(Throwable e, long editTrxPK)
    {
        EDITTrx editTrx = EDITTrx.findBy_PK(new Long(editTrxPK));

        String contractNumber = editTrx.getContractNumber();

        //  Log error to database
        EDITMap columnInfo = new EDITMap ("ProcessDate", new EDITDate().getFormattedDate());
        columnInfo.put("TaxReportType", this.taxReportFilter);

        if (this.is1099Misc())
        {
            columnInfo.put("AgentNumber", "N/A");   // should have agentNumber for 1099-Misc but don't have at this time
        }
        else
        {
            columnInfo.put("AgentNumber", "N/A");
        }

        columnInfo.put("ContractNumber", contractNumber);

        Log.logToDatabase(Log.YEAR_END_TAX_REPORTING, "Tax Extract Failed: " + e.getMessage(), columnInfo);
    }
}
