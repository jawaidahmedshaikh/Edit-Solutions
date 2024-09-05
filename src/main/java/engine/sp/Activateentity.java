package engine.sp;

import engine.sp.custom.entity.*;

import fission.utility.Util;

import org.dom4j.Element;

/*
 * ScriptProcessor is fundamentally based on the ability to build and navigate
 * a DOM. There are, however, fundamental issues when working with a hierarchical
 * structure, the least of which is the inablity to represent many-to-many
 * associations without performing witchcraft.
 *
 * In contrast to the hierarchical DOM is the relational model which, to date,
 * we have not made available for manipulation within PRASE. The Activateentity
 * instruction changes this by allowing the script writer to map the currently
 * loaded DOM to a relational model via series of entity-operations. Some of those
 * to be:
 *
 * Associate, Create, Retrieve, Update, Delete, Commit.
 *
 * Based on this list, expect the following custom instructions to be:
 *
 * AssociateEntity, CreateEntity, UpdateEntity, DeleteEntity, and CommitEntity.
 *
 * This list will likely grow over time.
 */
public class Activateentity extends Inst
{
    /**
     * The String contents of the sublassed Activateentity instruction.
     * 
     * The first token is always assumed to be the String name of the instruction
     * since the Activeentity(TargetInstruction, a,b...) always begins
     * with the target instruction.
     */
    private String[] parameterTokens;
    
    /**
     * Used to identify the AssociateEntity instruction.
     */
    public static String ASSOCIATE_ENTITY = "Associate";
    
    /**
     * Used to identify the CreateEntity instruction.
     */
    public static String CREATE_ENTITY = "Create";
    
    /**
     * Used to identify the RetrieveEntity instruction.
     */
    public static String RETRIEVE_ENTITY = "Retrieve";
    
    /**
     * Used to identify the UpdateEntity instruction.
     */
    public static String UPDATE_ENTITY = "Update";
    
    /**
     * Used to identify the DeleteEntity instruction.
     */
    public static String DELETE_ENTITY = "Delete";
    
    /**
     * Used to identiy the CommitEntity instructions.
     */
    public static String COMMIT_ENTITY = "Commit";
    
    /**
     * Used to identify the Clear instruction.
     */
    public static String CLEAR_ENTITY = "Clear";
    
    public Activateentity()
    {
    }

    protected void compile(ScriptProcessor theProcessor)
    {
        super.sp = theProcessor;
    }
    
    /**
     * Determines the desired ActivateEntity instruction by examining the
     * first token in the parameter String and then instantiated and
     * delegates to the targeted ActivateEntity instruction.
     * @param execSP
     */
    protected void exec(ScriptProcessor execSP) throws SPException
    {
        sp = execSP;
        
        ActivateEntityCommand targetInstruction = null;
        
        try
        {
            targetInstruction = getTargetInstruction();

            targetInstruction.execute(execSP, getParameterTokens());
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();
            
            String targetInstructionName = (targetInstruction != null)?targetInstruction.getClass().getName():"N/A";

            throw new SPException("An Activateentity instruction was attempted, but failed [targetInstruction =" + targetInstructionName + "]", SPException.PARAMETER_INVALID_ERROR, e);
        }
        finally
        {
            sp.incrementInstPtr();
        }
    }
    
    /**
     * All instructions based on "Activateentity" take a standard form of:
     * 
     * Activateentity(foo ...)
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
     * Most times, the syntax of the insruction resembles:
     * 
     * activateEntity (doFOO,B,C)
     * 
     * In such a case (above), we expect the tokens to be "doFoo", "B", and "C" or 3 tokens.
     * 
     * Some activateEntity instructions have the following syntax:
     * 
     * activateEntity (doFoo, B, C, {a, b, c})
     * 
     * In such a case (above), we expect the tokens to be "doFoo", "B", "C", "{a, b, c}" or 4 tokens.
     *
     * @see #parameterTokens
     * @return
     */
    private String[] getParameterTokens()
    {
        if (parameterTokens == null)
        {
            String parameterString = extractParameterString();        
            
            String subParameterString = null;
            
            int indexOfBracket = parameterString.indexOf("{");

            if (indexOfBracket > 0)
            {
                subParameterString = parameterString.substring(indexOfBracket, parameterString.length());

                parameterString = parameterString.substring(0, indexOfBracket);
            }
            
            parameterTokens = Util.fastTokenizer(parameterString, ",", true);

            if (subParameterString != null)
            {
                // The last token would be an empty String, so let's just replace it with the desired token value.
                parameterTokens[parameterTokens.length - 1] = subParameterString;
            }
        }
        
        return parameterTokens;
    }
    
    /**
     * Gets the targeted Activateentity instruction by examining the first 
     * token of the parameter String.
     * @return
     */
    private ActivateEntityCommand getTargetInstruction()
    {
        String targetInstructionString = getParameterTokens()[0];
        
        ActivateEntityCommand targetInstruction = null;
        
        if (targetInstructionString.equalsIgnoreCase(ASSOCIATE_ENTITY))
        {
            targetInstruction = new AssociateEntity();
        }
        else if (targetInstructionString.equalsIgnoreCase(CREATE_ENTITY))
        {
            targetInstruction = new CreateEntity();
        }
        else if (targetInstructionString.equalsIgnoreCase(RETRIEVE_ENTITY))
        {
            targetInstruction = new RetrieveEntity();            
        }
        else if (targetInstructionString.equalsIgnoreCase(UPDATE_ENTITY))
        {
            targetInstruction = new UpdateEntity();
        }
        else if (targetInstructionString.equalsIgnoreCase(DELETE_ENTITY))
        {
            targetInstruction = new DeleteEntity();
        }
        else if (targetInstructionString.equalsIgnoreCase(COMMIT_ENTITY))
        {
            targetInstruction = new CommitEntity();
        }
        else if (targetInstructionString.equalsIgnoreCase(CLEAR_ENTITY))
        {
            targetInstruction = new ClearEntity();
        }
        
        return targetInstruction;
    }
}
