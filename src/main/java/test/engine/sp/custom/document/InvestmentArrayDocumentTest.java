package test.engine.sp.custom.document;

import engine.sp.custom.document.InvestmentArrayDocument;

import junit.framework.TestCase;

import test.textfixture.TestHelper;

public class InvestmentArrayDocumentTest extends TestCase
{
  TestHelper th = new TestHelper();

  public InvestmentArrayDocumentTest(String sTestName)
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
   * @see InvestmentArrayDocument#build()
   */
  public void testBuild()
  {
    fail("***   Test needed for this Document   ***");
  }
}
