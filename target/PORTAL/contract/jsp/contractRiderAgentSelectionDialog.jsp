<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 fission.utility.*,
                 contract.AgentHierarchyAllocation,
                 role.*,
                 client.*" %>

<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>
<jsp:useBean id="contractRiders"
    class="fission.beans.SessionBean" scope="session"/>

<%
    String companyStructureId = contractMainSessionBean.getPageBean("formBean").getValue("companyStructureId");

    // CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    // String startDate = (String) request.getAttribute("startDate");

    String agentMessage = (String) request.getAttribute("agentMessage");
    if (agentMessage == null) {

        agentMessage = "";
    }

    String agentNumber 	= "";
	String agentType	= "";
    String lastName     = "";
    String firstName    = "";
    String middleName   = "";
    
    String newRiderBeanKey = contractMainSessionBean.getValue("newRiderBeanKey");

    PageBean riderBean = contractRiders.getPageBean(newRiderBeanKey);
    String coverage = riderBean.getValue("coverage");
    String startDate = (String) riderBean.getValue("effectiveDate");

    AgentVO agentVO = (AgentVO) session.getAttribute("selectedAgentVO");

    if (agentVO != null) {

        ClientDetailVO clientDetailVO = (ClientDetailVO) agentVO.getParentVO(ClientRoleVO.class).getParentVO(ClientDetailVO.class);
        agentNumber = Util.initString(agentVO.getAgentNumber(), "");
        agentType = Util.initString(agentVO.getAgentTypeCT(), "");
        lastName = Util.initString(clientDetailVO.getLastName(), "");
        firstName = Util.initString(clientDetailVO.getFirstName(), "");
        middleName = Util.initString(clientDetailVO.getMiddleName(), "");
    }

    PlacedAgentBranchVO[] placedAgentBranchVOs = (PlacedAgentBranchVO[]) session.getAttribute("placedAgentBranchVOs");
    
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

	/**
	 * The associated AgentVO of the specified UIAgentHierarchyVO.
	 * @param uiAgentHierarchyVO
	 * @return
	 */
	private AgentVO getAgent(UIAgentHierarchyVO uiAgentHierarchyVO)
	{
	    AgentVO agentVO = null;
	
	    if (uiAgentHierarchyVO != null)
	    {
	        agentVO = uiAgentHierarchyVO.getAgentVO();
	    }
	
	    return agentVO;
	}
	
	 private String getLastName(AgentVO agentVO)
    {
        ClientRole[] clientRoles = ClientRole.findByAgentFK(agentVO.getAgentPK());
                            
        ClientDetail clientDetail = ClientDetail.findByPK(new Long(clientRoles[0].getClientDetailFK()));

        String lastName = Util.initString(getClientDetailName(clientDetail), "&nbsp;");
        
        return lastName;
	 }
	 

    private String getClientDetailName(ClientDetail clientDetail)
    {
        String name = null;

        if (clientDetail.getTrustTypeCT().equalsIgnoreCase("CorporateTrust") || 
            clientDetail.getTrustTypeCT().equalsIgnoreCase("Corporate") ||
            clientDetail.getTrustTypeCT().equalsIgnoreCase("LLC"))
        {
            name = clientDetail.getCorporateName();
        }
        else
        {
            String firstName = Util.initString(clientDetail.getFirstName(), "");
            if (firstName.equals(""))
            {
                name = clientDetail.getLastName();
            }
            else
            {
                name = clientDetail.getLastName() + ", " + firstName;
            }
        }

        return name;
    }

	/**
	 * The associated AgentHierarchyVO of the specified UIAgentHierarchyVO.
	 * @param uiAgentHierarchyVO
	 * @return
	 */
	private AgentHierarchyVO getAgentHierarchy(UIAgentHierarchyVO uiAgentHierarchyVO)
	{
	    AgentHierarchyVO agentHierarchyVO = null;
	
	    if (uiAgentHierarchyVO != null)
	    {
	        agentHierarchyVO = uiAgentHierarchyVO.getAgentHierarchyVO();
	    }
	
	    return agentHierarchyVO;
	}

	/**
	 * The associated AgentHierarchyAllocaitonVO of the specified UIAgentHierarchyVO.
	 * @param uiAgentHierarchyVO
	 * @return
	 */
	private AgentHierarchyAllocationVO getAgentHierarchyAllocation(UIAgentHierarchyVO uiAgentHierarchyVO)
	{
	    AgentHierarchyAllocationVO agentHierarchyAllocationVO = null;
	
	    if (uiAgentHierarchyVO != null)
	    {
	        agentHierarchyAllocationVO = uiAgentHierarchyVO.getAgentHierarchyAllocationVO();
	    }
	
	    return agentHierarchyAllocationVO;
	}

	/**
	 * Finds the active UIAgentHierarchyVO in the set of UIAgentHierarchyVOs.
	 * @param uiAgentHierarchyVOs
	 * @param selectedAgentHierarchyAllocationPK
	 * @return
	 */
	private UIAgentHierarchyVO getSelectedUIAgentHierarchyVO(UIAgentHierarchyVO[] uiAgentHierarchyVOs, String selectedAgentHierarchyAllocationPK)
	{
	    UIAgentHierarchyVO uiAgentHierarchyVO = null;
	
	    if (uiAgentHierarchyVOs != null)
	    {
	        for (int i = 0; i < uiAgentHierarchyVOs.length; i++)
	        {
	            UIAgentHierarchyVO currentUIAgentHierarchyVO = uiAgentHierarchyVOs[i];
	
	            String currentAgentHierarchyAllocationPK = currentUIAgentHierarchyVO.getAgentHierarchyAllocationVO().getAgentHierarchyAllocationPK() + "";
	
	            if (currentAgentHierarchyAllocationPK.equals(selectedAgentHierarchyAllocationPK))
	            {
	                uiAgentHierarchyVO = currentUIAgentHierarchyVO;
	            }
	        }
	    }
	
	    return uiAgentHierarchyVO;
	}

    /**
     * Performs date comparison between the date passed-in and the current date.
     * @param stopDate
     * @return true if stopDate is less than the current date
     */
    private boolean showAsDisabled(String stopDate)
    {
        boolean showAsDisabled = false;

        if ( (stopDate != null) && EDITDate.isACandidateDate(stopDate))
        {
            EDITDate currentDate = new EDITDate();

            EDITDate currentEDITStopDate = new EDITDate(stopDate);

            if (currentEDITStopDate.before(currentDate))
            {
                showAsDisabled = true;
            }
        }

        return showAsDisabled;
    }

