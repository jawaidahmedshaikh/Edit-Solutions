<%@ page import="edit.common.vo.*,
                 java.util.*,
                 fission.utility.*,
                 edit.common.*,
                 edit.portal.common.session.*,
                 agent.component.AgentComponent,
                 agent.business.Agent,
                 java.math.BigDecimal,
                 engine.ProductStructure,
                 edit.common.EDITBigDecimal"%>
<%@ page import="agent.*"%>
<%@ page import="role.*"%>
<%@ page import="client.*"%>
<%
    String agentMessage = Util.initString((String) request.getAttribute("agentMessage"), "");

    String selectedAgentHierarchyAllocationPK = Util.initString((String) request.getAttribute("selectedAgentHierarchyAllocationPK"), "");
    String selectedAgentHierarchyPK = Util.initString((String) request.getAttribute("selectedAgentHierarchyPK"), "");

    UIAgentHierarchyVO[] uiAgentHierarchyVOs = (UIAgentHierarchyVO[]) session.getAttribute("uiAgentHierarchyVOs");

    UIAgentHierarchyVO selectedUIAgentHierarchyVO = getSelectedUIAgentHierarchyVO(uiAgentHierarchyVOs, selectedAgentHierarchyAllocationPK);

    PlacedAgentVO selectedPlacedAgentVO = getLowestLevelPlacedAgent(selectedUIAgentHierarchyVO);

    AgentHierarchyVO selectedAgentHierarchyVO = getAgentHierarchy(selectedUIAgentHierarchyVO);

    AgentHierarchyAllocationVO selectedAgentHierarchyAllocationVO = getAgentHierarchyAllocation(selectedUIAgentHierarchyVO);

    BigDecimal splitPercent = Util.initBigDecimal(selectedAgentHierarchyAllocationVO, "AllocationPercent", new BigDecimal("0.00"));

    CommissionProfileVO selectedCommissionProfileVO = (selectedPlacedAgentVO != null)?(CommissionProfileVO) selectedPlacedAgentVO.getParentVO(CommissionProfileVO.class):null;

    String contractCodeCT = Util.initString(selectedCommissionProfileVO, "contractCodeCT", "");

    String stopDate = "";

    if (selectedPlacedAgentVO != null)
    {
        stopDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(selectedPlacedAgentVO.getStopDate());
    }

    String situationCode = Util.initString(selectedPlacedAgentVO, "situationCode", "");
    
