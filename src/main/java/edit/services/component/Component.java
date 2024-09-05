package edit.services.component;



/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Jan 20, 2004
 * Time: 11:38:17 AM
 * To change this template use Options | File Templates.
 */
public interface Component
{
    public static final String[] COMPONENT_CATEGORIES = {"ACCOUNTING",          // 0
                                                            "AGENT",            // 1
                                                            "BATCH",            // 2
                                                            "CLIENT",           // 3
                                                            "EVENT",            // 4
                                                            "INFORCE",          // 5
                                                            "NEWBUSINESS",      // 6
                                                            "PRODUCTBUILD",     // 7
                                                            "SECURITY",         // 8
                                                            "REINSURANCE",      // 9
                                                            "RMD",              // 10
                                                            "STAGING",          // 11
                                                            "CASETRACKING",     // 12
                                                            "CASE"};            // 13

    public static final String ACCOUNTING = COMPONENT_CATEGORIES[0];
    public static final String AGENT = COMPONENT_CATEGORIES[1];
    public static final String BATCH = COMPONENT_CATEGORIES[2];
    public static final String CLIENT = COMPONENT_CATEGORIES[3];
    public static final String EVENT = COMPONENT_CATEGORIES[4];
    public static final String INFORCE = COMPONENT_CATEGORIES[5];
    public static final String NEWBUSINESS = COMPONENT_CATEGORIES[6];
    public static final String PRODUCTBUILD = COMPONENT_CATEGORIES[7];
    public static final String SECURITY = COMPONENT_CATEGORIES[8];
    public static final String REINSURANCE = COMPONENT_CATEGORIES[9];
    public static final String RMD = COMPONENT_CATEGORIES[10];
    public static final String STAGING = COMPONENT_CATEGORIES[11];
    public static final String CASETRACKING = COMPONENT_CATEGORIES[12];
    public static final String CASE = COMPONENT_CATEGORIES[13];
}
