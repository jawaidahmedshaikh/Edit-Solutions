package edit.common.exceptions;

/**
 * Business-level checked exception for activity related to Billing.
 */
public class EDITBillingException  extends EDITException
{
    /**
     * Constructor.
     */
    public EDITBillingException()
    {

    }

    /**
     * Constructor.
     * @param message
     */
    public EDITBillingException(String message)
    {
        super(message);
    }
}