//    String advancePercent = (selectedAgentHierarchyVO != null)?selectedAgentHierarchyVO.getAdvancePercent().toPlainString(): "";
//
//    String recoveryPercent = (selectedAgentHierarchyVO != null)?selectedAgentHierarchyVO.getRecoveryPercent().toPlainString(): "";
            
    String stopDateReasonCT = Util.initString(selectedPlacedAgentVO, "stopDateReasonCT", "");

    String groupAgentNumber = "";
    String groupAgentName = "";
    String groupAgentType = "";
    String rollupIndStatus = "";
    String groupCommissionProfile = "";
    String groupEffectiveDate = "";
    String groupTerminationDate = "";

    String pageMode = (String) request.getAttribute("pageMode");

    UserSession userSession = (UserSession) session.getAttribute("userSession");
    
    String businessContractName = null;
    
    if (userSession != null) {
    	ProductStructure productStructure = userSession.getCurrentProductStructure();
    	
    	if (productStructure != null) {
    		businessContractName = productStructure.getBusinessContractName();
    	}
    }

    CodeTableVO[] stopDateReasonCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("STOPDATEREASON");
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
            String firstName = Util.initString(clientDetailVO.getFirstName(), "");
            if (firstName.equals(""))
            {
                name = clientDetailVO.getLastName();
            }
            else
            {
                name = clientDetailVO.getLastName() + ", " + firstName;
            }
        }

        return name;
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

    /**
     * Finds the PlacedAgent associated with the lowest level PlacedAgent (highest hierarchy level).
     * The PlacedAgent is composed [with] AgentContract, Agent, ClientRole, ClientDetail.
     * @param agentSnapshotVOs
     * @return
     */
    private long getLowestLevelPlacedAgent(AgentSnapshotVO[] agentSnapshotVOs)
    {
        long lowestLevelPlacedAgentPK = 0;

        int highestHierarchyLevel = 0;

        for (int i = 0; i < agentSnapshotVOs.length; i++)
        {
            AgentSnapshotVO agentSnapshotVO = agentSnapshotVOs[i];

            int currentHierarchyLevel = agentSnapshotVO.getHierarchyLevel();

            if (currentHierarchyLevel >= highestHierarchyLevel)
            {
                highestHierarchyLevel = currentHierarchyLevel;

                lowestLevelPlacedAgentPK = agentSnapshotVO.getPlacedAgentFK();
            }
        }

        return lowestLevelPlacedAgentPK;
    }

    /**
     * Returns the lowest-level PlacedAgent if the specified uiAgentHierarchyVO != null composed [with]
     * the CommissionHistoryVo. Null otherwise.
     * @param uiAgentHierarchyVO
     * @return
     */
    private PlacedAgentVO getLowestLevelPlacedAgent(UIAgentHierarchyVO uiAgentHierarchyVO)
    {
        PlacedAgentVO placedAgentVO = null;

        if (uiAgentHierarchyVO != null)
        {
            AgentSnapshotVO[] agentSnapshotVOs = uiAgentHierarchyVO.getAgentHierarchyVO().getAgentSnapshotVO();

            long placedAgentPK = getLowestLevelPlacedAgent(agentSnapshotVOs);

            placedAgentVO = getPlacedAgent(placedAgentPK, true);
        }

        return placedAgentVO;
    }

    /**
     * Gets the specified PlacedAgent composed [with] AgentContract, Agent, ClientRole, ClientDetail.
     * @param placedAgentPK
     * @return
     */
    private PlacedAgentVO getPlacedAgent(long placedAgentPK, boolean includeCommissionProfile)
    {
        PlacedAgent placedAgent = null;

        if (includeCommissionProfile)
        {
            placedAgent = PlacedAgent.findBy_PK_V1(new Long(placedAgentPK));
        }
        else if (!includeCommissionProfile)
        {
            placedAgent = PlacedAgent.findBy_PK_V2(new Long(placedAgentPK));
        }

        PlacedAgentVO placedAgentVO = (PlacedAgentVO) placedAgent.getVO();

        AgentContract agentContract = placedAgent.getAgentContract();
        AgentContractVO agentContractVO = (AgentContractVO) agentContract.getVO();

        agent.Agent agent = agentContract.getAgent();
        AgentVO agentVO = (AgentVO) agent.getVO();

        ClientRole clientRole = placedAgent.getClientRole();
        ClientRoleVO clientRoleVO = (ClientRoleVO) clientRole.getVO();

        ClientDetail clientDetail = clientRole.getClientDetail();
        ClientDetailVO clientDetailVO = (ClientDetailVO) clientDetail.getVO();

        placedAgentVO.setParentVO(AgentContractVO.class, agentContractVO);
        placedAgentVO.setParentVO(ClientRoleVO.class, clientRoleVO);
        agentContractVO.setParentVO(AgentVO.class, agentVO);
        clientRoleVO.setParentVO(ClientDetailVO.class, clientDetailVO);

        if (includeCommissionProfile)
        {
            CommissionProfile commissionProfile = placedAgent.getActivePlacedAgentCommissionProfile().getCommissionProfile();
            CommissionProfileVO commissionProfileVO = (CommissionProfileVO) commissionProfile.getVO();
            placedAgentVO.setParentVO(CommissionProfileVO.class, commissionProfileVO);
        }

        return placedAgentVO;
    }

    /**
     * Returns the yes-icon if the specified AgentSnapshot has overrides, the no-icon otherwise.
     * @param agentSnapshotVO
     * @return
     */
    private String getOverrideIcon(AgentSnapshotVO agentSnapshotVO)
    {
        String overrideIcon = null;

        EDITBigDecimal commissionOverrideAmount = new EDITBigDecimal(agentSnapshotVO.getCommissionOverrideAmount());

        EDITBigDecimal commissionOverridePercent = new EDITBigDecimal(agentSnapshotVO.getCommissionOverridePercent());

        EDITBigDecimal commHoldAmountOverride = new EDITBigDecimal(agentSnapshotVO.getCommHoldAmountOverride());

        EDITDate commHoldReleaseDateOverride = null;
        if (agentSnapshotVO.getCommHoldReleaseDateOverride() != null)
        {
            commHoldReleaseDateOverride = new EDITDate(agentSnapshotVO.getCommHoldReleaseDateOverride());
        }

        if (commissionOverrideAmount.isGT("0") || commissionOverridePercent.isGT("0") || 
                commHoldAmountOverride.isGT("0") || commHoldReleaseDateOverride != null)
        {
            overrideIcon = "yesMark.gif";
        }
        else
        {
            overrideIcon = "noMark.gif";
        }

        return overrideIcon;
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
     * The associated AgentHierarchyAllocationVO of the specified UIAgentHierarchyVO.
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
     * Sorts the AgentSnapshots by HierarchyLevel ascending.
     * @param agentSnapshotVOs
     * @return
     */
    private AgentSnapshotVO[] sortByHierarchyLevel(AgentSnapshotVO[] agentSnapshotVOs)
    {
        agentSnapshotVOs = (AgentSnapshotVO[]) Util.sortObjects(agentSnapshotVOs, new String[]{"getHierarchyLevel"});

        return agentSnapshotVOs;
    }


%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

    var f = null;

    var selectedAgentHierarchyAllocationPK = "<%= selectedAgentHierarchyAllocationPK %>";
    var selectedAgentHierarchyPK = "<%= selectedAgentHierarchyPK %>";

    var agentMessage = "<%= agentMessage %>";

    var pageMode = "<%= pageMode %>";

    var shouldShowLockAlert = true;
    
    var editableContractStatus = true;

    var agentIsLocked = <%= userSession.getAgentIsLocked()%>;

    function init()
    {
    	f = document.theForm;

        top.frames["main"].setActiveTab("agentTab");

        shouldShowLockAlert = <%= ! userSession.getSegmentIsLocked() %>;
        
     	// check for terminated status to disallow contract updates
        editableContractStatus = <%= userSession.getEditableSegmentStatus()%>;

        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if ( (elementType == "text" || elementType == "checkbox" || elementType == "button") && (shouldShowLockAlert == true || editableContractStatus == false) )
            {
                f.elements[i].onclick = showLockAlert;

                f.elements[i].onkeydown = showLockAlert;
            }
        }

        if (agentMessage != "")
        {
            alert(agentMessage);
        }
    }

    function showLockAlert(){

        if (shouldShowLockAlert == true){

            alert("The Agent can not be edited - Contract Not Locked.");

            return false;
            
        } else if (editableContractStatus == false) {
        	
        	alert("This Contract Cannot Be Edited Due to Terminated Status.");

            return false;
        }
    }

    function setButtonState()
    {
        if (pageMode == "BROWSE")
        {
            f.contractCode.contentEditable = false;
            f.stopDate.contentEditable = false;
            f.situation.contentEditable = false;
        }

        else if (pageMode == "ADD")
        {
            f.contractCode.contentEditable = false;
            f.stopDate.contentEditable = false;
            f.situation.contentEditable = false;
        }

        else if (pageMode == "SELECT")
        {
            f.contractCode.contentEditable = true;
            f.stopDate.contentEditable = true;
            f.situation.contentEditable = true;
        }
    }

    function showMessage()
    {
        if (message != "null")
        {
            alert(message);
        }
    }

    function scrollActiveEntriesIntoView()
    {
        if (selectedAgentHierarchyAllocationPK != "")
        {
            document.getElementById(selectedAgentHierarchyAllocationPK).scrollIntoView(false);
        }
    }

    function multiSelectDeselectRow()
    {
        var tdElement = window.event.srcElement;

        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }

        var className = currentRow.className;

        if (currentRow.isSelected == "false")
        {
            currentRow.style.backgroundColor = "#FFFFCC";

            currentRow.isSelected = "true";
        }
        else if (currentRow.isSelected == "true")
        {
            if (className == "default")
            {
                currentRow.style.backgroundColor = "#BBBBBB";
            }
            else if (className == "associated")
            {
                currentRow.style.backgroundColor = "#00BB00";
            }

            currentRow.isSelected = "false";
        }
    }

    /**
     * Shows the detail of the currently selected AgentHierarchy entry.
     */
    function showAgentDetailSummary()
    {
        var tdElement = window.event.srcElement;

        var currentRow = tdElement.parentElement;

        f.selectedAgentHierarchyAllocationPK.value = currentRow.id;

        prepareToSendTransactionAction("ContractDetailTran", "showAgentDetailSummary", "contentIFrame");
    }

