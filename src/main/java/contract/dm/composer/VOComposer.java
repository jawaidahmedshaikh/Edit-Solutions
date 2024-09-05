package contract.dm.composer;

import contract.dm.dao.DAOFactory;
import contract.Bucket;
import edit.common.vo.*;
import edit.common.EDITBigDecimal;
import fission.utility.Util;
import role.dm.composer.ClientRoleComposer;

import java.util.ArrayList;
import java.util.List;

import engine.FilteredFund;
import engine.Fund;
import event.EDITTrx;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Aug 15, 2003
 * Time: 10:23:29 AM
 * To change this template use Options | File Templates.
 */
public class VOComposer
{
     public ContractClientVO composeContractClientVO(ClientSetupVO clientSetupVO, List voInclusionList)
    {
        ContractClientAllocationOvrdVO[] contractClientAllocationOverrideVO = clientSetupVO.getContractClientAllocationOvrdVO();

        WithholdingOverrideVO[] withholdingOverrideVO = clientSetupVO.getWithholdingOverrideVO();

        ContractClientAllocationVO[] contractClientAllocationVO = null;

        if (contractClientAllocationOverrideVO != null && contractClientAllocationOverrideVO.length > 0) // Get override
        {
            contractClientAllocationVO = DAOFactory.getContractClientAllocationDAO().findByContractClientAllocationPK(contractClientAllocationOverrideVO[0].getContractClientAllocationFK(), false, null);
        }
        else // Otherwise, get by OverrideStatus
        {
            contractClientAllocationVO = DAOFactory.getContractClientAllocationDAO().findByContractClientPKAndOverrideStatus(clientSetupVO.getContractClientFK(), "P", false, null);
        }

        WithholdingVO[] withholdingVO = null;

        if (withholdingOverrideVO != null && withholdingOverrideVO.length > 0) // Get override
        {
            withholdingVO = DAOFactory.getWithholdingDAO().findByWithholdingPK(withholdingOverrideVO[0].getWithholdingFK(), false, null);
        }
        else
        {
            withholdingVO = DAOFactory.getWithholdingDAO().findByContractClientPK(clientSetupVO.getContractClientFK(), false, null);
        }

        ContractClientComposer composer = new ContractClientComposer(voInclusionList);
        composer.substituteContractClientAllocationVO(contractClientAllocationVO);
        composer.substituteWithholdingVO(withholdingVO);

        ContractClientVO contractClientVO = composer.compose(clientSetupVO.getContractClientFK());

        return contractClientVO;
    }

    /**
     * Composes ContractClientVO with FK references filled (ClientRoleVO, for example)
     * @param segmentPK
     * @param contractClientOverrideStatus
     * @param voInclusionList Possible values are ContractClientAllocationVO and WithholdingVO. The ClientRole is assumed fully composed.
     * @return
     * @throws Exception
     */
    public ContractClientVO[] composeContractClientVO(long segmentPK, String contractClientOverrideStatus, List voInclusionList) throws Exception
    {
        ContractClientVO[] contractClientVO = DAOFactory.getContractClientDAO().findBySegmentPKAndContractClientAllocationOverrideStatus(segmentPK, contractClientOverrideStatus, false, null);

        List contractClientVOs = null;

        if (contractClientVO != null)
        {
            contractClientVOs = new ArrayList();

            for (int i = 0; i < contractClientVO.length; i++)
            {
                ContractClientComposer contractClientComposer = new ContractClientComposer(voInclusionList);

                ClientRoleVO clientRoleVO = new ClientRoleComposer(voInclusionList).compose(contractClientVO[i].getClientRoleFK());

                contractClientVO[i].setParentVO(ClientRoleVO.class, clientRoleVO);

                contractClientComposer.compose(contractClientVO[i]);;

                contractClientVOs.add(contractClientVO[i]);
            }
        }

        if (contractClientVOs == null)
        {
            return null;
        }
        else
        {
            return (ContractClientVO[]) contractClientVOs.toArray(new ContractClientVO[contractClientVOs.size()]);
        }
    }

