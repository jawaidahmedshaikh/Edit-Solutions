/*
 * Gettable.java      06/04/2001
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */

package engine.sp;

import edit.common.vo.RulesVO;
import edit.common.vo.TableDefVO;
import engine.dm.dao.DAOFactory;
import fission.utility.*;

/**
 * This is the implementation for the Gettable instruction.
 * The Gettable instruction is used to get the table for the
 * rule specified.Place the name retrieved into ws under the
 * rule name.
 *
 * Ex.
 * 
 *	rule = Expense1
 *	tablename = Per1000Expense
 *
 *	ws = Expense1,string=Per1000Expense
 *
 */
public final class Gettable extends InstOneOperand {

    /**
     * Gettable constructor
     * <p> 
     * @exception SPException  
     */ 
     public Gettable() throws SPException {
    
        super(); 
     }
     
    /**
     * Compiles the instruction
     * <p>
     * @param aScriptProcessor  Instance of ScriptProcessor
     */
    protected void compile(ScriptProcessor aScriptProcessor) throws SPException{
    
        sp = aScriptProcessor;  //Save instance of ScriptProcessor
   
        //Does common tasks in superclass such as
        //setting member variables and some operand editting
        commonCompileTasks();
      
    }


    /**
     *  Executes the instruction
     *  <p>
     *  @exception SPException If there is an error while 
     *       executing the instruction
     */
   	protected void exec(ScriptProcessor execSP) throws SPException {
	
        sp = execSP;
		
        String ruleName = this.getOperand();
        
        if (ruleName.startsWith("ws:")) {
            int colonIndex = ruleName.indexOf(":");
            String operandDataName = ruleName.substring(colonIndex + 1);
            ruleName = (String) sp.getWSEntry(operandDataName);
        } else {
        	ruleName = extractOperandDataName();
        }
        
        //Rate tables need to use the contract EffectiveDate in the BestMatch Process
		String dateTypeAccessInd = Util.initString((String) sp.getWSEntry("DateTypeAccessInd"), "N");
        String effectiveDate = null;

		//The instance of pr has the csid and rsid
		//send in rule name	to get tableid returned
        RulesVO rulesVO = null;
        if (dateTypeAccessInd.equalsIgnoreCase("N"))
        {
            rulesVO = sp.getProductRule().getBestMatchTableId(ruleName);
        }
        else
        {
            effectiveDate =  (String) sp.getWSEntry("ContractEffectiveDate");
            rulesVO = sp.getProductRule().getBestMatchTableId(ruleName, effectiveDate);
        }
        long tableId = rulesVO.getTableDefFK();

		TableDefVO tableDefVO = CSCache.getCSCache().getTableDefVOBy_TableDefPK(tableId);
		
		String tableName = tableDefVO.getTableName();
		
        // Place rule name and table name in ws
        sp.addWSEntry(ruleName, tableName);

		// Put table name onto the stack
		sp.push(tableName);

        // Increment instruction pointer
        sp.incrementInstPtr();

    }
}