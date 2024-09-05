package com.segsoftware.model.calendar
{
	/**
	 * Maps the seven days of a week to the calendar specified
	 * starting on Sunday ending with Saturday.
	 * 
	 * For example, if the week is WEEK_0, but the first day of the month does not
	 * occur until Tuesday, then this SEGCalendarWeek would be filled as:
	 * 
	 * Sunday:		-
	 * Monday:		-
	 * Tuesday:		1
	 * Wednesday:	2
	 * Thursday:	3
	 * Friday:		4
	 * Saturday:	5
	 */  
	public class SEGCalendarWeek
	{		
		public static final var WEEK_0:int = 0;
		
		public static final var WEEK_1:int = 1;
		
		public static final var WEEK_2:int = 2;
		
		public static final var WEEK_3:int = 3;
		
		public static final var WEEK_4:int = 4;
		
		public static final var WEEK_5:int = 5;
		
		public static final var WEEKS:Array = {WEEK_0, WEEK_1, WEEK_2, WEEK_3, WEEK_4, WEEK_5};								
		
		/**
		 * The year associated with this SEGCalendarWeek.
		 */ 
		private var _year:int;

		/**
		 * The 0-indexed month associated with this SEGCalendarWeek.
		 */ 		
		private var _month:int;

		/**
		 * The 0-indexed week that identifies this SEGCalendarWeek. Possible values
		 * are 0 - 5 since there can be at most 6 weeks in a 31 day month.
		 */ 		
		private var _week:int;
		
		public function SEGCalendarWeek(year:int, month:int, week:int)
		{
			this.year = year;
			
			this.month = month;
			
			this.week = week;
			
			buildSEGCalendarDays();			
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
		
		/**
		 * @see #_week 
		 */ 
		public function set week(week:int):void
		{
			_week = week;
		}
		
		/**
		 * @see #_week
		 */ 
		public function get week():int
		{
			return _week;
		}
	}
}