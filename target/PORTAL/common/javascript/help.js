/********************************************************************************************************************
Brings up the Help dialog.  Done as a separate javascript file to reduce maintenance.


To use these function, use a Javascript Include. Here is an example:

This will include the JS functions in your code. Notice that this is placed before your normal javascript code.

<script src="/PORTAL/common/javascript/help.js"></script>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script>
    var f = document.theForm; // Your normal JS
</script>

NOTE: this uses the openDialog and sendTransactionAction from the commonJavascriptFunctions.js which also must be
included
********************************************************************************************************************/

function getHelp()
{
    var width   = 0.40 * screen.width;
    var height  = 0.45 * screen.height;

    openDialog("help", "top=0,left=0,resizable=yes", width,  height);
    sendTransactionAction("PortalLoginTran", "showHelp", "help");
}