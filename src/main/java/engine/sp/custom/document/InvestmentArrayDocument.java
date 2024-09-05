package engine.sp.custom.document;

import businesscalendar.BusinessCalendar;
import businesscalendar.BusinessDay;

import contract.Bucket;
import contract.Investment;
import contract.Segment;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITMap;
import edit.common.vo.FundVO;
import edit.common.vo.GeneralAccountArrayVO;
import edit.common.vo.UnitValuesVO;

import engine.Fund;

import engine.sp.CSCache;

import event.BucketHistory;
import event.EDITTrx;
import event.EDITTrxHistory;
import event.InvestmentHistory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import fission.utility.Util;

import javax.jws.WebService;


/**
 * This class needs to be re-written. The original code comes from NaturalDoc.
 * Other than attempting to retrieve necessary records with limited DB requests,
 * it is near-impossible to optimize this code since there is no documented code
 * to base this on.
 * 
 * The structure is:
 * 
 * InvestmentArrayDocVO
 * InvestmentArrayDocVO.InvestmentArrayVO
 * InvestmentArrayDocVO.GeneralAccountArrayVO
 */
public class InvestmentArrayDocument extends PRASEDocBuilder
{
  /**
   * The driving EDITTrxPK.
   */
  private Long editTrxPK;

  /**
   * The driving EDITTrx;
   */
  private EDITTrx editTrx;

  /**
   * The keye of the EDITTrx building parameter.
   */
  public static final String BUILDING_PARAMETER_NAME_EDITTRXPK = "EDITTrxPK";
  
  /**
   * The parameters that will be extracted from working storage to build this document.
   */
  private static final String[] buildingParametersNames = {BUILDING_PARAMETER_NAME_EDITTRXPK};

  public InvestmentArrayDocument(){}

  /**
   * Constructor.
   * @param editTrxPK
   */
  public InvestmentArrayDocument(Long editTrxPK)
  {
    super(new EDITMap(BUILDING_PARAMETER_NAME_EDITTRXPK, editTrxPK.toString()));

    this.editTrxPK = editTrxPK;
  }

  /**
   * Constructor. The specified building parameters expected to contain
   * the EDITTrxPK.
   * @param buildingParameters
   */
  public InvestmentArrayDocument(Map<String, String> buildingParameters)
  {
    super(buildingParameters);

    this.editTrxPK = new Long(buildingParameters.get(BUILDING_PARAMETER_NAME_EDITTRXPK));
  }

