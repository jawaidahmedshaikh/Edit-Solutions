package event.common;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jun 23, 2003
 * Time: 10:54:35 AM
 * To change this template use Options | File Templates.
 */
public class Constants
{
    public static class TrxCategory
    {
        public static final int DEPOSIT = 0;
        public static final int DISBURSEMENT = 1;
        public static final int TRANSFER = 2;
        public static final int ADJUSTMENT = 3;
    }

    public static class TrxError
    {
        public static final String TRX_BYPASSED = "Transaction(s) Bypassed - Previous Error";

        public static final String TRX_ERRORED = "Transaction Errored";
    }

    public static class TrxStatus
    {
        public static final String NATURAL = "N";
        public static final String PENDING = "P";
        public static final String HISTORY = "H";
        public static final String LOOKUP  = "L";
        public static final String OVERDUE = "O";
        public static final String TERMINATED = "T";
        public static final String WAITING_FOR_UVS = "M";
    }

    public static class RoleType
    {
        public static final String OWNER = "OWN";
        public static final String PAYEE = "PAY";
        public static final String ANNUITANT = "ANN";
        public static final String INSURED = "Insured";
        public static final String PAYOR = "POR";
    }
}
