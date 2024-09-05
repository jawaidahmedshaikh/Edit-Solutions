package com.segsoftware.model.scriptProcessor
{
	/**
	 * A single executed script line.  Each script lines contains a type and a result.  The
	 * type is usually an instruction, push, or pop.  The result is what happened as a result of
	 * executing the script line.
	 */ 
	public class SPRecordedScriptLine
	{
        private var _type:String;
		private var _result:String;
		private var _typeIcon:Class;
		
		public static var SCRIPTLINE_TYPE_INSTRUCTION:String = "Instruction"; 
		public static var SCRIPTLINE_TYPE_PUSH:String = "Push"; 
		public static var SCRIPTLINE_TYPE_POP:String = "Pop"; 
		
		[Embed(source="/com/segsoftware/view/assets/icons/notes.gif")] 
        public static var typeIcon_Instruction:Class;
	
		[Embed(source="/com/segsoftware/view/assets/icons/arrowSelectedIcon.gif")] 
        public static var typeIcon_Push:Class;
        
        [Embed(source="/com/segsoftware/view/assets/icons/arrowLeft.gif")] 
        public static var typeIcon_Pop:Class;
      	
      	/**
		 * Constructor
		 */
		public function SPRecordedScriptLine()
		{
		}
		
		/**
		 * @see #_type
		 */ 		
		public function get type():String
		{
			return _type;	
		}
		
		/**
		 * @see #_type
		 */ 
		[Bindable]
		public function set type(type:String):void
		{
			_type = type;
		}
		
		/**
		 * @see #_result
		 */ 		
		public function get result():String
		{
			return _result;	
		}
		
		/**
		 * @see #_result
		 */ 
		[Bindable]
		public function set result(result:String):void
		{
			_result = result;
		}
		
		/**
		 * @see #_typeIcon
		 */ 		
		public function get typeIcon():Class
		{
			return _typeIcon;	
		}
		
		/**
		 * @see #_typeIcon
		 */ 
		[Bindable]
		public function set typeIcon(typeIcon:Class):void
		{
			_typeIcon = typeIcon;
		}
		
		/**
		 * Restores the state of this entity from the specified xml.
		 */ 
		public function unmarshal(xml:XML):void
		{
			this.type = xml.Type;
			
			this.result = xml.Result;
			
			this.typeIcon = determineTypeIcon(this.type);
		}
		
		private function determineTypeIcon(type:String):Class
		{
			if (type == SCRIPTLINE_TYPE_INSTRUCTION)
			{
				return typeIcon_Instruction;
			}
			else if (type == SCRIPTLINE_TYPE_PUSH)
			{
				return typeIcon_Push;
			}
			else if (type == SCRIPTLINE_TYPE_POP)
			{
				return typeIcon_Pop;
			}
			else
			{
				return null;
			}	
			
		}
	}
}