  public void build()
  {
    if (!isDocumentBuilt())
    {
      Element investmentArrayDoc = new DefaultElement(getRootElementName());

      EDITTrx drivingEDITTrx = getEDITTrx();

      Segment segment = Segment.findSeparateBy_EDITTrx_V1(drivingEDITTrx);

      EDITDate currMFEffDate = drivingEDITTrx.getEffectiveDate();
      EDITDate priorMFEffDate = getPriorMFEffDate(currMFEffDate);
      EDITDate editTrxFromDate = priorMFEffDate;
      EDITDate edPolEffDate = segment.getEffectiveDate();

      if (priorMFEffDate.before(edPolEffDate))
      {
        editTrxFromDate = segment.getEffectiveDate();
        editTrxFromDate = editTrxFromDate.subtractDays(1);
        priorMFEffDate = editTrxFromDate;
      }

      EDITDate edPriorMFDate = priorMFEffDate;

      BusinessDay[] businessDays = BusinessDay.findBy_Range_Inclusive(priorMFEffDate, currMFEffDate);

      EDITTrxHistory[] editTrxHistories = EDITTrxHistory.findSeparateBy_SegmentPK_V1(segment, editTrxFromDate, currMFEffDate, "MF");

      Investment[] allInvestments = retrieveAllInvestmentsForSegment(segment);

      engine.business.Lookup engineLookup = new engine.component.LookupComponent();

      Map invArrayVOMap = new TreeMap();

      for (int i = 0; i < allInvestments.length; i++)
      {
        long filteredFundFK = allInvestments[i].getFilteredFundFK();

        // Shoot me - performance is too critical not to use this engine api.
        FundVO fundVO = CSCache.getCSCache().getFundVOBy_FilteredFundPK(filteredFundFK);

        boolean skipInvestment = false;

        //For Loan funds now build and set the GeneralAccountArrayVO
        if (fundVO.getLoanQualifierCT() != null && fundVO.getLoanQualifierCT().equalsIgnoreCase(Fund.NONPREFRRED_LOAN_QUALIFIER))
        {
          GeneralAccountArrayVO[] generalAccountArrayVOs = createGeneralAccountArrayVO(businessDays, editTrxHistories, allInvestments[i], edPriorMFDate);

          buildGeneralAccountArrayVOElement(generalAccountArrayVOs, investmentArrayDoc);

          skipInvestment = true;
        }

        //If contract is "Surrendered" or "FSHedgeFundPend", do not include the holding account
        //in the investmentArray (the holding account will have a Fund.TypeCode value of "System"
        if (segment.getSegmentStatusCT().equalsIgnoreCase("Surrendered") || segment.getSegmentStatusCT().equalsIgnoreCase("FSHedgeFundPend"))
        {
          if (fundVO.getTypeCodeCT().equalsIgnoreCase("System"))
          {
            skipInvestment = true;
          }
        }

        if (!skipInvestment)
        {
          Set buckets = allInvestments[i].getBuckets();
          EDITDate priorUVEffDate = null;
          EDITBigDecimal priorCumUnits = new EDITBigDecimal();
          long priorChargeCodeFK = 0;

          Hashtable bucketsUsed = new Hashtable();

          for (int j = 0; j < businessDays.length; j++)
          {
            EDITDate businessDate = businessDays[j].getBusinessDate();
            EDITDate edBusinessDate = new EDITDate(businessDate.getFormattedDate()); // keep by value
            if (j == 0 && !edBusinessDate.equals(edPriorMFDate))
            {
              buildInvestmentArrayElement(invArrayVOMap, priorMFEffDate, allInvestments[i].getInvestmentPK(), new EDITBigDecimal(), new EDITBigDecimal());

              priorUVEffDate = edPriorMFDate;
            }

            UnitValuesVO[] unitValuesVO = null;

            EDITBigDecimal unitValue = new EDITBigDecimal();

            boolean exactMatchFound = false;
            boolean investmentFoundOnTrx = false;

            EDITBigDecimal cumUnits = new EDITBigDecimal();


            for (int l = 0; l < editTrxHistories.length; l++)
            {
              long chargeCodeFK = 0;
              investmentFoundOnTrx = false;
              List<InvestmentHistory> investmentHistories = new ArrayList<InvestmentHistory>(editTrxHistories[l].getInvestmentHistories());

              // Sort on toFromStatus descending (1st sort ascending)
              Collections.sort(investmentHistories, new Comparator<InvestmentHistory>()
                  {
                    public int compare(InvestmentHistory investmentHistory1, InvestmentHistory investmentHistory2)
                    {
                      String toFromStatus1 = Util.initString(investmentHistory1.getToFromStatus(), "F");

                      String toFromStatus2 = Util.initString(investmentHistory2.getToFromStatus(), "F");

                      return toFromStatus1.compareTo(toFromStatus2);
                    }
                  });

              // (2nd sort descending) We want "froms" before "tos" ???
              Collections.reverse(investmentHistories);

              for (int m = 0; m < investmentHistories.size(); m++)
              {
                if (investmentHistories.get(m).getInvestmentFK().longValue() == allInvestments[i].getInvestmentPK().longValue())
                {
                    if (investmentHistories.get(m).getChargeCodeFK() != null)
                    {
                        chargeCodeFK = investmentHistories.get(m).getChargeCodeFK().longValue();
                    }
                    investmentFoundOnTrx = true;
                    break;
                }
              }

              if (investmentFoundOnTrx)
              {
                unitValuesVO = engineLookup.getUnitValuesByFund_ChargeCode_Date(filteredFundFK, chargeCodeFK, businessDate.getFormattedDate(), "Forward");

                unitValue = new EDITBigDecimal();

                if (unitValuesVO != null && unitValuesVO.length > 0)
                {
                  unitValue = new EDITBigDecimal(unitValuesVO[0].getUnitValue());
                }

                EDITTrx editTrx = editTrxHistories[l].getEDITTrx();
                EDITDate edTrxEffDate = editTrx.getEffectiveDate();
                if (priorUVEffDate != null)
                {
                  if ((editTrx.getEffectiveDate().after(priorUVEffDate) || editTrx.getEffectiveDate().equals(priorUVEffDate)) && editTrx.getEffectiveDate().before(edBusinessDate))
                  {
                    EDITDate nextUVEffDate = priorUVEffDate;
                    int noDaysToGenerate = edTrxEffDate.getElapsedDays(priorUVEffDate) - 1;

                    for (int m = 0; m < noDaysToGenerate; m++)
                    {
                      nextUVEffDate.addDays(1);

                      buildInvestmentArrayElement(invArrayVOMap, nextUVEffDate, allInvestments[i].getInvestmentPK(), priorCumUnits, unitValue);
                    }

                    Set<BucketHistory> bucketHistories = editTrxHistories[l].getBucketHistories();

                    cumUnits = getCumUnitsForInvestmentArray(bucketHistories, buckets, bucketsUsed);

                    buildInvestmentArrayElement(invArrayVOMap, edTrxEffDate, allInvestments[i].getInvestmentPK(), cumUnits, unitValue);

                    priorUVEffDate = edTrxEffDate;
                    priorCumUnits = cumUnits;
                    priorChargeCodeFK = chargeCodeFK;
                  }
                }
                else
                {
                  if (edTrxEffDate.before(edBusinessDate))
                  {
                    Set<BucketHistory> bucketHistories = editTrxHistories[l].getBucketHistories();
                    cumUnits = getCumUnitsForInvestmentArray(bucketHistories, buckets, bucketsUsed);

                    buildInvestmentArrayElement(invArrayVOMap, edTrxEffDate, allInvestments[i].getInvestmentPK(), cumUnits, unitValue);

                    priorUVEffDate = edTrxEffDate;
                    priorCumUnits = cumUnits;
                    priorChargeCodeFK = chargeCodeFK;
                  }
                }

                if (edTrxEffDate.equals(edBusinessDate))
                {
                  exactMatchFound = true;

                  if (priorUVEffDate != null)
                  {
                    EDITDate nextUVEffDate = priorUVEffDate;
                    int noDaysToGenerate = edTrxEffDate.getElapsedDays(priorUVEffDate) - 1;

                    if (noDaysToGenerate > 0)
                    {
                      for (int m = 0; m < noDaysToGenerate; m++)
                      {
                        nextUVEffDate.addDays(1);

                        buildInvestmentArrayElement(invArrayVOMap, nextUVEffDate, allInvestments[i].getInvestmentPK(), priorCumUnits, unitValue);
                      }
                    }
                  }

                  Set<BucketHistory> bucketHistories = editTrxHistories[l].getBucketHistories();
                  cumUnits = getCumUnitsForInvestmentArray(bucketHistories, buckets, bucketsUsed);

                  buildInvestmentArrayElement(invArrayVOMap, businessDate, allInvestments[i].getInvestmentPK(), cumUnits, unitValue);

                  priorUVEffDate = edBusinessDate;
                  priorCumUnits = cumUnits;
                  priorChargeCodeFK = chargeCodeFK;
                }
              }
            }

            if (!exactMatchFound)
            {

              unitValuesVO = engineLookup.getUnitValuesByFund_ChargeCode_Date(filteredFundFK, priorChargeCodeFK, businessDate.getFormattedDate(), "Forward");

              unitValue = new EDITBigDecimal();

              if (unitValuesVO != null && unitValuesVO.length > 0)
              {
                unitValue = new EDITBigDecimal(unitValuesVO[0].getUnitValue());
              }

              buildInvestmentArrayElement(invArrayVOMap, businessDate, allInvestments[i].getInvestmentPK(), priorCumUnits, unitValue);
              priorUVEffDate = edBusinessDate;
            }

          }
        }
      }

      if (!invArrayVOMap.isEmpty())
      {
        Collection<Element> values = invArrayVOMap.values();

        for (Element element: values)
        {
          investmentArrayDoc.add(element);
        }
      }

      setDocumentBuilt(true);

      setRootElement(investmentArrayDoc);
    }
  }

