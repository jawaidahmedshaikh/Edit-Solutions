// Anytime new menu item is added UseCaseComponentName and MethodName needs to be added at the end of the line as comments.
// If mainMenuItem or subMainMenuItem has submenus then UseCaseComponentName and MethodName need not be added

//startMainMenu("mainmenu_top.gif",39,415,1,0,0)
startMainMenu("",0,0,1,0,0)
mainMenuItem("mainmenu_b1",".gif",41,415,"javascript:showIndividuals();","","Client Administration",2,2,"mainmenu_plain"); //UseCaseComponentName=client.component.ClientUseCaseComponent&MethodName=accessClient
mainMenuItem("mainmenu_b2",".gif",41,415,"javascript:;","","Case/Group",1,2,"mainmenu_plain");
mainMenuItem("mainmenu_b3",".gif",41,415,"javascript:;","","Contract Administration",1,2,"mainmenu_plain");
mainMenuItem("mainmenu_b4",".gif",41,415,"javascript:;","","Agent Administration",1,2,"mainmenu_plain");
mainMenuItem("mainmenu_b5",".gif",41,415,"javascript:showCasetracking();","","Claims",2,2,"mainmenu_plain"); //UseCaseComponentName=casetracking.usecase.CasetrackingUseCaseImpl&MethodName=accessCaseTracking
mainMenuItem("mainmenu_b6",".gif",41,415,"javascript:;","","Reinsurance",1,2,"mainmenu_plain");
mainMenuItem("mainmenu_b7",".gif",41,415,"javascript:;","","Financial",1,2,"mainmenu_plain");
mainMenuItem("mainmenu_b8",".gif",41,415,"javascript:;","","System Administration",1,2,"mainmenu_plain");
endMainMenu("",0,0);
//endMainMenu("mainmenu_bottom.gif",39,415)

startSubmenu("mainmenu_b8_4","mainmenu_menu",150);
submenuItem("Logging Admin","javascript:showLoggingAdmin();","","mainmenu_plain");
submenuItem("Logging Setup","javascript:showLoggingSetup();","","mainmenu_plain");
submenuItem("Logging Results","javascript:showLoggingResults();","","mainmenu_plain");
endSubmenu("mainmenu_b8_4");

startSubmenu("mainmenu_b8_2","mainmenu_menu",170);
submenuItem("PRASE","javascript:showProductProfessionals();","","mainmenu_plain"); //UseCaseComponentName=productbuild.component.ProductBuildUseCaseComponent&MethodName=accessPRASE
submenuItem("Online Report","javascript:showOnlineReportSummary();","","mainmenu_plain"); //UseCaseComponentName=productbuild.component.ProductBuildUseCaseComponent&MethodName=accessOnlineReport
submenuItem("Business Calendar","javascript:showBusinessCalendar();","","mainmenu_plain"); //UseCaseComponentName=productbuild.component.ProductBuildUseCaseComponent&MethodName=accessBusinessCalendar
submenuItem("Export","javascript:showExportSelection();","","mainmenu_plain"); //UseCaseComponentName=productbuild.component.ProductBuildUseCaseComponent&MethodName=accessExport
endSubmenu("mainmenu_b8_2");

startSubmenu("mainmenu_b8_1","mainmenu_menu",170);
submenuItem("Daily Batch","javascript:showBatch();","","mainmenu_plain"); //UseCaseComponentName=batch.component.BatchUseCaseComponent&MethodName=accessDailyBatch
submenuItem("On-Request Batch","javascript:showReports();","","mainmenu_plain"); //UseCaseComponentName=batch.component.BatchUseCaseComponent&MethodName=accessOnRequestBatch
endSubmenu("mainmenu_b8_1");

startSubmenu("mainmenu_b8","mainmenu_menu",196);
mainMenuItem("mainmenu_b8_1","Off-line Processing",0,0,"javascript:;","","",1,1,"mainmenu_l");
mainMenuItem("mainmenu_b8_2","Product Build",0,0,"javascript:;","","",1,1,"mainmenu_l");
submenuItem("Security","javascript:showSecurityAdmin();","","mainmenu_plain"); //UseCaseComponentName=security.component.SecurityUseCaseComponent&MethodName=accessSecurity
mainMenuItem("mainmenu_b8_4","Logging",0,0,"javascript:;","","",1,1,"mainmenu_l");
submenuItem("Conversion","javascript:showConversion();","","mainmenu_plain");
submenuItem("Query","javascript:showQuery();","","mainmenu_plain"); 
endSubmenu("mainmenu_b8");

