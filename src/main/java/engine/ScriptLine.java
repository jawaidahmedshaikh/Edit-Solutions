package engine;

import edit.services.db.hibernate.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

/*
 * User: cgleason
 * Date: Mar 3, 2006
 * Time: 4:21:08 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */
public class ScriptLine extends HibernateEntity
{
    private Long scriptLinePK;
    private int lineNumber;
    private String scriptLine;
    private Long scriptFK;

    private Script script;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.ENGINE;
    

    /**
     * getter
     * @return
     */
    public Long getScriptLinePK()
    {
        return scriptLinePK;
    }

    /**
     * setter
     * @param scriptLinePK
     */
    public void setScriptLinePK(Long scriptLinePK)
    {
        this.scriptLinePK = scriptLinePK;
    }

    /**
     * getter
     * @return
     */
    public int getLineNumber()
    {
        return lineNumber;
    }

    /**
     * setter
     * @param lineNumber
     */
    public void setLineNumber(int lineNumber)
    {
        this.lineNumber = lineNumber;
    }

    /**
     * getter
     * @return
     */
    public String getScriptLine()
    {
        return scriptLine;
    }

    /**
     * setter
     * @param scriptLine
     */
    public void setScriptLine(String scriptLine)
    {
        this.scriptLine = scriptLine;
    }

    /**
     * getter
     * @return
     */
    public Long getScriptFK()
    {
        return scriptFK;
    }

    public void setScriptFK(Long scriptFK)
    {
        this.scriptFK = scriptFK;
    }
    /**
     * Getter
     * @return
     */
    public Script getScript()
    {
        return script;
    }

    /**
     * Setter
     * @param script
     */
    public void setScript(Script script)
    {
        this.script = script;
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
        return ScriptLine.DATABASE;
    }

    /**
     * Finds all script lines by scriptPK ordered by line number.
     * @param scriptPK
     * @return
     */
    // Though we can use script.getScriptLines() since we are using Sets the script lines are not ordered.
    public static final ScriptLine[] findScriptLinesByScriptPK(Long scriptPK)
    {
        String hql = " select scriptLine from ScriptLine scriptLine" +
                     " where ScriptFK = :scriptPK" +
                     " order by LineNumber";

        Map params = new HashMap();
        params.put("scriptPK", scriptPK);

        List results = SessionHelper.executeHQL(hql, params, ScriptLine.DATABASE);

        return (ScriptLine[]) results.toArray(new ScriptLine[results.size()]);
    }
}