    /**
     * Composes ContractClientVO with FK references filled (ClientRoleVO, for example)
     * @param segmentPK
     * @param voInclusionList Possible values are ContractClientAllocationVO and WithholdingVO. The ClientRole is assumed fully composed.
     * @return
     * @throws Exception
     */
    public ContractClientVO[] composeContractClientVOBySegmentFK(long segmentPK, List voInclusionList) throws Exception
    {
        ContractClientVO[] contractClientVO = DAOFactory.getContractClientDAO().findBySegmentFK(segmentPK, false, new ArrayList());

        List contractClientVOs = null;

        if (contractClientVO != null)
        {
            contractClientVOs = new ArrayList();

            for (int i = 0; i < contractClientVO.length; i++)
            {
                ContractClientComposer contractClientComposer = new ContractClientComposer(voInclusionList);

                ClientRoleVO clientRoleVO = new ClientRoleComposer(voInclusionList).compose(contractClientVO[i].getClientRoleFK());

                contractClientVO[i].setParentVO(ClientRoleVO.class, clientRoleVO);

                contractClientComposer.compose(contractClientVO[i]);;

                contractClientVOs.add(contractClientVO[i]);
            }
        }

        if (contractClientVOs == null)
        {
            return null;
        }
        else
        {
            return (ContractClientVO[]) contractClientVOs.toArray(new ContractClientVO[contractClientVOs.size()]);
        }
    }

