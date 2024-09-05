package engine.common;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jun 23, 2003
 * Time: 10:54:35 AM
 * To change this template use Options | File Templates.
 */
public class Constants
{
    public static class ErrorMsg
    {
        public static final String FILTERED_AREA_IS_ATTACHED = "Area Is Associated With A Filtered Area And Can Not Be Deleted";

        public static final String PRIMARY_AREA_ALREADY_EXISTS = "Primary Area Already Exists";

        public static final String FILTERED_AREA_ALREADY_EXISTS = "Filtered Area Already Exists";

        public static final String DEFAULT_AREA_ENTRY_NOT_FOUND = "Default Area Entry Not Found";

    }

    public static class SuccessMsg
    {
        public static final String AREA_SUCCESSFULLY_SAVED = "Area Successfully Saved";
    }

    /**
     * Error messages relating to ValidateInst errors.
     */
    public static class ValidateErrorMsg
    {
        public static final String DATE_FORMAT_ERROR = "Date Format Error";

        public static final String NUMERIC_EQUALITY_ERROR = "Numeric Equality Error";

        public static final String NUMERIC_INEQUALITY_ERROR = "Numeric Inequality Error";

        public static final String STRING_EQUALITY_ERROR = "Character Equality Error";

        public static final String STRING_INEQUALITY_ERROR = "Character Inequality Error";

        public static final String DATE_EQUALITY_ERROR = "Date Equality Error";

        public static final String DATE_INEQUALITY_ERROR = "Date Inequality Error";

        public static final String UNRECOGNIZED_REQUEST = "The method can not fulfill the request - most likely due to unrecognized method parameter(s).";

        public static final String INVALID_EXPRESSION_ERROR = "The Expression Can Not Be Evaluated";

        public static final String FAIL_MESSAGE = "The Test Succeded When It Should Have Failed";

        public static final String NULL_ERROR = "#NULL Can Not Be Used In This Context";

        public static final String NUMERIC_FORMAT_ERROR = "Numerica Format Error";

        public static final String ABORT_ONE_HARD_VALIDATION_ERROR = "Abort On Hard Validation Error";
    }

    public static class ScriptKeyword
    {
        public static final String NULL = "#NULL";

        public static final String SYSTEM_DATE = "#SYSTEMDATE";

        public static final String SYSTEM_DATE_TIME = "#SYSTEMDATETIME";
        
        public static final String ALL = "#ALL";
        
        public static final String DATEFORMAT = "#DATEFORMAT";
    }
}
