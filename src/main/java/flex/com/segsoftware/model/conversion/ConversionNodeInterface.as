package com.segsoftware.model.conversion
{
	public interface ConversionNodeInterface
	{
		/**
		 * Every implementing class is to provide the
		 * node to XML mapping.
		 */ 
		function marshal():XML;		
		
		/**
		 * Every implementing class is to provide the
		 * XML to ConversionNode mapping.
		 */ 
		function unmarshal(nodeAsXML:XML):void;
	}
}