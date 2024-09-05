<%@ page import="edit.common.vo.*,
                 edit.common.*,
                 fission.utility.*"%>
<%
    IssueDocumentVO issueDocumentVO = (IssueDocumentVO) request.getAttribute("issueDocumentVO");

    String productName = Util.initString(issueDocumentVO.getProductStructureVO().getMarketingPackageName(), "");
    String productDescription = "Single Premium Deferred Annuity, with Market Value Adjustment";
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
    String jointName = "";
    String jointTaxID = "";
    String jointDOB = "";
    String jointSex = "";
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
       
       else if (roleType.equalsIgnoreCase("ANN"))
       {
           annuitantName = name;
           annuitantTaxID = taxId;
           annuitantDOB = dob;
           annuitantSex = gender;
       }
       else if (roleType.equalsIgnoreCase("OWN"))
       {
           primaryName = name;
           primaryTaxID = taxId;
           primaryDOB = dob;
           primarySex = gender;
           primaryStreet = address;
           primaryCityStateZip = cityStateZip;
       }
       else if (roleType.equalsIgnoreCase("SOW"))
       {
           secondaryName = name;
           secondaryTaxID = taxId;
           secondaryDOB = dob;
           secondarySex = gender;
       }
       if (roleType.equalsIgnoreCase("SAN"))
       {
           jointName = name;
           jointTaxID = taxId;
           jointDOB = dob;
           jointSex = gender;
       }
    }
    SegmentVO segmentVO = issueDocumentVO.getSegmentVO();

    String policyNumber = Util.initString(segmentVO.getContractNumber(), "");

    String date = Util.initString(segmentVO.getEffectiveDate(), "");
    String[] tokens = new java.lang.String[0];
    String policyDate = "";
    if (!date.equals(""))
    {
        policyDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(date);
    }

    date = Util.initString(segmentVO.getIssueDate(), "");
    String policyIssueDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(date);

    date = Util.initString(segmentVO.getApplicationReceivedDate(), "");
    String policyAppSignedDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(date);

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
    String policyPayment = amount.toString();
    String policyType = Util.initString(issueDocumentVO.getProductStructureVO().getBusinessContractName(), "");
    String policyIssueState = Util.initString(segmentVO.getIssueStateCT(), "");

    date = Util.initString(segmentVO.getPayoutVO(0).getPaymentStartDate(), "");
    String policyNormalIncomeDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(date);

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
    String noteTypeCT = "No";
    String policyHasEndorsement = "No";

    if (noteReminderVO != null && noteReminderVO.length > 0)
    {
        for (int i = 0; i < noteReminderVO.length; i++)
        {
            noteTypeCT = noteReminderVO[i].getNoteTypeCT();

            if (noteTypeCT.equalsIgnoreCase("Amendment"))
            {
                policyHasAmendment = "Yes";
            }
            else if (noteTypeCT.equalsIgnoreCase("Endorsement"))
            {
                policyHasEndorsement = "Yes";
            }
        }
    }

    IssueInvestmentVO[] issueInvestmentVO = issueDocumentVO.getIssueInvestmentVO();
    String policyInitialBaseRate = "";
    String policyInitialGuarPeriod = "";
    String policyGuarMinInterest = "";
    String policyBonusRate = "";
    String policySubGuarPeriod = "";

    if (issueInvestmentVO != null && issueInvestmentVO.length > 0)
    {
        BucketVO[] bucketVO = issueInvestmentVO[0].getInvestmentVO().getBucketVO();
        if (bucketVO != null && bucketVO.length > 0)
        {
            policyInitialBaseRate = Util.initString((bucketVO[0].getBucketInterestRate() + ""), "");
        }

        RatesVO ratesVO = issueInvestmentVO[0].getRatesVO();
        if (ratesVO != null)
        {
            policyInitialGuarPeriod = Util.initString((ratesVO.getInitialGuaranteePeriod() + ""), "");
            policyGuarMinInterest = Util.initString((ratesVO.getGuaranteedRate() + ""), "");
            policyBonusRate = Util.initString((ratesVO.getBonusRate() + ""), "");
            policySubGuarPeriod = Util.initString((ratesVO.getInitialGuaranteePeriod() + ""), "");
        }
    }
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
<link rel=File-List href="SPDAReport_01_files/filelist.xml">
<link rel=Edit-Time-Data href="SPDAReport_01_files/editdata.mso">

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

