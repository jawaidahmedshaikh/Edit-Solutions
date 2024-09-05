<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.exceptions.*,
                 edit.common.exceptions.VOEditException,
                 fission.utility.*,
                 edit.portal.common.session.UserSession" %>


 <jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="contractRiders"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="statusBean"
    class="fission.beans.PageBean" scope="request"/>


<%
    String riderMessage = (String) request.getAttribute("riderMessage");
    if (riderMessage == null)
    {
        riderMessage = "";
    }
    String errorRiderType = (String) request.getAttribute("errorRiderType");
    if (errorRiderType == null)
    {
        errorRiderType = "";
    }
    String errorCoverage = (String) request.getAttribute("errorCoverage");
    if (errorCoverage == null)
    {
        errorCoverage = "";
    }

    //  NOTE:  contractMainSessionBean contains the formBean for the contract (the selected base which is displayed in
    //  the summary), contractRidersSessionBean contains the formBean for the selected rider, and contractRiders contains
    //  the page beans for all the riders (displayed in the summary).
    //
    //  This page was created copying quoteCommitRider and adding the base (contract) to the summary listing

    PageBean mainFormBean =  contractMainSessionBean.getPageBean("formBean");
    String companyStructureId = mainFormBean.getValue("companyStructureId");

    //  Pull downs
//    CodeTableVO[] optionCodes  = CodeTableWrapper.getSingleton().getCodeTableEntries("OPTIONCODE", Long.parseLong(companyStructureId));
    CodeTableVO[] segmentNames = CodeTableWrapper.getSingleton().getCodeTableEntries("SEGMENTNAME", Long.parseLong(companyStructureId));
    String optionCodePK = mainFormBean.getValue("selectedCoveragePK");
    String riderOption = Util.initString(mainFormBean.getValue("riderOption"), "");

    String segmentNamePK  = "";
    String effectiveDate = "";
    String terminateDate  = "";
    String units          = "";
    String commissionPhaseID = "";
    String commissionPhaseOverride = "";

    //  Get all the riders for this segment
    Map allRiders = contractRiders.getPageBeans();

    //  Get the selected rider
    PageBean formBean = contractMainSessionBean.getPageBean("riderFormBean");
    segmentNamePK        = formBean.getValue("segmentNamePK");
    optionCodePK         = formBean.getValue("optionCodePK");
    effectiveDate        = formBean.getValue("effectiveDate");
    terminateDate         = formBean.getValue("terminationDate");
    units                = formBean.getValue("units");
    commissionPhaseID    = formBean.getValue("commissionPhaseID");
    commissionPhaseOverride = formBean.getValue("commissionPhaseOverride");
    String riderNumber = formBean.getValue("riderNumber");

    //  Default initialization
    segmentNamePK        = Util.initString(segmentNamePK, errorRiderType);
    optionCodePK         = Util.initString(optionCodePK, errorCoverage);
    units                = Util.initString(units, "0");
    commissionPhaseID    = Util.initString(commissionPhaseID, "");
    commissionPhaseOverride = Util.initString(commissionPhaseOverride, "");

    //  Get contract
    PageBean contractFormBean = contractMainSessionBean.getPageBean("formBean");

    String contractOptionCode     = contractFormBean.getValue("optionId");
	String contractEffectiveDate = contractFormBean.getValue("effectiveDate");
    String amount                 = contractFormBean.getValue("purchaseAmount");
    String terminationDate       = contractFormBean.getValue("terminateDate");

    String contractOptionCodePK = CodeTableWrapper.getSingleton().getCodeTablePKByCodeTableNameAndCode("OPTIONCODE", contractOptionCode) + "";

    contractOptionCodePK   = Util.initString(contractOptionCodePK, "");
    contractOptionCode     = Util.initString(contractOptionCode, "");
    amount                 = Util.initString(amount, "");

     UserSession userSession = (UserSession) session.getAttribute("userSession");

%>

