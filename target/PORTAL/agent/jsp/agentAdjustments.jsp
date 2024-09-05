<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.exceptions.*,
                 edit.common.exceptions.VOEditException,
                 fission.utility.*,
                 edit.portal.common.session.UserSession,
                 agent.*,
                 role.ClientRole" %>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] adjustmentTypes = codeTableWrapper.getCodeTableEntries("ADJUSTMENTTYPE");
    CodeTableVO[] adjustmentStatuses = codeTableWrapper.getCodeTableEntries("ADJUSTMENTSTATUS");
    CodeTableVO[] taxableStatuses = codeTableWrapper.getCodeTableEntries("TAXABLESTATUS");
    CodeTableVO[] adjustmentModes = codeTableWrapper.getCodeTableEntries("ADJUSTMENTMODE");

    String adjustmentType = "";
    String adjustmentAmount = "";
    String adjustmentPercent = "";
    String adjustmentStatus = "";
    String adjStartDate = "";
    String adjStopDate = "";
    String taxableStatus = "";
    String adjustmentMode = "";
    String nextDueDate = "";
    String description = "";
    String agentPK = "0";
    String agentNumber = "";
    String checkAdjustmentPK = Util.initString((String)request.getAttribute("checkAdjustmentPK"), "");
  
    CheckAdjustmentVO[] checkAdjustmentVOs = null;
    ClientRoleFinancialVO clientRoleFinancialVO = null;
    boolean commAmtPositive = false;
    EDITBigDecimal commBalance = new EDITBigDecimal();
    
    List agentNumbers = new ArrayList();

    AgentVO agentVO = (AgentVO) session.getAttribute("agentVO");
    if (agentVO != null)
    {
        ClientRole selectedClientRole = null;
        
        PlacedAgent[] placedAgents = PlacedAgent.findBy_Agent(new Agent(agentVO));
        
        for (int i = 0; i < placedAgents.length; i++)
        {
            ClientRole clientRole = placedAgents[i].getClientRole();
            if (clientRole.getReferenceID() != null && !agentNumbers.contains(clientRole.getReferenceID()))
            {
                agentNumbers.add(clientRole.getReferenceID());
            }
        }
    
        agentPK = agentVO.getAgentPK() + "";
        checkAdjustmentVOs = agentVO.getCheckAdjustmentVO();
        ClientRoleVO[] clientRoleVOs = agentVO.getClientRoleVO();
        for (int i = 0; i < clientRoleVOs.length; i++)
        {
            if (clientRoleVOs[i].getClientRoleFinancialVOCount() > 0)
            {
                clientRoleFinancialVO = clientRoleVOs[i].getClientRoleFinancialVO(0);
                commBalance = commBalance.addEditBigDecimal(new EDITBigDecimal(clientRoleFinancialVO.getCommBalance()));
            }
        }

        if (commBalance.isGTE("0"))
        {
            commAmtPositive = true;
        }
    }

    if (checkAdjustmentVOs != null)
    {
        for (int i = 0; i < checkAdjustmentVOs.length; i++)
        {
            CheckAdjustmentVO checkAdjustmentVO = (CheckAdjustmentVO)checkAdjustmentVOs[i];
            String adjustmentPK = checkAdjustmentVO.getCheckAdjustmentPK() + "";

            if (checkAdjustmentPK.equals(adjustmentPK))
            {
                adjustmentType = checkAdjustmentVO.getAdjustmentTypeCT();
                adjustmentAmount = checkAdjustmentVO.getAdjustmentDollar().toString();
                adjustmentPercent = new EDITBigDecimal(checkAdjustmentVO.getAdjustmentPercent()).toString();
                adjustmentStatus = checkAdjustmentVO.getAdjustmentStatusCT();
                adjStartDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(checkAdjustmentVO.getStartDate());
                adjStopDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(checkAdjustmentVO.getStopDate());
                taxableStatus = Util.initString(checkAdjustmentVO.getTaxableStatusCT(), "");
                adjustmentMode = Util.initString(checkAdjustmentVO.getModeCT(), "");
                nextDueDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(checkAdjustmentVO.getNextDueDate());
                description = Util.initString(checkAdjustmentVO.getDescription(), "");

                Long placedAgentFK = new Long(checkAdjustmentVOs[i].getPlacedAgentFK());            
                PlacedAgent placedAgent = PlacedAgent.findByPK(placedAgentFK);                
                ClientRole clientRole = placedAgent.getClientRole();                
                agentNumber = clientRole.getReferenceID();
            }
        }
    }

    String stopDate = Util.initString((String)request.getAttribute("stopDate"), "");
    boolean activeDebitRepay = false;
    String activeDebitRepayString = Util.initString((String) request.getAttribute("activeInd"), "");
    if (activeDebitRepayString.equalsIgnoreCase("false") || activeDebitRepayString.equals(""))
    {
        activeDebitRepay = false;
    }
    else
    {
        activeDebitRepay = true;
    }

    String rowToMatchBase = checkAdjustmentPK + adjustmentType;

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>


