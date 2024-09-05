<%@ page import="fission.utility.Util,
                 java.math.BigDecimal,
                 edit.common.EDITDate,
                 edit.common.vo.*,
                 event.business.Event,
                 event.component.EventComponent,
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
                 test.webservice.quote.rpc.client.NewBusinessQuoteStub,
                 java.util.Calendar"%>
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
    //Scenario
    String scenario = request.getParameter("scenario");

    // Quote
    String quoteDate = Util.initString(request.getParameter("quoteDate"), "");

    // Segment
    String companyStructureId = request.getParameter("companyStructureId");
    // CompanyStructureVO companyStructureVO = new CompanyStructureDAO().findByCompanyStructureId(Long.parseLong(companyStructureId))[0];
    // String companyStructureKey = Util.getCompanyStructure(companyStructureVO, ",");
    String optionId = request.getParameter("optionId");
    String areaId = request.getParameter("areaId");
    String costBasis = request.getParameter("costBasis");
    String purchaseAmount = Util.initString(request.getParameter("purchaseAmount"), "0");
    String effectiveDate = Util.initString(request.getParameter("effectiveDate"), "");
    String startDate = Util.initString(request.getParameter("startDate"), "");
    String postJune1986InvestmentInd = Util.initString(request.getParameter("postJune1986InvestmentInd"), "N");

    // Payout
    String inCertainDuration = Util.initString(request.getParameter("certainDuration"), "0");
    String frequencyId = request.getParameter("frequencyId");
    String paymentAmount = Util.initString(request.getParameter("paymentAmount"), "0");

    // Client
    String birthDate = Util.initString(request.getParameter("birthDate"), null);
    String issueAge = Util.initString(request.getParameter("issueAge"), "0");
    String genderId = request.getParameter("genderId");
    
    String targetEndPoint = "http://seg-appserver:8080/PORTAL/services/NewBusinessQuote";
    
    NewBusinessQuoteStub stub = new NewBusinessQuoteStub(targetEndPoint);

    //Create the request
    NewBusinessQuoteStub.GetNewBusinessQuote quoteRequest = new NewBusinessQuoteStub.GetNewBusinessQuote();

    NewBusinessQuoteStub.NewBusinessQuoteInput input = new NewBusinessQuoteStub.NewBusinessQuoteInput();
    input.setScenario(Integer.parseInt(scenario));
    Calendar quoteDateCalendar = Calendar.getInstance();
    String[] quoteDateParts = Util.fastTokenizer(quoteDate, "/");
    quoteDateCalendar.set(Integer.parseInt(quoteDateParts[2]), Integer.parseInt(quoteDateParts[0])-1, Integer.parseInt(quoteDateParts[1]));
    input.setQuoteDate(quoteDateCalendar);
    // input.setCompanyStructureId(companyStructureKey);
    input.setAnnuityOption(optionId);
    input.setIssueState(areaId);
    input.setFrequency(frequencyId);
    input.setCostBasis(Double.parseDouble(costBasis));
    input.setCertainDuration(Integer.parseInt(inCertainDuration));
    input.setPurchaseAmount(Double.parseDouble(purchaseAmount));
    input.setPaymentAmount(Double.parseDouble(paymentAmount));
    Calendar effectiveDateCalendar = Calendar.getInstance();
    String[] effectiveDateParts = Util.fastTokenizer(effectiveDate, "/");
    effectiveDateCalendar.set(Integer.parseInt(effectiveDateParts[2]), Integer.parseInt(effectiveDateParts[0])-1, Integer.parseInt(effectiveDateParts[1]));
    input.setEffectiveDate(effectiveDateCalendar);
    Calendar startDateCalendar = Calendar.getInstance();
    String[] startDateParts = Util.fastTokenizer(startDate, "/");
    startDateCalendar.set(Integer.parseInt(startDateParts[2]), Integer.parseInt(startDateParts[0])-1, Integer.parseInt(startDateParts[1]));
    input.setStartDate(startDateCalendar);
    input.setPostJune1986InvestmentInd(postJune1986InvestmentInd);
    Calendar birthDateCalendar = Calendar.getInstance();
    if (birthDate != null)
    {
        String[] birthDateParts = Util.fastTokenizer(birthDate, "/");
        birthDateCalendar.set(Integer.parseInt(birthDateParts[2]), Integer.parseInt(birthDateParts[0])-1, Integer.parseInt(birthDateParts[1]));
        input.setBirthDate(birthDateCalendar);
    }
    else
    {
        birthDateCalendar.set(1800, 0, 1);
        input.setBirthDate(birthDateCalendar);
    }
    input.setIssueAge(Integer.parseInt(issueAge));
    input.setGender(genderId);

    quoteRequest.setNewBusinessQuoteInput(input);

    //Invoke the service
    NewBusinessQuoteStub.GetNewBusinessQuoteResponse quoteResponse = stub.getNewBusinessQuote(quoteRequest);
    NewBusinessQuoteStub.NewBusinessQuoteOutput output = quoteResponse.get_return();

    int outCertainDuration = output.getCertainDuration();
    double finalDistributionAmount = output.getFinalDistributionAmount();
    double premiumTaxes = output.getPremiumTaxes();
    double frontEndLoads = output.getFrontEndLoads();
    double fees = output.getFees();
    double totalProjectedAnnuity = output.getTotalProjectedAnnuity();
    double exclusionRatio = output.getExclusionRatio();
    double commutedValue = output.getCommutedValue();
    double yearlyTaxableBenefit = output.getYearlyTaxableBenefit();
    double purchaseAmountOut = output.getPurchaseAmount();
    double paymentAmountOut = output.getPaymentAmount();
