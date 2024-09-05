package com.segsoftware.model.engine
{
	import com.segsoftware.model.SEGEntity;
	import mx.rpc.events.AbstractEvent;
	import com.segsoftware.model.SEGMarshaller;

	public class AreaValue extends SEGEntity
	{
		private var _areaValuePK:Number;
		
		private var _areaKeyFK:Number;
		
		private var _areaKey:AreaKey;
		
		private var _areaValue:String;
		
		private var _qualifierCT:String;
		
		public function AreaValue()
		{
			
		}
		
		public function get areaValuePK():Number
		{
			return this._areaValuePK;
		}
		
		public function set areaValuePK(areaValuePK:Number):void
		{
			this._areaValuePK = areaValuePK;
		}
		
		public function get areaKeyFK():Number
		{
			return this._areaKeyFK;
		}
		
		public function set areaKeyFK(areaKeyFK:Number):void
		{
			this._areaKeyFK = areaKeyFK;
		}
		
		public function get areaKey():AreaKey
		{
			return this._areaKey;
		}
		
		public function set areaKey(areaKey:AreaKey):void
		{
			this._areaKey = areaKey;
		}
		
		public function get areaValue():String
		{
			return this._areaValue;
		}
		
		public function set areaValue(areaValue:String):void
		{
			this._areaValue = areaValue;
		}
		
		public function get qualifierCT():String
		{
			return this._qualifierCT;
		}
		
		public function set qualifierCT(qualifierCT:String):void
		{
			this._qualifierCT = qualifierCT;
		}
		
		/**
		 *  Unmarshalls the specified AreaValue as XML and its
		 * parent AreaKey (if available).
		 */ 
		override public function unmarshal(areaValueAsXML:XML=null, version:String=null):void
		{
			super.unmarshal();
			
			SEGMarshaller.getInstance().unmarshal(areaValueAsXML, this);
			
			if (areaValueAsXML.AreaKeyVO)
			{
				var areaKey:AreaKey = new AreaKey();
				
				areaKey.unmarshal(areaValueAsXML.AreaKeyVO[0]);	
				
				this.areaKey = areaKey;	
			}
			
		}
	}
}