package edit.services;

import batch.*;
import client.component.*;


/*
 * User: sramamurthy
 * Date: Jul 26, 2004
 * Time: 4:03:39 PM
 *
 * 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
public class EditServiceLocator
{
    private static EditServiceLocator editServiceLocator;

    private static BatchAgent batchAgent;

    public static EditServiceLocator getSingleton()
    {
        if (editServiceLocator == null)
        {
            editServiceLocator = new EditServiceLocator();
        }

        return editServiceLocator;
    }

    public EditOFACCheck getOFACValidateService() throws Exception
    {
        return new EditOFACCheck();
    }

    /**
     * The Agent for the MBeanServer used to house and manager MBeans related to batch jobs.
     * @return
     */
    public BatchAgent getBatchAgent()
    {
        if (batchAgent == null)
        {
            batchAgent = new BatchAgent();
        }

        return batchAgent;
    }
}
