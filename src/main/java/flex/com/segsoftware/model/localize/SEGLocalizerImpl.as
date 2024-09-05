package com.segsoftware.model.localize
{
	import flash.events.EventDispatcher;
	import flash.events.Event 
 
	/*
		Implementation of ILocalizer.
 
		Each translation file format should have its own Localizer, 
		implementing ILocalizer.
 
		In this example, we'll get the translated data by searching
		the XML nodes by an attribute named id, which is our key,
		using E4X.
 
	*/
 
	[Event(name="change")]	
 	[Bindable]
 	public class SEGLocalizerImpl extends EventDispatcher implements SEGLocalizer
	{ 
		private var _language:String;
	
		private var _langXML:XML;
 
		private static var instance:SEGLocalizer;
 
		public function SEGLocalizerImpl()
		{
			if(instance != null) throw new Error("Localizer is a Singleton");
		}
 
		public static function getInstance():SEGLocalizer
		{
			if(instance == null) instance=new SEGLocalizerImpl();
			
			return instance ;
		}
 
		/*
			string search method
		*/
		[Bindable("change")]
        public function getString(pKey:String):String
        {
		// string to display while waiting for data to load       	
        	if(langXML==null) return "...";
 
        	var s:String = langXML..item.(@id==pKey)[_language];
 
        	// If there is no translation available, return the key
        	if(s=="") return pKey ;	        	
 
        	return s;
        }		
 
	   	public function get langXML():XML
	   	{
	   		return _langXML;
	   	}
 
	   	public function set langXML(pXML:XML):void
	   	{
	   		_langXML = pXML;
	   		
	   		dispatchEvent(new Event("change"));
	   	}
 
 
	   	public function get language():String
	   	{
	   		return _language;
	   	}
 
	   	public function set language(pLg:String):void
	   	{
	   		_language = pLg;
	   		
	   		dispatchEvent(new Event("change"));
	   	}
	}

}