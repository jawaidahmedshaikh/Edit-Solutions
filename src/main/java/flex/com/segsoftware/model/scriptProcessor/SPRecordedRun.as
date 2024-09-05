package com.segsoftware.model.scriptProcessor
{
	import mx.collections.ArrayCollection;
	import mx.collections.XMLListCollection;
	
	
	/**
	 * A single run within all of the operator's runs (SPRecordedOperator).  A single run
	 * contains an array of input documents, an array of output documents, an array of executed 
	 * script lines (SPRecordedScriptLine), and a string of run information (like timestamp, 
	 * execution parameters, etc)
	 */ 
	public class SPRecordedRun
	{
		private var _runInformation:String;
		private var _scriptLines:ArrayCollection = new ArrayCollection();	// array of SPRecordedScriptLine
		private var _inputDocuments:XMLListCollection = new XMLListCollection(); // array of xml documents
		private var _outputDocuments:XMLListCollection = new XMLListCollection(); // array of xml documents
      	
      	/**
		 * Constructor
		 */
		public function SPRecordedRun()
		{
		}
		
		/**
		 * @see #_runInformation
		 */ 		
		public function get runInformation():String
		{
			return _runInformation;	
		}
		
		/**
		 * @see #_runInformation
		 */ 
		[Bindable]
		public function set runInformation(runInformation:String):void
		{
			_runInformation = runInformation;
		}

		public function get scriptLines():ArrayCollection
		{
			return this._scriptLines;
		}
		
		[Bindable]
		public function set scriptLines(scriptLines:ArrayCollection):void
		{
			this._scriptLines = scriptLines;
		}
		
		public function get inputDocuments():XMLListCollection
		{
			return this._inputDocuments;
		}
		
		[Bindable]
		public function set inputDocuments(inputDocuments:XMLListCollection):void
		{
			this._inputDocuments = inputDocuments;
		}
		
		public function get outputDocuments():XMLListCollection
		{
			return this._outputDocuments;
		}
		
		[Bindable]
		public function set outputDocuments(outputDocuments:XMLListCollection):void
		{
			this._outputDocuments = outputDocuments;
		}
		
		/**
		 * Restores the state of this entity from the specified xml.
		 */ 
		public function unmarshal(xml:XML):void
		{
			this.runInformation = xml.RunInformation;
			
			for each (var scriptLineXML:XML in xml.SPRecordedScriptLine)
			{
				var spRecordedScriptLine:SPRecordedScriptLine = new SPRecordedScriptLine();
				
				spRecordedScriptLine.unmarshal(scriptLineXML);
				
				this.scriptLines.addItem(spRecordedScriptLine);
			}
			
			for each (var inputDocumentXML:XML in xml.InputDocument)
			{
				inputDocumentXML = parseOuterDocument(inputDocumentXML);
				this.inputDocuments.addItem(inputDocumentXML);
			}
			
			for each (var outputDocumentXML:XML in xml.OutputDocument)
			{
				outputDocumentXML = parseOuterDocument(outputDocumentXML);
				this.outputDocuments.addItem(outputDocumentXML);
			}
		}
		
		/**
		 * Removes the outer document (InputDocument or OutputDocument tag) from
		 * the document
		 */ 
		private function parseOuterDocument(document:XML):XML
		{
			var elements:XMLList = document.elements();
			
			var newDocument:XML = new XML(elements[0]);
			
			return newDocument;
		}
	}
}