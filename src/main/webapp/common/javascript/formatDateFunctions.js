/*
 * User: sdorman
 * Date: Jun 22, 2005
 * Time: 9:37:19 AM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

/*
<!-- Original:  Richard Gorremans (RichardG@spiritwolfx.com) -->
<!-- Web Site:  http://www.spiritwolfx.com -->

<!-- This script and many more are available free online at -->
<!-- The JavaScript Source!! http://javascript.internet.com -->
*/

var strSeparator = "/";

// If you are using any Java validation on the back side you will want to use the / because
// Java date validations do not recognize the dash as a valid date separator.

//  The date format must be entered as mm/dd/yyyy

var vYearType = 4;              // Set to 2 or 4 for number of digits in the year for Netscape
//var vYearLength = 2;            // Set to 4 if you want to force the user to enter 4 digits for the year before validating.
var vYearLength = 4;            // Set to 4 if you want to force the user to enter 4 digits for the year before validating.
var err = 0;                    // Set the error code to a default of zero


function DateFormat(vDateName, vDateValue, e, dateCheck)
{
    //  vDateName = object name
    //  vDateValue = value in the field being checked
    //  e = event

    //  dateCheck  True  = Verify that the vDateValue is a valid date
    //             False = Format values being entered into vDateValue only


    //  Enter a tilde sign for the first number and you can check the variable information.
    if (vDateValue == "~")
    {
        alert("Year Type = " + vYearType + " \nSeparator = " + strSeparator);
        vDateName.value = "";
        vDateName.focus();
        return true;
    }

    var whichCode = (window.Event) ? e.which : e.keyCode;

    //  Eliminate all the ASCII codes that are not valid
    var alphaCheck = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ/-";

    if (alphaCheck.indexOf(vDateValue) >= 1)
    {
        vDateName.value = vDateName.value.substr(0, (vDateValue.length-1));

        return false;
    }

    //  Create numeric string values for 0123456789/
    //  The codes provided include both keyboard and keypad values
    var strCheck = '47,48,49,50,51,52,53,54,55,56,57,58,59,95,96,97,98,99,100,101,102,103,104,105';

    if (strCheck.indexOf(whichCode) != -1)
    {
        if (((vDateValue.length < 8 && dateCheck) || (vDateValue.length == 9 && dateCheck)) && (vDateValue.length >=1))
        {
            alert("Invalid Date\nPlease Re-Enter");
            vDateName.value = "";
            vDateName.focus();

            return true;
        }

        // Reformat date to format that can be validated. mm/dd/yyyy
        if (vDateValue.length >= 8 && dateCheck)
        {
            var mMonth = vDateName.value.substr(0,2);
            var mDay = vDateName.value.substr(3,2);
            var mYear = vDateName.value.substr(6,4)

            if (vYearLength == 4)
            {
                if (mYear.length < 4)
                {
                    alert("Invalid Date\nPlease Re-Enter");
                    vDateName.value = "";
                    vDateName.focus();
                    return true;
                }
                else if (!validYearRange(mYear))
                {
                    alert("Invalid Year\nPlease Re-Enter");
                    return true;
                }                
            }

            // Store reformatted date to new variable for validation.
            //var vDateValueCheck = mMonth + strSeparator + mDay + strSeparator + mYear;

            if (mYear.length == 2 && vYearType == 4 && dateCheck)
            {
                //  Turn a two digit year into a 4 digit year
                var mToday = new Date();

                //  If the year is greater than 30 years from now use 19, otherwise use 20
                var checkYear = mToday.getFullYear() + 30;
                var mCheckYear = '20' + mYear;

                if (mCheckYear >= checkYear)
                {
                    mYear = '19' + mYear;
                }
                else
                {
                    mYear = '20' + mYear;
                }

                //vDateValueCheck = mMonth + strSeparator + mDay + strSeparator + mYear;

                // Store the new value back to the field.
                vDateName.value = mMonth + strSeparator + mDay + strSeparator + mYear;
            }

//            if (!dateValid(vDateValueCheck))
            if (!dateValid(vDateName.value))
            {
                alert("Invalid Date\nPlease Re-Enter");
                vDateName.value = "";
                vDateName.focus();
                return true;
            }

            return true;
        }
        else
        {
            if (vDateValue.length == 2)
            {
                vDateName.value = vDateValue + strSeparator;
            }
            if (vDateValue.length == 5)
            {
                vDateName.value = vDateValue + strSeparator;
            }

            return true;
        }

        if (vDateValue.length == 10 && dateCheck)
        {
            if (!dateValid(vDateName))
            {
                //  Un-comment the next line of code for debugging the dateValid() function error messages
                //alert(err);
                alert("Invalid Date\nPlease Re-Enter");
                vDateName.focus();
                vDateName.select();
            }
        }

        return false;
    }
    else
    {
        // If the value is not in the string return the string minus the last key entered.
        vDateName.value = vDateName.value.substr(0, (vDateValue.length-1));

        return false;
    }
}


