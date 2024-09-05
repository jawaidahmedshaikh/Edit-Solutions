package com.segsoftware.event
{
	import mx.events.ListEvent;
	import mx.controls.listClasses.IListItemRenderer;

	/**
	 *	This class extends the ListEvent. There are times when we want to throw an event and we
	 *  want to know why it was thrown or who threw it.  ListEvent has a reason field but it can not
	 *  be manually set.  SEGListEvent can only be purposely thrown by our application.
	 */
	public class SEGListEvent extends ListEvent
	{
		public function SEGListEvent(type:String, bubbles:Boolean = false, cancelable:Boolean = false, columnIndex:int = -1, rowIndex:int = -1, reason:String = null, itemRenderer:IListItemRenderer = null)
		{
			super(type, bubbles, cancelable, columnIndex, rowIndex, reason, itemRenderer);
		}
	}
}