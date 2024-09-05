<!--
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use
 *  subject to the license agreement.
 -->

<html>
<%@ page import="edit.common.*,
				 contract.*" %>

<jsp:useBean id="pageBean"
    class="fission.beans.PageBean" scope="request"/>
    
<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>
	
<%
	String[] contractIds = pageBean.getValues("contractIds");     	
	String contractId    = pageBean.getValue("bankAccountTypeId");

    String freeLookEndDate = pageBean.getValue("freeLookEndDate");
    String graceExtensionDate = "";

    String contractNumber = contractMainSessionBean.getValue("contractId");
   	
   	Segment segment = Segment.findByContractNumber(contractNumber);
  	Segment[] segmentRiders = segment.getRiders();
  	EDITDate lapseDate;
  	
  	for (Segment rider : segmentRiders)
  	{
  		if(rider.getOptionCodeCT().equalsIgnoreCase("LTC") ||
  			rider.getOptionCodeCT().equalsIgnoreCase("LTC30D") ||
  			rider.getOptionCodeCT().equalsIgnoreCase("LTCHCA") ||
  			rider.getOptionCodeCT().equalsIgnoreCase("LTCTNB") )  
  		{

			Life life = Life.findBy_SegmentPK(segment.getPK());
			lapseDate = life.getLapseDate();
			if (lapseDate!= null && rider.getTerminationDate().afterOREqual(lapseDate))
			{
				graceExtensionDate = new EDITDate(lapseDate.addDays(30)).getMMDDYYYYDate();
			}

  		}
  	}
%>
<head>
<title>Date Values</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="Javascript1.2">

	var f = null;

	function init() {

        f = document.dateForm;
        
        f.terminateDate.value    = opener.f.termDt.value;
        f.finalPayDate.value     = opener.f.finalPayDate.value;
        f.statusChangeDate.value = opener.f.statusChangeDate.value;
        f.lastCheckDate.value    = opener.f.lastCheckDate.value;
        f.nextPaymentDate.value  = opener.f.nextPaymentDate.value;
        f.certainPeriodEndDate.value = opener.f.certainPeriodEndDate.value;
        f.lastAnnivDate.value    = opener.f.lastAnnivDate.value;
        //f.freeLookEndDate.value  =  "<%= freeLookEndDate %>";
        f.freeLookEndDate.value  =  opener.f.freeLookEndDt.value;
        f.lapsePendingDate.value = opener.f.lapsePendingDate.value;
        f.lapseDate.value = opener.f.lapseDate.value;
        f.graceExtensionDate.value = "<%= graceExtensionDate %>";
	}

	function enterDialog()
    {
		window.close();
	}

</script>
</head>
<body bgcolor="#DDDDDD" onLoad="init()">
<form name="dateForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="40%" border="0" cellspacing="5" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="right" width="5%">Terminate Date:</td>
      <td>
        <input type="text" name="terminateDate" size="10" maxlength="10" disabled>
      </td>
    </tr>
    <tr>
      <td nowrap align="right" width="5%">Certain Period End Date:</td>
      <td>
        <input type="text" name="certainPeriodEndDate" size="10" maxlength="10" disabled>
      </td>
    </tr>
    <tr>
      <td nowrap align="right" width="5%">Final Pay Date:</td>
      <td>
        <input type="text" name="finalPayDate" size="10" maxlength="10" disabled>
      </td>
    </tr>
    <tr>
      <td nowrap align="right" width="5%">Status ChangeDate:</td>
      <td>
        <input type="text" name="statusChangeDate" size="10" maxlength="10" disabled>
      </td>
    </tr>
    <tr>
      <td nowrap align="right" width="5%">Last Check Date:</td>
      <td>
        <input type="text" name="lastCheckDate" size="10" maxlength="10" disabled>
      </td>
    </tr>
    <tr>
      <td nowrap align="right" width="5%">Next Payment Date:</td>
      <td>
        <input type="text" name="nextPaymentDate" size="10" maxlength="10" disabled>
      </td>
    </tr>
    <tr>
      <td nowrap align="right" width="5%">Last Anniversary Date:</td>
      <td>
        <input type="text" name="lastAnnivDate" size="10" maxlength="10" disabled>
      </td>
    </tr>
    <tr>
      <td nowrap align="right" width="5%">Free Look End Date:</td>
      <td>
        <input type="text" name="freeLookEndDate" size="10" maxlength="10" disabled>
      </td>
    </tr>
    <tr>
      <td nowrap align="right" width="5%">Lapse Pending Date:</td>
      <td>
        <input type="text" name="lapsePendingDate" size="10" maxlength="10" disabled>
      </td>
    </tr>
    <tr>
      <td nowrap align="right" width="5%">Lapse Date:</td>
      <td>
        <input type="text" name="lapseDate" size="10" maxlength="10" disabled>
      </td>
    </tr>
    <tr>
      <td nowrap align="right" width="5%">Grace Extension Date:</td>
      <td>
        <input type="text" name="graceExtensionDate" size="10" maxlength="10" disabled>
      </td>
    </tr>
    &nbsp;&nbsp;
    <tr>
      <td nowrap align="right" width="5%">&nbsp;</td>
      <td align="right">
        <input type="button" name="close" value="Close" onClick="enterDialog()">
      </td>
    </tr>
  </table>
</form>
</body>
</html>
