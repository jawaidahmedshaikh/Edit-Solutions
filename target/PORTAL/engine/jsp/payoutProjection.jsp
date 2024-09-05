<!-- ****** JAVA CODE ***** //-->

<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<jsp:useBean id="pageBean"
    class="fission.beans.PageBean" scope="request"/>

<%
	String issueAge = pageBean.getValue("issueAge");

	String genderId = pageBean.getValue("genderId");

	if (genderId.equalsIgnoreCase("M"))
		genderId = "Male";
	else if (genderId.equalsIgnoreCase("F"))
		genderId = "Female";

	String classId = pageBean.getValue("classId");

	if (classId.equalsIgnoreCase("N"))
		classId = "Non Smoker";
	else if (classId.equalsIgnoreCase("S"))
		classId = "Smoker";
	else if (classId.equalsIgnoreCase("P"))
		classId = "Preferred";
	else
		classId = "noValue";

	String annuityOptionId = pageBean.getValue("annuityOptionId");

	if (annuityOptionId.equalsIgnoreCase("LOA")) {

        annuityOptionId = "Life";
    }

    else if (annuityOptionId.equalsIgnoreCase("PCA")) {

        annuityOptionId = "PerCert";
    }

    else if (annuityOptionId.equalsIgnoreCase("LPC")) {

        annuityOptionId = "LifePerCert";
    }

    else if (annuityOptionId.equalsIgnoreCase("JSA")) {

        annuityOptionId = "JTLife";
    }

	else if (annuityOptionId.equalsIgnoreCase("JPC")) {

        annuityOptionId = "JTPerCert";
    }

    else if (annuityOptionId.equalsIgnoreCase("LCR")) {

        annuityOptionId = "LifeCashRefund";
    }

    else if (annuityOptionId.equalsIgnoreCase("AMC")) {

        annuityOptionId = "AmtCert";
    }

    else if (annuityOptionId.equalsIgnoreCase("INR")) {

        annuityOptionId = "InstRef";
    }

    else if (annuityOptionId.equalsIgnoreCase("INT")) {

        annuityOptionId = "IntOnly";
    }

    else if (annuityOptionId.equalsIgnoreCase("TML")) {

        annuityOptionId = "TmpLife";
    }

	String certainPeriod = pageBean.getValue("certainPeriod");


	String frequencyId = pageBean.getValue("frequencyId");


    if (frequencyId.equalsIgnoreCase("AN")) {
            frequencyId = "Annual";
    }
    else if (frequencyId.equalsIgnoreCase("SA")) {
            frequencyId = "Semi";
    }
     else if (frequencyId.equalsIgnoreCase("QU")) {
            frequencyId = "Quarterly";
    }
    else if (frequencyId.equalsIgnoreCase("BM")) {
            frequencyId = "BiMonthly";
    }
    else if (frequencyId.equalsIgnoreCase("MO")) {
            frequencyId = "Monthly";
    }
	else if (frequencyId.equalsIgnoreCase("BW")) {
           frequencyId = "BiWeekly";
    }
    else if (frequencyId.equalsIgnoreCase("WE")) {
            frequencyId = "Weekly";
    }
	else
	{
		frequencyId = "no value";
	}

	String payoutBasisId = pageBean.getValue("payoutBasisId");
	if(payoutBasisId.equalsIgnoreCase("B"))
	{
		payoutBasisId = "Beginning of Period";
	}
	else if(payoutBasisId.equalsIgnoreCase("E"))
	{
		payoutBasisId = "End of Period";
	}
	else
	{
		payoutBasisId = "no value";
	}

	String nonQualInd = pageBean.getValue("nonQualInd");
	if(nonQualInd.equalsIgnoreCase("Y"))
	{
		nonQualInd = "Non Qualified";
	}
	else if(nonQualInd.equalsIgnoreCase("N"))
	{
		nonQualInd = "Qualified";
	}
	else
	{
		nonQualInd = "no value";
	}

	String interestRate = "no value";

	String purchaseAmount = pageBean.getValue("purchaseAmount");
	String modalAmount = pageBean.getValue("paymentAmount");

	String PurchaseAmount = "0";
	String ModalAmount = "0";
	if(purchaseAmount.equals("0"))
	{
		PurchaseAmount = pageBean.getValue("PurchaseAmount");
		ModalAmount = pageBean.getValue("paymentAmount");
	}

	if(modalAmount.equals("0"))
	{
		ModalAmount = pageBean.getValue("ModalAmount");
		PurchaseAmount = pageBean.getValue("purchaseAmount");

	}
	String CertainDuration      = pageBean.getValue("CertainDuration");
	String ExclusionRatio       = pageBean.getValue("ExclusionRatio");
	String ExpectedReturn       = pageBean.getValue("ExpectedReturn");
	String Fees                 = pageBean.getValue("Fees");
	String Loads                = pageBean.getValue("Loads");
	String PremiumTaxes         = pageBean.getValue("PremiumTaxes");
//	String PurchaseAmount       = pageBean.getValue("PurchaseAmount");
	String YearlyTaxableBenefit = pageBean.getValue("YearlyTaxableBenefit");

	String ExcessInterest = "no Value";
	String MRDAmount = "no value";
	String FinalDistributionAmount = "no Value";

%>


