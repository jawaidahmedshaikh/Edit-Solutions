package test.fission.utility;

import fission.utility.Util;

import junit.framework.TestCase;

public class UtilTest extends TestCase
{
  public UtilTest(String sTestName)
  {
    super(sTestName);
  }

  /**
   * Test simple success/failure cases when using the tokenized resource bundle
   * messages.
   * @see Util#getResourceMessage(String,String[])
   */
  public void testGetResourceMessage() throws Exception
  {
    // Test success
    String[] tokenValues = { "foo1", "foo2" };

    String successMessage = "A message with foo1 token and foo2 token.";

    String testMessage = Util.getResourceMessage("foo.message.1", tokenValues);

    assertEquals(successMessage, testMessage);

    // Test failure - there are more token values than token spots.
    tokenValues = new String[] { "foo1", "foo2", "foo3" };

    try
    {
      Util.getResourceMessage("foo.message.1", tokenValues);
      
      fail("There were more token values than tokens - it should have failed.");
    }
    catch (Exception e)
    {      
    }
  }
}
