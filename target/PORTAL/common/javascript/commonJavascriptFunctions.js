/********************************************************************************************************************
What follows are common javascript functions used throughout our JSP pages. As of this writing, the following
JSPs use these functions (i.e. they serve as examples):

a. /engine/areaSummary.jsp
b. /engine/areaRelation.jsp
c. /engine/areaKeyDialog.jsp

To use these function, use a Javascript Include. Here is an example:

This will include the JS functions in your code. Notice that this is placed before your normal javascript code.

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script>
    var f = document.theForm; // Your normal JS
</script>
********************************************************************************************************************/

/**
 * Displays any response message returned from the server.
 * It is assumed that the following javascript exists (ignore comments):
 * <%-- var responseMessage = "<%= responseMessage %>"; --%>
 */
function checkForResponseMessage()
{
    if (! valueIsEmpty(responseMessage))
    {
        alert(responseMessage);
    }
}

/**
 * Sends the request to the server-side. Common targets are:
 * main     - target the main section of the frameset
 * contentIFrame - target the inner frame of the main frame
 * _top     - target the top-level frame of the frameset
 * _parent  - target the immediate parent frame of this frame
 * _blank   - target a brand-new window
 * _self    - target this window
 */
function sendTransactionAction(transaction, action, target)
{
    unformatCurrency(f);
    f.transaction.value = transaction;
    f.action.value = action;
    f.target = target;
    f.submit();
}

/**
 * Pops-open an dialog as a response to the supplied transaction/action.
 * winName - a name by which to refer to this open window
 * features - default features are "left=0,top=0,resizable=no", but you can substitute this if desired
 * width - dialog width
 * height - dialog height
 * returns - a reference to the dialog window
 */
function openDialog(winName, features, width, height)
{
    var theFeatures = "left=0,top=0,resizable=no";
    // defaults

    if (features != null)
    {
        theFeatures = features;
    }

    theFeatures += ",width=" + width + ",height=" + height;

    var dialog = window.open("", winName, theFeatures);

    dialog.focus();

    return dialog;
}

/**
 * Highlights a row triggered by a mouseover event.
 * A typical row <tr> looks like (ignore the commenting):
 * <%--<tr class="<%= className %>" id="<%= fooPK %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false);showFooDetails()">--%>
 */
function highlightRow()
{
    var tdElement = window.event.srcElement;

    var targetRow = tdElement.parentElement;

    if (targetRow.tagName != "TR") // Bubble up if necessary
    {
        targetRow = targetRow.parentElement;
    }

    targetRow.style.backgroundColor = "#FFFFCC";
}

/**
 * Unhighlights a row triggered by a mouseout event. For this to work, the row needs to be tagged with a "isSelected"
 * attribute, and have a table.summary tr style associated with it.
 * A typical row <tr> looks like (ignore the commenting):
 * <%--<tr class="<%= className %>" id="<%= fooPK %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false);showFooDetails()">--%>
 */
function unhighlightRow()
{
    var tdElement = window.event.srcElement;

    var targetRow = tdElement.parentElement;

    if (targetRow.tagName != "TR") // Bubble up if necessary
    {
        targetRow = targetRow.parentElement;
    }

    var className = targetRow.className;

    if (targetRow.isSelected == "false")
    {
        if (className == "associated")
        {
            targetRow.style.backgroundColor = "#00BB00";
        }
        else if (className == "highlighted")
        {
            targetRow.style.backgroundColor = "#FFFFCC";
        }
        else if (className == "default")
        {
            targetRow.style.backgroundColor = "#BBBBBB";
        }
        else if (className == "disabled + default")
        {
            targetRow.style.backgroundColor = "#BBBBBB";
        }
    }
}

/**
 * Tags the "just selected" row(s) with an "isSelected" attribute, and highlights the row(s).
 * allowMultiSelect - false allows only one row to be selected at a time, true allows multi row select
 */