<html>
<head>
<title>Contract Rider</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<script language="Javascript1.2">

	var dialog = null;

	var f = null;

    var shouldShowLockAlert = true;
    var riderMessage = "<%= riderMessage %>";

	function init()
    {
		f = document.contractRidersForm;

		top.frames["main"].setActiveTab("riderTab");

        if (riderMessage != "")
        {
            alert(riderMessage);
        }

         shouldShowLockAlert = <%= ! userSession.getSegmentIsLocked() %>;

        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if ( (elementType == "text" || elementType == "button") && (shouldShowLockAlert == true) )
            {
                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
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

	function saveRider()
    {
        if (f.selectedPullDownSegmentNamePK.value == "")
        {
            alert("Rider Type and Coverage Required.");
        }
        else
        {
            sendTransactionAction("ContractDetailTran", "saveRider", "contentIFrame");
        }
	}

	function addRider()
    {
		clearForm();

        var width = .30 * screen.width;
        var height = .15 * screen.height;

		openDialog("contractRiderCoverageSelectionDialog","top=0,left=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showRiderCoverageSelectionDialog", "contractRiderCoverageSelectionDialog");
	}

	function clearForm()
    {
	    f.selectedPullDownSegmentNamePK.selectedIndex = 0;
	    f.riderOption = "";
        f.segmentNamePK.value     = "";
        f.optionCodePK.value      = "";
		f.effectiveDate.value    = "";
		f.terminationDate.value     = "";
	}

	function showDetail()
    {
        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }

        f.optionCodePK.value = currentRow.optionCodePK;

		sendTransactionAction("ContractDetailTran", "showRiderDetailSummary", "contentIFrame");
	}


    function cancelRiderForm()
    {
		clearForm();
	}

	function deleteSelectedRider()
    {
		sendTransactionAction("ContractDetailTran", "deleteSelectedRider", "contentIFrame");
	}

    function selectDeselectRow()
    {
        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        var containingTable = currentRow.parentElement;

        for (var i = 0; i < containingTable.rows.length; i++)
        {
            containingTable.rows[i].style.backgroundColor = "#BBBBBB";
            containingTable.rows[i].isSelected = "false";
        }

        // Deactivate the selection in the "other" table
        var otherTable = null;
        if (containingTable.parentElement.id == "scriptsTable")
        {
            otherTable = document.all.tablesTable;
        }
        else
        {
            otherTable = document.all.scriptsTable;
        }

        for (var i = 0; i < otherTable.rows.length; i++)
        {
            otherTable.rows[i].style.backgroundColor = "#BBBBBB";
            otherTable.rows[i].isSelected = "false";
        }

        currentRow.style.backgroundColor = "#FFFFCC";
        currentRow.isSelected = "true";
    }

    function prepareToSendTransactionAction(transaction, action, target)
    {
        sendTransactionAction(transaction, action, target);
    }

</script>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;" >
<form name= "contractRidersForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="contractInfoHeader.jsp" flush="true"/>

  <table width="100%" height="25%" border="0" cellspacing="0" cellpadding="0">
    <tr>
	  <td nowrap>Rider Type:
	    <select name="selectedPullDownSegmentNamePK">
	      <option> Please Select. </option>
	        <%
              for(int i = 0; i < segmentNames.length; i++) {

                  String codeTablePK = segmentNames[i].getCodeTablePK() + "";
                  String codeDesc    = segmentNames[i].getCodeDesc();

                  if (segmentNamePK.equalsIgnoreCase(codeTablePK)) {

                      out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                  }
                  else  {

                      out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                  }
              }
	        %>
	    </select>
	  </td>
	   <td nowrap>Coverage:
        <input type="text" name="riderOption" value="<%= riderOption %>" size="50" maxlength="50">
       </td>
      <td nowrap>Units:&nbsp;
        <input type="text" name="units" tabindex="6" value="<%= units %>" maxlength="20">
      </td>
	</tr>
    <tr>
      <td nowrap>Commission Phase ID:&nbsp;
        <input type="text" name="commissionPhaseID" value="<%= commissionPhaseID %>" maxlength="5" disabled>
      </td>
      <td nowrap colspan="2">Commission Phase Override:&nbsp;
        <input type="text" name="commissionPhaseOverride" tabindex="9" value="<%= commissionPhaseOverride %>" maxlength="20">
      </td>
    </tr>
    <tr>
      <td nowrap>Effective Date:
           <input type="text" name="effectiveDate" value="<%= effectiveDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.effectiveDate', f.effectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>

      <td nowrap colspan="2">Maturity Date:
           <input type="text" name="terminationDate" value="<%= terminateDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.terminationDate', f.terminationDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>

    </tr>
  </table>
  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
	<tr height="5%">
	  <td align="left">
		<input type="button" name="add" value="   Add   " onClick="addRider()">
		<input type="button" name="save" value=" Save " onClick="saveRider()">
		<input type="button" name="cancel" value="Cancel" onClick="cancelRiderForm()">
	  </td>
	</tr>
  </table>
  <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th width="25%" align="left">Coverage</th>
      <th width="25%" align="left">Effective Date</th>
      <th width="25%" align="left">Amount</th>
	  <th width="25%" align="left">End Date</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:50%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="contractRiderSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
      <%
          //  Display contract
          String className = "default";
          boolean selected  = false;
          String contractEndDate = terminationDate;
      %>
        <tr class="<%= className %>" isSelected="<%= selected %>" id="<%= contractOptionCodePK %>"
            optionCodePK="<%= contractOptionCodePK %>" onClick="showDetail()"
            onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
          <td width="25%" nowrap>
            <%= contractOptionCode %>
          </td>
          <td width="25%" nowrap>
            <%= contractEffectiveDate %>
          </td>
          <td width="25%" nowrap>
            <script>document.write(formatAsCurrency(<%= amount %>))</script>
          </td>
          <td width="25%" nowrap>
            <%= contractEndDate %>
          </td>
        </tr>
      <%
          //  Display riders
          String currentOptionCodePK = "";
          String currentSegmentNamePK = "";
          String currentEffectiveDate = "";
          String currentTerminateDate = "";
          String currentOptionCode = "";

          Iterator pageBeans = allRiders.values().iterator();
          while (pageBeans.hasNext())
          {
            PageBean rider = (PageBean) pageBeans.next();

            currentOptionCodePK  = rider.getValue("optionCodePK");
            currentSegmentNamePK = rider.getValue("segmentNamePK");
            currentEffectiveDate = rider.getValue("effectiveDate");
            currentTerminateDate  = rider.getValue("terminateDate");

            if (! currentOptionCodePK.equalsIgnoreCase(""))
            {
                currentOptionCode = CodeTableWrapper.getSingleton().getCodeTableEntry(Long.parseLong(currentOptionCodePK)).getCode();
            }

            if (currentOptionCodePK.equals(optionCodePK))
            {
                className = "highlighted";
                selected = true;
            }
            else
            {
                className = "default";
                selected = false;
            }
      %>
        <tr class="<%= className %>" isSelected="<%= selected %>" id="<%= currentOptionCodePK %>"
            optionCodePK="<%= currentOptionCodePK %>" segmentNamePK="<%= currentSegmentNamePK %>"
            onClick="showDetail()" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
          <td width="25%" nowrap>
            <%= currentOptionCode %>
          </td>
          <td width="25%" nowrap>
            <%= currentEffectiveDate %>
          </td>
          <td>
          </td>
          <td width="25%" nowrap>
            <%= currentTerminateDate %>
          </td>
        </tr>
      <%
          }// end while
	  %>
    </table>
  </span>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction"  value="">
 <input type="hidden" name="action"       value="">

 <input type="hidden" name="segmentNamePK" value="<%= segmentNamePK %>">
 <input type="hidden" name="optionCodePK" value="<%= optionCodePK %>">

 <input type="hidden" name="commissionPhaseID" value="<%= commissionPhaseID %>">
 <input type="hidden" name="riderNumber" value="<%= riderNumber %>">

 <!-- recordPRASEEvents is set by the toolbar -->
 <input type="hidden" name="recordPRASEEvents" value="false">
</form>
</body>
</html>
