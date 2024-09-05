<%@ page import="contract.ContractClient,
                 role.ClientRole,
                 client.ClientDetail,
                 edit.common.EDITDate,
                 edit.common.EDITBigDecimal,
                 contract.ContractClientAllocation,
                 edit.common.vo.CodeTableVO,
                 codetable.dm.dao.CodeTableDAO,
                 edit.common.CodeTableWrapper,
                 client.ClientAddress,
                 fission.utility.*"%>
<!--
 * User: dlataille
 * Date: Oct 3, 2005
 * Time: 3:01:58 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    String casetrackingOption = (String) request.getAttribute("casetrackingOption");
    String newPolicyIndStatus = Util.initString((String) request.getAttribute("newPolicyIndStatus"), "");
    String newPolicyNumber = Util.initString((String) request.getAttribute("newPolicyNumber"), "");
    String beneficiaryStatus = Util.initString((String) request.getAttribute("beneficiaryStatus"), "");
    String frequencyCT = Util.initString((String) request.getAttribute("frequencyCT"), "");
    String rmdStartDate = (String) request.getAttribute("rmdStartDate");
    String printLine1 = Util.initString((String) request.getAttribute("printLine1"), "");
    String printLine2 = Util.initString((String) request.getAttribute("printLine2"), "");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] relationshipVOs = codeTableWrapper.getCodeTableEntries("RELATIONTOINSURED");
    CodeTableVO[] primaryBeneVO     = new CodeTableDAO().findByTableNameAndCode("LIFERELATIONTYPE", "PBE");
    CodeTableVO[] contingentBeneVO  = new CodeTableDAO().findByTableNameAndCode("LIFERELATIONTYPE", "CBE");

    CodeTableVO[] states = codeTableWrapper.getCodeTableEntries("STATE");

    String segmentPK = (String) request.getAttribute("segmentPK");

    ContractClient selectedBene = (ContractClient) request.getAttribute("selectedBene");
    long selectedBenePK = Util.initLong(selectedBene, "contractClientPK", 0L);

    ClientDetail selectedProspectiveBene = (ClientDetail) request.getAttribute("selectedProspectiveBene");
    long selectedProspectiveBenePK = Util.initLong(selectedProspectiveBene, "clientDetailPK", 0L);

    String taxId2                    = "";
    String lastName                  = "";
    String firstName                 = "";
    String middleName                = "";
    EDITDate birthDate               = null;
    String corporateName             = "";
    String addressLine1              = "";
    String addressLine2              = "";
    String addressLine3              = "";
    String addressLine4              = "";
    String city                      = "";
    String state                     = "";
    String zipcode                   = "";
    String relationship              = "";
    EDITBigDecimal allocationPercent = null;
    EDITDate terminationDate         = null;
    String terminationReason         = "";
    String beneficiaryRole           = "";

    if (selectedBene != null)
    {
        ClientRole selectedClientRole = selectedBene.getClientRole();
        ClientDetail selectedClientDetail = selectedClientRole.getClientDetail();
        ClientAddress selectedClientAddress = selectedClientDetail.getPrimaryAddress();
        ContractClientAllocation selectedConCliAllocation = selectedBene.getContractClientAllocation();

        taxId2              = (String) Util.initObject(selectedClientDetail, "TaxIdentification", "");
        lastName            = (String) Util.initObject(selectedClientDetail, "LastName", "");
        firstName           = (String) Util.initObject(selectedClientDetail, "FirstName", "");
        middleName          = (String) Util.initObject(selectedClientDetail, "MiddleName", "");
        birthDate           = (EDITDate) Util.initObject(selectedClientDetail, "BirthDate", null);
        corporateName       = (String) Util.initObject(selectedClientDetail, "CorporateName", "");
        addressLine1        = (String) Util.initObject(selectedClientAddress, "AddressLine1", "");
        addressLine2        = (String) Util.initObject(selectedClientAddress, "AddressLine2", "");
        addressLine3        = (String) Util.initObject(selectedClientAddress, "AddressLine3", "");
        addressLine4        = (String) Util.initObject(selectedClientAddress, "AddressLine4", "");
        city                = (String) Util.initObject(selectedClientAddress, "City", "");
        state               = (String) Util.initObject(selectedClientAddress, "StateCT", "");
        zipcode             = (String) Util.initObject(selectedClientAddress, "ZipCode", "");
        relationship        = (String) Util.initObject(selectedBene, "RelationshipToInsuredCT", "");
        allocationPercent   = (EDITBigDecimal) Util.initObject(selectedConCliAllocation, "AllocationPercent", null);
        terminationDate     = (EDITDate) Util.initObject(selectedBene, "TerminationDate", null);
        terminationReason   = (String) Util.initObject(selectedBene, "TerminationReasonCT", "");
        beneficiaryRole     = (String) Util.initObject(selectedClientRole, "RoleTypeCT", "");
    }
    else if (selectedProspectiveBene != null)
    {
        ClientAddress prospectiveBeneAddress = selectedProspectiveBene.getPrimaryAddress();

        taxId2          = (String) Util.initObject(selectedProspectiveBene, "TaxIdentification", "");
        lastName        = (String) Util.initObject(selectedProspectiveBene, "LastName", "");
        firstName       = (String) Util.initObject(selectedProspectiveBene, "FirstName", "");
        middleName      = (String) Util.initObject(selectedProspectiveBene, "MiddleName", "");
        birthDate       = (EDITDate) Util.initObject(selectedProspectiveBene, "BirthDate", null);
        corporateName   = (String) Util.initObject(selectedProspectiveBene, "CorporateName", "");
        addressLine1        = (String) Util.initObject(prospectiveBeneAddress, "AddressLine1", "");
        addressLine2        = (String) Util.initObject(prospectiveBeneAddress, "AddressLine2", "");
        addressLine3        = (String) Util.initObject(prospectiveBeneAddress, "AddressLine3", "");
        addressLine4        = (String) Util.initObject(prospectiveBeneAddress, "AddressLine4", "");
        city                = (String) Util.initObject(prospectiveBeneAddress, "City", "");
        state               = (String) Util.initObject(prospectiveBeneAddress, "StateCT", "");
        zipcode             = (String) Util.initObject(prospectiveBeneAddress, "ZipCode", "");
    }
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Case Tracking - Stretch IRA Beneficiaries</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
<style>
    .Border_Bottom
    {
        border-bottom-style:solid;
        border-bottom-width:1px;
        border-bottom-color:black;
    }
