/*
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */
package engine.sp.custom.document;

import contract.Life;
import contract.Segment;

import edit.common.EDITDate;
import edit.common.EDITMap;
import edit.common.vo.HistoryVO;
import edit.common.vo.SegmentVO;
import edit.common.vo.TamraRetestVO;

import event.EDITTrx;
import event.FinancialHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;


/**
 * This is a refactoring of the TamraRetestCode in the NaturalDoc.
 * 
 * Little was done other than the Hibernation of the data.
 */
public class TamraRetestDocument extends PRASEDocBuilder
{
  private Long segmentPK;
  
  private EDITDate drivingTrxEffectiveDate;
  
  /**
   * The key name of the SegmentPK driving parameter.
   */
  public static final String BUILDING_PARAMETER_NAME_SEGMENTPK = "SegmentPK";
  
  /**
   * The key name of the TrxEffectiveDate driving parameter.
   */
  public static final String BUILDING_PARAMETER_NAME_TRXEFFECTIVEDATE = "TrxEffectiveDate";
  
  /**
   * The building parameters extracted from working storage used to build this document.
   */
  private static final String[] buildingParameterNames = {BUILDING_PARAMETER_NAME_SEGMENTPK, BUILDING_PARAMETER_NAME_TRXEFFECTIVEDATE};
  
  public TamraRetestDocument(){}

  public TamraRetestDocument(Long segmentPK, EDITDate trxEffectiveDate)
  {
    super(new EDITMap(BUILDING_PARAMETER_NAME_SEGMENTPK, segmentPK.toString()).put(BUILDING_PARAMETER_NAME_TRXEFFECTIVEDATE, trxEffectiveDate.getFormattedDate()));
  
    this.segmentPK = segmentPK;
    
    this.drivingTrxEffectiveDate = trxEffectiveDate;
  }
  
  /**
   * Constructor. The building paramters is expected to contain the SegmentPK and
   * TrxEffectiveDate building paramters.
   * @param buildingParameters
   */
  public TamraRetestDocument(Map<String, String> buildingParameters)
  {
    super(buildingParameters);
  
    this.segmentPK = new Long(buildingParameters.get(BUILDING_PARAMETER_NAME_SEGMENTPK));
    
    this.drivingTrxEffectiveDate = new EDITDate(buildingParameters.get(BUILDING_PARAMETER_NAME_TRXEFFECTIVEDATE));
  }

  /**
   * Identify the SegmentPK and EDITTrxPK and run the build process. This document
   * has not been refactored for speed.
   */
  public void build()
  {
    if (!isDocumentBuilt())
    {
      Segment segment = Segment.findSeparateBy_SegmentPK(getSegmentPK());
      
      Element tamraRetestDocVOElement = new DefaultElement(getRootElementName());

      TamraRetestVO[] tamraRetestVOs = getTamraRetestVOs((SegmentVO) segment.getVO(), getTrxEffectiveDate());
      
      for (TamraRetestVO tamraRetestVO: tamraRetestVOs)
      {
        buildTamraRetestVOElement(tamraRetestVO, tamraRetestDocVOElement);
      }
      
      setDocumentBuilt(true);
      
      setRootElement(tamraRetestDocVOElement);
    }
  }
  
  /**
   *
   * @param segmentVO
   * @param trxEffectiveDate
   * @return
   */
  public TamraRetestVO[] getTamraRetestVOs(SegmentVO segmentVO, EDITDate trxEffectiveDate)
  {
      boolean trxWithinSevenYears = sevenYearTest(new EDITDate(segmentVO.getEffectiveDate()), trxEffectiveDate);

      TamraRetestVO[] tamraRetestVOs = null;
      long segmentPK = segmentVO.getSegmentPK();
      EDITDate effectiveDate = new EDITDate(trxEffectiveDate);
      if (trxWithinSevenYears)
      {
          tamraRetestVOs = buildWithin7TamraRetestInfo(segmentPK, effectiveDate);
      }
      else
      {
          tamraRetestVOs = buildAfter7TamraRetestInfo(segmentPK, effectiveDate);
      }

      return tamraRetestVOs;
  }  
  
  private TamraRetestVO[] buildWithin7TamraRetestInfo(Long segmentPK, EDITDate effectiveDate)
  {
      TamraRetestVO[] tamraRetestVOs = null;

      EDITTrx[] editTrxs = EDITTrx.findSeparateAllTamraRetest(segmentPK, effectiveDate);

      tamraRetestVOs = buildTamraRetestInfo(editTrxs);

      return tamraRetestVOs;
  }  
  
