/*
 * User: gfrosti
 * Date: May 24, 2006
 * Time: 12:31:05 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.common;

import java.util.*;


/**
 * Convenience class to allow rapid additions to a HashMap.
 */
public class EDITMap extends HashMap
{
    public EDITMap()
    {
    }

    public EDITMap(String name, String value)
    {
        put(name, value);
    }

    public EDITMap(String name, Object value)
    {
        put(name, value);
    }

    /**
     * Convenience method to the basic put method of a Map in that
     * the return is a reference to itself.
     * e.g.
     * tne EDITMap("name1", "value1").put("name2", "value2").put("name3", "value3");
     *
     * @param name
     * @param value
     *
     * @return
     */
    public EDITMap put(String name, String value)
    {
        super.put(name, value);

        return this;
    }

    /**
     * Convenience method to the basic put method of a Map in that
     * the return is a reference to itself.
     * e.g.
     * tne EDITMap("name1", value1).put("name2", value2).put("name3", value3);
     *
     * @param name
     * @param value
     *
     * @return
     */
    public EDITMap put(String name, Object value)
    {
        super.put(name, value);

        return this;
    }

    /**
     * The array of keys.
     *
     * @return
     */
    public Object[] keys()
    {
        Set keys = super.keySet();

        return (Object[]) keys.toArray(new Object[keys.size()]);

//        return keys();
    }
}
