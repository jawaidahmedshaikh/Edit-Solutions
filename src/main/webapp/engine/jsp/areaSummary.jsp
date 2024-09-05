<!--
 * User: gfrosti
 * Date: Nov 3, 2004
 * Time: 12:46:16 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%@ page import="edit.common.vo.*,
                 edit.common.*,
                 fission.utility.*"%>


<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    CodeTableVO[] areaCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("STATE");
    CodeTableVO[] areaQualifierCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("AREAQUALIFIER");

    AreaValueVO[] areaValueVOs = (AreaValueVO[]) request.getAttribute("areaValueVOs");

    AreaKeyVO[] areaKeyVOs = (AreaKeyVO[]) request.getAttribute("areaKeyVOs");
    
    String activeAreaKeyPK = Util.initString((String) request.getAttribute("activeAreaKeyPK"), "");

    String activeAreaValuePK = Util.initString((String) request.getAttribute("activeAreaValuePK"), "");

    AreaValueVO areaValueVO = (AreaValueVO) request.getAttribute("areaValueVO");

    String effectiveDate = "";
    String effectiveMonth = "";
    String effectiveDay = "";
    String effectiveYear = "";
    String areaValue = "";
    String areaCT = null;
    String qualifierCT = null;

    if (areaValueVO != null)
    {
        effectiveDate = areaValueVO.getEffectiveDate();
        EDITDate editDate = new EDITDate(effectiveDate);
        effectiveMonth = editDate.getFormattedMonth();
        effectiveDay = editDate.getFormattedDay();
        effectiveYear = editDate.getFormattedYear();
        areaValue = areaValueVO.getAreaValue();
        areaCT = areaValueVO.getAreaCT();
        qualifierCT = areaValueVO.getQualifierCT();
    }
%>

<%-- ****************************** End Java Code ****************************** --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>
            Area Summary
        </title>
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

        checkForResponseMessage();

        f.effectiveMonth.focus();
        
        var scrollToRow = document.getElementById("<%= activeAreaKeyPK %>");

        if (scrollToRow != null)
        {
            //the true value sets matching id at the top
            scrollToRow.scrollIntoView(true);
        }
    }

    /**
     * Pops-up the AreaKey dialog for entering a new AreaKey.
     */
    function showAreaKeyDialog()
    {
        openDialog("areaKeyDialog", null, 300, 300);

        sendTransactionAction("AreaTran", "showAreaKeyDialog", "areaKeyDialog");
    }

    /**
     * Renders the list of AreaValues for the targeted AreaKey.
     */
    function showAreaValues()
    {
        var selectedRowId = getSelectedRowId("areaKeyTable");

        f.activeAreaKeyPK.value = selectedRowId;

        sendTransactionAction("AreaTran", "showAreaValues", "main");
    }

    /**
     * Deletes the selected AreaKey.
     */
    function deleteAreaKey()
    {
        var selectedRowId = getSelectedRowId("areaKeyTable");

        if (valueIsEmpty(selectedRowId))
        {
            alert("Area Key Required");
        }
        else
        {
            var shouldDeleteAreaKey = confirm("Delete Area Key?");

            if (shouldDeleteAreaKey)
            {
                sendTransactionAction("AreaTran", "deleteAreaKey", "main");
            }
        }
    }

    /**
     * Puts the current page into an "Add" mode.
     */
    function addAreaValue()
    {
        var selectedRowId = getSelectedRowId("areaKeyTable");

        if (valueIsEmpty(selectedRowId))
        {
            alert("AreaKey Required");
        }
        else
        {
            sendTransactionAction("AreaTran", "addAreaValue", "main");
        }
    }

    /**
     * Saves the current AreaValue.
     */
    function saveAreaValue()
    {
        if (textElementIsEmpty(f.effectiveMonth))
        {
            alert("Effective Month Required");
        }
        else if (textElementIsEmpty(f.effectiveDay))
        {
            alert("Effective Day Required");
        }
        else if (textElementIsEmpty(f.effectiveYear))
        {
            alert("Effective Year Required");
        }
        else if (selectElementIsEmpty(f.areaCT))
        {
            alert("Area Required");
        }
        else if (textElementIsEmpty(f.areaValue))
        {
            alert("Value Required");
        }
        else if (selectElementIsEmpty(f.qualifierCT))
        {
            alert("Qualifier Required");
        }
        else
        {
            sendTransactionAction("AreaTran", "saveAreaValue", "main");
        }
    }

    /**
     * Retrieves the details of the selected AreaValue.
     */
    function showAreaValueDetails()
    {
        var selectedRowId = getSelectedRowId("areaValueTable");

        f.activeAreaValuePK.value = selectedRowId;

        sendTransactionAction("AreaTran", "showAreaValueDetails", "main");
    }

    /**
     * Cancels any current AreaValue edits.
     */
    function cancelAreaValue()
    {
        sendTransactionAction("AreaTran", "cancelAreaValue", "main");
    }

    /**
     * Deletes the currently selected AreaValue.
     */
     function deleteAreaValue()
     {
        var selectedRowId = getSelectedRowId("areaValueTable");

        if (valueIsEmpty(selectedRowId))
        {
            alert("Area Value Required");
        }
        else
        {
            var shouldDeleteAreaValue = confirm("Delete Area Value?");

            if (shouldDeleteAreaValue)
            {
                sendTransactionAction("AreaTran", "deleteAreaValue", "main");
            }
        }
     }

    /**
     * Convenience link to the CompanyStructure to AreaValue relations page.
     */
    function showAreaRelation()
    {
        sendTransactionAction("AreaTran", "showAreaRelation", "main");
    }
