package com.segsoftware.model.query
{
	import mx.collections.ArrayCollection;
	import mx.collections.XMLListCollection;
	import com.segsoftware.model.SEGMarshallable;
	
	/**
	 * Encapsulates the individual QueryColumn(s).
	 * Additionally, there are some developer-friendly features:
	 * 
	 * 1. The column names are stored here.
	 * 
	 * 2. This column name/values are stored as dymamic properties
	 *    so that the developer does not have to create any
	 *    custom item renderers to display the results.		
	 */ 
	dynamic public class QueryRow implements SEGMarshallable
	{
		/**
		 * The columns of this row.
		 */ 
		private var _queryColumns:ArrayCollection = new ArrayCollection();
		
		/**
		 * @see #_queryColumns
		 */ 
		public function get queryColumns():ArrayCollection
		{
			return this._queryColumns;
		}
		
		/**
		 * @see #_queryColumns
		 */ 
		public function set queryColumns(queryColumns:ArrayCollection):void
		{
			this._queryColumns = queryColumns;
		}
		
		public function unmarshal(queryRowXML:XML=null, version:String=null):void
		{
			var queryColumnVOs:XMLList = queryRowXML.QueryColumnVO;
			
			for each (var queryColumnVO:XML in queryColumnVOs)
			{
				var queryColumn:QueryColumn = new QueryColumn();
				
				queryColumn.unmarshal(queryColumnVO);
				
				this.queryColumns.addItem(queryColumn);	
				
				// Dynamically assign this property as well. This will allow
				// any List (e.g. DataGrid) to directly access the value without
				// having to use some custom ItemRenderer.
				this[queryColumn.name] = queryColumn.value;
			}						
		}		
		
		public function marshal(version:String=null):XML
		{
			return null;
		}		
	}
}