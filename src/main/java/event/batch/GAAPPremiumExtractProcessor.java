/*
 * User: cgleason
 * Date: Jun 10, 2004
 * Time: 12:43:07 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC. All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential. Any use is
 * subject to the license agreement.
 */

package event.batch;

import batch.business.Batch;

import contract.Segment;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.EDITMap;
import edit.common.vo.BucketHistoryVO;
import edit.common.vo.BucketVO;
import edit.common.vo.ClientDetailVO;
import edit.common.vo.ClientRoleVO;
import edit.common.vo.ClientSetupVO;
import edit.common.vo.ProductStructureVO;
import edit.common.vo.ContractClientVO;
import edit.common.vo.ContractSetupVO;
import edit.common.vo.EDITExport;
import edit.common.vo.EDITTrxHistoryVO;
import edit.common.vo.EDITTrxVO;
import edit.common.vo.FilteredFundVO;
import edit.common.vo.FinancialHistoryVO;
import edit.common.vo.FundTypeIDVO;
import edit.common.vo.GAAPDocumentVO;
import edit.common.vo.GAAPReservesDetailVO;
import edit.common.vo.InvestmentAllocationVO;
import edit.common.vo.InvestmentVO;
import edit.common.vo.RateTableVO;
import edit.common.vo.SegmentVO;
import edit.common.vo.TableDefVO;
import edit.common.vo.TableKeysVO;
import edit.common.vo.VOObject;

import edit.services.EditServiceLocator;
import edit.services.config.ServicesConfig;
import edit.services.logging.Logging;

import engine.sp.SPOutput;

import event.dm.composer.EDITTrxComposer;
import event.dm.dao.FastDAO;

import fission.utility.Util;
import fission.utility.XMLUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import logging.LogEvent;

import org.apache.logging.log4j.Logger;

import role.dm.dao.DAOFactory;


public class GAAPPremiumExtractProcessor implements Serializable
{
    private int keyCounter = 0;

    public GAAPPremiumExtractProcessor()
    {
        super();
    }

