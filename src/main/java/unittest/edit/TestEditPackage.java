/*
 * User: sdorman
 * Date: Jul 26, 2005
 * Time: 9:45:01 AM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package unittest.edit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class TestEditPackage extends TestCase
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite();

        suite.addTestSuite(TestEDITDate.class);
        suite.addTestSuite(TestEDITDateTime.class);

        return suite;
    }
}
