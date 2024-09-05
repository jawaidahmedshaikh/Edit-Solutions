<%@ page import="java.util.Map,
                 java.util.Iterator,
                 fission.beans.PageBean,
                 fission.utility.Util,
                 edit.common.EDITDate,
                 edit.common.EDITBigDecimal,
                 edit.common.vo.*"%><!--
 *
 * User: cgleason
 * Date: May 3, 2005
 * Time: 10:47:58 AM
 *
 * (c) 2000 - 2005 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use
 *  subject to the license agreement.
 -->
<jsp:useBean id="contractFunds"
    class="fission.beans.SessionBean" scope="session"/>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String errorMessage = (String) request.getAttribute("ErrorMessage");

    if (errorMessage == null) errorMessage = "";

    String responseMessage = (String) request.getAttribute("responseMessage");

    String originatingTrxType = "";
    String originatingTrxEffDate = "";
    String originatingTrxAmount = "";
    InvestmentAllocationOverrideVO[] investmentAllocationOvrdVOs = new edit.common.vo.InvestmentAllocationOverrideVO[0];
    if (errorMessage.equals(""))
    {
        originatingTrxType = (String) request.getAttribute("OriginatingTrxType");
        originatingTrxEffDate = (String) request.getAttribute("OriginatingTrxEffDate");

        originatingTrxAmount = (String) request.getAttribute("OriginatingTrxAmount");

        investmentAllocationOvrdVOs = (InvestmentAllocationOverrideVO[])request.getAttribute("OriginatingInvestmentInfoVOs");
    }

    UIFilteredFundVO[] uiFilteredFundVOs = (UIFilteredFundVO[]) request.getAttribute("uiFilteredFundVOs");


%>
<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var errorMessage = "<%= errorMessage %>";

    function init()
    {
        if (errorMessage != "")
        {
            alert(errorMessage);
            window.close();
        }
    }

	function closeOriginatingTrxDialog() {

		window.close();
	}
</script>

<head>
<title>Originating Transaction Info</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name= "originatingTrxForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="mainContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible;  background-color:#DDDDDD">
  <table id="table1" width="40%" border="0" cellspacing="5" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="right" width="5%">Originating Transaction Type:</td>
      <td>
        <input type="text" name="OriginatingTrxType" size="20" maxlength="20" disabled value="<%= originatingTrxType %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right" width="5%">Originating Transaction EffectiveDate:</td>
      <td>
        <input disabled type="text" name="originatingTrxEffDate" size="10" maxlength="10" value="<%= originatingTrxEffDate %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right" width="5%">Originating Transaction Amount:</td>
      <td>
        <input type="text" name="OriginatingTrxAmount" size="25" maxlength="25" disabled value="<%= originatingTrxAmount %>">
      </td>
    </tr>
    &nbsp;&nbsp;
  </table>

  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td colspan="4" nowrap>Investment Info:</td>
    </tr>
  </table>
  <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th width="25%" align="left">Fund</th>
      <th width="25%" align="left">Allocation Percent</th>
	  <th width="25%" align="left">Allocation Dollars</th>
      <th width="25%" align="left">ToFromStatus</th>
    </tr>
  </table>

<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:34%; top:0; left:0;">
    <table id="investmentSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
      <%
          String sAllocationPct = "";
          String sFilteredFundFK = "";
          String sFundName = "";
          String sAllocationDollars = "";
          String sToFromStatus = "";
          InvestmentAllocationOverrideVO investmentAllocationOvrdVO;
          InvestmentAllocationVO investmentAllocationVO;
          InvestmentVO investmentVO;

          if (investmentAllocationOvrdVOs != null)
          {
              for (int i = 0; i < investmentAllocationOvrdVOs.length; i++)
              {
                  investmentAllocationOvrdVO = investmentAllocationOvrdVOs[i];
                  sToFromStatus = investmentAllocationOvrdVO.getToFromStatus();
                  investmentAllocationVO = (InvestmentAllocationVO)investmentAllocationOvrdVO.getParentVO(InvestmentAllocationVO.class);
                  investmentVO = (InvestmentVO) investmentAllocationOvrdVO.getParentVO(InvestmentVO.class);
                  sFilteredFundFK = investmentVO.getFilteredFundFK() + "";
                  sAllocationPct = investmentAllocationVO.getAllocationPercent() + "";
                  if (!sAllocationPct.equals(""))
                  {
                      sAllocationPct = Util.formatDecimal("###,###,##0.0000", new EDITBigDecimal(sAllocationPct));
                  }

                  sAllocationDollars = investmentAllocationVO.getDollars() + "";


                  if (uiFilteredFundVOs != null)
                  {
                       for(int j = 0; j < uiFilteredFundVOs.length; j++)
                       {

                           FilteredFundVO[] filteredFundVO = uiFilteredFundVOs[j].getFilteredFundVO();
                           if (sFilteredFundFK.equals(filteredFundVO[0].getFilteredFundPK() + ""))
                           {
                               FundVO[] fundVO = uiFilteredFundVOs[j].getFundVO();
                               sFundName = fundVO[0].getName();
                               break;
                           }
                       }
                 }
          %>
            <tr class="false" isSelected="false" >
                <td align="left" nowrap width="30%">
                  <%= sFundName %>&nbsp;&nbsp;&nbsp;
                </td>
                <td align="left" nowrap width="23%">
                  <%= sAllocationPct %>
                </td>
                <td align="left" nowrap width="23%">
                   <script>document.write(formatAsCurrency(<%= sAllocationDollars %>))</script>
                </td>
                <td align="left" nowrap width="23%">
                  &nbsp;&nbsp;&nbsp;<%= sToFromStatus %>
                </td>
            </tr>
          <%
             } // End for
         }  // end if
      %>
      <tr class="filler">
        <td colspan="4">
            &nbsp;
        </td>
      </tr>
    </table>
</span>
  <table id="table3" width="100%" border="0" cellspacing="6" cellpadding="0">
	<tr>
	  <td align="right" nowrap colspan="2">
		<input type="button" name="close" value="Close" onClick ="closeOriginatingTrxDialog()">
	  </td>
	</tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">


</form>
</body>
</html>
