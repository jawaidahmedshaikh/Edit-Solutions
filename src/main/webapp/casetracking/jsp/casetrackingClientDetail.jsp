<%@ page import="client.ClientDetail,
                 role.ClientRole,
                 contract.ContractClient,
                 contract.Segment,
                 edit.common.vo.CodeTableVO,
                 contract.Payout,
                 casetracking.CasetrackingNote,
                 edit.services.db.hibernate.*,
                 java.util.*,
                 edit.common.*,
                 fission.utility.*"%>
<!--
 * User: sprasad
 * Date: Mar 17, 2005
 * Time: 10:44:58 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    Long clientDetailPK = (Long) session.getAttribute("casetracking.selectedClientDetailPK");

    ClientDetail clientDetail = null;

    if (clientDetailPK != null)
    {
        clientDetail = ClientDetail.findByPK(clientDetailPK);
    }

    CodeTableVO[] processes = CodeTableWrapper.getSingleton().getCodeTableEntries("PROCESS");

    String firstName            = (String) Util.initObject(clientDetail, "firstName", "");
    String lastName             = (String) Util.initObject(clientDetail, "lastName", "");
    String middleName           = (String) Util.initObject(clientDetail, "middleName", "");
    String taxId                = (String) Util.initObject(clientDetail, "taxIdentification", "");
    EDITDate birthDate          = (EDITDate) Util.initObject(clientDetail, "birthDate", null);
    EDITDate dateOfDeath        = (EDITDate) Util.initObject(clientDetail, "dateOfDeath", null);
    String stateOfDeath         = (String) Util.initObject(clientDetail, "stateOfDeathCT", "");
    String residentStateAtDeath = (String) Util.initObject(clientDetail, "residentStateAtDeathCT", "");

    String caseTrackingProcess  = (String) session.getAttribute("casetracking.process");
    if (caseTrackingProcess == null)
    {
        caseTrackingProcess    = (String) Util.initObject(clientDetail, "caseTrackingProcess", "");
        if (!caseTrackingProcess.equals(""))
        {
            session.setAttribute("casetracking.process", caseTrackingProcess);
        }
    }

    String notesStatus          = "unChecked";

    if (clientDetail != null)
    {
        if (clientDetail.hasCaseTrackingNotes())
        {
            notesStatus="Checked";
        }
    }
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Case Tracking - Client Detail</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/casetracking/javascript/casetrackingTabFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>


