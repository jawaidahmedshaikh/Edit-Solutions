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
                 edit.portal.taglib.InputSelect,
                 contract.FilteredProduct"%>
<!--
 * User: dlataill
 * Date: Jul 23, 2007
 * Time: 10:07:50 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<jsp:useBean id="caseProductUnderwriting" class="group.CaseProductUnderwriting" scope="request"/>
<jsp:useBean id="enrollment" class="group.Enrollment" scope="request"/>

<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] relationToEmployeeCTs = codeTableWrapper.getCodeTableEntries("RELATIONTOEMPLOYEE");
    CodeTableVO[] requiredOptionalCTs = codeTableWrapper.getCodeTableEntries("REQUIREDOPTIONAL");

    Hashtable filteredProducts = (Hashtable) request.getAttribute("filteredProducts");
    Hashtable deptLocations = (Hashtable) request.getAttribute("deptLocs");
    
    InputSelect filteredProductCTs = new InputSelect(filteredProducts);

    String filteredProductPK = (String) request.getAttribute("filteredProductPK");
    String activeEnrollmentPK = (String) request.getAttribute("activeEnrollmentPK");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Case Underwriting</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>

<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";

    var shouldShowLockAlert = true;

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        checkForResponseMessage();

        // Initialize scroll tables
<%--        initScrollTable(document.getElementById("BillingHistoryTableModelScrollTable"));--%>
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

    function getUnderwritingInformation()
    {
    	f.selectedProductStructure.value = f.filteredProductFK.options[f.filteredProductFK.selectedIndex].value;

        sendTransactionAction("CaseDetailTran", "getUnderwritingInformation", "_self");
    }

    function resetFormValues()
    {
<%--        f.groupNumber.value = "";--%>
<%--        f.groupName.value = "";--%>
<%--        f.effectiveMonth.value = "";--%>
<%--        f.effectiveDay.value = "";--%>
<%--        f.effectiveYear.value = "";--%>
<%--        f.terminationMonth.value = "";--%>
<%--        f.terminationDay.value = "";--%>
<%--        f.terminationYear.value = "";--%>
<%--        f.operator.value = "";--%>
<%--        f.creationDay.value = "";--%>
<%--        f.creationMonth.value = "";--%>
<%--        f.creationYear.value = "";--%>
<%----%>
<%--        sendTransactionAction("CaseDetailTran", "cancelGroupEntry", "main");--%>
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

  /**
  * Associates the selected Underwriting criteria to the current Enrollment.
  */
  function addBaseUnderwriting()
  {
      sendTransactionAction("CaseDetailTran", "addBaseUnderwriting", "_self");
  }

  /**
   * Removes the selected underwriting criteria from the current Enrollment.
   */
  function removeBaseUnderwriting()
  {
    sendTransactionAction("CaseDetailTran", "removeBaseUnderwriting", "_self");
  }

  /**
  * Associates the selected Underwriting criteria to the current Enrollment.
  */
  function addRiderUnderwriting()
  {
      sendTransactionAction("CaseDetailTran", "addRiderUnderwriting", "_self");
  }

  /**
   * Removes the selected underwriting criteria from the current Enrollment.
   */
  function removeRiderUnderwriting()
  {
    sendTransactionAction("CaseDetailTran", "removeRiderUnderwriting", "_self");
  }

  /**
  * Associates the selected Underwriting criteria to the current Enrollment.
  */
  function addOtherUnderwriting()
  {
      sendTransactionAction("CaseDetailTran", "addOtherUnderwriting", "_self");
  }

  /**
   * Removes the selected underwriting criteria from the current Enrollment.
   */
  function removeOtherUnderwriting()
  {
    sendTransactionAction("CaseDetailTran", "removeOtherUnderwriting", "_self");
  }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="dialog" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<span id="mainContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:95%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" nowrap>Product Key:&nbsp;
          <input:select  bean="caseProductUnderwriting" name="filteredProductFK" options="<%= filteredProductCTs.getOptions() %>"
                                        attributesText="id='filteredProductFK' onChange='getUnderwritingInformation()' "/>
        </td>
        <td align="left" nowrap colspan="4">&nbsp;</td>
    </tr>
  </table>
  <table cellspacing="3" cellpadding="2" border="0" width="100%" height="30%">
    <tr>
        <td width="50%" height="5%" colspan="3">
              <span class="tableHeading">Base</span>
        </td>
    </tr>
    <tr>
        <td width="47%">
          <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
                <jsp:param name="tableId" value="CandidateBaseUnderwritingTableModel"/>
                <jsp:param name="tableHeight" value="90"/>
                <jsp:param name="multipleRowSelect" value="true"/>
                <jsp:param name="singleOrDoubleClick" value="single"/>
          </jsp:include>
        </td>
        <td width="6%" align="center">
          <input name="btnRight" value="  &#8594;  " type="button"
                 style="border-style:solid; border-width:1; background-color: #99CCCC; font-size: 11pt;"
                 onclick="addBaseUnderwriting()" align="left"
                 title="Add Base Product Underwriting To Enrollment."></input>
          <br></br>
          <br></br>
          <input name="btnLeft" value="  &#8592;  " type="button"
                 style="border-style:solid; border-width:1; background-color: #99CCCC; font-size: 11pt;"
                 onclick="removeBaseUnderwriting()"></input>
        </td>
        <td width="47%">
              <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
                    <jsp:param name="tableId" value="BaseUnderwritingTableModel"/>
                    <jsp:param name="tableHeight" value="90"/>
                    <jsp:param name="multipleRowSelect" value="true"/>
                    <jsp:param name="singleOrDoubleClick" value="single"/>
              </jsp:include>
        </td>
    </tr>
  </table>
  <table cellspacing="0" cellpadding="0" border="0" width="100%" height="2%">
    <tr>
        <td align="left" width="80%">Rel to EE:&nbsp;
          <select name="baseRelToEE">
              <option value="Please Select">Please Select</option>
                <%
                    for(int i = 0; i < relationToEmployeeCTs.length; i++) {

                        String codeTablePK = relationToEmployeeCTs[i].getCodeTablePK() + "";
                        String codeDesc    = relationToEmployeeCTs[i].getCodeDesc();

                        out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                    }
               %>
          </select>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          Dept/Loc:&nbsp;
          <select name="baseDeptLoc">
              <option value="Please Select">Please Select</option>
                <%
                    if (!deptLocations.isEmpty())
                    {
                        Enumeration keysEnum = deptLocations.keys();
                        
                        while(keysEnum.hasMoreElements())
                        {
                            String htKey = (String) keysEnum.nextElement();

                            String deptLoc = (String) deptLocations.get(htKey);

                            out.println("<option name=\"id\" value=\"" + htKey + "\">" + deptLoc + "</option>");
                        }
                    }
               %>
          </select>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          Value:&nbsp;
          <input type="text" name="baseValue" size="10" maxlength="10">
        </td>
        <td nowrap align="right" width="20%">&nbsp;</td>
    </tr>
  </table>
  <table cellspacing="3" cellpadding="2" border="0" width="100%" height="30%">
    <tr>
        <td width="50%" height="5%" colspan="3">
              <span class="tableHeading">Rider</span>
        </td>
    </tr>
    <tr>
        <td width="47%">
          <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
                <jsp:param name="tableId" value="CandidateRiderUnderwritingTableModel"/>
                <jsp:param name="tableHeight" value="90"/>
                <jsp:param name="multipleRowSelect" value="true"/>
                <jsp:param name="singleOrDoubleClick" value="single"/>
          </jsp:include>
        </td>
        <td width="6%" align="center">
          <input name="btnRight" value="  &#8594;  " type="button"
                 style="border-style:solid; border-width:1; background-color: #99CCCC; font-size: 11pt;"
                 onclick="addRiderUnderwriting()" align="left"
                 title="Add Rider Underwriting To Enrollment."></input>
          <br></br>
          <br></br>
          <input name="btnLeft" value="  &#8592;  " type="button"
                 style="border-style:solid; border-width:1; background-color: #99CCCC; font-size: 11pt;"
                 onclick="removeRiderUnderwriting()"></input>
        </td>
        <td width="47%">
              <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
                    <jsp:param name="tableId" value="RiderUnderwritingTableModel"/>
                    <jsp:param name="tableHeight" value="90"/>
                    <jsp:param name="multipleRowSelect" value="true"/>
                    <jsp:param name="singleOrDoubleClick" value="single"/>
              </jsp:include>
        </td>
    </tr>
  </table>
  <table cellspacing="0" cellpadding="0" border="0" width="100%" height="2%">
    <tr>
        <td align="left" colspan="2">Rel to EE:&nbsp;
          <select name="riderRelToEE">
              <option value="Please Select">Please Select</option>
                <%
                    for(int i = 0; i < relationToEmployeeCTs.length; i++) {

                        String codeTablePK = relationToEmployeeCTs[i].getCodeTablePK() + "";
                        String codeDesc    = relationToEmployeeCTs[i].getCodeDesc();

                        out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                    }
               %>
          </select>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          Dept/Loc:&nbsp;
          <select name="riderDeptLoc">
              <option value="Please Select">Please Select</option>
                <%
                    if (!deptLocations.isEmpty())
                    {
                        Enumeration keysEnum = deptLocations.keys();

                        while(keysEnum.hasMoreElements())
                        {
                            String htKey = (String) keysEnum.nextElement();

                            String deptLoc = (String) deptLocations.get(htKey);

                            out.println("<option name=\"id\" value=\"" + htKey + "\">" + deptLoc + "</option>");
                        }
                    }
               %>
          </select>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          Value:&nbsp;
          <input type="text" name="riderValue" size="10" maxlength="10">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          Incl/Opt:&nbsp;
          <select name="riderInclOpt">
              <option value="Please Select">Please Select</option>
                <%
                    for(int i = 0; i < requiredOptionalCTs.length; i++) {

                        String codeTablePK = requiredOptionalCTs[i].getCodeTablePK() + "";
                        String codeDesc    = requiredOptionalCTs[i].getCodeDesc();

                        out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                    }
               %>
          </select>
        </td>
    </tr>
  </table>
  <table cellspacing="3" cellpadding="2" border="0" width="100%" height="30%">
    <tr>
        <td width="50%" height="5%" colspan="3">
              <span class="tableHeading">Other</span>
        </td>
    </tr>
    <tr>
        <td width="47%">
          <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
                <jsp:param name="tableId" value="CandidateOtherUnderwritingTableModel"/>
                <jsp:param name="tableHeight" value="90"/>
                <jsp:param name="multipleRowSelect" value="true"/>
                <jsp:param name="singleOrDoubleClick" value="single"/>
          </jsp:include>
        </td>
        <td width="6%" align="center">
          <input name="btnRight" value="  &#8594;  " type="button"
                 style="border-style:solid; border-width:1; background-color: #99CCCC; font-size: 11pt;"
                 onclick="addOtherUnderwriting()" align="left"
                 title="Add Other Underwriting To Enrollment."></input>
          <br></br>
          <br></br>
          <input name="btnLeft" value="  &#8592;  " type="button"
                 style="border-style:solid; border-width:1; background-color: #99CCCC; font-size: 11pt;"
                 onclick="removeOtherUnderwriting()"></input>
        </td>
        <td width="47%">
              <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
                    <jsp:param name="tableId" value="OtherUnderwritingTableModel"/>
                    <jsp:param name="tableHeight" value="90"/>
                    <jsp:param name="multipleRowSelect" value="true"/>
                    <jsp:param name="singleOrDoubleClick" value="single"/>
              </jsp:include>
        </td>
    </tr>
    <tr>
        <td align="left" width="47%">Value:&nbsp;
          <input type="text" name="otherValue" size="10" maxlength="10">
        </td>
        <td width="6%" align="center">&nbsp;</td>
        <td nowrap align="right" width="47%">&nbsp;</td>
    </tr>
  </table>
</span>
<%-- ****************************** END Form Data ****************************** --%>

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
    <input type="hidden" name="selectedProductStructure" value="">
    <input type="hidden" name="filteredProductPK" value="<%= filteredProductPK %>">
    <input type="hidden" name="activeEnrollmentPK" value="<%= activeEnrollmentPK %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>
