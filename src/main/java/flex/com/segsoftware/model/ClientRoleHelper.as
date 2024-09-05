package com.segsoftware.model
{
	public class ClientRoleHelper
	{
		public static var ROLETYPECT_INSURED:String = "Insured";
		public static var ROLETYPECT_OWNER:String = "OWN";
		public static var ROLETYPECT_PAYOR:String = "POR";
			
		/**
		 * True if the specified roleTypeCT represents an "insured".
		 */ 
		public static function isInsured(roleTypeCT:String):Boolean
		{
			var isInsured:Boolean = false;
			
			if (roleTypeCT == ROLETYPECT_INSURED)
			{
				isInsured = true;
			}
			
			return isInsured;
		}		
		
		/**
		 * True if the specified roleTypeCT represents an "owner".
		 */ 
		public static function isOwner(roleTypeCT:String):Boolean
		{
			var isOwner:Boolean = false;
			
			if (roleTypeCT == ROLETYPECT_OWNER)
			{
				isOwner = true;
			}
			
			return isOwner;
		}	
		
		/**
		 * True if the specified roleTypeCT represents an "payor".
		 */ 
		public static function isPayor(roleTypeCT:String):Boolean
		{
			var isPayor:Boolean = false;
			
			if (roleTypeCT == ROLETYPECT_PAYOR)
			{
				isPayor = true;
			}
			
			return isPayor;
		}			
		
		/**
		 * Users may modify the state of the ClientRoleUIVO, but need to reset its
		 * code values for later modification.
		 */ 
		public static function resetCodeValues(clientRoleUIVO:XML):void
		{
			clientRoleUIVO.ClassTypeCode = "";
				
			clientRoleUIVO.TableRatingCode = "";
			
			clientRoleUIVO.BeneficiaryAllocation = "";
			
			clientRoleUIVO.BeneficiaryAllocationType = "";
			
			clientRoleUIVO.BeneficiaryRelationshipToInsured = "";	
		}
	}
}