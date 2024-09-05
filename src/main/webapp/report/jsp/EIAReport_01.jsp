<%@ page import="edit.common.vo.*,
                 edit.common.*,
                 fission.utility.*"%>
<%
    IssueDocumentVO issueDocumentVO = (IssueDocumentVO) request.getAttribute("issueDocumentVO");

    String productName = Util.initString(issueDocumentVO.getProductStructureVO().getMarketingPackageName(), "");
    String productDescription = "Equity Indexed Annuity";
    String productPrintTransactionType = "Issue";

    IssueClientVO[] issueClientVOs = issueDocumentVO.getIssueClientVO();
    String agentName1 = "";
    String agentStreet1 = "";
    String agentCityStateZip1 = "";
    String agentName2 = "";
    String agentStreet2 = "";
    String agentCityStateZip2 = "";
    String agentName3 = "";
    String agentStreet3 = "";
    String agentCityStateZip3 = "";
    String agentName4 = "";
    String agentStreet4 = "";
    String agentCityStateZip4 = "";
    String annuitantName = "";
    String annuitantTaxID = "";
    String annuitantDOB = "";
    String annuitantSex = "";
    String jointName = "";
    String jointTaxID = "";
    String jointDOB = "";
    String jointSex = "";
    String primaryName = "";
    String primaryTaxID = "";
    String primaryDOB = "";
    String primarySex = "";
    String primaryStreet = "";
    String primaryCityStateZip = "";
    String secondaryName = "";
    String secondaryTaxID = "";
    String secondaryDOB = "";
    String secondarySex = "";
    String address = "";
    String cityStateZip = "";
    boolean agent1Filled = false;
    boolean agent2Filled = false;
    boolean agent3Filled = false;
    boolean agent4Filled = false;

    for (int i = 0; i < issueClientVOs.length; i++)
    {
        String roleType = issueClientVOs[i].getRoleTypeCT();
        ClientDetailVO clientDetailVO = issueClientVOs[i].getClientDetailVO();
        ClientAddressVO[] clientAddresses = clientDetailVO.getClientAddressVO();
        if (clientAddresses != null && clientAddresses.length > 0)
        {
            ClientAddressVO clientAddressVO = clientDetailVO.getClientAddressVO(0);
            address = Util.initString(clientAddressVO.getAddressLine1(), "");
            cityStateZip = Util.initString(clientAddressVO.getCity() + "," + clientAddressVO.getStateCT() + " " + clientAddressVO.getZipCode(), "");
        }

        String name = Util.initString(clientDetailVO.getFirstName() + " " + clientDetailVO.getLastName(), "");
        if (clientDetailVO.getLastName() == null || clientDetailVO.getLastName().equals(""))
        {
           name = Util.initString(clientDetailVO.getCorporateName(), "");
        }

        String taxId = Util.initString(clientDetailVO.getTaxIdentification(), "");
        String dobyyyymmdd = Util.initString(clientDetailVO.getBirthDate(), "");
        String gender = Util.initString(clientDetailVO.getGenderCT(), "");

        String dob = "";

        if (!dobyyyymmdd.equals(""))
        {
            dob = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(dobyyyymmdd);
        }
        if (roleType.equalsIgnoreCase("Agent"))
        {
           if (!agent1Filled)
           {
               agentName1 =  name;
               agentStreet1 = address;
               agentCityStateZip1 = cityStateZip;
               agent1Filled = true;
           }
           else if (!agent2Filled)
           {
               agentName2 =  name;
               agentStreet2 = address;
               agentCityStateZip2 = cityStateZip;
               agent2Filled = true;
           }
           else if (!agent3Filled)
           {
               agentName3 =  name;
               agentStreet3 = address;
               agentCityStateZip3 = cityStateZip;
               agent3Filled = true;
           }
           else if (!agent4Filled)
           {
               agentName4 =  name;
               agentStreet4 = address;
               agentCityStateZip4 = cityStateZip;
               agent4Filled = true;
           }
        }
        
        if (roleType.equalsIgnoreCase("ANN"))
        {
            annuitantName = name;
            annuitantTaxID = taxId;
            annuitantDOB = dob;
            annuitantSex = gender;
        }
        if (roleType.equalsIgnoreCase("SAN"))
        {
            jointName = name;
            jointTaxID = taxId;
            jointDOB = dob;
            jointSex = gender;
        }
        if (roleType.equalsIgnoreCase("OWN"))
        {
            primaryName = name;
            primaryTaxID = taxId;
            primaryDOB = dob;
            primarySex = gender;
            primaryStreet = address;
            primaryCityStateZip = cityStateZip;
        }
        if (roleType.equalsIgnoreCase("SOW"))
        {
            secondaryName = name;
            secondaryTaxID = taxId;
            secondaryDOB = dob;
            secondarySex = gender;
        }
    }
    SegmentVO segmentVO = issueDocumentVO.getSegmentVO();

    String policyNumber = Util.initString(segmentVO.getContractNumber(), "");

    String[] tokens  = null;
    String policyDate = "";
    String date = Util.initString(segmentVO.getEffectiveDate(), "");
    if (!date.equals(""))
    {
        policyDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(date);
    }

    date = Util.initString(segmentVO.getIssueDate(), "");
    String policyIssueDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(date);

    date = Util.initString(segmentVO.getApplicationReceivedDate(), "");
    String policyAppSignedDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(date);

    String policyGuarMinCashSurrenderRate = Util.initString(issueDocumentVO.getGuarMinCashSurrenderRate().toString(), "");
    String policyFreeLook = Util.initString(issueDocumentVO.getFreeLookDays() + "", "");

    String policyReplacementType = "";
    DepositsVO[] depositsVOs = segmentVO.getDepositsVO();
    // double amount = 0;
    EDITBigDecimal amount = new EDITBigDecimal();
    if (depositsVOs != null && depositsVOs.length > 0)
    {
        policyReplacementType = Util.initString(segmentVO.getDepositsVO(0).getDepositTypeCT(), "");
        for (int i = 0; i < depositsVOs.length; i++)
        {
            // amount = amount + depositsVOs[i].getAmountReceived();
            amount = amount.addEditBigDecimal( depositsVOs[i].getAmountReceived() );
        }
    }

    // String policyPayment = amount + "";
    String policyPayment = amount.toString();
    String policyIsQualified = "No";
    String policyQualifiedType = "None";
    if (segmentVO.getQualNonQualCT().equalsIgnoreCase("Qualified"))
    {
        policyIsQualified = "Yes";
        policyQualifiedType = Util.initString(segmentVO.getQualifiedTypeCT(), "");
    }

    SegmentVO[] riderSegmentVO = segmentVO.getSegmentVO();
    String riderOption = "No";
    String policyNursingHomeWaiver = "No";
    if (riderSegmentVO != null && riderSegmentVO.length > 0)
    {
        riderOption = riderSegmentVO[0].getOptionCodeCT();
    }
    if (riderOption.equalsIgnoreCase("NursingHomeWaiver"))
    {
        policyNursingHomeWaiver = "Yes";
    }

    NoteReminderVO[] noteReminderVO = segmentVO.getNoteReminderVO();
    String policyHasAmendment = "No";
    String noteTypeCT = "";

    if (noteReminderVO != null && noteReminderVO.length > 0)
    {
        for (int i = 0; i < noteReminderVO.length; i++)
        {
            noteTypeCT = noteReminderVO[i].getNoteTypeCT();
            if (noteTypeCT.equalsIgnoreCase("Amendment"))
            {
                policyHasAmendment = "Yes";
            }
        }
    }

    String policyIssueState = Util.initString(segmentVO.getIssueStateCT(), "");

    date = Util.initString(segmentVO.getPayoutVO(0).getPaymentStartDate(), "");
    String policyNormalIncomeDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(date);

    String policyType = Util.initString(issueDocumentVO.getProductStructureVO().getBusinessContractName(), "");

    IssueInvestmentVO[] issueInvestmentVO = issueDocumentVO.getIssueInvestmentVO();

    String policyPremiumBonusAmt = "";
    String policyGrossPremium = "";
    String policyPremiumBonusPct = "";
    String allocationsFixed = "";
    String allocationsAveraging = "";
    String allocationsPTP = "";
    String allocationsTotal = "";
    String allocationsFixedCB = "unchecked";
    String allocationsAveragingCB = "unchecked";
    String allocationsPTPCB = "unchecked";
    String allocationsTotalCB = "unchecked";
    String fixedAllocatedPremium = "N/A";
    String fixedGuarMinIntRate = "N/A";
    String fixedCurrentIntRate = "N/A";
    String indexAtIssue = "";
    String indexCapRateGuarantee = "";
    String averagingAllocatedPremium = "N/A";
    String averagingInitialCap = "N/A";
    String averagingMinimumCap = "N/A";
    String averagingParticRate = "N/A";
    String averagingIndexMargin = "N/A";
    String ptpAllocatedPremium = "N/A";
    String ptpInitialCap = "N/A";
    String ptpMinimumCap = "N/A";
    String ptpParticRate = "N/A";
    String ptpIndexMargin = "N/A";
    /*double grossPremium = amount;
    double polBonusAmt = 0;
    double bonusRate = 0;
    double totalAllocationPct = 0;
    double minimumCap = 0;
    double participationRate = 0;
    double indexMargin = 0;
    int initialGuarPeriod = 0;
    double guaranteedRate = 0;
    double allocationPct = 0;
    double cumDollars = 0;
    double bonusAmt = 0;
    double indexCapRate = 0;*/

    // commented above line(s) for double to BigDecimal conversion
    // sprasad 9/30/2004

    EDITBigDecimal grossPremium = amount;
    EDITBigDecimal polBonusAmt = new EDITBigDecimal();
    EDITBigDecimal bonusRate = new EDITBigDecimal();
    EDITBigDecimal totalAllocationPct = new EDITBigDecimal();
    EDITBigDecimal minimumCap = new EDITBigDecimal();
    EDITBigDecimal participationRate = new EDITBigDecimal();
    EDITBigDecimal indexMargin = new EDITBigDecimal();
    int initialGuarPeriod = 0;
    EDITBigDecimal guaranteedRate = new EDITBigDecimal();
    EDITBigDecimal allocationPct = new EDITBigDecimal();
    EDITBigDecimal cumDollars = new EDITBigDecimal();
    EDITBigDecimal bonusAmt = new EDITBigDecimal();
    EDITBigDecimal indexCapRate = new EDITBigDecimal();

    String bucketInterestRate = "";
    String fundType = "";
    String method = "";
    BucketVO[] bucketVOs = null;

    for (int i = 0; i < issueInvestmentVO.length; i++)
    {
        RatesVO ratesVO = issueInvestmentVO[i].getRatesVO();
        bucketVOs = issueInvestmentVO[i].getInvestmentVO().getBucketVO();
        InvestmentAllocationVO investmentAllocationVO = issueInvestmentVO[i].getInvestmentVO().getInvestmentAllocationVO(0);

        if (bucketVOs != null && bucketVOs.length > 0)
        {
            BucketVO bucketVO = bucketVOs[0];
            /*cumDollars = bucketVO.getCumDollars();
            bonusAmt = bucketVO.getBonusAmount();
            indexCapRate = bucketVO.getIndexCapRate();*/
            // commented above line(s) for double to BigDecimal conversion
            // sprasad 10/1/2004
            if (bucketVO.getCumDollars() != null)
            {
                cumDollars = new EDITBigDecimal( bucketVO.getCumDollars() + "", 2);
            }

            if (bucketVO.getBonusAmount() != null)
            {
                bonusAmt = new EDITBigDecimal( bucketVO.getBonusAmount() + "", 2);
            }

            if ( bucketVO.getIndexCapRate() != null)
            {
                indexCapRate = new EDITBigDecimal( bucketVO.getIndexCapRate() );
            }

            if (bucketVO.getBucketInterestRate() != null)
            {
                bucketInterestRate = Util.initString(bucketVO.getBucketInterestRate().toString(), "");
            }
        }

        if (ratesVO != null)
        {
            /*minimumCap = ratesVO.getMinimumCap();
            participationRate = ratesVO.getParticipationRate();
            indexMargin = ratesVO.getIndexMargin();
            guaranteedRate = ratesVO.getGuaranteedRate();
            bonusRate = ratesVO.getBonusRate();*/
            // commented above line(s) for double to BigDecimal conversion
            // sprasad 10/1/2004
            if ( ratesVO.getMinimumCap() != null)
            {
                minimumCap = new EDITBigDecimal( ratesVO.getMinimumCap() );
            }
            if (ratesVO.getParticipationRate() != null)
            {
                participationRate = new EDITBigDecimal( ratesVO.getParticipationRate() );
            }
            if ( ratesVO.getIndexMargin() != null)
            {
                indexMargin = new EDITBigDecimal( ratesVO.getIndexMargin() );
            }
            if ( ratesVO.getIndexMargin() != null)
            {
                guaranteedRate = new EDITBigDecimal(ratesVO.getGuaranteedRate());
            }
            if (ratesVO.getBonusRate() != null)
            {
                bonusRate = new EDITBigDecimal(ratesVO.getBonusRate());
            }
            initialGuarPeriod = ratesVO.getInitialGuaranteePeriod();
            fundType = Util.initString(ratesVO.getFundType(), "");
            method = Util.initString(ratesVO.getIndexingMethod(), "");
            if (fundType.equalsIgnoreCase("EquityIndex"))
            {
                if (ratesVO.getIndexAtIssue() != null)
                {
                    indexAtIssue = Util.initString(ratesVO.getIndexAtIssue().toString(), "");
                }
                indexCapRateGuarantee = Util.initString(ratesVO.getCapRateGuar() + "", "");
            }
        }

        if (investmentAllocationVO != null)
        {
            // allocationPct = investmentAllocationVO.getAllocationPercent();
            if ( investmentAllocationVO.getAllocationPercent() != null)
            {
                allocationPct = new EDITBigDecimal( investmentAllocationVO.getAllocationPercent() );
            }
        }

        /*totalAllocationPct += allocationPct;
        polBonusAmt += bonusAmt;
        grossPremium += bonusAmt;*/
        // commented above line(s) for double to BigDecimal conversion
        // sprasad 10/1/2004
        totalAllocationPct = totalAllocationPct.addEditBigDecimal(allocationPct);
        polBonusAmt = polBonusAmt.addEditBigDecimal(bonusAmt);
        grossPremium = grossPremium.addEditBigDecimal(bonusAmt);

        if (fundType.equalsIgnoreCase("Fixed"))
        {
            allocationsFixed = allocationPct.toString();
            allocationsFixedCB = "checked";
            fixedAllocatedPremium = cumDollars.toString();
            fixedGuarMinIntRate = Util.initString(guaranteedRate.toString(), "N/A");
            fixedCurrentIntRate = bucketInterestRate;
        }

//        //index fund need to be added
//        indexAtIssue = guaranteedRate + "";
//        indexCapRateGuarantee = initialGuarPeriod + "";

        if (method.equalsIgnoreCase("Averaging"))
        {
            allocationsAveraging = allocationPct.toString();
            allocationsAveragingCB =  "checked";
            averagingAllocatedPremium = cumDollars.toString();
            averagingInitialCap = indexCapRate.toString();
            averagingMinimumCap = minimumCap.toString();
            averagingParticRate = participationRate.toString();
            averagingIndexMargin = indexMargin.toString();
        }

        if (method.equalsIgnoreCase("PointToPoint"))
        {
            allocationsPTP = allocationPct.toString();
            allocationsPTPCB = "checked";
            ptpAllocatedPremium = cumDollars.toString();
            ptpInitialCap = indexCapRate.toString();
            ptpMinimumCap = minimumCap.toString();
            ptpParticRate = participationRate.toString();
            ptpIndexMargin = indexMargin.toString();
        }
    }

    // if (totalAllocationPct > 0)
    if ( totalAllocationPct.isGT("0") )
    {
        allocationsTotal = totalAllocationPct.toString();
        allocationsTotalCB = "checked";
    }

    policyPremiumBonusAmt = polBonusAmt.toString();
    policyGrossPremium = grossPremium.toString();
    policyPremiumBonusPct = bonusRate.toString();
