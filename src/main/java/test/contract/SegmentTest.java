package test.contract;

import contract.Segment;

import edit.common.EDITDate;

import junit.framework.TestCase;

import test.textfixture.TestHelper;

public class SegmentTest extends TestCase
{
  TestHelper th = new TestHelper();

  public SegmentTest(String sTestName)
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
   * @see Segment#findBy_SegmentPK_V1(java.lang.Long,edit.common.EDITDate,edit.common.EDITDate,java.lang.String)
   */
  public void testFindBy_SegmentPK_V1()
  {
    Long segmentPK = 1107371748546L;
    
    EDITDate fromDate = new EDITDate("2000/01/01");
    
    EDITDate toDate = new EDITDate("2006/12/31");
    
    String transactionTypeCT = "MF";
    
//    Segment segment = Segment.findBy_SegmentPK_V1(segmentPK, fromDate, toDate, transactionTypeCT);
  }
}
