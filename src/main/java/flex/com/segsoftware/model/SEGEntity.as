package com.segsoftware.model
{
	import com.segsoftware.model.conversion.ConversionNodeInterface;
	import flash.events.EventDispatcher;
	import mx.events.PropertyChangeEvent;
	import flash.utils.describeType;
	import com.segsoftware.utility.Util;
	import mx.collections.ArrayCollection;
	import com.segsoftware.model.event.ValidationEvent;

	/**
	 * SEG Entities that are established on the back-end need to have a corresponding
	 * representation on the front-end. To date, the following requirements exist for
	 * the actionscript implementation of a SEGEntity.
	 * 
	 * 1. The SEGEntity should be able to unmarshal itself to the proper state when
	 * given a corresponding xml document from the back-end.
	 * 
	 * 2. The SEGEntity should be able to marshal itself to the proper xml representation
	 * to be read by the back-end and unmarshalled.
	 * 
	 * 3. The SEGEntity should be able to detect a state change of its properties. The idea
	 * is that a SEGEntity does not need to be persisted if it has not changed. 
	 */ 
	public class SEGEntity extends EventDispatcher implements SEGMarshallable
	{	
		public static var DEFAULT_MAX_DATE:Date = new Date("12/31/9999");
		
		/**
		 * True if the state of this entity changed.
		 * This is not rigorous - it does not compare its state to
		 * that of the back-end. It should be sufficient to determine
		 * if it is required (worth-it) to try to persist the state
		 * of this entity to the back-end.
		 */ 
		private var _propertyChanged:Boolean;
		
		/**
		 * Boolean to flag if this entity should be deleted (when appropriate).
		 * If the entity has not been persisted, then delete means to simply remove from
		 * the current collection. If the entity has been persisted, then
		 * this will become a backend issue. 
		 */ 
		private var _deleted:Boolean;
		
		/**
		 * Event name for validation events.
		 */ 
		public static var VALIDATION_EVENT:String = "VALIDATION EVENT";
	
		public function SEGEntity()
		{
			initSEGEntity();
		}
		
		/**
		 * @see #_deleted
		 */ 
		public function get deleted():Boolean
		{
			return this._deleted;
		}
		
		/**
		 * @see #_deleted
		 */ 
		public function set deleted(deleted:Boolean):void
		{
			this._deleted = deleted;
		}
		
		/**
		 * Initial state.
		 */ 
		private final function initSEGEntity():void
		{
			this.propertyChanged = false;
			
			this.addEventListener(PropertyChangeEvent.PROPERTY_CHANGE, segEntityPropertyChangeHandler);
		}		
		
		/**
		 * @see #_propertyChanged
		 */ 
		public function get propertyChanged():Boolean
		{
			return this._propertyChanged;
		}
		
		/**
		 * @see #_propertyChanged
		 */ 
		public function set propertyChanged(propertyChanged:Boolean):void
		{
			this._propertyChanged = propertyChanged;
		}
		
		/**
		 * Flags that this entity has had a state change.
		 */ 
		private function segEntityPropertyChangeHandler(event:PropertyChangeEvent):void
		{
			this.propertyChanged = true;			
		}
		
		/**
         * Entities will be marshalled on the back-end, and reconstituted on the
         * front-end. The implementing actionscript class needs to be able
         * to restore its state given the specified xml.
		 */ 
		public function unmarshal(segEntityAsXML:XML=null, version:String=null):void
		{	
			this.propertyChanged = false; // once unmarshalled, it [is] the initial state
		}
		
		/**
		 * Entities will be marshalled on the front-end, and reconstituted on the 
		 * back-end. The implementing actionscript class needs to be able to represent
		 * its current state as xml using known parameter names (likely dictated by DB table columns).
		 */ 
		public function marshal(version:String=null):XML
		{			
			return null;
		}
		
		
		/**
		 * True if this entity has been persisted previously.
		 */ 
		public function isPersisted():Boolean
		{	
			var isPersisted:Boolean = true;
			
			var pkValue:Number = Util.getPKValue(this);
			
			if (isNaN(pkValue))
			{
				isPersisted = false;
			}
			else if (pkValue == 0)
			{
				isPersisted = false;
			}
			
			return isPersisted;
		}	
	}
}