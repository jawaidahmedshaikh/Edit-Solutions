/*
 * User: gfrosti
 * Date: Nov 6, 2003
 * Time: 4:13:52 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.financial.client.trx;

import edit.common.vo.*;
import edit.common.*;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import event.*;
import event.dm.dao.DAOFactory;
import fission.utility.*;


public class ClientTrxImpl
{

    /**
     *
     * @param editTrxVO
     * @throws Exception
     */
    public void setupCorrespondence(EDITTrxVO editTrxVO)  throws Exception
    {
        TransactionCorrespondenceVO[] requiredTrxCorrespondenceVO = getTransactionCorrespondence(editTrxVO, null);

        ClientSetupVO clientSetupVO = ClientSetup.findByPK(editTrxVO.getClientSetupFK());

        ContractSetupVO contractSetupVO = ContractSetup.findByPK(clientSetupVO.getContractSetupFK());

        String complexChangeType = contractSetupVO.getComplexChangeTypeCT();

        for (int i = 0; i < requiredTrxCorrespondenceVO.length; i++)
        {
            TransactionCorrespondence transactionCorrespondence = new TransactionCorrespondence(requiredTrxCorrespondenceVO[i]);

            if ((complexChangeType != null &&
                 transactionCorrespondence.getTransactionTypeQualifierCT().equalsIgnoreCase(complexChangeType)) ||
                complexChangeType == null)
            {
                boolean shouldCreateCorrespondence = getDateRangeForGeneration(editTrxVO, transactionCorrespondence);

                if (shouldCreateCorrespondence)
                {
                    EDITTrxCorrespondence editTrxCorrespondence = new EDITTrxCorrespondence();

                    editTrxCorrespondence.set_EDITTrx(editTrxVO); // This will be persisted once saved

                    editTrxCorrespondence.set_TransactionCorrespondence(transactionCorrespondence); // This will be persisted once saved

                    EDITDate trxEffectiveDate = new EDITDate(editTrxVO.getEffectiveDate());

                    editTrxCorrespondence.save(); // Save rules will be invoked.
                }
            }
        }
    }

    /**
     * When start and stop dates exist on the TransactionCorrespondence, the dates dicate whether
     * correspondence will be created.
     * @param editTrxVO
     * @param transactionCorrespondence
     * @return
     */
    private boolean getDateRangeForGeneration(EDITTrxVO editTrxVO, TransactionCorrespondence transactionCorrespondence)
    {
        boolean shouldCreateCorrespondence = false;

        EDITDate trxEffDate = new EDITDate(editTrxVO.getEffectiveDate());
        EDITDate startDate = transactionCorrespondence.getStartDate();
        if (startDate == null)
        {
            startDate = new EDITDate(EDITDate.DEFAULT_MIN_DATE);
        }

        EDITDate stopDate = transactionCorrespondence.getStopDate();
        if (stopDate == null)
        {
            stopDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
        }

        if (trxEffDate.afterOREqual(startDate) && trxEffDate.beforeOREqual(stopDate))
        {
            shouldCreateCorrespondence = true;
        }

        return shouldCreateCorrespondence;
    }

    private void setupCorrespondence(ClientTrx clientTrx, CRUD crud) throws Exception
    {
        TransactionCorrespondenceVO[] requiredTrxCorrespondenceVO = getTransactionCorrespondence(clientTrx.getEDITTrxVO(), crud);

        ContractSetupVO contractSetupVO = (ContractSetupVO) clientTrx.getEDITTrxVO().getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class);

        String complexChangeType = contractSetupVO.getComplexChangeTypeCT();

        for (int i = 0; i < requiredTrxCorrespondenceVO.length; i++)
        {
            TransactionCorrespondence transactionCorrespondence = new TransactionCorrespondence(requiredTrxCorrespondenceVO[i]);
            if ((complexChangeType != null &&
                 transactionCorrespondence.getTransactionTypeQualifierCT().equalsIgnoreCase(complexChangeType)) ||
                complexChangeType == null)
            {
                boolean shouldCreateCorrespondence = getDateRangeForGeneration(clientTrx.getEDITTrxVO(), transactionCorrespondence);

                if (shouldCreateCorrespondence)
                {
                    EDITTrxCorrespondence editTrxCorrespondence = new EDITTrxCorrespondence();

                    editTrxCorrespondence.setEDITTrx(clientTrx); // This will be persisted once saved

                    editTrxCorrespondence.setTransactionCorrespondence(transactionCorrespondence); // This will be persisted once saved
                    editTrxCorrespondence.setTransactionCorrespondenceFK(transactionCorrespondence.getPK());

                    EDITDate trxEffectiveDate = new EDITDate(clientTrx.getEDITTrxVO().getEffectiveDate());

                    editTrxCorrespondence.save(new EDITTrx(clientTrx.getEDITTrxVO()), crud); // Save rules will be invoked.
                }
            }
        }
    }

    private void setupCorrespondence(ClientTrx clientTrx, int notificationDays, String notificationDaysType, CRUD crud) throws Exception
    {
        TransactionCorrespondenceVO[] requiredTrxCorrespondenceVO = getTransactionCorrespondence(clientTrx.getEDITTrxVO(), crud);

        ContractSetupVO contractSetupVO = (ContractSetupVO) clientTrx.getEDITTrxVO().getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class);

        String complexChangeType = contractSetupVO.getComplexChangeTypeCT();

        for (int i = 0; i < requiredTrxCorrespondenceVO.length; i++)
        {
            TransactionCorrespondence transactionCorrespondence = new TransactionCorrespondence(requiredTrxCorrespondenceVO[i]);

            if ((complexChangeType != null &&
                 transactionCorrespondence.getTransactionTypeQualifierCT().equalsIgnoreCase(complexChangeType)) ||
                complexChangeType == null)
            {
                boolean shouldCreateCorrespondence = getDateRangeForGeneration(clientTrx.getEDITTrxVO(), transactionCorrespondence);

                if (shouldCreateCorrespondence)
                {
                    EDITTrxCorrespondence editTrxCorrespondence = new EDITTrxCorrespondence();

                    editTrxCorrespondence.setEDITTrx(clientTrx); // This will be persisted once saved

                    editTrxCorrespondence.set_TransactionCorrespondence(transactionCorrespondence); // This will be persisted once saved

                    editTrxCorrespondence.setNotificationAmount(new EDITBigDecimal(clientTrx.getEDITTrxVO().getTrxAmount()));

                    EDITDate trxEffectiveDate = new EDITDate(clientTrx.getEDITTrxVO().getEffectiveDate());

                    //All transactions have been save but not committed, passed the editTrxVO
                    editTrxCorrespondence.save(notificationDays, notificationDaysType, clientTrx.getEDITTrxVO(), crud); // Save rules will be invoked.
                }
            }
        }
    }

    protected long save(ClientTrx clientTrx, CRUD crud, int notificationDays, String notificationDaysType)
    {
        long editTrxPK = 0;

        try
        {
            if (shouldSetupCorrespondence(clientTrx.getEDITTrxVO(), crud))
            {
                setupCorrespondence(clientTrx, notificationDays, notificationDaysType, crud);
            }

            if (clientTrx.hasReinsurance() && clientTrx.isPending())
            {
                clientTrx.setReinsuranceStatus("P");
            }

            editTrxPK = crud.createOrUpdateVOInDB(clientTrx.getEDITTrxVO());

            //Now we need to set correspondence from the original trx to the new reapply trx (if there is any)
            if (clientTrx.getEDITTrxVO().getStatus().equalsIgnoreCase("A"))
            {
                EDITTrxCorrespondenceVO[] editTrxCorrespondenceVO = DAOFactory.getEDITTrxCorrespondenceDAO().findByEDITTrxPK(clientTrx.getEDITTrxVO().getReapplyEDITTrxFK());

                if (editTrxCorrespondenceVO != null)
                {
                    clientTrx.getEDITTrxVO().setEDITTrxPK(editTrxPK);

                    for (int i = 0; i < editTrxCorrespondenceVO.length; i++)
                    {
                        EDITTrxCorrespondence newCorrespondence = new EDITTrxCorrespondence(editTrxCorrespondenceVO[i]);

                        newCorrespondence.setEDITTrx(clientTrx); // This will be persisted once saved

                        newCorrespondence.save(); // Save rules will be invoked.
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }

        return editTrxPK;
    }

    protected long save(ClientTrx clientTrx)
    {
        CRUD crud = null;

        long editTrxPK = 0;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            EDITTrxVO editTrxVO = clientTrx.getEDITTrxVO();
            if (shouldSetupCorrespondence(editTrxVO))
            {
                setupCorrespondence(editTrxVO);
            }

            if (clientTrx.hasReinsurance() && clientTrx.isPending())
            {
                clientTrx.setReinsuranceStatus("P");
            }

            editTrxPK = crud.createOrUpdateVOInDB(editTrxVO);

            //Now we need to set correspondence from the original trx to the new reapply trx (if there is any)
            if (clientTrx.getEDITTrxVO().getStatus().equalsIgnoreCase("A"))
            {
                EDITTrxCorrespondenceVO[] editTrxCorrespondenceVO = DAOFactory.getEDITTrxCorrespondenceDAO().findByEDITTrxPK(clientTrx.getEDITTrxVO().getReapplyEDITTrxFK());

                if (editTrxCorrespondenceVO != null)
                {
                    clientTrx.getEDITTrxVO().setEDITTrxPK(editTrxPK);

                    for (int i = 0; i < editTrxCorrespondenceVO.length; i++)
                    {
                        EDITTrxCorrespondence newCorrespondence = new EDITTrxCorrespondence(editTrxCorrespondenceVO[i]);

                        newCorrespondence.setEDITTrx(clientTrx); // This will be persisted once saved

                        newCorrespondence.save(); // Save rules will be invoked.
                    }
                }
            }
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

        return editTrxPK;
    }

   protected long save(ClientTrx clientTrx, CRUD crud)
    {
        long editTrxPK = 0;

        try
        {
            if (shouldSetupCorrespondence(clientTrx.getEDITTrxVO(), crud))
            {
//                setupCorrespondence(clientTrx, 0, null);
                setupCorrespondence(clientTrx, crud);
            }

            if (clientTrx.hasReinsurance() && clientTrx.isPending())
            {
                clientTrx.setReinsuranceStatus("P");                
            }

            editTrxPK = crud.createOrUpdateVOInDB(clientTrx.getEDITTrxVO());

            //Now we need to set correspondence from the original trx to the new reapply trx (if there is any)
            if (clientTrx.getEDITTrxVO().getStatus().equalsIgnoreCase("A"))
            {
                EDITTrxCorrespondenceVO[] editTrxCorrespondenceVO = DAOFactory.getEDITTrxCorrespondenceDAO().findByEDITTrxPK(clientTrx.getEDITTrxVO().getReapplyEDITTrxFK());

                if (editTrxCorrespondenceVO != null)
                {
                    clientTrx.getEDITTrxVO().setEDITTrxPK(editTrxPK);

                    for (int i = 0; i < editTrxCorrespondenceVO.length; i++)
                    {
                        EDITTrxCorrespondence newCorrespondence = new EDITTrxCorrespondence(editTrxCorrespondenceVO[i]);

                        newCorrespondence.setEDITTrx(clientTrx); // This will be persisted once saved

                        newCorrespondence.save(); // Save rules will be invoked.
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
//        finally
//        {
//            if (crud != null) crud.close();
//
//            crud = null;
//        }

        return editTrxPK;
    }

    public boolean shouldSetupCorrespondence(EDITTrxVO editTrxVO) throws Exception
    {
        boolean correspondenceRequired = false;
        boolean editTrxCorrespondenceDoesNotExist = false;
        boolean statusFieldsOK = false;

       // Does this TrxType even have correspondence associated with it (i.e. are there any TransactionCorrespondenceVO)?
        TransactionCorrespondenceVO[] transactionCorrespondenceVO = getTransactionCorrespondence(editTrxVO, null);

        if (transactionCorrespondenceVO != null)
        {
            correspondenceRequired = true;
        }

        // Is there already correspondence?
        if (correspondenceRequired == true)
        {
            EDITTrxCorrespondenceVO[] editTrxCorrespondenceVO = DAOFactory.getEDITTrxCorrespondenceDAO().findByEDITTrxPK(editTrxVO.getEDITTrxPK());

            if (editTrxCorrespondenceVO != null)
            {
                editTrxCorrespondenceDoesNotExist = false;
            }
            else
            {
                editTrxCorrespondenceDoesNotExist = true;
            }
        }

        // Check the status' on the EDITTrx
        if (editTrxCorrespondenceDoesNotExist)
        {
            if (editTrxVO.getStatus().equals("N") &&
                    editTrxVO.getPendingStatus().equals("P") &&
                    editTrxVO.getNoCorrespondenceInd().equals("N"))
            {
                statusFieldsOK = true;
            }
            else
            {
                statusFieldsOK = false;
            }
        }

        return correspondenceRequired && editTrxCorrespondenceDoesNotExist && statusFieldsOK;
    }

    public boolean shouldSetupCorrespondence(EDITTrxVO editTrxVO, CRUD crud) throws Exception
    {
        boolean correspondenceRequired = false;
        boolean editTrxCorrespondenceDoesNotExist = false;
        boolean statusFieldsOK = false;

       // Does this TrxType even have correspondence associated with it (i.e. are there any TransactionCorrespondenceVO)?
        TransactionCorrespondenceVO[] transactionCorrespondenceVO = getTransactionCorrespondence(editTrxVO, crud);

        if (transactionCorrespondenceVO != null)
        {
            correspondenceRequired = true;
        }

        // Is there already correspondence?
        if (correspondenceRequired == true)
        {
            EDITTrxCorrespondenceVO[] editTrxCorrespondenceVO = new edit.common.vo.EDITTrxCorrespondenceVO[0];
            if (crud == null)
            {
                editTrxCorrespondenceVO = DAOFactory.getEDITTrxCorrespondenceDAO().findByEDITTrxPK(editTrxVO.getEDITTrxPK());
            }
            else
            {
                editTrxCorrespondenceVO = DAOFactory.getEDITTrxCorrespondenceDAO().findByEDITTrxPK(editTrxVO.getEDITTrxPK(), crud);
            }

            if (editTrxCorrespondenceVO != null)
            {
                editTrxCorrespondenceDoesNotExist = false;
            }
            else
            {
                editTrxCorrespondenceDoesNotExist = true;
            }
        }

        // Check the status' on the EDITTrx
        if (editTrxCorrespondenceDoesNotExist)
        {
            if (editTrxVO.getStatus().equals("N") &&
                    editTrxVO.getPendingStatus().equals("P") &&
                    editTrxVO.getNoCorrespondenceInd().equals("N"))
            {
                statusFieldsOK = true;
            }
            else
            {
                statusFieldsOK = false;
            }
        }

        return correspondenceRequired && editTrxCorrespondenceDoesNotExist && statusFieldsOK;
    }


    private TransactionCorrespondenceVO[] getTransactionCorrespondence(EDITTrxVO editTrxVO, CRUD crud) throws Exception
    {
        String trxTypeQualifier = null;
        if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("TU"))
        {
            trxTypeQualifier = editTrxVO.getTransferUnitsType();
        }

        TransactionPriorityVO[] transactionPriorityVO = null;
        if (crud != null)
        {
            transactionPriorityVO = DAOFactory.getTransactionPriorityDAO().findByTrxType(editTrxVO.getTransactionTypeCT(), crud);
        }
        else
        {
            transactionPriorityVO = DAOFactory.getTransactionPriorityDAO().findByTrxType(editTrxVO.getTransactionTypeCT());
        }

        TransactionCorrespondenceVO[] transactionCorrespondenceVO = null;

        if (trxTypeQualifier != null)
        {
            transactionCorrespondenceVO =
                    DAOFactory.getTransactionCorrespondenceDAO().findByTransactionPriorityPK_TrxTypeQualifier(transactionPriorityVO[0].getTransactionPriorityPK(), trxTypeQualifier);
        }
        else
        {
            transactionCorrespondenceVO =
                    DAOFactory.getTransactionCorrespondenceDAO().findByTransactionPriorityPK(transactionPriorityVO[0].getTransactionPriorityPK());
        }

        return transactionCorrespondenceVO;
    }
}