  /**
   * The driving EDITTrx.
   * @return
   */
  public Long getEDITTrxPK()
  {
    return editTrxPK;
  }

  /**
   * Calculates the effective date of the prior MF transaction
   * @param currMFEffDate
   * @return
   */
  private EDITDate getPriorMFEffDate(EDITDate currMFEffDate)
  {
    EDITDate edPriorMFEffDate = currMFEffDate.subtractMonths(1);
    edPriorMFEffDate = edPriorMFEffDate.getEndOfMonthDate();

    BusinessCalendar businessCalendar = new BusinessCalendar();

    BusinessDay businessDay = businessCalendar.getBestBusinessDay(edPriorMFEffDate);

    edPriorMFEffDate = businessDay.getBusinessDate();

    return edPriorMFEffDate;
  }

  /**
   * Gets the driving EDITTrx from the driving Segment composition. There
   * should only be one EDITTrx in the set of EDITTrxs for the instance 
   * Segment.
   * @return
   */
  private EDITTrx getEDITTrx()
  {
    if (editTrx == null)
    {
      editTrx = EDITTrx.findSeparateBy_EDITTrxPK_V1(getEDITTrxPK());
    }

    return editTrx;
  }

  /**
   * Retrieve the EDITTrxHistory records which are part of the Segment datasource.
   * Since the query which built the Segment included the expected date ranges,
   * the existing EDITTrxHistories should be the appropriate EDITTrxHistories.
   * @return the EDITTrxHistories currently associated with the specified segment as the result of a previous hql query
   */
  private EDITTrxHistory[] retrieveEDITTrxHistorys(Segment segment)
  {
    List<EDITTrxHistory> editTrxHistories = new ArrayList<EDITTrxHistory>();

    for (Investment investment: segment.getInvestments())
    {
      for (InvestmentHistory investmentHistory: investment.getInvestmentHistories())
      {
        EDITTrxHistory editTrxHistory = investmentHistory.getEDITTrxHistory();

        editTrxHistories.add(editTrxHistory);
      }
    }

    // EDITTrxHistories are sorted by EDITTrxHistoryPK (ascending).
    Collections.sort(editTrxHistories, new Comparator<EDITTrxHistory>()
        {
          public int compare(EDITTrxHistory editTrxHistory1, EDITTrxHistory editTrxHistory2)
          {
            Long editTrxHistoryPK1 = editTrxHistory1.getEDITTrxHistoryPK();

            Long editTrxHistoryPK2 = editTrxHistory2.getEDITTrxHistoryPK();

            return editTrxHistoryPK1.compareTo(editTrxHistoryPK2);
          }
        });

    return editTrxHistories.toArray(new EDITTrxHistory[editTrxHistories.size()]);
  }

