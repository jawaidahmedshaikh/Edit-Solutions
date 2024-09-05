/**
 * User: cgleason
 * Date: Nov 17, 2004
 * Time: 2:59:32 PM
 * <p/>
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine;

import edit.common.EDITBigDecimal;
import edit.common.vo.TableKeysVO;
import engine.sp.*;

import java.util.ArrayList;
import java.util.List;

public class TableKeysMatcher
{

    public TableKeysMatcher()
    {

    }

    /**
     * Get all the tableKey records for the requested table and date.  An instance of script process
     * is needed for the tableKeys parameters set by the script.
     * @param tableDefId
     * @param effectiveDate
     * @param sp
     * @return
     */
    public TableKeysVO[] getTableKeyRecords(long tableDefId, String effectiveDate, ScriptProcessor sp)
    {
        TableKeysVO[] tableKeysVOs = CSCache.getCSCache().getTableKeysBy_TableDefPK_EffectiveDate(tableDefId, effectiveDate);
        List tableKeys = new ArrayList();
        if (tableKeysVOs != null)
        {
            if (tableKeysVOs.length == 1)
            {
                tableKeys.add(tableKeysVOs[0]);
            }
            else
            {
                tableKeys = matchKeysToParams(tableKeysVOs, sp);
            }
        }

        return (TableKeysVO[]) tableKeys.toArray(new TableKeysVO[tableKeys.size()]);
    }

    /**
     * Match to the script parameters - to get only the applicable table key records.  When
     * Activate uses this process only one tableKey record will be returned.  For the Gettierrate
     * instruction an array of table key records will be returned for band processing.
     * @param tableKeysVOs
     * @param sp
     * @return
     */
    private List matchKeysToParams(TableKeysVO[] tableKeysVOs, ScriptProcessor sp)
    {
        String genderId = (String) sp.getWSEntry("Gender");
        String classId = (String) sp.getWSEntry("ClassType");
        String userKey = (String) sp.getWSEntry("UserKey");
        String tableType = (String) sp.getWSEntry("TableType");
        String wsBandAmount = (String) sp.getWSEntry("BandAmount");
        String areaId = areaId = (String) sp.getWSEntry("State");
        EDITBigDecimal ebdBandAmount;

         if (wsBandAmount == null || wsBandAmount.equals(" "))
        {

            ebdBandAmount = new EDITBigDecimal("0");
        }
        else
        {

            ebdBandAmount = new EDITBigDecimal(wsBandAmount);
        }

        List tableKeys  = findQualifiyingTableKeys(tableKeysVOs, genderId, classId, userKey, tableType);
        List finalTableKeys = stateMatch(tableKeys, areaId);

        int count = finalTableKeys.size();
        int entriesToUse = 0;
        boolean equalBandFound = false;

        for (int i = 0; i < count; i++)
        {
            TableKeysVO tableKeysVO = (TableKeysVO)finalTableKeys.get(i);
            if (tableKeysVO != null)
            {
                EDITBigDecimal bandAmount = new EDITBigDecimal(tableKeysVO.getBandAmount());
                if (bandAmount.isEQ(ebdBandAmount))
                {
                    equalBandFound = true;
                    entriesToUse++;
                }
                else if (bandAmount.isLTE(ebdBandAmount))
                {
                    entriesToUse++;
                }
            }
        }

        // If all the tableKey records were eliminated use the first band amount tablekey, it will qualify.
        // The following code sets entriesToUse and removes from finalTableKeys the ones that do not qualify.
        if(!equalBandFound && entriesToUse != count)
        {
            entriesToUse++;
        }

        if (count > entriesToUse)
        {
            for (int i = count - 1; i > entriesToUse - 1; i--)
            {
                finalTableKeys.remove(i);
            }
        }

        return finalTableKeys;
    }

    /**
     * Match the script parameters with the actual tableKey fields to find the qualifying records.
     * @param tableKeysVO
     * @param genderId
     * @param classId
     * @param userKey
     * @param tableType
     * @return
     */
    private List findQualifiyingTableKeys(TableKeysVO[] tableKeysVOs, String genderId, String classId,
                             String userKey, String tableType)
    {

        String space = " ";
        String dashValue = "-";

        List matchTableKeysVO = new ArrayList();
        for (int i = 0; i < tableKeysVOs.length; i++)
        {
            String classType = tableKeysVOs[i].getClassType();
            String sexCode = tableKeysVOs[i].getGender();
            String tableUserKey = tableKeysVOs[i].getUserKey();
            String tableKeyType = tableKeysVOs[i].getTableType();

            if (classType.equalsIgnoreCase("NotApplicable"))
            {

                classId = classType;
            }

            if (sexCode.equalsIgnoreCase("NotApplicable"))
            {

                genderId = sexCode;
            }

            // if userKey empty or dash, use that value
            if (tableUserKey.equals(space) || tableUserKey == null || tableUserKey.equals(dashValue))
            {

                userKey = dashValue;
            }

            if (tableKeyType.equalsIgnoreCase("NotApplicable"))
            {

                tableType = tableKeyType;
            }

            if ((tableKeysVOs[i].getGender().equalsIgnoreCase(genderId)) &&
                    (tableKeysVOs[i].getClassType().equalsIgnoreCase(classId)) &&
                    (tableKeysVOs[i].getUserKey().equalsIgnoreCase(userKey)) &&
                    (tableKeysVOs[i].getTableType().equalsIgnoreCase(tableType)))
            {
                matchTableKeysVO.add(tableKeysVOs[i]);
            }
        }

        return matchTableKeysVO;
    }

    /**
     * From the set of qualifying tableKey records, match to the area(state).
     * @param tableKeys
     * @param areaId
     * @return
     */
     private List stateMatch(List tableKeys, String areaId)
     {
         List finalTableKeys = new ArrayList();

         String defaultState = "NotApplicable";
         int defaultAreaIndex = 0;
         int exactMatchIndex = 0;
         boolean exactAreaMatchFound = false;
         boolean defaultAreaMatchFound = false;
         TableKeysVO tableKeysVO = null;

         for (int i = 0; i < tableKeys.size(); i++)
         {
             //reset boolean before each loop
             exactAreaMatchFound = false;
             tableKeysVO = (TableKeysVO) tableKeys.get(i);

             if (tableKeysVO.getState().equalsIgnoreCase(areaId))
             {

                 exactAreaMatchFound = true;
                 exactMatchIndex = i;
             }

             if (tableKeysVO.getState().equalsIgnoreCase(defaultState))
             {
                 defaultAreaMatchFound = true;
                 defaultAreaIndex = i;
             }

             if (defaultAreaMatchFound && !exactAreaMatchFound)
             {
                 tableKeysVO = (TableKeysVO) tableKeys.get(defaultAreaIndex);
                 finalTableKeys.add(tableKeysVO);
             }

             if (exactAreaMatchFound)
             {

                 tableKeysVO = (TableKeysVO) tableKeys.get(exactMatchIndex);
                 finalTableKeys.add(tableKeysVO);
             }
         }


         return finalTableKeys;
     }
}
