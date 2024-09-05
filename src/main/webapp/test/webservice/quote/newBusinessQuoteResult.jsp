<%@ page import="fission.utility.Util,
                 java.math.BigDecimal,
                 edit.common.EDITDate,
                 edit.common.vo.*,
                 event.business.Event,
                 event.component.EventComponent,
                 engine.dm.dao.ProductStructureDAO,
                 org.dom4j.DocumentHelper,
                 org.apache.axis2.addressing.EndpointReference,
                 org.apache.axiom.om.*,
                 org.apache.axis2.client.Options,
                 org.apache.axis2.Constants,
                 org.apache.axis2.client.ServiceClient,
                 javax.xml.namespace.QName,
                 fission.utility.XMLUtil,
                 org.apache.axis2.AxisFault,
                 edit.common.EDITBigDecimal,
                 org.apache.axis2.util.UUIDGenerator,
                 edit.services.config.ServicesConfig,
                 webservice.WebServiceUtil,
                 webservice.WebClientUtil"%>
<!--
 * User: sprasad
 * Date: Jul 27, 2006
 * Time: 2:25:05 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->

<%
    // Quote
    String quoteDate = request.getParameter("quoteDate");

    // Segment
    String companyStructureId = request.getParameter("companyStructureId");
    ProductStructureVO companyStructureVO = new ProductStructureDAO().findByProductStructureId(Long.parseLong(companyStructureId))[0];
    String companyStructureKey = Util.getProductStructure(companyStructureVO, ",");
    String optionId = request.getParameter("optionId");
    String areaId = request.getParameter("areaId");
    String costBasis = request.getParameter("costBasis");
    String purchaseAmount = Util.initString(request.getParameter("purchaseAmount"), "0");
    String effectiveDate = request.getParameter("effectiveDate");
    String startDate = Util.initString(request.getParameter("startDate"), null);
    String postJune1986InvestmentInd = Util.initString(request.getParameter("postJune1986InvestmentInd"), "N");

    // Payout
    String inCertainDuration = Util.initString(request.getParameter("certainDuration"), "0");
    String frequencyId = request.getParameter("frequencyId");
    String paymentAmount = Util.initString(request.getParameter("paymentAmount"), "0");

    // Client
    String birthDate = Util.initString(request.getParameter("birthDate"), "");
    String issueAge = Util.initString(request.getParameter("issueAge"), "0");
    String genderId = request.getParameter("genderId");

    org.dom4j.Document inputDocument = DocumentHelper.createDocument();
    org.dom4j.Element newBusQuoteInputElement = inputDocument.addElement("NewBusinessQuoteInput");
    newBusQuoteInputElement.addElement("QuoteDate").setText(quoteDate);
    newBusQuoteInputElement.addElement("CompanyStructureId").setText(companyStructureKey);
    newBusQuoteInputElement.addElement("AnnuityOption").setText(optionId);
    newBusQuoteInputElement.addElement("IssueState").setText(areaId);
    newBusQuoteInputElement.addElement("Frequency").setText(frequencyId);
    newBusQuoteInputElement.addElement("CostBasis").setText(costBasis);
    newBusQuoteInputElement.addElement("CertainDuration").setText(inCertainDuration);
    newBusQuoteInputElement.addElement("PurchaseAmount").setText(purchaseAmount);
    newBusQuoteInputElement.addElement("PaymentAmount").setText(paymentAmount);
    newBusQuoteInputElement.addElement("EffectiveDate").setText(effectiveDate);
    newBusQuoteInputElement.addElement("StartDate").setText(startDate);
    newBusQuoteInputElement.addElement("PostJune1986InvestmentInd").setText(postJune1986InvestmentInd);
    newBusQuoteInputElement.addElement("BirthDate").setText(birthDate);
    newBusQuoteInputElement.addElement("IssueAge").setText(issueAge);
    newBusQuoteInputElement.addElement("GenderType").setText(genderId);

    String host = "localhost";
    String port = "9080";

    org.dom4j.Document result = WebClientUtil.process(inputDocument, "NewBusinessQuote", host, port);
    org.dom4j.Element outputElement = result.getRootElement();

    String outCertainDuration = outputElement.selectSingleNode("CertainDuration").getText();
    String finalDistributionAmount = outputElement.selectSingleNode("FinalDistributionAmount").getText();
    String premiumTaxes = outputElement.selectSingleNode("PremiumTaxes").getText();
    String frontEndLoads = outputElement.selectSingleNode("FrontEndLoads").getText();
    String fees = outputElement.selectSingleNode("Fees").getText();
    String totalProjectedAnnuity = outputElement.selectSingleNode("TotalProjectedAnnuity").getText();
    String exclusionRatio = outputElement.selectSingleNode("ExclusionRatio").getText();
    String commutedValue = outputElement.selectSingleNode("CommutedValue").getText();
    String yearlyTaxableBenefit = outputElement.selectSingleNode("YearlyTaxableBenefit").getText();
    String purchaseAmountOut = outputElement.selectSingleNode("PurchaseAmount").getText();
    String paymentAmountOut = outputElement.selectSingleNode("PaymentAmount").getText();
