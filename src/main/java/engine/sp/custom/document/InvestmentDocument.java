package engine.sp.custom.document;

import contract.Bucket;
import contract.Investment;
import contract.InvestmentAllocation;
import contract.Segment;

import edit.common.EDITDate;
import edit.common.EDITMap;
import edit.common.vo.AreaValueVO;

import edit.services.db.hibernate.SessionHelper;
import engine.sp.ScriptProcessor;

import event.ClientSetup;
import event.ContractSetup;
import event.EDITTrx;
import event.GroupSetup;
import event.InvestmentAllocationOverride;

import fission.utility.DOMUtil;
import fission.utility.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;


/**
 * This class is a refactoring of the contract.dm.composer.VOComposer.composeInvestmentVO(..) method.
 * When building this Document, each Investment of the target Segment has to be considered
 * individually to determine if it should even be added, if InvestmentAllocationOverrides should be
 * used, etc. The rules are dizzying, but the general principle is that we are trying to determine:
 * 1. Which Investments apply.
 * 2. Which InvestmentAllocations apply (may have overrides).
 * 3. Which Buckets to apply.
 *
 * The structure is:
 *
 * InvestmentVO
 * InvestmentVO.InvestmentAllocation (may have been driven by the InvestmentAllocationOverrides if any)
 * InvestmentVO.BucketVO
 *
 * Key to under
 */
public class InvestmentDocument extends PRASEDocBuilder
{
    /**
     * There are three possible building scenarios for building the
     * InvestmentDocument. They are defined in later code, and are driven
     * by each Investment of interest for the associated Segment.
     */
    public enum InvestmentCase
    {
        A,
        B,
        C,
        D,
        E,
        F
        ;
    }
    
    /**
     * The driving EDITTrxPK.
     */
    private Long editTrxPK;

    /**
     * The Segment Identifier for the Investments.
     */
    private Long segmentPK;
    
    /**
     * The key name of the driving EDITTrxPK.
     */
    public static final String BUILDING_PARAMETER_EDITTRXPK = "EDITTrxPK";

    public static final String BUILDING_PARAMETER_SEGMENTPK = "SegmentPK";
    
    /**
     * The parameters that will be extracted from working storage to build this document.
     */
    private static final String[] buildingParameterNames = {BUILDING_PARAMETER_EDITTRXPK};
    
    public InvestmentDocument(){}
    
    public InvestmentDocument(Long editTrxPK)
    {
        super(new EDITMap(BUILDING_PARAMETER_EDITTRXPK, editTrxPK.toString()));
        
        this.editTrxPK = editTrxPK;
    }
    
    /**
     * Constructor. The specified building parameters is expected to contain
     * the keyed EDITTrxPK.
     * @param buildingParameters
     * @see #BUILDING_PARAMETER_EDITTRXPK
     */
    public InvestmentDocument(Map<String, String> buildingParameters)
    {
        super(buildingParameters);
        
        this.editTrxPK = new Long(buildingParameters.get(BUILDING_PARAMETER_EDITTRXPK));
        
        this.segmentPK = new Long(buildingParameters.get(BUILDING_PARAMETER_SEGMENTPK));
    }
    
    /**
     * Builds all InvestmentVOs for the specied Segment. All BucketVOs are included with BucketAllocationVOs where overrides are supplied.
     * InvestmentVOs include their InvestmentAllocationVO by OverrideStatus, unless overrides are supplied.
     * @return
     * @throws java.lang.Exception
     */
    public void build()
    {
        if (!isDocumentBuilt())
        {
            Element investmentDocElement = new DefaultElement(getRootElementName());
            
            Investment[] investments = null;
            
            EDITTrx editTrx = null;

            if (isNewTransaction())
            {
                editTrx = buildEntitiesAndGetEDITTrxFromGroupSetupDocument();
            }
            else
            {
                editTrx = getEDITTrx();
            }
            
            investments = getInvestments(editTrx);
            
            if (investments != null && investments.length > 0) {
	            for (Investment investment: investments)
	            {
	                InvestmentCase investmentCase = getInvestmentCase(investment, editTrx);
	                
	                buildInvestmentElement(investment, investmentCase, investmentDocElement, editTrx);
	            }
            }
            
            setRootElement(investmentDocElement);
            
            setDocumentBuilt(true);
        }
    }

