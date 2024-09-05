 <!-- ************* JSP Code ************* -->
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%@ page import="java.util.Map,
                 java.util.Iterator,
                 edit.common.vo.*,
                 java.util.Hashtable,
                 java.util.Enumeration,
                 edit.common.*,
                 fission.utility.*"%>

<%
    String companyName = Util.initString((String) request.getAttribute("CompanyName"), "");

    SeparateAccountValuesReportVO sepAcctValReportVO = (SeparateAccountValuesReportVO) request.getAttribute("SeparateAccountValuesReportVO");
    SeparateAccountValueDetailsVO[] sepAcctValDetailsVOs = sepAcctValReportVO.getSeparateAccountValueDetailsVO();

    EDITDate editRunDate = new EDITDate((String) request.getAttribute("runDate"));
    String monthName = editRunDate.getMonthName();
    String runDay = editRunDate.getFormattedDay();

    String runDate = monthName + " " + runDay + ", " + editRunDate.getFormattedYear();

    sepAcctValDetailsVOs = (SeparateAccountValueDetailsVO[]) Util.sortObjects(sepAcctValDetailsVOs, new String[] {"getFundName"});
%>

<html>
<head>
<title>Separate Account Investment Values By Division</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<!-- ************* Java Script Code ************* -->

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

</head>

