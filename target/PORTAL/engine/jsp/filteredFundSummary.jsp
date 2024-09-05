<!--
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%@ page import="edit.common.vo.*,
                 fission.utility.*,
                 edit.common.CodeTableWrapper,
                 java.util.StringTokenizer,
                 edit.common.exceptions.VOEditException,
                 edit.common.EDITDate"%>
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%
    VOEditException voEditException = (VOEditException) session.getAttribute("VOEditException");
    String voEditExceptionExists = "false";
    if (voEditException != null){

        voEditExceptionExists = "true";
    }

    String responseMessage = (String) request.getAttribute("responseMessage");

    // User selected items...
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] pricingDirectionCTVOs = codeTableWrapper.getCodeTableEntries("PRICINGDIRECTION");
    CodeTableVO[] annSubBkts = codeTableWrapper.getCodeTableEntries("ANNUALIZEDSUBBUCKET");
    CodeTableVO[] categories = codeTableWrapper.getCodeTableEntries("CATEGORY");

    FilteredFundVO filteredFundVO = (FilteredFundVO) session.getAttribute("selectedFilteredFundVO");

    FundVO[] fundVOs = (FundVO[]) session.getAttribute("fundVOs");
    if (fundVOs == null)  {

        fundVOs = new FundVO[0];
    }

    String filteredFundPK = "";
    String fundFK = "";
    String fundNumber = "";
    String fundName = "";
    String indexingMethodCT = "";
    String fundAdjustmentCT = "";
    String effectiveMonth = "";
    String effectiveDay   = "";
    String effectiveYear  = "";
    String terminationMonth = "";
    String terminationDay   = "";
    String terminationYear  = "";
    String pricingDirection = "";
    String guaranteedDuration = "";
    String indexCapRateGuarPeriod = "";
    String premiumBonusDuration = "";
    String minimumTransferAmount = "";
    String mvaStartingIndexGuarPeriod = "";
    String fundNewClientCloseDate = "";
    String fundNewClientCloseMonth = "";
    String fundNewClientCloseDay = "";
    String annualizedSubBkt = "";
    String fundNewClientCloseYear = "";
    String fundNewDepositCloseMonth = "";
    String fundNewDepositCloseDay = "";
    String fundNewDepositCloseYear = "";
    String fundNewDepositCloseDate = "";
    String contributionLockUpDuration = "";
    String divisionLevelLockUpDuration = "";
    String divisionLockUpEndDate = "";
    String divisionLockUpEndMonth = "";
    String divisionLockUpEndDay = "";
    String divisionLockUpEndYear = "";
    String separateAccountName = "";
    String postLockWithdrawalDateCT = "";
    String divisionFeesLiquidationModeCT = "";
    String categoryCT = "";
    String seriesToSeriesEligibilityInd = "";
    String seriesToSeriesEligibilityStatus = "unchecked";
    String includeInCorrespondenceInd = "";

    if (filteredFundVO != null) {

        filteredFundPK = filteredFundVO.getFilteredFundPK() + "";
        fundFK = filteredFundVO.getFundFK() + "";
        fundNumber = filteredFundVO.getFundNumber();
        indexingMethodCT = Util.initString(filteredFundVO.getIndexingMethodCT(), "");
        fundAdjustmentCT = Util.initString(filteredFundVO.getFundAdjustmentCT(), "");

        EDITDate effectiveDate = new EDITDate(filteredFundVO.getEffectiveDate());

        effectiveYear  = effectiveDate.getFormattedYear();
        effectiveMonth = effectiveDate.getFormattedMonth();
        effectiveDay   = effectiveDate.getFormattedDay();

        EDITDate terminationDate = new EDITDate(filteredFundVO.getTerminationDate());

        terminationYear  = terminationDate.getFormattedYear();
        terminationMonth = terminationDate.getFormattedMonth();
        terminationDay   = terminationDate.getFormattedDay();

        pricingDirection = Util.initString(filteredFundVO.getPricingDirection(), "");
        annualizedSubBkt = Util.initString(filteredFundVO.getAnnualSubBucketCT(), "");
        guaranteedDuration = filteredFundVO.getGuaranteedDuration() + "";
        indexCapRateGuarPeriod = filteredFundVO.getIndexCapRateGuarPeriod() + "";
        premiumBonusDuration = filteredFundVO.getPremiumBonusDuration() + "";
        minimumTransferAmount = Util.initString(filteredFundVO.getMinimumTransferAmount() + "", "0");
        mvaStartingIndexGuarPeriod = filteredFundVO.getMVAStartingIndexGuarPeriod() + "";

        fundNewClientCloseDate = Util.initString(filteredFundVO.getFundNewClientCloseDate(), "");

        if (!fundNewClientCloseDate.equals(""))
        {
            EDITDate fundCloseDate = new EDITDate(fundNewClientCloseDate);
            fundNewClientCloseYear = fundCloseDate.getFormattedYear();
            fundNewClientCloseMonth = fundCloseDate.getFormattedMonth();
            fundNewClientCloseDay = fundCloseDate.getFormattedDay();
        }

        fundNewDepositCloseDate = Util.initString(filteredFundVO.getFundNewDepositCloseDate(), "");

        if (!fundNewDepositCloseDate.equals(""))
        {
            EDITDate fundCloseDate = new EDITDate(fundNewDepositCloseDate);
            fundNewDepositCloseYear = fundCloseDate.getFormattedYear();
            fundNewDepositCloseMonth = fundCloseDate.getFormattedMonth();
            fundNewDepositCloseDay = fundCloseDate.getFormattedDay();
        }

        contributionLockUpDuration = Util.initString(filteredFundVO.getContributionLockUpDuration() + "", "");
        divisionLevelLockUpDuration = Util.initString(filteredFundVO.getDivisionLevelLockUpDuration() + "", "");

        divisionLockUpEndDate = Util.initString(filteredFundVO.getDivisionLockUpEndDate(), "");

        if (!divisionLockUpEndDate.equals(""))
        {
            EDITDate endDate = new EDITDate(divisionLockUpEndDate);
            divisionLockUpEndYear = endDate.getFormattedYear();
            divisionLockUpEndMonth = endDate.getFormattedMonth();
            divisionLockUpEndDay = endDate.getFormattedDay();
        }

        separateAccountName = Util.initString(filteredFundVO.getSeparateAccountName(), "");

        postLockWithdrawalDateCT = Util.initString(filteredFundVO.getPostLockWithdrawalDateCT(), "");
        divisionFeesLiquidationModeCT = Util.initString(filteredFundVO.getDivisionFeesLiquidationModeCT(), "");
        categoryCT = Util.initString(filteredFundVO.getCategoryCT(), "");
        seriesToSeriesEligibilityInd = Util.initString(filteredFundVO.getSeriesToSeriesEligibilityInd(), "N");
        if (seriesToSeriesEligibilityInd.equalsIgnoreCase("Y"))
        {
            seriesToSeriesEligibilityStatus = "checked";
        }
        includeInCorrespondenceInd = Util.initString(filteredFundVO.getIncludeInCorrespondenceInd(), "N");
        if (includeInCorrespondenceInd.equalsIgnoreCase("n"))
        {
            includeInCorrespondenceInd = "unchecked";
    }
        else
        {
            includeInCorrespondenceInd = "checked";
        }
    }

    // Summary items...
    FilteredFundVO[] filteredFundVOs = (FilteredFundVO[]) session.getAttribute("filteredFundVOs");

    // Sort them...
    filteredFundVOs = (FilteredFundVO[]) Util.sortObjects(filteredFundVOs, new String[]{"getFundNumber"});

    if (filteredFundVOs == null){

        filteredFundVOs = new FilteredFundVO[0];
    }
