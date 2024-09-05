<!--
 *
 * User: cgleason
 * Date: Jul 11, 2006
 * Time: 10:29:53 AM
 *
 * (c) 2000 - 2006 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use
 *  is subject to the license agreement.
 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="edit.portal.widgettoolkit.TableModel"%>
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page import="edit.portal.widget.*" %>
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*" %>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");
%>

<html>
<head>
<title>Export Selection</title>


<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script type="text/javascript">

    var responseMessage = "<%= responseMessage %>";

   	var f = null;

<%--	function init() {--%>
<%----%>
<%--		f = document.exportform;--%>
<%----%>
<%--        checkForResponseMessage();--%>
<%--    }--%>

    function showScriptList()
    {
        sendTransactionAction("FileExportTran", "showScriptList", "_self");
    }

    function showRulesList()
    {
        alert('TO BE Implemented');
    }


</script>
</head>

<%--<body  bgColor="#99BBBB"  onLoad="init()">--%>

<%--<span style="border-style:solid; border-width:1;  position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">--%>
<%--<form name="exportform" method="post" action="/PORTAL/servlet/RequestManager">--%>
<table width="100%" height="100%" border="0" height="296">
  <tr align="center" valign="middle">
    <td height="294" width="94%" align="top" valign="middle" class="unnamed1">
      <table width="100%" height="100%" border="0" align="center"  bgcolor="#DDDDDD">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
            <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
                 <a href="javascript:showScriptList()">Script Export</a>
	      </td>
        </tr>
        <tr>
            <td align="middle"><b><font face="Arial, Helvetica, sans-serif" size="4">
                 <a href="javascript:showRulesList()">Rules Export</a>
	      </td>
        </tr>

        <tr>
            <td height="50%">
                &nbsp;
            </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<!-- ****** Hidden Values ***** //-->
<%--	<input type="hidden" name="transaction">--%>
<%--	<input type="hidden" name="action">--%>

<%--</form>--%>
<%--</span>--%>
<%--</body>--%>
</html>
