package com.segsoftware.model
{
	import mx.collections.ArrayCollection;
	
	public interface SEGValidable
	{
		/**
		 * Various components may wish to participate in a validation process.
		 * The component which implements this interface will perform its 
		 * necessary valiation(s). Any error(s) should be returned as
		 * a ValidationResultEvent.
		 * 
		 * @return an array of ValidationResultEvent(s) for every validation error found
		 */ 
		function validate():Array;	
	}
}