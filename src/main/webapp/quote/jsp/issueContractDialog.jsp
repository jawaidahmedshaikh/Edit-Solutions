<!-- issueContractNumberDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 edit.common.EDITDate,
                 event.Suspense,
                 edit.common.vo.DepositsVO,
                 edit.common.EDITBigDecimal,
                 event.CashBatchContract,
                 fission.utility.*,
                 contract.*,
                 engine.*" %>

<jsp:useBean id="formBean"
    class="fission.beans.PageBean" scope="request"/>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
    String message = Util.initString((String) request.getAttribute("message"), "");
	String dateMessage = Util.initString((String) request.getAttribute("dateMessage"), "");

    PageBean mainBean = quoteMainSessionBean.getPageBean("formBean");
    
	String segmentPK = mainBean.getValue("segmentPK");
	Segment segment = new Segment(new Long(segmentPK));

	Company company = Company.findByProductStructurePK(segment.getProductStructureFK());
	String policyNumberPrefix = company.getPolicyNumberPrefix();
	String policyNumberSuffix = Util.initString(company.getPolicyNumberSuffix(), "");
	int policySequenceLength = company.getPolicySequenceLength();

	int contractMaxLength = policyNumberPrefix.length() + policySequenceLength + policyNumberSuffix.length();
	
    String companyStructureId = Util.initString(mainBean.getValue("companyStructureId"), "0");
    
    Suspense[] suspenses = (Suspense[]) request.getAttribute("suspenses");
    DepositsVO[] depositsVOs = (DepositsVO[]) session.getAttribute("depositsVOs");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

	String contractNumber		= quoteMainSessionBean.getValue("contractId");
    String userEnteredIssueDate = mainBean.getValue("issueDate");       // mm/dd/yyyy

    String issueDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(new EDITDate().getFormattedDate());

    if (!userEnteredIssueDate.equals(""))
    {
        issueDate = userEnteredIssueDate;
    }

	String contractTarget = (String) request.getAttribute("contractTarget");

	if (contractNumber.equals(""))
    {
		contractNumber = mainBean.getValue("contractNumber");
	}

    CodeTableVO[] terminationStats = codeTableWrapper.getCodeTableEntries("TERMINATIONSTATUS", Long.parseLong(companyStructureId));
    String terminationStatus = formBean.getValue("terminationStatus");

	String checkBoxStatus      = mainBean.getValue("checkBoxStatus");
    String suppressPolicyPages = mainBean.getValue("suppressPolicyPages");

	if (checkBoxStatus.equals("true"))
    {
		checkBoxStatus = "checked";
	}
	else
    {
		checkBoxStatus = "";
	}

    if (suppressPolicyPages != null && suppressPolicyPages.equals("true"))
    {
        suppressPolicyPages = "checked";
    }
    else
    {
        suppressPolicyPages = "unchecked";
    }
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<script language="Javascript1.2">

	var f = null;
	var contractTarget = "<%=contractTarget%>";
    var message = "<%= message %>";
    var dateMessage = "<%= dateMessage %>";

	function init()
    {
		f = document.contractNumberForm;

        if (message == "Errored")
        {
            sendTransactionAction("QuoteDetailTran", "saveQuote", "contentIFrame");
        }
        else if (message == "Generate")
        {
            f.message.value = message;
            openDialog("", "issueReportDialog", "top=0,left=0,resizable=no,scrollbars=yes,width=" + .80 * screen.width + ",height=" + .80 * screen.height,"QuoteDetailTran", "generateIssueReport", "issueReportDialog");
        }
	}

	function openDialog(theURL,winName,features,transaction,action)
    {
	    dialog = window.open(theURL,winName,features);

	    sendTransactionAction(transaction, action, winName);
	}

    function checkForEnter()
    {
        if (window.event.keyCode == 13)
        {
            save();
        }
    }

	function sendTransactionAction(transaction, action, target)
    {
		f.transaction.value=transaction;
		f.action.value=action;
		f.target = target;

		f.submit();
	}

	function autoGenerateContractNumber()
    {
		if (f.autoAssignNumberBox.checked == true)
        {
			f.checkBoxStatus.value = "true";
			f.contractTarget.value = "<%=contractTarget%>";
			sendTransactionAction('QuoteDetailTran', 'autoGenContractNbrForIssue', '_self');
		}
		else
        {
			f.contractNumber.value        = "";
			f.autoAssignNumberBox.checked = false;
			f.checkBoxStatus.value = "false";
			f.contractTarget.value = "<%=contractTarget%>";
		}
	}

	function cancelDialog()
    {
		window.close();
	}

	function save()
    {
		var policyNumberPrefix = "<%=policyNumberPrefix%>";
		var policyNumberSuffix = "<%=policyNumberSuffix%>";
		
		var policySequenceLength = "<%=policySequenceLength%>";
		
		//check policy prefix and sequence have values
		if( policyNumberPrefix && policySequenceLength ) {
		
			var regEx = new RegExp( "^" + policyNumberPrefix + "[0-9]{" + policySequenceLength + "," + policySequenceLength + "}" + policyNumberSuffix + "$" );
			
			if (!regEx.test(f.contractNumber.value))			
				window.alert("Contract number doesn't meet requirements"); 
			else {
		        if (f.suppressPolicyPages.checked == true)
		        {
		            f.suppressPolicyPagesStatus.value = "true";
		        }
		        else
		        {
		            f.suppressPolicyPagesStatus.value = "false";
		        }

		        if (dateMessage != null && dateMessage != "") {
					if (confirm(dateMessage)) {
						sendTransactionAction("QuoteDetailTran", "commitContract", "contentIFrame");
						window.close();
					}
				} else {
					sendTransactionAction("QuoteDetailTran", "commitContract", "contentIFrame");
					window.close();
				}  
			}
		}
	}

    function generateIssueReport()
    {
        sendTransactionAction("QuoteDetailTran","generateIssueReport","_self");
    }

