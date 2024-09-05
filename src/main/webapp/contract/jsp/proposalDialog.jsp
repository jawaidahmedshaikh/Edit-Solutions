<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 fission.utility.*" %>

<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>
<%
    String proposalMessage = Util.initString((String) request.getAttribute("proposalMessage"), "");

    ProposalVO proposalVO = (ProposalVO) session.getAttribute("proposalVO");

    String contractNumber = contractMainSessionBean.getValue("contractId");

    String proposalDate = "";

    ProposalProjectionVO[] proposalProjectionVOs = null;
    if (proposalVO != null)
    {
        proposalDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(proposalVO.getProposalDate());

        proposalProjectionVOs = proposalVO.getProposalProjectionVO();
    }
%>

<html>

<!-- ***** JAVASCRIPT *****  -->

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<script language="Javascript1.2">

    var f = null;

    var inforceQuoteMessage = "<%= proposalMessage %>";

    function init()
    {
	    f = document.proposalForm;

        f.proposalDate.focus();

        if (proposalMessage != "")
        {
            alert(proposalMessage);
        }

        formatCurrency();
    }

    function performProposal()
    {
        try
        {
            sendTransactionAction("ContractDetailTran","performProposal","_self");
        }
        catch (e)
        {
            alert(e);
        }
    }

    function clearProposalDialog()
    {
        sendTransactionAction("ContractDetailTran","clearProposalDialog","_self");
    }

    function closeProposalDialog()
    {
        sendTransactionAction("ContractDetailTran","closeProposalDialog","contentIFrame");
        window.close();
    }

</script>

<head>
<title>Proposal</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1">

<form  name="proposalForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="span1" style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="5%" border="0" cellspacing="0" cellpadding="4">
    <tr height="100%">
      <td align="right" nowrap>Contract Number:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="contractNumber" maxlength="15" size="15" value="<%= contractNumber %>">
      </td>
      <td>&nbsp;</td>
      <td align="right" nowrap>Proposal Date:&nbsp;</td>
      <td align="left" nowrap>
           <input type="text" name="proposalDate" value="<%= proposalDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.proposalDate', f.proposalDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap colspan="5">
        <input type="button" name="enter" value="Enter" onClick="performProposal()">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" name="clear" value="Clear" onClick="clearProposalDialog()">
      </td>
    </tr>
  </table>
  <hr align="left" width="100%" noshade>
  <br>
  Proposal Information
  <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th align="left" width="14%">End of Year</th>
      <th align="left" width="14%">Date</th>
      <th align="left" width="14%">Age</th>
      <th align="left" width="14%">Premiums Received</th>
      <th align="left" width="14%">Charge</th>
      <th align="left" width="14%">Death Benefit</th>
      <th align="left" width="16%">Non-Guaranteed Projected Fund Value</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:75%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="proposalSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
        <%
            String hClassName = "default";
            boolean hSelected = false;

            String endOfYear = "";
            String date = "";
            String age = "";
            EDITBigDecimal premiumsReceived = new EDITBigDecimal();
            EDITBigDecimal charge = new EDITBigDecimal();
            EDITBigDecimal deathBenefit = new EDITBigDecimal();
            EDITBigDecimal nonGuarProjFundValue = new EDITBigDecimal();

            if (proposalProjectionVOs != null)
            {
                for (int i = 0; i < proposalProjectionVOs.length; i++)
                {
                    endOfYear = proposalProjectionVOs[i].getEndOfYear() + "";
                    date = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(proposalProjectionVOs[i].getDate());
                    age = proposalProjectionVOs[i].getAge() + "";
                    premiumsReceived = new EDITBigDecimal(Util.initBigDecimal(proposalProjectionVOs[i], "PremiumsReceived", new EDITBigDecimal().getBigDecimal()));
                    charge = new EDITBigDecimal(Util.initBigDecimal(proposalProjectionVOs[i], "Charge", new EDITBigDecimal().getBigDecimal()));
                    deathBenefit = new EDITBigDecimal(Util.initBigDecimal(proposalProjectionVOs[i], "DeathBenefit", new EDITBigDecimal().getBigDecimal()));
                    nonGuarProjFundValue = new EDITBigDecimal(Util.initBigDecimal(proposalProjectionVOs[i], "NonGuarProjectedFundValue", new EDITBigDecimal().getBigDecimal()));
        %>
        <tr class="<%= hClassName %>" isSelected="<%= hSelected %>"
            onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
          <td align="left" nowrap width="14%">
            <%= endOfYear %>
          </td>
          <td align="left" nowrap width="14%">
            <%= date %>
          </td>
          <td align="left" nowrap width="14%">
            <%= age %>
          </td>
          <td align="left" nowrap width="14%">
            <script>document.write(formatAsCurrency(<%= premiumsReceived.toString() %>))</script>
          </td>
          <td align="left" nowrap width="14%">
            <script>document.write(formatAsCurrency(<%= charge.toString() %>))</script>
          </td>
          <td align="left" nowrap width="14%">
            <script>document.write(formatAsCurrency(<%= deathBenefit.toString() %>))</script>
          </td>
          <td align="left" nowrap width="16%">
            <script>document.write(formatAsCurrency(<%= nonGuarProjFundValue.toString() %>))</script>
          </td>
        </tr>
        <%
                }
            }
        %>
    </table>
  </span>
  <table width="100%" height="2%">
    <tr>
      <td align="right">
        <input type="button" name="close" value="Close" onClick="closeProposalDialog()">
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
