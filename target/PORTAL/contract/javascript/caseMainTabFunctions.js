 /**
   * Visually sets the "on" tab, while displaying all the other images of the image collection as "off".
   */
  function setActiveImage(imageId)
  {
     var imageBase = "/PORTAL/contract/images/";

     var imageSrc = null;

     var images = document.all.imageCollection.childNodes;

     for (var i = 0; i < images.length; i++)
     {
         if(images[i].tagName == "IMG")
         {
             if (images[i].id == imageId)
             {
                 imageSrc = imageBase + images[i].id + "Tag.gif";
             }
             else
             {
                 imageSrc = imageBase + images[i].id + "Tab.gif";
             }

             images[i].src = imageSrc;
         }
     }
  }

  /**
   * Shows case page.
   */
  function showCase()
  {  
     setActiveImage("case");

     f.pageToShow.value = "caseMain";

     sendTransactionAction("CaseDetailTran", "showCaseMain", "main");
  }

  /**
   * Shows group page.
   */
  function showGroup()
  {
     setActiveImage("group");

     f.pageToShow.value = "group";

     sendTransactionAction("CaseDetailTran", "showGroupSummary", "main");
  }

  /**
   * Shows requirements page.
   */
  function showRequirements()
  {
     setActiveImage("requirements");

     f.pageToShow.value = "requirements";

     sendTransactionAction("CaseDetailTran", "showCaseRequirements", "main");
  }

  /**
   * Shows agents page.
   */
  function showAgents()
  {
     setActiveImage("agents");

     f.pageToShow.value = "agents";

     sendTransactionAction("CaseDetailTran", "showCaseAgents", "main");
  }

  /**
   * Shows history page.
   */
  function showHistory()
  {
     setActiveImage("history");

     f.pageToShow.value = "caseHistory";

     sendTransactionAction("CaseDetailTran", "showCaseHistory", "main");

  }
  
  /**
   * Talking to Tom, we decided that clicking on any tab in the set of tabs for
   * these set of screens would invoke a "Save Without Validation". In short,
   * we are trying to save state in the DB and not in the front-end while
   * entering information. Without the backing of JSF or some other "stateful"
   * front-end, saving state with plain-old HttpSession is unruly.
   */
  function saveCurrentPage()
  {
    // Track the current page before assigning to the next page (the next page to show).
    var currentPage = f.pageToShow.value;
    
    f.pageToLeave.value = currentPage;
    
    sendTransactionAction("CaseDetailTran", "saveWithoutValidation", "main");
  }
  