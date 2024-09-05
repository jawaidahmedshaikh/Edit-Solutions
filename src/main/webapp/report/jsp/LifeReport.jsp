<%@ page import="edit.common.vo.*,
                 java.util.ArrayList,
                 java.util.List,
                 engine.FilteredFund,
                 engine.Fund,
                 engine.Area,
                 engine.AreaValue,
                 reinsurance.Reinsurer,
                 client.ClientDetail,
                 edit.common.*,
                 fission.utility.*"%>
<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    IssueDocumentVO issueDocumentVO = (IssueDocumentVO) request.getAttribute("issueDocumentVO");

    ReinsuranceVO[] reinsuranceVOs = (ReinsuranceVO[]) issueDocumentVO.getReinsuranceVO();

    String productName = Util.initString(issueDocumentVO.getProductStructureVO().getBusinessContractName(), "");
    String productDescription = Util.initString(issueDocumentVO.getProductStructureVO().getBusinessContractName(), "");

    IssueClientVO[] issueClientVOs = issueDocumentVO.getIssueClientVO();

    SegmentVO segmentVO = issueDocumentVO.getSegmentVO();
    LifeVO lifeVO = segmentVO.getLifeVO(0);

    String policyNumber = Util.initString(segmentVO.getContractNumber(), "");

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

    String agentType1 = "";
    String agentName1 = "";
    String agentStreet1 = "";
    String agentCityStateZip1 = "";
    EDITBigDecimal agentAllocation1 = new EDITBigDecimal();
    String agentType2 = "";
    String agentName2 = "";
    String agentStreet2 = "";
    String agentCityStateZip2 = "";
    EDITBigDecimal agentAllocation2 = new EDITBigDecimal();
    String agentType3 = "";
    String agentName3 = "";
    String agentStreet3 = "";
    String agentCityStateZip3 = "";
    EDITBigDecimal agentAllocation3 = new EDITBigDecimal();
    String agentType4 = "";
    String agentName4 = "";
    String agentStreet4 = "";
    String agentCityStateZip4 = "";
    EDITBigDecimal agentAllocation4 = new EDITBigDecimal();
    String insuredName = "";
    String insuredTaxID = "";
    String insuredDOB = "";
    String insuredSex = "";
    int insuredAge = 0;
    String primaryName = "";
    String primaryTaxID = "";
    String primaryStreet = "";
    String primaryCityStateZip = "";
    String secondaryName = "";
    String secondaryTaxID = "";
    String secondaryStreet = "";
    String secondaryCityStateZip = "";
    String address = "";
    String cityStateZip = "";
    String residenceState = "";
    String ratingClass = "";
    EDITBigDecimal flatExtra = new EDITBigDecimal();
    int flatExtraDur = 0;
    EDITBigDecimal pctExtra = new EDITBigDecimal();
    int pctExtraDur = 0;
    String smokingClass = "";
    String mecStatus = Util.initString(codeTableWrapper.getCodeDescByCodeTableNameAndCode("MECSTATUS", Util.initString(lifeVO.getMECStatusCT(), "")), "NonMEC");
    String option7702 = Util.initString(codeTableWrapper.getCodeDescByCodeTableNameAndCode("OPTION7702", Util.initString(lifeVO.getOption7702CT(), "")), "");
    String dbOption = Util.initString(codeTableWrapper.getCodeDescByCodeTableNameAndCode("DEATHBENOPT", Util.initString(lifeVO.getDeathBenefitOptionCT(), "")), "");
    EDITBigDecimal faceAmount = new EDITBigDecimal(lifeVO.getFaceAmount());
    EDITBigDecimal guidelineLevelPrem = new EDITBigDecimal(lifeVO.getGuidelineLevelPremium());
    EDITBigDecimal guidelineSinglePrem = new EDITBigDecimal(lifeVO.getGuidelineSinglePremium());
    EDITBigDecimal sevenPayPrem = new EDITBigDecimal(lifeVO.getTamra());
    EDITBigDecimal sevenPayRate = new EDITBigDecimal(issueDocumentVO.getSevenPayRate());
    List primaryBeneficiaries = new ArrayList();
    List contingentBeneficiaries = new ArrayList();
    boolean agent1Filled = false;
    boolean agent2Filled = false;
    boolean agent3Filled = false;
    boolean agent4Filled = false;

    for (int i = 0; i < issueClientVOs.length; i++)
    {
        String roleType = issueClientVOs[i].getRoleTypeCT();
        String agentType = issueClientVOs[i].getAgentTypeCT();
        EDITBigDecimal agentAllocation = new EDITBigDecimal(issueClientVOs[i].getAgentAllocation());
        ClientDetailVO clientDetailVO = issueClientVOs[i].getClientDetailVO();
        ClientAddressVO[] clientAddresses = clientDetailVO.getClientAddressVO();
        if (roleType.equalsIgnoreCase("Insured"))
        {
            ContractClientVO contractClientVO = issueClientVOs[i].getContractClientVO();
            smokingClass = Util.initString(codeTableWrapper.getCodeDescByCodeTableNameAndCode("CLASS", Util.initString(contractClientVO.getClassCT(), "")), "");
            ratingClass = Util.initString(codeTableWrapper.getCodeDescByCodeTableNameAndCode("UNDERWRITINGCLASS", Util.initString(contractClientVO.getUnderwritingClassCT(), "")), "");
            flatExtra = new EDITBigDecimal(contractClientVO.getFlatExtra());
            flatExtraDur = contractClientVO.getFlatExtraDur();
            pctExtra = new EDITBigDecimal(contractClientVO.getPercentExtra());
            pctExtraDur = contractClientVO.getPercentExtraDur();
        }

        if (clientAddresses != null && clientAddresses.length > 0)
        {
            ClientAddressVO clientAddressVO = clientDetailVO.getClientAddressVO(0);
            address = Util.initString(clientAddressVO.getAddressLine1(), "");
            cityStateZip = Util.initString(clientAddressVO.getCity() + "," + clientAddressVO.getStateCT() + " " + clientAddressVO.getZipCode(), "");
            if (roleType.equalsIgnoreCase("Insured"))
            {
                residenceState = clientAddressVO.getStateCT();
            }
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
               agentType1 = codeTableWrapper.getCodeDescByCodeTableNameAndCode("AGENTTYPE", agentType);
               agentName1 =  name;
               agentStreet1 = address;
               agentCityStateZip1 = cityStateZip;
               agentAllocation1 = agentAllocation.multiplyEditBigDecimal("100");
               agent1Filled = true;
           }
           else if (!agent2Filled)
           {
               agentType2 = codeTableWrapper.getCodeDescByCodeTableNameAndCode("AGENTTYPE", agentType);
               agentName2 =  name;
               agentStreet2 = address;
               agentCityStateZip2 = cityStateZip;
               agentAllocation2 = agentAllocation.multiplyEditBigDecimal("100");
               agent2Filled = true;
           }
           else if (!agent3Filled)
           {
               agentType3 = codeTableWrapper.getCodeDescByCodeTableNameAndCode("AGENTTYPE", agentType);
               agentName3 =  name;
               agentStreet3 = address;
               agentCityStateZip3 = cityStateZip;
               agentAllocation3 = agentAllocation.multiplyEditBigDecimal("100");
               agent3Filled = true;
           }
           else if (!agent4Filled)
           {
               agentType4 = codeTableWrapper.getCodeDescByCodeTableNameAndCode("AGENTTYPE", agentType);
               agentName4 =  name;
               agentStreet4 = address;
               agentCityStateZip4 = cityStateZip;
               agentAllocation4 = agentAllocation.multiplyEditBigDecimal("100");
               agent4Filled = true;
           }
        }
        
        //Insured
        if (roleType.equalsIgnoreCase("Insured"))
        {
            insuredName = name;
            insuredTaxID = taxId;
            insuredDOB = dob;
            insuredSex = gender;
            insuredAge = getInsuredAge(Util.initString(segmentVO.getEffectiveDate(), ""),
                                       Util.initString(segmentVO.getIssueDate(), ""),
                                       clientDetailVO.getBirthDate());
        }
        //Primary Owner
        if (roleType.equalsIgnoreCase("OWN"))
        {
            primaryName = name;
            primaryTaxID = taxId;
            primaryStreet = address;
            primaryCityStateZip = cityStateZip;
        }
        //Secondary Owner
        if (roleType.equalsIgnoreCase("SOW"))
        {
            secondaryName = name;
            secondaryTaxID = taxId;
            secondaryStreet = address;
            secondaryCityStateZip = cityStateZip;
        }
        //Primary Beneficiary
        if (roleType.equalsIgnoreCase("PBE"))
        {
            primaryBeneficiaries.add(issueClientVOs[i]);
        }
        //Contingent Beneficiary
        if (roleType.equalsIgnoreCase("CBE"))
        {
            contingentBeneficiaries.add(issueClientVOs[i]);
        }
    }

    DepositsVO[] depositsVOs = segmentVO.getDepositsVO();

    String exchangeCoName = "";
    String exchangePolicyYearDate = "";
    EDITBigDecimal exchangeCostBasis = new EDITBigDecimal();
    EDITBigDecimal cashValueReceived = new EDITBigDecimal();
    String exchangeMecStatus = "";
    String exchangePolicyNumber = "";
    boolean exchangeDepositFound = false;
    if (depositsVOs != null && depositsVOs.length > 0)
    {
        for (int i = 0; i < depositsVOs.length; i++)
        {
            if ((Util.initString(depositsVOs[i].getDepositTypeCT(), "")).startsWith("1035"))
            {
                exchangeDepositFound = true;
                exchangeCoName = Util.initString(depositsVOs[i].getOldCompany(), " ");
                exchangePolicyYearDate = Util.initString(depositsVOs[i].getExchangePolicyEffectiveDate(), " ");
                exchangeCostBasis = new EDITBigDecimal(depositsVOs[i].getCostBasis());
                exchangePolicyNumber = Util.initString(depositsVOs[i].getOldPolicyNumber(), " ");
                cashValueReceived = new EDITBigDecimal(depositsVOs[i].getAmountReceived());
                exchangeMecStatus = Util.initString(depositsVOs[i].getPriorCompanyMECStatusCT(), "NonMEC");
            }
        }
    }

    String policyIssueState = Util.initString(segmentVO.getIssueStateCT(), "");

    date = Util.initString(segmentVO.getTerminationDate(), "");
    String maturityDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(date);

    IssueInvestmentVO[] issueInvestmentVO = issueDocumentVO.getIssueInvestmentVO();

    String chargeDeductFund = getChargeDeductFund(segmentVO);