<!--[if !mso]>
<style>
v\:* {behavior:url(#default#VML);}
o\:* {behavior:url(#default#VML);}
w\:* {behavior:url(#default#VML);}
.shape {behavior:url(#default#VML);}
</style>
<![endif]-->
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
  <o:Revision>13</o:Revision>
  <o:TotalTime>334</o:TotalTime>
  <o:Created>2004-05-18T14:58:00Z</o:Created>
  <o:LastSaved>2004-05-18T15:48:00Z</o:LastSaved>
  <o:Pages>1</o:Pages>
  <o:Words>197</o:Words>
  <o:Characters>1123</o:Characters>
  <o:Company>SEG</o:Company>
  <o:Lines>9</o:Lines>
  <o:Paragraphs>2</o:Paragraphs>
  <o:CharactersWithSpaces>1318</o:CharactersWithSpaces>
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
span.SpellE
	{mso-style-name:"";
	mso-spl-e:yes;}
span.GramE
	{mso-style-name:"";
	mso-gram-e:yes;}
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
 <o:shapedefaults v:ext="edit" spidmax="8194">
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
.5in'>Policy Number: <span class=SpellE><%= policyNumber %></span></p>

</div>

<p class=MsoBodyText><o:p>&nbsp;</o:p></p>

<p class=StyleHeading2Left0>Product</p>

<p class=StyleBodyTextLeft0>Name: <span class=SpellE><%= productName %></span></p>

<p class=StyleBodyTextLeft0>Description: <span class=SpellE><%= productDescription %></span></p>

<p class=StyleBodyTextLeft0>Print Transaction Type: <span class=SpellE><%= productPrintTransactionType %></span></p>

<p class=StyleBodyTextLeft01><span style='mso-tab-count:2'>                </span></p>

<p class=StyleHeading2Left0>Agent</p>

<p class=StyleBodyTextLeft0><span class=SpellE><span class=GramE><%= agentName1 %></span></span></p>

<p class=StyleBodyTextLeft0><span class=SpellE><span class=GramE><%= agentStreet1 %></span></span></p>

<p class=StyleBodyTextLeft0><span class=SpellE><span class=GramE><%= agentCityStateZip1 %></span></span></p>

<p class=MsoNormal><o:p>&nbsp;</o:p></p>
<p class=StyleHeading2Left0>Agent</p>

<p class=StyleBodyTextLeft0><span class=SpellE><span class=GramE><%= agentName2 %></span></span></p>

<p class=StyleBodyTextLeft0><span class=SpellE><span class=GramE><%= agentStreet2 %></span></span></p>

<p class=StyleBodyTextLeft0><span class=SpellE><span class=GramE><%= agentCityStateZip2 %></span></span></p>

<p class=MsoNormal><o:p>&nbsp;</o:p></p>
<p class=StyleHeading2Left0>Agent</p>

<p class=StyleBodyTextLeft0><span class=SpellE><span class=GramE><%= agentName3 %></span></span></p>

<p class=StyleBodyTextLeft0><span class=SpellE><span class=GramE><%= agentStreet3 %></span></span></p>

<p class=StyleBodyTextLeft0><span class=SpellE><span class=GramE><%= agentCityStateZip3 %></span></span></p>

<p class=MsoNormal><o:p>&nbsp;</o:p></p>
<p class=StyleHeading2Left0>Agent</p>

<p class=StyleBodyTextLeft0><span class=SpellE><span class=GramE><%= agentName4 %></span></span></p>

<p class=StyleBodyTextLeft0><span class=SpellE><span class=GramE><%= agentStreet4 %></span></span></p>

<p class=StyleBodyTextLeft0><span class=SpellE><span class=GramE><%= agentCityStateZip4 %></span></span></p>

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
  <p class=StyleBodyTextLeft0>Sex: <a name="OLE_LINK2"></a><a name="OLE_LINK1"><span
  style='mso-bookmark:OLE_LINK2'></span></a><span class=SpellE><span
  style='mso-bookmark:OLE_LINK1'><span style='mso-bookmark:OLE_LINK2'><%= secondarySex %></span></span></span><span
  style='mso-bookmark:OLE_LINK1'><span style='mso-bookmark:OLE_LINK2'></span></span></p>
  </td>
 </tr>
</table>

<p class=MsoNormal style='margin-left:0in'><span style='font-family:"Times New Roman";
color:windowtext'><o:p>&nbsp;</o:p></span></p>

<div class=MsoNormal align=center style='margin-left:0in;text-align:center'><span
style='font-family:"Times New Roman";color:windowtext'>

<hr size=2 width="100%" align=center>

</span></div>

<p class=StyleHeading2Left0><o:p>&nbsp;</o:p></p>

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
  <p class=StyleBodyTextLeft0>Payment</p>
  </td>
  <td width="16%" valign=top style='width:16.66%;border:solid #585858 1.0pt;
  border-left:none;mso-border-left-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policyPayment %></span></p>
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
 </tr>
 <tr style='mso-yfti-irow:1;mso-yfti-lastrow:yes'>
  <td width="16%" valign=top style='width:16.66%;border:solid #585858 1.0pt;
  border-top:none;mso-border-top-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Initial Guar Period</p>
  </td>
  <td width="16%" valign=top style='width:16.66%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policyInitialGuarPeriod %></span></p>
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
  <td width="16%" valign=top style='width:16.66%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>App Date</p>
  </td>
  <td width="16%" valign=top style='width:16.66%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policyAppSignedDate %></span></p>
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
  <p class=StyleBodyTextLeft0>Has Endorsement</p>
  </td>
  <td width="12%" valign=top style='width:12.46%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><%= policyHasEndorsement %></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:2'>
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
 <tr style='mso-yfti-irow:3'>
  <td width="20%" valign=top style='width:20.06%;border:solid #585858 1.0pt;
  border-top:none;mso-border-top-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Guar Min Interest</p>
  </td>
  <td width="13%" valign=top style='width:13.42%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policyGuarMinInterest %></span></p>
  </td>
  <td width="20%" valign=top style='width:20.22%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Initial Base Rate</p>
  </td>
  <td width="19%" valign=top style='width:19.42%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policyInitialBaseRate %></span></p>
  </td>
  <td width="14%" valign=top style='width:14.4%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Bonus Rate</p>
  </td>
  <td width="12%" valign=top style='width:12.46%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policyBonusRate %></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:4;mso-yfti-lastrow:yes'>
  <td width="20%" valign=top style='width:20.06%;border:solid #585858 1.0pt;
  border-top:none;mso-border-top-alt:solid #585858 .5pt;mso-border-alt:solid #585858 .5pt;
  padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0>Sub Guar Period</p>
  </td>
  <td width="13%" valign=top style='width:13.42%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><span class=SpellE><%= policySubGuarPeriod %></span></p>
  </td>
  <td width="20%" valign=top style='width:20.22%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><o:p>&nbsp;</o:p></p>
  </td>
  <td width="19%" valign=top style='width:19.42%;border-top:none;border-left:
  none;border-bottom:solid #585858 1.0pt;border-right:solid #585858 1.0pt;
  mso-border-top-alt:solid #585858 .5pt;mso-border-left-alt:solid #585858 .5pt;
  mso-border-alt:solid #585858 .5pt;padding:0in 5.4pt 0in 5.4pt'>
  <p class=StyleBodyTextLeft0><o:p>&nbsp;</o:p></p>
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
</table>

<p class=MsoNormal><o:p>&nbsp;</o:p></p>



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
