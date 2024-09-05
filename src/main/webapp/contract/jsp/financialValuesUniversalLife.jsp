<!--
 * (c) 2000 - 2008 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 -->
<!-- financialValuesUniversalLife.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.*,
                 event.*,
                 contract.*,
                 fission.utility.*" %>
<%@ page import="client.ClientDetail" %>

<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
	PageBean formBean  = contractMainSessionBean.getPageBean("formBean");

    String segmentPK   = formBean.getValue("segmentPK");
    String targetfield = "financialHistory.GrossAmount";

    //  Find base Segment
    Segment baseSegment = Segment.findByPK(new Long(segmentPK));   // includes Life table

    //  Find riders
    Segment[] riderSegments = Segment.findRidersBy_SegmentPK_V4(new Long(segmentPK));     // includes Life tables

    Life baseLife = baseSegment.getLife();

    //  Determine the date to display in the last anniversary date field.  If the paidToDate is less than the Segment's
    //  lastAnniversary, display the Segment's less 1 year.  If the paidToDate >= than the Segment's lastAnniversaryDate,
    //  display the Segment's.
    String lastAnnivDate = null;

    if (baseLife.getPaidToDate() != null)
    {
        EDITDate lastAnniversaryDateEDITDate = baseSegment.getLastAnniversaryDate();
        while (lastAnniversaryDateEDITDate.after(baseLife.getPaidToDate()))
        {
            lastAnniversaryDateEDITDate = lastAnniversaryDateEDITDate.subtractYears(1);
        }

        lastAnnivDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(lastAnniversaryDateEDITDate);
    }

    EDITBigDecimal premiumsPaidPolicyYTD = FinancialHistory.sumPremiumsPaidTD(new Long(segmentPK), baseSegment.getLastAnniversaryDate(), new EDITDate(), targetfield);
    EDITBigDecimal premiumsPaidToDate = FinancialHistory.sumPremiumsPaidTD(new Long(segmentPK), new EDITDate(EDITDate.DEFAULT_MIN_DATE), new EDITDate(), targetfield);
    EDITBigDecimal totalAdvanceBalance = AgentSnapshot.sumAvanceAmount(new Long(segmentPK));
    EDITBigDecimal totalAdvanceRecovery = AgentSnapshot.sumAvanceRecovery(new Long(segmentPK));
    EDITBigDecimal outstandingAdvanceBalance = totalAdvanceBalance.subtractEditBigDecimal(totalAdvanceRecovery);
    EDITBigDecimal totalLTCPayments = FinancialHistory.sumLTCPayments(new Long(segmentPK), new EDITDate(EDITDate.DEFAULT_MIN_DATE), new EDITDate(), targetfield);

    //Accum across all Life rows base and riders
    EDITBigDecimal totalCurrentDeathBenefit = new EDITBigDecimal();

    if (baseSegment.getStatus().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_PUTERM) ||
        baseSegment.getStatus().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_REDUCED_PAIDUP))
    {
        totalCurrentDeathBenefit = totalCurrentDeathBenefit.addEditBigDecimal(baseLife.getRPUDeathBenefit());
    }
    else
    {
        totalCurrentDeathBenefit = totalCurrentDeathBenefit.addEditBigDecimal(baseLife.getCurrentDeathBenefit());
    }

    EDITBigDecimal totalMortalityCredit = baseLife.getMortalityCredit();
    EDITBigDecimal totalEndowmentCredit = baseLife.getEndowmentCredit();
    EDITBigDecimal totalExcessIntCredit = baseLife.getExcessInterestCredit();
    EDITBigDecimal totalOneYearTerm = baseLife.getOneYearTerm();
    EDITBigDecimal totalNonGuarPUT = baseLife.getNonGuarPaidUpTerm();
    EDITBigDecimal totalGuarPUT = baseLife.getGuarPaidUpTerm();

    //  Add in the riders
    // ** Only include the riders if they are active or, if they have lapsed, they must have been active at the time of
    //  their lapse date
    if (riderSegments != null)
    {
        for (int i = 0; i < riderSegments.length; i++)
        {
            Segment riderSegment = riderSegments[i];

            if (riderSegment.getLife() != null && isActive(riderSegment))
            {
                Life riderLife = riderSegment.getLife();

                if (baseSegment.getStatus().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_PUTERM) ||
                    baseSegment.getStatus().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_REDUCED_PAIDUP))
                {
                    totalCurrentDeathBenefit = totalCurrentDeathBenefit.addEditBigDecimal(riderLife.getRPUDeathBenefit());
                }
                else
                {
                    totalCurrentDeathBenefit = totalCurrentDeathBenefit.addEditBigDecimal(riderLife.getCurrentDeathBenefit());
                }
                totalMortalityCredit = totalMortalityCredit.addEditBigDecimal(riderLife.getMortalityCredit());
                totalEndowmentCredit = totalEndowmentCredit.addEditBigDecimal(riderLife.getEndowmentCredit());
                totalExcessIntCredit = totalExcessIntCredit.addEditBigDecimal(riderLife.getExcessInterestCredit());
                totalOneYearTerm = totalOneYearTerm.addEditBigDecimal(riderLife.getOneYearTerm());
                totalNonGuarPUT = totalNonGuarPUT.addEditBigDecimal(riderLife.getNonGuarPaidUpTerm());
                totalGuarPUT = totalGuarPUT.addEditBigDecimal(riderLife.getGuarPaidUpTerm());
            }
        }
    }

    String tamra = Util.initEDITBigDecimal(baseLife.getTamra().toString(), new EDITBigDecimal()).toString();

    String tamraStartDate = "";
    String MAPEndDate = (baseLife.getMAPEndDate() == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(baseLife.getMAPEndDate()));
    String accum7Pay = "";
    if (baseLife.getTamraStartDate() != null)
    {
    	tamraStartDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(baseLife.getTamraStartDate());
	    EDITBigDecimal numYearsElapsed = Util.initEDITBigDecimal(baseLife.getTamraStartDate().getElapsedFullYears(new EDITDate()) + "", new EDITBigDecimal());
	    accum7Pay = baseLife.getTamra().multiplyEditBigDecimal(numYearsElapsed).toString();
    }
    
    String option7702CT = (baseLife.getOption7702CT() == null ? "" : baseLife.getOption7702CT());
    String guidelineSinglePremium = Util.initEDITBigDecimal(baseLife.getGuidelineSinglePremium().toString(), new EDITBigDecimal()).toString();
    String guidelineLevelPremium = Util.initEDITBigDecimal(baseLife.getGuidelineLevelPremium().toString(), new EDITBigDecimal()).toString();
    String cumGuidelineLevelPremium = Util.initEDITBigDecimal(baseLife.getCumGuidelineLevelPremium().toString(), new EDITBigDecimal()).toString();
 	String mecStatus = (baseLife.getMECStatusCT() == null ? "" : baseLife.getMECStatusCT());
    
    EDITBigDecimal totalPUT = totalNonGuarPUT.addEditBigDecimal(totalGuarPUT);
    String totalNonGuarPlusGuar = Util.initEDITBigDecimal(totalPUT.toString(), new EDITBigDecimal()).toString();

    PremiumDue premiumDue = PremiumDue.findBySegmentPK_LatestEffectiveDate(baseSegment.getSegmentPK());

    FinancialHistory[] financialHistories = FinancialHistory.findBySegment_LatestMVISTrx(Long.parseLong(segmentPK));
    FinancialHistory financialHistory = null;
    if (financialHistories != null && financialHistories.length > 0) {
    	financialHistory = financialHistories[0];
    }
    
    String currentCorridorPercent = "";
    String currentDeathBenefit = "";
    String surrenderCharge = "";
    		
    if (financialHistory != null) {
    	currentCorridorPercent = (financialHistory.getCurrentCorridorPercent() == null ? "" : financialHistory.getCurrentCorridorPercent().toString());
    	currentDeathBenefit = (financialHistory.getCurrentDeathBenefit() == null ? "" : financialHistory.getCurrentDeathBenefit().toString());
    	surrenderCharge = (financialHistory.getSurrenderCharge() == null ? "" : financialHistory.getSurrenderCharge().toString());
    }
    
    String minimumMonthlyPremium = "";
    String accumulatedMap = "";
    if (premiumDue != null)
    {
    	Map<String, EDITBigDecimal> totalExpectedMonthlyPremiums = CommissionPhase.findTotalExpectedMonthlyPremiums(premiumDue.getPremiumDuePK());
		EDITBigDecimal totalExpectedMonthlyPremium = totalExpectedMonthlyPremiums.get("totalExpectedMonthlyPremium");
		EDITBigDecimal totalPrevCumExpectedMonthlyPremium = totalExpectedMonthlyPremiums.get("totalPrevCumExpectedMonthlyPremium");
		
		if (totalExpectedMonthlyPremium != null)
		{
    		minimumMonthlyPremium = totalExpectedMonthlyPremium.toString();
		}
		
		if (totalPrevCumExpectedMonthlyPremium != null) 
		{
    		accumulatedMap = totalPrevCumExpectedMonthlyPremium.toString();
		}
    }

    String paidUpAge = baseLife.getPaidUpAge() + "";

    //  Determine ADB benefit and LT benefit field values
    EDITBigDecimal adbBenefit = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);
    EDITBigDecimal levelTermBenefit = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);

    if (!baseSegment.getStatus().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_LAPSE)
                && !baseSegment.getStatus().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_REDUCED_PAIDUP)
                && !baseSegment.getStatus().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_EXTENSION)
                && !baseSegment.getStatus().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_REDUCED_BENEFIT)
                && !baseSegment.getStatus().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_PUTERM)) {
            if (riderSegments != null) {
                for (int i = 0; i < riderSegments.length; i++) {
                    Segment riderSegment = riderSegments[i];
                        
                    if (riderSegment.getOptionCodeCT().equalsIgnoreCase(Segment.OPTIONCODECT_RIDER_ACCIDENTAL_DEATH_BENEFIT)) {
                        if (baseSegment.getChargeCodeStatus() != null && !baseSegment.getChargeCodeStatus().equalsIgnoreCase(Segment.SEGMENT_CHARGECODESTATUS)) {
                            //  ADB benefit.  Show nothing (i.e. zero) unless there is an ADB rider, then show it's amount. if Segment status is Death and Chargecodestatus is P show Zero.
                            adbBenefit = adbBenefit.addEditBigDecimal(riderSegment.getAmount());
                        }
                    } else if (riderSegment.getOptionCodeCT().equalsIgnoreCase(Segment.OPTIONCODECT_RIDER_LEVEL_TERM_BENEFIT)) {
                        //  LT benefit.  Show nothing (i.e. zero) unless there is an LT rider, then show it's amount as long as
                        //  it's TermInsured is the same person as the base's Primary Insured
                        ClientDetail termInsuredClientDetail = riderSegment.getTermInsured();
                        ClientDetail primaryInsuredClientDetail = baseSegment.getInsured();
                            
                        if (termInsuredClientDetail.getClientDetailPK().equals(primaryInsuredClientDetail.getClientDetailPK())) {
                            levelTermBenefit = levelTermBenefit.addEditBigDecimal(riderSegment.getAmount());
                        }
                    }
                }
            }
        }

    //  Sum the benefits for the net benefit field
    EDITBigDecimal netDeathBenefit = new EDITBigDecimal().addEditBigDecimal(adbBenefit).addEditBigDecimal(levelTermBenefit).addEditBigDecimal(totalCurrentDeathBenefit);

    netDeathBenefit = netDeathBenefit.subtractEditBigDecimal(totalLTCPayments);
    totalCurrentDeathBenefit = totalCurrentDeathBenefit.subtractEditBigDecimal(totalLTCPayments);

    //  Here are fields that were added to the display but we are not ready to show any values for yet
    EDITBigDecimal loanPayoff = baseSegment.calculateLoanPayoff("PolicyLoan");
    EDITBigDecimal tiLienPrinciple = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);
    EDITBigDecimal tiLienInterest = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);
