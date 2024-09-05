<%@ page import="edit.common.vo.CodeTableVO,
                 codetable.dm.dao.CodeTableDAO,
                 contract.Segment,
                 client.ClientDetail,
                 fission.utility.Util,
                 edit.common.CodeTableWrapper"%>
<!--
 * User: dlataille
 * Date: Sept 19, 2007
 * Time: 11:35:16 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    CodeTableVO[] states = codeTableWrapper.getCodeTableEntries("STATE");
    CodeTableVO[] careTypes = codeTableWrapper.getCodeTableEntries("CARETYPE");
    CodeTableVO[] conditions = codeTableWrapper.getCodeTableEntries("CONDITION");
    CodeTableVO[] yesNo = codeTableWrapper.getCodeTableEntries("YESNO");
    Long clientDetailPK = (Long) session.getAttribute("casetracking.selectedClientDetailPK");
    ClientDetail clientDetail = ClientDetail.findByPK(clientDetailPK);
    String responseMessage = Util.initString((String) request.getAttribute("responseMessage"), null);

    String effectiveDate = "";
    String claimType = "";
    String condition = "";
    String authorizedSignatureCT = "";
    String amountOverride = "";
    String interestOverride = "";
    String stateOfDeath = "";
    String residentStateAtDeath = "";

    if (responseMessage != null)
    {
        effectiveDate = Util.initString((String) request.getAttribute("effectiveDate"), "");
        claimType = Util.initString((String)request.getAttribute("claimType"), "");
        condition = Util.initString((String)request.getAttribute("conditionCT"), "");
        authorizedSignatureCT = Util.initString((String)request.getAttribute("authorizedSignatureCT"), "");
        amountOverride = Util.initString((String)request.getAttribute("amountOverride"), "");
        stateOfDeath = (String) Util.initObject(clientDetail, "stateOfDeathCT", "");
        residentStateAtDeath = (String) Util.initObject(clientDetail, "residentStateAtDeathCT", "");
        if (amountOverride.equals("0"))
        {
            amountOverride = "";
        }
        interestOverride = Util.initString((String)request.getAttribute("interestOverride"), "");
        if (interestOverride.equals("0"))
        {
            interestOverride = "";
        }
    }

    String segmentPK    = (String) request.getAttribute("segmentPK");
    Segment segment     = Segment.findByPK(new Long(segmentPK));

    String contractNumber   = segment.getContractNumber();
    String segmentNameCT    = segment.getSegmentNameCT();

    String casetrackingOption = (String) request.getAttribute("casetrackingOption");
    
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Case Tracking - Rider Claim</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>