%>

<html>
<head>
<title>New Business Quote</title>
<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td align="center">
      <h5>New Business Quote</h5>
    </td>
  </tr>
  <tr>
    <td>
      <fieldset>
      <legend>Quote&nbsp;</legend>
      <table width="100%" border="0" cellspacing="0" cellpadding="5">
        <tr>
          <td align="right" width="25%" nowrap>Quote Date:</td>
          <td align="left" width="25%"><%= quoteDate %></td>
          <td align="right" width="25%" nowrap>&nbsp;</td>
          <td align="left" width="25%">&nbsp;</td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <fieldset>
      <legend>Segment&nbsp;</legend>
      <table width="100%" border="0" cellspacing="0" cellpadding="5">
        <tr>
          <td align="right" width="25%" nowrap>Company Structure:</td>
          <td align="left" width="25%"><%= companyStructureKey %></td>
          <td align="right" width="25%" nowrap>Annuity Option:</td>
          <td align="left" width="25%"><%= optionId %></td>
        </tr>
        <tr>
          <td align="right" width="25%" nowrap>IssueState:</td>
          <td align="left" width="25%"><%= areaId%></td>
          <td align="right" width="25%" nowrap>Frequency:</td>
         <td align="left" width="25%"><%= frequencyId%></td>
        </tr>
        <tr>
          <td align="right" width="25%" nowrap>Cost Basis:</td>
          <td align="left" width="25%"><%= Util.formatDecimal("##,###,###,###,##0.00", new BigDecimal(costBasis)) %></td>
          <td align="right" width="25%" nowrap>Certain Period:</td>
          <td align="left" width="25%"><%= inCertainDuration %></td>
        </tr>
        <tr>
          <td align="right" width="25%" nowrap>Purchase Amount:</td>
          <td align="left" width="25%"><%= Util.formatDecimal("##,###,###,###,##0.00", new BigDecimal(purchaseAmount)) %></td>
          <td align="right" width="25%" nowrap>Payment Amount:</td>
          <td align="left" width="25%"><%= Util.formatDecimal("##,###,###,###,##0.00", new BigDecimal(paymentAmount)) %></td>
        </tr>
        <tr>
          <td align="right" width="25%" nowrap>Effective Date:</td>
          <td align="left" width="25%"><%= effectiveDate %></td>
          <td align="right" width="25%" nowrap>Start Date:</td>
          <td align="left" width="25%"><%= startDate %></td>
        </tr>
        <tr>
          <td colspan="4" align="center">
            <input disabled type="checkbox" name="postJune1986InvestmentInd" <%= "N".equals(postJune1986InvestmentInd) ? "" : "checked" %>>
            Post June 1986 Investment
          </td>
        </tr>
      </table>
      </fieldset>
    </td>
  </tr>
  <tr>
    <td>
    <fieldset>
    <legend>Client&nbsp;</legend>
    <table width="100%" border="0" cellspacing="0" cellpadding="5">
      <tr>
        <td align="right" width="25%" nowrap>Birth Date:</td>
        <td align="left" width="25%"><%= birthDate %></td>
        <td align="right" width="25%" nowrap>Issue Age:</td>
        <td align="left" width="25%"><%= issueAge %></td>
      </tr>
      <tr>
        <td align="right" width="25%" nowrap>Gender Type:</td>
        <td align="left" width="25%"><%= genderId %></td>
        <td align="right" width="25%" nowrap>&nbsp;</td>
        <td align="left" width="25%">&nbsp;</td>
      </tr>
    </table>
    </fieldset>
    </td>
  </tr>
  <tr>
    <td>
      <fieldset>
      <legend>Result&nbsp;</legend>
      <table width="100%" border="0" cellspacing="0" cellpadding="5">
        <tr>
          <td align="right" widht="25%" nowrap>Certain Duration:</td>
          <td align="left" widht="25%" nowrap>
            <%= new EDITBigDecimal(outCertainDuration) %>
          </td>
          <td align="right" width="25%" nowrap>Final Distribution Amount:</td>
          <td align="left" width="25%">
            <%= Util.formatDecimal("##,###,###,###,##0.00", new EDITBigDecimal(finalDistributionAmount)) %>
          </td>
        </tr>
        <tr>
          <td align="right" widht="25%" nowrap>Premium Taxes:</td>
          <td align="left" widht="25%" nowrap>
            <%= Util.formatDecimal("##,###,###,###,##0.00", new EDITBigDecimal(premiumTaxes)) %>
          </td>
          <td align="right" width="25%" nowrap>Front-end Loads:</td>
          <td align="left" width="25%">
            <%= Util.formatDecimal("##,###,###,###,##0.00", new EDITBigDecimal(frontEndLoads)) %>
          </td>
        </tr>
        <tr>
          <td align="right" width="25%" nowrap>Fees:</td>
          <td align="left" width="25%">
            <%= Util.formatDecimal("##,###,###,###,##0.00", new EDITBigDecimal(fees)) %>
          </td>
          <td align="right" widht="25%" nowrap>Total Projected Annuity:</td>
          <td align="left" widht="25%" nowrap>
            <%= Util.formatDecimal("##,###,###,###,##0.00", new EDITBigDecimal(totalProjectedAnnuity)) %>
          </td>
        </tr>
        <tr>
          <td align="right" widht="25%" nowrap>Exclusion Ratio:</td>
          <td align="left" widht="25%" nowrap>
            <%= Util.formatDecimal("###,###,##0.000000000", new EDITBigDecimal(exclusionRatio)) %>
          </td>
          <td align="right" widht="25%" nowrap>Commuted Value:</td>
          <td align="left" widht="25%" nowrap>
            <%= Util.formatDecimal("###,###,##0.00", new EDITBigDecimal(commutedValue)) %>
          </td>
        </tr>
        <tr>
          <td align="right" widht="25%" nowrap>Yearly Taxable Benefit:</td>
          <td align="left" widht="25%" nowrap>
            <%= Util.formatDecimal("##,###,###,###,##0.00", new EDITBigDecimal(yearlyTaxableBenefit)) %>
          </td>
          <td align="right" width="25%" nowrap>&nsbp;</td>
          <td align="left" width="25%">
            &nbsp;
          </td>
        </tr>
        <tr>
          <td align="right" widht="25%" nowrap>Purchase Amount:</td>
          <td align="left" widht="25%" nowrap><%= Util.formatDecimal("###,###,##0.00", new EDITBigDecimal(purchaseAmountOut)) %></td>
          <td align="right" width="25%" nowrap>Payment Amount:</td>
          <td align="left" width="25%"><%= Util.formatDecimal("###,###,##0.00", new EDITBigDecimal(paymentAmountOut)) %></td>
        </tr>
      </table>
      </fieldset>
    </td>
  </tr>
</table>
</body>
</html>