%>

<html>
<head>
<title>New Business Quote (RPC) </title>
<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td align="center">
      <h5>New Business Quote (RPC)</h5>
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
        <td align="left" width="25%"><%= birthDate == null ? "" : birthDate%></td>
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
          <td align="right" width="25%" nowrap>Certain Duration:</td>
          <td align="left" width="25%" nowrap>
            <%= outCertainDuration %>
          </td>
          <td align="right" width="25%" nowrap>Final Distribution Amount:</td>
          <td align="left" width="25%">
            <%= Util.formatDecimal("##,###,###,###,##0.00", new EDITBigDecimal(finalDistributionAmount+"")) %>
          </td>
        </tr>
        <tr>
          <td align="right" width="25%" nowrap>Premium Taxes:</td>
          <td align="left" width="25%" nowrap>
            <%= Util.formatDecimal("##,###,###,###,##0.00", new EDITBigDecimal(premiumTaxes+"")) %>
          </td>
          <td align="right" width="25%" nowrap>Front-end Loads:</td>
          <td align="left" width="25%">
            <%= Util.formatDecimal("##,###,###,###,##0.00", new EDITBigDecimal(frontEndLoads+"")) %>
          </td>
        </tr>
        <tr>
          <td align="right" width="25%" nowrap>Fees:</td>
          <td align="left" width="25%">
            <%= Util.formatDecimal("##,###,###,###,##0.00", new EDITBigDecimal(fees+"")) %>
          </td>
          <td align="right" width="25%" nowrap>Total Projected Annuity:</td>
          <td align="left" width="25%" nowrap>
            <%= Util.formatDecimal("##,###,###,###,##0.00", new EDITBigDecimal(totalProjectedAnnuity+"")) %>
          </td>
        </tr>
        <tr>
          <td align="right" width="25%" nowrap>Exclusion Ratio:</td>
          <td align="left" width="25%" nowrap>
            <%= Util.formatDecimal("###,###,##0.000000000", new EDITBigDecimal(exclusionRatio+"")) %>
          </td>
          <td align="right" width="25%" nowrap>Commuted Value:</td>
          <td align="left" width="25%" nowrap>
            <%= Util.formatDecimal("###,###,##0.00", new EDITBigDecimal(commutedValue+"")) %>
          </td>
        </tr>
        <tr>
          <td align="right" width="25%" nowrap>Yearly Taxable Benefit:</td>
          <td align="left" width="25%" nowrap>
            <%= Util.formatDecimal("##,###,###,###,##0.00", new EDITBigDecimal(yearlyTaxableBenefit+"")) %>
          </td>
          <td align="right" width="25%" nowrap>&nbsp;</td>
          <td align="left" width="25%">
            &nbsp;
          </td>
        </tr>
        <tr>
          <td align="right" width="25%" nowrap>Purchase Amount:</td>
          <td align="left" width="25%" nowrap><%= Util.formatDecimal("###,###,##0.00", new EDITBigDecimal(purchaseAmountOut+"")) %></td>
          <td align="right" width="25%" nowrap>Payment Amount:</td>
          <td align="left" width="25%" nowrap><%= Util.formatDecimal("###,###,##0.00", new EDITBigDecimal(paymentAmountOut+"")) %></td>
        </tr>
      </table>
      </fieldset>
    </td>
  </tr>
</table>
</body>
</html>