<!-- ************* HTML Code ************* -->
<body>
  <% if (!companyName.equals(""))
      {
  %>
    <h4 align="center">
      <%= companyName %>
    </h4>
  <%
      }
  %>

  <h4 align="center">
    Separate Account Investment Values By Division
  </h4>
  <h4 align="center">
    As Of &nbsp;<%= runDate %>
  </h4>

  <table align="left" width="100%" cellpadding="2" cellspacing="4">
    <tr>
      <td align="left"><font size="1">&nbsp;</font></td>
      <td align="left" width="2%"><font size="1">&nbsp;</font></td>
      <td align="left"><font size="1">&nbsp;</font></td>
      <td align="left" width="2%"><font size="1">&nbsp;</font></td>
      <td align="left"><font size="1">&nbsp;</font></td>
      <td align="left" width="2%"><font size="1">&nbsp;</font></td>
      <td align="left"><font size="1">&nbsp;</font></td>
      <td align="left" width="2%"><font size="1">&nbsp;</font></td>
      <td align="left"><font size="1">&nbsp;</font></td>
      <td align="left" width="2%"><font size="1">&nbsp;</font></td>
      <td align="center" colspan="3"><font size="1">Seed Money</font></td>
    </tr>
    <tr>
      <td align="left"><font size="1">Division</font></td>
      <td align="left" width="2%"><font size="1">&nbsp;</font></td>
      <td align="left"><font size="1">Valuation Date</font></td>
      <td align="left" width="2%"><font size="1">&nbsp;</font></td>
      <td align="left"><font size="1">Unit Value</font></td>
      <td align="left" width="2%"><font size="1">&nbsp;</font></td>
      <td align="left"><font size="1">Units</font></td>
      <td align="left" width="2%"><font size="1">&nbsp;</font></td>
      <td align="left"><font size="1">Investment Value</font></td>
      <td align="left" width="2%"><font size="1">&nbsp;</font></td>
      <td align="left"><font size="1">Units</font></td>
      <td align="left" width="2%"><font size="1">&nbsp;</font></td>
      <td align="left"><font size="1">Value</font></td>
    </tr>
    <tr>
      <td align="left" colspan="13"><hr size="2" color="black" noshade></hr></td>
    </tr>
    <%
        Hashtable summaryHT = new Hashtable(); 
        
        String division = "";
        String valuationDate = "";
        String unitValue = "";
        String units = "";
        String investmentValue = "";
        String seedUnits = "0.0000";
        String seedValue = "0";
        EDITBigDecimal mpTotalInvUnits = new EDITBigDecimal();

        EDITBigDecimal overallTotalInvValue = new EDITBigDecimal();

        for (int i = 0; i < sepAcctValDetailsVOs.length; i++)
        {
            division = sepAcctValDetailsVOs[i].getFundNumber() + " - " + sepAcctValDetailsVOs[i].getFundName();

            valuationDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(sepAcctValDetailsVOs[i].getValuationDate());

            unitValue = Util.roundUnits(sepAcctValDetailsVOs[i].getUnitValue(), 7).toString();
            units = Util.roundUnits(sepAcctValDetailsVOs[i].getParticipantUnits(), 4).toString();
            investmentValue = Util.formatDecimal("###,###,##0", Util.roundWithNoCents(sepAcctValDetailsVOs[i].getInvestmentValue()));
    %>
    <tr>
      <td align="left"><font size="1"><%= division %></font></td>
      <td width="2%"><font size="1">&nbsp;</font></td>
      <td align="left"><font size="1"><%= valuationDate %></font></td>
      <td width="2%"><font size="1">&nbsp;</font></td>
      <td align="left"><font size="1"><%= unitValue %></font></td>
      <td width="2%"><font size="1">&nbsp;</font></td>
      <td align="left"><font size="1"><%= units %></font></td>
      <td width="2%"><font size="1">&nbsp;</font></td>
      <td align="left"><font size="1"><%= investmentValue %>
      </font></td>
      <td width="2%"><font size="1">&nbsp;</font></td>
      <td align="left"><font size="1"><%= seedUnits %></font></td>
      <td width="2%"><font size="1">&nbsp;</font></td>
      <td align="left"><font size="1"><%= seedValue %>
      </font></td>
    </tr>
    <%
            if (summaryHT.containsKey(sepAcctValDetailsVOs[i].getMarketingPackageName()))
            {
                mpTotalInvUnits = (EDITBigDecimal) summaryHT.get(sepAcctValDetailsVOs[i].getMarketingPackageName());

                mpTotalInvUnits = mpTotalInvUnits.addEditBigDecimal(sepAcctValDetailsVOs[i].getInvestmentValue());
            }
            else
            {
                mpTotalInvUnits = new EDITBigDecimal(sepAcctValDetailsVOs[i].getInvestmentValue());
            }

            overallTotalInvValue = overallTotalInvValue.addEditBigDecimal(sepAcctValDetailsVOs[i].getInvestmentValue());

            summaryHT.put(sepAcctValDetailsVOs[i].getMarketingPackageName(), mpTotalInvUnits);
        }

        String overallTotalInvValueStr = Util.formatDecimal("###,###,##0", overallTotalInvValue);
    %>
    <tr>
      <td align="left" colspan="13"><hr size="2" color="black" noshade></hr></td>
    </tr>
    <tr>
      <td align="left"><font size="1">
        TOTAL
      </font></td>
      <td width="2%"><font size="1">&nbsp;
      </font></td>
      <td align="left"><font size="1">&nbsp;
      </font></td>
      <td width="2%"><font size="1">&nbsp;
      </font></td>
      <td align="left"><font size="1">&nbsp;
      </font></td>
      <td width="2%"><font size="1">&nbsp;
      </font></td>
      <td align="left"><font size="1">&nbsp;
      </font></td>
      <td width="2%"><font size="1">&nbsp;
      </font></td>
      <td align="left"><font size="1"><%= overallTotalInvValueStr %>
      </font></td>
      <td width="2%"><font size="1">&nbsp;
      </font></td>
      <td align="left"><font size="1">&nbsp;
      </font></td>
      <td width="2%"><font size="1">&nbsp;
      </font></td>
      <td align="left"><font size="1">0
      </font></td>
    </tr>
    <tr></tr>
    <tr></tr>
    <tr></tr>
    <tr></tr>
    <tr></tr>
    <tr></tr>
    <tr>
      <td align="center" colspan="13"><font size="1">Summary By Separate Account</font></td>
    </tr>
    <tr>
      <td align="left" colspan="13"><hr size="2" color="black" noshade></hr></td>
    </tr>
    <%
        Enumeration mktgPkgEnum = summaryHT.keys();

        while (mktgPkgEnum.hasMoreElements())
        {
            String marketingPackage = (String) mktgPkgEnum.nextElement();
            String totalInvestmentValue = Util.formatDecimal("###,###,##0", Util.roundWithNoCents((EDITBigDecimal) summaryHT.get(marketingPackage)));
    %>
    <tr>
      <td class="data"><font size="1">
        <%= marketingPackage %>
      </font></td>
      <td width="2%"><font size="1">&nbsp;
      </font></td>
      <td align="left"><font size="1">&nbsp;
      </font></td>
      <td width="2%"><font size="1">&nbsp;
      </font></td>
      <td align="left"><font size="1">&nbsp;
      </font></td>
      <td width="2%"><font size="1">&nbsp;
      </font></td>
      <td align="left"><font size="1">&nbsp;
      </font></td>
      <td width="2%"><font size="1">&nbsp;
      </font></td>
      <td align="left"><font size="1"><%= totalInvestmentValue %>
      </font></td>
      <td width="2%"><font size="1">&nbsp;
      </font></td>
      <td align="left"><font size="1">&nbsp;
      </font></td>
      <td width="2%"><font size="1">&nbsp;
      </font></td>
      <td align="left"><font size="1">0
      </font></td>
    </tr>
    <%
        }
    %>
    <tr>
      <td align="left" colspan="13"><hr size="2" color="black" noshade></hr></td>
    </tr>
  </table>

</body>
</html>
