<%@ page import="codetable.dm.dao.CodeTableDAO,
                 contract.Segment,
                 engine.ProductStructure,
                 engine.FilteredFund,
                 java.util.Set,
                 engine.Fund,
                 edit.common.vo.*,
                 edit.common.*,
                 fission.utility.*"%>
<!--
 * User: sprasad
 * Date: Mar 18, 2005
 * Time: 5:07:16 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    CodeTableVO[] stateVOs = codeTableWrapper.getCodeTableEntries("STATE");

    ProductStructure[] productStructures = ProductStructure.findByTypeCodeCT(ProductStructure.TYPECODECT_PRODUCT);

    String companyStructurePK = Util.initString((String) request.getAttribute("companyStructurePK"), "0");
    ProductStructure productStructure = ProductStructure.findByPK(new Long(companyStructurePK));

    CodeTableVO[] optionVOs   = codeTableWrapper.getCodeTableEntries("OPTIONCODE", Long.parseLong(companyStructurePK));
    CodeTableVO[] frequencyVOs = codeTableWrapper.getCodeTableEntries("FREQUENCY", Long.parseLong(companyStructurePK));

    String segmentPK    = (String) request.getAttribute("segmentPK");
    Segment segment     = Segment.findByPK(new Long(segmentPK));

    String contractNumber   = segment.getContractNumber();
    String segmentNameCT    = segment.getSegmentNameCT();

    String casetrackingOption = (String) request.getAttribute("casetrackingOption");

    String activeFilteredFundPK = Util.initString((String) request.getAttribute("activeFilteredFundPK"), "0");
    String activeAllocationPct  = Util.initString((String) request.getAttribute("activeFundAllocationPct"), "");

    EDITDate effectiveEDITDate = (EDITDate) request.getAttribute("effectiveDate");

    EDITBigDecimal paymentAmount = (EDITBigDecimal) null;
    EDITBigDecimal purchaseAmount = (EDITBigDecimal) null;
    EDITDate startEDITDate = (EDITDate) null;
    String certainPeriod = "";
    String frequency = "";
    String annuityOption = "";

    CaseTrackingQuoteVO quoteVO = (CaseTrackingQuoteVO) session.getAttribute("caseTrackingQuoteVO");
    if (quoteVO != null)
    {
        SegmentVO segmentVO = quoteVO.getSegmentVO(0);
        PayoutVO payoutVO = segmentVO.getPayoutVO(0);
        paymentAmount = new EDITBigDecimal(payoutVO.getPaymentAmount());
        purchaseAmount = new EDITBigDecimal(segmentVO.getAmount());
        startEDITDate = new EDITDate(payoutVO.getPaymentStartDate());
        certainPeriod = payoutVO.getCertainDuration() + "";
        frequency = payoutVO.getPaymentFrequencyCT();

    }
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Case Tracking - Supplemental / Open Claim Contract</title>
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
        initScrollTable(document.getElementById("FundTableModelScrollTable"));
    }

    /*
     * Moves rows.
     */
    function moveRows(tableId)
    {
        move(tableId, "CaseTrackingTran", "updateCasetrackingOptionDoubleTable", "_self");
    }

    function saveToFunds()
    {
        sendTransactionAction("CaseTrackingTran", "saveToFundSelections", "_self");
    }

    function performPayoutQuote()
    {
       try
       {
            sendTransactionAction("CaseTrackingTran", "performPayoutQuote", "_self");
       }
       catch (e)
       {
            alert(e);
       }
    }

    function saveSuppOpenClaim()
    {

        if (verifyIfRowsExist("ToTableSummary") == false)
        {
            alert('No clients selected');
            return;
        }

        if (selectElementIsEmpty(f.companyStructurePK))
        {
            alert('Please Select Company Structure');
            return;
        }

        if (f.createNewPolicy.checked == false)
         {
              alert('Please check New Policy Ind');
              return;
         }

        if (f.createNewPolicy.checked == true)
         {
              if (textElementIsEmpty(f.newPolicyNumber))
              {
                  alert('Please enter Policy Number');
                  return;
              }

              if (selectElementIsEmpty(f.issueState))
              {
                 alert('Please Enter Issue State');
                 return;
              }
         }



        if (textElementIsEmpty(f.effectiveDate))
        {
            alert('Please Enter Effective Date');
            return;
        }

        disableActionButtons();

        sendTransactionAction("CaseTrackingTran", "saveSupplementalContractOpenClaim", "_self");
    }

    function onTableRowSingleClick(tableId)
    {
        sendTransactionAction("CaseTrackingTran", "showFundDetails", "_self");
    }

    function refreshAndPopulate()
    {
        sendTransactionAction("CaseTrackingTran", "showSelectedTransaction", "_self");
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

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
<%--    BEGIN Form Content --%>
    <tr>
        <td align="left" colspan="2" nowrap>
            Contract Number:&nbsp;
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
                <jsp:param name="multipleRowSelect" value="false"/>
            </jsp:include>
        </td>
    </tr>
    <tr>
        <td colspan="2" nowrap>
            <table width="100%" border="0" cellspacing="0" cellpadding="5">
              <tr>
                <td>
                  Key:&nbsp;
                </td>
                <td>
                  <select name="companyStructurePK" onChange="refreshAndPopulate()">
                    <option value="null">Please Select</option>
                    <%
                        for(int i=0; i< productStructures.length; i++)
                        {
                            Long currentCompanyStructurePK = productStructures[i].getProductStructurePK();

                            if (currentCompanyStructurePK.longValue() == Long.parseLong(companyStructurePK))
                            {
                                out.println("<option selected value=\""+ currentCompanyStructurePK + "\">" + productStructures[i].toString() + "</option>");
                            }
                            else
                            {
                                out.println("<option value=\""+ currentCompanyStructurePK + "\">" + productStructures[i].toString() + "</option>");
                            }
                        }
                    %>
                  </select>
                </td>
              </tr>
              <tr>
                <td>
                  Transaction Type:&nbsp;
                </td>
                <td>
                  <input name="transactionType" type="text" size="20" value="<%= casetrackingOption %>">
                </td>
              </tr>
              <tr>
                <td>
                  Effective Date:&nbsp;
                </td>
                <td>
                    <input type="text" name="effectiveDate" value="<%= effectiveEDITDate == null ? "" : DateTimeUtil.formatYYYYMMDDToMMDDYYYY(effectiveEDITDate.getFormattedDate()) %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
                    <a href="javascript:show_calendar('f.effectiveDate', f.effectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
                </td>
              </tr>
              <tr>
                <td>
                  To Funds:&nbsp;
                </td>
                <td>
                  &nbsp;
                </td>
              </tr>
              <tr>
                <td colspan="2">
                  <table width="100%" style="border:1px solid black" cellspacing="0" cellpadding="0">
                    <tr>
                      <td align="left">
                        <table width="100%" border="0" cellspacing="0" cellpadding="5">
                          <tr>
                            <td align="left">
                              Fund:&nbsp;
                            </td>
                            <td align="left">
                              <select name="filteredFundPK">
                                <option value="null">Please Select</option>
                                <%
                                    if (productStructure != null)
                                    {
                                        Set filteredFundSet = productStructure.getFilteredFunds();

                                        FilteredFund[] filteredFunds = (FilteredFund[]) filteredFundSet.toArray(new FilteredFund[filteredFundSet.size()]);

                                        filteredFunds = (FilteredFund[]) Util.sortObjects(filteredFunds, new String[] {"getFilteredFundPK"});

                                        for (int i = 0; i < filteredFunds.length; i++)
                                        {
                                            FilteredFund filteredFund = filteredFunds[i];
                                            long filteredFundPK = filteredFund.getPK();

                                            Fund fund = filteredFund.getFund();
                                            String fundName = fund.getName();

                                            if (filteredFundPK == Long.parseLong(activeFilteredFundPK))
                                            {
                                                out.println("<option selected name=\"id\" value=\"" + filteredFundPK + "\">" + fundName + "</option>");
                                            }
                                            else
                                            {
                                                out.println("<option name=\"id\" value=\"" + filteredFundPK + "\">" + fundName + "</option>");
                                            }
                                        }
                                    }
                                %>
                              </select>
                            </td>
                            <td align="left">
                              Percent:&nbsp;
                            </td>
                            <td>
                              <input name="allocationPercent" type="text" size="4" value="<%= activeAllocationPct %>">
                            </td>
                          </tr>
                          <tr>
                            <td colspan="4">
                              <input type="button" value="Save" onClick="saveToFunds()">
                            </td>
                          </tr>
                          <tr>
                            <td colspan="4">
                                <%-- ****************************** BEGIN Summary Area ****************************** --%>
                                <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
                                    <jsp:param name="tableId" value="FundTableModel"/>
                                    <jsp:param name="tableHeight" value="30"/>
                                    <jsp:param name="multipleRowSelect" value="false"/>
                                    <jsp:param name="singleOrDoubleClick" value="single"/>
                                </jsp:include>
                                <%-- ****************************** END Summary Area ****************************** --%>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td>
                  New Policy:&nbsp;
                </td>
                <td>
                  &nbsp;
                </td>
              </tr>
              <tr>
                <td colspan="2">
                  <table width="100%" style="border:1px solid black" cellspacing="0" cellpadding="0"><tr><td align="left">
                    <table width="100%" border="0" cellspacing="0" cellpadding="5">
                      <tr>
                        <td align="left" colspan="2">
                          <input type="checkbox" name="createNewPolicy">&nbsp;
                          Create New Policy
                        </td>
                      </tr>
                      <tr>
                        <td align="left">
                          New Policy Number:&nbsp;
                        </td>
                        <td align="left">
                          <input name="newPolicyNumber" type="text" size="10" value="">
                        </td>
                      </tr>
                      <tr>
                        <td align="left">
                          Issue State:&nbsp;
                        </td>
                        <td>
                          <select name="issueState">
                            <option value="null">Please Select</option>
                            <%
                                for(int i = 0; i < stateVOs.length; i++)
                                {
                                    String codeDesc    = stateVOs[i].getCodeDesc();
                                    String code        = stateVOs[i].getCode();

                                    /*if (selectedState.equalsIgnoreCase(code)) {

                                        out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                                    }
                                    else
                                    {*/
                                        out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                                    //}
                                }
                          %>
                          </select>
                        </td>
                      </tr>
                    </table>
                  </td></tr></table>
                </td>
              </tr>
            </table>
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
        <td colspan="2" align="right" valign="top" nowrap>
            <table width="100%" border="0" cellspacing="0" cellpadding="5">
              <tr>
                <td>
                  Payouts
                </td>
              </tr>
              <tr>
                <td align="right">
                  <table width="100%" style="border:1px solid black" cellspacing="0" cellpadding="0"><tr><td align="right">
                    <table width="100%" border="0" cellspacing="0" cellpadding="5">
                      <tr>
                        <td align="right">
                          Annuity Option:&nbsp;
                        </td>
                        <td align="left">
                          <select name="annuityOption">
                            <option value="null">Please Select</option>
                            <%
                                  for(int i = 0; i < optionVOs.length; i++)
                                  {
                                      String codeDesc    = optionVOs[i].getCodeDesc();
                                      String code        = optionVOs[i].getCode();

                                      if (annuityOption.equalsIgnoreCase(code))
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
                        <td align="right">
                          Frequency:&nbsp;
                        </td>
                        <td align="left">
                          <select name="paymentFrequencyCT">
                            <option value="null">Please Select</option>
                            <%
                                  for(int i = 0; i < frequencyVOs.length; i++)
                                  {
                                      String codeDesc    = frequencyVOs[i].getCodeDesc();
                                      String code        = frequencyVOs[i].getCode();

                                      if (frequency.equalsIgnoreCase(code))
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
                        <td align="right">
                          Certain Period:&nbsp;
                        </td>
                        <td align="left">
                          <input name="certainDuration" type="text" size="15" value="<%= certainPeriod %>">
                        </td>
                      </tr>
                      <tr>
                        <td align="right">
                          Start Date:&nbsp;
                        </td>
                        <td align="left">
                            <input type="text" name="startDate" value="<%= startEDITDate == null ? "" : DateTimeUtil.formatYYYYMMDDToMMDDYYYY(startEDITDate.getFormattedDate()) %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
                            <a href="javascript:show_calendar('f.startDate', f.startDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
                        </td>
                      </tr>
                      <tr>
                        <td align="right">
                          Purchase Amount:&nbsp;
                        </td>
                        <td align="left">
                          <input name="purchaseAmount" type="text" size="19" value="<%= paymentAmount == null ? "" : purchaseAmount.toString() %>" CURRENCY>
                        </td>
                      </tr>
                      <tr>
                        <td align="right">
                          Payment Amount:&nbsp;
                        </td>
                        <td align="left">
                          <input name="paymentAmount" type="text" size="19" value="<%= purchaseAmount == null ? "" : paymentAmount.toString() %>" CURRENCY>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          &nbsp;
                        </td>
                        <td>
                          <input type="button" value="Quote" onClick="performPayoutQuote()">
                        </td>
                      </tr>
                    </table>
                  </td></tr></table>
                </td>
              </tr>
            </table>
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
            <input id="btnSave"   type="button" value=" Save " onClick="saveSuppOpenClaim()">
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
<%--<input type="hidden" name="effectiveDate">--%>
<%--<input type="hidden" name="startDate">--%>
<input type="hidden" name="contractNumber" value="<%= contractNumber %>">
<input type="hidden" name="activeFilteredFundPK" value="<%= activeFilteredFundPK %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>