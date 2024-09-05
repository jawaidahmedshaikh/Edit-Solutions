package com.segsoftware.view.custom.validators
{
	import mx.validators.Validator;
    import mx.validators.ValidationResult;

    public class TaxIdValidator extends Validator 
    {
    	//	Tax Id types that need to be validated
    	public static var SOCIAL_SECURITY_TAXID_TYPE:String = "SocialSecurityNumber";
		public static var CORPORATE_TAXID_TYPE:String = "CorporateTaxId";
		
		//	Acceptable formats for the tax id depending on the type
		public static var SOCIAL_SECURITY_FORMAT_WITH_DASHES:RegExp = /^[0-9]{3}\-[0-9]{2}\-[0-9]{4}$/;
		public static var SOCIAL_SECURITY_FORMAT_WITHOUT_DASHES:RegExp = /^[0-9]{3}\-?[0-9]{2}\-?[0-9]{4}$/;
		
		public static var CORPORATE_FORMAT_WITH_DASHES:RegExp = /^[0-9]{2}\-[0-9]{9}$/;
		public static var CORPORATE_FORMAT_WITHOUT_DASHES:RegExp = /^[0-9]{2}\-?[0-9]{9}$/;
		
		//	Error message to display when the tax id is not in the allowable format
		private static var SOCIAL_SECURITY_ERROR_MESSAGE:String = "Social Security Number must be in the format ###-##-#### (9 digits). Dashes may be omitted.";
		private static var CORPORATE_ERROR_MESSAGE:String = "Corporate Tax Id must be in the format ##-######### (11 digits). Dashes may be omitted.";
		
		//	Error codes for ValidationResult
		private static var VALIDATION_RESULT_ERROR_CODE_TYPE_UNSET:String = "typeNotSetYet";	
		private static var VALIDATION_RESULT_ERROR_CODE_WRONG_FORMAT:String = "wrongFormat";
			
        // Define Array for the return value of doValidation().
        private var results:Array;
        
        // The VO containing the taxIdType
        private var _taxIdTypeVO:XML;

        // Constructor.
        public function TaxIdValidator() 
        {
            // Call base class constructor. 
            super();
        }
        
        public function get taxIdTypeVO():XML
		{
			return _taxIdTypeVO;	
		}
		
		[Bindable]
		public function set taxIdTypeVO(taxIdTypeVO:XML):void
		{
			_taxIdTypeVO = taxIdTypeVO;
		}
    
        // Define the doValidation() method.
        override protected function doValidation(value:Object):Array 
        {
            // Clear results Array.
            results = [];

            // Call base class doValidation().
            results = super.doValidation(value); 
                   
            // Return if there are errors.
            if (results.length > 0)
            {
                return results;
            }
        
        	if (_taxIdTypeVO != null)
        	{
	        	var taxIdType:String = _taxIdTypeVO.Code;
	        	
	        	if (value != "")
	        	{
		            if (! taxIdFormatAllowed(taxIdType, value))
					{
						//	error
						results.push(new ValidationResult(true, null, VALIDATION_RESULT_ERROR_CODE_WRONG_FORMAT, getErrorMessage(taxIdType)));
						
						return results;
					}
	        	}
        	}
        	else
        	{
        		results.push(new ValidationResult(false, null, VALIDATION_RESULT_ERROR_CODE_TYPE_UNSET, ""));
        	}
        	
            return results;
        }
        
        private function getErrorMessage(taxIdType:String):String
        {
        	if (taxIdType == TaxIdValidator.SOCIAL_SECURITY_TAXID_TYPE)
        	{
        		return SOCIAL_SECURITY_ERROR_MESSAGE;
        	}
        	else if (taxIdType == TaxIdValidator.CORPORATE_TAXID_TYPE)
        	{
        		return CORPORATE_ERROR_MESSAGE;
        	}
        	
        	return null;
        }
        
		private function taxIdFormatAllowed(taxIdType:String, value:Object):Boolean
		{
			var taxId:String = value as String;
			
			if (taxIdType == TaxIdValidator.SOCIAL_SECURITY_TAXID_TYPE)
			{
				return formatAllowedForSocialSecurityNumber(taxId);
			}
			else if (taxIdType == TaxIdValidator.CORPORATE_TAXID_TYPE)
			{
				return formatAllowedForCorporateId(taxId);
			}
			else
			{
				//	It's some other type that does not require formatting
				return true;
			}
		}
		
		/**
		 *	Determines if the tax id is in an allowable format for a social security number
		 */
		private function formatAllowedForSocialSecurityNumber(taxId:String):Boolean
		{
			var resultsWithoutDashes:Array = taxId.match(TaxIdValidator.SOCIAL_SECURITY_FORMAT_WITHOUT_DASHES);
			var	resultsWithDashes:Array = taxId.match(TaxIdValidator.SOCIAL_SECURITY_FORMAT_WITH_DASHES);
			
			if (resultsWithoutDashes != null || resultsWithDashes != null)
			{
				return true;
			}
			else
			{
				return false;
			}	
		}
		
		/**
		 *	Determines if the tax id is in an allowable format for a corporate tax id
		 */
		private function formatAllowedForCorporateId(taxId:String):Boolean
		{
			var resultsWithoutDashes:Array = taxId.match(TaxIdValidator.CORPORATE_FORMAT_WITHOUT_DASHES);
			var	resultsWithDashes:Array = taxId.match(TaxIdValidator.CORPORATE_FORMAT_WITH_DASHES);
			
			if (resultsWithoutDashes != null || resultsWithDashes != null)
			{
				return true;
			}
			else
			{
				return false;
			}	
		}
    }
}