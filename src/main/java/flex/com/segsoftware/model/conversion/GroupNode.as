package com.segsoftware.model.conversion
{
	import com.segsoftware.model.conversion.ConversionNode;
	import mx.collections.ArrayCollection;
	import flash.events.Event;
	import mx.events.CollectionEvent;
	import mx.events.PropertyChangeEvent;
	
	/**
	* A Group-level containment node.
	*/ 	
	[Bindable]
	public class GroupNode extends ConversionNode
	{	
		/**
		 * The default name for any GroupNode.
		 */ 
		public static var DEFAULT_GROUP_NAME:String = "GroupVO";
		
		/**
		 * Null/Empty dates can be specified by empty spaces. For reasons of 
		 * practicality, we will represent these spaces as some other character.
		 */ 
		public static var NO_DATE_SPACE_MASK:String = "*";
		
		/**
		 * Name of the recordTypeLength property.
		 */ 
		public static var PROPERTY_RECORD_TYPE_LENGTH:String = "recordTypeLength";
		
		/**
		 * Every child RecordNode has its "type" defined as the first
		 * column of the record. Within any GroupNode, that length is fixed
		 * across all records.
		 */ 
		private var _recordTypeLength:int;
		
		/**
		 * The format of the date fields. The back-end needs to know the incoming format
		 * in order to be able to convert to our typical yyyy/MM/dd format.
		 */ 
		private var _dateFormat:String;
		
		/**
		 * Different clients represent the concept of a system minimum date differently. Examples might
		 * be 00/00/0000 or 00000000. We need to recognize and convert it to something meaningful to our system.
		 * For us, this has been 01/01/1800 which is a legitimate date. It is also possible that a client uses the same
		 * date for both a min and max date. In that case, it simply represents a place-holder for some min/max/no date.
		 * Nonetheless, we still demand that a min/max/no be defined.
		 */ 
		private var _minDate:String;
		
		/**
		 * Different clients represent the concept of a system maximum date differently. Examples might
		 * be 99/99/9999 or 99999999. We need to recognize and convert it to something meaningful to our system.
		 * For us, this has been 12/31/9999 which is a legitimate date. It is also possible that a client uses the same
		 * date for both a min and max date. In that case, it simply represents a place-holder for some min/max/no date.
		 * Nonetheless, we still demand that a min/max/no be defined.
		 */ 
		private var _maxDate:String;
		
		/**
		 * Different clients represent the concept of a system no/null date differently. Examples might
		 * be 00/00/0000 or 00000000 or '        '. We need to recognize and convert it to something meaningful to our system.
		 * For us, this has been null which is a legitimate empty value. It is also possible that a client uses the same
		 * date for both a min, max, and no date and simply relies on the coder's understanding of the context. 
		 * In that case, it simply represents a place-holder for some min/max/no date.
		 * Nonetheless, we still demand that a min/max/no be defined.
		 */ 		
		private var _noDate:String;
		
		public function GroupNode()
		{
			this.asXML = marshal();
			
			this.addEventListener(PropertyChangeEvent.PROPERTY_CHANGE, groupNodePropertyChangedHandler);
		}
		
		private function groupNodePropertyChangedHandler(event:PropertyChangeEvent):void
		{
			if (event.property != ConversionNode.PROPERTY_AS_XML) // otherwise we get an an infinite loop
			{
				if (event.property == PROPERTY_RECORD_TYPE_LENGTH)
				{
					updateIntialColumnNodeLengths();
				}
				
				this.asXML = marshal();								
			}
		}
		
		/**
		 * @see @_recordTypeLength
		 */ 
		public function get recordTypeLength():int
		{
			return this._recordTypeLength;	
		}
		
		/**
		 * @see #_recordTypeLength
		 */ 
		public function set recordTypeLength(recordTypeLength:int):void
		{
			this._recordTypeLength = recordTypeLength;
		}
		
		/**
		 * @see #_dateFormat
		 */ 
		public function get dateFormat():String
		{
			return this._dateFormat;
		}
		
		/**
		 * @see #_dateFormat
		 */ 
		public function set dateFormat(dateFormat:String):void
		{
			this._dateFormat = dateFormat;
		}
		
		/**
		 * @see #_minDate
		 */ 
		public function get minDate():String
		{
			return this._minDate;
		}
		
		/**
		 * @see #_minDate
		 */ 
		public function set minDate(minDate:String):void
		{
			this._minDate = minDate;
		}
		
		/**
		 * @see #_maxDate
		 */ 
		public function get maxDate():String
		{
			return this._maxDate;
		}
		
		/**
		 * @see #_maxDate
		 */ 
		public function set maxDate(maxDate:String):void
		{
			this._maxDate = maxDate;
		}		
		
		/**
		 * @see #_noDate
		 */ 
		public function get noDate():String
		{
			return this._noDate;
		}
		
		/**
		 * @see #_noDate
		 */ 
		public function set noDate(noDate:String):void
		{
			this._noDate = noDate;
		}			
		
		/**
		 * Update the XML composition - views are depending on it being current.
		 */ 
		override public function childNodeAddedHandler(collectionEvent:CollectionEvent):void
		{
			this.asXML = marshal();
			
			updateIsHeader();
			
			updateIntialColumnNodeLengths();			
		}

		/**
		 * Update the XML composition - views are depending on it being current.
		 */ 
		override public function childNodeRemovedHandler(collectionEvent:CollectionEvent):void
		{
			this.asXML = marshal();	
			
			updateIsHeader();
		}
		
		/**
		 * Update the XML whenever any child property (no matter deep) has changed.
		 */ 
		override public function childNodePropertyChangedHandler(event:PropertyChangeEvent):void
		{			
			this.asXML = marshal();			
		}
		
		/**
		 * Only one 1st RecordNode in the list of RecordNodes can have "isHeader = true"
		 * at any one time. 
		 */ 
		private function updateIsHeader():void
		{
			for (var i:int = 0; i < childNodeCount; i++)
			{
				var currentRecordNode:RecordNode = childNodes.getItemAt(i) as RecordNode;
				
				if (i == 0)
				{
					currentRecordNode.header = true;					
				}
				else
				{
					currentRecordNode.header = false;
				}
			}
		}
		
		/**
		 * This GroupNode dictates the length of any initial ColumnNode by definition
		 * since the 1st ColumnNode always defines the recordType and recordLength.
		 */ 
		private function updateIntialColumnNodeLengths():void
		{
			for each (var recordNode:RecordNode in childNodes)
			{
				recordNode.updateIntialColumnNodeLengths(this.recordTypeLength);
			}			
		}

		/**
		 * Performs a recursive building of this GroupNode.RecordNode(s).ColumnNode(s)
		 * to its XML equivalent.
		 */ 		
		override public function marshal():XML
		{
			var groupNodeXML:XML = <{DEFAULT_GROUP_NAME}/>;
						
			groupNodeXML.appendChild(<RecordTypeLength>{this.recordTypeLength}</RecordTypeLength>);
			
			groupNodeXML.appendChild(<DateFormat>{this.dateFormat}</DateFormat>);
			
			groupNodeXML.MinDate = this.minDate;
			
			groupNodeXML.MaxDate = this.maxDate;
			
			groupNodeXML.NoDate = this.noDate;		
			
			for each (var recordNode:RecordNode in this.childNodes)
			{
				var recordNodeXML:XML = recordNode.marshal();
				
				groupNodeXML.appendChild(recordNodeXML);
			}
			
			return groupNodeXML;		
		}
		
		/**
		 * Satisifies the implemented interface - there is no concept of "abstract".
		 */ 		
		override public function unmarshal(nodeAsXML:XML):void
		{						
			this.recordTypeLength = new Number(nodeAsXML["RecordTypeLength"]);
			
			this.dateFormat = nodeAsXML["DateFormat"];
			
			this.minDate = nodeAsXML.MinDate;
			
			this.maxDate = nodeAsXML.MaxDate;
			
			this.noDate = nodeAsXML.NoDate;
			
			for each (var recordNodeXML:XML in nodeAsXML.RecordVO)
			{
				var recordNode:RecordNode = new RecordNode();
				
				recordNode.unmarshal(recordNodeXML);
				
				this.childNodes.addItem(recordNode);
			}
		}
	}
}