<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        setActiveImage("client");

        checkForResponseMessage();

        // Initialize scroll tables
        initScrollTable(document.getElementById("ClientDetailTableModelScrollTable"));
    }

    function showNotesDialog()
    {
        var width = 0.99 * screen.width;
        var height = 0.90 * screen.height;

        if (valueIsEmpty(f.clientDetailPK.value))
        {
            alert('Please Select Client');
            return;
        }

        openDialog("notesDialog", "top=0,left=0,resizable=no", width,  height);
        sendTransactionAction("CaseTrackingTran", "showNotesDialog", "notesDialog");
    }

    function showDeathTransactionDialog()
    {
        var process = f.caseTrackingProcess;

        if (process == null ||  selectElementIsEmpty(process))
        {
            alert("Please Select the Process.");
        }

        else
        {
            var width = 0.99 * screen.width;
            var height = 0.50 * screen.height;

            if (valueIsEmpty(f.clientDetailPK.value))
            {
                alert('Please Select Client');
                return;
            }

            openDialog("deathTransactionDialog", "top=0,left=0,resizable=no", width,  height);
            sendTransactionAction("CaseTrackingTran", "showDeathTransactionDialog", "deathTransactionDialog");
        }
    }

    function onTableRowDoubleClick(tableId)
    {
        var process = f.caseTrackingProcess;

        if (selectElementIsEmpty(process))
        {
            alert("Please Select the Process.");
        }

        else
        {
            var width = 0.99 * screen.width;
            var height = 0.75 * screen.height;

            openDialog("casetrackingContractDialog", "top=0,left=0,resizable=no", width,  height);
            sendTransactionAction("CaseTrackingTran", "showContractDialog", "casetrackingContractDialog");
        }
    }

    function saveClientProcess()
    {
        if (!selectElementIsEmpty(f.caseTrackingProcess))
        {
            sendTransactionAction("CaseTrackingTran", "saveClientProcess", "_self");
        }
    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">


<%-- ****************************** Tab Content ****************************** --%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td>
        <jsp:include page="casetrackingTabContent.jsp" flush="true"/>
      </td>
    </tr>
</table>

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
<%--    BEGIN Form Content --%>
    <tr>
        <td align="right" nowrap>
            First Name:&nbsp;
        </td>
        <td align="left" nowrap>
            <input disabled type="text" name="firstName" size="15" value="<%= firstName %>">
        </td>
        <td align="right" nowrap>
            Last Name:&nbsp;
        </td>
        <td align="left" nowrap>
            <input disabled type="text" name="lastName" size="25" value="<%= lastName %>">
        </td>
        <td align="right" nowrap>
            Middle Name:&nbsp;
        </td>
        <td align="left" nowrap>
            <input disabled type="text" name="middleName" size="15" value="<%= middleName %>">
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Tax Identification:&nbsp;
        </td>
        <td align="left" nowrap>
            <input disabled type="text" name="taxId" size="15" value="<%= taxId %>">
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Date of Birth:&nbsp;
        </td>
        <td align="left" nowrap>
            <input disabled name="dob" type="text" size="10" value="<%= (birthDate != null)? DateTimeUtil.formatYYYYMMDDToMMDDYYYY(birthDate.getFormattedDate()): "" %>">
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" valign="top" colspan="2" rowspan="3" nowrap>
            <fieldset style="border-style:solid; border-width:1px; border-color:black">
            <legend><font color="black">Death Information</font></legend>
            <table width="100%" height="100%" border="0" cellspacing="1" cellpadding="1">
              <tr>
                <td align="right" nowrap>
                    Date of Death:&nbsp;
                </td>
                <td align="left" nowrap>
                    <input disabled name="dod" type="text" size="10" value="<%= (dateOfDeath != null)? DateTimeUtil.formatYYYYMMDDToMMDDYYYY(dateOfDeath.getFormattedDate()): "" %>">
                </td>
              </tr>
              <tr>
                <td align="right" nowrap>
                    State of Death:&nbsp;
                </td>
                <td align="left" nowrap>
                    <input disabled type="text" name="stateOfDeath" size="15" value="<%= stateOfDeath %>">
                </td>
              </tr>
              <tr>
                <td align="right" nowrap>
                    Resident State at Death:&nbsp;
                </td>
                <td align="left" nowrap>
                    <input disabled type="text" name="residentStateAtDeath" size="15" value="<%= residentStateAtDeath %>">
                </td>
              </tr>
            </table>
            </fieldset>
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Process:&nbsp;
        </td>
        <td align="left" nowrap>
            <%-- This following select field (caseTrackingProcess) is used in casetrackingTabFunctions.js for validation --%>
            <select name="caseTrackingProcess" id="caseTrackingProcess" REQUIRED onChange="saveClientProcess()">
                <option value="null">Please Select</option>
                <%
                    if (processes != null)
                    {
                        for(int i = 0; i < processes.length; i++)
                        {
                            String currentCodeDesc    = processes[i].getCodeDesc();
                            String currentCode        = processes[i].getCode();

                            if (currentCode.equalsIgnoreCase(caseTrackingProcess))
                            {
                                out.println("<option selected name=\"id\" value=\"" + currentCode+ "\">" + currentCodeDesc + "</option>");
                            }
                            else
                            {
                                out.println("<option name=\"id\" value=\"" + currentCode + "\">" + currentCodeDesc + "</option>");
                            }
                        }
                    }
                %>
            </select>
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            <input disabled type="checkbox" name="notesStatus" <%= notesStatus %>>&nbsp;
            <a href="javascript:showNotesDialog()">Notes</a>&nbsp;
        </td>
        <td align="left" nowrap>
            <input disabled type="checkbox" name="deathTransactionStatus">&nbsp;
            <a href="javascript:showDeathTransactionDialog()">Death Transaction</a>
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
    </tr>
<%--    END Form Content --%>

    <tr class="filler">
        <td colspan="6">
            &nbsp; <!--Filler Row -->
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<br>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="ClientDetailTableModel"/>
    <jsp:param name="tableHeight" value="60"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="double"/>
</jsp:include>

<%-- ****************************** END Summary Area ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="pageToShow">
<%-- This following hidden field (clientDetailPK) is used in casetrackingTabFunctions.js for validation --%>
<input type="hidden" name="clientDetailPK" value="<%= clientDetailPK %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>
