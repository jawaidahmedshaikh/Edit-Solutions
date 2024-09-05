

function DebugObject(obj) {
  var msg = "";
  for (var i in TB) {
    ans=prompt(i+"="+TB[i]+"\n");
    if (! ans) break;
  }
}

// Check if toolbar is being used when in text mode
function isRTextMode() {
  if (! bTextMode) return true;
  alert("Please uncheck the \"View HTML source\" checkbox.");
  mytext.focus();
  return false;
}

//Formats text in mytext.
function RunCom(what,opt) {
  if (!isRTextMode()) return;

  if (opt=="removeFormat") {
    what=opt;
    opt=null;
  }

  if (opt==null)
    mytext.document.execCommand(what);
  else
    mytext.document.execCommand(what,"",opt);

  pureText = false;
  mytext.focus();
}

//Switches between text and html mode.
function setMode(newMode) {

  bTextMode = newMode;
  var cont;
  if (bTextMode) {
    cleanHtml();
    cleanHtml();

    cont=mytext.document.body.innerHTML;
    mytext.document.body.innerText=cont;
  } else {
    cont=mytext.document.body.innerText;
    mytext.document.body.innerHTML=cont;
  }

  mytext.focus();
}

//Finds and returns an element.
function getEl(sTag,start) {
 //Copy the selected character to "start" string while start!=NULL && tagName doesn't have 'A'
  while ((start!=null) && (start.tagName!=sTag)) start = start.parentElement;
  return start;
}

function createLink() {

  //Is View Source is Checked!
  if (!isRTextMode()) return;


  var isA = getEl("A",mytext.document.selection.createRange().parentElement());

  var str=prompt("Enter URL :", isA ? isA.href : "http:\/\/");

  //if str selection type is None!(If the user didn't block the string) then
  // get the string and add the <A HREF and paste it.
  if ((str!=null) && (str!="http://")) {
    if (mytext.document.selection.type=="None") {
      var sel=mytext.document.selection.createRange();
      sel.pasteHTML("<A HREF=\""+str+"\">"+str+"</A> ");
      sel.select();
    }
    else
    //If user had selected/blocked the string  just pass this command.
    RunCom("CreateLink",str);
  }
  else
  //If nothing entered just Focust in our IFRAME.
  mytext.focus();
}

function cleanHtml() {
  var fonts = mytext.document.body.all.tags("FONT");
  var curr;
  for (var i = fonts.length - 1; i >= 0; i--) {
    curr = fonts[i];
    if (curr.style.backgroundColor == "#ffffff") curr.outerHTML = curr.innerHTML;
  }
}

function getPureHtml() {
  var str = "";
  var paras = mytext.document.body.all.tags("P");
  if (paras.length > 0) {
    for (var i=paras.length-1; i >= 0; i--) str = paras[i].innerHTML + "\n" + str;
  } else {
    str = mytext.document.body.innerHTML;
  }
  return str;
}

// Local Variables:
// c-basic-offset: 2
// End:
