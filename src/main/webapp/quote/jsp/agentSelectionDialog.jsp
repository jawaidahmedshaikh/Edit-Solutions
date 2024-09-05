<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 fission.utility.Util" %>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
    UISearchClientAgentVO[] uiSearchClientAgentVOs = (UISearchClientAgentVO[]) request.getAttribute("uiSearchClientAgentVOs");

    String agentType = "";
    String agentName = "";
    String agentStatus = "";
    String agentNumber = "";

	PageBean formBean = quoteMainSessionBean.getPageBean("formBean");
    String companyStructureId = Util.initString(formBean.getValue("companyStructureId"), "0");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    CodeTableVO[] coverages = codeTableWrapper.getCodeTableEntries("OPTIONCODE", Long.parseLong(companyStructureId));

    String agentMessage = Util.initString((String)request.getAttribute("agentMessage"), "");
    String agentNumberSearch = "";
    String clientName = Util.initString((String)request.getAttribute("clientName"), "");
//	String agentTypeSearch	= "";

    String coverage     = formBean.getValue("optionId");

    AgentVO agentVO = (AgentVO) session.getAttribute("selectedAgentVO");

    if (agentVO != null) {

//        ClientDetailVO clientDetailVO = (ClientDetailVO) agentVO.getParentVO(ClientRoleVO.class).getParentVO(ClientDetailVO.class);
        agentNumberSearch = Util.initString(agentVO.getAgentNumber(), "");
//        agentTypeSearch = Util.initString(agentVO.getAgentTypeCT(), "");
    }

    PlacedAgentBranchVO[] placedAgentBranchVOs = new edit.common.vo.PlacedAgentBranchVO[0];

    if (clientName.equals(""))
    {
        placedAgentBranchVOs = (PlacedAgentBranchVO[]) session.getAttribute("placedAgentBranchVOs");
    }
%>

<%!
    private String getClientDetailName(ClientDetailVO clientDetailVO)
    {
        String name = null;

        if (clientDetailVO.getTrustTypeCT().equalsIgnoreCase("CorporateTrust") ||                
            clientDetailVO.getTrustTypeCT().equalsIgnoreCase("Corporate") ||
            clientDetailVO.getTrustTypeCT().equalsIgnoreCase("LLC"))
        {
            name = clientDetailVO.getCorporateName();
        }
        else
        {
            name = clientDetailVO.getLastName() + ", " + clientDetailVO.getFirstName();
        }

        return name;
    }

//    /**
//     * Performs date comparison between the date passed-in and the current date.
//     * @param stopDate
//     * @return true if stopDate is less than the current date
//     */
//    private boolean stopDateExpired(String stopDate, String compareToDate)
//    {
//        boolean stopDateExpired = false;
//
//        if ( (stopDate != null) && EDITDate.isACandidateDate(stopDate))
//        {
//            EDITDate eStopDate = new EDITDate(stopDate);
//
//            EDITDate eCompareToDate = new EDITDate(compareToDate);
//
//            if (eStopDate.before(eCompareToDate))
//            {
//                stopDateExpired = true;
//            }
//        }
//
//        return stopDateExpired;
//    }

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

<html>

