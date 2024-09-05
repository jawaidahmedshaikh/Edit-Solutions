/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Jan 17, 2002
 * Time: 2:03:48 PM
 *
 * (c) 2000 - 2004 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */
package accounting.dm;

import accounting.dm.dao.DAOFactory;
import edit.common.vo.*;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class StorageManager implements Serializable {

    // Member variables:
    private static final String POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;

    /**
     * StorageManager constructor.
     */
    public StorageManager() {

    }

//    public long saveElement(ElementVO elementVO) throws Exception {
//
//        elementId = elementVO.getElementPK();
//
//        if (elementExists(elementId)) {
//
//            updateElement(elementVO);
//        }
//
//        else {
//
//            addElement(elementVO);
//        }
//
//		return elementId;
//    }

    public long saveVO(Object valueObject) throws Exception {

        CRUD crud = null;

        long pkValue = 0;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

            List voExclusionList = new ArrayList();
            voExclusionList.add(ElementCompanyRelationVO.class);

            pkValue = crud.createOrUpdateVOInDBRecursively(valueObject, voExclusionList);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }

        return pkValue;

    }
    public void deleteVO(String voName, long primaryKey) throws Exception {

        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

            crud.deleteVOFromDBRecursively(Class.forName("edit.common.vo." + voName) , primaryKey);
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }

     }

    public void saveElementCompanyRelation(long productStructureId,
                                            long[] elementIds)
                                          throws Exception {
        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

            for (int i = 0; i < elementIds.length; i++)
            {
                // Check to see if this aleady exists...
                ElementCompanyRelationVO[] elementCompanyRelationVOs =
                        DAOFactory.getElementCompanyRelationDAO().findElementCompanyRelationByIds(elementIds[i], productStructureId);


                if (elementCompanyRelationVOs == null)
                {
                    ElementCompanyRelationVO  elementCompanyRelation = new  ElementCompanyRelationVO();
                    elementCompanyRelation.setProductStructureFK(productStructureId);
                    elementCompanyRelation.setElementFK(elementIds[i]);
                    crud.createOrUpdateVOInDB(elementCompanyRelation);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }
    }

    public void deleteElementCompanyRelation(long[] elementIds, long productStructureId) throws Exception {

        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

            for (int i = 0; i < elementIds.length; i++)
            {
                // Check to see if this aleady exists...
                ElementCompanyRelationVO[] elementCompanyRelationVOs =
                        DAOFactory.getElementCompanyRelationDAO().findElementCompanyRelationByIds(elementIds[i], productStructureId);

                if (elementCompanyRelationVOs != null)
                {
                    crud.deleteVOFromDBRecursively(ElementCompanyRelationVO.class, elementCompanyRelationVOs[0].getElementCompanyRelationPK());
                }
            }
        }
        catch (Exception e) {

            System.out.println(e);

            e.printStackTrace();

            throw new Exception(e.toString());
        }
        finally
        {
            if (crud != null) crud.close();
        }
    }

    private long elementCompanyRelationExists(long elementId, long productStructureId) throws Exception {

        ElementCompanyRelationVO[] newElementCompanyRelationVO =  DAOFactory.getElementCompanyRelationDAO().
                 findElementCompanyRelationByIds(elementId, productStructureId);
        long pkValue = 0;

        if (newElementCompanyRelationVO == null) {

        }

        else {

            pkValue = newElementCompanyRelationVO[0].getElementCompanyRelationPK();

        }
        return pkValue;
    }

    private boolean elementExists(long elementId) throws Exception {

        ElementVO[] newElementVO = DAOFactory.getElementDAO().findByElementId(elementId);
        if (newElementVO == null) {

            return false;
        }

        else {

            return true;
        }
    }

    public void saveAccountingDetail(AccountingDetailVO accountingDetailVO) throws Exception {

        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

            crud.createOrUpdateVOInDBRecursively(accountingDetailVO);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }
    }

    /**
     * This method copies a set of ElementCompanyRelation records of one company structure to another company structure.
     * Access all the ElementCompanyStructure records of the fromCompanyStructure, reset the primary key and the company
     * structure to the toCompanyStructure for each one and save to the database.
     * @param fromCompanyStructure
     * @param toCompanyStructure
     * @throws Exception
     */
    public void cloneCompanyStructure(long fromCompanyStructure, long toCompanyStructure) throws Exception
    {
        CRUD crud = null;

        try
        {
            ElementCompanyRelationVO[] elementCompanyRelationVOs = DAOFactory.getElementCompanyRelationDAO().findRelationsByProductStructureId(fromCompanyStructure);

            if (elementCompanyRelationVOs != null)
            {
                crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

                for (int i = 0; i < elementCompanyRelationVOs.length; i++)
                {
                    ElementCompanyRelationVO elementCompanyRelationVO = elementCompanyRelationVOs[i];
                    elementCompanyRelationVO.setElementCompanyRelationPK(0);
                    elementCompanyRelationVO.setProductStructureFK(toCompanyStructure);
                    crud.createOrUpdateVOInDB(elementCompanyRelationVO);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw e;
        }
        finally
        {
            if (crud != null) crud.close();
        }
    }
}