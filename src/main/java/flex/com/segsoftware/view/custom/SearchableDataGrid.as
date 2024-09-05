package com.segsoftware.view.custom
{
    import flash.display.Sprite;
    
    import mx.collections.ArrayCollection;
    import mx.controls.*;

	/**
	 * Custom class that allows searching of the dataGrid and highlights the rows that contain the
	 * found text.  It also allows the user to go to the next or previous row found based on the
	 * currently selected row (i.e. uses the currently selected row as the starting cursor to
	 * determine which row is "next" or "previous").
	 * 
	 * See ScriptLineDisplay as an example of its usage.  The using class must provide
	 * a color to use for highlighted rows and the name of the column to be searched. 
	 * 
	 * Originally obtained from the internet as a class called CustomRowColorDG which just handled the
	 * row highlighting.
	 */
    public class SearchableDataGrid extends DataGrid
    {
        /**
         * Column in the dataGrid that will be searched on
         */
        private var _searchColumn:String;
        
        /**
         * Color to use for highlighted rows
         */
        private var _highlightedRowColor:uint;

		/**
		 * Array containing the data found to match the search criteria in the grid's dataProvider
		 */        
        private var _hits:Array = new Array();

		private var _rowIndexes:Array = new Array();

        /**
         * @see _searchColumn
         */
        public function set searchColumn(searchColumn:String):void
        {
        	this._searchColumn = searchColumn;
        }
        
        /**
         * @see _searchColumn
         */
        public function get searchColumn():String
        {
        	return this._searchColumn;
        }
        
        /**
         * @see _highlightedRowColor
         */
        public function set highlightedRowColor(highlightedRowColor:uint):void
        {
        	this._highlightedRowColor = highlightedRowColor;
        }
        
        /**
         * @see _highlightedRowColor
         */
        public function get highlightedRowColor():uint
        {
        	return this._highlightedRowColor;
        }

		/**
		 * @see _hits
		 */            
        public function get hits():Array
        {
        	return this._hits;
        }
        
        public function get rowIndexes():Array
        {
        	return this._rowIndexes;
        }
            
            
        /**
		 * Search the dataProvider for the search text in the specified column
		 */ 
        public function search(searchString:String):void
        {	
        	clearSearchArrays();   			
         	       
         	var dataProvider:ArrayCollection = this.dataProvider as ArrayCollection;
         	
         	if (searchString.length > 0)	// the user entered text to search, now do it
         	{
             	for (var i:int = 0; i < dataProvider.length; i++)
             	{
             		if (dataProvider[i][searchColumn].indexOf(searchString) > -1)
             		{
             			hits.push(dataProvider[i]);
             			rowIndexes.push(i);
             		}	
             	}
            }
 	
          	// Force redraw of grid rows
         	this.invalidateDisplayList();								
     	}
     	
     	/**
     	 * Clears the search results
     	 */
     	public function clearSearch():void
     	{
     		clearSearchArrays();	
         	
         	// Force redraw of grid rows 
         	this.invalidateDisplayList();
     	}
     	
     	/**
     	 * Selects and scrolls to the next hit in the search.  
     	 */
     	public function goToNextHit():void
		{
			var nextIndex:int = findNextIndex();
			
			this.selectedIndex = nextIndex;
			this.scrollToIndex(nextIndex);		
		}
		
		/**
     	 * Selects and scrolls to the previous hit in the search.  Uses the current
     	 * selectedIndex as the starting point to decide what is "previous"
     	 */	
		public function goToPreviousHit():void
		{
			var previousIndex:int = findPreviousIndex();
			
			this.selectedIndex = previousIndex;
			this.scrollToIndex(previousIndex);
		}
		
		/**
		 * Clears the arrays containing the search results
		 */
		private function clearSearchArrays():void
		{
         	hits.length = 0; 
         	rowIndexes.length = 0;  
		}
		
		/**
		 * Determines the "next" index in the search.  Uses the current
     	 * selectedIndex as the starting point to decide what is "next".  
     	 * Then compares the selectedIndex (cursor) to the indices of the
     	 * search hits.  
     	 */
		private function findNextIndex():int
		{
			var cursorIndex:int = this.selectedIndex;
			
			//	Check to see if the cursor is already at the last hit
			//	in the list.  If it is, set the cursor to the beginning so
			//	the first hit will be found.
			if (cursorIndex >= rowIndexes[rowIndexes.length-1])
			{
				cursorIndex = -1;
			}
			
			var nextIndex:int = cursorIndex;
			
           	for (var i:int = 0; i < rowIndexes.length; i++)
           	{
           		if (cursorIndex < rowIndexes[i])
           		{
           			nextIndex = rowIndexes[i];
           			
           			break;	
           		}
           	}

			return nextIndex;	
		}
		
		/**
		 * Determines the "previous" index in the search.  Uses the current
     	 * selectedIndex as the starting point to decide what is "previous".  
     	 * Then compares the selectedIndex (cursor) to the indices of the
     	 * search hits.  
     	 */
		private function findPreviousIndex():int
		{
			var cursorIndex:int = this.selectedIndex;
			
			//	Check to see if the cursor is already at the first hit
			//	in the list.  If it is, set the cursor just past the 
			//	last index so the last hit will be found (do this by adding
			//	1 to the value of the last index)
			if (cursorIndex <= rowIndexes[0])
			{
				cursorIndex = rowIndexes[rowIndexes.length-1] + 1;
			}
			
			var previousIndex:int = cursorIndex;
			
           	for (var i:int = rowIndexes.length-1; i >= 0; i--)
           	{
           		if (rowIndexes[i] < cursorIndex)
           		{
           			previousIndex = rowIndexes[i];
           			
           			break;
           		}
           	}

			return previousIndex;	
		}
     	
		/**
		 *	Returns the appropriate row color depending on whether the row was found in the
		 *  search (i.e. in the hits list) or not
		 */
		public function getRowColor(item:Object, color:uint, dataIndex:int):uint
        {
            if (this.hits.indexOf(item) > -1)
            {
                return 0xFFFF00;		// highlighted in yellow
            }
            else
            {
            	return color;			// not in hit list, no highlighting
            }
		}
        
        /**
         * Comment from original CustomRowColorDG class:
         * I wish this was protected, or internal so I didn't have to recalculate it myself.
         */
        private var displayWidth:Number;
         
        override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
        {
            super.updateDisplayList(unscaledWidth, unscaledHeight);            
            if (displayWidth != unscaledWidth - viewMetrics.right - viewMetrics.left)
            {
                displayWidth = unscaledWidth - viewMetrics.right - viewMetrics.left;
            }
        }
        
        /**
         * Draws the background of the row using the appropriate color
         */
        override protected function drawRowBackground(s:Sprite, rowIndex:int,
                                                y:Number, height:Number, color:uint, dataIndex:int):void
        {
            if( dataIndex < (this.dataProvider as ArrayCollection).length )
            {
                var item:Object = (this.dataProvider as ArrayCollection).getItemAt(dataIndex);
                color = getRowColor(item, color, dataIndex);
            }
            
            super.drawRowBackground(s, rowIndex, y, height, color, dataIndex);
        }
    }
}