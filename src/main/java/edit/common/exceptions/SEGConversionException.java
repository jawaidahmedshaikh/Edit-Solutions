package edit.common.exceptions;

public class SEGConversionException extends Exception
{
    public SEGConversionException()
    {
    }
    
    public SEGConversionException(Exception e)
    {
        super(e);
    }
    
    public SEGConversionException(String message)
    {
        super(message);
    }
}