</style>

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>


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

        // Initialize scroll tables
        initScrollTable(document.getElementById("ExistingBeneficiariesTableModelScrollTable"));
        initScrollTable(document.getElementById("ProspectiveBeneficiariesTableModelScrollTable"));
    }

    /**
     * Search
     */
    function doSearch()
    {
        if (textElementIsEmpty(f.taxId) && textElementIsEmpty(f.name))
        {
            alert('Please Enter [Tax Identification Number] Or [Name]');
            return;
        }

        if (!textElementIsEmpty(f.taxId) && !textElementIsEmpty(f.name))
        {
            alert("Invalid search parameters.");
            return;
        }

        var tdSearchMessage = document.getElementById("searchMessage");

        if (valueIsEmpty(f.taxId.value))
        {
            tdSearchMessage.innerHTML = "<font face='arial' size='1' color='blue'>Name</font>";
        }
        else if (valueIsEmpty(f.name.value))
        {
            tdSearchMessage.innerHTML = "<font face='arial' size='1' color='blue'>Tax ID</font>";
        }

        sendTransactionAction("CaseTrackingTran", "searchForNewStretchIRABeneficiaries", "_self");
    }

    function resetFormValues()
    {
        f.relationshipToInsuredCT.selectedIndex = 0;
        f.allocationPercent.value = "";
        f.terminationDate.value = "";
        f.terminationReasonCT.value = "";
        f.beneficiaryRole.selectedIndex = 0;
    }

    function saveBeneficiaryDetails()
    {
        if (f.selectedBenePK.value == 0 && f.selectedProspectiveBenePK.value == 0)
        {
            alert('Please Select Either Existing Beneficiary Or Select New Client');
            return;
        }

        if (selectElementIsEmpty(f.beneficiaryRole))
        {
            alert('Please Select Beneficiary Role');
            return;
        }

        sendTransactionAction("CaseTrackingTran", "saveStretchIRABeneficiaryDetails", "_self");
    }

    function onTableRowSingleClick(tableId)
    {
        if (tableId == "ExistingBeneficiariesTableModel")
        {
            sendTransactionAction("CaseTrackingTran", "showStretchIRABeneficiaryDetail", "_self");
        }
        else if (tableId == "ProspectiveBeneficiariesTableModel")
        {
            sendTransactionAction("CaseTrackingTran", "showProspectiveStretchIRABeneDetail", "_self");
        }
    }

    function checkForEnter()
    {
        var eventObj = window.event;

        if (eventObj.keyCode == 13)
        {
            doSearch();
        }
    }

    function closeAndShowStretchIRADialog()
    {
        sendTransactionAction("CaseTrackingTran", "showSelectedTransaction", "transactionDialog");

        window.close();
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
    <td width="50%" height="100%">
      <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="2%" valign="top">
                Existing Beneficiaries
            </td>
          </tr>
          <tr>
            <td height="45%" valign="top">
              <table class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                        <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
                            <jsp:param name="tableId" value="ExistingBeneficiariesTableModel"/>
                            <jsp:param name="tableHeight" value="91"/>
                            <jsp:param name="multipleRowSelect" value="false"/>
                            <jsp:param name="singleOrDoubleClick" value="single"/>
                        </jsp:include>
                    </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td height="2%" valign="top">
                New Beneficiaries
            </td>
          </tr>
          <tr>
            <td height="6%" valign="top">
                <font size="1">Search By...<span id="searchMessage" align="right" nowrap>&nbsp;</span></font>
                <table width="100%" border="0" cellspacing="0" cellpadding="3">
                  <tr>
                    <td>
                        Tax ID:
                    </td>
                    <td>
                      <input type="text" name="taxId" size="15" maxlength="11" value="" onKeyPress="checkForEnter()">
                    </td>
                    <td>
                        Name:
                    </td>
                    <td>
                      <input type="text" name="name" size="15" maxlength="30" value="" onKeyPress="checkForEnter()">
                    </td>
                    <td>
                      <input type="button" value=" Find " onClick="doSearch()">
                    </td>
                  </tr>
                </table>
            </td>
          </tr>
          <tr>
            <td height="45%" valign="top">
              <table class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>
                    <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
                        <jsp:param name="tableId" value="ProspectiveBeneficiariesTableModel"/>
                        <jsp:param name="tableHeight" value="91"/>
                        <jsp:param name="multipleRowSelect" value="false"/>
                        <jsp:param name="singleOrDoubleClick" value="single"/>
                    </jsp:include>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
    </td>

    <td>
      <span style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0;">
        <table width="100%" border="0" cellspacing="0" cellpadding="3">
          <tr>
            <td align="right" nowrap>
                TaxID:
            </td>
            <td align="left" nowrap>
                <input disabled name="taxId2" type="text" size="15" value="<%= taxId2 %>">
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
                Last Name:
            </td>
            <td align="left" nowrap>
                <input disabled name="lastName" type="text" size="15" value="<%= lastName %>">
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
                First Name:
            </td>
            <td align="left" nowrap>
                <input disabled name="firstName" type="text" size="15" value="<%= firstName %>">
            </td>
            <td align="right" nowrap>
                Middle:
            </td>
            <td align="left" nowrap>
                <input disabled name="middleName" type="text" size="2" value="<%= middleName %>">
            </td>
          </tr>
          <tr>
            <td align="right" nowrap>
                Date of Birth:
            </td>
            <td align="left" nowrap>
                <input type="text" name="dob" value="<%= birthDate == null? "": DateTimeUtil.formatYYYYMMDDToMMDDYYYY(birthDate.getFormattedDate()) %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
                <a href="javascript:show_calendar('f.dob', f.dob.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
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
                Corporate Name:
            </td>
            <td align="left" nowrap>
                <input disabled name="corporateName" type="text" size="15" value="<%= corporateName %>">
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
                Address (Line1):
            </td>
            <td align="left" nowrap>
                <input disabled name="addressLine1" type="text" size="15" value="<%= addressLine1 %>">
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
                Address (Line2):
            </td>
            <td align="left" nowrap>
                <input disabled name="addressLine2" type="text" size="15" value="<%= addressLine2 %>">
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
                Address (Line3):
            </td>
            <td align="left" nowrap>
                <input disabled name="addressLine3" type="text" size="15" value="<%= addressLine3 %>">
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
                Address (Line4):
            </td>
            <td align="left" nowrap>
                <input disabled name="addressLine4" type="text" size="15" value="<%= addressLine4 %>">
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
                City:
            </td>
            <td align="left" nowrap>
                <input disabled name="city" type="text" size="15" value="<%= city %>">
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
                State:
            </td>
            <td align="left" nowrap>
                <select name="state" disabled>
                <option value="null">Please Select</option>
                <%
                    for(int i = 0; i < states.length; i++)
                    {
                        String codeDesc    = states[i].getCodeDesc();
                        String code        = states[i].getCode();

                        if (code.equalsIgnoreCase(state)) {

                            out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                        }
                        else  {

                            out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                        }
                    }
                %>
            </select>
            </td>
            <td align="right" nowrap>
                &nbsp;
            </td>
            <td align="left" nowrap>
                &nbsp;
            </td>
          </tr>
          <tr>
            <td align="right" class="Border_Bottom" nowrap>
                Zip:
            </td>
            <td align="left" class="Border_Bottom" nowrap>
                <input disabled name="zipcode" type="text" size="10" value="<%= zipcode %>">
            </td>
            <td align="right" class="Border_Bottom" nowrap>
                &nbsp;
            </td>
            <td align="left" class="Border_Bottom" nowrap>
                &nbsp;
            </td>
          </tr>
          <tr>
            <td align="right" nowrap>
                Relationship:
            </td>
            <td align="left" nowrap>
                <select name="relationshipToInsuredCT">
                    <option value="null">Please Select</option>
                    <%
                        for(int i = 0; i < relationshipVOs.length; i++) {

                            String codeTablePK = relationshipVOs[i].getCodeTablePK() + "";
                            String codeDesc    = relationshipVOs[i].getCodeDesc();
                            String code        = relationshipVOs[i].getCode();

                            if (code.equalsIgnoreCase(relationship)) {

                                out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                            }
                            else  {

                                out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                            }
                        }
                   %>
                </select>
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
                Allocation %:
            </td>
            <td align="left" nowrap>
                <input name="allocationPercent" type="text" size="5" maxlength="6" value="<%= allocationPercent == null? "": allocationPercent.trim() %>">
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
                Termination Date:
            </td>
            <td align="left" nowrap>
                <input type="text" name="terminationDate" value="<%= terminationDate == null? "": DateTimeUtil.formatYYYYMMDDToMMDDYYYY(terminationDate.getFormattedDate()) %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
                <a href="javascript:show_calendar('f.dob', f.dob.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
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
                Termination Reason:
            </td>
            <td align="left" nowrap>
                <input name="terminationReasonCT" type="text" size="15" value="<%= terminationReason %>">
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
                Beneficiary Role:
            </td>
            <td align="left" nowrap>
                <select name="beneficiaryRole">
                    <option value="null">Please Select</option>
                    <%
                        if (primaryBeneVO != null)
                        {
                            if (primaryBeneVO[0].getCode().equals(beneficiaryRole))
                            {
                                out.println("<option selected name=\"id\" value=\"" + primaryBeneVO[0].getCode() + "\">" + primaryBeneVO[0].getCodeDesc() + "</option>");
                            }
                            else
                            {
                                out.println("<option name=\"id\" value=\"" + primaryBeneVO[0].getCode() + "\">" + primaryBeneVO[0].getCodeDesc() + "</option>");
                            }
                        }

                        if (contingentBeneVO != null)
                        {
                            if (contingentBeneVO[0].getCode().equals(beneficiaryRole))
                            {
                                out.println("<option selected name=\"id\" value=\"" + contingentBeneVO[0].getCode() + "\">" + contingentBeneVO[0].getCodeDesc() + "</option>");
                            }
                            else
                            {
                                out.println("<option name=\"id\" value=\"" + contingentBeneVO[0].getCode() + "\">" + contingentBeneVO[0].getCodeDesc() + "</option>");
                            }
                        }
                    %>
                </select>
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
                &nbsp;
            </td>
            <td align="left" nowrap>
                <input type="button" value=" Save " onClick="saveBeneficiaryDetails()">
                <input type="button" value=" Cancel " onClick="resetFormValues()">
            </td>
            <td align="right" nowrap>
                &nbsp;
            </td>
            <td align="left" nowrap>
                &nbsp;
            </td>
          </tr>
        </table>
      </span>
    </td>
 </tr>
<%--    END Form Content --%>
</table>

<br>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right">
            <input type="button" value=" Close " onClick="closeAndShowStretchIRADialog()">
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>


<%-- ****************************** BEGIN Summary Area ****************************** --%>


<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="selectedBenePK" value="<%= selectedBenePK %>">
<input type="hidden" name="selectedProspectiveBenePK" value="<%= selectedProspectiveBenePK %>">
<input type="hidden" name="segmentPK" value="<%= segmentPK %>">
<input type="hidden" name="casetrackingOption" value="<%= casetrackingOption %>">
<input type="hidden" name="newPolicyIndStatus" value="<%= newPolicyIndStatus %>">
<input type="hidden" name="newPolicyNumber" value="<%= newPolicyNumber %>">
<input type="hidden" name="beneficiaryStatus" value="<%= beneficiaryStatus %>">
<input type="hidden" name="frequencyCT" value="<%= frequencyCT %>">
<input type="hidden" name="rmdStartDate" value="<%= rmdStartDate %>">
<input type="hidden" name="printLine1" value="<%= printLine1 %>">
<input type="hidden" name="printLine2" value="<%= printLine2 %>">
<input type="hidden" name="terminationDate">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>