<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<script language="Javascript1.2">

	var f = null;

    var shouldShowLockAlert = true;
    var commAmtPositive = false;
    var activeDebitBalanceRepay = false;
    var checkAdjustmentPK = "<%= checkAdjustmentPK %>";

	function init() {

		f = document.agentAdjustmentsForm;

		top.frames["main"].setActiveTab("adjustmentTab");

        var agentIsLocked = <%= userSession.getAgentIsLocked()%>;
        var username = "<%= userSession.getUsername() %>";
        var elementPK = "<%= userSession.getAgentPK() %>";
        commAmtPositive = <%= commAmtPositive %>;


		top.frames["header"].updateLockState(agentIsLocked, username, elementPK);

        shouldShowLockAlert = !agentIsLocked;

        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if ((elementType == "text" ||
                 elementType == "button" ||
                 elementType == "select-one") &&
                (shouldShowLockAlert == true))
            {
                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }

        formatCurrency();
	}

    function showLockAlert(){

        if (shouldShowLockAlert == true){

            alert("The Agent can not be edited.");

            return false;
        }
    }

	function selectRow() {

		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var key = trElement.id;

		f.checkAdjustmentPK.value = key;

		sendTransactionAction("AgentDetailTran", "showAdjustmentDetailSummary", "_self");
	}

    function addAdjustment() {

        sendTransactionAction("AgentDetailTran","clearFormForAddOrCancel","_self");
    }

    function cancelAdjustment() {

        sendTransactionAction("AgentDetailTran","clearFormForAddOrCancel","_self");
    }

    function deleteAdjustment()
    {
        if (f.adjustmentType.value == "DebitBalAutoRepay")
        {
            alert("Cannot Delete Records of Type DebitBalanceAutoRepay");
            return;
        }

        sendTransactionAction("AgentDetailTran","deleteAdjustment","_self");
    }

    function saveAdjustment()
    {
        if (selectElementIsEmpty(f.agentNumber))
        {
            alert("Please Select Agent Number");
            return;
        }

        if (selectElementIsEmpty(f.adjustmentType))
        {
            alert("Please Select Adjustment Type");
            return;
        }

        if (selectElementIsEmpty(f.adjustmentStatus))
        {
            alert("Please Select Status");
            return;
        }

        //edits for the type of "DebitBalAutoRepay"
        if (f.adjustmentType.value == "DebitBalAutoRepay")
        {
            if (textElementIsEmpty(f.adjustmentPercent))
            {
                alert("Please Enter Percent for the Type Selected");
                return;
            }

            if (f.adjustmentMode.value != "NotApplicable")
            {
                alert("Mode must be Not Applicable");
                return;
            }

            if (f.adjustmentStatus.value != "AutoCheck")
            {
                alert("Status must be Auto Check");
                return;
            }

             var activeDebitBalanceRepay = <%= activeDebitRepay %>;

            if (commAmtPositive == true  && activeDebitBalanceRepay == false)
            {
                alert("Commission Balance Not Negative");
                return;
            }

            if (textElementIsEmpty(f.adjStartDate))
            {
                alert("Please Enter Start Date");
                return;
            }


            //if active Debit Balance and Not Complete, generate error if new DebitType being saved

            if (checkAdjustmentPK == "")
            {
                if (activeDebitBalanceRepay == true)
                {
                    alert("Multiple Debit Balance Auto Repay records not Allowed");
                    return;
                }
            }

        }
        else
        {
            if (selectElementIsEmpty(f.taxableStatus))
            {
                alert("Please Select Taxable Status");
                return;
            }

            //the nextduedate is required for status of one-time
            if (f.adjustmentStatus.value == "OneTime")
            {
                if (textElementIsEmpty(f.nextDueDate))
                {
                    alert("Please Enter Next Due Date");
                    return;
                }
            }

            //the startdate and mode are  required for status of scheduled
            if (f.adjustmentStatus.value == "Scheduled")
            {
                if (textElementIsEmpty(f.adjStartDate))
                {
                    alert("Please Enter Start Date");
                    return;
                }
                if (selectElementIsEmpty(f.adjustmentMode))
                {
                    alert("Please Enter Mode For Scheduled Status")
                    return;
                }
            }
        }

        sendTransactionAction("AgentDetailTran","saveAdjustmentToSummary","_self");
    }

    function showAdjustAccumsDialog() {

        var width = .30 * screen.width;
        var height = .40 * screen.height;

		openDialog("accumulations","left=0,top=0,resizable=no", width, height);

        sendTransactionAction("AgentDetailTran", "showAdjustAccumsDialog", "accumulations");
    }
