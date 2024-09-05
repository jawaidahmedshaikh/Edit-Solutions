package com.segsoftware.model.scriptProcessor
{
	import mx.collections.XMLListCollection;
	import mx.collections.ArrayCollection;
	
	
	public class SPRecordedOperator
	{
  		private var _operator:String;
        [SPRecordedRun] private var _spRecordedRuns:ArrayCollection = new ArrayCollection(); // collection of SPRecordedRun objects
    
    	/**
		 * Constructor
		 */
		public function SPRecordedOperator()
		{
		}
		
		/**
		 * @see #_operator
		 */ 		
		public function get operator():String
		{
			return _operator;	
		}
		
		/**
		 * @see #_operator
		 */ 
		[Bindable]
		public function set operator(operator:String):void
		{
			_operator = operator;
		}
		
		/**
		 * @see #_spRecordedRuns
		 */ 		
		public function get spRecordedRuns():ArrayCollection
		{
			return _spRecordedRuns;	
		}
		
		/**
		 * @see #_spRecordedRuns
		 */ 
		[Bindable]
		public function set spRecordedRuns(spRecordedRuns:ArrayCollection):void
		{
			_spRecordedRuns = spRecordedRuns;
		}
		
		/**
		 * Restores the state of this entity from the specified xml.
		 */ 
		public function unmarshal(xml:XML):void
		{
			this.operator = xml.Operator;
			
			for each (var recordedRunXML:XML in xml.SPRecordedRun)
			{
				var spRecordedRun:SPRecordedRun = new SPRecordedRun();
				
				spRecordedRun.unmarshal(recordedRunXML);
				
				this.spRecordedRuns.addItem(spRecordedRun);
			}
		}
	}
}