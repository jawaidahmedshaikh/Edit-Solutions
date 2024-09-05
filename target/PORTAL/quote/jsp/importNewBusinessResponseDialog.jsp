<!--
 * User: dlataille
 * Date: Aug 12, 2005
 * Time: 1:22:42 PM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 contract.ui.ImportNewBusinessResponse" %>

<%
    ImportNewBusinessResponse[] importResponses = (ImportNewBusinessResponse[]) request.getAttribute("importResponses");
    String companyStructureId = (String) request.getAttribute("companyStructureId");
%>
<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="JavaScript1.2">

   	var f = null;

	function init()
    {
		f = document.importResponseForm;
    }

    function showClientInformation()
    {
		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		f.insuredDBName.value = trElement.insuredDBName;
		f.insuredSpreadsheetName.value = trElement.insuredSpreadsheetName;
		f.ownerDBName.value = trElement.ownerDBName;
        f.ownerSpreadsheetName.value = trElement.ownerSpreadsheetName;
        f.importContractNumber.value = trElement.importContractNumber;
    }

    function showClientInformationFromCheckbox()
    {
        var checkElement = window.event.srcElement;
		var tdElement = checkElement.parentElement;
		var trElement = tdElement.parentElement;

		f.insuredDBName.value = trElement.insuredDBName;
		f.insuredSpreadsheetName.value = trElement.insuredSpreadsheetName;
		f.ownerDBName.value = trElement.ownerDBName;
        f.ownerSpreadsheetName.value = trElement.ownerSpreadsheetName;
        f.importContractNumber.value = trElement.importContractNumber;

        window.event.cancelBubble = true;
    }

    function completeNewBusinessImport()
    {
        f.importValues.value = getSelectedContracts();

        if (valueIsEmpty(f.importValues.value))
        {
            alert("Please Select Contract(s) For Import");
        }
        else
        {
            sendTransactionAction("QuoteDetailTran", "completeNewBusinessImport", "_self");
        }
    }

    function getSelectedContracts()
    {
        var importResponseTable = document.all.importResponseSummary2;

        var importValues = "";
        var firstRecordWritten = false;

        for (var i = 0; i < importResponseTable.rows.length; i++)
        {
            var importInd = document.getElementById(i + "_1");

            if (importInd != null)
            {
                if (importInd.checked == true)
                {
                    var insuredSpreadsheet = document.getElementsByName("source_" + i + "_2")[0];
                    var insuredDatabase = document.getElementsByName("source_" + i + "_2")[1];
                    var insuredSource = "";
                    var ownerSpreadsheet = document.getElementsByName("source_" + i + "_3")[0];
                    var ownerDatabase = document.getElementsByName("source_" + i + "_3")[1];
                    var ownerSource = "";

                    if (insuredSpreadsheet.checked == true)
                    {
                        insuredSource = "Spreadsheet";
                    }
                    else if (insuredDatabase.checked == true)
                    {
                        insuredSource = "Database";
                    }
                    else
                    {
                        alert("Please Select Spreadsheet or Database For all Selected Contracts/Clients");
                        i = importResponseTable.rows.length;
                    }

                    if (i < importResponseTable.rows.length)
                    {
                        if (ownerSpreadsheet.checked == true)
                        {
                            ownerSource = "Spreadsheet";
                        }
                        else if (ownerDatabase.checked == true)
                        {
                            ownerSource = "Database";
                        }
                        else
                        {
                            alert("Please Select Spreadsheet or Database For all Selected Contracts/Clients");
                            i = importResponseTable.rows.length;
                        }
                    }

                    if (firstRecordWritten == false)
                    {
                        importValues = importValues + importInd.contractNumber + "_" +  insuredSource + "__" + ownerSource;
                        firstRecordWritten = true;
                    }
                    else
                    {
                        importValues = importValues + "|" + importInd.contractNumber + "_" + insuredSource + "__" + ownerSource;
                    }
                }
            }
        }

        return importValues;
    }

    function closeDialog()
    {
        closeWindow();
    }

</script>

