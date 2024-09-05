<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.common.exceptions.VOEditException,
                 edit.portal.exceptions.PortalEditingException,
                 edit.portal.common.session.UserSession,
                 fission.utility.*" %>

<jsp:useBean id="formBean"
   class="fission.beans.PageBean" scope="request"/>
<%
    event.business.Event eventComponent = new event.component.EventComponent();

    FilteredFundVO[] hedgeFilteredFundVOs = (FilteredFundVO[]) session.getAttribute("hedgeFilteredFundVOs");
    SuspenseVO[] suspenseVOs = (SuspenseVO[]) session.getAttribute("hedgeSuspenseVOs");
    EDITTrxVO[] editTrxVOs = (EDITTrxVO[]) session.getAttribute("hedgeEditTrxVOs");

    String errorMessage = Util.initString((String) request.getAttribute("errorMessage"), "");
    String suspenseMessage = Util.initString((String) request.getAttribute("suspenseMessage"), "");
    String selectedFilteredFundFK = Util.initString((String) request.getAttribute("selectedFilteredFundFK"), "");
    String selectedSuspensePK = Util.initString((String) request.getAttribute("selectedSuspensePK"), "");
    String selectedEditTrxPK = Util.initString((String) request.getAttribute("selectedEditTrxPK"), "");
    String selectedOrigTrxFK = Util.initString((String) request.getAttribute("selectedOrigTrxFK"), "");

    String batchId = "";
    String creationMonth = "";
    String creationDay = "";
    String creationYear = "";
    String originalAmount = "";
    String batchAmount = "";
    String remainingAmount = "";
    String pendingAmount = "";
    String trxAmount = "";
    String effectiveMonth = Util.initString((String) request.getAttribute("effectiveMonth"), "");
    String effectiveDay = Util.initString((String) request.getAttribute("effectiveDay"), "");
    String effectiveYear = Util.initString((String) request.getAttribute("effectiveYear"), "");
    if (suspenseVOs != null)
    {
        for (int i = 0; i < suspenseVOs.length; i++)
        {
            if ((suspenseVOs[i].getSuspensePK() + "").equals(selectedSuspensePK))
            {
                batchId = suspenseVOs[i].getUserDefNumber();
                String[] creationDate = DateTimeUtil.initDate(suspenseVOs[i].getEffectiveDate());
                creationYear = creationDate[0];
                creationMonth = creationDate[1];
                creationDay = creationDate[2];
                batchAmount = suspenseVOs[i].getSuspenseAmount().toString();
                originalAmount = suspenseVOs[i].getOriginalAmount().toString();
                pendingAmount = suspenseVOs[i].getPendingSuspenseAmount().toString();
                remainingAmount = new EDITBigDecimal(suspenseVOs[i].getSuspenseAmount()).subtractEditBigDecimal(pendingAmount).toString();
                break;
            }
        }
    }

    if (editTrxVOs != null)
    {
        for (int i = 0; i < editTrxVOs.length; i++)
        {
            if ((editTrxVOs[i].getEDITTrxPK() + "").equals(selectedEditTrxPK))
            {
                trxAmount = Util.roundToNearestCent(editTrxVOs[i].getNotificationAmount()).
                            subtractEditBigDecimal(Util.roundToNearestCent(editTrxVOs[i].getNotificationAmountReceived())).toString();
                break;
            }
        }
    }

    if (!Util.initString((String) request.getAttribute("trxAmount"), "").equals(""))
    {
        trxAmount = Util.roundToNearestCent(new EDITBigDecimal(trxAmount)).toString();
    }
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="JavaScript1.2">

