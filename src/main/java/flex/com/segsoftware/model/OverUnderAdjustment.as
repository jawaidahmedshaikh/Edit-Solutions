package com.segsoftware.model
{
	import com.segsoftware.utility.*;
	
	/**
	 * Part of Billing is the ability to adjust certain fund amounts that 
	 * are being stored in the BillGroupVO. Once adjusted, the "balance" 
	 * needs to be updated. This class is really a wrapper to the BillGroupVO
	 * fund amounts that also adds maintenance of the "balance";
	 * 
	 * The underlying BillGroupVO's fund values are also updated as the
	 * OverUnderAdjustment values are updated.
	 */ 
	public class OverUnderAdjustment
	{
		/**
		 * The summation of:
		 * FundsAmount - AllocatedAmount - OverageFundsAmount + ShortageFunds + CreditFunds.
		 */  
		private var _balance:Number;
		
		/**
		 * Taken from BillGroupVO.TotalBilledAmount.
		 */ 
		private var _fundsAmount:Number;
		
		/**
		 * Taken from BillGroupVO.TotalPaidAmount.
		 */ 
		private var _allocatedAmount:Number;
		
		/**
		 * Taken from BillGroupVO.OverageFundsAmount.
		 */ 
		private var _overageFundsAmount:Number;
		
		/**
		 * Taken from BillGroupVO.ShortageFundsAmount.
		 */ 
		private var _shortageFundsAmount:Number;
		
		/**
		 * Taken from BillGroupVO.CreditFundsAmount.
		 */ 		
		private var _creditFundsAmount:Number;
		
		/**
		 * Constructor. Data is bound to the 
		 * currently selected BillGroupVO.
		 */ 
		public function OverUnderAdjustment()
		{
			fundsAmount = Util.initNumber(Util.unformatCurrency(SEGModelLocator.getInstance().selectedBillGroupVO.TotalBilledAmount), 0.00);
			
			allocatedAmount = Util.initNumber(Util.unformatCurrency(SEGModelLocator.getInstance().selectedBillGroupVO.TotalPaidAmount), 0.00);
			
			overageFundsAmount = Util.initNumber(Util.unformatCurrency(SEGModelLocator.getInstance().selectedBillGroupVO.OverageFundsAmount), 0.00);
			
			shortageFundsAmount = Util.initNumber(Util.unformatCurrency(SEGModelLocator.getInstance().selectedBillGroupVO.ShortageFundsAmount), 0.00);
			
			creditFundsAmount = Util.initNumber(Util.unformatCurrency(SEGModelLocator.getInstance().selectedBillGroupVO.CreditFundsAmount), 0.00);
			
			calculateFundsBalalance();
		}
		
		/**
		 * Calculates the funds balance as:
		 * 
		 * fundsAmount - allocatedAmount - overageFundsAmount + shortageFundsAmount + creditFundsAmount = balance
		 * 
		 * @return the calculated balance as defined by the above formula
		 */ 
		private function calculateFundsBalalance():void
		{
			this.balance = fundsAmount - allocatedAmount - overageFundsAmount + shortageFundsAmount + creditFundsAmount;
		}	

		/**
		 * @see #_balance
		 */ 
		[Bindable]		
		public function set balance(balance:Number):void
		{
			this._balance = balance;
		}
		
		/**
		 * @see #_balance
		 */ 
		public function get balance():Number
		{
			return _balance;
		}
		
		/**
		 * @see #_fundsAmount
		 */ 
		[Bindable]  
		public function set fundsAmount(fundsAmount:Number):void
		{
			this._fundsAmount = fundsAmount;
			
			calculateFundsBalalance();			
		}
		
		/**
		 * @see #_fundsAmount 
		 */ 
		public function get fundsAmount():Number
		{
			return this._fundsAmount;
		}
		
		/**
		 * @see #_allocatedAmount
		 */ 
		[Bindable] 
		public function set allocatedAmount(allocatedAmount:Number):void
		{
			this._allocatedAmount = allocatedAmount;	
					
			calculateFundsBalalance();		
		}
		
		/**
		 * @see #_allocatedAmount
		 */ 
		public function get allocatedAmount():Number
		{
			return this._allocatedAmount;
		}
		
		/**
		 * @see #_overageFundsAmount
		 */ 
		[Bindable]  
		public function set overageFundsAmount(overageFundsAmount:Number):void
		{
			this._overageFundsAmount = overageFundsAmount;
			
			SEGModelLocator.getInstance().selectedBillGroupVO.OverageFundsAmount = overageFundsAmount;									
			
			calculateFundsBalalance();			
		}
		
		/**
		 * @see #_overageFundsAmount
		 */ 
		public function get overageFundsAmount():Number
		{
			return this._overageFundsAmount;
		}
		
		/**
		 * @see #_shortageFundsAmount
		 */ 
		[Bindable]  
		public function set shortageFundsAmount(shortageFundsAmount:Number):void
		{
			this._shortageFundsAmount = shortageFundsAmount;
			
  			SEGModelLocator.getInstance().selectedBillGroupVO.ShortageFundsAmount = shortageFundsAmount;													
			
			calculateFundsBalalance();				
		}
		
		/**
		 * @see #_shortageFundsAmount
		 */ 
		public function get shortageFundsAmount():Number
		{
			return this._shortageFundsAmount;	
		}
		
		/**
		 * @see #_creditFundsAmount
		 */ 
		[Bindable]  
		public function set creditFundsAmount(creditFundsAmount:Number):void
		{
			this._creditFundsAmount = creditFundsAmount;
			
			SEGModelLocator.getInstance().selectedBillGroupVO.CreditFundsAmount = creditFundsAmount;			
			
			calculateFundsBalalance();			
		}
		
		/**
		 * @see #_creditFundsAmount
		 */ 
		public function get creditFundsAmount():Number
		{
			return _creditFundsAmount;	
		}
	}
}