function selectRow(allowMultiSelect)
{
    var tdElement = window.event.srcElement;

    var currentRow = tdElement.parentElement;

    if (currentRow.tagName != "TR") // Bubble up if necessary
    {
        currentRow = currentRow.parentElement;
    }

    var containingTable = currentRow.parentElement;

    for (var i = 0; i < containingTable.rows.length; i++)
    {
        var trRow = containingTable.rows(i);

        var className = trRow.className;

        var isSelected = trRow.isSelected;

        if (allowMultiSelect)
        {
            if (isSelected == "true")
            {
                trRow.style.backgroundColor = "#FFFFCC";
            }
        }
        else if (! allowMultiSelect)
        {
            if (className == "associated")
            {
                trRow.style.backgroundColor = "#00BB00";
            }
            else
            {
                trRow.style.backgroundColor = "#BBBBBB";
            }

            trRow.isSelected = "false";
        }
    }

    if (currentRow.isSelected == "true")
    {
        currentRow.style.backgroundColor = "#BBBBBB";

        currentRow.isSelected = "false";
    }
    else
    {
        currentRow.style.backgroundColor = "#FFFFCC";

        currentRow.isSelected = "true";
    }
}

/**
 * The array of all row ids that have been flagged as "selected". This is useful when the user is multi-selecting
 * the rows of a table, and it is necessary to find the set of rows/ids that have been selected. The result
 * is the set of ids comma delimited. Typically, this sting (e.g. "pk1, pk2, pk3" etc..) can be sent to the
 * server-side and then tokenized on the "," delimiter to find the set of ids/pks.
 */
function getSelectedRowIds(tableId)
{
    var selectedRows = new Array();

    var tTable = document.getElementById(tableId);

    var index = 0;

    for (var i = 0; i < tTable.rows.length; i++)
    {
        var currentRow = tTable.rows(i);

        var isSelected = currentRow.isSelected;

        if (isSelected == "true")
        {
            selectedRows[index] = currentRow.id;

            index++;
        }
    }

    return selectedRows;
}

/**
 * The row id for tables which do not have multi-select capabilities.
 * This is really a convenience method to getSelectedRowIds() when you know that there
 * is only going to be one row selected (at most).
 * Since a JSP can have many tables on it, it is necessary to use the id attribute of the table.
 * For example:
 * <table id="fooTable">
 */
function getSelectedRowId(tableId)
{
    var selectedId = getSelectedRowIds(tableId)[0];

    return selectedId;
}

/**
 * Returns true if the value is null or empty. This is useful to test if the user has even selected a row.
 * For example:
 * var selectedId = getSelectedRowId("fooTable");
 * if (valueIsEmpty(selectedId))...
 */
function valueIsEmpty(theValue)
{
    var valueIsEmpty = false;

    if (theValue == null)
    {
        valueIsEmpty = true;
    }
    else if (theValue == "null")
    {
        valueIsEmpty = true;
    }
    else if (theValue.length == 0)
    {
        valueIsEmpty = true;
    }

    return valueIsEmpty;
}

function valueIsZero(theValue)
{
    var valueIsZero = false;

    // Even if the value is empty it still satisfies the following condition.
    // No need to check for valueIsEmpty again.
    if (theValue == 0)
    {
        valueIsZero = true;
    }

    return valueIsZero;
}

/**
 * Returns true if the html text element is empty or null
 */
function textElementIsEmpty(theElement)
{
    var tagName = theElement.tagName;

    var elementIsEmpty = false;

    if ((tagName == "INPUT") && (theElement.type == "text"))
    {
        var value = theElement.value;

        elementIsEmpty = valueIsEmpty(value);
    }
    else
    {
        alert("ERROR! This Javascript Function Does Not Recognize The Element [" + tagName + "]");
    }

    return elementIsEmpty;
}

/**
 * Returns true if the html hidden element is empty or null
 */
function hiddenElementIsEmpty(theElement)
{
    var tagName = theElement.tagName;

    var elementIsEmpty = false;

    if ((tagName == "INPUT") && (theElement.type == "hidden"))
    {
        var value = theElement.value;

        elementIsEmpty = valueIsEmpty(value);
    }
    else
    {
        alert("ERROR! This Javascript Function Does Not Recognize The Element [" + tagName + "]");
    }

    return elementIsEmpty;
}

/**
 * Returns true if the html select element is still on the 0th element.
 */
