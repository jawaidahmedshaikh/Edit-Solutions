<%--
  Created by IntelliJ IDEA.
  User: gfrosti
  Date: Nov 12, 2004
  Time: 3:08:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="fission.utility.*,
                 edit.portal.common.session.*,
                 edit.common.vo.*,
                 reinsurance.business.*,
                 reinsurance.component.*,
                 event.business.*,
                 event.component.*,
                 contract.business.*,
                 contract.component.*,
                 java.math.*,
                 java.util.*,
                 edit.common.CodeTableWrapper,
                 edit.common.EDITDate,
                 edit.common.EDITDateTime,
                 fission.beans.SessionBean,
                 fission.beans.PageBean,
                 reinsurance.ContractTreaty,
                 reinsurance.TreatyGroup,
                 reinsurance.Treaty,
                 event.ClientSetup"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    UserSession userSession = (UserSession) session.getAttribute("userSession");

    String responseMessage = (String) request.getAttribute("responseMessage");
    
    String activeReinsuranceHistoryPK = (String) request.getAttribute("activeReinsuranceHistoryPK");

    // EDITTrx
    String transactionTypeCT = "";
    String transactionTypeDesc = "";
    String effectiveDateMonth = "";
    String effectiveDateDay = "";
    String effectiveDateYear = "";

    // ReinsuranceHistory
    String operator = "";
    String maintDateTime = "";
    BigDecimal modalPremiumAmount = new BigDecimal("0.00");
    BigDecimal reinsuranceNAR = new BigDecimal("0.00");
    String reinsuranceType = "";

    // EDITTrxHistory
    String processDateMonth = "";
    String processDateDay = "";
    String processDateYear = "";

    // Segment
    String contractNumber = "";
    String optionCodeCT = "";
    String optionCodeDesc = "";

    // ClientDetail
    String lastName = "";

    if (activeReinsuranceHistoryPK != null)
    {
        // EDITTrx
        EDITTrxVO editTrxVO = findEDITTrx(Long.parseLong(activeReinsuranceHistoryPK));
        transactionTypeCT = Util.initString(editTrxVO.getTransactionTypeCT(), "");
        transactionTypeDesc = Util.initString(CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("TRXTYPE", transactionTypeCT), "");

        if (editTrxVO.getEffectiveDate() != null)
        {
            EDITDate effectiveDate = new EDITDate(editTrxVO.getEffectiveDate());
            effectiveDateYear = effectiveDate.getFormattedYear();
            effectiveDateMonth = effectiveDate.getFormattedMonth();
            effectiveDateDay = effectiveDate.getFormattedDay();
        }

        // ReinsuranceHistory
        ReinsuranceHistoryVO reinsuranceHistoryVO = findReinsuranceHistory(Long.parseLong(activeReinsuranceHistoryPK));
        operator = Util.initString(reinsuranceHistoryVO.getOperator(), "");
        maintDateTime = Util.initString(reinsuranceHistoryVO.getMaintDateTime(), "");
        modalPremiumAmount = reinsuranceHistoryVO.getModalPremiumAmount();
        reinsuranceNAR = reinsuranceHistoryVO.getReinsuranceNAR();
        reinsuranceType = Util.initString(codeTableWrapper.getCodeDescByCodeTableNameAndCode("REINSURANCETYPE", Util.initString(reinsuranceHistoryVO.getReinsuranceTypeCT(), "")), "");

        // EDITTrxHistory
        EDITTrxHistoryVO editTrxHistoryVO = findEDITTrxHistory(reinsuranceHistoryVO.getEDITTrxHistoryFK());
        String processDateTime = editTrxHistoryVO.getProcessDateTime();

        EDITDate processDate = new EDITDateTime(processDateTime).getEDITDate();
        processDateMonth = processDate.getFormattedMonth();
        processDateDay = processDate.getFormattedDay();
        processDateYear = processDate.getFormattedYear();

        // Segment
        SegmentVO segmentVO = findSegment(Long.parseLong(activeReinsuranceHistoryPK));
        if (segmentVO != null)
        {
            contractNumber = Util.initString(segmentVO.getContractNumber(), "");
            optionCodeCT = Util.initString(segmentVO.getOptionCodeCT(), "");
            optionCodeDesc = Util.initString(CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("OPTIONCODE", optionCodeCT), "");

            // ClientDetail
            ClientDetailVO clientDetailVO = findClientDetail(segmentVO.getSegmentPK());
            lastName = Util.initString(clientDetailVO.getLastName(), "");
        }
    }

    ReinsuranceHistoryVO[] reinsuranceHistoryVOs = (ReinsuranceHistoryVO[]) request.getAttribute("reinsuranceHistoryVOs");
