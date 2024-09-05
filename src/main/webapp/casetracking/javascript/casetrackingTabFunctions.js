  /**
   * Visually sets the "on" tab, while displaying all the other images of the image collection as "off".
   */
  function setActiveImage(imageId)
  {
     var imageBase = "/PORTAL/casetracking/images/";

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

  /**
   * Shows client page.
   */
  function showClient()
  {
     setActiveImage("client");

     f.pageToShow.value = "casetrackingClient";
     
     sendTransactionAction("CaseTrackingTran", "showCasetrackingClient", "main");

     //document.frames["contentIFrame"].sendTransactionAction("CaseTrackingTran", "showCasetrackingClient", "contentIFrame");
  }

  function checkRequiredFieldsOnClientPage()
  {
    var returnValue = true;

    // the following two elemens are supposed to be on casetracking ClientDetail page
    // process is a selecte list box
    var process = document.getElementById("caseTrackingProcess");
    // ClientDetailPK is a hidden parameter.
    var clientDetailPK = document.getElementById("clientDetailPK");

    // the process element is on ClientDetail page.
    if (clientDetailPK != null)
    {
        if (!valueIsEmpty(clientDetailPK.value))
        {
            if (process != null)
            {
                if (selectElementIsEmpty(process))
                {
                    alert("Please Select the Process.");

                    returnValue = false;
                }
            }
        }
    }

    return returnValue;
  }

  /**
   * Shows requirement page.
   */
  function showRequirement()
  {
     if (checkRequiredFieldsOnClientPage())
     {
         setActiveImage("requirement");

         f.pageToShow.value = "casetrackingRequirement";

         sendTransactionAction("CaseTrackingTran", "showCasetrackingRequirement", "main");
     }
     //document.frames["contentIFrame"].sendTransactionAction("CaseTrackingTran", "showCasetrackingRequirement", "contentIFrame");
  }

  /**
   * Shows log page.
   */
  function showLog()
  {
     setActiveImage("log");

     f.pageToShow.value = "casetrackingLog";

     sendTransactionAction("CaseTrackingTran", "showCasetrackingLog", "main");

     //document.frames["contentIFrame"].sendTransactionAction("CaseTrackingTran", "showCasetrackingHistory", "contentIFrame");
  }