    /* At this point there may be a editTrxPK already in DB
     * but to pick all the investment which may have been just added by user and not yet been saved to DB
     * investmentDoc needs to be build from groupsetupDoc as if it's for a new TRansaction
     * groupSetupDocument must be built before this gets built
     */
    public void buildInvestmentDocFromCloudland(ScriptProcessor sp)
    {
        if (!isDocumentBuilt())
        {
            Element investmentDocElement = new DefaultElement(getRootElementName());

            Investment[] investments = null;

            EDITTrx editTrx = null;


            editTrx = buildEntitiesAndGetEDITTrxFromGroupSetupDocumentForCloudland(sp);

            investments = getInvestments(editTrx);

            if (investments != null)
            {
                for (Investment investment : investments)
                {
                    InvestmentCase investmentCase = getInvestmentCase(investment, editTrx);

                    buildInvestmentElement(investment, investmentCase, investmentDocElement, editTrx);
                }
            }

            setRootElement(investmentDocElement);

            setDocumentBuilt(true);

            //System.out.println("investmentDocElement:" + investmentDocElement.asXML());//remove??
        }
    }

    private EDITTrx buildEntitiesAndGetEDITTrxFromGroupSetupDocumentForCloudland(ScriptProcessor sp)
    {
        //PRASEDocBuilder groupSetupDocument = (PRASEDocBuilder)getSPParams().getDocumentByName("GroupSetupDocVO");
         PRASEDocBuilder groupSetupDocument = (PRASEDocBuilder)sp.getSPParams().getDocumentByName("GroupSetupDocVO");

        Element groupSetupElement =
            (Element)DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO", groupSetupDocument).get(0);

        GroupSetup groupSetup =
            (GroupSetup)SessionHelper.mapToHibernateEntity(GroupSetup.class, groupSetupElement, SessionHelper.EDITSOLUTIONS);

        Element contractSetupElement =
            (Element)DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO.ContractSetupVO", groupSetupDocument).get(0);

        ContractSetup contractSetup =
            (ContractSetup)SessionHelper.mapToHibernateEntity(ContractSetup.class, contractSetupElement, SessionHelper.EDITSOLUTIONS);

        Element clientSetupElement =
            (Element)DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO.ContractSetupVO.ClientSetupVO", groupSetupDocument).get(0);

        ClientSetup clientSetup =
            (ClientSetup)SessionHelper.mapToHibernateEntity(ClientSetup.class, clientSetupElement, SessionHelper.EDITSOLUTIONS);

        Element editTrxElement =
            (Element)DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO.ContractSetupVO.ClientSetupVO.EDITTrxVO", groupSetupDocument).get(0);

        EDITTrx editTrx =
            (EDITTrx)SessionHelper.mapToHibernateEntity(EDITTrx.class, editTrxElement, SessionHelper.EDITSOLUTIONS);

        List<InvestmentAllocationOverride> investmentAllocationOverrides =
            new ArrayList<InvestmentAllocationOverride>();

        List<Element> investmentAllocationOverrideElements =
            DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO.ContractSetupVO.InvestmentAllocationOverrideVO", groupSetupDocument);

        for (Element investmentAllocationOverrideElement: investmentAllocationOverrideElements)
            {
                InvestmentAllocationOverride investmentAllocationOverride =
                    (InvestmentAllocationOverride)SessionHelper.mapToHibernateEntity(InvestmentAllocationOverride.class, investmentAllocationOverrideElement, SessionHelper.EDITSOLUTIONS);

                investmentAllocationOverrides.add(investmentAllocationOverride);
            }

        groupSetup.addContractSetup(contractSetup);
        contractSetup.addClientSetup(clientSetup);
        clientSetup.addEDITTrx(editTrx);

        for (InvestmentAllocationOverride investmentAllocationOverride: investmentAllocationOverrides)
            {
                contractSetup.addInvestmentAllocationOverride(investmentAllocationOverride);
            }

        return editTrx;
    }
    