%>

<html>

<!-- ***** JAVASCRIPT *****  -->

<script language="Javascript1.2">

    var f = null;
    var agentMessage = "<%= agentMessage %>";

    var colorMouseOver = "#00BB00";
    var colorHighlighted = "#FFFFCC";
    var colorMainEntry   = "#BBBBBB";

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

        f.agentId.focus();
    }

    function getAgentInfo() {

            sendTransactionAction("ContractDetailTran", "showReportToAgent", "_self");
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
    }

	function openDialog(theURL,winName,features,transaction,action) {

	  dialog = window.open(theURL,winName,features);

	  sendTransactionAction(transaction, action, winName);
	}

    function checkForEnter(){

        var eventObj = window.event;

        if (eventObj.keyCode == 13){

            doSearch();
        }
    }

    function saveAgentSelection(){

        if (f.selectedPlacedAgentPK.value == "") {

            alert("Please Select Report To Agent");
        } else {

        	var width = 0.90 * screen.width;

            var height= 0.65 * screen.height;
            
        	openDialog("","contractAgentHierarchyAllocation","top=5,left=5,resizable=yes,width=" + width + ",height=" + height,"ContractDetailTran","saveAgentSelection");    
        	
            // sendTransactionAction("ContractDetailTran","saveAgentSelection","contractAgentHierarchyAllocation");
            window.close();
        }
    }

    function cancelAgentSelectionDialog(){

        sendTransactionAction("ContractDetailTran","closeAgentSelectionDialog","contentIFrame");
        window.close();
    }

    function sendTransactionAction(transaction, action, target) {

		f.transaction.value = transaction;
    	f.action.value = action;

    	f.target = target;

    	f.submit();
    }

</script>

<head>
<title>Select Agent For Rider</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()">

