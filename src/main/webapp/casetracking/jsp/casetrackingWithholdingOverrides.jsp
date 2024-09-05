<%@ page import="contract.Withholding,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 fission.utility.Util,
                 edit.common.EDITBigDecimal"%><!--
 * User: sprasad
 * Date: Jul 20, 2005
 * Time: 3:56:28 PM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    Long companyStructurePK = (Long) request.getAttribute("companyStructurePK");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] federalWithholdingTypes   = codeTableWrapper.getCodeTableEntries("FEDWITHHOLDINGTYPE", companyStructurePK.longValue());
    CodeTableVO[] stateWithholdingTypes     = codeTableWrapper.getCodeTableEntries("STATEWITHHOLDINGTYPE", companyStructurePK.longValue());
    CodeTableVO[] cityWithholdingTypes      = codeTableWrapper.getCodeTableEntries("CITYWITHHOLDINGTYPE", companyStructurePK.longValue());
    CodeTableVO[] countyWithholdingTypes    = codeTableWrapper.getCodeTableEntries("CNTYWITHHOLDINGTYPE", companyStructurePK.longValue());

    Withholding withholding = (Withholding) session.getAttribute("withholdingOverride");

    String federalWithholdingTypeCT         = (String) Util.initObject(withholding, "FederalWithholdingTypeCT", "");
    EDITBigDecimal federalWithholdingAmount = (EDITBigDecimal) Util.initObject(withholding, "FederalWithholdingAmount", null);
    EDITBigDecimal federalWithholdingPercent = (EDITBigDecimal) Util.initObject(withholding, "FederalWithholdingPercent", null);
    String stateWithholdingTypeCT           = (String) Util.initObject(withholding, "StateWithholdingTypeCT", "");
    EDITBigDecimal stateWithholdingAmount   = (EDITBigDecimal) Util.initObject(withholding, "StateWithholdingAmount", null);
    EDITBigDecimal stateWithholdingPercent  = (EDITBigDecimal) Util.initObject(withholding, "StateWithholdingPercent", null);
    String cityWithholdingTypeCT            = (String) Util.initObject(withholding, "CityWithholdingTypeCT", "");
    EDITBigDecimal cityWithholdingAmount    = (EDITBigDecimal) Util.initObject(withholding, "CityWithholdingAmount", null);
    EDITBigDecimal cityWithholdingPercent   = (EDITBigDecimal) Util.initObject(withholding, "CityWithholdingPercent", null);
    String countyWithholdingTypeCT          = (String) Util.initObject(withholding, "CountyWithholdingTypeCT", "");
    EDITBigDecimal countyWithholdingAmount  = (EDITBigDecimal) Util.initObject(withholding, "CountyWithholdingAmount", null);
    EDITBigDecimal countyWithholdingPercent = (EDITBigDecimal) Util.initObject(withholding, "CountyWithholdingPercent", null);

    String segmentPK = (String) request.getAttribute("segmentPK");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Case Tracking - Withholding Overrides</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        checkForResponseMessage();

        formatCurrency();
    }

    function saveWithholdingOverride()
    {
        sendTransactionAction("CaseTrackingTran", "saveWithholdingOverride", "_self");
    }

    function deleteWithholdingOverride()
    {
        sendTransactionAction("CaseTrackingTran", "deleteWithholdingOverride", "_self");
    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
<%--    BEGIN Form Content --%>
    <tr>
        <td align="right" nowrap>
            Federal W/H Type:
        </td>
        <td align="left" nowrap>
          <select name="federalWithholdingTypeCT">
            <option value="null">Please Select</option>
            <%
              for(int i = 0; i < federalWithholdingTypes.length; i++)
              {
                  String codeDesc    = federalWithholdingTypes[i].getCodeDesc();
                  String code        = federalWithholdingTypes[i].getCode();

                  if (code.equals(federalWithholdingTypeCT))
                  {
                      out.println("<option selected value=\""+ code + "\">" + codeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
              }
            %>
          </select>
        </td>
        <td align="right" nowrap>
            Federal W/H Amt:
        </td>
        <td align="left" nowrap>
            <input type="text" name="federalWithholdingAmount" size="11" maxlength="11" value="<%= federalWithholdingAmount == null ? "" : federalWithholdingAmount.trim() %>" CURRENCY>
        </td>
        <td align="right" nowrap>
            Federal W/H Pct:
        </td>
        <td align="left" nowrap>
            <input type="text" name="federalWithholdingPercent" size="11" maxlength="11" value="<%= federalWithholdingPercent == null ? "" : federalWithholdingPercent.trim() %>">
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            State W/H Type:
        </td>
        <td align="left" nowrap>
          <select name="stateWithholdingTypeCT">
            <option value="null">Please Select</option>
            <%
                for(int i = 0; i < stateWithholdingTypes.length; i++)
                {
                    String codeDesc    = stateWithholdingTypes[i].getCodeDesc();
                    String code        = stateWithholdingTypes[i].getCode();

                    if (code.equals(stateWithholdingTypeCT))
                    {
                        out.println("<option selected value=\""+ code + "\">" + codeDesc + "</option>");
                    }
                    else
                    {
                        out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                    }
                }
            %>
          </select>
        </td>
        <td align="right" nowrap>
            State W/H Amt:
        </td>
        <td align="left" nowrap>
            <input type="text" name="stateWithholdingAmount" size="11" maxlength="11" value="<%= stateWithholdingAmount == null ? "" : stateWithholdingAmount.trim() %>" CURRENCY>
        </td>
        <td align="right" nowrap>
            State W/H Pct:
        </td>
        <td align="left" nowrap>
            <input type="text" name="stateWithholdingPercent" size="11" maxlength="11" value="<%= stateWithholdingPercent == null ? "" : stateWithholdingPercent.trim() %>">
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            City W/H Type:
        </td>
        <td align="left" nowrap>
          <select name="cityWithholdingTypeCT">
            <option value="null">Please Select</option>
            <%
                for(int i = 0; i < cityWithholdingTypes.length; i++)
                {
                    String codeDesc    = cityWithholdingTypes[i].getCodeDesc();
                    String code        = cityWithholdingTypes[i].getCode();

                    if (code.equals(cityWithholdingTypeCT))
                    {
                        out.println("<option selected value=\""+ code + "\">" + codeDesc + "</option>");
                    }
                    else
                    {
                        out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                    }
                }
            %>
          </select>
        </td>
        <td align="right" nowrap>
            City W/H Amt:
        </td>
        <td align="left" nowrap>
            <input type="text" name="cityWithholdingAmount" size="11" maxlength="11" value="<%= cityWithholdingAmount == null ? "" : cityWithholdingAmount.trim() %>" CURRENCY>
        </td>
        <td align="right" nowrap>
            City W/H Pct:
        </td>
        <td align="left" nowrap>
            <input type="text" name="cityWithholdingPercent" size="11" maxlength="11" value="<%= cityWithholdingPercent == null ? "" : cityWithholdingPercent.trim() %>">
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            County W/H Type:
        </td>
        <td align="left" nowrap>
          <select name="countyWithholdingTypeCT">
            <option value="null">Please Select</option>
            <%
                for(int i = 0; i < countyWithholdingTypes.length; i++)
                {
                    String codeDesc    = countyWithholdingTypes[i].getCodeDesc();
                    String code        = countyWithholdingTypes[i].getCode();

                    if (code.equals(countyWithholdingTypeCT))
                    {
                        out.println("<option selected value=\""+ code + "\">" + codeDesc + "</option>");
                    }
                    else
                    {
                        out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                    }
                }
            %>
          </select>
        </td>
        <td align="right" nowrap>
            County W/H Amt:
        </td>
        <td align="left" nowrap>
            <input type="text" name="countyWithholdingAmount" size="11" maxlength="11" value="<%= countyWithholdingAmount == null ? "" : countyWithholdingAmount.trim() %>" CURRENCY>
        </td>
        <td align="right" nowrap>
            County W/H Pct:
        </td>
        <td align="left" nowrap>
            <input type="text" name="countyWithholdingPercent" size="11" maxlength="11" value="<%= countyWithholdingPercent == null ? "" : countyWithholdingPercent.trim() %>">
        </td>
    </tr>
<%--    END Form Content --%>

    <tr class="filler">
        <td colspan="4">
            &nbsp; <!--Filler Row -->
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<br>

<%-- ****************************** BEGIN Summary Area ****************************** --%>
<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" width="33%">
            <input type="button" value=" Save " onClick="saveWithholdingOverride()">
            <input type="button" value="Cancel" onClick="resetForm()">
            <input type="button" value="Delete" onClick="deleteWithholdingOverride()">
        </td>
        <td width="33%">
            &nbsp;
        </td>
        <td width="33%">
            &nbsp;
        </td>
    </tr>
</table>

<%-- Column Headings --%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="25%" nowrap>
            Federal W/H Type
        </td>
        <td width="25%" nowrap>
            State W/H Type
        </td>
        <td width="25%" nowrap>
            City W/H Type
        </td>
        <td width="25%" nowrap>
            County W/H Type
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:30%; top:0; left:0;">
    <table id="tableSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
    if (withholding != null) // Test for the existence of the target VOs.
    {
%>
        <tr class="highlighted" isSelected="true" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
            <td width="25%" nowrap>
                <%= federalWithholdingTypeCT %>
            </td>
            <td width="25%" nowrap>
                <%= stateWithholdingTypeCT %>
            </td>
            <td width="25%" nowrap>
                <%= cityWithholdingTypeCT %>
            </td>
            <td width="25%" nowrap>
                <%= countyWithholdingTypeCT %>
            </td>
        </tr>
<%
    } // end if
%>
        <tr class="filler">
            <td colspan="4">
                &nbsp;
            </td>
        </tr>
    </table>
</span>


<%-- Additional Buttons --%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" width="33%">
            &nbsp;
        </td>
        <td width="33%">
            &nbsp;
        </td>
        <td align="right" width="33%">
              <input type="button" value=" Close " onClick="window.close()">
        </td>
    </tr>
</table>


<%-- ****************************** END Summary Area ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="segmentPK" value="<%= segmentPK %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>