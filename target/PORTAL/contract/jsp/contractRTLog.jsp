<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*" %>

<jsp:useBean id="contractRealTimeLogBean"
    class="fission.beans.SessionBean" scope="session"/>

<%!
    private TreeMap sortActivitiesByEffectiveDate(SessionBean realTimeLogBean) {

		Map realTimeLogBeans = realTimeLogBean.getPageBeans();

		TreeMap sortedTransactions = new TreeMap();

		Iterator enumer  = realTimeLogBeans.values().iterator();

		while (enumer.hasNext()) {

			PageBean realTimeBean = (PageBean) enumer.next();

			String contractId    = realTimeBean.getValue("contractId");
            String effectiveDate = realTimeBean.getValue("effectiveDate");
            String status        = realTimeBean.getValue("statusInd");
            String rtKey         = realTimeBean.getValue("rtKey");

			sortedTransactions.put(contractId + effectiveDate + status + rtKey, realTimeBean);
		}

		return sortedTransactions;
	}
%>
<html>

<head>
<title>Real Time Activity Log</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- ****** STYLE CODE ***** //-->

<style>

	.highLighted {

		background-color: #FFFFCC;
	}

	.dummy {
	}

	table {

		background-color: #DCDCDC;
	}
</style>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.realTimeLogForm;
	}


    function closeRealTimeDialog() {

    	window.close();
    }

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value = transaction;
		f.action.value = action;

		f.target = target;

		f.submit();
	}

</script>
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
 <form name="realTimeLogForm" method="post" action="/PORTAL/servlet/RequestManager">

 <span id="scrollableContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:80%; top:0; left:0; z-index:0; overflow:scroll">
  <table id="summaryTable" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr bgcolor="#30548E">
          <th align="center"><font color= "#FFFFFF">      Company      </font></th>
          <th align="center"><font color= "#FFFFFF">  Contract Number  </font></th>
          <th align="center"><font color= "#FFFFFF">    Transaction    </font></th>
  		  <th align="center"><font color= "#FFFFFF">       Status      </font></th>
  		  <th align="center"><font color= "#FFFFFF">   Effective Date  </font></th>
  		  <th align="center"><font color= "#FFFFFF">       Amount      </font></th>
  		  <th align="left">  <font color= "#FFFFFF">       Message     </font></th>
    </tr>
  	<%
  			int rowId   = 0;

  			String rowToMatch = "";
  			String trClass = "";
  			String selected = "";

            if (contractRealTimeLogBean.hasPageBeans()) {

                Map sortedActivities = sortActivitiesByEffectiveDate(contractRealTimeLogBean);

                Iterator it = sortedActivities.values().iterator();

                while (it.hasNext())  {

                    PageBean realTimeBean = (PageBean) it.next();

                    String tCompanyStructure = realTimeBean.getValue("companyStructure");
                    String tContractId		 = realTimeBean.getValue("contractId");
                    String tEffectiveDate	 = realTimeBean.getValue("effectiveDate");
                    String tStatusInd        = realTimeBean.getValue("statusInd");
                    String tTransactionType  = realTimeBean.getValue("transactionType");
                    String tAmount			 = realTimeBean.getValue("amount");
                    String tMessage 		 = realTimeBean.getValue("message");

                    // Store behind the scenes...
  		%>
  			<tr>
  				<td nowrap>
  					<%= tCompanyStructure %>
  				</td>
  				<td nowrap>
  					&nbsp;&nbsp;<%= tContractId %>
  				</td>
  				<td nowrap>
  					&nbsp;&nbsp;<%= tTransactionType %>
  				</td>
  				<td nowrap>
  					&nbsp;&nbsp;<%= tStatusInd %>
  				</td>
  				<td nowrap>
  					&nbsp;&nbsp;<%= tEffectiveDate %>
  				</td>
  				<td nowrap>
  					&nbsp;&nbsp;<%= tAmount %>
  				</td>
  				<td nowrap>
  					&nbsp;&nbsp;<%= tMessage %>
  				</td>
  			</tr>
  	<%
                }// end while
            }// end if
  	%>
  	</table>
</span>

<table id="table3" width="100%" border="0" cellspacing="6" cellpadding="0" bgcolor="#DDDDDD">
	<tr>
			<td align="right" nowrap colspan="4">
				<input type="button" name="close" value="Close" onClick ="closeRealTimeDialog()">
			</td>
	</tr>
</table>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction"  value="">
 <input type="hidden" name="action"       value="">

</form>
</body>
</html>
