package engine;

import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.common.vo.ProductStructureVO;
import security.Role;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Nov 10, 2003
 * Time: 3:12:16 PM
 * To change this template use Options | File Templates.
 */
public class ProductStructureImpl extends CRUDEntityImpl
{
    protected void save(ProductStructure productStructure)
    {
        super.save(productStructure, ConnectionFactory.ENGINE_POOL, false);
    }

    protected void delete(ProductStructure productStructure)
    {
        super.delete(productStructure, ConnectionFactory.ENGINE_POOL);
    }

    protected void load(ProductStructure productStructure, long pk)
    {
        super.load(productStructure, pk, ConnectionFactory.ENGINE_POOL);
    }
}