%>
<%!
    /**
     * Finder.
     * @param reinsuranceHistoryPK
     * @return
     */
    private SegmentVO findSegment(long reinsuranceHistoryPK)
    {
        SegmentVO segmentVO = null;

        Lookup contractLookup = new LookupComponent();

        segmentVO = contractLookup.findSegmentBy_ReinsuranceHistoryPK(reinsuranceHistoryPK);

        return segmentVO;
    }

    /**
     * Finder.
     * @param reinsuranceHistoryPK
     * @return
     */
    private EDITTrxVO findEDITTrx(long reinsuranceHistoryPK)
    {
        EDITTrxVO editTrxVO= null;

        Event event = new EventComponent();

        editTrxVO = event.findEDITTrxVOBy_ReinsuranceHistoryPK(reinsuranceHistoryPK);

        return editTrxVO;
    }

    /**
     * Finder.
     * @param reinsuranceHistoryPK
     * @return
     */
    private ReinsurerVO findReinsurer(long reinsuranceHistoryPK)
    {
        ReinsurerVO reinsurerVO = null;

        Reinsurance reinsurance = new ReinsuranceComponent();

        reinsurerVO = reinsurance.findReinsurerBy_ReinsuranceHistoryPK(reinsuranceHistoryPK);

        return reinsurerVO;
    }

    /**
     * Finder.
     * @param reinsuranceHistoryPK
     * @return
     */
    private ReinsuranceHistoryVO findReinsuranceHistory(long reinsuranceHistoryPK)
    {
        ReinsuranceHistoryVO reinsuranceHistoryVO = null;

        Reinsurance reinsurance = new ReinsuranceComponent();

        reinsuranceHistoryVO = reinsurance.findReinsuranceHistoryBy_ReinsuranceHistoryPK(reinsuranceHistoryPK);

        return reinsuranceHistoryVO;
    }

    /**
     * Finder.
     * @param editTrxHistoryPK
     * @return
     */
    private EDITTrxHistoryVO findEDITTrxHistory(long editTrxHistoryPK)
    {
        EDITTrxHistoryVO editTrxHistoryVO = null;

        Event event = new EventComponent();

        editTrxHistoryVO = event.composeEDITTrxHistoryVOByPK(editTrxHistoryPK, new ArrayList());

        return editTrxHistoryVO;
    }

    /**
     * Finders the "insured" client for this Segment.
     */
    private ClientDetailVO findClientDetail(long segmentPK)
    {
        ClientDetailVO clientDetailVO = null;

        client.business.Lookup clientLookup = new client.component.LookupComponent();
        
        ClientDetailVO[] clientDetailVOs = clientLookup.findClientDetailsBy_SegmentPK_RoleType(segmentPK, "Insured");
        
        if (clientDetailVOs != null)
        {
            clientDetailVO = clientDetailVOs[0];
        }

        return clientDetailVO;
    }
%>

<%!
	private TreeMap sortHistoryByEffectiveDate(ReinsuranceHistoryVO[] reinsuranceHistoryVOs)
    {
		TreeMap sortedHistories = new TreeMap();
		for (int i = 0; i < reinsuranceHistoryVOs.length; i++)
        {
            EDITTrxVO editTrxVO = findEDITTrx(reinsuranceHistoryVOs[i].getReinsuranceHistoryPK());

            sortedHistories.put(editTrxVO.getEffectiveDate() +
                                        reinsuranceHistoryVOs[i].getReinsuranceHistoryPK(), reinsuranceHistoryVOs[i]);
		}

		return sortedHistories;
	}