<!--******* JAVASCRIPT ******//-->

    var f = null;

    var suspenseMessage = "<%= suspenseMessage %>";
    var errorMessage = "<%= errorMessage %>";

    function init()
    {
        f = document.hedgeFundRedemptionForm;

        if (errorMessage != "")
        {
            alert(errorMessage);
        }
        else if (suspenseMessage == "Error" || suspenseMessage == "ErrorAmountUsed")
        {
            if (suspenseMessage == "Error")
            {
                alert("Suspense Amount Entered/Selected > Remaining Suspense Amount");
            }
            else
            {
                alert("Suspense Amount Entered/Selected > Amount Needed");
            }
            f.contractAmount.focus();
        }
        else
        {
            f.effectiveMonth.focus();
        }

        formatCurrency();
    }

    function selectHedgeSuspense()
    {
        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        var suspensePK = currentRow.suspensePK;
        f.selectedSuspensePK.value = suspensePK;

        sendTransactionAction("EventAdminTran", "showSelectedHedgeSuspense", "_self");
    }

    function selectEditTrx()
    {
        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        var editTrxPK = currentRow.editTrxPK;
        var originatingTrxFK = currentRow.originatingTrxFK
        f.selectedEditTrxPK.value = editTrxPK;
        f.selectedOrigTrxFK.value = originatingTrxFK;

        sendTransactionAction("EventAdminTran", "showSelectedEditTrxForRedemption", "_self");
    }

    function retrieveSuspenseAndTransactions()
    {
        sendTransactionAction("EventAdminTran", "retrieveSuspenseAndTransactionsForHedgeFund", "_self");
    }

    function generateTransaction()
    {
        if (f.selectedSuspensePK.value == "")
        {
            alert("Please Select Suspense Entry for Redemption");
        }
        else if (f.trxAmount.value == "")
        {
            alert("Please Enter The Redemption Amount For The Transaction");
        }
        else
        {
            sendTransactionAction("EventAdminTran", "generateRedemptionTransaction", "_self");
        }
    }

    function showDivisionLevelFeesDialog()
    {
        var width = .99 * screen.width;
        var height = .50 * screen.height;

        if (valueIsEmpty(f.selectedFilteredFundFK.value) || f.selectedFilteredFundFK.value == "0")
        {
            alert("Please Select the Fund");
        }
        else if (valueIsEmpty(f.selectedSuspensePK.value))
        {
            alert("Please Select Suspense Entry for Redemption");
        }
        else
        {
            openDialog("divisionLevelFees", "top=0,left=0,resizable=no", width, height);
            sendTransactionAction("EventAdminTran", "showDivisionLevelFeesDialog", "divisionLevelFees");
        }
    }