  /**
   * A convenience method that returns the associated Investments for the
   * specified Segment. Like other entities in the specified Segment, these
   * entities should already by filtered by the appropriate date ranges, except for
   * the fund type they are associated with. For this reason, any Investment that is 
   * associated with a "System" Fund is filtered-out.
   * @param segment
   * @return
   */
  public Investment[] retrieveAllInvestmentsForSegment(Segment segment)
  {
    List<Investment> nonSystemInvestments = new ArrayList<Investment>();

    Set<Investment> investments = segment.getInvestments();

    for (Investment investment: investments)
    {
      if (!investment.isSystemFund() ||
          investment.isLoanFund())
      {
        nonSystemInvestments.add(investment);
      }
    }

    return nonSystemInvestments.toArray(new Investment[nonSystemInvestments.size()]);
  }

  /**
   * The driving EDITTrx really drives little more than the appropriate date
   * ranges for this historical transactions sought AND the driving Segment.
   * This InvestmentDocument is very much a Segment-level document and not
   * a EDITTrx-level document.
   * @return the driving Segment specified by the driving EDITTrxPK
   * @see #editTrx
   */
  private Long getSegmentPK()
  {
    Long segmentPK = Segment.findSeparateBy_EDITTrxPK(getEDITTrxPK());

    return segmentPK;
  }

  /**
   * Sums up the cumUnits for all of the given bucketHistory records 
   * @param bucketHistories
   * @param buckets
   * @return
   */
  private EDITBigDecimal getCumUnitsForInvestmentArray(Set<BucketHistory> bucketHistories, Set<Bucket> buckets, Hashtable bucketsUsed)
  {
    EDITBigDecimal cumUnits = new EDITBigDecimal();
    List bucketFKs = new ArrayList();
    for (BucketHistory bucketHistory: bucketHistories)
    {
      long bucketFK = bucketHistory.getBucketFK();

      for (Bucket bucket: buckets)
      {
        if (bucketFK == bucket.getBucketPK())
        {
          bucketFKs.add(bucketFK + "");
          cumUnits = cumUnits.addEditBigDecimal(bucketHistory.getCumUnits());
          bucketsUsed.put(bucketFK + "", bucketHistory.getCumUnits());
        }
      }
    }

    Enumeration bucketKeys = bucketsUsed.keys();
    boolean bucketFound = false;
    while (bucketKeys.hasMoreElements())
    {
      bucketFound = false;
      String bucketKey = (String) bucketKeys.nextElement();
      for (int i = 0; i < bucketFKs.size(); i++)
      {
        if (bucketFKs.get(i).equals(bucketKey))
        {
          bucketFound = true;
          i = bucketFKs.size();
        }
      }

      if (!bucketFound)
      {
        cumUnits = cumUnits.addEditBigDecimal((EDITBigDecimal) bucketsUsed.get(bucketKey));
      }
    }

    return cumUnits;
  }

