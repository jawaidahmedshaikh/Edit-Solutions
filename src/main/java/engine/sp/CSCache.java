/*
 * User: unknown
 * Date: Mar 24, 2003
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine.sp;

import edit.common.vo.FilteredFundVO;
import edit.common.vo.FundVO;
import edit.common.vo.RateTableVO;
import edit.common.vo.RulesVO;
import edit.common.vo.ScriptLineVO;
import edit.common.vo.ScriptVO;
import edit.common.vo.TableDefVO;
import edit.common.vo.TableKeysVO;

import edit.services.logging.Logging;

import engine.ProductStructure;

import engine.dm.dao.DAOFactory;
import engine.dm.dao.FastDAO;
import engine.dm.dao.FilteredFundDAO;
import engine.dm.dao.FundDAO;
import engine.dm.dao.ScriptLineDAO;
import engine.dm.dao.TableDefDAO;

import java.util.List;
import java.util.Map;

import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import logging.LogEvent;


public class CSCache
{
    private static CSCache csCache;
    private boolean enableCaching = true;
    private Map scriptPKsBy_BestMatchElements;
    private Map scriptNamesBy_ScriptPK;
    private Map scriptPKsBy_ScriptName;
    private Map filteredFundVOsByPK;
    private Map fundVOBy_FundPK;
    private Map tableDefVOBy_TableDefPK;
    private Map tableDefVOBy_TableName;
    private Map tableKeysBy_TableDefPK_EffectiveDate;
    private Map issueRatesBy_TableKeyPK_Age;
    private Map scriptLinesBy_ScriptPK;
    private Map fundVOBy_FilteredFundPK;

    private CSProvider csProvider;

    private CSCache()
    {
        init();
    }

    public static final CSCache getCSCache()
    {
        if (csCache == null)
        {
            csCache = new CSCache();
        }

        return csCache;
    }

    /**
     * The ordered ScriptLines by ScriptPK.
     * @param scriptPK
     * @return
     */
    public ScriptLineVO[] getScriptLinesBy_ScriptPK(long scriptPK)
    {
        ScriptLineVO[] scriptLines = new ScriptLineDAO().findScriptLinesById(scriptPK);

        //      Long key = new Long(scriptPK);

        //      if ((scriptLines = (ScriptLineVO[]) scriptLinesBy_ScriptPK.get(key)) == null)
        //      {
        //        scriptLines = new ScriptLineDAO().findScriptLinesById(scriptPK);
        //
        //        if (enableCaching)
        //        {
        //          scriptLinesBy_ScriptPK.put(key, scriptLines);
        //        }
        //      }

        return scriptLines;
    }

    public void clearCSCache()
    {
        scriptPKsBy_BestMatchElements.clear();

        scriptPKsBy_ScriptName.clear();

        scriptNamesBy_ScriptPK.clear();

        filteredFundVOsByPK.clear();

        tableDefVOBy_TableDefPK.clear();

        tableDefVOBy_TableName.clear();

        fundVOBy_FundPK.clear();

        tableKeysBy_TableDefPK_EffectiveDate.clear();

        issueRatesBy_TableKeyPK_Age.clear();

        scriptLinesBy_ScriptPK.clear();

        fundVOBy_FilteredFundPK.clear();

        csProvider.clear();
    }

    /**
     * Simple caching getter to avoid a potential best match hits via KeyMatcher.
     * Cache Hit Success: Near;y 0% on initial run, cached from that point on.
     * @param ruleName
     * @return
     */
    public Long getScriptPKBy_BestMatchElements(String ruleName, ProductRuleProcessor pr) throws RuntimeException
    {
        Long scriptPK = null;

        String key = getKey(pr.getProductStructurePK(), ruleName, pr.getProcessName(), pr.getEventName(), pr.getEventTypeName());

        scriptPK = (Long) scriptPKsBy_BestMatchElements.get(key);

        if (scriptPK == null)
        {
            RulesVO rulesVO = pr.getBestMatchScriptId(ruleName);

            if (rulesVO != null)
            {
                scriptPK = new Long(rulesVO.getScriptFK());

                if (enableCaching)
                {
                    scriptPKsBy_BestMatchElements.put(key, scriptPK);
                }
            }
        }

        return scriptPK;
    }

    /**
     * As of this writing, there was a single unique need for this method. We needed to fake a best-rules match
     * for Scripts that are associated with a RuleVO found from a GetTable instruction. GetTable always maps
     * to a Table Rule. The twist is that Table Rule may also have a Script associated with it. To make
     * caching work, we need to make this Script available via GetScript as opposed to Call. Dizzying, but
     * necessary.
     * 
     *@param ruleName
     * @return boolean true if a ScriptPK was able to be set (i.e. a Rule was found), false otherwise
     */
    public boolean setScriptPKBy_BestMatchElements(String ruleName, ProductRuleProcessor pr) throws RuntimeException
    {
        boolean scriptPKSet = false;
        
        String key = getKey(pr.getProductStructurePK(), ruleName, pr.getProcessName(), pr.getEventName(), pr.getEventTypeName());

        RulesVO rulesVO = pr.getBestMatchScriptId(ruleName);

        if (rulesVO != null)
        {
            Long scriptPK = new Long(rulesVO.getScriptFK());

            scriptPKsBy_BestMatchElements.put(key, scriptPK);
            
            scriptPKSet = true;
        }
        
        return scriptPKSet;
    }

    /**
     * Simple caching getter.
     * @param scriptName
     * @return
     */
    public Long getScriptPKBy_ScriptName(String scriptName) throws RuntimeException // Checked
    {
        Long scriptPK = null;

        scriptPK = (Long) scriptPKsBy_ScriptName.get(scriptName);

        if (scriptPK == null)
        {
            try
            {
                ScriptVO scriptVO = DAOFactory.getScriptDAO().findByScriptName(scriptName)[0];

                scriptPK = new Long(scriptVO.getScriptPK());

                if (enableCaching)
                {
                    scriptPKsBy_ScriptName.put(scriptName, scriptPK);
                }
            }
            catch (RuntimeException e)
            {
                String message = ("Script Not Found, Name = [ " + scriptName + " ]");

                Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(message));

                logging.Log.logGeneralExceptionToDatabase(message, e);

                throw new RuntimeException(message);
            }
        }

        return scriptPK;
    }

    /**
     * Simple caching getter.
     * Cache Success Hit: 50+%
     * @param scriptPK
     * @return
     */
    public String getScriptNameBy_ScriptPK(long scriptPK)
    {
        String scriptName = null;

        scriptName = (String) scriptNamesBy_ScriptPK.get(new Long(scriptPK));

        if (scriptName == null)
        {
            ScriptVO scriptVO = DAOFactory.getScriptDAO().findScriptById(scriptPK)[0];

            scriptName = scriptVO.getScriptName();

            if (enableCaching)
            {
                scriptNamesBy_ScriptPK.put(new Long(scriptPK), scriptName);
            }
        }

        return scriptName;
    }

    /**
     * Simple caching getter. If the target FilteredFundVO is not found in cache, it is retrieved and added to the cache.
     * Cache Hit Success: Over 90%
     * @param filteredFundPK
     * @return
     */
    public FilteredFundVO getFilteredFundVOBy_FilteredFundPK(long filteredFundPK)
    {
        Long key = null;

        FilteredFundVO filteredFundVO;

        key = new Long(filteredFundPK);

        filteredFundVO = (FilteredFundVO) filteredFundVOsByPK.get(key);

        if (filteredFundVO == null)
        {
            FilteredFundVO[] filteredFundVOs = new FilteredFundDAO().findByFilteredFundPK(filteredFundPK);

            if (filteredFundVOs != null)
            {
                filteredFundVO = filteredFundVOs[0];

                if (enableCaching)
                {
                    filteredFundVOsByPK.put(key, filteredFundVO);
                }
            }
        }

        return filteredFundVO;
    }

    /**
     * Simple caching getter. If the target TableDefVO is not found in cache, it is retrieved and added to the cache.
     * @param tableDefPK
     * @return
     */
    public TableDefVO getTableDefVOBy_TableDefPK(long tableDefPK)
    {
        Long key = null;

        TableDefVO tableDefVO;

        key = new Long(tableDefPK);

        tableDefVO = (TableDefVO) tableDefVOBy_TableDefPK.get(key);

        if (tableDefVO == null)
        {
            TableDefVO[] tableDefVOs = new TableDefDAO().findTableNameById(tableDefPK);

            if (tableDefVOs != null)
            {
                tableDefVO = tableDefVOs[0];

                if (enableCaching)
                {
                    tableDefVOBy_TableDefPK.put(key, tableDefVO);
                }
            }
        }

        return tableDefVO;
    }

    /**
     * Simple caching getter. If the target TableDefVO is not found in cache, it is retrieved and added to the cache.
     * @param tableName
     * @return
     */
    public TableDefVO getTableDefVOBy_TableName(String tableName)
    {
        TableDefVO tableDefVO = null;

        tableDefVO = (TableDefVO) tableDefVOBy_TableName.get(tableName);

        if (tableDefVO == null)
        {
            TableDefVO[] tableDefVOs = new TableDefDAO().findTableIdByName(tableName);

            if (tableDefVOs != null)
            {
                tableDefVO = tableDefVOs[0];

                if (enableCaching)
                {
                    tableDefVOBy_TableName.put(tableName, tableDefVO);
                }
            }
        }

        return tableDefVO;
    }

    /**
     * Initializes the instance variabls of this CSCache singleton.
     */
    private final void init()
    {
        scriptPKsBy_BestMatchElements = new ConcurrentHashMap();

        scriptPKsBy_ScriptName = new ConcurrentHashMap();

        scriptNamesBy_ScriptPK = new ConcurrentHashMap();

        filteredFundVOsByPK = new ConcurrentHashMap();

        tableDefVOBy_TableDefPK = new ConcurrentHashMap();

        tableDefVOBy_TableName = new ConcurrentHashMap();

        fundVOBy_FundPK = new ConcurrentHashMap();

        tableKeysBy_TableDefPK_EffectiveDate = new ConcurrentHashMap();

        issueRatesBy_TableKeyPK_Age = new ConcurrentHashMap();

        scriptLinesBy_ScriptPK = new ConcurrentHashMap();

        fundVOBy_FilteredFundPK = new ConcurrentHashMap();

        csProvider = new CSProvider();
    }

    /**
     * Utility method to concatenate the ruleName to the productStructurePK
     * @param ruleName
     * @param productStructurePK
     * @return
     */
    private String getKey(long productStructurePK, String ruleName, String process, String event, String eventType)
    {
        ruleName = (ruleName == null)? "": ruleName;

        process = (process == null)? "": process;

        event = (event == null)? "": event;

        eventType = (eventType == null)? "": eventType;

        String key = productStructurePK + "-" + ruleName + "-" + process + "-" + event + "-" + eventType;

        return key;
    }

    /**
     * Getter.
     * @return
     */
    public boolean isCachingEnabled()
    {
        return enableCaching;
    }

    public FundVO getFundVOBy_FilteredFundPK(long filteredFundPK)
    {
        Long key = null;

        FundVO fundVO;

        key = new Long(filteredFundPK);

        fundVO = (FundVO) fundVOBy_FilteredFundPK.get(key);

        if (fundVO == null)
        {
            fundVO = new FundDAO().findFundbyFilteredFundPK(filteredFundPK, false, null)[0];

            if (fundVO != null)
            {
                if (enableCaching)
                {
                    fundVOBy_FilteredFundPK.put(key, fundVO);
                }
            }
        }

        return fundVO;
    }

    /**
     * Simple caching getter. If the target fundTypeCT is not found in cache, it is retrieved and added to the cache.
     * Cache Hit Success: Over 90%*
     * @param fundPK
     * @return
     */
    public FundVO getFundVOBy_FundPK(long fundPK)
    {
        Long key = null;

        FundVO fundVO;

        key = new Long(fundPK);

        fundVO = (FundVO) fundVOBy_FundPK.get(key);

        if (fundVO == null)
        {
            fundVO = new FastDAO().findFundVOBy_FundPK(fundPK);

            if (fundVO != null)
            {
                if (enableCaching)
                {
                    fundVOBy_FundPK.put(key, fundVO);
                }
            }
        }

        return fundVO;
    }

    /**
     * Finder. If TableKeys are not in cache, they are retrieved and added to cache before returning.
     * @param tableDefPK
     * @param effectiveDate
     * @return
     */
    public TableKeysVO[] getTableKeysBy_TableDefPK_EffectiveDate(long tableDefPK, String effectiveDate)
    {
        String key = tableDefPK + "-" + effectiveDate;

        TableKeysVO[] tableKeysVOs;

        tableKeysVOs = null;

        tableKeysVOs = (TableKeysVO[]) tableKeysBy_TableDefPK_EffectiveDate.get(key);

        if (tableKeysVOs == null)
        {
            tableKeysVOs = new FastDAO().findTableKeysByTableDefPKAndEffectiveDate(tableDefPK, effectiveDate);

            if (enableCaching)
            {
                tableKeysBy_TableDefPK_EffectiveDate.put(key, tableKeysVOs);
            }
        }

        return tableKeysVOs;
    }

    /**
     * Finder. If RateTables are not in cache, they are retrieved and added to cache before returning.
     * @param tableKeyId
     * @param age
     * @return
     */
    public RateTableVO[] getIssueRatesBy_TableKeyPK_Age(long tableKeyId, int age)
    {
        String key = tableKeyId + "-" + age;

        RateTableVO[] rateTableVOs = null;

        rateTableVOs = (RateTableVO[]) issueRatesBy_TableKeyPK_Age.get(key);

        if (rateTableVOs == null)
        {
            rateTableVOs = new FastDAO().findIssueRates(tableKeyId, age);

            if (enableCaching)
            {
                issueRatesBy_TableKeyPK_Age.put(key, rateTableVOs);
            }
        }

        return rateTableVOs;
    }

    /**
     * Compiled scripts are assumed to be fully-composed where the CSKey must be of type CSKey.BY_COMPANY_STRUCTURE.
     * @param scriptPK
     * @return CompiledScript
     * @throws RuntimeException if key is of the wrong type.
     */
    public final CompiledScript borrowCompiledScript(Long scriptPK, ProductRuleProcessor pr)
    {
        return csProvider.borrowCompiledScript(scriptPK, pr);
    }

    /**
     * This method will only work (properly) with CSKey types of CSKey.BY_COMPANY_STRUCTURE.
     * @param compiledScript
     */
    public final void returnCompiledScript(CompiledScript compiledScript)
    {
        csProvider.returnCompiledScript(compiledScript);
    }

    /**
     * A convenience method to CSProvider.
     * @see CSProvider#addCompiledScript(Long, List<Inst>, TreeMap<String, String>)
     * @param cs
     */
    public final void addCompiledScript(Long scriptPK, ProductRuleProcessor pr, List<Inst> script, TreeMap<String, String> labels)
    {
        csProvider.addCompiledScript(scriptPK, pr, script, labels);
    }
}
