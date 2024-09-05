package com.segsoftware.model.localize
{
	import flash.events.IEventDispatcher;
 
	/*
		Interface to implement
		so that the views can access localized data
	*/
	[Event(name="change")]
	public interface SEGLocalizer extends IEventDispatcher
	{
		function get language():String;
		function set language(pLang:String):void;
 
		function get langXML():XML
		function set langXML(pXML:XML):void
 
		[Bindable("change")]
		function getString(pKey:String):String;
	}

}