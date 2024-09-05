package edit.common.vo.user;

import edit.common.vo.RulesVO;
import edit.common.vo.ScriptVO;
import edit.common.vo.TableDefVO;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: May 6, 2003
 * Time: 2:24:55 PM
 * To change this template use Options | File Templates.
 */
public class UIRulesVO implements Comparable
{

    public RulesVO getRulesVO()
    {
        return rulesVO;
    }

    public void setRulesVO(RulesVO rulesVO)
    {
        this.rulesVO = rulesVO;
    }

    public ScriptVO getScriptVO()
    {
        return scriptVO;
    }

    public void setScriptVO(ScriptVO scriptVO)
    {
        this.scriptVO = scriptVO;
    }

    public TableDefVO getTableDefVO()
    {
        return tableDefVO;
    }

    public void setTableDefVO(TableDefVO tableDefVO)
    {
        this.tableDefVO = tableDefVO;
    }

    private RulesVO rulesVO;
    private ScriptVO scriptVO;
    private TableDefVO tableDefVO;

    public int compareTo(Object o)
    {
        UIRulesVO uiRulesVO = (UIRulesVO) o;

        String incomingRuleName = uiRulesVO.getRulesVO().getRuleName();

        return getRulesVO().getRuleName().compareTo(incomingRuleName);
    }
}
