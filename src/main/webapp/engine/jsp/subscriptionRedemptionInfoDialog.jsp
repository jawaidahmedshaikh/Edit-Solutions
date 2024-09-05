<%@ page import="edit.common.vo.*,
                 fission.utility.*,
                 edit.common.CodeTableWrapper,
                 java.util.StringTokenizer,
                 edit.common.exceptions.VOEditException"%>
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] hedgeDayTypeCTVOs = codeTableWrapper.getCodeTableEntries("HEDGEDAYTYPE");
    CodeTableVO[] hedgeModeCTVOs = codeTableWrapper.getCodeTableEntries("HEDGEMODE");

    FilteredFundVO filteredFundVO = (FilteredFundVO) session.getAttribute("selectedFilteredFundVO");

    FundVO[] fundVOs = (FundVO[]) session.getAttribute("fundVOs");

    String subscriptionNotificationDays = Util.initString(filteredFundVO.getSubscriptionNotificationDays() + "", "");
    String subscriptionDaysTypeCT = Util.initString(filteredFundVO.getSubscriptionDaysTypeCT(), "");
    String subscriptionModeCT = Util.initString(filteredFundVO.getSubscriptionModeCT(), "");
    String coiReplenishmentDaysTypeCT = Util.initString(filteredFundVO.getCOIReplenishmentDaysTypeCT(), "");
    String coiReplenishmentDays = Util.initString(filteredFundVO.getCOIReplenishmentDays() + "", "");
    String coiReplenishmentModeCT = Util.initString(filteredFundVO.getCOIReplenishmentModeCT(), "");
    String deathDaysTypeCT = Util.initString(filteredFundVO.getDeathDaysTypeCT(), "");
    String deathDays = Util.initString(filteredFundVO.getDeathDays() + "", "");
    String deathModeCT = Util.initString(filteredFundVO.getDeathModeCT(), "");
    String fullSurrenderDaysTypeCT = Util.initString(filteredFundVO.getFullSurrenderDaysTypeCT(), "");
    String fullSurrenderDays = Util.initString(filteredFundVO.getFullSurrenderDays() + "", "");
    String fullSurrenderModeCT = Util.initString(filteredFundVO.getFullSurrenderModeCT(), "");
    String withdrawalDaysTypeCT = Util.initString(filteredFundVO.getWithdrawalDaysTypeCT(), "");
    String withdrawalDays = Util.initString(filteredFundVO.getWithdrawalDays() + "", "");
    String withdrawalModeCT = Util.initString(filteredFundVO.getWithdrawalModeCT(), "");
    String transferDaysTypeCT = Util.initString(filteredFundVO.getTransferDaysTypeCT(), "");
    String transferDays = Util.initString(filteredFundVO.getTransferDays() + "", "");
    String transferModeCT = Util.initString(filteredFundVO.getTransferModeCT(), "");
    String loanDaysTypeCT = Util.initString(filteredFundVO.getLoanDaysTypeCT(), "");
    String loanDays = Util.initString(filteredFundVO.getLoanDays() + "", "");
    String loanModeCT = Util.initString(filteredFundVO.getLoanModeCT(), "");
    String seriesTransferDaysTypeCT = Util.initString(filteredFundVO.getSeriesTransferDaysTypeCT(), "");
    String seriesTransferDays = Util.initString(filteredFundVO.getSeriesTransferDays() + "", "");
    String seriesTransferModeCT = Util.initString(filteredFundVO.getSeriesTransferModeCT(), "");
    String divFeesLiquidationDaysTypeCT = Util.initString(filteredFundVO.getDivFeesLiquidatnDaysTypeCT(), "");
    String divFeesLiquidationDays = Util.initString(filteredFundVO.getDivisionFeesLiquidationDays() + "", "");
    String divisionFeesLiquidationModeCT = Util.initString(filteredFundVO.getDivisionFeesLiquidationModeCT(), "");
    String holdingAccountFK = Util.initString(filteredFundVO.getHoldingAccountFK() + "", "");
%>

