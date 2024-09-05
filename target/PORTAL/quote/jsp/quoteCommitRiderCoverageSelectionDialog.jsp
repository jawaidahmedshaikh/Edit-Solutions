<!-- quoteCommitRiderCoverageSelectionDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.*,
                 edit.common.*,
                 fission.utility.*" %>

<jsp:useBean id="formBean"
    class="fission.beans.PageBean" scope="request"/>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    String companyStructureId = Util.initString(quoteMainSessionBean.getPageBean("formBean").getValue("companyStructureId"), "0");

    CodeTableVO[] optionCodes  = codeTableWrapper.getCodeTableEntries("RIDERNAME", Long.parseLong(companyStructureId));

    String riderOption = formBean.getValue("selectedCoverage");

%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init()
    {

		f = document.coverageTypeForm;

<%--		window.resizeTo(getPreferredWidth(), getPreferredHeight());--%>
	}

	function getPreferredWidth()
    {

		return 1.25 * document.all.table1.offsetWidth;
	}

	function getPreferredHeight()
    {

		return 1.50 * document.all.table1.offsetHeight;
	}


	function closeDialog()
    {
		window.close();
	}

	function save()
    {
	    sendTransactionAction("QuoteDetailTran", "saveRiderCoverage", "contentIFrame");

		window.close();
	}

</script>

<title>Rider Coverage Selection</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="coverageTypeForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="51%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
	   <td nowrap>Coverage:
	    <select name="selectedCoverage">
	      <option>Please Select</option>
	        <%
              for(int i = 0; i < optionCodes.length; i++) {

                  String codeTablePK = optionCodes[i].getCodeTablePK() + "";
                  String code =   optionCodes[i].getCode();
                  String codeDesc = optionCodes[i].getCodeDesc();

                  if (riderOption.equalsIgnoreCase(code)) {

                      out.println("<option selected name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                  }
                  else  {

                      out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                  }
              }
	        %>
	    </select>
	  </td>
    </tr>
    <tr>
      <td colspan="3" align="center">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="3" align="right" valign="bottom" nowrap>
        <input type="button" name="enter" value="Enter" onClick="save()">
        <input type="button" name="cancel" value="Close" onClick="closeDialog()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">

</form>
</body>
</html>