  /**
   * Util method to map the specified values to an InvestmentArrayVO. The resulting VO is added to the specified invArrayVOMap.
   * @param unitValueDate
   * @param investmentFK
   * @param cumUnits
   * @param unitValue
   * @return
   */
  private void buildInvestmentArrayElement(Map invArrayVOMap, EDITDate unitValueDate, Long investmentFK, EDITBigDecimal cumUnits, EDITBigDecimal unitValue)
  {
    Element investmentArrayElement = new DefaultElement("InvestmentArrayVO");

    // Unit Value Date
    Element unitValueDateElement = new DefaultElement("UnitValueDate");

    unitValueDateElement.setText(unitValueDate.getFormattedDate());

    // InvestmentFK
    Element investmentFKElement = new DefaultElement("InvestmentFK");

    investmentFKElement.setText(investmentFK.toString());

    // Cum Units
    Element cumUnitsElement = new DefaultElement("CumUnits");

    cumUnitsElement.setText(cumUnits.getBigDecimal().toString());

    // Unit Value
    Element unitValueElement = new DefaultElement("UnitValue");

    unitValueElement.setText(unitValue.getBigDecimal().toString());

    // Add them together...
    investmentArrayElement.add(unitValueDateElement);

    investmentArrayElement.add(investmentFKElement);

    investmentArrayElement.add(cumUnitsElement);

    investmentArrayElement.add(unitValueElement);

    invArrayVOMap.put(investmentFK + "_" + unitValueDate.getFormattedDate(), investmentArrayElement);
  }

  /**
   * 
   * @return
   */
  public String getRootElementName()
  {
    return "InvestmentArrayDocVO";
  }

  public GeneralAccountArrayVO[] createGeneralAccountArrayVO(BusinessDay[] businessDays, EDITTrxHistory[] editTrxHistories, Investment investment, EDITDate edPriorMFDate)
  {
    Map generalAccountArrayVOMap = new TreeMap();

    EDITBigDecimal previousCumDollars = new EDITBigDecimal();

    Set<Bucket> buckets = investment.getBuckets();

    for (int i = 0; i < businessDays.length; i++)
    {
      EDITDate businessDate = businessDays[i].getBusinessDate();

      GeneralAccountArrayVO generalAccountArrayVO = new GeneralAccountArrayVO();

      generalAccountArrayVO.setInvestmentFK(investment.getInvestmentPK().longValue());

      if (i == 0 && !businessDate.equals(edPriorMFDate))
      {
        generalAccountArrayVO.setDollars(new EDITBigDecimal().getBigDecimal());
        generalAccountArrayVO.setCumDollars(new EDITBigDecimal().getBigDecimal());
        generalAccountArrayVO.setBusinessDate(businessDate.getFormattedDate());
        generalAccountArrayVOMap.put(investment.getInvestmentPK().longValue() + "_" + businessDate, generalAccountArrayVO);
      }
      else
      {
        if (editTrxHistories != null)
        {
          for (EDITTrxHistory editTrxHistory: editTrxHistories)
          {
            EDITTrx editTrx = editTrxHistory.getEDITTrx();

            EDITDate edTrxEffDate = editTrx.getEffectiveDate();

            generalAccountArrayVO.setBusinessDate(businessDate.getFormattedDate());

            previousCumDollars = matchToBucket(editTrxHistory, buckets, edTrxEffDate, generalAccountArrayVO, previousCumDollars);

            generalAccountArrayVOMap.put(investment.getInvestmentPK().longValue() + "_" + generalAccountArrayVO.getBusinessDate(), generalAccountArrayVO);
          }
        }
      }
    } //end of for loop for business days

    if (businessDays.length > generalAccountArrayVOMap.size())
    {
      for (int i = generalAccountArrayVOMap.size(); i < businessDays.length; i++)
      {
        EDITDate businessDate = businessDays[i].getBusinessDate();

        GeneralAccountArrayVO generalAccountArrayVO = new GeneralAccountArrayVO();

        generalAccountArrayVO.setInvestmentFK(investment.getInvestmentPK().longValue());

        generalAccountArrayVO.setCumDollars(previousCumDollars.getBigDecimal());

        generalAccountArrayVO.setDollars(new EDITBigDecimal().getBigDecimal());

        generalAccountArrayVO.setBusinessDate(businessDate.getFormattedDate());

        generalAccountArrayVOMap.put(investment.getInvestmentPK().longValue() + "_" + businessDate, generalAccountArrayVO);
      }
    }

    List generalAccountArrayVOList = new ArrayList();

    if (!generalAccountArrayVOMap.isEmpty())
    {
      Set keys = generalAccountArrayVOMap.keySet();

      Iterator it = keys.iterator();

      while (it.hasNext())
      {
        String mapKey = (String) it.next();

        generalAccountArrayVOList.add(generalAccountArrayVOMap.get(mapKey));
      }
    }

    return (GeneralAccountArrayVO[]) generalAccountArrayVOList.toArray(new GeneralAccountArrayVO[generalAccountArrayVOList.size()]);
  }


