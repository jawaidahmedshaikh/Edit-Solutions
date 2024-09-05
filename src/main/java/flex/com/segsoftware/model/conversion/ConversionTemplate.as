package com.segsoftware.model.conversion
{
	import com.segsoftware.utility.Util;
	
	/**
	 * A wrapper class for the GroupNode.RecordNode.ColumnNode
	 * composition that defines the rules for mapping a flat file
	 * to a basic XML equivalent. As of this writing, this is the
	 * second major iteration of the Conversion process. We were
	 * unable to use the first approach due to licensing issues
	 * with the underlying Flat File -> XML conversion utility we
	 * were considering. I opted to treat the ConversionTemplate
	 * as an abstract to any XML-based template to convert Flat File
	 * to XML.
	 */ 
	 [Bindable]
	public class ConversionTemplate
	{
		/**
		 * A unique user-defined name.
		 */ 
		private var _templateName:String;
		
		/**
		 * A user-defined description of this template.
		 */ 
		private var _templateDescription:String;
		
		/**
		 * The XML "schema" that defines the conversion rules
		 * to map the flat-file to some XML equivalent.
		 */ 
		private var _templateText:String;
		
		/**
		 * The time stamp that this defines that last time it was edited.
		 */ 
		private var _maintDateTime:String;
		
		/**
		 * The associated PK.
		 */ 
		private var _conversionTemplatePK:Number;
		
		public function ConversionTemplate()
		{
			// default PK
			conversionTemplatePK = new Number(0);			
		}
		
		/**
		 * @see #_templateName
		 */ 
		public function get templateName():String
		{
			return this._templateName;
		}
		
		/**
		 * @see #_templateName
		 */ 
		public function set templateName(templateName:String):void
		{
			this._templateName = templateName;
		}
		
		/**
		 * @see #_templateDescription
		 */ 
		public function get templateDescription():String
		{
			return this._templateDescription;
		}
		
		/**
		 * @see #_templateDescription
		 */ 
		public function set templateDescription(templateDescription:String):void
		{
			this._templateDescription = templateDescription;	
		}
		
		/**
		 * @see #_templateText
		 */ 
		public function get templateText():String
		{
			return this._templateText;	
		}
		
		/**
		 * @see #_templateText
		 */ 
		public function set templateText(templateText:String):void
		{
			this._templateText = templateText;
		}
		
		/**
		 * @see #_maintDateTime
		 */ 
		public function get maintDateTime():String
		{
			return this._maintDateTime;
		}
		
		/**
		 * @see #_maintDateTime
		 */ 
		public function set maintDateTime(maintDateTime:String):void
		{
			this._maintDateTime = maintDateTime;
		}
		
		/**
		 * @see @_conversionTemplatePK
		 */ 
		public function get conversionTemplatePK():Number
		{
			return this._conversionTemplatePK;
		}
		
		/**
		 * @see #_conversionTemplatePK
		 */ 
		public function set conversionTemplatePK(conversionTemplatePK:Number):void
		{
			this._conversionTemplatePK = conversionTemplatePK;	
		}
		
		/**
		 * Converts the state of the wrapped GroupNode to
		 * its ConversionTemplate equivalent. e.g.
		 * <ConversionTemplateVO>
		 * 	<TemplateName/>
		 *  <TemplateDescription/>
		 * 	<TemplateText/>
		 * </ConversionTemplateVO>
		 * 
		 */ 
		public function asXML():XML
		{
			var conversionTemplateVO:XML = <ConversionTemplateVO>
												<ConversionTemplatePK>{conversionTemplatePK}</ConversionTemplatePK>
												<TemplateName>{templateName}</TemplateName>
												<TemplateDescription>{templateDescription}</TemplateDescription>
												<TemplateText>{templateText}</TemplateText>
											</ConversionTemplateVO>;
											
			return conversionTemplateVO;				
		}
		
		/**
		 * Builds an instance of ConversionTemplate from the specified parameters.
		 */ 
		public static function buildConversionTemplate_V1(templateName:String, templateDescription:String, groupNode:GroupNode):ConversionTemplate
		{
			var conversionTemplate:ConversionTemplate = new ConversionTemplate();
			
			conversionTemplate.templateName = templateName;
			
			conversionTemplate.templateDescription = templateDescription;
			
			conversionTemplate.templateText = Util.formatXMLToString(groupNode.asXML, false);
			
			return conversionTemplate;
		}
		
		/**
		 * Builds an instance of ConversionTemplate from the specified parameters.
		 */ 
		public static function buildConversionTemplate_V2(conversionTemplateVO:XML):ConversionTemplate
		{
			var conversionTemplate:ConversionTemplate = new ConversionTemplate();
			
			conversionTemplate.templateName = conversionTemplateVO["TemplateName"];
			
			conversionTemplate.templateDescription = conversionTemplateVO["TemplateDescription"];
			
			conversionTemplate.templateText = conversionTemplateVO["TemplateText"];
			
			conversionTemplate.conversionTemplatePK = conversionTemplateVO["ConversionTemplatePK"];
			
			return conversionTemplate;
		}		
		
		/**
		 * Updates an instance of ConversionTemplate from the specified parameters.
		 */ 
		public static function updateConversionTemplate_V1(conversionTemplate:ConversionTemplate, templateName:String, templateDescription:String, groupNode:GroupNode):void
		{			
			conversionTemplate.templateName = templateName;
			
			conversionTemplate.templateDescription = templateDescription;
			
			conversionTemplate.templateText = Util.formatXMLToString(groupNode.asXML, false);
		}		
	}
}