<!-- taxAdjustmentDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 fission.utility.Util" %>


<jsp:useBean id="contractTaxesSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
    SessionBean contractMainSessionBean = (SessionBean) session.getAttribute("contractMainSessionBean");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    String companyStructureKey = Util.initString(contractMainSessionBean.getPageBean("formBean").getValue("companyStructureId"), "0");

	CodeTableVO[] distributions = codeTableWrapper.getCodeTableEntries("DISTRIBUTIONCODE", Long.parseLong(companyStructureKey));

//	PageBean formBean      = contractMainSessionBean.getPageBean("historyFormBean");

//	String costBasis = formBean.getValue("costBasis");
//    String taxYear = formBean.getValue("taxYear");
//    String distributionCode = formBean.getValue("calculatedDistCode");
    // sprasad 8/22/06
    // Since we are moving away from storing data in httpSession, I am getting the values from request scope.
    String costBasis = request.getParameter("costBasis");
    String taxYear = request.getParameter("taxYear");
    String distributionCode = request.getParameter("calculatedDistCode");
    distributionCode = codeTableWrapper.getCodeByCodeTableNameAndCodeDesc("DISTRIBUTIONCODE", distributionCode);    
%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.taxAdjustmentForm;

		window.resizeTo(getPreferredWidth(), getPreferredHeight());

        formatCurrency();
	}

	function getPreferredWidth()
    {
		return .75 * document.all.table1.offsetWidth;
	}

	function getPreferredHeight()
    {
		return 1.75 * document.all.table1.offsetHeight;
	}

	function saveTaxes()
    {
        sendTransactionAction("ContractDetailTran", "saveDisburseTaxAdjustment", "contentIFrame");
		window.close();
	}
</script>

<title>Taxes</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()" bgcolor="#DDDDDD">
<form name="taxAdjustmentForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="80%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
       <td nowrap align="left">Tax Year:&nbsp;&nbsp;
        <input type="text" name="taxYear" tabindex="4" value= "<%= taxYear %>">
    </tr>
    <tr>
      <td>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td nowrap align="left">Cost Basis:&nbsp;
        <input type="text" name="costBasis" tabindex="4" value= "<%= costBasis %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td nowrap align="left">Distribution Code:&nbsp;
        <select name="distributionCode">
          <%
              out.println("<option>Please Select</option>");

              for(int i = 0; i < distributions.length; i++) {

                  String codeTablePK = distributions[i].getCodeTablePK() + "";
                  String codeDesc    = distributions[i].getCodeDesc();
                  String code        = distributions[i].getCode();

                  if (code.equalsIgnoreCase(distributionCode)) {

                      out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
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
      <td>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td width="57%" align="right" nowrap>
        <input type="button" name="enter" value="Enter" onClick="saveTaxes()">
        <input type="button" name="enter" value="Cancel" onClick="closeWindow()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">

</form>
</body>
</html>