%>

<html xmlns:v="urn:schemas-microsoft-com:vml"
xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:w="urn:schemas-microsoft-com:office:word"
xmlns:dt="uuid:C2F41010-65B3-11d1-A29F-00AA00C14882"
xmlns:st1="urn:schemas-microsoft-com:office:smarttags"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta name="Microsoft Theme 2.00" content="Arctic 011">
<meta http-equiv=Content-Type content="text/html; charset=windows-1252">
<meta name=ProgId content=Word.Document>
<meta name=Generator content="Microsoft Word 11">
<meta name=Originator content="Microsoft Word 11">
<link rel=File-List href="EIAReport_01_files/filelist.xml">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">
	var f = null;

    function init()
    {
		f = document.onlineReportForm;
    }

    function closeReport()
    {
        sendTransactionAction("QuoteDetailTran", "closeOnlineReport", "contractNumberDialog");
        window.close();
    }

</script>
<title>EquiTrust Print Information Sheet</title>
<o:SmartTagType namespaceuri="urn:schemas-microsoft-com:office:smarttags"
 name="place"/>
<o:SmartTagType namespaceuri="urn:schemas-microsoft-com:office:smarttags"
 name="PlaceType"/>
<o:SmartTagType namespaceuri="urn:schemas-microsoft-com:office:smarttags"
 name="PlaceName"/>
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>gfrosti</o:Author>
  <o:LastAuthor>gfrosti</o:LastAuthor>
  <o:Revision>18</o:Revision>
  <o:TotalTime>280</o:TotalTime>
  <o:Created>2004-05-17T17:56:00Z</o:Created>
  <o:LastSaved>2004-05-17T19:31:00Z</o:LastSaved>
  <o:Pages>1</o:Pages>
  <o:Words>322</o:Words>
  <o:Characters>1837</o:Characters>
  <o:Company>SEG</o:Company>
  <o:Lines>15</o:Lines>
  <o:Paragraphs>4</o:Paragraphs>
  <o:CharactersWithSpaces>2155</o:CharactersWithSpaces>
  <o:Version>11.5606</o:Version>
 </o:DocumentProperties>
