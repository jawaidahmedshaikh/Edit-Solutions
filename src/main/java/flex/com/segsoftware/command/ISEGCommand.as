package com.segsoftware.command
{
	import com.segsoftware.event.SEGEvent;
	
	/**
	 * Mimics the Cairngorn's ICommand, but allows the
	 * use of the SEGEvent as opposed to the CairngormEvent
	 * as a function parameter.
	 */  	
	public interface ISEGCommand
	{
		/**
		 * The command function to be executed.
		 * @return Object the resulting data of the command
		 */
		function executeCommand(event:SEGEvent):void; 			
	}
}