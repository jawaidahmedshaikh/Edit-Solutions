<%@ page import="edit.common.vo.CodeTableVO,
                 codetable.dm.dao.CodeTableDAO,
                 fission.utility.Util,
                 edit.common.CodeTableWrapper,
                 contract.Segment,
                 engine.*"%>
<!--
 * User: dlataille
 * Date: Oct 3, 2005
 * Time: 12:21:16 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    String segmentPK    = (String) request.getAttribute("segmentPK");
    Segment segment     = Segment.findByPK(new Long(segmentPK));

    String contractNumber   = segment.getContractNumber();
    String segmentNameCT    = segment.getSegmentNameCT();

    ProductStructure productStructure = ProductStructure.findByPK(segment.getProductStructureFK());

    String key = productStructure.getCompanyName() + ", " + productStructure.getMarketingPackageName() + ", " +
                 productStructure.getGroupProductName() + ", " + productStructure.getAreaName() + ", " +
                 productStructure.getBusinessContractName();

    String casetrackingOption = (String) request.getAttribute("casetrackingOption");
    String newPolicyIndStatus = Util.initString((String) request.getAttribute("newPolicyIndStatus"), "");
    String newPolicyNumber = Util.initString((String) request.getAttribute("newPolicyNumber"), "");
    String beneficiaryStatus = Util.initString((String) request.getAttribute("beneficiaryStatusCT"), "");
    String frequencyCT = Util.initString((String) request.getAttribute("frequencyCT"), "");
    String rmdStartDate = Util.initString((String) request.getAttribute("rmdStartDate"), "");   
    String printLine1 = Util.initString((String) request.getAttribute("printLine1"), "");
    String printLine2 = Util.initString((String) request.getAttribute("printLine2"), "");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] beneficiaryStatuses = codeTableWrapper.getCodeTableEntries("BENEFICIARYSTATUS");
    CodeTableVO[] rmdFrequencies = codeTableWrapper.getCodeTableEntries("RMDFREQUENCY");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Case Tracking - Stretch IRA</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

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

        formatCurrency();

        // Initialize scroll tables
        initScrollTable(document.getElementById("ContractDetailDoubleTableModelScrollTable"));
    }

    /**
     * Dummary method to show how one might show the detail of a selected row in the summary table.
     */
    function showTableSummaryDetail()
    {
        var selectedRowId = getSelectedRowId("tableSummary");

        if (valueIsEmpty(selectedRowId))
        {
            alert("Value Is Empty");
        }
        else
        {
            f.activeTableSummaryPK.value = selectedRowId;

            sendTransactionAction("FooTran", "showTableSummaryDetail", "main");
        }
    }

    /**
     *  save the Strect Ira transactions requested
     */
    function saveStretchIRA()
    {
       if (verifyIfRowsExist("ToTableSummary") == false)
       {
            alert('No clients selected');
            return;
       }

       if (f.newPolicyInd.checked == false)
       {
            alert('Please check New Policy Ind');
            return;
       }
       else
       {
            if (f.newPolicyNumber.value == "")
            {
                alert('Please enter Policy Number');
                return;
            }
       }

       disableActionButtons();

       sendTransactionAction("CaseTrackingTran", "saveStretchIRA", "_self");
    }

    /**
     * While a tranaction is being saved, we want to disable all buttons to prevent double-saves.
     */
    function disableActionButtons()
    {
        document.all.trxMessage.innerHTML = "<font color='blue' face=''>Processing...<font>";

        document.all.btnSave.style.backgroundColor = "#99BBBB";
        document.all.btnCancel.style.backgroundColor = "#99BBBB";
        document.all.btnClose.style.backgroundColor = "#99BBBB";

        document.all.btnSave.disabled = true;
        document.all.btnCancel.disabled = true;
        document.all.btnClose.disabled = true;
    }


    /*
     * Moves rows.
     */
    function moveRows(tableId)
    {
        move(tableId, "CaseTrackingTran", "updateCasetrackingOptionDoubleTable", "_self");
    }

    function showBeneficiariesDialog()
    {
        if (f.newPolicyInd.checked == true)
        {
			f.newPolicyIndStatus.value = "checked";
		}
        else
        {
			f.newPolicyIndStatus.value = "";
        }

        var width = 0.99 * screen.width;
        var height = 0.80 * screen.height;

        openDialog("stretchIRABeneDialog", "top=0,left=0,resizable=no", width,  height);
        sendTransactionAction("CaseTrackingTran", "showStretchIRABeneDialog", "stretchIRABeneDialog");
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
          <jsp:param name="tableId" value="ContractDetailDoubleTableModel"/>
          <jsp:param name="multipleRowSelect" value="true"/>
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
        Key:&nbsp;
      </td>
      <td align="left" nowrap colspan="5">
        <input disabled name="key" type="text" size="50" maxlength="50" value="<%= key %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>
        Transaction Type:&nbsp;
      </td>
      <td align="left" nowrap colspan="2">
        <input disabled name="transactionType" type="text" size="20" maxlength="20" value="<%= casetrackingOption %>">
      </td>
      <td align="left" nowrap colspan="3">
        <a href="javascript:showBeneficiariesDialog()">Beneficiaries</a>
      </td>
    </tr>
    <tr>
      <td align="left" valign="top" colspan="2" rowspan="3" nowrap>
        New Policy
        <br>
        <span style="border-style:solid; border-width:1; position:relative; width:45%; height:35% top:0; left:0; z-index:0; overflow:visible">
          <table width="100%" height="100%" border="0" cellspacing="1" cellpadding="1">
            <tr>
              <td align="left" nowrap>
                <input type="checkbox" name="newPolicyInd" <%= newPolicyIndStatus %>>&nbsp;New Policy
              </td>
            </tr>
            <tr>
              <td colspan="6">
                &nbsp;
              </td>
            </tr>
            <tr>
              <td align="left" nowrap>New Policy Number:&nbsp;
                <input type="text" name="newPolicyNumber" type="text" size="15" maxlength="15" value="<%= newPolicyNumber %>">
              </td>
            </tr>
          </table>
        </span>
      </td>
      <td align="left" valign="top" colspan="4" rowspan="3" nowrap>
        Required Minimum Distribution
        <br>
        <span style="border-style:solid; border-width:1; position:relative; width:100%; height:100% top:0; left:0; z-index:0; overflow:visible">
          <table width="100%" height="100%" border="0" cellspacing="1" cellpadding="1">
            <tr>
              <td align="right" nowrap>Beneficiary Status:&nbsp;</td>
              <td align="left" nowrap>
                <select name="beneficiaryStatusCT">
                    <option value="null">Please Select</option>
                    <%
                        if (beneficiaryStatuses != null)
                        {
                            for(int i = 0; i < beneficiaryStatuses.length; i++)
                            {
                                String currentCodeDesc    = beneficiaryStatuses[i].getCodeDesc();
                                String currentCode        = beneficiaryStatuses[i].getCode();

                                if (currentCode.equals(beneficiaryStatus))
                                {
                                    out.println("<option selected name=\"id\" value=\"" + currentCode + "\">" + currentCodeDesc + "</option>");
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
            </tr>
            <tr>
              <td align="right" nowrap>Frequency:&nbsp;</td>
              <td align="left" nowrap>
                <select name="frequencyCT">
                    <option value="null">Please Select</option>
                    <%
                        if (rmdFrequencies != null)
                        {
                            for(int i = 0; i < rmdFrequencies.length; i++)
                            {
                                String currentCodeDesc    = rmdFrequencies[i].getCodeDesc();
                                String currentCode        = rmdFrequencies[i].getCode();

                                if (currentCode.equals(frequencyCT))
                                {
                                    out.println("<option selected name=\"id\" value=\"" + currentCode + "\">" + currentCodeDesc + "</option>");
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
            </tr>
            <tr>
              <td align="right" nowrap>Start Date:&nbsp;</td>
              <td align="left" nowrap>
               <input type="text" name="rmdStartDate" value="<%= rmdStartDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
               <a href="javascript:show_calendar('f.rmdStartDate', f.rmdStartDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
              </td>
            </tr>
          </table>
        </span>
      </td>
    </tr>
    <tr>
      <td colspan="6">
        &nbsp;
      </td>
    </tr>
    <tr>
      <td colspan="6">
        &nbsp;
      </td>
    </tr>
    <tr>
      <td colspan="6">
        &nbsp;
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Print Line 1:&nbsp;</td>
      <td align="left" nowrap>
        <input name="printLine1" type="text" size="70" maxlength="70" value="<%= printLine1 %>">
      </td>
   </tr>
   <tr>
      <td align="right" nowrap>Print Line 2:&nbsp;</td>
      <td align="left" nowrap>
        <input name="printLine2" type="text" size="70" maxlength="70" value="<%= printLine2 %>">
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
            <input id="btnSave" type="button" value=" Save " onClick="saveStretchIRA()">
            <input id="btnCancel" type="button" value=" Cancel " onClick="resetForm()">
            <input id="btnClose" type="button" value=" Close " onClick="closeWindow()">
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="segmentPK" value="<%= segmentPK %>">
<input type="hidden" name="casetrackingOption" value="<%= casetrackingOption %>">
<input type="hidden" name="newPolicyIndStatus" value="">
<%--<input type="hidden" name="rmdStartDate">--%>
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>
