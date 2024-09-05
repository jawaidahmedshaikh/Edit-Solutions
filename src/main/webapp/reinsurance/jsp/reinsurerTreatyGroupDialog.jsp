<%--
  Created by IntelliJ IDEA.
  User: gfrosti
  Date: Nov 19, 2004
  Time: 10:57:08 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="fission.utility.*,
                 edit.common.vo.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");
    
    TreatyGroupVO activeTreatyGroupVO = (TreatyGroupVO) request.getAttribute("activeTreatyGroupVO");

    String activeTreatyGroupPK = null;

    String groupNumber = "";

    if (activeTreatyGroupVO != null)
    {
        activeTreatyGroupPK = String.valueOf(activeTreatyGroupVO.getTreatyGroupPK());

        groupNumber = Util.initString(activeTreatyGroupVO.getTreatyGroupNumber(), "");
    }

    TreatyGroupVO[] treatyGroupVOs = (TreatyGroupVO[]) request.getAttribute("treatyGroupVOs");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Treaty Group Dialog</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script>

    var f = null;
    
    var responseMessage = "<%= responseMessage %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        f.groupNumber.focus();

        checkForResponseMessage();
    }

    /**
     * Prepares for the adding of a new Treaty Group.
     */
    function addTreatyGroup()
    {
        sendTransactionAction("ReinsuranceTran", "addTreatyGroup", "_self");
    }

    /**
     *  Saves the entered Group Number.
     */
    function saveTreatyGroup()
    {
        var groupNumber = f.groupNumber.value;

        if (valueIsEmpty(groupNumber))
        {
            alert("Group Number Required");
        }
        else
        {
            sendTransactionAction("ReinsuranceTran", "saveTreatyGroup", "_self");
        }
    }

    /**
     * Cancels and current Treaty Group edits.
     */
    function cancelTreatyGroup()
    {
        sendTransactionAction("ReinsuranceTran", "cancelTreatyGroup", "_self");
    }

    /**
     * Shows the Treaty Group Details of the selected row.
     */
    function showTreatyGroupDetails()
    {
        var selectedRowId = getSelectedRowId("treatyGroupSummary");

        f.activeTreatyGroupPK.value = selectedRowId;

        sendTransactionAction("ReinsuranceTran", "showTreatyGroupDetails", "_self");
    }

    /**
     *  Deletes the selected Treaty Group.
     */
    function deleteTreatyGroup()
    {
        sendTransactionAction("ReinsuranceTran", "deleteTreatyGroup", "_self");
    }

    /**
     * Loads the reinsurerPage with the updated TreatyGroup list.
     */
    function closeTreatyGroupDialog()
    {
        sendTransactionAction("ReinsuranceTran", "showReinsurerTreaty", "contentIFrame");

        closeWindow();
    }
</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" height="10%" border="0" cellspacing="0" cellpadding="5">
    <tr height="50%">
        <td colspan="2">
            &nbsp; <!--Filler Row -->
        </td>
    </tr>

<%--    BEGIN Form Content --%>
    <tr>
        <td align="right" nowrap width="50%">
            Treaty Group Number:
        </td>
        <td align="left" nowrap width="50%">
            <input type="text" name="groupNumber" value="<%= groupNumber %>" size="20" maxlength="50" onKeyPress="if (enterKeyPressed()){saveTreatyGroup()}">
        </td>
    </tr>
<%--    END Form Content --%>
    
    <tr height="50%">
        <td colspan="2">
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
        <td align="left" width="33%" nowrap>
            <input type="button" value="  Add " onClick="addTreatyGroup()">
            <input type="button" value=" Save " onClick="saveTreatyGroup()">
            <input type="button" value="Cancel" onClick="cancelTreatyGroup()">
            <input type="button" value="Delete" onClick="deleteTreatyGroup()">
        </td>
        <td width="33%" nowrap>
            <span class="tableHeading">Treaty Group Summary</span>
        </td>
        <td align="right" width="33%" nowrap>
            &nbsp;
        </td>
    </tr>
</table>

<%-- Column Headings --%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="100%" nowrap>
            Treaty Group Number
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:63%; top:0; left:0;">
    <table id="treatyGroupSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
    if (treatyGroupVOs != null) // Test for the existence of the target VOs.
    {
        treatyGroupVOs = (TreatyGroupVO[]) Util.sortObjects(treatyGroupVOs, new String[]{"getTreatyGroupNumber"});

        for (int i = 0; i < treatyGroupVOs.length; i++) // Loop through the target VOs.
        {
            String currentTreatyGroupPK = String.valueOf(treatyGroupVOs[i].getTreatyGroupPK());

            String currentTreatyGroupNumber = treatyGroupVOs[i].getTreatyGroupNumber();

            boolean isSelected = false;
            
            boolean isAssociated = false;

            String className = null;

            if (currentTreatyGroupPK.equals(activeTreatyGroupPK))
            {
                isSelected = true;
                
                className = "highlighted";
            }
            else
            {
                className = "default";
            }
%>
        <tr class="<%= className %>" id="<%= currentTreatyGroupPK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false);showTreatyGroupDetails()">
            <td>
                <%= currentTreatyGroupNumber %>
            </td>
        </tr>
<%
        }// end for
    } // end if
%>      
        <tr class="filler">
            <td colspan="1">
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
            <input type="button" value="Cancel" onClick="closeTreatyGroupDialog()">
        </td>
    </tr>
</table>
<%-- ****************************** END Buttons ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="activeTreatyGroupPK" value="<%= activeTreatyGroupPK %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>