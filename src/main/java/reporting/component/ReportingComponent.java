/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Mar 22, 2002
 * Time: 2:19:24 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package reporting.component;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.vo.FilteredFundVO;
import edit.common.vo.ProductFilteredFundStructureVO;
import edit.common.vo.ProductStructureVO;
import edit.common.vo.SegmentVO;
import edit.common.vo.UnitValuesVO;
import edit.common.vo.YearEndTaxVO;

import edit.services.component.AbstractComponent;

import engine.ControlBalance;
import engine.ControlBalanceDetail;

import engine.dm.dao.FilteredFundDAO;

import engine.dm.dao.ProductFilteredFundStructureDAO;

import engine.util.TransformChargeCodes;

import fission.global.AppReqBlock;

import fission.utility.DateTimeUtil;
import fission.utility.Util;
import fission.utility.XMLUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

import reporting.batch.ReportsProcessor;
import reporting.batch.ReservesProcessor;

import reporting.business.Reporting;

import java.util.*;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

import webservice.SEGResponseDocument;


public class ReportingComponent extends AbstractComponent implements Reporting {

    //Constants
    private static final String XMLDATA_PATH  = "c:\\accounting\\data\\";

    //Member variables

    public ReportingComponent() {

    }

    public void createReservesExtracts(AppReqBlock appReqBlock,
                                        SegmentVO[] segmentVOs,
                                         String effectiveDate) throws Exception {

        new ReservesProcessor().createReservesExtracts(appReqBlock, segmentVOs, effectiveDate);
    }

    public YearEndTaxVO[] processTaxes(SegmentVO[] segmentVOs,
                                        String fromDate,
                                         String toDate,
                                          String taxYear,
                                           ProductStructureVO[] productStructureVOs)
                                      throws Exception {

        return new ReportsProcessor().processTaxes(segmentVOs,
                                              fromDate,
                                               toDate,
                                                taxYear,
                                                 productStructureVOs);
    }

    public void createValuationExtracts(String companyName,
                                         String valuationDate) throws Exception {

        new ReportsProcessor().createValuationExtracts(companyName, valuationDate);
    }
    
    public void closeAccounting(String marketingPackageName,
                                String accountingPeriod) throws Exception
    {
        new ReportsProcessor().closeAccounting(marketingPackageName, accountingPeriod);
    }

    public void runAccountingExtract(String accountingPeriod) throws Exception
    {
        //new ReportsProcessor().createAccountingExtract_XML(accountingPeriod);
    }

    public long createOrUpdateVO(Object valueObject, boolean recursively) throws Exception {

        return 0;
    }

    public void deleteVO(String voName, long primaryKey)throws Exception {

    }

    public String[] findVOs() {return null;};

    public int deleteVO(Class voClass, long primaryKey, boolean recursively) throws Exception
    {
//        return super.deleteVO(voClass, primaryKey, ConnectionFactory.REPORTING_POOL, false);
        return 0;
    }


    public Object retrieveVO(Class voClass, long primaryKey, boolean recursively, List voInclusionList) throws Exception
    {
//        return super.retrieveVO(voClass, primaryKey, ConnectionFactory.REPORTING_POOL, false, null);
        return null;
    }

    /**
     * @see reporting.business.Reporting#generateFundActivityReport(org.dom4j.Document)
     * @param requestDocument
     * @return responseDocument
     */
    public Document generateFundActivityReport(Document requestDocument)
    {
        SEGResponseDocument responseDocument = new SEGResponseDocument();

        try
        {
            Element fundActivityReportElement = new DefaultElement("fundactivityreport");

            TransformChargeCodes transformChargeCodes = new TransformChargeCodes();

            Map fundElementsByFundNumber = new TreeMap();

            Element requestParameterElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

            EDITDate startCycleDate = new EDITDate(DateTimeUtil.formatMMDDYYYYToYYYYMMDD(requestParameterElement.element("StartCycleDate").getText()));

            EDITDate endCycleDate = new EDITDate(DateTimeUtil.formatMMDDYYYYToYYYYMMDD(requestParameterElement.element("EndCycleDate").getText()));

            ControlBalanceDetail[] controlBalanceDetails = ControlBalanceDetail.findByEndingBalanceCycleDate_Range(startCycleDate, endCycleDate);

            for (int i = 0; i < controlBalanceDetails.length; i++)
            {
                ControlBalanceDetail controlBalanceDetail = controlBalanceDetails[i];

                ControlBalance controlBalance = controlBalanceDetail.getControlBalance();

                long companyFilteredFundStructureFK = controlBalance.getProductFilteredFundStructureFK().longValue();

                long chargeCodeFK = controlBalance.getChargeCodeFK() == null ? 0 : controlBalance.getChargeCodeFK().longValue();

                ProductFilteredFundStructureVO companyFilteredFundStructureVO = new ProductFilteredFundStructureDAO().findByPK(companyFilteredFundStructureFK)[0];

                long filteredFundFK = companyFilteredFundStructureVO.getFilteredFundFK();

                FilteredFundVO filteredFundVO = new FilteredFundDAO().findByFilteredFundPK(filteredFundFK)[0];

                String fundNumber = getFundNumber(filteredFundVO, chargeCodeFK, transformChargeCodes);

                Element fundElement = null;

                if (fundElementsByFundNumber.containsKey(fundNumber))
                {
                    fundElement = (Element) fundElementsByFundNumber.get(fundNumber);
                }
                else
                {
                    fundElement = new DefaultElement("fund");

                    fundElement.addAttribute("fundnumber", fundNumber);
                }

                fundElement = generateFundData(fundElement, controlBalanceDetail, filteredFundVO, chargeCodeFK);

                fundElementsByFundNumber.put(fundNumber, fundElement);
            }

            fundActivityReportElement = addFundElementsToFundActivityReportElement(fundActivityReportElement, fundElementsByFundNumber);

            responseDocument.addToRootElement(fundActivityReportElement);

            responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Fund Activity Report Successfully generated");
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            responseDocument.setResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, "Unexepected error while generating Fund Activity Report [" + e.getMessage() + "]");
        }