<%--    /**--%>
<%--     * Adds a new AgentHierarchy/AgentSnapshot to the current policy.--%>
<%--     */--%>
<%--	function addNewAgent()--%>
<%--    {--%>
<%--        var width = 0.90 * screen.width;--%>
<%----%>
<%--        var height= 0.65 * screen.height;--%>
<%----%>
<%--        openDialog("","agentSelection","top=0,left=0,resizable=no,width=" + width + ",height=" + height,"ContractDetailTran","showAgentSelectionDialog");--%>
<%--	}--%>

    /**
     * Pops-open the specified dialog.
     */
	function openDialog(theURL,winName,features,transaction,action) {

        dialog = window.open(theURL,winName,features);

	    prepareToSendTransactionAction(transaction, action, winName);
	}

    /**
     * Opens the dialog that allows the overriding of Commission Amount and Commission Percent.
     */
    function showCommissionOverrides()
    {
        var imgElement = window.event.srcElement;

        var tdElement = imgElement.parentElement;

        var currentRow = tdElement.parentElement;

        f.selectedAgentSnapshotPK.value = currentRow.id;

        var width = .60 * screen.width;
        var height= .20 * screen.height;

        openDialog("","commissionOverrides","top=0,left=0,width=" + width + ",height=" + height,"ContractDetailTran","showCommissionOverrides");
    }


