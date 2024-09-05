package com.segsoftware.command
{
	import mx.collections.XMLListCollection;
	import com.segsoftware.model.SEGModelBuilder;
	import com.segsoftware.model.SEGModelLocator;
	
	/**
	 * When a SEGCommand invokes a service request against a SEGDelegate, the SEGDelegate
	 * needs consistant information, that being:
	 * 
	 * 1. The service name.
	 * 2. The service operator.
	 * 3. The callback results and fault Functions.
	 * 3. The parameters.
	 * 
	 * This consistant set of required information is encapsulated in this SEGRequest.
	 */ 
	public class SEGRequest
	{
		/**
		 * The name of the backend service (e.g. "Billing", "Contract").
		 */ 
		private var _serviceName:String;
		
		/**
		 * The operation to invoke on the backend service.
		 */ 
		private var _serviceOperation:String;
		
		/**
		 * The operator from the user session
		 */ 
		private var _operator:String;
		
		/**
		 * The callback function that the invoking service will call [after]
		 * invoking the remote service.
		 */ 
		private var _resultFunction:Function;
		
		/**
		 * The callback function that the invoking service will call [afer]
		 * invoking the remote service.
		 */ 
		private var _faultFunction:Function;
		
		/**
		 * The list of XML centric parameters.
		 */ 
		private var _requestParameters:XMLListCollection;
		
		/**
		 * Constructor.
		 */ 		
		function SEGRequest(serviceName:String, 
							serviceOperation:String, 
							resultFunction:Function, 
							faultFunction:Function, 
							requestParameters:XMLListCollection)
		{
			_serviceName = serviceName;
			
			_serviceOperation = serviceOperation;
			
			_resultFunction = resultFunction;
			
			_faultFunction = faultFunction;
			
			_requestParameters = requestParameters;
			
			_operator = SEGModelLocator.getInstance().operator;
		}
		
		/**
		 * @see #_serviceName
		 */ 
		public function get serviceName():String
		{
			return _serviceName;		
		}		
		
		/**
		 * @see #_serviceName
		 */ 
		public function set serviceName(serviceName:String):void
		{
			_serviceName = serviceName;
		}
		
		/**
		 * @see #_serviceOperation
		 */ 
		public function get serviceOperation():String
		{
			return _serviceOperation;	
		}
		
		/**
		 * @see #_seriviceOperation
		 */ 
		public function set serviceOperation(serviceOperation:String):void
		{
			_serviceOperation = serviceOperation;
		}
		
		/**
		 * @see #_operator
		 */ 
		public function get operator():String
		{
			return _operator;	
		}
		
		/**
		 * @see #_operator
		 */ 
		public function set operator(operator:String):void
		{
			_operator = operator;
		}
		
		/**
		 * @see #_resultFunction
		 */ 
		public function get resultFunction():Function
		{
			return _resultFunction;
		}
		
		/**
		 * @see #_resultFunction
		 */ 
		public function set resultFunction(resultFunction:Function):void
		{
			_resultFunction = resultFunction;
		}
		
		/**
		 * @see #_faultFunction
		 */ 
		public function get faultFunction():Function
		{
			return _faultFunction;
		}
		
		/**
		 * @see #_faultFunction
		 */ 
		public function set faultFunction(faultFunction:Function):void
		{
			_faultFunction = faultFunction;
		}
		
		/**
		 * @see #_requestParamters
		 */ 
		public function get requestParameters():XMLListCollection
		{
			return _requestParameters;
		}
		
		public function set requestParameters(requestParameters:XMLListCollection):void
		{
			_requestParameters = requestParameters;
		}		
		
		/**
		 * Structures this SEGRequest as an XML equivalent.
		 */ 
		public function getAsXML():XML
		{
			var segRequestVO:XML = SEGModelBuilder.getInstance().buildSEGRequestVO(
													serviceName, 
													serviceOperation, 
													operator,
													requestParameters);			
			
			return segRequestVO;
		}
	}
}