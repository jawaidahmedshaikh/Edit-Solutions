package com.segsoftware.model.conversion
{
	import mx.collections.ArrayCollection;
	import mx.events.CollectionEvent;
	import flash.events.Event;
	import com.segsoftware.utility.Util;
	import mx.events.PropertyChangeEvent;
	
	/**
	 * GroupNode(s) can contain RecodeNode(s) which can contain ColumnNode(s).
	 * 
	 */ 
	[Bindable]
	public class RecordNode extends ConversionNode
	{
		/**
		 * The default name for any RecordNode.
		 */ 
		public static var DEFAULT_RECORD_NAME:String = "RecordVO";
		
		/**
		 * Records need to be explicitly flagged for skipping.
		 */ 
		public static var DEFAULT_RECORD_SKIP:Boolean = false;
		
		/**
		 * Needs to match the name of the actual "isHeader" property.
		 */ 
		public static var PROPERTY_ISHEADER:String = "isHeader";
		
		/**
		 * The total length summation of each ColummNode.length;
		 */ 
		private var _totalLength:int = 0;
		
		/**
		 * Only one RecordNode per GroupNode can be flagged as a header. This
		 * toggles any other RecordNode's isHeader flag.
		 */ 
		private var _header:Boolean;
		
		/**
		 * Records can be flagged to be skipped/ignored.
		 */
		private var _skip:Boolean;
		
		/**
		 * Every record needs to identified with a user-defined type.
		 * This is ultimately captured by the 1st ColumnNode in the
		 * RecordNode by definition. 
		 */
		private var _type:String;
		
		public function RecordNode()
		{			
			this.skip = DEFAULT_RECORD_SKIP;
		}		
		
		/**
		 * @see #_type
		 */ 
		public function get type():String
		{
			return this._type;
		}
		
		/**
		 * @see #_type
		 */ 
		public function set type(type:String):void
		{
			this._type = type;
		}
		
		/**
		 * @see #_skip
		 */ 
		public function get skip():Boolean
		{
			return this._skip;
		}
		
		/**
		 * @see #_skip
		 */ 
		public function set skip(skip:Boolean):void
		{
			this._skip = skip;
		}
		
		/**
		 * Sums the total ColumnLengths for this RecordNode.
		 */ 
		public function sumColumnLengths():void
		{
			var newTotalLength:int = 0;
			
			for each (var columnNode:ColumnNode in childNodes)
			{
				newTotalLength += columnNode.length;	
			}
			
			this.totalLength = newTotalLength;			
		}
		
		/**
		 * @see #_totalLength
		 */ 
		public function get totalLength():int
		{
			return this._totalLength;
		}	
		
		/**
		 * @see #_totalLength
		 */ 
		public function set totalLength(totalLength:int):void
		{
			this._totalLength = totalLength;
		}
		
		/**
		 * @see #_isHeader
		 */
		public function set header(isHeader:Boolean):void
		{
			this._header = isHeader;
		}	
		
		/**
		 * @see #_isHeader
		 */ 
		public function get header():Boolean
		{
			return this._header;
		}
		
		/**
		 * This GroupNode dictates the length of any initial ColumnNode by definition
		 * since the 1st ColumnNode always defines the recordType and recordLength.
		 */ 
		public function updateIntialColumnNodeLengths(length:int):void
		{
			if (childNodeCount > 0)
			{
				(childNodes.getItemAt(0) as ColumnNode).length = length;
			}		
		}		
		
		/**
		 * Performs a recursive building of this RecordNode.ColumnNode(s)
		 * to its XML equivalent.
		 */ 		
		override public function marshal():XML
		{
			var recordNodeXML:XML = <{DEFAULT_RECORD_NAME}/>
			
			recordNodeXML.Type = Util.initString(this.type, "");
			
			recordNodeXML.Header = (this.header)?"Y":"N";	
			
			recordNodeXML.Skip = (this.skip)?"Y":"N";	
			
			for each (var columnNode:ColumnNode in this.childNodes)
			{
				var columnNodeXML:XML = columnNode.marshal();
				
				recordNodeXML.appendChild(columnNodeXML);
			}			
			
			return recordNodeXML;		
		}		
	
		/**
		 * Maps the specified XML to the state of this RecordNode.
		 */ 	
		override public function unmarshal(nodeAsXML:XML):void
		{			
			this.header = (nodeAsXML["Header"] == "Y")?true:false;
			
			this.type = Util.initString(nodeAsXML["Type"], "");
			
			this.skip = (nodeAsXML.Skip == "Y")?true:false;
			
			for each (var columnNodeXML:XML in nodeAsXML.ColumnVO)
			{
				var columnNode:ColumnNode = new ColumnNode();
				
				columnNode.unmarshal(columnNodeXML);
				
				this.childNodes.addItem(columnNode);
			}	
		}
		
		/**
		 * When a child node (ColumnNode) is added, we register to be notified of certain
		 * property changes.
		 */ 
		override public function childNodeAddedHandler(collectionEvent:CollectionEvent):void
		{
			var columnNode:ColumnNode = collectionEvent.items.pop() as ColumnNode;
			
			sumColumnLengths();
			
			flagInitialColumn();
			
			// Continue propagation			
			super.childNodeAddedHandler(collectionEvent);
		}	
		
		/**
		 * When a child node (ColumnNode) is added, we register to be notified of certain
		 * property changes.
		 */ 
		override public function childNodeRemovedHandler(collectionEvent:CollectionEvent):void
		{
			var columnNode:ColumnNode = collectionEvent.items.pop() as ColumnNode;
			
			sumColumnLengths();
			
			flagInitialColumn();			
			
			// Continue propagation
			super.childNodeRemovedHandler(collectionEvent);
		}	
		
		/**
		 * The initial ColumnNode in the list of ordered ColumnNodes is
		 * to be flagged as such. This is important since the first ColumnNode is
		 * almost [always] used to determine the the type of the RecordNode (this is a
		 * flat file thingy). 
		 */ 
		private function flagInitialColumn():void
		{
			for (var i:int = 0; i < childNodeCount; i++)
			{
				var childNode:ColumnNode = childNodes.getItemAt(i) as ColumnNode;
				
				if (i == 0)
				{
					childNode.recordType = true;
					
					childNode.name = "Type";
				}
				else
				{
					childNode.recordType = false;
				}
			}		
		}
		
		/**
		 * Targets the length property to sum the total lengths.
		 */ 
		override public function childNodePropertyChangedHandler(event:PropertyChangeEvent):void
		{
			if (event.property == ColumnNode.PROPERTY_LENGTH)
			{
				sumColumnLengths();
			}
			
			// Continue propagation			
			super.childNodePropertyChangedHandler(event);
		}
		
	}
}