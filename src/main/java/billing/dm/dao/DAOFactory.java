package billing.dm.dao;

import java.io.Serializable;

import billing.dm.dao.BillScheduleDAO;

public class DAOFactory implements Serializable {

//******************************
//          Variables
//******************************

    private static BillScheduleDAO              billScheduleDAO;

    static {

    	billScheduleDAO             = new BillScheduleDAO();
        
    }

    public static BillScheduleDAO getBillScheduleDAO()
    {
        return billScheduleDAO;
    }

}