<script type="text/javascript">

    var f = null;

    var responseMessage = "<%= responseMessage %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        checkForResponseMessage();
    }

    /*
     * Moves rows.
     */
    function moveRows(tableId)
    {
        move(tableId, "CaseTrackingTran", "updateRiderDetailDoubleTable", "_self");
    }

    function saveRiderClaim()
    {
       try
       {
           if (f.conditionCT.selectedIndex == 0)
           {
                f.conditionCT.value = "";
           }

           if (f.careTypeCT.selectedIndex == 0)
           {
               f.careTypeCT.value = "";
           }

           if (f.claimType.selectedIndex == 0)
           {
               f.claimType.value = "";
           }

           if (f.authorizedSignatureCT.selectedIndex == 0)
           {
               f.authorizedSignatureCT.value = "";
           }

           if (f.effectiveDate.value != "")
           {
               disableActionButtons();

               sendTransactionAction("CaseTrackingTran", "saveRiderClaim", "_self");
           }
           else
           {
               alert("Please Enter Effective Date of Claim");
           }
       }
       catch (e)
       {
            alert(e);
       }
    }

    /**
     * While a tranaction is being saved, we want to disable all buttons to prevent double-saves.
     */
    function disableActionButtons()
    {
        document.all.trxMessage.innerHTML = "<font color='blue' face=''>Processing...<font>";

        document.all.btnSubmit.style.backgroundColor = "#99BBBB";
        document.all.btnCancel.style.backgroundColor = "#99BBBB";
        document.all.btnClose.style.backgroundColor = "#99BBBB";

        document.all.btnSubmit.disabled = true;
        document.all.btnCancel.disabled = true;
        document.all.btnClose.disabled = true;
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
            Contract Number:&nbsp;
        </td>
        <td align="left" nowrap>
            <input disabled name="contractNumber" type="text" size="15" value="<%= contractNumber %>">
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
        <td align="right" nowrap>
            Segment:&nbsp;
        </td>
        <td align="left" nowrap>
            <input disabled name="segment" type="text" size="15" value="<%= segmentNameCT %>">
        </td>
    </tr>
    <tr>
        <td colspan="6">
            <jsp:include page="/common/jsp/widget/doubleTableWidget.jsp" flush="true">
                <jsp:param name="tableId" value="RiderDetailDoubleTableModel"/>
                <jsp:param name="multipleRowSelect" value="false"/>
            </jsp:include>
        </td>
    </tr>
    <tr>
        <td colspan="6">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Effective Date:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="effectiveDate" value="<%= effectiveDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
            <a href="javascript:show_calendar('f.effectiveDate', f.effectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
        </td>
        <td align="right" nowrap>
            Notification Received Date:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="notificationReceivedDate" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
            <a href="javascript:show_calendar('f.notificationReceivedDate', f.notificationReceivedDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
        </td>
        <td align="right" nowrap>
            State of Death:&nbsp;
        </td>
        <td align="left" nowrap>
            <select name="stateOfDeathCT">
                <option value="null">Please Select</option>
                <%
                    for(int i = 0; i < states.length; i++)
                    {
                        String codeDesc    = states[i].getCodeDesc();
                        String code        = states[i].getCode();

                        if (stateOfDeath.equalsIgnoreCase(code)) {

                            out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                        }
                        else  {

                            out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                        }
                    }
                %>
            </select>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Date of Death:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="dateOfDeath" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
            <a href="javascript:show_calendar('f.dateOfDeath', f.dateOfDeath.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
        </td>
        <td align="right" nowrap>
            Proof of Death Received Date:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="proofOfDeathReceivedDate" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
            <a href="javascript:show_calendar('f.proofOfDeathReceivedDate', f.proofOfDeathReceivedDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
        </td>
        <td align="right" nowrap>
            Resident State at Death:&nbsp;
        </td>
        <td align="left" nowrap>
            <select name="residentStateAtDeathCT">
              <option value="null">Please Select</option>
              <%
                for(int i = 0; i < states.length; i++)
                {
                    String codeDesc    = states[i].getCodeDesc();
                    String code        = states[i].getCode();

                    if (residentStateAtDeath.equalsIgnoreCase(code))
                    {
                        out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                    }
                    else
                    {
                        out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                    }
                }
              %>
            </select>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Care Type:&nbsp;
        </td>
        <td align="left" nowrap>
          <select name="careTypeCT">
            <option>Please Select</option>
              <%
                  for(int i = 0; i < careTypes.length; i++)
                  {
                      String codeDesc    = careTypes[i].getCodeDesc();
                      String code        = careTypes[i].getCode();

                      out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
                %>
            </select>
        </td>
        <td align="right" nowrap>
            Amount Override:&nbsp;
        </td>
        <td align="left" nowrap>
            <input name="amountOverride" type="text" size="11" maxlength="11" value="" CURRENCY>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Claim Type:&nbsp;
        </td>
        <td align="left" nowrap>
          <select name="claimType">
            <option>Please Select</option>
              <%
                  out.println("<option name=\"id\" value=\"" + "LTC" + "\">" + "Long Term Care" + "</option>");
                  out.println("<option name=\"id\" value=\"" + "TI" + "\">" + "Terminal Illness" + "</option>");
                  out.println("<option name=\"id\" value=\"" + "DE" + "\">" + "Death" + "</option>");
                 if (!claimType.equals("")) {

                      out.println("<option selected name=\"id\" value=\"" + claimType+ "\">" + claimType + "</option>");
                  }

              %>
            </select>
        </td>
        <td align="right" nowrap>
            Interest Override:&nbsp;
        </td>
        <td align="left" nowrap>
            <input name="interestOverride" type="text" size="11" maxlength="11" value="" CURRENCY>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Condition:&nbsp;
        </td>
        <td align="left" nowrap>
          <select name="conditionCT">
            <option>Please Select</option>
              <%
                  for(int i = 0; i < conditions.length; i++)
                  {
                      String codeDesc    = conditions[i].getCodeDesc();
                      String code        = conditions[i].getCode();

                      if (condition.equalsIgnoreCase(code))
                      {
                          out.println("<option selected name=\"id\" value=\"" + code+ "\">" + codeDesc + "</option>");
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
            Authorized Signature:&nbsp;
        </td>
        <td align="left" nowrap>
          <select name="authorizedSignatureCT">
            <option>Please Select</option>
              <%
                  for(int i = 0; i < yesNo.length; i++)
                  {
                      String codeDesc    = yesNo[i].getCodeDesc();
                      String code        = yesNo[i].getCode();

                      if (authorizedSignatureCT.equalsIgnoreCase(code)) {

                          out.println("<option selected name=\"id\" value=\"" + code+ "\">" + codeDesc + "</option>");
                      }
                      else  {

                          out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                      }
                  }
                %>
            </select>
        </td>
    </tr>
<%--    END Form Content --%>

    <tr>
        <td align="left" colspan="3">
            &nbsp;
        </td>
        <td id="trxMessage">
            &nbsp;
        </td>
       <td align="left" colspan="1">
            &nbsp;
        </td>
        <td align="right">
            <input id="btnSubmit" type="button" value=" Submit " onClick="saveRiderClaim()">
            <input id="btnCancel" type="button" value=" Cancel " onClick="resetForm()">
            <input id="btnClose"  type="button" value=" Close " onClick="window.close()">
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="segmentPK" value="<%= segmentPK %>">
<input type="hidden" name="casetrackingOption" value="<%= casetrackingOption %>">
<input type="hidden" name="clientDetailPK" value="<%= clientDetailPK %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>