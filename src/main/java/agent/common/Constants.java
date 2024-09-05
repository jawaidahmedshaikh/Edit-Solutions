package agent.common;

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
        public static final String PLACED_AGENT_NOT_INDEXED = "PlacedAgent Is Not Yet Indexed And/Or Parent Has Not Been Set.";

        public static final String AGENT_ALREADY_INDEXED = "Agent Already Indexed.";

        public static final String AGENT_LICENSE_DOES_NOT_EXIST_FOR_APPROPRIATE_STATE = "Agent License Does Not Exist For Appropriate State";

        public static final String AGENT_NOT_LICENSED_FOR_PRODUCT = "Agent Not Licensed For Product";

        public static final String AGENT_IS_NOT_ACTIVE = "Agent is not Active";

        public static final String AGENT_LICENSE_HAS_TERMINATED = "Agent License Has Terminated";

        public static final String AGENT_LICENSE_NOT_VALID_FOR_TRX_DATE = "AgentLicense Not Valid For Trx Date";

        public static final String AGENT_HAS_DEPENDENCIES = "Cannot Remove Placed Agent - Snapshot(s) And/Or  History (ies) Exist";

        public static final String AGENT_STOPDATE_HAS_EXPIRED = "The Agent's Stop Date Has Expired";

        public static final String START_DATE_REQUIRED = "PlacedAgent.StartDate Is A Required Field";

        public static final String INVALID_SITUATION = "Invalid Situation - Start/Stop Dates Can Not Overlap For The Same Situation Code";

        public static final String INVALID_AGENTSNAPSHOT = "The AgentSnapshot Is Not Valid - The Situation Has Changed";

        public static final String AGENTSNAPSHOT_HAS_OVERRIDES = "Commission Overrides Exist - Hierarchy Can Not Be Updated";

        public static final String STOP_DATE_LESS_THAN_START_DATE = "Stop Date Less Than Start Date";
    }
}
