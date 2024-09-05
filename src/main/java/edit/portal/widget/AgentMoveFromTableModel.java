/*
 * AgentMoveFromTableModel.java
 *
 * Created on June 14, 2006, 11:01 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edit.portal.widget;

import fission.global.AppReqBlock;

/**
 * The is a use of two (2) AgentMoveTableModels on the same page. The framework requires a unique
 * identifier based on class name, thus a simple extension is used. 
 * @author gfrosti
 */
public class AgentMoveFromTableModel extends AgentMoveTableModel
{
    /** Creates a new instance of AgentMoveTableModel */
    public AgentMoveFromTableModel(AppReqBlock appReqBlock, String agentName, String agentNumber, String contractCodeCT)
    {
        super(appReqBlock, agentName, agentNumber, contractCodeCT, AgentMoveTableModel.MOVING_FROM);
    }
}
