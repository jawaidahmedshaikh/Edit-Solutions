<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page import="edit.common.EDITDate,
                 fission.utility.Util,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 edit.services.db.hibernate.*,
                 edit.portal.widget.GroupDeptLocTableColumns,
                 java.util.*,
                 contract.*,
                 client.*,
                 engine.*,
                 edit.portal.common.session.*"%>
<!--
 * User: dlataill
 * Date: May 2, 2007
 * Time: 7:54:50 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<jsp:useBean id="contractGroup" class="group.ContractGroup" scope="request"/>
<jsp:useBean id="clientDetail" class="client.ClientDetail" scope="request"/>
<jsp:useBean id="formBean" class="fission.beans.PageBean" scope="request"/>


<%
    String responseMessage = (String) request.getAttribute("responseMessage");
	UserSession userSession = (UserSession) session.getAttribute("userSession");
	
    String shouldResetForm = Util.initString((String) request.getAttribute("resetForm"), "NO");
    
    String filterTerminated = (String) request.getParameter("filterTerminated");
    String descInd = (String) request.getParameter("descInd");
    String orderByColumn = (String) request.getParameter("orderByColumn");
    
    String[] tableColumns = GroupDeptLocTableColumns.getColumnDisplayNames();

%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Group Dept/Loc</title>
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

    var shouldShowLockAlert = true;

    var shouldResetForm = "<%= shouldResetForm %>";

    var filterTerminated = "<%= filterTerminated %>";
    var orderByColumnVal = "<%= orderByColumn %>";
    var descInd = "<%= descInd %>";
    
    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        checkForResponseMessage();

        if (filterTerminated === undefined || filterTerminated === null || filterTerminated === "null" || filterTerminated == "false") {
        	f.filterTerminatedCheckbox.checked = false;
        } else {
        	f.filterTerminatedCheckbox.checked = true;
        }
        
        if (descInd === undefined || descInd === null || descInd === "null" || descInd !== "desc") {
        	f.descIndCheckbox.checked = false;
        } else {
        	f.descIndCheckbox.checked = true;
        }
        
        if (orderByColumnVal === undefined || orderByColumnVal === null || orderByColumnVal === "null" || orderByColumnVal == "Please Select") {
        	f.descIndCheckbox.disabled = true;
        } else {
        	f.descIndCheckbox.disabled = false;
        }

        if (shouldResetForm == "YES")
        {
            clearUserFields();
        }
    }

    function refreshDialog()
    {
        
     	if (f.filterTerminatedCheckbox.checked == true)
        {
            f.filterTerminated.value = "true";
        } else {
            f.filterTerminated.value = "false";
        }

     	if (f.descIndCheckbox.checked == true)
        {
            f.descInd.value = "desc";
        } else {
            f.descInd.value = "asc";
        }
        
        sendTransactionAction("CaseDetailTran", "refreshGroupDeptLocDialog", "_self");
    }
    
    /**
    * If the user is adding a new Dept Location, we need to clear three fields [only]:
    * 1. Dept/Loc Code
    * 2. Dept/Loc Name
    * 3. Effective Date
    * 4. departmentLocationPK
    */
    function clearUserFields()
    {        
        f.deptLocCode.value = "";
        
        f.deptLocName.value = "";
        
        f.uIDeptLocEffectiveDate.value = "";
        
        f.departmentLocationPK.value = "";

        f.filterTerminatedCheckbox.checked = false;
        f.descIndCheckbox.checked = false;
        f.orderByCol.value = "";
    }

    function showLockAlert()
    {
<%--        if (shouldShowLockAlert == true)--%>
<%--        {--%>
<%--            alert("The Case can not be edited.");--%>
<%----%>
<%--            return false;--%>
<%--        }--%>
    }

    function saveDeptLocChange()
    {
        if (validateForm(f, "REQUIRED"))
        {
            f.effectiveDate.value = f.uIDeptLocEffectiveDate.value;

            f.terminationDate.value = f.uIDeptLocTerminationDate.value;

            sendTransactionAction("CaseDetailTran", "saveDeptLocChange", "_self");
        }
    }

    function cancelDeptLocChange()
    {
        sendTransactionAction("CaseDetailTran", "clearDeptLoc", "_self");
    }

    function addDeptLoc()
    {
        sendTransactionAction("CaseDetailTran", "clearDeptLoc", "_self");
    }

    /**
     * While a tranaction is being saved, we want to disable all buttons to prevent double-saves.
     */
    function disableActionButtons()
    {
        document.all.trxMessage.innerHTML = "<font color='blue' face=''>Processing...<font>";

        document.all.btnSave.style.backgroundColor = "#99BBBB";
        document.all.btnCancel.style.backgroundColor = "#99BBBB";

        document.all.btnSave.disabled = true;
        document.all.btnCancel.disabled = true;
    }

    /*
    Callback method from TableModel implemenation.
    */
    function onTableRowSingleClick(tableId)
    {
        showDeptLocDetail();
    }

    /**
    * Shows the detail(s) of the just-selected Group Summary Table.
    */
    function showDeptLocDetail()
    {
        sendTransactionAction("CaseDetailTran", "showDeptLocDetail", "_self");
    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="dialog" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<span id="mainContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:60%; top:0; left:0; z-index:0; overflow:visible">
<%--    BEGIN Form Content --%>
  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" nowrap>
            Group Number:&nbsp;
            <input:text name="contractGroupNumber" bean="contractGroup" attributesText="id='contractGroupNumber' disabled" size="20"/>
            &nbsp;&nbsp;&nbsp;&nbsp;
        </td>
        <td align="left" nowrap colspan="3">
            Group Name:&nbsp;
            <input:text name="name" bean="clientDetail" attributesText="id='contractGroupName' disabled" size="50"/>
        </td>
    </tr>
    <tr>
        <td align="left" valign="top" nowrap colspan="3">&nbsp;</td>
    </tr>
    <tr>
        <td align="left" valign="top" nowrap>
            Dept/Loc Code:&nbsp;
        </td>
        <td align="left" valign="top" nowrap>
            <input:text name="deptLocCode" bean="departmentLocation" attributesText="id='deptLocCode' REQUIRED" size="10"/>
        </td>
        <td align="left" valign="top" nowrap>
            Dept/Loc Name:&nbsp;
        </td>
        <td align="left" valign="top" nowrap>
            <input:text name="deptLocName" bean="departmentLocation" attributesText="id='deptLocName' maxlength='20' REQUIRED" size="20"/>
        </td>
    </tr>
    <tr>
        <td align="left" nowrap width="5%">
            Effective Date:&nbsp;
        </td>
        <td align="left" nowrap>
            <input:text name="uIDeptLocEffectiveDate" bean="departmentLocation"
                  attributesText="REQUIRED id='uIDeptLocEffectiveDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
            <a href="javascript:show_calendar('f.uIDeptLocEffectiveDate', f.uIDeptLocEffectiveDate.value);"><img
                   src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                   alt="Select a date from the calendar"></a>
        </td>
        <td  align="left" nowrap width="5%">
            Termination Date:&nbsp;
        </td>
        <td align="left" nowrap>
            <input:text name="uIDeptLocTerminationDate" bean="departmentLocation"
                  attributesText="id='uIDeptLocTerminationDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
            <a href="javascript:show_calendar('f.uIDeptLocTerminationDate', f.uIDeptLocTerminationDate.value);"><img
                   src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                   alt="Select a date from the calendar"></a>
        </td>
    </tr>
    <tr>
        <td align="left" nowrap colspan="4">&nbsp;</td>
    </tr>
  </table>
  <table>
    <tr>
		<td nowrap align="left">
			<input id="btnAdd" type="button" value=" Add " onClick="addDeptLoc()">
			<input id="btnSave" type="button" value=" Save " onClick="saveDeptLocChange()">
			<input id="btnCancel" type="button" value="Cancel" onClick="cancelDeptLocChange()">
		</td>
		<td nowrap align="left">
		   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		   <input type="checkbox" name="filterTerminatedCheckbox" onclick="refreshDialog()" <%= filterTerminated %>> Hide Inactive
		 </td>
		 
		 <td nowrap align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Order By:&nbsp;</td>
		 <td nowrap align="right">
		   <select name="orderByColumn" onchange="refreshDialog()">
		     <option value="null">Please Select</option>
		     <%
		         for(int i = 0; i < tableColumns.length; i++) {
		
		             String columnName       = tableColumns[i];
		             
		             if (columnName.equalsIgnoreCase(orderByColumn))
		             {
		                 out.println("<option selected name=\"id\" value=\"" + columnName + "\">" + columnName + "</option>");
		             }
		             else
		             {
		                 out.println("<option name=\"id\" value=\"" + columnName + "\">" + columnName + "</option>");
		             }
		         }
		     %>
		   </select>
		 </td>
		<td nowrap align="left">
			<input type="checkbox" name="descIndCheckbox" onclick="refreshDialog()" <%= descInd %>> desc
		 </td>
    </tr>
  </table>
<%--    END Form Content --%>
</span>
<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="GroupDeptLocTableModel"/>
    <jsp:param name="tableHeight" value="30"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Area ****************************** --%>

  <table id="closeDialog" width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
    <tr>
	  <td colspan="8" nowrap align="right">
        <input type="button" name="close" value="Close" onClick="window.close()">
      </td>
    </tr>
  </table>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input:hidden name="transaction"/>
<input:hidden name="action"/>
<input:hidden name="departmentLocationPK" bean="departmentLocation"/>
<input:hidden name="effectiveDate"/>
<input:hidden name="terminationDate"/>
<input:hidden name="filterTerminated"/>
<input:hidden name="descInd"/>
<input:hidden name="orderByColumn"/>
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>
