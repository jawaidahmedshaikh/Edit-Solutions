<!--
 * (c) 2000 - 2008 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 -->
<!-- financialValuesTrad.jsp //-->

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
    String tamraStartDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(baseLife.getTamraStartDate());

    EDITBigDecimal totalPUT = totalNonGuarPUT.addEditBigDecimal(totalGuarPUT);
    String totalNonGuarPlusGuar = Util.initEDITBigDecimal(totalPUT.toString(), new EDITBigDecimal()).toString();

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
    EDITBigDecimal loanPayoff = new EDITBigDecimal(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR);
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
<body class="mainTheme" bgcolor="#DDDDDD" onLoad="init()">
<form name="financialValueForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="45%" border="0" cellspacing="6" cellpadding="0">
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
      <td nowrap align="right">Paid To Date:</td>
      <td nowrap align="left">
        <input type="text" name="paidToDate" size="19" maxlength="19" disabled value="<%= DateTimeUtil.formatEDITDateAsMMDDYYYY(baseLife.getPaidToDate()) %>" >
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
      <td nowrap align="right">Mortality Credit:</td>
      <td nowrap align="left">
        <input type="text" name="mortalityCredit" size="19" maxlength="19" disabled value="<%= totalMortalityCredit.toString() %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Outstanding Advance Balance:</td>
      <td nowrap align="left">
        <input type="text" name="outstandingAdvanceBalance" size="19" maxlength="19" disabled value="<%= outstandingAdvanceBalance %>" CURRENCY>
      </td>
      <td nowrap align="right">Endowment Credit:</td>
      <td nowrap align="left">
        <input type="text" name="endowmentCredit" size="19" maxlength="19" disabled value="<%= totalEndowmentCredit.toString() %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Loan Payoff:</td>
      <td nowrap align="left">
        <input type="text" name="loanPayoff" size="19" maxlength="19" disabled value="<%= loanPayoff %>" CURRENCY>
      </td>
      <td nowrap align="right">Excess Interest Credit:</td>
      <td nowrap align="left">
        <input type="text" name="excessInterestCredit" size="19" maxlength="19" disabled value="<%= totalExcessIntCredit.toString() %>" CURRENCY>
      </td>
    </tr>

    <tr>
      <td nowrap align="right">TI Lien Principle:</td>
      <td nowrap align="left">
        <input type="text" name="tiLienPrinciple" size="19" maxlength="19" disabled value="<%= tiLienPrinciple.toString() %>" CURRENCY>
      </td>
      <td nowrap align="right">One Year Term Amount:</td>
      <td nowrap align="left">
        <input type="text" name="oneYearTerm" size="19" maxlength="19" disabled value="<%= totalOneYearTerm.toString() %>" CURRENCY>
      </td>
    </tr>

    <tr>
      <td nowrap align="right">TI Lien Interest:</td>
      <td nowrap align="left">
        <input type="text" name="tiLienInterest" size="19" maxlength="19" disabled value="<%= tiLienInterest %>" >
      </td>
      <td nowrap align="right">Non-Guar Paid Up Term:</td>
      <td nowrap align="left">
        <input type="text" name="nonGuarPaidUpTerm" size="19" maxlength="19" disabled value="<%= totalNonGuarPUT.toString() %>" CURRENCY>
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

      <td nowrap align="right">Guar Paid Up Term:</td>
      <td nowrap align="left">
        <input type="text" name="guarPaidUpTerm" size="19" maxlength="19" disabled value="<%= totalGuarPUT.toString() %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <!-- COMMENTED OUT - DON'T WANT TO DELIVER TO VISION YET
      <td nowrap align="right">TAMRA Start Date:</td>
      <td nowrap align="left">
        <input type="text" name="tamraStartDate" size="10" maxlength="10" disabled value="<%= tamraStartDate %>">
      </td>
      -->
      <!-- Put in next lines to space next column - remove it when put TAMRA back in -->
      <td nowrap align="right">Current Death Benefit:</td>
      <td nowrap align="left">
        <input type="text" name="currentDeathBenefit" size="19" maxlength="19" disabled value="<%= totalCurrentDeathBenefit.toString() %>" CURRENCY>
      </td>

      <td nowrap align="right">Total NGPUT + GPUT:</td>
      <td nowrap align="left">
        <input type="text" name="totalNonGuarPlusGuar" size="19" maxlength="19" disabled value="<%= totalNonGuarPlusGuar %>" CURRENCY>
      </td>
    </tr>
    <tr>
     <td nowrap align="right">ADB Benefit:</td>
      <td nowrap align="left">
        <input type="text" name="adbBenefit" size="19" maxlength="19" disabled value="<%= adbBenefit.toString() %>" CURRENCY>
      </td>
      <td nowrap align="right">Paid Up Age:</td>
      <td nowrap align="left">
        <input type="text" name="paidUpAge" size="19" maxlength="19" disabled value="<%= paidUpAge %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="right">Level Term Benefit:</td>
      <td nowrap align="left">
        <input type="text" name="levelTermBenefit" size="19" maxlength="19" disabled value="<%= levelTermBenefit.toString() %>" CURRENCY>
      </td>
      <td nowrap align="right"></td>
      <td nowrap align="left"></td>
    </tr>
    <tr>
      <td nowrap align="right">Net Death Benefit:</td>
      <td nowrap align="left">
        <input type="text" name="netDeathBenefit" size="19" maxlength="19" disabled value="<%= netDeathBenefit.toString() %>" CURRENCY>
      </td>
    </tr>
    <tr>
      <td colspan="3">&nbsp;</td>
      <td align="right">
        <input type="button" name="close" value="Close" onClick="closeWindow()">
      </td>
    </tr>

  </table>
</form>
</body>
</html>
