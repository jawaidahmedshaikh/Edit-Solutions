package com.segsoftware.model.query
{
	import com.segsoftware.control.SEGController;
	import com.segsoftware.model.SEGMarshallable;
	import com.segsoftware.utility.Util;
	
	import mx.collections.ArrayCollection;
	
	/**
	 * Developers need to run many ad-hoc queries against the backend.
	 * This class encapsulates the basic hql information needed to
	 * execute a hql query. Since interfacing with the backend is required,
	 * This class, in turn, will execute a Command object.
	 * 
	 * To isolate the user from the backend mechanics of this object, 
	 * the User can specify a callback function that will be invoked 
	 * upon availability of the results.
	 * 
	 * e.g.
	 * 
	 * queryStatement.executeQuery(fooFunction):void
	 * 
	 * In the above line, fooFunction(queryStatement) will be called
	 * upon availability of the results.
	 * 
	 * 
	 * 
	 */ 
	public class QueryStatement implements SEGMarshallable
	{
		public static var MAX_ALLOWABLE_RESULTS:Number = 1000;
		
		/**
		 * Identifies the Edit Solutions DB.
		 */ 
		public static var EDITSOLUTIONS:String = "EDITSOLUTIONS";
		
		/**
		 * Identifies the Engine DB which we also refer to as PRASE.
		 */ 
		public static var ENGINE:String = "PRASE";
		
		
		/**
		 * The hql to execute.
		 */ 
		private var _hql:String;
		
		/**
		 * The DB against which to execute the hql.
		 * @see #EDITSOLUTIONS
		 * @see #ENGINE
		 */ 
		private var _targetDB:String;
		
		/**
		 * Results of the query can fed to
		 * a callback function if desired
		 */ 
		private var _resultsCallbackFunction:Function;
		
		/**
		 * Puts a restriction on the number of rows to be returned.
		 * If no number is specified, we default to MAX_ALLOWABLE_RESULTS.
		 */ 
		private var _maxResults:Number;
		
		/**
		 * The collection of Query
		 */ 
		private var _queryResult:QueryResult;
		
		/**
		 * The name/value pairs used to execute the query.
		 */ 
		private var _queryParameters:ArrayCollection = new ArrayCollection();
		
	    /**
	     * QueryStatments executed as hql will 
	     * have their results marshalled as an 
	     * entity composition (e.g. SegmentVO.InvestmentVO etc.).
	     * 
	     * Query statements executed as sql will
	     * have their results marshalled as RowVOs.
	     * 
	     * The default is NOT as RowVOs.
	     */
		private var _marshalAsRows:Boolean = false;
		
		/**
		 * Constructor
		 * @param hql valid hql expression
		 * @param targetDB typically EditSolutions or Engine
		 * @param resultsCallbackFunction 	
		 * 			- a callback function for which to supply the results 
		 * 			- the function is expected to take a single parameter
		 * 			- that parameter being the original QueryStatement itself
		 * @param maxResults the number of result rows to permit - can't excede MAX_ALLOWABLE_RESULTS
		 */ 
		public function QueryStatement(hql:String, targetDB:String, resultsCallbackFunction:Function=null, maxResults:Number=1000)
		{
			this._hql = hql;
			
			this._targetDB = targetDB;
			
			this._resultsCallbackFunction = resultsCallbackFunction;

			if (maxResults > MAX_ALLOWABLE_RESULTS)
			{
				this._maxResults = MAX_ALLOWABLE_RESULTS;
			}
			
			this._maxResults = maxResults;
		}
		
		/**
		 * @see #_resultsCallbackFunction
		 */ 
		public function get resultsCallbackFunction():Function
		{
			return this._resultsCallbackFunction;	
		}
		
		/**
		 * @see #_resultsCallbackFunction
		 */ 
		public function set resultsCallbackFunction(resultsCallbackFunction:Function):void
		{
			this._resultsCallbackFunction = resultsCallbackFunction;
		}
		
		/**
		 * @see #_queryResult
		 */ 
		public function get queryResult():QueryResult
		{
			return this._queryResult;	
		}
		
		/**
		 * @see #_queryResult
		 */ 
		public function set queryResult(queryResult:QueryResult):void
		{
			this._queryResult = queryResult;
		}
		
		/**
		 * Executes this Query against the backend. 
		 * 
		 */ 
		public function executeQuery():void
		{			
			Util.dispatchEvent(SEGController.EVENT_EXECUTE_QUERY_STATEMENT, this);			
		}
		
		/**
		 * @se #_maxResults
		 */ 
		public function get maxResults():Number
		{
			return this._maxResults;
		}
		
		/**
		 * @see #_maxResults
		 */ 
		public function set maxResults(maxResults:Number):void
		{
			this._maxResults = maxResults;
		}
		
		/**
		 * @see #_targetDB
		 */ 
		public function get targetDB():String
		{
			return this._targetDB;
		}
		
		/**
		 * @see #_targetDb
		 */ 
		public function set targetDB(targetDB:String):void
		{
			this._targetDB = targetDB;
		}
		
		/**
		 * @see #_hql
		 */ 
		public function get hql():String
		{
			return this._hql;
		}
		
		/**
		 * @see #_hql
		 */ 
		public function set hql(hql:String):void
		{
			this._hql = hql;
		}
		
		/**
		 * @see #_marshalAsRows
		 */ 
		public function get marshalAsRows():Boolean
		{
			return this._marshalAsRows;
		}
		
		/**
		 * @see #_marshalAsRows
		 */ 
		public function set marshalAsRows(marshalAsRows:Boolean):void
		{
			this._marshalAsRows = marshalAsRows;
		}
		
		/**
		 * @se #_parameters
		 */ 
		public function get queryParameters():ArrayCollection
		{
			return this._queryParameters;
		}
		
		/**
		 * @see #_parameters
		 */ 
		public function set queryParameters(queryParameters:ArrayCollection):void
		{
			this._queryParameters = queryParameters;
		}
		
		public function unmarshal(entityAsXML:XML=null, version:String=null):void
		{
			
		} 
			
		public function marshal(version:String=null):XML
		{
			var queryStatementVO:XML = <QueryStatementVO>
											<Hql>{hql}</Hql>
											<TargetDB>{targetDB}</TargetDB>
											<MaxResults>{maxResults}</MaxResults>
											<MarshalAsRows>{marshalAsRows}</MarshalAsRows>
										</QueryStatementVO>		
									
			for each (var currentQueryParameter:QueryParameter in queryParameters)
			{
				var queryParameterVO:XML = currentQueryParameter.marshal();		
				
				queryStatementVO.appendChild(queryParameterVO);
			}	
			
			return queryStatementVO;								
		}
		
		/**
		 * Determines if the hql contains parameters or not.  
		 * 
		 * @return true if the hql contains parameters, false otherwise
		 */
		public function hqlHasParameters():Boolean
		{
			var pattern:RegExp = /:([\w]++)/g;
			
			var results:Array = this.hql.match(pattern);
			
			if (results.length > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		/**
		 * Parses the parameter names from the hql expression
		 */
		public function parseParametersFromHQL():Array
		{
			//	First, find the pattern of a colon followed by a word character, many occurrences
			var pattern:RegExp = /:([\w]++)/g;
			
			var results:Array = this.hql.match(pattern);	// get back an array of parameter names with colons
			
			//	Now, strip the colons from each result string in the array
			var colonPattern:RegExp = /:/;
			
			for (var i:int = 0; i < results.length; i++)
			{
				var result:String = results[i] as String;
				
				results[i] = result.replace(colonPattern, "");	
			}
			
			return results;
		}
	}
}