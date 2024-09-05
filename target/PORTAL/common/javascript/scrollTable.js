/*
 Initializes and handles resizing of scrollable tables.  This javascript was obtained at
 http://webfx.eae.net/dhtml/syncscroll/scrolltable.html
 */
function initScrollTable(oElement)
{
	resizeScrollTable(oElement);
	oElement.firstChild.syncTo = oElement.lastChild.uniqueID;
	oElement.firstChild.syncDirection = "horizontal";
	oElement.attachEvent("onresize", function () {
		resizeScrollTable(oElement);
	});
}

function resizeScrollTable(oElement)
{
	var head = oElement.firstChild;

	var headTable = head.firstChild;

	var body = oElement.lastChild;

	var bodyTable = body.firstChild;

	body.style.height = Math.max(0, oElement.clientHeight - head.offsetHeight);

	var scrollBarWidth = body.offsetWidth - body.clientWidth;

	// set width of the table in the head
	headTable.style.width = Math.max(0, Math.max(bodyTable.offsetWidth + scrollBarWidth, oElement.clientWidth));

	// go through each cell in the head and resize
	var headCells = headTable.rows[0].cells;

	var bodyTableRows = bodyTable.rows[0];

	//  Some tables don't have any rows, check first
    if (bodyTableRows != null)
    {
        var bodyCells = bodyTable.rows[0].cells;

	    for (var i = 0; i < bodyCells.length; i++)
	    {
		    headCells[i].style.width = bodyCells[i].offsetWidth;
		}
    }
}

/*
window.attachEvent("onload", function () {
	var testElement = document.getElementById("testScrollTable")
	initScrollTable(testElement);
});
*/