</xml><![endif]--><!--[if gte mso 9]><xml>
 <w:WordDocument>
  <w:DisplayBackgroundShape/>
  <w:SpellingState>Clean</w:SpellingState>
  <w:GrammarState>Clean</w:GrammarState>
  <w:PunctuationKerning/>
  <w:DefaultTableStyle Number="155">Table Theme</w:DefaultTableStyle>
  <w:ValidateAgainstSchemas/>
  <w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid>
  <w:IgnoreMixedContent>false</w:IgnoreMixedContent>
  <w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText>
  <w:DoNotShadeFormData/>
  <w:Compatibility>
   <w:BreakWrappedTables/>
   <w:SnapToGridInCell/>
   <w:WrapTextWithPunct/>
   <w:UseAsianBreakRules/>
   <w:DontGrowAutofit/>
  </w:Compatibility>
  <w:BrowserLevel>MicrosoftInternetExplorer4</w:BrowserLevel>
 </w:WordDocument>
</xml><![endif]--><!--[if gte mso 9]><xml>
 <w:LatentStyles DefLockedState="false" LatentStyleCount="156">
 </w:LatentStyles>
</xml><![endif]--><!--[if !mso]><object
 classid="clsid:38481807-CA0E-42D2-BF39-B33AF135CC4D" id=ieooui></object>
