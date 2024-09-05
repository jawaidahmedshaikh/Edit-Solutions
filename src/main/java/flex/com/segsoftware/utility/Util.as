package com.segsoftware.utility
{
	import com.adobe.cairngorm.control.CairngormEventDispatcher;
	import com.segsoftware.event.SEGEvent;
	import com.segsoftware.model.SEGEntity;
	import com.segsoftware.model.engine.Company;
	import com.segsoftware.model.engine.ProductStructure;
	import com.segsoftware.model.logging.SEGLogMessageType;
	
	import flash.display.Sprite;
	import flash.utils.getQualifiedClassName;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.collections.ListCollectionView;
	import mx.collections.Sort;
	import mx.collections.SortField;
	import mx.collections.XMLListCollection;
	import mx.controls.*;
	import mx.formatters.CurrencyFormatter;
	import mx.formatters.DateFormatter;
	import mx.rpc.http.*;
	import mx.validators.Validator;
	
	/**
	 * Common helper methods for String manipulations, etc.
	 */ 
	public class Util
	{
		public static var DATE_FORMAT:String = "MM/DD/YYYY";	//	Properly formatted date
    	public static var DATE_TIME_FORMAT:String = "MM/DD/YYYY HH:NN:SS";	//	Properly formatted date/time
		public static var DATE_FORMAT_NOT_DELIMITED:String = "MMDDYYYY";	// User is allowed to enter dates without delimiters
		public static var DATE_DELIMITER:String = "/";
		
		/**
		 * Millis per second.
		 */  
		public static var UNIT_SECONDS:int = 1000;
		
		/**
		 * Millis per minute.
		 */ 
		public static var UNIT_MINUTES:int = 60000;
		
		/**
		 * Millis per hour.
		 */ 
		public static var UNIT_HOURS:int = 3600000;
		
		/**
		 * Millis per day.
		 */ 
		public static var UNIT_DAYS:int = 86400000;		
		
		/**
		 * Takes a 'value' String. If the value is null, or "", then it looks to the
		 * specified 'defaultValue' and returns that. Otherwise, the specified value
		 * is returned.
		 * @param value the parameter to check for its contents
		 * @param defaultValue what to to return if the 'value' is null or ""
		 * @return the specified 'value', or 'defaultValue' if 'value' is null or ""
		 */ 
		public static function initString(value:String, defaultValue:String):String
		{
			var returnValue:String = value;
			
			if ((value == null) || (value == "null") || (value.length == 0))
			{
				returnValue = defaultValue;	
			}
			
			return returnValue;
		}
		
		/**
		 * Initializes a Number with the specified value, or defaults to the
		 * specified defaultValue if the value is null or empty.
		 */ 
		public static function initNumber(value:Object, defaultValue:Object):Number
		{
			var returnValue:Number = new Number(0.00);
			
			if ((value == null) || (value.length == 0))
			{
				returnValue = new Number(defaultValue);	
			}
			else
			{
				returnValue = new Number(value);
			}
			
			return returnValue;			
		}
		
		/**
		 * Takes a 'value' String. If the value is null, or "", then it looks to the
		 * specified 'defaultValue' and returns that as an XML element with the specified elementName. 
		 * Otherwise, the specified value is returned using the specified elementName.
		 * @param value the parameter to check for its contents
		 * @param defaultValue what to to return if the 'value' is null or ""
		 * @param elementName the name of the element
		 * @return the specified 'value', or 'defaultValue' if 'value' is null or ""
		 */ 
		public static function initXML(value:String, defaultValue:String, elementName:String):XML
		{
			var returnXML:XML = null;
			
			var textValue:String = initString(value, defaultValue);
			
			if (textValue != null)
			{
				returnXML = buildXMLElement(elementName, textValue);
			}
			
			return returnXML;
		}
		
		/**
		 * Determines the number of rows in the specifed DataGrid and
		 * then selects all of them.
		 * @param dataGrid the DataGrid whose rows will be selected
		 */ 
		public static function selectAllRows(dataGrid:DataGrid):void
		{
			var numberOfRows:int = (dataGrid.dataProvider as XMLListCollection).length;
			
			var selectedIndexes:ArrayCollection = new ArrayCollection(new Array());
			
			for (var i:int = 0; i < numberOfRows; i++)
			{
				selectedIndexes.addItem(new Number(i));
			}
			
			dataGrid.selectedIndices = selectedIndexes.toArray();
		}
		
		/**
		 * Takes the XML elements of the specified Array and maps them 
		 * to an XMLListCollection.
		 * @param the Array containing XML Element to map
		 */ 
		public static function convertToXMLListCollection(...xmlElements:Array):XMLListCollection
		{
			// Yes, I know this is a very odd way to do this. However, if I simply looped through the Array and
			// added the elements to the XMLListCollection one-by-one, Flex was generated FUNKY XMLListCollection
			// events that was affecting existing, but unrelated, collections!
			var rootXML:XML = <Root/>;
			
			for each (var xmlElement:XML in xmlElements)
			{
				rootXML.appendChild(xmlElement);
			}
			
			return new XMLListCollection(rootXML.children());
		}
		
		/**
		 * Uses the specified elementName and elementValue to build the equivalent XML
		 * representation of:
		 * <elementName>elementValue</elementName>
		 * @param elementName the name of the XML element
		 * @param elementValue the textual value of the XML element
		 * @return the XML equivalent		
		 */ 
		public static function buildXMLElement(elementName:String, elementValue:String):XML
		{
			var xmlElement:XML = <{elementName}>{elementValue}</{elementName}>;
			
			return xmlElement;
		}
		
		/**
		 * Uses the specified ProductStructureVO.CompanyVO to parse and
		 * return the ubiquitous concept of a ProductKey in the format of:
		 * 
		 * companyName, marketingPackageName, groupProductName, areaName, businessContractName.
		 */ 
		public static function formatProductKey(productStructureVO:XML):String
		{			
			var companyVO:XML = productStructureVO.CompanyVO[0];
			
			var companyName:String = companyVO.CompanyName[0];
			
			var marketingPackageName:String = productStructureVO.MarketingPackageName[0];
			
			var groupProductName:String = productStructureVO.GroupProductName[0];
			
			var areaName:String = productStructureVO.AreaName[0];
			
			var businessContractName:String = productStructureVO.BusinessContractName[0];
			
			var productKey:String = companyName + ", " +
									marketingPackageName + ", " +
									groupProductName + ", " +
									areaName + ", " +
									businessContractName;		
			
			return productKey;									
		}
		
		/**
		 * Uses the specified ProductStructure.Company to parse and
		 * return the ubiquitous concept of a ProductKey in the format of:
		 * 
		 * companyName, marketingPackageName, groupProductName, areaName, businessContractName.
		 */ 
		public static function formatProductStructureKey(productStructure:ProductStructure):String
		{			
			var company:Company = productStructure.company;
			
			var companyName:String = company.companyName;
			
			var marketingPackageName:String = productStructure.marketingPackageName;
			
			var groupProductName:String = productStructure.groupProductName;
			
			var areaName:String = productStructure.areaName;
			
			var businessContractName:String = productStructure.businessContractName;
			
			var productKey:String = companyName + ", " +
									marketingPackageName + ", " +
									groupProductName + ", " +
									areaName + ", " +
									businessContractName;		
			
			return productKey;									
		}		
		
		/**
		 * Updates the specified as:  [valueObjectVO.valueObjectVOField = newField].
		 */
		public static function updateValueObjectVO(valueObjectVO:XML, valueObjectVOField:String, newValue:String):void
		{
			valueObjectVO[valueObjectVOField] = newValue;
		}
		
		/**
		 * Extracts the XML/String value of the specified parameters as valueObjectVO.valueObjectVOField.
		 * I say XML/String instead of just XML since apparently the following are both valid:
		 * 
		 * var myXML:XML = getValueObjectVOValue(...)
		 * var myString:String = getValueObjectVOValue(...) // apparently an implicit casting to String takes place.
		 * 
		 * The assumption is the the valueObjectVOField applies to a Field level Element with a simple text value.
		 */ 		
		public static function getValueObjectVOValue(valueObjectVO:XML, valueObjectVOField:String):XML
		{
			if (valueObjectVO != null)
			{
				var valueObjectVOValue:XML = valueObjectVO.child(valueObjectVOField)[0];
			
				return valueObjectVOValue;
			}
			else
			{
				return null;
			}
		}
		
		/**
		 * Pages will typically have a plethora of fields that need to be validated
		 * on the page itself. Additionally, it's often an all-or-nothing proposition
		 * where multiple validations need to succeed. 
		 * 
		 * @param validator the Array of Validators that need to have their *.validate() method called
		 * @return an array of the ValidationResultEvent(s) or an empty Array
		 */ 
		public static function validate(...validators:Array):Array
		{				
			var validationErrors:Array = Validator.validateAll(validators);
			
			return validationErrors;
		}
		
		/**
		 * Subtracts the number of specified days from the specified
		 * Date and returns a new Date object representing the 
		 * newly calculated date. 
		 * 
		 * @param baseDate the date from which to subtract the specified days
		 * @param numberOfDays the number of days to subtract from the base Date
		 * @return the new Date as a result of the subtraction calculation
		 */
		public static function subtractDays(baseDate:Date, numberOfDays:int):Date
		{
			var baseDateTimeMillis:Number = baseDate.getTime();		
			
			var newDateTimeMillis:Number = baseDateTimeMillis - (getMillisecondsInDay() * numberOfDays);
			
			var newDate:Date = new Date(newDateTimeMillis)
			
			return newDate;				
		}
		
		/**
		 * The number of milliseconds in a single day.
		 */ 
		public static function getMillisecondsInDay():Number
		{
			return 86400000;;			
		}
		
		/**
		 * Front-ends that display currency can format currency values
		 * using this return CurrencyFormatter object. The formatter
		 * assumes 2 decimal formatting.
		 */ 
		public static function getCurrencyFormatter():CurrencyFormatter
		{
			var currencyFormatter:CurrencyFormatter = new CurrencyFormatter();
			
			currencyFormatter.precision = 2;
			
			currencyFormatter.currencySymbol = "";
			
			currencyFormatter.decimalSeparatorFrom = ".";
			
			currencyFormatter.decimalSeparatorTo = ".";
			
			currencyFormatter.useNegativeSign = true;
			
			currencyFormatter.useThousandsSeparator = true;
			
			currencyFormatter.thousandsSeparatorFrom = ",";
			
			currencyFormatter.thousandsSeparatorTo = ",";
			
			return currencyFormatter;
		}
		
		/**
		 * "Unformats" a string that was formatted as currency.  It removes
		 * all of the currency formatting, such as dollar signs and commas.
		 * 
		 * @return a string containing an unformatted number
		 */
		public static function unformatCurrency(currency:String):String
		{
			var regEx:RegExp = /[,$]/g;
			
			var simpleNumberString:String = currency.replace(regEx,""); 
			
			return simpleNumberString;
		}

		/**
		 * Formats the specified date object to have the
		 * standard 'mm/dd/yyyy' format as a String.
		 */ 		
		public static function formatDate1(date:Date):String
		{
			var dateFormatter:DateFormatter = new DateFormatter();
			
			dateFormatter.formatString = Util.DATE_FORMAT;
			
			var formattedDate:String = dateFormatter.format(date);
			
			return formattedDate;
		}
		
		/**
		 * Builds a DateFormatter object that will format dates as:
		 * "EEEE, MMMM D, YYYY". For example,
		 * 
		 * Saturday, January 27, 2007
		 * 
		 */ 
		public static function formatDate2(date:Date):String
		{
			var dateFormatter:DateFormatter = new DateFormatter();
			
			dateFormatter.formatString = "EEEE, MMMM D, YYYY";
			
			return dateFormatter.format(date);
		}
		
		/**
		 * Formats the specified dateTime as (e.g.)
		 * 
		 * 01/27/1970 12:31:47 A.
		 */ 
		public static function formatDateTime1(dateTime:Date):String
		{
			var dateFormatter:DateFormatter = new DateFormatter();
			
			dateFormatter.formatString = "MM/DD/YYYY H:NN:SS A";
			
			return dateFormatter.format(dateTime);
		}
		
		/**
		 * Determines if the specified date string contains a date delimiter
		 * or not.  The date delimiter at this time is "/".
		 */
		public static function containsDateDelimiter(dateString:String):Boolean
		{
			var indexOfDelimiter:int = dateString.indexOf(Util.DATE_DELIMITER);
			
			if (indexOfDelimiter >= 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		/**
		 * Converts the specified date which has date delimiters to its equivalent Date object.
		 */ 
		public static function convertDelimitedDateStringToDate(dateString:String):Date
		{
			var date:Date = DateField.stringToDate(dateString, Util.DATE_FORMAT)									 
			
			return date;									 		
		}
	
		/**
		 * Converts the specified date which has no date delimiters to its equivalent Date object
		 */
		public static function convertNonDelimitedDateStringToDate(dateString:String):Date
		{
			var date:Date = DateField.stringToDate(dateString, Util.DATE_FORMAT_NOT_DELIMITED)									 
			
			return date;									 		
		}
		
		/**
		 * Converts the specified number (as a String) to its
		 * Number object equivalent. The specified number may have leading zeroes
		 * which is the point of this function.
		 */ 
		public static function convertToNumber(number:String):Number
		{
			number = removeLeadingZeroes(number);
			
			return new Number(number);						
		}
		
		/**
		 * Removes all leading zeroes of the specified String. For example:
		 * 
		 * 00123 would return 123.
		 * 
		 * 00010023 would return 10023.
		 */ 
		public static function removeLeadingZeroes(value:String):String
		{
			while (value.indexOf("0") == 0)
			{
				value = value.substr(1, value.length);
			}
			
			return value;			
		}
		
		/**
		 * A convenience method to pop-up a confirmation dialog relative to the specified parent and a reference
		 * to the specified callback function. 
		 * 
		 * The dialog uses the OK and Cancel buttons. The user of this method will want to compare the
		 * closeHandler's CloseEvent's detail as follows:
		 * 
		 * if (myCloseEvent.detail == Alert.OK){...}
		 * if (myCloseEvent.detail == Alert.CANCEL){...}
		 */ 
		public static function showConfirmationDialog(message:String, parent:Sprite, closeHandler:Function):void
		{
        	Alert.show(message, "Confirmation...", Alert.OK + Alert.CANCEL, parent, closeHandler);			
		} 
		
		/**
		 * A utility method to look at ResponseMessages and to see if all the messages are Success or not.
		 * Returns false if any of the response messages are not 'Success'.
		 */
		public static function isSuccess(responseMessageVOs:XMLListCollection):Boolean
		{
			var isSuccess:Boolean = true;
			
			var numberOfRows:int = responseMessageVOs.length;
			
			for (var i:int = 0; i < numberOfRows; i++)
			{
				var messageType:String = responseMessageVOs[i].MessageType[0];				
				
				if (messageType != SEGLogMessageType.MESSAGE_TYPE_SUCCESS)
				{
					isSuccess = false;
					
					break;
				}
			}
			
			return isSuccess;
		}
		
		/**
		 * A utility method to look at ResponseMessages and to see if all the messages are NOT errors
		 * (i.e. Success or Warning).
		 * Returns false if any of the response messages are 'Error'.
		 */
		public static function isNotError(responseMessageVOs:XMLListCollection):Boolean
		{
			var isNotError:Boolean = true;
			
			var numberOfRows:int = responseMessageVOs.length;
			
			for (var i:int = 0; i < numberOfRows; i++)
			{
				var messageType:String = responseMessageVOs[i].MessageType[0];				
				
				if (messageType == SEGLogMessageType.MESSAGE_TYPE_ERROR)				
				{
					isNotError = false;
					
					break;
				}
			}
			
			return isNotError;
		}
		
		/**
		 * Sorts the specified voCollection using the specified field names in 
		 * the order that they are presented. This would often be used to 
		 * sort the dataprovider of a DataGrid to render the table in the desired
		 * sorted format.
		 */ 
		public static function sort(voCollection:ICollectionView, ...fieldNames:Array):void
		{
			var sortFields:ArrayCollection = new ArrayCollection();
			
			for each(var fieldName:String in fieldNames)
			{
				var sortField:SortField = new SortField(fieldName, true);// true -> case insensitive
				
				sortFields.addItem(sortField);	
			}
	
			var sort:Sort = new Sort();
			
			sort.fields = sortFields.toArray(); 		
			
	     	voCollection.sort = sort;
	
	       	voCollection.refresh();
		}
		
		/**
		 * A convenience method to dispatch a SEGEvent on behalf of the
		 * Cairngorm Event Framework. This is equivalent to the following
		 * command:
		 * 
		 * CairngormEventDispatcher.getInstance().dispatchEvent(new SEGEvent(SEGController.EVENT_THE_EVENT_NAME, formData))
		 * 
		 */ 
		public static function dispatchEvent(eventName:String, formData:Object=null, caller:Object=null):void
		{
			CairngormEventDispatcher.getInstance().dispatchEvent(new SEGEvent(eventName, formData, caller));			
		}	
		
		/**
		 * Applies the specified filterFunction to the specified listCollection and refreshes
		 * the listCollectionView to apply the filter. The filter function must have
		 * the following signature:
		 * 
		 * f(item:Object):Boolean
		 * 
		 * The function will be given each item of the collection one element at the time.
		 * If the specified function returns true, the item will be used from the collection.
		 * If false, the item will not be used from the collection.
		 */
		public static function applyFilterFunction(filterFunction:Function, listCollectionView:ListCollectionView):void
		{
			listCollectionView.filterFunction = filterFunction;
			
			listCollectionView.refresh();							
		}	
		
		/**
		 * Removes the existing filterFunction, if any, from the specified listCollectioView and refreshes
		 * the collection to commit the change.
		 */ 
		public static function removeFilterFunction(listCollectionView:ListCollectionView):void
		{
			listCollectionView.filterFunction = null;
			
			listCollectionView.refresh();
		}
		
		/**
		 *	Returns the class name (non-qualified name) of the specified object.  
		 *  The package is removed.  The getQualifiedClassName method returns the qualified name
		 *  as com.segsoftware.command::GetAllBatchContractSetups (for example).
		 */ 
		public static function getClassName(object:Object):String
		{
			var qualifiedClassName:String = getQualifiedClassName(object);
			
			//	Get just the class name by parsing out the package name 
			var splitFields:Array = qualifiedClassName.split(":");
			
			return splitFields[splitFields.length-1];	// just want the last one in the array
		}

		/**
		 * Moves the specfied item from the specified "from" collection to the specified "to" collection. 
		 */ 
		public static function moveItemFromTo(item:Object, fromCollection:ListCollectionView, toCollection:ListCollectionView):void
		{
			var fromItemIndex:int = fromCollection.getItemIndex(item);
			
			fromCollection.removeItemAt(fromItemIndex);	
			
			toCollection.addItem(item);
		}
		
		/**
		 * Pads the string passed in with spaces to the specified length 
		 */
		public static function padStringWithSpaces(originalString:String, lengthOfNewString:int):String
		{
			var lengthOfOrigString:int = originalString.length;

			var numberOfSpaces:int = lengthOfNewString - lengthOfOrigString;

        	var space:String = " ";
        	
        	var newString:String = originalString;
        	
        	for (var i:int = 0; i < numberOfSpaces; i++)
			{
				newString = newString + space;
			}

        	return newString;
		}
		
		/**
		 * Converts the specified XML to its String equivalent. It uses the
		 * XML.toXMLString() version. It also allows one to specify pretty print
		 * or not.
		 */ 
		public static function formatXMLToString(xml:XML, prettyPrinting:Boolean):String
		{
			// Store this to avoid an unexpected global change (odd that this
			// is a static setting).
			var currentPrettyPrint:Boolean = XML.prettyPrinting;
			
			XML.prettyPrinting = prettyPrinting;	
			
			var xmlString:String = xml.toXMLString();
			
			XML.prettyPrinting = currentPrettyPrint;
			
			return xmlString;		
		}
		
		/**
		 * Often when sorting, we need to evaluate two strings s1, s2 as:
		 * 
		 * s1 < s2 -> -1
		 * s1 == s2 -> 0
		 * s1 > s2 -> 1
		 */ 
		public static function compareStrings(s1:String, s2:String):int
		{
			var returnValue:int = 0;
			
			if (s1 < s2)
			{
				returnValue = -1;
			}
			else if (s1 == s2)
			{
				returnValue = 0;
			}
			else if (s1 > s2)
			{
				returnValue = 1;
			}
			
			return returnValue;
		}
		
		/**
		 * Converts the specified millis into its unit equivalent where
		 * the unit can be SECS/MINS/HOURS/DAYS.
		 * @see #UNIT_SECONDS
		 * @see #UNIT_MINUTES
		 * @see #UNIT_HOURS
		 * @see #UNIT_DAYS
		 */ 
		public static function convertMillis(millis:Number, unit:int):Number
		{
			var conversionResults:Number = (millis/unit);
			
			return conversionResults;
		}
		
		/**
		 * Determines the client's "name".  If the LastName exists, the client's name is 
		 * the fully concatenated name (LastName, firstName, etc.).  If not, the CorporateName is used.
		 * 
		 * @return appropriate client name
		 */
		public static function determineClientName(clientDetailVO:XML):String
		{
            var lastName:String = clientDetailVO.LastName;
            
            //	If the LastName exists, concatenate the LastName, FirstName, Middle, Prefix, and Suffix
            if (lastName.length != 0)
            {
            	return Util.buildClientName(lastName, clientDetailVO.FirstName, clientDetailVO.MiddleName,
            					   	         clientDetailVO.NamePrefix, clientDetailVO.NameSuffix);
            }
            else
            {
            	//	LastName doesn't exist, use the CorporateName
            	return clientDetailVO.CorporateName;
            }
  		}
		
		/**
         * Builds the client name by concatenating the lastName, firstName, middleName, prefix, and suffix
         * - if those fields exist
         * 
         * @return concatenated individual's name
         */  
        public static function buildClientName(lastName:String, firstName:String, middleName:String, prefix:String, suffix:String):String
        {
			var name:String = lastName;
			
			if (firstName && firstName.length > 0)
			{
				name = name + ", " + firstName;
			}                	
			
			if (middleName && middleName.length > 0)
			{
				name = name + " " + middleName;
			}
			
			if (prefix && prefix.length > 0)
			{
				name = name + ", " + prefix;
			}
			
			if (suffix && suffix.length > 0)
			{
				name = name + ", " + suffix;
			}
			
			return name;
        }
        
        /**
        * Determines the PK name of the specified SEGEntity. Standard SEG naming
        * conventions are assumed. If the entity is called 'Foo', then the pk name
        * is assumed to be 'FooPK'.
        */ 
        public static function getPKName(segEntity:SEGEntity):String
        {
			var className:String = getClassName(segEntity);
			
			var pkName:String = className.charAt(0).toLowerCase() + className.substr(1, className.length - 1) + "PK";
			
			return pkName;      	
        }
        
        /**
        * Determines the pk value of the specified SEGEntity. 
        * @see #getPKName(SEGEntity)
        */ 
        public static function getPKValue(segEntity:SEGEntity):Number
        {
        	var pkName:String = getPKName(segEntity);
        	
        	var pkValue:Number = segEntity[pkName] as Number;
        	
        	return pkValue;
        }
     
     	/**
     	 * A convenience method to select the target item of a ComboBox.
     	 */    
        public static function selectComboBoxItem(comboBox:ComboBox, valueField:String, value:Object):void
        {
			for each (var dataProviderItem:Object in  comboBox.dataProvider)
			{
				if (dataProviderItem[valueField] == value)
				{
					comboBox.selectedItem = dataProviderItem;
					
					break;
				}			
			}        	
        }
        
        /**
        * A convenience method to test the specified date for null. 
        * If it is null, then the specified defaultDate will be returned,
        * otherwise the original date will be returned.
        */ 
        public static function initDate(date:String, defaultDate:Date=null):Date
        {
        	var targetDate:Date = null;
        	
			if (date)
			{
				targetDate = new Date(date);
			}
			else
			{
				targetDate = defaultDate;
			}
			
			return targetDate;
        }
  
  		
  		/**
  		 * Converts the first characters of the specified value String to upper case. 
  		 */      
        public static function firstCharToUpperCase(value:String):String
        {
			var newValue:String = value.charAt(0).toUpperCase() + value.substr(1, value.length);
			
			return newValue;        	
        }
	}
} 
