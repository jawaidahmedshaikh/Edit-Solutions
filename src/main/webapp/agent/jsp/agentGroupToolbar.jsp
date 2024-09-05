<jsp:include page="/common/jsp/template/template-toolbar.jsp" flush="true">
    <jsp:param name="pageTitle" value="Agent Group"/>
    <jsp:param name="toolbar-item:CommissionProfile" value="Commission Profile:showCommissionContract"/>
    <jsp:param name="toolbar-item:AgentDetail" value="Agent Detail:showAgentDetail"/>
    <jsp:param name="toolbar-item:AgentBonusProgram" value="Agent Bonus Program:showAgentBonusProgram"/>
    <jsp:param name="toolbar-item:AgentGroupMove" value="Agent Group Move:showAgentMove"/>    
    <jsp:param name="toolbar-item:Client" value="Client:showIndividuals"/>
    <jsp:param name="toolbar-item:Roles" value="Roles:showRoleAdmin"/>
</jsp:include>