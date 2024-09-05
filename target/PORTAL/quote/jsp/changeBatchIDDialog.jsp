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

 <jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    BatchContractSetup[] batchContractSetups = (BatchContractSetup[]) session.getAttribute("BatchContractSetups");

    String responseMessage = (String) request.getAttribute("responseMessage");

	String segmentStatus = quoteMainSessionBean.getValue("status");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Change Batch ID</title>
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

    var segmentStatus = "<%= segmentStatus %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        checkForResponseMessage();
    }

    function findBatchContractSetups()
    {
        //  Check to make sure a Group Number has been entered
        if (f.contractGroupNumber == null || f.contractGroupNumber == "")
        {
            alert("A Group Number must be entered to find a Batch ID");
        }

        sendTransactionAction("QuoteDetailTran", "findBatchContractSetups", "_self");
    }

    function saveBatchIDChange()
    {
        //  Check to make sure a BatchContractSetup has been selected
        if (f.selectedBatchContractSetupPK.value == "Please Select")
        {
            alert("A Batch ID must be selected");
        }
        else if (segmentStatus != "SubmitPend")
        {
            alert("Batch ID Change Not Allowed - Invalid Status");
        }
        else
        {
            // Call the opening window's save method so its form will be submitted (and all hidden variables on its page will be saved)
            opener.saveBatchIDChange(f.selectedBatchContractSetupPK.value, f.contractGroupNumber.value);

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
                        <input:text name="contractGroupNumber" bean="contractGroup" attributesText="id='contractGroupNumber'" size="20"/>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp</td>
                </tr>
                <tr>
                    <td align="left" valign="top" nowrap>
                        Batch ID:&nbsp;
                    </td>
                </tr>
                <tr>
                    <td align="left" nowrap="nowrap">
                        <select name="selectedBatchContractSetupPK">

                            <%

                              //  Don't let "Please Select" display if there are no batchContractSetups.  Checks are
                              //  made against "Please Select" when determining if the user selected a batchContractSetup or not
                              if (batchContractSetups != null && batchContractSetups.length > 0)
                              {
                                  out.println("<option selected value=\"Please Select\"> Please Select </option>");

                                  for(int i = 0; i < batchContractSetups.length; i++)
                                  {
                                      String batchID = batchContractSetups[i].getBatchID();
                                      Long batchContractSetupPK = batchContractSetups[i].getBatchContractSetupPK();
    
                                      out.println("<option name=\"selectedBatchContractSetupPK\" value=\"" + batchContractSetupPK + "\">" + batchID + "</option>");
                                  }
                              }

                            %>
                        </select>
                    </td>
                    <td>
                        <input id="btnFind" type="button" value=" Find " onClick="findBatchContractSetups()">
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
			    <input id="btnSave" type="button" value=" Save " onClick="saveBatchIDChange()">
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
