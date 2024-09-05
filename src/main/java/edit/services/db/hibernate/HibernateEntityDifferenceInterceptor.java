package edit.services.db.hibernate;

import contract.ChangeHistory;
import contract.Segment;

import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.exceptions.EDITNonFinancialException;
import edit.common.vo.NonFinancialEntity;
import edit.common.vo.ValidationVO;

import edit.services.config.ServicesConfig;
import edit.services.db.HibernateEntityDifferenceDocument;

import engine.ProductStructure;

import engine.business.Calculator;

import engine.component.CalculatorComponent;

import engine.sp.SPException;
import engine.sp.SPOutput;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

 
/**
 * Certain entities need to have their "before" and "after" values compared before
 * a final commit to the database.
 * 
 * The "before" and "after" relate to the entity as it stands in the DB [before], and the
 * entity as it stands in session [after].
 * 
 * Entities which are targeted for this before/after comparison (most likely a configuration
 * option) will a HibernateEntityDifference generated. This difference "report" will
 * be generated and sent to PRASE in (up-to) two phases:
 * 
 * 1. Before the entity is actually saved to the DB. This gives the business logic the 
 * opportunity to veto the saving of the entity (perhaps some basic validation failed). In 
 * this case, the current transaction is rolled back.
 * 
 * 2. After the entity is actually saved to the DB. The business logic can no longer
 * veto the saving of the enity, but the business logic can use the the difference "report"
 * and the saved entity to generate complex changes.
 * 
 * In addition to before/update comparisons, we also want to detect when a new
 * entity is saved for the first time so that we can trap an Operator and MaintDateTime
 * for the entity's change.
 * 
 * Hibernate has a rich Interceptor/EventListener framework. Nonetheless, it has 
 * been [very!] tedious trying to find the set of events which best matches our needs.
 * One of the issues was that enabling one set of events seems to deactivate the
 * trapping of another set of events. I don't know if this is a feature or a bug.
 * 
 * Nonetheless, 
 */
public class HibernateEntityDifferenceInterceptor extends EmptyInterceptor //implements PreUpdateEventListener
{
    /**
     * The name by which all differences will be stored for list of entity changes 
     * within the current thread's context.
     */
    private static final String HIBERNATE_ENTITY_DIFFERENCES = "HIBERNATE_ENTITY_DIFFERENCES";
    
    /**
     * During the process to commit Pending ChangeHistories, multiple ChangeHistories
     * may be used to "reconstruct" the underlying HibernateEntity. Once that
     * HibernateEntity is committed, all of the Pending ChangeHistories used
     * to reconstruct the HibernateEntity need to have their PendingStatus changed
     * from 'P' to 'H'.
     */
    private static final String PENDING_CHANGE_HISTORIES = "PENDING_CHANGE_HISTORIES";

    /**
     * The product key will be a "system" one. Once we get it, it
     * will be unnecessary to look it up again.
     */
    private static Long nfProductKey;

    /**
     * Essentially a cache of those entities configured to be tracked as
     * non-financial entities. Stored here for performance reasons.
     */
    private static List<NonFinancialEntity> nonFinancialEntities = null;

    /**
     * The name by which hard edit ValidationVOs will be associated.
     */
    public static final String HARD_EDITS = "hardEdits";
    
    /**
     * Singleton.
     */
    public static HibernateEntityDifferenceInterceptor hibernateEntityDifferenceInterceptor;

    public HibernateEntityDifferenceInterceptor()
    {
        hibernateEntityDifferenceInterceptor = this;
    }

