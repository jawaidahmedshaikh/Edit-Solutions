<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.common.session.UserSession,
                 fission.utility.*" %>

<%
    String rmdMessage = Util.initString(((String) request.getAttribute("rmdMessage")), "");
    String closeDialog = Util.initString(((String) request.getAttribute("closeDialog")), "NO");

    RequiredMinDistributionVO rmdVO = (RequiredMinDistributionVO) session.getAttribute("rmdVO");

    String seventyAndHalfDate = "";
    String rmdElection = "";
    String initialRMDAmount = "";
    String rmdAmount = "";
    String rmdAnnualDate = "";
    String rmdFirstPayDate = "";
    String rmdStartDate = "";
    String lastNotificationDate = "";
    String lastPaymentDate = "";
    String lastPaymentAmount = "";
    String nextPaymentDate = "";
    String lifeExpectancyMultiple = "";
    String rmdFrequency = "";
    String modalOverrideAmount = "";
    String calculatedRMDAmount = "";
    String initialCYAccumValue = "";

    if (rmdVO != null)
    {
        seventyAndHalfDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(rmdVO.getSeventyAndHalfDate());

        rmdElection = Util.initString(rmdVO.getElectionCT(), "");
        initialRMDAmount = Util.roundToNearestCent(rmdVO.getInitialRMDAmount()).doubleValue() + "";
        rmdAmount = Util.roundToNearestCent(rmdVO.getAmount()).doubleValue() + "";

        rmdAnnualDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(rmdVO.getAnnualDate());

        rmdFirstPayDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(rmdVO.getFirstPayDate());

        lastNotificationDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(rmdVO.getLastNotificationDate());

        lastPaymentDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(rmdVO.getLastPaymentDate());

        lastPaymentAmount = Util.roundToNearestCent(rmdVO.getLastPaymentAmount()).toString();

        nextPaymentDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(rmdVO.getNextPaymentDate());

        rmdStartDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(rmdVO.getRMDStartDate());

        lifeExpectancyMultiple = Util.initString(rmdVO.getLifeExpectancyMultipleCT(), "");
        rmdFrequency = Util.initString(rmdVO.getFrequencyCT(), "");
        modalOverrideAmount = Util.roundToNearestCent(rmdVO.getModalOverrideAmount()).toString();
        calculatedRMDAmount = Util.roundToNearestCent(rmdVO.getCalculatedRMDAmount()).toString();
        initialCYAccumValue = Util.roundToNearestCent(rmdVO.getInitialCYAccumValue()).toString();
    }

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    CodeTableVO[] rmdElections = codeTableWrapper.getCodeTableEntries("RMDELECTION");
    CodeTableVO[] lifeExpectancyMultiples = codeTableWrapper.getCodeTableEntries("LIFEEXPECTANCYMULT");
    CodeTableVO[] rmdFrequencies = codeTableWrapper.getCodeTableEntries("RMDFREQUENCY");

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<script language="Javascript1.2">

	var f = null;

    var shouldShowLockAlert = true;
    var rmdMessage = "<%= rmdMessage %>";
    var closeDialog = "<%= closeDialog %>";

	function init()
    {
		f = document.rmdForm;

        shouldShowLockAlert = <%= ! userSession.getSegmentIsLocked() %>;

        for (var i = 0; i < f.elements.length; i++) {

            var elementType = f.elements[i].type;

            if (f.elements[i].name != "bCancel") {

                if ((elementType == "text" ||
                    elementType == "button") &&
                    (shouldShowLockAlert == true)) {

                    f.elements[i].onclick = showLockAlert;
                    f.elements[i].onkeydown = showLockAlert;
                }
            }
        }

        formatCurrency();

        if (closeDialog == "NO" && rmdMessage != "")
        {
            alert(rmdMessage);
        }
        else if (closeDialog == "YES")
        {
            closeRmdDialog();
        }
	}

    function showLockAlert()
    {
        if (shouldShowLockAlert == true)
        {
            alert("The Contract can not be edited.");

            return false;
        }
    }

    function saveRmdDialog()
    {
        sendTransactionAction("ContractDetailTran", "saveRmdDialog", "_self");
    }

    function closeRmdDialog()
    {
        sendTransactionAction("ContractDetailTran", "closeRmdDialog", "contentIFrame");
        closeWindow();
    }

</script>

