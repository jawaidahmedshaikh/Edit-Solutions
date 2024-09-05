package com.segsoftware.model.engine
{
	import com.segsoftware.model.SEGEntity;
	import com.segsoftware.model.SEGMarshaller;
	
	[Bindable]
	public class Company extends SEGEntity
	{
		private var _companyPK:Number;
		
		private var _companyName:String;
		
		public function Company()
		{
			
		}
		
		public function get companyPK():Number
		{
			return this._companyPK;
		} 
		
		public function set companyPK(companyPK:Number):void
		{
			this._companyPK = companyPK;	
		}
		
		public function get companyName():String
		{
			return this._companyName;
		}
		
		public function set companyName(companyName:String):void
		{
			this._companyName = companyName;
		}
		
		override public function unmarshal(companyAsXML:XML=null, version:String=null):void
		{
			super.marshal();
			
			SEGMarshaller.getInstance().unmarshal(companyAsXML, this);
		}
	}
}