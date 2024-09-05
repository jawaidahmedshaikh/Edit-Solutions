<%@ page import="engine.sp.*,
                 group.component.*,
                 org.dom4j.*,
                 fission.utility.*,
                 billing.component.*,
                 billing.business.*,
                 org.dom4j.tree.*,
                 group.business.*"%>
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
     String test = "GroupComponent.addSegmentToContractGroup";

     Group group = new GroupComponent();

     // Set up request document
     String requestXmlString =        "<SEGRequestVO>\n" +
"                                          <RequestParameters>\n" +
"                                               <SegmentInformationVO>\n" +
"                                                   <FilteredProductPK>1182974975945</FilteredProductPK>\n" +
"                                                   <ContractNumber></ContractNumber>\n" +         ///////////??????????????????
"                                                   <DepartmentLocationPK>1</DepartmentLocationPK>\n" +
"                                                   <IssueStateCT>FL</IssueStateCT>\n" +
"                                                   <FaceAmount>10000</FaceAmount>\n" +
"                                                   <EffectiveDate>07/01/2007</EffectiveDate>\n" +
"                                                   <ApplicationSignedDate>07/01/2007</ApplicationSignedDate>\n" +
"                                                   <ApplicationReceivedDate>07/01/2007</ApplicationReceivedDate>\n" +
"                                                   <ApplicationSignedStateCT>FL</ApplicationSignedStateCT>\n" +
"                                                   <DeathBenefitOptionCT>Level</DeathBenefitOptionCT>\n" +
"                                                   <NonForfeitureOptionCT>NFO</NonForfeitureOptionCT>\n" +   // ??
"                                               </SegmentInformationVO>\n" +
"                                               <BatchContractSetupPK>1183738271423</BatchContractSetupPK>\n" +
"                                               <Operator>System</Operator>\n" +
"                                               <ClientInformationVO>\n" +
"                                                   <ClientDetailPK>1177333251239</ClientDetailPK>\n" +
"                                                   <RelationshipToEmployeeCT>Self</RelationshipToEmployeeCT>\n" +
"                                                   <ContractClientInformationVO>\n" +
"                                                       <RoleTypeCT>Insured</RoleTypeCT>\n" +
"                                                       <ClassCT>Standard</ClassCT>\n" +
"                                                       <TableRatingCT>TableA</TableRatingCT>\n" +
"                                                       <BeneficiaryAllocation></BeneficiaryAllocation>\n" +
"                                                   </ContractClientInformationVO>\n" +
"                                                   <ContractClientInformationVO>\n" +
"                                                       <RoleTypeCT>POR</RoleTypeCT>\n" +
"                                                       <ClassCT></ClassCT>\n" +
"                                                       <TableRatingCT></TableRatingCT>\n" +
"                                                       <BeneficiaryAllocation></BeneficiaryAllocation>\n" +
"                                                   </ContractClientInformationVO>\n" +
"                                               </ClientInformationVO>\n" +
"                                               <ClientInformationVO>\n" +
"                                                   <ClientDetailPK>1177333251234</ClientDetailPK>\n" +
"                                                   <RelationshipToEmployeeCT>Spouse</RelationshipToEmployeeCT>\n" +
"                                                   <ContractClientInformationVO>\n" +
"                                                       <RoleTypeCT>PBE</RoleTypeCT>\n" +
"                                                       <ClassCT></ClassCT>\n" +
"                                                       <TableRatingCT></TableRatingCT>\n" +
"                                                       <BeneficiaryAllocation>1.0</BeneficiaryAllocation>\n" +
"                                                   </ContractClientInformationVO>\n" +
"                                               </ClientInformationVO>\n" +
"                                               <CandidateRiderVO>\n" +
"                                                   <Coverage>Long Term Care Rider</Coverage>\n" +
"                                                   <Qualifier>LTC</Qualifier>\n" +
"                                                   <RequiredOptionalCT>Required</RequiredOptionalCT>\n" +
"                                                   <EffectiveDate></EffectiveDate>\n" +
"                                                   <Units>25.2</Units>\n" +
"                                               </CandidateRiderVO>\n" +
"                                               <CandidateRiderVO>\n" +
"                                                   <Coverage>Child Term Rider</Coverage>\n" +
"                                                   <Qualifier>CTR</Qualifier>\n" +
"                                                   <RequiredOptionalCT>Optional</RequiredOptionalCT>\n" +
"                                                   <EffectiveDate>05/05/2007</EffectiveDate>\n" +
"                                                   <Units>1.456</Units>\n" +
"                                               </CandidateRiderVO>\n" +
"                                           </RequestParameters>\n" +
"                                       </SEGRequestVO>";

     Document requestDocument = XMLUtil.parse(requestXmlString);
     
     // Call service
     Document responseDocument = group.addSegmentToContractGroup(requestDocument);
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
        <p>
        <%
            out.println(XMLUtil.parseForJSPDisplay(responseDocument));
        %>
        </p>
    </body>
</html>