        return responseDocument.getDocument();
    }

    /**
     * An utility method to get Client Fund Number
     * @param filteredFundVO
     * @param chargeCodeFK
     * @param transformChargeCodes
     * @return
     */
    private String getFundNumber(FilteredFundVO filteredFundVO, long chargeCodeFK, TransformChargeCodes transformChargeCodes)
    {
        String fundNumber = filteredFundVO.getFundNumber();

        if (chargeCodeFK != 0)
        {
            fundNumber = transformChargeCodes.getClientFundNumber(chargeCodeFK);
        }

        return fundNumber;
    }

    /**
     * Creates fund data
     * @param fundElement
     * @param controlBalanceDetail
     * @param filteredFundVO
     * @param chargeCodeFK
     * @return
     */
    private Element generateFundData(Element fundElement, ControlBalanceDetail controlBalanceDetail, FilteredFundVO filteredFundVO, long chargeCodeFK)
    {
        Element dataElement = createDataElement(controlBalanceDetail, filteredFundVO, chargeCodeFK);

        fundElement.add(dataElement);

        return fundElement;
    }

    /**
     * Creates fund data with fund activity values as attributes
     * @param controlBalanceDetail
     * @param filteredFundVO
     * @param chargeCodeFK
     * @return
     */
    private Element createDataElement(ControlBalanceDetail controlBalanceDetail, FilteredFundVO filteredFundVO, long chargeCodeFK)
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        long filteredFundPK = filteredFundVO.getFilteredFundPK();

        String pricingDirection = filteredFundVO.getPricingDirection();

        ControlBalance controlBalance = controlBalanceDetail.getControlBalance();

        EDITDate valuationDate = controlBalanceDetail.getValuationDate();

        UnitValuesVO[] unitValuesForValuationDate = engineLookup.getUnitValuesByFilteredFundIdDateChargeCode(
                filteredFundPK,
                valuationDate.getFormattedDate(),
                pricingDirection,
                chargeCodeFK);

        EDITDate endingBalanceCycleDate = controlBalance.getEndingBalanceCycleDate();

        UnitValuesVO[] unitValuesForCycleDate = engineLookup.getUnitValuesByFilteredFundIdDateChargeCode(
                filteredFundPK,
                endingBalanceCycleDate.getFormattedDate(),
                pricingDirection,
                chargeCodeFK);
        
        EDITBigDecimal unitValue = new EDITBigDecimal();
        EDITBigDecimal netAssetValue2 = new EDITBigDecimal();
        EDITBigDecimal uvalAssets = new EDITBigDecimal();
        EDITBigDecimal nav1Assets = new EDITBigDecimal();
        EDITBigDecimal nav2Assets = new EDITBigDecimal();

        if (unitValuesForValuationDate != null)
        {
            unitValue = new EDITBigDecimal(unitValuesForValuationDate[0].getUnitValue());
            netAssetValue2 = new EDITBigDecimal(unitValuesForValuationDate[0].getNetAssetValue2());
        }

        if (unitValuesForCycleDate != null)
        {
            uvalAssets = new EDITBigDecimal(unitValuesForCycleDate[0].getUVALAssets());
            nav1Assets = new EDITBigDecimal(unitValuesForCycleDate[0].getNAV1Assets());
            nav2Assets = new EDITBigDecimal(unitValuesForCycleDate[0].getNAV2Assets());
        }

        Element dataElement = new DefaultElement("data");

        dataElement.addAttribute("cycdate", endingBalanceCycleDate.getFormattedDate());
        dataElement.addAttribute("acctperiod", controlBalanceDetail.getAccountingPeriod());
        dataElement.addAttribute("effdate", controlBalanceDetail.getEffectiveDate().getFormattedDate());
        dataElement.addAttribute("valdate", controlBalanceDetail.getValuationDate().getFormattedDate());

        dataElement.addAttribute("pov", controlBalanceDetail.getPolicyOwnerValue().toString());
        dataElement.addAttribute("uvala", uvalAssets.toString());
        dataElement.addAttribute("policyAct", controlBalanceDetail.getPolicyActivity().toString());
        dataElement.addAttribute("unitBal", controlBalanceDetail.getUnitBalance().toString());

        dataElement.addAttribute("endUnitBal", controlBalance.getEndingUnitBalance().toString());
        dataElement.addAttribute("unitsPurch", controlBalanceDetail.getUnitsPurchased().toString());
        dataElement.addAttribute("uv", unitValue.toString());
        dataElement.addAttribute("accruedGL", controlBalanceDetail.getAccruedGainLoss().toString());

        dataElement.addAttribute("accruedNP", controlBalanceDetail.getTotalAccruedNetPremiums().toString());
        dataElement.addAttribute("accruedAF", controlBalanceDetail.getTotalAccruedAdminFees().toString());
        dataElement.addAttribute("accruedCOI", controlBalanceDetail.getTotalAccruedCOI().toString());
        dataElement.addAttribute("accruedRealloc", controlBalanceDetail.getTotalAccruedReallocations().toString());

        dataElement.addAttribute("accruedRRD", controlBalanceDetail.getTotalAccruedRRD().toString());
        dataElement.addAttribute("accruedSurr", controlBalanceDetail.getTotalAccruedSurrenders().toString());
        dataElement.addAttribute("accruedMR", controlBalanceDetail.getTotalAccruedContribToMortRsv().toString());
        dataElement.addAttribute("accruedME", controlBalanceDetail.getTotalAccruedMAndE().toString());

        dataElement.addAttribute("accruedAdvF", controlBalanceDetail.getTotalAccruedAdvanceTransfers().toString());
        dataElement.addAttribute("accruedMF", controlBalanceDetail.getTotalAccruedMgtFees().toString());
        dataElement.addAttribute("accruedRVPF", controlBalanceDetail.getTotalAccruedRVPFees().toString());
        dataElement.addAttribute("accruedAT", controlBalanceDetail.getTotalAccruedAdvanceTransfers().toString());

        dataElement.addAttribute("netAssets", controlBalanceDetail.getNetAssets().toString());
        dataElement.addAttribute("nav1A", nav1Assets.toString());
        dataElement.addAttribute("nav2A", nav2Assets.toString());
        dataElement.addAttribute("netCash", controlBalanceDetail.getNetCash().toString());

        dataElement.addAttribute("shareBalance", controlBalanceDetail.getShareBalance().toString());
        dataElement.addAttribute("sharesPurch", controlBalanceDetail.getSharesPurchased().toString());
        dataElement.addAttribute("nav2", netAssetValue2.toString());
        dataElement.addAttribute("cashNP", controlBalanceDetail.getTotalCashNetPremium().toString());

        dataElement.addAttribute("cashGL", controlBalanceDetail.getCashGainLoss().toString());
        dataElement.addAttribute("cashAF", controlBalanceDetail.getTotalCashAdminFees().toString());
        dataElement.addAttribute("cashCOI", controlBalanceDetail.getTotalCashCoi().toString());
        dataElement.addAttribute("cashRalloc", controlBalanceDetail.getTotalCashReallocations().toString());

        dataElement.addAttribute("cashRRD", controlBalanceDetail.getTotalCashRRD().toString());
        dataElement.addAttribute("cashSurr", controlBalanceDetail.getTotalCashSurrenders().toString());
        dataElement.addAttribute("cashMR", controlBalanceDetail.getTotalCashContribToMortRsv().toString());
        dataElement.addAttribute("cashME", controlBalanceDetail.getTotalCashMAndE().toString());

        dataElement.addAttribute("cashAdvF", controlBalanceDetail.getTotalCashAdvisoryFees().toString());
        dataElement.addAttribute("cashMF", controlBalanceDetail.getTotalCashMgmtFees().toString());
        dataElement.addAttribute("cashSVA", controlBalanceDetail.getTotalCashSVAFees().toString());
        dataElement.addAttribute("cashAT", controlBalanceDetail.getTotalCashAdvanceTransfers().toString());

        return dataElement;
    }

    /**
     * Appends fund elements to fundactivityreport element
     * @param fundActivityReportElement
     * @param fundElementsByFundNumber
     * @return
     */
    private Element addFundElementsToFundActivityReportElement(Element fundActivityReportElement, Map fundElementsByFundNumber)
    {
        for (Iterator iterator = fundElementsByFundNumber.keySet().iterator(); iterator.hasNext();)
        {
            String fundNumber = (String) iterator.next();

            Element fundElement = (Element) fundElementsByFundNumber.get(fundNumber);

            fundActivityReportElement.add(fundElement);
        }

        return fundActivityReportElement;
    }
}
