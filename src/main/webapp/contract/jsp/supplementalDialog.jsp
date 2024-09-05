<!--
 * User: cgleason
 * Date: Oct 19, 2005
 * Time: 3:07:57 PM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 fission.beans.*,
                 fission.utility.*"%>

<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
	PageBean formBean       = contractMainSessionBean.getPageBean("formBean");

    String suppOriginalContractNumber = Util.initString(formBean.getValue("suppOriginalContractNumber"), "");
 
    Set contractNumbers = (Set)session.getAttribute("suppContractNumbers");



%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Supplemental Contract Information</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script>

    var f = null;


    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

    }

    function closeDialog()
    {
        window.close();
    }



</script>
<%-- ****************************** END JavaScript ****************************** --%>
<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1; background-color:#DDDDDD">

<form  name="contractClientAddForm" method="post" action="/PORTAL/servlet/RequestManager">
<%--<body class="mainTheme" onLoad="init()">--%>
<%--<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">--%>

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
<%--    BEGIN Form Content --%>
    <tr>
        <td nowrap align="left">Original Contract Number:
            <input disabled type="text" name="suppOriginalContractNumber" maxlength="15" size="15" value="<%= suppOriginalContractNumber %>">
        </td>
    </tr>

<%--    END Form Content --%>

    <tr class="filler">
        <td colspan="4">
            &nbsp; <!--Filler Row -->
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<br>


<%-- ****************************** BEGIN Summary Area ****************************** --%>

  <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th align="left">Supplemental Contract Number </th>
    </tr>
  </table>
 <span id="span2" class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:30%; top:0; left:0; background-color:#BBBBBB">
   <table class="summary" id="selectedClientSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">

      <%
          if (contractNumbers != null)
          {
              for (Iterator iterator = contractNumbers.iterator(); iterator.hasNext();)
              {
                   String suppContractNumber = (String)iterator.next();
                   String className = "default";
                   String selected = "false";

        %>
      <tr class="<%= className %>" isSelected="<%= selected %>"
          onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" >
        <td nowrap>
          <%= suppContractNumber %>
        </td>
      </tr>
        <%
              }
          }
      %>
  </table>
</span>
<%-- ****************************** END Summary Area ****************************** --%>
<%--Buttons--%>
<span id="span4" style="position:relative; width:100%; height:10%; top:0; left:0; z-index:0">
  <table width="100%">
    <tr>
      <td align="right">
        <input type="button" value="Close" onClick="closeDialog()">
      </td>
    </tr>
  </table>
</span>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>