/*
 * User: gfrosti
 * Date: May 23, 2006
 * Time: 10:37:05 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.common;

import java.util.ArrayList;


/**
 * A convenience class that allows rapid additions to an ArrayList by providing a reference to itself upon adding
 * (e.g.) a String.
 * e.g.
 * new EDITList("msg1").add("msg2").add("msg3").
 */
public class EDITList extends ArrayList
{
    public EDITList()
    {
    }

    /**
     * Adds an initional element to the list of current elements.
     *
     * @param element
     */
    public EDITList(Object element)
    {
        addTo(element);
    }

    /**
     * Adds an additional element to the list of current elements.
     *
     * @param element
     *
     * @return
     */
    public EDITList addTo(Object element)
    {
        super.add(element);

        return this;
    }
}