    /**
     * Builds all InvestmentVOs for the specied Segment. All BucketVOs are included with BucketAllocationVOs where overrides are supplied.
     * InvestmentVOs include their InvestmentAllocationVO by OverrideStatus, unless overrides are supplied.
     * @return
     * @throws java.lang.Exception
     */
    public InvestmentVO[] composeInvestmentVO(long segmentPK, InvestmentAllocationOverrideVO[] investmentAllocationOverrideVO,  List voInclusionList,
                                               String trxType) throws Exception
    {
        List investmentVOs = new ArrayList();

        List systemFundPKs = new ArrayList();



        if (investmentAllocationOverrideVO != null && investmentAllocationOverrideVO.length > 0 && !trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_DEATH))
        {
            boolean investmentAlreadyFound = false;

            for (int i = 0; i < investmentAllocationOverrideVO.length; i++)
            {
                investmentAlreadyFound = false;
                InvestmentVO[] investmentVO = DAOFactory.getInvestmentDAO().findByInvestmentPK(investmentAllocationOverrideVO[i].getInvestmentFK(), false, null);

                if (investmentVO != null)
                {
                    InvestmentAllocationVO[] investmentAllocationVO = DAOFactory.getInvestmentAllocationDAO().findByInvestmentAllocationPK(investmentAllocationOverrideVO[i].getInvestmentAllocationFK(), false, null);

                    long[] bucketAllocationPK = null;


                    for (int j = 0; j < investmentVOs.size(); j++)
                    {
                        InvestmentVO invVO = (InvestmentVO) investmentVOs.get(j);
                        InvestmentAllocationVO[] existingInvAllocVOs = invVO.getInvestmentAllocationVO();
                        if (invVO.getInvestmentPK() == investmentVO[0].getInvestmentPK())
                        {
                            boolean invAllocAlreadyFound = false;

                            for (int k = 0; k < investmentAllocationVO.length; k++)
                            {
                                invAllocAlreadyFound = false;
                                if (existingInvAllocVOs != null)
                                {
                                    for (int l = 0; l < existingInvAllocVOs.length; l++)
                                    {
                                        if (investmentAllocationVO[k].getInvestmentAllocationPK() == existingInvAllocVOs[l].getInvestmentAllocationPK())
                                        {
                                            invAllocAlreadyFound = true;
                                            investmentAlreadyFound = true;
                                            l = existingInvAllocVOs.length;
                                        }
                                    }
                                }

                                if (!invAllocAlreadyFound)
                                {
                                    invVO.addInvestmentAllocationVO(investmentAllocationVO[k]);
                                    investmentVOs.set(j, invVO);
                                    investmentAlreadyFound = true;
                                    j = investmentVOs.size();
                                }
                            }
                        }
                    }

                    if (!investmentAlreadyFound)
                    {
                        InvestmentComposer composer = new InvestmentComposer(voInclusionList);
                        composer.substituteInvestmentAllocationVO(investmentAllocationVO);

                        composer.compose(investmentVO[0]);

                        investmentVOs.add(investmentVO[0]);
                    }
                }
            }

            // for LO, HLOAN or LR  add the Loan fund to the investment overrides
            List voExclusionList = new ArrayList();
            voExclusionList.add(CommissionInvestmentHistoryVO.class);
            voExclusionList.add(InvestmentAllocationVO.class);
            voExclusionList.add(AnnualizedSubBucketVO.class);
            voExclusionList.add(BucketHistoryVO.class);
            voExclusionList.add(InvestmentHistoryVO.class);

            if (trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN) ||
                trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT) ||
                trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_LOAN))
            {
                InvestmentVO[] investmentVO = DAOFactory.getInvestmentDAO().findBySegmentPK(segmentPK, true, voExclusionList);

                for (int j = 0; j < investmentVO.length; j++)
                {
                    FundVO fundVO = Fund.findByFilteredFundPK(investmentVO[j].getFilteredFundFK());
                    if (fundVO.getLoanQualifierCT() != null && fundVO.getLoanQualifierCT().equalsIgnoreCase(Fund.NONPREFRRED_LOAN_QUALIFIER))
                    {
                        investmentVOs.add(investmentVO[j]);
                    }
                }
            }
        }
        else
        {
            //some transactions need all the non-System type code funds and loan fund if it exists
            List voExclusionList = new ArrayList();
            voExclusionList.add(BucketVO.class);
            voExclusionList.add(BucketAllocationVO.class);
            InvestmentVO[] investmentVO = null;

            // These are money-out or no effect at all (e.g NOT money in)
            if (trxType.equalsIgnoreCase("MD") ||
                trxType.equalsIgnoreCase("FS") ||
                trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SURRENDER_OVERLOAN) ||
                trxType.equalsIgnoreCase("DE") ||
                trxType.equalsIgnoreCase("NT") ||
                trxType.equalsIgnoreCase("WI") ||
                trxType.equalsIgnoreCase("SW") ||
                trxType.equalsIgnoreCase("CY") ||
                trxType.equalsIgnoreCase("PE") ||
                trxType.equalsIgnoreCase("MF") ||
                trxType.equalsIgnoreCase("TU") ||
                trxType.equalsIgnoreCase("FD") ||
                trxType.equalsIgnoreCase("LS") ||
                trxType.equalsIgnoreCase("SLS") ||
                trxType.equalsIgnoreCase("CC") ||
                trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LUMPSUM) ||
                trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_DEATH) ||
                trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN) ||
                trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT) ||
                trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_MONTHLY_COLLATERALIZATION) ||
                trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_CAPITALIZATION) ||
                trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_STATEMENT))
            {
                investmentVO = DAOFactory.getInvestmentDAO().findBySegmentPK(segmentPK, true, voExclusionList);

                if (investmentVO != null)
                {
                //The MF and HDTH get all the investments
                if (!trxType.equalsIgnoreCase("MF") && !trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_DEATH))
                {
                    List productInvestments = new ArrayList();
                    for (int i = 0; i < investmentVO.length; i++)
                    {
                        if (investmentVO[i].getStatus() == null)
                        {
                            long filteredFundFK = investmentVO[i].getFilteredFundFK();
                            FilteredFund filteredFund = FilteredFund.findByPK(new Long(filteredFundFK));
                            Fund fund = filteredFund.getFund();

                            // the LC get only the loan fund
                            if (trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_CAPITALIZATION))
                            {
                                  if (fund.getLoanQualifierCT() != null && fund.getLoanQualifierCT().equalsIgnoreCase(Fund.NONPREFRRED_LOAN_QUALIFIER))
                                  {
                                      productInvestments.add(investmentVO[i]);
                                  }
                            }
                            //Loan trx pro-rata get all non-Hedge Funds
                            else if (trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN))
                            {
                                if (!fund.getFundType().equalsIgnoreCase("Hedge"))
                                {
                                    productInvestments.add(investmentVO[i]);
                                }
                            }
                            // Most of the other transactions in the list above will get all the non-system funds and the loan fund if it exists
                            else if (!fund.getTypeCodeCT().equals("System"))
                            {
                                if (trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_MONTHLY_COLLATERALIZATION))
                                {
                                    BucketVO[] bucketVOs = DAOFactory.getBucketDAO().findByInvestmentFK(investmentVO[i].getInvestmentPK(), false, null);

                                    EDITBigDecimal totalCumDollars = new EDITBigDecimal();
                                    EDITBigDecimal totalCumUnits = new EDITBigDecimal();

                                    if (bucketVOs != null)
                                    {
                                        for (int j = 0; j < bucketVOs.length; j++)
                                        {
                                            totalCumDollars = totalCumDollars.addEditBigDecimal(bucketVOs[j].getCumDollars());
                                            totalCumUnits = totalCumUnits.addEditBigDecimal(bucketVOs[j].getCumUnits());
                                        }

                                        // for ML transactions do not add investment that have all empty buckets
                                        if (totalCumDollars.isGT("0") || totalCumUnits.isGT("0"))
                                        {
                                productInvestments.add(investmentVO[i]);
                            }
                                    }
                                }
                                else
                                {
                                    productInvestments.add(investmentVO[i]);
                                }
                            }
                            // the MD looks at the system fund and will use it, if the buckets were created were for the TF
                            else if (trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_MODALDEDUCTION) &&
                                     fund.getTypeCodeCT().equalsIgnoreCase("System"))
                            {
                                BucketVO[] bucketVOs = DAOFactory.getBucketDAO().findByInvestmentFK(investmentVO[i].getInvestmentPK(), false, null);
                                if (bucketVOs != null)
                                {
                                    investmentVO[i].removeAllBucketVO();
                                    boolean bucketsFound = false;
                                    for (int j = 0; j < bucketVOs.length; j++)
                                    {
                                        if (bucketVOs[j].getBucketSourceCT() != null &&
                                            bucketVOs[j].getBucketSourceCT().equalsIgnoreCase(Bucket.BUCKETSOURCECT_TRANSFER) &&
                                            new EDITBigDecimal(bucketVOs[j].getCumUnits()).isGT("0"))
                                        {
                                            systemFundPKs.add(investmentVO[i].getInvestmentPK() + "");
                                            bucketsFound = true;
                                            break;
                                        }
                                    }

                                    if (bucketsFound)
                                    {
                                        productInvestments.add(investmentVO[i]);
                                    }
                                    else
                                    {
                                      if (fund.getLoanQualifierCT() != null && fund.getLoanQualifierCT().equalsIgnoreCase(Fund.NONPREFRRED_LOAN_QUALIFIER))
                                      {
                                          productInvestments.add(investmentVO[i]);
                                      }
                                    }
                                }
                                else
                                {
                                  if (fund.getLoanQualifierCT() != null && fund.getLoanQualifierCT().equalsIgnoreCase(Fund.NONPREFRRED_LOAN_QUALIFIER))
                                  {
                                      productInvestments.add(investmentVO[i]);
                                  }
                                }
                            }
                            //The WI and SW will not get the loan fund
                            else if (!trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_WITHDRAWAL) &&
                                     !trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SYSTEMATIC_WITHDRAWAL))
                            {
                                  if (fund.getLoanQualifierCT() != null && fund.getLoanQualifierCT().equalsIgnoreCase(Fund.NONPREFRRED_LOAN_QUALIFIER))
                                  {
                                      productInvestments.add(investmentVO[i]);
                                  }
                            }
                        }
                    }

                        // These replace the ones initially found.
                    investmentVO = (InvestmentVO[]) productInvestments.toArray(new InvestmentVO[productInvestments.size()]);
                }
            }
            }
            else
            {
                investmentVO = DAOFactory.getInvestmentDAO().findBySegmentPKAndInvestmentAllocationOverrideStatus(segmentPK, "P", true, voExclusionList);
                if (investmentVO != null)
                {
                    for (int i = 0; i < investmentVO.length; i++)
                    {
                        InvestmentAllocationVO[] investmentAllocationVOs = investmentVO[i].getInvestmentAllocationVO();

                        investmentVO[i].removeAllInvestmentAllocationVO();

                        for (int j = 0; j < investmentAllocationVOs.length; j++)
                        {
                            if (investmentAllocationVOs[j].getOverrideStatus().equalsIgnoreCase("P"))
                            {
                                investmentVO[i].addInvestmentAllocationVO(investmentAllocationVOs[j]);
                                break;
                            }
                        }
                    }
                }
            }

            if (investmentVO != null)
            {
                for (int i = 0; i < investmentVO.length; i++)
                {
                    long[] bucketAllocationPK = null;

                    List bucketAllocationVOs = null;

                    if (bucketAllocationPK != null) // Get the overrides if there were any.
                    {
                        bucketAllocationVOs = new ArrayList();

                        for (int j = 0; j < bucketAllocationPK.length; j++)
                        {
                            BucketAllocationVO bucketAllocationVO = DAOFactory.getBucketAllocationDAO().findByBucketAllocationPK(bucketAllocationPK[j], false, null)[0];

                            bucketAllocationVOs.add(bucketAllocationVO);
                        }
                    }

                    InvestmentComposer composer = new InvestmentComposer(voInclusionList);

                    if (bucketAllocationVOs != null)
                    {
                        // InvestmentComposer will automatically associate the BucketAllocationOverride with its Bucket.
                        composer.substituteBucketVO((BucketVO[]) bucketAllocationVOs.toArray(new BucketAllocationVO[bucketAllocationVOs.size()]));
                    }

                    composer.substituteInvestmentAllocationVO(investmentVO[i].getInvestmentAllocationVO());

                    composer.compose(investmentVO[i]);

                    for (int j = 0; j < systemFundPKs.size(); j++)
                    {
                        if ((investmentVO[i].getInvestmentPK() + "").equals((String) systemFundPKs.get(j)))
                        {
                            BucketVO[] bucketVOs = investmentVO[i].getBucketVO();

                            if (bucketVOs != null)
                            {
                                investmentVO[i].removeAllBucketVO();
                                for (int k = 0; k < bucketVOs.length; k++)
                                {
                                    if (bucketVOs[k].getBucketSourceCT() != null &&
                                        bucketVOs[k].getBucketSourceCT().equalsIgnoreCase(Bucket.BUCKETSOURCECT_TRANSFER) &&
                                        new EDITBigDecimal(bucketVOs[k].getCumUnits()).isGT("0"))
                                    {
                                        investmentVO[i].addBucketVO(bucketVOs[k]);
                                    }
                                }
                            }
                        }
                    }

                    investmentVOs.add(investmentVO[i]);
                }
            }
        }

        if (investmentVOs.size() == 0)
        {
            return null;
        }
        else
        {
            return (InvestmentVO[]) investmentVOs.toArray(new InvestmentVO[investmentVOs.size()]);
        }
    }

    /**
     * Builds all investmentVOs for the given SegmentPK
     * @param investmentPK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public InvestmentVO[] composeInvestmentVOBySegmentPK(long segmentPK, List voInclusionList) throws Exception
    {
        InvestmentVO[] investmentVOs = DAOFactory.getInvestmentDAO().findBySegmentPK(segmentPK, false, new ArrayList());

        if (investmentVOs != null)
        {
            for (int i = 0; i < investmentVOs.length; i++)
            {
                InvestmentComposer composer = new InvestmentComposer(voInclusionList);

                composer.compose(investmentVOs[i]);
            }
        }

        return investmentVOs;
    }

    /**
     * Builds an investmentVO using the given investmentPK
     * @param investmentPK
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public InvestmentVO composeInvestmentVOByPK(long investmentPK, List voInclusionList) throws Exception
    {
        InvestmentVO[] investmentVO = DAOFactory.getInvestmentDAO().findByInvestmentPK(investmentPK, false, null);

        if (investmentVO != null)
        {
            InvestmentComposer composer = new InvestmentComposer(voInclusionList);

            composer.compose(investmentVO[0]);

            return investmentVO[0];
        }
        else
        {
            return null;
        }
    }

    /**
     * Builds a SegmentVO with PayoutVO, and NoteReminderVO if specified.
     * @param segmentPK
     * @param voInclusionList
     * @return
     */
    public SegmentVO composeSegmentVO(long segmentPK, List voInclusionList)
    {
        SegmentComposer composer = new SegmentComposer(voInclusionList);

        SegmentVO segmentVO = composer.compose(segmentPK);

        return segmentVO;
    }

    public SegmentVO composeSegmentVO(String contractNumber, List voInclusionList)
    {
        SegmentVO[] segmentVO = DAOFactory.getSegmentDAO().findSegmentByContractNumber(contractNumber, false, null);
        SegmentVO returnSegment = null;

        if (segmentVO != null)
        {
            SegmentComposer composer = new SegmentComposer(voInclusionList);

            composer.compose(segmentVO[0]);

            returnSegment = segmentVO[0];
        }

        return returnSegment;
    }

    public SegmentVO[] composeSegmentVOs(String contractNumber, List voInclusionList)
    {
        SegmentVO[] segmentVO = DAOFactory.getSegmentDAO().findSegmentsByContractNumber(contractNumber, false, null);

        if (segmentVO != null)
        {
            for (int i = 0; i < segmentVO.length; i++)
            {
                SegmentComposer composer = new SegmentComposer(voInclusionList);

                composer.compose(segmentVO[i]);
            }
        }

        return segmentVO;
    }

    public SegmentVO[] composeSegmentVOByFund(long filteredFundFK, List voInclusionList)
    {
        SegmentVO[] segmentVO = DAOFactory.getSegmentDAO().findSegmentByFund(filteredFundFK);

        if (segmentVO != null)
        {
            for (int i = 0; i < segmentVO.length; i++)
            {
                SegmentComposer composer = new SegmentComposer(voInclusionList);

                composer.compose(segmentVO[i]);
            }
        }

        return segmentVO;
    }
