<!--
 This page displays the new agent hierarchy allocations and allows the user to edit them.
 -->
<%@ page import="edit.common.vo.*,
                 java.util.*,
                 fission.utility.*,
                 edit.common.*,
                 edit.portal.common.session.*,
                 agent.component.AgentComponent,
                 java.math.BigDecimal,
                 fission.beans.PageBean,
                 contract.*,
                 agent.*,
                 client.*,
                 role.*"%>

<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>
<jsp:useBean id="contractRiders"
    class="fission.beans.SessionBean" scope="session"/>
    
<%
    //  Get information from Request and Session scope
    String agentMessage = Util.initString((String) request.getAttribute("agentMessage"), "");

    String selectedAgentHierarchyAllocationPK = Util.initString((String) request.getAttribute("selectedAgentHierarchyAllocationPK"), "");

    //???
    String newRiderBeanKey = contractMainSessionBean.getValue("newRiderBeanKey");

    PageBean riderBean = contractRiders.getPageBean(newRiderBeanKey);
    String startDate = Util.initString((String) riderBean.getValue("effectiveDate"), "");

    //end???
    		
    UIAgentHierarchyVO[] tempUIAgentHierarchyVOs = (UIAgentHierarchyVO[]) session.getAttribute("tempUIAgentHierarchyVOs");

    //  Determine the which objects (if any) have already been selected by the user
    UIAgentHierarchyVO selectedUIAgentHierarchyVO = getSelectedUIAgentHierarchyVO(tempUIAgentHierarchyVOs, selectedAgentHierarchyAllocationPK);
    AgentHierarchyAllocationVO selectedAgentHierarchyAllocationVO = getAgentHierarchyAllocation(selectedUIAgentHierarchyVO);
    AgentHierarchyVO selectedAgentHierarchyVO = getAgentHierarchy(selectedUIAgentHierarchyVO);
    AgentVO selectedAgentVO = getAgent(selectedUIAgentHierarchyVO);

    //  Get parameters for selected allocation
    EDITBigDecimal selectedAllocationPercent = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);
    String selectedLastName = "";
    String selectedAgentNumber = "";

    if (! selectedAgentHierarchyAllocationPK.equals(""))
    {
        selectedAllocationPercent = new EDITBigDecimal(selectedAgentHierarchyAllocationVO.getAllocationPercent());
        selectedLastName = getLastName(selectedAgentVO);
        selectedAgentNumber = Util.initString(selectedAgentVO.getAgentNumber(), "");
    }

    //  Set some defaults
    EDITDate currentDate = new EDITDate();
    EDITBigDecimal totalAllocationPercent = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);
	Map<String, EDITBigDecimal> allocationMap = new HashMap<String, EDITBigDecimal>();

    String pageMode = (String) request.getAttribute("pageMode");
    UserSession userSession = (UserSession) session.getAttribute("userSession");

    boolean validAllocations = false;
%>
<%!
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
     * Performs date comparison between the date passed-in and the current date.
     * @param stopDate
     * @return true if stopDate is less than the current date
     */
    private boolean showAsDisabled(EDITDate stopDate)
    {
        boolean showAsDisabled = false;

//        if ( (stopDate != null) && Util.isADate(stopDate))
//        {
//            EDITDate currentDate = new EDITDate(EDITDate.getCurrentDate());
//
//            EDITDate currentEDITStopDate = new EDITDate(stopDate);
//
//            if (currentEDITStopDate.ifLess(currentDate))
//            {
//                showAsDisabled = true;
//            }
//        }

        return showAsDisabled;
    }

    private String getLastName(AgentVO agentVO)
    {
        ClientRole[] clientRoles = ClientRole.findByAgentFK(agentVO.getAgentPK());
                            
        ClientDetail clientDetail = ClientDetail.findByPK(new Long(clientRoles[0].getClientDetailFK()));

        String lastName = Util.initString(getClientDetailName(clientDetail), "&nbsp;");
        
        return lastName;
    }

