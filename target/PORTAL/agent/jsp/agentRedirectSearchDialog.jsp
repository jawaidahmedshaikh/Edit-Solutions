<%@ page import="edit.common.vo.*,
                 java.util.TreeMap,
                 java.util.Map,
                 java.util.Iterator,
                 fission.utility.*,
                 edit.common.CodeTableWrapper"%>
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] redirectTypes = codeTableWrapper.getCodeTableEntries("REDIRECTTYPE");

    UISearchClientAgentVO[] uiSearchClientAgentVOs = (UISearchClientAgentVO[]) request.getAttribute("uiSearchClientAgentVOs");
    SearchResponseVO[] searchClientContractVOs = (SearchResponseVO[]) request.getAttribute("searchClientContractVOs");

    String searchStatus = (String) request.getAttribute("searchStatus");
    String redirectType = Util.initString((String) request.getAttribute("redirectType"), "");
%>

<%!
	private TreeMap sortSearchByName(UISearchClientAgentVO[] uiSearchClientAgentVOs)
    {
		TreeMap sortedSearch = new TreeMap();

		for (int i = 0; i < uiSearchClientAgentVOs.length; i++)
        {
            UISearchClientVO uiSearchClientVO = uiSearchClientAgentVOs[i].getUISearchClientVO();
            if (uiSearchClientVO != null)
            {
                String clientName = uiSearchClientVO.getName();
                sortedSearch.put(clientName + uiSearchClientVO.getClientDetailPK(), uiSearchClientAgentVOs[i]);
            }
            else
            {
                sortedSearch.put("" + i, uiSearchClientAgentVOs[i]);
            }
		}

		return sortedSearch;
	}
%>
<%!
	private TreeMap sortSearchByName(SearchResponseVO[] searchClientContractVOs)
    {
		TreeMap sortedSearch = new TreeMap();

		for (int i = 0; i < searchClientContractVOs.length; i++)
        {
            String clientName = searchClientContractVOs[i].getClientName();

            sortedSearch.put(clientName + searchClientContractVOs[i].getClientDetailFK(), searchClientContractVOs[i]);
		}

		return sortedSearch;
	}
%>

<html>
<head>
<title>Agent Inquire/Search</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<!-- ***** JAVASCRIPT *****  -->

<script language="Javascript1.2">

    var f = null;
    var clientPKs = null;
    var currentClientIndex = 0;
    var lastClientIndex    = 0;
    var totalClientCount   = 0;
    var searchStatus = "<%= searchStatus %>";
    var lastRowColor = null;

    function init()
    {
	    f = document.theForm;

        // Check for empty search results
        if (searchStatus == "noData")
        {

        }
        else
        {
            // Update the client counts
            updateMessages();
        }
    }

    function selectClientForRedirect()
    {
    	var tdElement = window.event.srcElement;
    	var currentRow = tdElement.parentElement;
        var clientDetailPK = currentRow.id;
        var companyName = currentRow.companyName;

        f.clientDetailPK.value = clientDetailPK;
        f.companyName.value = companyName;

        sendTransactionAction("AgentDetailTran", "showRedirectAfterSearch", "contentIFrame");

        closeDialog();
    }

    function checkForEnter()
    {
        var eventObj = window.event;

        if (eventObj.keyCode == 13)
        {
            doSearch();
        }
    }

    function updateMessages()
    {
        var tdMessages = document.getElementById("clientMessages");
        var currentClientCount = 0;

        if (currentClientIndex == 0 && totalClientCount != 0)
        {
            currentClientCount = 1;
        }
        else if (currentClientIndex == 0 && totalClientCount == 0)
        {
            currentClientCount = 0;
        }
        else
        {
            currentClientCount = currentClientIndex + 1;
        }

        tdMessages.innerHTML = "Found: " + totalClientCount;
    }

    function scrollToNextClient()
    {
        if (currentClientIndex == totalClientCount - 1)
        {
            return;
        }
        else
        {
            currentClientIndex++;

            var clientPK = clientPKs[currentClientIndex].clientPK;

            scrollClientIntoView(clientPK);

            updateMessages();
        }
    }

    function scrollToPrevClient()
    {
        if (currentClientIndex == 0)
        {
            return;
        }
        else
        {
            currentClientIndex--;

            var clientPK = clientPKs[currentClientIndex].clientPK;

            scrollClientIntoView(clientPK);

            updateMessages();
        }
    }

    function scrollClientIntoView(clientPK)
    {
        var clientRow = document.getElementById(clientPK);

        if (clientRow != null)
        {
            clientRow.scrollIntoView(true);
        }
    }

    function closeDialog()
    {
        window.close();
    }

    function doSearch()
    {
        var tdSearchMessage = document.getElementById("searchMessage");
        tdSearchMessage.innerHTML = "<font face='arial' size='3' color='blue'>Searching ...</font>";

        if (f.redirectType.selectedIndex == 0)
        {
            var tdSearchMessage = document.getElementById("searchMessage");
            tdSearchMessage.innerHTML = "&nbsp;"

            alert("Please Select Redirect Type");
        }
        else
        {
            sendTransactionAction("SearchTran", "searchForRedirectClients", "_self");
        }
    }

    function resetForm()
    {
        f.reset();
    }

    function highlightRow()
    {
    	var tdElement = window.event.srcElement;
    	var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }

        lastRowColor = currentRow.style.backgroundColor;

        currentRow.style.backgroundColor = "#AAAAAA";
    }

    function unhighlightRow()
    {
    	var tdElement = window.event.srcElement;
    	var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }

        currentRow.style.backgroundColor = lastRowColor;
    }

    function cancelSearch()
    {
        sendTransactionAction("SearchTran", "showAgentSearchDialog", "_self");
    }

    function sendTransactionAction(transaction, action, target)
    {
		f.transaction.value = transaction;
    	f.action.value = action;

    	f.target = target;

    	f.submit();
    }
