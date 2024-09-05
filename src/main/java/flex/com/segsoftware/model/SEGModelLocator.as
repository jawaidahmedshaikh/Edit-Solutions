package com.segsoftware.model
{
	import com.adobe.cairngorm.model.ModelLocator;
	import com.segsoftware.model.conversion.ConversionData;
	import com.segsoftware.model.conversion.ConversionJob;
	import com.segsoftware.model.conversion.ConversionResult;
	import com.segsoftware.model.conversion.ConversionTemplate;
	import com.segsoftware.model.conversion.GroupNode;
	import com.segsoftware.model.scriptProcessor.*;
	import com.segsoftware.view.conversion.GroupDesign;
	
	import mx.collections.ArrayCollection;
	import mx.collections.XMLListCollection;
	import mx.core.UIComponent;
	
	/**
	 * A singleton that stores a reference to the "model" state 
	 * of this application.
	 * 
	 * It is reasonable to assume that many views in this application will need
	 * access to the same model information, or to the same state information.
	 * In this RIA, no view "owns" the model data, but merely represents its
	 * state in many ways.
	 * 
	 * In a hierarchical RIA such as flex, it can be brittle to try to gain access to
	 * a model retrieved by one view component and render it in another. If
	 * the view hierarchy changes, then access to the model will be changed
	 * by definition.
	 * 
	 * By placing all models in this singleton, model state is flattened and
	 * much more manageable.
	 * 
	 * By making this model locator class [Bindable], then all "listening" views 
	 * will be updated whenever its underlying model changes.
	 * 
	 */
	[Bindable]
	public class SEGModelLocator implements ModelLocator
	{
	  // VOs used within the system...
	  public static var MODEL_BATCHPROGRESSLOG:String = "BatchProgressLog";
	  
	  public static var MODEL_BATCHPRODUCTLOG:String = "BatchProductLog";
	  
	  public static var MODEL_CLIENTDETAIL:String = "ClientDetail";
	  
	  public static var MODEL_CLIENTADDRESS:String = "ClientAddress";
	  
	  public static var MODEL_TAXINFORMATION:String = "TaxInformation";
		
      private static var modelLocator : SEGModelLocator;
      
      // Constants for managaging page or model state.
      public static var BILLING_ADJUST_MODE_BY_PAYOR:String = "BY_PAYOR";
      
      public static var BILLING_ADJUST_MODE_BY_SEGMENT:String = "BY_SEGMENT";
      
     /**
	  * The set of all BillGroups. This is likely filtered, but typically
	  * starts by representing all BillGroups that are "Not Paid".
	  */ 
	  public var billGroupVOs:XMLListCollection = new XMLListCollection();   
	 
	 /**
	  * The selected or "active" BillGroup in the application.
	  */ 
	  public var selectedBillGroupVO:XML;    
	  
	  /**
	  * The Payors dictated by the currently selected BillGroup.
	  */ 
	  public var payorClientDetailVOs:XMLListCollection = new XMLListCollection();
	  
	 /**
	  * The selected or "active" Payor for the application.
	  */ 
	  public var selectedPayorClientDetailVO:XML;
	  
	 /**
	  * The BillVOs associated with the currently selected BillGroup.
	  */  
	  public var batchContractBillVOs:XMLListCollection = new XMLListCollection();
	  
	 /**
	  * The current set of BatchContractSetupVO when beginning
	  * the batch segment explosion process.
	  */
	  public var batchContractSetupVOs:XMLListCollection = new XMLListCollection(); 
	  
	  /**
	  * The current set of GIO options obtained for a specific client
	  */
	  public var gioOptions:XMLListCollection = new XMLListCollection(); 
	  
	   /**
	  * The current set of RatedGenderCT obtained for a specific client
	  */
	  public var ratedGenderCT:XMLListCollection = new XMLListCollection(); 	
	  
	 /**
	  *	The CheckBox for beneficiaries equally split.  Note that this is a ui object which is
	  * different from the normal "model-backing-the-ui".  The check box is an oddity since it
	  * doesn't really belong to a VO. But we need access to it from the command object to 
	  * control its value (ex. AppEntryCommand sets it to false if any of its commands executes 
	  * successfully) 
	  */
	  public var splitEquallyCheckBox:Object;	  
	  
	  /**
	   * Holder for selected roles to compare for duplicate client assignment.
	   */
	  public var selectedClientRolesHolder:Array = new Array();
	  
     /**
	  * The selected BatchContractSetupVO in the set
	  * of all BatchContractSetupVOs.
	  */
	  private var _selectedBatchContractSetupVO:XML; 


	  
	  public function set selectedBatchContractSetupVO(selectedBatchContractSetupVO:XML):void
	  {
	  	_selectedBatchContractSetupVO = selectedBatchContractSetupVO;
	  }
	  
	  public function get selectedBatchContractSetupVO():XML
	  {
	  	return _selectedBatchContractSetupVO;
	  }
	  
	  /**
	  * When adding a new BatchContractSetup, it is necessary to base
	  * it on an existing Group-ContractGroup. Upon searching, the
	  * user will select from the returned set.
	  */ 
	  public var searchedForGroupContractGroupVOs:XMLListCollection = new XMLListCollection();
  
	  /**
	  * The logged in operator.  Gets set in the init of the application from the jsp page.
	  * Do not set a default value here.  Want it to be unset so components that bind to it
	  * will wait until the app's init is called
	  */ 
	  public var operator:String;
	  
	  /**
	  * A BatchContractSetup has a set of Candidate AgentHierarchies
	  * via its associated Case-ContractSetup.
	  */ 
	  public var candidateAgentHierarchyVOs:XMLListCollection = new XMLListCollection();
	  
	  /**
	  * In BatchContractSetup, the user will select AgentHierarchies from
	  * the list of candidate AgentHierarchies.
	  */ 
	  public var selectedCandidateAgentHierarchyVOs:XMLListCollection = new XMLListCollection();
	  
	  /**
	  * In the app-entry phase for the BatchContract, the user will choose
	  * client(s) to associate with the target policy (ultimately to become
	  * ContractClients).
	  */ 
	  public var searchedForAppEntryClientDetailVOs:XMLListCollection = new XMLListCollection();
	  
	  /**
	  * After entering the BatchContractSetup information, we move onto 
	  * the building of each separate BatchSegmentSetupVO. Some of the
	  * information will be defaulted everytime the BatchContractSetup
	  * information is committed.
	  * 
	  * It's default structure is meant to mimic the BatchContractSetupVO.
	  */  
	  public var selectedBatchSegmentSetupVO:XML;	
	  
	  /**
	  * After searching for ClientDetails, each (selected) ClientDetail is added to 
	  * the list of all selected ClientDetails during AppEntry.
	  */
	  public var appEntryClientDetailUIVOs:XMLListCollection = new XMLListCollection();
	  
	  /**
	  * In appEntry, the user will select a client and then add additional role specific
	  * information.
	  */ 
	  private var _selectedAppEntryClientDetailVO:XML;
	  
	  public function set selectedAppEntryClientDetailVO(selectedAppEntryClientDetailVO:XML):void
	  {
	  	_selectedAppEntryClientDetailVO = selectedAppEntryClientDetailVO;
	  }
	  
	  public function get selectedAppEntryClientDetailVO():XML
	  {
	  	return _selectedAppEntryClientDetailVO;	
	  }
	  
	  /**
	  * The CandidateClientRoleUIVOs that are nested in the current selectedAppEntryClientDetailVO. They
	  * are presented at this level (even though they are nested) to support Binding.
	  */ 
	  public var candidateClientRoleUIVOs:XMLListCollection = new XMLListCollection();
	  
	  /**
	  * The SelectedClientRoleUIVOs that are nested in the current selectedAppEntryClientDetailVO. They
	  * are presented at this level (even though they are nested) to support Binding.
	  */ 
	  public var selectedClientRoleUIVOs:XMLListCollection = new XMLListCollection();
	  
	  /**
	   * Array of ClientSegment objects which relate the contract clients to the
	   * appropriate segment (rider or base)
	   */
	  public var clientSegments:ArrayCollection = new ArrayCollection(); 

	  /**
	  * Store all CodeTableDef/CodeTable entries in the SEG system.
	  */ 
      public var codeTableDefVOs:XMLListCollection = new XMLListCollection();
      
      /**
      * For the currently selected BatchContractSetupVO, there are 
      * BatchContractSetupLogs that describe any delays to the batch process.
      */ 
      public var batchProgressLogVOs:XMLListCollection = new XMLListCollection();
      
      /**
      * For the current BatchContractSetupVO, information is gathered that
      * describes the number of applications received by product type.
      */ 
      public var batchProductLogVOs:XMLListCollection = new XMLListCollection();
      
      /**
      * The list of FilteredProductVO.ProductStructureVO.CompanyVOs for the selected BatchContractSetup.
      */ 
      private var _candidateFilteredProductVOs:XMLListCollection = new XMLListCollection();

      public function set candidateFilteredProductVOs(candidateFilteredProductVOs:XMLListCollection):void
      {
      	_candidateFilteredProductVOs = candidateFilteredProductVOs
      }
      
      public function get candidateFilteredProductVOs():XMLListCollection
      {
      	return _candidateFilteredProductVOs;
      }
      
      /**
      * During AppEntry, a Segment gets associated with a DepartmentLocation. The
      * list of available DepartmentLocations comes from the associations made
      * via the ContractGroup (group) of the current BatchContractSetup.
      */ 
      public var departmentLocationVOs:XMLListCollection = new XMLListCollection();
      
      /**
      * When the user wants to "instantiate" a new valueObjectVO, we want to [Bind]
      * to it from the front-end so that any mods to the front end (e.g. an InputText)
      * automatically get progagated to the newly created VO. The belief is that there
      * will only be a limited set of new valueObjectVOs associated with any use case.
      * The every newValueObjectVO created is attached to (this) XML document. The user
      * is expected to know how to query the newValueObjectVO to get their targeted VO(s). 
      */ 
      public var newValueObjectVO:XML = <NewValueObjectVO/>; 
     
      /**
      * Tracks the current Segment information for each Segment being
      * added during AppEntry.
      * @see SEGModelBuilder#buildSegmentInformationVO(...)
      */ 
      public var segmentInformationVO:XML;
      
      /**
      * Stores the collection of all PayrollDeductionCalendarVOs for a specified year
      * for a specified PayrollDeductionSchedulePK.
      */ 
      public var payrollDeductionCalendarVOs:XMLListCollection = new XMLListCollection();
      
      /**
      * When adding a client during AppEntry, the user may want to examine all policies that
      * the targeted Client is associated with.
      */ 
      public var searchedForPolicyInformationVOs:XMLListCollection = new XMLListCollection();
      
      /**
      * When creating a BatchContractSetup, the user needs to select an Enrollment from 
      * the collection of Enrollments available.
      */ 
      public var candidateEnrollmentVOs:XMLListCollection = new XMLListCollection();
      
      /**
      * During AppEntry, the user has a list of candidate riders from which
      * to select and associate to the current policy.
      */ 
      public var candidateCandidateRiderVOs:XMLListCollection = new XMLListCollection();
      
      /**
      * The currently selected BillGroupVO can have its fund values adjusted
      * by the user. The end result of the adjustment is represented as a 
      * "balance" that users will want to be notified of. As the user
      * adjusts the OverUnderAdjust values, the corresponding values of the
      * BillGroupVO will also be updated.
      */ 
      public var overUnderAdjustment:OverUnderAdjustment;
      
      /**
      * Represents the result of a new business quote.
      */  
      public var quoteVO:XML;
      
      /**
      * During AppEntry, the use may wish to add a ClientDetail (ultimately a ContractClient)
      * to the current App. However, the ClientDetail may not exist in the system
      * The use has the option of adding a minimal ClientDetail via a quick add process.
      */ 
      public var quickAddClientDetailVO:XML;
      
      /**
      * Compliments the quickAddClientClientDetailVO document as a minimal address
      * will also be needed.
      */ 
      public var quickAddClientAddressVO:XML;
      
      /**
      * Compliments the 
      */ 
      public var quickAddTaxInformationVO:XML;
      
      /**
	   * Holds onto the ClientSelectDialog to control state
	   */  
	  public var clientSelectDialog:UIComponent;
      
      /**
      * The PayrollDeductionCalendar (as of this writing, it is really a separate application, but uses the same model)
      * needs to know which PayrollDeduction for which to render its entries.
      */ 
      public var payrollDeductionSchedulePK:String;
      
      /**
      * The currently selected PayrollDeductionCalendarVO while updating/deleting the PayrollDeductionCalendarVOs
      * in the PayrollDeductionCalendar.
      */ 
      public var selectedPayrollDeductionCalendarVO:XML = null;
      
      /**
      * Users may adjust bills by Payor or Segment. We need to track the mode that
      * they are using. It is either going to by "BY_PAYOR" or "BY_SEGMENT".
      */ 
      public var billingAdjustMode:String;

      /**
      * Users may adjust exchangeInd. We need to track the choice that
      * they are using. It is either going to by "Y" or "N".
      */ 
      public var exchangeInd:String;
      public var mecStatusCT:String;

      /**
      */ 
      public var deductionFrequencyDesc:String;
      
      /**
      * When adding an entry to the BatchTransmitted/Days-Added-Log, the user will select
      * a specific FilteredRequirementVO from the list of candidate ones.
      */ 
      public var caseFilteredRequirementVOs:XMLListCollection = new XMLListCollection();
      
      /**
      * Conversions often require converting from Flat files to
      * that of XML. This represents the templates
      * used to convert the source data.
      */ 
      [ConversionTemplate]
      public var conversionTemplates:ArrayCollection = new ArrayCollection();
      
      /**
      * The currently selected ConversionTemplate of the conversionTemplates (Design Screen).
      */ 
      public var selectedConversionTemplateDesign:ConversionTemplate;
      
      /**
      * The currently selected ConversionTemplateVO of the conversionTemplateVOs (Run Screen).
      */ 
      public var selectedConversionTemplateRun:ConversionTemplate;     
      
      /**
      * The currently selected ConversionData of the a (Run Screen).
      */ 
      public var selectedConversionData:ConversionData;            
      
      /**
	  * Users may run sample conversions as well as full conversions. The results
	  * can vary depending on user requirements. We abstract they various conversion
	  * results.	
      */
      public var conversionResult:ConversionResult = new ConversionResult();
      
      /**
      * Represents the collection of all ConversionJobs for selected ConversionTemplate.
      */
      public var conversionJobs:ArrayCollection = new ArrayCollection();
      
      /**
      * Represents the currently selected CovnersionJob.
      */
      public var selectedConversionJob:ConversionJob;
      
      /**
      * Represents the list of all ConversionLogs for selected ConversionJob.
      */
      public var conversionLogVOs:XMLListCollection;
      
      /**
      * Flat File name where the data is located that is ready for conversion.
      */
      public var flatFileName:XML;
      
      /**
      * The list of PRASEDocuments within PRASE.
      */ 
      public var praseDocumentVOs:XMLListCollection = new XMLListCollection();
      
      /**
      * The currently selected PRASEDocumentVO.
      */ 
      public var selectedPRASEDocumentVO:XML;
      
      /**
      * The list of PRASETests associated with the currently selected PRASEDocument.
      */ 
      public var associatedPRASETestVOs:XMLListCollection = new XMLListCollection();
      
      /**
      * All PRASETests available.
      */ 
      public var praseTestVOs:XMLListCollection = new XMLListCollection();
      
      /**
      * All ProductStructureVOs including their associated Company
      */ 
      public var productStructureVOs:XMLListCollection = new XMLListCollection();
      
      /**
      * When setting-up a PRASETest, a collection of PRASEDocuments must be 
      * made available to select from.
      */  
      public var candidatePRASEDocumentWrapperVOs:XMLListCollection = new XMLListCollection();
      
      /**
      * From the collection of candidate PRASEDocuments, these represented the 
      * set being used for a selected PRASETest.
      */ 
      public var selectedPRASEDocumentWrapperVOs:XMLListCollection = new XMLListCollection();
      
      /**
      * The currently selected PRASETest.
      */ 
      public var selectedPRASETestVO:XML;
      
      /**
      * The currently selected PRASEDocumentWrapperVO.
      */ 
	  public var selectedPRASEDocumentWrapperVO:XML;
	  
	  /**
	  * When a PRASETest is executed, the output of SPOutput is captured.
	  */ 
	  public var actualPRASEOutputXML:XML;
	  
	  /**
	  * When a PRASETest is run, a difference report is generated between the
	  * expected results and the actual results from PRASE.
	  */ 
	  public var differenceXML:XML;
	  
	  /**
	  * Passed in from EDITServicesConfig. If true, then a UI option
	  * will be made available on certain pages to notify PRASE to capture
	  * all inputs/outputs as test data. An example of this might be on the 
	  * AppEntry page.
	  */ 
	  public var allowPRASETest:Boolean;
	  
	  /**
	   * Passed in from EDITServicesConfig.  If true, then the UI option will be
	   * made on certain pages to notify PRASE to record events while processing.
	   * An exmaple of this is on the NewConversionJobDialog in the Conversion application
	   */  
	  public var allowPRASERecording:Boolean;
	  
	  /**
	  * The list of all files available to supply raw data for a conversion.
	  */
	  [ConversionData] public var conversionDatas:ArrayCollection = new ArrayCollection();
	  
	  /**
	  * In Conversion, a GroupNode.RecordNode.ColummNode composition is 
	  * created to map Flat file data to its XML equivalent.
	  */ 
	  public var groupNode:GroupNode;
	  
	  /**
	  * Convenience reference to the GroupDesign component.
	  */ 
	  public var groupDesignUI:GroupDesign;
	  
	  /**
	   * Script Processor results for display	  
	   */ 
	  [SPRecordedOperator]
	  public  var  spResults:ArrayCollection = new ArrayCollection();
	  
	  /**
	  * The current collection of Queries that can be selected and
	  * run online, etc.
	  */ 
	  public var queries:ArrayCollection = new ArrayCollection();
	  
	  /**
	  * Queries can be designed, stored, and tested. Test results
	  * are captured here.
	  */ 
	  public var queryResultRows:ArrayCollection;
      
      /**
      * Constructor.
      */ 
      public function SEGModelLocator()
      {
      	super();
      }
      
      /**
       * The singleton.
       */  
      public static function getInstance() : SEGModelLocator 
      {
      	if ( modelLocator == null )
      	{
      		modelLocator = new SEGModelLocator();
      	}
      		
      	return modelLocator;
      }		
	}
}