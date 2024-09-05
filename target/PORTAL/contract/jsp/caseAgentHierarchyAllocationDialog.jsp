<!--
 * User: dlataille
 * Date: May 2, 2007
 * Time: 12:27:14 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <!--
 This page displays the active agent hierarchy allocations and allows the user to edit them.
 -->
<%@ page import="edit.common.vo.*,
                 java.util.*,
                 fission.utility.*,
                 edit.common.*,
                 edit.portal.common.session.*,
                 agent.component.AgentComponent,
                 java.math.BigDecimal,
                 contract.*,
                 agent.*,
                 client.*,
                 role.*"%>
<%
    //  Get information from Request and Session scope
    String agentMessage = Util.initString((String) request.getAttribute("agentMessage"), "");
    String startDate = Util.initString((String) request.getAttribute("startDate"), "");

    String selectedAgentHierarchyAllocationPK = Util.initString((String) request.getAttribute("selectedAgentHierarchyAllocationPK"), "");

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

    //  Page junk
    String pageMode = (String) request.getAttribute("pageMode");
    String shouldLockStartDate = (String) request.getAttribute("shouldLockStartDate");  // determines whether the start date should be locked from further changes


    UserSession userSession = (UserSession) session.getAttribute("userSession");

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

    var shouldShowLockAlert = true;;

    var agentIsLocked = <%= userSession.getAgentIsLocked()%>;

    var totalAllocationPercentString = "<%= totalAllocationPercent.toString() %>";

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