    /**
     * NonFinancial Framework: PHASE-1     
     *  
     * Considers the entire list of all HibernateEntityChanges and submits them
     * to PRASE for business processing/analysis. PRASE can VETO this
     * these changes. In such a case, the current transaction is aborted,
     * and appropriate messages are supplied to the calling client.
     * @param transaction
     */
    @Override
    public void beforeTransactionCompletion(Transaction transaction)
    {
        List<HibernateEntityDifference> hibernateEntityDifferences = (List<HibernateEntityDifference>) SessionHelper.getFromThreadLocal(HIBERNATE_ENTITY_DIFFERENCES);

        if (hibernateEntityDifferences != null)
        {
            // Remove the current hibernateEntityDifferences otherwise you will enter an infinite loop
            // since saving the [below] ChangeHistory(s) goes through the same process.
            // Do this even before dispatching to PRASE since Log entries may be generated
            // forcing you through this interceptor and placing you in an infinite loop.
            SessionHelper.removeFromThreadLocal(HIBERNATE_ENTITY_DIFFERENCES);

            boolean abortedOnChangeEffectiveDate = abortOnChangeEffectiveDate(hibernateEntityDifferences, transaction);
            
            if (!abortedOnChangeEffectiveDate)
            {
                SPOutput spOutput = dispatchToPRASE(hibernateEntityDifferences, HibernateEntityDifferenceDocument.PHASE_PRE_COMMIT);

                List<ValidationVO> hardEdits = spOutput.getHardEdits();

                // NOTE: We don't put the hibernateEntityDifferences back in ThreadLocal if there are hard-edits.
                // If we left them there, then when the afterTransactionCompletion() would find them and
                // dispatch them to PRASE.
                //
                // NOTE: I would liked to have thrown some kind of unchecked exception here to notify the caller
                // that hard-edits exist. However, Hibernate appears to squash [all] exceptions being thrown
                // from within this interceptor implementation. [Therefore] I am relying on the fact that it is
                // SessionHelper.commitTransaction(..) that initiated this interceptor. It is in that method
                // that I will throw an exception.
                if (!hardEdits.isEmpty())
                {
                    generateHardEditMessages(hardEdits);

                    transaction.rollback();
                }
                else 
                {
                    // Put back.
                    SessionHelper.putInThreadLocal(HIBERNATE_ENTITY_DIFFERENCES, hibernateEntityDifferences);                    
                }
            }
        }

        super.beforeTransactionCompletion(transaction);
    }

    /**
     * NonFinancial Framework: PHASE-2
     * 
     * Assumes that PHASE-1 was successful in that no NF change was vetoed. 
     * Nonetheless, the set of NF changes (as HibernateEntityDifferences)
     * are re-submitted to PRASE for any post-save activities.
     * @param transaction
     */
    public void afterTransactionCompletion(Transaction transaction)
    {
        List<HibernateEntityDifference> hibernateEntityDifferences = (List<HibernateEntityDifference>) SessionHelper.getFromThreadLocal(HIBERNATE_ENTITY_DIFFERENCES);

        if (hibernateEntityDifferences != null)
        {
            // Remove them once-and-for-all. This is the last stop.
            SessionHelper.removeFromThreadLocal(HIBERNATE_ENTITY_DIFFERENCES);

            generateChangeHistory(hibernateEntityDifferences);

            SPOutput spOutout = dispatchToPRASE(hibernateEntityDifferences, HibernateEntityDifferenceDocument.PHASE_POST_COMMIT);
        }

        super.afterTransactionCompletion(transaction);
    }

    /**
     * Called when a transient entity (never saved before) is saved to the DB.
     * It is expected that entities flagged as "Non Financial" entities (probably
     * specified in a configuration file) will have their NonFinancialDateTime/Operator
     * fields set.
     * Operator/MaintDateTime set.
     * @param object
     * @param serializable
     * @param object1
     * @param string
     * @param type
     * @return
     */
    public boolean onSave(Object object, Serializable serializable, Object[] object1, String[] string, Type[] type)
    {
        return super.onSave(object, serializable, object1, string, type);
    }

    /**
     * A ChangeHistory.ChangeEffectiveDate (actually ChangeHistory.EffectiveDate in the DB) has
     * been detected in one of the HibernateEntityDifferences. This tells us that we are going
     * to created the future-dated ChangeHistory and abort the saving of [ALL] other entities currently
     * being saved within the transaction.
     * @param hibernateEntityDifference
     */
    private boolean abortOnChangeEffectiveDate(List<HibernateEntityDifference> hibernateEntityDifferences, Transaction transaction)
    {
        boolean abortOnChangeEffectiveDate = false;
        
        for (HibernateEntityDifference hibernateEntityDifference:hibernateEntityDifferences)
        {
            if (hibernateEntityDifference.shouldAbortOnChangeEffectiveDate())
            {
                // Kill the current transaction
                transaction.rollback();
                
                // Store the future dated ChangeHistory
                List<HibernateEntityDifference> futuredDatedHibernateEntityDifference = new ArrayList<HibernateEntityDifference>();
                
                futuredDatedHibernateEntityDifference.add(hibernateEntityDifference);
                
                generateChangeHistory(futuredDatedHibernateEntityDifference);
                
                // Set the flag
                abortOnChangeEffectiveDate = true;
                
                break; // We are assuming that there is only one Entity with future-dated changes.
            }
        }
        
        return abortOnChangeEffectiveDate;
    }

