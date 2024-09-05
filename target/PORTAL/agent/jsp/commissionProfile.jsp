<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 fission.utility.Util" %>

<%
    String profileMessage = (String) request.getAttribute("profileMessage");
    if (profileMessage == null) {

        profileMessage = "";
    }

    CommissionProfileVO[] commissionProfileVOs = (CommissionProfileVO[]) session.getAttribute("commissionProfileVOs");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] contractCodeCTs = codeTableWrapper.getCodeTableEntries("CONTRACTCODE");
    CodeTableVO[] commissionLevelCTs = codeTableWrapper.getCodeTableEntries("COMMISSIONLEVEL");
    CodeTableVO[] commissionOptionCTs = codeTableWrapper.getCodeTableEntries("COMMISSIONOPTION");

    String contractCode = "";
    String commissionLevel = "";
    String commissionOption = "";
    String selectedCommProfilePK = Util.initString((String) request.getAttribute("selectedCommProfilePK"), "");

    String trailStatus = "unchecked";

    if (commissionProfileVOs != null)
    {
        for (int p = 0; p < commissionProfileVOs.length; p++)
        {
            if ((commissionProfileVOs[p].getCommissionProfilePK() + "").equals(selectedCommProfilePK))
            {
                commissionLevel = commissionProfileVOs[p].getCommissionLevelCT();
                contractCode = commissionProfileVOs[p].getContractCodeCT();
                commissionOption = commissionProfileVOs[p].getCommissionOptionCT();

                String trailInd = commissionProfileVOs[p].getTrailStatus();
                if (trailInd.equals("Y"))
                {
                    trailStatus = "checked";
                }

                break;
            }
        }
    }

	String rowToMatchBase = selectedCommProfilePK;
%>

<%!
    private TreeMap sortContractCodes(CodeTableVO[] contractCodeCTs)
    {
		TreeMap sortedContractCodes = new TreeMap();

		for (int i = 0; i < contractCodeCTs.length; i++) {

			String contractCode = contractCodeCTs[i].getCode();
            sortedContractCodes.put(contractCode, contractCodeCTs[i]);
		}

		return sortedContractCodes;
	}
%>

<%!
    private TreeMap sortCommissionLevels(CodeTableVO[] commissionLevelCTs)
    {
		TreeMap sortedCommLevels = new TreeMap();

		for (int i = 0; i < commissionLevelCTs.length; i++)
        {
			String commLevel = commissionLevelCTs[i].getCode();
            sortedCommLevels.put(commLevel, commissionLevelCTs[i]);
		}

		return sortedCommLevels;
	}
%>

<%!
    private TreeMap sortCommissionOptions(CodeTableVO[] commissionOptionCTs)
    {
		TreeMap sortedCommOptions = new TreeMap();

		for (int i = 0; i < commissionOptionCTs.length; i++)
        {
			String commOption = commissionOptionCTs[i].getCode();
            sortedCommOptions.put(commOption, commissionOptionCTs[i]);
		}

		return sortedCommOptions;
	}
%>

<%!
    private TreeMap sortCommissionProfiles(CommissionProfileVO[] commProfileVOs)
    {
		TreeMap sortedCommProfiles = new TreeMap();

		for (int i = 0; i < commProfileVOs.length; i++)
        {
			String commLevel = commProfileVOs[i].getCommissionLevelCT();
            String contractCode = commProfileVOs[i].getContractCodeCT();
            String commOption = commProfileVOs[i].getCommissionOptionCT();
            sortedCommProfiles.put(contractCode + commLevel + commOption, commProfileVOs[i]);
		}

		return sortedCommProfiles;
	}
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

    var profileMessage = "<%= profileMessage %>";

    var width = screen.width;
    var height = screen.height;

	function init()
    {
		f = document.commissionContractForm;

        if (profileMessage != "")
        {
            alert(profileMessage);
        }
	}

	function selectProfileRow()
    {
		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

        var selectedCommProfilePK = trElement.commProfilePK;
        f.selectedCommProfilePK.value = selectedCommProfilePK;

        sendTransactionAction("AgentDetailTran", "showProfileDetailSummary", "_self");
	}

	function addNewProfile()
    {
		sendTransactionAction("AgentDetailTran","addOrCancelProfile","_self");
	}

	function saveCommissionProfile()
    {
		if (f.trailStatus.checked == true)
        {
			f.trailStatus.value = "checked";
		}
        else
        {
            f.trailStatus.value = "unchecked";
        }

        if (f.contractCode.value == "Please Select")
        {
            alert("Please Select Contract Code for Profile");
        }
        else if (f.commissionLevel.value == "Please Select")
        {
            alert("Please Select Commission Level for Profile");
        }
        else if (f.commissionOption.value == "Please Select")
        {
            alert("Please Select Commission Option for Profile");
        }
        else
        {
            sendTransactionAction("AgentDetailTran", "saveCommissionProfile", "_self");
        }
	}

	function cancelProfile()
    {
		sendTransactionAction("AgentDetailTran","addOrCancelProfile","_self");
	}

    function deleteProfile()
    {
        if (f.selectedCommProfilePK.value == "")
        {
            alert("Please Select Profile for Deletion");
        }
        else
        {
            sendTransactionAction("AgentDetailTran", "deleteCommissionProfile","_self");
        }
    }
</script>
<head>
<title>Commission Profiles</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" conte="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

