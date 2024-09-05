/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Oct 24, 2003
 * Time: 3:53:52 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package agent.dm.composer;

import edit.common.vo.CommissionLevelDescriptionVO;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;

import java.util.List;

public class CommissionLevelDescriptionComposer  extends Composer {

    private List voInclusionList;

    private CRUD crud;

    public CommissionLevelDescriptionComposer(List voInclusionList) {

        this.voInclusionList = voInclusionList;
    }

    public CommissionLevelDescriptionVO compose(long commissionLevelDescriptionPK) throws Exception {

        CommissionLevelDescriptionVO commissionLevelDescriptionVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            commissionLevelDescriptionVO = (CommissionLevelDescriptionVO) crud.retrieveVOFromDB(CommissionLevelDescriptionVO.class, commissionLevelDescriptionPK);
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }

        return commissionLevelDescriptionVO;
    }
}
