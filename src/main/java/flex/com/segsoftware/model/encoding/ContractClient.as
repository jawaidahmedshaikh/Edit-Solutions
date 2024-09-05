package com.segsoftware.model.encoding
{
	import com.segsoftware.utility.Util;
	
	/**
	 * A Client that exists on a contract (i.e. the ClientDetail and ClientRole information)
	 * 
	 * This class is not a true class yet since it uses existing XML VOs to define the clientDetail
	 * and clientRole.  This was a first pass attempt to get objects into the encoding/appEntry
	 * system.  At this time, this class has a method that returns the client's full name.  Ideally, this
	 * should exist on a ClientDetail class but that does not exist yet.
	 */
	public class ContractClient
	{
		private var _appEntryClientDetailUIVO:XML;
		
		private var _clientRoleUIVO:XML;
		
		public function ContractClient(appEntryClientDetailUIVO:XML, clientRoleUIVO:XML)
		{
			_appEntryClientDetailUIVO = appEntryClientDetailUIVO;
			_clientRoleUIVO = clientRoleUIVO;
		}

		public function get appEntryClientDetailUIVO():XML
		{
			return _appEntryClientDetailUIVO;
		}
		
		public function set appEntryClientDetailUIVO(appEntryClientDetailUIVO:XML):void
		{
			_appEntryClientDetailUIVO = appEntryClientDetailUIVO;
		}
		
		public function get clientRoleUIVO():XML
		{
			return _clientRoleUIVO;
		}
		
		public function set clientRoleUIVO(clientRoleUIVO:XML):void
		{
			_clientRoleUIVO = clientRoleUIVO;
		}
		
		public function getRelationshipToEmployeeCT():String
		{
			return appEntryClientDetailUIVO.RelationshipToEmployeeCT;
		}
		
		public function getClientName():String
		{
			var clientDetailVO:XML = appEntryClientDetailUIVO.ClientDetailVO[0];
				
			return Util.determineClientName(clientDetailVO);	
		}
	}
}