<!-- ***** JAVA CODE ***** -->
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.net.*, edit.common.vo.*, fission.global.*, fission.beans.*" %>

<jsp:useBean id="pageBean"
    class="fission.beans.PageBean" scope="request"/>

<%

		BatchStatusVO[] batchStatusVO = (BatchStatusVO[])request.getAttribute("batchesUpdateData");
		int size = batchStatusVO.length;
		String batchId[]            = new String[size];
		String operator[]           = new String[size];
		String totalContracts[]     = new String[size];
		String completedContracts[] = new String[size];
		String databaseUpdated[]    = new String[size];
		for(int i=0; i<size; ++i){
			BatchStatusVO batch = batchStatusVO[i];
			batchId[i]            = batch.getUniqueBatchId();
			operator[i]           = batch.getOperator();
			totalContracts[i]     = batch.getTotalNumberOfContracts() + "";
			completedContracts[i] = batch.getCompletedContracts()+"";
			databaseUpdated[i]    = batch.getDataBaseUpdated();
		}

%>

<html>
<head>
<title>batchStatus.jsp</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Refresh" content="3; URL=/PORTAL/servlet/RequestManager?transaction=DailyDetailTran&action=showBatchStatus">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
<script language="Javascript1.2">

	function init() {
		f = document.batchStatus;
	}

	function batchCompleted(batchId){
		f.completedBatchId.value = batchId;

		sendTransactionAction('DailyDetailTran','batchCompleted','main');

	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value = transaction;
		f.action.value = action;
		f.target = target;

		f.submit();
	}

    function bCancel() {

		sendTransactionAction('DailyDetailTran', 'showDailySelection', 'main');
    }

</script>
</head>

<!-- ***** HTML CODE *****  //-->

<body bgcolor="#FFFFFF"  onLoad="init()">

<form name="batchStatus">

  <table width="95%" border="0" cellspacing="0" cellpadding="0" height="45">
	<tr class="dataHeading" bgcolor="#30548E" >
    	<th class="dataHeading" width="5%" align="center"> <font color="#FFFFFF" size="2">BatchId
    		</font> </th>
    	<th class="dataHeading" width="5%" align="center"><font color="#FFFFFF" size="2"> Total Contracts
      		</font></th>
    	<th class="dataHeading" width="5%" align="center"><font color="#FFFFFF" size="2"> Completed Contracts
      		</font></th>
    	<th class="dataHeading" width="5%" align="center"><font color="#FFFFFF" size="2"> Database Updated
      		</font></th>
		<th class="dataHeading" width="5%" align="center"><font color="#FFFFFF" size="2"> Operator
      		</font></th>
		<th class="dataHeading" width="5%" align="right"><font color="#FFFFFF" size="2"> Acknowledged
      		</font></th>
  	</tr>
	<tr>
		<table READONLY border="0" width="100%" cellpadding="0" cellspacing="0" align="center">
		<% for (int i = 0; i < batchStatusVO.length; i++)  { %>
    	<tr id="ln<%= i%>" class="link">
        	<td width="14%" align="center">
				<%= batchId[i] %>
        	</td>
        	<td width="18%" align="center">
            	<%= totalContracts[i] %>
        	</td>
        	<td width="14%" align="center">
            	<%= completedContracts[i] %>
        	</td>
			<td width="14%" align="center">
            	<%= databaseUpdated[i] %>
            </td>
			<td width="14%" align="center">
            	<%= operator[i] %>
        	</td>
			<td width="19%" align="left">
            	<input type="button" name="acknowledged" value="Acknowledged" onclick="batchCompleted('<%= batchId[i] %>')">
        	</td>
    	</tr>
		<% } %>
		</table>
	</tr>
    <tr>
    	<td width="47%" height="49" align="center" valign="middle">
    		<input type="button" name="cancel" value="Cancel" onClick="bCancel()">
    	</td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->

<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="completedBatchId" value="0">
</form>
<p>&nbsp;</p>
</body>
</html>