    /**
     * Builds object model from the GroupSetup document.
     */
    // Always expects to have one and only one GroupSetup, ContractSetup, ClientSetup and edittrx.
    // Always must have GroupSetup, ContractSetup, ClientSetup and EDITTrx
    private EDITTrx buildEntitiesAndGetEDITTrxFromGroupSetupDocument()
    {
        PRASEDocBuilder groupSetupDocument = (PRASEDocBuilder) getSPParams().getDocumentByName("GroupSetupDocVO");

        Element groupSetupElement = (Element) DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO", groupSetupDocument).get(0);

        GroupSetup groupSetup = (GroupSetup) SessionHelper.mapToHibernateEntity(GroupSetup.class, groupSetupElement, SessionHelper.EDITSOLUTIONS);

        Element contractSetupElement = (Element) DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO.ContractSetupVO", groupSetupDocument).get(0);

        ContractSetup contractSetup = (ContractSetup) SessionHelper.mapToHibernateEntity(ContractSetup.class, contractSetupElement, SessionHelper.EDITSOLUTIONS);

        Element clientSetupElement = (Element) DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO.ContractSetupVO.ClientSetupVO", groupSetupDocument).get(0);

        ClientSetup clientSetup = (ClientSetup) SessionHelper.mapToHibernateEntity(ClientSetup.class, clientSetupElement, SessionHelper.EDITSOLUTIONS);

        Element editTrxElement = (Element) DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO.ContractSetupVO.ClientSetupVO.EDITTrxVO", groupSetupDocument).get(0);

        EDITTrx editTrx = (EDITTrx) SessionHelper.mapToHibernateEntity(EDITTrx.class, editTrxElement, SessionHelper.EDITSOLUTIONS);

        List<InvestmentAllocationOverride> investmentAllocationOverrides = new ArrayList<InvestmentAllocationOverride>();

        List<Element> investmentAllocationOverrideElements = DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO.ContractSetupVO.InvestmentAllocationOverrideVO", groupSetupDocument);

        for (Element investmentAllocationOverrideElement : investmentAllocationOverrideElements)
        {
            InvestmentAllocationOverride investmentAllocationOverride = (InvestmentAllocationOverride) SessionHelper.mapToHibernateEntity(InvestmentAllocationOverride.class, investmentAllocationOverrideElement, SessionHelper.EDITSOLUTIONS);

            investmentAllocationOverrides.add(investmentAllocationOverride);
        }

        groupSetup.addContractSetup(contractSetup);
        contractSetup.addClientSetup(clientSetup);
        clientSetup.addEDITTrx(editTrx);

        for (InvestmentAllocationOverride investmentAllocationOverride : investmentAllocationOverrides)
        {
            contractSetup.addInvestmentAllocationOverride(investmentAllocationOverride);
        }

        return editTrx;
    }
    
    private InvestmentAllocationOverride[] getInvestmentAllocationOverridesFromGroupSetupDocument()
    {
        List<InvestmentAllocationOverride> investmentAllocationOverrides = new ArrayList<InvestmentAllocationOverride>();
    
        PRASEDocBuilder groupSetupDocument = (PRASEDocBuilder) getSPParams().getDocumentByName("GroupSetupDocVO");
        
        List<Element> investmentAllocationOverrideElements = DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO.ContractSetupVO.InvestmentAllocationOverrideVO", groupSetupDocument);
        
        for (Element investmentAllocationOverrideElement : investmentAllocationOverrideElements)
        {
            InvestmentAllocationOverride investmentAllocationOverride = (InvestmentAllocationOverride) SessionHelper.mapToHibernateEntity(InvestmentAllocationOverride.class, investmentAllocationOverrideElement, SessionHelper.EDITSOLUTIONS);
            
            investmentAllocationOverrides.add(investmentAllocationOverride);            
        }
        
        return investmentAllocationOverrides.toArray(new InvestmentAllocationOverride[investmentAllocationOverrides.size()]);
    }

    private EDITTrx getEDITTrxFromGroupSetupDocument()
    {
        PRASEDocBuilder groupSetupDocument = (PRASEDocBuilder) getSPParams().getDocumentByName("GroupSetupDocVO");
        
        List<Element> editTrxElements = DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO.ContractSetupVO.ClientSetupVO.EDITTrxVO", groupSetupDocument);
        
        // At this point of time we are supporting only EDITTrx for a GroupSetup and EDITTrx should always exist.
        // GroupSetupVO.ContractSetupVO.ClientSetupVO.EDITTrxVO - this chain will have one child always.
        Element editTrxElement = editTrxElements.get(0);
        
        EDITTrx editTrx = (EDITTrx) SessionHelper.mapToHibernateEntity(EDITTrx.class, editTrxElement, SessionHelper.EDITSOLUTIONS);
        
        return editTrx;
    }
    
