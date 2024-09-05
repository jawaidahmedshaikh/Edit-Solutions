<%@ page import="edit.common.vo.*,
                 edit.common.vo.user.*,
                 java.util.*,
                 edit.common.*,
                 fission.utility.*"%>
<%
    EDITTrxCorrespondenceVO[] editTrxCorrespondenceVOs = (EDITTrxCorrespondenceVO[]) request.getAttribute("editTrxCorrespondenceVOs");
%>
<html>

<head>
<title>Correspondence</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	function init()
    {
	}
</script>
</head>
<body  class="mainTheme" onLoad="init()">
  <span class="tableHeading">Correspondence</span><br>
    <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="heading">
        <th nowrap width="20%">Type</th>
        <th nowrap width="20%">Qualifier</th>
        <th nowrap width="20%">Status</th>
        <th nowrap width="20%">Date</th>
        <th nowrap width="20%">Notification Amount</th>
      </tr>
    </table>
    <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:80%; top:0; left:0; background-color:#BBBBBB">
      <table class="summary" id="correspondenceSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
          <%
            if (editTrxCorrespondenceVOs != null)
            {
                for (int i = 0; i < editTrxCorrespondenceVOs.length; i++)
                {
                    EDITTrxCorrespondenceVO editTrxCorrespondenceVO = editTrxCorrespondenceVOs[i];

                    TransactionCorrespondenceVO transactionCorrespondenceVO = (TransactionCorrespondenceVO) editTrxCorrespondenceVO.getParentVO(TransactionCorrespondenceVO.class);

                    String type = Util.initString(transactionCorrespondenceVO.getCorrespondenceTypeCT(), "");
                    String qualifier = Util.initString(transactionCorrespondenceVO.getTransactionTypeQualifierCT(), "");
                    String status = Util.initString(editTrxCorrespondenceVO.getStatus(), "");
                    String corrDate = "";
                    if (editTrxCorrespondenceVO.getCorrespondenceDate() != null)
                    {
                        corrDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(editTrxCorrespondenceVO.getCorrespondenceDate());
                    }
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
              <%= corrDate %>
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
            <td colspan="4">&nbsp;</td>
          </tr>
      </table>
    </span>

    <table width="100%">
      <tr>
         <td align="right" valign="bottom">
            <input type="button" name="clone" value="Close" onClick="closeWindow()">
        </td>
      </tr>
    </table>

</body>
</html>
