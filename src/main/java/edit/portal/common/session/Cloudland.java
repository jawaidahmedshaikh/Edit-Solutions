/*
 * User: gfrosti
 * Date: Nov 18, 2004
 * Time: 3:48:21 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package edit.portal.common.session;

import java.util.*;

/**
 * A user's session involves the tedius process of maintaining signicant UI state. This state is typically, if not
 * always, in the form of VO and composite VO models. Cloudland helps manage this wildly state. For example, in the
 * case of Reinsurance, there could be a ReinsuranceCloud that helps maintain the state of the various in-session
 * form data, summary data, etc.
 */
public class Cloudland
{
    private Map cloudlands;

    public Cloudland()
    {
        cloudlands = new HashMap();
    }

    /**
     * Returns an instance of the requested Cloud class. The cache is checked first to return
     * any pre-existing one, or a new instance of the Cloud is returned.
     * @param cloudClass
     * @return
     */
    public Cloud getCloud(Class cloudClass)
    {
        Cloud cloud = null;

        if ((cloud = (Cloud) cloudlands.get(cloudClass)) == null)
        {
            try
            {
                cloud = (Cloud) cloudClass.newInstance();

                cloudlands.put(cloudClass, cloud);
            }
            catch (Exception e)
            {
                System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        return cloud;
    }
}
