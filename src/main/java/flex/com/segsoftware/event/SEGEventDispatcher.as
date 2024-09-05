package com.segsoftware.event
{
	import flash.events.IEventDispatcher;
	import flash.events.EventDispatcher;
	import flash.events.Event;
	
	/**
	 *	Helper class to dispatch events from an ActionScript file.  This is  
	 *  useful for objects that need to dispatch an event but cannot extend
	 *  EventDispatcher because they already extend another class.
	 */
	public class SEGEventDispatcher implements IEventDispatcher
	{
	    private var dispatcher:EventDispatcher;
	               
	    public function SEGEventDispatcher() 
	    {
	        dispatcher = new EventDispatcher(this);
	    }
	           
	    public function addEventListener(type:String, listener:Function, useCapture:Boolean = false, priority:int = 0, useWeakReference:Boolean = false):void
	    {
	        dispatcher.addEventListener(type, listener, useCapture, priority);
	    }
	           
	    public function dispatchEvent(evt:Event):Boolean
	    {
	        return dispatcher.dispatchEvent(evt);
	    }
	    
	    public function hasEventListener(type:String):Boolean
	    {
	        return dispatcher.hasEventListener(type);
	    }
	    
	    public function removeEventListener(type:String, listener:Function, useCapture:Boolean = false):void
	    {
	        dispatcher.removeEventListener(type, listener, useCapture);
	    }
	                   
	    public function willTrigger(type:String):Boolean 
	    {
	        return dispatcher.willTrigger(type);
	    }
	}
}