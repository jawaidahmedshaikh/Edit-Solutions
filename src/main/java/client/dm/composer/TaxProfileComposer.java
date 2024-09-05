package client.dm.composer;

import client.dm.dao.DAOFactory;
import edit.common.vo.TaxProfileVO;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Aug 6, 2003
 * Time: 10:08:47 AM
 * To change this template use Options | File Templates.
 */
public class TaxProfileComposer extends Composer
{
    private List voInclusionList = null;

    private CRUD crud = null;

    public TaxProfileComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public TaxProfileVO compose(long taxProfilePK) throws Exception
    {
        TaxProfileVO taxProfileVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            taxProfileVO = (TaxProfileVO) crud.retrieveVOFromDB(TaxProfileVO.class, taxProfilePK);

            compose(taxProfileVO);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            throw e;
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }

        return taxProfileVO;
    }

    public TaxProfileVO composePrimaryTaxProfile(long taxInformationPK) throws Exception
    {
        TaxProfileVO[] taxProfileVO = null;

        try
        {
            taxProfileVO = DAOFactory.getTaxProfileDAO().findPrimaryByTaxInformationPK(taxInformationPK, false, null);

            if (taxProfileVO != null && taxProfileVO.length > 0)
            {
                compose(taxProfileVO[0]);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            throw e;
        }

        if (taxProfileVO != null && taxProfileVO.length > 0)
        {
            return taxProfileVO[0];
        }
        else
        {
            return null;
        }
    }

    public void compose(TaxProfileVO taxProfileVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            throw e;
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }
    }
}
