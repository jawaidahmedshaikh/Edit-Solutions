package edit.common.exceptions;

import edit.common.EDITList;


public class EDITRuntimeException extends RuntimeException
{
  /**
   * The ordered list of messages.
   */
  private EDITList messageList = new EDITList();

  public EDITRuntimeException()
  {
    super();
  }

  public EDITRuntimeException(String message)
  {
    super(message);
    
    getMessageList().addTo(message);
  }
  
  /**
   * The List of validation messages. The user of this class can add a 
   * message to the message list by calling:
   * getMessageList().add("the new message").
   * @return List
   */
  public EDITList getMessageList()
  {
    return messageList;
  }
}
