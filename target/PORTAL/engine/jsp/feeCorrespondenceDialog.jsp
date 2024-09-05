<%@ page import="edit.common.vo.FeeCorrespondenceVO,
                 edit.common.*,
                 fission.utility.*"%>
<%
    FeeCorrespondenceVO feeCorrespondenceVO = (FeeCorrespondenceVO) request.getAttribute("feeCorrespondenceVO");
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
        // do nothing
	}

</script>
</head>
<body  class="mainTheme" onLoad="init()">
  <span class="tableHeading">Correspondence</span><br><br>
    <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="heading">
        <th nowrap width="25%">Address Type</th>
        <th nowrap width="25%">Status</th>
        <th nowrap width="25%">Correspondence Date</th>
        <th nowrap width="25%">Notification Amount</th>
      </tr>
    </table>
    <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:80%; top:0; left:0; background-color:#BBBBBB">
      <table class="summary" id="correspondenceSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
          <%
            String addressTypeCT = "";
            String statusCT = "";
            String correspondenceDateString = null;
            EDITDate correspondenceDate = null;
            String notificationAmount = "";

            if (feeCorrespondenceVO != null)
            {
                addressTypeCT = feeCorrespondenceVO.getAddressTypeCT();
                statusCT = feeCorrespondenceVO.getStatusCT();

                if (feeCorrespondenceVO.getCorrespondenceDate() != null)
                {
                    correspondenceDate = new EDITDate(feeCorrespondenceVO.getCorrespondenceDate());

                    correspondenceDateString = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(correspondenceDate.getFormattedDate());
                }

                correspondenceDateString = Util.initString(correspondenceDateString, "");
                notificationAmount = feeCorrespondenceVO.getNotificationAmount().toString();
          %>
          <tr class="default">
            <td width="25%">
              <%= addressTypeCT %>
            </td>
            <td width="25%">
              <%= statusCT %>
            </td>
            <td width="25%">
              <%= correspondenceDate %>
            </td>
            <td width="25%">
              <%= notificationAmount %>
            </td>
          </tr>
          <%
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
            <input type="button" name="close" value="Close" onClick="window.close();">
        </td>
      </tr>
    </table>

</body>
</html>
