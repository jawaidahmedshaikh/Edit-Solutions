package event.common;

import edit.common.vo.TransactionPriorityVO;
import event.dm.dao.DAOFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Aug 14, 2003
 * Time: 2:08:00 PM
 * To change this template use Options | File Templates.
 */
public class TransactionPriorityCache
{
    private Map transactionPriorities;
    private static TransactionPriorityCache transactionPriorityCache;

    private TransactionPriorityCache() throws Exception
    {
        this.transactionPriorities = new HashMap();

        TransactionPriorityVO[] transactionPriorityVO = DAOFactory.getTransactionPriorityDAO().findAll();

        for (int i = 0; i < transactionPriorityVO.length; i++)
        {
            transactionPriorities.put(transactionPriorityVO[i].getTransactionTypeCT(), new Integer(transactionPriorityVO[i].getPriority()));
        }
    }

    public static TransactionPriorityCache getInstance()
    {
        try
        {
            if (transactionPriorityCache == null)
            {
                transactionPriorityCache = new TransactionPriorityCache();
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            throw new RuntimeException(e.getMessage());
        }

        return transactionPriorityCache;
    }

    public int getPriority(String transactionTypeCT)
    {
        Integer priority = (Integer) transactionPriorities.get(transactionTypeCT);

        return priority.intValue();
    }

    public void reload()
    {
        try
        {
            transactionPriorityCache = new TransactionPriorityCache();

        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