%>

<%!
    /**
     * Determines if the Segment (specifically, rider) is active or not
     *
     * A segment is active if the termination date is after today's date or, if the segment has a status of Lapse,
     * the lapseDate must be between the effectiveDate and the terminationDate.
     */
    public boolean isActive(Segment segment)
    {
        boolean isActive = false;

        if (segment.getSegmentStatusCT().equalsIgnoreCase(Segment.SEGMENTSTATUSCT_LAPSE))
        {
            Life life = segment.getLife();

            EDITDate lapseDate = life.getLapseDate();

            if (lapseDate.afterOREqual(segment.getEffectiveDate()) && lapseDate.beforeOREqual(segment.getTerminationDate()))
            {
                isActive = true;
            }
        }
        else if (!segment.getTerminationDate().before(new EDITDate()))
        {
            //  hasn't been terminated, active
            isActive = true;
        }

        return isActive;
    }
%>

<html>
<head>
<title>Financial Values</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.financialValueForm;

        formatCurrency();
	}
</script>
</head>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
<body class="mainTheme" bgcolor="#DDDDDD" onLoad="init()">
<form name="financialValueForm" method="post" action="/PORTAL/servlet/RequestManager">
<table class="fieldGroupContainer" id="tableAll" >
  <tr>
  <td>
  <table class="fieldGroup1" id="table1" border="0" cellspacing="6" cellpadding="0">
    <tr>
      <td nowrap align="right">Contract Number:</td>
      <td nowrap align="left">
        <input type="text" name="contractNumber" size="19" maxlength="19" disabled value="<%= baseSegment.getContractNumber() %>" >
      </td>
      <td nowrap align="right">Contract Status:</td>
      <td nowrap align="left">
        <input type="text" name="contractStatus" size="19" maxlength="19" disabled value="<%= baseSegment.getSegmentStatusCT() %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Premiums Paid Policy Year to Date:</td>
      <td nowrap align="left">
        <input type="text" name="premiumsPaidPolicyYTD" size="19" maxlength="19" disabled value="<%= premiumsPaidPolicyYTD %>" CURRENCY>
      </td>
      <td nowrap align="right">Last Anniversary Date:</td>
      <td nowrap align="left">
        <input type="text" name="lastAnnivDate" size="19" maxlength="19" disabled value="<%= lastAnnivDate %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Premiums Paid to Date:</td>
      <td nowrap align="left">
        <input type="text" name="premiumsPaidToDate" size="19" maxlength="19" disabled value="<%= premiumsPaidToDate %>" CURRENCY>
      </td>
      <td nowrap align="right">Current Death Benefit:</td>
      <td nowrap align="left">
        <input type="text" name="currentDeathBenefit" size="19" maxlength="19" disabled value="<%= currentDeathBenefit %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Outstanding Advance Balance:</td>
      <td nowrap align="left">
        <input type="text" name="outstandingAdvanceBalance" size="19" maxlength="19" disabled value="<%= outstandingAdvanceBalance %>" CURRENCY>
      </td>
      <td nowrap align="right">ADB Benefit:</td>
      <td nowrap align="left">
        <input type="text" name="adbBenefit" size="19" maxlength="19" disabled value="<%= adbBenefit.toString() %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Loan Payoff:</td>
      <td nowrap align="left">
        <input type="text" name="loanPayoff" size="19" maxlength="19" disabled value="<%= loanPayoff %>" CURRENCY>
      </td>
      <td nowrap align="right">TI Lien Principle:</td>
      <td nowrap align="left">
        <input type="text" name="tiLienPrinciple" size="19" maxlength="19" disabled value="<%= tiLienPrinciple.toString() %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <!-- COMMENTED OUT - DON'T WANT TO DELIVER TO VISION YET
      <td nowrap align="right">TAMRA:</td>
      <td nowrap align="left">
        <input type="text" name="tamra" size="19" maxlength="19" disabled value="<%= tamra %>" CURRENCY>
      </td>
      -->
      <!-- Put in next lines to space next column - remove it when put TAMRA back in -->
      <td nowrap align="right">Total LTC Payments:</td>
      <td nowrap align="left">
        <input type="text" name="totalLTCPayments" size="19" maxlength="19" disabled value="<%= totalLTCPayments.toString() %>" CURRENCY>
      </td>
      <td nowrap align="right">TI Lien Interest:</td>
      <td nowrap align="left">
        <input type="text" name="tiLienInterest" size="19" maxlength="19" disabled value="<%= tiLienInterest %>" >
      </td>
    </tr>
  </table>
  </td>
  </tr>
  <tr>
  <td>
  <table class="fieldGroup1" id="table2" width="100%" border="0" cellspacing="6" cellpadding="0">
  	<caption>Definition of Life Insurance</caption>
    <tr>
      <td nowrap align="right">Section 7702 Rule:</td>
      <td nowrap align="left">
         <input type="text" name="option7702CT" size="19" maxlength="19" disabled value="<%= option7702CT + "" %>" >
      </td>
      <td nowrap align="right">Guideline Single Premium:</td>
      <td nowrap align="left">
         <input type="text" name="guidelineSinglePremium" size="19" maxlength="19" disabled value="<%= guidelineSinglePremium %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Guideline Level Premium:</td>
      <td nowrap align="left">
         <input type="text" name="guidelineLevelPremium" size="19" maxlength="19" disabled value="<%= guidelineLevelPremium %>" CURRENCY>
      </td>
      <td nowrap align="right">Accumulated GLP:</td>
      <td nowrap align="left">
         <input type="text" name="cumGuidelineLevelPremium" size="19" maxlength="19" disabled value="<%= cumGuidelineLevelPremium %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Current Corridor Percent:</td>
      <td nowrap align="left">
        <input type="text" name="currentCorridorPercent" size="19" maxlength="19" disabled value="<%= currentCorridorPercent %>">
      </td>
    </tr>
  </table>
  </td>
  </tr>
  <tr>
  <td>
  <table class="fieldGroup1" id="table3" width="100%" border="0" cellspacing="6" cellpadding="0">
  	<caption>TAMRA - Section 7702A</caption>
    <tr>
      <td nowrap align="right">MEC Status:</td>
      <td nowrap align="left">
        <input type="text" name="mecStatus" size="19" maxlength="19" disabled value="<%= mecStatus %>" >
      </td>
      <td nowrap align="right">TAMRA Start Date:</td>
      <td nowrap align="left">
         <input type="text" name="tamraStartDate" size="19" maxlength="19" disabled value="<%= tamraStartDate + "" %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right">7-Pay Premium:</td>
      <td nowrap align="left">
         <input type="text" name="tamra" size="19" maxlength="19" disabled value="<%= tamra %>" CURRENCY>
      </td>
      <td nowrap align="right">Accum 7-Pay:</td>
      <td nowrap align="left">
         <input type="text" name="accum7Pay" size="19" maxlength="19" disabled value="<%= accum7Pay %>" CURRENCY>
      </td>
    </tr>
  </table>
  </td>
  </tr>
  <tr>
  <td>
  <table class="fieldGroup1" id="table4" width="100%" border="0" cellspacing="6" cellpadding="0">
  	<caption>Minimum Premium - No Lapse Guarantee</caption>
    <tr>
      <td nowrap align="right">MAP End Date:</td>
      <td nowrap align="left">
        <input type="text" name="MAPEndDate" size="19" maxlength="19" disabled value="<%= MAPEndDate %>">
      </td>
      <td nowrap align="right">Minimum Monthly Premium:</td>
      <td nowrap align="left">
        <input type="text" name="minimumMonthlyPremium" size="19" maxlength="19" disabled value="<%= minimumMonthlyPremium %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Accumulated MAP:</td>
      <td nowrap align="left">
        <input type="text" name="accumulatedMap" size="19" maxlength="19" disabled value="<%= accumulatedMap %>" CURRENCY>
      </td>
    </tr>
  </table>
  </td>
  </tr>
    <tr>
      <td align="center">
        <input type="button" name="close" value="Close" onClick="closeWindow()">
      </td>
    </tr>
</table>
</form>
</body>
</html>
