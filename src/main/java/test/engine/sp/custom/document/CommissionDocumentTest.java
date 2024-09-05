package test.engine.sp.custom.document;

import agent.AdditionalCompensation;
import agent.Agent;
import agent.AgentContract;
import agent.CheckAdjustment;
import agent.CommissionProfile;
import agent.PlacedAgent;

import agent.PlacedAgentCommissionProfile;

import client.ClientDetail;

import contract.*;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;

import edit.common.EDITDate;

import edit.services.db.hibernate.SessionHelper;

import engine.sp.custom.document.CommissionDocument;

import java.math.BigDecimal;

import junit.framework.Assert;
import junit.framework.TestCase;

import role.ClientRole;

import role.ClientRoleFinancial;

import test.textfixture.TestHelper;

public class CommissionDocumentTest extends TestCase
{
  TestHelper th = new TestHelper();

  public CommissionDocumentTest(String sTestName)
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
   * Tests building of the CommissionDocument driven by a Segment and a trxEffectiveDate
   * of the currently running EDITTrx.
   * 
   * 1. Build a Hibernate Segment aggregation that contains Segment, AgentHierarchy ...
   * through PlacedAgent ... Clientdetail.
   * 
   * 2. Build the CommissionDocument using the test Segment and test EDITTrx.EffectiveDate (12/01/2006)
   * 
   * 3. Test the CommissionDocument to see if the expected Elements exist.
   * 
   *
   * @see CommissionDocument#build()
   */
  public void testBuild() throws Exception
  {
    // ... EDITDate.EffectiveDate 
    EDITDate trxEffectiveDate = new EDITDate("2006/12/01");

    // 1. Build the Hibernate Segment aggregation.
    Segment segment = (Segment) th.buildEntity(Segment.class, SessionHelper.EDITSOLUTIONS);

    AgentHierarchy agentHierarchy = (AgentHierarchy) th.buildEntity(AgentHierarchy.class, SessionHelper.EDITSOLUTIONS);

    AgentHierarchyAllocation agentHierarchyAllocation = (AgentHierarchyAllocation) th.buildEntity(AgentHierarchyAllocation.class, SessionHelper.EDITSOLUTIONS);

    AgentSnapshot agentSnapshot = (AgentSnapshot) th.buildEntity(AgentSnapshot.class, SessionHelper.EDITSOLUTIONS);

    PlacedAgent placedAgent = (PlacedAgent) th.buildEntity(PlacedAgent.class, SessionHelper.EDITSOLUTIONS);

    PlacedAgentCommissionProfile placedAgentCommissionProfile = (PlacedAgentCommissionProfile) th.buildEntity(PlacedAgentCommissionProfile.class, SessionHelper.EDITSOLUTIONS);

    CommissionProfile commissionProfile = (CommissionProfile) th.buildEntity(CommissionProfile.class, SessionHelper.EDITSOLUTIONS);

    AgentContract agentContract = (AgentContract) th.buildEntity(AgentContract.class, SessionHelper.EDITSOLUTIONS);

    AdditionalCompensation additionalCompensation = (AdditionalCompensation) th.buildEntity(AdditionalCompensation.class, SessionHelper.EDITSOLUTIONS);

    Agent agent = (Agent) th.buildEntity(Agent.class, SessionHelper.EDITSOLUTIONS);

    CheckAdjustment checkAdjustment = (CheckAdjustment) th.buildEntity(CheckAdjustment.class, SessionHelper.EDITSOLUTIONS);

    ClientRole clientRole = (ClientRole) th.buildEntity(ClientRole.class, SessionHelper.EDITSOLUTIONS);

    ClientRoleFinancial clientRoleFinancial = (ClientRoleFinancial) th.buildEntity(ClientRoleFinancial.class, SessionHelper.EDITSOLUTIONS);

    ClientDetail clientDetail = (ClientDetail) th.buildEntity(ClientDetail.class, SessionHelper.EDITSOLUTIONS);

    // ... Associate the entities
    segment.addAgentHierarchy(agentHierarchy);

    agentHierarchy.addAgentSnapshot(agentSnapshot);

    agentHierarchy.addAgentHierarchyAllocation(agentHierarchyAllocation);

    placedAgent.add(agentSnapshot);

    placedAgent.add(placedAgentCommissionProfile);

    commissionProfile.add(placedAgentCommissionProfile);

    agentContract.addPlacedAgent(placedAgent);

    agentContract.addAdditionalCompensation(additionalCompensation);

    agent.addAgentContract(agentContract);

    agent.add(checkAdjustment);

    agent.addClientRole(clientRole);

    clientRole.add(clientRoleFinancial);

    clientDetail.addClientRole(clientRole);


    // ... specify some dates, etc.  which will be needed for the query and testing conditions
    placedAgentCommissionProfile.setStartDate(trxEffectiveDate);

    placedAgentCommissionProfile.setStopDate(trxEffectiveDate);

    clientRole.setRoleTypeCT("Agent");

    agentHierarchyAllocation.setAllocationPercent(new EDITBigDecimal("1.1"));

    agentSnapshot.setCommissionOverrideAmount(new EDITBigDecimal("1.11"));

    agentSnapshot.setCommissionOverridePercent(new EDITBigDecimal("1.11"));
    
    checkAdjustment.setStartDate(new EDITDate(EDITDate.DEFAULT_MIN_DATE));
    
    checkAdjustment.setStopDate(new EDITDate(EDITDate.DEFAULT_MAX_DATE));
    
    checkAdjustment.setAdjustmentCompleteInd("N");


    // ... Save and flush for Hibernate
    SessionHelper.saveOrUpdate(segment, SessionHelper.EDITSOLUTIONS); // One root

    SessionHelper.saveOrUpdate(clientRole, SessionHelper.EDITSOLUTIONS); // Two roots

    SessionHelper.flushSessions();


    // 2. Build the CommissionDocument
    CommissionDocument commissionDocument = new CommissionDocument(segment, trxEffectiveDate);

    commissionDocument.build();


    // 3. Look for expected elements.
    String xml = commissionDocument.asXML();

    System.out.println(xml);

    Assert.assertTrue("Validating existence of [CommissionDocVO]", xml.indexOf("<CommissionDocVO>") >= 0);

    Assert.assertTrue("Validating existence of [CommissionVO]", xml.indexOf("<CommissionVO>") >= 0);

    Assert.assertTrue("Validating existence of [AgentSnapshotDetailVO]", xml.indexOf("<AgentSnapshotDetailVO>") >= 0);

    Assert.assertTrue("Validating existence of [AgentGroupFK]", xml.indexOf("<AgentGroupFK>") >= 0);

    Assert.assertTrue("Validating existence of [AgentSnapshotDetailPK]", xml.indexOf("<AgentSnapshotDetailPK>") >= 0);

    Assert.assertTrue("Validating existence of [PlacedAgentFK]", xml.indexOf("<PlacedAgentFK>") >= 0);

    Assert.assertTrue("Validating existence of [CommissionOverrideAmount]", xml.indexOf("<CommissionOverrideAmount>") >= 0);

    Assert.assertTrue("Validating existence of [CommissionOverridePercent]", xml.indexOf("<CommissionOverridePercent>") >= 0);

    Assert.assertTrue("Validating existence of [AgentVO]", xml.indexOf("<AgentVO>") >= 0);

    Assert.assertTrue("Validating existence of [CheckAdjustmentVO]", xml.indexOf("<CheckAdjustmentVO>") >= 0);

    Assert.assertTrue("Validating existence of [CommissionProfileVO]", xml.indexOf("<CommissionProfileVO>") >= 0);

    Assert.assertTrue("Validating existence of [AdditionalCompensationVO]", xml.indexOf("<AdditionalCompensationVO>") >= 0);

    Assert.assertTrue("Validating existence of [ClientDetailVO]", xml.indexOf("<ClientDetailVO>") >= 0);

    Assert.assertTrue("Validating existence of [ClientRoleVO]", xml.indexOf("<ClientRoleVO>") >= 0);

    Assert.assertTrue("Validating existence of [ClientRoleFinancialVO]", xml.indexOf("<ClientRoleFinancialVO>") >= 0);


  }
}
