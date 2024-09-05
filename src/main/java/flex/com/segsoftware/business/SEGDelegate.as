package com.segsoftware.business
{
	import com.segsoftware.command.SEGRequest;
	import mx.collections.XMLListCollection;
	
	/**
	 * All business delegates
	 */ 
	public class SEGDelegate
	{
		/**
		 * The request issued by a SEGCommand specifies a callback function for results.
		 */ 
		private var _resultFunction:Function;
		
		/**
		 * The request issued by a SEGCommand specifies a callback function for faults.
		 */ 
		private var _faultFunction:Function;
		
		/**
		 * The set of request parameters formatted as XML.
		 */ 
		private var _requestParameters:XMLListCollection;
		
		function SEGDelegate(resultFunction:Function, faultFunction:Function, requestParameters:XMLListCollection)
		{
			_resultFunction = resultFunction;
			
			_faultFunction = faultFunction;
			
			_requestParameters = requestParameters;
		}
		
		/**
		 * @see #_resultFunction
		 */ 
		public function get resultFunction():Function
		{
			return _resultFunction;
		}
		
		/**
		 * @see #_faultFunction
		 */ 
		public function get faultFunction():Function
		{
			return _faultFunction;
		}
		
		/**
		 * @see #_requestParameters
		 */ 
		public function get requestParameters():XMLListCollection
		{
			return _requestParameters;
		}
	}
}