function selectElementIsEmpty(theElement)
{
    var tagName = theElement.tagName;

    var elementIsEmpty = false;

    if (tagName == "SELECT")
    {
        if (theElement.selectedIndex == 0)
        {
            elementIsEmpty = true;
        }
    }
    else
    {
        alert("ERROR! This Javascript Function Does Not Recognize The Element [" + tagName + "]");
    }

    return elementIsEmpty;
}

/**
 * Returns the width of the screen in pixels.
 * This works well with the openDialog function to help specifiy a width.
 */
function getScreenWidth()
{
    var width = screen.width;

    return width;
}

/**
 * Returns the height of the screen in pixels.
 * This works well with the openDialog function to help specifiy a height.
 */
function getScreenHeight()
{
    var height = screen.height

    return height;
}

/**
 * Closes the window.
 */
function closeWindow()
{
    window.close();
}

/**
 * Returns true if the Enter key has just been pressed.
 * A user oftens expects to be able to click a button, OR hit the enter key.
 * This would be done as follows:
 * <input type="text" name="fooTextfield" onKeyPress="if (enterKeyPressed()){goDoSomething()}">
 */
function enterKeyPressed()
{
    var eventObj = window.event;

    if (eventObj.keyCode == 13)
    {

        return true;
    }
    else
    {
        return false;
    }
}

/**
 * Clears the specified text(field) of any value.
 */
function clearTextElement(textElement)
{
    textElement.value = "";
}

/**
 * Clears the specified select of any value.
 */
function clearSelectElement(selectElement)
{
    selectElement.selectedIndex = 0;
}

/**
 * Converts the month, day, year tokens into the standard date representation of yyyy/MM/dd. If any of month, day, year
 * are empty, then an empty string is returned unless validate is set to true.
 */
function formatDate(month, day, year, validate)
{
    var date = "";
    var daysInMonth = daysArray(12, year);
    var validDate = true;

    var validDate = true;

    // Do not even consider a date where the month, day, or year value is missing.
    if (valueIsEmpty(month) || valueIsEmpty(day) || valueIsEmpty(year))
    {
        validDate = false;
    }

    else if ((month.length != 2) || (day.length != 2) || (year.length != 4))
    {
        validDate = false;
    }

    else if (validate && (!isInteger(month) || !isInteger(day) || !isInteger(year)))
    {
        validDate = false;
        throw "Invalid Date - Only Numbers are allowed - Received [" + month + "/" + day + "/" + year + "]";
    }

    else if (validate && month > 12)
    {
        validDate = false;
        throw "Invalid Month - Received [" + month + "/" + day + "/" + year + "]";
    }

    else if (validate && day > daysInMonth[parseInt(month)])
    {
        validDate = false;
        throw "Invalid Day - Received [" + month + "/" + day + "/" + year + "]";
    }

    if (validate && !validDate)
    {
        throw "Invalid Date Format - Required [MM/dd/yyyy] - Received [" + month + "/" + day + "/" + year + "]";
    }

    if (validDate)
    {
        date = year + "/" + month + "/" + day;
    }

    return date;
}

/**
 * "Checks" the checkBox if checkBoxValue = 'Y', "unchecks" if the checkBoxValue is 'N'.
 */
function setCheckBoxState(checkBox, checkBoxValue)
{
    if (checkBoxValue == "Y")
    {
        checkBox.checked = true;
    }
    else if (checkBoxValue == "N")
    {
        checkBox.checked = false;
    }
}

/**
 * Sets the value of the hidden form field to 'Y' (checked), or 'N', (not checked).
 */
function setCheckBoxValue(checkBox, hiddenFormField)
{
    if (checkBox.checked)
    {
        hiddenFormField.value = "Y";
    }
    else
    {
        hiddenFormField.value = "N";
    }
}

/**
 * Builds an array of string where each string is a colon-separated representation of the name:value pairs of the
 * form element. For example, if a textfield had a name of "fooName" and a value of "fooValue", the array entry
 * would be "fooName:fooValue".
 */