%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<script language="Javascript1.2">

    var f = null;

    var selectedAgentHierarchyAllocationPK = "<%= selectedAgentHierarchyAllocationPK %>";

    var agentMessage = "<%= agentMessage %>";

    var pageMode = "<%= pageMode %>";

    var shouldShowLockAlert = true;

    var agentIsLocked = <%= userSession.getAgentIsLocked()%>;

    var totalAllocationPercentString = "<%= totalAllocationPercent.toString() %>";

    var validAllocations = <%= validAllocations %>;

    function init()
    {
    	f = document.theForm;

        shouldShowLockAlert = <%= ! userSession.getSegmentIsLocked() %>;

        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if ( (elementType == "text" || elementType == "checkbox" || elementType == "button") && (shouldShowLockAlert == true) )
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

    function showLockAlert()
    {
        if (shouldShowLockAlert == true){

            alert("The Agent can not be edited - Contract Not Locked.");

            return false;
        }
    }

    function setButtonState()
    {
        if (pageMode == "BROWSE")
        {
            f.addButton.disabled = false;
            f.saveButton.disabled = true;
            f.cancelButton.disabled = true;
            f.deleteButton.disabled = true;
            f.splitPercent.contentEditable = false;
        }

        else if (pageMode == "ADD")
        {
            f.addButton.disabled = false;
            f.saveButton.disabled = true;
            f.cancelButton.disabled = true;
            f.deleteButton.disabled = true;
            f.splitPercent.contentEditable = false;

        }

        else if (pageMode == "SELECT")
        {
            f.addButton.disabled = false;
            f.saveButton.disabled = false;
            f.cancelButton.disabled = false;
            f.deleteButton.disabled = false;
            f.splitPercent.contentEditable = true;
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
     * Shows the detail of the currently selected AgentHierarchyAllocation entry.
     */
    function showAgentHierarchyAllocationDetail()
    {
        var tdElement = window.event.srcElement;

        var currentRow = tdElement.parentElement;

        f.selectedAgentHierarchyAllocationPK.value = currentRow.id;

        prepareToSendTransactionAction("ContractDetailTran", "showAgentHierarchyAllocationDetail", "_self");
    }

    /**
     * Pops-open the specified dialog.
     */
	function openDialog(theURL,winName,features,transaction,action)
    {
        dialog = window.open(theURL,winName,features);

	    prepareToSendTransactionAction(transaction, action, winName);
	}

    /**
     * Adds a new AgentHierarchy/AgentSnapshot to the current policy.
     */
	function addNewAgent()
    {
        if (f.startDate.value == "")
        {
            alert("Start date is required.  Please go back and select an effective date for this new rider.");
        }
        else
        {
            var width = 0.90 * screen.width;

            var height= 0.65 * screen.height;

            openDialog("","agentSelection","top=10,left=10,resizable=yes,width=" + width + ",height=" + height,"ContractDetailTran","showContractRiderAgentSelectionDialog");
        }
	}

    /**
     * Updates the AgentHierachy details to Cloudland.
     */
	function saveAllocationToSummary()
    {
        if (selectedAgentHierarchyAllocationPK == "")
        {
            alert("Agent Hierarchy Required");
        }
        else
        {
            if (f.startDate.value == "")
            {
                alert("Start date is required.  Please go back and select an effective date for this new rider.");
            }
            else
            {
                prepareToSendTransactionAction("ContractDetailTran", "saveAllocationToSummary", "_self");
            }
        }
	}

    /**
     * Cancels current Allocation edits.
     */
	function cancelAllocation()
    {
		prepareToSendTransactionAction("ContractDetailTran", "clearAllocationForm", "_self");
	}

    /**
     * Deletes the current AgentHierarchy.
     */
	function deleteSelectedAllocation()
    {
        if (selectedAgentHierarchyAllocationPK == "")
        {
            alert("Agent Hierarchy Required");
        }
        else
        {
            if (f.startDate.value == "")
            {
                alert("Start date is required.  Please go back and select an effective date for this new rider.");
            }
            else
            {
                prepareToSendTransactionAction("ContractDetailTran", "deleteSelectedAllocation", "_self");
            }
        }
	}

    function prepareToSendTransactionAction(transaction, action, target)
    {
        sendTransactionAction(transaction, action, target);
    }

    /**
     * Ensures that the startDate has been entered and the sum of allocation percents is 1 before saving the changes
     * and closing the window
     */
    function saveChanges()
    {
        
        if (f.startDate.value == "")
        {
            alert("Start date is required.  Please go back and select an effective date for this new rider.");
        }
        else
        {                  
            //if (eval(totalAllocationPercentString) != 1)
            if (!validAllocations)
            {
                alert("Allocation Percents Must Add Up to 1 For Each Rider");
            }
            else
            {
                prepareToSendTransactionAction("ContractDetailTran", "saveAllocationChanges", "contentIFrame");

                closeWindow();
            }
        }
    }

    /**
     * Removes all changes and closes the window
     */
    function cancelChanges()
    {
        prepareToSendTransactionAction("ContractDetailTran", "cancelAllocationChanges", "contentIFrame");

        closeWindow();
    }

</script>

<title>Agent Hierarchy Allocation</title>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

</head>


<body class="dialog" onLoad="init()">

<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<table border="0" width="100%" height="90%">
    <tr height="40%">
        <td align="left" colspan="2">

            <table style="border: 1px solid black" width="100%" height="100%" border="0" cellspacing="0" cellpadding="4">
                 <tr>
                    <td align="right" nowrap>Start Date:&nbsp;</td>
                    <td align="left" nowrap>
                        <input type="text" name="startDate" size='10' maxlength="10" value="<%= startDate %>" disabled>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        Name:&nbsp;
                    </td>
                    <td align="left">
                        <input type="text" name="selectedLastName" value="<%= selectedLastName %>" length="20" maxlength="15" disabled>
                    </td>
                    <td align="right">
                        Agent #:&nbsp;
                    </td>
                    <td align="left">
                        <input type="text" name="selectedAgentNumber" value="<%= selectedAgentNumber %>" length="20" maxlength="15" disabled>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        Split Percent:&nbsp;
                    </td>
                    <td align="left">
                        <input type="text" name="selectedAllocationPercent" maxlength="5" size="10" value="<%= selectedAllocationPercent %>">
                    </td>
                    <td colspan="2">
                        &nbsp;
                    </td>
                </tr>
             </table>
        </td>
    </tr>

    <tr height="1%">
        <td width="40%" align="left">
            <br>
            <input type="button" name="addButton" value="   Add   " onClick="addNewAgent()">
            <input type="button" name="saveButton" value=" Save " onClick="saveAllocationToSummary()">
            <!-- <input type="button" name="cancelButton" value="Cancel" onClick="cancelAllocation()"> -->
            <input type="button" name="deleteButton" value="Delete" onClick="deleteSelectedAllocation()">
        </td>
        <td>
           <br>
           <font size="3" align="left">New Rider Hierarchy Allocation</font>
        </td>
     </tr>

    <tr>
        <td width="100%" NOWRAP colspan="2">

            <!-- Summary Table for Report To Hierarchies  -->
                <table id="agentHierarchyTable" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr class="heading">
                        <th width="16%">
                            Name
                        </th>
                        <th width="16%">
                            Agent #
                        </th>
                        <th width="16%">
                            Agent Type
                        </th>
                        <th width="16%">
                            Split Percent
                        </th>
                        <th width="16%">
                            Allocation Stop Date
                        </th>
                        <th width="16%">
                            Coverage
                        </th>
                    </tr>
                    <tr>
                        <td height="99%" colspan="6">
                            <span class="scrollableContent">
                                <table class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

<%
                    if (tempUIAgentHierarchyVOs != null)
                    {
                    	allocationMap.clear();
                    	
                        for (int i = 0; i < tempUIAgentHierarchyVOs.length; i++)
                        {
                            AgentHierarchyAllocationVO agentHierarchyAllocationVO = tempUIAgentHierarchyVOs[i].getAgentHierarchyAllocationVO();

                            // Check to see if the allocation is active or not.  If not, don't display it.
                            AgentHierarchyAllocation agentHierarchyAllocation = new AgentHierarchyAllocation(agentHierarchyAllocationVO);

                            AgentHierarchyVO agentHierarchyVO = tempUIAgentHierarchyVOs[i].getAgentHierarchyVO();
                            AgentVO agentVO = tempUIAgentHierarchyVOs[i].getAgentVO();

                            String coverage = tempUIAgentHierarchyVOs[i].getCoverage();
                            String agentNumber = Util.initString(agentVO.getAgentNumber(), "&nbsp;");
                            String agentTypeCT = Util.initString(agentVO.getAgentTypeCT(), "&nbsp;");
                            String lastName = getLastName(agentVO);

                            EDITBigDecimal allocationPercent = new EDITBigDecimal(agentHierarchyAllocationVO.getAllocationPercent());
                            EDITDate stopDate = new EDITDate(agentHierarchyAllocationVO.getStopDate());

                            if (new EDITDate(EDITDate.DEFAULT_MAX_DATE).equals(stopDate))
                            {
                                totalAllocationPercent = totalAllocationPercent.addEditBigDecimal(allocationPercent);
                                
                               	if (allocationMap.get(coverage) != null) {
                               		allocationMap.put(coverage, (allocationMap.get(coverage).addEditBigDecimal(allocationPercent)));
                               	} else {
                               		allocationMap.put(coverage, allocationPercent);
                               	}
                            }
                            
                            validAllocations = true;
                            
                            for (EDITBigDecimal value : allocationMap.values()) {
                            	if (!value.isEQ(new EDITBigDecimal("1.0"))) {
                            		validAllocations = false;
                            		break;
                            	}
                            }

                            boolean showAsDisabled = showAsDisabled(stopDate);

                            boolean isSelected = false;

                            String className = "default";

                            if ((agentHierarchyAllocationVO.getAgentHierarchyAllocationPK() + "").equals(selectedAgentHierarchyAllocationPK))
                            {
                                className = "highlighted";

                                isSelected = true;
                            }
%>
                            <tr class="<%= className %>" id="<%= agentHierarchyAllocationVO.getAgentHierarchyAllocationPK() %>" 
                            	agentHierarchyAllocationPK="<%= agentHierarchyAllocationVO.getAgentHierarchyAllocationPK() %>" isSelected="<%= isSelected %>" 
                            	onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="showAgentHierarchyAllocationDetail()">
                            	
                                <td width="16%">
                                    <%= lastName %>
                                </td>
                                <td width="16%" <%= (showAsDisabled)?"class='disabled'":"" %> >
                                    <%= agentNumber %>
                                </td>
                                <td width="16%" <%= (showAsDisabled)?"class='disabled'":"" %>>
                                    <%= agentTypeCT %>
                                </td>
                                <td width="16%">
                                    <%= allocationPercent.toString() %>
                                </td>
                                <td width="16%">
                                    <%= DateTimeUtil.formatYYYYMMDDToMMDDYYYY(stopDate.getFormattedDate()) %>
                                </td>
                                <td width="16%">
                                    <%= coverage %>
                                </td>
                            </tr>
<%
                        } //end for loop
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

<script language="Javascript1.2">

//  Update the javaScript variable with the latest total since the javaScript vars are rendered in page order

totalAllocationPercentString = "<%= totalAllocationPercent.toString() %>";
validAllocations = <%= validAllocations %>;
</script>

<span style="position:relative; width:100%; height:15%; top:0; left:0; z-index:0">
    <table width="100%" border="0" cellspacing="6" cellpadding="0">
        <tr>
            <td align="right" nowrap>
                <input type="button" name="save" value="Save" onClick ="saveChanges()">
                <input type="button" name="cancel" value="Cancel" onClick ="cancelChanges()">
            </td>
        </tr>
    </table>
</span>

<input type="hidden" name="transaction" value="">
<input type="hidden" name="action" value="">

<input type="hidden" name="selectedAgentHierarchyAllocationPK" value="<%= selectedAgentHierarchyAllocationPK %>">
<input type="hidden" name="selectedAllocationPercent" value="<%= selectedAllocationPercent%>">
<input type="hidden" name="selectedStartDate" value="<%= startDate%>">



</form>

</body>
</html>