<!-- ***** JAVASCRIPT *****  -->
<head>
<title>Select Agent New Business</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<script language="Javascript1.2">

    var f = null;
    var agentMessage = "<%= agentMessage %>";

    var colorMouseOver = "#00BB00";
    var colorHighlighted = "#FFFFCC";
    var colorMainEntry   = "#BBBBBB";
    var lastRowColor = null;
    var selectedAgentElement = null;

    function highlightRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") {

            currentRow = currentRow.parentElement;
        }

        currentRow.style.backgroundColor = colorMouseOver;
    }

    function unhighlightRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") {

            currentRow = currentRow.parentElement;
        }

        var className = currentRow.className;

        if (currentRow.isSelected != "true") {

            if (className == "highLighted") {

                currentRow.style.backgroundColor = colorHighlighted;
            }

            else {

                currentRow.style.backgroundColor = colorMainEntry;
            }
        }
        else {

            currentRow.style.backgroundColor = colorHighlighted;
        }
    }

    function init() {

	    f = document.agentSelectionForm;

        if (agentMessage != "") {

            alert(agentMessage);
        }

        f.agentIdSearch.focus();
        document.body.style.cursor='auto';
    }

    function trim(s)
    {
        return s.replace(/^\s*|\s*$/g,"");
    }

    function doAgentSearch()
    {
        if (trim(f.agentIdSearch.value).length > 0)
        {
            showAgentHierarchy(f.agentIdSearch.value);
        }
        else if (trim(f.clientName.value).length>0)
        {
            sendTransactionAction("SearchTran", "findAgentForContractByName", "_self");
        }
        else
        {
            alert("Please enter an AgentID or Name to search for.");
        }
    }

    function showAgentHierarchy(agentNumber)
    {
        document.body.style.cursor='wait';
        f.agentId.value = agentNumber;
        sendTransactionAction("QuoteDetailTran", "showReportToAgent", "_self");
    }

    function highlightAgentRow()
    {
    	var tdElement = window.event.srcElement;
    	var currentRow = tdElement.parentElement;
        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }
        lastRowColor = currentRow.style.backgroundColor;
        currentRow.style.backgroundColor = colorMouseOver;
    }

    function unhighlightAgentRow()
    {
    	var tdElement = window.event.srcElement;
    	var currentRow = tdElement.parentElement;
        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }
        currentRow.style.backgroundColor = colorMainEntry;
    }

    function selectRow() {

		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

        if (trElement.isSelected == "false") {

            if (f.selectedPlacedAgentPK.value != "") {

                alert("Report To Agent Already Selected");
            }

            else {

                trElement.style.backgroundColor = "#FFFFCC";
                trElement.isSelected = "true";

                var selectedPlacedAgentPK = trElement.placedAgentPK;
                f.selectedPlacedAgentPK.value = selectedPlacedAgentPK;
            }
        }
        else {

            trElement.style.backgroundColor = "#BBBBBB";

            trElement.isSelected = "false";
            f.selectedPlacedAgentPK.value = "";
        }


<%--        var tableElement = trElement.parentElement;--%>
<%----%>
<%--        for(i=0;i<tableElement.children.length;i++)--%>
<%--        {--%>
<%--            unselectTrElement(tableElement.children[i]);--%>
<%--        }--%>
<%----%>
<%--        trElement.style.backgroundColor = "#FFFFCC";--%>
<%--        trElement.isSelected = "true";--%>
<%--        var selectedPlacedAgentPK = trElement.placedAgentPK;--%>
<%--        f.selectedPlacedAgentPK.value = selectedPlacedAgentPK;--%>
    }

<%--    function unselectTrElement(trElement)--%>
<%--    {--%>
<%--        trElement.style.backgroundColor = "#BBBBBB";--%>
<%--        trElement.isSelected = "false";--%>
<%--        f.selectedPlacedAgentPK.value = "";--%>
<%--    }--%>

	function openDialog(theURL,winName,features,transaction,action) {

	  dialog = window.open(theURL,winName,features);

	  sendTransactionAction(transaction, action, winName);
	}

    function checkForEnter(){

        var eventObj = window.event;

        if (eventObj.keyCode == 13){

            doAgentSearch();
        }
    }

    function saveAgentSelection(){

        if (f.selectedPlacedAgentPK.value == "") {

            alert("Please Select Report To Agent");
        }
        else if (f.coverage.selectedIndex == 0) {

            alert("Please Select Coverage");
        }

        else
        {
          <%
               int numberOfPlacedAgents = 0;
               if( placedAgentBranchVOs!=null)
               {
                   numberOfPlacedAgents = placedAgentBranchVOs.length;
               }
            %>
            var situationCodeCorrect = true;
            if(<%=numberOfPlacedAgents%>>1)
            {
                situationCodeCorrect = confirm("Is the situation code correct?");
            }

            if(situationCodeCorrect)
            {
                sendTransactionAction("QuoteDetailTran","saveAgentSelection","contentIFrame");
                window.close();
            }
        }
    }

    function cancelAgentSelectionDialog(){

        sendTransactionAction("QuoteDetailTran","closeAgentSelectionDialog","contentIFrame");
        window.close();
    }

    function sendTransactionAction(transaction, action, target) {

        document.body.style.cursor='wait';
        f.transaction.value = transaction;
    	f.action.value = action;

    	f.target = target;

    	f.submit();
    }

</script>

<body class="mainTheme" onLoad="init()">

