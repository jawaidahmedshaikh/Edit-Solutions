package engine;

import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Nov 17, 2003
 * Time: 4:17:49 PM
 * To change this template use Options | File Templates.
 */
public class FilteredFundImpl extends CRUDEntityImpl
{
    protected void load(FilteredFund filteredFund, long pk) throws Exception
    {
        super.load(filteredFund, pk, ConnectionFactory.ENGINE_POOL);
    }
}
