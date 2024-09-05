package engine.sp.custom.document;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.vo.BucketChargeHistoryVO;
import edit.common.vo.BucketHistoryVO;
import edit.common.vo.ChargeHistoryVO;
import edit.common.vo.CommissionHistoryVO;
import edit.common.vo.EDITTrxCorrespondenceVO;
import edit.common.vo.EDITTrxVO;
import edit.common.vo.FinancialHistoryVO;
import edit.common.vo.InSuspenseVO;
import edit.common.vo.InvestmentHistoryVO;
import edit.common.vo.LoanSettlementVO;

import edit.common.vo.OverdueChargeSettledVO;
import edit.common.vo.OverdueChargeVO;
import edit.common.vo.ReinsuranceHistoryVO;
import edit.common.vo.SegmentHistoryVO;
import edit.common.vo.WithholdingHistoryVO;

import event.EDITTrx;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;

import javax.jws.WebService;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * A pure copy-paste from NaturalDocument. This code has not been refactored or
 * optimized via Hibernate, etc. due to time constraints.
 */
public class LoanSettlementDocument extends PRASEDocBuilder
{
  /**
   * Name for the EDITTrxPK building param.
   */
  public static final String BUILDING_PARAMETER_NAME_EDITTRXPK = "EDITTrxPK";  
  
  /**
   * Name for the SegmentPK building param.
   */
  public static final String BUILDING_PARAMETER_NAME_SEGMENTPK = "SegmentPK"; 
  
  /**
   * The parameters that will be extracted from working storage to build this document.
   */
  private static final String[] buildingParameterNames = {BUILDING_PARAMETER_NAME_EDITTRXPK, BUILDING_PARAMETER_NAME_SEGMENTPK};
  
  /**
   * The driving EDITTrxPK.
   */
  private Long editTrxPK;
  
  /**
   * The driving SegmentPK.
   */
  private Long segmentPK;

  public LoanSettlementDocument(){}

  /**
   * Constuctor. Assumes that the specified building parameters contains
   * the required parameters of SegmentPK and EDITTrxPK.
   *
   * @param buildingParameters
   *
   * @see
   */
  public LoanSettlementDocument(Map<String, String> buildingParameters)
  {
      super(buildingParameters);

      this.segmentPK = new Long(buildingParameters.get(BUILDING_PARAMETER_NAME_SEGMENTPK));

      this.editTrxPK = new Long(buildingParameters.get(BUILDING_PARAMETER_NAME_EDITTRXPK));
  }

