<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.common.session.UserSession,
                 fission.utility.*,
                 group.*" %>

<jsp:useBean id="quoteRiders"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="statusBean"
    class="fission.beans.PageBean" scope="request"/>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

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

    //  NOTE:  quoteRidersSessionBean contains the formBean (the selected rider), quoteRiders contains the page beans for
    //  all the riders (the summary)

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    PageBean mainBean = quoteMainSessionBean.getPageBean("formBean");
    String companyStructureId = Util.initString(mainBean.getValue("companyStructureId"), "0");

    CaseProductUnderwriting[] caseProductUnderwritings = null;
    String pageMode = Util.initString(mainBean.getValue("pageMode"), "");
    String optionCodePK = mainBean.getValue("selectedCoveragePK");
    String riderOption = Util.initString(mainBean.getValue("riderOption"), "");

    String increaseOptionStatus = mainBean.getValue("increaseOptionStatus");
    if (increaseOptionStatus.equals("display"))
    {
        caseProductUnderwritings = (CaseProductUnderwriting[])request.getAttribute("caseProductUnderwriting");
    }
    //  Pull downs
    CodeTableVO[] segmentNames = codeTableWrapper.getCodeTableEntries("SEGMENTNAME", Long.parseLong(companyStructureId));

    String segmentNamePK  = "";
    String effectiveDate  = "";
    String maturityDate   = "";
    String units          = "";
    String commissionPhaseID = "";
    String commissionPhaseOverride = "";

    //  Get all the riders for this segment
    Map allRiders = quoteRiders.getPageBeans();

    //  Get the selected rider
    PageBean formBean = quoteMainSessionBean.getPageBean("riderFormBean");
    if (formBean.hasDisplayValues())
    {
        optionCodePK = formBean.getValue("optionCodePK");
        riderOption  = codeTableWrapper.getCodeTableEntry(Long.parseLong(optionCodePK)).getCodeDesc();
    }

    segmentNamePK    = formBean.getValue("segmentNamePK");
    effectiveDate    = formBean.getValue("effectiveDate");
    maturityDate  = formBean.getValue("terminationDate");
    units            = formBean.getValue("units");
    commissionPhaseID = formBean.getValue("commissionPhaseID");
    commissionPhaseOverride = formBean.getValue("commissionPhaseOverride");

    //  Default initialization
    segmentNamePK        = Util.initString(segmentNamePK, errorRiderType);
    optionCodePK         = Util.initString(optionCodePK, errorCoverage);
    units                = Util.initString(units, "0");
    commissionPhaseID    = Util.initString(commissionPhaseID, "");
    commissionPhaseOverride = Util.initString(commissionPhaseOverride, "");
    String riderNumber = formBean.getValue("riderNumber");

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<html>
<head>
<title>New Business Rider</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<script language="Javascript1.2">

	var dialog = null;

	var f = null;

    var shouldShowLockAlert = true;
    var editableContractStatus = true;
    
    var riderMessage = "<%= riderMessage %>";
    var  increaseOptionStatus = "<%=  increaseOptionStatus %>";
    var  pageMode = "<%=  pageMode %>";

    var colorMouseOver   = "#00BB00";
    var colorHighlighted = "#FFFFCC";
    var colorMainEntry   = "#BBBBBB";

    function highlightRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") {

            currentRow = currentRow.parentElement;
        }

        currentRow.style.backgroundColor = colorMouseOver;
    }

    function unhighlightRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") {

            currentRow = currentRow.parentElement;
        }

        var className = currentRow.className;

        if (className == "highLighted") {

            currentRow.style.backgroundColor = colorHighlighted;
        }

        else {

            currentRow.style.backgroundColor = colorMainEntry;
        }
    }

	function init() {

		f = document.quoteCommitRidersForm;

		top.frames["main"].setActiveTab("riderTab");

        if (riderMessage != "")
        {
            alert(riderMessage);
        }

        if (increaseOptionStatus == "")
        {
            document.getElementById("gioOption").disabled = true;
        }

        shouldShowLockAlert = <%= ! userSession.getSegmentIsLocked() %>;

        // check for terminated status to disallow contract updates
        editableContractStatus = <%= userSession.getEditableSegmentStatus()%>;
        
        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if ( (elementType == "text" || elementType == "button") && (shouldShowLockAlert == true || editableContractStatus == false) )
            {
                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }

       if (pageMode == "add")
        {
            setButtonStateForAddMode();
        }

        if (pageMode == "detail")
        {
            setButtonStateForDetailMode();
        }

        formatCurrency();
	}

    function showLockAlert(){

    	if (shouldShowLockAlert == true)
        {
            alert("The Contract Cannot Be Edited.");

            return false;
            
        } else if (editableContractStatus == false) {
        	
        	alert("This Contract Cannot Be Edited Due to Terminated Status.");

            return false;
        }
    }

    function checkForRequiredFields()
    {
		return true;
    }


	function saveRider()
    {
        if (f.selectedPullDownSegmentNamePK.value == "")
        {
            alert("Rider Type and Coverage Required.");
        }
        else
        {
            sendTransactionAction("QuoteDetailTran", "saveRider", "contentIFrame");
        }
	}

	function addRider()
    {
		clearForm();


        var width = .30 * screen.width;
        var height = .15 * screen.height;

		openDialog("quoteCommitRiderCoverageSelectionDialog","top=0,left=0,resizable=no", width, height);

        prepareToSendTransactionAction("QuoteDetailTran", "showRiderCoverageSelectionDialog", "quoteCommitRiderCoverageSelectionDialog");
	}

	function clearForm() {

	    f.selectedPullDownSegmentNamePK.selectedIndex = 0;
	    f.riderOption.value = "";
        f.segmentNamePK.value     = "";
        f.optionCodePK.value      = "";
		f.effectiveDate.value     = "";
		f.maturityDate.value      = "";
	}

    function prepareToSendTransactionAction(transaction, action, target)
    {
        sendTransactionAction(transaction, action, target)
    }

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value= transaction;
		f.action.value= action;

		f.target = target;

		f.submit();
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

		sendTransactionAction("QuoteDetailTran", "showRiderDetail", "contentIFrame");
	}


    function cancelRiderForm()
    {
		clearForm();
	}

	function deleteSelectedRider()
    {
		sendTransactionAction("QuoteDetailTran", "deleteSelectedRider", "contentIFrame");
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

    function setButtonStateForAddMode()
    {
        // Enabled Buttons
        document.getElementById("btnAdd").src = "/PORTAL/common/images/btnAdd.gif";
        document.getElementById("btnAdd").disabled = false;

        // Disabled Buttons
        document.getElementById("btnCancel").src = "/PORTAL/common/images/btnCancelDis.gif";
        document.getElementById("btnCancel").disabled = true;

        document.getElementById("btnSave").src = "/PORTAL/common/images/btnSaveDis.gif";
        document.getElementById("btnSave").disabled = true;

        document.getElementById("btnDelete").src = "/PORTAL/common/images/btnDeleteDis.gif";
        document.getElementById("btnDelete").disabled = true;
    }

    function setButtonStateForDetailMode()
    {
        // Enabled Buttons
        document.getElementById("btnDelete").src = "/PORTAL/common/images/btnDelete.gif";
        document.getElementById("btnDelete").disabled = false;

        document.getElementById("btnSave").src = "/PORTAL/common/images/btnSave.gif";
        document.getElementById("btnSave").disabled = false;

        document.getElementById("btnCancel").src = "/PORTAL/common/images/btnCancel.gif";
        document.getElementById("btnCancel").disabled = false;

        document.getElementById("btnAdd").src = "/PORTAL/common/images/btnAdd.gif";
        document.getElementById("btnAdd").disabled = false;
    }


</script>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;" >
<form name= "quoteCommitRidersForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="quoteInfoHeader.jsp" flush="true"/>

<span id="mainContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
      <table width="100%" height="25%" border="0" cellspacing="0" cellpadding="0">
    <tr>
	  <td nowrap>Rider Type:
	    <select name="selectedPullDownSegmentNamePK">
	      <option>Please Select</option>
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
           <input type="text" name="maturityDate" value="<%= maturityDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.maturityDate', f.maturityDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
    </tr>
  </table>
  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
	<tr height="5%">
	  <td align="left">
		<input type="button" id="btnAdd" name="add" value="   Add   " onClick="addRider()">
		<input type="button" id="btnSave" name="save" value=" Save " onClick="saveRider()">
		<input type="button" id="btnCancel" name="cancel" value="Cancel" onClick="cancelRiderForm()">
		<input type="button" id="btnDelete" name="Delete" value="Delete" onClick="deleteSelectedRider()">
	  </td>
	</tr>
  </table>
  <table class="summaryArea" id="summaryTable" width="100%" height="50%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <th width="25%" align="left">Coverage</th>
      <th width="25%" align="left">Effective Date</th>
	  <th width="25%" align="left">Maturity Date</th>
    </tr>
    <tr width="100%" height="99%">
      <td colspan="4">
        <span class="scrollableContent">
          <table class="scrollableArea" id="riderSummary" width="100%" border="0" cellspacing="0" cellpadding="0">
            <%
              String className = "mainEntry";
              String selected = "false";
              String currentOptionCodePK = "";
              String currentSegmentNamePK = "";
              String currentEffectiveDate = "";
              String currentMaturityDate = "";
              String currentOptionCode = "";

              Iterator pageBeans = allRiders.values().iterator();
              while (pageBeans.hasNext())
              {
                  PageBean rider = (PageBean) pageBeans.next();

                  currentOptionCodePK  = rider.getValue("optionCodePK");
                  currentSegmentNamePK = rider.getValue("segmentNamePK");
                  currentEffectiveDate = rider.getValue("effectiveDate");
                  currentMaturityDate  = rider.getValue("maturityDate");

                  if (! currentOptionCodePK.equalsIgnoreCase(""))
                  {
                      currentOptionCode = codeTableWrapper.getCodeTableEntry(Long.parseLong(currentOptionCodePK)).getCode();
                  }

                  if (currentOptionCodePK.equals(optionCodePK))
                  {
                      className = "highLighted";
                      selected = "true";
                  }
                  else
                  {
                      className = "mainEntry";
                      selected="false";
                  }
            %>
            <tr class="<%= className %>" id="<%= currentOptionCodePK %>" optionCodePK="<%= currentOptionCodePK %>"
                segmentNamePK="<%= currentSegmentNamePK %>" isSelected="<%= selected %>"
                onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="showDetail()">
              <td width="25%" nowrap>
                <%= currentOptionCode %>
              </td>
              <td width="25%" nowrap>
                <%= currentEffectiveDate %>
              </td>
              <td width="25%" nowrap>
                <%= currentMaturityDate %>
              </td>
            </tr>
            <%
              }// end while
	        %>
          </table>
        </span>
      </td>
    </tr>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction"  value="">
 <input type="hidden" name="action"       value="">

 <input type="hidden" name="segmentNamePK" value="<%= segmentNamePK %>">
 <input type="hidden" name="optionCodePK" value="<%= optionCodePK %>">

 <input type="hidden" name="commissionPhaseID" value="<%= commissionPhaseID %>">
 <input type="hidden" name="riderNumber" value="<%= riderNumber %>">

 <!-- recordPRASEEvents is set by the toolbar when saving the client -->
 <input type="hidden" name="recordPRASEEvents" value="false">

</form>
</body>
</html>
