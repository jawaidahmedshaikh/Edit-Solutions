package com.segsoftware.event
{
	import flash.events.Event;

	/**
	 * Targeted for events that occur within the conversion framework.
	 */ 
	public class SEGConversionEvent extends Event
	{
		/**
		 *  To be dispatched whenever the length of a ColumnNode changes.
		 */ 
		public static var COLUMN_LENGTH_MODIFIED_EVENT:String = "columnLengthModified";	
		
		/**
		 * Dispatched whenever a change to a ConversionNode has occured. This is "coarse" 
		 * because we need to consider the entire GroupNode.RecordNode.ColumNode composition
		 * when any the properties anywhere change (as of this writing, I needed to redraw the
		 * entire XML diagram with the change of any property on any node).
		 */ 
		public static var CONVERSION_NODE_CHANGED_EVENT:String = "conversionNodeChanged";	

		/**
		 * Dispatched when a RecordNode is flagged as [being/not being] a header record.
		 * Header records allow us to define the beginning of a grouping of records.
		 */ 
		public static var RECORD_NODE_HEADER_FLAGGED_EVENT:String = "recordNodeHeaderFlagged";		
		
		/**
		 * @param type - the identifier for this event - handler will register by the type/name of the event
		 */ 
		public function SEGConversionEvent(type:String)
		{
			super(type, true, false);
		}
	}
}