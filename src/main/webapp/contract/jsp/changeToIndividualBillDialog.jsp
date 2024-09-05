<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page import="edit.common.EDITDate,
                 fission.utility.Util,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 edit.services.db.hibernate.*,
                 java.util.*,
                 contract.*,
                 client.*,
                 engine.*,
                 edit.portal.common.session.*,
                 edit.portal.taglib.InputSelect,
                 group.ContractGroup,
                 edit.common.vo.BillScheduleVO,
                 billing.*"%>
<!--
 * User: dlataill
 * Date: July 10, 2007
 * Time: 9:31:50 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] methods = codeTableWrapper.getCodeTableEntries("BILLINGMETHOD");

    BillScheduleVO billScheduleVO = (BillScheduleVO) session.getAttribute("billScheduleVO");
    long billSchedulePK = 0;

    if (billScheduleVO != null)
    {
        billSchedulePK = billScheduleVO.getBillSchedulePK();
    }

%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Change To Individual Billing</title>
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

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;
    }

    function saveChangeToIndividualBill()
    {
        sendTransactionAction("ContractDetailTran", "saveChangeToIndividualBill", "contractBillingDialog");
        window.close();
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
                    <td align="left" valign="top" nowrap>
                        Billing Method:&nbsp;
                    </td>
                    <td align="left" nowrap="nowrap">
                        <select name="billMethodCT">
                          <option selected value="Please Select"> Please Select </option>
                            <%

                              for(int i = 0; i < methods.length; i++)
                              {
                                  String codeTablePK = methods[i].getCodeTablePK() + "";
                                  String codeDesc    = methods[i].getCodeDesc();
                                  String code        = methods[i].getCode();

                                  if (!code.equalsIgnoreCase(BillSchedule.BILL_METHOD_LISTBILL))       // Don't display List Bill - need individual bill method
                                  {
                                      out.println("<option name=\"billMethodCT\" value=\"" + code + "\">" + codeDesc + "</option>");
                                  }
                              }

                            %>
                        </select>
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
			    <input id="btnSave" type="button" value=" Save " onClick="saveChangeToIndividualBill()">
                <input type="button" name="cancel" value="Cancel" onClick="window.close()">
	  	    </td>
        </tr>
    </table>
</span>

<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input:hidden name="transaction"/>
<input:hidden name="action"/>
<input type="hidden" name="billSchedulePK" value="<%= billSchedulePK %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>