</script>
<%-- ****************************** END JavaScript ****************************** --%>

    </head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
    <tr height="50%">
      <td colspan="6">
        &nbsp; <!--Filler Row -->
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Effective Date:&nbsp;
        <input name="effectiveMonth" type="text" size="2" maxlength="2" value="<%= effectiveMonth %>"> /
        <input name="effectiveDay" type="text" size="2" maxlength="2" value="<%= effectiveDay %>"> /
        <input name="effectiveYear" type="text" size="4" maxlength="4" value="<%= effectiveYear %>">
      </td>
      <td align="left" nowrap>Area:&nbsp;
        <select name="areaCT">
          <option name="id" value="0">Please Select</option>
<%
    if (areaCTs != null)
    {
        for (int i = 0; i < areaCTs.length; i++)
        {
            String areaCTCode = areaCTs[i].getCode();
            String areaCTDescription = areaCTs[i].getCodeDesc();

            if (areaCTCode.equals(areaCT))
            {
                out.println("<option selected name=\"id\" value=\"" + areaCTCode + "\">" + areaCTDescription + "</option>");
            }
            else
            {
                out.println("<option name=\"id\" value=\"" + areaCTCode + "\">" + areaCTDescription + "</option>");
            }
        }
    }
%>
        </select>
      </td>
      <td align="left" nowrap>Qualifier:&nbsp;
        <select name="qualifierCT">
          <option name="id" value="0">Please Select</option>
<%
    if (areaQualifierCTs != null)
    {
        for (int i = 0; i < areaQualifierCTs.length; i++)
        {
            String code = areaQualifierCTs[i].getCode();
            String codeDesc = areaQualifierCTs[i].getCodeDesc();

            if (code.equals(qualifierCT))
            {
                out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
            }
            else
            {
                out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
            }
        }
    }
%>
        </select>
      </td>
      <td align="left" nowrap>Value:&nbsp;
        <input name="areaValue" type="text" size="10" maxlength="20" value="<%= areaValue %>">
      </td>
    </tr>
    <tr height="50%">
        <td colspan="6">
            &nbsp; <!--Filler Row -->
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<br>