</script>

<!-- ****** STYLE CODE ***** //-->
<style>

	.highLighted {

		background-color: #FFFFCC;
	}

    .attached {

		background-color: #CCFFCC;
	}

</style>

</head>

<body class="mainTheme" onLoad="init()">

<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table class="formData" width="100%" height="15%" border="0" cellspacing="0" cellpadding="4">
    <tr>
      <td align="left" nowrap>Redirect Type:&nbsp;
        <select name="redirectType">
          <option> Please Select </option>
          <%
              for(int i = 0; i < redirectTypes.length; i++) {

                  String codeDesc    = redirectTypes[i].getCodeDesc();
                  String code        = redirectTypes[i].getCode();

                 if (redirectType.equalsIgnoreCase(code)) {

                     out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                 }
                 else  {

                     out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                 }
              }
          %>
        </select>
      </td>
      <td align="left" nowrap>Agent/Client Name:&nbsp;
        <input type="text" name="clientName" maxlength="50" size="50" onKeyDown="checkForEnter()">
      </td>
    </tr>
    </tr>
    <tr>
      <td id="clientMessages" align="right" nowrap>
        &nbsp;
      </td>
      <td colspan="2" id="searchMessage" align="middle" nowrap>
        &nbsp;
      </td>
      <td align="right" nowrap>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" value="Search" tabindex="7" onClick="doSearch()">
        <input type="button" value="Cancel" tabindex="8" onClick="cancelSearch()">
      </td>
    </tr>
  </table>

  <br><br>

  <table id="summaryTable" class="summary" width="100%" height="60%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading" height="2%">
      <th align="left" width="33%">Agent/Client Name</th>
      <th align="left" width="33%">Tax Id</th>
      <th align="left" width="33%">Company</th>
    </tr>
    <tr>
      <td colspan="4" height="98%">
        <span class="scrollableContent">
          <table id="summaryTable" class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
            <%
              if (uiSearchClientAgentVOs != null)
              {
    		      Map sortedSearch = sortSearchByName(uiSearchClientAgentVOs);

                  Iterator it = sortedSearch.values().iterator();

                  String color1 = "#E1E1E1";
                  String color2 = "#F9F9F9";
                  String bgColor = color1;

			      while (it.hasNext())
                  {
			          UISearchClientAgentVO uiSearchClientAgentVO = (UISearchClientAgentVO) it.next();

                      UISearchClientVO uiSearchClientVO = uiSearchClientAgentVO.getUISearchClientVO();

                      if (bgColor.equals(color1))
                      {
                            bgColor = color2;
                      }
                      else
                      {
                            bgColor = color1;
                      }

                      UISearchAgentVO[] uiSearchAgentVOs = uiSearchClientAgentVO.getUISearchAgentVO();

                      if (uiSearchClientVO != null)
                      {
                          if (uiSearchAgentVOs != null)
                          {
                              for (int j = 0; j < uiSearchAgentVOs.length; j++)
                              {
                                  String companyName = Util.initString(uiSearchAgentVOs[j].getCompanyName(), "&nbsp;");
            %>
            <tr bgColor="<%= bgColor %>" rowType="contract" id="<%= uiSearchClientVO.getClientDetailPK() %>"
                companyName="<%= companyName %>" onDblClick="selectClientForRedirect()"
                onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
              <td nowrap align="left" width="33%">
                 <%= uiSearchClientVO.getName() %>
              </td>
              <td nowrap align="left" width="33%">
                 <%= Util.initString(uiSearchClientVO.getClientId(), "")%>
              </td>
              <td nowrap align="left" width="33%">
                 <%= companyName %>
              </td>
            </tr>
            <%
                              } // end if
                          } // end for
                      }// end if
                  }
              }
              else if (searchClientContractVOs != null)
              {
    		      Map sortedSearch = sortSearchByName(searchClientContractVOs);

                  Iterator it = sortedSearch.values().iterator();

                  String color1 = "#E1E1E1";
                  String color2 = "#F9F9F9";
                  String bgColor = color1;

			      while (it.hasNext())
                  {
			          SearchResponseVO searchClientContractVO = (SearchResponseVO) it.next();

                      String clientName = searchClientContractVO.getClientName();
                      String taxId = Util.initString(searchClientContractVO.getTaxIdentification(), "");

                      if (bgColor.equals(color1))
                      {
                            bgColor = color2;
                      }
                      else
                      {
                            bgColor = color1;
                      }
            %>
            <tr bgColor="<%= bgColor %>" rowType="contract" id="<%= searchClientContractVO.getClientDetailFK() %>"
                companyName="" onDblClick="selectClientForRedirect()"
                onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
              <td nowrap align="left" width="33%">
                 <%= clientName %>
              </td>
              <td nowrap align="left" width="33%">
                 <%= taxId %>
              </td>
              <td nowrap align="left" width="33%">
                 &nbsp;
              </td>
            </tr>
            <%
                  }
              }
            %>
          </table>
        </span>
      </td>
    </tr>
  </table>
</span>

<span id="span3" style="position:relative; width:100%; height:10%; top:0; left:0; z-index:0">
  <table width="100%">
    <tr>
      <td align="right">
        <input tabindex="11" type="button" value="Close" onClick="closeDialog()">
      </td>
    </tr>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction" value="">
<input type="hidden" name="action"      value="">
<input type="hidden" name="clientDetailPK" value="">
<input type="hidden" name="companyName" value="">

</form>
</body>
</html>