  /**
   *
   * @param editTrxs
   * @return
   */
  private TamraRetestVO[] buildTamraRetestInfo(EDITTrx[] editTrxs)
  {
      List history = new ArrayList();
      List<TamraRetestVO> tamraRetest = new ArrayList<TamraRetestVO>();
      TamraRetestVO tamraRetestVO = null;

      for (int i = 0; i < editTrxs.length; i++)
      {
          String transactionType = editTrxs[i].getTransactionTypeCT();
          FinancialHistory financialHistory = null;
          String effectiveDate = editTrxs[i].getEffectiveDate().getFormattedDate();

          if (transactionType.equalsIgnoreCase("IS"))
          {
              tamraRetestVO = new TamraRetestVO();
              tamraRetestVO.setTamraStartDate(effectiveDate);
              financialHistory = editTrxs[i].getEDITTrxHistories().iterator().next().getFinancialHistory();
              tamraRetestVO.setAccumulatedValue(financialHistory.getAccumulatedValue().getBigDecimal());
              tamraRetest.add(tamraRetestVO);
          }
          else if (transactionType.equalsIgnoreCase("PY") || transactionType.equalsIgnoreCase("WI"))
          {
              HistoryVO historyVO = new HistoryVO();
              historyVO.setEffectiveDate(effectiveDate);
              financialHistory = editTrxs[i].getEDITTrxHistories().iterator().next().getFinancialHistory();
              historyVO.setAmount(financialHistory.getGrossAmount().getBigDecimal());
              historyVO.setTransactionTypeCT(transactionType);
              history.add(historyVO);
          }
          else if (transactionType.equalsIgnoreCase("CC"))
          {
              Life life = editTrxs[i].getClientSetup().getContractSetup().getSegment().getLife();
              
              boolean startNew7PayIndicatorSet = life.hasStartNew7PayIndicator();
              //if any premium history created an IS trx or another CC created the tamra retest
              if (startNew7PayIndicatorSet)
              {
                  if (!history.isEmpty())
                  {
                      tamraRetestVO.setHistoryVO((HistoryVO[]) history.toArray(new HistoryVO[history.size()]));
                      history = new ArrayList();
                  }

                  tamraRetestVO = new TamraRetestVO();
                  tamraRetestVO.setTamraStartDate(effectiveDate);
                  financialHistory = editTrxs[i].getEDITTrxHistories().iterator().next().getFinancialHistory();
                  tamraRetestVO.setAccumulatedValue(financialHistory.getAccumulatedValue().getBigDecimal());
                  tamraRetest.add(tamraRetestVO);
              }
          }
      }

      //At end of transactions get the last tamra retest into the List
      if (!history.isEmpty())
      {
          tamraRetestVO.setHistoryVO((HistoryVO[]) history.toArray(new HistoryVO[history.size()]));
      }


      return tamraRetest.toArray(new TamraRetestVO[tamraRetest.size()]);
  }  
  
  /**
   * Check the TamraStartDate of the Base segment to determine if the CC qualifies for testing.
   * @param segmentPK
   * @param effectiveDate
   * @return
   */
  private EDITTrx[] afterSevenYearTest(Long segmentPK, EDITDate effectiveDate)
  {
     Life life = Life.findSeparateBy_SegmentPK(segmentPK);
     EDITDate tamraStartDate = life.getTamraStartDate();
     EDITTrx[] editTrxs = null;
     boolean dateWithinSevenYears = sevenYearTest(tamraStartDate, effectiveDate);

     //if the effective date is within 7 years of the tamra start date find the transactions needed for retest
     if (dateWithinSevenYears)
     {
         editTrxs = EDITTrx.findSeparatePartialTamraRetest(segmentPK, effectiveDate);
     }

     return editTrxs;
  }  
  