<style>
st1\:*{behavior:url(#ieooui) }
</style>
<![endif]-->
<style>
<!--
 /* Font Definitions */
 @font-face
	{font-family:Tahoma;
	panose-1:2 11 6 4 3 5 4 4 2 4;
	mso-font-charset:0;
	mso-generic-font-family:swiss;
	mso-font-pitch:variable;
	mso-font-signature:1627421319 -2147483648 8 0 66047 0;}
@font-face
	{font-family:Impact;
	panose-1:2 11 8 6 3 9 2 5 2 4;
	mso-font-charset:0;
	mso-generic-font-family:swiss;
	mso-font-pitch:variable;
	mso-font-signature:647 0 0 0 159 0;}
@font-face
	{font-family:Verdana;
	panose-1:2 11 6 4 3 5 4 4 2 4;
	mso-font-charset:0;
	mso-generic-font-family:swiss;
	mso-font-pitch:variable;
	mso-font-signature:536871559 0 0 0 415 0;}
 /* Style Definitions */
 p.MsoNormal, li.MsoNormal, div.MsoNormal
	{mso-style-parent:"";
	margin-top:0in;
	margin-right:0in;
	margin-bottom:0in;
	margin-left:41.75pt;
	margin-bottom:.0001pt;
	mso-pagination:widow-orphan;
	font-size:12.0pt;
	font-family:Verdana;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:black;}
h1
	{mso-style-next:"Body Text";
	margin-top:0in;
	margin-right:0in;
	margin-bottom:0in;
	margin-left:41.75pt;
	margin-bottom:.0001pt;
	mso-line-height-alt:10.0pt;
	mso-pagination:widow-orphan lines-together;
	page-break-after:avoid;
	mso-outline-level:1;
	font-size:16.0pt;
	font-family:Verdana;
	color:black;
	letter-spacing:-.5pt;
	mso-font-kerning:14.0pt;}
h2
	{mso-style-next:"Body Text";
	margin-top:0in;
	margin-right:0in;
	margin-bottom:0in;
	margin-left:41.75pt;
	margin-bottom:.0001pt;
	mso-line-height-alt:10.0pt;
	mso-pagination:widow-orphan lines-together;
	page-break-after:avoid;
	mso-outline-level:2;
	font-size:14.0pt;
	font-family:Verdana;
	color:black;
	letter-spacing:-.5pt;
	mso-font-kerning:14.0pt;
	font-weight:normal;}
h3
	{mso-style-link:"Heading 3 Char";
	mso-style-next:"Body Text";
	margin-top:0in;
	margin-right:0in;
	margin-bottom:0in;
	margin-left:41.75pt;
	margin-bottom:.0001pt;
	mso-line-height-alt:11.0pt;
	mso-pagination:widow-orphan lines-together;
	page-break-after:avoid;
	mso-outline-level:3;
	font-size:13.0pt;
	font-family:Verdana;
	color:black;
	letter-spacing:-.25pt;
	mso-font-kerning:14.0pt;
	font-weight:normal;}
h4
	{mso-style-next:"Body Text";
	margin-top:0in;
	margin-right:0in;
	margin-bottom:0in;
	margin-left:41.75pt;
	margin-bottom:.0001pt;
	mso-line-height-alt:11.0pt;
	mso-pagination:widow-orphan lines-together;
	page-break-after:avoid;
	mso-outline-level:4;
	font-size:14.0pt;
	font-family:Verdana;
	color:black;
	letter-spacing:-.1pt;
	mso-font-kerning:14.0pt;
	font-weight:normal;}
h5
	{mso-style-next:"Body Text";
	margin-top:0in;
	margin-right:0in;
	margin-bottom:0in;
	margin-left:1.0in;
	margin-bottom:.0001pt;
	mso-line-height-alt:11.0pt;
	mso-pagination:widow-orphan lines-together;
	page-break-after:avoid;
	mso-outline-level:5;
	font-size:13.0pt;
	font-family:Verdana;
	color:black;
	letter-spacing:-.1pt;
	mso-font-kerning:14.0pt;
	font-weight:normal;}
h6
	{mso-style-next:Normal;
	margin-top:12.0pt;
	margin-right:0in;
	margin-bottom:3.0pt;
	margin-left:41.75pt;
	mso-pagination:widow-orphan;
	mso-outline-level:6;
	font-size:11.0pt;
	font-family:Verdana;
	color:black;
	font-weight:normal;}
p.MsoNormalIndent, li.MsoNormalIndent, div.MsoNormalIndent
	{margin-top:0in;
	margin-right:0in;
	margin-bottom:0in;
	margin-left:1.0in;
	margin-bottom:.0001pt;
	mso-pagination:widow-orphan;
	font-size:12.0pt;
	font-family:Verdana;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:black;}
p.MsoHeader, li.MsoHeader, div.MsoHeader
	{mso-style-parent:"Header Base";
	margin-top:0in;
	margin-right:-.75in;
	margin-bottom:0in;
	margin-left:-.75in;
	margin-bottom:.0001pt;
	mso-pagination:widow-orphan lines-together;
	tab-stops:-.75in center 3.0in right 474.0pt;
	font-size:12.0pt;
	font-family:Arial;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:black;
	font-style:italic;
	mso-bidi-font-style:normal;}
p.MsoFooter, li.MsoFooter, div.MsoFooter
	{mso-style-parent:"Header Base";
	margin-top:21.0pt;
	margin-right:-.75in;
	margin-bottom:0in;
	margin-left:-.75in;
	margin-bottom:.0001pt;
	mso-pagination:widow-orphan lines-together;
	tab-stops:-.75in center 3.0in right 474.0pt;
	font-size:12.0pt;
	font-family:Arial;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:black;
	font-weight:bold;
	mso-bidi-font-weight:normal;}
p.MsoClosing, li.MsoClosing, div.MsoClosing
	{margin-top:0in;
	margin-right:0in;
	margin-bottom:0in;
	margin-left:41.75pt;
	margin-bottom:.0001pt;
	mso-line-height-alt:11.0pt;
	mso-pagination:widow-orphan;
	font-size:12.0pt;
	font-family:Verdana;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:black;}
p.MsoSignature, li.MsoSignature, div.MsoSignature
	{mso-style-parent:"Body Text";
	margin-top:33.0pt;
	margin-right:0in;
	margin-bottom:0in;
	margin-left:41.75pt;
	margin-bottom:.0001pt;
	mso-line-height-alt:11.0pt;
	mso-pagination:widow-orphan lines-together;
	page-break-after:avoid;
	font-size:12.0pt;
	font-family:Verdana;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:black;}
p.MsoBodyText, li.MsoBodyText, div.MsoBodyText
	{margin-top:0in;
	margin-right:0in;
	margin-bottom:11.0pt;
	margin-left:41.75pt;
	mso-line-height-alt:11.0pt;
	mso-pagination:widow-orphan;
	font-size:12.0pt;
	font-family:Verdana;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:black;}
p.MsoMessageHeader, li.MsoMessageHeader, div.MsoMessageHeader
	{mso-style-parent:"Body Text";
	margin-top:0in;
	margin-right:0in;
	margin-bottom:0in;
	margin-left:78.0pt;
	margin-bottom:.0001pt;
	text-indent:-.5in;
	line-height:20.75pt;
	mso-pagination:widow-orphan lines-together;
	font-size:12.0pt;
	font-family:Verdana;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:black;}
a:link, span.MsoHyperlink
	{color:#9D454F;
	text-decoration:underline;
	text-underline:single;}
a:visited, span.MsoHyperlinkFollowed
	{color:#814E95;
	text-decoration:underline;
	text-underline:single;}
p.MsoAcetate, li.MsoAcetate, div.MsoAcetate
	{mso-style-noshow:yes;
	margin-top:0in;
	margin-right:0in;
	margin-bottom:0in;
	margin-left:41.75pt;
	margin-bottom:.0001pt;
	mso-pagination:widow-orphan;
	font-size:8.0pt;
	font-family:Tahoma;
	mso-fareast-font-family:"Times New Roman";
	color:black;}
span.Heading3Char
	{mso-style-name:"Heading 3 Char";
	mso-style-locked:yes;
	mso-style-link:"Heading 3";
	mso-ansi-font-size:13.0pt;
	mso-bidi-font-size:13.0pt;
	font-family:Verdana;
	mso-ascii-font-family:Verdana;
	mso-hansi-font-family:Verdana;
	color:black;
	letter-spacing:-.25pt;
	mso-font-kerning:14.0pt;
	mso-ansi-language:EN-US;
	mso-fareast-language:EN-US;
	mso-bidi-language:AR-SA;}
p.CompanyName, li.CompanyName, div.CompanyName
	{mso-style-name:"Company Name";
	margin-top:0in;
	margin-right:-5.75pt;
	margin-bottom:0in;
	margin-left:0in;
	margin-bottom:.0001pt;
	line-height:10.0pt;
	mso-pagination:widow-orphan lines-together;
	font-size:8.0pt;
	mso-bidi-font-size:12.0pt;
	font-family:Verdana;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:black;}
p.DocumentLabel, li.DocumentLabel, div.DocumentLabel
	{mso-style-name:"Document Label";
	mso-style-parent:"";
	mso-style-next:Normal;
	margin-top:0in;
	margin-right:0in;
	margin-bottom:0in;
	margin-left:42.0pt;
	margin-bottom:.0001pt;
	line-height:30.0pt;
	mso-pagination:widow-orphan;
	font-size:30.0pt;
	mso-bidi-font-size:10.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:"Times New Roman";
	letter-spacing:-1.9pt;}
p.Enclosure, li.Enclosure, div.Enclosure
	{mso-style-name:Enclosure;
	mso-style-parent:"Body Text";
	mso-style-next:Normal;
	margin-top:11.0pt;
	margin-right:0in;
	margin-bottom:11.0pt;
	margin-left:41.75pt;
	mso-line-height-alt:11.0pt;
	mso-pagination:widow-orphan lines-together;
	font-size:12.0pt;
	font-family:Verdana;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:black;}
p.HeaderBase, li.HeaderBase, div.HeaderBase
	{mso-style-name:"Header Base";
	margin-top:0in;
	margin-right:-42.0pt;
	margin-bottom:0in;
	margin-left:-.75in;
	margin-bottom:.0001pt;
	mso-pagination:widow-orphan lines-together;
	tab-stops:-.75in center 3.0in right 474.0pt;
	font-size:12.0pt;
	font-family:Arial;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:black;}
p.HeadingBase, li.HeadingBase, div.HeadingBase
	{mso-style-name:"Heading Base";
	mso-style-parent:"Body Text";
	mso-style-next:"Body Text";
	margin-top:0in;
	margin-right:0in;
	margin-bottom:0in;
	margin-left:41.75pt;
	margin-bottom:.0001pt;
	line-height:11.0pt;
	mso-pagination:widow-orphan lines-together;
	page-break-after:avoid;
	font-size:9.0pt;
	mso-bidi-font-size:12.0pt;
	font-family:Arial;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:black;
	letter-spacing:-.5pt;
	mso-font-kerning:14.0pt;}
p.MessageHeaderFirst, li.MessageHeaderFirst, div.MessageHeaderFirst
	{mso-style-name:"Message Header First";
	mso-style-parent:"Message Header";
	mso-style-next:"Message Header";
	margin-top:0in;
	margin-right:0in;
	margin-bottom:0in;
	margin-left:78.0pt;
	margin-bottom:.0001pt;
	text-indent:-.5in;
	line-height:20.75pt;
	mso-pagination:widow-orphan lines-together;
	font-size:12.0pt;
	font-family:Verdana;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:black;}
p.MessageHeaderLast, li.MessageHeaderLast, div.MessageHeaderLast
	{mso-style-name:"Message Header Last";
	mso-style-parent:"Message Header";
	mso-style-next:"Body Text";
	margin-top:0in;
	margin-right:0in;
	margin-bottom:20.0pt;
	margin-left:78.0pt;
	text-indent:-.5in;
	line-height:20.75pt;
	mso-pagination:widow-orphan lines-together;
	border:none;
	mso-border-bottom-alt:solid windowtext .75pt;
	padding:0in;
	mso-padding-alt:0in 0in 22.0pt 0in;
	font-size:12.0pt;
	font-family:Verdana;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:black;}
p.ReturnAddress, li.ReturnAddress, div.ReturnAddress
	{mso-style-name:"Return Address";
	margin-top:0in;
	margin-right:-6.0pt;
	margin-bottom:0in;
	margin-left:0in;
	margin-bottom:.0001pt;
	line-height:10.0pt;
	mso-pagination:widow-orphan lines-together;
	font-size:8.0pt;
	mso-bidi-font-size:12.0pt;
	font-family:Verdana;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:black;}
p.SignatureJobTitle, li.SignatureJobTitle, div.SignatureJobTitle
	{mso-style-name:"Signature Job Title";
	mso-style-parent:Signature;
	mso-style-next:Normal;
	margin-top:0in;
	margin-right:0in;
	margin-bottom:0in;
	margin-left:41.75pt;
	margin-bottom:.0001pt;
	mso-line-height-alt:11.0pt;
	mso-pagination:widow-orphan lines-together;
	page-break-after:avoid;
	font-size:12.0pt;
	font-family:Verdana;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:black;}
p.SignatureName, li.SignatureName, div.SignatureName
	{mso-style-name:"Signature Name";
	mso-style-parent:Signature;
	mso-style-next:"Signature Job Title";
	margin-top:.5in;
	margin-right:0in;
	margin-bottom:0in;
	margin-left:41.75pt;
	margin-bottom:.0001pt;
	mso-line-height-alt:11.0pt;
	mso-pagination:widow-orphan lines-together;
	page-break-after:avoid;
	font-size:12.0pt;
	font-family:Verdana;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:black;}
p.Slogan, li.Slogan, div.Slogan
	{mso-style-name:Slogan;
	margin:0in;
	margin-bottom:.0001pt;
	mso-pagination:widow-orphan;
	mso-element:frame;
	mso-element-frame-width:258.5pt;
	mso-element-frame-height:1.25in;
	mso-element-frame-hspace:9.35pt;
	mso-element-frame-vspace:9.35pt;
	mso-element-wrap:no-wrap-beside;
	mso-element-anchor-vertical:page;
	mso-element-anchor-horizontal:page;
	mso-element-left:48.3pt;
	mso-element-top:bottom;
	mso-element-anchor-lock:locked;
	font-size:24.0pt;
	mso-bidi-font-size:12.0pt;
	font-family:Impact;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:#DFDFDF;
	text-transform:uppercase;
	letter-spacing:1.0pt;}
p.StyleMessageHeaderLastBottomSinglesolidlineAuto075, li.StyleMessageHeaderLastBottomSinglesolidlineAuto075, div.StyleMessageHeaderLastBottomSinglesolidlineAuto075
	{mso-style-name:"Style Message Header Last + Bottom\: \(Single solid line Auto  0\.75\.\.\.";
	mso-style-parent:"Message Header Last";
	margin-top:0in;
	margin-right:0in;
	margin-bottom:2.0pt;
	margin-left:78.0pt;
	text-indent:-.5in;
	line-height:20.75pt;
	mso-pagination:widow-orphan lines-together;
	border:none;
	mso-border-bottom-alt:solid windowtext .75pt;
	padding:0in;
	mso-padding-alt:0in 0in 5.0pt 0in;
	font-size:12.0pt;
	mso-bidi-font-size:10.0pt;
	font-family:Verdana;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:black;}
p.StyleBodyTextLeft0, li.StyleBodyTextLeft0, div.StyleBodyTextLeft0
	{mso-style-name:"Style Body Text + Left\:  0\0022";
	mso-style-parent:"Body Text";
	margin:0in;
	margin-bottom:.0001pt;
	line-height:11.0pt;
	mso-pagination:widow-orphan;
	font-size:11.0pt;
	font-family:Verdana;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:black;}
p.StyleBodyTextLeft01, li.StyleBodyTextLeft01, div.StyleBodyTextLeft01
	{mso-style-name:"Style Body Text + Left\:  0\00221";
	mso-style-parent:"Body Text";
	margin:0in;
	margin-bottom:.0001pt;
	mso-line-height-alt:11.0pt;
	mso-pagination:widow-orphan;
	font-size:12.0pt;
	mso-bidi-font-size:10.0pt;
	font-family:Verdana;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:black;}
p.StyleMessageHeader10ptLeft0, li.StyleMessageHeader10ptLeft0, div.StyleMessageHeader10ptLeft0
	{mso-style-name:"Style Message Header + 10 pt Left\:  0\0022";
	mso-style-parent:"Message Header";
	margin-top:0in;
	margin-right:0in;
	margin-bottom:0in;
	margin-left:.5in;
	margin-bottom:.0001pt;
	text-indent:-.5in;
	line-height:10.0pt;
	mso-pagination:widow-orphan lines-together;
	font-size:10.0pt;
	font-family:Verdana;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:black;}
span.MessageHeaderLabel
	{mso-style-name:"Message Header Label";
	mso-style-parent:"";
	mso-ansi-font-size:9.0pt;
	font-family:Arial;
	mso-ascii-font-family:Arial;
	mso-hansi-font-family:Arial;
	mso-bidi-font-family:Arial;
	letter-spacing:-.2pt;
	font-weight:bold;
	mso-bidi-font-weight:normal;
	vertical-align:baseline;}
p.StyleHeading2Left0, li.StyleHeading2Left0, div.StyleHeading2Left0
	{mso-style-name:"Style Heading 2 + Left\:  0\0022";
	mso-style-parent:"Heading 2";
	margin-top:0in;
	margin-right:0in;
	margin-bottom:6.0pt;
	margin-left:0in;
	mso-line-height-alt:0pt;
	mso-pagination:widow-orphan lines-together;
	page-break-after:avoid;
	mso-outline-level:2;
	font-size:14.0pt;
	mso-bidi-font-size:10.0pt;
	font-family:Verdana;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:black;
	letter-spacing:-.5pt;
	mso-font-kerning:14.0pt;}
p.StyleHeading3Left0LinespacingAtleast0pt, li.StyleHeading3Left0LinespacingAtleast0pt, div.StyleHeading3Left0LinespacingAtleast0pt
	{mso-style-name:"Style Heading 3 + Left\:  0\0022 Line spacing\:  At least 0 pt";
	mso-style-parent:"Heading 3";
	margin-top:0in;
	margin-right:0in;
	margin-bottom:6.0pt;
	margin-left:0in;
	mso-line-height-alt:0pt;
	mso-pagination:widow-orphan lines-together;
	page-break-after:avoid;
	mso-outline-level:3;
	font-size:13.0pt;
	mso-bidi-font-size:10.0pt;
	font-family:Verdana;
	mso-fareast-font-family:"Times New Roman";
	mso-bidi-font-family:"Times New Roman";
	color:black;
	letter-spacing:-.25pt;
	mso-font-kerning:14.0pt;}
span.SpellE
	{mso-style-name:"";
	mso-spl-e:yes;}
@page Section1
	{size:8.5in 11.0in;
	margin:1.0in 1.25in 1.0in 1.25in;
	mso-header-margin:.5in;
	mso-footer-margin:.5in;
	border:solid windowtext 1.0pt;
	mso-border-alt:solid windowtext .5pt;
	padding:24.0pt 24.0pt 24.0pt 24.0pt;
	mso-paper-source:0;}
div.Section1
	{
		page:Section1;  
		background: White;
		border: thin solid Black;
		margin: 20px 20px 20px 20px;
		height: 100%;
		width: 100%;
		}
-->
</style>
<!--[if gte mso 10]>
<style>
 /* Style Definitions */
 table.MsoNormalTable
	{mso-style-name:"Table Normal";
	mso-tstyle-rowband-size:0;
	mso-tstyle-colband-size:0;
	mso-style-noshow:yes;
	mso-style-parent:"";
	mso-padding-alt:0in 5.4pt 0in 5.4pt;
	mso-para-margin:0in;
	mso-para-margin-bottom:.0001pt;
	mso-pagination:widow-orphan;
	font-size:10.0pt;
	font-family:"Times New Roman";
	mso-ansi-language:#0400;
	mso-fareast-language:#0400;
	mso-bidi-language:#0400;}
table.MsoTableTheme
	{mso-style-name:"Table Theme";
	mso-tstyle-rowband-size:0;
	mso-tstyle-colband-size:0;
	border:solid #585858 1.0pt;
	mso-border-alt:solid #585858 .5pt;
	mso-padding-alt:0in 5.4pt 0in 5.4pt;
	mso-border-insideh:.5pt solid #585858;
	mso-border-insidev:.5pt solid #585858;
	mso-para-margin-top:0in;
	mso-para-margin-right:0in;
	mso-para-margin-bottom:0in;
	mso-para-margin-left:41.75pt;
	mso-para-margin-bottom:.0001pt;
	mso-pagination:widow-orphan;
	font-size:10.0pt;
	font-family:"Times New Roman";
	mso-ansi-language:#0400;
	mso-fareast-language:#0400;
	mso-bidi-language:#0400;}
</style>
<![endif]--><!--[if gte mso 9]><xml>
 <o:shapedefaults v:ext="edit" spidmax="6146">
  <o:colormenu v:ext="edit" fillcolor="none"/>
 </o:shapedefaults></xml><![endif]--><!--[if gte mso 9]><xml>
 <o:shapelayout v:ext="edit">
  <o:idmap v:ext="edit" data="1"/>
 </o:shapelayout></xml><![endif]-->
</head>

<body bgColor="#E8E8E8" lang=EN-US link="#9D454F" vlink="#814E95" style='tab-interval:.5in' onLoad="init()">
<form name="onlineReportForm" method="post" action="/PORTAL/servlet/RequestManager">

<div class=Section1>

<p class=DocumentLabel style='margin-left:0in'><span class=SpellE>EquiTrust</span>
Print Information Sheet</p>

<div style='mso-element:para-border-div;border:none;border-bottom:solid windowtext 1.0pt;
mso-border-bottom-alt:solid windowtext .75pt;padding:0in 0in 5.0pt 0in'>

<p class=StyleMessageHeaderLastBottomSinglesolidlineAuto075 style='margin-left:
.5in'>Policy Number <%= policyNumber %></p>

</div>

<p class=MsoBodyText><o:p>&nbsp;</o:p></p>

<p class=StyleHeading2Left0>Product</p>

<p class=StyleBodyTextLeft0>Name: <span class=SpellE><%= productName %></span></p>

<p class=StyleBodyTextLeft0>Description: <span class=SpellE><%= productDescription %></span></p>

<p class=StyleBodyTextLeft0>Print Transaction Type: <span class=SpellE><%= productPrintTransactionType %></span></p>

<p class=StyleBodyTextLeft01><span style='mso-tab-count:2'>                </span></p>

<p class=StyleHeading2Left0>Agent</p>

<table class=MsoTableTheme border=0 cellspacing=0 cellpadding=0 width="100%"
 style='width:100.0%;border-collapse:collapse;mso-yfti-tbllook:480;mso-padding-alt:
 0in 5.4pt 0in 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td width="100%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentName1 %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentStreet1 %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentCityStateZip1 %></span></p>
    <p class=StyleBodyTextLeft0><o:p>&nbsp;</o:p></p>
  </td>
 </tr>
</table>

<p class=StyleHeading2Left0>Agent</p>

<table class=MsoTableTheme border=0 cellspacing=0 cellpadding=0 width="100%"
 style='width:100.0%;border-collapse:collapse;mso-yfti-tbllook:480;mso-padding-alt:
 0in 5.4pt 0in 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td width="100%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentName2 %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentStreet2 %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentCityStateZip2 %></span></p>
    <p class=StyleBodyTextLeft0><o:p>&nbsp;</o:p></p>
  </td>
 </tr>
</table>

<p class=StyleHeading2Left0>Agent</p>

<table class=MsoTableTheme border=0 cellspacing=0 cellpadding=0 width="100%"
 style='width:100.0%;border-collapse:collapse;mso-yfti-tbllook:480;mso-padding-alt:
 0in 5.4pt 0in 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td width="100%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentName3 %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentStreet3 %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentCityStateZip3 %></span></p>
    <p class=StyleBodyTextLeft0><o:p>&nbsp;</o:p></p>
  </td>
 </tr>
</table>

<p class=StyleHeading2Left0>Agent</p>

<table class=MsoTableTheme border=0 cellspacing=0 cellpadding=0 width="100%"
 style='width:100.0%;border-collapse:collapse;mso-yfti-tbllook:480;mso-padding-alt:
 0in 5.4pt 0in 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td width="100%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentName4 %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentStreet4 %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentCityStateZip4 %></span></p>
    <p class=StyleBodyTextLeft0><o:p>&nbsp;</o:p></p>
  </td>
 </tr>
</table>

<p class=MsoNormal><o:p>&nbsp;</o:p></p>

<p class=StyleHeading2Left0>Annuitant</p>

<table class=MsoTableTheme border=0 cellspacing=0 cellpadding=0 width="100%"
 style='width:100.0%;border-collapse:collapse;mso-yfti-tbllook:480;mso-padding-alt:
 0in 5.4pt 0in 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td width="25%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= annuitantName %></span></p>
  </td>
  <td width="25%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Tax ID: <span class=SpellE><%= annuitantTaxID %></span></p>
  </td>
  <td width="25%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>DOB: <span class=SpellE><%= annuitantDOB %></span></p>
  </td>
  <td width="25%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Sex: <span class=SpellE><%= annuitantSex %></span></p>
  </td>
 </tr>
</table>

<p class=StyleBodyTextLeft0><span style='mso-tab-count:1'>          </span></p>

<p class=StyleHeading2Left0>Joint Annuitant</p>

<table class=MsoTableTheme border=0 cellspacing=0 cellpadding=0 width="100%"
 style='width:100.0%;border-collapse:collapse;mso-yfti-tbllook:480;mso-padding-alt:
 0in 5.4pt 0in 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td width="25%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= jointName %></span></p>
  </td>
  <td width="25%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
  <p class=MsoNormal style='margin-left:0in;text-align:justify'>Tax ID: <span
  class=SpellE><%= jointTaxID %></span></p>
  </td>
  <td width="25%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>DOB: <span class=SpellE><%= jointDOB %></span></p>
  </td>
  <td width="25%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Sex: <span class=SpellE><%= jointSex %></span></p>
  </td>
 </tr>
</table>

<p class=StyleHeading2Left0><o:p>&nbsp;</o:p></p>

<p class=StyleHeading2Left0>Owner</p>

<p class=StyleMessageHeader10ptLeft0>Primary</p>

<table class=MsoTableTheme border=0 cellspacing=0 cellpadding=0 width="100%"
 style='width:100.0%;border-collapse:collapse;mso-yfti-tbllook:480;mso-padding-alt:
 0in 5.4pt 0in 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td width="25%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= primaryName %></span></p>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= primaryStreet %></span></p>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= primaryCityStateZip %></span></p>
  </td>
  <td width="25%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Tax ID: <span class=SpellE><%= primaryTaxID %></span></p>
  </td>
  <td width="25%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>DOB: <span class=SpellE><%= primaryDOB %></span></p>
  </td>
  <td width="25%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Sex: <span class=SpellE><%= primarySex %></span></p>
  </td>
 </tr>
</table>

<p class=ReturnAddress><o:p>&nbsp;</o:p></p>

<p class=StyleMessageHeader10ptLeft0>Secondary</p>

<table class=MsoTableTheme border=0 cellspacing=0 cellpadding=0 width="100%"
 style='width:100.0%;border-collapse:collapse;mso-yfti-tbllook:480;mso-padding-alt:
 0in 5.4pt 0in 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td width="25%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= secondaryName %></span></p>
  </td>
  <td width="25%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Tax ID: <span class=SpellE><%= secondaryTaxID %></span></p>
  </td>
  <td width="25%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>DOB: <span class=SpellE><%= secondaryDOB %></span></p>
  </td>
  <td width="25%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Sex: <span class=SpellE><%= secondarySex %></span></p>
  </td>
 </tr>
</table>

<p class=StyleHeading2Left0><o:p>&nbsp;</o:p></p>

<p class=StyleHeading2Left0>Policy Details</p>

<table class=MsoTableTheme border=1 cellspacing=0 cellpadding=0 width="100%"
 style='width:100.0%;border-collapse:collapse;border:none;mso-border-alt:solid #585858 .5pt;
 mso-yfti-tbllook:480;mso-padding-alt:0in 5.4pt 0in 5.4pt;mso-border-insideh:
 .5pt solid #585858;mso-border-insidev:.5pt solid #585858'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes'>
  <td width="16%" valign=top style='width:16.66%;border:solid #585858 1.0pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Policy Date</p>
  </td>
  <td width="16%" valign=top style='width:16.66%;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policyDate %></span></p>
  </td>
  <td width="16%" valign=top style='width:16.66%;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Issue Date</p>
  </td>
  <td width="16%" valign=top style='width:16.66%;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policyIssueDate %></span></p>
  </td>
  <td width="16%" valign=top style='width:16.66%;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>App Date</p>
  </td>
  <td width="16%" valign=top style='width:16.66%;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policyAppSignedDate %></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:1'>
  <td width="16%" valign=top style='width:16.66%;border:solid #585858 1.0pt;
  border-top:none;mso-border-top-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Payment</p>
  </td>
  <td width="16%" valign=top style='width:16.66%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policyPayment %></span></p>
  </td>
  <td width="16%" valign=top style='width:16.66%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Premium<span style='mso-spacerun:yes'> 
  </span>Bonus Amt</p>
  </td>
  <td width="16%" valign=top style='width:16.66%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policyPremiumBonusAmt %></span></p>
  </td>
  <td width="16%" valign=top style='width:16.66%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Gross Premium</p>
  </td>
  <td width="16%" valign=top style='width:16.66%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policyGrossPremium %></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:2;mso-yfti-lastrow:yes'>
  <td width="16%" valign=top style='width:16.66%;border:solid #585858 1.0pt;
  border-top:none;mso-border-top-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Premium Bonus Pct</p>
  </td>
  <td width="16%" valign=top style='width:16.66%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policyPremiumBonusPct %></span></p>
  </td>
  <td width="16%" valign=top style='width:16.66%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Guar Min Cash Surrender Rate</p>
  </td>
  <td width="16%" valign=top style='width:16.66%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policyGuarMinCashSurrenderRate %></span></p>
  </td>
  <td width="16%" valign=top style='width:16.66%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Free Look</p>
  </td>
  <td width="16%" valign=top style='width:16.66%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policyFreeLook %></span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal><o:p>&nbsp;</o:p></p>

<table class=MsoTableTheme border=1 cellspacing=0 cellpadding=0 width="100%"
 style='width:100.0%;border-collapse:collapse;border:none;mso-border-alt:solid #585858 .5pt;
 mso-yfti-tbllook:480;mso-padding-alt:0in 5.4pt 0in 5.4pt;mso-border-insideh:
 .5pt solid #585858;mso-border-insidev:.5pt solid #585858'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes'>
  <td width="20%" valign=top style='width:20.06%;border:solid #585858 1.0pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Replacement<span style='font-size:12.0pt;
  mso-bidi-font-size:10.0pt'><o:p></o:p></span></p>
  <p class=StyleBodyTextLeft0>Type</p>
  </td>
  <td width="13%" valign=top style='width:13.42%;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policyReplacementType %></span></p>
  </td>
  <td width="20%" valign=top style='width:20.22%;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Is Qualified</p>
  </td>
  <td width="19%" valign=top style='width:19.42%;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policyIsQualified %></span></p>
  </td>
  <td width="14%" valign=top style='width:14.4%;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Qualified Type</p>
  </td>
  <td width="12%" valign=top style='width:12.46%;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policyQualifiedType %></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:1'>
  <td width="20%" valign=top style='width:20.06%;border:solid #585858 1.0pt;
  border-top:none;mso-border-top-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Nursing Home Waiver</p>
  </td>
  <td width="13%" valign=top style='width:13.42%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policyNursingHomeWaiver %></span></p>
  </td>
  <td width="20%" valign=top style='width:20.22%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Has Amendment</p>
  </td>
  <td width="19%" valign=top style='width:19.42%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policyHasAmendment %></span></p>
  </td>
  <td width="14%" valign=top style='width:14.4%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><o:p>&nbsp;</o:p></p>
  </td>
  <td width="12%" valign=top style='width:12.46%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><o:p>&nbsp;</o:p></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:2;mso-yfti-lastrow:yes'>
  <td width="20%" valign=top style='width:20.06%;border:solid #585858 1.0pt;
  border-top:none;mso-border-top-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><st1:place w:st="on"><st1:PlaceName w:st="on">Issue</st1:PlaceName>
   <st1:PlaceType w:st="on">State</st1:PlaceType></st1:place></p>
  </td>
  <td width="13%" valign=top style='width:13.42%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policyIssueState %></span></p>
  </td>
  <td width="20%" valign=top style='width:20.22%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Normal Income Date</p>
  </td>
  <td width="19%" valign=top style='width:19.42%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policyNormalIncomeDate %></span></p>
  </td>
  <td width="14%" valign=top style='width:14.4%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Policy Type</p>
  </td>
  <td width="12%" valign=top style='width:12.46%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policyType %></span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal><o:p>&nbsp;</o:p></p>

<p class=StyleHeading3Left0LinespacingAtleast0pt>Allocations <span
style='font-size:11.0pt;mso-bidi-font-size:10.0pt;letter-spacing:0pt;
mso-font-kerning:0pt'>&#9674; Fixed: <span class=SpellE><%= allocationsFixed %></span>
&#9674; Averaging: <span class=SpellE><%= allocationsAveraging %></span> &#9674;
P-t-P: <span class=SpellE><%= allocationsPTP %></span> &#9674; Total: <span
class=SpellE><%= allocationsTotal %></span><o:p></o:p></span></p>

<p class=MsoNormal><span style='mso-bidi-font-size:10.0pt'><o:p>&nbsp;</o:p></span></p>

<p class=StyleHeading3Left0LinespacingAtleast0pt>Fixed Account<span
style='mso-bidi-font-size:13.0pt'><o:p></o:p></span></p>

<table class=MsoTableTheme border=1 cellspacing=0 cellpadding=0
 style='border-collapse:collapse;border:none;mso-border-alt:solid #585858 .5pt;
 mso-yfti-tbllook:480;mso-padding-alt:0in 5.4pt 0in 5.4pt;mso-border-insideh:
 .5pt solid #585858;mso-border-insidev:.5pt solid #585858'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td width=98 valign=top style='width:73.8pt;border:solid #585858 1.0pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span style='mso-bidi-font-size:10.0pt'>Allocated
  Premium + Bonus<o:p></o:p></span></p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span style='mso-bidi-font-size:10.0pt'><span
  class=SpellE><%= fixedAllocatedPremium %></span><o:p></o:p></span></p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span style='mso-bidi-font-size:10.0pt'>Guar Min <span
  class=SpellE>Int</span> Rate<o:p></o:p></span></p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span style='mso-bidi-font-size:10.0pt'><span
  class=SpellE><%= fixedGuarMinIntRate %></span><o:p></o:p></span></p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span style='mso-bidi-font-size:10.0pt'>Current <span
  class=SpellE>Int</span> Rate<o:p></o:p></span></p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span style='mso-bidi-font-size:10.0pt'><span
  class=SpellE><%= fixedCurrentIntRate %></span><o:p></o:p></span></p>
  </td>
 </tr>
</table>

<p class=StyleBodyTextLeft0><span class=Heading3Char><span style='font-size:
13.0pt'><o:p>&nbsp;</o:p></span></span></p>

<p class=StyleHeading3Left0LinespacingAtleast0pt>Index Accounts</p>

<p class=StyleBodyTextLeft0><span style='mso-bidi-font-size:10.0pt'>S&amp;P 500
Index at Issue: <span class=SpellE><%= indexAtIssue %></span><o:p></o:p></span></p>

<p class=StyleBodyTextLeft0>Index Cap Rate Guarantee Period: <span
class=SpellE><%= indexCapRateGuarantee %></span> Year(s)</p>

<p class=StyleBodyTextLeft0><span class=Heading3Char><span style='font-size:
13.0pt'><o:p>&nbsp;</o:p></span></span></p>

<p class=StyleHeading3Left0LinespacingAtleast0pt>Averaging Index Account</p>

<table class=MsoTableTheme border=1 cellspacing=0 cellpadding=0
 style='border-collapse:collapse;border:none;mso-border-alt:solid #585858 .5pt;
 mso-yfti-tbllook:480;mso-padding-alt:0in 5.4pt 0in 5.4pt;mso-border-insideh:
 .5pt solid #585858;mso-border-insidev:.5pt solid #585858'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes'>
  <td width=98 valign=top style='width:73.8pt;border:solid #585858 1.0pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Allocated Premium + Bonus</p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= averagingAllocatedPremium %></span></p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Initial Cap</p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= averagingInitialCap %></span>%</p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Minimum Cap</p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=MsoNormal style='margin-left:0in'><span class=SpellE><%= averagingMinimumCap %></span>%</p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:1;mso-yfti-lastrow:yes'>
  <td width=98 valign=top style='width:73.8pt;border:solid #585858 1.0pt;
  border-top:none;mso-border-top-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE>Partic</span> Rate</p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border-top:none;border-left:none;
  border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= averagingParticRate %></span>%</p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border-top:none;border-left:none;
  border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Index Margin</p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border-top:none;border-left:none;
  border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= averagingIndexMargin %></span>%</p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border-top:none;border-left:none;
  border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><o:p>&nbsp;</o:p></p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border-top:none;border-left:none;
  border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=MsoBodyText style='margin-left:0in'><o:p>&nbsp;</o:p></p>
  </td>
 </tr>
</table>

<p class=MsoNormal><o:p>&nbsp;</o:p></p>

<p class=StyleHeading3Left0LinespacingAtleast0pt>Point-to-Point Index Account</p>

<table class=MsoTableTheme border=1 cellspacing=0 cellpadding=0
 style='border-collapse:collapse;border:none;mso-border-alt:solid #585858 .5pt;
 mso-yfti-tbllook:480;mso-padding-alt:0in 5.4pt 0in 5.4pt;mso-border-insideh:
 .5pt solid #585858;mso-border-insidev:.5pt solid #585858'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes'>
  <td width=98 valign=top style='width:73.8pt;border:solid #585858 1.0pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Allocated Premium + Bonus</p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= ptpAllocatedPremium %></span></p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Initial Cap</p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= ptpInitialCap %></span>%</p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Minimum Cap</p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= ptpMinimumCap %></span>%</p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:1;mso-yfti-lastrow:yes'>
  <td width=98 valign=top style='width:73.8pt;border:solid #585858 1.0pt;
  border-top:none;mso-border-top-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE>Partic</span> Rate</p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border-top:none;border-left:none;
  border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= ptpParticRate %></span>%</p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border-top:none;border-left:none;
  border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Index Margin</p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border-top:none;border-left:none;
  border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= ptpIndexMargin %></span>%</p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border-top:none;border-left:none;
  border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><o:p>&nbsp;</o:p></p>
  </td>
  <td width=98 valign=top style='width:73.8pt;border-top:none;border-left:none;
  border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><o:p>&nbsp;</o:p></p>
  </td>
 </tr>
</table>

<p class=MsoNormal style='margin-left:0in'><o:p>&nbsp;</o:p></p>

</div>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">
</form>
</body>

<table>
   <tr>
      <td align="left">
        <input type="button" name="close" value="Close" onClick="closeReport()">
      </td>
    </tr>
</table>
</html>