  public void build()
  {
    if (!isDocumentBuilt())
    {
      Element loanSettlementDocVOElement = new DefaultElement(getRootElementName());
      
      EDITTrxVO editTrxVO = EDITTrx.findSeparateBy_PK(getEditTrxPK()).getAsVO(); 
         
      LoanSettlementVO loanSettlementVO = new LoanSettlementVO();
      loanSettlementVO.setDollars(new EDITBigDecimal().getBigDecimal());
      loanSettlementVO.setLoanInterestDollars(new EDITBigDecimal().getBigDecimal());
      loanSettlementVO.setLoanInterestLiabilityPaid(new EDITBigDecimal().getBigDecimal());
      loanSettlementVO.setLoanPrincipalDollars(new EDITBigDecimal().getBigDecimal());

      if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_DEATH) ||
          editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_REDEMPTION))
      {
          populateLoanSettlementVO(loanSettlementVO, editTrxVO, loanSettlementDocVOElement);
      }
      else
      {
          loanSettlementDocVOElement.add(getLoanSettlementVOElement(loanSettlementVO));
      }
      
      setDocumentBuilt(true);
      
      setRootElement(loanSettlementDocVOElement);
    }
  }
  
  private Element getLoanSettlementVOElement(LoanSettlementVO loanSettlementVO)
  {
    Element loanSettlementVOElement = new DefaultElement("LoanSettlementVO");
    
    // LoanInterestLiabilityPaid
    Element loanInterestLiabilityPaidElement = new DefaultElement("LoanInterestLiabilityPaid");
    
    loanInterestLiabilityPaidElement.setText(loanSettlementVO.getLoanInterestLiabilityPaid().toString());
    
    // LoanPrincipalDollars
    Element loanPrincipalDollarsElement = new DefaultElement("LoanPrincipalDollars");
    
    loanPrincipalDollarsElement.setText(loanSettlementVO.getLoanPrincipalDollars().toString());
    
    // LoanInterestDollars
    Element loanInterestDollarsElement = new DefaultElement("LoanInterestDollars");
    
    loanInterestDollarsElement.setText(loanSettlementVO.getLoanInterestDollars().toString());
    
    // Dollars
    Element dollarsElement = new DefaultElement("Dollars");
    
    dollarsElement.setText(loanSettlementVO.getDollars().toString());

    // Add them together.
    loanSettlementVOElement.add(loanInterestLiabilityPaidElement);
    
    loanSettlementVOElement.add(loanPrincipalDollarsElement);
    
    loanSettlementVOElement.add(loanInterestDollarsElement);
    
    loanSettlementVOElement.add(dollarsElement);
    
    return loanSettlementVOElement;
  }

  public String getRootElementName()
  {
    return "LoanSettlementDocVO";
  }
  
  /**
   * For trx type of HDTH and HREM check for loan history and populate the settlementVO.  The HREM is
   * only used when genereated from the Full Surrender transaction.
   * @param loanSettlementVO
   */
  private void populateLoanSettlementVO(LoanSettlementVO loanSettlementVO, EDITTrxVO editTrxVO, Element loanSettlementDocVO)
  {
      boolean trxIsFS = false;

      if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_REDEMPTION))
      {
          trxIsFS = checkOriginatingTrx(editTrxVO.getOriginatingTrxFK());
          if (trxIsFS)
          {
              populateFromBucketHistory(loanSettlementVO);
          }
      }
      else
      {
          populateFromBucketHistory(loanSettlementVO);
      }

      loanSettlementDocVO.add(getLoanSettlementVOElement(loanSettlementVO));
  }  
  
  private void populateFromBucketHistory(LoanSettlementVO loanSettlementVO)
  {
      List voExclusionList = new ArrayList();
      voExclusionList.add(OverdueChargeVO.class);
      voExclusionList.add(OverdueChargeSettledVO.class);
      voExclusionList.add(FinancialHistoryVO.class);
      voExclusionList.add(ChargeHistoryVO.class);
      voExclusionList.add(SegmentHistoryVO.class);
      voExclusionList.add(ReinsuranceHistoryVO.class);
      voExclusionList.add(WithholdingHistoryVO.class);
      voExclusionList.add(CommissionHistoryVO.class);
      voExclusionList.add(InvestmentHistoryVO.class);
      voExclusionList.add(InSuspenseVO.class);
      voExclusionList.add(EDITTrxCorrespondenceVO.class);
      voExclusionList.add(BucketChargeHistoryVO.class);

      EDITTrxVO[] editTrxVOs = EDITTrx.findBySegmentPK_TrxTypes_Status(getSegmentPK(), new String[] {"FS", "DE"}, true, voExclusionList);

      if (editTrxVOs != null)
      {
          for (int i = 0; i < editTrxVOs.length; i++)
          {
              BucketHistoryVO[] bucketHistoryVOs = editTrxVOs[i].getEDITTrxHistoryVO(0).getBucketHistoryVO();
              EDITBigDecimal accumLoanInterestLiability = new EDITBigDecimal();
              for (int j = 0; j < bucketHistoryVOs.length; j++)
              {
                  EDITBigDecimal loanInterestLiability = new EDITBigDecimal(bucketHistoryVOs[j].getLoanInterestLiability());
                  accumLoanInterestLiability = accumLoanInterestLiability.addEditBigDecimal(loanInterestLiability);

                  EDITBigDecimal loanPrincipalDollars = new EDITBigDecimal(bucketHistoryVOs[j].getLoanPrincipalDollars());
                  if (loanPrincipalDollars.isGT("0"))
                  {
                      loanSettlementVO.setDollars(bucketHistoryVOs[j].getDollars());
                      loanSettlementVO.setLoanInterestDollars(bucketHistoryVOs[j].getLoanInterestDollars());
                      loanSettlementVO.setLoanPrincipalDollars(bucketHistoryVOs[j].getLoanPrincipalDollars());
                  }

              }

              if ((new EDITBigDecimal(loanSettlementVO.getLoanPrincipalDollars())).isGT("0"))
              {
                  loanSettlementVO.setLoanInterestLiabilityPaid(accumLoanInterestLiability.getBigDecimal());
              }
          }
      }
  }
  
  private boolean checkOriginatingTrx(long originatingTrxFK)
  {
      boolean trxIsFS = false;
      if (originatingTrxFK != 0)
      {
          EDITTrxVO editTrxVO = EDITTrx.findByPK_UsingCRUD(originatingTrxFK);

          trxIsFS = false;

          if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_FULLSURRENDER))
          {
              trxIsFS = true;
          }
      }

      return trxIsFS;
  }  

  /**
   * Getter.
   * @see #editTrxPK
   * @return
   */
  public Long getEditTrxPK()
  {
    return editTrxPK;
  }

  /**
   * Getter.
   * @see #segmentPK
   * @return
   */
  public Long getSegmentPK()
  {
    return segmentPK;
  }

    public String[] getBuildingParameterNames()
    {
        return buildingParameterNames;
    }
}