function dateValid(objName)
{
    var strDate;
    var strDateArray;
    var strDay;
    var strMonth;
    var strYear;
    var intday;
    var intMonth;
    var intYear;
    var booFound = false;
    var datefield = objName;
    var strSeparatorArray = new Array("-"," ","/",".");
    var intElementNr;
    // var err = 0;
    var strMonthArray = new Array(12);
    strMonthArray[0] = "Jan";
    strMonthArray[1] = "Feb";
    strMonthArray[2] = "Mar";
    strMonthArray[3] = "Apr";
    strMonthArray[4] = "May";
    strMonthArray[5] = "Jun";
    strMonthArray[6] = "Jul";
    strMonthArray[7] = "Aug";
    strMonthArray[8] = "Sep";
    strMonthArray[9] = "Oct";
    strMonthArray[10] = "Nov";
    strMonthArray[11] = "Dec";
    //strDate = datefield.value;
    strDate = objName;

    if (strDate.length < 1)
    {
        return true;
    }

    for (intElementNr = 0; intElementNr < strSeparatorArray.length; intElementNr++)
    {
        if (strDate.indexOf(strSeparatorArray[intElementNr]) != -1)
        {
            strDateArray = strDate.split(strSeparatorArray[intElementNr]);

            if (strDateArray.length != 3)
            {
                err = 1;
                return false;
            }
            else
            {
                strDay = strDateArray[0];
                strMonth = strDateArray[1];
                strYear = strDateArray[2];
            }

            booFound = true;
        }
    }

    if (booFound == false)
    {
        if (strDate.length > 5)
        {
            strDay   = strDate.substr(0, 2);
            strMonth = strDate.substr(2, 2);
            strYear  = strDate.substr(4);
        }
    }

    //  Adjustment for short years entered
    if (strYear.length == 2)
    {
        strYear = '20' + strYear;
    }

    strTemp = strDay;
    strDay = strMonth;
    strMonth = strTemp;
    intday = parseInt(strDay, 10);

    if (isNaN(intday))
    {
        err = 2;
        return false;
    }

    intMonth = parseInt(strMonth, 10);

    if (isNaN(intMonth))
    {
        for (i = 0; i < 12; i++)
        {
            if (strMonth.toUpperCase() == strMonthArray[i].toUpperCase())
            {
                intMonth = i + 1;
                strMonth = strMonthArray[i];
                i = 12;
            }
        }

        if (isNaN(intMonth))
        {
            err = 3;
            return false;
        }
    }

    intYear = parseInt(strYear, 10);

    if (isNaN(intYear))
    {
        err = 4;
        return false;
    }

    if (intMonth > 12 || intMonth < 1)
    {
        err = 5;
        return false;
    }

    if ((intMonth == 1 || intMonth == 3 || intMonth == 5 || intMonth == 7 || intMonth == 8 || intMonth == 10 || intMonth == 12) && (intday > 31 || intday < 1))
    {
        err = 6;
        return false;
    }

    if ((intMonth == 4 || intMonth == 6 || intMonth == 9 || intMonth == 11) && (intday > 30 || intday < 1))
    {
        err = 7;
        return false;
    }

    if (intMonth == 2)
    {
        if (intday < 1)
        {
            err = 8;
            return false;
        }
        if (LeapYear(intYear) == true)
        {
            if (intday > 29)
            {
                err = 9;
                return false;
            }
        }
        else
        {
            if (intday > 28)
            {
                err = 10;
                return false;
            }
        }
    }
    return true;
}

