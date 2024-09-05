package test.engine.sp.custom.document;

import client.ClientAddress;
import client.ClientDetail;

import client.Preference;
import client.TaxInformation;

import client.TaxProfile;

import contract.ContractClient;
import contract.ContractClientAllocation;
import contract.Segment;

import contract.Withholding;

import edit.common.EDITDate;
import edit.common.EDITDate;

import edit.common.EDITMap;

import edit.services.db.DBColumn;
import edit.services.db.DBDatabase;
import edit.services.db.DBTable;
import edit.services.db.hibernate.SessionHelper;

import engine.sp.custom.document.ClientDocument;

import event.ClientSetup;

import event.ContractClientAllocationOvrd;
import event.EDITTrx;
import event.WithholdingOverride;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import role.ClientRole;

import test.textfixture.TestHelper;

public class ClientDocumentTest extends TestCase
{
  TestHelper th = new TestHelper();

  public ClientDocumentTest(String sTestName)
  {
    super(sTestName);
  }

  protected void setUp() throws Exception
  {
    super.setUp();

    th.setUp();
  }

  protected void tearDown() throws Exception
  {
    super.tearDown();

    th.tearDown();
  }

  /**
   * Test the construction of the ClientDocument as follows:
   * 
   * 1. Build a Hibernate Entity test structure:
   * Segment
   * ContractClient
   * ContractClient.Withholding
   * ContractClient.ContractClientAllocation
   * ContractClient.ClientSetup
   * ContractClient.ClientSetup.WithholdingOverride
   * ContractClient.ClientSetup.WithholdingOverride.Withholding
   * ContractClient.ClientSetup.ContractClientAllocationOvrd
   * ContractClient.ClientSetup.ContractClientAllocationOvrd.Allocation
   * ContractClient.ClientRole.ClientDetail
   * ContractClient.ClientRole.ClientDetail.TaxInformation
   * ContractClient.ClientRole.ClientDetail.TaxInformation.TaxProfile
   * ContractClient.ClientRole.ClientDetail.Preference
   * ContractClient.ClientRole.ClientDetail.ClientAddress.
   * 
   * The following data needs to be set:
   * 
   * ContractClient.TerminationDate = EDITDate.DEFAULT_MAX_DATE
   * 
   * ClientAddress.AddressTypeCT = "PrimaryAddress"
   * ClientAddress.TerminationDate = EDITDate.DEFAULT_MAX_DATE
   * 
   * ClientRole.RoleTypeCT = "OWN"
   * 
   * 2. Build the DOM4J Equivalent taking special note that the existence of
   * the ClientSetup.WithholdingOverride and ClientSetup.ContractClientAllocationOvrd means
   * that the final document needs to SUBSTITUTE the ContractClient.Withholding and
   * ContractClient.Allocation with their overridden records.
   * 
   * 3. Test for expected elements.
   * 
   * @see ClientDocument#build()
   */
  public void testBuild()
  {
    // 1. Build the Hibernate Model
    Segment segment = (Segment) th.buildEntity(Segment.class, SessionHelper.EDITSOLUTIONS);

    ContractClient contractClient = (ContractClient) th.buildEntity(ContractClient.class, SessionHelper.EDITSOLUTIONS);

    Withholding withholding = (Withholding) th.buildEntity(Withholding.class, SessionHelper.EDITSOLUTIONS);

    ContractClientAllocation contractClientAllocation = (ContractClientAllocation) th.buildEntity(ContractClientAllocation.class, SessionHelper.EDITSOLUTIONS);

    ClientSetup clientSetup = (ClientSetup) th.buildEntity(ClientSetup.class, SessionHelper.EDITSOLUTIONS);

    WithholdingOverride withholdingOverride = (WithholdingOverride) th.buildEntity(WithholdingOverride.class, SessionHelper.EDITSOLUTIONS);

    Withholding withholdingForOverride = (Withholding) th.buildEntity(Withholding.class, SessionHelper.EDITSOLUTIONS);

    EDITTrx editTrx = (EDITTrx) th.buildEntity(EDITTrx.class, SessionHelper.EDITSOLUTIONS);

    ContractClientAllocationOvrd contractClientAllocationOvrd = (ContractClientAllocationOvrd) th.buildEntity(ContractClientAllocationOvrd.class, SessionHelper.EDITSOLUTIONS);

    ContractClientAllocation contractClientAllocationForOverride = (ContractClientAllocation) th.buildEntity(ContractClientAllocation.class, SessionHelper.EDITSOLUTIONS);

    ClientRole clientRole = (ClientRole) th.buildEntity(ClientRole.class, SessionHelper.EDITSOLUTIONS);

    ClientDetail clientDetail = new ClientDetail(); //(ClientDetail) th.buildEntity(ClientDetail.class, SessionHelper.EDITSOLUTIONS);

    TaxInformation taxInformation = (TaxInformation) th.buildEntity(TaxInformation.class, SessionHelper.EDITSOLUTIONS);

    TaxProfile taxProfile = (TaxProfile) th.buildEntity(TaxProfile.class, SessionHelper.EDITSOLUTIONS);

    Preference preference = new Preference(); //(Preference) th.buildEntity(Preference.class, SessionHelper.EDITSOLUTIONS);

    ClientAddress clientAddress = (ClientAddress) th.buildEntity(ClientAddress.class, SessionHelper.EDITSOLUTIONS);

    // Associate the entities.
    segment.addContractClient(contractClient); // Save

    contractClient.addWithholding(withholding);

    contractClient.addContractClientAllocation(contractClientAllocation);

    contractClient.addClientSetup(clientSetup);

    clientSetup.addContractClientAllocationOvrd(contractClientAllocationOvrd);

    clientSetup.addWithholdingOverride(withholdingOverride);

    clientSetup.addEDITTrx(editTrx);

    contractClientAllocationForOverride.addContractClientAllocationOverride(contractClientAllocationOvrd); // Save

    withholdingForOverride.addWithholdingOverride(withholdingOverride); // Save

    clientRole.addContractClient(contractClient);

    clientDetail.addClientRole(clientRole); // Save

    clientDetail.addPreference(preference);

    clientDetail.addClientAddress(clientAddress);

    clientDetail.addTaxInformation(taxInformation);

    taxInformation.addTaxProfile(taxProfile);

    // Set test values.
    contractClient.setTerminationDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));

    clientAddress.setAddressTypeCT(ClientAddress.CLIENT_PRIMARY_ADDRESS);

    clientAddress.setTerminationDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));

    clientRole.setRoleTypeCT(ClientRole.ROLETYPECT_OWNER);

    clientRole.setNewIssuesEligibilityStatusCT("Y");

    // Save/Flush root entities for Hibernate.
    SessionHelper.saveOrUpdate(segment, SessionHelper.EDITSOLUTIONS);

    SessionHelper.saveOrUpdate(contractClientAllocationForOverride, SessionHelper.EDITSOLUTIONS);

    SessionHelper.saveOrUpdate(withholdingForOverride, SessionHelper.EDITSOLUTIONS);

    SessionHelper.saveOrUpdate(clientDetail, SessionHelper.EDITSOLUTIONS);

    SessionHelper.flushSessions();

    // 2. Build the Document.

    ClientDocument clientDocument = new ClientDocument(segment.getSegmentPK(), ClientRole.ROLETYPECT_OWNER, ClientDocument.BUILDING_PARAMETER_VALUE_SEGMENT);

    clientDocument.build();


    // 3. Test for expected Elements

    String xml = clientDocument.asXML();

    Assert.assertTrue("Validating existence of [ClientDocVO]", xml.indexOf("<ClientDocVO>") >= 0);

    Assert.assertTrue("Validating existence of [RoleTypeCT]", xml.indexOf("<RoleTypeCT>") >= 0);

    Assert.assertTrue("Validating existence of [NewIssuesEligibilityStatusCT]", xml.indexOf("<NewIssuesEligibilityStatusCT>") >= 0);

    Assert.assertTrue("Validating existence of [ClientPK]", xml.indexOf("<ClientPK>") >= 0);
    
    Assert.assertTrue("Validating existence of [ClientDetailVO]", xml.indexOf("<ClientDetailVO>") >= 0);

    Assert.assertTrue("Validating existence of [WithholdingVO]", xml.indexOf("<WithholdingVO>") >= 0);
    
    Assert.assertTrue("Validating existence of [ContractClientAllocationVO]", xml.indexOf("<ContractClientAllocationVO>") >= 0);

    Assert.assertTrue("Validating existence of [WithholdingVO]", xml.indexOf("<WithholdingVO>") >= 0);

    Assert.assertTrue("Validating NON existence (should not be in this Document) of [ClientRoleVO]", xml.indexOf("<ClientRoleVO>") < 0);

    Assert.assertTrue("Validating existence of [ClientDetailVO]", xml.indexOf("<ClientDetailVO>") >= 0);

    Assert.assertTrue("Validating existence of [TaxInformationVO]", xml.indexOf("<TaxInformationVO>") >= 0);

    Assert.assertTrue("Validating existence of [TaxProfileVO]", xml.indexOf("<TaxProfileVO>") >= 0);

    Assert.assertTrue("Validating existence of [PreferenceVO]", xml.indexOf("<PreferenceVO>") >= 0);

    Assert.assertTrue("Validating existence of [ClientAddressVO]", xml.indexOf("<ClientAddressVO>") >= 0);
  }
}
