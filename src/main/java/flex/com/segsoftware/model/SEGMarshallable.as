package com.segsoftware.model
{
	public interface SEGMarshallable
	{
		/**
		 * Implementors map the xml-based state to
		 * its corresponding object state.
		 * 
		 * @param entityAsXML the target entity with its state represented as XML
		 * @param version it is possible that implementors will want to have multiple
		 * 			ways to unmarshal an entity - the version is a user-defined "flag"
		 * 			that allows the implementor to vary the process as needed.
		 */ 
		function unmarshal(entityAsXML:XML=null, version:String=null):void;		 
		
		/**
		 * Implmentors map the object state to its
		 * corresponding xml state.  
		 * 
		 * @param version it is possible that implementors will want to have multiple
		 * 			ways to unmarshal an entity - the version is a user-defined "flag"
		 * 			that allows the implementor to vary the process as needed.
		 */ 
		function marshal(version:String=null):XML; 
	}
}