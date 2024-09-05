<!--
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 --> 
<%
//    String agentPK = (String) session.getAttribute("searchValue");

    String iFrameSrc = null;

//    if (agentPK == null){

        iFrameSrc = "/PORTAL/servlet/RequestManager?transaction=ReinsuranceTran&action=showReinsurerDetail";
//    }
//    else {

//        iFrameSrc = "/PORTAL/servlet/RequestManager?transaction=AgentDetailTran&action=showAgentDetailMainContent&agentPK=" + agentPK;
//    }
%>
<html>
<head>

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<script language="Javascript1.2">
	var f = null;

 	function init()
    {
        var width = screen.width;
        var height = screen.height;

		f = document.tabContentForm;

		document.all.contentIFrame.width = 0.97 * width;
		document.all.contentIFrame.height = height;
 	}

     /**
      * Shows reinsurer detail page.
      */
     function showReinsurerDetail()
     {
        setActiveImage("reinsurer");

		window.frames["contentIFrame"].sendTransactionAction("ReinsuranceTran", "showReinsurerDetail", "contentIFrame");
     }

     /**
      * Shows Treaty page.
      */
     function showReinsurerTreaty()
     {
        setActiveImage("treaty");

		window.frames["contentIFrame"].sendTransactionAction("ReinsuranceTran", "showReinsurerTreaty", "contentIFrame");
     }

     /**
      * Shows reinsurer treaty detail.
      */
     function showReinsurerHistory()
     {
        setActiveImage("history");

		window.frames["contentIFrame"].sendTransactionAction("ReinsuranceTran", "showReinsurerHistory", "contentIFrame");
     }

     /**
      * Shows company structure to treaty relations.
      */
     function showTreatyRelations()
     {
        setActiveImage("relations");

		window.frames["contentIFrame"].sendTransactionAction("ReinsuranceTran", "showTreatyRelations", "contentIFrame");
     }

     /**
      * Shows the Treaty to Case and Segment relations.
      */
     function showContractTreatyRelations()
     {
        setActiveImage("contractTreaty");

		window.frames["contentIFrame"].sendTransactionAction("ReinsuranceTran", "showContractTreatyRelations", "contentIFrame");
     }

     /**
      * Visually sets the "on" tab, while displaying all the other images of the image collection as "off".
      */
     function setActiveImage(imageId)
     {
        var imageBase = "/PORTAL/reinsurance/images/";

        var imageSrc = null;

        var images = document.all.imageCollection.childNodes;

        for (var i = 0; i < images.length; i++)
        {
            if(images[i].tagName == "IMG")
            {
                if (images[i].id == imageId)
                {
                    imageSrc = imageBase + images[i].id + "_on.gif";
                }
                else
                {
                    imageSrc = imageBase + images[i].id + "_off.gif";
                }

                images[i].src = imageSrc;
            }
        }
     }
</script>
</head>

<body class="mainTheme" onLoad="init()">

<table style="position:relative; top:0; left:0" cellspacing="0" cellpadding="0" border="0">
  <tr valign="bottom">
    <td>
        <span id="imageCollection">
            <img id="reinsurer" src="/PORTAL/reinsurance/images/reinsurer_on.gif"  style="position:relative; top:4; left:0" onClick="showReinsurerDetail()" name="" width="60" height="26">
            <img id="treaty" src="/PORTAL/reinsurance/images/treaty_off.gif" style="position:relative; top:4; left:0" onClick="showReinsurerTreaty()" name="" width="60" height="26">
            <img id="history" src="/PORTAL/reinsurance/images/history_off.gif" style="position:relative; top:4; left:0" onClick="showReinsurerHistory()" name="" width="60" height="26">
        </span>
    </td>
  </tr>
  <tr bgcolor="#30548E" height="14">
	<td>&nbsp;
	</td>
  </tr>
  <tr>
	<td>
	  <iframe style="overflow:visible"
            name="contentIFrame"
            id="contentIFrame"
			scrolling="no"
			frameborder="0"
            marginheight="0"
            marginwidth="0"
			src="<%= iFrameSrc %>">
	  </iframe>
	</td>
  </tr>
</table>

<form name="tabContentForm" method="post" action="/PORTAL/servlet/RequestManager">

<!-- ****** HIDDEN FIELDS ***** //-->
  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">

</form>
</body>
</html>


