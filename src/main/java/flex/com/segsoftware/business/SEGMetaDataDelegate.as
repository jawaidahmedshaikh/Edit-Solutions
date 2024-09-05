package com.segsoftware.business
{
	import mx.utils.XMLUtil;
	import mx.collections.XMLListCollection;
	import com.segsoftware.command.SEGRequest;
	
	/**
	 * Certain services span across any and all domain-specific 
	 * services.
	 */  
	public class SEGMetaDataDelegate extends SEGDelegate
	{
		public function SEGMetaDataDelegate(resultFunction:Function, faultFunction:Function, requestParameters:XMLListCollection)
		{
			super(resultFunction, faultFunction, requestParameters)
		}
		
		/**
		 * In the absense of an object model (we are avoiding
		 * duplicating SEG's object model in this Flex thin-rich-client),
		 * we still need XML reprensentations of key entities.
		 * 
		 * This facilitates considerable hard-coding on the front-end,
		 * while centralizing responsibilities (the front-end probably shouldn't
		 * know how to build the entities in the absense of an object model).
		 * 
		 * This builds an [empty] XML(VO) representation of the requested
		 * entity name. For example:
		 * 
		 * buildEntity("CodeTable") would return the following:
		 * 
		 *		<CodeTableVO>
		 *			<CodeTablePK></CodeTablePK>	
		 *			<CodeTableDefFK></CodeTableDefFK>
		 *			<Code></Code>
		 *			<CodeDesc></CodeDesc>	
		 *		</CodeTableVO>
		 */ 
		public function buildDefaultValueObjectVO():void
		{	
			var segRequest:SEGRequest = new SEGRequest("SEGMetaData", "getStubbedTableDocument", resultFunction, faultFunction, requestParameters);
				
			Services.dispatchServiceRequest(segRequest);			
		}
	}
}