function captureFormValues(theForm)
{
    var formObj = new Object();

    var formElements = theForm.elements;

    for (var i = 0; i < formElements.length; i++)
    {
        var elementName = formElements[i].name;

        if (!valueIsEmpty(elementName))
        {
            var elementValue = formElements[i].value;

            var arrayEntry = elementValue;

            formObj[elementName] = arrayEntry;
        }
    }

    return formObj;
}

/**
 * Compares the values of two forms (before and after). Returns true if a change is detected in
 * any of the form values.
 * @see captureFormValues
 */
function formValuesChanged(beforeFormValues, theForm)
{
    var valueChanged = false;

    var formElements = theForm.elements;

    for (var i = 0; i < formElements.length; i++)
    {
        var elementName = formElements[i].name;

        if (!valueIsEmpty(elementName))
        {
            var currentValue = formElements[i].value;

            var beforeValue = beforeFormValues[elementName];

            if (beforeValue != currentValue)
            {
                valueChanged = true;

                break;
            }
        }
    }

    return valueChanged;
}

/**
 * Checks to see if changes have occured in the form.
 */
function checkForFormChanges()
{
    var shouldSaveChanges = false;

    if (formValuesChanged(beforeFormValues, f))
    {
        shouldSaveChanges = confirm("Form Values Changed - You Should Save Your Changes");
    }

    return shouldSaveChanges;
}

/**
 * Strips all commas out of the value of any textField flagged with the CURRENCY attribute.
 */
function unformatCurrency()
{
    var theForm = document.forms[0];

    var formElements = theForm.elements;

    for (var i = 0; i < formElements.length; i++)
    {
        var formElement = formElements[i];

        if (formElement.tagName == "INPUT")
        {
            if (formElement.CURRENCY != null)
            {
                var amount = formElement.value;

                amount += '';

                var rgx = /[,]/g;

                amount = amount.replace(rgx, '');

                formElement.value = amount;
            }
        }
    }
}

/**
 * Formats every textFieldflagged with a CURRENCY attribute in U.S. currency format.
 * e.g. <input type="text" name="amount" CURRENCY>
 */
function formatCurrency()
{
    var theForm = document.forms[0];
    
    var formElements = theForm.elements;

    for (var i = 0; i < formElements.length; i++)
    {
        var formElement = formElements[i];

        if (formElement.tagName == "INPUT")
        {
            if (formElement.CURRENCY != null)
            {
                formatTextFieldAsCurrency(formElement)

                formElement.attachEvent("onblur", formatTextFieldAsCurrency);
            }
        }
    }
}

/**
 * Formats every textField flagged with a CURRENCY attribute. e.g. <input type="text" name="amount" CURRENCY>
 * In order for this function to work, the "formatCurrency()" function has to have been called first.
 */
function formatTextFieldAsCurrency()
{
    var textField = arguments[0];

    var theEvent = window.event;

    if (theEvent.srcElement != null)
    {
        textField = theEvent.srcElement;
    }

    var amount = formatAsCurrency(textField.value);

    textField.value = amount;
}

/**
 * Returns the specified amount as a formatted (U.S. - style) currency.
 */
function formatAsCurrency(amount)
{
    var inDecimal = '.';

    var outDecimal = '.';

    var separator = ',';

    amount += '';

    var decimalPosition = amount.indexOf(inDecimal);

    var amountEnd = '';

    if (decimalPosition != -1)
    {
        amountEnd = outDecimal + amount.substring(decimalPosition + 1, amount.length);

        if (amountEnd.length == 2)
        {
            amountEnd += "0";
        }

        amount = amount.substring(0, decimalPosition);
    }
    else
    {
        amountEnd += outDecimal + "00";
    }

    var rgx = /(\d+)(\d{3})/;

    while (rgx.test(amount))
    {
        amount = amount.replace(rgx, '$1' + separator + '$2');
    }

    amount = amount + amountEnd;

    return amount;
}

/*
 * Selects/Unselects all rows in a table.
 * tableId :- elementId of the table
 * select :- true = select; false = unselect
 */