    /**
     * For the parameters entered, create the GAAP Reserves Extract
     * @param startDate
     * @param endDate
     * @param productStructure
     */
    public void createPremiumReservesExtract(String startDate, String endDate, String productStructure)
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_EQUITY_PREMIUM_RESERVES_EXTRACTS).tagBatchStart(Batch.BATCH_JOB_CREATE_EQUITY_PREMIUM_RESERVES_EXTRACTS, "Premium Reserve Extract");

        ProductStructureVO[] productStructureVOs = getProductStructures(productStructure);

        try
        {
            processRequestForSelectedProductStructures(startDate, endDate, productStructureVOs);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();

            LogEvent logEvent = new LogEvent("GAAP Premium Extract Errored", e);

            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

            logger.error(logEvent);

//            logErrorToDatabase(e);
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_EQUITY_PREMIUM_RESERVES_EXTRACTS).tagBatchStop();
        }
    }

    /**
     * The set of ProductStructureVOs relating to the specified productStrucure(PK). All ProductStructureVOs are returned if
     * the productStructure name is "All".
     * @param productStructure
     * @return
     */
    private ProductStructureVO[] getProductStructures(String productStructure)
    {
        ProductStructureVO[] productStructureVOs = null;

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        if (productStructure.equalsIgnoreCase("All"))
        {
            productStructureVOs = engineLookup.getAllProductStructures();
        }
        else
        {
            productStructureVOs = engineLookup.findProductStructureVOByPK(Long.parseLong(productStructure), false, null);
        }

        return productStructureVOs;
    }

    /**
     * For the parameters entered, get the contracts that satisfy the request.  Then by contract key get all qualifying
     * transactions.  Only one extract per contract will be created, the transactions will be summerized.
     * @param startDate
     * @param endDate
     * @param productStructureVOs
     * @throws Exception
     */
    private void processRequestForSelectedProductStructures(String startDate, String endDate, ProductStructureVO[] productStructureVOs) throws Exception
    {
        // 1. Get segmentPKs for the input parameters
        FastDAO fastDAO = new FastDAO();

        long[] segmentPKs = null;

        if (productStructureVOs.length == 1)
        {
            segmentPKs = fastDAO.getUniqueSegmentPKsByDateRangeAndProductStructure(startDate, endDate, productStructureVOs[0].getProductStructurePK());
        }
        else
        {
            segmentPKs = fastDAO.getUniqueSegmentPKsByDateRange(startDate, endDate);
        }

        //initiate the output file
        File exportFile = getExportFile();
        insertStartGAAP(exportFile);

        if (segmentPKs != null)
        {
            for (int i = 0; i < segmentPKs.length; i++)
            {
                //Get all Premium, Withdrawal, Full Surrender and Not Taken edit trx records in history with status of "N" or "R"
                //  within the dates selected and for the contract segmentPK
                long[] editTrxPKs = fastDAO.findPremTrxByDateRangeSegmentPK(startDate ,endDate, segmentPKs[i]);

                if (editTrxPKs != null)
                {
                    try
                    {
                        for (int j = 0; j < editTrxPKs.length; j++)
                        {
                            processEachContract(editTrxPKs[j], productStructureVOs, endDate, exportFile);

                            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_EQUITY_PREMIUM_RESERVES_EXTRACTS).updateSuccess();
                        }
                    }
                    catch (Throwable e)
                    {
                        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_EQUITY_PREMIUM_RESERVES_EXTRACTS).updateFailure();

                        System.out.println(e);

                        e.printStackTrace();

                        logErrorToDatabase(e, segmentPKs[i]);
                    }
                }
            }
        }

        insertEndGAAP(exportFile);
    }

    private void logErrorToDatabase(Throwable e, long segmentPK)
    {
        Segment segment = Segment.findByPK(new Long(segmentPK));

        EDITMap columnInfo = new EDITMap("ProcessDate", new EDITDate().getFormattedDate());
        columnInfo.put("ContractNumber", segment.getContractNumber());

        logging.Log.logToDatabase(logging.Log.PREMIUM_EXTRACT_RESERVES_FILE, "GAAP Premium Extract Errored: " + e.getMessage(), columnInfo);
    }

    /**
     * For each contract get clients, rates from PRASE and partially build the extract. Each edit trx key will be used to
     * compose an editTrxVO with its specifed parents and children.
     * @param editTrxPK
     * @param productStructureVOs
     * @param endDate
     * @return
     * @throws Exception
     */
    private GAAPReservesDetailVO processEachContract(long editTrxPK, ProductStructureVO[] productStructureVOs,
                                                     String endDate, File exportFile) throws Exception
    {
        GAAPReservesDetailVO gaapReservesDetailVO = null;
        GAAPDocumentVO gaapDocumentVO = null;
        HashMap filteredFunds = new HashMap();

        EDITTrxVO editTrxVO = composeEDITTrxVO(editTrxPK);

        SegmentVO segmentVO = (SegmentVO)editTrxVO.getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class).getParentVO(SegmentVO.class);

        gaapReservesDetailVO = new GAAPReservesDetailVO();
        gaapReservesDetailVO.setGAAPReservesDetailPK(keyCounter);

        getClientsAndRoles(segmentVO, gaapReservesDetailVO);

        gaapDocumentVO = buildInitialGAAPDocumentVO(editTrxVO, productStructureVOs, gaapReservesDetailVO, endDate);

        filteredFunds = getFilteredFunds(segmentVO, gaapDocumentVO);

        gaapDocumentVO = executeGAAPDocumentVO(gaapDocumentVO);

        setupGAAPReservesContractFields(gaapReservesDetailVO, gaapDocumentVO, segmentVO);

        processDollarsForAllTansactions(editTrxVO, gaapReservesDetailVO, segmentVO, gaapDocumentVO, filteredFunds, exportFile);

        return gaapReservesDetailVO;
    }

    private GAAPDocumentVO executeGAAPDocumentVO(GAAPDocumentVO gaapDocumentVO) throws Exception
    {
        engine.business.Calculator calculator = new engine.component.CalculatorComponent();

        SPOutput spOutput = calculator.processScript("GAAPDocumentVO", gaapDocumentVO, "GAAPReserves","*", "*", gaapDocumentVO.getPolicyEffectiveDate(),
                                                        gaapDocumentVO.getProductStructureVO().getProductStructurePK(), true);

        VOObject[] voObject = spOutput.getSPOutputVO().getVOObject();

        FundTypeIDVO[] fundTypeIDVOs = gaapDocumentVO.getFundTypeIDVO();

        for (int i = 0; i < voObject.length; i++)
        {
            FundTypeIDVO updatedFundTypeIDVO = (FundTypeIDVO)voObject[i];
            fundTypeIDVOs[i].setCurrentInterestRate(updatedFundTypeIDVO.getCurrentInterestRate());
            if (updatedFundTypeIDVO.getCurrentInterestRateDuration() == null)
            {
                fundTypeIDVOs[i].setCurrentInterestRateDuration("0");
            }
            else
            {
                fundTypeIDVOs[i].setCurrentInterestRateDuration(updatedFundTypeIDVO.getCurrentInterestRateDuration());
            }
            fundTypeIDVOs[i].setGuaranteedMinRate(updatedFundTypeIDVO.getGuaranteedMinRate());
            fundTypeIDVOs[i].setIndexMinCapRate(updatedFundTypeIDVO.getIndexMinCapRate());
            fundTypeIDVOs[i].setIndexMarginRate(updatedFundTypeIDVO.getIndexMarginRate());
            fundTypeIDVOs[i].setIndexParticipationRate(updatedFundTypeIDVO.getIndexParticipationRate());
        }

        return gaapDocumentVO;
    }

    /**
     * The editTrxVO is built according to the define list.
     * @param editTrxPK
     * @return EDITTrxVO
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
        voInclusionList.add(ContractClientVO.class);
        voInclusionList.add(InvestmentVO.class);
        voInclusionList.add(InvestmentAllocationVO.class);
        voInclusionList.add(BucketVO.class);
        voInclusionList.add(ClientDetailVO.class);

        EDITTrxComposer editTrxComposer = new EDITTrxComposer(voInclusionList);
        EDITTrxVO editTrxVO = editTrxComposer.compose(editTrxPK);

        return editTrxVO;
    }

    /**
     * For the contract clients defined on the contract get their defined roles.  If the role is the owner or joint owner
     * get the client detail for the gender.  Issueage of the owner and joint owner are captured into the extract record
     * along with the genders.
     * @param segmentVO
     * @param gaapReservesDetailVO
     * @throws Exception
     */
    private void getClientsAndRoles(SegmentVO segmentVO, GAAPReservesDetailVO gaapReservesDetailVO) throws Exception
     {
         ContractClientVO[] contractClientVOs = segmentVO.getContractClientVO();
         String roleTypeCT = null;
         EDITDate currentDate = new EDITDate();

         for (int i = 0; i < contractClientVOs.length; i++)
         {
             // eliminate using terminated contractClients
             int compareValue = contractClientVOs[i].getTerminationDate().compareTo(currentDate.getFormattedDate());

             if (compareValue >= 0)
             {
                 ClientRoleVO[] clientRoleVO = DAOFactory.getClientRoleDAO().findByClientRolePK(contractClientVOs[i].getClientRoleFK(), false, new ArrayList());
                 ClientDetailVO[] clientDetailVO = null;

                 if (clientRoleVO != null)
                 {
                     roleTypeCT = clientRoleVO[0].getRoleTypeCT();

                     if (roleTypeCT.equalsIgnoreCase("OWN") || roleTypeCT.equalsIgnoreCase("SOW"))
                     {
                         int issueAge = contractClientVOs[i].getIssueAge();
                         long clientDetailPK = clientRoleVO[0].getClientDetailFK();
                         clientDetailVO = client.dm.dao.DAOFactory.getClientDetailDAO().findByClientPK(clientDetailPK, false, new ArrayList());

                         if (clientDetailVO != null)
                         {
                            String gender = clientDetailVO[0].getGenderCT();
                            if (roleTypeCT.equalsIgnoreCase("OWN"))
                            {
                                gaapReservesDetailVO.setOwnerGender(gender);
                                gaapReservesDetailVO.setOwnerIssueAge(issueAge);
                            }
                            else
                            {
                                gaapReservesDetailVO.setJointOwnerGender(gender);
                                gaapReservesDetailVO.setJointOwnerIssueAge(issueAge);
                            }
                         }
                     }
                 }
             }
         }
     }

    /**
     * Populate the GAAPDocumentVO for PRASE processing.  PRASE will update this VO with rates.  The rates will be used
     * to populate the extract record.
     * @param editTrxVO
     * @param productStructureVOs
     * @param gaapReservesDetailVO
     * @param endDate
     * @return GAAPDocumentVO
     * @throws Exception
     */
    private GAAPDocumentVO buildInitialGAAPDocumentVO(EDITTrxVO editTrxVO, ProductStructureVO[] productStructureVOs,
                                                      GAAPReservesDetailVO gaapReservesDetailVO, String endDate)  throws Exception
    {
        SegmentVO segmentVO = (SegmentVO)editTrxVO.getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class).getParentVO(SegmentVO.class);
        String policyEffDate = segmentVO.getEffectiveDate();
        ProductStructureVO productStructureVO = null;

        for (int i = 0; i < productStructureVOs.length; i++)
        {
            if (segmentVO.getProductStructureFK() == productStructureVOs[i].getProductStructurePK())
            {
                productStructureVO = productStructureVOs[i];
                break;
            }
        }

        GAAPDocumentVO gaapDocumentVO = new GAAPDocumentVO();
        gaapDocumentVO.setGAAPDocumentPK(1);
        gaapDocumentVO.setProductStructureVO(productStructureVO);
        gaapDocumentVO.setPolicyEffectiveDate(policyEffDate);
        gaapDocumentVO.setTrxEffectiveDate(endDate);
        gaapDocumentVO.setOwnerIssueAge(gaapReservesDetailVO.getOwnerIssueAge());
        gaapDocumentVO.setOwnerGender(gaapReservesDetailVO.getOwnerGender());
        gaapDocumentVO.setIssueState(segmentVO.getIssueStateCT());
        gaapReservesDetailVO.setMarketingPackageName(productStructureVO.getMarketingPackageName());

        return gaapDocumentVO;
    }

    /**
     * For each investment on the contract, get its corresponding filtered fund record and create a FundTypeIDVO for the
     * GAAPDocuemntVO.
     * @param segmentVO
     * @param gaapDocumentVO
     * @return
     * @throws Exception
     */
    private HashMap getFilteredFunds(SegmentVO segmentVO, GAAPDocumentVO gaapDocumentVO)  throws Exception
    {
        InvestmentVO[] investmentVOs = segmentVO.getInvestmentVO();
        HashMap filteredFunds = new HashMap();
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();
        FundTypeIDVO fundTypeIdVO = null;
        List fundTypeIdVOs = new ArrayList();
        long averagingFilteredFundId = 0;
        long pointTOPointFilteredFundId = 0;
        long otherFilteredFundId = 0;

        for (int i = 0; i < investmentVOs.length; i++)
        {
            long filteredFundPK = investmentVOs[i].getFilteredFundFK();
            FilteredFundVO[] filteredFundVO = engineLookup.findFilteredFundByPK(filteredFundPK);
            if (filteredFundVO != null)
            {
                fundTypeIdVO = new FundTypeIDVO();
                String method = filteredFundVO[0].getIndexingMethodCT();
                fundTypeIdVO.setFilteredFundId(filteredFundPK);
                fundTypeIdVO.setFundTypeIDPK(i);

                if (method == null && otherFilteredFundId == 0)
                {
                    otherFilteredFundId = filteredFundPK;
                    fundTypeIdVO.setIndexingMethod("Other");
                    gaapDocumentVO.addFundTypeIDVO(fundTypeIdVO);
                    fundTypeIdVOs.add(fundTypeIdVO);
                }
                else if (method.equalsIgnoreCase("PointToPoint") && pointTOPointFilteredFundId == 0)
                {
                    pointTOPointFilteredFundId = filteredFundPK;
                    fundTypeIdVO.setIndexingMethod(method);
                    gaapDocumentVO.addFundTypeIDVO(fundTypeIdVO);
                    fundTypeIdVOs.add(fundTypeIdVO);
                }
                else if(method.equalsIgnoreCase("Averaging") && averagingFilteredFundId == 0)
                {
                    averagingFilteredFundId = filteredFundPK;
                    fundTypeIdVO.setIndexingMethod(method);
                    fundTypeIdVOs.add(fundTypeIdVO);
                    gaapDocumentVO.addFundTypeIDVO(fundTypeIdVO);
                }

                filteredFunds.put(filteredFundPK + "", filteredFundVO[0]);
            }
        }

        return filteredFunds;
    }
    /**
     * Populate the extract record with contract data and the rates from PRASE
     * @param gaapReservesDetailVO
     * @param gaapDocumentVO
     * @param segmentVO
     */
    private void setupGAAPReservesContractFields(GAAPReservesDetailVO gaapReservesDetailVO, GAAPDocumentVO gaapDocumentVO, SegmentVO segmentVO)
    {
         gaapReservesDetailVO.setPolicyNumber(segmentVO.getContractNumber());
         gaapReservesDetailVO.setPolicyEffectiveDate(segmentVO.getEffectiveDate());
         gaapReservesDetailVO.setBusinessContract(gaapDocumentVO.getProductStructureVO().getBusinessContractName());
         gaapReservesDetailVO.setSegmentStatus(segmentVO.getSegmentStatusCT());
    }

    /**
     * For the qualifying transactions, accumulate bucket history dollars.  Different activity must take place based on
     * trx type and status.
     * @param editTrxVO
     * @param gaapReservesDetailVO
     * @param segmentVO
     * @param gaapDocumentVO
     * @throws Exception
     */
    private void processDollarsForAllTansactions(EDITTrxVO editTrxVO,  GAAPReservesDetailVO gaapReservesDetailVO,
                                                 SegmentVO segmentVO, GAAPDocumentVO gaapDocumentVO,
                                                 HashMap filteredFunds, File exportFile) throws Exception
    {
        BucketHistoryVO[] bucketHistoryVOs = editTrxVO.getEDITTrxHistoryVO(0).getBucketHistoryVO();
//        gaapReservesDetailVO.setProcessDate(editTrxVO.getEDITTrxHistoryVO(0).getProcessDate());
        EDITDate processDate =  new EDITDateTime(editTrxVO.getEDITTrxHistoryVO(0).getProcessDateTime()).getEDITDate();
        gaapReservesDetailVO.setProcessDate(processDate.getFormattedDate());
        gaapReservesDetailVO.setTransactionType(editTrxVO.getTransactionTypeCT());
        gaapReservesDetailVO.setPremiumIndicator(editTrxVO.getStatus());

        for (int i = 0; i < bucketHistoryVOs.length; i++)
        {
            processBucketHistory(bucketHistoryVOs[i], segmentVO, gaapReservesDetailVO, gaapDocumentVO, filteredFunds, exportFile);
        }
    }

    /**
     * The dollars on the bucket history are to be accumulated by Index method type, defined by the filtered fund. Anything
     * that is not index method of Averaging or PointToPoint will be accumulated into the Fixed accumulator.  The bucket
     * on the contract must be matched to the bucket history, bucketFK in order to find the filtered fund.
     * @param bucketHistoryVO
     * @param segmentVO
     * @param gaapReservesDetailVO
     * @param gaapDocumentVO
     * @param filteredFunds
     * @param exportFile
     * @throws Exception
     */
    private void processBucketHistory(BucketHistoryVO bucketHistoryVO, SegmentVO segmentVO,
                                      GAAPReservesDetailVO gaapReservesDetailVO, GAAPDocumentVO gaapDocumentVO,
                                      HashMap filteredFunds, File exportFile) throws Exception
    {
        FundTypeIDVO[] fundTypeIDVO = gaapDocumentVO.getFundTypeIDVO();
        InvestmentVO[] investmentVOs = segmentVO.getInvestmentVO();
        BucketVO[] bucketVO = null;
        long filteredFundPK = 0;

        for (int i = 0; i < investmentVOs.length; i++)
        {
            bucketVO = investmentVOs[i].getBucketVO();
            // double dollars = bucketHistoryVO.getDollars();
            // commented above line(s) for double to BigDecimal conversion
            // sprasad 9/29/2004
            EDITBigDecimal dollars = new EDITBigDecimal( bucketHistoryVO.getDollars() );
            if (bucketVO != null && bucketVO.length > 0)
            {
                if (bucketVO[0].getBucketPK() == bucketHistoryVO.getBucketFK())
                {
                    filteredFundPK = investmentVOs[i].getFilteredFundFK();
                    FilteredFundVO filteredFundVO = lookupInHashMap(filteredFundPK, filteredFunds);
                    String fundNumber = filteredFundVO.getFundNumber();

                    for (int j = 0; j < fundTypeIDVO.length; j++)
                    {
                        if (fundTypeIDVO[j].getFilteredFundId() == filteredFundPK)
                        {
                            gaapReservesDetailVO.setCurrentInterestRate(fundTypeIDVO[j].getCurrentInterestRate());
                            if (fundTypeIDVO[j].getCurrentInterestRateDuration() == null)
                            {
                                gaapReservesDetailVO.setCurrentInterestRateDuration("0");
                            }
                            else
                            {
                                gaapReservesDetailVO.setCurrentInterestRateDuration(fundTypeIDVO[j].getCurrentInterestRateDuration());
                            }
                            gaapReservesDetailVO.setGuaranteedMinRate(fundTypeIDVO[j].getGuaranteedMinRate());
                            // gaapReservesDetailVO.setGuaranteedMinRateDuration(fundTypeIDVO[j].getGuaranteedMinRateDuration());
                            // commented above line(s) for double to BigDecimal conversion
                            // sprasad 9/29/2004
                            EDITBigDecimal guaranteedMinRateDuration = new EDITBigDecimal(fundTypeIDVO[j].getGuaranteedMinRateDuration() + "");
                            gaapReservesDetailVO.setGuaranteedMinRateDuration(guaranteedMinRateDuration.getBigDecimal());
                            gaapReservesDetailVO.setIndexMinCapRate(fundTypeIDVO[j].getIndexMinCapRate());
                            gaapReservesDetailVO.setIndexMarginRate(fundTypeIDVO[j].getIndexMarginRate());
                            gaapReservesDetailVO.setIndexParticipationRate(fundTypeIDVO[j].getIndexParticipationRate());
                            break;
                        }
                    }

                    setRatesDuration(bucketVO[0], gaapDocumentVO, gaapReservesDetailVO);

                    gaapReservesDetailVO.setGAAPReservesDetailPK(keyCounter);
                    // gaapReservesDetailVO.setBucketAllocation(dollars);
                    // commented above line(s) for double to BigDecimal conversion
                    // sprasad 9/29/2004
                    gaapReservesDetailVO.setBucketAllocation( dollars.getBigDecimal() );
                    gaapReservesDetailVO.setFundNumber(fundNumber);

                    exportGAAP((GAAPReservesDetailVO) gaapReservesDetailVO, exportFile);

                    keyCounter++;
                }
            }
        }
    }

    private FilteredFundVO lookupInHashMap(long filteredFundPK, HashMap filteredFunds)
    {
        FilteredFundVO filteredFundVO = null;
        String key = filteredFundPK + "";
        if (filteredFunds.containsKey(key))
        {
            filteredFundVO = (FilteredFundVO)filteredFunds.get(key);
        }
        return filteredFundVO;
    }

    /**
     * If the rate info has updated the extract record, it shouldn't do it again.  The data from PRASE will be used to
     * populate these fields, along with bucket rates.
     * @param bucketVO
     * @param gaapDocumentVO
     * @param gaapReservesDetailVO
     */
    private void setRatesDuration(BucketVO bucketVO, GAAPDocumentVO gaapDocumentVO, GAAPReservesDetailVO gaapReservesDetailVO) throws Exception
    {
        gaapReservesDetailVO.setIndexCapRate(bucketVO.getIndexCapRate());

        // if (gaapReservesDetailVO.getCurrentInterestRate() == 0)
        // commented above line(s) for double to BigDecimal conversion
        // sprasad 9/29/2004
        if (new EDITBigDecimal( gaapReservesDetailVO.getCurrentInterestRate() ).isEQ("0") )
        {
            gaapReservesDetailVO.setCurrentInterestRate(bucketVO.getBucketInterestRate());
        }

//        String currentInterestRateDuration = gaapReservesDetailVO.getCurrentInterestRateDuration();
//        if (currentInterestRateDuration == null)
//        {
//            gaapReservesDetailVO.setCurrentInterestRateDuration(gaapDocumentVO.getCurrentInterestRateDuration());
//        }
//
//        if (gaapReservesDetailVO.getGuaranteedMinRate() == 0)
//        {
//            gaapReservesDetailVO.setGuaranteedMinRate(gaapDocumentVO.getGuaranteedMinRate());
//        }

        // if (gaapReservesDetailVO.getGuaranteedMinRateDuration() == 0)
        // commented above line(s) for double to BigDecimal conversion
        // sprasad 9/29/2004
        if (new EDITBigDecimal( gaapReservesDetailVO.getGuaranteedMinRateDuration() ).isEQ("0") )
        {
            int duration = findGuarMinDuration(gaapDocumentVO);
            // gaapReservesDetailVO.setGuaranteedMinRateDuration(duration);
            // commented above line(s) for double to BigDecimal conversion
            // sprasad 9/29/2004
            gaapReservesDetailVO.setGuaranteedMinRateDuration( new EDITBigDecimal(duration + "").getBigDecimal() );
        }
    }

    /**
     * Using the gaapDocumentVO data and defined constants, get the duration on the Rate Table for the Guaranteed
     * Interest table.  First find the TableDefPK, then the tableKey record for a specific state or not, and then the
     * Rate Table record attached to the TableKeys record for the IssueAge.
     * @param gaapDocumentVO
     * @return
     * @throws Exception
     */
    public int findGuarMinDuration(GAAPDocumentVO gaapDocumentVO)  throws Exception
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        String notApplicable = "NotApplicable";
        String tableName = "GuaranteedInterest";

        //get the tabledef key
        TableDefVO[] tableDefVO = engineLookup.getTableDefByName(tableName);
        if (tableDefVO == null)
        {
            return 0;
        }
        else
        {
            //get the table entry for the issue state
            TableKeysVO[] tableKeysVO = engineLookup.findTableKeysByAllColumns(tableDefVO[0].getTableDefPK(),
                                                                                 gaapDocumentVO.getTrxEffectiveDate(),
                                                                                 "0", "0", "-", notApplicable, notApplicable,
                                                                                 gaapDocumentVO.getIssueState(), notApplicable);

            if (tableKeysVO == null)
            {
                tableKeysVO = engineLookup.findTableKeysByAllColumns(tableDefVO[0].getTableDefPK(),
                                                                       gaapDocumentVO.getTrxEffectiveDate(),
                                                                        "0", "0", "-", notApplicable, notApplicable,
                                                                       notApplicable, notApplicable);

                //get the table entry for state N/A
                if (tableKeysVO == null)
                {
                    return 0;
                }
                else
                {
                    RateTableVO[] rateTableVO = engineLookup.findIssueRates(tableKeysVO[0].getTableKeysPK(), gaapDocumentVO.getOwnerIssueAge());

                    if (rateTableVO == null)
                    {
                        return 0;
                    }
                    else
                        return rateTableVO[0].getDuration();
                }
            }
        }

        return 0;
    }

    /**
     * Set up the export file
     * @return  File - define name
     */
    private File getExportFile()
    {
        EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");

        File exportFile = new File(export1.getDirectory() + "SEGGAAP_" + System.currentTimeMillis() + ".xml");

        return exportFile;
    }

    /**
     * Write extract record to the export file
     * @param gaapReservesDetailVO
     * @param exportFile
     * @throws Exception
     */
    private void exportGAAP(GAAPReservesDetailVO gaapReservesDetailVO, File exportFile) throws Exception
    {
        String parsedXML = roundDollarFields(gaapReservesDetailVO);

        parsedXML = XMLUtil.parseOutXMLDeclaration(parsedXML);      

        appendToFile(exportFile, parsedXML);
    }

    /**
     * Each new extract record gets appended to the file.
     * @param exportFile
     * @param data
     * @throws Exception
     */
    private void appendToFile(File exportFile, String data) throws Exception
    {
        BufferedWriter bw = new BufferedWriter(new FileWriter(exportFile, true));

        bw.write(data);

        bw.flush();

        bw.close();
    }

    /**
     * Set up the initial identify for the export file.
     * @param exportFile
     * @throws Exception
     */
    private void insertStartGAAP(File exportFile) throws Exception
    {
        appendToFile(exportFile, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        appendToFile(exportFile, "<GAAPReservesVO>\n");
    }

    /**
     * Ending reocrding for the xml document, export file.
     * @param exportFile
     * @throws Exception
     */
    private void insertEndGAAP(File exportFile) throws Exception
    {
        appendToFile(exportFile, "\n</GAAPReservesVO>");
    }

    /**
     * Round the dollars fields for the those specified.
     * @param gaapReservesDetailVO
     * @return
     * @throws Exception
     */
    private String roundDollarFields(GAAPReservesDetailVO gaapReservesDetailVO) throws Exception
    {
        String[] fieldNames = setupFieldNamesForRounding();

        String voToXML = Util.roundDollarTextFields(gaapReservesDetailVO, fieldNames);

        return voToXML;
    }

    /**
     * List field names for rounding
     * @return
     */
    private String[] setupFieldNamesForRounding()
    {
        List fieldNames = new ArrayList();

        fieldNames.add("GAAPReservesDetailVO.BucketAllocation");

        return (String[]) fieldNames.toArray(new String[fieldNames.size()]);
    }
}
