package test;

import junit.framework.Test;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

import test.engine.sp.custom.document.ClientDocumentTest;
import test.engine.sp.custom.document.CommissionDocumentTest;
import test.engine.sp.custom.document.GroupSetupDocumentTest;
import test.engine.sp.custom.document.InvestmentArrayDocumentTest;
import test.engine.sp.custom.document.InvestmentDocumentTest;
import test.engine.sp.custom.document.OverdueChargeRemainingDocumentTest;
import test.engine.sp.custom.document.ReinsuranceDocumentTest;
import test.engine.sp.custom.document.RiderDocumentTest;
import test.engine.sp.custom.document.SegmentDocumentTest;
import test.engine.sp.custom.document.TamraRetestDocumentTest;

public class TestAll_EngineSpCustomDocument
{
  public static Test suite()
  {
    TestSuite suite;
    suite = new TestSuite("TestDocuments");
    suite.addTestSuite(ClientDocumentTest.class);
    suite.addTestSuite(CommissionDocumentTest.class);
    suite.addTestSuite(GroupSetupDocumentTest.class);
    suite.addTestSuite(InvestmentDocumentTest.class);
    suite.addTestSuite(InvestmentArrayDocumentTest.class);
    suite.addTestSuite(OverdueChargeRemainingDocumentTest.class);
    suite.addTestSuite(ReinsuranceDocumentTest.class);
    suite.addTestSuite(SegmentDocumentTest.class);
    suite.addTestSuite(RiderDocumentTest.class);
    suite.addTestSuite(TamraRetestDocumentTest.class);
    
    return suite;
  }
}