%>

<html>

<head>

<!-- ***** JAVASCRIPT *****  -->

<script language="Javascript1.2">

    var f           = null;
    var previousRow = null;
    var tdElement = null;
    var currentRow = null;
    var voEditExceptionExists = "<%= voEditExceptionExists %>";
    var responseMessage = "<%= responseMessage %>";

    function highlightRow()
    {
        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }

        currentRow.style.backgroundColor = "#FFFFCC";
    }

    function unhighlightRow()
    {
        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }

        var className = currentRow.className;

        if (currentRow.isSelected != "true")
        {
            if (className == "highLighted")
            {
                currentRow.style.backgroundColor = "#FFFFCC";
            }
            else
            {
                currentRow.style.backgroundColor = "#BBBBBB";
            }
        }
    }

    function init()
    {
	    f = document.theForm;

        previousRow = document.getElementById("<%= filteredFundPK %>");

        if (previousRow != null)
        {
            previousRow.scrollIntoView(false);
        }

        checkForResponseMessage();
    }

    function showFundDetails()
    {
    	var tdElement = window.event.srcElement;
    	var currentRow = tdElement.parentElement;

        if (previousRow == null)
        {
            previousRow = currentRow;
        }

        currentRow.style.backgroundColor="#FFFFCC";

        if (previousRow != currentRow)
        {
            previousRow.style.backgroundColor="DCDCDC";
        }

        if (currentRow != null)
        {
            currentRow.scrollIntoView(false);
        }

        previousRow = currentRow;

        f.filteredFundPK.value = currentRow.id;

        sendTransactionAction("FundTran", "showFilteredFundDetails", "main");
    }

    function addNewFilteredFund()
    {
        sendTransactionAction("FundTran", "addNewFilteredFund", "main");
    }

    function saveFilteredFund()
    {
        setEligibilityInd();
        sendTransactionAction("FundTran", "saveFilteredFund", "main");
    }

    function cancelFilteredFund()
    {
        sendTransactionAction("FundTran", "cancelFilteredFundChanges", "main");
    }

    function checkForVOEditException()
    {
        if (voEditExceptionExists == "true")
        {
            openDialog("", "voEditExceptionDialog", "resizable=no,width=" + .75 * screen.width + ",height=" + screen.height/3,"FundTran", "showVOEditExceptionDialog", "voEditExceptionDialog");
        }
    }

    function showRelation()
    {
        sendTransactionAction("FundTran", "showFilteredFundRelations", "main");
    }

    function showSubscriptionRedemptionInfoDialog()
    {
        setEligibilityInd();
        var width = .75 * screen.width;
        var height = .70 * screen.height;

		if (valueIsEmpty(f.filteredFundPK.value))
        {
            alert('Please Select the Filtered Fund');
        }
        else
        {
            openDialog("","subRedInfoDialog","top=0,left=0,resizable=no,width="+width+",height="+height, "FundTran", "showSubscriptionRedemptionInfoDialog");
        }
    }

    function showFeeDescriptionDialog()
    {
        setEligibilityInd();
        var width = .70 * screen.width;
        var height = .55 * screen.height;

        if (valueIsEmpty(f.filteredFundPK.value))
        {
            alert('Please Select the Filtered Fund');
        }
        else
        {
            openDialog("","feeDescriptionDialog","top=0,left=0,resizable=no,width="+width+",height="+height, "FundTran", "showFeeDescriptionDialog");
        }
    }

    function showFeeDialog()
    {
        setEligibilityInd();
        var width = .90 * screen.width;
        var height = .75 * screen.height;

		if (valueIsEmpty(f.filteredFundPK.value))
        {
            alert('Please Select the Filtered Fund');
        }
        else
        {
            openDialog("","feeDialog","top=0,left=0,resizable=no,width="+width+",height="+height, "FundTran", "showFeeDialog");
        }
    }

    function showChargeCodeDialog()
    {
        setEligibilityInd();
        var width = .95 * screen.width;
        var height = .75 * screen.height;

		if (valueIsEmpty(f.filteredFundPK.value))
        {
            alert('Please Select the Filtered Fund');
        }
        else
        {
            openDialog("","chargeCodeDialog","top=0,left=0,resizable=no,width="+width+",height="+height, "FundTran", "showChargeCodeDialog");
        }
    }

    function openDialog(theURL,winName,features,transaction,action)
    {
	    dialog = window.open(theURL,winName,features);

	    sendTransactionAction(transaction, action, winName);
	}

    function sendTransactionAction(transaction, action, target)
    {
		f.transaction.value = transaction;
    	f.action.value = action;

    	f.target = target;

    	f.submit();
    }

    function valueIsEmpty(theValue)
    {
        var valueIsEmpty = false;

        if (theValue == null)
        {
            valueIsEmpty = true;
        }
        else if (theValue == "null")
        {
            valueIsEmpty = true;
        }
        else if (theValue.length == 0)
        {
            valueIsEmpty = true;
        }

        return valueIsEmpty;
    }

    function checkForResponseMessage()
    {
        if (! valueIsEmpty(responseMessage))
        {
            alert(responseMessage);
        }
    }

    function setEligibilityInd()
    {
        if ( f.seriesToSeriesEligibilityStatus.checked == true)
        {
            f.seriesToSeriesEligibilityInd.value = "Y";
        }
        else
        {
            f.seriesToSeriesEligibilityInd.value = "N";
        }
    }

