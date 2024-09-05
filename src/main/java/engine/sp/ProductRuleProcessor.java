/*
 * ProductRuleProcessor.java      Version 1.00  06/01/2000
 *
 * Copyright (c) 2000 CalcEngs Engineering Group, LLC. All Rights Reserved.
 *
 * This program is the confidential and proprietary information of
 * Systems Engineering Group, LLC and may not be copied in whole or in part
 * without the written permission of Systems Engineering Group, LLC.
 */

package engine.sp;

import edit.common.vo.RulesVO;
import engine.dm.dao.FastDAO;

import java.io.Serializable;

/**
 * The ProductRule request controller
 */
public class ProductRuleProcessor implements Serializable
{

    private String processName = null;
    private String event = null;
    private String eventType = null;
    private String effectiveDate = null;

    private long productStructurePK;

    private RulesVO[] candidateRulesVOs;

    public ProductRuleProcessor()
    {

        super();
    }

    public long getProductStructurePK()
    {
        return productStructurePK;
    }

    public String getProcessName()
    {

        return processName;
    }

    public void setProcessName(String newValue)
    {

        processName = newValue;
    }

    public String getEventName()
    {

        return event;
    }

    public void setEvent(String newValue)
    {

        event = newValue;
    }

    public String getEventTypeName()
    {

        return eventType;
    }

    public void setEventType(String newValue)
    {

        eventType = newValue;
    }

    public String getEffectiveDate()
    {

        return effectiveDate;
    }

    public void setEffectiveDate(String newValue)
    {

        effectiveDate = newValue;
    }

    public void setProductStructurePK(long productStructurePK)
    {

        this.productStructurePK = productStructurePK;
    }

    public RulesVO getBestMatchScriptId(String ruleName)
    {

        return getBestMatchRule(ruleName);
    }

    public RulesVO getBestMatchTableId(String ruleName)
    {
        RulesVO rulesVO = getBestMatchRule(ruleName);

        return rulesVO;
    }

    private RulesVO getBestMatchRule(String ruleName)
    {
        RulesVO bestMatchRulesVO = null;
        if (candidateRulesVOs == null)
        {
            candidateRulesVOs = new FastDAO().findByProductStructureEffectiveDate(productStructurePK, effectiveDate);
        }
		
        if (candidateRulesVOs != null)
        {
            bestMatchRulesVO = new KeyMatcher().getBestMatch(ruleName, processName, event, eventType, candidateRulesVOs);
        }

        return bestMatchRulesVO;
    }
    
    public RulesVO getBestMatchTableId(String ruleName, String effectiveDate)
    {
        RulesVO rulesVO = getBestMatchRule(ruleName, effectiveDate);

        return rulesVO;
    }

    private RulesVO getBestMatchRule(String ruleName, String effectiveDate)
    {
        RulesVO bestMatchRulesVO = null;
        
        RulesVO[] candidateRulesVOs = null;
            
        candidateRulesVOs = new FastDAO().findByProductStructureEffectiveDate(productStructurePK, effectiveDate);

        //when the effective date is passed in a new set of candidate rules is needed.  The current ones are stored, then reset.
        RulesVO[] storeCurrentCandidateRules = candidateRulesVOs;

        candidateRulesVOs = new FastDAO().findByProductStructureEffectiveDate(productStructurePK, effectiveDate);

        if (candidateRulesVOs != null)
        {
            bestMatchRulesVO = new KeyMatcher().getBestMatch(ruleName, processName, event, eventType, candidateRulesVOs);
        }

        candidateRulesVOs = storeCurrentCandidateRules;

        return bestMatchRulesVO;
    }

    public void clearProductRuleProcessorValues()
    {
        processName = null;
        event = null;
        eventType = null;
        effectiveDate = null;
        productStructurePK = 0;
        candidateRulesVOs = null;
    }
}