%>

<%!
    private int getInsuredAge(String policyDate, String policyIssueDate, String dateOfBirth)
    {
        int issueAge = 0;
        if ((EDITDate.isACandidateDate(dateOfBirth)))
        {
            EDITDate edPolicyDate = null;
            if (!policyDate.equals(""))
            {
                edPolicyDate = new EDITDate(policyDate);
            }
            else
            {
                edPolicyDate = new EDITDate(policyIssueDate);
            }

            EDITDate edDOB = new EDITDate(dateOfBirth);

            issueAge = edPolicyDate.getAgeAtNearestBirthday(edDOB);
        }

        return issueAge;
    }
%>

<%!
    /**
     * Retrieves the charge deduct fund from the product table
     * @param segmentVO
     * @return
     * @throws Exception
     */
    private String getChargeDeductFund(SegmentVO segmentVO) throws Exception
    {
        long companyStructurePK = segmentVO.getProductStructureFK();
        String areaCT = segmentVO.getIssueStateCT();
        String qualifierCT = Util.initString(segmentVO.getQualNonQualCT(), "*");
        String grouping = "TRANSACTION";

        EDITDate effectiveDate = new EDITDate(segmentVO.getEffectiveDate());
        String field = "CHARGEDEDUCTFUND";

        Area area = new Area(companyStructurePK, areaCT, grouping, effectiveDate, qualifierCT);

        AreaValue areaValue = area.getAreaValue(field);

        AreaValueVO areaValueVO = (AreaValueVO) areaValue.getVO();

        String fundNumber = areaValueVO.getAreaValue();

        return fundNumber;
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
<title>New Business Issue Report</title>
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

<p class=DocumentLabel style='margin-left:0in'><span class=SpellE>New Business Issue Report</span></p>

<div style='mso-element:para-border-div;border:none;border-bottom:solid windowtext 1.0pt;
mso-border-bottom-alt:solid windowtext .75pt;padding:0in 0in 5.0pt 0in'>

<p class=StyleMessageHeaderLastBottomSinglesolidlineAuto075 style='margin-left:
.5in'>Policy Number <%= policyNumber %></p>

<p class=StyleBodyTextLeft01><span style='mso-tab-count:2'>&nbsp;</span></p>

<p class=StyleHeading2Left0>Product</p>

<p class=StyleBodyTextLeft0>Name: <span class=SpellE><%= productName %></span></p>

<p class=StyleBodyTextLeft0>Description: <span class=SpellE><%= productDescription %></span></p>

</div>

<p class=StyleBodyTextLeft01><span style='mso-tab-count:2'>&nbsp;</span></p>

<div style='mso-element:para-border-div;border:none;border-bottom:solid windowtext 1.0pt;
mso-border-bottom-alt:solid windowtext .75pt;padding:0in 0in 5.0pt 0in'>

<table class=MsoTableTheme border=0 cellspacing=0 cellpadding=0 width="100%"
 style='width:100.0%;border-collapse:collapse;mso-yfti-tbllook:480;mso-padding-alt:
 0in 5.4pt 0in 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <%
      if (!agentName1.equals(""))
      {
  %>
  <td width="100%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
    <p class=StyleHeading2Left0><%= agentType1 %></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentName1 %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentStreet1 %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentCityStateZip1 %></span></p>
    <p class=StyleBodyTextLeft0>% Allocation: <span class=SpellE><%= agentAllocation1.toString() %></span>%</p>
    <p class=StyleBodyTextLeft0><o:p>&nbsp;</o:p></p>
  </td>
  <%
      }

      if (!agentName2.equals(""))
      {
  %>
  <td width="100%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
    <p class=StyleHeading2Left0><%= agentType2 %></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentName2 %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentStreet2 %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentCityStateZip2 %></span></p>
    <p class=StyleBodyTextLeft0>% Allocation: <span class=SpellE><%= agentAllocation2.toString() %></span>%</p>
    <p class=StyleBodyTextLeft0><o:p>&nbsp;</o:p></p>
  </td>
  <%
      }
  %>
 </tr>
</table>

<table class=MsoTableTheme border=0 cellspacing=0 cellpadding=0 width="100%"
 style='width:100.0%;border-collapse:collapse;mso-yfti-tbllook:480;mso-padding-alt:
 0in 5.4pt 0in 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <%
      if (!agentName3.equals(""))
      {
  %>
  <td width="100%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
    <p class=StyleHeading2Left0><%= agentType3 %></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentName3 %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentStreet3 %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentCityStateZip3 %></span></p>
    <p class=StyleBodyTextLeft0>% Allocation: <span class=SpellE><%= agentAllocation3.toString() %></span>%</p>
    <p class=StyleBodyTextLeft0><o:p>&nbsp;</o:p></p>
  </td>
  <%
      }

      if (!agentName4.equals(""))
      {
  %>
  <td width="100%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
    <p class=StyleHeading2Left0><%= agentType4 %></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentName4 %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentStreet4 %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= agentCityStateZip4 %></span></p>
    <p class=StyleBodyTextLeft0>% Allocation: <span class=SpellE><%= agentAllocation4.toString() %></span>%</p>
    <p class=StyleBodyTextLeft0><o:p>&nbsp;</o:p></p>
  </td>
  <%
      }
  %>
 </tr>
</table>

<p class=MsoNormal><o:p>&nbsp;</o:p></p>

<table class=MsoTableTheme border=0 cellspacing=0 cellpadding=0 width="100%"
 style='width:100.0%;border-collapse:collapse;mso-yfti-tbllook:480;mso-padding-alt:
 0in 5.4pt 0in 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td width="25%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
    <p class=StyleHeading2Left0>Insured/Annuitant</p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= insuredName %></span></p>
    <p class=StyleBodyTextLeft0>Tax ID: <span class=SpellE><%= insuredTaxID %></span></p>
    <p class=StyleBodyTextLeft0>DOB: <span class=SpellE><%= insuredDOB %></span></p>
    <p class=StyleBodyTextLeft0>Gender: <span class=SpellE><%= insuredSex %></span></p>
    <p class=StyleBodyTextLeft0>Age: <span class=SpellE><%= insuredAge %></span></p>
  </td>
 </tr>
</table>

<p class=StyleBodyTextLeft0><span style='mso-tab-count:1'>          </span></p>

<table class=MsoTableTheme border=0 cellspacing=0 cellpadding=0 width="100%"
 style='width:100.0%;border-collapse:collapse;mso-yfti-tbllook:480;mso-padding-alt:
 0in 5.4pt 0in 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td width="25%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
    <p class=StyleHeading2Left0>Primary Owner</p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= primaryName %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= primaryStreet %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= primaryCityStateZip %></span></p>
    <p class=StyleBodyTextLeft0>Tax ID: <span class=SpellE><%= primaryTaxID %></span></p>
  </td>
  <%
      if (!secondaryName.equals(""))
      {
  %>
  <td width="25%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
    <p class=StyleHeading2Left0>Secondary Owner</p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= secondaryName %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= secondaryStreet %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= secondaryCityStateZip %></span></p>
    <p class=StyleBodyTextLeft0>Tax ID: <span class=SpellE><%= secondaryTaxID %></span></p>
  </td>
 <%
      }
 %>
 </tr>
</table>

 <p class=StyleBodyTextLeft0><span style='mso-tab-count:1'>          </span></p>

 <%
     if (primaryBeneficiaries.size() > 0)
     {
        for (int i = 0; i < primaryBeneficiaries.size(); i++)
        {
            IssueClientVO issueClientVO = (IssueClientVO) primaryBeneficiaries.get(i);
            ContractClientVO contractClientVO = issueClientVO.getContractClientVO();
            ClientDetailVO clientDetailVO = issueClientVO.getClientDetailVO();
            String pBeneAddress = "";
            String pBeneCityStateZip = "";
            if (clientDetailVO.getClientAddressVOCount() > 0)
            {
                ClientAddressVO clientAddressVO = clientDetailVO.getClientAddressVO(0);
                pBeneAddress = Util.initString(clientAddressVO.getAddressLine1(), "");
                pBeneCityStateZip = Util.initString(clientAddressVO.getCity() + "," + clientAddressVO.getStateCT() + " " + clientAddressVO.getZipCode(), "");
            }

            String pBeneName = Util.initString(clientDetailVO.getFirstName() + " " + clientDetailVO.getLastName(), "");
            if (clientDetailVO.getLastName() == null || clientDetailVO.getLastName().equals(""))
            {
               pBeneName = Util.initString(clientDetailVO.getCorporateName(), "");
            }

            String pBeneTaxId = Util.initString(clientDetailVO.getTaxIdentification(), "");
            EDITBigDecimal pBeneAlloc = new EDITBigDecimal();
            if (contractClientVO.getContractClientAllocationVOCount() > 0)
            {
                ContractClientAllocationVO[] contractClientAllocationVOs = contractClientVO.getContractClientAllocationVO();
                for (int j = 0; j < contractClientAllocationVOs.length; j++)
                {
                    if (contractClientAllocationVOs[j].getOverrideStatus().equalsIgnoreCase("P"))
                    {
                        pBeneAlloc = new EDITBigDecimal(contractClientAllocationVOs[j].getAllocationPercent());
                        pBeneAlloc = pBeneAlloc.multiplyEditBigDecimal("100");
                    }
                }
            }

 %>
<p class=StyleBodyTextLeft0><span style='mso-tab-count:1'>          </span></p>

<table class=MsoTableTheme border=0 cellspacing=0 cellpadding=0 width="100%"
 style='width:100.0%;border-collapse:collapse;mso-yfti-tbllook:480;mso-padding-alt:
 0in 5.4pt 0in 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td width="25%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
    <p class=StyleHeading2Left0>Primary Beneficiary</p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= pBeneName %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= pBeneAddress %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= pBeneCityStateZip %></span></p>
    <p class=StyleBodyTextLeft0>Tax ID: <span class=SpellE><%= pBeneTaxId %></span></p>
    <p class=StyleBodyTextLeft0>% Allocation: <span class=SpellE><%= pBeneAlloc.toString() %></span>%</p>
  </td>
  <%
            if (contingentBeneficiaries.size() > i + 1 ||
                contingentBeneficiaries.size() == i + 1)
            {
                IssueClientVO cbeIssueClientVO = (IssueClientVO) contingentBeneficiaries.get(i);
                ContractClientVO cbeContractClientVO = cbeIssueClientVO.getContractClientVO();
                ClientDetailVO cbeClientDetailVO = cbeIssueClientVO.getClientDetailVO();
                String cBeneAddress = "";
                String cBeneCityStateZip = "";
                if (cbeClientDetailVO.getClientAddressVOCount() > 0)
                {
                    ClientAddressVO clientAddressVO = cbeClientDetailVO.getClientAddressVO(0);
                    cBeneAddress = Util.initString(clientAddressVO.getAddressLine1(), "");
                    cBeneCityStateZip = Util.initString(clientAddressVO.getCity() + "," + clientAddressVO.getStateCT() + " " + clientAddressVO.getZipCode(), "");
                }

                String cBeneName = Util.initString(cbeClientDetailVO.getFirstName() + " " + cbeClientDetailVO.getLastName(), "");
                if (cbeClientDetailVO.getLastName() == null || cbeClientDetailVO.getLastName().equals(""))
                {
                   cBeneName = Util.initString(cbeClientDetailVO.getCorporateName(), "");
                }

                String cBeneTaxId = Util.initString(cbeClientDetailVO.getTaxIdentification(), "");
                EDITBigDecimal cBeneAlloc = new EDITBigDecimal();
                if (cbeContractClientVO.getContractClientAllocationVOCount() > 0)
                {
                    ContractClientAllocationVO[] contractClientAllocationVOs = cbeContractClientVO.getContractClientAllocationVO();
                    for (int j = 0; j < contractClientAllocationVOs.length; j++)
                    {
                        if (contractClientAllocationVOs[j].getOverrideStatus().equalsIgnoreCase("P"))
                        {
                            cBeneAlloc = new EDITBigDecimal(contractClientAllocationVOs[j].getAllocationPercent());
                            cBeneAlloc = cBeneAlloc.multiplyEditBigDecimal("100");
                        }
                    }
                }
  %>
  <td width="25%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
    <p class=StyleHeading2Left0>Contingent Beneficiary</p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= cBeneName %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= cBeneAddress %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= cBeneCityStateZip %></span></p>
    <p class=StyleBodyTextLeft0>Tax ID: <span class=SpellE><%= cBeneTaxId %></span></p>
    <p class=StyleBodyTextLeft0>% Allocation: <span class=SpellE><%= cBeneAlloc.toString() %></span>%</p>
  </td>
 <%
            }
        }
     }
 %>
 </tr>

<%
     if (contingentBeneficiaries.size() > primaryBeneficiaries.size())
     {
        for (int i = primaryBeneficiaries.size(); i < contingentBeneficiaries.size(); i++)
        {
 %>
<p class=StyleBodyTextLeft0><span style='mso-tab-count:1'>          </span></p>

<table class=MsoTableTheme border=0 cellspacing=0 cellpadding=0 width="100%"
 style='width:100.0%;border-collapse:collapse;mso-yfti-tbllook:480;mso-padding-alt:
 0in 5.4pt 0in 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td width="25%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
    <p class=StyleHeading2Left0>&nbsp;</p>
    <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
  </td>
  <%
            IssueClientVO cbeIssueClientVO = (IssueClientVO) contingentBeneficiaries.get(i);
            ContractClientVO cbeContractClientVO = cbeIssueClientVO.getContractClientVO();
            ClientDetailVO cbeClientDetailVO = cbeIssueClientVO.getClientDetailVO();
            String cBeneAddress = "";
            String cBeneCityStateZip = "";
            if (cbeClientDetailVO.getClientAddressVOCount() > 0)
            {
                ClientAddressVO clientAddressVO = cbeClientDetailVO.getClientAddressVO(0);
                cBeneAddress = Util.initString(clientAddressVO.getAddressLine1(), "");
                cBeneCityStateZip = Util.initString(clientAddressVO.getCity() + "," + clientAddressVO.getStateCT() + " " + clientAddressVO.getZipCode(), "");
            }

            String cBeneName = Util.initString(cbeClientDetailVO.getFirstName() + " " + cbeClientDetailVO.getLastName(), "");
            if (cbeClientDetailVO.getLastName() == null || cbeClientDetailVO.getLastName().equals(""))
            {
               cBeneName = Util.initString(cbeClientDetailVO.getCorporateName(), "");
            }

            String cBeneTaxId = Util.initString(cbeClientDetailVO.getTaxIdentification(), "");
            EDITBigDecimal cBeneAlloc = new EDITBigDecimal();
            if (cbeContractClientVO.getContractClientAllocationVOCount() > 0)
            {
                ContractClientAllocationVO[] contractClientAllocationVOs = cbeContractClientVO.getContractClientAllocationVO();
                for (int j = 0; j < contractClientAllocationVOs.length; j++)
                {
                    if (contractClientAllocationVOs[j].getOverrideStatus().equalsIgnoreCase("P"))
                    {
                        cBeneAlloc = new EDITBigDecimal(contractClientAllocationVOs[j].getAllocationPercent());
                        cBeneAlloc = cBeneAlloc.multiplyEditBigDecimal("100");
                    }
                }
            }
  %>
  <td width="25%" valign=top style='width:25.0%;padding:0in 5.4pt 0in 5.4pt'>
    <p class=StyleHeading2Left0>Contingent Beneficiary</p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= cBeneName %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= cBeneAddress %></span></p>
    <p class=StyleBodyTextLeft0><span class=SpellE><%= cBeneCityStateZip %></span></p>
    <p class=StyleBodyTextLeft0>Tax ID: <span class=SpellE><%= cBeneTaxId %></span></p>
    <p class=StyleBodyTextLeft0>% Allocation: <span class=SpellE><%= cBeneAlloc.toString() %></span>%</p>
  </td>
 <%
        }
     }
 %>
 </tr>
</table>

<p class=ReturnAddress><o:p>&nbsp;</o:p></p>

<table class=MsoTableTheme border=0 cellspacing=0 cellpadding=0 width="100%"
 style='width:100.0%;border-collapse:collapse;mso-yfti-tbllook:480;mso-padding-alt:
 0in 5.4pt 0in 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
 </tr>
</table>

</div>

<p class=StyleBodyTextLeft01><span style='mso-tab-count:2'>&nbsp;</span></p>

<div style='mso-element:para-border-div;border:none;border-bottom:solid windowtext 1.0pt;
mso-border-bottom-alt:solid windowtext .75pt;padding:0in 0in 5.0pt 0in'>

<p class=StyleHeading2Left0>&nbsp;Policy Details</p>

<table class=MsoTableTheme border=0 cellspacing=0 cellpadding=0 width="100%"
 style='width:95.0%;border-collapse:collapse;mso-yfti-tbllook:480;mso-padding-alt:
 0in 5.4pt 0in 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td valign=top style='width:5%;padding:0in 5.4pt 0in 5.4pt'>
   <p class=StyleBodyTextLeft0>Policy Date:</p>
   <p class=StyleBodyTextLeft0>Issue Date:</p>
   <p class=StyleBodyTextLeft0>App Date:</p>
   <p class=StyleBodyTextLeft0>Maturity Date:</p>
   <p class=StyleBodyTextLeft0>Iss State:</p>
   <p class=StyleBodyTextLeft0>Res State:</p>
   <p class=StyleBodyTextLeft0>Rating Class:</p>
   <p class=StyleBodyTextLeft0>Flat Extra Prem:</p>
   <p class=StyleBodyTextLeft0>Flat Extra # Yrs:</p>
   <p class=StyleBodyTextLeft0>Rating %:</p>
   <p class=StyleBodyTextLeft0>Rating % # Yrs:</p>
  </td>
  <td valign=top style='width:6%;padding:0in 5.4pt 0in 5.4pt'>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= policyDate %></span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= policyIssueDate %></span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= policyAppSignedDate %></span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= maturityDate %></span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= policyIssueState %></span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= residenceState %></span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= ratingClass %></span></p>
   <p class=StyleBodyTextLeft0>$<span class=SpellE><script>document.write(formatAsCurrency(<%= flatExtra %>))</script></span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= flatExtraDur %></span></p>
   <p class=StyleBodyTextLeft0>$<span class=SpellE><script>document.write(formatAsCurrency(<%= pctExtra %>))</script></span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= pctExtraDur %></span></p>
  </td>
  <td valign=top style='width:10%;padding:0in 5.4pt 0in 5.4pt'>
   <p class=StyleBodyTextLeft0>Smoking Class:</p>
   <p class=StyleBodyTextLeft0>MEC Status:</p>
   <p class=StyleBodyTextLeft0>GPT/CVAT:</p>
   <p class=StyleBodyTextLeft0>DB Option:</p>
   <p class=StyleBodyTextLeft0>Frgn Tvl Ex Rdr:</p>
   <p class=StyleBodyTextLeft0>Mat. Ext. Rdr:</p>
   <p class=StyleBodyTextLeft0>Face Amt:</p>
   <p class=StyleBodyTextLeft0>Gdln Lvl Prem:</p>
   <p class=StyleBodyTextLeft0>Gdln Sgl Prem:</p>
   <p class=StyleBodyTextLeft0>7 Pay Prem:</p>
   <p class=StyleBodyTextLeft0>7 Pay Rate:</p>
  </td>
  <td valign=top style='width:8%;padding:0in 5.4pt 0in 5.4pt'>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= smokingClass %></span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= mecStatus %></span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= option7702 %></span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= dbOption %></span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>Yes</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>Yes</span></p>
   <p class=StyleBodyTextLeft0>$<span class=SpellE><script>document.write(formatAsCurrency(<%= faceAmount %>))</script></span></p>
   <p class=StyleBodyTextLeft0>$<span class=SpellE><script>document.write(formatAsCurrency(<%= guidelineLevelPrem %>))</script></span></p>
   <p class=StyleBodyTextLeft0>$<span class=SpellE><script>document.write(formatAsCurrency(<%= guidelineSinglePrem %>))</script></span></p>
   <p class=StyleBodyTextLeft0>$<span class=SpellE><script>document.write(formatAsCurrency(<%= sevenPayPrem %>))</script></span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= sevenPayRate %></span></p>
  </td>
 </tr>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td width="8%" valign=top style='width:8%;padding:0in 5.4pt 0in 5.4pt'>&nbsp;</td>
 </tr>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td width="8%" valign=top style='width:8%;padding:0in 5.4pt 0in 5.4pt'>&nbsp;</td>
 </tr>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td valign=top style='width:12%;padding:0in 5.4pt 0in 5.4pt'>
   <p class=StyleBodyTextLeft0><u>Reinsurer Info</u></p>
   <p class=StyleBodyTextLeft0>Name:</p>
   <p class=StyleBodyTextLeft0>Percent:</p>
   <p class=StyleBodyTextLeft0>Treaty Number:</p>
   <p class=StyleBodyTextLeft0>&nbsp;</p>
   <p class=StyleBodyTextLeft0>&nbsp;</p>
   <p class=StyleBodyTextLeft0>&nbsp;</p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
  </td>
  <%
      if (reinsuranceVOs != null && reinsuranceVOs.length > 0)
      {
          try
          {
              TreatyVO treatyVO = reinsuranceVOs[0].getTreatyVO(0);
              Reinsurer reinsurer = Reinsurer.findBy_ReinsurerNumber(reinsuranceVOs[0].getReinsurerNumber());
              ClientDetail clientDetail = ClientDetail.findBy_ReinsurerPK(reinsurer.getPK());
              String reinsurerName = clientDetail.getCorporateName();
              EDITBigDecimal reinsurerPct = new EDITBigDecimal(treatyVO.getPoolPercentage()).multiplyEditBigDecimal("100");
              String treatyNumber = reinsuranceVOs[0].getTreatyGroupNumber();
  %>
  <td valign=top style='width:10%;padding:0in 5.4pt 0in 5.4pt'>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= reinsurerName %></span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= reinsurerPct.toString() %></span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= treatyNumber %></span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
  </td>
  <%
          }
          catch(Exception e)
          {
              System.out.println(e.getMessage());
          }
      }
      else
      {
  %>
  <td valign=top style='width:10%;padding:0in 5.4pt 0in 5.4pt'>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
  </td>
  <%
      }
  %>
  <td valign=top style='width:10%;padding:0in 5.4pt 0in 5.4pt'>
   <p class=StyleBodyTextLeft0><u>1035 Data</u></p>
   <p class=StyleBodyTextLeft0>Co Name:</p>
   <p class=StyleBodyTextLeft0>Pol Yr Date:</p>
   <p class=StyleBodyTextLeft0>Cost Basis:</p>
   <p class=StyleBodyTextLeft0>Cash Val Rcvd:</p>
   <p class=StyleBodyTextLeft0>MEC Status:</p>
   <p class=StyleBodyTextLeft0>Orig. Pol Nbr:</p>
   <p class=StyleBodyTextLeft0>Loan:</p>
  </td>
  <%
      if (exchangeDepositFound)
      {
  %>
  <td valign=top style='width:10%;padding:0in 5.4pt 0in 5.4pt'>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= exchangeCoName %></span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= exchangePolicyYearDate %></span></p>
   <p class=StyleBodyTextLeft0>$<span class=SpellE><script>document.write(formatAsCurrency(<%= exchangeCostBasis %>))</script></span></p>
   <p class=StyleBodyTextLeft0>$<span class=SpellE><script>document.write(formatAsCurrency(<%= cashValueReceived %>))</script></span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= exchangeMecStatus %></span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= exchangePolicyNumber %></span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
  </td>
  <%
      }
      else
      {
  %>
  <td valign=top style='width:10%;padding:0in 5.4pt 0in 5.4pt'>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
  </td>
  <%
      }
  %>
 </tr>
  <%
      if (reinsuranceVOs != null && reinsuranceVOs.length > 1)
      {
          try
          {
              for (int i = 1; i < reinsuranceVOs.length; i++)
              {
                  TreatyVO treatyVO = reinsuranceVOs[i].getTreatyVO(0);
                  Reinsurer reinsurer = Reinsurer.findBy_ReinsurerNumber(reinsuranceVOs[i].getReinsurerNumber());
                  ClientDetail clientDetail = ClientDetail.findBy_ReinsurerPK(reinsurer.getPK());
                  String reinsurerName = clientDetail.getCorporateName();
                  EDITBigDecimal reinsurerPct = new EDITBigDecimal(treatyVO.getPoolPercentage()).multiplyEditBigDecimal("100");
                  String treatyNumber = reinsuranceVOs[i].getTreatyGroupNumber();
  %>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td valign=top style='width:8%;padding:0in 5.4pt 0in 5.4pt'>
   <p class=StyleBodyTextLeft0>&nbsp;</p>
   <p class=StyleBodyTextLeft0>Name:</p>
   <p class=StyleBodyTextLeft0>Percent:</p>
   <p class=StyleBodyTextLeft0>Treaty Number:</p>
   <p class=StyleBodyTextLeft0>&nbsp;</p>
   <p class=StyleBodyTextLeft0>&nbsp;</p>
   <p class=StyleBodyTextLeft0>&nbsp;</p>
   <p class=StyleBodyTextLeft0>&nbsp;</p>
  </td>
  <td valign=top style='width:10%;padding:0in 5.4pt 0in 5.4pt' colspan="3">
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= reinsurerName %></span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= reinsurerPct.toString() %></span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE><%= treatyNumber %></span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
   <p class=StyleBodyTextLeft0><span class=SpellE>&nbsp;</span></p>
  </td>
  <%
              }
          }
          catch(Exception e)
          {
              System.out.println(e.getMessage());
          }
      }
  %>
 </tr>
</table>

</div>

<p class=StyleBodyTextLeft01><span style='mso-tab-count:2'>&nbsp;</span></p>

<p class=StyleHeading2Left0>&nbsp;Initial Fund Allocation</p>

<table class=MsoTableTheme border=0 cellspacing=0 cellpadding=0 width="100%"
 style='width:100.0%;border-collapse:collapse;mso-yfti-tbllook:480;mso-padding-alt:
 0in 5.4pt 0in 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td width="12%" valign=top style='width:12%;padding:0in 5.4pt 0in 5.4pt'>
   <p class=StyleBodyTextLeft0><u>Fund Name</u></p>
  </td>
  <td width="10%" valign=top style='width:10%;padding:0in 5.4pt 0in 5.4pt'>
   <p class=StyleBodyTextLeft0><u>% Allocated</u></p>
  </td>
 </tr>
  <%
      EDITBigDecimal totalAllocPct = new EDITBigDecimal();
      if (issueInvestmentVO != null)
      {
          for (int i = 0; i < issueInvestmentVO.length; i++)
          {
              InvestmentVO investmentVO = issueInvestmentVO[i].getInvestmentVO();
              FilteredFund filteredFund = FilteredFund.findByPK(new Long(investmentVO.getFilteredFundFK()));
              Fund fund = filteredFund.getFund();
              String fundName = fund.getName();
              EDITBigDecimal invAllocPct = new EDITBigDecimal(); 
              InvestmentAllocationVO[] invAllocVOs = investmentVO.getInvestmentAllocationVO();
              if (invAllocVOs != null)
              {
                for (int j = 0; j < invAllocVOs.length; j++)
                {
                    if (invAllocVOs[j].getOverrideStatus().equalsIgnoreCase("P"))
                    {
                        invAllocPct = new EDITBigDecimal(invAllocVOs[j].getAllocationPercent()).multiplyEditBigDecimal("100");
                        totalAllocPct = totalAllocPct.addEditBigDecimal(invAllocPct);
                    }
                }
              }
  %>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td width="12%" valign=top style='width:12%;padding:0in 5.4pt 0in 5.4pt'>
   <p class=StyleBodyTextLeft0><%= fundName %></p>
  </td>
  <%
              if (i == issueInvestmentVO.length - 1)
              {
  %>
  <td width="10%" valign=top style='width:10%;padding:0in 5.4pt 0in 5.4pt'>
   <p class=StyleBodyTextLeft0><u><%= invAllocPct.toString() %>%</u></p>
  </td>
  <%
              }
              else
              {
  %>
  <td width="10%" valign=top style='width:10%;padding:0in 5.4pt 0in 5.4pt'>
   <p class=StyleBodyTextLeft0><%= invAllocPct.toString() %>%</p>
  </td>
  <%
              }
  %>
 </tr>
  <%
          }
      }
  %>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td width="12%" valign=top style='width:12%;padding:0in 5.4pt 0in 5.4pt'>
   <p class=StyleBodyTextLeft0><b>TOTAL:</b></p>
  </td>
  <td width="10%" valign=top style='width:10%;padding:0in 5.4pt 0in 5.4pt'>
   <p class=StyleBodyTextLeft0><%= totalAllocPct.toString() %>%</p>
  </td>
 </tr>

 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td width="8%" valign=top style='width:8%;padding:0in 5.4pt 0in 5.4pt'>&nbsp;</td>
 </tr>

 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes'>
  <td width="12%" valign=top style='width:12%;padding:0in 5.4pt 0in 5.4pt'>
   <p class=StyleBodyTextLeft0>Charge Deduct:&nbsp;<%= chargeDeductFund %></p>
  </td>
  <td width="10%" valign=top style='width:10%;padding:0in 5.4pt 0in 5.4pt'>
   <p class=StyleBodyTextLeft0>&nbsp;</p>
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
