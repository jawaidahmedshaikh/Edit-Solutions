<html>  
<script language="JavaScript">

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

	var f = null;
	var currentPage = "";

 	function init()  {
		
		f = document.tabContentForm;
		document.all.contentIFrame.style.pixelWidth = .95 * document.all.contentIFrame.document.body.clientWidth;
		document.all.contentIFrame.style.pixelHeight = document.body.clientHeight;		
		
		currentPage = "accountingSummary";
 	}
	
	function requiredFieldsMissing() {
			
		return false;
	}
	
	function showAccountingSummary() {
	
		currentPage = "accountingMain";		
			
		window.frames["contentIFrame"].sendTransactionAction("AccountingDetailTran", "showAccountingSummary", "contentIFrame");
	}	
		
	function showAccountingDetail() {
	
		currentPage = "accountingDetail";	
	
		window.frames["contentIFrame"].sendTransactionAction("AccountingDetailTran", "showAccountingDetail", "contentIFrame");
	}
		
	function showAccountingRelation() {
			
		currentPage = "accountingRelation";				
	
		window.frames["contentIFrame"].sendTransactionAction("AccountingDetailTran", "showAccountingRelation", "contentIFrame");
	}
	
	function showAccountingCompanyStructure() {
	
		currentPage = "accountingCompanyStructure";		
	
		window.frames["contentIFrame"].sendTransactionAction("AccountingDetailTran", "showAccountingCompanyStructure", "contentIFrame");
	}
			
	function sendTransactionAction(transaction, action, target) {
		
		f.transaction.value = transaction;
		f.action.value = action;
		f.target = target;
		
		f.submit();	
	}
	
function setActiveTab(tabName) {

	if (tabName == "mainTab")  {
		
		MM_swapImage('summary','','../images/summaryTag.gif','detail','','../images/detailTab.gif','relation','','../images/relationTab.gif','structure','','../images/structureTab.gif',1)
	}
	else if (tabName == "detailTab") {
	
		MM_swapImage('summary','','../images/summaryTab.gif','detail','','../images/detailTag.gif','relation','','../images/relationTab.gif','structure','','../images/structureTab.gif',1)
	}
	else if (tabName == "relationTab") {
		
		MM_swapImage('summary','','../images/summaryTab.gif','detail','','../images/detailTab.gif','relation','','../images/relationTag.gif','structure','','../images/structureTab.gif',1)
	}
	else if (tabName == "structureTab") {
	
		MM_swapImage('summary','','../images/summaryTab.gif','detail','','../images/detailTab.gif','relation','','../images/relationTab.gif','structure','','../images/structureTag.gif',1)
	}	
}	

</script>

<body onLoad="init()" bgColor="#99BBBB">
<form name="tabContentForm" method="post" action="/PORTAL/servlet/RequestManager">
<input type="hidden" name="page" value="">
  <table style="position:relative; width:100%; top:0; left:0" cellspacing="0" cellpadding="0" border="0" height="3%" width="100%">
    <tr valign="bottom">
		
      <td height="22"><img src="../images/summaryTab.gif"  style="position:relative; top:5; left:0" width="60" height="24" name="summary" onClick= "showAccountingSummary()">
        <img src="../images/detailTab.gif"  style="position:relative; top:5; left:0" width="58" height="23" name="detail" onClick= "showAccountingDetail()">
        <img src="../images/relationTab.gif"  style="position:relative; top:5; left:0" width="60" height="22" name="relation" onClick= "showAccountingRelation()">
        <img src="../images/structureTab.gif"  style="position:relative; top:5; left:0" width="60" height="23" name="structure" onClick=" showAccountingCompanyStructure()">
      </td>	
	</tr>
	<tr> 
      <td bgcolor="#30548E">&nbsp; </td>
	</tr>
	<tr>
		<td>
		  <iframe style="overflow:visible"
		  	name="contentIFrame"
			id="contentIFrame" 
			scrolling="yes"
			frameborder="0"
			src="/PORTAL/servlet/RequestManager?transaction=AccountingDetailTran&action=showAccountingMainDefault"
			>
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