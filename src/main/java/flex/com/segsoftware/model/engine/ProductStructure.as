package com.segsoftware.model.engine
{
	import com.segsoftware.model.SEGEntity;
	import com.segsoftware.model.SEGMarshaller;

	[Bindable]
	public class ProductStructure extends SEGEntity
	{
		private var _productStructurePK:Number;
		
		private var _companyFK:Number;
		
		private var _company:Company;
		
		private var _marketingPackageName:String;
		
		private var _groupProductName:String;
		
		private var _areaName:String;
		
		private var _businessContractName:String;
		
		public function ProductStructure()
		{
			
		}
		
		public function get marketingPackageName():String
		{
			return this._marketingPackageName;
		}
		
		public function set marketingPackageName(marketingPackageName:String):void
		{
			this._marketingPackageName = marketingPackageName;
		}
		
		public function get groupProductName():String
		{
			return this._groupProductName;	
		}
		
		public function set groupProductName(groupProductName:String):void
		{
			this._groupProductName = groupProductName;
		}
		
		public function get areaName():String
		{
			return this._areaName;	
		}
		
		public function set areaName(areaName:String):void
		{
			this._areaName = areaName;
		}
		
		public function get businessContractName():String
		{
			return this._businessContractName;			
		}
		
		public function set businessContractName(businessContractName:String):void
		{
			this._businessContractName = businessContractName;
		}
		
		public function get productStructurePK():Number
		{
			return this._productStructurePK;
		}
		
		public function set productStructurePK(productStructurePK:Number):void
		{
			this._productStructurePK = productStructurePK;
		}
		
		public function get companyFK():Number
		{
			return this._companyFK;
		}
		
		public function set companyFK(companyFK:Number):void
		{
			this._companyFK = companyFK;
		}
		
		public function get company():Company
		{
			return this._company;
		}
		
		public function set company(company:Company):void
		{
			this.companyFK = company.companyPK;
			
			this._company = company;
		}

		/**
		 * Unmarshals the ProductStructure and Company (if available).
		 */ 		
		override public function unmarshal(productStructureAsXML:XML=null, version:String=null):void
		{
			super.unmarshal();
		
			SEGMarshaller.getInstance().unmarshal(productStructureAsXML, this);

			if (productStructureAsXML.CompanyVO)
			{
				var company:Company = new Company();
				
				company.unmarshal(productStructureAsXML.CompanyVO[0]); // there's only one	
				
				this.company = company;
			}				
		}
	}
}