package test.acord.client;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
  public static Test suite()
  {
    TestSuite suite;
    suite = new TestSuite("AllTests");
    suite.addTestSuite(CallRqTest.class);
    return suite;
  }
}
