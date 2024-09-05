package com.segsoftware.model
{
	import mx.collections.ArrayCollection;
	import com.segsoftware.model.encoding.ContractClient;
	
	/**
	 * This object contains the association between a rider and a client/gio option
	 * 
	 * The client is defined by the ContractClient object, the CandidateRiderVO 
	 * defines the rider.  
	 */
	[Bindable] public class ClientSegment
	{
		/**
		 * The ContractClient object that defines the client 
		 */
		private var _contractClient:ContractClient;
		
		/**
		 * The rider
		 */
		private var _candidateRiderVO:XML;
		
		/**
		 * The GIO Option that should be saved on the rider
		 */
		private var _gioOption:String;
		
		/**
		 * Constructor
		 */
		public function ClientSegment()
		{
		}
		
		public function get contractClient():ContractClient
		{
			return _contractClient;
		}
		
		[Bindable]
		public function set contractClient(contractClient:ContractClient):void
		{
			_contractClient = contractClient;
		}
		
		public function get candidateRiderVO():XML
		{
			return _candidateRiderVO;
		}
		
		[Bindable]
		public function set candidateRiderVO(candidateRiderVO:XML):void
		{
			_candidateRiderVO = candidateRiderVO;
		}
		
		public function get gioOption():String
		{
			return _gioOption;
		}
		
		[Bindable]
		public function set gioOption(gioOption:String):void
		{
			_gioOption = gioOption;
		}
		
		/**
		 * Getter of FaceAmount from the candidateRiderVO.  
		 * This is needed to make the selected riders dataGrid work properly
		 */
		public function get faceAmount():String
		{
			if (this.candidateRiderVO != null)
			{
				return this.candidateRiderVO.FaceAmount;
			}
			else
			{
				return "";
			}
		}
		
		/**
		 * Setter of FaceAmount from the candidateRiderVO.  
		 * This is needed to make the selected riders dataGrid work properly
		 */
		[Bindable]
		public function set faceAmount(faceAmount:String):void
		{
			if (this.candidateRiderVO != null)
			{
				this.candidateRiderVO.FaceAmount = faceAmount;
			}
		}
		
		/**
		 * Getter of Units from the candidateRiderVO.  
		 * This is needed to make the selected riders dataGrid work properly
		 */
		public function get units():String
		{
			if (this.candidateRiderVO != null)
			{
				return this.candidateRiderVO.Units;
			}
			else
			{
				return "";
			}
		}
		
		/**
		 * Setter of Units from the candidateRiderVO.  
		 * This is needed to make the selected riders dataGrid work properly
		 */
		[Bindable]
		public function set units(units:String):void
		{
			if (this.candidateRiderVO != null)
			{
				this.candidateRiderVO.Units = units;
			}
		}
		
		
		/**
		 * Getter of EOBMultiple from the candidateRiderVO.  
		 * This is needed to make the selected riders dataGrid work properly
		 */
		public function get eobMultiple():String
		{
			if (this.candidateRiderVO != null)
			{
				return this.candidateRiderVO.EOBMultiple;
			}
			else
			{
				return "";
			}
		}
		
		/**
		 * Setter of EOBMultiple from the candidateRiderVO.  
		 * This is needed to make the selected riders dataGrid work properly
		 */
		[Bindable]
		public function set eobMultiple(eobMultiple:String):void
		{
			if (this.candidateRiderVO != null)
			{
				this.candidateRiderVO.EOBMultiple = eobMultiple;
			}
		}
		
		/**
		 * Determines whether a ContractClient exists in this object or not
		 * 
		 * @return false if one does not exist, true otherwise
		 */
		public function contractClientExists():Boolean
		{
			if (contractClient == null)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
	}
}