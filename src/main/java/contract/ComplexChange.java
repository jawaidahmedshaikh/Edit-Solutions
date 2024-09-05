/*
 * User: cgleason
 * Date: Mar 3, 2005
 * Time: 8:42:54 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;

import edit.common.EDITDate;
import edit.common.Change;
import edit.common.vo.*;
import edit.common.exceptions.EDITEventException;
import event.financial.group.trx.GroupTrx;
import event.*;
import engine.*;

import java.util.*;

import java.util.ArrayList;
import java.util.List;


public class ComplexChange
{

    public ComplexChange()
    {
    }

    /**
      * The only change to process on the Life table is the DeathBenefitOptionCT
      * @param segmentPK
      * @return
      */
     public void checkForDBOChange(Change change, ContractClient[] contractClients,
                                        Segment segment, String operator)
     {

         String changedValue = change.getAfterValue();

         String changedType = "DBOChange";

         ContractClient contractClient = getInsuredContractClient(contractClients);

         setupForEDITTrx(changedValue, changedType, contractClient, segment, operator);
     }
    
     /**
      * If ClassCT or TableRatingCT values have changed, special processing call complex change must occur.
      * The change is recorded in a trxtype = "CC" marked to execute on the next mode.  The table will not
      * be updated with this change at this time.
      * If the TerminationDate value has changed, and the termination reason is "Ownership Change - Child/Other",
      * a complex change must occur.  The change is recorded in a trxtype = "CC", with an effective date equal to
      * the termination date of the client.
      *
      * @param contractClient
      * @param fieldName
      * @param change
      * @param segment
      * @param classcChangeEffectiveDate
      * @param operator
      */
     public void createComplexChangeForRating(ContractClient contractClient, String fieldName, Change change,
                                                Segment segment, String operator) {
    	 createComplexChangeForRating(contractClient, fieldName, change, segment, null, operator);
     }

     public void createComplexChangeForRating(ContractClient contractClient, String fieldName, Change change,
                                                Segment segment, String classChangeEffectiveDate, String operator)
     {
         String changedValue = null;
         String changedType = null;

         if (fieldName.equalsIgnoreCase("ClassCT"))
         {
//             changedValue = contractClient.getClassCT();
             changedValue = change.getAfterValue();
             contractClient.setClassCT(changedValue);
             changedType = "ClassChange";
         }

         if (fieldName.equalsIgnoreCase("TableRatingCT"))
         {
             changedValue = contractClient.getTableRatingCT();
             contractClient.setTableRatingCT(change.getBeforeValue());
             changedType = "TableRatingChange";
         }

         setupForEDITTrx(changedValue, changedType, contractClient, segment, classChangeEffectiveDate, operator);
     }

    /**
     * If the TerminationDate value has changed, and the termination reason is "Ownership Change - Child/Other",
     * a complex change must occur.  The change is recorded in a trxtype = "CC", with an effective date equal to
     * the termination date of the client.
     * @param contractClientVO
     * @param fieldName
     * @param change
     * @param segmentVO
     * @param operator
     */
    public void createComplexChangeForOwnershipChange(ContractClient contractClient, String fieldName, Change change,
                                                      Segment segment, String operator)
    {
        EDITDate terminationDate = contractClient.getTerminationDate();
        String changeType = "OwnershipChange";

        setupForEDITTrx(terminationDate.getFormattedDate(), changeType, contractClient, segment, operator);

        try
        {
            segment.updateOwnerForPendingTransactions(contractClient, null);
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
        }
    }

    public void createComplexChangeForRothConversion(Segment segment, String fieldName, Change change, String operator)
    {
        String changedValue = segment.getQualifiedTypeCT();
        String changeType = "ROTHConversion";

        Set setOfContractClients = segment.getContractClients();
        ContractClient[] contractClients = (ContractClient[]) setOfContractClients.toArray(new ContractClient[setOfContractClients.size()]);

        ContractClient ownerContractClient = getOwnerContractClient(contractClients);

        setupForEDITTrx(changedValue, changeType, ownerContractClient, segment, operator);
    }

    /** A new rider is a face increase for the Life contracts.
      *
      * @param segmentVO
      */
     public void checkForFaceIncrease(Segment riderSegment, ContractClient[] contractClients) throws Exception
     {
//         if (riderSegment.getPK() == 0 && riderSegment.getSegmentStatusCT().equalsIgnoreCase("Pending"))
         if (riderSegment.getSegmentStatusCT().equalsIgnoreCase("Pending"))
         {
             Life riderLife = riderSegment.getLife();

             String changedValue = riderLife.getFaceAmount().toString();

             String changedType = "FaceIncrease";

             ContractClient contractClient = getInsuredContractClient(contractClients);

             event.business.Event eventComponent = new event.component.EventComponent();

             List voInclusionList = new ArrayList();
             voInclusionList.add(ClientSetupVO.class);
             voInclusionList.add(ContractSetupVO.class);
             voInclusionList.add(GroupSetupVO.class);

             EDITTrxVO[] editTrxVOs = eventComponent.composePendingEDITTrxVOBySegmentPKAndTrxType(((SegmentVO)riderSegment.getVO()).getSegmentFK(), new String[] {"CC"}, voInclusionList);
             boolean complexChangeExists = false;
             if (editTrxVOs != null)
             {
                 for (int i = 0; i < editTrxVOs.length; i++)
                 {
                     ContractSetupVO contractSetupVO = (ContractSetupVO) editTrxVOs[i].getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class);
                     if (contractSetupVO.getComplexChangeTypeCT().equalsIgnoreCase("FaceIncrease") &&
                         editTrxVOs[i].getPendingStatus().equalsIgnoreCase("P"))
                     {
                         complexChangeExists = true;
                         modifyExistingEDITTrx(changedValue, riderSegment, editTrxVOs[i]);
                         i = editTrxVOs.length;
                     }
                 }
             }

             if (!complexChangeExists)
             {
                setupForEDITTrx(changedValue, changedType, contractClient,
                                riderSegment, riderSegment.getOperator());
             }
         }
     }

    
    /**
     * For an array of ContractClients find the Insured role
     * @return
     */
    public ContractClient getInsuredContractClient(ContractClient[] contractClients)
    {
        return ContractClient.getInsuredContractClient(contractClients);
    }

    public ContractClient getOwnerContractClient(ContractClient[] contractClients)
    {
        return ContractClient.getOwnerContractClient(contractClients);
    }

    /**
     * Initialize an editTrx structure, then use GroupTrx to save the transaction
     * The change value for a Face Increase is the rider number
     * @param changedValue
     * @param changedType
     * @param contractClientVO
     * @param classChangeEffectiveDate
     */
    public void setupForEDITTrx(String changedValue, String changeType, ContractClient contractClient, Segment segment, String operator) {
    	setupForEDITTrx(changedValue, changeType, contractClient, segment, operator);
    }

    public void setupForEDITTrx(String changedValue, String changeType, ContractClient contractClient, Segment segment, String classChangeEffectiveDate,
    		String operator)
    {
        GroupSetup groupSetup = new GroupSetup();

        groupSetup.setGroupSetupPK(new Long(0));

        ContractSetup contractSetup = new ContractSetup();

        contractSetup.setContractSetupPK(new Long(0));
        contractSetup.setGroupSetupFK(new Long(0));

        Long baseSegmentKey = new Long(0);
//        if (segment.getRiderNumber() == 0)
        if (segment.isBase())
        {
            // it's a base segment, get the pk
            baseSegmentKey = segment.getSegmentPK();
        }
        else
        {
            // it's a rider segment, get the base's pk
            baseSegmentKey = segment.getSegmentFK();
        }

        contractSetup.setSegmentFK(baseSegmentKey);
        contractSetup.setComplexChangeNewValue(changedValue);
        contractSetup.setComplexChangeTypeCT(changeType);

        ClientSetup clientSetup = new ClientSetup();

        clientSetup.setClientSetupPK(new Long(0));
        clientSetup.setContractSetupFK(new Long(0));
        clientSetup.setContractClientFK(contractClient.getContractClientPK());
        clientSetup.setClientRoleFK(contractClient.getClientRoleFK());

//        EDITTrx[] editTrxs = EDITTrx.findBy_SegmentPK_EffectiveDate_TransactionType_WithPendingStatus(baseSegmentKey, new EDITDate(), "MD");
        EDITTrxVO[] editTrxVOs = event.dm.dao.DAOFactory.getEDITTrxDAO().findBySegmentPK_AND_TrxType_AND_Date(baseSegmentKey.longValue(), "MD", new EDITDate().getFormattedDate());

        EDITDate effectiveDate = null;

        if (changeType.equalsIgnoreCase("OwnershipChange"))
        {
            effectiveDate = contractClient.getTerminationDate();
        }
        else if (changeType.equalsIgnoreCase("ROTHConversion"))
        {
            effectiveDate = new EDITDate();
        } else if (changeType.equalsIgnoreCase("ClassChange")) 
        {
        	effectiveDate = new EDITDate(classChangeEffectiveDate);
        }
        else
        {
            if ((editTrxVOs != null) && editTrxVOs.length > 0)
            {
                effectiveDate = new EDITDate(editTrxVOs[0].getEffectiveDate());
            }
            else
            {
                //todo - This condition is should never happen - change to throw an error
                effectiveDate = new EDITDate();
            }
        }

        int taxYear = effectiveDate.getYear();

        EDITTrx editTrx = EDITTrx.buildDefaultEDITTrx("CC", effectiveDate, taxYear, operator);

        if (changeType.equalsIgnoreCase("OwnershipChange"))
        {
            editTrx.setNoAccountingInd("Y");
        }

        //todo - these entities are not built correctly to save the trx - must wait until full hibernate conversion
//        clientSetup.addEDITTrx(editTrx);
//        contractSetup.addClientSetup(clientSetup);
//        groupSetup.addContractSetup(contractSetup);

        GroupSetupVO groupSetupVO = groupSetup.getAsVO();
        ContractSetupVO contractSetupVO = (ContractSetupVO)contractSetup.getVO();
        ClientSetupVO clientSetupVO = (ClientSetupVO)clientSetup.getVO();
        EDITTrxVO editTrxVO = editTrx.getAsVO();

        clientSetupVO.addEDITTrxVO(editTrxVO);
        contractSetupVO.addClientSetupVO(clientSetupVO);
        groupSetupVO.addContractSetupVO(contractSetupVO);


        GroupTrx groupTrx = new GroupTrx();

        ProductStructure productStructure = new ProductStructure(segment.getProductStructureFK().longValue());

        try
        {
            groupTrx.saveGroupSetup(groupSetupVO, editTrxVO, "ComplexChange", segment.getOptionCodeCT(), productStructure.getProductStructurePK().longValue());
        }
        catch (EDITEventException e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Modify existing pending complex change transaction (for FaceIncrease Only)
     * @param changedValue
     * @param operator
     * @param editTrxVO
     */
    public void modifyExistingEDITTrx(String changedValue, Segment riderSegment, EDITTrxVO editTrxVO)
    {
        ContractSetupVO contractSetupVO = (ContractSetupVO) editTrxVO.getParentVO(ClientSetupVO.class).getParentVO(ContractSetupVO.class);
        GroupSetupVO groupSetupVO = (GroupSetupVO) contractSetupVO.getParentVO(GroupSetupVO.class);

        contractSetupVO.setComplexChangeNewValue(changedValue);

        editTrxVO.setOperator(riderSegment.getOperator());

        GroupTrx groupTrx = new GroupTrx();

        SegmentVO riderSegmentVO = (SegmentVO) riderSegment.getVO();

        try
        {
            groupTrx.saveGroupSetup(groupSetupVO, editTrxVO, "ComplexChange", riderSegmentVO.getOptionCodeCT(), riderSegmentVO.getProductStructureFK());
        }
        catch (EDITEventException e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
