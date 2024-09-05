/*
 * User: sdorman
 * Date: Aug 30, 2006
 * Time: 1:17:11 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package webservice.service;

import engine.component.*;
import engine.business.*;

import acord.model.*;
import acord.model.common.*;
import acord.model.tabular.*;
import org.dom4j.*;
import org.apache.axiom.om.*;
import org.apache.axis2.*;

import java.util.*;

import fission.utility.*;
import webservice.*;
import edit.common.*;



/**
 * Gets the interest rates for a given fundNumber, optionCT, interestRateDate, lastValuationDate, and trxEffectiveDate.
 * <P>
 * Returns the following list of parameters:
 * <BR>  GuarDur
 * <BR>  DateCounter
 * <BR>  LastValuationDate (input repeated)
 * <BR>  IntRateDate (input repeated)
 * <BR>  BonusCalcInd
 * <BR>  StopDate
 * <BR>  TrxEffectiveDate (input repeated)
 * <BR>  Option (input repeated)
 * <BR>  FilteredFundId (input repeated)
 * <BR>  Array containing rates and date ranges (StartDate, EndDate, Rate)
 */
public class GetInterestRates implements EDITWebService
{
    private OMElement soapRequest = null;
    private OMElement soapResponse = null;

    private static final String MESSAGE_TYPE = "GetInterestRates";


    /**
     *
     * @param soapRequest
     * @return
     * @throws org.apache.axis2.AxisFault
     */
    public OMElement execute(OMElement soapRequest) throws AxisFault
    {
        this.soapRequest = soapRequest;

        try
        {
            Document acordDocumentInput = WebServiceUtil.getAttachment(soapRequest);

            Document acordDocumentResults = getInterestRates(acordDocumentInput.asXML());

            buildResponse(acordDocumentResults);
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new AxisFault(e);
        }

        return soapResponse;
    }

    public Document getInterestRates(String xmlInput)
    {
        Element criteriaExpressionElement = getCriteriaExpressionElement(xmlInput);

        String companyName = getParameterValue(criteriaExpressionElement, "companyName");
        String marketingPackageName = getParameterValue(criteriaExpressionElement, "marketingPackageName");
        String groupProductName = getParameterValue(criteriaExpressionElement, "groupProductName");
        String areaName = getParameterValue(criteriaExpressionElement, "areaName");
        String businessContractName = getParameterValue(criteriaExpressionElement, "businessContractName");
        String fundNumber = getParameterValue(criteriaExpressionElement, "fundNumber");
        String interestRateDate = getParameterValue(criteriaExpressionElement, "interestRateDate");
        String lastValuationDate = getParameterValue(criteriaExpressionElement, "lastValuationDate");
        String trxEffectiveDate = getParameterValue(criteriaExpressionElement, "trxEffectiveDate");
        String optionCT = getParameterValue(criteriaExpressionElement, "optionCT");

        //  Convert ACORD dates to our format
        EDITDate editInterestRateDate = new ACORDDate(interestRateDate).getEDITDate();
        EDITDate editLastValuationDate = new ACORDDate(lastValuationDate).getEDITDate();
        EDITDate editTrxEffectiveDate = new ACORDDate(trxEffectiveDate).getEDITDate();

        Calculator calculator = new CalculatorComponent();

        List results = calculator.getInterestRates(companyName, marketingPackageName, groupProductName, areaName,
                businessContractName, fundNumber, editInterestRateDate.getFormattedDate(),
                editLastValuationDate.getFormattedDate(), editTrxEffectiveDate.getFormattedDate(), optionCT);

        return convertToACORD(results);
    }

    private String getParameterValue(Element criteriaExpressionElement, String parameterName)
    {
        String parameterValue = "";

        List criteriaList = criteriaExpressionElement.selectNodes("Criteria");

        for (Iterator iterator = criteriaList.iterator(); iterator.hasNext();)
        {
            Element criteriaElement = (Element) iterator.next();

            Node propertyName = criteriaElement.selectSingleNode("PropertyName");

            Node propertyValue = criteriaElement.selectSingleNode("PropertyValue");

            if (propertyName.getText().equals(parameterName))
            {
                parameterValue = propertyValue.getText();

                break;
            }
        }

        return parameterValue;
    }

