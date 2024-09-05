/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 16, 2002
 * Time: 9:09:32 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package edit.services.component;

import java.util.*;


public class AbstractLookupComponent
{
    protected List convertVOClassExclusionList(String[] voClasses)
    {
        try
        {
            List voClassExclusionList = new ArrayList();

            if ((voClasses != null) && (voClasses.length > 0))
            {
                for (int i = 0; i < voClasses.length; i++)
                {
                    voClassExclusionList.add(Class.forName("edit.common.vo." + voClasses[i]));
                }

                return voClassExclusionList;
            }
            else
            {
                return null;
            }
        }
        catch (ClassNotFoundException e)
        {
            String msg = "LookupComponent.getVOClassExclusionList(): Invalid VO class name: " + e;

            System.out.println(msg);

          System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(msg);
        }
    }
}
