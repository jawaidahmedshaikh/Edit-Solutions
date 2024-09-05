<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page import="edit.common.EDITDate,
                 fission.utility.Util,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 edit.services.db.hibernate.*,
                 java.util.*,
                 group.*,
                 client.*,
                 engine.*,
                 edit.portal.common.session.*,
                 fission.utility.DateTimeUtil,
                 edit.portal.taglib.InputSelect"%>
<!--
 * User: dlataill
 * Date: Aug 3, 2007
 * Time: 10:57:50 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<jsp:useBean id="filteredProduct" class="contract.FilteredProduct" scope="request"/>
<jsp:useBean id="contractGroup" class="group.ContractGroup" scope="request"/>
<jsp:useBean id="masterContract" class="contract.MasterContract" scope="request"/>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");
    String productName = (String) request.getAttribute("productKey");

    CodeTableVO[] stateVOCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("STATE");
    InputSelect stateCTs = new InputSelect(stateVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);
    
    CodeTableVO[] brandingCompanyVOCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("BRANDINGCOMPANY");
    InputSelect brandingCompanyCTs = new InputSelect(brandingCompanyVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);
    
    String deleteAlertStatus = (String)request.getAttribute("shouldShowDeleteAlert");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Product/Master Contract Information</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>

<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";
    
    var defaultMaxDate = "<%= EDITDate.DEFAULT_MAX_MONTH + EDITDate.DATE_DELIMITER + EDITDate.DEFAULT_MAX_DAY + EDITDate.DATE_DELIMITER +  EDITDate.DEFAULT_MAX_YEAR %>";
    
    var shouldShowLockAlert = true;
    
    var deleteAlertStatus = "<%= deleteAlertStatus %>";
    
   
    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;
        checkForResponseMessage();
        initScrollTable(document.getElementById("MasterContractTableModelScrollTable"));
        checkDeleteStatus();
    }

    {
<%--        if (shouldShowLockAlert == true)--%>
<%--        {--%>
<%--            alert("The Case can not be edited.");--%>
<%----%>
<%--            return false;--%>
<%--        }--%>
    }
           
    function saveChange()
    {
        f.masterContractEffectiveDate.value = f.uIMCEffectiveDate.value;
        f.masterContractTerminationDate.value = f.uIMCTerminationDate.value;

        sendTransactionAction("CaseDetailTran", "saveMasterContractInfoChange", "_self");
    }
     function checkDeleteStatus()
        {
            if (deleteAlertStatus == "true")
            {
                alert("The MasterContract can not be Deleted - Contract(s) Already Associated.");
                return false; 
            }
        }

    function onTableRowSingleClick(tableId)
    {
        selectMasterContractDetail();
    }

    function selectMasterContractDetail()
    {
        f.uIMCEffectiveDate.value =f.masterContractEffectiveDate.value;
        f.uIMCTerminationDate.value = f.masterContractTerminationDate.value;
        sendTransactionAction("CaseDetailTran", "selectMasterContractDetail", "masterContractInfoDialog");
    }
    
    function resetSelectedMasterContractDetail()
    {
        sendTransactionAction("CaseDetailTran", "resetSelectedMasterContractDetail", "masterContractInfoDialog");
    }
   
    function updateMasterContractFromProduct()
   {
   		if (f.productkey.value=="FLA,*,*,*,LBT1.5" && (f.brandingCompanyCT==null || f.brandingCompanyCT.value==null || f.brandingCompanyCT.value=="Please Select" || f.brandingCompanyCT.value=="")) {
			alert("Branding Company Must Be Selected For This Product");
     	} else {
    	    sendTransactionAction("CaseDetailTran", "updateMasaterContractInfoChange", "masterContractInfoDialog");
     	}
   }
    
   function removeMasterContractFromProduct()
   {   
       clearOut();
       sendTransactionAction("CaseDetailTran", "removeMasterContractFromProduct", "masterContractInfoDialog");
   }
 
    function cancel()
    {
        clearOut();
        resetSelectedMasterContractDetail();
        
    }
    
    function addNew()
    {
        defaultValues();
        resetSelectedMasterContractDetail();
    }
    
    function defaultValues()
    {
            f.masterContractNumber.disabled = false;
            f.masterContractNumber.value = ""
            f.masterContractName.disabled = false;
            f.masterContractName.value = "";
            f.uIMCEffectiveDate.disabled = false;
            f.uIMCEffectiveDate.value = "";
            f.uIMCTerminationDate.disabled = false;
            f.uIMCTerminationDate.value = defaultMaxDate;
            f.creationOperator.disabled = "true";
            f.creationOperator.value = "";
            f.creationDate.disabled = "true";
            f.creationDate.value= "";
            f.stateCT.disabled = false;
            f.stateCT.selectedIndex = 0;
            f.brandingCompanyCT.disabled = false;
            f.brandingCompanyCT.selectedIndex = 0;
           
            
    }
    function clearOut()
    {
            
            f.masterContractNumber.disabled = true;
            f.masterContractNumber.value = ""
            f.masterContractName.disabled = true;
            f.masterContractName.value = "";
            f.uIMCEffectiveDate.disabled = true;
            f.uIMCEffectiveDate.value = "";
            f.uIMCTerminationDate.disabled = true;
            f.uIMCTerminationDate.value = "";
            f.creationOperator.disabled = "true";
            f.creationOperator.value = "";
            f.creationDate.disabled = "true";
            f.creationDate.value= "";
            f.stateCT.disabled = true;
            f.brandingCompanyCT.disabled = true;
    }
    
     function resetFormValues()
    {

    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="dialog" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<span id="mainContent" style="border-style:solid; border-width:0;  position:relative; width:100%; height:55%; top:0; left:0; z-index:0; overflow:visible">
<%--    BEGIN Form Content --%>
  <table width="100%" height="98%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td nowrap colspan="2">
            <span class="tableHeading">Master Contract Information</span>
        </td>
    </tr>
    <tr>
        <td align="left" nowrap>Number:&nbsp;
        </td>
        <td align="left" nowrap="nowrap">
            <input:text name="masterContractNumber" bean="masterContract" attributesText="id='masterContractNumber'" size="15"/>
        </td>
    </tr>
    
    <tr>
        <td align="left" nowrap>Name:&nbsp;
        </td>
        <td align="left" nowrap="nowrap">
            <input:text name="masterContractName" bean="masterContract" attributesText="id='masterContractName'" size="60"/>
        </td>
    </tr>
    <tr>
        <td align="left" nowrap>
            Effective Date:&nbsp;
        </td>
        <td align="left" nowrap>
            <input:text name="uIMCEffectiveDate" bean="masterContract"
                  attributesText="id='uIMCEffectiveDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
            <a href="javascript:show_calendar('f.uIMCEffectiveDate', f.uIMCEffectiveDate.value);"><img
                   src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                   alt="Select a date from the calendar"></a>
			<span style="margin-left: 40px">No Interim Coverage:&nbsp;</span>  
			<input type="checkbox" name="noInterimCoverage" <% if(masterContract.isNoInterimCoverage()) { %> checked <% } %> />
        </td>
    </tr>
     <tr>
        <td align="left" nowrap>
            Termination Date:&nbsp;
        </td>
        <td align="left" nowrap>
            <input:text name="uIMCTerminationDate" bean="masterContract"
                  attributesText="id='uIMCTerminationDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
            <a href="javascript:show_calendar('f.uIMCTerminationDate', f.uIMCTerminationDate.value);"><img
                   src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                   alt="Select a date from the calendar"></a>
        </td>
    </tr>
     <tr>
        <td align="left" nowrap>Product Key:&nbsp;
        </td>
        <td align="left" nowrap="nowrap">
            <input type="text" name="productkey" attributesText="id='productkey'" value="<%= productName %>" maxlength="20" disabled>
        </td>
    </tr>
    <tr>
        <td align="left" nowrap>CreationOperator:&nbsp;
        </td>
        <td align="left" nowrap="nowrap">
            <input:text name="creationOperator" bean="masterContract" attributesText="id='creationOperator' CONTENTEDITABLE='false'" size="15"/>
        </td>
    </tr>
    <tr>
        <td align="left" nowrap>CreationDate:&nbsp;
        </td>
        <td disabled>
           <input:text name="uICreationDate" bean="masterContract"
                                      attributesText="id='uICreationDate' CONTENTEDITABLE='false'" size="10"/> 
        </td>
    </tr>
    <tr>
        <td align="left" nowrap>State:&nbsp; </td>
     	<td align="left" nowrap="nowrap">
            <input:select bean="masterContract" name="stateCT" options="<%= stateCTs.getOptions() %>"
                           attributesText="id='stateCT'"/>
      	</td>
     </tr>
     
     <tr>
        <td align="left" nowrap>Branding Company:&nbsp; </td>
     	<td align="left" nowrap="nowrap">
            <input:select bean="masterContract" name="brandingCompanyCT" options="<%= brandingCompanyCTs.getOptions() %>"
                           attributesText="id='brandingCompanyCT'"/>
      	</td>
     </tr>
     
  </table>
  <table width="100%" height="2%">
    <tr>
	  	<td nowrap align="left">
			<input id="btnAdd" type="button" value=" Add " onClick="addNew()">
                        <input id="btnSave" type="button" value=" Save " onClick="updateMasterContractFromProduct()">
                        <input id="btnCancel" type="button" value="Cancel" onClick="cancel()">
                        <input id="btnDelete" type="button" value="Delete" onClick="removeMasterContractFromProduct()">
	  	</td>
    </tr>
  </table>
<%--    END Form Content --%>
</span>
<%-- ****************************** END Form Data ****************************** --%>

 <%--****************************** BEGIN Summary Area ******************************--%>
            <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
                <jsp:param name="tableId" value="MasterContractTableModel"/>
                <jsp:param name="tableHeight" value="32"/>
                <jsp:param name="multipleRowSelect" value="false"/>
                <jsp:param name="singleOrDoubleClick" value="single"/>
            </jsp:include>
<%-- ****************************** END Summary Area ****************************** --%>

 <table id="table3" width="100%" border="0" cellspacing="6" cellpadding="0">
    <tr>
      <td align="right" nowrap colspan="3">
        <input type="button" name="close" value="Close" onClick="window.close()">

      </td>
    </tr>
  </table>
</span>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
    <input:hidden name="transaction"/>
    <input:hidden name="action"/>
    <input:hidden name="creationDate" bean="filteredProduct"/>
    <input:hidden name="productKey" bean="masterContract"/>
    <input:hidden name="filteredProductPK" bean="filteredProduct"/>
    <input:hidden name="contractGroupPK" bean="contractGroup"/>
    <input:hidden name="masterContractPK" bean="masterContract"/>
    <input:hidden name="masterContractEffectiveDate"/>
    <input:hidden name="masterContractTerminationDate"/>
    <input:hidden name="statusCT" bean= "masterContract"/>
    

<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>
