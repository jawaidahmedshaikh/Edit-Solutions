package fission.utility;

import java.util.Collection;

/**
 * Utility methods for common argument validations.
 *
 *<P> Replace <code>if</code> statements at the start of a method with 
 * more compact method calls.
 *
 * @used.By large number of classes in this application.
 * @author <a href="http://www.javapractices.com/">javapractices.com</a>
 */
public final class Args
{

  /**
   * If <code>aObject</code> is null, throw a <code>NullPointerException</code>.
   *
   * <P>Use cases :
  <pre>
   doSomething( SoccerBall aBall ){
     //call some method on the argument : 
     //if aBall is null, then exception is automatically thrown, so 
     //there is no need for an explicit check for null.
     aBall.inflate();
    
     //assign to a corresponding field (common in constructors): 
     //if aBall is null, no exception is immediately thrown, so 
     //an explicit check for null may be useful here
     Args.checkForNull( aBall );
     fBall = aBall;
     
     //passed on to some other method as param : 
     //it may or may not be appropriate to have an explicit check 
     //for null here, according the needs of the problem
     Args.checkForNull( aBall ); //??
     fReferee.verify( aBall );
   }
   </pre>
   */
  public static void checkForNull(Object aObject)
  {
    if (aObject == null)
    {
      throw new NullPointerException();
    }
  }

  /**
   * Throw an <code>IllegalArgumentException</code> if <code>aText</code> does not 
   * satisfy {@link Util#textHasContent}.
   *
   * <P>Most text used in an application is meaningful only if it has visible content.
   */
  public static void checkForContent(String aText)
  {
    if (!Util.textHasContent(aText))
    {
      throw new IllegalArgumentException(Util.getResourceMessage("illegal.argument.exception.1"));
    }
  }

  /**
   * Throw an <code>IllegalArgumentException</code> if {@link Util#isInRange}
   * returns <code>false</code>.
   *
   * @param aLow is less than or equal to <code>aHigh</code>.
   */
  public static void checkForRange(int aNumber, int aLow, int aHigh)
  {
    if (!Util.isInRange(aNumber, aLow, aHigh))
    {
      throw new IllegalArgumentException(aNumber + " " + Util.getResourceMessage("illegal.argument.exception.2") + " " + aLow + ".." + aHigh);
    }
  }

  /**
   * Throw an <code>IllegalArgumentException</code> only if <code>aCollection.isEmpty</code> 
   * returns <code>true</code>.
   */
  public static void checkForEmpty(Collection aCollection)
  {
    if (aCollection.isEmpty())
    {
      throw new IllegalArgumentException(Util.getResourceMessage("illegal.argument.exception.3"));
    }
  }

  /**
   * Often, an argument of a method must be within a specified
   * @param testObject
   * @param testSet
   */
  public static void checkForMembership(Object testObject, Object[] testSet)
  {
    boolean memberOf = false;

    for (int i = 0; i < testSet.length; i++)
    {
      if (testObject.equals(testSet[i]))
      {
        memberOf = true;

        break;
      }
    }

    if (!memberOf)
    {
      String message = "";

      message += testObject.toString() + " " + Util.getResourceMessage("illegal.argument.exception.4");

      message += "[";

      for (int i = 0; i < testSet.length; i++)
      {
        message += testSet[i].toString();

        if (i < (testSet.length - 1))
        {
          message += ", ";
        }
      }

      throw new IllegalArgumentException(Util.getResourceMessage("illegal.argument.exception.4"));
    }
  }
  
  /**
   * A simple wrapper to the {@link String#matches(String)} method with the added 
   * advantage of throwing an appropriate IllegalArgumentException when the
   * match fails.
   * @param testString
   * @param regularExpression
   * @see String#matches(String)
   * @throws IllegalArgumentException if the match fails
   */
  public static void checkForMatch(String testString, String regularExpression)
  {
    boolean matches = testString.matches(regularExpression);    
    
    if (!matches) throw new IllegalArgumentException(Util.getResourceMessage("illegal.argument.exception.5"));
  }
}

