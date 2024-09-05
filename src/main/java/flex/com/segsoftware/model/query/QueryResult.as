package com.segsoftware.model.query
{
	import com.segsoftware.model.SEGClass;
	import com.segsoftware.model.SEGEntity;
	import com.segsoftware.model.SEGMarshallable;
	
	import flash.utils.*;
	
	import mx.collections.ArrayCollection;
	
	public class QueryResult implements SEGMarshallable
	{
		/**
		 * When specified, the results are presented as row/columns.
		 */ 
		public static var UNMARSHAL_AS_ROWS:String = "UNMARSHAL_AS_ROWS";
		
		/**
		 * When specified, the results are presented as an XML composition.
		 */ 
		public static var UNMARSHAL_AS_COMPOSITION:String = "UNMARSHAL_AS_COMPOSITION";
		
		/**
		 * The rows of the resulting data.
		 */ 
		private var _queryRows:ArrayCollection = new ArrayCollection();
		
		/**
		 * The collection of compositions of the resulting data.
		 * (ArrayCollection of SEGEntity objects)
		 */ 
		private var _queryCompositions:ArrayCollection = new ArrayCollection();
		
		/**
		 * The ordered list of column names used in the original sql.
		 */ 
		private var _columnNames:ArrayCollection = new ArrayCollection();
		
		/**
		 * @see #_queryRows
		 */ 
		public function get queryRows():ArrayCollection
		{
			return this._queryRows;
		}
		
		/**
		 * @see #_queryRows
		 */ 
		public function set queryRows(queryRows:ArrayCollection):void
		{
			this._queryRows = queryRows;
		}
		
		/**
		 * @see #_queryCompositions
		 */ 
		public function get queryCompositions():ArrayCollection
		{
			return this._queryCompositions;
		}
		
		/**
		 * @see #_queryCompositions
		 */ 
		public function set queryCompositions(queryCompositions:ArrayCollection):void
		{
			this._queryCompositions = queryCompositions;
		}
		
		/**
		 * @see #_columnNames
		 */ 
		public function get columnNames():ArrayCollection
		{
			return this._columnNames;
		}
		
		/**
		 * @see #_columnNames
		 */ 
		public function set columnNames(columnNames:ArrayCollection):void
		{
			this._columnNames = columnNames;
		}
		
		public function unmarshal(queryResultXML:XML=null, version:String="UNMARSHAL_AS_COMPOSITION"):void
		{
			if (version == UNMARSHAL_AS_ROWS)
			{
				unmarshalAsRows(queryResultXML);
			}
			else if (version == UNMARSHAL_AS_COMPOSITION)
			{
				unmarshalAsComposition(queryResultXML);
			}
		}	
		
		/**
		 * Unmarshals the results as a SEGEntity composition.
		 */ 
		private function unmarshalAsComposition(queryResultXML:XML):void
		{
			var segEntities:XMLList = queryResultXML.children();
			
			for each (var segEntityXML:XML in segEntities)
			{
				var segEntityVOName:String = segEntityXML.name();
				
				var segEntityClassName:String = segEntityVOName.substr(0, segEntityVOName.length - 2);
				
				var fullyQualifiedClassName:String = SEGClass.getFullyQualifiedClassName(segEntityClassName);
				
				var segEntityClass:Class = flash.utils.getDefinitionByName(fullyQualifiedClassName) as Class;
	
				var instance:SEGEntity = new segEntityClass();
				
				instance.unmarshal(segEntityXML);
				
				this.queryCompositions.addItem(instance);
			}
		}
		
		/**
		 * Unmarshals the results as Row/Column objects.
		 */ 
		private function unmarshalAsRows(queryResultXML:XML):void
		{
			// Column Names
			var columnNamesXML:XMLList = queryResultXML.ColumnName;
			
			for each (var columnNameXML:XML in columnNamesXML)
			{
				this.columnNames.addItem(columnNameXML.toString());
			}
			
			// Result Rows
			var queryRowVOs:XMLList = queryResultXML.QueryRowVO;
			
			for each (var queryRowVO:XML in queryRowVOs)
			{
				var queryRow:QueryRow = new QueryRow();
				
				queryRow.unmarshal(queryRowVO);
				
				this.queryRows.addItem(queryRow);
			}				
		}
		
		public function marshal(version:String=null):XML
		{
			return null;	
		}			
	}
}