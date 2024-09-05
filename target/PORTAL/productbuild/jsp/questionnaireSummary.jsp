<!--
 *
 * User: cgleason
 * Date: Jun 20, 2007
 * Time: 10:21:45 AM
 *
 * (c) 2000 - 2007 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 -->

<%@ page import="edit.services.db.hibernate.*,
                 fission.utility.Util,
                 productbuild.*,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.CodeTableVO"%>

 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    Questionnaire questionnaire = (Questionnaire) request.getAttribute("activeQuestionnaire");

    long activeQuestionnairePK = Util.initLong(questionnaire, "questionnairePK", 0L);
    String questionnaireId   = (String) Util.initObject(questionnaire, "questionnaireId", "");
    String questionnaireDescription = (String) Util.initObject(questionnaire, "questionnaireDescription", "");
    int followupDays         = Util.initInt(questionnaire, "followupDays", 0);
    String manualInd         = (String) Util.initObject(questionnaire, "manualInd", "");
    String areaCT = (String) Util.initObject(questionnaire, "areaCT", "");
    String manualIndStatus   = "unchecked";

    if (manualInd.equalsIgnoreCase("Y"))
    {
        manualIndStatus = "checked";
    }

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] states = codeTableWrapper.getCodeTableEntries("STATE");

%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Questionnaire Table</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>

<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        checkForResponseMessage();

        // Initialize scroll tables
        initScrollTable(document.getElementById("QuestionnaireTableModelScrollTable"));
    }

    function onTableRowSingleClick(tableId)
    {
        sendTransactionAction("ProductBuildTran", "showQuestionnaireDetail", "main");
    }

	function addQuestionnaire()
    {
  		sendTransactionAction("ProductBuildTran", "addQuestionnaire", "main");
    }

	function saveQuestionnaire()
    {
       try
       {
 		    sendTransactionAction("ProductBuildTran", "saveQuestionnaire", "main");
       }
       catch (e)
       {
           alert(e);
       }

	}

	function cancelQuestionnaire()
    {
  		sendTransactionAction("ProductBuildTran", "cancelQuestionnaire", "main");
    }


	function deleteQuestionnaire()
    {
  		sendTransactionAction("ProductBuildTran", "deleteQuestionnaire", "main");
    }

    function showQuestionnaireRelation()
    {
        sendTransactionAction("ProductBuildTran", "showQuestionnaireRelation", "main");
    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
  <table class="formData" width="100%" border="0" cellspacing="6" cellpadding="0">
    <tr>
      <td align="left" nowrap>Questionnaire Id:&nbsp;
              <input type="text" name="questionnaireId" size="5" maxlength="5" value="<%= questionnaireId %>">
      </td>
      <td align="left" nowrap>Description:&nbsp;
        <input type="text" name="questionnaireDescription" size="75" maxlength="250" value="<%= questionnaireDescription %>">
      </td>
	  <td nowrap align="left">Area:&nbsp;
        <select name="areaCT">
          <option> Please Select </option>
            <%
              for(int i = 0; i < states.length; i++) {

                  String codeTablePK = states[i].getCodeTablePK() + "";
                  String codeDesc    = states[i].getCodeDesc();
                  String code        = states[i].getCode();

                  if (areaCT.equalsIgnoreCase(code))
                  {
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
      <td align="left" nowrap>Followup Days:&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="text" name="followupDays" size="3" maxlength="3" value="<%= followupDays %>">
        &nbsp;&nbsp;
      </td>
      <td align="left" nowrap colspan="2">Manual:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="checkbox" name="manualIndStatus" <%= manualIndStatus %> >
      </td>
    </tr>
  </table>
<%-- ****************************** END Form Data ****************************** --%>

  <br>
  <br>
  <br>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="left">
        <input type="button" name="add" value= " Add  " onClick="addQuestionnaire()">
	    <input type="button" name="save" value=" Save " onClick="saveQuestionnaire()">
	    <input type="button" name="cancel" value="Cancel" onClick="cancelQuestionnaire()">
	    <input type="button" name="delete" value="Delete" onClick="deleteQuestionnaire()">
	  </td>
      <td width="33%" align="center">
        <span class="tableHeading">Questionnaire Summary</span>
      </td>
      <td align="right" width="33%">
        <input type="button" value="Questionnaire Relation" onClick="showQuestionnaireRelation()">
      </td>
	</tr>
  </table>

<%-- ****************************** BEGIN Summary Questionnaire ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="QuestionnaireTableModel"/>
    <jsp:param name="tableHeight" value="60"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Questionnaire ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
 <input type="hidden" name="questionnairePK" value="<%= activeQuestionnairePK %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>