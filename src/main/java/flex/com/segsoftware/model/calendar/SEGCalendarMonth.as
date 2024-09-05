package com.segsoftware.model.calendar
{
	import com.segsoftware.view.payroll.SEGCalendar;
	
	/**
	 * Represents
	 */ 
	public class SEGCalendarMonth
	{
		/**
		 * The year represented by this SEGCalendarMonth.
		 */ 
		private var _year:int;
		
		/**
		 * The zero-indexed month represented by this SEGCalendarMonth.
		 */ 
		private var _month:int;		
		
		private var weeks:Array = [0, 1, 2, 3, 4, 5];
		
		/**
		 * Constructor.
		 */
		public function SEGCalendarMonth(year:int, month:int)
		{
			this._year = year;
			
			this._month = month;
			
			buildSEGCalendarMonthDays();
		}
		
		/**
		 * For the current SEGCalendarMonth, builds the Sunday through Saturday
		 * daily cells for each week in this calendar month. This is a little brute-force
		 * in that 6 rows of 7 cells for each month are [always] built regardless of the
		 * month. This is to help support the representation in grid-based views which will
		 * likely have to render as many as as 6 weeks (some are partial) in a month
		 * with 31 days.
		 */
		private function buildSEGCalendarMonthWeeks():void
		{
			// TODO - you may just want to loop through the days in a month and mod 7 them
			// to get the week that they fall into. This will give you the direct
			// building of the days of the month. I'm not confident that driving this
			// by the weeks in a month is the best way since there are no normal Date
			// operations to determine the number of weeks in a month.	
			
			for each (var week:int in SEGCalendarWeek.WEEKS)
			{
				var segCalendarWeek:SEGCalendarWeek = new SEGCalendarWeek(year, month, week);								
			}						
		}
		
		/**
		 * @see #_year
		 */ 
		public function set year(year:int):void
		{
			_year = year;
		}

		/**
		 * @see #_year
		 */ 		
		public function get year():int
		{
			return _year;
		}
		
		/**
		 * @see #_month
		 */ 		
		public function set month(month:int):void
		{
			_month = month;
		}
		
		/**
		 * @see #_month
		 */ 			
		public function get month():int
		{
			return _month;
		}
	}
}