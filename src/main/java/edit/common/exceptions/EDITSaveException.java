package edit.common.exceptions;

/**
 * Generic exception thrown, for example, when Hibernate is attempting to
 * save a Hibernate Entity, but violates some validation rules.
 */
public class EDITSaveException extends EDITException
{
  public EDITSaveException()
  {
    super();
  }

}
