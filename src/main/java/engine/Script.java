package engine;

import edit.services.db.hibernate.*;
import edit.common.*;
import edit.common.vo.*;

import java.util.*;

import engine.dm.dao.*;

/*
 * User: cgleason
 * Date: Mar 3, 2006
 * Time: 3:47:13 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */
public class Script extends HibernateEntity
{
    private Long scriptPK;
    private String scriptName;
    private String scriptStatusCT;
    private String scriptTypeCT;
    private EDITDateTime maintDateTime;
    private String operator;
    private ScriptLine scriptLine;

    private Set scriptLines = new HashSet();

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.ENGINE;
    

    public Script()
    {
        this.maintDateTime = new EDITDateTime();
    }

    public Long getScriptPK()
    {
        return scriptPK;
    }

    public void setScriptPK(Long scriptPK)
    {
        this.scriptPK = scriptPK;
    }

    public String getScriptName()
    {
        return scriptName;
    }

    public void setScriptName(String scriptName)
    {
        this.scriptName = scriptName;
    }

    public String getOperator()
    {
        return operator;
    }

    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public EDITDateTime getMaintDateTime()
    {
        return maintDateTime;
    }

    public void setMaintDateTime(EDITDateTime maintDateTime)
    {
        this.maintDateTime = maintDateTime;
    }

    public String getScriptTypeCT()
    {
        return scriptTypeCT;
    }

    public void setScriptTypeCT(String scriptTypeCT)
    {
        this.scriptTypeCT = scriptTypeCT;
    }

    /**
     * getter
     * @return
     */
    public String getScriptStatusCT()
    {
        return scriptStatusCT;
    }


    /**
     * getter
     */
    public ScriptLine getScriptLine()
    {
        return scriptLine;
    }

    /**
     * setter
     */
    public void setScriptLine(ScriptLine scriptLine)
    {
        this.scriptLine = scriptLine;
    }

   /**
     * Getter.
     * @return
     */
    public Set getScriptLines()
    {
        return scriptLines;
    }

    /**
     * Setter.
     * @param scriptLines
     */
    public void setScriptLines(Set scriptLines)
    {
        this.scriptLines = scriptLines;
    }

    /**
     * Adder.
     * @param contributingProduct
     */
    public void add(ScriptLine scriptLine)
    {
        getScriptLines().add(scriptLine);

        scriptLine.setScript(this);
    }

    public void setScriptStatusCT(String scriptStatusCT)
    {
        this.scriptStatusCT = scriptStatusCT;
    }

   /**
     * @see HibernateEntity#hSave()
     */
    public void hSave()
    {
    }

   /**
     * @see HibernateEntity#hDelete()
     */
    public void hDelete()
    {
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return Script.DATABASE;
    }

    public static Script[] findAll()
    {
        String hql = "from Script";

        List results = SessionHelper.executeHQL(hql, null, Script.DATABASE);

        return (Script[]) results.toArray(new Script[results.size()]);
    }

    /**
     * Finder by PK.
     * @param scriptPK
     * @return
     */
    public static final Script findByPK(Long scriptPK)
    {
        return (Script) SessionHelper.get(Script.class, scriptPK, Script.DATABASE);
    }

    public static ScriptVO findByPartialScriptName(String scriptFilter)
    {
        ScriptVO[] scriptVOs = new ScriptDAO().findByPartialScriptName(scriptFilter);

        ScriptVO scriptVO = null;
        if (scriptVOs != null)
        {
            scriptVO = scriptVOs[0];
        }

        return scriptVO;
    }

    public static final Script findByPKWithScriptLine(Long scriptPK)
    {
        Script script = null;

        String hql = " select script from Script script " +
                     " join fetch script.ScriptLines scriptLine" +
                     " where script.ScriptPK = :scriptPK" +
                     " order by scriptLine.LineNumber";

        Map params = new HashMap();
        params.put("scriptPK", scriptPK);

        List results = SessionHelper.executeHQL(hql, params, Script.DATABASE);

        if (!results.isEmpty())
        {
            script = (Script) results.get(0);
        }

        return script;

    }
    
    /**
     * Finds the Script by the specified Script Name join/fetched with the Script Lines, and
     * orders them by line number.
     */
    public static Script findBy_ScriptName_V1(String scriptName)
    {
        Script script = null;
        
        String hql = " from Script script" +
                    " join fetch script.ScriptLines scriptLine" +
                    " where script.ScriptName = :scriptName" +
                    " order by scriptLine.LineNumber asc";
        
        EDITMap params = new EDITMap("scriptName", scriptName);
        
        List<Script> results = SessionHelper.executeHQL(hql, params, Script.DATABASE);
        
        if (!results.isEmpty())
        {
            script = results.get(0);
        }
        
        return script;
    }
}

