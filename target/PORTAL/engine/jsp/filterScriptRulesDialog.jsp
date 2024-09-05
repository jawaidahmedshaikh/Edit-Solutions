<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.CodeTableVO,
                 fission.utility.Util" %>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] ruleFilterTypes = codeTableWrapper.getCodeTableEntries("RULEFILTERTYPE");

    List filterNames = (List) request.getAttribute("filterNames");

    String filterType = Util.initString((String) request.getAttribute("scriptFilterType"), "All");
    String filterName = (String) request.getAttribute("scriptFilterName");
    String productStructurePK = (String) request.getAttribute("productStructurePK");

    String tableFilterType = Util.initString((String) request.getAttribute("tableFilterType"), "All");
    String tableFilterName = Util.initString((String) request.getAttribute("tableFilterName"), "");
%>

<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.scriptRulesFilterForm;
	}

    function checkForFilterName()
    {
        if (f.filterType.value == "RuleName" ||
            f.filterType.value == "Process")
        {
            sendTransactionAction("ProductRuleStructureTran", "populateFilterNames", "filterScriptRulesDialog");
        }
    }

	function filterScriptRules()
    {
        if ((f.filterType.value == "RuleName" ||
             f.filterType.value == "Process") &&
            f.filterName.value == "Please Select")
        {
            alert("Filter Name Required For Filter Type");
        }
        else
        {
            sendTransactionAction("ProductRuleStructureTran", "filterScriptRules", "buildRuleDialog");
            window.close();
        }
	}

	function sendTransactionAction(transaction, action, target)
    {
		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}

</script>

<title>Script Rules Filter</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()" bgcolor="#DDDDDD">
<form name="scriptRulesFilterForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="right">Filter Type:&nbsp;</td>
      <td nowrap align="left">
        <select name="filterType" onChange="checkForFilterName()">
        <%
            if (ruleFilterTypes != null)
            {
                for(int i = 0; i < ruleFilterTypes.length; i++)
                {
                    String code = ruleFilterTypes[i].getCode();
                    String codeDesc = ruleFilterTypes[i].getCodeDesc();

                    if (filterType.equalsIgnoreCase(code))
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
    </tr>
    <tr>
      <td>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Filter Name:&nbsp;</td>
      <td align="left" nowrap>
        <select name="filterName">
          <%
              out.println("<option name=\"id\" value=\"" + "Please Select" + "\">" + "Please Select" + "</option>");
              if (filterNames != null)
              {
                  for(int i = 0; i < filterNames.size(); i++)
                  {
                      String aFilterName = (String) filterNames.get(i);
                      if (aFilterName.equalsIgnoreCase(filterName))
                      {
                          out.println("<option selected name=\"id\" value=\"" + aFilterName + "\">" + aFilterName + "</option>");
                      }
                      else
                      {
                          out.println("<option name=\"id\" value=\"" + aFilterName + "\">" + aFilterName + "</option>");
                      }
                  }
              }
          %>
        </select>
      </td>
    </tr>
    <tr>
      <td colspan="5">
        &nbsp;
      </td>
    </tr>
    <tr>
      <td width="57%" align="right" nowrap colspan="5">
        <input type="button" name="enter" value="Enter" onClick="filterScriptRules()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">
  <input type="hidden" name="filterPage" value="Script">
  <input type="hidden" name="productStructurePK" value="<%= productStructurePK %>">
  <input type="hidden" name="tableFilterType" value="<%= tableFilterType %>">
  <input type="hidden" name="tableFilterName" value="<%= tableFilterName %>">

</form>
</body>
</html>
