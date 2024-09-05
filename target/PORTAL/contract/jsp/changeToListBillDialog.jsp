<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page import="group.*"%>
<!--
 * User: sdorman
 * Date: January 15, 2008
 * Time: 9:31:50 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

 <jsp:useBean id="contractGroup" class="group.ContractGroup" scope="request"/>


<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    DepartmentLocation[] departmentLocations = (DepartmentLocation[]) session.getAttribute("DepartmentLocations");
      String groupNumber = (String) session.getAttribute("groupNumber");

    String responseMessage = (String) request.getAttribute("responseMessage");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Change To List Billing</title>
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
    var originalGroupNumber = "<%= groupNumber %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        checkForResponseMessage();
    }

    function findDepartmentLocations()
    {
        //  Check to make sure a Group Number has been entered
        if (f.contractGroupNumber == null || f.contractGroupNumber == "") {
            alert("A Group Number must be entered to find a Department Location");
        }

        sendTransactionAction("ContractDetailTran", "findDepartmentLocations", "_self");
    }

    function saveChangeToListBill() {
       
        //  Check to make sure a DepartmentLocation has been selected
        if (f.departmentLocationPK.value == "Please Select") {
            alert("A Department/Location must be selected");
        } else if (f.contractGroupNumber.value != originalGroupNumber) {
            var continueProcessing = confirm("Are you sure you want to assign policy to a new group?");
	    if (continueProcessing) {
                sendTransactionAction("ContractDetailTran", "saveChangeToListBill", "contractBillingDialog");
                window.close();
            }
        } else {
            sendTransactionAction("ContractDetailTran", "saveChangeToListBill", "contractBillingDialog");
            window.close();
        }
    }


</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="dialog" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<span id="mainContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:70%; top:0; left:0; z-index:0; overflow:visible">
<%--    BEGIN Form Content --%>
  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" valign="top" nowrap colspan="5">&nbsp;</td>
    </tr>
    <tr>
        <td align="left" valign="top" colspan="5" rowspan="3">
            <span style="width:27%; position:relative; top:0; left:0; z-index:0; overflow:visible">
              <table width="100%" height="100%">
                <tr>
                    <td align="left" nowrap>
                        Group Number:&nbsp;
                    </td>
                </tr>
                <tr>
                    <td>
			    <input:text name="contractGroupNumber" default="<%= groupNumber %>" bean="contractGroup" attributesText="id='contractGroupNumber' " size="20"/>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp</td>
                </tr>
                <tr>
                    <td align="left" valign="top" nowrap>
                        Department/Location:&nbsp;
                    </td>
                </tr>
                <tr>
                    <td align="left" nowrap="nowrap">
                        <select name="departmentLocationPK">

                            <%
                              //  Don't let "Please Select" display if there are no departmentLocations.  Checks are
                              //  made against "Please Select" when determining if the user selected a departmentLocation or not
                              if (departmentLocations != null && departmentLocations.length > 0)
                              {
                                  out.println("<option selected value=\"Please Select\"> Please Select </option>");

                                  for(int i = 0; i < departmentLocations.length; i++)
                                  {
                                      String departmentLocationName = departmentLocations[i].getDeptLocName();
                                      String departmentLocationCode = departmentLocations[i].getDeptLocCode();
                                      Long departmentLocationPK = departmentLocations[i].getDepartmentLocationPK();
    
                                      out.println("<option name=\"departmentLocationPK\" value=\"" + departmentLocationPK + "\">" + departmentLocationCode + " - " + departmentLocationName + "</option>");
                                  }
                              }

                            %>
                        </select>
                    </td>
                    <td>
                        <input id="btnFind" type="button" value=" Find " onClick="findDepartmentLocations()">
                    </td>
                </tr>
              </table>
            </span>
        </td>
    </tr>
  </table>
<%--    END Form Content --%>
</span>
<span style="width:100%; position:relative; top:0; left:0; z-index:0; overflow:visible">
    <table width="100%" height="100%">
        <tr>
            <td align="right" valign="top" nowrap colspan="5">&nbsp;</td>
        </tr>
        <tr>
	  	    <td nowrap align="right" colspan="5">
			    <input id="btnSave" type="button" value=" Save " onClick="saveChangeToListBill()">
                <input type="button" name="cancel" value="Cancel" onClick="window.close()">
	  	    </td>
        </tr>
    </table>
</span>

<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input:hidden name="transaction"/>
<input:hidden name="action"/>
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>
