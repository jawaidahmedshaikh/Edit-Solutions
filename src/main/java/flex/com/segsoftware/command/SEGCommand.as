package com.segsoftware.command
{
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.adobe.cairngorm.commands.ICommand;
	import com.segsoftware.event.SEGEvent;
	import mx.rpc.Responder;
	import mx.rpc.IResponder;
	import mx.rpc.events.*;
	import com.segsoftware.utility.Util;
	import com.segsoftware.model.logging.*;

	/**
	 * The ICommand interface takes a CairngormEvent object in its
	 * execute(event:CairngormEvent) function. The problem is that
	 * the CairngormEvent object provides no information about the
	 * original event. 
	 * 
	 * This is remedied with the creation of the 
	 * SEGEvent class which extends the CairngormEvent class
	 * and provides the originating Event object.
	 * 
	 * This still leaves the issue of the ICommand interface which
	 * is not prepared to dispatch a SEGEvent object. This is remedied
	 * by providing the SEGCommand class that can participate in the
	 * Cairngorm framework while delegating to the SEGCommand's 
	 * executeCommand(segEvent:SEGEvent) function.
	 */ 
	public class SEGCommand implements ICommand, ISEGCommand
	{
		private var className:String = Util.getClassName(this);
		
		private var timeStamp:String = new Date().toLocaleString();
		
		
		public function execute(event:CairngormEvent):void
		{
			executeCommand(event as SEGEvent);
		} 
		
		/**
		 * This method is expected to be overridden by any
		 * Command class that is participating in the Cairngorm
		 * framework.
		 * 
		 * e.g. in some class "FooCommand extends SEGCommand", we
		 * would expect to find:
		 * 
		 * override public function executeCommand(event:SEGEvent):void{...}
		 */ 
		public function executeCommand(event:SEGEvent):void{}
		
		/**
		 * Flex calls are asynchonous by nature. The implementing 
		 * Command can override this function to handle the [result]
		 * of any Delegate being invoked. The implementing Command does
		 * [not] have to use this exact function, although the implementing
		 * Command [does] have to use the same signature (the parameters).
		 * 
		 * The CairngormEvent requires the result method have the signature of
		 * public function result(resultEvent:ResultEvent, token:Object=null):void
		 * - at a minimum.  We are able to add our own argument of eventName for
		 * the logging
		 */
        public function result(resultEvent:ResultEvent, eventName:String, token:Object=null):void 
        {
        	SEGLog.getInstance().logResultEvent(resultEvent, className, timeStamp, eventName);
        }
        
		/**
		 * Flex calls are asynchonous by nature. The implementing 
		 * Command can override this function to handle the [fault]
		 * of any Delegate being invoked. The implementing Command does
		 * [not] have to use this exact function, although the implementing
		 * Command [does] have to use the same signature (the parameters).
		 * 
		 * The CairngormEvent requires the fault method have the signature of
		 * public function fault(faultEvent:FaultEvent, token:Object=null):void
		 * - at a minimum.  We are able to add our own argument of eventName for
		 * the logging
		 */
        public function fault(faultEvent:FaultEvent, eventName:String, token:Object=null):void 
        {
        	SEGLog.getInstance().logFaultEvent(faultEvent, className, timeStamp, eventName);
        }		
	}
}