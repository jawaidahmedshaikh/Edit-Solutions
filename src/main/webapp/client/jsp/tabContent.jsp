<%
    String clientDetailPK = (String) session.getAttribute("searchValue");
    session.removeAttribute("searchValue");

    String iFrameSrc = null;

    if (clientDetailPK == null){

        iFrameSrc = "/PORTAL/servlet/RequestManager?transaction=ClientDetailTran&action=showClientDetails";
    }
    else {

        iFrameSrc = "/PORTAL/servlet/RequestManager?transaction=ClientDetailTran&action=showSelectedClient&clientDetailPK=" + clientDetailPK;
    }

%>


<html>
<script language="JavaScript">
<!--


function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v3.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function setActiveTab(tabName) {

	if (tabName == "mainTab")  {

		MM_swapImage('clientMain','','/PORTAL/client/images/clientTag.gif','address','','/PORTAL/client/images/addressTab.gif','history','','/PORTAL/client/images/historyTab.gif',1);
	}
	else if (tabName == "addressTab") {

		MM_swapImage('clientMain','','/PORTAL/client/images/clientTab.gif','address','','/PORTAL/client/images/addressTag.gif','history','','/PORTAL/client/images/historyTab.gif',1);
	}
    else if (tabName == "historyTab") {

        MM_swapImage('clientMain','','/PORTAL/client/images/clientTab.gif','address','','/PORTAL/client/images/addressTab.gif','history','','/PORTAL/client/images/historyTag.gif',1);
    }
}

//-->
</script>

<script language="Javascript1.2">

	var f = null;
	var currentPage = "";

 	function init()  {

		f = document.tabContentForm;
		document.all.contentIFrame.style.pixelWidth = .95 * document.all.contentIFrame.document.body.clientWidth;
		document.all.contentIFrame.style.pixelHeight = document.body.clientHeight;

		currentPage = "clientMain";
 	}

	function showClientDetails() {

		currentPage = "clientMain";

		window.frames["contentIFrame"].sendTransactionAction("ClientDetailTran", "showClientDetails", "contentIFrame");
	}

	function showClientAddress() {

		currentPage = "address";

		window.frames["contentIFrame"].sendTransactionAction("ClientDetailTran", "showClientAddress", "contentIFrame");
	}

    function showClientHistory() {

        currentPage = "history";

        window.frames["contentIFrame"].sendTransactionAction("ClientDetailTran", "showClientHistory", "contentIFrame");
    }

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;
		f.target = target;

		f.submit();
	}

</script>

<body onLoad="init()"  bgColor="#99BBBB">
<form name="tabContentForm" method="post" action="/PORTAL/servlet/RequestManager">
<input type="hidden" name="page" value="">
<table style="position:relative; width:100%; top:0; left:0" cellspacing="0" cellpadding="0" border="0">

	<tr valign="bottom">
      <td>
        <img src="/PORTAL/client/images/clientTab.gif" style="position:relative; top:5; left:0" onClick="showClientDetails()" name="clientMain" width="60" height="25">
        <img src="/PORTAL/client/images/addressTab.gif" style="position:relative; top:5; left:0" onClick="showClientAddress()" name="address" width="60" height="25">
        <img src="/PORTAL/client/images/historyTab.gif" style="position:relative; top:5; left:0" onClick="showClientHistory()" name="history" width="60" height="25">
	</tr>

	<tr bgcolor="#30548E">
	  <td>
        &nbsp;
	  </td>
	</tr>
	<tr>
	  <td>
		<iframe style="overflow:visible"
		  	name="contentIFrame"
			id="contentIFrame"
			scrolling="yes"
			frameborder="0"
			src="<%= iFrameSrc %>">
	    </iframe>
	  </td>
	</tr>
</table>

<!-- ****** HIDDEN FIELDS ***** //-->
  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">

</form>
</body>
</html>