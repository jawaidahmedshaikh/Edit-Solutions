<!--
 * User: cgleason
 * Date: Jan 30, 2008
 * Time: 3:07:16 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%@ page import="edit.common.vo.CodeTableVO,
                 codetable.dm.dao.CodeTableDAO,
                 edit.common.CodeTableWrapper,
                 client.ClientDetail,
                 fission.utility.Util,
                 edit.common.EDITDate,
                 fission.beans.*,
                 edit.portal.common.session.UserSession"%>

<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");
	PageBean formBean       = contractMainSessionBean.getPageBean("formBean");
    String companyStructureId = Util.initString(formBean.getValue("companyStructureId"), "0");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] nfoOptions = codeTableWrapper.getCodeTableEntries("NONFORFEITUREOPTION", Long.parseLong(companyStructureId));
    String nfoOption = formBean.getValue("nonForfeitureOption");

    CodeTableVO[] deathBenefitOptions = codeTableWrapper.getCodeTableEntries("DEATHBENOPT", Long.parseLong(companyStructureId));
    String deathBeneOption           = formBean.getValue("deathBeneOption");

    UserSession userSession = (UserSession) session.getAttribute("userSession");

%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - NFO/DBO Change</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>


<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";

    var editableContractStatus = true;

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;
        
     	// check for terminated status to disallow contract updates
        editableContractStatus = <%= userSession.getEditableSegmentStatus()%>;

        checkForResponseMessage();
    }


    function saveNFODBOChangeDialog()
    {
    	if (editableContractStatus == true)
		{
	        sendTransactionAction("ContractDetailTran", "saveNFODBOChangeDialog", "contentIFrame");
	        window.close();
	        
		} else {
			alert("This Contract Cannot Be Edited Due to Terminated Status.");
		}
    }

    function closeAndShowContractMainPage()
    {
        window.close();
    }




</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
<%--    BEGIN Form Content --%>

    <tr>
      <td nowrap align="left">Nonforfeiture Option:&nbsp;
        <select name="nonForfeitureOption">
          <option> Please Select </option>
          <%

              for(int i = 0; i < nfoOptions.length; i++)
              {

                  String codeTablePK = nfoOptions[i].getCodeTablePK() + "";
                  String codeDesc    = nfoOptions[i].getCodeDesc();
                  String code        = nfoOptions[i].getCode();

                 if (nfoOption.equalsIgnoreCase(code))
                 {

                     out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                 }
                 else
                 {

                     out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                 }
              }
		   %>
        </select>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Death Benefit Option:&nbsp;
	    <select name="deathBeneOption">
          <option>Please Select</option>
         	<%
              for(int i = 0; i < deathBenefitOptions.length; i++)
              {
                  String codeTablePK = deathBenefitOptions[i].getCodeTablePK() + "";
                  String codeDesc    = deathBenefitOptions[i].getCodeDesc();
                  String code        = deathBenefitOptions[i].getCode();

                  if (deathBeneOption.equalsIgnoreCase(code))
                  {
                      out.println("<option selected name=\"id\" value=\"" + code+ "\">" + codeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
              }
         	%>
        </select>
      </td>
     </tr>
<%--    END Form Content --%>

    <tr>
      <td align="right">
            <input id="btnSave" type="button" value=" Save " onClick="saveNFODBOChangeDialog()">
            <input id="btnCancel" type="button" value=" Cancel " onClick="closeAndShowContractMainPage()">
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="deathBeneOption" value="<%= deathBeneOption %>">
<input type="hidden" name="nonForfeitureOption" value="<%= nfoOption %>">

<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>