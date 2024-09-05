package com.segsoftware.model.query
{
	import com.segsoftware.model.SEGEntity;
	import com.segsoftware.model.SEGMap;
	import com.segsoftware.model.SEGMarshaller;
	
	/**
	 * BA's often have a need to define queries that can be executed realtime against
	 * the system. It is useful to define and store these queries to 
	 * be executed independently at some later time. Some examples of their use
	 * may be:
	 * 
	 * 1. During a Conversion, the scripter often needs to retrieve data back from the DB.
	 * 2. Reports and Correspondence.
	 * 3. A often-repeated ad-hoc query that a BA may wish to store.
	 * 
	 * Since the system is largely grounded in Hibernate, we are consolidating on
	 * using hql, although this is not an ultimate restriction in that sql may also
	 * be ultimately used. Hql has proven intuitive even for the BAs once they have
	 * some initial queries from which to copy and paste. Hql's easy use of
	 * parameters is also useful.
	 * 
	 */
	[Bindable]  
	public class Query extends SEGEntity
	{	
		/**
		 * Defines that hql will be the target query language.
		 */ 
		public static var TYPE_HQL:String = "HQL";
		
		/**
		 * Defines that sql will be the target query language.
		 */ 
		public static var TYPE_SQL:String = "SQL";
	
		/**
		 * PK.
		 */
		private var _queryPK:Number;
		
		/**
		 * A unique name to identify the query. It must
		 * match normal "java" naming standards (e.g. no spaces, etc.).
		 */ 
		private var _name:String;
		
		/**
		 * A meaningful free-form description.
		 */ 
		private var _description:String;
		
		/**
		 * The expression defined in the target language (almost certainly hql).
		 */ 
		private var _expression:String;
		
		/**
		 * The name of the database which the Query will be executed against
		 */
		private var _databaseName:String;
		
		/**
		 * HQL or SQL.
		 * @see #TYPE_HQL
		 * @see #TYPE_SQL
		 */ 
		private var _type:String;
		
		public function Query()
		{	
			defaults();	
		}
		
		/**
		 * Default state values.
		 */ 
		private final function defaults():void
		{
			// Not using a "property" - don't want to trigger a PropertyChange.
			this._type = TYPE_HQL;
			
			this._queryPK = 0; 
		}
		
		/**
		 * @se #_name
		 */ 
		public function get name():String
		{
			return this._name;
		}
		
		/**
		 * @see #_name 
		 */
		public function set name(name:String):void
		{			
			this._name = name;
		}
		
		/**
		 * @see #_description
		 */ 
		public function get description():String
		{
			return this._description;
		}
		
		/**
		 * @see #_description
		 */ 
		public function set description(description:String):void
		{
			this._description = description;
		}
		
		/**
		 * @see #_expression
		 */ 
		public function get expression():String
		{
			return this._expression;	
		}
		
		/**
		 * @see #_expression
		 */ 
		public function set expression(expression:String):void
		{
			this._expression = expression;
		}
		
		/**
		 * @see #_type
		 */ 
		public function get type():String
		{
			return this._type;	
		}
		
		/**
		 * @see #_type
		 */ 
		public function set type(type:String):void
		{
			this._type = type;
		}
		
		/**
		 * @see #_queryPK 
		 */
		public function get queryPK():Number
		{
			return this._queryPK;
		}
		
		/**
		 * @see #_queryPK
		 */ 
		public function set queryPK(queryPK:Number):void
		{
			this._queryPK = queryPK;
		}
		
		/**
		 * @see #_database
		 */ 
		public function get databaseName():String
		{
			return this._databaseName;
		}
		
		/**
		 * @see #_database
		 */ 
		public function set databaseName(databaseName:String):void
		{
			this._databaseName = databaseName;	
		}
		
		/**
		 * Builder for Query.
		 */ 
		/* 
		public static function buildQuery(name:String, description:String, type:String, expression:String, databaseName:String):Query
		{
			var query:Query = new Query();
			
			query.name = name;
			
			query.description = description;
			
			query.type = type;
			
			query.expression = expression;
			
			query.databaseName = databaseName;
			
			return query;			
		}	
		*/
		
		/**
		 * Updates this object with the specified values
		 */
		public function updateQuery(name:String, description:String, type:String, expression:String, databaseName:String):void
		{
			this.name = name;
			
			this.description = description;
			
			this.type = type;
			
			this.expression = expression;
			
			this.databaseName = databaseName;
		}	
		
		/**
		 * Marshals this object into XML
		 */
		override public function marshal(version:String=null):XML
		{
			super.marshal();
								
			return SEGMarshaller.getInstance().marshal(this);								
		}
		
		/**
		 * Unmarshals XML into a Query object
		 */
		override public function unmarshal(segEntityAsXML:XML=null, version:String=null):void
		{	
			this.name = segEntityAsXML.Name;
			
			this.description = segEntityAsXML.Description;
			
			this.type = segEntityAsXML.Type;
			
			this.expression = segEntityAsXML.Expression;
			
			this.databaseName = segEntityAsXML.DatabaseName;
			
			this.queryPK = segEntityAsXML.QueryPK;
		}	
		
		/**
		 * Finds all the persisted queries using the general find framework
		 */
		public static function findAll(callbackFunction:Function):void
		{
			var hql:String = "from Query query";

			var namedParameters:SEGMap = new SEGMap();
			
			QueryHelper.executeHQL(hql, namedParameters, QueryStatement.ENGINE, callbackFunction);
		}
	}
}
