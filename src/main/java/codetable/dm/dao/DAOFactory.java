/*
 * DAOFactory.java      Version 2.0  05/18/2004

 *
 * (c) 2000-2004 Systems Engineering Group, LLC. All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential. Any use is
 * subject to the license agreement.
 */

package codetable.dm.dao;

import java.io.Serializable;

/**
 *  This class follows the Factory pattern by building individual Data Access Objects (DAO)
 *  For example: The method getClientDetailDAO returns a ClientDetailDAO object.
 *
 */
public class DAOFactory implements Serializable {

//*******************************
//          Variables
//*******************************

 	private static CodeTableDefDAO codeTableDefDAO;
    private static CodeTableDAO codeTableDAO;
    private static FilteredCodeTableDAO filteredCodeTableDAO;
    private static OnlineReportDAO onlineReportDAO;
    private static FilteredOnlineReportDAO filteredOnlineReportDAO;

    static {

        codeTableDefDAO  = new CodeTableDefDAO();
        codeTableDAO     = new CodeTableDAO();
        filteredCodeTableDAO = new FilteredCodeTableDAO();
        onlineReportDAO  = new OnlineReportDAO();
        filteredOnlineReportDAO = new FilteredOnlineReportDAO();
    }

//*******************************
//          Public Methods
//*******************************

	/**
	 * Factory Method
	 *
	 * @return CodeTableDefDAO
	 */
	public static CodeTableDefDAO getCodeTableDefDAO() {

		return codeTableDefDAO;
    }

    public static CodeTableDAO getCodeTableDAO()
    {
        return codeTableDAO;
    }

    public static FilteredCodeTableDAO getFilteredCodeTableDAO()
    {
        return filteredCodeTableDAO;
    }

    public static OnlineReportDAO getOnlineReportDAO()
    {
        return onlineReportDAO;
    }

    public static FilteredOnlineReportDAO getFilteredOnlineReportDAO()
    {
        return filteredOnlineReportDAO;
    }
}

