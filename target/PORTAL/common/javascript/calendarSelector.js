// Title: Timestamp picker
// Description: See the demo at url
// URL: http://us.geocities.com/tspicker/
// Script featured on: http://javascriptkit.com/script/script2/timestamp.shtml
// Version: 1.0
// Date: 12-05-2001 (mm-dd-yyyy)
// Author: Denis Gritcyuk <denis@softcomplex.com>; <tspicker@yahoo.com>
// Notes: Permission given to use this script in any kind of applications if
//    header lines are left unchanged. Feel free to contact the author
//    for feature requests and/or donations

function show_calendar(str_target, str_datetime) {
	var arr_months = ["January", "February", "March", "April", "May", "June",
		"July", "August", "September", "October", "November", "December"];
	var week_days = ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"];
	var n_weekstart = 1; // day week starts from (normally 0 or 1)

	var dt_datetime = (str_datetime == null || str_datetime =="" ?  new Date() : str2dt(str_datetime));

	var dt_prev_month = new Date(dt_datetime);
	dt_prev_month.setMonth(dt_datetime.getMonth()-1);

	var dt_next_month = new Date(dt_datetime);
	dt_next_month.setMonth(dt_datetime.getMonth()+1);

	var dt_prev_year = new Date(dt_datetime);
	dt_prev_year.setYear(dt_datetime.getYear()-1);

	var dt_next_year = new Date(dt_datetime);
	dt_next_year.setYear(dt_datetime.getYear()+1);

	var dt_firstday = new Date(dt_datetime);
	dt_firstday.setDate(1);
	dt_firstday.setDate(1-(7+dt_firstday.getDay()-n_weekstart)%7);

	var dt_lastday = new Date(dt_next_month);
	dt_lastday.setDate(0);
	
	// html generation (feel free to tune it for your particular application)
	// print calendar header
	var str_buffer = new String
	(
		"<html>\n"+
		"<head>\n"+
		"  <title>Calendar</title>\n"+
		"</head>\n"+
		"<body bgcolor=\"#99BBBB\" style=\"margin: 5;\">\n"+
		"  <table class=\"clsOTable\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n"+
		"    <tr>\n      <td bgcolor=\"#4682B4\">\n"+
		"        <table cellspacing=\"1\" cellpadding=\"3\" border=\"0\" width=\"100%\">\n"+
		"          <tr>\n            <td colspan=\"7\">\n"+
		"              <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\">\n"+
		"                <tr>\n                  <td>\n                    <a href=\"javascript:window.opener.show_calendar('"+
		str_target+"', '"+ dt2dtstr(dt_prev_year)+"');\">"+
		"<img src=\"/PORTAL/common/images/calendarPreviousYear.gif\" width=\"16\" height=\"16\" border=\"0\""+
		" alt=\"previous year\"></a>&nbsp;\n"+
		"                    <a href=\"javascript:window.opener.show_calendar('"+
		str_target+"', '"+ dt2dtstr(dt_prev_month)+"');\">"+
		"<img src=\"/PORTAL/common/images/calendarPrevious.gif\" width=\"16\" height=\"16\" border=\"0\""+
		" alt=\"previous month\"></a>\n                  </td>\n"+
		"                  <td align=\"center\" width=\"100%\">"+
		"<font color=\"#FFFFFF\" face=\"tahoma, verdana\" size=\"2\">"
		+arr_months[dt_datetime.getMonth()]+" "+dt_datetime.getFullYear()+"</font></td>\n"+
		"                  <td>\n                    <a href=\"javascript:window.opener.show_calendar('"
		+str_target+"', '"+dt2dtstr(dt_next_month)+"');\">"+
		"<img src=\"/PORTAL/common/images/calendarNext.gif\" width=\"16\" height=\"16\" border=\"0\""+
		" alt=\"next month\"></a>&nbsp;\n"+
		"                    <a href=\"javascript:window.opener.show_calendar('"
		+str_target+"', '"+dt2dtstr(dt_next_year)+"');\">"+
		"<img src=\"/PORTAL/common/images/calendarNextYear.gif\" width=\"16\" height=\"16\" border=\"0\""+
		" alt=\"next year\"></a>\n                  </td>\n                </tr>\n              </table>\n"+
		"            </td>\n          </tr>\n"
	);

	var dt_current_day = new Date(dt_firstday);
	// print weekdays titles
	str_buffer += "          <tr>\n";
	for (var n=0; n<7; n++)
		str_buffer += "            <td bgcolor=\"#87CEFA\" align=\"center\">"+
		"<font color=\"#FFFFFF\" face=\"tahoma, verdana\" size=\"2\">"+
		week_days[(n_weekstart+n)%7]+"</font></td>\n";
	// print calendar table
	str_buffer += "          </tr>\n";
	while (dt_current_day.getMonth() == dt_datetime.getMonth() ||
		dt_current_day.getMonth() == dt_firstday.getMonth()) {
		// print row header
		str_buffer += "          <tr>\n";
		for (var n_current_wday=0; n_current_wday<7; n_current_wday++) {
				if (dt_current_day.getDate() == dt_datetime.getDate() &&
					dt_current_day.getMonth() == dt_datetime.getMonth())
					// print current date
					str_buffer += "            <td bgcolor=\"#FFB6C1\" align=\"center\" width=\"14%\">";
				else if (dt_current_day.getDay() == 0 || dt_current_day.getDay() == 6)
					// weekend days
					str_buffer += "            <td bgcolor=\"#DBEAF5\" align=\"center\" width=\"14%\">";
				else
					// print working days of current month
					str_buffer += "            <td bgcolor=\"#FFFFFF\" align=\"center\" width=\"14%\">";

				if (dt_current_day.getMonth() == dt_datetime.getMonth())
					// print days of current month
					str_buffer += "<a href=\"javascript:window.opener."+str_target+
					".value='"+dt2dtstr(dt_current_day)+"'; window.close();\">"+
					"<font color=\"black\" face=\"tahoma, verdana\" size=\"2\">";
				else 
					// print days of other months
					str_buffer += "<a href=\"javascript:window.opener."+str_target+
					".value='"+dt2dtstr(dt_current_day)+"'; window.close();\">"+
					"<font color=\"gray\" face=\"tahoma, verdana\" size=\"2\">";
				str_buffer += dt_current_day.getDate()+"</font></a></td>\n";
				dt_current_day.setDate(dt_current_day.getDate()+1);
		}
		// print row footer
		str_buffer += "          </tr>\n";
	}
	// print calendar footer
	str_buffer +=
		//"          <form name=\"cal\">\n          <tr>\n            <td colspan=\"7\" bgcolor=\"#87CEFA\">"+
		//"<font color=\"#FFFFFF\" face=\"tahoma, verdana\" size=\"2\">"+
		//"</font></td>\n          </tr>\n          </form>\n" +
		"        </table>\n" +
		"      </td>\n    </tr>\n  </table>\n" +
		"</body>\n" +
		"</html>\n";




	var vWinCal = window.open("", "Calendar", 
		"width=215,height=210,status=no,resizable=yes,top=200,left=200,titlebar=no,toolbar=no,menubar=no,dependent=yes,alwaysRaised=yes");
	vWinCal.opener = self;
	var calc_doc = vWinCal.document;
	calc_doc.open();
	calc_doc.write (str_buffer);
	calc_doc.close();
}

// datetime parsing and formatting routines. modify them if you wish other datetime format
function str2dt (str_datetime)
{
//                   month   day   year
	var re_date = /^(\d+)\/(\d+)\/(\d+)$/;

	if (!re_date.exec(str_datetime))
	{
	//	return alert("Invalid Datetime format: "+ str_datetime);
	}

	//                 year      month        day
	return (new Date (RegExp.$3, RegExp.$1-1, RegExp.$2));
}

function dt2dtstr (dt_datetime)
{
    var month = dt_datetime.getMonth() + 1;
    var day = dt_datetime.getDate();
    var year = dt_datetime.getFullYear();

    var strMonth = new String(month);
    var strDay   = new String(day);

    if (month < 10)
    {
        strMonth = new String("0" + strMonth);
    }

    if (day < 10)
    {
        strDay = new String("0" + strDay);
    }

    return (new String (strMonth + "/" + strDay + "/" +  year));
}