%>

<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Reinsurer History</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script>

    var f = null;

    var shouldShowLockAlert = true;

    var pageIsLocked = <%= userSession.getReinsurerIsLocked()%>;

    var responseMessage = "<%= responseMessage %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        setLockState();

        checkForResponseMessage();

        formatCurrency();
    }

    /**
     *  Determines if the page should be locked.
     */
    function setLockState()
    {
        shouldShowLockAlert = !pageIsLocked;

        for (var i = 0; i < f.elements.length; i++) {

            elementType = f.elements[i].type;

            if ( (elementType == "text" || elementType == "select-one") &&
                 (shouldShowLockAlert == true) ) {

                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }
    }

    /**
     * Certain events can be assigned to this function to test for lock status. E.g - a mouse click.
     */
    function showLockAlert()
    {
        if (shouldShowLockAlert == true) {

            alert("The Page Can Not Be Edited");

            return false;
        }
    }

    /**
     * Shows detail for this ReinsuranceHistory.
     */
    function showReinsuranceHistoryDetail()
    {
        var selectedRowId = getSelectedRowId("reinsuranceHistory");

        f.activeReinsuranceHistoryPK.value = selectedRowId;

        sendTransactionAction("ReinsuranceTran", "showReinsuranceHistoryDetail", "contentIFrame");
    }
</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
    <tr>
        <td colspan="4">
            &nbsp; <!--Filler Row -->
        </td>
    </tr>

<%--    BEGIN Form Content --%>
    <tr>
        <td align="right" nowrap width="25%">
            Transaction Type:
        </td>
        <td align="left" nowrap width="25%">
            <input type="text" name="transactionTypeCT" size="20" maxlength="20" value="<%= transactionTypeDesc %>">
        </td>
        <td align="right" nowrap width="25%">
            &nbsp;
        </td>
        <td align="left" nowrap width="25%">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap width="25%">
            Effective Date:
        </td>
        <td align="left" nowrap width="25%">
            <input type="text" name="effectiveDateMonth" size="2" maxlength="2" value="<%= effectiveDateMonth %>"> /
            <input type="text" name="effectiveDateDay" size="2" maxlength="2" value="<%= effectiveDateDay %>"> /
            <input type="text" name="effectiveDateYear" size="4" maxlength="4" value="<%= effectiveDateYear %>">
        </td>
        <td align="right" nowrap width="25%">
<%--            Contract Period:--%>
            &nbsp;
        </td>
        <td align="left" nowrap width="25%">
<%--            <input type="text" name="contractPeriod" size="4" maxlength="4">(history)--%>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap width="25%">
            Process Date:
        </td>
        <td align="left" nowrap width="25%">
            <input type="text" name="processDateMonth" size="2" maxlength="2" value="<%= processDateMonth %>"> /
            <input type="text" name="processDateDay" size="2" maxlength="2" value="<%= processDateDay %>"> /
            <input type="text" name="processDateYear" size="4" maxlength="4" value="<%= processDateYear %>">
        </td>
        <td colspan="2" rowspan="5" width="50%">
            <font face="" size="1">Policy Info</font>
            <table width="100%" height="90%" cellspacing="0" cellpadding="5" style="border-style:solid; border-width:1; border-color:black">
                <tr>
                    <td align="right" nowrap width="50%">
                        PolicyNumber:
                    </td>
                    <td align="left" nowrap width="50%">
                        <input type="text" name="policyNumber" size="20" maxlength="20" value="<%= contractNumber %>">
                    </td>
                </tr>
                <tr>
                    <td align="right" nowrap>
                        Coverage:
                    </td>
                    <td align="left" nowrap>
                        <input type="text" name="coverage" size="20" maxlength="20" value="<%= optionCodeDesc %>">
                    </td>
                </tr>
                <tr>
                    <td align="right" nowrap>
                        Insured Last:
                    </td>
                    <td align="left" nowrap>
                        <input type="text" name="insuredLast" size="20" maxlength="20" value="<%= lastName %>">
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap width="25%">
            Modal Reinsurance:
        </td>
        <td align="left" nowrap width="25%">
            <input type="text" name="modalReinsurance" size="10" maxlength="10" value="<%= modalPremiumAmount %>" CURRENCY>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap width="25%">
            Reinsurance NAR:
        </td>
        <td align="left" nowrap width="25%">
            <input type="text" name="reinsuranceNAR" size="10" maxlength="10" value="<%= reinsuranceNAR %>" CURRENCY>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap width="25%">
            Reinsurance Type:
        </td>
        <td align="left" nowrap width="25%">
            <input type="text" name="reinsuranceType" size="10" maxlength="10" value="<%= reinsuranceType %>">
        </td>
    </tr>
    <tr>
        <td align="right" nowrap width="25%">
            &nbsp;
        </td>
        <td align="left" nowrap width="25%">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap width="25%">
            Operator:
        </td>
        <td align="left" nowrap width="25%">
            <input type="text" name="operator" size="15" maxlength="15" value="<%= operator %>" CONTENTEDITABLE="false">
        </td>
        <td align="right" nowrap width="25%">
            Date/Time:
        </td>
        <td align="left" nowrap width="25%">
            <input type="text" name="maintDateTime" size="35" maxlength="35" value="<%= maintDateTime %>" CONTENTEDITABLE="false">
        </td>
    </tr>
<%--    END Form Content --%>
    
    <tr>
        <td colspan="4">
            &nbsp; <!--Filler Row -->
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<br>

<%-- ****************************** BEGIN Summary Area ****************************** --%>
<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" width="33%">
            <input type="button" value=" Save " onClick="alert('To Be Implemented')">
            <input type="button" value="Adjustment" onClick="alert('To Be Implemented')">
        </td>
        <td width="33%">
            <span class="tableHeading">History</span>
        </td>
        <td align="right" width="33%">
            <input type="button" value="Filter" onClick="alert('To Be Implemented')">
        </td>
    </tr>
</table>

<%--Header--%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <td align="left" width="11%" nowrap>
            Acctg Pending
        </td>
        <td align="left" width="11%" nowrap>
            Update Status
        </td>
        <td align="left" width="11%" nowrap>
            Pol Number
        </td>
        <td align="left" width="11%" nowrap>
            Treaty Grp Nbr
        </td>
        <td align="left" width="11%" nowrap>
            Eff Date
        </td>
        <td align="left" width="11%" nowrap>
            Trx Type
        </td>
        <td align="left" width="11%" nowrap>
            Reinsurance Type
        </td>
        <td align="left" width="11%" nowrap>
            Status
        </td>
        <td align="left" width="11%" nowrap>
            Reinsurance
        </td>
    </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:42%; top:0; left:0;">
    <table id="reinsuranceHistory" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<%
    if (reinsuranceHistoryVOs != null) // Test for the existence of the target VOs.
    {
        Map sortedReinsuranceHistory = sortHistoryByEffectiveDate(reinsuranceHistoryVOs);

	    Iterator it = sortedReinsuranceHistory.values().iterator();

	    while (it.hasNext())
        {
            ReinsuranceHistoryVO currentReinsuranceHistoryVO = (ReinsuranceHistoryVO) it.next();
            long currentReinsuranceHistoryPK = currentReinsuranceHistoryVO.getReinsuranceHistoryPK();
            EDITTrxVO editTrxVO = findEDITTrx(currentReinsuranceHistoryPK);

            String treatyGroupNumber = "";
            if (currentReinsuranceHistoryVO.getContractTreatyFK() > 0)
            {
                ContractTreaty contractTreaty = ContractTreaty.findBy_ContractTreatyPK(currentReinsuranceHistoryVO.getContractTreatyFK());
                Treaty treaty = Treaty.findBy_TreatyPK(((ContractTreatyVO) contractTreaty.getVO()).getTreatyFK());
                TreatyGroup treatyGroup = TreatyGroup.findBy_TreatyGroupPK(((TreatyVO) treaty.getVO()).getTreatyGroupFK());
                treatyGroupNumber = treatyGroup.getTreatyGroupNumber();
            }
            else
            {
                ClientSetupVO clientSetupVO = ClientSetup.findByPK(editTrxVO.getClientSetupFK());
                Treaty treaty = Treaty.findBy_TreatyPK(clientSetupVO.getTreatyFK());
                TreatyGroup treatyGroup = TreatyGroup.findBy_TreatyGroupPK(((TreatyVO) treaty.getVO()).getTreatyGroupFK());
                treatyGroupNumber = treatyGroup.getTreatyGroupNumber();
            }

            String currentAccountPending = Util.initString(currentReinsuranceHistoryVO.getAccountingPendingStatus(), "&nbsp;");
            String currentUpdateStatus = Util.initString(currentReinsuranceHistoryVO.getUpdateStatus(), "&nbsp;");
            String currentModalReinsurance = Util.initString(currentReinsuranceHistoryVO.getModalPremiumAmount().toString(), "&nbsp");
            String currentReinType = currentReinsuranceHistoryVO.getReinsuranceTypeCT();


            SegmentVO currentSegmentVO = findSegment(currentReinsuranceHistoryPK);
            String currentPolicyNumber = (currentSegmentVO != null)?Util.initString(currentSegmentVO.getContractNumber(), "&nbsp"):"&nbsp;";

            String trxStatus = editTrxVO.getStatus();
            String currentTransactionTypeCT = (editTrxVO != null)?Util.initString(editTrxVO.getTransactionTypeCT(), "&nbsp"):"&nbsp;";
            String currentTransactionTypeDesc = "&nbsp;";

            if (currentTransactionTypeCT != "&nbsp;")
            {
                currentTransactionTypeDesc = CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("TRXTYPE", currentTransactionTypeCT);
            }
            String currentEffectiveDate = (editTrxVO != null)?Util.initString(editTrxVO.getEffectiveDate(), null):null;

            currentEffectiveDate = (currentEffectiveDate != null)?DateTimeUtil.formatYYYYMMDDToMMDDYYYY(currentEffectiveDate):"&nbsp;";

            boolean isSelected = false;
            
            boolean isAssociated = false;

            String className = null;

            if (String.valueOf(currentReinsuranceHistoryPK).equals(activeReinsuranceHistoryPK))
            {
                isSelected = true;
                
                className = "highlighted";
            }
            else
            {
                className = "default";
            }
%>
        <tr class="<%= className %>" id="<%= currentReinsuranceHistoryPK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false);showReinsuranceHistoryDetail()">
            <td align="left" width="11%" nowrap>
                <%= currentAccountPending %>
            </td>
            <td align="left" width="11%" nowrap>
                <%= currentUpdateStatus %>
            </td>
            <td align="left" width="11%" nowrap>
                <%= currentPolicyNumber %>
            </td>
            <td align="left" width="11%" nowrap>
                <%= treatyGroupNumber %>
            </td>
            <td align="left" width="11%" nowrap>
                <%=currentEffectiveDate %>
            </td>
            <td align="left" width="11%" nowrap>
                <%= currentTransactionTypeDesc %>
            </td>
            <td align="left" width="11%" nowrap>
                <%= currentReinType %>
            </td>
            <td align="left" width="11%" nowrap>
                <%= trxStatus %>
            </td>
            <td align="left" width="11%" nowrap>
                <script>document.write(formatAsCurrency(<%= currentModalReinsurance %>))</script>
            </td>
        </tr>
<%
        }// end for
    } // end if
%>      
        <tr class="filler">
            <td colspan="6">
                &nbsp;
            </td>
        </tr>
    </table>
</span>
<%-- ****************************** END Summary Area ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="activeReinsuranceHistoryPK" value="<%= activeReinsuranceHistoryPK %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>