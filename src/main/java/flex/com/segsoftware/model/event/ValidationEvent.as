package com.segsoftware.model.event
{
	import flash.events.Event;
	import mx.collections.ArrayCollection;

	/**
	 * An effort to establish a validation framework on the front-end
	 * of the flex pages (only). We already support validation on the back-end, but
	 * we face the usual dilemna of what validation do we support on the front-end.
	 * This also has its issues. Simple flex front-ends, where there is a basic form-entry
	 * has the flex validation framework which works well. However, what does one
	 * do in a ListBase driven front-end (e.g. you have one or more custom itemRenderers/editors
	 * in a DataGrid). In the case of an editable DataGrid, you don't have a nice.
	 */ 
	public class ValidationEvent extends Event
	{
		/**
		 * All messages associated with this validation event.
		 */ 
		private var _messages:ArrayCollection;
		
		public function ValidationEvent(messages:ArrayCollection=null)
		{
			super("Validation Errors Exist:");
			
			this._messages = messages;			
		}
		
		/**
		 * @see #_message;
		 */ 
		public function get messages():ArrayCollection
		{
			return this._messages;
		}
	}
}