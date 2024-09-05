/*
 * User: gfrosti
 * Date: Oct 27, 2004
 * Time: 2:49:54 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine;

import edit.common.*;

import java.util.*;


public class Area
{
    private String areaCT;
    private long productStructurePK;
    private EDITDate effectiveDate;
    private String grouping;
    private String qualifierCT;

    /************************************** Constructor Methods **************************************/
    public Area(long productStructurePK, String areaCT, String grouping, EDITDate effectiveDate, String qualifierCT)
    {
        this.productStructurePK = productStructurePK;
        this.areaCT = areaCT;
        this.grouping = grouping;
        this.effectiveDate = effectiveDate;
        this.qualifierCT = qualifierCT;
    }

    /************************************** Public Methods **************************************/
    /**
     * Finds the single (or no) AreaValue for the productStructurePK, grouping, effectiveDate, and field. Fields which
     * do not have a direct areaCT match will be assigned the default value (if any).
     * @param field
     * @return
     */
    public AreaValue getAreaValue(String field)
    {
        AreaValue areaValue = null;
        
        AreaValue[] areaValues = AreaValue.findBy_ProductStructurePK_Grouping_Field(productStructurePK, grouping, field);

        if (areaValues != null)
        {
            List areaValueList = buildAreaValueList(areaValues);

            if (areaValueList.size() > 0)
            {
                areaValue = (AreaValue) areaValueList.get(0); // There should only be one.
            }
        }

        return areaValue;
    }

    /**
     * Finds the array of AreaValues (or possibly none) for the productStructurePK, grouping, effectiveDate, and field. Fields which
     * do not have a direct areaCT match will be assigned the default value (if any).
     * @param field
     * @return
     */
    public AreaValue[] getMultipleAreaValues(String field)
    {
        AreaValue[] areaValues = AreaValue.findBy_ProductStructurePK_Grouping_Field(productStructurePK, grouping, field);

        List areaValueList = new ArrayList();

        if (areaValues != null)
        {
            areaValueList = buildAreaValueList(areaValues);
        }

        if (areaValueList.size() > 0)
        {
            return (AreaValue[]) areaValueList.toArray(new AreaValue[areaValueList.size()]);
        }
        else
        {
            return null;
        }
    }

    /**
     * Finds all AreaKeys by productStructurePK, grouping, effectiveDate. Fields which do not have a direct areaCT match
     * will be assigned the default value (if any).
     * @return
     * @see #Area(long, String, String, EDITDate)
     */
    public AreaValue[] getAreaValues()
    {
        AreaValue[] areaValues = AreaValue.findBy_ProductStructurePK_Grouping(productStructurePK, grouping);

        List areaValueList = buildAreaValueList(areaValues);

        return (AreaValue[]) areaValueList.toArray(new AreaValue[areaValueList.size()]);
    }

    /************************************** Private Methods **************************************/
    /**
     * Helper method to find the matching AreaValues.
     * @param areaValues
     * @return
     */
    private List buildAreaValueList(AreaValue[] areaValues)
    {
        List areaValueList = new ArrayList();

        List valueAddedList = new ArrayList();

        if (areaValues != null)
        {
            for (int i = 0; i < areaValues.length; i++)
            {
                AreaValue currentAreaValue = areaValues[i];
                AreaKey areaKey = currentAreaValue.getAreaKey();
                String field = areaKey.getField();

                currentAreaValue.contributeAreaValue(areaCT, effectiveDate, field, areaValueList, valueAddedList, qualifierCT);
            }

            for (int i = 0; i < areaValues.length; i++)
            {
                AreaValue currentAreaValue = areaValues[i];
                AreaKey areaKey = currentAreaValue.getAreaKey();
                String field = areaKey.getField();

                if (!valueAddedList.contains(field))
                {
                   currentAreaValue.contributeDefaultValue(effectiveDate, areaValueList, qualifierCT, areaCT);
                }
            }
        }

        return areaValueList;
    }

    /************************************** Static Methods **************************************/
}