<html>

<head>

<!-- ***** JAVASCRIPT *****  -->

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.subRedInfoForm;
	}

    function saveSubscriptionRedemptionInfo()
    {
        sendTransactionAction("FundTran", "saveSubscriptionRedemptionInfo", "main");
        window.close();
    }

    function cancelSubscriptionRedemptionInfo()
    {
        sendTransactionAction("FundTran", "cancelSubscriptionRedemptionInfo", "main");
        window.close();
    }

    function sendTransactionAction(transaction, action, target)
    {
		f.transaction.value = transaction;
    	f.action.value = action;

    	f.target = target;

    	f.submit();
    }

</script>
<title>Subscription/Redemption Information</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
</head>
<body  bgcolor="#DDDDDD" onLoad="init()">
<form name="subRedInfoForm" method="post" action="/PORTAL/servlet/RequestManager">
  <br>
  Subscription
  <br>
  <span id="span1" style="border-style:solid; border-width:1;  position:relative; width:100%; height:15%; top:0; left:0; z-index:0; overflow:visible">
    <table width="45%" border="0" cellspacing="6" cellpadding="0" bgcolor="#DDDDDD">
      <tr>
        <td nowrap align="left" colspan="2">&nbsp;</td>
        <td nowrap align="left">Notification Days</td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">Type</td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">Mode</td>
      </tr>
      <tr>
        <td nowrap align="right">Premium/Transfer To</td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="subscriptionNotificationDays" size="3" maxlength="3" value="<%= subscriptionNotificationDays %>">
        </td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <select name="subscriptionDaysTypeCT">
            <%
              out.println("<option>Please Select</option>");

              for (int i = 0; i < hedgeDayTypeCTVOs.length; i++)
              {
                  String hedgeDayTypeCode = hedgeDayTypeCTVOs[i].getCode();
                  String hedgeDayTypeCodeDesc = hedgeDayTypeCTVOs[i].getCodeDesc();

                  if (subscriptionDaysTypeCT.equals(hedgeDayTypeCode))
                  {
                      out.println("<option selected value=\"" + hedgeDayTypeCode + "\">" + hedgeDayTypeCodeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option value=\"" + hedgeDayTypeCode + "\">" + hedgeDayTypeCodeDesc + "</option>");
                  }
              }
            %>
          </select>
        </td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <select name="subscriptionModeCT">
            <%
              out.println("<option>Please Select</option>");

              for (int i = 0; i < hedgeModeCTVOs.length; i++)
              {
                  String hedgeModeCode = hedgeModeCTVOs[i].getCode();
                  String hedgeModeCodeDesc = hedgeModeCTVOs[i].getCodeDesc();

                  if (subscriptionModeCT.equals(hedgeModeCode))
                  {
                      out.println("<option selected value=\"" + hedgeModeCode + "\">" + hedgeModeCodeDesc + "</option>");
                  }
                  else if (!hedgeModeCode.equalsIgnoreCase("Special"))
                  {
                      out.println("<option value=\"" + hedgeModeCode + "\">" + hedgeModeCodeDesc + "</option>");
                  }
              }
            %>
          </select>
        </td>
      </tr>
    </table>
  </span>
  <br>
  Redemption
  <br>
  <span id="span2" style="border-style:solid; border-width:1;  position:relative; width:100%; height:45%; top:0; left:0; z-index:0; overflow:visible">
    <table width="45%" border="0" cellspacing="6" cellpadding="0" bgcolor="#DDDDDD">
      <tr>
        <td nowrap align="left" colspan="2">&nbsp;</td>
        <td nowrap align="left">Notification Days</td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">Type</td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">Mode</td>
      </tr>
      <tr>
        <td nowrap align="right">COI Replenishment</td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="coiReplenishmentDays" size="3" maxlength="3" value="<%= coiReplenishmentDays %>">
        </td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <select name="coiReplenishmentDaysTypeCT">
            <%
              out.println("<option>Please Select</option>");

              for (int i = 0; i < hedgeDayTypeCTVOs.length; i++)
              {
                  String hedgeDayTypeCode = hedgeDayTypeCTVOs[i].getCode();
                  String hedgeDayTypeCodeDesc = hedgeDayTypeCTVOs[i].getCodeDesc();

                  if (coiReplenishmentDaysTypeCT.equals(hedgeDayTypeCode))
                  {
                      out.println("<option selected value=\"" + hedgeDayTypeCode + "\">" + hedgeDayTypeCodeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option value=\"" + hedgeDayTypeCode + "\">" + hedgeDayTypeCodeDesc + "</option>");
                  }
              }
            %>
          </select>
        </td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <select name="coiReplenishmentModeCT">
            <%
              out.println("<option>Please Select</option>");

              for (int i = 0; i < hedgeModeCTVOs.length; i++)
              {
                  String hedgeModeCode = hedgeModeCTVOs[i].getCode();
                  String hedgeModeCodeDesc = hedgeModeCTVOs[i].getCodeDesc();

                  if (coiReplenishmentModeCT.equals(hedgeModeCode))
                  {
                      out.println("<option selected value=\"" + hedgeModeCode + "\">" + hedgeModeCodeDesc + "</option>");
                  }
                  else if (!hedgeModeCode.equalsIgnoreCase("Special"))
                  {
                      out.println("<option value=\"" + hedgeModeCode + "\">" + hedgeModeCodeDesc + "</option>");
                  }
              }
            %>
          </select>
        </td>
      </tr>
      <tr>
        <td nowrap align="right">Death Claim</td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="deathDays" size="3" maxlength="3" value="<%= deathDays %>">
        </td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <select name="deathDaysTypeCT">
            <%
              out.println("<option>Please Select</option>");

              for (int i = 0; i < hedgeDayTypeCTVOs.length; i++)
              {
                  String hedgeDayTypeCode = hedgeDayTypeCTVOs[i].getCode();
                  String hedgeDayTypeCodeDesc = hedgeDayTypeCTVOs[i].getCodeDesc();

                  if (deathDaysTypeCT.equals(hedgeDayTypeCode))
                  {
                      out.println("<option selected value=\"" + hedgeDayTypeCode + "\">" + hedgeDayTypeCodeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option value=\"" + hedgeDayTypeCode + "\">" + hedgeDayTypeCodeDesc + "</option>");
                  }
              }
            %>
          </select>
        </td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <select name="deathModeCT">
            <%
              out.println("<option>Please Select</option>");

              for (int i = 0; i < hedgeModeCTVOs.length; i++)
              {
                  String hedgeModeCode = hedgeModeCTVOs[i].getCode();
                  String hedgeModeCodeDesc = hedgeModeCTVOs[i].getCodeDesc();

                  if (deathModeCT.equals(hedgeModeCode))
                  {
                      out.println("<option selected value=\"" + hedgeModeCode + "\">" + hedgeModeCodeDesc + "</option>");
                  }
                  else if (!hedgeModeCode.equalsIgnoreCase("Special"))
                  {
                      out.println("<option value=\"" + hedgeModeCode + "\">" + hedgeModeCodeDesc + "</option>");
                  }
              }
            %>
          </select>
        </td>
      </tr>
      <tr>
        <td nowrap align="right">Full Surrender</td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="fullSurrenderDays" size="3" maxlength="3" value="<%= fullSurrenderDays %>">
        </td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <select name="fullSurrenderDaysTypeCT">
            <%
              out.println("<option>Please Select</option>");

              for (int i = 0; i < hedgeDayTypeCTVOs.length; i++)
              {
                  String hedgeDayTypeCode = hedgeDayTypeCTVOs[i].getCode();
                  String hedgeDayTypeCodeDesc = hedgeDayTypeCTVOs[i].getCodeDesc();

                  if (fullSurrenderDaysTypeCT.equals(hedgeDayTypeCode))
                  {
                      out.println("<option selected value=\"" + hedgeDayTypeCode + "\">" + hedgeDayTypeCodeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option value=\"" + hedgeDayTypeCode + "\">" + hedgeDayTypeCodeDesc + "</option>");
                  }
              }
            %>
          </select>
        </td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <select name="fullSurrenderModeCT">
            <%
              out.println("<option>Please Select</option>");

              for (int i = 0; i < hedgeModeCTVOs.length; i++)
              {
                  String hedgeModeCode = hedgeModeCTVOs[i].getCode();
                  String hedgeModeCodeDesc = hedgeModeCTVOs[i].getCodeDesc();

                  if (fullSurrenderModeCT.equals(hedgeModeCode))
                  {
                      out.println("<option selected value=\"" + hedgeModeCode + "\">" + hedgeModeCodeDesc + "</option>");
                  }
                  else if (!hedgeModeCode.equalsIgnoreCase("Special"))
                  {
                      out.println("<option value=\"" + hedgeModeCode + "\">" + hedgeModeCodeDesc + "</option>");
                  }
              }
            %>
          </select>
        </td>
      </tr>
      <tr>
        <td nowrap align="right">Withdrawal</td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="withdrawalDays" size="3" maxlength="3" value="<%= withdrawalDays %>">
        </td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <select name="withdrawalDaysTypeCT">
            <%
              out.println("<option>Please Select</option>");

              for (int i = 0; i < hedgeDayTypeCTVOs.length; i++)
              {
                  String hedgeDayTypeCode = hedgeDayTypeCTVOs[i].getCode();
                  String hedgeDayTypeCodeDesc = hedgeDayTypeCTVOs[i].getCodeDesc();

                  if (withdrawalDaysTypeCT.equals(hedgeDayTypeCode))
                  {
                      out.println("<option selected value=\"" + hedgeDayTypeCode + "\">" + hedgeDayTypeCodeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option value=\"" + hedgeDayTypeCode + "\">" + hedgeDayTypeCodeDesc + "</option>");
                  }
              }
            %>
          </select>
        </td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <select name="withdrawalModeCT">
            <%
              out.println("<option>Please Select</option>");

              for (int i = 0; i < hedgeModeCTVOs.length; i++)
              {
                  String hedgeModeCode = hedgeModeCTVOs[i].getCode();
                  String hedgeModeCodeDesc = hedgeModeCTVOs[i].getCodeDesc();

                  if (withdrawalModeCT.equals(hedgeModeCode))
                  {
                      out.println("<option selected value=\"" + hedgeModeCode + "\">" + hedgeModeCodeDesc + "</option>");
                  }
                  else if (!hedgeModeCode.equalsIgnoreCase("Special"))
                  {
                      out.println("<option value=\"" + hedgeModeCode + "\">" + hedgeModeCodeDesc + "</option>");
                  }
              }
            %>
          </select>
        </td>
      </tr>
      <tr>
        <td nowrap align="right">Transfer</td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="transferDays" size="3" maxlength="3" value="<%= transferDays %>">
        </td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <select name="transferDaysTypeCT">
            <%
              out.println("<option>Please Select</option>");

              for (int i = 0; i < hedgeDayTypeCTVOs.length; i++)
              {
                  String hedgeDayTypeCode = hedgeDayTypeCTVOs[i].getCode();
                  String hedgeDayTypeCodeDesc = hedgeDayTypeCTVOs[i].getCodeDesc();

                  if (transferDaysTypeCT.equals(hedgeDayTypeCode))
                  {
                      out.println("<option selected value=\"" + hedgeDayTypeCode + "\">" + hedgeDayTypeCodeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option value=\"" + hedgeDayTypeCode + "\">" + hedgeDayTypeCodeDesc + "</option>");
                  }
              }
            %>
          </select>
        </td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <select name="transferModeCT">
            <%
              out.println("<option>Please Select</option>");

              for (int i = 0; i < hedgeModeCTVOs.length; i++)
              {
                  String hedgeModeCode = hedgeModeCTVOs[i].getCode();
                  String hedgeModeCodeDesc = hedgeModeCTVOs[i].getCodeDesc();

                  if (transferModeCT.equals(hedgeModeCode))
                  {
                      out.println("<option selected value=\"" + hedgeModeCode + "\">" + hedgeModeCodeDesc + "</option>");
                  }
                  else if (!hedgeModeCode.equalsIgnoreCase("Special"))
                  {
                      out.println("<option value=\"" + hedgeModeCode + "\">" + hedgeModeCodeDesc + "</option>");
                  }
              }
            %>
          </select>
        </td>
      </tr>
      <tr>
        <td nowrap align="right">Loan</td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="loanDays" size="3" maxlength="3" value="<%= loanDays %>">
        </td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <select name="loanDaysTypeCT">
            <%
              out.println("<option>Please Select</option>");

              for (int i = 0; i < hedgeDayTypeCTVOs.length; i++)
              {
                  String hedgeDayTypeCode = hedgeDayTypeCTVOs[i].getCode();
                  String hedgeDayTypeCodeDesc = hedgeDayTypeCTVOs[i].getCodeDesc();

                  if (loanDaysTypeCT.equals(hedgeDayTypeCode))
                  {
                      out.println("<option selected value=\"" + hedgeDayTypeCode + "\">" + hedgeDayTypeCodeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option value=\"" + hedgeDayTypeCode + "\">" + hedgeDayTypeCodeDesc + "</option>");
                  }
              }
            %>
          </select>
        </td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <select name="loanModeCT">
            <%
              out.println("<option>Please Select</option>");

              for (int i = 0; i < hedgeModeCTVOs.length; i++)
              {
                  String hedgeModeCode = hedgeModeCTVOs[i].getCode();
                  String hedgeModeCodeDesc = hedgeModeCTVOs[i].getCodeDesc();

                  if (loanModeCT.equals(hedgeModeCode))
                  {
                      out.println("<option selected value=\"" + hedgeModeCode + "\">" + hedgeModeCodeDesc + "</option>");
                  }
                  else if (!hedgeModeCode.equalsIgnoreCase("Special"))
                  {
                      out.println("<option value=\"" + hedgeModeCode + "\">" + hedgeModeCodeDesc + "</option>");
                  }
              }
            %>
          </select>
        </td>
      </tr>
      <tr>
        <td nowrap align="right">Series Transfer</td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="seriesTransferDays" size="3" maxlength="3" value="<%= seriesTransferDays %>">
        </td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <select name="seriesTransferDaysTypeCT">
            <%
              out.println("<option>Please Select</option>");

              for (int i = 0; i < hedgeDayTypeCTVOs.length; i++)
              {
                  String hedgeDayTypeCode = hedgeDayTypeCTVOs[i].getCode();
                  String hedgeDayTypeCodeDesc = hedgeDayTypeCTVOs[i].getCodeDesc();

                  if (seriesTransferDaysTypeCT.equals(hedgeDayTypeCode))
                  {
                      out.println("<option selected value=\"" + hedgeDayTypeCode + "\">" + hedgeDayTypeCodeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option value=\"" + hedgeDayTypeCode + "\">" + hedgeDayTypeCodeDesc + "</option>");
                  }
              }
            %>
          </select>
        </td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <select name="seriesTransferModeCT">
            <%
              out.println("<option>Please Select</option>");

              for (int i = 0; i < hedgeModeCTVOs.length; i++)
              {
                  String hedgeModeCode = hedgeModeCTVOs[i].getCode();
                  String hedgeModeCodeDesc = hedgeModeCTVOs[i].getCodeDesc();

                  if (seriesTransferModeCT.equals(hedgeModeCode))
                  {
                      out.println("<option selected value=\"" + hedgeModeCode + "\">" + hedgeModeCodeDesc + "</option>");
                  }
                  else if (!hedgeModeCode.equalsIgnoreCase("Monthly"))
                  {
                      out.println("<option value=\"" + hedgeModeCode + "\">" + hedgeModeCodeDesc + "</option>");
                  }
              }
            %>
          </select>
        </td>
      </tr>
      <tr>
        <td nowrap align="right">Division Fees Liquidation</td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="divFeesLiquidationDays" size="3" maxlength="3" value="<%= divFeesLiquidationDays %>">
        </td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <select name="divFeesLiquidationDaysTypeCT">
            <%
              out.println("<option>Please Select</option>");

              for (int i = 0; i < hedgeDayTypeCTVOs.length; i++)
              {
                  String hedgeDayTypeCode = hedgeDayTypeCTVOs[i].getCode();
                  String hedgeDayTypeCodeDesc = hedgeDayTypeCTVOs[i].getCodeDesc();

                  if (divFeesLiquidationDaysTypeCT.equals(hedgeDayTypeCode))
                  {
                      out.println("<option selected value=\"" + hedgeDayTypeCode + "\">" + hedgeDayTypeCodeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option value=\"" + hedgeDayTypeCode + "\">" + hedgeDayTypeCodeDesc + "</option>");
                  }
              }
            %>
          </select>
        </td>
        <td nowrap align="left">&nbsp;</td>
        <td nowrap align="left">
          <select name="divisionFeesLiquidationModeCT">
            <%
              out.println("<option>Please Select</option>");

              for (int i = 0; i < hedgeModeCTVOs.length; i++)
              {
                  String hedgeModeCode = hedgeModeCTVOs[i].getCode();
                  String hedgeModeCodeDesc = hedgeModeCTVOs[i].getCodeDesc();

                  if (divisionFeesLiquidationModeCT.equals(hedgeModeCode))
                  {
                      out.println("<option selected value=\"" + hedgeModeCode + "\">" + hedgeModeCodeDesc + "</option>");
                  }
                  else if (!hedgeModeCode.equalsIgnoreCase("Monthly"))
                  {
                      out.println("<option value=\"" + hedgeModeCode + "\">" + hedgeModeCodeDesc + "</option>");
                  }
              }
            %>
          </select>
        </td>
      </tr>
      <tr>
        <td nowrap align="left" colspan="6">&nbsp;</td>