    /**
     * 
     * @param hibernateEntityDifferences
     * @param phase
     * @return
     */
    private SPOutput dispatchToPRASE(List<HibernateEntityDifference> hibernateEntityDifferences, String phase)
    {
        SPOutput spOutput = null;

        HibernateEntityDifferenceDocument document = new HibernateEntityDifferenceDocument(hibernateEntityDifferences);

        document.build(phase);
        
        Calculator calculator = new CalculatorComponent();

        Long productStructurePK = null;
        
        if (hibernateEntityDifferences != null && hibernateEntityDifferences.size() > 0 ) {
        	for (HibernateEntityDifference hibernateEntityDifference : hibernateEntityDifferences) {
        		if (hibernateEntityDifference.getEntityName() != null && 
        				hibernateEntityDifference.getEntityName().equalsIgnoreCase("contract.Segment") &&
        				hibernateEntityDifference.getEntityPK() != null) {
        			
        			Segment segment = Segment.findByPK(hibernateEntityDifference.getEntityPK());
        			
        			if (segment != null && segment.getProductStructureFK() != null) {
        				productStructurePK = segment.getProductStructureFK();
        				break;
        			}
        		} else if (hibernateEntityDifference.getEntityName() != null && 
        				hibernateEntityDifference.getEntityName().equalsIgnoreCase("contract.ContractClient") &&
        				hibernateEntityDifference.getEntityPK() != null) {
        			
        			Segment segment = Segment.findBy_ContractClientPK(hibernateEntityDifference.getEntityPK());
        			
        			if (segment != null && segment.getProductStructureFK() != null) {
        				productStructurePK = segment.getProductStructureFK();
        				break;
        			}
        		}
        	}
        }
        
        if (productStructurePK == null || productStructurePK.compareTo(0L) != 1) {
            productStructurePK = getNfProductKey();
        }
        
        try
        {
            spOutput = 
                    calculator.processScriptWithDocument(HibernateEntityDifferenceDocument.ROOT_ELEMENT_NAME, document, "NFChange", "*", "*", new EDITDate().getFormattedDate(), productStructurePK.longValue(), 
                                                         false);
        }
        catch (SPException e)
        {
            System.out.println(e);

            e.printStackTrace();
        }

        return spOutput;
    }

    /**
     * @see HibernateEntityDifferenceInterceptor#nfProductKey
     * @param nFCHANGE_PRODUCT_KEY
     */
    public static void setNfProductKey(Long nFCHANGE_PRODUCT_KEY)
    {
        nfProductKey = nFCHANGE_PRODUCT_KEY;
    }

    /**
     * @see HibernateEntityDifferenceInterceptor#nfProductKey
     */
    public static Long getNfProductKey()
    {
        if (nfProductKey == null)
        {
            ProductStructure productStructure = ProductStructure.findBy_CompanyName_MPN_GPN_AN_BCN("NFChange", "*", "*", "*", "*");

            nfProductKey = productStructure.getProductStructurePK();
        }

        return nfProductKey;
    }

    /**
     * True if the specified entity has been configured in our configuration file (most likely
     * EDITServicesoConfig.xml) to be tracked as a non-financial entity.
     * 
     * There is an additional constraint in that we don't track brand-new entities (
     * @param hibernateEntity
     * @return
     */
    private boolean isValidNonFinancialEntity(HibernateEntity hibernateEntity)
    {
        boolean isValidNonFinancialEntity = false;

        String className = hibernateEntity.getClass().getName();

        // Test 1: Is this entity registered in the configuration file?
        for (NonFinancialEntity nonFinancialEntity: ServicesConfig.getNonFinancialEntities())
        {
            String nonFinancialEntityclassName = nonFinancialEntity.getClassName();

            if (nonFinancialEntityclassName.equals(className))
            {
                isValidNonFinancialEntity = SessionHelper.isPersisted(hibernateEntity);
                
                break;
            }
        }

        return isValidNonFinancialEntity;
    }

