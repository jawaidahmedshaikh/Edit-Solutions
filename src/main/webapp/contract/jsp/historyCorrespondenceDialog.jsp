<%@ page import="edit.common.vo.*,
                 edit.common.vo.user.*,
                 java.util.*,
                 edit.common.*,
                 fission.utility.*"%>
<%
    EDITTrxCorrespondenceVO[] editTrxCorrespondenceVOs = (EDITTrxCorrespondenceVO[]) request.getAttribute("editTrxCorrespondenceVOs");
    String editTrxPK = (String) request.getAttribute("editTrxPK");
    if (editTrxPK == null)
    {
        editTrxPK = "";
    }
%>
<html>

<head>
<title>Correspondence</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.historyCorrForm;

        formatCurrency();
	}

    function addNewEDITTrxCorr()
    {
        f.corrType.selectedIndex = 0;
    }

    function saveEDITTrxCorr()
    {
        if (f.corrType.value == "")
        {
            alert("Please Select Correspondence Type");
        }
        else
        {
            sendTransactionAction("ContractDetailTran", "createNewEDITTrxCorrespondence", "_self");
        }
    }
</script>
</head>
<body  class="mainTheme" onLoad="init()">
<form name= "historyCorrForm" method="post" action="/PORTAL/servlet/RequestManager">

    <span class="tableHeading">Correspondence</span><br>

    <table width="100%">
      <tr>
        <td align="left" nowrap >Type:&nbsp;
          <select name="corrType">
            <option>Please Select</option>
            <%
              if (editTrxCorrespondenceVOs != null)
              {
                  Map corrTypeHT = new HashMap();

                  for (int e = 0; e < editTrxCorrespondenceVOs.length; e++)
                  {
                      TransactionCorrespondenceVO transactionCorrespondenceVO = (TransactionCorrespondenceVO) editTrxCorrespondenceVOs[e].getParentVO(TransactionCorrespondenceVO.class);
                      String trxCorrPK = transactionCorrespondenceVO.getTransactionCorrespondencePK() + "";
                      String corrType  = transactionCorrespondenceVO.getCorrespondenceTypeCT();
                      if (!corrTypeHT.containsKey(corrType))
                      {
                        corrTypeHT.put(corrType, trxCorrPK);
                        out.println("<option name=\"id\" value=\"" + trxCorrPK + "\">" + corrType + "</option>");
                      }
                  }
              }
		     %>
          </select>
        </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      <tr>
    </table>

    <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0" class="contentArea">
      <tr>
	  	<td nowrap align="left">
	  	  <input type="button" value="Add" onClick="addNewEDITTrxCorr()">
		  <input type="button" value="Save" onClick="saveEDITTrxCorr()">
	  	</td>
      </tr>
    </table>
    <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="heading">
        <th nowrap width="20%">Type</th>
        <th nowrap width="20%">Qualifier</th>
        <th nowrap width="20%">Status</th>
        <th nowrap width="20%">Date</th>
        <th nowrap width="20%">Notification Amount</th>
      </tr>
    </table>
    <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:65%; top:0; left:0; background-color:#BBBBBB">
      <table class="summary" id="histCorrespondenceSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
          <%
            if (editTrxCorrespondenceVOs != null)
            {
                for (int i = 0; i < editTrxCorrespondenceVOs.length; i++)
                {
                    EDITTrxCorrespondenceVO editTrxCorrespondenceVO = editTrxCorrespondenceVOs[i];

                    TransactionCorrespondenceVO transactionCorrespondenceVO = (TransactionCorrespondenceVO) editTrxCorrespondenceVO.getParentVO(TransactionCorrespondenceVO.class);

                    String type = transactionCorrespondenceVO.getCorrespondenceTypeCT();
                    String qualifier = Util.initString(transactionCorrespondenceVO.getTransactionTypeQualifierCT(), "");
                    String status = editTrxCorrespondenceVO.getStatus();
                    String date = editTrxCorrespondenceVO.getCorrespondenceDate();
                    String notificationAmount = editTrxCorrespondenceVO.getNotificationAmount().toString();
          %>
          <tr class="default">
            <td width="20%">
              <%= type %>
            </td>
            <td width="20%">
              <%= qualifier %>
            </td>
            <td width="20%">
              <%= status %>
            </td>
            <td width="20%">
              <%= date %>
            </td>
            <td width="20%">
              <script>document.write(formatAsCurrency(<%= notificationAmount %>))</script>
            </td>
          </tr>
          <%
                }
            }
          %>
          <tr class="filler">
            <td colspan="4">
              &nbsp;
            </td>
          </tr>
      </table>
    </span>
        </td>
      </tr>
    </table>

    <table width="100%">
      <tr>
        <td align="right" valign="bottom">
          <input type="button" name="clone" value="Close" onClick="closeWindow()">
        </td>
      </tr>
    </table>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">

 <input type="hidden" name="editTrxPK" value="<%= editTrxPK %>">

</form>
</body>
</html>