function selectUnselectAllRowsInTable(tableId, select)
{
    var tTable = document.getElementById(tableId);

    for (var i = 0; i < tTable.rows.length; i++)
    {
        var currentRow = tTable.rows(i);

        if (currentRow.isSelected)
        {
            if (select == "true")
            {
                currentRow.style.backgroundColor = "#FFFFCC";

                currentRow.isSelected = "true";
            }
            else
            {
                currentRow.style.backgroundColor = "#BBBBBB";

                currentRow.isSelected = "false";
            }
        }


    }
}

/*
 * Resets the form.
 * Form elements which have the NORESET attribute are not reset.
 * e.g. <input type="text" NORESET>
 */
function resetForm()
{
    var formElements = f.elements;

    for (var i = 0; i < formElements.length; i++)
    {
        var formElement = formElements[i];

        if (formElement.value)
        {
            if ((formElement.type != "button") && (formElement.NORESET == null))
            {
                formElement.value = "";
            }
        }
    }
}

/*
 * Changes the case of the first letter to either upper or lower
   U = to upper
   L = to lower
 */
function changeFirstCharacterCase(value, mode)
{
    var modifiedValue = value;

    var firstLetter = value.substring(0, 1);

    var remainingString = value.substring(1, value.length);

    if (mode == "U")
    {
        firstLetter = firstLetter.toUpperCase();
    }
    else if (mode == "L")
    {
        firstLetter = firstLetter.toLowerCase();
    }

    modifiedValue = firstLetter + remainingString;

    return modifiedValue;
}

/*
 * Validates the form for fields defined the following way
 * e.g. <input type="text" name="foo" REQUIRED1>
 * e.g. <select name="foo" REQUIRED2>
 * e.g. <textarea name="foo" REQUIRED3>
 * returns true if the form has all REQUIRED values.
 * The REQUIRED? is arbitrary in name. It is possible to validate
 * some fields on a form but not others by assigning arbitrary flags.
 */
function validateForm(theForm, flag)
{
    var invalidColor = "#FFFFCC";

    var validColor = 'white';

    var alertText = "The following Fields are required " + "\n\n";

    var formElements = theForm.elements;

    var invalidElementExists = false;

    for (var i = 0; i < formElements.length; i++)
    {
        var isValid = true;

        var formElement = formElements[i];
        
        var formElementName = formatFormElementName(formElement.name);

        if (formElement.getAttribute(flag) != null)
        {
            var elementType = formElement.type;
            
            if (elementType == "text")
            {
                if (textElementIsEmpty(formElement))
                {
                    alertText += changeFirstCharacterCase(formElementName, "U") + "\n";
                    
                    isValid = false;
                }
            }
            else if (elementType == "hidden")
            {
                if (hiddenElementIsEmpty(formElement))
                {
                    alertText += changeFirstCharacterCase(formElementName, "U") + "\n";

                    isValid = false;
                }
            }            
            else if (elementType == "select-one")
            {
                if (selectElementIsEmpty(formElement))
                {
                    alertText += changeFirstCharacterCase(formElementName, "U") + "\n";

                    isValid = false;
                }
            }
            else if (elementType == "textarea")
            {
                if (valueIsEmpty(formElement.value))
                {
                    alertText += changeFirstCharacterCase(formElementName, "U") + "\n";

                    isValid = false;
                }
            }

            if (!isValid)
            {
                formElement.style.backgroundColor = invalidColor;

                invalidElementExists = true;
            }
            else
            {
                formElement.style.backgroundColor = validColor;
            }
        }
    }

    if (invalidElementExists)
    {
        alert(alertText);
    }

    return !invalidElementExists;
}

/*
 * Finds if any rows exist in the table with 'tableId'.
 */
function verifyIfRowsExist(tableId)
{
    var isExists = true;

    var tTable = document.getElementById(tableId);

    // this is a specific condition, always one row exists for filler.
    if (tTable.rows.length <= 1)
    {
        isExists = false;
    }

    return isExists;
}