    /**
     * After successfully committing the NF changes to the database and notifying
     * PRASE for Phase-2 changes, we want to permanently record the NF changes in the
     * ChangeHistory table.
     * 
     * All operations are performed outside of the existing user-generated transaction
     * as the operations in this method don't have access to the user-generated transaction; it
     * was already committed by the time this method is invoked.
     */
    private void generateChangeHistory(List<HibernateEntityDifference> hibernateEntityDifferences)
    {
        Session session = null;

        Transaction t = null;

        try
        {
            session = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);

            t = session.beginTransaction();

            for (HibernateEntityDifference hibernateEntityDifference: hibernateEntityDifferences)
            {
                for (HibernateFieldDifference hibernateFieldDifference: hibernateEntityDifference.getHibernateEntityFieldDifferences())
                {
                    ChangeHistory changeHistory = new ChangeHistory();

                    Object afterValue = hibernateFieldDifference.getNewValue();

                    if (afterValue != null)
                    {
                        changeHistory.setAfterValue(afterValue.toString());
                    }

                    Object beforeValue = hibernateFieldDifference.getOldValue();

                    if (beforeValue != null)
                    {
                        changeHistory.setBeforeValue(beforeValue.toString());
                    }

                    changeHistory.setEffectiveDate(hibernateEntityDifference.getEffectiveDate());

                    changeHistory.setFieldName(hibernateFieldDifference.getFieldName());

                    changeHistory.setMaintDateTime(new EDITDateTime());

                    changeHistory.setModifiedRecordFK(hibernateEntityDifference.getEntityPK());

                    changeHistory.setOperator(hibernateEntityDifference.getOperator());

                    changeHistory.setProcessDate(new EDITDate());

                    changeHistory.setTableName(hibernateEntityDifference.getEntityName());

                    changeHistory.setPendingStatus(hibernateEntityDifference.getPendingStatus());

                    session.saveOrUpdate(changeHistory);
                }
            }

            t.commit();
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            if (t != null)
            {
                if (t.isActive() && !t.wasCommitted())
                {
                    t.rollback();
                }
            }
            
            throw new RuntimeException(e);
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }
    }

    /**
     * Trap the set of differences of the Hibernate Entity being saved to its
     * current state in the DB>
     * @param object
     * @param serializable
     * @param newState
     * @param oldState
     * @param fieldNames
     * @param fieldTypes
     * @return
     */
    @Override
    public boolean onFlushDirty(Object object, Serializable serializable, Object[] newState, Object[] oldState, String[] fieldNames, Type[] fieldTypes)
    {
        // Certain parts of the system don't want the NF to engage. To accomplish this, a
        // DISABLE_NF_FRAMEWORK 'flag' would have been placed in ThreadLocal
        Boolean disableNFFramework = (Boolean) SessionHelper.getFromThreadLocal(SessionHelper.DISABLE_NF_FRAMEWORK);

        if (disableNFFramework == null)
        {
            HibernateEntity hibernateEntity = (HibernateEntity) object;

            if (isValidNonFinancialEntity(hibernateEntity))
            {
                HibernateEntityDifferenceEngine hibernateEntityDifferenceEngine = new HibernateEntityDifferenceEngine(hibernateEntity);

                FieldNameType fieldNameType = getTargetFieldNameType(hibernateEntity, fieldNames, fieldTypes);

                HibernateEntityDifference hibernateEntityDifference = hibernateEntityDifferenceEngine.generateHibernateEntityDifference(newState, oldState, fieldNameType.fieldNames.toArray(new String[fieldNameType.fieldNames.size()]), fieldNameType.fieldTypes.toArray(new Type[fieldNameType.fieldTypes.size()]));

                if (hibernateEntityDifference != null)
                {
                    List hibernateEntityDifferences = (List) SessionHelper.getFromThreadLocal(HIBERNATE_ENTITY_DIFFERENCES);

                    if (hibernateEntityDifferences == null)
                    {
                        hibernateEntityDifferences = new ArrayList<HibernateEntityDifference>();

                        SessionHelper.putInThreadLocal(HIBERNATE_ENTITY_DIFFERENCES, hibernateEntityDifferences);
                    }

                    hibernateEntityDifferences.add(hibernateEntityDifference);
                }
            }
        }

        return super.onFlushDirty(object, serializable, newState, oldState, fieldNames, fieldTypes);
    }

    /**
     * Entities with NonFinancial differences will have been sent to PRASE for validation. As part of the
     * PRASE Validation Framework, entities may fail certain validations. Such validations need to be
     * presented to the calling client. Since this entire NonFinancial framework is a low-level framework,
     * it is unwise to throw any exceptions here for fear that they will not be handled appropriately, or
     * they will be squashed. For this reason, they are placed in ThreadLocal via the SessionHelper to be
     * presented to the callling client (most likely through the HibernateFilter).
     * @param hardEdits
     */
    private void generateHardEditMessages(List<ValidationVO> hardEdits)
    {
        SessionHelper.putInThreadLocal(HARD_EDITS, hardEdits);
    }
    
    /**
     * During normal user-activity, some non-financial field will get flagged as future-dated by 
     * setting their corresonding ChangeHistory.EffectiveDate to some future date.
     * 
     * At times, we want to apply any future-dated non-financial
     * changes by comparing the specified effectiveDate to any Pending ChangeHistories with 
     * a ChangeHistory.EffectiveDate <= the specified effectiveDate.
     * @param effectiveDate
     */
    public void processPendingChangeHistories(EDITDate effectiveDate) throws Exception
    {
        ChangeHistory[] pendingChangeHistories = ChangeHistory.findSeparateBy_PendingStatus_EffectiveDateLTE("P", effectiveDate);
        
        // There can be multiple ChangeHistory records that need to be grouped since they represent individual fields of a single Hibernate entity.
        // The toggle key will be a change in ModifiedRecordFK since we are allowing several days of changes to be grouped together into the same modified record.
        Long currentModifiedRecordFK = null;
        
        Long lastModifiedRecordFK = null;
        
        HibernateEntity currentHibernateEntity = null;
        
        boolean currentHibernateEntityCommitted = false;
        
        for (ChangeHistory pendingChangeHistory:pendingChangeHistories)
        {
            currentHibernateEntityCommitted = false;
            
            currentModifiedRecordFK = pendingChangeHistory.getModifiedRecordFK();
            
            if (lastModifiedRecordFK == null)
            {
                lastModifiedRecordFK = currentModifiedRecordFK;
            }
            
            // Save the current entity and start again
            if (shouldCommitPendingChangeHistory(currentModifiedRecordFK, lastModifiedRecordFK))
            {
                commitPendingHibernateEntity(currentHibernateEntity);
                
                lastModifiedRecordFK = null;
                
                currentHibernateEntity = null;
                
                currentHibernateEntityCommitted = true;
            }
            
            if (currentHibernateEntity == null)
            {
                currentHibernateEntity = (HibernateEntity) SessionHelper.getFromSeparateSession(Class.forName(pendingChangeHistory.getTableName()), currentModifiedRecordFK, SessionHelper.EDITSOLUTIONS);
            }

            String currentFieldName = pendingChangeHistory.getFieldName();

            Method currentFieldGetter = currentHibernateEntity.getClass().getMethod("get" + currentFieldName, null);

            Class currentFieldType = currentFieldGetter.getReturnType();

            Method currentFieldSetter = currentHibernateEntity.getClass().getMethod("set" + currentFieldName, new Class[]{currentFieldType});
            
            Object newValue = SessionHelper.convertStringToData(false, pendingChangeHistory.getAfterValue(), currentFieldType);
            
            currentFieldSetter.invoke(currentHibernateEntity, new Object[]{newValue});
            
            getPendingChangeHistories().add(pendingChangeHistory);
        }
        
        // The above loop might exit before we can commit the "last" currentHibernateEntity.
        if (shouldCommitPendingChangeHistory(pendingChangeHistories, currentHibernateEntityCommitted))
        {
            commitPendingHibernateEntity(currentHibernateEntity);
        }
    }

    /**
     * A convenience method to detect if the "last" HibernateEntity built from the Pending ChangeHistories
     * was, in fact, committed or not.
     * @param pendingChangeHistories
     * @param currentHibernateEntityCommitted
     * @return
     */
    private boolean shouldCommitPendingChangeHistory(ChangeHistory[] pendingChangeHistories, boolean currentHibernateEntityCommitted)
    {
        return (!currentHibernateEntityCommitted && (pendingChangeHistories.length > 0));
    }
    
    /**
     * A convenience method extract the List associated to ThreadLocal.PENDING_CHANGE_HISTORY.
     * If the collection does not exist, an empty one is created.
     * @return
     */
    private List<ChangeHistory> getPendingChangeHistories()
    {
        List<ChangeHistory> pendingChangeHistories = (List) SessionHelper.getFromThreadLocal(PENDING_CHANGE_HISTORIES);
        
        if (pendingChangeHistories == null)
        {
            SessionHelper.putInThreadLocal(PENDING_CHANGE_HISTORIES, pendingChangeHistories = new ArrayList());
        }
        
        return pendingChangeHistories;
    }

    /**
     * A convenience method to flag a change in the specified ModifiedRecordPKs suggesting that a new ChangeHistory
     * record should be started and the old one committed.
     * @param currentModifiedRecordFK
     * @param lastModifiedRecordFK
     * @return
     */
    private boolean shouldCommitPendingChangeHistory(Long currentModifiedRecordFK, Long lastModifiedRecordFK)
    {
        return (currentModifiedRecordFK.longValue() != lastModifiedRecordFK.longValue());
    }    
    
    /**
     * Commits the specified HibernateEntity to the DB. It also deletes all ChangeHistories
     * that were used to construct the specified HibernateEntity. In essence, they will
     * be re-generated shortly as a result of saving the HibernateEntity.
     * @param hibernateEntity
     * @throws Exception
     */
    private void commitPendingHibernateEntity(HibernateEntity hibernateEntity) throws Exception
    {
        Session separateSession = null;
        
        org.hibernate.Transaction t = null;
        
        try
        {
            separateSession = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);
            
            t = separateSession.beginTransaction();
            
            separateSession.saveOrUpdate(hibernateEntity);
            
            List<ChangeHistory> pendingChangeHistories = getPendingChangeHistories();
            
            for (ChangeHistory pendingChangeHistory:pendingChangeHistories)
            {
                separateSession.delete(pendingChangeHistory);
            }
            
            t.commit();
        }
        catch (Exception e)
        {
            System.out.println(e);
            
            e.printStackTrace();
            
            if (t != null)
            {
                if (t.isActive() && !t.wasCommitted())
                {
                    t.rollback();
                }
            }          
            
            throw e;
        }
        finally
        {
            if (separateSession != null) separateSession.close();
            
            getPendingChangeHistories().clear();
        }        
    }
    
    /**
     * Singleton.
     * @return
     */
    public static HibernateEntityDifferenceInterceptor getInstance()
    {
        return hibernateEntityDifferenceInterceptor;
    }

    /**
     * The EDITServicesConfig.xml configured which Hibernate Entities to track for
     * differences. If the specific HibernateEntity.FieldNames is [not] specified,
     * then all of the fieldNames of the Hibernate Entity are condidered. If the
     * fieldNames [are] specified, then those are [ignored].
     * @param fieldNames
     * @return the fieldNames to target for the difference engine
     */
    private FieldNameType getTargetFieldNameType(HibernateEntity hibernateEntity, String[] fieldNames, Type[] fieldTypes)
    {
        FieldNameType fieldNameType = new FieldNameType();
        
        List<String> fieldNamesToIgnore = new ArrayList<String>();
        
        NonFinancialEntity nonFinancialEntity = null;
        
        String className = hibernateEntity.getClass().getName();

        // Get the configuration for the targeted NonFinancialEntity/HibernateEntity
        for (NonFinancialEntity currentNonFinancialEntity: ServicesConfig.getNonFinancialEntities())
        {
            String nonFinancialEntityclassName = currentNonFinancialEntity.getClassName();

            if (nonFinancialEntityclassName.equals(className))
            {
                nonFinancialEntity = currentNonFinancialEntity;
                
                break;
            }
        }
        
        if (nonFinancialEntity.getIgnoreField().length != 0)
        {
            fieldNamesToIgnore = Arrays.asList(nonFinancialEntity.getIgnoreField());
        }
        
        for (int i = 0; i < fieldNames.length; i++)
        {
            String currentFieldName = fieldNames[i];
            
            if (!fieldNamesToIgnore.contains(fieldNames[i]))
            {
                fieldNameType.fieldNames.add(currentFieldName);
                
                fieldNameType.fieldTypes.add(fieldTypes[i]);
            }
        }
        
        return fieldNameType;
    }

    /**
     * Convenience class to allow multiple return types when evaluating
     * Field Name/Type to process for differences.
     */
    private class FieldNameType
    {
        public  List<String> fieldNames = new ArrayList<String>();
        
        public List<Type> fieldTypes = new ArrayList<Type>();
    }

}
