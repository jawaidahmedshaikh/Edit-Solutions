/*
 * KeyMatcher.java      Version 1.1  07/26/2001
 * 
 * Copyright (c) 2001 Systems Engineering Group, LLC. All Rights Reserved.
 * 
 * 
 * This program is the confidential and proprietary information of 
 * Systems Engineering Group, LLC and may not be copied in whole or in part 
 * without the written permission of Systems Engineering Group, LLC.
 */

package engine.sp;

import edit.common.vo.RulesVO;
import fission.dm.valueobject.ValueObject;

/**
 * This class uses a weighting algorithm to find the best possible
 * key match. The best match comes from supplying the algorithm two
 * things:
 *
 * 1. The driving ProductStructureKey (determined by the driving
 *    ProductRule Structure, and
 *
 * 2. A Pool of candidate ProductStructureKeys.
 *
 * The algorithm weights each candidate key against the driving key
 * to determine the beest possible fit.
 * 
 */
public class KeyMatcher  {

//*******************************      
//          Variables            
//*******************************


    private static final int WEIGHT_LEVEL_4 = 43;
	private static final int WEIGHT_LEVEL_3 = 7;
	private static final int WEIGHT_LEVEL_2 = 3;
	private static final int WEIGHT_LEVEL_1 = 2;
	private static final int WEIGHT_LEVEL_0 = 0;
	
	private static final int WEIGHT_LEVEL_WILD_CARD_MATCH = 1; 
	
	
//*******************************      
//          Public Methods
//*******************************

	/**
	 * Returns the best matching ProductStructureKey using a weighted algorithm
	 *
	 * @param drivingKey Attained from the driving ProductRuleStructure
	 * @param keyPool candidate keys to weight
	 * @return the best matching ProductStructureKey
	 * @see ProductRuleStructure
	 * @see ProductStructureKey
	 */
	public synchronized RulesVO getBestMatch(String ruleName, String processName, String event, String eventType, RulesVO[] candidateRulesVOs) {
	
		int bestWeight = 0;
        int newWeight  = 0;
		RulesVO bestMatchRulesVO  = null;
	
		for (int index = 0; index < candidateRulesVOs.length; index++) {
		
			newWeight = weightKey(ruleName, processName, event, eventType, candidateRulesVOs[index]);

            if (newWeight > 0) {

                if (bestWeight <= newWeight) {

                    bestWeight = newWeight;
                    bestMatchRulesVO = candidateRulesVOs[index];
                }
            }
		}
		
		return bestMatchRulesVO;
	}
	

//*******************************      
//          Private Methods
//*******************************

	/**	
	 * Weights a single, candidateKey against the drivingKey
	 * 
	 * @param drivingKey comes from the driving ProductStructure
	 * @param candidateKey
	 * @return the weighted key
	 */
	private synchronized int weightKey(String ruleName, String processName, String event, String eventType,
 	    						  RulesVO candidateRulesVO) {
		int keyWeight = 1;

		keyWeight *= weightParam(ruleName,
								 candidateRulesVO.getRuleName(),
								 WEIGHT_LEVEL_4);

		keyWeight *= weightParam(processName,
								 candidateRulesVO.getProcessName(),
								 WEIGHT_LEVEL_3);

		keyWeight *= weightParam(event,
								 candidateRulesVO.getEventName(),
								 WEIGHT_LEVEL_2);
		
		keyWeight *= weightParam(eventType,
								 candidateRulesVO.getEventTypeName(),
								 WEIGHT_LEVEL_1);
		
		return keyWeight;
	}
	
	
	/**
	 * Weights a single param of the candidateKey against the drivingKey
	 * 
	 * @param drivingParam
	 * @param candidateParam
	 * @param weightLevel the current weight level (there are 5)
	 * @return the weight value of the candidateParam
	 */
	private synchronized int weightParam(String drivingParam,
								   String candidateParam,
								   int weightLevel) {
	
		if (drivingParam.equals(candidateParam)) {
			
			return weightLevel;
		}
		else if (candidateParam.equals(ValueObject.DEFAULT_CHAR)) {
		
			return WEIGHT_LEVEL_WILD_CARD_MATCH;
		}
		else {
		
			return WEIGHT_LEVEL_0;
		}
	}
}