//    private long[] findBucketAllocationOverridePKForInvestment(long investmentPK)
//    {
//        List bucketAllocationPK = new ArrayList();
//
//        for (int i = 0; i < bucketAllocationOverrideVO.length; i++)
//        {
//            if (investmentPK == bucketAllocationOverrideVO[i].getInvestmentFK())
//            {
//                bucketAllocationPK.add(new Long(bucketAllocationOverrideVO[i].getBucketAllocationFK()));
//            }
//        }
//
//        if (bucketAllocationPK.size() == 0)
//        {
//            return null;
//        }
//        else
//        {
//            return Util.convertLongToPrim((Long[]) bucketAllocationPK.toArray(new Long[bucketAllocationPK.size()]));
//        }
//    }

    public FilteredRequirementVO[] composeFilteredRequirements(long productStructureFK) throws Exception
    {
        FilteredRequirementVO[] filteredRequirementVO = DAOFactory.getFilteredRequirementDAO().findProductStructureAndManualInd(productStructureFK, false, null);

        if (filteredRequirementVO != null)
        {
            List voInclusionList = new ArrayList();
            voInclusionList.add(RequirementVO.class);

            for (int i = 0; i < filteredRequirementVO.length; i++)
            {
                FilteredRequirementComposer filteredRequirementComposer = new FilteredRequirementComposer(voInclusionList);
                filteredRequirementComposer.compose(filteredRequirementVO[i]);
            }
        }
        return filteredRequirementVO;
    }

    public FilteredRequirementVO composeFilteredRequirementVOByPK(long filteredRequirementPK, List voInclusionList) throws Exception
    {
        FilteredRequirementVO filteredRequirementVO = null;
        FilteredRequirementVO[] filteredRequirementVOs = DAOFactory.getFilteredRequirementDAO().findByPK(filteredRequirementPK, false, null);
        if (filteredRequirementVOs != null)
        {
            FilteredRequirementComposer composer = new FilteredRequirementComposer(voInclusionList);
            composer.compose(filteredRequirementVOs[0]);
            filteredRequirementVO = filteredRequirementVOs[0];
        }

        return filteredRequirementVO;
    }

    public RequirementVO[] composeRequirementVOs(List voInclusionList) throws Exception {

        List requirementVOs = new ArrayList();

        RequirementVO[] requirementVO = DAOFactory.getRequirementDAO().findAllRequirements(false, null);
        if (requirementVO != null) {

            for (int r = 0; r < requirementVO.length; r++) {

                RequirementComposer composer = new RequirementComposer(voInclusionList);
                composer.compose(requirementVO[r]);
                requirementVOs.add(requirementVO[r]);
            }
        }

        if (requirementVOs.size() == 0) {

            return null;
        }

        else {

            return (RequirementVO[]) requirementVOs.toArray(new RequirementVO[requirementVOs.size()]);
        }
    }

    public RequirementVO composeRequirementVOByPK(long requirementPK, List voInclusionList) throws Exception
    {
        RequirementVO requirementVO = null;
        RequirementVO[] requirementVOs = DAOFactory.getRequirementDAO().findByPK(requirementPK, false, null);
        if (requirementVOs != null)
        {
            RequirementComposer composer = new RequirementComposer(voInclusionList);
            composer.compose(requirementVOs[0]);
            requirementVO = requirementVOs[0];
        }

        return requirementVO;
    }

    public AgentSnapshotVO[] composeAgentSnapshotVOsByPlacedAgentFK(long placedAgentFK,
                                                                     List voInclusionList)
                                                                   throws Exception
    {
        AgentSnapshotVO[] agentSnapshotVO = DAOFactory.getAgentSnapshotDAO().findAgentSnapshotVOsByPlacedAgentFK(placedAgentFK);

        return agentSnapshotVO;
    }

    /**
     * Composes DepositsVOs by SegmentFK and EDITTrxFK
     * @param segmentPK - the primary key for the specified contract
     * @param editTrxPK - the primary key for the specified transaction
     * @return DepositsVO[] - all the deposit records for the specified contract/transaction combination
     * @throws Exception
     */
    public DepositsVO[] composeDepositsBySegmentPKAndEDITTrxPK(long segmentPK, String editTrxPK) throws Exception
    {
        long editTrxFK = 0;
        if (Util.isANumber(editTrxPK) && Long.parseLong(editTrxPK) > 0)
        {
            editTrxFK = Long.parseLong(editTrxPK);
        }

        return DAOFactory.getDepositsDAO().findDepositsBySegmentPKAmtReceivedAndEDITTrxPK(segmentPK, 0, editTrxFK);
    }

    /**
     * Composes DepositsVOs by EDITTrxFK
     * @param editTrxPK - the transaction primary key
     * @return DepositsVO[] - all the deposit records used in creation of the given transaction
     * @throws Exception
     */
    public DepositsVO[] composeDepositsByEDITTrxPK(long editTrxPK) throws Exception
    {
        return DAOFactory.getDepositsDAO().findDepositsByEDITTrxPK(editTrxPK);
    }

    /**
     * Composes DepositsVO by primary key
     * @param depositsPK - the deposit primary key
     * @return DepositsVO - the deposit record for the given deposit key
     * @throws Exception
     */
    public DepositsVO composeDepositsVOByPK(long depositsPK) throws Exception
    {
        DepositsVO depositsVO = null;
        DepositsVO[] depositsVOs = DAOFactory.getDepositsDAO().findByPK(depositsPK);
        if (depositsVOs != null && depositsVOs.length > 0)
        {
            depositsVO = depositsVOs[0];
        }

        return depositsVO;
    }

    /**
     * Composes SegmentVO by segmentStatus (SegmentStatusCT in the array of statuses passes in params)
     * @param segmentStatus
     * @param voInclusionList
     * @return
     * @throws Exception
     */
    public SegmentVO[] composeSegmentVOByStatus(String[] segmentStatus, List voInclusionList) throws Exception
    {
        
        SegmentVO[] segmentVO = DAOFactory.getSegmentDAO().findBySegmentStatus(segmentStatus);

        if (segmentVO != null)
        {
            for (int i = 0; i < segmentVO.length; i++)
            {
                SegmentComposer composer = new SegmentComposer(voInclusionList);

                composer.compose(segmentVO[i]);
            }

            return segmentVO;
        }
        else
        {
            return null;
        }
    }

   /**
    * Composes all SegmentVOs by the given productStructurePKs
    * @param csIds
    * @param voInclusionList
    * @return
    * @throws Exception
    */
    public SegmentVO[] composeSegmentVOByProductStructure(long[] csIds, List voInclusionList) throws Exception
    {
        SegmentVO[] segmentVOs = DAOFactory.getSegmentDAO().findAllByCSId(csIds);

        if (segmentVOs != null)
        {
            for (int i = 0; i < segmentVOs.length; i++)
            {
                SegmentComposer composer = new SegmentComposer(voInclusionList);
                composer.compose(segmentVOs[i]);
            }
        }

        return segmentVOs;
    }
}
