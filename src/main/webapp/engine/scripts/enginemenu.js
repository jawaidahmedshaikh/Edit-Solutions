//©Xara Ltd
if(typeof(loc)=="undefined"||loc==""){var loc="";if(document.body&&document.body.innerHTML){var tt=document.body.innerHTML;var ml=tt.match(/["']([^'"]*)enginemenu.js["']/i);if(ml && ml.length > 1) loc=ml[1];}}

var bd=0
document.write("<style type=\"text/css\">");
document.write("\n<!--\n");
document.write(".enginemenu_menu {z-index:999;border-color:#000000;border-style:solid;border-width:"+bd+"px 0px "+bd+"px 0px;background-color:#99bbbb;position:absolute;left:0px;top:0px;visibility:hidden;}");
document.write(".enginemenu_plain, a.enginemenu_plain:link, a.enginemenu_plain:visited{text-align:left;background-color:#99bbbb;color:#000000;text-decoration:none;border-color:#000000;border-style:solid;border-width:0px "+bd+"px 0px "+bd+"px;padding:3px 0px 3px 0px;cursor:hand;display:block;font-size:12pt;font-family:Arial, Helvetica, sans-serif;}");
document.write("a.enginemenu_plain:hover, a.enginemenu_plain:active{background-color:#648282;color:#000000;text-decoration:none;border-color:#000000;border-style:solid;border-width:0px "+bd+"px 0px "+bd+"px;padding:3px 0px 3px 0px;cursor:hand;display:block;font-size:12pt;font-family:Arial, Helvetica, sans-serif;}");
document.write("a.enginemenu_l:link, a.enginemenu_l:visited{text-align:left;background:#99bbbb url("+loc+"enginemenu_l.gif) no-repeat right;color:#000000;text-decoration:none;border-color:#000000;border-style:solid;border-width:0px "+bd+"px 0px "+bd+"px;padding:3px 0px 3px 0px;cursor:hand;display:block;font-size:12pt;font-family:Arial, Helvetica, sans-serif;}");
document.write("a.enginemenu_l:hover, a.enginemenu_l:active{background:#648282 url("+loc+"enginemenu_l.gif) no-repeat right;color: #000000;text-decoration:none;border-color:#000000;border-style:solid;border-width:0px "+bd+"px 0px "+bd+"px;padding:3px 0px 3px 0px;cursor:hand;display:block;font-size:12pt;font-family:Arial, Helvetica, sans-serif;}");
document.write("\n-->\n");
document.write("</style>");

var fc=0x000000;
var bc=0x648282;
if(typeof(frames)=="undefined"){var frames=0;}

startMainMenu("enginemenu_top.gif",25,150,1,0,0)
mainMenuItem("enginemenu_b1",".gif",33,150,"javascript:;","","Product",1,2,"enginemenu_plain");
mainMenuItem("enginemenu_b2",".gif",33,150,"javascript:;","","Tables",1,2,"enginemenu_plain");
mainMenuItem("enginemenu_b3",".gif",33,150,"/PORTAL/servlet/RequestManager?transaction=ScriptTran&action=showScriptDefault","","Scripts",2,2,"enginemenu_plain");
endMainMenu("enginemenu_bottom.gif",25,150)

startSubmenu("enginemenu_b1","enginemenu_menu",147);
submenuItem("Company Structure","/PORTAL/servlet/RequestManager?transaction=ProductStructureTran&action=showMain","","enginemenu_plain");
mainMenuItem("enginemenu_b1_2","Rules",0,0,"javascript:;","","",1,1,"enginemenu_l");
mainMenuItem("enginemenu_b1_3","Code Table",0,0,"javascript:;","","",1,1,"enginemenu_l");
mainMenuItem("enginemenu_b1_4","Area Table",0,0,"javascript:;","","",1,1,"enginemenu_l");
mainMenuItem("enginemenu_b1_5","Requirements",0,0,"javascript:;","","",1,1,"enginemenu_l");
mainMenuItem("enginemenu_b1_6","Questionnaire",0,0,"javascript:;","","",1,1,"enginemenu_l");
submenuItem("Transactions","/PORTAL/servlet/RequestManager?transaction=EventAdminTran&action=showTransactionPrioritySummary","","enginemenu_plain");
endSubmenu("enginemenu_b1");

startSubmenu("enginemenu_b1_2","enginemenu_menu",80);
submenuItem("Summary","/PORTAL/servlet/RequestManager?transaction=ProductRuleStructureTran&action=showRuleSummary","","enginemenu_plain");
submenuItem("Relations","/PORTAL/servlet/RequestManager?transaction=ProductRuleStructureTran&action=showRelation","","enginemenu_plain");
endSubmenu("enginemenu_b1_2");

startSubmenu("enginemenu_b1_3","enginemenu_menu",80);
submenuItem("Summary","/PORTAL/servlet/RequestManager?transaction=CodeTableTran&action=showCodeTableDefSummary","","enginemenu_plain");
submenuItem("Relations","/PORTAL/servlet/RequestManager?transaction=CodeTableTran&action=showCodeTableRelations","","enginemenu_plain");
endSubmenu("enginemenu_b1_3");

startSubmenu("enginemenu_b1_4","enginemenu_menu",80);
submenuItem("Summary","/PORTAL/servlet/RequestManager?transaction=AreaTran&action=showAreaSummary","","enginemenu_plain");
submenuItem("Relations","/PORTAL/servlet/RequestManager?transaction=AreaTran&action=showAreaRelation","","enginemenu_plain");
endSubmenu("enginemenu_b1_4");

startSubmenu("enginemenu_b1_5","enginemenu_menu",80);
submenuItem("Summary","/PORTAL/servlet/RequestManager?transaction=QuoteDetailTran&action=showRequirementDetailSummary","","enginemenu_plain");
submenuItem("Relations","/PORTAL/servlet/RequestManager?transaction=QuoteDetailTran&action=showRequirementRelationPage","","enginemenu_plain");
endSubmenu("enginemenu_b1_5");

startSubmenu("enginemenu_b1_6","enginemenu_menu",80);
submenuItem("Summary","/PORTAL/servlet/RequestManager?transaction=ProductBuildTran&action=showQuestionnaire","","enginemenu_plain");
submenuItem("Relations","/PORTAL/servlet/RequestManager?transaction=ProductBuildTran&action=showQuestionnaireRelation","","enginemenu_plain");
endSubmenu("enginemenu_b1_6");

startSubmenu("enginemenu_b2","enginemenu_menu",121);
submenuItem("Rate","/PORTAL/servlet/RequestManager?transaction=TableTran&action=showTableDefault","","enginemenu_plain");
submenuItem("Unit Value","/PORTAL/servlet/RequestManager?transaction=TableTran&action=showUnitValueTable","","enginemenu_plain");
submenuItem("Interest Rate","/PORTAL/servlet/RequestManager?transaction=TableTran&action=showInterestRateTable","","enginemenu_plain");
mainMenuItem("enginemenu_b2_4","Fund",0,0,"javascript:;","","",1,1,"enginemenu_l");
endSubmenu("enginemenu_b2");

startSubmenu("enginemenu_b2_4","enginemenu_menu",100);
submenuItem("Summary","/PORTAL/servlet/RequestManager?transaction=FundTran&action=showFundSummary","","enginemenu_plain");
mainMenuItem("enginemenu_b2_4_2","Filtered",0,0,"javascript:;","","",1,1,"enginemenu_l");
endSubmenu("enginemenu_b2_4");

startSubmenu("enginemenu_b2_4_2","enginemenu_menu",80);
submenuItem("Summary","/PORTAL/servlet/RequestManager?transaction=FundTran&action=showFilteredFundSummary","","enginemenu_plain");
submenuItem("Relations","/PORTAL/servlet/RequestManager?transaction=FundTran&action=showFilteredFundRelations","","enginemenu_plain");
endSubmenu("enginemenu_b2_4_2");

loc="";