function LeapYear(intYear)
{
    if (intYear % 100 == 0)
    {
        if (intYear % 400 == 0) { return true; }
    }
    else
    {
        if ((intYear % 4) == 0) { return true; }
    }

    return false;
}





/**
 *  Converts a date string from mm/dd/yyyy format to yyyy/mm/dd
 */
function convertMMDDYYYYToYYYYMMDD(dateString)
{
     var separator = "/";

     var day = dateString.substr(3,2);
     var month = dateString.substr(0,2);
     var year = dateString.substr(6,4);

     var newDateString = year + separator + month + separator + day;

     if (newDateString == "//")
     {
        newDateString = null;
     }

     return newDateString;
}

/**
 * Converts a date string from mm/dd/yyyy to yyyymmdd
 * NOW WITH REGULAR EXPRESSION!
 *
 * Some people, when confronted with a problem, think
 * “I know, I'll use regular expressions.”   Now they have two problems.
 *  -Jamie Zawinski
 *
 * @param dateString String with a date in "MM/DD/YYYYY"
 */
function convertMDYtoYMDNoSlash(dateString)
{
    var regex = /(\d+)\/(\d+)\/(\d+)/gm;
    var m;
    var stack = [];
    while ((m = regex.exec(dateString)) !== null) {
        // This is necessary to avoid infinite loops with zero-width matches
        if (m.index === regex.lastIndex) {
            regex.lastIndex++;
        }
        stack.push(m[1]);
        stack.push(m[2]);
        stack.push(m[3]);
        for(var match in m)
        {
            // stack.push(match);
        }
        //ES6 notation, can't use on IE11.
        // m.forEach((match, groupIndex) => {
        //     stack.push(match);
        // });

    }
    if(stack.length!==3)
    {
       return "00000000" ;
    }
    stack = stack.reverse();

    return stack[0]+""+stack[2]+""+stack[1];

}




/**
 *  Converts a date string from yyyy/mm/dd format to mm/dd/yyyy
 */
function convertYYYYMMDDToMMDDYYYY(dateString)
{
     var separator = "/";

     var day = dateString.substr(8,2);
     var month = dateString.substr(5,2);
     var year = dateString.substr(0,4);

     var newDateString = month + separator + day + separator + year;

     if (newDateString == "//")
     {
        newDateString = null;
     }
     
     return newDateString;
}

/**
 *  Converts a date string from yyyy/mm/dd to mm/dd/yyyy format if incoming date is in mm/dd/yyyy format
 */
function convertYYYYMMDDToMMDDYYYYifNeeded(dateString)
{
     var separator = "/";

     var chkformat = dateString.substr(4,1);
     if(chkformat == "/")
     {
        alert("yyyymmddformat");
         var separator = "/";

         var day = dateString.substr(8,2);
         var month = dateString.substr(5,2);
         var year = dateString.substr(0,4);
    
         var newDateString = month + separator + day + separator + year;
    
         if (newDateString == "//")
         {
            newDateString = null;
         }
         
         return newDateString;
     }
     else
     {
        alert("mmddyyyyformat");
        return dateString;
     }

     
}

/**
 * SEG allows years to be within the range of 1800 - 9999.
 * @return true if the specified date is within the range of 1800 - 9999.
 */
function validYearRange(year)
{
    var validRange = false;
    
    if ((parseInt(year) >= 1800) && (parseInt(year) <= 9999))
    {
        validRange = true;
    }
    
    return validRange;

}