<%-- ****************************** BEGIN Area Value Summary ****************************** --%>
<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" width="33%">
            <input type="button" value="  Add " onClick="addAreaValue()">
            <input type="button" value=" Save " onClick="saveAreaValue()">
            <input type="button" value="Cancel" onClick="cancelAreaValue()">
            <input type="button" value="Delete" onClick="deleteAreaValue()">
        </td>
        <td width="33%">
            <span class="tableHeading">Area Values</span>
        </td>
        <td align="right" width="33%">
            <input type="button" value="Area Table Relation" onClick="showAreaRelation()">
        </td>
    </tr>
</table>

<%--Header--%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td width="25%">
            Effective Date
        </td>
        <td width="25%">
            Area
        </td>
        <td width="25%">
            Qualifier
        </td>
        <td width="25%">
            Value
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:34%; top:0; left:0;">
    <table id="areaValueTable" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%    
    if (areaValueVOs != null)
    {
        areaValueVOs = (AreaValueVO[]) Util.sortObjects(areaValueVOs, new String[]{"getAreaCT", "getEffectiveDate"});

        for (int i = 0; i < areaValueVOs.length; i++)
        {
            String currentEffectiveDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(new EDITDate(areaValueVOs[i].getEffectiveDate()));
            String currentAreaCT = areaValueVOs[i].getAreaCT();
            String currentAreaValue = areaValueVOs[i].getAreaValue();
            String currentQualifier = areaValueVOs[i].getQualifierCT();

            String areaValuePK = String.valueOf(areaValueVOs[i].getAreaValuePK());

            boolean isSelected = false;

            String className = null;

            if (areaValuePK.equals(activeAreaValuePK))
            {
                isSelected = true;

                className = "highlighted";
            }
            else
            {
                className = "default";
            }
%>
        <tr class="<%= className %>" id="<%= areaValuePK %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false);showAreaValueDetails()">
            <td width="25%">
                <%= currentEffectiveDate %>
            </td>
            <td width="25%">
                <%= currentAreaCT %>
            </td>
            <td width="25%">
                <%= currentQualifier %>
            </td>
            <td width="25%">
                <%= currentAreaValue %>
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
<%-- ****************************** END Area Value Summary ****************************** --%>

<br><br>

<%-- ****************************** BEGIN Area Key Summary ****************************** --%>
<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" width="33%">
            <input type="button" value="  Add " onClick="showAreaKeyDialog()">
            <input type="button" value="Delete" onClick="deleteAreaKey()">
        </td>
        <td width="33%">
            <span class="tableHeading">Area Keys</span>
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
            Grouping
        </td>
        <td width="50%">
            Field
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:34%; top:0; left:0;">
    <table id="areaKeyTable" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
    if (areaKeyVOs != null)
    {
        areaKeyVOs = (AreaKeyVO[]) Util.sortObjects(areaKeyVOs, new String[]{"getGrouping", "getField"});

        for (int i = 0; i < areaKeyVOs.length; i++)
        {
            String areaKeyPK = String.valueOf(areaKeyVOs[i].getAreaKeyPK());
            String grouping = areaKeyVOs[i].getGrouping();
            String field = areaKeyVOs[i].getField();

            boolean isSelected = false;

            String className = null;

            if (areaKeyPK.equals(activeAreaKeyPK))
            {
                isSelected = true;
                
                className = "highlighted";
            }
            else
            {
                className = "default";
            }
%>
        <tr class="<%= className %>" id="<%= areaKeyPK %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false);showAreaValues()">
            <td width="50%">
                <%= grouping %>
            </td>
            <td width="50%">
                <%= field %>
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
<%-- ****************************** END Area Key Summary ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">

<input type="hidden" name="activeAreaKeyPK" value="<%= activeAreaKeyPK %>">
<input type="hidden" name="activeAreaValuePK" value="<%= activeAreaValuePK %>">
<%-- ****************************** END Hidden Variables ****************************** --%>
</form>
</body>

</html>