<%--    /**--%>
<%--     * Cancels current AgentHierarchy edits.--%>
<%--     */--%>
<%--	function cancelAgentInfo()--%>
<%--    {--%>
<%--		prepareToSendTransactionAction("ContractDetailTran", "clearContractAgentForm", "contentIFrame");--%>
<%--	}--%>

<%--    /**--%>
<%--     * Deletes the current AgentHierarchy.--%>
<%--     */--%>
<%--	function deleteSelectedAgent()--%>
<%--    {--%>
<%--        if (selectedAgentHierarchyPK == "")--%>
<%--        {--%>
<%--            alert("Agent Hierarchy Required");--%>
<%--        }--%>
<%--        else--%>
<%--        {--%>
<%--    		prepareToSendTransactionAction("ContractDetailTran", "deleteSelectedAgent", "contentIFrame");--%>
<%--        }--%>
<%--	}--%>

    /**
     * Brings up the AgentHierarchyAllocation dialog
     */
    function adjustAllocations()
    {
        var width = 0.90 * screen.width;

        var height= 0.65 * screen.height;

        openDialog("","contractAgentHierarchyAllocation","top=0,left=0,resizable=no,width=" + width + ",height=" + height,"ContractDetailTran","showAgentHierarchyAllocationDialog");
	}

    function prepareToSendTransactionAction(transaction, action, target)
    {
        sendTransactionAction(transaction, action, target);
    }

<%--    /**--%>
<%--     * Updates the AgentHierachy details to Cloudland.--%>
<%--     */--%>
<%--	function saveAgentToSummary() {--%>
<%----%>
<%--        if (selectedAgentHierarchyPK == "")--%>
<%--        {--%>
<%--            alert("Agent Hierarchy Required");--%>
<%--        }--%>
<%--        else--%>
<%--        {--%>
<%--            prepareToSendTransactionAction("ContractDetailTran", "saveAgentToSummary", "contentIFrame");--%>
<%--        }--%>
<%--	}--%>

</script>

<title>Report-To</title>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

</head>


<body class="mainTheme" onLoad="init()">

<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<jsp:include page="contractInfoHeader.jsp" flush="true"/>

