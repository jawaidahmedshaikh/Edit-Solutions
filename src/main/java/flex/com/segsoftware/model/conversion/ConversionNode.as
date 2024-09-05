package com.segsoftware.model.conversion
{
	import flash.events.EventDispatcher;
	import flash.events.Event;
	import com.segsoftware.event.SEGConversionEvent;
	import mx.collections.ArrayCollection;
	import mx.events.CollectionEvent;
	import mx.events.PropertyChangeEvent;
	
	/**
	 * When creating a mapping template to convert (e.g.) a flat file 
	 * format to XML, we need to capture mapping information. The concept
	 * of "Node" is introduced since it is envisioned that mapping documents
	 * consist of a Group, RecordType, ColumnType that are combined into composite
	 * structures that [resemble] a XML Schema.
	 */ 
	[Bindable]
	public class ConversionNode extends EventDispatcher implements ConversionNodeInterface 
	{
		/**
		 * The name of the asXML property.
		 */ 
		public static var PROPERTY_AS_XML:String = "asXML";
		
		/**
		 * ConversionNodes can be added to other ConversionNodes to create complex
		 * data structures.
		 */ 
		private var _childNodes:ArrayCollection = new ArrayCollection();
		
		/**
		 * The XML version of this node to be implemented by any subclasses.
		 */ 
		private var _asXML:XML;
		
		public function ConversionNode()
		{
			childNodes.addEventListener(CollectionEvent.COLLECTION_CHANGE, childNodeCollectionChangedHandler);	
		}
		
		/**
		 * Responds to any child ConversionNode being added/removed and dispatches to the corresponding
		 * "childNode[Added/Removed]Handler".
		 */ 
		private function childNodeCollectionChangedHandler(collectionEvent:CollectionEvent):void
		{		
			var conversionNode:ConversionNode = null;
			
			if(collectionEvent.kind == "add")
			{
				conversionNode = new ArrayCollection(collectionEvent.items).getItemAt(0) as ConversionNode;
				
				conversionNode.addEventListener(CollectionEvent.COLLECTION_CHANGE, childNodeAddedHandler);
				
				conversionNode.addEventListener(PropertyChangeEvent.PROPERTY_CHANGE, childNodePropertyChangedHandler);
				
				childNodeAddedHandler(collectionEvent);
				
				dispatchEvent(collectionEvent);
			}
			else if (collectionEvent.kind == "remove")
			{
				conversionNode = new ArrayCollection(collectionEvent.items).getItemAt(0) as ConversionNode;				
				
				conversionNode.removeEventListener(CollectionEvent.COLLECTION_CHANGE, childNodeRemovedHandler);
				
				conversionNode.removeEventListener(PropertyChangeEvent.PROPERTY_CHANGE, childNodePropertyChangedHandler);
				
				childNodeRemovedHandler(collectionEvent);
				
				dispatchEvent(collectionEvent);
			}
		}
		
		/**
		 * Intended to be overridden by parent ConversionNode classes that wish 
		 * to track the property changes of their child nodes. The parent can single
		 * out the specific property they seek since the property name will match the
		 * property name of the getter/setter of the child.
		 */ 
		public function childNodePropertyChangedHandler(event:PropertyChangeEvent):void
		{
			// Continue propagating event
			this.dispatchEvent(event);
		}
		
		/**
		 * Intended to be overridden by the parent ConversionNode class so that
		 * it may respond to whenever a child ConversionNode is added to its child collection.
		 */ 
		public function childNodeAddedHandler(collectionEvent:CollectionEvent):void
		{
			// Continue propagating event			
			this.dispatchEvent(collectionEvent);
		}
		
		/**
		 * Intended to be overridden by the parent ConversionNode class so that
		 * it may respond to whenever a child ConversionNode is added to its child collection.
		 */ 
		public function childNodeRemovedHandler(collectionEvent:CollectionEvent):void
		{
			// Continue propagating event			
			this.dispatchEvent(collectionEvent);			
		}		
		
		/**
		 * @see #_childNodes
		 */ 
		public function get childNodes():ArrayCollection
		{
			return this._childNodes;	
		}
		
		/**
		 * @see #_childNodes
		 */ 
		public function set childNodes(childNodes:ArrayCollection):void
		{
			this._childNodes = childNodes;	
		}
		
		/**
		 * The number of child ConversionNodes contained in this parent ConversionNode.
		 */ 
		public function get childNodeCount():int
		{
			return this.childNodes.length;	
		}
		
		/**
		 * @see #_asXML
		 */ 
		public function get asXML():XML
		{
			return this._asXML;	
		}
		
		/**
		 * @see #_asXML 
		 */
		public function set asXML(asXML:XML):void
		{
			this._asXML = asXML;
		}
		
		/**
		 * Satisifies the implemented interface - there is no concept of "abstract".
		 */ 
		public function marshal():XML
		{
			return null;
		}
		
		/**
		 * Satisifies the implemented interface - there is no concept of "abstract".
		 */ 		
		public function unmarshal(nodeAsXML:XML):void
		{
			
		}
	}
}