<%--            f.startDate.contentEditable = false;--%>
            f.splitPercent.contentEditable = false;
        }

        else if (pageMode == "ADD")
        {
            f.addButton.disabled = false;
            f.saveButton.disabled = true;
            f.cancelButton.disabled = true;
            f.deleteButton.disabled = true;

<%--            f.startDate.contentEditable = false;--%>
            f.splitPercent.contentEditable = false;

        }

        else if (pageMode == "SELECT")
        {
            f.addButton.disabled = false;
            f.saveButton.disabled = false;
            f.cancelButton.disabled = false;
            f.deleteButton.disabled = false;

<%--            f.startDate.contentEditable = true;--%>
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

        prepareToSendTransactionAction("CaseDetailTran", "showAgentHierarchyAllocationDetail", "_self");
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
            alert("Start date must be entered before continuing");
        }
        else
        {
            var width = 0.90 * screen.width;

            var height= 0.65 * screen.height;

            openDialog("","agentSelection","top=0,left=0,resizable=no,width=" + width + ",height=" + height,"CaseDetailTran","showAgentSelectionDialog");
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
                alert("Start date must be entered before continuing");
            }
            else
            {
                prepareToSendTransactionAction("CaseDetailTran", "saveAllocationToSummary", "_self");
            }
        }
	}

    /**
     * Cancels current Allocation edits.
     */
	function cancelAllocation()
    {
		prepareToSendTransactionAction("CaseDetailTran", "clearAllocationForm", "_self");
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
                alert("Start date must be entered before continuing");
            }
            else
            {
                prepareToSendTransactionAction("CaseDetailTran", "deleteSelectedAllocation", "_self");
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
            alert("You must enter a start date");
        }
        else
        {
            if (eval(totalAllocationPercentString) != 1)
            {
                alert("Allocation Percents must add up to 1");
            }
            else
            {
                prepareToSendTransactionAction("CaseDetailTran", "saveAllocationChanges", "contentIFrame");

                closeWindow();
            }
        }
    }

    /**
     * Removes all changes and closes the window
     */
    function cancelChanges()
    {
        prepareToSendTransactionAction("CaseDetailTran", "cancelAllocationChanges", "contentIFrame");

        closeWindow();
    }

    /**
     * Checks to see if the start date can be edited or not.  If so, brings up the calendar.  If not, displays a message
     */
    function showCalendarIfAllowed(str_target, str_datetime)
    {
        if (<%= shouldLockStartDate.equals("true") %>)
        {
            alert("Start Date cannot be edited once it is set.  Must cancel page before changing.");
        }
        else
        {
            show_calendar(str_target, str_datetime);
        }
    }

    /**
     * Checks to see if the startDate field should be formatted or not.  This depends on whether the field can be edited.
     * (NOTE:  I don't think this function is needed any more since resolved the lock of start date a different way.
     * No time to check right now, will do shortly - S. Dorman).
     */
    function formatDateIfAllowed(dateObjectName, dateValue, event, validate)
    {
        if (! <%= shouldLockStartDate.equals("true") %>)
        {
            DateFormat(dateObjectName, dateValue, event, validate);
        }
    }

    /**
     *  Checks for focus on the start date field.  If the start date should be locked, displays an error message
     */
    function checkStartDateFocus()
    {
        if (<%= shouldLockStartDate.equals("true") %>)
        {
            alert("Start Date cannot be edited once it is set.  Must cancel page before changing.");
        }
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
<%--                        <input type="text" name="startDate" size='10' maxlength="10" value="<%= startDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">--%>
<%--                        <a href="javascript:show_calendar('f.startDate', f.startDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>--%>
                        <input type="text" name="startDate" <%= (shouldLockStartDate.equals("true"))?"readonly":"" %> size='10' maxlength="10" value="<%= startDate %>" onKeyUp="formatDateIfAllowed(this,this.value,event,false)" onBlur="formatDateIfAllowed(this,this.value,event,true)" onfocus="checkStartDateFocus()" >
                        <a href="javascript:showCalendarIfAllowed('f.startDate', f.startDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
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
            <input type="button" name="cancelButton" value="Cancel" onClick="cancelAllocation()">
            <input type="button" name="deleteButton" value="Delete" onClick="deleteSelectedAllocation()">
        </td>
        <td>
           <br>
           <font size="3" align="left">Hierarchy Allocations</font>
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
                        for (int i = 0; i < tempUIAgentHierarchyVOs.length; i++)
                        {
                            AgentHierarchyAllocationVO agentHierarchyAllocationVO = tempUIAgentHierarchyVOs[i].getAgentHierarchyAllocationVO();

                            // Check to see if the allocation is active or not.  If not, don't display it.
                            AgentHierarchyAllocation agentHierarchyAllocation = new AgentHierarchyAllocation(agentHierarchyAllocationVO);
                            if (! agentHierarchyAllocation.isActive())
                            {
                                continue;
                            }

                            AgentHierarchyVO agentHierarchyVO = tempUIAgentHierarchyVOs[i].getAgentHierarchyVO();
                            AgentVO agentVO = tempUIAgentHierarchyVOs[i].getAgentVO();

                            String coverage = tempUIAgentHierarchyVOs[i].getCoverage();
                            String agentNumber = Util.initString(agentVO.getAgentNumber(), "&nbsp;");
                            String agentTypeCT = Util.initString(agentVO.getAgentTypeCT(), "&nbsp;");
                            String lastName = getLastName(agentVO);

                            EDITBigDecimal allocationPercent = new EDITBigDecimal(agentHierarchyAllocationVO.getAllocationPercent());
                            EDITDate stopDate = new EDITDate(agentHierarchyAllocationVO.getStopDate());

                            totalAllocationPercent = totalAllocationPercent.addEditBigDecimal(allocationPercent);

                            boolean showAsDisabled = showAsDisabled(stopDate);

                            boolean isSelected = false;

                            String className = "default";

                            if ((agentHierarchyAllocationVO.getAgentHierarchyAllocationPK() + "").equals(selectedAgentHierarchyAllocationPK))
                            {
                                className = "highlighted";

                                isSelected = true;
                            }
%>
                            <tr class="<%= className %>" id="<%= agentHierarchyAllocationVO.getAgentHierarchyAllocationPK() %>" agentHierarchyAllocationPK="<%= agentHierarchyAllocationVO.getAgentHierarchyAllocationPK() %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="showAgentHierarchyAllocationDetail()">
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

<script language="Javascript1.2">

//  Update the javaScript variable with the latest total since the javaScript vars are rendered in page order

totalAllocationPercentString = "<%= totalAllocationPercent.toString() %>";

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