<%--        <td nowrap align="left">Special Month/Day:&nbsp;--%>
<%--          <input type="text" name="specialMonth" size="2" maxlength="2" value="<%= specialMonth %>">--%>
<%--          /--%>
<%--          <input type="text" name="specialDay" size="2" maxlength="2" value="<%= specialDay %>">--%>
<%--        </td>--%>
    </table>
  </span>
  <span id="span3" style="border-style:solid; border-width:0; position:relative; width:100%; height:5%; top:0; left:0; z-index:0; overflow:visible">
    <table width="100%" border="0" cellspacing="6" cellpadding="0" bgcolor="#DDDDDD">
      <tr>
        <td nowrap align="left">Holding Account:&nbsp;
          <select name="holdingAccountFK">
            <%
              out.println("<option>Please Select</option>");

              for (int i = 0; i < fundVOs.length; i++)
              {
                  String fundPK = fundVOs[i].getFundPK() + "";

                  if (holdingAccountFK.equals(fundPK))
                  {
                      out.println("<option selected value=\"" + fundPK + "\">" + fundVOs[i].getName() + "</option>");
                  }
                  else
                  {
                      out.println("<option value=\"" + fundPK + "\">" + fundVOs[i].getName() + "</option>");
                  }
              }
            %>
          </select>
        </td>
      </tr>
    </table>
  </span>
  <span id="span4" style="border-style:solid; border-width:0; position:relative; width:100%; height:5%; top:0; left:0; z-index:0; overflow:visible">
    <table width="100%" border="0" cellspacing="6" cellpadding="0" bgcolor="#DDDDDD">
      <tr>
        <td nowrap align="right" colspan="7">
          <input type="button" name="save" value="Save" onClick="saveSubscriptionRedemptionInfo()">
          <input type="button" name="cancel" value="Cancel" onClick="cancelSubscriptionRedemptionInfo()">
        </td>
      </tr>
    </table>
  </span>

<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction"    value="">
<input type="hidden" name="action"         value="">

</form>
</body>
</html>
