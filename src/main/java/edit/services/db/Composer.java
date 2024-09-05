package edit.services.db;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Sep 17, 2003
 * Time: 4:31:26 PM
 * To change this template use Options | File Templates.
 */
public class Composer
{
    protected Object[] retrieveFromSubstitutionHashtable(Map substitutionHashtable, Class childVOClass, Long parentPK)
    {
        List childVOs = (List) substitutionHashtable.get(parentPK);

        if (childVOs == null)
        {
            return null;
        }
        else
        {
            int childCount = childVOs.size();

            Object targetChildVOs = Array.newInstance(childVOClass, childCount);

            for (int i = 0; i < childCount; i++)
            {
                Array.set(targetChildVOs, i, childVOs.get(i));
            }

            return (Object[]) targetChildVOs;
        }
    }

    protected void populateSubstitutionHashtable(Map substitutionHashtable, Object[] childVOs, Method parentFKMethod)
    {
        try
        {
            for (int i = 0; i < childVOs.length; i++)
            {
                Long parentFK = (Long) parentFKMethod.invoke(childVOs[i], null);

                List currentChildVOs = (List) substitutionHashtable.get(parentFK);

                if (currentChildVOs == null)
                {
                    currentChildVOs = new ArrayList();

                    substitutionHashtable.put(parentFK, currentChildVOs);
                }

                currentChildVOs.add(childVOs[i]);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();

            throw new RuntimeException(e.getMessage());
        }
    }
}