<head>
<title>RMD</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name="rmdForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="mainContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="72%" border="0" cellspacing="0" cellpadding="3" bgcolor="#DDDDDD">
    <tr valign="top">
      <td align="right" nowrap>RMD Frequency:&nbsp;</td>
      <td align="left" nowrap>
	    <select name="rmdFrequency">
          <option>Please Select</option>
         	<%
              for(int i = 0; i < rmdFrequencies.length; i++)
              {
                  String codeTablePK = rmdFrequencies[i].getCodeTablePK() + "";
                  String codeDesc    = rmdFrequencies[i].getCodeDesc();
                  String code        = rmdFrequencies[i].getCode();

                  if (rmdFrequency.equalsIgnoreCase(code))
                  {
                      out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                  }
              }
         	%>
        </select>
      </td>
      <td align="left" rowspan="9">
        <span style="border-style:solid; border-width:1; position:relative; width:100%; top:0; left:0; z-index:0; overflow:visible">
          <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="5">
            <tr>
              <td align="right" nowrap>70 1/2 Date:&nbsp;</td>
              <td align="left" nowrap>
                <input disabled type="text" name="seventyAndHalfDate" maxlength="10" size="10" value="<%= seventyAndHalfDate %>">
              </td>
            </tr>
            <tr>
              <td align="right" nowrap>RMD First Pay Date:&nbsp;</td>
              <td align="left" nowrap>
                <input disabled type="text" name="rmdFirstPayDate" maxlength="10" size="10" value="<%= rmdFirstPayDate %>">
              </td>
            </tr>
            <tr>
              <td align="right" nowrap>Calculated RMD Amount:&nbsp;</td>
              <td align="left" nowrap>
                <input disabled type="text" name="calculatedRMDAmount" maxlength="20" size="20" value="<%= calculatedRMDAmount %>" CURRENCY>
              </td>
            </tr>
            <tr>
              <td align="right" nowrap>Initial RMD Amount:&nbsp;</td>
              <td align="left" nowrap>
                <input disabled type="text" name="initialRMDAmount" maxlength="20" size="20" value="<%= initialRMDAmount %>" CURRENCY>
              </td>
            </tr>
            <tr>
              <td align="right" nowrap>RMD Notif Amount:&nbsp;</td>
              <td align="left" nowrap>
                <input disabled type="text" name="rmdAmount" maxlength="20" size="20" value="<%= rmdAmount %>" CURRENCY>
              </td>
            </tr>
            <tr>
              <td align="right" nowrap>Annual Date:&nbsp;</td>
              <td align="left" nowrap>
                <input disabled type="text" name="rmdAnnualDate" maxlength="10" size="10" value="<%= rmdAnnualDate %>">
              </td>
            </tr>
            <tr>
              <td align="right" nowrap>Last Notification Date:&nbsp;</td>
              <td align="left" nowrap>
                <input disabled type="text" name="lastNotificationDate" maxlength="10" size="10" value="<%= lastNotificationDate %>">
              </td>
            </tr>
            <tr>
              <td align="right" nowrap>Last Payment Date:&nbsp;</td>
              <td align="left" nowrap>
                <input disabled type="text" name="lastPaymentDate" maxlength="10" size="10" value="<%= lastPaymentDate %>">
              </td>
            </tr>
            <tr>
              <td align="right" nowrap>Last Payment Amount:&nbsp;</td>
              <td align="left" nowrap>
                <input disabled type="text" name="lastPaymentAmount" maxlength="20" size="20" value="<%= lastPaymentAmount %>" CURRENCY>
              </td>
            </tr>
            <tr>
              <td align="right" nowrap>Next Payment Date:&nbsp;</td>
              <td align="left" nowrap>
                <input type="text" name="nextPaymentDate" tabindex="4" maxlength="10" size="10" value="<%= nextPaymentDate %>">
              </td>
            </tr>
          </table>
        </span>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>RMD Election:&nbsp;</td>
      <td align="left" nowrap>
	    <select name="rmdElection">
          <option>Please Select</option>
         	<%
              for(int i = 0; i < rmdElections.length; i++)
              {
                  String codeTablePK = rmdElections[i].getCodeTablePK() + "";
                  String codeDesc    = rmdElections[i].getCodeDesc();
                  String code        = rmdElections[i].getCode();

                  if (rmdElection.equalsIgnoreCase(code))
                  {
                      out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                  }
              }
         	%>
        </select>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Life Expectancy Multiple:&nbsp;</td>
      <td align="left" nowrap>
	    <select name="lifeExpectancyMultiple">
          <option>Please Select</option>
         	<%
              for(int i = 0; i < lifeExpectancyMultiples.length; i++)
              {
                  String codeTablePK = lifeExpectancyMultiples[i].getCodeTablePK() + "";
                  String codeDesc    = lifeExpectancyMultiples[i].getCodeDesc();
                  String code        = lifeExpectancyMultiples[i].getCode();

                  if (lifeExpectancyMultiple.equalsIgnoreCase(code))
                  {
                      out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                  }
              }
         	%>
        </select>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>RMD Modal Override Amount:&nbsp;</td>
      <td align="left" nowrap colspan="3">
        <input type="text" name="modalOverrideAmount" maxlength="20" size="20" value="<%= modalOverrideAmount %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Initial CY Accum Value:&nbsp;</td>
      <td align="left" nowrap colspan="3">
        <input type="text" name="initialCYAccumValue" maxlength="20" size="20" value="<%= initialCYAccumValue %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>RMD Start Date:&nbsp;</td>
      <td align="left" nowrap colspan="3">
           <input type="text" name="rmdStartDate" value="<%= rmdStartDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.rmdStartDate', f.rmdStartDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td align="right" nowrap colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td align="right" nowrap colspan="4">&nbsp;</td>
    </tr>
  </table>
  <table id="table3" width="100%" height="28%" border="0" cellspacing="6" cellpadding="0" bgcolor="#DDDDDD">
	<tr>
	  <td valign="top" align="right" nowrap colspan="2">
        <input type="button" name="bSave" value="Save" tabindex="7" onClick="saveRmdDialog()">
		<input type="button" name="bCancel" value="Cancel" tabindex="8" onClick ="closeWindow()">
	  </td>
	</tr>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">

</form>
</body>
</html>