    /**
     * Getter.
     * @see #editTrxPK
     */
    private Long getEDITTrxPK()
    {
        return editTrxPK;
    }
    
    /**
     * A convenience method to get the EDITTrx structure.
     * the [one] EDITTrx that is contained.
     * @return
     */
    private EDITTrx getEDITTrx()
    {
        return EDITTrx.findSeparateBy_EDITTrxPK_V1(getEDITTrxPK());
    }
    
    /**
     * A convenience method to get the Set of InvestmentAllocationOverrides
     * contained (somewhere) in this editTrx.
     * @param editTrx
     * @return
     */
    private Set<InvestmentAllocationOverride> getInvestmentAllocationOverrides(EDITTrx editTrx)
    {
        return editTrx.getClientSetup().getContractSetup().getInvestmentAllocationOverrides();
    }
    
    /**
     * Builds the Investment Element. There are five building scenarios as
     * Case A, B, C, D and E.
     * Cases A, B, D and E blindly add the Investment Element.
     * Case C evaluates the Investment before adding it to this Document.
     *
     * @see #getInvestmentCase(Investment, EDITTrx)
     * @param investment
     * @return
     */
    private void buildInvestmentElement(Investment investment, InvestmentCase investmentCase, Element investmentDocElement, EDITTrx editTrx)
    {
        Element investmentElement = null;
        
        switch (investmentCase)
        {
            case A:
            case B:
            case D:
                investmentElement = investment.getAsElement();
                break;
                
            case C:
                if (systemRemovalBucketExists(investment.getBuckets()))
                {
                    investmentElement = investment.getAsElement();
                }
                break;
        }
        
        // It's a valid scenario for investment element to be null. It implies we do not want to include the investment.
        if (investmentElement != null)
        {
            investmentDocElement.add(investmentElement);
            
            buildBucketElement(investment.getBuckets(), investmentElement, investmentCase, investment.getSegment(), editTrx);
            
            if (investmentCase != InvestmentCase.D)
            {
                buildInvestmentAllocation(investment, investmentElement);    
            }
        }
    }
    
    /**
     * Builds the InvestmentAllocation. Case A deals with overrides.
     * Cases B and C simply add the InvestmentAllocation Element without concern.
     * @param investment
     * @param investmentElement
     */
    private void buildInvestmentAllocation(Investment investment, Element investmentElement)
    {
        for (InvestmentAllocation investmentAllocation: investment.getInvestmentAllocations())
        {
            investmentElement.add(investmentAllocation.getAsElement());
        }
    }
    
    /**
     * Builds the Bucket Element. Cases A and B add the Bucket Element without concern.
     * Case C needs to make sure that the Bucket meets its System requirements.
     */
    private void buildBucketElement(Set<Bucket> buckets, Element investmentElement, InvestmentCase investmentCase, Segment segment, EDITTrx editTrx)
    {
        Bucket[] bucketsArray = buckets.toArray(new Bucket[buckets.size()]);
    
        bucketsArray = sortBuckets(bucketsArray, segment, editTrx);
    
        for (Bucket bucket: bucketsArray)
        {
            if (investmentCase.equals(InvestmentCase.A) || investmentCase.equals(InvestmentCase.B) ||
                investmentCase.equals(InvestmentCase.D) || investmentCase.equals(InvestmentCase.E))
            {
                Element bucketElement = bucket.getAsElement();
                
                investmentElement.add(bucketElement);
            }
            else if (investmentCase.equals(InvestmentCase.C))
            {
                if (isSystemRemovalBucket(bucket))
                {
                    Element bucketElement = bucket.getAsElement();
                    
                    investmentElement.add(bucketElement);
                }
            }
        }
    }
    
    /**
     * Loops through the specified Buckets
     * @param buckets
     * @return
     */
    private boolean systemRemovalBucketExists(Set<Bucket> buckets)
    {
        boolean systemRemovalBucketExists = false;
        
        for (Bucket bucket: buckets)
        {
            if (isSystemRemovalBucket(bucket))
            {
                systemRemovalBucketExists = true;
                
                break;
            }
        }
        
        return systemRemovalBucketExists;
    }
    
