package test.engine.sp.custom.document;

import contract.Segment;

import engine.sp.custom.document.InvestmentArrayDocument;

import junit.framework.TestCase;

import test.textfixture.TestHelper;

public class InvestmentDocumentTest extends TestCase
{
  TestHelper th = new TestHelper();

  public InvestmentDocumentTest(String sTestName)
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
   * @see engine.sp.custom.document.InvestmentArrayDocument#build()
   */
  public void testBuild()
  {
    fail("*** InvestmentDocumentTest.testBuild() needs to be implemented ***");
  }
  
  /**
   * Like many of the Documents, there is a primary "data source" method
   * that builds the composite finder. This method does not use mock entities,
   * but merely tests the syntax of the query.
   * @throws Exception
   */
  public void testDataSourceSyntax() throws Exception
  {
//    Segment.findStatelessBy_EDITTrxPK_V2(new Long(-1));        
  }
}