</head>
<body class="mainTheme" onLoad="init()">
<form name="commissionContractForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table height="45%" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="right" nowrap>Contract Code:&nbsp;</td>
      <td align="left" nowrap>
        <select name="contractCode">
        <option name="id" value="">Please Select</option>
          <%
              if (contractCodeCTs != null)
              {
                  Map sortedCommissionContracts = sortContractCodes(contractCodeCTs);

                  Iterator it = sortedCommissionContracts.values().iterator();

                  while (it.hasNext())
                  {
                      CodeTableVO contractCodeCT = (CodeTableVO) it.next();

                      String aContractCode = contractCodeCT.getCode();
                      String contractCodeDesc = contractCodeCT.getCodeDesc();
                      if (aContractCode.equals(contractCode))
                      {
                          out.println("<option selected name=\"id\" value=\"" + aContractCode + "\">" + contractCodeDesc + "</option>");
                      }
                      else
                      {
                          out.println("<option name=\"id\" value=\"" + aContractCode + "\">" + contractCodeDesc + "</option>");
                      }
                  }
              }
          %>
        </select>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Commission Level:&nbsp;</td>
      <td align="left" nowrap>
        <select name="commissionLevel">
        <option name="id" value="">Please Select</option>
          <%
              if (commissionLevelCTs != null)
              {
                  Map sortedCommLevels = sortCommissionLevels(commissionLevelCTs);

                  Iterator it = sortedCommLevels.values().iterator();

                  while (it.hasNext())
                  {
                      CodeTableVO commissionLevelCT = (CodeTableVO) it.next();

                      String aCommissionLevel = commissionLevelCT.getCode();
                      String commissionLevelDesc = commissionLevelCT.getCodeDesc();
                      if (aCommissionLevel.equals(commissionLevel))
                      {
                          out.println("<option selected name=\"id\" value=\"" + aCommissionLevel + "\">" + commissionLevelDesc + "</option>");
                      }
                      else
                      {
                          out.println("<option name=\"id\" value=\"" + aCommissionLevel + "\">" + commissionLevelDesc + "</option>");
                      }
                  }
              }
          %>
        </select>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Option Code:&nbsp;</td>
      <td align="left" nowrap>
        <select name="commissionOption">
        <option name="id" value="">Please Select</option>
          <%
              if (commissionOptionCTs != null)
              {
                  Map sortedCommOptions = sortCommissionOptions(commissionOptionCTs);

                  Iterator it = sortedCommOptions.values().iterator();

                  while (it.hasNext())
                  {
                      CodeTableVO commissionOptionCT = (CodeTableVO) it.next();

                      String aCommissionOption = commissionOptionCT.getCode();
                      String commissionOptionDesc = commissionOptionCT.getCodeDesc();
                      if (aCommissionOption.equals(commissionOption))
                      {
                          out.println("<option selected name=\"id\" value=\"" + aCommissionOption + "\">" + commissionOptionDesc + "</option>");
                      }
                      else
                      {
                          out.println("<option name=\"id\" value=\"" + aCommissionOption + "\">" + commissionOptionDesc + "</option>");
                      }
                  }
              }
          %>
        </select>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Trail&nbsp;</td>
      <td align="left" nowrap>
        <input type="checkbox" name="trailStatus" <%= trailStatus %> >
      </td>
    </tr>
  </table>
  <br>
  <br>
  <table class="summaryArea" width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td align="left" colspan="3">
		<input type="button" value="   Add   " style="background-color:#DEDEDE" onClick="addNewProfile()">
		<input type="button" value="   Save  " style="background-color:#DEDEDE" onClick="saveCommissionProfile()">
		<input type="button" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelProfile()">
		<input type="button" value="  Delete " style="background-color:#DEDEDE" onClick="deleteProfile()">
	  </td>
	</tr>
  </table>
  <table>
  <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
	  <th width="33%" align="left">Contract Code</th>
	  <th width="33%" align="left">Commission Level</th>
      <th width="33%" align="left">Option Code</th>
	</tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:50%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="commissionProfileSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
      <%
        String rowToMatch = "";

        String className = "default";
        boolean selected = false;

        if (commissionProfileVOs != null) {

            Map sortedCommProfiles = sortCommissionProfiles(commissionProfileVOs);

            Iterator it = sortedCommProfiles.values().iterator();

            while (it.hasNext()) {

                CommissionProfileVO commProfileVO = (CommissionProfileVO) it.next();

                String sCommProfilePK = commProfileVO.getCommissionProfilePK() + "";
                String sCommOptionCode = commProfileVO.getCommissionOptionCT();
                String sCommLevel = commProfileVO.getCommissionLevelCT();
                String sContractCode = commProfileVO.getContractCodeCT();
                rowToMatch = sCommProfilePK;

                if (rowToMatch.equals(rowToMatchBase) &&
                    !rowToMatchBase.equals(""))
                {
                    className = "highlighted";
                    selected = true;
                }
                else
                {
                    className = "default";
                    selected = false;
                }
      %>
      <tr class="<%= className %>" isSelected="<%= selected %>" commProfilePK="<%= sCommProfilePK %>"
          onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectProfileRow()">
        <td width="25%" nowrap>
          <%= sContractCode %>
        </td>
        <td width="25%" nowrap>
          <%= sCommLevel %>
        </td>
        <td width="25%" nowrap>
          <%= sCommOptionCode %>
        </td>
      </tr>
      <%
            } // end for
        } // end if
      %>
    </table>
  </span>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">

 <input type="hidden" name="selectedCommProfilePK" value="<%= selectedCommProfilePK %>">

</form>
</body>
</html>