    /**
     * Finds the root CriteriaExpression in the xml and returns it
     * @param xml
     * @return
     */
    private Element getCriteriaExpressionElement(String xml)
    {
        Document document = null;

        try
        {
            document = XMLUtil.parse(xml);

//            XMLUtil.printDocumentToSystemOut(document);

        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return document.getRootElement();
    }

    public Document convertToACORD(List results)
    {
        ACORDDocument acordDocument = new ACORDDocument();

        XTbML xtbml = createXTbML(results);

        acordDocument.add(xtbml);

//        acordDocument.printDocument();

        return acordDocument.getDocument();
    }

    private XTbML createXTbML(List results)
    {
        XTbML xtbml = new XTbML("InterestRateTable", "Output of getInterestRates", acord.model.tabular.Constants.ContentType.OLI_OTHER);

        Table nameValueTable = createNameValueTable(results);
        Table rateTable = createRatesTable(results);

        xtbml.add(nameValueTable);
        xtbml.add(rateTable);

        return xtbml;
    }

    private Table createNameValueTable(List results)
    {
        Map parameters = (Map) results.get(0);      // name value pairs

        MetaData metaData = new MetaData();
        Values values = new Values();

        metaData.setTableDescription("Output from getInterestRates: Name-value pairs");

        Set keySet = parameters.keySet();

        for (Iterator iterator = keySet.iterator(); iterator.hasNext();)
        {
            String key = (String) iterator.next();

            String value = (String) parameters.get(key);

            if (key.endsWith("Date"))
            {
                //  It is a date field, convert to ACORD standard
                ACORDDate acordDate = new ACORDDate(new EDITDate(value));

                value = acordDate.getFormattedDate();
            }

            Y y = new Y(value, key);

            values.add(y);
        }

        Table table = new Table(metaData, values);

        return table;
    }

    private Table createRatesTable(List results)
    {
        MetaData metaData = new MetaData();
        Values values = new Values();

        metaData.setTableDescription("Output from getInterestRates: start and end date ranges with rates");

        AxisDef startDateAxisDef = new AxisDef("StartDate", acord.model.tabular.Constants.ScaleType.SCALETYPE_DATE);
        AxisDef endDateAxisDef = new AxisDef("EndDate", acord.model.tabular.Constants.ScaleType.SCALETYPE_DATE);

        //  Starts with 1 because already 0th element contains the name-value pairs
        for (int i = 1; i < results.size(); i++)
        {
            Map dateRates = (Map) results.get(i);

            String startDate = (String) dateRates.get("RateEffectiveDate");
            String endDate = (String) dateRates.get("Date");
            String rate = (String) dateRates.get("Rate");

            //  Convert dates to ACORD standard
            ACORDDate acordStartDate = new ACORDDate(new EDITDate(startDate));
            ACORDDate acordEndDate = new ACORDDate(new EDITDate(endDate));

            Axis startDateAxis = new Axis("StartDate", acordStartDate.getFormattedDate());
            Axis endDateAxis = new Axis("EndDate", acordEndDate.getFormattedDate());

            Y yrate = new Y(rate, "1");

            endDateAxis.add(yrate);
            startDateAxis.add(endDateAxis);

            values.add(startDateAxis);      // add outer axis only, other axes are children of outer axis

            if (i == 1)
            {
                startDateAxisDef.setMinScaleDate(acordStartDate.getFormattedDate());
                endDateAxisDef.setMinScaleDate(acordEndDate.getFormattedDate());
            }

            if (i == (results.size()-1))
            {
                startDateAxisDef.setMaxScaleDate(acordStartDate.getFormattedDate());
                endDateAxisDef.setMaxScaleDate(acordEndDate.getFormattedDate());
            }
        }

        metaData.add(startDateAxisDef);
        metaData.add(endDateAxisDef);

        Table table = new Table(metaData, values);

        return table;
    }

    /**
     * Builds response message from result.
     * @param results
     */
    private void buildResponse(Document acordDocumentResults)
    {
        soapResponse = WebServiceUtil.buildSOAPResponse(soapRequest, MESSAGE_TYPE, acordDocumentResults.asXML());
    }
}
