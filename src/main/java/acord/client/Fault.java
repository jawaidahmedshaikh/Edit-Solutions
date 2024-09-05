package acord.client;

public class Fault
{
  /**
   * Suggested values from Acord are:
   * 1. Server.
   * 2. Client.
   */
  private int faultcode;
  
  /**
   * Suggested values from Acord are:
   * 1. unavailable.
   * 2. security.
   * 3. operation_failed.
   * 4. unsupported_operation.
   * 5. unsupported_version.
   * 6. syntax.
   */
  private int faultstring;
  
  private FaultDetail faultDetail;
  
  /**
   * A recommended Acord faultcode.
   */
  public static final int FAULTCODE_SERVER = 0;
  
  /**
   * A recommended Acord faultcode.
   */
  public static final int FAULTCODE_CLIENT = 1;
  
  /**
   * Used for an unknown FAULTCODE.
   */
   public static final int FAULTCODE_UNKNOWN = 2;
  
  /**
   * A recommended Acord faultstring.
   */
  public static final int FAULTSTRING_UNAVAILABLE = 0;
  
  /**
   * A recommended Acord faultstring.
   */
  public static final int FAULTSTRING_SECURITY = 1;
  
  /**
   * A recommended Acord faultstring.
   */  
  public static final int FAULTSTRING_OPERATION_FAILED = 2;
  
  /**
   * A recommended Acord faultstring.
   */  
  public static final int FAULTSTRING_UNSUPPORTED_OPERATION = 3;
  
  /**
   * A recommended Acord faultstring.
   */  
  public static final int FAULTSTRING_UNSUPPORTED_VERSION = 4;
  
  /**
   * A recommended Acord faultstring.
   */  
  public static final int FAULTSTRING_SYNTAX = 5;
  
  /**
   * Used for an unknown FAULTSTRING.
   */
   public static final int FAULTSTRING_UNKNOWN = 6;  
  
  /**
   * An Acord-recommented Fault. It reads:
   * "Message cannot be processed due to unavailability or congestion of the 
   * port invoked". 
   * Predefined faultcodes and faultstrings are used. The user of
   * this Fault is expected to supply the FaultDetail.
   * @see #FAULTCODE_SERVER
   * @see #FAULTSTRING_UNAVAILABLE
   */
  public static final Fault FAULT_00 = new Fault(FAULTCODE_SERVER, FAULTSTRING_UNAVAILABLE);
  
  /**
   * An Acord-recommented Fault. It reads:
   * "Security error at the SOAP envelope level. This code will be used when:
   * The Security Profile is not supported The SOAP envelope has been 
   * altered (integrity checking) The Sender party cannot be authenticated 
   * The SOAP envelope cannot be decrypted.
   * For the Ping operation, the 
   * following error conditions will additionally be reported: 
   * the sender party (id+role) is not authorized as message origin 
   * the receiver party (id+role) is not 
   * authorized as message destination the application is not authorized". 
   * Predefined faultcodes and faultstrings are used. The user of
   * this Fault is expected to supply the FaultDetail.
   * @see #FAULTCODE_SERVER
   * @see #FAULTSTRING_SECURITY
   */
  public static final Fault FAULT_01 = new Fault(FAULTCODE_SERVER, FAULTSTRING_SECURITY);  
  
  /**
   * An Acord-recommented Fault. It reads:
   * "The service operation could not be completed for reasons most probably at
   * the server side."
   * Predefined faultcodes and faultstrings are used. The user of
   * this Fault is expected to supply the FaultDetail.
   * @see #FAULTCODE_SERVER
   * @see #FAULTSTRING_OPERATION_FAILED
   */
  public static final Fault FAULT_02 = new Fault(FAULTCODE_SERVER, FAULTSTRING_OPERATION_FAILED); 
  
  /**
   * An Acord-recommented Fault. It reads:
   * "The client submitted a service operation that is not supported by the server."
   * Predefined faultcodes and faultstrings are used. The user of
   * this Fault is expected to supply the FaultDetail.
   * @see #FAULTCODE_CLIENT
   * @see #FAULTSTRING_UNSUPPORTED_OPERATION
   */
  public static final Fault FAULT_13 = new Fault(FAULTCODE_CLIENT, FAULTSTRING_UNSUPPORTED_OPERATION);    
  
  /**
   * An Acord-recommented Fault. It reads:
   * "The client specified a Messaging Service version that is not supported 
   * by the server."
   * Predefined faultcodes and faultstrings are used. The user of
   * this Fault is expected to supply the FaultDetail.
   * @see #FAULTCODE_CLIENT
   * @see #FAULTSTRING_UNSUPPORTED_VERSION
   */
  public static final Fault FAULT_14 = new Fault(FAULTCODE_CLIENT, FAULTSTRING_UNSUPPORTED_VERSION);     
  
  /**
   * An Acord-recommented Fault. It reads:
   * "Service request message is invalid."
   * Predefined faultcodes and faultstrings are used. The user of
   * this Fault is expected to supply the FaultDetail.
   * @see #FAULTCODE_CLIENT
   * @see #FAULTSTRING_SYNTAX
   */
  public static final Fault FAULT_15 = new Fault(FAULTCODE_CLIENT, FAULTSTRING_SYNTAX);    
  
  /**
   * Predefined faultcode (uknown) and faultstrings (unknown) are used. The user of
   * this Fault is expected to supply the FaultDetail.
   * @see #FAULTCODE_SERVER
   * @see #FAULTSTRING_UNAVAILABLE
   */
  public static final Fault FAULT_26 = new Fault(FAULTCODE_UNKNOWN, FAULTSTRING_UNKNOWN);   

  /**
   * Constructor.
   * @param faultcode
   * @param faultstring
   */
  public Fault(int faultcode, int faultstring)
  {
    this(faultcode, faultstring, null);
  }

  /**
   * Constructor.
   * @param faultcode
   * @param faultstring
   * @param faultDetail
   */
  public Fault(int faultcode, int faultstring, FaultDetail faultDetail)
  {
    this.faultcode = faultcode;
    
    this.faultstring = faultstring;
    
    this.faultDetail = faultDetail;
  }

  /**
   * Setter.
   * @param faultcode
   */
  public void setFaultcode(int faultcode)
  {
    this.faultcode = faultcode;
  }

  /**
   * Getter.
   * @return
   */
  public int getFaultcode()
  {
    return faultcode;
  }

  public void setFaultstring(int faultstring)
  {
    this.faultstring = faultstring;
  }

  public int getFaultstring()
  {
    return faultstring;
  }

  public void setFaultDetail(FaultDetail faultDetail)
  {
    this.faultDetail = faultDetail;
  }

  public FaultDetail getFaultDetail()
  {
    return faultDetail;
  }
}
