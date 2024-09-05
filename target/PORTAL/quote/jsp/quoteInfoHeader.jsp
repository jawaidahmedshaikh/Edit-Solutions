<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.CodeTableVO,
                 edit.common.EDITDateTime,
                 event.dm.dao.*,
                 contract.Segment,
                 edit.common.vo.EDITTrxVO,
                 fission.utility.Util" %>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
	PageBean formBean = quoteMainSessionBean.getPageBean("formBean");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

	String contractId 		= quoteMainSessionBean.getValue("contractId");
	String status     		= quoteMainSessionBean.getValue("status");
	String companyStructure = quoteMainSessionBean.getValue("companyStructure");
    String contractGroup    = Util.initString(quoteMainSessionBean.getValue("contractGroup") , "");
    String clientUpdate     = quoteMainSessionBean.getValue(("clientUpdate"));
    String selectedClientName = Util.initString(quoteMainSessionBean.getValue("selectedClientName"), "");
    String ownerClientName = quoteMainSessionBean.getValue("policyOwnerName"); 
    String ownerTaxId = quoteMainSessionBean.getValue("ownerTaxId"); 
    String groupName = quoteMainSessionBean.getValue("groupName"); 
    String paidToDate = Util.initString(quoteMainSessionBean.getValue("paidToDate"), "N/A");

    String companyName = quoteMainSessionBean.getValue("companyName"); 
    String product = quoteMainSessionBean.getValue("product"); 
    String billingType = Util.initString(quoteMainSessionBean.getValue("billingSchedule"), "N/A");
    String brandingCompany = Util.initString(quoteMainSessionBean.getValue("brandingCompany"), "N/A");
    
    String productStructurePK = quoteMainSessionBean.getValue("productStructurePK"); 
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
	
	String lastProcessedTrxInfo = "N/A";
	
	String reloadHeader = Util.initString((String) session.getAttribute("reloadHeader"), "");
    
    if (contractId != null && contractId != "" && reloadHeader.equalsIgnoreCase("true"))
    {
		Segment segment = Segment.findByContractNumber(contractId);
    	EDITTrxVO latestTrx = event.dm.dao.DAOFactory.getEDITTrxDAO().findBySegmentPK_LastProcessedTrx(segment.getSegmentPK());
    	
    	if(latestTrx != null)
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
    	lastProcessedTrxInfo = Util.initString((String) session.getAttribute("lastProcessedTrxInfo"), "N/A");
    }
%>

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<table class="infoHeader" style="background-color:<%=backgroundColor %>">    

	<tr>
	    <td nowrap align="left" width="25%"> Contract: <%= contractId%> </td>
	    <td nowrap align="left" width="25%">Owner's Name: <%= ownerClientName%> </td>
		<td nowrap align="left" width="25%">Group Number: <%= contractGroup %> </td>
		<td nowrap align="right" width="25%"> Status: <%= status%> </td>
	
	</tr> 
    <tr>
   		<td nowrap align="left" width="25%"> Product: <%= companyName%> <%= product%> </td>
   		<td nowrap align="left" width="25%">Owner's Tax ID#: <%= ownerTaxId%> </td>
    	<td nowrap align="left" width="25%">Group Name: <%= groupName%> </td>      
      	<td nowrap align="right" width="25%"> Paid to Date: <%= paidToDate%> </td>
	</tr>
    <tr>
    	<td nowrap align="left" width="25%">Branding Company: <%= brandingCompany%> </td>
      	<td nowrap align="left" width="25%">Insured Name: <%= selectedClientName%> </td>
        <td nowrap align="left"width="25%"> Billing Type: <%= billingType %> </td>
     	<td nowrap align="right" width="25%"> Last Processed Trx: <%= lastProcessedTrxInfo%> </td>
	</tr>
    
</table>