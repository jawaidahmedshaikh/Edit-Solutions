package edit.services.db.hibernate;

import edit.services.db.*;
import org.hibernate.engine.*;
import org.hibernate.id.*;

import java.io.*;
import java.lang.reflect.*;

import fission.utility.*;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Mar 21, 2005
 * Time: 9:57:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class KeyGenerator implements IdentifierGenerator
{
    /**
     * For a conversion jobs being used for the JNL conversions
     */
    private static boolean overrideKeyGenerator;

    public KeyGenerator()
    {

    }

    /**
     * Comment me!
     * @param overrideKeyGen
     */
    public static void setOverrideKeyGenerator(boolean overrideKeyGen)
    {
        overrideKeyGenerator = overrideKeyGen;
    }

    /**
     * Comment me!
     * @return
     */
    public static boolean getOverrideKeyGenerator()
    {
        return overrideKeyGenerator;
    }

    public Serializable generate(SessionImplementor sessionImplementor, Object hibernateObject)
    {
        Long id = null;

        if (getOverrideKeyGenerator())
        {
            try
            {
                String className = Util.getClassName(Util.getFullyQualifiedClassName(hibernateObject.getClass()));

                Method pkMethod = hibernateObject.getClass().getMethod("get" + className + "PK", null);

                Object pkValue = pkMethod.invoke(hibernateObject, null);

                id = (Long) pkValue;
            }
            catch (Exception e)
            {
                System.out.println(e);

                e.printStackTrace();
            }
        }
        else
        {
            id = new Long(CRUD.getNextAvailableKey());
        }

        return id;
    }
}
