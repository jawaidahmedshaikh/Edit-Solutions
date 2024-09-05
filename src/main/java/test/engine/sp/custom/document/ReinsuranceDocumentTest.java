package test.engine.sp.custom.document;

import contract.Segment;

import edit.services.db.hibernate.SessionHelper;

import engine.sp.custom.document.ReinsuranceDocument;

import junit.framework.TestCase;

import reinsurance.ContractTreaty;
import reinsurance.Reinsurer;
import reinsurance.Treaty;
import reinsurance.TreatyGroup;

import test.textfixture.TestHelper;


public class ReinsuranceDocumentTest extends TestCase
{
  TestHelper th = new TestHelper();

  Segment segment;

  ContractTreaty contractTreaty;

  Treaty treaty;

  TreatyGroup treatyGroup;

  Reinsurer reinsurer;

  public ReinsuranceDocumentTest(String sTestName)
  {
    super(sTestName);
  }

  /**
   * Builds the following test model:
   * 
   * @throws Exception
   */
  protected void setUp() throws Exception
  {
    super.setUp();

    th.setUp();

    // The required entities.
    segment = (Segment) th.buildEntity(Segment.class, SessionHelper.EDITSOLUTIONS); // root

    contractTreaty = (ContractTreaty) th.buildEntity(ContractTreaty.class, SessionHelper.EDITSOLUTIONS);

    treaty = (Treaty) th.buildEntity(Treaty.class, SessionHelper.EDITSOLUTIONS);

    treatyGroup = (TreatyGroup) th.buildEntity(TreatyGroup.class, SessionHelper.EDITSOLUTIONS); // root

    reinsurer = (Reinsurer) th.buildEntity(Reinsurer.class, SessionHelper.EDITSOLUTIONS); // root

    // Associate
    segment.add(contractTreaty);

    treaty.add(contractTreaty);

    treatyGroup.addTreaty(treaty);

    reinsurer.add(treaty);

    // Set test values.
    treatyGroup.setTreatyGroupNumber("123");

    reinsurer.setReinsurerNumber("456");

    // Save/Flush for Hibernate
    SessionHelper.saveOrUpdate(segment, SessionHelper.EDITSOLUTIONS);

    SessionHelper.saveOrUpdate(treatyGroup, SessionHelper.EDITSOLUTIONS);

    SessionHelper.saveOrUpdate(reinsurer, SessionHelper.EDITSOLUTIONS);

    SessionHelper.flushSessions();
  }


  protected void tearDown() throws Exception
  {
    super.tearDown();

    th.tearDown();

    segment = null;

    contractTreaty = null;

    treaty = null;

    treatyGroup = null;

    reinsurer = null;
  }

  /**
   * 1. Builds the test model as defined in the setUp().
   * 
   * 2. Builds the ReinsuranceDocument
   * 
   * 3. Validates expected structure.
   * @see ReinsuranceDocument#build()
   */
  public void testBuild()
  {
    // 2. Build the ReinsuranceDocument.
    ReinsuranceDocument document = new ReinsuranceDocument(segment.getSegmentPK());

    document.build();

    String xml = document.asXML();

    // 3. Validate expected structure.
    assertTrue("Testing existence of [ReinsuranceDocVO]", xml.indexOf("<ReinsuranceDocVO>") >= 0);

    assertTrue("Testing existence of [TreatyGroupNumber]", xml.indexOf("<TreatyGroupNumber>") >= 0);

    assertTrue("Testing existence of [ReinsurerNumber]", xml.indexOf("<ReinsurerNumber>") >= 0);

    assertTrue("Testing existence of [TreatyVO]", xml.indexOf("<TreatyVO>") >= 0);

    assertTrue("Testing existence of [ContractTreatyVO]", xml.indexOf("<ContractTreatyVO>") >= 0);
  }
}