startSubmenu("mainmenu_b7","mainmenu_menu",226);
submenuItem("Chart Of Accounts","javascript:showAccounting();","","mainmenu_plain"); //UseCaseComponentName=accounting.component.AccountingUseCaseComponent&MethodName=accessAccounting
submenuItem("Accounting Adjustments","javascript:showJournalAdjustment();","","mainmenu_plain"); //UseCaseComponentName=accounting.component.AccountingUseCaseComponent&MethodName=accessJournalAdjustment
submenuItem("Cash Batch","javascript:showCashBatch();","","mainmenu_plain"); //UseCaseComponentName=contract.component.NewBusinessUseCaseComponent&MethodName=accessCashBatch
endSubmenu("mainmenu_b7");

startSubmenu("mainmenu_b6","mainmenu_menu",229);
submenuItem("Reinsurance Information","javascript:showReinsurerInformation();","","mainmenu_plain"); //UseCaseComponentName=reinsurance.component.ReinsuranceUseCaseComponent&MethodName=accessReinsurerInformation
submenuItem("Treaty Relationships","javascript:showTreatyRelations();","","mainmenu_plain"); //UseCaseComponentName=reinsurance.component.ReinsuranceUseCaseComponent&MethodName=accessTreatyRelations
submenuItem("Contract Treaty","javascript:showContractTreatyRelations();","","mainmenu_plain"); //UseCaseComponentName=reinsurance.component.ReinsuranceUseCaseComponent&MethodName=accessContractTreatyRelations
endSubmenu("mainmenu_b6");
 
startSubmenu("mainmenu_b4","mainmenu_menu",182);
submenuItem("Commission Profile","javascript:showCommissionContract();","","mainmenu_plain"); //UseCaseComponentName=productbuild.component.ProductBuildUseCaseComponent&MethodName=accessCommissionContract
submenuItem("Agent Detail","javascript:showAgentDetail();","","mainmenu_plain"); //UseCaseComponentName=agent.component.AgentUseCaseComponent&MethodName=accessAgentDetail
submenuItem("Agent Hierarchies","javascript:showAgentHierarchy();","","mainmenu_plain"); //UseCaseComponentName=agent.component.AgentUseCaseComponent&MethodName=accessAgentHierarchy
submenuItem("Bonus Programs","javascript:showAgentBonusProgram();","","mainmenu_plain"); //UseCaseComponentName=agent.component.AgentUseCaseComponent&MethodName=accessAgentBonusProgram
submenuItem("Group Move","javascript:showAgentMove();","","mainmenu_plain"); //UseCaseComponentName=agent.component.AgentUseCaseComponent&MethodName=accessAgentHierarchy
endSubmenu("mainmenu_b4");

startSubmenu("mainmenu_b3","mainmenu_menu",197);
submenuItem("New Business","javascript:showQuote();","","mainmenu_plain"); //UseCaseComponentName=contract.component.NewBusinessUseCaseComponent&MethodName=accessNewBusiness
submenuItem("New Business Import","javascript:showImportNewBusiness();","","mainmenu_plain"); //UseCaseComponentName=contract.component.NewBusinessUseCaseComponent&MethodName=accessImportNewBusiness
submenuItem("Inforce Contract","javascript:showContract();","","mainmenu_plain"); //UseCaseComponentName=contract.component.InforceUseCaseComponent&MethodName=accessInforceContract
submenuItem("Batch Contract","javascript:showBatchContract();","","mainmenu_plain");
endSubmenu("mainmenu_b3");

startSubmenu("mainmenu_b2","mainmenu_menu",200);
submenuItem("Case/Group Setup","javascript:showCase();","","mainmenu_plain"); //UseCaseComponentName=contract.component.CaseUseCaseComponent&MethodName=accessCase
submenuItem("Billing And Collection","javascript:showBilling();","","mainmenu_plain"); //UseCaseComponentName=contract.component.CaseUseCaseComponent&MethodName=accessCase
//submenuItem("Batch Contract","javascript:showCase();","","mainmenu_plain"); //UseCaseComponentName=contract.component.CaseUseCaseComponent&MethodName=accessCase
endSubmenu("mainmenu_b2");
