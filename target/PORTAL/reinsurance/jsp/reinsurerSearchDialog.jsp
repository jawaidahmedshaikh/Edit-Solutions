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
    ReinsurerVO[] reinsurerVOs = (ReinsurerVO[]) request.getAttribute("reinsurerVOs");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Reinsurer Search</title>
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

        f.corporateName.focus();
    }

    /**
     * Loads the selected client info into the reinsurer detail page.
     */
    function showReinsurerDetailAfterSearch()
    {
        var selectedId = getSelectedRowId("resultsSummary");

        f.reinsurerPK.value = selectedId;

        sendTransactionAction("ReinsuranceTran", "showReinsurerDetailAfterSearch", "contentIFrame");

        opener.top.frames["main"].setActiveImage("reinsurer");

        closeWindow();
    }

    /**
     * Searches by direct match against the TaxId.
     */
    function findReinsurerByTaxIdentification()
    {
        if (textElementIsEmpty(f.taxIdentification))
        {
            alert("Tax Identification Required");
        }
        else
        {
            sendTransactionAction("ReinsuranceTran", "findReinsurerByTaxIdentification", "_self");
        }
    }

    /**
     * Searches by direct match against the ReinsurerNumber.
     */
    function findReinsurerByReinsurerNumber()
    {
        if (textElementIsEmpty(f.reinsurerNumber))
        {
            alert("Reinsurer Number Required");
        }
        else
        {
            sendTransactionAction("ReinsuranceTran", "findReinsurerByReinsurerNumber", "_self");
        }
    }

    /**
     * Searches by partial match of the corporate name.
     */
    function findReinsurerByPartialCorporateName()
    {
        if (textElementIsEmpty(f.corporateName))
        {
            alert("Corporate Name Required");
        }
        {
            sendTransactionAction("ReinsuranceTran", "findReinsurerByPartialCorporateName", "_self");
        }
    }

    /**
     * Convenience method to narrow by CorporateName, TaxId, or ReinsurerId.
     */
    function findReinsurers()
    {
        if (!textElementIsEmpty(f.corporateName))
        {
            findReinsurerByPartialCorporateName();
        }
        else if (!textElementIsEmpty(f.taxIdentification))
        {
            findReinsurerByTaxIdentification();
        }
        else if (!textElementIsEmpty(f.reinsurerNumber))
        {
            findReinsurerByReinsurerNumber();
        }
        else
        {
            alert("Search Parameter Required");
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
            <input type="text" name="corporateName" size="25" maxlength="25" onKeyPress="if (enterKeyPressed()){findReinsurerByPartialCorporateName()}" onFocus="clearTextElement(f.reinsurerNumber);clearTextElement(f.taxIdentification)">
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap width="20%">
            Reinsurer Number:
        </td>
        <td align="left" nowrap width="20%">
            <input type="text" name="reinsurerNumber" size="25" maxlength="15" onKeyPress="if (enterKeyPressed()){findReinsurerByReinsurerNumber()}" onFocus="clearTextElement(f.corporateName);clearTextElement(f.taxIdentification)">
        </td>
        <td align="right" nowrap>
            Tax Identification #:
        </td>
        <td align="left" nowrap>
            <input type="text" name="taxIdentification" size="25" maxlength="25" onKeyPress="if (enterKeyPressed()){findReinsurerByTaxIdentification()}" onFocus="clearTextElement(f.reinsurerNumber);clearTextElement(f.corporateName)">
        </td>
        <td align="right" nowrap>
           <input type="button" value="Enter " onClick="findReinsurers()">
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
        <td width="33%">
            Corporate Name
        </td>
        <td width="33%">
            Reinsurer #
        </td>
        <td width="33%">
            Tax Identification #
        </td>
    </tr>
</table>

<%--Summary For the Reinsurer Results --%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:55%; top:0; left:0;">
    <table id="resultsSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
    if (reinsurerVOs != null) // Test for the existence of the target VOs.
    {
        client.business.Lookup clientLookup = new client.component.LookupComponent();

        for (int i = 0; i < reinsurerVOs.length; i++) // Loop through the target VOs.
        {
            long currentReinsurerPK = reinsurerVOs[i].getReinsurerPK();

            long currentClientDetailFK = reinsurerVOs[i].getClientDetailFK();

            ClientDetailVO currentClientDetailVO = clientLookup.findByClientPK(currentClientDetailFK, false, null)[0];

            String currentCorporateName = Util.initString(currentClientDetailVO.getCorporateName(), "&nbsp;");

            String currentReinsurerNumber = Util.initString(reinsurerVOs[i].getReinsurerNumber(), "&nbsp;");

            String currentTaxIdentification = Util.initString(currentClientDetailVO.getTaxIdentification(), "&nbsp;");

            boolean isSelected = false;

            boolean isAssociated = false;

            String className = "default";
%>
        <tr class="<%= className %>" id="<%= currentReinsurerPK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onDblClick="showReinsurerDetailAfterSearch()" onClick="selectRow(false)">
            <td width="33%" NOWRAP>
                <%= currentCorporateName %>
            </td>
            <td width="33%" NOWRAP>
                <%= currentReinsurerNumber %>
            </td>
            <td width="33%" NOWRAP>
                <%= currentTaxIdentification %>
            </td>
        </tr>
<%
        }// end for
    } // end if
%>
        <tr class="filler">
            <td colspan="3">
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
<input type="hidden" name="reinsurerPK">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>