<html>
<head>
<title>ENGINE - Calculation Toolkit</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">

<!-- ****** JAVAScript CODE ***** //-->

<script LANGUAGE="JavaScript">

	var f  = null;
	var tf = null;

	function getMode() {

		return "Rate Table";
	}

	function sendTransactionAction(transaction, action, target) {

		tf.transaction.value = transaction;
		tf.action.value      = action;
		tf.target			 = target;
		tf.submit();
	}

	function init() {

		}

</script>
</head>

<!-- ****** HTML CODE ***** //-->

<body style="margin-top:5;margin-left:25" onload="init()">

<form method="post" action="/PORTAL/servlet/RequestManager" name="tableForm">

<p>
<table width="100%" border="0" align"center" cellspacing="2" cellpadding="1">
    <tr>
		<td class="dataHeading" colspan="4">
            <font size="5">PAYOUT</font>
		</td>
	</tr>
	<tr>
            <td colspan="6">&nbsp; </td>
    </tr>
    <tr>
        <td class="dataHeading">
            SEX:
		</td>
		<td> &nbsp
				<%= genderId %>
		</td>
		<td class="dataHeading" >
            AGE:
		</td>
		<td> &nbsp
				<%= issueAge %>
		</td>
        <td class="dataHeading" >
			CLASS:
		</td>
		<td> &nbsp
				<%= classId %>
		</td>
		<td>
	</tr>
	<tr>
            <td colspan="6">&nbsp; </td>
    </tr>
	<tr>
		<td class="dataHeading">
            ANNUITY OPTION:
		</td>
		<td> &nbsp
				<%= annuityOptionId %>
		</td>
		<td class="dataHeading">
            CERTAIN PERIOD:
		</td>
		<td> &nbsp
				<%= certainPeriod %>
		</td>
		<td class="dataHeading">
            FREQUENCY:
		</td>
		<td> &nbsp
				<%= frequencyId %>
		</td>
	</tr>
	<tr>
            <td colspan="6">&nbsp; </td>
    </tr>
	<tr>
		<td class="dataHeading">
            PAYOUT BASIS:
		</td>
		<td> &nbsp
				<%= payoutBasisId %>
		</td>
		<td class="dataHeading">
            QUAL/NON-QUAL:
		</td>
		<td> &nbsp
				<%= nonQualInd %>
		</td>
		<td class="dataHeading">
            INTEREST RATE:
		</td>
		<td> &nbsp
				<%= interestRate %>
		</td>
	</tr>
	<tr>
            <td colspan="6">&nbsp; </td>
    </tr>
	<tr>
		<td class="dataHeading">
            PURCHASE AMOUNT:
		</td>
		<td> &nbsp
				<%= PurchaseAmount %>
		</td>
		<td class="dataHeading" colspan="2">
            MODAL AMOUNT:
		</td>
		<td> &nbsp
				<%= ModalAmount %>
		</td>
	</tr>
	<tr>
            <td colspan="6">&nbsp; </td>
    </tr>
	<tr>
		<td class="dataHeading" colspan="2">
            EXPECTED RETURN:
		</td>
		<td> &nbsp
				<%= ExpectedReturn %>
		</td>
		<td class="dataHeading" colspan="2">
            EXCLUSION RATIO:
		</td>
		<td> &nbsp
				<%= ExclusionRatio %>
		</td>
	</tr>
	<tr>
            <td colspan="6">&nbsp; </td>
    </tr>
	<tr>
		<td class="dataHeading" colspan="2">
            YEARLY TAXABLE RETURN:
		</td>
		<td> &nbsp
				<%= YearlyTaxableBenefit %>
		</td>
		<td class="dataHeading" colspan="2">
            PREMIUM TAXES:
		</td>
		<td> &nbsp
				<%= PremiumTaxes %>
		</td>
	</tr>
	<tr>
            <td colspan="6">&nbsp; </td>
    </tr>
	<tr>
		<td class="dataHeading" colspan="2">
            FRONT END LOADS:
		</td>
		<td> &nbsp
				<%= Loads %>
		</td>
		<td class="dataHeading" colspan="2">
            FEES:
		</td>
		<td> &nbsp
				<%= Fees %>
		</td>
	</tr>
	<tr>
            <td colspan="6">&nbsp; </td>
    </tr>
	<tr>
		<td class="dataHeading" colspan="2">
            EXCESS INTEREST:
		</td>
		<td> &nbsp
				<%= ExcessInterest %>
		</td>
		<td class="dataHeading" colspan="2">
            MRD AMOUNT:
		</td>
		<td> &nbsp
				<%= MRDAmount %>
		</td>
	</tr>
	<tr>
            <td colspan="6">&nbsp; </td>
    </tr>
	<tr>
		<td class="dataHeading" colspan="2">
            FINAL DISTRIBUTION AMOUNT:
		</td>
		<td> &nbsp
				<%= FinalDistributionAmount %>
		</td>
	</tr>
        <tr>
            <td colspan="9">&nbsp; </td>
        </tr>
        </table>
        </td>
    </tr>
</table>

<!-- ****** HIDDEN FIELDS ***** //-->
<!-- ****** Iframe has the hidden values ****//-->


<!-- ??? User key is not shown on TabMod - we are just passing
     it to be consistant

//-->

</form>
</body>
</html>