function daysInFebruary(year)
{
    // February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}

function daysArray(n, year)
{
    for (var i = 1; i <= n; i++)
    {
        this[i] = 31;
        if (i == 4 || i == 6 || i == 9 || i == 11)
        {
            this[i] = 30;
        }
        if (i == 2)
        {
            this[i] = daysInFebruary(year);
        }
    }
    return this;
}

function isInteger(s)
{
    for (var i = 0; i < s.length; i++)
    {
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}

/**
 * When registered with the onKeyPress() of a TextArea, pops-up an alert, and prevents any further typing.
 * You must a "maxLength" attribute to the TextArea element.
 * e.g.
 * <textarea onKeyUp='checkTextAreaLimit()' maxLength='200'>
 */
function checkTextAreaLimit()
{
    var taObj = event.srcElement;

    var taValue = taObj.value;

    if (taValue.length >= taObj.maxLength * 1)
    {
        var maxLength = taObj.maxLength;

        alert("Max # Characters Allowed: [" + maxLength + "]");

        taValue = taValue.substring(0, maxLength - 1);

        taObj.value = taValue;

        return false;
    }
}

/**
 * The visible width of your document.
 */
function getWindowWidth()
{
    var myWidth = 0;

    if (typeof( window.innerWidth ) == 'number')
    {
        //Non-IE
        myWidth = window.innerWidth;
    }
    else if (document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ))
    {
        //IE 6+ in 'standards compliant mode'
        myWidth = document.documentElement.clientWidth;
    }
    else if (document.body && ( document.body.clientWidth || document.body.clientHeight ))
    {
        //IE 4 compatible
        myWidth = document.body.clientWidth;
    }

    return myWidth;
}

/**
 * The visible height of your document;
 */
function getWindowHeight()
{
    var myHeight = 0;

    if (typeof( window.innerWidth ) == 'number')
    {
        //Non-IE
        myHeight = window.innerHeight;
    }
    else if (document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ))
    {
        //IE 6+ in 'standards compliant mode'
        myHeight = document.documentElement.clientHeight;
    }
    else if (document.body && ( document.body.clientWidth || document.body.clientHeight ))
    {
        //IE 4 compatible
        myHeight = document.body.clientHeight;
    }

    return myHeight;
}


/**
 * Form elements may be rendered in a table ID'd by row:column values.
 * For example, the element in a rowId of "112233" for tableId "MyTable" in 
 * the column "MyColumn" would be accessed with the id of:
 * "MyTable:112233:MyColumn".
 */
function getTableElement(tableId, rowId, columnName)
{
  var id = tableId + ":" + rowId + ":" + columnName;
  
  var element = document.getElementById(id);
  
  return element;
}

/**
 * Builds a new DOM Document from the specified xml.
 * It is assumed that the xml is well-formed with
 * a single root element.
 * The return DOM is prepared to accept XPath expressions.
 */
function buildDOMDocument(xmlString)
{
  var domDocument = (new DOMParser()).parseFromString(xmlString, "text/xml");
  
  domDocument.setProperty("SelectionLanguage", "XPath");
  
  return domDocument;
}

/**
 * The result of the specified xpath run against the specified
 * domDocument as a (Sarissa)NodeList.
 * e.g.
 * var domDocument = buildDOMDocument("<root><foo>fooValue1</foo><foo>fooValue2</foo></root>");
 * var nodes = selectDOMNodes(domDocument, "/root/foo");
 * This results in:
 * nodes[0].text = "fooValue1"
 * nodes[1].text = "fooValue2"
 */
function selectDOMNodes(domDocument, xpath)
{
  var nodeList = domDocument.selectNodes(xpath);
  
  return nodeList;
}

/**
* This is goofy, but the BA's don't want to see 'UIFooName'
* in the Alert when doing the validateForm() function.
* 'UIFooName' is a naming convention we use in the rare
* case that we are using Apache Taglibs and Beans.
* This strips UI from the name if it exists.
*
* returns the formElementName without the 'UI' or the original string if 'UI' is not present
*/
function formatFormElementName(formElementName)
{
    var newFormElementName = formElementName;

    if (typeof formElementName !== "undefined") {
	    if (formElementName.toUpperCase().charAt(0) == 'U' && formElementName.toUpperCase().charAt(1) == 'I')
	    {
	        newFormElementName = formElementName.substr(2);
	    }
    }

    return newFormElementName;
}
