/*
 * User: unknown
 * Date: Jun 4, 2001
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */


package engine.sp;

import edit.common.*;

/**
 * This is the implementation of the Agenextbirthday instruction.
 * This will return the age on the next birthday from the date given. The
 * system will assume that the lesser date is the birthdate.
 * <p>
 * Sample script for Agenextbirthday:
 * push date:1945/01/01
 * push date:1997/07/01
 * agenextbirthday
 */
public final class Agenextbirthday extends Inst {

    /**
	 * Compiles the instruction
	 * <p>
	 * @param aScriptProcessor  Instance of ScriptProcessor
	 */
	 
	protected void compile(ScriptProcessor aScriptProcessor) {
	
	    sp = aScriptProcessor;  // Save instance of ScriptProcessor
		
		// Note: No compiling is required for this instruction
	}
	
	/**
	 *  Execute the instruction
	 *  <p>
	 *  @exception SPException If there is an error while
	 *       executing the instruction
	 */
	protected void exec(ScriptProcessor execSP) throws SPException {

	    sp = execSP;
        //Remove the top two items from the data stack

        EDITDate interimresult = new EDITDate(sp.popFromStack());
        EDITDate interimresult2 = new EDITDate(sp.popFromStack());

//        String type = "next";
        int age = 0;

        //  Determine the greater date
        //  The lesser date is the date of birth, greater date is the date to calculate for
	    if (interimresult.after(interimresult2))
        {
//            age = interimresult.getAge(interimresult2, type);
            age = interimresult2.getAgeAtNextBirthday(interimresult);

	    }
        else
        {
//        	age = interimresult2.getAge(interimresult, type);
            age = interimresult.getAgeAtNextBirthday(interimresult2);
	    }
        
		// move the integer age to result
		String result = age + "";

		// Push the result back onto the data stack
		sp.push(result);
		
		// Increment instruction pointer
		sp.incrementInstPtr();
	}

}