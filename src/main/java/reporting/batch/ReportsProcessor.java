/*
 * User: dlataill
 * Date: Mar 29, 2002
 * Time: 8:09:38 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package reporting.batch;

import batch.business.Batch;

import billing.BillSchedule;

import client.ClientDetail;

import client.dm.dao.DAOFactory;

import contract.ContractClient;
import contract.Segment;

import contract.interfaces.PayoutTrxRptInterfaceCmd;

import edit.common.CodeTableWrapper;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.EDITMap;
import edit.common.vo.BucketChargeHistoryVO;
import edit.common.vo.BucketHistoryVO;
import edit.common.vo.BucketVO;
import edit.common.vo.ClientAddressVO;
import edit.common.vo.ClientDetailVO;
import edit.common.vo.ClientRoleVO;
import edit.common.vo.CodeTableVO;
import edit.common.vo.ContractClientVO;
import edit.common.vo.ControlBalanceVO;
import edit.common.vo.EDITExport;
import edit.common.vo.EDITServicesConfig;
import edit.common.vo.EDITTrxHistoryVO;
import edit.common.vo.EDITTrxVO;
import edit.common.vo.FeeVO;
import edit.common.vo.FilteredFundVO;
import edit.common.vo.FundVO;
import edit.common.vo.InvestmentHistoryVO;
import edit.common.vo.InvestmentInformationVO;
import edit.common.vo.InvestmentVO;
import edit.common.vo.ProductFilteredFundStructureVO;
import edit.common.vo.ProductStructureVO;
import edit.common.vo.QuoteInvestmentVO;
import edit.common.vo.QuoteVO;
import edit.common.vo.SegmentVO;
import edit.common.vo.SeparateAccountValueDetailsVO;
import edit.common.vo.SeparateAccountValuesReportVO;
import edit.common.vo.SeparateAcctValByCaseVO;
import edit.common.vo.SeparateAcctValDetailByCaseVO;
import edit.common.vo.TaxFormVO;
import edit.common.vo.UnitValuesVO;
import edit.common.vo.VOObject;
import edit.common.vo.YearEndTaxVO;

import edit.portal.common.session.UserSession;

import edit.services.EditServiceLocator;
import edit.services.config.ServicesConfig;
import edit.services.logging.Logging;

import engine.ChargeCode;
import engine.Company;
import engine.Fee;
import engine.ProductFilteredFundStructure;
import engine.ProductStructure;

import engine.util.TransformChargeCodes;

import event.EDITTrx;

import fission.utility.Util;
import fission.utility.XMLUtil;

import group.ContractGroup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import logging.LogEvent;

import org.apache.logging.log4j.Logger;

import reporting.business.Reporting;

import reporting.interfaces.AccountingExtractInterfaceCmd;
import reporting.interfaces.ValuationInterfaceCmd;

import role.ClientRole;

import event.EDITTrxHistory;
import logging.Log;
import staging.Datawarehouse;
import staging.RequirementStaging;
import staging.Worksheet;


public class ReportsProcessor
{
    private Reporting reporting = null;
    private CodeTableWrapper codeTableWrapper;

    public ReportsProcessor()
    {
    }

    public YearEndTaxVO[] processTaxes(SegmentVO[] segmentVOs, String fromDate, String toDate, String taxYear, ProductStructureVO[] productStructureVOs) throws Exception
    {
        codeTableWrapper = CodeTableWrapper.getSingleton();

        Map clientCache = new HashMap();
        Map productCache = new HashMap();

        ClientDetailVO[] clientDetailVOs = DAOFactory.getClientDetailDAO().findAll();

        for (int c = 0; c < clientDetailVOs.length; c++)
        {
            String clientId = clientDetailVOs[c].getClientDetailPK() + "";
            clientCache.put(clientId, clientDetailVOs[c]);
        }

        for (int d = 0; d < productStructureVOs.length; d++)
        {
            String productStructureId = productStructureVOs[d].getProductStructurePK() + "";
            productCache.put(productStructureId, productStructureVOs[d]);
        }

        String clientId = null;

        YearEndTaxVO[] yeTaxVOs = new YearEndTaxVO[segmentVOs.length];

        CodeTableVO[] relationshipCTVO = codeTableWrapper.getCodeTableEntries("LIFERELATIONTYPE");
        CodeTableVO[] taxFilingStatusCTVO = codeTableWrapper.getCodeTableEntries("TAXFILINGSTATUS");
        CodeTableVO[] transactionTypeCTVO = codeTableWrapper.getCodeTableEntries("TRXTYPE");
        CodeTableVO[] stateCTVO = codeTableWrapper.getCodeTableEntries("STATE");
        CodeTableVO[] countryCTVO = codeTableWrapper.getCodeTableEntries("COUNTRY");

        for (int i = 0; i < segmentVOs.length; i++)
        {
            Map ten99R = new HashMap();
            Map ten99I = new HashMap();
            Map ten42S = new HashMap();
            Map fifty498 = new HashMap();

            ContractClientVO[] contractClientVOs = segmentVOs[i].getContractClientVO();

            String relationship = "";
            EDITDate currentDate = new EDITDate();

            for (int a = 0; a < contractClientVOs.length; a++)
            {
                // eliminate using terminated contractClients
                int compareValue = contractClientVOs[a].getTerminationDate().compareTo(currentDate.getFormattedDate());

                if (compareValue >= 0)
                {
                    long clientRoleFK = contractClientVOs[a].getClientRoleFK();

                    ClientRoleVO[] clientRoleVO = role.dm.dao.DAOFactory.getClientRoleDAO().findByClientRolePK(clientRoleFK, false, new ArrayList());

                    if ((clientRoleVO != null) && (clientRoleVO.length > 0))
                    {
                        relationship = clientRoleVO[0].getRoleTypeCT();

                        if (relationship.equalsIgnoreCase("OWN"))
                        {
                            clientId = clientRoleVO[0].getClientDetailFK() + "";

                            break;
                        }
                    }
                }
            }

            ClientDetailVO clientDetails = (ClientDetailVO) clientCache.get(clientId);
            ClientAddressVO clientAddress = clientDetails.getClientAddressVO(0);

            ProductStructureVO productStructureVO = ((ProductStructureVO) productCache.get(segmentVOs[i].getProductStructureFK() + ""));
            Company company = Company.findByPK(new Long(productStructureVO.getCompanyFK()));
            String companyName = company.getCompanyName();

            String qualNonQual = segmentVOs[i].getQualNonQualCT();
            String taxQualifier = null;

            if (qualNonQual.equalsIgnoreCase("Y"))
            {
                taxQualifier = "N";
            }

            else
            {
                taxQualifier = "Q";
            }

            int t = 0;

            YearEndTaxVO yeTaxVO = new YearEndTaxVO();
            yeTaxVO.setContractNumber(segmentVOs[i].getContractNumber());
            yeTaxVO.setCompanyName(companyName);
            yeTaxVO.setClientLastName(clientDetails.getLastName());
            yeTaxVO.setClientFirstName(clientDetails.getFirstName());
            yeTaxVO.setClientMiddleName(clientDetails.getMiddleName());
            yeTaxVO.setClientAddressLine1(clientAddress.getAddressLine1());
            yeTaxVO.setClientAddressLine2(clientAddress.getAddressLine2());
            yeTaxVO.setClientAddressLine3(clientAddress.getAddressLine3());
            yeTaxVO.setClientAddressLine4(clientAddress.getAddressLine4());
            yeTaxVO.setCounty(clientAddress.getCounty());
            yeTaxVO.setCity(clientAddress.getCity());
            yeTaxVO.setState(clientAddress.getStateCT());
            yeTaxVO.setCountry(clientAddress.getCountryCT());
            yeTaxVO.setZipCode(clientAddress.getZipCode());

            yeTaxVO.setFromDate(fromDate);
            yeTaxVO.setToDate(toDate);
            yeTaxVO.setTaxYear(Integer.parseInt(taxYear));
            yeTaxVO.setTaxQualifier(taxQualifier);

            //            SegmentActivityHistoryVO[] segmentActivityHistory = segmentVOs[i].getSegmentActivityHistoryVO();
            //
            //            if (segmentActivityHistory != null) {
            //
            //                String transactionType = "";
            //
            //                for (int b = 0; b < segmentActivityHistory.length; b++) {
            //
            //                    if (segmentActivityHistory[b].getTaxYear().equals(taxYear)) {
            //
            //                        String trxTypeCode = segmentActivityHistory[b].getTransactionType();
            //
            //                        for (t = 0; t < transactionTypeCTVO.length; t++) {
            //
            //                            if (transactionTypeCTVO[t].getCode().equalsIgnoreCase(trxTypeCode)) {
            //
            //                                transactionType = transactionTypeCTVO[t].getCodeDesc();
            //                                break;
            //                            }
            //                        }
            //
            //                        if ((transactionType.equalsIgnoreCase("Withdrawal")) ||
            //                            (transactionType.equalsIgnoreCase("FullSurrender")) ||
            //                            (transactionType.equalsIgnoreCase("Death")) ||
            //                            (transactionType.equalsIgnoreCase("Payout")) ||
            //                            (transactionType.equalsIgnoreCase("LumpSum"))) {
            //
            //                            create1099R(segmentActivityHistory[b], ten99R);
            //                        }
            //
            //                        if (transactionType.equalsIgnoreCase("Premium")) {
            //
            //                            create5498(segmentActivityHistory[b], fifty498);
            //                        }
            //                    }
            //                }
            //
            //                if (ten99R != null) {
            //
            //                    Iterator enum = ten99R.keySet().iterator();
            //
            //                    while (enum.hasNext()) {
            //
            //                        String distributionCode = (String) enum.next();
            //
            //                        Map ht = (Map) ten99R.get(distributionCode);
            //
            //                        TaxFormVO taxFormVO = new TaxFormVO();
            //                        taxFormVO.setTaxFormName("1099R");
            //                        taxFormVO.setDistributionCode(distributionCode);
            //                        taxFormVO.setGrossAmount(Double.parseDouble((String) ht.get("GrossAmount")));
            //                        taxFormVO.setTaxableBenefit(Double.parseDouble((String) ht.get("TaxableBenefit")));
            //                        taxFormVO.setFederalWithholding(Double.parseDouble((String) ht.get("FedWithholding")));
            //                        taxFormVO.setStateWithholding(Double.parseDouble((String) ht.get("StateWithholding")));
            //                        taxFormVO.setCountyWithholding(Double.parseDouble((String) ht.get("CountyWithholding")));
            //                        taxFormVO.setCityWithholding(Double.parseDouble((String) ht.get("CityWithholding")));
            //                        taxFormVO.setMarketValue(Double.parseDouble((String) ht.get("MarketValue")));
            //                        taxFormVO.setPremiumType("");
            //
            //                        yeTaxVO.addTaxFormVO(taxFormVO);
            //                    }
            //                }
            //
            //                if (fifty498 != null) {
            //
            //                    Iterator enum = fifty498.keySet().iterator();
            //
            //                    while (enum.hasNext()) {
            //
            //                        String premiumType = (String) enum.next();
            //
            //                        Map ht = (Map) fifty498.get(premiumType + "");
            //
            //                        TaxFormVO taxFormVO = new TaxFormVO();
            //                        taxFormVO.setTaxFormName("5498");
            //                        taxFormVO.setDistributionCode("");
            //                        taxFormVO.setGrossAmount(Double.parseDouble((String) ht.get("GrossAmount")));
            //                        taxFormVO.setTaxableBenefit(Double.parseDouble((String) ht.get("TaxableBenefit")));
            //                        taxFormVO.setFederalWithholding(Double.parseDouble((String) ht.get("FedWithholding")));
            //                        taxFormVO.setStateWithholding(Double.parseDouble((String) ht.get("StateWithholding")));
            //                        taxFormVO.setCountyWithholding(Double.parseDouble((String) ht.get("CountyWithholding")));
            //                        taxFormVO.setCityWithholding(Double.parseDouble((String) ht.get("CityWithholding")));
            //                        taxFormVO.setMarketValue(Double.parseDouble((String) ht.get("MarketValue")));
            //                        taxFormVO.setPremiumType(premiumType);
            //
            //                        yeTaxVO.addTaxFormVO(taxFormVO);
            //                    }
            //                }
            //            }
            TaxFormVO[] forms = yeTaxVO.getTaxFormVO();

            //            if (forms != null) {
            //
            //                System.out.println("---------------------------------------------------------------");
            //                System.out.println("Year End Tax Reporting for Contract: " + yeTaxVO.getContractId());
            //                System.out.println("Tax Responsible Client: " + yeTaxVO.getClientLastName() +
            //                                                                ", " + yeTaxVO.getClientFirstName() +
            //                                                                " " + yeTaxVO.getClientMiddleName());
            //                System.out.println("");
            //                System.out.println("Address: " + yeTaxVO.getClientAddressLine1());
            //                System.out.println(" " + yeTaxVO.getClientAddressLine2());
            //                System.out.println(" " + yeTaxVO.getClientAddressLine3());
            //                System.out.println(" " + yeTaxVO.getClientAddressLine4());
            //                System.out.println(" " + yeTaxVO.getClientCity() + ", "
            //                                   + yeTaxVO.getClientStateId());
            //                System.out.println("");
            //                System.out.println("Tax Filing Status: " + yeTaxVO.getClientFilingStatus());
            //                System.out.println("Tax Qualifier: " + yeTaxVO.getTaxQualifier());
            //                System.out.println("Tax Year: " + yeTaxVO.getTaxYear());
            //                System.out.println("For the Period From: " + yeTaxVO.getFromDate() + " To: "
            //                                                       + yeTaxVO.getToDate());
            //
            //                for (int e = 0; e < forms.length; e++) {
            //
            //                    System.out.println("");
            //                    System.out.println("+++++");
            //                    System.out.println("Form: " + forms[e].getTaxFormName());
            //                    System.out.println("Premium Type: " + forms[e].getPremiumType());
            //                    System.out.println("Distribution Code: " + forms[e].getDistributionCode());
            //                    System.out.println("Gross Amount: " + forms[e].getGrossAmount());
            //                    System.out.println("Taxable Benefit: " + forms[e].getTaxableBenefit());
            //                    System.out.println("Federal Withholding: " + forms[e].getFederalWithholding());
            //                    System.out.println("State Withholding: " + forms[e].getStateWithholding());
            //                    System.out.println("County Withholding: " + forms[e].getCountyWithholding());
            //                    System.out.println("City Withholing: " + forms[e].getCityWithholding());
            //                    System.out.println("Market Value: " + forms[e].getMarketValue());
            //                    System.out.println("+++++");
            //                    System.out.println("");
            //                }
            //            }
            //
            //            else {
            //
            //                System.out.println("---------------------------------------------------------------");
            //                System.out.println("No Year End Tax Reporting for Contract: " + yeTaxVO.getContractId());
            //            }
            yeTaxVOs[i] = yeTaxVO;
        }

        return yeTaxVOs;
    }

    //    private void create1099R(SegmentActivityHistoryVO segmentActivityHistory,
    //                              Map ten99R) {
    //
    //        Map ht = null;
    //
    //        String distributionCode = segmentActivityHistory.getDistributionCode();
    //
    //        double grossAmount = 0;
    //        double taxableBenefit = 0;
    //        double fedWithholding = 0;
    //        double stateWithholding = 0;
    //        double countyWithholding = 0;
    //        double cityWithholding = 0;
    //        double marketValue = 0;
    //
    //        if (ten99R.containsKey(distributionCode)) {
    //
    //            ht = (Map) ten99R.get(distributionCode);
    //
    //            grossAmount = Double.parseDouble((String) ht.get("GrossAmount"));
    //            taxableBenefit = Double.parseDouble((String) ht.get("TaxableBenefit"));
    //            fedWithholding = Double.parseDouble((String) ht.get("FedWithholding"));
    //            stateWithholding = Double.parseDouble((String) ht.get("StateWithholding"));
    //            countyWithholding = Double.parseDouble((String) ht.get("CountyWithholding"));
    //            cityWithholding   = Double.parseDouble((String) ht.get("CityWithholding"));
    //            marketValue       = Double.parseDouble((String) ht.get("MarketValue"));
    //        }
    //
    //        else {
    //
    //            ht = new HashMap();
    //        }
    //
    //        grossAmount += segmentActivityHistory.getGrossAmount();
    //        taxableBenefit += segmentActivityHistory.getTaxableBenefit();
    //        fedWithholding += segmentActivityHistory.getFederalWithholding();
    //        stateWithholding += segmentActivityHistory.getStateWithholding();
    //        countyWithholding += segmentActivityHistory.getCountyWithholding();
    //        cityWithholding += segmentActivityHistory.getCityWithholding();
    //
    //        ht.put("GrossAmount", grossAmount + "");
    //        ht.put("TaxableBenefit", taxableBenefit + "");
    //        ht.put("FedWithholding", fedWithholding + "");
    //        ht.put("StateWithholding", stateWithholding + "");
    //        ht.put("CountyWithholding", countyWithholding + "");
    //        ht.put("CityWithholding", cityWithholding + "");
    //        ht.put("MarketValue", marketValue + "");
    //
    //        ten99R.put(distributionCode, ht);
    //    }
    //    private void create5498(SegmentActivityHistoryVO segmentActivityHistory,
    //                             Map fifty498) {
    //
    //        Map ht = null;
    //
    //        String premiumType = segmentActivityHistory.getPremiumType();
    //
    //        double grossAmount = 0;
    //        double taxableBenefit = 0;
    //        double fedWithholding = 0;
    //        double stateWithholding = 0;
    //        double countyWithholding = 0;
    //        double cityWithholding = 0;
    //        double marketValue = 0;
    //
    //        if (fifty498.containsKey(premiumType)) {
    //
    //            ht = (Map) fifty498.get(premiumType);
    //
    //            grossAmount = Double.parseDouble((String) ht.get("GrossAmount"));
    //            taxableBenefit = Double.parseDouble((String) ht.get("TaxableBenefit"));
    //            fedWithholding = Double.parseDouble((String) ht.get("FedWithholding"));
    //            stateWithholding = Double.parseDouble((String) ht.get("StateWithholding"));
    //            countyWithholding = Double.parseDouble((String) ht.get("CountyWithholding"));
    //            cityWithholding   = Double.parseDouble((String) ht.get("CityWithholding"));
    //            marketValue       = Double.parseDouble((String) ht.get("MarketValue"));
    //        }
    //
    //        else {
    //
    //            ht = new HashMap();
    //        }
    //
    //        grossAmount += segmentActivityHistory.getGrossAmount();
    //        taxableBenefit += segmentActivityHistory.getTaxableBenefit();
    //        fedWithholding += segmentActivityHistory.getFederalWithholding();
    //        stateWithholding += segmentActivityHistory.getStateWithholding();
    //        countyWithholding += segmentActivityHistory.getCountyWithholding();
    //        cityWithholding += segmentActivityHistory.getCityWithholding();
    //
    //        ht.put("GrossAmount", grossAmount + "");
    //        ht.put("TaxableBenefit", taxableBenefit + "");
    //        ht.put("FedWithholding", fedWithholding + "");
    //        ht.put("StateWithholding", stateWithholding + "");
    //        ht.put("CountyWithholding", countyWithholding + "");
    //        ht.put("CityWithholding", cityWithholding + "");
    //        ht.put("MarketValue", marketValue + "");
    //
    //        fifty498.put(premiumType, ht);
    //    }
    public void createValuationExtracts(String companyName, String valuationDate)
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_VALUATION_EXTRACTS).tagBatchStart(Batch.BATCH_JOB_CREATE_VALUATION_EXTRACTS, "Valuation Extract");

        ValuationInterfaceCmd valuationInterfaceCmd = new ValuationInterfaceCmd();

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        try
        {
            ClientDetailVO[] clientDetailVOs = DAOFactory.getClientDetailDAO().findAll();

            int c = 0;
            Map clientCache = new HashMap();

            for (c = 0; c < clientDetailVOs.length; c++)
            {
                String clientPK = clientDetailVOs[c].getClientDetailPK() + "";

                clientCache.put(clientPK, clientDetailVOs[c]);
            }

            ProductStructureVO[] productStructureVOs = null;

            if (companyName.equalsIgnoreCase("All"))
            {
                productStructureVOs = engineLookup.getAllProductStructures();
            }

            else
            {
                productStructureVOs = engineLookup.getAllProductStructuresByCoName(companyName);
            }

            Map productCache = new HashMap();

            for (c = 0; c < productStructureVOs.length; c++)
            {
                String productStructurePK = productStructureVOs[c].getProductStructurePK() + "";
                String businessContract = productStructureVOs[c].getBusinessContractName();

                if (!businessContract.equalsIgnoreCase("GVSN") && !businessContract.equalsIgnoreCase("GVSW"))
                {
                    productCache.put(productStructurePK, productStructureVOs[c]);
                }
            }

            valuationInterfaceCmd.setValuationInformation(clientCache, productCache, valuationDate);

            valuationInterfaceCmd.exec();
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_VALUATION_EXTRACTS).tagBatchStop();
        }
    }

    public void closeAccounting(String marketingPackageName, String accountingPeriod)
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CLOSE_ACCOUNTING).tagBatchStart(Batch.BATCH_JOB_CLOSE_ACCOUNTING, "Account Closing");

        try
        {
            engine.business.Lookup engineLookup = new engine.component.LookupComponent();

            ProductStructureVO[] productStructureVOs = null;

            if (marketingPackageName.equalsIgnoreCase("All"))
            {
                productStructureVOs = engineLookup.getAllProductStructures();
            }
            else
            {
                productStructureVOs = engineLookup.getAllProductStructuresByMarketingPackage(marketingPackageName);
            }

            if (productStructureVOs != null)
            {
                for (int i = 0; i < productStructureVOs.length; i++)
                {
                    EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CLOSE_ACCOUNTING).updateSuccess();

                    ProductStructure productStructure = new ProductStructure();
                    productStructureVOs[i].setAccountingClosePeriod(accountingPeriod);
                    productStructure.setVO(productStructureVOs[i]);
                    productStructure.save();
                }
            }
        }
        catch (Exception e)
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CLOSE_ACCOUNTING).updateFailure();

          System.out.println(e);

            e.printStackTrace();
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CLOSE_ACCOUNTING).tagBatchStop();
        }
    }

    public void createAccountingExtract_FLAT(String accountingPeriod)
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_FLAT).tagBatchStart(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_FLAT, "Accounting Extract");

        try
        {
            AccountingExtractInterfaceCmd accountingExtractCmd = new AccountingExtractInterfaceCmd(accountingPeriod);

            accountingExtractCmd.exec();
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_ACCOUNTING_EXTRACT_FLAT).tagBatchStop();
        }
    }

    public void runPendingRequirementsExtract(EDITDate extractDate)
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_PENDING_REQUIREMENTS_EXTRACT).tagBatchStart(Batch.BATCH_JOB_RUN_PENDING_REQUIREMENTS_EXTRACT, "Pending Requirements Extract");

        try
        {
            RequirementStaging requirementStaging = new RequirementStaging(new EDITDateTime(extractDate.toString() + EDITDateTime.DATE_TIME_DELIMITER + new EDITDateTime().getFormattedTime()));
            int requirementCount = requirementStaging.stageTables();

            if (requirementCount > 0)
            {
                EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_PENDING_REQUIREMENTS_EXTRACT).updateSuccess();
            }
        }
        catch (Exception e)
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_PENDING_REQUIREMENTS_EXTRACT).updateFailure();

            System.out.println(e);

            e.printStackTrace();
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_PENDING_REQUIREMENTS_EXTRACT).tagBatchStop();
        }
    }

    public void runAlphaExtract(EDITDate paramDate)
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_ALPHA_EXTRACT).tagBatchStart(Batch.BATCH_JOB_RUN_ALPHA_EXTRACT, "Alpha Extract");

        Company company = null;

        try
        {
            String exportFile = null;
            StringBuffer fileData;

            Segment[] segments = Segment.findByCreationDateLE(paramDate);

            if (segments != null)
            {
                exportFile = getAlphaExportFile(paramDate);

                int fieldLen = 0;
                int j = 0;

                String firstName = "";
                String middleName = "";
                String lastName = "";
                String nameSuffix = "";
                String namePrefix = "";
                String formattedLastName = "";
                String formattedFirstName = "";
                String formattedName = "";
                String payorName = "";
                String payorSSN = "";
                String insuredName = "";
                String companyName = "";
                String contractNumber = "";
                String insuredGender = "";
                String source = "VNS";
                String insuredDOB = "";
                String contractStatus = "";
                String marketCode = "";
                String billingForm = "";
                String keyName = "                         ";
                String relationshipToEE = "";
                String employeeId = "";

                for (int i = 0; i < segments.length; i++)
                {
                    Segment segment = segments[i];

                    Set<ContractClient> contractClients = segment.getContractClients();
                    Iterator it = contractClients.iterator();
                    while (it.hasNext())
                    {
                        ContractClient contractClient = (ContractClient) it.next();
                        ClientRole clientRole = ClientRole.findBy_ContractClient(contractClient);
                        if (clientRole.getRoleTypeCT().equalsIgnoreCase(ClientRole.ROLETYPECT_PAYOR) ||
                            clientRole.getRoleTypeCT().equalsIgnoreCase(ClientRole.ROLETYPECT_INSURED))
                        {
                            ClientDetail clientDetail = ClientDetail.findByPK(clientRole.getClientDetailFK());
                            if (clientDetail.getLastName() == null ||
                                clientDetail.getLastName().equals(""))
                            {
                                formattedName = clientDetail.getCorporateName();
                            }
                            else
                            {
                                firstName = Util.initString(clientDetail.getFirstName(), "");
                                middleName = Util.initString(clientDetail.getMiddleName(), "");
                                lastName = Util.initString(clientDetail.getLastName(), "");
                                nameSuffix = Util.initString(clientDetail.getNameSuffix(), "");
                                namePrefix = Util.initString(clientDetail.getNamePrefix(), "");

                                if (nameSuffix == null || nameSuffix.length() == 0)
                                {
                                    formattedLastName = lastName;
                                }
                                else
                                {
                                    formattedLastName = lastName + " " + nameSuffix;
                                }

                                if (namePrefix == null || namePrefix.length() == 0)
                                {
                                    formattedFirstName = firstName;
                                }
                                else
                                {
                                    formattedFirstName = namePrefix + " " + firstName;
                                }

                                formattedName = formattedLastName + "," + formattedFirstName + " " + middleName;
                            }

                            if (formattedName.length() > 25)
                            {
                                formattedName = formattedName.substring(0, 24);
                            }

                            if (clientRole.getRoleTypeCT().equalsIgnoreCase(ClientRole.ROLETYPECT_PAYOR))
                            {
                                if (clientDetail.getTaxIdentification().length() == 9)
                                {
                                    payorSSN = clientDetail.getTaxIdentification().substring(0, 3) + "-" +
                                               clientDetail.getTaxIdentification().substring(3, 5) + "-" +
                                               clientDetail.getTaxIdentification().substring(5);
                                }
                                else
                                {
                                    payorSSN = clientDetail.getTaxIdentification();
                                }

                                payorName = formattedName;

                                employeeId = Util.initString(clientDetail.getTaxIdentification(), "");

                                if (employeeId.length() > 9)
                                {
                                    employeeId = employeeId.substring(0, 8);
                                }
                            }
                            else
                            {
                                insuredGender = Util.initString(clientDetail.getGenderCT(), "");
                                if (insuredGender.length() > 0)
                                {
                                    insuredGender = insuredGender.substring(0, 1);
                                }
                                else
                                {
                                    insuredGender = " ";
                                }

                                EDITDate edDOB = clientDetail.getBirthDate();
                                if (edDOB != null)
                                {
                                    insuredDOB = edDOB.getFormattedMonth() + EDITDate.DATE_DELIMITER + edDOB.getFormattedDay() + EDITDate.DATE_DELIMITER + edDOB.getFormattedYear();
                                }
                                relationshipToEE = Util.initString(contractClient.getRelationshipToEmployeeCT(), "");
                                if (relationshipToEE.length() > 0)
                                {
                                    relationshipToEE = relationshipToEE.substring(0, 1);
                                }
                                else
                                {
                                    relationshipToEE = " ";
                                }

                                insuredName = formattedName;
                            }
                        }
                    }

                    company = Company.findByProductStructurePK(segment.getProductStructureFK());

                    if (company != null)
                    {
                        companyName = company.getCompanyName();
                    }

                    contractNumber = Util.initString(segment.getContractNumber(), "");
                    if (contractNumber.length() > 10)
                    {
                        contractNumber = contractNumber.substring(0, 9);
                    }
                    contractStatus = segment.getSegmentStatusCT();

                    ContractGroup contractGroup = ContractGroup.findByPK(segment.getContractGroupFK());

                    if (contractGroup != null)
                    {
                        marketCode = contractGroup.getContractGroupNumber();
                    }
                    else
                    {
                        marketCode = "   ";
                    }

                    if (segment.getBillScheduleFK() != null)
                    {
                        BillSchedule billSchedule = BillSchedule.findBy_BillSchedulePK(segment.getBillScheduleFK());
                        if (billSchedule != null)
                        {
                            if (billSchedule.getBillMethodCT().equalsIgnoreCase(BillSchedule.BILL_METHOD_LISTBILL))
                            {
                                billingForm = "SD ";
                            }
                            else
                            {
                                billingForm = "DIR";
                            }
                        }
                    }
                    else
                    {
                        billingForm = "   ";
                    }

                    fileData = new StringBuffer();
                    fileData.append(payorName);
                    fieldLen = payorName.length();
                    if (fieldLen < 25)
                    {
                        for (j = 0; j < 25 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }

                    fileData.append(payorSSN);
                    fieldLen = payorSSN.length();
                    if (fieldLen < 11)
                    {
                        for (j = 0; j < 11 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }

                    fileData.append(insuredName);
                    fieldLen = insuredName.length();
                    if (fieldLen < 25)
                    {
                        for (j = 0; j < 25 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }

                    fileData.append(companyName);
                    fieldLen = companyName.length();
                    if (fieldLen < 15)
                    {
                        for (j = 0; j < 15 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }

                    fileData.append(contractNumber);
                    fieldLen = contractNumber.length();
                    if (fieldLen < 10)
                    {
                        for (j = 0; j < 10 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }

                    fileData.append(insuredGender);
                    fileData.append(source);
                    fileData.append(insuredDOB);
                    fileData.append(contractStatus);
                    fieldLen = contractStatus.length();
                    if (fieldLen < 20)
                    {
                        for (j = 0; j < 20 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }

                    fileData.append(marketCode);
                    fieldLen = marketCode.length();
                    if (fieldLen < 20)
                    {
                        for (j = 0; j < 20 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }

                    fileData.append(billingForm);
                    fileData.append(keyName);
                    fileData.append(relationshipToEE);
                    fileData.append(employeeId);
                    fieldLen = employeeId.length();
                    if (fieldLen < 9)
                    {
                        for (j = 0; j < 9 - fieldLen; j++)
                        {
                            fileData.append(" ");
                        }
                    }

                    fileData.append("\n");

                    appendToAlphaFile(exportFile, fileData.toString());

                    EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_ALPHA_EXTRACT).updateSuccess();
                }
            }
            else
            {

            }
        }
        catch (Exception e)
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_ALPHA_EXTRACT).updateFailure();

            System.out.println(e);

            e.printStackTrace();

            logErrorToDatabase(e, company);
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_ALPHA_EXTRACT).tagBatchStop();
        }
    }

    private void logErrorToDatabase(Exception e, Company company)
    {
        String companyName ="";

        companyName = company.getCompanyName();

        EDITMap columnInfo = new EDITMap();

        columnInfo.put("CompanyName", companyName);
        
        Log.logToDatabase(Log.ALPHA_EXPORT, "Alpha Export Job Errored: " + e.getMessage(), columnInfo);


    }


    public void runDataWarehouse(EDITDate dataWarehouseDate, String companyName, String caseNumber, String groupNumber)
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DATAWAREHOUSE).tagBatchStart(Batch.BATCH_JOB_RUN_DATAWAREHOUSE, "Data Warehouse");

        try
        {
            Datawarehouse dataWarehouse = new Datawarehouse(new EDITDateTime(dataWarehouseDate.toString() + EDITDateTime.DATE_TIME_DELIMITER + new EDITDateTime().getFormattedTime()), companyName, caseNumber, groupNumber);
            dataWarehouse.run();
        }
        catch (Exception e)
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DATAWAREHOUSE).updateFailure();

            System.out.println(e);

            e.printStackTrace();

            LogEvent logEvent = new LogEvent(e.getMessage(), e);

            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

            logger.error(logEvent);
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_DATAWAREHOUSE).tagBatchStop();
        }
    }

    public void runWorksheet(String batchId)
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_WORKSHEET).tagBatchStart(Batch.BATCH_JOB_RUN_WORKSHEET, "Worksheet");

        EDITDateTime worksheetDateTime = new EDITDateTime();

        try
        {
            Worksheet worksheet = new Worksheet(worksheetDateTime);
            worksheet.run(batchId);
        }
        catch (Exception e)
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_WORKSHEET).updateFailure();

            System.out.println(e);

            e.printStackTrace();

            LogEvent logEvent = new LogEvent(e.getMessage(), e);

            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

            logger.error(logEvent);
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_RUN_WORKSHEET).tagBatchStop();
        }
    }

    public SeparateAccountValuesReportVO getInfoForSepAcctReport(EDITDate runDate) throws Exception
    {
        SeparateAccountValuesReportVO sepAcctValReportVO = new SeparateAccountValuesReportVO();

        EDITDate edRunDate = new EDITDate(runDate);

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        List voInclusionList = new ArrayList();

        voInclusionList.add(FilteredFundVO.class);
        voInclusionList.add(ProductFilteredFundStructureVO.class);
        voInclusionList.add(ProductStructureVO.class);

        FundVO[] fundVOs = engineLookup.composeAllVariableFundVOs(voInclusionList);

        for (int i = 0; i < fundVOs.length; i++)
        {
            FilteredFundVO[] filteredFundVOs = fundVOs[i].getFilteredFundVO();
            String fundName = fundVOs[i].getName();

            for (int j = 0; j < filteredFundVOs.length; j++)
            {
                EDITDate edTerminationDate = new EDITDate(filteredFundVOs[j].getTerminationDate());

                if (edTerminationDate.after(edRunDate) || edTerminationDate.equals(edRunDate))
                {
                    long filteredFundId = filteredFundVOs[j].getFilteredFundPK();
                    String pricingDirection = filteredFundVOs[j].getPricingDirection();

                    if (filteredFundVOs[j].getProductFilteredFundStructureVOCount() > 0)
                    {
                        ProductFilteredFundStructureVO cffsVO = filteredFundVOs[j].getProductFilteredFundStructureVO(0);

                        ProductStructureVO productStructureVO = (ProductStructureVO) cffsVO.getParentVO(ProductStructureVO.class);

                        UnitValuesVO[] unitValuesVO = engine.dm.dao.DAOFactory.getUnitValuesDAO().findUnitValuesByFilteredFundIdDate(filteredFundId, runDate.getFormattedDate(), pricingDirection);

                        if ((unitValuesVO != null) && (unitValuesVO.length > 0))
                        {
                            EDITBigDecimal unitValue = new EDITBigDecimal(unitValuesVO[0].getUnitValue());

                            EDITBigDecimal participantUnits = new EDITBigDecimal();

                            if (unitValuesVO[0].getParticipantUnits() != null)
                            {
                                participantUnits = new EDITBigDecimal(unitValuesVO[0].getParticipantUnits());
                            }

                            EDITBigDecimal investmentValue = unitValue.multiplyEditBigDecimal(participantUnits);

                            SeparateAccountValueDetailsVO sepAcctValDetailsVO = new SeparateAccountValueDetailsVO();
                            sepAcctValDetailsVO.setFundNumber(filteredFundVOs[j].getFundNumber());
                            sepAcctValDetailsVO.setFundName(fundName);
                            sepAcctValDetailsVO.setMarketingPackageName(productStructureVO.getMarketingPackageName());
                            sepAcctValDetailsVO.setValuationDate(unitValuesVO[0].getEffectiveDate());
                            sepAcctValDetailsVO.setUnitValue(unitValue.getBigDecimal());
                            sepAcctValDetailsVO.setParticipantUnits(participantUnits.getBigDecimal());
                            sepAcctValDetailsVO.setInvestmentValue(Util.roundToNearestCent(investmentValue).getBigDecimal());

                            sepAcctValReportVO.addSeparateAccountValueDetailsVO(sepAcctValDetailsVO);
                        }
                    }
                }
            }
        }

        return sepAcctValReportVO;
    }

    public SeparateAcctValByCaseVO getSepAcctValuesByCase(String runDate, String quoteTypeCT, UserSession userSession) throws Exception
    {
        EDITServicesConfig editServicesConfig = ServicesConfig.getEditServicesConfig();
        String reportCompanyName = editServicesConfig.getReportCompanyName();

        String[] statusCodes = new String[8];

        statusCodes[0] = "Active";
        statusCodes[1] = "FSHedgeFundPend";
        statusCodes[2] = "DeathCertain";
        statusCodes[3] = "DeathPending";
        statusCodes[4] = "Frozen";
        statusCodes[5] = "JointDeathPrimary";
        statusCodes[6] = "JointDeathSecondary";
        statusCodes[7] = "DeathHedgeFundPend";

        SegmentVO[] segmentVOs = Segment.findBySegmentStatus(statusCodes);

        event.business.Event eventComponent = new event.component.EventComponent();

        SeparateAcctValByCaseVO sepAcctValByCaseVO = new SeparateAcctValByCaseVO();

        String operator = userSession.getUsername();

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        for (int i = 0; i < segmentVOs.length; i++)
        {
            userSession.lockSegment(segmentVOs[i].getSegmentPK());

            QuoteVO quoteVO = eventComponent.performInforceQuote(runDate, quoteTypeCT, segmentVOs[i].getSegmentPK(), operator);

            userSession.unlockSegment();

            SeparateAcctValDetailByCaseVO sepAcctValDetailByCaseVO = new SeparateAcctValDetailByCaseVO();

            QuoteInvestmentVO[] quoteInvestmentVO = quoteVO.getQuoteInvestmentVO();

            for (int j = 0; j < quoteInvestmentVO.length; j++)
            {
                InvestmentInformationVO investmentInformationVO = new InvestmentInformationVO();

                investmentInformationVO.setQuoteInvestmentVO(quoteInvestmentVO[j]);

                InvestmentVO investmentVO = quoteInvestmentVO[j].getInvestmentVO();
                investmentVO.removeAllInvestmentAllocationOverrideVO();
                investmentVO.removeAllInvestmentAllocationVO();

                FundVO[] fundVO = engineLookup.getFundByFilteredFundFK(investmentVO.getFilteredFundFK(), false, new ArrayList());
                investmentInformationVO.setFundName(fundVO[0].getName());
                sepAcctValDetailByCaseVO.addInvestmentInformationVO(investmentInformationVO);
            }

            sepAcctValDetailByCaseVO.setContractNumber(quoteVO.getSegmentVO(0).getContractNumber());
            sepAcctValDetailByCaseVO.setClientDetailVO(quoteVO.getClientDetailVO());

            sepAcctValByCaseVO.addSeparateAcctValDetailByCaseVO(sepAcctValDetailByCaseVO);
        }

        FeeVO[] feeVOs = new Fee().findByFeeType_Date("DPURCH", runDate);

        EDITBigDecimal advanceTransfer = new EDITBigDecimal();

        if (feeVOs != null)
        {
            for (int i = 0; i < feeVOs.length; i++)
            {
                advanceTransfer = advanceTransfer.addEditBigDecimal(feeVOs[i].getTrxAmount());
            }
        }

        sepAcctValByCaseVO.setAdvanceTransfers(Util.roundToNearestCent(advanceTransfer).getBigDecimal());
        sepAcctValByCaseVO.setReportCompanyName(reportCompanyName);
        sepAcctValByCaseVO.setRunDate(runDate);

        File xmlFile = getExportFile(runDate);
        exportVOObjectToFile(sepAcctValByCaseVO, xmlFile);

        return sepAcctValByCaseVO;
    }

    /**
     * Composes fee and transaction information for the Case Manager Review and Fund Activity Reports
     * @param selectedProductStructure
     * @param selectedFund
     * @param startDate
     * @param endDate
     * @param dateType
     * @return
     * @throws Exception
     */
    public Hashtable getFeeAndTrxInfoForReport(String selectedMarketingPackage, String selectedFund, String startDate, String endDate, String dateType) throws Exception
    {
        event.business.Event eventComponent = new event.component.EventComponent();

        FilteredFundVO[] filteredFundVOs = getFilteredFunds(selectedMarketingPackage, selectedFund);

        Hashtable caseManagerReviewInfo = new Hashtable();

        List funds = new ArrayList();

        Hashtable cashTransfers = new Hashtable();

        Hashtable accrualTransfers = new Hashtable();

        List voInclusionList = new ArrayList();
        voInclusionList.add(EDITTrxVO.class);
        voInclusionList.add(InvestmentHistoryVO.class);
        voInclusionList.add(InvestmentVO.class);
        voInclusionList.add(SegmentVO.class);
        voInclusionList.add(BucketHistoryVO.class);
        voInclusionList.add(BucketVO.class);
        voInclusionList.add(BucketChargeHistoryVO.class);

        Map sortedFunds = new TreeMap();

        if (filteredFundVOs != null)
        {
            TransformChargeCodes transformChargeCodes = new TransformChargeCodes();

            for (int i = 0; i < filteredFundVOs.length; i++)
            {
                List clientFundNumbers = ChargeCode.getUniqueClientFundNumbers(filteredFundVOs[i].getFilteredFundPK());

                long[] chargeCodePKs = ChargeCode.getAllChargeCodePKsIncludingZero(filteredFundVOs[i].getFilteredFundPK());

                if (clientFundNumbers.size() > 1)
                {
                    for (int j = 0; j < chargeCodePKs.length; j++)
                    {
                        String clientFundNumber = transformChargeCodes.getClientFundNumber(chargeCodePKs[j]);

                        if (clientFundNumber == null)
                        {
                            clientFundNumber = filteredFundVOs[i].getFundNumber();
                        }

                        sortedFunds.put(clientFundNumber + "_" + filteredFundVOs[i].getFilteredFundPK() + "__" + chargeCodePKs[j], "");

                        Fee[] fees = Fee.findByFilteredFundPKDateChargeCode(filteredFundVOs[i].getFilteredFundPK(), startDate, endDate, dateType, chargeCodePKs[j]);

                        if (fees != null)
                        {
                            cashTransfers.put(clientFundNumber + "_" + filteredFundVOs[i].getFilteredFundPK() + "__" + chargeCodePKs[j], fees);
                        }

                        EDITTrxHistoryVO[] editTrxHistoryVOs = eventComponent.composeEDITTrxHistoryByDate_And_Fund(startDate, endDate, dateType, filteredFundVOs[i].getFilteredFundPK(), chargeCodePKs[j], voInclusionList);

                        if (editTrxHistoryVOs != null)
                        {
                            accrualTransfers.put(clientFundNumber + "_" + filteredFundVOs[i].getFilteredFundPK() + "__" + chargeCodePKs[j], editTrxHistoryVOs);
                        }
                    }
                }
                else
                {
                    String clientFundNumber = transformChargeCodes.getClientFundNumber(chargeCodePKs[0]);

                    if (clientFundNumber == null)
                    {
                        clientFundNumber = filteredFundVOs[i].getFundNumber();
                    }

                    sortedFunds.put(clientFundNumber + "_" + filteredFundVOs[i].getFilteredFundPK(), "");

                    Fee[] fees = Fee.findByFilteredFundPK_And_Date(filteredFundVOs[i].getFilteredFundPK(), startDate, endDate, dateType);

                    if (fees != null)
                    {
                        cashTransfers.put(clientFundNumber + "_" + filteredFundVOs[i].getFilteredFundPK(), fees);
                    }

                    EDITTrxHistoryVO[] editTrxHistoryVOs = eventComponent.composeEDITTrxHistoryByDate_And_Fund(startDate, endDate, dateType, filteredFundVOs[i].getFilteredFundPK(), voInclusionList);

                    if (editTrxHistoryVOs != null)
                    {
                        accrualTransfers.put(clientFundNumber + "_" + filteredFundVOs[i].getFilteredFundPK(), editTrxHistoryVOs);
                    }
                }
            }
        }

        Set sortedFundKeys = sortedFunds.keySet();

        Iterator it = sortedFundKeys.iterator();

        while (it.hasNext())
        {
            funds.add(it.next());
        }

        caseManagerReviewInfo.put("funds", funds);
        caseManagerReviewInfo.put("cashTransfers", cashTransfers);
        caseManagerReviewInfo.put("accrualTransfers", accrualTransfers);

        return caseManagerReviewInfo;
    }

    /**
     * Composes fee and transaction information for the Assets - Liabilities Report (ALR)
     * @param date
     * @param dateType
     * @return
     * @throws Exception
     */
    public Hashtable getInfoForAssetsLiabilitiesReport(String date) throws Exception
    {
        TreeMap filteredFundMap = composeAllFilteredFunds();

        Hashtable assetsLiabilitiesReportInfo = new Hashtable();

        Hashtable assets = new Hashtable();

        Hashtable liabilities = new Hashtable();

        Set ffKeySet = filteredFundMap.keySet();
        Iterator it = ffKeySet.iterator();

        TransformChargeCodes transformChargeCodes = new TransformChargeCodes();

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        EDITBigDecimal totalAssets;
        EDITBigDecimal totalLiabs;

        while (it.hasNext())
        {
            String htKey = (String) it.next();
            FilteredFundVO filteredFundVO = (FilteredFundVO) filteredFundMap.get(htKey);

            ProductFilteredFundStructure[] cffs = ProductFilteredFundStructure.findByFilteredFundFK(new Long(filteredFundVO.getFilteredFundPK()));

            long[] chargeCodePKs = ChargeCode.getAllChargeCodePKsIncludingZero(filteredFundVO.getFilteredFundPK());

            for (int i = 0; i < chargeCodePKs.length; i++)
            {
                totalAssets = new EDITBigDecimal();
                totalLiabs = new EDITBigDecimal();

                String clientFundNumber = transformChargeCodes.getClientFundNumber(chargeCodePKs[i]);

                if (clientFundNumber == null)
                {
                    clientFundNumber = filteredFundVO.getFundNumber();
                }

                for (int j = 0; j < cffs.length; j++)
                {
                    long cffsPK = cffs[j].getProductFilteredFundStructurePK().longValue();

                    ControlBalanceVO[] controlBalanceVO = engineLookup.findControlBalanceByCoFilteredFundStructure_DateClosest(cffsPK, chargeCodePKs[i], date);

                    if (controlBalanceVO != null)
                    {
                        for (int k = 0; k < controlBalanceVO.length; k++)
                        {
                            totalAssets = totalAssets.addEditBigDecimal(controlBalanceVO[k].getDFCASHEndingShareBalance());
                            totalLiabs = totalLiabs.addEditBigDecimal(controlBalanceVO[k].getEndingDollarBalance());
                        }
                    }
                }

                assets.put(clientFundNumber + "_" + filteredFundVO.getFilteredFundPK() + "__" + chargeCodePKs[i], totalAssets);
                liabilities.put(clientFundNumber + "_" + filteredFundVO.getFilteredFundPK() + "__" + chargeCodePKs[i], totalLiabs);
            }
        }

        assetsLiabilitiesReportInfo.put("funds", filteredFundMap);
        assetsLiabilitiesReportInfo.put("assets", assets);
        assetsLiabilitiesReportInfo.put("liabilities", liabilities);

        return assetsLiabilitiesReportInfo;
    }

    /**
     * Composes Fee and Transaction information for the Money Move Report
     * @param date
     * @param dateType
     * @return
     * @throws Exception
     */
    public Hashtable getInfoForMoneyMoveReport(String date, String dateType) throws Exception
    {
        event.business.Event eventComponent = new event.component.EventComponent();

        FilteredFundVO[] filteredFundVOs = getAllNonAutomatedFilteredFunds();

        Hashtable moneyMoveReportInfo = new Hashtable();

        Hashtable cashTransfers = new Hashtable();

        Hashtable accrualTransfers = new Hashtable();

        List voInclusionList = new ArrayList();
        voInclusionList.add(EDITTrxVO.class);
        voInclusionList.add(InvestmentHistoryVO.class);
        voInclusionList.add(InvestmentVO.class);

        for (int i = 0; i < filteredFundVOs.length; i++)
        {
            long[] chargeCodeFKs = ChargeCode.getAllChargeCodePKsIncludingZero(filteredFundVOs[i].getFilteredFundPK());

            for (int j = 0; j < chargeCodeFKs.length; j++)
            {
                long chargeCodeFK = chargeCodeFKs[j];
                Fee[] fees = Fee.findByFilteredFundPKDateChargeCode(filteredFundVOs[i].getFilteredFundPK(), date, date, dateType, chargeCodeFK);

                String key = filteredFundVOs[i].getFundNumber() + "_" + filteredFundVOs[i].getFilteredFundPK() + "_" + chargeCodeFK;

                if (fees != null)
                {
                    cashTransfers.put(key, fees);
                }

                EDITTrxHistoryVO[] editTrxHistoryVOs = eventComponent.composeEDITTrxHistoryByDate_And_Fund(date, date, dateType, filteredFundVOs[i].getFilteredFundPK(), chargeCodeFK, voInclusionList);

                if (editTrxHistoryVOs != null)
                {
                    accrualTransfers.put(key, editTrxHistoryVOs);
                }
            }
        }

        moneyMoveReportInfo.put("funds", filteredFundVOs);
        moneyMoveReportInfo.put("cashTransfers", cashTransfers);
        moneyMoveReportInfo.put("accrualTransfers", accrualTransfers);

        return moneyMoveReportInfo;
    }

    /**
     * Retrieves filtered fund information for the given marketingPackage and fund
     * @param selectedMarketingPackage
     * @param selectedFund
     * @return
     * @throws Exception
     */
    public FilteredFundVO[] getFilteredFunds(String selectedMarketingPackage, String selectedFund) throws Exception
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        FilteredFundVO[] filteredFundVOs = null;

        if (selectedMarketingPackage.equalsIgnoreCase("All"))
        {
            if (selectedFund.equalsIgnoreCase("All"))
            {
                filteredFundVOs = engineLookup.getAllFilteredFunds();
            }
            else
            {
                filteredFundVOs = engineLookup.getByFundId(Long.parseLong(selectedFund));
            }
        }
        else
        {
            if (selectedFund.equalsIgnoreCase("All"))
            {
                filteredFundVOs = engineLookup.findFilteredFundByMarketingPackage(selectedMarketingPackage, false, new ArrayList());
            }
            else
            {
                filteredFundVOs = engineLookup.getByMarketingPackageFundId(selectedMarketingPackage, Long.parseLong(selectedFund));
            }
        }

        return filteredFundVOs;
    }

    /**
     * Retrieves all FilteredFundVOs (non-automated funds are those of type "Hedge")
     * @return
     * @throws Exception
     */
    private FilteredFundVO[] getAllNonAutomatedFilteredFunds() throws Exception
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        FilteredFundVO[] filteredFundVOs = engineLookup.getAllHedgeFunds();

        List voInclusionList = new ArrayList();
        voInclusionList.add(FundVO.class);

        for (int i = 0; i < filteredFundVOs.length; i++)
        {
            FilteredFundVO[] fullFilteredFundVO = engineLookup.composeFilteredFundVOByFilteredFundPK(filteredFundVOs[i].getFilteredFundPK(), voInclusionList);
            filteredFundVOs[i] = fullFilteredFundVO[0];
        }

        return filteredFundVOs;
    }

    /**
     * Retrieves all FilteredFundVOs for all ProductStructures
     * @return
     * @throws Exception
     */
    private TreeMap composeAllFilteredFunds() throws Exception
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        ProductStructureVO[] productStructureVOs = engineLookup.getAllProductStructures();

        TreeMap sortedFilteredFunds = new TreeMap();
        List updatedFilteredFundVOs = new ArrayList();

        List voInclusionList = new ArrayList();
        voInclusionList.add(ControlBalanceVO.class);

        TransformChargeCodes transformChargeCodes = new TransformChargeCodes();

        for (int i = 0; i < productStructureVOs.length; i++)
        {
            long productStructurePK = productStructureVOs[i].getProductStructurePK();

            FilteredFundVO[] filteredFundVOs = engineLookup.composeFilteredFundVOByProductStructurePK(productStructurePK, voInclusionList);

            if (filteredFundVOs != null)
            {
                updatedFilteredFundVOs.clear();

                for (int j = 0; j < filteredFundVOs.length; j++)
                {
                    long[] chargeCodePKs = ChargeCode.getAllChargeCodePKsIncludingZero(filteredFundVOs[j].getFilteredFundPK());

                    for (int k = 0; k < chargeCodePKs.length; k++)
                    {
                        String clientFundNumber = transformChargeCodes.getClientFundNumber(chargeCodePKs[k]);

                        if (clientFundNumber == null)
                        {
                            clientFundNumber = filteredFundVOs[j].getFundNumber();
                        }

                        FilteredFundVO updatedFilteredFund = (FilteredFundVO) filteredFundVOs[j].cloneVO();

                        updatedFilteredFund.setFundNumber(clientFundNumber);

                        sortedFilteredFunds.put(productStructureVOs[i].getMarketingPackageName() + "_" + clientFundNumber + "__" + chargeCodePKs[k], updatedFilteredFund);
                    }
                }
            }
        }

        return sortedFilteredFunds;
    }

    /**
     * Set up the alpha extract file
     * @return  File - define name
     */
    private String getAlphaExportFile(EDITDate paramDate)
    {
        EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");

        String fileDate = paramDate.getFormattedMonth() + paramDate.getFormattedDay() + paramDate.getFormattedYear();

        String exportFile = export1.getDirectory() + "MF_Alpha__Rpt_Extract_File_" + fileDate + ".txt";

        return exportFile;
    }

    private File getExportFile(String runDate)
    {
        EDITExport export1 = ServicesConfig.getEDITExport("ExportDirectory1");

        EDITDate editRunDate = new EDITDate(runDate);

        String fileDate = editRunDate.getYearAsYY() + editRunDate.getFormattedMonth() + editRunDate.getFormattedDay();

        File exportFile = new File(export1.getDirectory() + "SepAcctFileByCase_" + fileDate + ".xml");

        return exportFile;
    }

    private void appendToFile(File exportFile, String data) throws Exception
    {
        BufferedWriter bw = new BufferedWriter(new FileWriter(exportFile, true));

        bw.write(data);

        bw.flush();

        bw.close();
    }

    private void appendToAlphaFile(String exportFile, String data)
    {
        BufferedWriter bw = null;

        try
        {
            bw = new BufferedWriter(new FileWriter(exportFile, true));

            bw.write(data);

            bw.flush();

        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                bw.close();
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    private void exportVOObjectToFile(VOObject voObject, File exportFile) throws Exception
    {
        String parsedXML = Util.marshalVO(voObject);

        parsedXML = XMLUtil.parseOutXMLDeclaration(parsedXML);

        appendToFile(exportFile, parsedXML);
    }

    public void createPayoutTrxExtract(EDITDate fromDate , EDITDate toDate, String dateOption)
    {
        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_PAYOUT_TRX_EXTRACT).tagBatchStart(Batch.BATCH_JOB_CREATE_PAYOUT_TRX_EXTRACT, "Payout Trx Extract");

        try
        {
            processPayoutExtract(fromDate, toDate, dateOption);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();

            LogEvent logEvent = new LogEvent("Payout Trx Extract Errored", e);

            Logger logger = Logging.getLogger(Logging.BATCH_JOB);

            logger.error(logEvent);

//            logErrorToDatabase(e);
        }
        finally
        {
            EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_PAYOUT_TRX_EXTRACT).tagBatchStop();
        }
    }

    private void processPayoutExtract(EDITDate fromDate, EDITDate toDate, String dateOption)
    {
        PayoutTrxRptInterfaceCmd payoutTrxRptInterfaceCmd = new PayoutTrxRptInterfaceCmd();

        StringBuffer trxFileData = new StringBuffer();

        Long[] editTrxPKs = null;

        if (dateOption.equalsIgnoreCase("D"))
        {
            editTrxPKs = EDITTrx.findByDateRangeOfDueDate(fromDate, toDate);
        }
        else
        {
            editTrxPKs = EDITTrx.findByDateRangeOfEffectiveDate(fromDate, toDate);
        }

        if (editTrxPKs != null)
        {
            try
            {
                for (int i = 0; i < editTrxPKs.length; i++)
                {
                    EDITTrx editTrx = EDITTrx.findBy_PK(editTrxPKs[i]);

                    payoutTrxRptInterfaceCmd.setPayoutRptInformation(editTrx, trxFileData);


                        payoutTrxRptInterfaceCmd.exec();

                        EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_PAYOUT_TRX_EXTRACT).updateSuccess();
                    }
                }

            catch (Exception e)
            {
                EditServiceLocator.getSingleton().getBatchAgent().getBatchStat(Batch.BATCH_JOB_CREATE_PAYOUT_TRX_EXTRACT).updateFailure();
                System.out.println(e);
                e.printStackTrace();

                LogEvent logEvent = new LogEvent("Payout Trx Extract Errored", e);

                Logger logger = Logging.getLogger(Logging.BATCH_JOB);

                logger.error(logEvent);
            }
        }
    }
}
