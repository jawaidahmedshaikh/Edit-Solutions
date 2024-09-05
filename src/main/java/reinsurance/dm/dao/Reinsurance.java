/*
 * User: gfrosti
 * Date: Dec 1, 2004
 * Time: 12:45:33 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package reinsurance.dm.dao;

import reinsurance.*;

public class Reinsurance
{
    /**
     * Maps all ReinsuranceHistory.ModalPremiumAmount(s) with an update status of 'U' to its corresponding
     * Treaty.ReinsuranceProcess.
     */
    public void updateReinsurerBalances()
    {
        ReinsuranceHistory[] reinsuranceHistories = ReinsuranceHistory.findBy_UpdateStatus(ReinsuranceHistory.UPDATE_STATUS);

        if (reinsuranceHistories != null)
        {
            for (int i = 0; i < reinsuranceHistories.length; i++)
            {
                reinsuranceHistories[i].updateBalance();
            }
        }
    }
}
