/*
 * User: dlataill
 * Date: Oct 25, 2004
 * Time: 9:44:57 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package event.batch;

import edit.services.db.RecursionListener;
import edit.services.db.VOClass;
import edit.services.db.RecursionContext;
import edit.common.vo.VOObject;
import edit.common.vo.HedgeFundNotificationDetailVO;

import java.io.Serializable;
import java.util.Map;

public class HedgeFundNotificationProcessor implements Serializable, RecursionListener
{
    public HedgeFundNotificationProcessor()
    {
        super();
    }

    public boolean runHedgeFundNotification(String notifyCorrDate) throws Exception
    {
        return true;
    }

    public void currentNode(Object currentNode, Object parentNode, RecursionContext recursionContext)
    {
        Map vosByPK = (Map) recursionContext.getFromMemory("vosByPK");

        VOObject voObject = (VOObject) currentNode;

        VOClass voClass = VOClass.getVOClassMetaData(voObject.getClass());

        if (voObject instanceof HedgeFundNotificationDetailVO)
        {
            if (vosByPK.containsKey("hedgeFundNotificationDetailVO"))
            {
                VOObject voFromOutput = (VOObject) vosByPK.get("hedgeFundNotificationDetailVO");

                voObject.copyFrom(voFromOutput);

                vosByPK.remove("hedgeFundNotificationDetailVO");
            }
        }
        else
        {
            try
            {
                Long pk = (Long) voClass.getPKGetter().getMethod().invoke(voObject, null);

                if (vosByPK.containsKey(pk.toString()))
                {
                    VOObject voFromOutput = (VOObject) vosByPK.get(pk.toString());

                    voObject.copyFrom(voFromOutput);

                    vosByPK.remove(pk.toString());
                }
            }
            catch (Exception e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use Options | File Templates.

                throw new RuntimeException(e);
            }
        }

        if (vosByPK.isEmpty())
        {
            recursionContext.setShouldContinueRecursion(false);
        }
    }
}
