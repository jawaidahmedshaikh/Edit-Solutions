package test.engine.sp.custom.document;

import contract.Bucket;
import contract.Investment;
import contract.Segment;

import edit.services.db.hibernate.SessionHelper;

import engine.sp.custom.document.SegmentDocument;

import junit.framework.TestCase;

import test.textfixture.TestHelper;


public class SegmentDocumentTest extends TestCase
{
  TestHelper th = new TestHelper();
  
  Segment segment;
  
  Investment investment;
  
  Bucket bucket;

  public SegmentDocumentTest(String sTestName)
  {
    super(sTestName);
  }

  protected void setUp() throws Exception
  {
    super.setUp();
    
    th.setUp();
    
    // Build the entities.
    segment = (Segment) th.buildEntity(Segment.class, SessionHelper.EDITSOLUTIONS);
    
    investment = (Investment) th.buildEntity(Investment.class, SessionHelper.EDITSOLUTIONS);
    
    bucket = (Bucket) th.buildEntity(Bucket.class, SessionHelper.EDITSOLUTIONS);
    
    // Make the associations.
    segment.addInvestment(investment);
    
    investment.addBucket(bucket);
    
    SessionHelper.flushSessions();
  }

  protected void tearDown() throws Exception
  {
    super.tearDown();
    
    th.tearDown();
  }

  /**
   * Tests the building of SegmentDocument
   * 
   * Looks for the existence of Segment, Investment, Bucket entities in 
   * the Document structure.
   * 
   * @see SegmentDocument#build()
   */
  public void testBuild_VariationOne()
  {
    SegmentDocument document = new SegmentDocument(segment.getSegmentPK());
    
    document.build();
    
    String xml = document.asXML();
  
    fail("***   Need test for SegmentDocument   ***");
    
//    assertTrue("Validating existence of [SegmentVO]", xml.indexOf("<SegmentVO>") >= 0);
  }
}
