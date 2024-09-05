<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 edit.portal.exceptions.*,
                 edit.common.exceptions.VOEditException,
                 edit.portal.common.session.UserSession,
                 fission.utility.*" %>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>
<%
    SuspenseVO[] contractSuspenseVOs = (SuspenseVO[]) session.getAttribute("contractSuspenseVOs");
    String selectedContractSuspensePK = (String) request.getAttribute("selectedContractSuspensePK");

    String userDefNumber = "";
    String effectiveDate = "";
    String processDate = "";
    String direction = "";
    String amount = "";
    String memoCode = "";
    String premiumType = "";
    String operator = "";
    String maintDateTime = "";
    String originalContractNumber = "";
    String originalAmount = "";
    String originalMemoCode = "";
    String originalInformationInd = "unchecked";
    String pendingAmount = "";
    String maintenanceInd = "";
    String status = "";
    if (contractSuspenseVOs != null)
    {
        for (int s = 0; s < contractSuspenseVOs.length; s++)
        {
            if ((contractSuspenseVOs[s].getSuspensePK() + "").equals(selectedContractSuspensePK))
            {
                originalInformationInd = "checked";
                userDefNumber = contractSuspenseVOs[s].getUserDefNumber();
                effectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(contractSuspenseVOs[s].getEffectiveDate());
                processDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(contractSuspenseVOs[s].getProcessDate());
                direction = contractSuspenseVOs[s].getDirectionCT();
                amount = contractSuspenseVOs[s].getSuspenseAmount().toString();
                memoCode = contractSuspenseVOs[s].getMemoCode();
                if (memoCode == null)
                {
                    memoCode = "";
                }
                premiumType = contractSuspenseVOs[s].getPremiumTypeCT();
                if (premiumType == null)
                {
                    premiumType = "";
                }
                operator = contractSuspenseVOs[s].getOperator();
                maintDateTime = contractSuspenseVOs[s].getMaintDateTime();
                originalContractNumber = contractSuspenseVOs[s].getOriginalContractNumber();
                if (originalContractNumber == null)
                {
                    originalContractNumber = "";
                }
                originalAmount = contractSuspenseVOs[s].getOriginalAmount().toString();
                originalMemoCode = contractSuspenseVOs[s].getOriginalMemoCode();
                if (originalMemoCode == null)
                {
                    originalMemoCode = "";
                }
                pendingAmount = contractSuspenseVOs[s].getPendingSuspenseAmount().toString();
                maintenanceInd = contractSuspenseVOs[s].getMaintenanceInd();
                status = contractSuspenseVOs[s].getStatus();
                if (status == null)
                {
                    status = "";
                }
            }
        }
    }

    String rowToMatchBase = selectedContractSuspensePK;

   UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<%!
	private TreeMap sortSuspenseByEffectiveDate(SuspenseVO[] suspenseVOs) {

		TreeMap sortedSuspenseEntries = new TreeMap();

		for (int s = 0; s < suspenseVOs.length; s++)
        {
			String userDefNumber = suspenseVOs[s].getUserDefNumber();
            String suspensePK = suspenseVOs[s].getSuspensePK() + "";

			sortedSuspenseEntries.put(userDefNumber + suspensePK + suspenseVOs[s].getEffectiveDate(), suspenseVOs[s]);
		}

		return sortedSuspenseEntries;
	}
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

    var shouldShowLockAlert = true;

	function init()
    {
		f = document.suspenseForm;

        var contractIsLocked = <%= userSession.getSegmentIsLocked()%>;
        var username = "<%= userSession.getUsername() %>";
        var elementPK = "<%= userSession.getSegmentPK() %>";

        shouldShowLockAlert = !contractIsLocked;

        for (var i = 0; i < f.elements.length; i++) {

            var elementType = f.elements[i].type;

            if (f.elements[i].name != "close") {

                if ((elementType == "text" ||
                    elementType == "button") &&
                    (shouldShowLockAlert == true)) {

                    f.elements[i].onclick = showLockAlert;
                    f.elements[i].onkeydown = showLockAlert;
                }
            }
        }

        formatCurrency();
	}

    function showLockAlert()
    {
        if (shouldShowLockAlert == true)
        {
            alert("The Contract can not be edited.");

            return false;
        }
    }

	function selectRow()
    {
		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var selectedContractSuspensePK = trElement.id;

		f.selectedContractSuspensePK.value = selectedContractSuspensePK;

		sendTransactionAction("QuoteDetailTran", "showSelectedNewBusinessSuspense", "_self");
	}

	function reverseSuspenseEntry()
    {
        sendTransactionAction("QuoteDetailTran", "reverseSuspenseEntry", "contentIFrame");
        window.close();
	}

	function originalInformation()
    {
        var height  = 0.20 * screen.height;
        var width   = 0.40 * screen.width;

		openDialog("originalInformation", "top=0,left=0,resizable=no", width, height);

        sendTransactionAction("QuoteDetailTran", "showOriginalSuspenseInfo", "originalInformation");
	}
</script>