  private boolean sevenYearTest(EDITDate secondDate, EDITDate trxEffectiveDate)
  {
      boolean trxWithinSevenYears = false;
      int monthsDiff = trxEffectiveDate.getElapsedMonths(secondDate);
      int years = monthsDiff / 12;
      if (years <= 7)
      {
          trxWithinSevenYears = true;
      }

      return trxWithinSevenYears;
  }  
  

  
  private TamraRetestVO[] buildAfter7TamraRetestInfo(Long segmentPK, EDITDate effectiveDate)
  {
      TamraRetestVO[] tamraRetestVOs = null;

      EDITTrx[] editTrxs = afterSevenYearTest(segmentPK, effectiveDate);
      if (editTrxs.length > 0)
      {
          tamraRetestVOs = buildAfter7TamraRetest(editTrxs);
      }

      return tamraRetestVOs;
  }  
  /**
   * Only one tamraRetestVO will be built for the after 7 years test, if there is qualifying data.
   * @param editTrxs
   * @return
   */
  private TamraRetestVO[] buildAfter7TamraRetest(EDITTrx[] editTrxs)
  {
      List history = new ArrayList();
      List<TamraRetestVO> tamraRetest = new ArrayList<TamraRetestVO>();
      TamraRetestVO tamraRetestVO = null;

      for (int i = 0; i < editTrxs.length; i++)
      {
          String transactionType = editTrxs[i].getTransactionTypeCT();
          FinancialHistory financialHistory = null;
          String effectiveDate = editTrxs[i].getEffectiveDate().getFormattedDate();

          if (transactionType.equalsIgnoreCase("PY") || transactionType.equalsIgnoreCase("WI"))
          {
              HistoryVO historyVO = new HistoryVO();
              historyVO.setEffectiveDate(effectiveDate);
              financialHistory = editTrxs[i].getEDITTrxHistories().iterator().next().getFinancialHistory();
              historyVO.setAmount(financialHistory.getGrossAmount().getBigDecimal());
              historyVO.setTransactionTypeCT(transactionType);
              history.add(historyVO);
          }
          else if (transactionType.equalsIgnoreCase("CC"))
          {
              tamraRetestVO = new TamraRetestVO();
              tamraRetestVO.setTamraStartDate(effectiveDate);
              financialHistory = editTrxs[i].getEDITTrxHistories().iterator().next().getFinancialHistory();
              tamraRetestVO.setAccumulatedValue(financialHistory.getAccumulatedValue().getBigDecimal());
          }
      }

      //At end of transactions get the history into tamra retest
      if (!history.isEmpty())
      {
          tamraRetestVO.setHistoryVO((HistoryVO[]) history.toArray(new HistoryVO[history.size()]));
      }

      tamraRetest.add(tamraRetestVO);

      return tamraRetest.toArray(new TamraRetestVO[tamraRetest.size()]);
  }

  public Long getSegmentPK()
  {
    return segmentPK;
  }

  public EDITDate getTrxEffectiveDate()
  {
    return drivingTrxEffectiveDate;
  }

  public String getRootElementName()
  {
    return "TamraRetestDocVO";
  }

  /**
   * Maps the specified TamraRetestVO to its DOM4J equivalent and adds it to the specified parent Element.
   * @param tamraRetestVO
   * @param tamraRetestDocVOElement
   */
  private void buildTamraRetestVOElement(TamraRetestVO tamraRetestVO, Element tamraRetestDocVOElement)
  {
    Element tamraRetestVOElement = new DefaultElement("TamraRetestVO");
    
    // TamraStartDate
    Element tamraStartDateElement = new DefaultElement("TamraStartDate");
    
    tamraStartDateElement.setText(tamraRetestVO.getTamraStartDate());
    
    // AccumulatedValue
    Element accumulatedValueElement = new DefaultElement("AccumulatedValue");
    
    accumulatedValueElement.setText(tamraRetestVO.getAccumulatedValue() + "");
    
    // Add
    tamraRetestVOElement.add(tamraStartDateElement);
    
    tamraRetestVOElement.add(accumulatedValueElement);
    
    tamraRetestDocVOElement.add(tamraRetestVOElement);
    
    buildHistoryVOElement(tamraRetestVO.getHistoryVO(), tamraRetestVOElement);
  }

  private void buildHistoryVOElement(HistoryVO[] historyVOs, Element tamraRetestVOElement)
  {
    for (HistoryVO historyVO: historyVOs)
    {
      Element historyVOElement = new DefaultElement("HistoryVO");
      
      // Amount
      Element amountElement = new DefaultElement("Amount");
      
      amountElement.setText(historyVO.getAmount().toString());
      
      // EffectiveDate
      Element effectiveDateElement = new DefaultElement("EffectiveDate");
      
      effectiveDateElement.setText(historyVO.getEffectiveDate());
      
      // TransactionTypeCT
      Element transactionTypeCTElement = new DefaultElement("TransactionTypeCT");
      
      transactionTypeCTElement.setText(historyVO.getTransactionTypeCT());
      
      // Add
      historyVOElement.add(amountElement);
      
      historyVOElement.add(effectiveDateElement);
      
      historyVOElement.add(transactionTypeCTElement);
      
      tamraRetestVOElement.add(historyVOElement);
    }
  }

    public String[] getBuildingParameterNames()
    {
        return buildingParameterNames;
    }
}