  private EDITBigDecimal matchToBucket(EDITTrxHistory editTrxHistory, Set<Bucket> buckets, EDITDate edTrxEffDate, GeneralAccountArrayVO generalAccountArrayVO, EDITBigDecimal previousCumDollars)
  {
    Set<BucketHistory> bucketHistories = editTrxHistory.getBucketHistories();

    EDITDate businessDate = new EDITDate(generalAccountArrayVO.getBusinessDate());

    EDITBigDecimal accumCumDollars = new EDITBigDecimal();

    for (Bucket bucket: buckets)
    {
      for (BucketHistory bucketHistory: bucketHistories)
      {
        if (bucket.getBucketPK().longValue() == bucketHistory.getBucketFK().longValue())
        {
          if (businessDate.equals(edTrxEffDate))
          {
            accumCumDollars = accumCumDollars.addEditBigDecimal(bucketHistory.getCumDollars());

            EDITBigDecimal dollars = bucketHistory.getDollars();

            String toFromStatus = bucketHistory.getToFromStatus();

            if (toFromStatus != null && toFromStatus.equalsIgnoreCase("F") && !dollars.isEQ("0"))
            {
              dollars = dollars.multiplyEditBigDecimal("-1");
            }

            generalAccountArrayVO.setCumDollars(accumCumDollars.getBigDecimal());

            generalAccountArrayVO.setDollars(dollars.getBigDecimal());

            previousCumDollars = accumCumDollars;
          }
          else if (businessDate.before(edTrxEffDate))
          {
            generalAccountArrayVO.setCumDollars(previousCumDollars.getBigDecimal());

            generalAccountArrayVO.setDollars(new EDITBigDecimal().getBigDecimal());
          }
        }
        else
        {
          generalAccountArrayVO.setCumDollars(previousCumDollars.getBigDecimal());

          generalAccountArrayVO.setDollars(new EDITBigDecimal().getBigDecimal());
        }
      }
    }

    return previousCumDollars;
  }

  /**
   * Converts the specified VOs to their DOM4J equivalent and adds them to the parent Element of InvestmentArrayDocVO.
   * @param generalAccountArrayVOs
   * @param investmentArrayDoc
   */
  private void buildGeneralAccountArrayVOElement(GeneralAccountArrayVO[] generalAccountArrayVOs, Element investmentArrayDoc)
  {
    for (GeneralAccountArrayVO generalAccountArrayVO: generalAccountArrayVOs)
    {
      Element generalAccountArrayVOElement = new DefaultElement("GeneralAccountArrayVO");

      // CumDollars
      Element cumDollarsElement = new DefaultElement("CumDollars");

      cumDollarsElement.setText(generalAccountArrayVO.getCumDollars().toString());

      // Dollars
      Element dollarsElement = new DefaultElement("Dollars");

      dollarsElement.setText(generalAccountArrayVO.getDollars().toString());

      // BusinessDate
      Element businessDateElement = new DefaultElement("BusinessDate");

      businessDateElement.setText(generalAccountArrayVO.getBusinessDate());

      // InvestmentFK
      Element investmentFKElement = new DefaultElement("InvestmentFK");

      investmentFKElement.setText(generalAccountArrayVO.getInvestmentFK() + "");

      // Add all to the parent generalAccountArrayVOElement
      generalAccountArrayVOElement.add(cumDollarsElement);

      generalAccountArrayVOElement.add(dollarsElement);

      generalAccountArrayVOElement.add(businessDateElement);

      generalAccountArrayVOElement.add(investmentFKElement);

      // Add this generalAccountArrayVOElement to its parent investmentArrayDoc
      investmentArrayDoc.add(generalAccountArrayVOElement);
    }
  }

    public String[] getBuildingParameterNames()
    {
        return buildingParametersNames;
    }
}

