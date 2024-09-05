package com.segsoftware.model.engine
{
	import com.segsoftware.model.SEGEntity;
	import com.segsoftware.model.SEGMarshaller;

	public class AreaKey extends SEGEntity
	{
		private var _areaKeyPK:Number;
		
		private var _grouping:String;
		
		private var _field:String;
		
		public function AreaKey()
		{
			
		}
		
		public function get areaKeyPK():Number
		{
			return this._areaKeyPK;
		}
		
		public function set areaKeyPK(areaKeyPK:Number):void
		{
			this._areaKeyPK = areaKeyPK;
		}
		
		public function get grouping():String
		{
			return this._grouping;
		}
		
		public function set grouping(grouping:String):void
		{
			this._grouping = grouping;
		}
		
		public function get field():String
		{
			return this._field;
		}
		
		public function set field(field:String):void
		{
			this._field = field;
		}
		
		/**
		 * Unmarshals the specified areakeyAsXML to this AreaKey.
		 */ 
		override public function unmarshal(areaKeyAsXML:XML=null, version:String=null):void
		{
			super.unmarshal();
			
			SEGMarshaller.getInstance().unmarshal(areaKeyAsXML, this);					
		}
	}
}