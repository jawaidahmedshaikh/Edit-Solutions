package client.dm.composer;

import edit.common.vo.*;
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
public class ClientDetailComposer extends Composer
{
    private List voInclusionList = null;
    private ClientAddressVO[] clientAddressVO;
    private TaxInformationVO[] taxInformationVO;
    private PreferenceVO[] preferenceVO;
    private TaxProfileVO[] taxProfileVO;
    private ClientRoleVO[] clientRoleVO;

    private CRUD crud = null;

    public ClientDetailComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public ClientDetailVO compose(long clientDetailPK)
    {
        ClientDetailVO clientDetailVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            clientDetailVO = (ClientDetailVO) crud.retrieveVOFromDB(ClientDetailVO.class, clientDetailPK);

            compose(clientDetailVO);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            throw new RuntimeException(e);
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }

        return clientDetailVO;
    }

    public void compose(ClientDetailVO clientDetailVO)
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(ClientAddressVO.class)) appendClientAddressVO(clientDetailVO);

            if (voInclusionList.contains(TaxInformationVO.class)) appendTaxInformationVO(clientDetailVO);

            if (voInclusionList.contains(PreferenceVO.class)) appendPreferenceVO(clientDetailVO);

            if (voInclusionList.contains(ClientRoleVO.class)) appendClientRoleVO(clientDetailVO);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            throw new RuntimeException(e);
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }
    }

    public void substituteClientAddressVO(ClientAddressVO[] clientAddressVO)
    {
        this.clientAddressVO = clientAddressVO;
    }

    public void substituteTaxInformationVO(TaxInformationVO[] taxInformationVO)
    {
        this.taxInformationVO = taxInformationVO;
    }

    public void substitutePreferenceVO(PreferenceVO[] preferenceVO)
    {
        this.preferenceVO = preferenceVO;
    }

    public void substituteTaxProfileVO(TaxProfileVO[] taxProfileVO)
    {
        this.taxProfileVO = taxProfileVO;
    }

    private void appendClientAddressVO(ClientDetailVO clientDetailVO) throws Exception
    {
        voInclusionList.remove(ClientDetailVO.class);

        if (clientAddressVO == null) // No Overrides
        {
            clientAddressVO = (ClientAddressVO[]) crud.retrieveVOFromDB(ClientAddressVO.class, ClientDetailVO.class, clientDetailVO.getClientDetailPK());
        }

        if (clientAddressVO != null) clientDetailVO.setClientAddressVO(clientAddressVO);

        voInclusionList.add(ClientDetailVO.class);
    }

    private void appendTaxInformationVO(ClientDetailVO clientDetailVO) throws Exception
    {
        voInclusionList.remove(ClientDetailVO.class);

        if (taxInformationVO == null) // No overrides
        {
            taxInformationVO = (TaxInformationVO[]) crud.retrieveVOFromDB(TaxInformationVO.class, ClientDetailVO.class, clientDetailVO.getClientDetailPK());
        }

        if (taxInformationVO != null)
        {
            clientDetailVO.setTaxInformationVO(taxInformationVO);

            for (int i = 0; i < taxInformationVO.length; i++)
            {
                if (voInclusionList.contains(TaxProfileVO.class)) appendTaxProfileVO(taxInformationVO[i]);
            }
        }

        voInclusionList.add(ClientDetailVO.class);
    }

    private void appendPreferenceVO(ClientDetailVO clientDetailVO) throws Exception
    {
        voInclusionList.remove(ClientDetailVO.class);

        if (preferenceVO == null)
        {
            preferenceVO = (PreferenceVO[]) crud.retrieveVOFromDB(PreferenceVO.class, ClientDetailVO.class, clientDetailVO.getClientDetailPK());
        }

        if (preferenceVO != null) clientDetailVO.setPreferenceVO(preferenceVO);

        voInclusionList.add(ClientDetailVO.class);
    }

    private void appendTaxProfileVO(TaxInformationVO taxInformationVO) throws Exception
    {
        voInclusionList.remove(TaxInformationVO.class);

        if (taxProfileVO == null) // No overrides
        {
            taxProfileVO = (TaxProfileVO[]) crud.retrieveVOFromDB(TaxProfileVO.class, TaxInformationVO.class, taxInformationVO.getTaxInformationPK());
        }

        if (taxProfileVO != null) taxInformationVO.setTaxProfileVO(taxProfileVO);

        voInclusionList.add(TaxInformationVO.class);
    }
    private void appendClientRoleVO(ClientDetailVO clientDetailVO)
    {
        voInclusionList.remove(ClientDetailVO.class);

        if (clientRoleVO == null) // No overrides
        {
            clientRoleVO = (ClientRoleVO[]) crud.retrieveVOFromDB(ClientRoleVO.class, ClientDetailVO.class, clientDetailVO.getClientDetailPK());
        }

        if (clientRoleVO != null) clientDetailVO.setClientRoleVO(clientRoleVO);

        voInclusionList.add(ClientDetailVO.class);
    }
}