<table border="0" width="100%" height="90%">

    <tr height="40%">
        <td align="left" colspan="2">

        <span class="formData" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">

                <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

                     <tr>
                        <td align="right" nowrap>
                            Contract Code:&nbsp;
                        </td>
                        <td align="left">
                            <input type="text" name="contractCode" value="<%= contractCodeCT %>" length="20" maxlength="20" disabled=true>
                        </td>
                        <td rowspan="6" width="70%">
            <!-- Currently selected hierarchy  -->
              <table id="reportToHierarchiesTable" border="0" class="summary" style="border-width:0px; border-color:#BBBBBB"  width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                <tr class="heading">
                  <th width="13%">Name</th>
                  <th width="13%">Agent #</th>
                  <th width="13%">Situation</th>
                  <th width="13%">Agent Type</th>
                  <th width="13%">Stop</th>
                  <th width="13%">Level</th>
                  <th width="13%">Option</th>
                  <th width="9%">Ovrds</th>
                </tr>
                <tr>
                  <td height="99%" colspan="8">
                    <span class="scrollableContent">
                      <table class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                      <%
                        if (selectedUIAgentHierarchyVO != null)
                        {
                            AgentHierarchyVO agentHierarchyVO = selectedUIAgentHierarchyVO.getAgentHierarchyVO();

                            AgentSnapshotVO[] agentSnapshotVOs = sortByHierarchyLevel(agentHierarchyVO.getAgentSnapshotVO());

                            for (int i = 0; i < agentSnapshotVOs.length; i++)
                            {
                                // Need # 1
                                PlacedAgentVO currentPlacedAgentVO = getPlacedAgent(agentSnapshotVOs[i].getPlacedAgentFK(), true);

                                // Need # 2
                                AgentVO currentAgentVO = (AgentVO) currentPlacedAgentVO.getParentVO(AgentContractVO.class).getParentVO(AgentVO.class);

                                // Need # 3
                                CommissionProfileVO currentCommissionProfileVO = (CommissionProfileVO) currentPlacedAgentVO.getParentVO(CommissionProfileVO.class);

                                String currentCommissionLevel = Util.initString(currentCommissionProfileVO.getCommissionLevelCT(), "");

                                String currentCommissionOption = Util.initString(currentCommissionProfileVO.getCommissionOptionCT(), "");

                                // Need # 4
                                ClientRole clientRole = ClientRole.findByPK(currentPlacedAgentVO.getClientRoleFK());
                                ClientDetail clientDetail = ClientDetail.findByPK(clientRole.getClientDetailFK());
                                ClientDetailVO currentClientDetailVO = (ClientDetailVO) clientDetail.getVO();

                                String currentAgentId = Util.initString(clientRole.getReferenceID(), "&nbsp;");
                                String currentAgentTypeCT = Util.initString(currentAgentVO.getAgentTypeCT(), "&nbsp;");
                                String lastName = Util.initString(getClientDetailName(currentClientDetailVO), "&nbsp;");
                                String currentSituation = Util.initString(currentPlacedAgentVO.getSituationCode(), "&nbsp;");
                                String currentStopDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(Util.initString(currentPlacedAgentVO.getStopDate(), "&nbsp;"));

                                long currentAgentSnapshotPK = Util.initLong(agentSnapshotVOs[i], "agentSnapshotPK", 0);

                                boolean showAsDisabled = showAsDisabled(currentStopDate);

                                String className = null;

                                String overrideIcon = getOverrideIcon(agentSnapshotVOs[i]);

                                if (i == (agentSnapshotVOs.length - 1)) // If it's the last element, then it's the lowest level agent.
                                {
                                    className = "highlighted";
                                }
                                else
                                {
                                    className = "default";
                                }
                      %>
                        <tr id="<%= currentAgentSnapshotPK %>" <%= (showAsDisabled)?"class='disabled + " + className + "'":"class='" + className + "'" %> >
                          <td width="13%">
                            <%= lastName %>
                          </td>
                          <td widsth="13%">
                            <%= currentAgentId %>
                          </td>
                          <td width="13%">
                            <%= currentSituation %>
                          </td>
                          <td width="13%">
                            <%= currentAgentTypeCT %>
                          </td>
                          <td width="13%">
                            <%= currentStopDate %>
                          </td>
                          <td width="13%">
                            <%= currentCommissionLevel %>
                          </td>
                          <td width="13%">
                            <%= currentCommissionOption %>
                          </td>
                          <td width="9%" align="center">
                            <img src="/PORTAL/common/images/<%= overrideIcon %>" width="22" height="22" onClick="showCommissionOverrides()" onmouseover="this.style.cursor='hand'">
                          </td>
                        </tr>
                      <%
                            }
                        }
                      %>
                        <tr class="filler"> <!-- A dummy row to help with sizing -->
                          <td colspan="8">
                            &nbsp;
                          </td>
                        </tr>
                      </table>
                    </span>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
                    <tr>
                        <td align="right">
                            Stop-Date:&nbsp;
                        </td>
                        <td align="left" nowrap>
                            <input disabled type="text" name="stopDate" value="<%= stopDate %>" size="10" maxlength="10">
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            Situation:&nbsp;
                        </td>
                        <td align="left">
                            <input type="text" name="situation" value="<%= situationCode %>" length="20" maxlength="15" disabled=true>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            Split Percent:&nbsp;
                        </td>
                        <td align="left">
                            <input type="text" name="splitPercent" maxlength="5" size="10" value="<%= splitPercent %>" disabled=true>
                        </td>
                    </tr>
                    <tr>
                    <td align="right">
                        Stop Date Reason:&nbsp;
                    </td>
                    <td align="left">
                        <select name="stopDateReasonCT" disabled=true>
                        <option name="id" value="">Please Select</option>
                <%
                      if (stopDateReasonCTs != null){

                       for (int i = 0; i < stopDateReasonCTs.length; i++){

                            String currentCode = stopDateReasonCTs[i].getCode();
                            String currentCodeDescription = stopDateReasonCTs[i].getCodeDesc();

                            if (stopDateReasonCT.equals(currentCode))
                            {
                                out.println("<option selected name=\"id\" value=\"" + currentCode + "\">" + currentCodeDescription + "</option>");
                            }
                            else
                            {
                                out.println("<option name=\"id\" value=\"" + currentCode + "\">" + currentCodeDescription + "</option>");
                            }
                      }
                  }
              %>
                   </select>
            </td>
          </tr>
          <tr>
            <td colspan="2">&nbsp;</td>
          </tr>
        </table>
      </span>
    </td>
  </tr>


    <tr height="1%">
        <td width="40%" align="left">
            <br>
            <input type="button" name="adjustAllocationsButton" value="Adjust Allocations" onClick="adjustAllocations()">
        </td>
        <td>
           <br>
           <font size="3" align="left">Hierarchies</font>
        </td>
     </tr>

   <tr>
       <td width="100%" NOWRAP colspan="2">

            <!-- Summary Table for Report To Hierarchies  -->
                <table id="agentHierarchyTable" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr class="heading">
                        <th width="14%">
                            Name
                        </th>
                        <th width="14%">
                            Agent #
                        </th>
                        <th width="14%">
                            Agent Type
                        </th>
                        <th width="14%">
                            Split Percent
                        </th>
                        <th width="14%">
                            Allocation Stop-Date
                        </th>
                        <th width="14%">
                            Coverage
                        </th>
                        <th width="14%">
                            Segment Effective Date
                        </th>
                    </tr>
                    <tr>
                        <td height="99%" colspan="7">
                            <span class="scrollableContent">
                                <table class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