    /**
     * A Bucket is a System Removal Bucket (I made this name up) iff:
     * 1. The Bucket.BucketSourceCT != null.
     * 2. The Bucket.BucketSourceCT = BUCKETSOURCECT_TRANSFER
     * 3. The Bucket.CumUnits > 0.00
     * @param bucket
     * @return
     */
    private boolean isSystemRemovalBucket(Bucket bucket)
    {
        boolean isSystemRemovalBucket = false;
        
        if (bucket.getBucketSourceCT() != null)
        {
            if (bucket.getBucketSourceCT().equalsIgnoreCase(Bucket.BUCKETSOURCECT_TRANSFER))
            {
                if (bucket.getCumUnits().isGT("0.00"))
                {
                    isSystemRemovalBucket = true;
                }
            }
        }
        
        return isSystemRemovalBucket;
    }
    
    /**
     * Tests to see if the specified TransactionTypeCT is an MF or FD or DE or HDTH transaction.
     * @param transactionTypeCT
     * @return true if the tranactionTypeCT of MF, FD, DE, HDTH
     */
    private boolean isMFOrFDOrDEOrHDTHTrx(String transactionTypeCT)
    {
        return (transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_MONTHLYFEE) || transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_DEATH) || 
                transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_FACEDECREASE) || transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_DEATH));
    }
    
    /**
     * InvestmentCase-A. If there are InvestmentAllocationOverrides, then use the overrides and
     * continue with Buckets without concern.
     *
     * InvestmentCase-B. If there are no InvestmentAllocationOverrides and:
     * a. The specified EDITTrx [is not] a RemovalTransaction
     * b. Or it [is] a RemovalTransaction, but happens to be of type (MF, HDTH),
     * c. Or it [is] a RemovalTransaction is not of type (MF, HDTH) and is a "System" Investment
     *
     * then use the InvestmentAllocation(s) as is and continue with the Buckets without concern.
     *
     * InvestmentCase-C. If the specified EDITTrx
     * a. [IS] a RemovalTransaction
     * b. And it [is] type (MD)
     * c. And it is [not] a "System" Investment,
     *
     * then [before] we accept the Investment
     * we need to verify its Bucket situation. The Buckets must satisfy three criteria:
     *    i.   Bucket.BucketSourceCT != null
     *    ii.  Bucket.BucketSourceCT = 'Transfer'
     *    iii. Bucket.CumUnits > 0.00.
     *
     * If the above Bucket conditions hold true, the Investment is accepted [and] only
     * the Buckets which satisfied the test conditions are used.
     *
     * InvestmentCase - F. When an investment need not be included in the document.
     * a. Is 'ML' or 'LO' or 'LS' transaction and Investment is a system fund but not loan fund.
     * b. Is 'WI' transaction and Investment is a system fund.
     *
     * @param investment
     * @param editTrx
     * @return the proper InvestmentCase based on the aforementioned Cases
     */
    private InvestmentDocument.InvestmentCase getInvestmentCase(Investment investment, EDITTrx editTrx)
    {
        InvestmentCase investmentCase = null;
        
        // Cases when Investment need to be excluded.
        if (((editTrx.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_MONTHLY_COLLATERALIZATION) ||
        editTrx.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN) ||
        editTrx.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LUMPSUM) ||
        editTrx.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_FULLSURRENDER)) &&
        investment.isSystemFund() && !investment.isLoanFund()) ||
        ((editTrx.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_WITHDRAWAL) && investment.isSystemFund())))
        {
            investmentCase = InvestmentCase.F;
        }       
        
        if (investmentCase == null)
        {
            if (investment.isGeneralAccountFund())
            {
                investmentCase = InvestmentCase.D;
            }
            
            else if (editTrx.getClientSetup().getContractSetup().hasInvestmentAllocationOverrides())        
            {
                investmentCase = InvestmentCase.A;
            }
            
            else if (!editTrx.isRemovalTransaction() ||
                    (editTrx.isRemovalTransaction() && isMFOrFDOrDEOrHDTHTrx(editTrx.getTransactionTypeCT())) ||
                    (editTrx.isRemovalTransaction() && !investment.isSystemFund()) ||
                    (!editTrx.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SYSTEMATIC_WITHDRAWAL) &&
                    !editTrx.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_WITHDRAWAL) &&
                    investment.isLoanFund()) ||
                    (editTrx.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN) &&
                    !investment.isHedgeFund() && !investment.isSystemFund()) ||
                    ((editTrx.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_CAPITALIZATION) ||
                    editTrx.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT)) &&
                    investment.isLoanFund()))
            {
                investmentCase = InvestmentCase.B;
            }
            
            else if (editTrx.isRemovalTransaction() && editTrx.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_MODALDEDUCTION) && investment.isSystemFund())
            {
                investmentCase = InvestmentCase.C;
            }
        }
        
        return investmentCase;
    }
    
    /**
     *
     * @return
     */
    public String getRootElementName()
    {
        return "InvestmentDocVO";
    }
    
    /**
     * A convenience method to return the one ContractSetup associated with the specified
     * Investment.
     * @param investment
     * @return
     */
    private ContractSetup getContractSetup(Investment investment)
    {
        Segment segment = investment.getSegment();
        
        ContractSetup contractSetup = segment.getContractSetups().iterator().next();
        
        return contractSetup;
    }
    
    /**
     * Convenience method to get the Investments associated with the InvestmentAllocationOverrides (if any)
     * or just the Primary Investmenst if there are no overrides. The Investment returned is a composite
     * structure with the necessary child entities.
     *
     * There is an additional clause that if the Investment is a "General Fund" [and] the specified EDITTrx is [not]
     *
     * @param editTrx
     * @return
     */
    private Investment[] getInvestments(EDITTrx editTrx)
    {
        Investment[] investments = null;
        
        if (editTrx.getClientSetup().getContractSetup().hasInvestmentAllocationOverrides())
        {
            ContractSetup contractSetup = editTrx.getClientSetup().getContractSetup();
         
            // Get investments associated with the InvestmentAllocationOverrides of the ContractSetup
            // At this point of time the code expects to have Investments in the database.
            investments = getInvestmentsFromInvestmentAllocationOverrides(contractSetup);
             
            Long segmentPK = editTrx.getClientSetup().getContractSetup().getSegmentFK();

            Investment generalAccountInvestment = getGeneralAccountInvestment(editTrx, segmentPK);
        
            if (generalAccountInvestment != null)
            {
                investments = (Investment[]) Util.joinArrays(investments, new Object[]{generalAccountInvestment}, Investment.class);
            }
            if (editTrx.isPartialRemovalTransaction())
            {
                investments = getInvestmentsForPartialRemoval(investments, segmentPK);
            }
        }

        else if (editTrx.isPartialRemovalTransaction())
        {
            investments = getInvestmentsForPartialRemoval(investments, segmentPK);
        }

        else
        {
            // Just get the 'P'rimary InvestmentAllocations.
            // investments = Investment.findSeparateBy_EDITTrx(editTrx);
            investments = getInvestments(segmentPK);
        }
        
        return investments;
    }
    
    /**
     * Returns the investments from InvestmentAllocationOverrides.
     * @param contractSetup
     * @return
     */
    private Investment[] getInvestmentsFromInvestmentAllocationOverrides(ContractSetup contractSetup)
    {
        List<Long> investmentAllocationFKs = new ArrayList<Long>();
        
        Set<InvestmentAllocationOverride> investmentAllocationOverrides = contractSetup.getInvestmentAllocationOverrides();
    
        for (InvestmentAllocationOverride investmentAllocationOverride : investmentAllocationOverrides)
        {
            Long investmentAllocationFK = investmentAllocationOverride.getInvestmentAllocationFK();
            
            investmentAllocationFKs.add(investmentAllocationFK);
        }

        return Investment.findSeperateBy_InvestmentPK_V2(investmentAllocationFKs);
    }
    
    /**
     * Returns the investments for a segment.
     * @param segmentPK
     * @return
     */
    private Investment[] getInvestments(Long segmentPK)
    {
        return Investment.findSeperateBy_SegmentPK_V1(segmentPK);
    }

    /**
     *  PartialRemovals need all investments plus the overrides to calculate the TaxableIncome and TaxableBenefit.
     *  Buckets must have value to be included here
     * @param investments
     * @param segmentPK
     * @return
     */
    private Investment[] getInvestmentsForPartialRemoval(Investment[] investments, Long segmentPK)
    {
        // Investment[] allInvestments = Investment.findSeparateBy_EDITTrx_V3(editTrx);
        Investment[] allInvestments = Investment.findSeperateBy_SegmentPK_V3(segmentPK);

        if (allInvestments != null && allInvestments.length > 0)
        {
            investments = joinArrays(investments, allInvestments);
        }

        return investments;
    }

    /**
     * When overrides exist join the override with the rest of the Investments on the contract
     * @param investments
     * @param allInvestments
     * @return
     */
    private Investment[] joinArrays(Investment[] investments, Investment[] allInvestments)
    {
        List<Investment> investmentList = null;
        List saveOccurs = new ArrayList(allInvestments.length);

        if (investments != null)
        {
            investmentList = new ArrayList<Investment>(Arrays.asList(investments));

            for (int i = 0; i < investments.length; i++)
            {
                Long investmentPK = investments[i].getInvestmentPK();
                boolean investmentExists = false;
                Investment currentInvestment = null;

                for (int j = 0; j < allInvestments.length; j++)
                {
                    currentInvestment =  allInvestments[j];
                    if (investmentPK.equals(currentInvestment.getInvestmentPK()))
                    {
                        investmentExists = true;
                        saveOccurs.add(j);
                        break;
                    }
                }

                if (!investmentExists)
                {
                    investmentList.add(currentInvestment);
                }
            }

            //Merge the rest
            Integer[] occurs = (Integer[])saveOccurs.toArray(new Integer[saveOccurs.size()]);
            if (investments.length < allInvestments.length)
            {
                for (int i = 0; i < allInvestments.length; i++)
                {
                    boolean investmentExists = false;

                    for (int j = 0; j < occurs.length; j++)
                    {
                        if (i == occurs[j].intValue())
                        {
                            investmentExists = true;
                        }
                    }
                    if (!investmentExists)
                    {
                        investmentList.add(allInvestments[i]);
                    }
                }
            }
        }
        else
        {
            investmentList = new ArrayList<Investment>(Arrays.asList(allInvestments));
        }

        return  investmentList.toArray(new Investment[investmentList.size()]);
    }
    
    /**
     * Returns true if the driving transaction is new.
     * @return
     */
    private boolean isNewTransaction()
    {
        return this.editTrxPK == 0 ? true : false;
    }

    /**
     * Every InvestmentDocument should include the Investment associatd with the General Account if it exists. The
     * rules are:
     *
     * 1. The Investment associated with the General Account needs to exist for the associated policy.
     * 2. The current EDITTrx can't be of type WI or SW.
     */
    private Investment getGeneralAccountInvestment(EDITTrx editTrx, Long segmentPK)
    {
        Investment investment = null;
        
        String transactionTypeCT = editTrx.getTransactionTypeCT();
        
        if (!transactionTypeCT.equals(EDITTrx.TRANSACTIONTYPECT_WITHDRAWAL) && !transactionTypeCT.equals(EDITTrx.TRANSACTIONTYPECT_SYSTEMATIC_WITHDRAWAL))
        {
            // investment = Investment.findSeparateBy_EDITTrx_V2(editTrx);
            investment = Investment.findSeperateBy_SegmentPK_V2(segmentPK);
        }
        
        return investment;
    }
    
    /**
    * Sorts Buckets by DepositDate.
    * @param buckets
    * @param segment
    * @param editTrx
    */
    private Bucket[] sortBuckets(Bucket[] buckets, Segment segment, EDITTrx editTrx)
    {
        String sortOrder = getSortOrder(segment, editTrx);
    
        if (buckets != null)
        {
            buckets = (Bucket[]) Util.sortObjects(buckets, new String[]{"getDepositDate"});

            if (sortOrder != null && sortOrder.equalsIgnoreCase("LIFO"))
            {
                List<Bucket> bucketsAsList = Arrays.asList(buckets);
            
                Collections.reverse(bucketsAsList);
                
                buckets = bucketsAsList.toArray(new Bucket[bucketsAsList.size()]);
            }
        }

        return buckets;
    }
    
    /**
     * Returns the sort order from area table.
     * @param segment
     * @param editTrx
     * @return
     */
    private String getSortOrder(Segment segment, EDITTrx editTrx)
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        String grouping = "TRANSACTION";
        String field = "REMOVALORDER";
        EDITDate effectiveDate = editTrx.getEffectiveDate();

        AreaValueVO areaValueVO = engineLookup.getAreaValue(segment.getProductStructureFK(), segment.getIssueStateCT(), grouping, effectiveDate, field, segment.getQualNonQualCT());

        String sortOrder = null;

        if (areaValueVO != null)
        {
            sortOrder = areaValueVO.getAreaValue();
        }

        return sortOrder;
    }

	public String[] getBuildingParameterNames()
    {
        return buildingParameterNames;
    }
}
