/*
 * AgentTran.java      Version 2.00  06/04/2001
 *
 * Copyright (c) 2000 Systems Engineering Group, LLC. All Rights Reserved.
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may not be copied in whole or in part
 * without the written permission of Systems Engineering Group, LLC.
 */

package engine.ui.servlet;

import edit.portal.common.transactions.Transaction;
import fission.global.AppReqBlock;

/**
 * AgentTran is used to handle Agent related requests
 */
public class AgentTran extends Transaction {
    
    // Screen Names
    public static final String AGENTMAIN_SCREEN = "agntmain.htm";
   
    /**
     * Used to execute transaction 
     */
    public String execute(AppReqBlock aAppReqBlock) throws Exception  {
 
        String action = aAppReqBlock.getReqParm("action");

        if ("showAgentMain".equalsIgnoreCase(action))  {
            return AGENTMAIN_SCREEN;
        
        } else {
            throw new Exception("AgentTran: Invalid action " + action);
        }
    }

}