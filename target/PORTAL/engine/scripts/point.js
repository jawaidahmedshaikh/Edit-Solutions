if(typeof(loc)=="undefined"||loc==""){var loc="";if(document.body&&document.body.innerHTML){var tt=document.body.innerHTML.toLowerCase();var last=tt.indexOf("point.js\"");if(last>0){var first=tt.lastIndexOf("\"",last);if(first>0&&first<last)loc=document.body.innerHTML.substr(first+1,last-first-1);}}}

var bd=1
document.write("<style type=\"text/css\">");
document.write("\n<!--\n");
document.write(".point_menu {border-color:#000000;border-style:solid;border-width:"+bd+"px 0px "+bd+"px 0px;background-color:#99bbbb;position:absolute;left:0px;top:0px;visibility:hidden;}");
document.write("a.point_plain:link, a.point_plain:visited{text-align:left;background-color:#99bbbb;color:#ffffff;text-decoration:none;border-color:#000000;border-style:solid;border-width:0px "+bd+"px 0px "+bd+"px;padding:1px 0px 1px 0px;cursor:hand;display:block;font-size:12pt;font-family:Arial, Helvetica, sans-serif;}");
document.write("a.point_plain:hover, a.point_plain:active{background-color:#ffffcc;color:#000000;text-decoration:none;border-color:#000000;border-style:solid;border-width:0px "+bd+"px 0px "+bd+"px;padding:1px 0px 1px 0px;cursor:hand;display:block;font-size:12pt;font-family:Arial, Helvetica, sans-serif;}");
document.write("a.point_l:link, a.point_l:visited{text-align:left;background:#99bbbb url("+loc+"point_l.gif) no-repeat right;color:#ffffff;text-decoration:none;border-color:#000000;border-style:solid;border-width:0px "+bd+"px 0px "+bd+"px;padding:1px 0px 1px 0px;cursor:hand;display:block;font-size:12pt;font-family:Arial, Helvetica, sans-serif;}");
document.write("a.point_l:hover, a.point_l:active{background:#ffffcc url("+loc+"point_l2.gif) no-repeat right;color: #000000;text-decoration:none;border-color:#000000;border-style:solid;border-width:0px "+bd+"px 0px "+bd+"px;padding:1px 0px 1px 0px;cursor:hand;display:block;font-size:12pt;font-family:Arial, Helvetica, sans-serif;}");
document.write("\n-->\n");
document.write("</style>");

var fc=0x000000;
var bc=0xffffcc;
if(typeof(frames)=="undefined"){var frames=0;}

startMainMenu("",0,0,1,25,0)
mainMenuItem("point_b1",".gif",30,133,"javascript:;","","Allows quick and easy definition, creation and deployment of product rules.",1,2,"point_plain");
mainMenuItem("point_b2",".gif",30,133,"javascript:;","","Allows entry and maintenance of rates, factors, plan information unit values and fund information.",1,2,"point_plain");
mainMenuItem("point_b3",".gif",31,133,"javascript:;","","Allows definition, entry, maintenance and immediate validation of calculations supporting products.",1,2,"point_plain");
mainMenuItem("point_b4",".gif",31,133,"javascript:;","","Allows execution of projections and display of results.",1,2,"point_plain");
endMainMenu("",0,0);

startSubmenu("point_b4","point_menu",81);
submenuItem("Life","/PORTAL/servlet/RequestManager?transaction=ParamTran&action=showDefaultBaseParametersScreen","","point_plain");
submenuItem("Payout","/PORTAL/servlet/RequestManager?transaction=ParamTran&action=showPayoutBaseParametersScreen","","point_plain");
endSubmenu("point_b4");

startSubmenu("point_b3_2","point_menu",122);
submenuItem("Life","/PORTAL/servlet/RequestManager?transaction=ParamTran&action=showDefaultDebugBaseParametersScreen","","point_plain");
submenuItem("Payout","/PORTAL/servlet/RequestManager?transaction=ParamTran&action=showPayoutParametersScreen","","point_plain");
submenuItem("Transaction","/PORTAL/servlet/RequestManager?transaction=ParamTran&action=showTransactionParametersScreen","","point_plain");
endSubmenu("point_b3_2");

startSubmenu("point_b3_1","point_menu",112);
submenuItem("Text Mode","/PORTAL/servlet/RequestManager?transaction=ScriptTran&action=showScriptDefault","","point_plain");
endSubmenu("point_b3_1");

startSubmenu("point_b3","point_menu",113);
mainMenuItem("point_b3_1","Maintain",0,0,"javascript:;","","",1,1,"point_l");
mainMenuItem("point_b3_2","Analyze",0,0,"javascript:;","","",1,1,"point_l");
endSubmenu("point_b3");

startSubmenu("point_b2_5_2","point_menu",103);
submenuItem("Summary","/PORTAL/servlet/RequestManager?transaction=FundTran&action=showFilteredFundSummary","","point_plain");
submenuItem("Relation","/PORTAL/servlet/RequestManager?transaction=FundTran&action=showFilteredFundRelations","","point_plain");
endSubmenu("point_b2_5_2");

startSubmenu("point_b2_5","point_menu",123);
submenuItem("Summary","/PORTAL/servlet/RequestManager?transaction=FundTran&action=showFundSummary","","point_plain");
mainMenuItem("point_b2_5_2","Filtered",0,0,"javascript:;","","",1,1,"point_l");
endSubmenu("point_b2_5");

startSubmenu("point_b2_4","point_menu",103);
submenuItem("Summary","/PORTAL/servlet/RequestManager?transaction=AreaTran&action=showAreaSummary","","point_plain");
submenuItem("Relation","/PORTAL/servlet/RequestManager?transaction=AreaTran&action=showAreaRelation","","point_plain");
endSubmenu("point_b2_4");

startSubmenu("point_b2","point_menu",155);
submenuItem("Rate","/PORTAL/servlet/RequestManager?transaction=TableTran&action=showTableDefault","","point_plain");
submenuItem("Unit Value","/PORTAL/servlet/RequestManager?transaction=TableTran&action=showUnitValueTable","","point_plain");
submenuItem("Interest Rate","/PORTAL/servlet/RequestManager?transaction=TableTran&action=showInterestRateTable","","point_plain");
mainMenuItem("point_b2_4","Area",0,0,"javascript:;","","",1,1,"point_l");
mainMenuItem("point_b2_5","Fund",0,0,"javascript:;","","",1,1,"point_l");
endSubmenu("point_b2");

startSubmenu("point_b1","point_menu",189);
submenuItem("Summary","/PORTAL/servlet/RequestManager?transaction=CompanyRuleStructureTran&action=showRuleSummary","","point_plain");
submenuItem("Relations","/PORTAL/servlet/RequestManager?transaction=CompanyRuleStructureTran&action=showRelation","","point_plain");
submenuItem("Company Structure","/PORTAL/servlet/RequestManager?transaction=CompanyStructureTran&action=showMain","","point_plain");
endSubmenu("point_b1");

loc="";