<head>
<title>EDITSOLUTIONS - New Business Suspense</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()">
<form name="suspenseForm" method="post" action="">
  <table width="100%" height="50%" border="0" cellspacing="5" cellpadding="0">
    <tr>
      <td align="right" nowrap>User Def Number:&nbsp;</td>
      <td align="left" nowrap colspan="3">
        <input disabled type="text" name="userDefNumber" size="15" maxlength="15" value="<%= userDefNumber %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Effective Date:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="effectiveDate" size="10" maxlength="10" value="<%= effectiveDate %>">
      </td>
      <td align="right" nowrap>Process Date:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="processDate" size="10" maxlength="10" value="<%= processDate %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Direction:&nbsp;</td>
      <td align="left" nowrap colspan="3">
        <input disabled type="text" name="direction" size="20" maxlength="20" value="<%= direction %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Amount:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="amount" size="15" maxlength="15" value="<%= amount %>" CURRENCY>
      </td>
      <td align="right" nowrap>Pending Amount:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="pendingAmount" size="15" maxlength="15" value="<%= pendingAmount %>" CURRENCY>
        &nbsp;&nbsp;&nbsp;&nbsp;
        Memo Code: &nbsp;
        <input disabled type="text" name="memoCode" size="7" maxlength="7" value="<%= memoCode %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Premium Type:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="premiumType" size="20" maxlength="20" value="<%= premiumType %>">
      </td>
      <td align="right" nowrap>Suspense Status:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="status" size="1" maxlength="1" value="<%= status %>">
      </td>
    </tr>
    <tr>
      <td nowrap colspan="8"><b>Indicators:</b></td>
    </tr>
    <tr>
      <td colspan="3">
        <input disabled type="checkbox" name="originalInformationInd" <%= originalInformationInd %> value="checkbox"><a href ="javascript:originalInformation()">Original Information</a>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Operator:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="operator" size="8" maxlength="8" value="<%= operator %>">
      </td>
      <td align="right" nowrap>Date/Time:&nbsp;</td>
      <td align="left" nowrap>
        <input disabled type="text" name="maintDateTime" size="35" maxlength="35" value="<%= maintDateTime %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Reverse:&nbsp;</td>
      <td align="left" nowrap colspan="3">
        <input type="text" name="reversalInd" size="1" maxlength="1" value="">
      </td>
    </tr>
  </table>
  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td nowrap align="left">
		<input type="button" value="Save" style="background-color:#DEDEDE" onClick="reverseSuspenseEntry()">
	  </td>
	</tr>
  </table>
  <table class="summary" id="summaryTable" width="100%" height="35%" border="0" cellspacing="0" cellpadding="0" height="10">
    <tr class="heading">
      <th align="left" width="16%">Acctg Pend</th>
      <th align="left" width="16%">User Number</th>
      <th align="left" width="16%">Eff Date</th>
 	  <th align="left" width="16%">Direction</th>
 	  <th align="left" width="16%">Amount</th>
      <th align="left" width="16%">Status</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:35%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="newBusSuspenseSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
        <%
          String rowToMatch = "";
          String trClass = "default";
          boolean selected = false;

          if (contractSuspenseVOs != null)
          {
              Map sortedSuspenseEntries = sortSuspenseByEffectiveDate(contractSuspenseVOs);

              Iterator it = sortedSuspenseEntries.values().iterator();

              while (it.hasNext())  {

                  SuspenseVO suspenseVO = (SuspenseVO) it.next();

                  String tUserNumber     = suspenseVO.getUserDefNumber();
                  if (tUserNumber == null)
                  {
                      tUserNumber = "";
                  }
                  String tEffectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(suspenseVO.getEffectiveDate());
                  String tDirectionValue = suspenseVO.getDirectionCT();
                  String tAmount		 = suspenseVO.getSuspenseAmount().toString();
                  String tAcctPendingInd = suspenseVO.getAccountingPendingInd();
                  String tSuspensePK     = suspenseVO.getSuspensePK() + "";
                  String tStatus         = suspenseVO.getStatus();
                  if (tStatus == null)
                  {
                      tStatus = "";
                  }

                  // Store behind the scenes...
                  String key = tSuspensePK;

                  rowToMatch = key;

                  if (rowToMatch.equals(rowToMatchBase))
                  {
                      trClass = "highlighted";
                      selected = true;
                  }
                  else
                  {
                      trClass = "default";
                      selected = false;
                  }
        %>
        <tr class="<%= trClass %>" isSelected="<%= selected %>" id="<%= key %>"
            onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow()">
          <td align="left" nowrap width="16%">
            <%= tAcctPendingInd %>
          </td>
          <td align="left" nowrap width="16%">
            <%= tUserNumber %>
          </td>
          <td align="left" nowrap width="16%">
            <%= tEffectiveDate %>
          </td>
          <td align="left" nowrap width="16%">
            <%= tDirectionValue %>
          </td>
          <td align="left" nowrap width="16%">
            <script>document.write(formatAsCurrency(<%= tAmount %>))</script>
          </td>
          <td align="left" nowrap width="16%">
            <%= tStatus %>
          </td>
        </tr>
        <%
              }// end while
          }// end if
        %>
    </table>
  </span>
  <table id="table3" width="100%" height="1%" border="0" cellspacing="6" cellpadding="0">
    <tr>
      <td align="right" nowrap colspan="4">
        <input type="button" name="close" value="Close" onClick ="closeWindow()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction"  value="">
 <input type="hidden" name="action"       value="">
 <input type="hidden" name="selectedContractSuspensePK" value="<%= selectedContractSuspensePK %>">
 <input type="hidden" name="originalContractNumber" value="<%= originalContractNumber %>">
 <input type="hidden" name="originalAmount" value="<%= originalAmount %>">
 <input type="hidden" name="originalMemoCode" value="<%= originalMemoCode %>">

</form>
</body>
</html>