</script>
<head>
<title>Hedge Fund Redemption</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<!-- ****** HTML CODE ***** //-->

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1">
<form name="hedgeFundRedemptionForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="mainContent" style="position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table width="90%" height="90%" border="0" cellspacing="4" cellpadding="0">
    <tr>
      <td align="left" nowrap colspan="2">Fund Name:&nbsp;
        <select name="filteredFundFK">
          <option selected value="Please Select"> Please Select </option>
            <%
              if (hedgeFilteredFundVOs != null)
              {
                  for(int i = 0; i < hedgeFilteredFundVOs.length; i++)
                  {
                      String filteredFundPK = hedgeFilteredFundVOs[i].getFilteredFundPK() + "";
                      String fundNumber = hedgeFilteredFundVOs[i].getFundNumber();

                      if (selectedFilteredFundFK.equalsIgnoreCase(filteredFundPK))
                      {
                          out.println("<option selected name=\"id\" value=\"" + filteredFundPK+ "\">" + fundNumber + "</option>");
                      }
                      else
                      {
                          out.println("<option name=\"id\" value=\"" + filteredFundPK + "\">" + fundNumber + "</option>");
                      }
                  }
              }
            %>
        </select>
        &nbsp;&nbsp;
        <input type="button" name="retrieve" value="Retrieve" onClick="retrieveSuspenseAndTransactions()">
      </td>
    </tr>
    <tr>
      <td colspan="2">
        &nbsp;
      </td>
    </tr>
    <tr>
      <td align="left" nowrap colspan="2">
        Suspense Ids:
      </td>
    </tr>
    <tr>
      <td align="left" nowrap width="37%" height="100%" rowspan="4" valign="top">
        <table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td>
              <span class="scrollableContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:scroll; background-color: #BBBBBB">
                <table class="summary" id="batchIdSummaryTable" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
                <%
                    String className = "";
                    boolean selected = false;
                    if (suspenseVOs != null)
                    {
                        for(int i = 0; i < suspenseVOs.length; i++)
                        {
                            EDITBigDecimal suspenseAmt =  new EDITBigDecimal(suspenseVOs[i].getSuspenseAmount());
                            suspenseAmt =  Util.roundToNearestCent(suspenseAmt);
                            EDITBigDecimal pendSuspenseAmt =  new EDITBigDecimal(suspenseVOs[i].getPendingSuspenseAmount());
                            pendSuspenseAmt = Util.roundToNearestCent(pendSuspenseAmt);
                            suspenseAmt = suspenseAmt.subtractEditBigDecimal(pendSuspenseAmt);
                            suspenseAmt =  Util.roundToNearestCent(suspenseAmt);
                            if (suspenseAmt.isGT("0"))
                            {
                                String suspensePK = suspenseVOs[i].getSuspensePK() + "";
                                String sBatchId = suspenseVOs[i].getUserDefNumber();
                                String sType = suspenseVOs[i].getSuspenseType();
                                if (suspensePK.equalsIgnoreCase(selectedSuspensePK))
                                {
                                    className = "highlighted";
                                    selected = true;
                                }
                                else
                                {
                                    className = "default";
                                    selected = false;
                                }

                                String sCreationDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(suspenseVOs[i].getEffectiveDate());
                %>
                <tr class="<%= className %>" suspensePK="<%= suspensePK %>" onMouseOver="highlightRow()"
                      onMouseOut="unhighlightRow()" onClick="selectHedgeSuspense()" isSelected="<%= selected %>">
                  <td>
                    <%= sBatchId %>
                  </td>
                  <td>
                    &nbsp;
                  </td>
                  <td>
                    <%= sCreationDate %>
                  </td>
                  <td>
                    &nbsp;
                  </td>
                  <td>
                    <%= sType %>
                  </td>
                </tr>
                <%
                            }
                        }
                    }
                %>
                </table>
              </span>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td height="25%" valign="top">
        <table class="formData" height="100%" width="100%" height="100%" border="0" cellspacing="6" cellpadding="0">
          <tr>
            <td align="left" nowrap>Suspense ID:&nbsp;
              <input disabled type="text" name="batchId" size="20" maxlength="20" value="<%= batchId %>">
            </td>
            <td align="left" nowrap>Creation Date:&nbsp;
              <input disabled type="text" name="creationMonth" size="2" maxlength="2" value="<%= creationMonth %>">
              /
              <input disabled type="text" name="creationDay" size="2" maxlength="2" value="<%= creationDay %>">
              /
              <input disabled type="text" name="creationYear" size="4" maxlength="4" value="<%= creationYear %>">
            </td>
          </tr>
          <tr>
            <td align="left" nowrap>Original Suspense Amount:&nbsp;
              <input disabled type="text" name="originalAmount" size="15" maxlength="15" value="<%= originalAmount %>" CURRENCY>
            </td>
            <td align="left" nowrap>Remaining Suspense Amount:&nbsp;
              <input disabled type="text" name="batchAmount" size="15" maxlength="15" value="<%= remainingAmount %>" CURRENCY>
            </td>
          </tr>
          <tr>
            <td align="left" nowrap>Effective Date:&nbsp;
              <input type="text" name="effectiveMonth" size="2" maxlength="2" value="<%= effectiveMonth %>">
              /
              <input type="text" name="effectiveDay" size="2" maxlength="2" value="<%= effectiveDay %>">
              /
              <input type="text" name="effectiveYear" size="4" maxlength="4" value="<%= effectiveYear %>">
            </td>
            <td align="left" nowrap>Amount:&nbsp;
              <input type="text" name="trxAmount" size="15" maxlength="15" value="<%= trxAmount %>" CURRENCY>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td height="10%">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center" nowrap colspan="2">
              <a href ="javascript:showDivisionLevelFeesDialog()">Division Level Fees</a>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td height="65%" valign="bottom">
        <table height="100%" width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
              <table class="summary" height="100%" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr class="heading">
                  <th width="16%" align="left">Trx Type</th>
                  <th width="17%" align="left">Eff Date</th>
                  <th width="16%" align="left">Status</th>
                  <th width="17%" align="left">Contract Number</th>
                  <th width="17%" align="left">Amt Due</th>
                  <th width="17%" align="left">Amt Paid</th>
                </tr>
                <tr>
                  <td colspan="6" height="100%" width="100%">
                    <span class="scrollableContent" style="position:relative; width:100%; height:100%; top:0; left:0; background-color:#BBBBBB">
                      <table class="summary" id="hedgeFundRedemptionSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
                        <%
                          String etTRClass = "default";
                          boolean etSelected = false;
                          String trxType = "";
                          String effDate = "";
                          String status = "";
                          String contractNumber = "";
                          String amountDue= "";
                          String amountPaid = "";
                          String editTrxPK = "";
                          long originatingTrxFK = 0;
                          EDITBigDecimal amtDueEBD = new EDITBigDecimal();
                          if (editTrxVOs != null)
                          {
                              for (int i = 0; i < editTrxVOs.length; i++)
                              {
                                  SegmentVO segmentVO = (SegmentVO) editTrxVOs[i].getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class).getParentVO(SegmentVO.class);
                                  editTrxPK = editTrxVOs[i].getEDITTrxPK() + "";
                                  originatingTrxFK = editTrxVOs[i].getOriginatingTrxFK();
                                  EDITTrxVO origEditTrxVO = eventComponent.composeEDITTrxVOByEDITTrxPK(originatingTrxFK, new ArrayList());
                                  if (origEditTrxVO != null)
                                  {
                                      trxType = Util.initString(origEditTrxVO.getTransactionTypeCT(), "");

                                      effDate = Util.initString(origEditTrxVO.getEffectiveDate(), "");
                                  }
                                  status = editTrxVOs[i].getPendingStatus();
                                  contractNumber = segmentVO.getContractNumber();
                                  amtDueEBD = Util.roundToNearestCent(editTrxVOs[i].getNotificationAmount()).subtractEditBigDecimal(editTrxVOs[i].getNotificationAmountReceived());
                                  if (amtDueEBD.isGT(new EDITBigDecimal()))
                                  {
                                      amountDue = Util.roundToNearestCent(editTrxVOs[i].getNotificationAmount()).subtractEditBigDecimal(editTrxVOs[i].getNotificationAmountReceived()) + "";
                                      amountPaid = Util.roundToNearestCent(editTrxVOs[i].getNotificationAmountReceived()) + "";

                                      if (editTrxPK.equals(selectedEditTrxPK))
                                      {
                                          etTRClass = "highlighted";
                                          etSelected = true;
                                      }
                                      else
                                      {
                                          etTRClass = "default";
                                          etSelected = false;
                                      }
                        %>
                        <tr class="<%= etTRClass %>" isSelected="<%= etSelected %>" editTrxPK="<%= editTrxPK %>"
                            originatingTrxFK="<%= originatingTrxFK + "" %>" onMouseOver="highlightRow()"
                            onMouseOut="unhighlightRow()" onClick="selectEditTrx()">
                          <td width="16%" align="left">
                            <%= trxType %>
                          </td>
                          <td width="17%" align="left">
                            <%= effDate %>
                          </td>
                          <td width="16%" align="left">
                            <%= status %>
                          </td>
                          <td width="17%" align="left">
                            <%= contractNumber %>
                          </td>
                          <td width="17%" align="left">
                            <script>document.write(formatAsCurrency(<%= amountDue %>))</script>
                          </td>
                          <td width="17%" align="left">
                            <script>document.write(formatAsCurrency(<%= amountPaid %>))</script>
                          </td>
                        </tr>
                        <%
                                  }
                              }
                          }
                        %>
                      </table>
                    </span>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap colspan="2">
        <input type="button" name="enter" value="Save" onClick="generateTransaction()">
      </td>
    </tr>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">
    <input type="hidden" name="batchId" value="<%= batchId %>">
    <input type="hidden" name="batchAmount" value="<%= batchAmount %>">
    <input type="hidden" name="selectedSuspensePK" value="<%= selectedSuspensePK %>">
    <input type="hidden" name="selectedEditTrxPK" value="<%= selectedEditTrxPK %>">
    <input type="hidden" name="selectedFilteredFundFK" value="<%= selectedFilteredFundFK %>">
    <input type="hidden" name="contractAmount" value="<%= trxAmount %>">
    <input type="hidden" name="selectedOrigTrxFK" value="<%= selectedOrigTrxFK %>">

</body>
</form>
</html>
