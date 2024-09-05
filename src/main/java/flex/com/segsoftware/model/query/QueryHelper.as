package com.segsoftware.model.query
{
	import com.segsoftware.model.SEGKey;
	import com.segsoftware.model.SEGMap;
	
	import flash.utils.describeType;
	
	import mx.collections.ArrayCollection;
	import com.segsoftware.utility.Util;
	
	/**
	 * A wrapper to the Query framework. It's meant to mimic
	 * the SessionHelper we use in the back-end. We don't
	 * have 'sessions' in this front-end, so we restict ourselves
	 * to queries only.
	 */ 
	public class QueryHelper
	{
		public static var ENGINE:String = QueryStatement.ENGINE;
		
		public static var EDITSOLUTIONS:String = QueryStatement.EDITSOLUTIONS;
		
		/**
		 * Executes the specified hql against the specified DB returning the results to
		 * the specified callback function. The callback function should have the 
		 * following signature:
		 * 
		 * foo(ArrayCollection) - where ArrayCollection will contain any results.
		 */ 
		public static function executeHQL(hql:String, namedParameters:SEGMap, targetDB:String, callback:Function, maxResults:Number=1000):void
		{
			var q:QueryStatement = new QueryStatement(hql, targetDB, callback, maxResults);
			
			var keys:ArrayCollection = namedParameters.getKeys();
			
			for each (var key:String in keys)
			{
				var value:Object = namedParameters.getValue(key);
				
				var queryParameter:QueryParameter = new QueryParameter();
				
				queryParameter.name = key;
				
				if (value is Date)
				{
					value =  Util.formatDate1(value as Date);
				}
				
				queryParameter.value = value.toString();
				
				q.queryParameters.addItem(queryParameter);				
			}
			
			q.executeQuery();
		}
	}
}