<form  name="agentSelectionForm" method="post" action="/PORTAL/servlet/RequestManager">

  <table class="formData" width="100%" height="20%" border="0" cellspacing="0" cellpadding="4">
    <tr>
	  <td align="right" nowrap>Agent Number:&nbsp;
	  </td>
      <td align="left">
		<input type="text" name="agentId" maxlength="11" size="11" value="<%= agentNumber %>">
      </td>
      <td align="right" nowrap>
            Agent Type:&nbsp;
      </td>
	  <td align="left" nowrap>
		<input disabled type="text" name="agentType" maxlength="20" size="20" value="<%= agentType %>">
	  </td>
      <td>
            &nbsp;
      </td>
      <td>
            &nbsp;
      </td>
      <td width="25%">
           &nbsp;
      </td>
	</tr>
	<tr>
	  <td align="right" nowrap>Last Name:&nbsp;
        &nbsp;&nbsp;
	  </td>
      <td align="left">
	    <input disabled type="text" name="lastName" maxlength="30" size="30" value="<%= lastName %>">
      </td>
      <td align="right" nowrap>
  	    First Name:&nbsp;
      </td>
      <td align="left">
	    <input disabled type="text" name="firstName" maxlength="15" size="15" value="<%= firstName %>">
      </td>
      <td align="right" nowrap>
        Middle Name:&nbsp;
      </td>
      <td align="left">
	    <input disabled type="text" name="middleName" maxlength="15" size="15" value="<%= middleName %>">
      </td>
      <td width="25%">
           &nbsp;
      </td>
	</tr>
    <tr>
        <td colspan="7" align="right">
            <input type="button" value="Enter" onClick="getAgentInfo()">
        </td>
    </tr>
  </table>

  <br>
  <br>

  <table class="summary" width="100%" height="60%" border="0" cellspacing="0" cellpadding="0">
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

                    for (int b = 0; b < placedAgentBranchVOs.length; b++) {


                        int placedAgentCount = placedAgentBranchVOs[b].getPlacedAgentVOCount();
                        int paIndex = 0;
                        if (placedAgentCount > 1) {

                            paIndex = placedAgentCount - 2;
                        }
                        else {

                            paIndex = placedAgentCount - 1;
                        }

                        PlacedAgentVO reportToPlacedAgentVO = placedAgentBranchVOs[b].getPlacedAgentVO(paIndex);  // ReportTo Agent

                        PlacedAgentVO writingPlacedAgentAgentVO = placedAgentBranchVOs[b].getPlacedAgentVO(placedAgentCount - 1);  // Writing Agent

                        String stopDate = writingPlacedAgentAgentVO.getStopDate();

                        if (showAsDisabled(stopDate))
                        {
							continue;
						}

                        String writingAgentPK = writingPlacedAgentAgentVO.getPlacedAgentPK() + "";
                        String situation = Util.initString(writingPlacedAgentAgentVO.getSituationCode(), "&nbsp;");

                        // Get the Report-To info
                        AgentContractVO agentContract = (AgentContractVO) reportToPlacedAgentVO.getParentVO(AgentContractVO.class);
                        AgentVO agent = (AgentVO) agentContract.getParentVO(AgentVO.class);
                        ClientRole clientRole = ClientRole.findByPK(new Long(reportToPlacedAgentVO.getClientRoleFK()));
                        ClientDetail clientDetail = clientRole.getClientDetail();
                        String contractCode = agentContract.getContractCodeCT();
                        String reportToAgentId = clientRole.getReferenceID();
                        String reportToName = Util.initString(getClientDetailName((ClientDetailVO) clientDetail.getVO()), "");
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
  
  <table width="100%" height="2%">
    <tr>
      <td align="right">
        <input type="button" value="Enter" onClick="saveAgentSelection()">
        <input type="button" value="Cancel" onClick="cancelAgentSelectionDialog()">
      </td>
    </tr>
  </table>
  
</table>

<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction" value="">
<input type="hidden" name="action"      value="">

<input type="hidden" name="selectedPlacedAgentPK" value="">
<input type="hidden" name="startDate" value="<%= startDate %>">
<input type="hidden" name="coverage" value="<%= coverage %>">

</form>
</body>
</html>
