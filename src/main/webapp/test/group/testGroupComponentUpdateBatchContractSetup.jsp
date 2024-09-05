<%@ page import="group.component.*,
                 org.dom4j.*,
                 fission.utility.*,
                 org.dom4j.tree.*,
                 group.business.*,
                 group.*"%>
<!--
 * User: sdorman
 * Date: Jun 25, 2007
 * Time: 12:56:06 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%
     String test = "GroupComponent.updateBatchContractSetup()";

     Group group = new GroupComponent();

     // Set up request document
     String requestXMLString =  "<SEGRequestVO>\n" +
                                "  <RequestParameters>\n" +
                                "    <BatchContractSetupVO>\n" +
                                "       <BatchContractSetupPK>1183738271423</BatchContractSetupPK>\n" +
                                "       <ContractGroupFK>1182544086074</ContractGroupFK>\n" +
                                "       <FilteredProductFK>1182974975945</FilteredProductFK>\n" +
                                "       <StatusCT>AL</StatusCT>\n" +
                                "       <NumberOfContracts>0</NumberOfContracts>\n" +
                                "       <EffectiveDate>01/01/2007</EffectiveDate>\n" +
                                "       <ApplicationReceivedDate>01/01/2007</ApplicationReceivedDate>\n" +
                                "       <ApplicationSignedDate>01/01/2007</ApplicationSignedDate>\n" +
                                "       <ApplicationSignedStateCT>CT</ApplicationSignedStateCT>\n" +
                                "       <DeathBenefitOptionCT>Level</DeathBenefitOptionCT>\n" +
                                "       <NonForfeitureOptionCT>Blah</NonForfeitureOptionCT>\n" +
                                "    </BatchContractSetupVO>\n" +
                                "    <BatchProductLogVO>\n" +
                                "       <BatchProductLogPK></BatchProductLogPK>\n" +
                                "       <FilteredProductFK>1182974975945</FilteredProductFK>\n" +
                                "       <BatchContractSetupFK></BatchContractSetupFK>\n" +
                                "       <NumberOfAppsReceived>0</NumberOfAppsReceived>\n" +
                                "       <EstimatedAnnualPremium>123.45</EstimatedAnnualPremium>\n" +
                                "    </BatchProductLogVO>\n" +
                                "    <BatchProgressLogVO>\n" +
                                "       <BatchProgressLogPK></BatchProgressLogPK>\n" +
                                "       <BatchContractSetupFK></BatchContractSetupFK>\n" +
                                "       <DaysAdded>1</DaysAdded>\n" +
                                "       <DaysAddedReasonCT>Tired</DaysAddedReasonCT>\n" +
                                "       <Description>Lazy</Description>\n" +
                                "    </BatchProgressLogVO>\n" +
                                "    <AgentHierarchyPK>1182974975938</AgentHierarchyPK>\n" +          // repeats
                                "  </RequestParameters>\n" +
                                "</SEGRequestVO>";

     Document requestDocument = XMLUtil.parse(requestXMLString);

     // Call service
     Document responseDocument = group.updateBatchContractSetup(requestDocument);
 %>

<html>
    <body>
        <p>
            <b>Test of <%=test %></b>
        </p>
        <p>
            <b><i>INPUT</i></b><br>
        </p>
        <%
            out.println(XMLUtil.parseForJSPDisplay(requestDocument));
        %>

        <p>
            <b><i>OUTPUT</i></b>
        </p>
        <%
            out.println(XMLUtil.parseForJSPDisplay(responseDocument));
        %>
    </body>
</html>
