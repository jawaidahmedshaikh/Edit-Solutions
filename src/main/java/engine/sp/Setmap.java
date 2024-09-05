package engine.sp;

import java.util.Map;

import edit.common.EDITBigDecimal;
import engine.sp.custom.entity.*;
import fission.utility.Util;

import org.dom4j.Element;

/*
 * Engine setmap instruction:
 * 
 * Stores value (currently on stack) to key (currently on stack) in map (map name provided by the instruction).
 * If the map doesn't yet exist, it is automatically created.
 *
 * Use is as follows:
 * 
 * push param:&EDITTrxVO.EDITTrxPK	//This is the key
 * push ws:ProcessDate				//This is the value to store
 * setmap (TrxProcessDateMap)		//This provides the name of the map to store the previous key/value pair
 *  
 */
public class Setmap extends Inst
{
    private String[] parameterTokens;
    
    private String mapName;
    private String key;
    private String value;
    
    public Setmap()
    {
    }

    protected void compile(ScriptProcessor theProcessor)
    {
        super.sp = theProcessor;
    }
    
    /**
     * Pops the key/value pair stored on the stack and stores them in the dataMap
     * with name provided in the instruction
     * 
     * @param execSP
     */
    protected void exec(ScriptProcessor execSP) throws SPException
    {
        sp = execSP;
                
        try
        {            
            String[] parameters = getParameterTokens();
            
            sp = execSP;

            // Get the mapName, key and value from parameter tokens
            this.mapName = parameters[0];
            
            this.value = sp.popFromStack();
            this.key = sp.popFromStack();
            
            // Empty Strings or the reserved word #NULL are treated as a null reference.
            if ((key.toString().length()) == 0 || (key.toString().equals(engine.common.Constants.ScriptKeyword.NULL)))
            {
            	key = null;
            }
            
            if ((value.toString().length()) == 0 || (value.toString().equals(engine.common.Constants.ScriptKeyword.NULL)))
            {
                value = null;
            }
            
            // Set values to the datamap stored in sp ... if map doesn't already exist, it is created
            sp.setDataMapValue(mapName, key, value);            
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();
            
            throw new SPException("Failed to setmap - MapName: " + this.mapName + ", Key: " + this.key + ", Value: " + this.value, SPException.PARAMETER_INVALID_ERROR, e);
        }
        finally
        {
        	// Increment instruction pointer
            sp.incrementInstPtr();
        }
    }
    
    /**
     * All instructions based on "setmap" take a standard form of:
     * 
     * setmap(foo ...)
     * 
     * We need to extract all content between the opening "(" and closing ")"
     * which is be referred to as the parameter String.
     * 
     * Currently the only parameter is the map name
     * 
     * @return
     */
    private String extractParameterString()
    {
        String parameterString = super.getInstAsEntered();

        int openingParenthesis = parameterString.indexOf("(");
        
        int closingParenthesis = parameterString.indexOf(")", openingParenthesis);

        parameterString = parameterString.substring(openingParenthesis + 1, closingParenthesis);
        
        return parameterString;
    }     
    
    /**
     * Tokenizes the parameter String.
     * 
     * Syntax of the instruction resembles:
     * 
     * setmap (mapName)
     *
     * @see #parameterTokens
     * @return
     */
    private String[] getParameterTokens()
    {
        if (parameterTokens == null)
        {
            String parameterString = extractParameterString();        
            
            /*
            //Commented these parts out until additional parameters are supplied by the instruction
            
            String subParameterString = null;
            
            int indexOfBracket = parameterString.indexOf("{");

            if (indexOfBracket > 0)
            {
                subParameterString = parameterString.substring(indexOfBracket, parameterString.length());

                parameterString = parameterString.substring(0, indexOfBracket);
            }
            */
            parameterTokens = Util.fastTokenizer(parameterString, ",", true);

            /*if (subParameterString != null)
            {
                // The last token would be an empty String, so let's just replace it with the desired token value.
                parameterTokens[parameterTokens.length - 1] = subParameterString;
            }*/
        }
        
        return parameterTokens;
    }
}