<form  name="agentSelectionForm" method="post" action="/PORTAL/servlet/RequestManager">
  <input type="hidden" name="agentId"/>
  <input type="hidden" name="repopulateSearch" value="true"/>
  <table class="formData" width="100%" height="10" border="0" cellspacing="0" cellpadding="4">
    <tr>
      <td align="right" width="20%" nowrap>
        Agent Number:
      </td>
      <td align="left" nowrap colspan = "3">
        <input type="text" name="agentIdSearch" maxlength="11" size="11" onKeyDown="checkForEnter()">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>
        Name:
      </td>
      <td align="left" nowrap colspan = "3">
        <input type="text" name="clientName" maxlength="30" size="30" value="<%= clientName %>" onKeyDown="checkForEnter()">
      </td>
    </tr>
    <tr>
        <td colspan="7" align="right">
        <input type="button" value="Search" onClick="doAgentSearch()">
        </td>
    </tr>
  </table>

  <br>
  <br>

    <span>
  <table id="summaryTable" class="summary" width="100%" height="25%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading" height="2%">
      <th align="left" width="25%">Name</th>
      <th align="left" width="25%">Agent </th>
      <th align="left" width="25%">Agent Type</th>
      <th align="left" width="25%">Status</th>
    </tr>
    <tr>
      <td colspan="4" height="98%">

        <span class="scrollableContent" id="agentScrollArea">
          <table id="summaryTableInner" class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
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

                    if (uiSearchAgentVOs != null)
                    {
                        for (int j = 0; j < uiSearchAgentVOs.length; j++)
                        {
                        	agentStatus = Util.initString(uiSearchAgentVOs[j].getAgentStatusCT(), "&nbsp;");
                        	if(!"Active".equals(agentStatus))
                        	{
                        		continue;
                        	}

                            agentName =  Util.initString(uiSearchClientAgentVO.getUISearchClientVO().getName(), "&nbsp;");
                            agentType = Util.initString(uiSearchAgentVOs[j].getAgentTypeCT(), "&nbsp;");
                            agentNumber = ""; 
                            if (uiSearchAgentVOs[j].getAgentNumberListCount() > 0)
                            {
                                AgentNumberList[] agentNumberList = uiSearchAgentVOs[j].getAgentNumberList();
                                for (int k = 0; k < agentNumberList.length; k++)
                                {
                                    agentNumber = Util.initString(agentNumberList[k].getAgentNumber(), "&nbsp;");

                                    if(agentNumber.equals(agentNumberSearch))
                                    {
                                        bgColor="#FFFCC";
                                        out.write("<script language=\"javascript\">selectedAgentElement="+uiSearchAgentVOs[j].getAgentPK()+"</script>");
                                    }
                          %>

                          <tr rowType="contract" id="<%= uiSearchAgentVOs[j].getAgentPK() %>"
                              onDblClick="showAgentHierarchy('<%=agentNumber%>')" onMouseOver="highlightAgentRow()" onMouseOut="unhighlightAgentRow()">
                            <td nowrap align="left" width="25%">
                              <%= uiSearchClientVO.getName() %>
                            </td>
                            <td nowrap align="left" width="25%">
                               <%= agentNumber %>
                            </td>
                            <td nowrap align="left" width="25%">
                               <%= agentType %>
                            </td>
                            <td nowrap align="left" width="25%">
                               <%= agentStatus %>
                            </td>
                          </tr>

                          <%
                                }
                            }
                            else
                            {
                          %>

                          <tr rowType="contract" id="<%= uiSearchAgentVOs[j].getAgentPK() %>"
                              onDblClick="showAgentHierarchy('<%=agentNumber%>')" onMouseOver="highlightAgentRow()" onMouseOut="unhighlightAgentRow()">
                            <td nowrap align="left" width="25%">
                              <%= uiSearchClientVO.getName() %>
                            </td>
                            <td nowrap align="left" width="25%">
                               &nbsp;
                            </td>
                            <td nowrap align="left" width="25%">
                               <%= agentType %>
                            </td>
                            <td nowrap align="left" width="25%">
                               <%= agentStatus %>
                            </td>
                          </tr>

                          <%
                                }// end else
                            } // end for
                        } // end for
                    } // end while
                } // end for
            %>
          </table>
        </span>
      </td>
    </tr>
  </table>
    </span>
  <br/>
  <br/>

  <table class="summary" width="100%" height="35%" border="0" cellspacing="0" cellpadding="0">
    <tr height="1%" class="heading">
      <th align="left" width="33%">Contract Code</th>
      <th align="left" width="33%">Situation</th>
      <th align="left" width="33%">Report To Agent Name/Number</th>
    </tr>
    <tr width="100%">
      <td colspan="3"  height="98%">
        <span class="scrollableContent">
          <table class="summary" id="agentSummary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <%
                String hClassName = "mainEntry";
                String hSelected = "false";

                if (placedAgentBranchVOs != null) {

                    for (int b = 0; b < placedAgentBranchVOs.length; b++)
                    {
                        PlacedAgentVO[] placedAgentVOs = placedAgentBranchVOs[b].getPlacedAgentVO();
                        int placedAgentCount = placedAgentBranchVOs[b].getPlacedAgentVOCount();
                        int paIndex = 0;
                        if (placedAgentCount > 1) {

                            paIndex = placedAgentCount - 2;
                        }
                        else {

                            paIndex = placedAgentCount - 1;
                        }

                        int writingAgentIndex = placedAgentCount - 1;

                        if (placedAgentVOs[paIndex].getInactiveIndicator() == null ||
                            placedAgentVOs[paIndex].getInactiveIndicator().equalsIgnoreCase("N"))
                        {
                            PlacedAgentVO currentPlacedAgentVO = placedAgentBranchVOs[b].getPlacedAgentVO(paIndex);
                            PlacedAgentVO writingPlacedAgentVO = placedAgentBranchVOs[b].getPlacedAgentVO(writingAgentIndex);

                            String situation = Util.initString(writingPlacedAgentVO.getSituationCode(), "&nbsp;");
                            AgentContractVO writingAgentContract = (AgentContractVO) writingPlacedAgentVO.getParentVO(AgentContractVO.class);
                            String contractCode = Util.initString(writingAgentContract.getContractCodeCT(), "");

                            String writingAgentPK = Util.initString(writingPlacedAgentVO.getPlacedAgentPK() + "", "&nbsp;");
                            ClientRoleVO clientRole = (ClientRoleVO) currentPlacedAgentVO.getParentVO(ClientRoleVO.class);
                            ClientDetailVO clientDetail = (ClientDetailVO) clientRole.getParentVO(ClientDetailVO.class);
                            String reportToAgentId = Util.initString(clientRole.getReferenceID(), "");
                            String reportToName = Util.initString(getClientDetailName(clientDetail), "");
            %>
            <tr class="<%= hClassName %>" isSelected="<%= hSelected %>" onClick="selectRow()"
                onMouseOver="highlightRow()" onMouseOut="unhighlightRow()"
                placedAgentPK="<%= writingAgentPK %>">
              <td nowrap width="33%">
                <%= contractCode %>
              </td>
              <td nowrap width="33%">
                <%= situation %>
              </td>
              <td nowrap width="33%">
                <%= reportToName %>/<%= reportToAgentId %>
              </td>
            </tr>
            <%
                        } // end if (inactive indicator)
                    } // end for
                } // end if

            %>
            <tr class="filler">
                <td colspan="3">
                     &nbsp;
                </td>
            </tr>
          </table>
        </span>
      </td>
    </tr>
  </table>
  <br>
  <table>
    <tr>
	  <td align="left" nowrap>Coverage:&nbsp;
        <select name="coverage">
          <option>Please Select</option>
          <%
              for(int i = 0; i < coverages.length; i++) {

                  String codeTablePK = coverages[i].getCodeTablePK() + "";
                  String codeDesc    = coverages[i].getCodeDesc();
                  String code        = coverages[i].getCode();

                 if (coverage.equalsIgnoreCase(code)) {

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
  </table>
  <table width="100%" height="2%">
    <tr>
      <td align="right">
        <input type="button" value="Enter" onClick="saveAgentSelection()">
        <input type="button" value="Cancel" onClick="cancelAgentSelectionDialog()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction" value="">
<input type="hidden" name="action"      value="">

<input type="hidden" name="selectedPlacedAgentPK" value="">

</form>

<script language="javascript">
    if(selectedAgentElement)
    {
        document.getElementById(selectedAgentElement).scrollIntoView();
    }
</script>

</body>
</html>