</script>

<head>
<title>Agent Adjustments</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init();" style="border-style:solid; border-width:1;">
<form name= "agentAdjustmentsForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="agentInfoHeader.jsp" flush="true"/>

  <table width="100%" height="60%">
    <tr>
      <td align="right" nowrap><span class="requiredField">*</span>&nbsp;Agent Number:</td>
      <td align="left" nowrap colspan="5">
        <select name="agentNumber">
          <option value="null">Please Select</option>
            <%
                if (agentNumbers.size() > 0)
                {
                  for(int i = 0; i < agentNumbers.size(); i++)
                  {
                      if (agentNumbers.get(i).equals(agentNumber))
                      {
                         out.println("<option selected name=\"id\" value=\"" + agentNumbers.get(i) + "\">" + agentNumbers.get(i) + "</option>");
                      }
                      else  {
                         out.println("<option name=\"id\" value=\"" + agentNumbers.get(i) + "\">" + agentNumbers.get(i) + "</option>");
                      }
                  }
                }
            %>
        </select>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap><span class="requiredField">*</span>Adjustment Type:</td>
      <td align="left" nowrap>
        <select name="adjustmentType">
          <option value="null">Please Select</option>
          <%
                for(int i = 0; i < adjustmentTypes.length; i++) 
                {
                  String codeTablePK = adjustmentTypes[i].getCodeTablePK() + "";
                  String codeDesc    = adjustmentTypes[i].getCodeDesc();
                  String code        = adjustmentTypes[i].getCode();

                    if (adjustmentType.equalsIgnoreCase(code)) 
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
      <td align="right" nowrap>Amount:</td>
      <td align="left" nowrap>
        <input type="text" name="adjustmentAmount" maxlength="24" size="18" value="<%= adjustmentAmount %>" CURRENCY>
      </td>
      <td align="right" nowrap>Percent:</td>
      <td align="left" nowrap>
        <input type="text" name="adjustmentPercent" maxlength="30" size="18" value="<%= adjustmentPercent %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap><span class="requiredField">*</span>Status:</td>
      <td align="left" nowrap>
        <select name="adjustmentStatus">
          <option value="null">Please Select</option>
          <%
              for(int i = 0; i < adjustmentStatuses.length; i++) 
              {
                  String codeTablePK = adjustmentStatuses[i].getCodeTablePK() + "";
                  String codeDesc    = adjustmentStatuses[i].getCodeDesc();
                  String code        = adjustmentStatuses[i].getCode();

                  if (adjustmentStatus.equalsIgnoreCase(code)) 
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
      <td align="right" nowrap>Start Date:</td>
      <td align="left" nowrap>
        <input type="text" name="adjStartDate" size="10" maxlength="10" value="<%= adjStartDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
        <a href="javascript:show_calendar('f.adjStartDate', f.adjStartDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
      <td align="right" nowrap>Stop Date:</td>
      <td align="left" nowrap>
        <input type="text" name="adjStopDate" size="10" maxlength="10" value="<%= adjStopDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
        <a href="javascript:show_calendar('f.adjStopDate', f.adjStopDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Taxable:</td>
      <td align="left" nowrap>
        <select name="taxableStatus">
          <option value="null">Please Select</option>
          <%
              for(int i = 0; i < taxableStatuses.length; i++) 
              {
                  String codeTablePK = taxableStatuses[i].getCodeTablePK() + "";
                  String codeDesc    = taxableStatuses[i].getCodeDesc();
                  String code        = taxableStatuses[i].getCode();

                  if (taxableStatus.equalsIgnoreCase(code)) 
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
      <td align="right" nowrap>Mode:</td>
      <td align="left" nowrap>
        <select name="adjustmentMode">
          <option value="null">Please Select</option>
          <%
              for(int i = 0; i < adjustmentModes.length; i++) 
              {
                  String codeTablePK = adjustmentModes[i].getCodeTablePK() + "";
                  String codeDesc    = adjustmentModes[i].getCodeDesc();
                  String code        = adjustmentModes[i].getCode();

                  if (adjustmentMode.equalsIgnoreCase(code)) 
                  {
                     out.println("<option selected name=\"id\" value=\"" + code+ "\">" + codeDesc + "</option>");
                 }
                  else  
                  {
                     out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                 }
              }
          %>
        </select>
      </td>
      <td align="right" nowrap>Next Due Date:</td>
      <td align="left" nowrap>
        <input type="text" name="nextDueDate" size="10" maxlength="10" value="<%= nextDueDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
        <a href="javascript:show_calendar('f.nextDueDate', f.nextDueDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Description:</td>
      <td align="left" nowrap colspan="5">
        <input type="text" name="description" maxlength="250" size="75" value="<%= description %>">
      </td>
    </tr>
  </table>
  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td align="left">
		<input type="button" name="add" value="   Add   " onClick="addAdjustment()">
		<input type="button" name="save" value=" Save " onClick="saveAdjustment()">
		<input type="button" name="cancel" value="Cancel" onClick="cancelAdjustment()">
		<input type="button" name="delete" value="Delete" onClick="deleteAdjustment()">
	  </td>
	</tr>
  </table>
  <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th width="20%" align="left">Type</th>
      <th width="20%" align="left">Amount</th>
	  <th width="20%" align="left">Start Date</th>
      <th width="20%" align="left">Stop Date</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:30%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="adjustmentsSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
      <%
          String rowToMatch = "";
          String trClass = "";
          String selected = "";

          if (checkAdjustmentVOs != null)
          {
              for (int i = 0; i < checkAdjustmentVOs.length; i++)
              {
                  CheckAdjustmentVO checkAdjustmentVO = (CheckAdjustmentVO)checkAdjustmentVOs[i];

                  if (checkAdjustmentVO.getAdjustmentCompleteInd() == null && !checkAdjustmentVO.getVoShouldBeDeleted())
                  {
                  String sAdjustmentPK = checkAdjustmentVO.getCheckAdjustmentPK() + "";
                  String sAdjustmentType = checkAdjustmentVO.getAdjustmentTypeCT();
                  String sAdjustmentAmount = checkAdjustmentVO.getAdjustmentDollar().toString();
                  String[] asTD = new java.lang.String[0];
                  String sStartDate = "";
                  if (checkAdjustmentVO.getStartDate() != null)
                  {
                      sStartDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(checkAdjustmentVO.getStartDate());
                  }
                  String sStopDate = "";
                  if (checkAdjustmentVO.getStopDate() != null)
                  {
                      sStopDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(checkAdjustmentVO.getStopDate());
                  }

                  rowToMatch =  sAdjustmentPK + sAdjustmentType;

                  if (rowToMatch.equals(rowToMatchBase))
                  {
                      trClass = "highlighted";
                      selected = "true";
                  }
                  else
                  {
                      trClass = "default";
                      selected = "false";
                  }
	  %>
        <tr class="<%= trClass %>" isSelected="<%= selected %>"
            id="<%= sAdjustmentPK %>" onClick="selectRow()" onMouseOver="highlightRow()"
            onMouseOut="unhighlightRow()">
          <td width="20%">
            <%= sAdjustmentType %>
          </td>
          <td width="20%">
            <script>document.write(formatAsCurrency(<%= sAdjustmentAmount %>))</script>
          </td>

          <td width="20%">
            <%= sStartDate %>
          </td>
          <td width="20%">
            <%= sStopDate %>
          </td>
        </tr>
      <%
                  }// end if ind check
                }// end for
            } // end if
      %>
    </table>
  </span>


<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction"  value="">
 <input type="hidden" name="action"       value="">

 <input type="hidden" name="agentPK" value="<%= agentPK %>">
 <input type="hidden" name="checkAdjustmentPK" value="<%= checkAdjustmentPK %>">
 <input type="hidden" name="activeInd" value="<%= activeDebitRepay %>">

</body>
</html>