</script>

<head>
<title>Specify Contract Number</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="contractNumberForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="51%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td align="right" nowrap>Contract Number:&nbsp;</td>
      <td>
        <input type="hidden" name="contractNumber"value="<%= contractNumber%>"  >
        <input type="text" disabled  maxlength="<%=contractMaxLength%>" size="15" value="<%= contractNumber%>"  tabindex="1">
      </td>
    </tr>
    <tr>
      <td colspan="2" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td align="right" nowrap>Auto Assign Number&nbsp;</td>
      <td align="left" nowrap>
	    <input type="checkbox" name="autoAssignNumberBox" <%= checkBoxStatus %> onClick="autoGenerateContractNumber()" tabindex="2">
	  </td>
    </tr>
    <tr>
      <td align="right" nowrap>SuppressPolicyPages&nbsp;</td>
      <td align="left" nowrap>
	    <input type="checkbox" name="suppressPolicyPages" tabindex="3" <%= suppressPolicyPages %>>
      </td>
    </tr>
    <tr>
      <td colspan="2" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td align="right" nowrap>Issue Date:&nbsp;</td>
      <td align="left" nowrap>
           <input type="text" name="issueDate" value="<%= issueDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.issueDate', f.issueDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
       </td>
    </tr>
    <tr>
      <td colspan="2" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td align="right" nowrap>Termination Status:&nbsp;</td>
      <td align="left" nowrap>
        <select name="terminationStatus" tabindex="7">
          <option> Please Select </option>
          <%
              for(int i = 0; i < terminationStats.length; i++) {

                  String codeTablePK = terminationStats[i].getCodeTablePK() + "";
                  String codeDesc    = terminationStats[i].getCodeDesc();
                  String code        = terminationStats[i].getCode();

                 if (terminationStatus.equalsIgnoreCase(code)) {

                     out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                 }
                 else  {

                     out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                 }
              }

		   %>
        </select>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">&nbsp;</td>
    </tr>
  </table>
  <table class="summary" id="summaryTable" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th width="25%" align="left">Last Name</th>
      <th width="25%" align="left">Anticipated Amt</th>
      <th width="25%" align="left">Amt Received</th>
      <th width="20%" align="left">Type</th>
      <th width="5%" align="left">&nbsp;</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:15%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="depositSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
      <%
          String trClass = "default";
          boolean selected = false;
          String anticipatedAmount = "";
          String amountReceived = "";
          String depositType = "";
          String lastName = "";
          String lockedUnlocked = "&nbsp;";

          List suspenseFKList = new ArrayList();
          
          if (depositsVOs != null)
          {
              for (int i = 0; i < depositsVOs.length; i++)
              {
                  if (!depositsVOs[i].getVoShouldBeDeleted())
                  {
                      anticipatedAmount = depositsVOs[i].getAnticipatedAmount().toString();
                      amountReceived = depositsVOs[i].getAmountReceived().toString();
                      depositType = Util.initString(depositsVOs[i].getDepositTypeCT(), "");
                      if (depositsVOs[i].getSuspenseFK() > 0)
                      {
                          suspenseFKList.add(depositsVOs[i].getSuspenseFK() + "");
                          if (suspenses != null)
                          {
                              for (int j = 0; j < suspenses.length; j++)
                              {
                                  if (suspenses[j].getSuspensePK().longValue() == depositsVOs[i].getSuspenseFK())
                                  {
                                      lastName = Util.initString(suspenses[j].getLastName(), "");

                                      CashBatchContract cashBatchContract = suspenses[j].getCashBatchContract();

                                      if (cashBatchContract != null && cashBatchContract.getReleaseIndicator().equalsIgnoreCase("R"))
                                      {
                                          lockedUnlocked = "<img src=\"/PORTAL/common/images/grnball.gif\">";
                                      }
                                      else
                                      {
                                          lockedUnlocked =  "&nbsp;";
                                      }

                                      j = suspenses.length;
                                  }
                              }
                          }
                      }
                      else
                      {
                          lastName = "";
                      }
        %>
        <tr class="<%= trClass %>" isSelected="<%= selected %>">
          <td width="25%" align="left" nowrap>
            <%= lastName %>
          </td>
          <td width="25%" align="left" nowrap>
            <script>document.write(formatAsCurrency(<%= anticipatedAmount %>))</script>
          </td>
          <td width="25%" align="left" nowrap>
            <script>document.write(formatAsCurrency(<%= amountReceived %>))</script>
          </td>
          <td width="20%" align="left" nowrap>
            <%= depositType %>
          </td>
          <td width="5%" align="left" nowrap>
            <%= lockedUnlocked %>
          </td>
        </tr>
        <%
                  } // end if
              } // end for
          } // end if
          
          if (suspenses != null)
          {
              for (int i = 0; i < suspenses.length; i++)
              {
                  if (!suspenseFKList.contains(suspenses[i].getSuspensePK().toString()))
                  {
                      anticipatedAmount = "0.00";
                      amountReceived = suspenses[i].getSuspenseAmount().toString();
                      depositType = Util.initString(suspenses[i].getPremiumTypeCT(), "");
                      lastName = Util.initString(suspenses[i].getLastName(), "");

                      // Commented the following code becuse it is getting cached version.
//                      CashBatchContract cashBatchContract = suspenses[i].getCashBatchContract();
                      Long cashBatchContractFK = suspenses[i].getCashBatchContractFK();
                      CashBatchContract cashBatchContract = null;
                      if (cashBatchContractFK != null)
                      {
                        cashBatchContract = CashBatchContract.findByPK(cashBatchContractFK);
                      }

                      if (cashBatchContract != null && cashBatchContract.getReleaseIndicator().equalsIgnoreCase("R"))
                      {
                          lockedUnlocked = "<img src=\"/PORTAL/common/images/grnball.gif\">";
                      }
                      else
                      {
                          lockedUnlocked =  "&nbsp;";
                      }
        %>
        <tr class="<%= trClass %>" isSelected="<%= selected %>">
          <td width="25%" align="left" nowrap>
            <%= lastName %>
          </td>
          <td width="25%" align="left" nowrap>
            <script>document.write(formatAsCurrency(<%= anticipatedAmount %>))</script>
          </td>
          <td width="25%" align="left" nowrap>
            <script>document.write(formatAsCurrency(<%= amountReceived %>))</script>
          </td>
          <td width="20%" align="left" nowrap>
            <%= depositType %>
          </td>
          <td width="5%" align="left" nowrap>
            <%= lockedUnlocked %>
          </td>
        </tr>
        <%
                  } //end if (suspensePK is not in list)
              } //end for (suspenses loop)
          } //end if (suspense != null
        %>
    </table>
  </span>
  <table width="100%" border="0" cellspacing="6" cellpadding="0">
    <tr>
      <td align="right" nowrap colspan="2">
        <input type="button" name="report" value="Report" onClick="generateIssueReport()" tabindex="8">
        <input type="button" name="enter" value="Enter" onClick="save()" tabindex="9">
        <input type="button" name="cancel" value="Cancel" onClick="cancelDialog()" tabindex="10">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">
  <input type="hidden" name="checkBoxStatus" value="">
  <input type="hidden" name="suppressPolicyPagesStatus" value="">
  <input type="hidden" name="contractTarget" value="">
  <input type="hidden" name="message" value="">

</form>
</body>
</html>
