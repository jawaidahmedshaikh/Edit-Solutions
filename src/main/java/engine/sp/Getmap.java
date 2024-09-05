package engine.sp;

import java.util.Map;

import edit.common.EDITBigDecimal;
import engine.sp.custom.entity.*;
import fission.utility.Util;

import org.dom4j.Element;

/*
 * Engine getmap instruction:
 * 
 * Retrieves value of key (currently on stack) in map (map name provided by the instruction).
 *
 * Use is as follows:
 * 
 * push param:&EDITTrxVO.EDITTrxPK	//This is the key we would like to retrieve a value for
 * getmap (TrxProcessDateMap)		//This provides the name of the map to get the associated value from
 * 
 * Value is pushed onto the stack for use.
 * 
 */
public class Getmap extends Inst
{
    private String[] parameterTokens;
    private String mapName;
    private String key;
    
    public Getmap()
    {
    }

    protected void compile(ScriptProcessor theProcessor)
    {
        super.sp = theProcessor;
    }
    
    /**
     * Returns a stored value to the stack
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

            // Get the mapName and key from parameter tokens
            this.mapName = parameters[0];
            this.key = sp.popFromStack();
            
            // Empty Strings or the reserved word #NULL are treated as a null reference.
            if ((key.toString().length()) == 0 || (key.toString().equals(engine.common.Constants.ScriptKeyword.NULL)))
            {
            	key = null;
            }
            
            // Get value from the datamap stored in sp
            String value = sp.getDataMapValue(mapName, key);
            
            // Return value to script
            sp.push(value.toString());
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();
            
            throw new SPException("Failed to getmap - MapName: " + this.mapName + ", Key: " + key, SPException.PARAMETER_INVALID_ERROR, e);
        }
        finally
        {
            sp.incrementInstPtr();
        }
    }
    
    /**
     * All instructions based on "getmap" take a standard form of:
     * 
     * getmap (foo)
     * 
     * We need to extract all content between the opening "(" and closing ")"
     * which is be referred to as the parameter String.
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
     * getmap (mapName)
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
