<!--
 * User: sdorman
 * Date: Dec 2, 2004
 * Time: 1:22:42 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*" %>

<%@ taglib uri="/WEB-INF/SecurityTaglib.tld" prefix="security" %>

<%
    String[] responseMessages = (String[]) request.getAttribute("responseMessages");

    String responseMessage = "";

    //  Concatenate the array of messages into 1 string separated by carriage return for display in the Alert box
    if (responseMessages != null)
    {
        for (int i = 0; i < responseMessages.length; i++)
        {
            responseMessage = responseMessage.concat(responseMessages[i] + "\\n");
        }
    }
%>

<html>
<head>
<title>EDITSolutions - Import New Business</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="JavaScript">

    var responseMessage = "<%= responseMessage %>";
    var f = null;

	function init()
    {
        f = document.FileForm;
        checkForResponseMessage();
    }

    function importNewBusiness()
    {
        var width = .99 * screen.width;
        var height = .99 * screen.height;

        if (f.companyStructureId.value == "" ||
            f.companyStructureId.value == "0")
        {
            alert("Please Select Company Structure For Import");
        }
        else
        {
            openDialog("importResponseDialog", "top=0, left=0, resizable=no", width, height);

            f.target = "importResponseDialog";

            f.submit();
        }
    }

</script>
</head>

<body  bgColor="#99BBBB"  onLoad="init()">

<span style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">

<form name="FileForm" method="post" action="/PORTAL/servlet/RequestManager?transaction=QuoteDetailTran&action=importNewBusiness" enctype="multipart/form-data">
  <table width="90%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="right" nowrap>Select File for Import:&nbsp;</td>
      <td align="left" nowrap>
        <input type="file" name="fileName" accept="text">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap colspan="2">&nbsp;</td>
    </tr>
    <tr>
      <td align="right" nowrap>Select Company Key for Import:&nbsp;</td>
      <td align="left" nowrap>
        <security:selectForUserCompanyStructures
                name="companyStructureId"
        />
      </td>
    </tr>
    <tr>
      <td align="right" nowrap colspan="2">&nbsp;</td>
    </tr>
    <tr>
      <td align="right" nowrap colspan="2">
        <input type="button" name="import" value="Import" onClick="importNewBusiness()">
      </td>
    </tr>
  </table>

<!-- ****** Hidden Values ***** //-->
	<input type="hidden" name="transaction">
	<input type="hidden" name="action">

</form>
</span>
</body>
</html>