<head>
<title>New Business Import Response</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name="importResponseForm" method="post" action="/PORTAL/servlet/RequestManager">

<h3 align="center">Informational</h3>
<table class="summary" id="summaryTable" width="100%" height="1%" border="0" cellspacing="0" cellpadding="2">
  <tr class="heading">
    <th align="left" width="50%">Contract</th>
	<th align="left" width="50%">Message</th>
  </tr>
</table>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:20%; top:0; left:0; background-color:#BBBBBB">
  <table id="importResponseSummary1" width="100%" border="0" cellspacing="0" cellpadding="0">
    <%
        for (int i = 0; i < importResponses.length; i++)
        {
            String message = importResponses[i].getMessage();
            List clientTypes = importResponses[i].getClientTypes();
            if (clientTypes.size() == 0)
            {
    %>
    <tr>
      <td align="left"><%= message %></td>
    </tr>
    <%
            }
        }
    %>
  </table>
</span>

<h3 align="center">Action Required - Duplicate Client Information Found</h3>

<table align="center" width="100%" height="17%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td colspan="3" align="center">Import Contract Number:&nbsp;
      <input type="text" name="importContractNumber" size="15" maxlength="15">
    </td>
  </tr>
  <tr>
    <td width="43%" style="background-color:#99BBBB">
      <div align="center">Insured Information</div>
    </td>
    <td width="10%" style="background-color:#99BBBB">
      &nbsp;
    </td>
    <td width="43%" style="background-color:#99BBBB">
      <div align="center">Owner Information</div>
    </td>
  </tr>
  <tr>
    <td width="43%">
      <span style="border-style:solid; border-width:1;  position:relative; width:90%; height:90%; top:0; left:8; background-color:#BBBBBB">
        <table id="insuredInfoTable" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
          <tr>
            <td colspan="2">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td align="right" nowrap>Spreadsheet:&nbsp;</td>
            <td align="left" nowrap>
              <input type="text" name="insuredSpreadsheetName" size="60" maxlength="60">
            </td>
          </tr>
          <tr>
            <td colspan="2">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td align="right" nowrap>Database:&nbsp;</td>
            <td align="left" nowrap>
              <input type="text" name="insuredDBName" size="60" maxlength="60">
            </td>
          </tr>
        </table>
      </span>
    </td>
    <td align="center" width="10%" nowrap bgcolor="#99BBBB">
      &nbsp;
    </td>
    <td width="43%">
      <span style="border-style:solid; border-width:1;  position:relative; width:90%; height:90%; top:0; left:5; background-color:#BBBBBB">
        <table id="ownerInfoTable" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
          <tr>
            <td colspan="2">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td align="right" nowrap>Spreadsheet:&nbsp;</td>
            <td align="left" nowrap>
              <input type="text" name="ownerSpreadsheetName" size="60" maxlength="60">
            </td>
          </tr>
          <tr>
            <td colspan="2">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td align="right" nowrap>Database:&nbsp;</td>
            <td align="left" nowrap>
              <input type="text" name="ownerDBName" size="60" maxlength="60">
            </td>
          </tr>
        </table>
      </span>
    </td>
  </tr>
</table>

<table class="summary" id="summaryTable" width="100%" height="1%" border="0" cellspacing="0" cellpadding="2">
  <tr class="heading">
    <th align="left" width="33%">Contract</th>
	<th align="left" width="33%">Insured</th>
	<th align="left" width="33%">Owner</th>
  </tr>
