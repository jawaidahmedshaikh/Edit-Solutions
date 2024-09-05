<!--
*
* User: cgleason
* Date: Jun 21, 2007
* Time: 9:11:21 AM
*
* (c) 2000 - 2007 Systems Engineering Group, LLC.  All rights Reserved
*  Systems Engineering Group, LLC Propietary and confidential.  Any use is
*  subject to the license agreement.
-->

<%@ page import="edit.common.vo.*,
                 edit.common.*,
                 fission.utility.*,
                 engine.business.*,
                 engine.component.*,
                 engine.*,
                 productbuild.*"%>


<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    ProductStructure[] productStructures = (ProductStructure[]) ProductStructure.findByTypeCodeCT("Product");

    String activeProductStructurePK = Util.initString((String) request.getAttribute("selectedRowIds_ProductStructureTableModel"), "0");
    String companyStructureString = (String) request.getAttribute("companyStructure");
    String activeQuestionnairePK = (String)request.getAttribute("activeQuestionnairePK");

    Questionnaire questionnaire = (Questionnaire)request.getAttribute("activeQuestionnaire");

    String questionnaireId = "";
    int displayOrder = 0;
    FilteredQuestionnaire filteredQuestionnaire = null;

    if (questionnaire != null)
    {
        questionnaireId = questionnaire.getQuestionnaireId();
        filteredQuestionnaire = questionnaire.getFilteredQuestionnaire();
        if (filteredQuestionnaire != null)
        {
            displayOrder = filteredQuestionnaire.getDisplayOrder();
        }
    }



%>
<%-- ****************************** End Java Code ****************************** --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>
            Questionnaire Relations
        </title>
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

        f.cloneButton.disabled = true;

        // Initialize scroll tables
        initScrollTable(document.getElementById("CloneQuestionnaireTableModelScrollTable"));
    }

    function enableDisableSelection()
    {
        var select = window.event.srcElement;

        if (select.selectedIndex == 0) // If "Please Select"
        {
            f.attachButton.disabled = false;
            f.detachButton.disabled = false;
            f.cloneButton.disabled = true;
        }
        else
        {
            f.attachButton.disabled = true;
            f.detachButton.disabled = true;
            f.cloneButton.disabled = false;
        }
    }

    function onTableRowSingleClick(tableId)
    {
        sendTransactionAction("ProductBuildTran", "showSelectedQuestionnaire", "_self");
    }


    /**
     * Associates the selected Questionnaires to the selected CompanyStructure.
     */
    function attachQuestionnaireToProductStructure()
    {
        if (f.displayOrder.value == 0)
        {
            alert("Display Order Required");
            return;
        }

        sendTransactionAction("ProductBuildTran", "attachQuestionnaireToProductStructure", "buildQuestionnaireDialog");
    }

    /**
     * Removes the association between the selected Questionnaires and the selected ProductStructure.
     */
    function detachQuestionnaireFromProductStructure()
    {
        sendTransactionAction("ProductBuildTran", "detachQuestionnaireFromProductStructure", "buildQuestionnaireDialog");
    }

    /**
     * Clones the active ProductStructures Questionnaires to the selected ProductStructure.
     */
    function cloneQuestionnaires()
    {
        if (f.cloneToProductStructurePK.value == "null")
        {
            alert("Clone To Product Structure Required");
        }
        else
        {
            sendTransactionAction("ProductBuildTran", "cloneQuestionnaires", "_self");
        }
    }

    /**
     * Closes this dialog.
     */
    function cancelDialog()
    {
        window.close();

        sendTransactionAction("ProductBuildTran", "showQuestionnaireRelation", "main");

    }
</script>
<%-- ****************************** END JavaScript ****************************** --%>

    </head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%--Header--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right" nowrap>Active Company Structure:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled type="text" size="50" maxlength="50" name="companyStructure" value="<%= companyStructureString %>">
    </td>
    <td align="right" nowrap>Clone To:&nbsp;</td>
    <td align="left" nowrap>
      <select name="cloneToProductStructurePK" onChange="enableDisableSelection()">
        <option name="id" value="0">Please Select</option>
        <%
            if (productStructures != null)
            {
                for(int i = 0; i < productStructures.length; i++)
                {
                    String cloneProductStructurePK = productStructures[i].getProductStructurePK() + "";
                    Company company = Company.findByPK(productStructures[i].getCompanyFK());

                    String cloneCompany = company.getCompanyName();
                    String cloneMarketingPackage = productStructures[i].getMarketingPackageName();
                    String cloneGroupProduct = productStructures[i].getGroupProductName();
                    String cloneArea = productStructures[i].getAreaName();
                    String cloneBusinessContract = productStructures[i].getBusinessContractName();
                    String cloneProductStructure = cloneCompany + ", " + cloneMarketingPackage + "," +
                                              cloneGroupProduct + ", " + cloneArea + ", " + cloneBusinessContract;

                    out.println("<option name=\"id\" value=\"" + cloneProductStructurePK + "\">" + cloneProductStructure + "</option>");
                }
            }
        %>
      </select>
    </td>
  </tr>
  <tr>
      <td align="right" nowrap>Questionnaire Id:&nbsp;</td>
      <td align="left" nowrap>
              <input type="text" name="questionnaireId" size="5" maxlength="5" value="<%= questionnaireId %>">
      </td>
      <td align="right" nowrap>Display Order:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="displayOrder" size="5" maxlength="5" value="<%= displayOrder %>">
      </td>
  </tr>
</table>

<br>

<%-- ****************************** BEGIN Summary Questionnaire ****************************** --%>

<span class="tableHeading">Questionnaire Summary</span>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
  <jsp:param name="tableId" value="CloneQuestionnaireTableModel"/>
  <jsp:param name="tableHeight" value="60"/>
  <jsp:param name="multipleRowSelect" value="false"/>
  <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Questionnaire ****************************** --%>

<br><br>

<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right">
            <input type="button" name="cloneButton" value=" Clone " onClick="cloneQuestionnaires()">
            <input type="button" name="attachButton" value="Attach" onClick="attachQuestionnaireToProductStructure()">
            <input type="button" name="detachButton" value="Detach" onClick="detachQuestionnaireFromProductStructure()">
            <input type="button" name="cancelButton" value="Cancel" onClick="cancelDialog()">
        </td>
    </tr>
</table>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">

<input type="hidden" name="cloneToProductStructurePK" value="null">
<input type="hidden" name="questionnairePKs" value="">
<input type="hidden" name="selectedRowIds_ProductStructureTableModel" value="<%= activeProductStructurePK %>"/>
<input type="hidden" name="activeQuestionnairePK" value="<%= activeQuestionnairePK %>">
<input type="hidden" name="companyStructure" value="<%= companyStructureString %>">

<%-- ****************************** END Hidden Variables ****************************** --%>
</form>
</body>

</html>