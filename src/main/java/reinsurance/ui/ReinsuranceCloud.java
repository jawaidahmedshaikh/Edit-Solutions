/*
 * User: gfrosti
 * Date: Nov 18, 2004
 * Time: 3:41:49 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package reinsurance.ui;

import edit.common.vo.*;

import edit.portal.common.session.*;

import edit.services.db.*;

import fission.utility.*;

import java.util.*;

public class ReinsuranceCloud implements Cloud
{
    private ReinsurerVO activeReinsurerVO;
    private TreatyVO activeTreatyVO;
    private Map treaties;

    public ReinsuranceCloud()
    {
        treaties = new HashMap();
    }

    /**
     * @see edit.portal.common.session.Cloud#clearCloudState()
     */
    public void clearCloudState()
    {
        activeReinsurerVO = null;
        activeTreatyVO = null;
        treaties.clear();
    }

    /**
     * Returns the current cloudland Reinsurer.
     * @return
     */
    public ReinsurerVO getActiveReinsurerVO()
    {
        return activeReinsurerVO;
    }

    /**
     * Gets the current cloudland Treaty.
     * @return
     */
    public TreatyVO getActiveTreatyVO()
    {
        return activeTreatyVO;
    }

    /**
     * Returns the list of treaties sorted by start-date, or an empty array of none exist.
     * @return
     */
    public TreatyVO[] getTreatyVOs()
    {
        Collection c = treaties.values();

        TreatyVO[] treatyVOs = (TreatyVO[]) c.toArray(new TreatyVO[c.size()]);

        treatyVOs = (TreatyVO[]) Util.sortObjects(treatyVOs, new String[]
        {
            "getStartDate"
        });

        return treatyVOs;
    }

    /**
     * Returns the specified Treaty, or null if not found.
     * @param treatyPK
     * @return
     */
    public TreatyVO getTreatyVO(long treatyPK)
    {
        return (TreatyVO) treaties.get(new Long(treatyPK));
    }

    /**
     * Sets the active cloudland Reinsurer.
     * @param activeReinsurerVO
     */
    public void setActiveReinsurerVO(ReinsurerVO activeReinsurerVO)
    {
        this.activeReinsurerVO = activeReinsurerVO;
    }

    /**
     * Sets the active cloudland Treaty.
     * @param activeTreatyVO
     */
    public void setActiveTreatyVO(TreatyVO activeTreatyVO)
    {
        this.activeTreatyVO = activeTreatyVO;
    }

    /**
     * A convenience method for bulk adding.
     * @param treatyVOs
     * @see #addTreatyToSummary(edit.common.vo.TreatyVO)
     */
    public void addTreatiesToSummary(TreatyVO[] treatyVOs)
    {
        for (int i = 0; i < treatyVOs.length; i++)
        {
            addTreatyToSummary(treatyVOs[i]);
        }
    }

    /**
     * Adds a Treaty to the list of treaties of the summary. A treaty which does not have a PK (i.e. it is new), is
     * assigned a negativePK for UI identification purposes only. If the VO already exists (i.e. the PK is in the list
     * of PKs), the VO is simply updated with the new values.
     * @param treatyVO
     */
    public void addTreatyToSummary(TreatyVO treatyVO)
    {
        long treatyPK = treatyVO.getTreatyPK();

        if (treatyPK == 0)
        {
            treatyPK = CRUD.getNextAvailableKey() * -1;

            treatyVO.setTreatyPK(treatyPK);
        }

        Long treatyPKObj = new Long(treatyPK);

        if (treaties.containsKey(treatyPKObj))
        {
            treaties.remove(treatyPKObj);
        }

        treaties.put(treatyPKObj, treatyVO);
    }

    /**
     * Removes the specified Treaty from the collection Treaties. The Treaty is flagges for deletion if it is an
     * existing Treaty (PK > ), or removed from the collection if it is a new Treaty (PK <= 0).
     * @param treatyPK
     */
    public void removeTreatyFromSummary(long treatyPK)
    {
        Long treatyPKObj = new Long(treatyPK);

        TreatyVO treatyVO = (TreatyVO) treaties.get(treatyPKObj);

        if (treatyVO.getTreatyPK() > 0)
        {
            treatyVO.setVoShouldBeDeleted(true); // Flag the Treaty
        }
        else
        {
            treaties.remove(treatyPKObj);
        }
    }
}