</table>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:20%; top:0; left:0; background-color:#BBBBBB">
  <table id="importResponseSummary2" width="100%" border="1" rules="rows" cellspacing="0" cellpadding="0">
	<%
        boolean insuredClient;
        boolean ownerClient;
        String sInsuredDBName;
        String sInsuredSpreadsheetName;
        String sOwnerDBName;
        String sOwnerSpreadsheetName;

        int tableRow = 0;

        for (int i = 0; i < importResponses.length; i++)
        {
            String contractNumber = importResponses[i].getContractNumber();
            sInsuredDBName = "";
            sInsuredSpreadsheetName = "";
            sOwnerDBName = "";
            sOwnerSpreadsheetName = "";

            List clientTypes = importResponses[i].getClientTypes();
            if (clientTypes.size() > 0)
            {
                insuredClient = false;
                ownerClient = false;

                for (int j = 0; j < clientTypes.size(); j++)
                {
                    if (((String) clientTypes.get(j)).equalsIgnoreCase("Insured"))
                    {
                        insuredClient = true;
                    }

                    if (((String) clientTypes.get(j)).equalsIgnoreCase("Owner"))
                    {
                        ownerClient = true;
                    }
                }

                sInsuredDBName = importResponses[i].getInsuredDBName();
                sInsuredSpreadsheetName = importResponses[i].getInsuredSpreadsheetName();
                sOwnerDBName = importResponses[i].getOwnerDBName();
                sOwnerSpreadsheetName = importResponses[i].getOwnerSpreadsheetName();
    %>
    <tr class="default" id="<%= i %>" isSelected="false"
        onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="showClientInformation()"
        insuredDBName="<%= sInsuredDBName %>" insuredSpreadsheetName="<%= sInsuredSpreadsheetName %>"
        ownerDBName="<%= sOwnerDBName %>" ownerSpreadsheetName="<%= sOwnerSpreadsheetName %>"
        importContractNumber="<%= contractNumber %>">
      <td nowrap width="33%">
        <input id="<%= tableRow %>_1" type="checkbox" name="importInd" onClick="showClientInformationFromCheckbox()" contractNumber="<%= contractNumber %>">&nbsp;<%= contractNumber %>
      </td>
    <%
                if (insuredClient)
                {
    %>
      <td nowrap width="33%">
        <input type="radio" name="source_<%= tableRow %>_2" onClick="showClientInformationFromCheckbox()">Import from Spreadsheet&nbsp;&nbsp;<input type="radio" name="source_<%= tableRow %>_2" onClick="showClientInformationFromCheckbox()">Use Database
      </td>
    <%
                }
                else
                {
    %>
      <td nowrap width="33%">
        <input checked type="radio" name="source_<%= tableRow %>_2" onClick="showClientInformationFromCheckbox()">Import from Spreadsheet&nbsp;&nbsp;<input disabled type="radio" name="source_<%= tableRow %>_2" onClick="showClientInformationFromCheckbox()">Use Database
      </td>
    <%
                }

                if (ownerClient)
                {
    %>
      <td nowrap width="33%">
        <input type="radio" name="source_<%= tableRow %>_3" onClick="showClientInformationFromCheckbox()">Import from Spreadsheet&nbsp;&nbsp;<input type="radio" name="source_<%= tableRow %>_3" onClick="showClientInformationFromCheckbox()">Use Database
      </td>
    <%
                }
                else
                {
    %>
      <td nowrap width="33%">
        <input checked type="radio" name="source_<%= tableRow %>_3" onClick="showClientInformationFromCheckbox()">Import from Spreadsheet&nbsp;&nbsp;<input disabled type="radio" name="source_<%= tableRow %>_3" onClick="showClientInformationFromCheckbox()">Use Database
      </td>
    <%
                }
    %>
    </tr>
    <%
                tableRow += 1;                    
            }
        }
    %>
  </table>
</span>

<table id="buttonsTable" width="100%" height="2%" border="0" cellspacing="6" cellpadding="0">
  <tr>
    <td align="right" nowrap>
      <input type="button" name="import" value="Import" onClick="completeNewBusinessImport()">
  	  <input type="button" name="close" value="Close" onClick="closeDialog()">
    </td>
  </tr>
</table>

<!-- ****** Hidden Values ***** //-->
<input type="hidden" name="transaction" value="">
<input type="hidden" name="action" value="">
<input type="hidden" name="importResponses" value="<%= importResponses %>">
<input type="hidden" name="importValues" value="">
<input type="hidden" name="companyStructureId" value="<%= companyStructureId %>">

</form>
</body>
</html>