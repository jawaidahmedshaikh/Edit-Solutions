package engine.sp;

import engine.sp.custom.function.FunctionCommand;

import java.lang.reflect.Constructor;


/**
 * There are certain calculations that have to exist across 
 * multiple entities. For example, summing a PY gross amount.
 * There are a number of ways to do this including retrieving the necessary
 * records and summing them as part of building the Document, or to allow
 * the script writer to sum them oneself. These, however, are deemed inneficient
 * as it requires the db transfer of the target data to the application to do 
 * the summation. Alternatively, one may allow the DB to do it itself. 
 * 
 * There may be other reasons that one chooses to use the Activatefunction as
 * opposed to writing scripts or doing pre-Document processing. As of this writing,
 * there aren't any.
 * 
 * The syntax of the call looks like follows:
 * 
 * activatefunction (SumTrxGrossAmount)
 * or
 * activatefunction (SumTrxNetAmount)
 * 
 * The name of the function corresponds to the name of a class that can 
 * be dynamically loaded and executed. The dynamically loaded class (SumTrxGrossAmount
 * as a possible example) knows what values exist in WS to perform the calculation.
 * 
 * The result of the calculation is to be placed on the Stack.
 * 
 * 
 */
public class Activatefunction extends Inst
{
  public Activatefunction()
  {
  }

  protected void compile(ScriptProcessor sp)
  {
    this.sp = sp;
  }

  /**
   * Identifies the requested function and dynamically isntantiates and
   * executes the corresponding class. 
   * @param sp the ScriptProcessor
   */
  protected void exec(ScriptProcessor sp) throws SPException
  {
    String functionName = getFunctionName();
    
    try
    {
      Constructor constructor = getFunctionClass().getConstructor(new Class[]{ScriptProcessor.class});

      FunctionCommand functionCommand = (FunctionCommand) constructor.newInstance(new Object[]{sp});

      functionCommand.exec();
    }
    catch (Exception e)
    {
      System.out.println(e);
      
      e.printStackTrace();
    
      throw new SPException("An 'activatefunction' was attempted but failed for [" + functionName + "]", SPException.INSTRUCTION_PROCESSING_ERROR, e);
    }
    finally
    {
      sp.incrementInstPtr();
    }
  }
  
  /**
   * Parses the general instruction syntax of:
   * activatefunction (FooFunction) to identify the "FooFunction" as a string.
   * @return the document name
   */
  private String getFunctionName()
  {
    String line = super.getInstAsEntered();

    int openingParenthesis = line.indexOf("(");
    
    int closingParenthesis = line.indexOf(")", openingParenthesis);

    String documentName = line.substring(openingParenthesis + 1, closingParenthesis);

    return documentName;
  }

  /**
   * Returns the targeted Function class by appending the package to the
   * function name. e.g.
   * 
   * "engine.sp.custom.function." + FooFunction where the original 
   * 
   * instruction was entered as:
   * 
   * activatefunction (FooFunction).
   */
  private Class getFunctionClass() throws ClassNotFoundException
  {
    String functionName = getFunctionName();
  
    return Class.forName("engine.sp.custom.function." + functionName);
  }
}