<%
                 if (uiAgentHierarchyVOs != null)
                 {
                     for (int i = 0; i < uiAgentHierarchyVOs.length; i++)
                     {
                         String coverage = uiAgentHierarchyVOs[i].getCoverage();
                         
                         String[] covSplit = coverage.split("_");
                         if (covSplit != null && covSplit.length > 1) {
                        	 coverage = covSplit[1];
                         }
                         
                         String segmentEffectiveDate = uiAgentHierarchyVOs[i].getSegmentEffectiveDate();

                         AgentHierarchyAllocationVO agentHierarchyAllocationVO = uiAgentHierarchyVOs[i].getAgentHierarchyAllocationVO();

                         AgentHierarchyVO agentHierarchyVO = uiAgentHierarchyVOs[i].getAgentHierarchyVO();

                         AgentSnapshotVO[] agentSnapshotVOs = agentHierarchyVO.getAgentSnapshotVO();

                         // Need # 1
                         long currentPlacedAgentPK = getLowestLevelPlacedAgent(agentSnapshotVOs);

                         PlacedAgentVO currentPlacedAgentVO = getPlacedAgent(currentPlacedAgentPK, true);

                         // Need # 2
                         AgentVO currentAgentVO = (AgentVO) currentPlacedAgentVO.getParentVO(AgentContractVO.class).getParentVO(AgentVO.class);

                         // Need # 3
                         ClientRole clientRole = ClientRole.findByPK(currentPlacedAgentVO.getClientRoleFK());
                         ClientDetail clientDetail = ClientDetail.findByPK(clientRole.getClientDetailFK());
                         ClientDetailVO currentClientDetailVO = (ClientDetailVO) clientDetail.getVO();

                          String currentAgentId = Util.initString(clientRole.getReferenceID(), "&nbsp;");
                          String currentAgentTypeCT = Util.initString(currentAgentVO.getAgentTypeCT(), "&nbsp;");
                          String lastName = Util.initString(getClientDetailName(currentClientDetailVO), "&nbsp;");
                          String currentStopDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(Util.initString(agentHierarchyAllocationVO.getStopDate(), "&nbsp;"));

                          EDITBigDecimal allocationPercent = new EDITBigDecimal(agentHierarchyAllocationVO.getAllocationPercent());

                          String currentAgentHierarchyAllocationPK = agentHierarchyAllocationVO.getAgentHierarchyAllocationPK() + "";

                          boolean showAsDisabled = showAsDisabled(currentStopDate);

                          boolean isSelected = false;

                          String className = null;


                            if (currentAgentHierarchyAllocationPK.equals(selectedAgentHierarchyAllocationPK)) // If it's the last element, then it's the lowest level agent.
                            {
                                className = "highlighted";

                                isSelected = true;
                            }
                            else
                            {
                                className = "default";
                            }
%>
                            <tr class="<%= className %>" id="<%= currentAgentHierarchyAllocationPK %>" placedAgentBranchPK="<%= currentAgentHierarchyAllocationPK %>" 
                            	isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="showAgentDetailSummary()">
                                
                                <td width="14%">
                                    <%= lastName %>
                                </td>
                                <td width="14%" <%= (showAsDisabled)?"class='disabled'":"" %> >
                                    <%= currentAgentId %>
                                </td>
                                <td width="14%" <%= (showAsDisabled)?"class='disabled'":"" %>>
                                    <%= currentAgentTypeCT %>
                                </td>
                                <td width="14%">
                                    <%= allocationPercent.toString() %>
                                </td>
                                <td width="14%">
                                    <%= currentStopDate %>
                                </td>
                                <td width="14%">
                                    <%= coverage %>
                                </td>
                                <td width="14%">
                                    <%= DateTimeUtil.formatYYYYMMDDToMMDDYYYY(segmentEffectiveDate) %>
                                </td>
                            </tr>
<%
                        }
                    }
%>
                            <tr class="filler"> <!-- A dummy row to help with sizing -->
                                <td colspan="4">
                                    &nbsp;
                                </td>
                            </tr>
                        </table>
                    </span>
                    </td>
                    </tr>
                </table>
        </td>
    </tr>
</table>

<input type="hidden" name="transaction" value="">
<input type="hidden" name="action" value="">

<input type="hidden" name="selectedAgentHierarchyAllocationPK" value="<%= selectedAgentHierarchyAllocationPK %>">
<input type="hidden" name="selectedAgentHierarchyPK" value="<%= selectedAgentHierarchyPK %>">
<input type="hidden" name="selectedAgentSnapshotPK" value="">

 <!-- recordPRASEEvents is set by the toolbar -->
 <input type="hidden" name="recordPRASEEvents" value="false">

</form>

</body>
</html>


