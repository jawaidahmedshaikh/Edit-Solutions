<!--
 * User: sprasad
 * Date: Oct 12, 2006
 * Time: 3:33:10 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%@ page import="edit.services.db.hibernate.SessionHelper,
                 engine.UnitValues,
                 edit.common.EDITBigDecimal,
                 engine.ChargeCode,
                 engine.FilteredFund,
                 engine.Fund,
                 edit.common.EDITDate,
                 java.util.*,
                 fission.utility.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    UnitValues[] unitValuesWithHedge = (UnitValues[]) request.getAttribute("unitValuesWithHedge");

    UnitValues[] unitValuesWithoutHedge = (UnitValues[]) request.getAttribute("unitValuesWithoutHedge");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Hedge Fund Pricing Report</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

function printReport()
{
    var printButton = window.event.srcElement;

    printButton.style.display = 'none';

    print();

    printButton.style.display = 'inline';
}

</script>

<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body>
  <table width="100%" style="border-width:1px;border-style:solid;border-color:black" cellspacing="0" cellpadding="4">
    <tr>
      <td>
    <table id="section1" width="100%" style="border-width:1px;border-style:solid" cellspacing="0" cellpadding="2">
    <%-- Column Headings --%>
    <thead>
      <tr>
        <th width="8%" style="font-size:12px;border-width:1px;border-style:solid" nowrap>
            Fund #
        </th>
        <th width="10%" style="font-size:12px;border-width:1px;border-style:solid" nowrap>
            Charge Code
        </th>
        <th width="26%" style="font-size:12px;border-width:1px;border-style:solid" nowrap>
            Fund Name
        </th>
        <th width="14%" style="font-size:12px;border-width:1px;border-style:solid" nowrap>
            Effective Date
        </th>
        <th width="14%" style="font-size:12px;border-width:1px;border-style:solid" nowrap>
            NAV1
        </th>
        <th width="14%" style="font-size:12px;border-width:1px;border-style:solid" nowrap>
            NAV2
        </th>
        <th width="14%" style="font-size:12px;border-width:1px;border-style:solid" nowrap>
            Unit Value
        </th>
      </tr>
    </thead>
    <tbody>
<%
    if (unitValuesWithHedge != null)
    {
        for (int i = 0; i < unitValuesWithHedge.length; i++)
        {
            UnitValues unitValue = unitValuesWithHedge[i];

            ChargeCode chargeCode = unitValue.getChargeCode();

            FilteredFund filteredFund = unitValue.getFilteredFund();

            Fund fund = filteredFund.getFund();

            long unitValuePK = unitValue.getUnitValuesPK().longValue();

            String fundNumber = filteredFund.getFundNumber();

            String chargeCodeColumnValue = chargeCode == null ? null : chargeCode.getChargeCode();

            String fundName = fund.getName();

            EDITDate effectiveDate = unitValue.getEffectiveDate();

            EDITBigDecimal nav1 = unitValue.getNetAssetValue1();

            EDITBigDecimal nav2 = unitValue.getNetAssetValue2();

            EDITBigDecimal unitValueColumnValue = unitValue.getUnitValue();

%>
        <tr id="<%= unitValuePK %>">
            <td width="8%" style="font-size:11px;border-width:1px;border-style:solid;text-align:center" nowrap>
                <%= fundNumber == null ? "&nbsp;" : fundNumber %>
            </td>
            <td width="10%" style="font-size:11px;border-width:1px;border-style:solid;text-align:center" nowrap>
                <%= chargeCodeColumnValue == null ? "&nbsp;" : chargeCodeColumnValue %>
            </td>
            <td width="26%" style="font-size:11px;border-width:1px;border-style:solid;text-align:left" nowrap>
                <%= fundName == null ? "&nbsp;" : fundName %>&nbsp;
            </td>
            <td width="14%" style="font-size:11px;border-width:1px;border-style:solid;text-align:center" nowrap>
                <%= effectiveDate == null ? "&nbsp;" : DateTimeUtil.formatYYYYMMDDToMMDDYYYY(effectiveDate.getFormattedDate()) %>
            </td>
            <td width="14%" style="font-size:11px;border-width:1px;border-style:solid;text-align:right" nowrap>
                <%= nav1 == null ?  "&nbsp;" : nav1.toString()%>
            </td>
            <td width="14%" style="font-size:11px;border-width:1px;border-style:solid;text-align:right" nowrap>
                <%= nav2 == null ? "&nbsp;" : nav2.toString() %>
            </td>
            <td width="14%" style="font-size:11px;border-width:1px;border-style:solid;text-align:right" nowrap>
                <%= unitValueColumnValue == null ? "&nbsp;" : unitValueColumnValue.toString() %>
            </td>
        </tr>
<%
        }// end for
    } // end if