</script>

<title>Filtered Fund Summary</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">

</head>

<body class="mainTheme" onLoad="init(); checkForVOEditException()">

<form  name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

    <span id="mainContent" style="border-style:solid; border-width:1;  position:relative; width:100%;  top:0; left:0; z-index:0; overflow:visible">

    <br>

    <table class="formData" width="100%" height="25%" border="0"  cellspacing="6" cellpadding="0">
      <tr>
        <td nowrap align="right">Fund Name:&nbsp;</td>
        <td nowrap align="left">
          <select name="fundFK">
          <%
              out.println("<option>Please Select</option>");

              for (int i = 0; i < fundVOs.length; i++)
              {
                  String fundPK = fundVOs[i].getFundPK() + "";

                  if (fundFK.equals(fundPK))
                  {
                      out.println("<option selected value=\"" + fundPK + "\">" + fundVOs[i].getName() + "</option>");
                      fundName = fundVOs[i].getName();
                  }
                  else
                  {
                      out.println("<option value=\"" + fundPK + "\">" + fundVOs[i].getName() + "</option>");
                  }
              }
          %>
          </select>
        </td>
        <td align="right">Index Method:&nbsp;</td>
        <td align="left">
          <select name="indexingMethodCT">
          <%
              out.println("<option>Please Select</option>");

              CodeTableVO[] indexingMethods = CodeTableWrapper.getSingleton().getCodeTableEntries("INDEXINGMETHOD");

              for (int i = 0; i < indexingMethods.length; i++)
              {
                  String indexingMethodsCode = indexingMethods[i].getCode();
                  String indexingMethodCodeDesc = indexingMethods[i].getCodeDesc();

                  if (indexingMethodCT.equals(indexingMethodsCode))
                  {
                      out.println("<option selected value=\"" + indexingMethodsCode + "\">" + indexingMethodCodeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option value=\"" + indexingMethodsCode + "\">" + indexingMethodCodeDesc + "</option>");
                  }
              }
          %>
          </select>
        </td>
      </tr>
      <tr>
        <td nowrap align="right">Fund Number:&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="fundNumber" size="6" maxSize="6" value="<%= fundNumber %>">
        </td>
        <td align="right">Fund Adjustment:&nbsp;</td>
        <td align="left">
          <select name="fundAdjustmentCT">
          <%
              out.println("<option>Please Select</option>");

              CodeTableVO[] fundAdjustments = CodeTableWrapper.getSingleton().getCodeTableEntries("FUNDADJUSTMENT");

              for (int i = 0; i < fundAdjustments.length; i++)
              {
                  String fundAdjustmentsCode = fundAdjustments[i].getCode();
                  String fundAdjustmentsCodeDesc = fundAdjustments[i].getCodeDesc();

                  if (fundAdjustmentCT.equals(fundAdjustmentsCode))
                  {
                      out.println("<option selected value=\"" + fundAdjustmentsCode + "\">" + fundAdjustmentsCodeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option value=\"" + fundAdjustmentsCode + "\">" + fundAdjustmentsCodeDesc + "</option>");
                  }
              }
          %>
          </select>
        </td>
      </tr>
      <tr>
        <td nowrap align="right">Effective Date:&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="effectiveMonth" size="2" maxSize="2" value="<%= effectiveMonth %>">
          /
          <input type="text" name="effectiveDay" size="2" maxSize="2" value="<%= effectiveDay %>">
          /
          <input type="text" name="effectiveYear" size="4" maxSize="4" value="<%= effectiveYear %>">
        </td>
        <td nowrap align="right">Termination Date:&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="terminationMonth" size="2" maxSize="2" value="<%= terminationMonth %>">
          /
          <input type="text" name="terminationDay" size="2" maxSize="2" value="<%= terminationDay %>">
          /
          <input type="text" name="terminationYear" size="4" maxSize="4" value="<%= terminationYear %>">
        </td>
      </tr>
      <tr>
        <td nowrap align="right">Fund Close Date - New Client:&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="fundNewClientCloseMonth" size="2" maxSize="2" value="<%= fundNewClientCloseMonth %>">
          /
          <input type="text" name="fundNewClientCloseDay" size="2" maxSize="2" value="<%= fundNewClientCloseDay %>">
          /
          <input type="text" name="fundNewClientCloseYear" size="4" maxSize="4" value="<%= fundNewClientCloseYear %>">
        </td>
        <td nowrap align="right">Fund Close Date - New Deposit:&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="fundNewDepositCloseMonth" size="2" maxSize="2" value="<%= fundNewDepositCloseMonth %>">
          /
          <input type="text" name="fundNewDepositCloseDay" size="2" maxSize="2" value="<%= fundNewDepositCloseDay %>">
          /
          <input type="text" name="fundNewDepositCloseYear" size="4" maxSize="4" value="<%= fundNewDepositCloseYear %>">
        </td>
      </tr>
      <tr>
        <td nowrap align="right">Pricing Direction:&nbsp;</td>
        <td nowrap align="left">
  	      <select name="pricingDirection">
		  <%
              out.println("<option selected>Please Select</option>");

              for(int i = 0; i < pricingDirectionCTVOs.length; i++)
              {
                  String codeTablePK = pricingDirectionCTVOs[i].getCodeTablePK() + "";
                  String codeDesc    = pricingDirectionCTVOs[i].getCodeDesc();
                  String code        = pricingDirectionCTVOs[i].getCode();

                  if (pricingDirection.equals(code))
                  {
                      out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
              }
		  %>
		  </select>
        </td>
        <td nowrap align="right">Guaranteed Duration:&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="guaranteedDuration" size="4" maxSize="4" value="<%= guaranteedDuration %>">
        </td>
      </tr>
      <tr>
        <td nowrap align="right">Index CapRate Guar Period:&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="indexCapRateGuarPeriod" size="4" maxSize="4" value="<%= indexCapRateGuarPeriod %>">
        </td>
        <td nowrap align="right">MVA Starting Index Guar Period:&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="mvaStartingIndexGuarPeriod" size="4" maxSize="4" value="<%= mvaStartingIndexGuarPeriod %>">
        </td>
      </tr>
      <tr>
        <td nowrap align="right">Premium Bonus Duration:&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="premiumBonusDuration" size="4" maxSize="4" value="<%= premiumBonusDuration %>">
        </td>
        <td nowrap align="right">Minimum Transfer Amount:&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="minimumTransferAmount" size="20" maxSize="20" value="<%= minimumTransferAmount %>">
        </td>
      </tr>
      <tr>
        <td nowrap align="right">Contribution Lock Up Duration:&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="contributionLockUpDuration" size="4" maxSize="4" value="<%= contributionLockUpDuration %>">
        </td>
        <td nowrap align="right">Division Level Lock Up Duration:&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="divisionLevelLockUpDuration" size="20" maxSize="20" value="<%= divisionLevelLockUpDuration %>">
        </td>
      </tr>
      <tr>
        <td nowrap align="right">Division Lock Up End Date:&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="divisionLockUpEndMonth" size="2" maxSize="2" value="<%= divisionLockUpEndMonth %>">
          /
          <input type="text" name="divisionLockUpEndDay" size="2" maxSize="2" value="<%= divisionLockUpEndDay %>">
          /
          <input type="text" name="divisionLockUpEndYear" size="4" maxSize="4" value="<%= divisionLockUpEndYear %>">
        </td>
        <td nowrap align="right">Separate Account Name:&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="separateAccountName" size="50" maxSize="50" value="<%= separateAccountName %>">
        </td>
      </tr>
       <tr>
        <td nowrap align="right">Post Lock Withdrawal Date:&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="postLockWithdrawalDateCT" size="20" maxSize="20" value="<%= postLockWithdrawalDateCT %>">
        </td>
        <td nowrap align="right">Division Fees Liquidation Mode:&nbsp;</td>
        <td nowrap align="left">
          <input type="text" name="divisionFeesLiquidationModeCT" size="20" maxSize="20" value="<%= divisionFeesLiquidationModeCT %>">
        </td>
      </tr>
      <tr>
        <td nowrap align="right">Annualized Sub Bucket:&nbsp;</td>
        <td nowrap align="left">
           	      <select name="annualizedSubBucket">
		  <%
              out.println("<option selected>Please Select</option>");
              if( annSubBkts != null )
              {
                  for(int i = 0; i < annSubBkts.length; i++)
                  {
                      String codeTablePK = annSubBkts[i].getCodeTablePK() + "";
                      String codeDesc    = annSubBkts[i].getCodeDesc();
                      String code        = annSubBkts[i].getCode();

                      if (annualizedSubBkt != null &&
                              annualizedSubBkt.equals(code))
                      {
                          out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                      }
                      else
                      {
                          out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                      }
                  }
              }
		  %>
		  </select>
        </td>
        <td nowrap align="right">Category:&nbsp;</td>
        <td nowrap align="left">
           	      <select name="category">
		  <%
              out.println("<option selected>Please Select</option>");
              if(categories != null )
              {
                  for(int i = 0; i < categories.length; i++)
                  {
                      String codeTablePK = categories[i].getCodeTablePK() + "";
                      String codeDesc    = categories[i].getCodeDesc();
                      String code        = categories[i].getCode();

                      if (categoryCT != null && categoryCT.equals(code))
                      {
                          out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                      }
                      else
                      {
                          out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                      }
                  }
              }
		  %>
		  </select>
        </td>
      </tr>
      <tr>
        <td align="right" nowrap>
            Series To Series Eligible:&nbsp;
        </td>
        <td align="left" nowrap colspan="3">
            <input type="checkbox" name="seriesToSeriesEligibilityStatus" <%= seriesToSeriesEligibilityStatus %> onClick="setEligibilityInd();">
        </td>
        <td align="right" nowrap>
            IncludeInCorrespondence:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="checkbox" name="includeInCorrespondenceInd" <%= includeInCorrespondenceInd %>>
        </td>
      </tr>

      <tr>
        <td nowrap align="center">
          <a href ="javascript:showSubscriptionRedemptionInfoDialog()">Subscription/Redemption Info</a>
        </td>
        <td nowrap align="center">
          <a href ="javascript:showFeeDescriptionDialog()">Fee Description</a>
        </td>
        <td nowrap align="center">
          <a href ="javascript:showFeeDialog()">Fees</a>
        </td>
        <td nowrap align="center">
          <a href ="javascript:showChargeCodeDialog()">Charge Code</a>
        </td>
      </tr>
    </table>

    </span>

    <br><br>

    <span id="buttonContent" style="position:relative; width:100%; height:2%; top:0; left:0; z-index:0; overflow:visible">

    <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0">
      <tr height="5%">
        <td align="left" height="14">
          <input type="button" name="add" value= " Add  " onClick="addNewFilteredFund()">
          <input type="button" name="save" value=" Save " onClick="saveFilteredFund()">
		  <input type="button" name="cancel" value="Cancel" onClick="cancelFilteredFund()">
	    </td>
        <td align="right">
          <input type="button" id="btnRelateFilteredFunds" name="relateFunds" value="Filtered Fund Relation" onClick="showRelation()">
        </td>
      </tr>
	</table>

    </span>

    <span style="border-style:solid; border-width:1; position:relative; width:100%; height:50%; top:0; left:0; z-index:0; overflow:visible">

        <table class="summaryArea" height="100%" width="100%" border="0" cellspacing="0" cellpadding="0">
           <tr>
             <th align="left" width="33%">Fund Number</th>
             <th align="left" width="33%">Fund Name</th>
             <th align="left" width="33%">Effective Date</th>
		   </tr>
           <tr>
             <td height="98%" colspan="3">
               <span class="scrollableContent">
                 <table id="filteredFundSummaryTable" class="scrollableArea" width="100%" border="0" cellspacing="0" cellpadding="0">
		         <%
				     String trClass = "";
				     String selected = "";
                     String currentKey = "";
                     String sFundName  = "";
                     String sFundNumber = "";
                     String sEffectiveDate = "";

                     if (filteredFundVOs != null)
                     {
                         for (int i = 0; i < filteredFundVOs.length; i++)
                         {
                             currentKey = filteredFundVOs[i].getFilteredFundPK() + "";

                             if (currentKey.equals(filteredFundPK))
                             {
                                 trClass = "highLighted";

                                 selected = "true";
                             }
                             else
                             {
                                 trClass = "mainEntry";

                                 selected="false";
                             }

                             sFundNumber = filteredFundVOs[i].getFundNumber();

                             sEffectiveDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(new EDITDate(filteredFundVOs[i].getEffectiveDate()));

                             long fundKey = filteredFundVOs[i].getFundFK();

                             for (int f = 0; f < fundVOs.length; f++)
                             {
                                 if (fundVOs[f].getFundPK() == fundKey)
                                 {
                                     sFundName = fundVOs[f].getName();
                                     break;
                                 }
                             }
                 %>
                 <tr class="<%= trClass %>" selected="<%= selected %>" id="<%= currentKey %>"
                     onClick="showFundDetails()" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
                   <td align="left" nowrap width="33%">
                     <%= sFundNumber %>
                   </td>
                   <td align="left" nowrap width="33%">
                     <%= sFundName %>
                   </td>
                   <td align="left" nowrap width="33%">
                     <%= sEffectiveDate %>
                   </td>
                 </tr>
                 <%
                         } // end for
                     }// end if
		         %>
                 <tr class="filler">
                   <td colspan="3">
                     &nbsp;
                   </td>
                 </tr>
                 </table>
               </span>
             </td>
           </tr>
		 </table>
      </span>

<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction"    value="">
<input type="hidden" name="action"         value="">

<input type="hidden" name="filteredFundPK" value="<%= filteredFundPK %>">
<input type="hidden" name="filteredFundFK" value="<%= filteredFundPK %>">
<input type="hidden" name="fundNumber" value="<%= fundNumber %>">
<input type="hidden" name="fundName" value="<%= fundName %>">
<input type="hidden" name="seriesToSeriesEligibilityInd">

</form>
</body>
</html>
