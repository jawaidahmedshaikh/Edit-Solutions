<%--
  Created by IntelliJ IDEA.
  User: gfrosti
  Date: Nov 12, 2004
  Time: 2:44:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="fission.utility.*,
                 edit.common.vo.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    ClientDetailVO[] clientDetailVOs = (ClientDetailVO[]) request.getAttribute("clientDetailVOs");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Reinsurer Add</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

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

        f.partialCorporateName.focus();
    }

    /**
     * Loads the selected client info into the reinsurer detail page.
     */
    function showReinsurerDetailAfterAdd()
    {
        var selectedId = getSelectedRowId("clientSummary");

        f.clientDetailPK.value = selectedId;

        sendTransactionAction("ReinsuranceTran", "showReinsurerDetailAfterAdd", "contentIFrame");

		opener.top.frames["main"].setActiveImage("reinsurer");

        closeWindow();
    }

    /**
     * Clears the specified text-field.
     */
    function clearTextField(textFieldId)
    {
        document.getElementById(textFieldId).value = "";
    }

    /**
     * Finds the client by id, if any.
     */
    function findClientByTaxId()
    {
        var elementIsEmpty = textElementIsEmpty(f.taxIdentificationNumber);

        if (elementIsEmpty)
        {
            alert("Tax Id Required");
        }
        else
        {
            sendTransactionAction("ReinsuranceTran", "findClientByTaxId", "_self");
        }
    }

    /**
     * Finds the client by partial corporate name, if any.
     */
    function findClientsByPartialCorporateName()
    {
        var elementIsEmpty = textElementIsEmpty(f.partialCorporateName);

        if (elementIsEmpty)
        {
            alert("Corporate Name Required");
        }
        else
        {
            sendTransactionAction("ReinsuranceTran", "findClientsByPartialCorporateName", "_self");
        }
    }

    /**
     * Finds clients by taxId or partial corporate name depending on which is supplied.
     */
    function findClients()
    {
        if (!valueIsEmpty(f.taxIdentificationNumber.value))
        {
            findClientByTaxId();
        }
        else if (!valueIsEmpty(f.partialCorporateName.value))
        {
            findClientsByPartialCorporateName();
        }
        else
        {
            alert("Tax Id or Partial Corporate Name Required");
        }
    }


</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" height="25%" border="0" cellspacing="0" cellpadding="5">
    <tr height="50%">
        <td colspan="4">
            &nbsp; <!--Filler Row -->
        </td>
    </tr>

<%--    BEGIN Form Content --%>
    <tr>
        <td align="right" nowrap>
            Corporate Name:
        </td>
        <td align="left" nowrap>
            <input type="text" id="partialCorporateName" name="partialCorporateName" size="25" maxlength="25"  onFocus="clearTextField('taxIdentificationNumber')" onKeyPress="if (enterKeyPressed()){findClientsByPartialCorporateName()}">
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap width="25%">
            Tax Id Number:
        </td>
        <td align="left" nowrap width="25%">
            <input type="text" id="taxIdentificationNumber" name="taxIdentificationNumber" size="25" maxlength="25" onFocus="clearTextField('partialCorporateName')" onKeyPress="if (enterKeyPressed()){findClientByTaxId()}">
        </td>
        <td align="right" nowrap width="25%">
            &nbsp;
        </td>
        <td align="left" nowrap width="25%">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="right" nowrap>
            <input type="button" value="Enter " onClick="findClients()">
        </td>
    </tr>
<%--    END Form Content --%>
    
    <tr height="50%">
        <td colspan="4">
            &nbsp; <!--Filler Row -->
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<br>

<%-- ****************************** BEGIN Summary Area ****************************** --%>
<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" width="33%">
            &nbsp;
        </td>
        <td width="33%">
            <span class="tableHeading">Search Results</span>
        </td>
        <td align="right" width="33%">
            &nbsp;
        </td>
    </tr>
</table>

<%--Header--%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="50%">
            Corporate Name
        </td>
        <td width="50%">
            Tax Id
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:50%; top:0; left:0;">
    <table id="clientSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
    if (clientDetailVOs != null)
    {
        for (int i = 0; i < clientDetailVOs.length; i++) // Loop through the target VOs.
        {
            String currentClientDetailPK = String.valueOf(clientDetailVOs[i].getClientDetailPK());

            String corporateName = Util.initString(clientDetailVOs[i].getCorporateName(), "(Not Available)");

            String taxId = Util.initString(clientDetailVOs[i].getTaxIdentification(), "&nbsp;");

            boolean isSelected = false;
            
            boolean isAssociated = false;

            String className = "default";
%>
        <tr class="<%= className %>" id="<%= currentClientDetailPK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false)" onDblClick="showReinsurerDetailAfterAdd()">
            <td width="50%" nowrap>
                <%= corporateName %>
            </td>
            <td width="50%" nowrap>
                <%= taxId %>
            </td>
        </tr>
<%
        }// end for
    } // end if
%>      
        <tr class="filler">
            <td colspan="2">
                &nbsp;
            </td>
        </tr>
    </table>
</span>
<%-- ****************************** END Summary Area ****************************** --%>

<br>

<%-- ****************************** BEGIN Buttons ****************************** --%>
<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right">
            <input type="button" value="Cancel" onClick="closeWindow()">
        </td>
    </tr>
</table>
<%-- ****************************** END Buttons ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="clientDetailPK" value="">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>