%>
    </tbody>
    </table>
      </td>
    </tr>
  </table>
  <br>






  <table width="100%" style="border-width:1px;border-style:solid;border-color:black" cellspacing="0" cellpadding="4">
    <tr>
      <td>
    <table id="section1" width="100%" style="border-width:1px;border-style:solid" cellspacing="0" cellpadding="2">
    <%-- Column Headings --%>
    <thead>
      <tr>
        <th width="8%" style="font-size:12px;border-width:1px;border-style:solid" nowrap>
            Fund #
        </th>
        <th width="10%" style="font-size:12px;border-width:1px;border-style:solid" nowrap>
            Charge Code
        </th>
        <th width="26%" style="font-size:12px;border-width:1px;border-style:solid" nowrap>
            Fund Name
        </th>
        <th width="14%" style="font-size:12px;border-width:1px;border-style:solid" nowrap>
            Effective Date
        </th>
        <th width="14%" style="font-size:12px;border-width:1px;border-style:solid" nowrap>
            NAV1
        </th>
        <th width="14%" style="font-size:12px;border-width:1px;border-style:solid" nowrap>
            NAV2
        </th>
        <th width="14%" style="font-size:12px;border-width:1px;border-style:solid" nowrap>
            Unit Value
        </th>
    </tr>
    </thead>
    <tbody>
<%
    if (unitValuesWithoutHedge != null)
    {
        String previousFundName = null;
        
        for (int i = 0; i < unitValuesWithoutHedge.length; i++)
        {
            UnitValues unitValue = unitValuesWithoutHedge[i];

            ChargeCode chargeCode = unitValue.getChargeCode();

            FilteredFund filteredFund = unitValue.getFilteredFund();

            Fund fund = filteredFund.getFund();

            long unitValuePK = unitValue.getUnitValuesPK().longValue();

            String fundNumber = filteredFund.getFundNumber();

            String chargeCodeColumnValue = chargeCode == null ? null : chargeCode.getChargeCode();

            String fundName = fund.getName();

            EDITDate effectiveDate = unitValue.getEffectiveDate();

            EDITBigDecimal nav1 = unitValue.getNetAssetValue1();

            EDITBigDecimal nav2 = unitValue.getNetAssetValue2();

            EDITBigDecimal unitValueColumnValue = unitValue.getUnitValue();
            
            if (fundName.equals(previousFundName))
            {
%>
        <tr id="<%= unitValuePK %>">
            <td width="8%" style="font-size:11px;border-width:1px;border-style:solid;text-align:center" nowrap>
                &nbsp;
            </td>
            <td width="10%" style="font-size:11px;border-width:1px;border-style:solid;text-align:center" nowrap>
                <%= chargeCodeColumnValue == null ? "&nbsp;" : chargeCodeColumnValue %>
            </td>
            <td width="26%" style="font-size:11px;border-width:1px;border-style:solid;text-align:center" nowrap>
                &nbsp;
            </td>
            <td width="14%" style="font-size:11px;border-width:1px;border-style:solid;text-align:center" nowrap>
                <%= effectiveDate == null ? "&nbsp;" : DateTimeUtil.formatYYYYMMDDToMMDDYYYY(effectiveDate.getFormattedDate()) %>
            </td>
            <td width="14%" style="font-size:11px;border-width:1px;border-style:solid;text-align:right" nowrap>
                <%= nav1 == null ?  "&nbsp;" : nav1.toString()%>
            </td>
            <td width="14%" style="font-size:11px;border-width:1px;border-style:solid;text-align:right" nowrap>
                <%= nav2 == null ? "&nbsp;" : nav2.toString() %>
            </td>
            <td width="14%" style="font-size:11px;border-width:1px;border-style:solid;text-align:right" nowrap>
                <%= unitValueColumnValue == null ? "&nbsp;" : unitValueColumnValue.toString() %>
            </td>
        </tr>
        
        <%
            }
            else
            {
        %>

        <tr id="<%= unitValuePK %>">
            <td width="8%" style="font-size:11px;border-width:1px;border-style:solid;text-align:center" nowrap>
                <%= fundNumber == null ? "&nbsp;" : fundNumber %>
            </td>
            <td width="10%" style="font-size:11px;border-width:1px;border-style:solid;text-align:center" nowrap>
                &nbsp;
            </td>
            <td style="font-size:11px;border-width:1px;border-style:solid;text-align:left" colspan="5" nowrap>
                <%= fundName == null ? "&nbsp;" : fundName %>&nbsp;
            </td>
        </tr>
<%
            } // end if fundName.equals(previousFundName)
            previousFundName = fundName;
        }// end for
    } // end if
%>
    </tbody>
    </table>
      </td>
    </tr>
  </table>

  <table width="100%" cellspacing="0" cellpadding="4">
    <tr>
      <td align="right">
        <form>
          <input id="printButton" type="button" name="print" value="  Print  " onClick="javascript:printReport()">
        </form>
      </td>
    </tr>
  </table>
</body>
</html>