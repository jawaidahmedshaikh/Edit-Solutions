package com.segsoftware.model
{
	import com.adobe.cairngorm.control.CairngormEventDispatcher;
	import com.segsoftware.control.SEGController;
	import com.segsoftware.event.SEGEvent;
	
	import mx.collections.XMLListCollection;
	
	/**
	 * The system is full of CodeTableDef -> CodeTable entries. We
	 * need some way of managing them within the Flex application.
	 * The CodeTableWrapper mimics the CodeTableWrapper back in the core
	 * application, but is XML centric.
	 */ 
	public class CodeTable
	{		
		private static var codeTable:CodeTable;
		
		public static var CODETABLENAME_LIFERELATIONTYPE:String = "LIFERELATIONTYPE";
		
		public static var CODETABLENAME_CLASS:String = "CLASS";
				
		public static var CODETABLENAME_RATEDGENDER:String = "RATEDGENDER";
						
		public static var CODETABLENAME_UNDERWRITINGCLASS:String = "UNDERWRITINGCLASS";
		
		public static var CODETABLENAME_GROUPPLAN:String = "GROUPPLAN";
								
		public static var CODETABLENAME_TABLERATING:String = "TABLERATING";
		
		public static var CODETABLENAME_DEATHBENOPT:String = "DEATHBENOPT";
		
		public static var CODETABLENAME_STATE:String = "STATE";
		
		public static var CODETABLENAME_NONFORFEITUREOPTION:String = "NONFORFEITUREOPTION";
		
		public static var CODETABLENAME_APPLICATIONTYPE:String = "APPLICATIONTYPE";
		
		public static var CODETABLENAME_ENROLLMENTTYPE:String = "ENROLLMENTTYPE";
		
		public static var CODETABENAME_DAYSADDEDREASON:String = "DAYSADDEDREASON";
		
		public static var CODETABLENAME_BATCHCONTRACTSTATUS:String = "BATCHCONTRACTSTATUS";
		
		public static var CODETABLENAME_RELATIONSHIPTOEMPLOYEE:String = "RELATIONTOEMPLOYEE";
		
		public static var CODETABLENAME_GENDER:String = "GENDER";
		
		public static var CODETABLENAME_ENROLLMENTMETHOD:String = "ENROLLMENTMETHOD";
		
		public static var CODETABLENAME_BILLSTOPREASON:String = "BILLSTOPREASON";
		
		public static var CODETABLENAME_RESPONSE:String = "RESPONSE";
		
		public static var CODETABLENAME_PRDCODE:String = "PRDCODE";		
		
		public static var CODETABLENAME_YESNO:String = "YESNO";
		
		public static var CODETABLENAME_TRUSTTYPE:String = "TRUSTTYPE";
		
		public static var CODETABLENAME_TAXIDTYPE:String = "TAXIDTYPE";
		
		public static var CODETABLENAME_REQUIREDOPTIONAL:String = "REQUIREDOPTIONAL";			

		public static var CODETABLENAME_MEC:String = "MECSTATUS";
		
		public static var CODETABLENAME_OPTIONCODE:String = "OPTIONCODE";
		
		/**
		 * CodeTable(s)
		 */ 
		public static var CODETABLENAME_REQUIREDOPTIONAL_REQUIRED:String = "Required";
		
		public static var CODETABLENAME_REQUIREDOPTIONAL_OPTIONAL:String = "Optional";			
		
	 	/**
	 	* CodeTableVOs
	 	*/
		[Bindable] 
		public var clientRoleCodeTableVOs:XMLListCollection= null;	
		
		[Bindable] 
		public var filteredClientRoleCodeTableVOs:XMLListCollection= null;	
			 	
		[Bindable] 
		public var classCodeTableVOs:XMLListCollection= null;
		
		[Bindable] 
		public var filteredClassCodeTableVOs:XMLListCollection= null;
		
		[Bindable] 
		public var ratedGenderFromCaseProductVOs:XMLListCollection= null;
		
		[Bindable] 
		public var ratedGenderCodeTableVOs:XMLListCollection= null;
		
		[Bindable] 
		public var filteredRatedGenderCodeTableVOs:XMLListCollection= null;
		
		[Bindable] 
		public var underwritingClassCodeTableVOs:XMLListCollection= null;
		
		[Bindable] 
		public var groupPlanCodeTableVOs:XMLListCollection= null;
		
		[Bindable] 
		public var filteredGroupPlanCodeTableVOs:XMLListCollection= null;
		
		[Bindable] 
		public var filteredUnderwritingClassCodeTableVOs:XMLListCollection= null;	
		
		[Bindable] 
		public var tableRatingCodeTableVOs:XMLListCollection= null;	
		
		[Bindable] 
		public var filteredTableRatingCodeTableVOs:XMLListCollection= null;	
		
		[Bindable] 
		public var deathBenefitOptionCodeTableVOs:XMLListCollection= null;	
		
		[Bindable] 
		public var filteredDeathBenefitOptionCodeTableVOs:XMLListCollection= null;	
		
		[Bindable] 
		public var stateCodeTableVOs:XMLListCollection= null;	
		
		[Bindable] 
		public var filteredStateCodeTableVOs:XMLListCollection= null;	
		
		[Bindable] 
		public var nonForfeitureOptionCodeTableVOs:XMLListCollection= null;
		
		[Bindable] 
		public var filteredNonForfeitureOptionCodeTableVOs:XMLListCollection= null;
		
		[Bindable] 
		public var applicationTypeCodeTableVOs:XMLListCollection= null;	
		
		[Bindable] 
		public var filteredApplicationTypeCodeTableVOs:XMLListCollection= null;	
		
		[Bindable] 
		public var enrollmentTypeCodeTableVOs:XMLListCollection= null;	
		
		[Bindable] 
		public var filteredEnrollmentTypeCodeTableVOs:XMLListCollection= null;			
							
		[Bindable] 
		public var daysAddedReasonCodeTableVOs:XMLListCollection= null;	
		
		[Bindable] 
		public var filteredDaysAddedReasonCodeTableVOs:XMLListCollection= null;		
				
		[Bindable]
		public var batchContractStatusVOs:XMLListCollection= null;	
		
		[Bindable]
		public var filteredBatchContractStatusVOs:XMLListCollection= null;	
		
		[Bindable] 
		public var relationshipToEmployeeVOs:XMLListCollection= null;
		
		[Bindable] 
		public var filteredRelationshipToEmployeeVOs:XMLListCollection= null;
		
		[Bindable] 
		public var genderVOs:XMLListCollection= null;
		
		[Bindable] 
		public var filteredGenderVOs:XMLListCollection= null;	
			 	
		[Bindable] 
		public var enrollmentMethodCodeTableVOs:XMLListCollection= null;
		
		[Bindable] 
		public var filteredEnrollmentMethodCodeTableVOs:XMLListCollection= null;
		
		[Bindable]
		public var billStopReasonVOs:XMLListCollection = null;
		
		[Bindable]
		public var filteredBillStopReasonVOs:XMLListCollection = null;
		
		[Bindable] 
		public var responseCodeTableVOs:XMLListCollection= null;
		
		[Bindable] 
		public var filteredResponseCodeTableVOs:XMLListCollection= null;
		 
		[Bindable] 
		public var prdCodeCodeTableVOs:XMLListCollection= null;
		
		[Bindable] 
		public var filteredPrdCodeCodeTableVOs:XMLListCollection= null;
		
		[Bindable]
		public var yesNoCodeTableVOs:XMLListCollection = null;
		
		[Bindable]
		public var filteredYesNoCodeTableVOs:XMLListCollection = null;		
		
		[Bindable]
		public var trustTypeCodeTableVOs:XMLListCollection = null;
		
		[Bindable]
		public var filteredTrustTypeCodeTableVOs:XMLListCollection = null;
		
		[Bindable]
		public var taxIdTypeCodeTableVOs:XMLListCollection = null;
		
		[Bindable]
		public var filteredTaxIdTypeCodeTableVOs:XMLListCollection = null;

		[Bindable]
		public var mecCodeTableVOs:XMLListCollection = null;
		
		[Bindable]
		public var filteredMecCodeTableVOs:XMLListCollection = null;
		
		[Bindable]
		public var optionCodeCodeTableVOs:XMLListCollection = null;
		
		[Bindable]
		public var filteredOptionCodeCodeTableVOs:XMLListCollection = null;
		
		/**
		 * Extracts the CodeTableVOs within the specified
		 * codeTableDefVO and maps them to their respective
		 * named collection.
		 */ 
		public function addCodeTableDef(codeTableDefVO:XML):void
		{
			var codeTableName:String = codeTableDefVO.CodeTableName[0];
			
			var codeTableVOs:XMLListCollection = new XMLListCollection(codeTableDefVO.CodeTableVO);				
			
			if (codeTableName == CODETABLENAME_LIFERELATIONTYPE)
			{
				clientRoleCodeTableVOs = codeTableVOs;				
			}
			else if (codeTableName == CODETABLENAME_CLASS)
			{
				classCodeTableVOs = codeTableVOs;	
			}
			else if (codeTableName == CODETABLENAME_RATEDGENDER)
			{
				ratedGenderCodeTableVOs = codeTableVOs;	
			}
			else if (codeTableName == CODETABLENAME_UNDERWRITINGCLASS)
			{
				underwritingClassCodeTableVOs = codeTableVOs;
			}
			else if (codeTableName == CODETABLENAME_GROUPPLAN)
			{
				groupPlanCodeTableVOs = codeTableVOs;
			}
			else if (codeTableName == CODETABLENAME_TABLERATING)
			{
				tableRatingCodeTableVOs = codeTableVOs;	
			}
			else if (codeTableName == CODETABLENAME_DEATHBENOPT)
			{
				deathBenefitOptionCodeTableVOs = codeTableVOs;	
			}
			else if (codeTableName == CODETABLENAME_STATE)
			{
				stateCodeTableVOs = codeTableVOs;	
			}
			else if (codeTableName == CODETABLENAME_NONFORFEITUREOPTION)
			{
				nonForfeitureOptionCodeTableVOs = codeTableVOs;	
			}
			else if (codeTableName == CODETABLENAME_APPLICATIONTYPE)
			{
				applicationTypeCodeTableVOs = codeTableVOs;
			}
			else if (codeTableName == CODETABLENAME_ENROLLMENTTYPE)
			{
				enrollmentTypeCodeTableVOs = codeTableVOs;	
			}
			else if (codeTableName == CODETABENAME_DAYSADDEDREASON)
			{
				daysAddedReasonCodeTableVOs = codeTableVOs;
			}
			else if (codeTableName == CODETABLENAME_BATCHCONTRACTSTATUS)
			{
				batchContractStatusVOs = codeTableVOs;	
			}
			else if (codeTableName == CODETABLENAME_RELATIONSHIPTOEMPLOYEE)
			{
				relationshipToEmployeeVOs = codeTableVOs;
			}
			else if (codeTableName == CODETABLENAME_GENDER)
			{
				genderVOs = codeTableVOs;	
			}
			else if (codeTableName == CODETABLENAME_ENROLLMENTMETHOD)
			{
				enrollmentMethodCodeTableVOs = codeTableVOs;				
			}
			else if (codeTableName == CODETABLENAME_BILLSTOPREASON)
			{
				billStopReasonVOs = codeTableVOs;
			}
			else if (codeTableName == CODETABLENAME_RESPONSE)
			{
				responseCodeTableVOs = codeTableVOs;				
			}
			else if (codeTableName == CODETABLENAME_PRDCODE)
			{
				prdCodeCodeTableVOs = codeTableVOs;	
			}		
			else if (codeTableName == CODETABLENAME_YESNO)
			{
				yesNoCodeTableVOs = codeTableVOs;
			}
			else if (codeTableName == CODETABLENAME_TRUSTTYPE)
			{
				trustTypeCodeTableVOs = codeTableVOs;
			}	
			else if (codeTableName == CODETABLENAME_TAXIDTYPE)
			{
				taxIdTypeCodeTableVOs = codeTableVOs;
			}																																													
			else if (codeTableName == CODETABLENAME_MEC)
			{
				mecCodeTableVOs = codeTableVOs;
			}
			else if (codeTableName ==  CODETABLENAME_OPTIONCODE)
			{
				optionCodeCodeTableVOs = codeTableVOs;
			}
		}
		
		/**
		 * Extracts the CodeTableVOs within the specified
		 * codeTableDefVO and maps them to their respective filtered
		 * named collection.
		 */ 
		public function addFilteredCodeTableDef(codeTableDefVO:XML):void
		{
			var codeTableName:String = codeTableDefVO.CodeTableName[0];
			
			var codeTableVOs:XMLListCollection = new XMLListCollection(codeTableDefVO.CodeTableVO);				
			
			if (codeTableName == CODETABLENAME_LIFERELATIONTYPE)
			{
				
				filteredClientRoleCodeTableVOs = codeTableVOs;	
				var names:Array = new Array( "Insured", "Owner", "Payor", "Term Insured", "Secondary Addressee", "Primary Beneficiary",
					"Annuitant", "Contingent Annuitant", "Contingent Beneficiary", "Check Bank Payor",
					"EFT Bank Payor", "Payee", "Trustee", "Secondary Annuitant", "Secondary Owner",
					"Deceased", "Full Assignee", "Collateral Assignee", "Irrevocable Beneficiary");
				var tempList:XMLListCollection = null;
				for each (var name in names) {
					for (var i:int = 0; i < filteredClientRoleCodeTableVOs.length; i++) {
						trace(filteredClientRoleCodeTableVOs[i]);
					}
					
				}
				
			}
			else if (codeTableName == CODETABLENAME_CLASS)
			{
				filteredClassCodeTableVOs = codeTableVOs;	
			}
			else if (codeTableName == CODETABLENAME_RATEDGENDER)
			{
				filteredRatedGenderCodeTableVOs = codeTableVOs;	
			}
			else if (codeTableName == CODETABLENAME_RATEDGENDER)
			{
				ratedGenderFromCaseProductVOs = codeTableVOs;	
			}
			else if (codeTableName == CODETABLENAME_UNDERWRITINGCLASS)
			{
				filteredUnderwritingClassCodeTableVOs = codeTableVOs;
			}
			else if (codeTableName == CODETABLENAME_GROUPPLAN)
			{
				filteredGroupPlanCodeTableVOs = codeTableVOs;
			}
			else if (codeTableName == CODETABLENAME_TABLERATING)
			{
				filteredTableRatingCodeTableVOs = codeTableVOs;	
			}
			else if (codeTableName == CODETABLENAME_DEATHBENOPT)
			{
				filteredDeathBenefitOptionCodeTableVOs = codeTableVOs;	
			}
			else if (codeTableName == CODETABLENAME_STATE)
			{
				filteredStateCodeTableVOs = codeTableVOs;	
			}
			else if (codeTableName == CODETABLENAME_NONFORFEITUREOPTION)
			{
				filteredNonForfeitureOptionCodeTableVOs = codeTableVOs;	
			}
			else if (codeTableName == CODETABLENAME_APPLICATIONTYPE)
			{
				filteredApplicationTypeCodeTableVOs = codeTableVOs;
			}
			else if (codeTableName == CODETABLENAME_ENROLLMENTTYPE)
			{
				filteredEnrollmentTypeCodeTableVOs = codeTableVOs;	
			}
			else if (codeTableName == CODETABENAME_DAYSADDEDREASON)
			{
				filteredDaysAddedReasonCodeTableVOs = codeTableVOs;
			}
			else if (codeTableName == CODETABLENAME_BATCHCONTRACTSTATUS)
			{
				filteredBatchContractStatusVOs = codeTableVOs;	
			}
			else if (codeTableName == CODETABLENAME_RELATIONSHIPTOEMPLOYEE)
			{
				filteredRelationshipToEmployeeVOs = codeTableVOs;
			}
			else if (codeTableName == CODETABLENAME_GENDER)
			{
				filteredGenderVOs = codeTableVOs;	
			}
			else if (codeTableName == CODETABLENAME_ENROLLMENTMETHOD)
			{
				filteredEnrollmentMethodCodeTableVOs = codeTableVOs;				
			}
			else if (codeTableName == CODETABLENAME_BILLSTOPREASON)
			{
				filteredBillStopReasonVOs = codeTableVOs;
			}
			else if (codeTableName == CODETABLENAME_RESPONSE)
			{
				filteredResponseCodeTableVOs = codeTableVOs;				
			}
			else if (codeTableName == CODETABLENAME_PRDCODE)
			{
				filteredPrdCodeCodeTableVOs = codeTableVOs;	
			}	
			else if (codeTableName == CODETABLENAME_YESNO)
			{
				filteredYesNoCodeTableVOs = codeTableVOs;
			}	
			else if (codeTableName == CODETABLENAME_TRUSTTYPE)
			{
				filteredTrustTypeCodeTableVOs = codeTableVOs;
			}	
			else if (codeTableName == CODETABLENAME_TAXIDTYPE)
			{
				filteredTaxIdTypeCodeTableVOs = codeTableVOs;
			}	
			else if (codeTableName == CODETABLENAME_MEC)
			{
				filteredMecCodeTableVOs = codeTableVOs;
			}
			else if (codeTableName ==  CODETABLENAME_OPTIONCODE)
			{
				filteredOptionCodeCodeTableVOs = codeTableVOs;
			}
		}
		
		/**
	    * The singleton.
	    */  
	    public static function getInstance() : CodeTable 
	    {
	    	if ( codeTable == null )
	      	{
	      		codeTable = new CodeTable();
	      		
	      		codeTable.initialize();
	      	}
	      		
	      	return codeTable;
		}
	      
	    /**
	    *	Loads the CodeTable document into memory.
	    */  
	    private function initialize():void
	    {
	    	if (SEGModelLocator.getInstance().codeTableDefVOs.length == 0)
	      	{
				CairngormEventDispatcher.getInstance().dispatchEvent(new SEGEvent(SEGController.EVENT_LOAD_CODETABLEDOCUMENT, null));
	      	}
	    }			
	}
}