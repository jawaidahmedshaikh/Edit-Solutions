/*
 * Created by IntelliJ IDEA.
 * User: cgleason
 * Date: Apr 25, 2002
 * Time: 10:34:26 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package contract.dm;

import edit.common.CodeTableWrapper;
import edit.common.vo.CodeTableVO;
import edit.common.vo.SuspenseVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryBuilder {

    private CodeTableVO       codeTableVO      = null;
    private CodeTableWrapper  codeTableWrapper;
    double amountPaidToDate;
    double accumLoads;
    double accumFees;
    double withdrawalsYTD;
    double lumpSumPaidToDate;
    double amountPaidYTD;
    double lumpSumPaidYTD;
    double grossAmount;
    double payeeAmountPaidToDate;
    double payeeAmountPaidYTD;
    double payeeFedWithholding;
    double payeeStateWithholding;
    double payeeCityWithholding;
    double payeeCountyWithholding;
    double totalDisbursements;
    double accumDollars;
    double accumUnits;
    Map ht                   = null;
    Map payeeHt;
    Map allocHt              = new HashMap();
//    AccumulationVO  accumulationVO = null;
    List payeeVector;
    List allocVector             = null;

//    public AccumulationVO  getAllAccumulations(long segmentId, String calendarYear) throws Exception {
//
//        amountPaidToDate       = 0;
//        accumLoads             = 0;
//        accumFees              = 0;
//        withdrawalsYTD         = 0;
//        lumpSumPaidToDate      = 0;
//        amountPaidYTD          = 0;
//        lumpSumPaidYTD         = 0;
//        grossAmount            = 0;
//        payeeAmountPaidToDate  = 0;
//        payeeAmountPaidYTD     = 0;
//        payeeFedWithholding    = 0;
//        payeeStateWithholding  = 0;
//        payeeCityWithholding   = 0;
//        payeeCountyWithholding = 0;
//        totalDisbursements     = 0;
//        accumDollars           = 0;
//        accumUnits             = 0;
//        payeeHt                = new HashMap();
//        payeeVector            = null;
//
//        SegmentVO[] segmentVO = DAOFactory.getSegmentDAO().findByPKWithExclusions(segmentId);
//
//        codeTableWrapper = CodeTableWrapper.getSingleton();
//        CodeTableVO[] trxTypeCTVO = codeTableWrapper.getCodeTableEntries("TRXTYPE");
//
//        SegmentActivityHistoryVO[] segmentActivityHistoryVO = segmentVO[0].getSegmentActivityHistoryVO();
//        ContractClientVO[] contractClientVO = segmentVO[0].getContractClientVO();
//        SuspenseVO[] suspenseVO = segmentVO[0].getSuspenseVO();
//        InvestmentVO[] investmentVOs =  segmentVO[0].getInvestmentVO();
//        accumulationVO = new AccumulationVO();
//
//        for (int i = 0; i < segmentActivityHistoryVO.length; i++) {
//
//            String trxName = segmentActivityHistoryVO[i].getTransactionType();
//            if (trxName != null) {
//
//                for (int t = 0; t < trxTypeCTVO.length; t++) {
//
//                    if (trxName.equalsIgnoreCase(trxTypeCTVO[t].getCode())) {
//
//                        trxName = trxTypeCTVO[t].getCodeDesc();
//                        break;
//                    }
//                }
//            }
//
//            String effectiveDate = segmentActivityHistoryVO[i].getEffectiveDate();
//            String processYear = "";
//            if (effectiveDate != null) {
//
//                processYear = effectiveDate.substring(0, 4);
//            }
//
//            grossAmount = segmentActivityHistoryVO[i].getGrossAmount();
//            long segmentActivityHistoryPK = segmentActivityHistoryVO[i].getSegmentActivityHistoryPK();
//            ChargeVO[] chargeVO = segmentActivityHistoryVO[i].getChargeVO();
//            AllocationActivityHistoryVO[] allocationActivityHistoryVO = segmentActivityHistoryVO[i].getAllocationActivityHistoryVO();
//            String statusInd = segmentActivityHistoryVO[i].getStatus();
//
//            if (statusInd != null &&
//                (statusInd.equalsIgnoreCase("N") ||
//                 statusInd.equalsIgnoreCase("A"))) {
//
//                accumFromGrossAmount(trxName, processYear, calendarYear, grossAmount);
//                accumCharges(trxName, chargeVO);
//                accumPayee(trxName,
//                            processYear,
//                             calendarYear,
//                              segmentActivityHistoryVO[i],
//                               clientRelationshipVO);
//                accumFundInfo(trxName, statusInd, allocationActivityHistoryVO, allocationVOs);
//                accumTotalDisbursments(segmentActivityHistoryPK,
//                                        suspenseVO,
//                                         trxName,
//                                          processYear,
//                                           calendarYear,
//                                            grossAmount);
//            }
//
//         }
         //  set accums in accumulationvo
//         accumulationVO.setAmountPaidToDate(amountPaidToDate);
//         accumulationVO.setAmountPaidYTD(amountPaidYTD);
//         accumulationVO.setWithdrawalsYTD(withdrawalsYTD);
//         accumulationVO.setLumpSumPaidToDate(lumpSumPaidToDate);
//         accumulationVO.setLumpSumPaidYTD(lumpSumPaidYTD);
//         accumulationVO.setTotalDisbursements(totalDisbursements);
//         setPayeeAccums(accumulationVO);
//         setAllocationHistoryAccums(accumulationVO);
//
//         return accumulationVO;
//    }
//
//    private void accumFromGrossAmount(String trxName, String processYear,
//                                       String calendarYear, double grossAmount)
//                                      throws Exception {
//
//        if (trxName.equalsIgnoreCase("Payout")) {
//
//            amountPaidToDate = amountPaidToDate + grossAmount;
//
//            if (processYear.equals(calendarYear)) {
//
//                amountPaidYTD = amountPaidYTD + grossAmount;
//            }
//        }
//
//        else if (trxName.equalsIgnoreCase("LumpSum")) {
//
//            lumpSumPaidToDate = lumpSumPaidToDate + grossAmount;
//
//            if (processYear.equals(calendarYear)) {
//
//                lumpSumPaidYTD = lumpSumPaidYTD + grossAmount;
//            }
//        }
//
//        else if (trxName.equalsIgnoreCase("Withdrawal")) {
//
//            if (processYear.equals(calendarYear)) {
//
//                withdrawalsYTD = withdrawalsYTD + grossAmount;
//            }
//        }
//    }
//
//    private void accumCharges(String trxName, ChargeVO[] chargeVO) throws Exception {
//
//        ht = new HashMap();
//        String chargeName = null;
//        if ((trxName.equalsIgnoreCase("Payout")) ||
//           (trxName.equalsIgnoreCase("LumpSum")) ||
//           (trxName.equalsIgnoreCase("Withdrawal")) ||
//           (trxName.equalsIgnoreCase("FullSurrender")) ||
//           (trxName.equalsIgnoreCase("ForcedPayout"))) {
//
//            for (int i = 0; i < chargeVO.length; i++) {
//
//                chargeName = chargeVO[i].getChargeType();
//                if (chargeName.equalsIgnoreCase("PremiumTax")){
//                    ;
//                }
//                else {
//                    getCharges(chargeName,chargeVO[i]);
//                }
//            }
//
//            accumulationVO.setAccumLoads(accumLoads);
//            accumulationVO.setAccumFees(accumFees);
//        }
//    }
//
//    private void getCharges(String chargeName, ChargeVO chargeVO) throws Exception{
//
//        if (chargeName.equalsIgnoreCase("TransactionFee")) {
//
//            accumFees = accumFees + chargeVO.getAmount();
//        }
//        else {
//            accumLoads = accumLoads + chargeVO.getAmount();
//        }
//    }
//
//    private void accumPayee(String trxName, String calendarYear, String processYear,
//                             SegmentActivityHistoryVO segmentActivityHistoryVO,
//                              ClientRelationshipVO[] clientRelationshipVO)
//                            throws Exception {
//
//        String reversalInd = segmentActivityHistoryVO.getStatus();
//
//        if ((trxName.equalsIgnoreCase("Payout")) ||
//           (trxName.equalsIgnoreCase("ForcedPayout")) ||
//           (trxName.equalsIgnoreCase("LumpSum")) ||
//           (trxName.equalsIgnoreCase("Withdrawal")) ||
//           (trxName.equalsIgnoreCase("FullSurrender")) ||
//           (trxName.equalsIgnoreCase("Refund")) ||
//           (trxName.equalsIgnoreCase("Death")))   {
//
//            Long clientKey = null;
//            long clientId = segmentActivityHistoryVO.getClientFK();
//
//            for (int i = 0; i < clientRelationshipVO.length; i++) {
//
//                String typeName = clientRelationshipVO[i].getRelationshipType();
//
//                if (typeName.equalsIgnoreCase("Payee")) {
//
//                    if (clientRelationshipVO[i].getClientFK() == clientId) {
//
//                        clientKey = new Long(clientId);
//                        payeeVector = (List) payeeHt.get(clientKey);
//                        if (payeeVector == null) {
//                            payeeVector = new ArrayList();
//                            payeeAmountPaidToDate  = 0;
//                            payeeAmountPaidYTD     = 0;
//                            payeeFedWithholding    = 0;
//                            payeeStateWithholding  = 0;
//                            payeeCityWithholding   = 0;
//                            payeeCountyWithholding = 0;
//                        }
//
//                        else {
//
//                            payeeAmountPaidToDate  = ((Double)payeeVector.get(0)).doubleValue();
//                            payeeAmountPaidYTD     = ((Double)payeeVector.get(1)).doubleValue();
//                            payeeFedWithholding    = ((Double)payeeVector.get(2)).doubleValue();
//                            payeeStateWithholding  = ((Double)payeeVector.get(3)).doubleValue();
//                            payeeCityWithholding   = ((Double)payeeVector.get(4)).doubleValue();
//                            payeeCountyWithholding = ((Double)payeeVector.get(5)).doubleValue();
//                        }
//
//                        if (reversalInd.equalsIgnoreCase("N") ||
//                            reversalInd.equalsIgnoreCase("A")) {
//
//                            if (trxName.equalsIgnoreCase("Payout")) {
//
//                                payeeAmountPaidToDate += grossAmount;
//                            }
//
//                            if (processYear.equals(calendarYear)) {
//
//                                if (trxName.equalsIgnoreCase("Payout")) {
//
//                                    payeeAmountPaidYTD      += grossAmount;
//                                }
//
//                                payeeFedWithholding     += segmentActivityHistoryVO.getFederalWithholding();
//                                payeeStateWithholding   += segmentActivityHistoryVO.getStateWithholding();
//                                payeeCityWithholding    += segmentActivityHistoryVO.getCityWithholding();
//                                payeeCountyWithholding  += segmentActivityHistoryVO.getCountyWithholding();
//                            }
//                        }
//
//                        payeeVector.add(0, new Double(payeeAmountPaidToDate));
//                        payeeVector.add(1, new Double(payeeAmountPaidYTD));
//                        payeeVector.add(2, new Double(payeeFedWithholding));
//                        payeeVector.add(3, new Double(payeeStateWithholding));
//                        payeeVector.add(4, new Double(payeeCityWithholding));
//                        payeeVector.add(5, new Double(payeeCountyWithholding));
//                        payeeHt.put(clientKey, payeeVector);
//                    }    //end if clientid match
//                }  //end if payee
//            }  //end inner for clrel
//        }
//    }

//    private void accumFundInfo(String trxName, String statusInd,
//                                AllocationActivityHistoryVO[] allocationActivityHistoryVO,
//                                 AllocationVO[] allocationVO)
//                                    throws Exception {
//
//        if  ((trxName.equalsIgnoreCase("Payout")) ||
//            (trxName.equalsIgnoreCase("Premium")) ||
//            (trxName.equalsIgnoreCase("Withdrawal")) ||
//            (trxName.equalsIgnoreCase("FullSurrender")) ||
//            (trxName.equalsIgnoreCase("LumpSum")) ||
//            (trxName.equalsIgnoreCase("ForcedPayout")) ||
//            (trxName.equalsIgnoreCase("Refund")) ||
//            (trxName.equalsIgnoreCase("Death")))   {
//
//            double interest     = 0;
//            long fundId = 0;
//            Long fundKey = null;
//            for (int i = 0; i < allocationActivityHistoryVO.length; i++) {
//
//                interest = allocationActivityHistoryVO[i].getInterestEarnedGuar() +
//                           allocationActivityHistoryVO[i].getInterestEarnedCurr();
//
//                fundId = matchAllocationId(allocationActivityHistoryVO[i], allocationVO);
//
////                fundId = allocationVO[0].getFundFK();
//                fundKey = new Long(fundId);
//                allocVector = (List) allocHt.get(fundKey);
//
//                if (allocVector == null) {
//
//                    allocVector = new ArrayList();
//                    accumDollars = 0;
//                    accumUnits = 0;
//                }
//                else {
//
//                    accumDollars  = ((Double)allocVector.get(0)).doubleValue();
//                    accumUnits    = ((Double)allocVector.get(1)).doubleValue();
//                }
//
//                if (trxName.equalsIgnoreCase("Premium"))
//                    if (statusInd.equals("N") ||
//                        statusInd.equals("A") ||
//                        statusInd.equals("D")) {
//
//                        accumDollars = accumDollars + allocationActivityHistoryVO[i].getDollars() + interest;
//                        accumUnits   = accumUnits   + allocationActivityHistoryVO[i].getUnits();
//                    }
//                    else  if (statusInd.equals("U") ||
//                              statusInd.equals("R")) {
//
//                        accumDollars = accumDollars - allocationActivityHistoryVO[i].getDollars() + interest;
//                        accumUnits   = accumUnits   - allocationActivityHistoryVO[i].getUnits();
//                    }
//
//                if  ((trxName.equalsIgnoreCase("Payout")) ||
//                    (trxName.equalsIgnoreCase("Withdrawal")) ||
//                    (trxName.equalsIgnoreCase("FullSurrender")) ||
//                    (trxName.equalsIgnoreCase("LumpSum")) ||
//                    (trxName.equalsIgnoreCase("ForcedPayout")) ||
//                    (trxName.equalsIgnoreCase("Refund")) ||
//                    (trxName.equalsIgnoreCase("Death")))   {
//
//                    if (statusInd.equals("N") ||
//                        statusInd.equals("A") ||
//                        statusInd.equals("D")) {
//
//                        accumDollars = accumDollars - allocationActivityHistoryVO[i].getDollars() + interest;
//                        accumUnits   = accumUnits   - allocationActivityHistoryVO[i].getUnits();
//                    }
//                    else  if (statusInd.equals("U") ||
//                              statusInd.equals("R")) {
//
//                        accumDollars = accumDollars + allocationActivityHistoryVO[i].getDollars() + interest;
//                        accumUnits   = accumUnits   + allocationActivityHistoryVO[i].getUnits();
//                    }
//                }
//
//                allocVector.add(new Double(accumDollars));
//                allocVector.add(new Double(accumUnits));
//                allocHt.put(fundKey, allocVector);
//            }
//        }
//    }

    private void accumTotalDisbursments(long segmentActivityHistoryPK,
                                       SuspenseVO[] suspenseVO,
                                        String trxName,
                                         String processYear,
                                          String calendarYear,
                                           double grossAmount) throws Exception {

           for (int k = 0; k < suspenseVO.length; k++) {

//                long segmentActivityHistoryFK = suspenseVO[k].getSegmentActivityHistoryFK();

//                if ((segmentActivityHistoryPK == segmentActivityHistoryFK)  &&
//                    (processYear.equals(calendarYear))   &&
//                    ((trxName.equalsIgnoreCase("Payout")) ||
//                    (trxName.equalsIgnoreCase("Withdrawal")) ||
//                    (trxName.equalsIgnoreCase("FullSurrender")) ||
//                    (trxName.equalsIgnoreCase("LumpSum")) ||
//                    (trxName.equalsIgnoreCase("ForcedPayout")) ||
//                    (trxName.equalsIgnoreCase("Refund")) ||
//                    (trxName.equalsIgnoreCase("Death"))))   {
//
//                    totalDisbursements     = totalDisbursements    + grossAmount;
//                }
            }   // end for suspense
    }

//    private void setPayeeAccums(AccumulationVO accumulationVO) {
//
//        PayeeAccumsVO payeeAccums = null;
//
//        Iterator keys = payeeHt.keySet().iterator();
//
//        while (keys.hasNext()) {
//
//            payeeAccums = new PayeeAccumsVO();
//
//            Long clientId = (Long) keys.next();
//
//            payeeVector = (List)payeeHt.get(clientId);
//
//            payeeAccums.setClientFK(clientId.longValue());
//
//            payeeAccums.setAmountPaidToDate(((Double)(payeeVector.get(0))).doubleValue());
//            payeeAccums.setAmountPaidYTD(((Double)(payeeVector.get(1))).doubleValue());
//            payeeAccums.setFederalWithholdingYTD(((Double)(payeeVector.get(2))).doubleValue());
//            payeeAccums.setStateWithholdingYTD(((Double)(payeeVector.get(3))).doubleValue());
//            payeeAccums.setCityWithholdingYTD(((Double)(payeeVector.get(4))).doubleValue());
//            payeeAccums.setCountyWithholdingYTD(((Double)(payeeVector.get(5))).doubleValue());
//
//            accumulationVO.addPayeeAccumsVO(payeeAccums);
//        }
//    }
//
//    private void setAllocationHistoryAccums(AccumulationVO accumulationVO) {
//
//        AllocationHistoryAccumsVO  allocationHistoryAccums = null;
//
//        Iterator keys = allocHt.keySet().iterator();
//        while (keys.hasNext()) {
//
//            allocationHistoryAccums = new AllocationHistoryAccumsVO();
//
//            Long fundId = (Long) keys.next();
//
//            allocVector = (List)allocHt.get(fundId);
//
//            allocationHistoryAccums.setFundFK(fundId.longValue());
//
//            allocationHistoryAccums.setCumulativeDollars(((Double)(allocVector.get(0))).doubleValue());
//            allocationHistoryAccums.setCumulativeUnits(((Double)(allocVector.get(1))).doubleValue());
//
//            accumulationVO.addAllocationHistoryAccumsVO(allocationHistoryAccums);
//        }
//    }

//    private long matchAllocationId(AllocationActivityHistoryVO allocationActivityHistoryVO,
//                                            AllocationVO[] allocationVOs)
//                                           throws Exception {
//
//        long allocationId = allocationActivityHistoryVO.getAllocationFK();
//        long fundFK = 0;
//
//        for (int i = 0; i < allocationVOs.length; i++) {
//
//            if (allocationId == allocationVOs[i].getAllocationPK()) {
//
//                fundFK = allocationVOs[i].getFundFK();
//
//                break;
//            }
//        }
//        return fundFK;
//    }
//   public static void main(String[] args) throws Exception {
//
//         long segmentId = 2;
//         String calendarYear = "2002";
//         LookupComponent lookup = new LookupComponent();
//
//         String accumulationVOXml = lookup.getAllAccumulations(segmentId, calendarYear);
//
//         AccumulationVO accumulationVO = (AccumulationVO)Util.unmarshalVO(AccumulationVO.class, accumulationVOXml);
//         System.out.println("done");
//
//
//    }
}
