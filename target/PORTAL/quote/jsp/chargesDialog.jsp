<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 fission.utility.Util,
                 edit.common.vo.EDITTrxVO,
                 edit.common.vo.EDITTrxHistoryVO,
                 edit.common.vo.ChargeHistoryVO" %>

<jsp:useBean id="contractHistoryChargesSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
    String historyKey = (String) request.getAttribute("editTrxHistoryPK");
    EDITTrxVO[] editTrxVOs = (EDITTrxVO[]) session.getAttribute("editTrxVOs");
%>
<html>
<head>
<title>Charges</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.chargesForm;

        formatCurrency();
	}
</script>
</head>
<body bgcolor="#DDDDDD" onLoad="init()">
<form name="chargesForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th align="left" width="50%">Charge Type</th>
	  <th align="left" width="50%">Amount</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:50%; top:0; left:0; background-color:#BBBBBB">
    <table id="chargesSummary" width="100%" border="0" cellspacing="0" cellpadding="0">
  	  <%
            String chargeType = "";
            String chargeAmount = "";
            if (editTrxVOs != null)
            {
                for (int e = 0; e < editTrxVOs.length; e++)
                {
                    EDITTrxHistoryVO[] editTrxHistoryVOs = editTrxVOs[e].getEDITTrxHistoryVO();
                    if (editTrxHistoryVOs != null)
                    {
                        for (int h = 0; h < editTrxHistoryVOs.length; h++)
                        {
                            if ((editTrxHistoryVOs[h].getEDITTrxHistoryPK() + "").equals(historyKey))
                            {
                                ChargeHistoryVO[] chargeHistoryVOs = editTrxHistoryVOs[h].getChargeHistoryVO();
                                if (chargeHistoryVOs != null)
                                {
                                    for (int c = 0; c < chargeHistoryVOs.length; c++)
                                    {
                                        chargeType = chargeHistoryVOs[c].getChargeTypeCT();
                                        chargeAmount = Util.roundToNearestCent(chargeHistoryVOs[c].getChargeAmount()).toString();
	  %>
	  <tr>
 	    <td nowrap>
		  <%= chargeType %>
		</td>
		<td nowrap>
          <script>document.write(formatAsCurrency(<%= chargeAmount %>))</script>
		</td>
	  </tr>
	  <%
                                    } // end for (chargeHistoryVOs)
                                } // end if (chargeHistoryVOs)
                            } // end if (EDITTrxHistory key)
                        } // end for (EDITTrxHistory loop)
                    } // end if (EDITTrxHIstory)
                } // end for (EDITTrx loop)
            } // end if (EDITTrx)
	  %>
	</table>
  </span>
  <span id="closeContent" style="border-style:solid; border-width:0; position:relative; width:100%;  height:10%; top:0; left:0; z-index:0;">
    <table id="closeTable" width="100%" border="0" cellspacing="0" cellpadding="0" height="10">
      <tr>
	    <td colspan="8" nowrap align="right">
	  	  <input type="button" value="   Close  " onClick="closeWindow()">
        </td>
      </tr>
    </table>
  </span>

  <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction"  value="">
  <input type="hidden" name="action"       value="">

</form>

</body>
</html>
