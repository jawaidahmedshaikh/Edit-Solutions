<!--
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use
 *  subject to the license agreement.
 -->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 fission.beans.*,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.CodeTableVO,
                 edit.common.EDITDateTime,
                 event.dm.dao.*,
                 contract.Segment,
                 edit.common.vo.EDITTrxVO,
                 fission.utility.*" %>

<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>
<jsp:useBean id="baseRiderBean"
    class="fission.beans.PageBean" scope="session"/>

<%
	String contractId 	 = contractMainSessionBean.getValue("contractId");
	String segmentStatus = contractMainSessionBean.getValue("statusCode");
//	String companyStructure = contractMainSessionBean.getValue("companyStructure");
    String billingType  = contractMainSessionBean.getValue("billingSchedule");
    String mecStatus = contractMainSessionBean.getValue("mecStatus");
    String contractTypeCode = Util.initString(contractMainSessionBean.getValue("contractTypeCode"), "");
    String clientUpdate = contractMainSessionBean.getValue(("clientUpdate"));
    if (!clientUpdate.equals(""))
    {
        contractTypeCode = clientUpdate;
    }
    String contractGroup    = Util.initString(contractMainSessionBean.getValue("contractGroup"), "");
    String selectedClientName = Util.initString(contractMainSessionBean.getValue("selectedClientName"), "");
    String brandingCompany = Util.initString(contractMainSessionBean.getValue("brandingCompany"), "N/A");
    String ownerClientName = contractMainSessionBean.getValue("policyOwnerName"); 
    String ownerTaxId = contractMainSessionBean.getValue("ownerTaxId"); 
    String groupName = contractMainSessionBean.getValue("groupName"); 
    String paidToDate = Util.initString(contractMainSessionBean.getValue("paidToDate"), "NULL"); 

    String companyName = contractMainSessionBean.getValue("companyName"); 
    String product = contractMainSessionBean.getValue("product"); 
    
    String productStructurePK = contractMainSessionBean.getValue("productStructurePK"); 
	String backgroundColor = "#bbbbbb";
	if (productStructurePK.equals("1365524021191")) {
		// CICA beige
		backgroundColor = "#dedcd1";
	} else if (productStructurePK.equals("1183390018285")) {
		// FLA LBT blue
		backgroundColor = "#d1d5e8";
	} else if (productStructurePK.equals("1638619121960")) {
		// A&H red
		backgroundColor = "#ddaab2";
	} else if (productStructurePK.equals("1591122347307")) {
		// UL orange
		backgroundColor = "#e4cabc";
	} else if (product.equalsIgnoreCase("LBT1.5")) {
		// FLA LBT1.5 yellow
		backgroundColor = "#EFDBB2";
	}
	
    String lastProcessedTrxInfo = "";
    
    String reloadHeader = Util.initString((String) session.getAttribute("reloadHeader"), "");
    
    if (contractId != null && contractId != "" && reloadHeader.equalsIgnoreCase("true"))
    {
		Segment segment = Segment.findByContractNumber(contractId);
    	EDITTrxVO latestTrx = event.dm.dao.DAOFactory.getEDITTrxDAO().findBySegmentPK_LastProcessedTrx(segment.getSegmentPK());
    	
    	if (latestTrx != null)
    	{
    		String lastProcessDate = new EDITDateTime(latestTrx.getMaintDateTime()).getEDITDate().getMMDDYYYYDate();
    		lastProcessedTrxInfo = latestTrx.getTransactionTypeCT() + " on " + lastProcessDate;
    	
    		session.setAttribute("lastProcessedTrxInfo", lastProcessedTrxInfo);
    	}
    	else
    	{
    		session.setAttribute("lastProcessedTrxInfo", "");
    	}
    	
    	session.setAttribute("reloadHeader", "");
    }
    else
    {
    	lastProcessedTrxInfo = Util.initString((String) session.getAttribute("lastProcessedTrxInfo"), "");
    }
%>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<table class="infoHeader" style="background-color:<%=backgroundColor %>">   
	<tr>
	    <td nowrap align="left" width="25%"> Contract: <%= contractId%> </td>
	    <td nowrap align="left" width="25%">Owner's Name: <%= ownerClientName%> </td>
		<td nowrap align="left" width="25%">Group Number: <%= contractGroup %> </td>
		<td nowrap align="left" width="25%"> Status: <%= segmentStatus%> </td>
	
	</tr> 
    <tr>
    
   		<td nowrap align="left" width="25%"> Product: <%= companyName%> <%= product%> </td>
   		<td nowrap align="left" width="25%">Owner's Tax ID#: <%= ownerTaxId%> </td>
    	<td nowrap align="left" width="25%">Group Name: <%= groupName%> </td>      
      	<td nowrap align="left" width="25%"> Paid to Date: <%= paidToDate%> </td>
	</tr>
    <tr>
    	<td nowrap align="left" width="25%">Branding Company: <%= brandingCompany%> </td>
      	<td nowrap align="left" width="25%">Insured Name: <%= selectedClientName%> </td>
        <td nowrap align="left"width="25%"> Billing Type: <%= billingType %> </td>
     	<td nowrap align="left" width="25%"> Last Processed Trx: <%= lastProcessedTrxInfo%> </td>
	</tr>
</table>
