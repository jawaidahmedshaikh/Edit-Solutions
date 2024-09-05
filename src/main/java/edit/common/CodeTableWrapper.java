/*
 * User: cgleason
 * Date: Mar 20, 2002
 * Time: 3:57:21 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edit.common.vo.CodeTableDefVO;
import edit.common.vo.CodeTableVO;
import edit.common.vo.FilteredCodeTableVO;
import fission.utility.Util;

public class CodeTableWrapper {

    private static Map<String, CodeTableVO[]> codeTables;
    private static Map<Long, CodeTableDefVO> codeTableDefs;
    private static Map<Long, CodeTableVO> codeTablesByPK;
    private static Map<String, Long> codeTableByCodeTableNameAndCode;
    private static Map<String, String> codeDescByCodeTableNameAndCode;
    private static Map<String, String> codeByCodeTableNameAndCodeDesc;
    private static Map<String, CodeTableDefVO> filteredCodeTablesByProductStructureCodeTableNames;
    private static CodeTableWrapper codeTableWrapper;
   	private static String [] LIFERELATIONTYPE_ORDER = { "Insured", "Owner", "Payor", "Term Insured", "Secondary Addressee", "Primary Beneficiary",
        			               "Annuitant", "Contingent Annuitant", "Contingent Beneficiary", "Check Bank Payor",
        			               "EFT Bank Payor", "Payee", "Trustee", "Secondary Annuitant", "Secondary Owner",
        			               "Deceased", "Full Assignee", "Collateral Assignee", "Irrevocable Beneficiary", 
        			               "Spouse", "Dependent" };

    private CodeTableWrapper()
    {
        codeTables = new HashMap<String, CodeTableVO[]>();
        codeTableDefs = new HashMap<Long, CodeTableDefVO>();
        codeTablesByPK = new HashMap<Long, CodeTableVO>();
        codeTableByCodeTableNameAndCode = new HashMap<String, Long>();
        codeDescByCodeTableNameAndCode = new HashMap<String, String>();
        codeByCodeTableNameAndCodeDesc = new HashMap<String, String>();
        filteredCodeTablesByProductStructureCodeTableNames = new HashMap<String, CodeTableDefVO>();

        try
        {
            loadAllCodeTables();
        }
        catch (Exception e)
        {
        	System.out.println(e);
        	
        	e.printStackTrace();
        	
            throw new RuntimeException(e);
        }
    }

    public static CodeTableWrapper getSingleton(){

        if (codeTableWrapper == null){

            codeTableWrapper = new CodeTableWrapper();
        }

        return codeTableWrapper;
    }

    public void reloadCodeTables()
    {
        codeTables.clear();
        codeTableDefs.clear();
        codeTablesByPK.clear();
        codeTableByCodeTableNameAndCode.clear();
        codeDescByCodeTableNameAndCode.clear();
        codeByCodeTableNameAndCodeDesc.clear();
        filteredCodeTablesByProductStructureCodeTableNames.clear();

        try
        {
            loadAllCodeTables();
        }
        catch (Exception e)
        {
            System.out.println("CodeTableWrapper.loadProblem " + e);
        }

    }

    /**
     * Sets all unfiltered codeTable entries.
     * Then sets all the filtered codeTable entries
     *
     * @param codeTableDefVOs All available CodeTableDefs
     * @return A hashtable of codeTable entries accessible by CodeTableDef name
     */
    private static Map<String, CodeTableVO[]> loadAllCodeTables() throws Exception {

        codetable.business.CodeTable codeTableComponent = new codetable.component.CodeTableComponent();

        CodeTableDefVO[] codeTableDefVOs = codeTableComponent.getFluffyCodeTableDefVOs();

        CodeTableVO[]    codeTableVOs;

        long codeTableDefPK = 0;
        String codeTableDefName;
        List<CodeTableVO> results = new ArrayList<CodeTableVO>();

        for (int i = 0; i < codeTableDefVOs.length; i++) {

            codeTableDefPK   = codeTableDefVOs[i].getCodeTableDefPK();
            codeTableDefs.put(new Long(codeTableDefPK), codeTableDefVOs[i]);

            codeTableDefName = codeTableDefVOs[i].getCodeTableName();

            //get the code table values for each category
            codeTableVOs = codeTableDefVOs[i].getCodeTableVO();

            for (int j = 0; j < codeTableVOs.length; j++) {

                long codeTablePK = codeTableVOs[j].getCodeTablePK();
                results.add(codeTableVOs[j]);
                codeTablesByPK.put(new Long(codeTablePK), codeTableVOs[j]);

                //to get code table id by codeTableDef and Code table value
                String codeTableName = codeTableDefVOs[i].getCodeTableName();
                String code = codeTableVOs[j].getCode();
                String key1 = codeTableName + "_" + code;
                codeTableByCodeTableNameAndCode.put(key1.toUpperCase(), new Long(codeTablePK));

                String codeDesc = codeTableVOs[j].getCodeDesc();

                String key3 = codeTableName + "_" + code;
                codeDescByCodeTableNameAndCode.put(key3.toUpperCase(), new String(codeDesc));

                String key4 = codeTableName + "_" + codeDesc;
                codeByCodeTableNameAndCodeDesc.put(key4.toUpperCase(), new String(code));
            }

            if (codeTableDefName.equals("LIFERELATIONTYPE")) {
            	
                CodeTableVO[] ctVOs = CodeTableWrapper.getCustomSortedTableEntries(codeTableDefName, 
                		results.toArray(new CodeTableVO[results.size()]), LIFERELATIONTYPE_ORDER);
                codeTables.put(codeTableDefName, ctVOs );
                codeTableDefVOs[i].setCodeTableVO(ctVOs);

            } else {
                codeTables.put(codeTableDefName, results.toArray(new CodeTableVO[results.size()]));
            }

            results.clear();
        }

        getAndStoreFilteredCodeTable(codeTableDefVOs);

        return codeTables;
    }

    /**
     * Builds a map of filtered code table entries by productStructurePK and codeTableName.  The objects of the map
     * are CodeTableDefVOs with their CodeTableVO children.  The CodeTableVO's description is the description from
     * the FilteredCodeTable, if it exists, otherwise, it is the CodeTable's.
     *
     * @param codeTableDefVOs
     *
     * @throws Exception
     */
    private static void getAndStoreFilteredCodeTable(CodeTableDefVO[] codeTableDefVOs) throws Exception
    {
        codetable.business.CodeTable codeTableComponent = new codetable.component.CodeTableComponent();

        filteredCodeTablesByProductStructureCodeTableNames = codeTableComponent.getFilteredCodeTableEntries(codeTableDefVOs);
    }
    
    public static CodeTableVO[] getCustomSortedTableEntries(String codeTableName, Object[] codeTables, String[] codeOrder) {
    	CodeTableVO[] customSortedEntries = null;
        if (codeTableName.equals("LIFERELATIONTYPE")) {
        	customSortedEntries = new CodeTableVO[codeTables.length];
        	for (int n = 0; n < codeOrder.length; n++) {
        	    for (int i = 0; i < codeTables.length; i++) {
        	    	if (codeOrder[n].equals(((CodeTableVO)codeTables[i]).getCodeDesc())) {
        	    		customSortedEntries[n] = (CodeTableVO)codeTables[i];
        	    		break;
        	    	}
        	     	
        	    }
        	}
        }
    	return customSortedEntries;
    }

    public CodeTableVO[] getCodeTableEntries(String codeTableDefName){


        CodeTableVO[] sortedEntries = (CodeTableVO[]) Util.sortObjects((Object[])codeTables.get(codeTableDefName), new String[]{"getCodeDesc"});
        
        // deck:  For request to order roles in ui.
        if (codeTableDefName.equals("LIFERELATIONTYPE")) {
            CodeTableVO[] ctVOs = CodeTableWrapper.getCustomSortedTableEntries(codeTableDefName, sortedEntries, LIFERELATIONTYPE_ORDER);
        	sortedEntries = ctVOs;
        }

        return sortedEntries;
    }

    public CodeTableVO[] getTRXCODE_CodeTableEntries(){

        Object[] trxTypeVOs = (CodeTableVO[])codeTables.get("TRXTYPE");
        CodeTableVO[] deathTrxTypeVOS = (CodeTableVO[]) codeTables.get("DEATHTRXTYPE");
        List<CodeTableVO> deathList = new ArrayList<CodeTableVO>();

        for (int i = 0; i < deathTrxTypeVOS.length; i++)
        {
            if (deathTrxTypeVOS[i].getCode().equalsIgnoreCase("DE"))
            {
                deathList.add(deathTrxTypeVOS[i]);
            }
        }

        trxTypeVOs = Util.joinArrays(trxTypeVOs, deathList.toArray(new Object[deathList.size()]), CodeTableVO.class);
        CodeTableVO[] sortedEntries = (CodeTableVO[]) Util.sortObjects(trxTypeVOs, new String[]{"getCodeDesc"});

        return sortedEntries;
    }

    public CodeTableVO[] getCodeTableEntries(String codeTableDefName, long productStructurePK)
    {
        CodeTableVO[] codeTableVOs = null;

        if (productStructurePK == 0)
        {
            codeTableVOs = getCodeTableEntries(codeTableDefName);
        }
        else
        {
            String key = (productStructurePK + "") + "_" + (codeTableDefName + "");

            CodeTableDefVO codeTableDefVO = (CodeTableDefVO) filteredCodeTablesByProductStructureCodeTableNames.get(key);

            if (codeTableDefVO != null)
            {
                codeTableVOs = codeTableDefVO.getCodeTableVO();

                if (codeTableVOs == null)
                {
                    codeTableVOs = (CodeTableVO[]) Util.sortObjects((Object[])codeTables.get(codeTableDefName), new String[]{"getCodeDesc"});
                }
                else
                {
                    codeTableVOs = (CodeTableVO[]) Util.sortObjects(codeTableVOs, new String[]{"getCodeDesc"});
                }
            }
            else
            {
                codeTableVOs = getCodeTableEntries(codeTableDefName);
            }
        }

        return codeTableVOs;
    }

    public CodeTableVO getCodeTableEntry(long codeTablePK) {

        return (CodeTableVO) codeTablesByPK.get(new Long(codeTablePK));
    }

   public long getCodeTablePKByCodeTableNameAndCode(String codeTableName, String code) {

       String key = codeTableName + "_" + code;

       if (codeTableByCodeTableNameAndCode.get(key.toUpperCase()) == null)
       {
            if (codeTableName.equalsIgnoreCase("TRXTYPE"))
            {
                key = checkForTrxTypeTable(key);
            }
            else
            {
                return 0L;
            }
       }
       return ((Long) (codeTableByCodeTableNameAndCode.get(key.toUpperCase()))).longValue();
    }

    public String getCodeDescByCodeTableNameAndCode(String codeTableName, String code){

        String key = codeTableName + "_" + code;

        if (codeTableName.equalsIgnoreCase("TRXTYPE"))
        {
            key = checkForTrxTypeTable(key);
        }

        return ((String) (codeDescByCodeTableNameAndCode.get(key.toUpperCase())));
    }


    public String getCodeByCodeTableNameAndCodeDesc(String codeTableName, String codeDesc)
    {
        String key = codeTableName + "_" + codeDesc;

        return ((String) (codeByCodeTableNameAndCodeDesc.get(key.toUpperCase())));
    }

    public String getCodeDescByCodeTableNameAndCode(String codeTableName, String code, long productStructureId){

        String description = null;
        long codeTablePK = getCodeTablePKByCodeTableNameAndCode(codeTableName, code);

        codetable.business.CodeTable codeTableComponent = new codetable.component.CodeTableComponent();
        FilteredCodeTableVO filteredCodeTableVO = codeTableComponent.findByCodeTablePKAndProductStructure(codeTablePK, productStructureId);

        if (filteredCodeTableVO != null)
        {
            description = filteredCodeTableVO.getCodeDesc();
        }

        if (description == null)
        {
            String key = codeTableName + "_" + code;
            description = ((String) (codeDescByCodeTableNameAndCode.get(key.toUpperCase())));

            //The death trx codes are in a separate table, this code looks at that table for the trx pages
            if (description == null)
            {
                key = "DEATHTRXTYPE_" + code;
                description = ((String) (codeDescByCodeTableNameAndCode.get(key.toUpperCase())));
            }
        }

        return description;
    }

    /**
     * Determines the CodeTableName from the CodeTableDefPK
     *
     * @param codeTableDefPK
     * @return The CodeTable name
     */
    public String getCodeTableDefName(long codeTableDefPK) {

        return ((CodeTableDefVO)codeTableDefs.get(new Long(codeTableDefPK))).getCodeTableName();
    }

    public CodeTableDefVO[] getAllCodeTableDefVOs()
    {
        Collection<CodeTableDefVO> allCodeTableDefs = codeTableDefs.values();

        return (CodeTableDefVO[]) allCodeTableDefs.toArray(new CodeTableDefVO[allCodeTableDefs.size()]);
    }

    /**
     * Returns the filtered CodeTableDefVOs for a given productStructurePK
     *
     * @param productStructurePK
     *
     * @return  array of filtered CodeTableDefVOs which contain their CodeTableVO children
     */
    public CodeTableDefVO[] getFilteredCodeTableDefVOs(Long productStructurePK)
    {
        List<CodeTableDefVO> codeTableDefs = new ArrayList<CodeTableDefVO>();

        String productStructurePKString = productStructurePK.toString();

        Set<String> keys = filteredCodeTablesByProductStructureCodeTableNames.keySet();

        for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();)
        {
            String key = iterator.next();

            if (key.startsWith(productStructurePKString))
            {
                CodeTableDefVO codeTableDefVO = (CodeTableDefVO) filteredCodeTablesByProductStructureCodeTableNames.get(key);

                codeTableDefs.add(codeTableDefVO);
            }
        }

        return (CodeTableDefVO[]) codeTableDefs.toArray(new CodeTableDefVO[codeTableDefs.size()]);
    }

    /**
     * Gets all codeTable entries for a given productStructure.  "All" means the regular codeTable entries with the
     * filtered ones overlayed.
     *
     * @param productStructurePK
     *
     * @return  Array of CodeTableDefVOs containing regular codeTable entries with filtered ones overlayed.
     */
    public CodeTableDefVO[] getAllCodeTableEntries(Long productStructurePK)
    {
        //  Code the codeTableDefs map to a new map that will be manipulated.  The codeTableDefs map contains all of
        //  the regular codeTableDefVOs with the codeTableDefPK as the key
        Map<Long, CodeTableDefVO> manipulatedCodeTableDefs = new HashMap<Long, CodeTableDefVO>(codeTableDefs);

        CodeTableDefVO[] filteredCodeTableDefVOs = this.getFilteredCodeTableDefVOs(productStructurePK);

        //  If the productStructurePK was not specified or if there are no filteredCodeTable entries for the specified
        //  productStructure, return all of the regular codeTableDefVOs
        if (productStructurePK == 0 || filteredCodeTableDefVOs.length == 0)
        {
            return getAllCodeTableDefVOs();
        }
        else
        {
            //  For each filteredCodeTableDefVO, find the corresponding "regular" codeTableDefVO and replace it
            //  with the filtered on.
            for (int i = 0; i < filteredCodeTableDefVOs.length; i++)
            {
                CodeTableDefVO filteredCodeTableDefVO = filteredCodeTableDefVOs[i];

                Long codeTableDefPK = filteredCodeTableDefVO.getCodeTableDefPK();

                if (manipulatedCodeTableDefs.containsKey(codeTableDefPK))
                {
                    manipulatedCodeTableDefs.remove(codeTableDefPK);
                    manipulatedCodeTableDefs.put(codeTableDefPK, filteredCodeTableDefVO);
                }
            }

            //  Convert the manipulated map to an array of CodeTableDefVOs
            Collection<CodeTableDefVO> codeTableDefsCollection = manipulatedCodeTableDefs.values();

            return (CodeTableDefVO[]) codeTableDefsCollection.toArray(new CodeTableDefVO[codeTableDefsCollection.size()]);
        }
    }

    private String checkForTrxTypeTable(String key)
    {
        int i = key.indexOf("_");
        String restOfKey = key.substring(i);

        if (!codeDescByCodeTableNameAndCode.containsKey(key))
        {
            key = "DEATHTRXTYPE" + restOfKey;
        }
        
        return key;
    }
}
