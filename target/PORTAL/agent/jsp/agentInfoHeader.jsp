<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 fission.beans.*,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.CodeTableVO,
                 edit.common.vo.AgentVO,
                 edit.common.vo.ClientDetailVO,
                 fission.utility.*,
                 engine.*" %>

<%
    AgentVO agentVO = (AgentVO) session.getAttribute("agentVO");
    ClientDetailVO clientDetailVO = (ClientDetailVO) session.getAttribute("clientDetailVO");
    String agentName = "";
    String agentNumber = "";
    String companyName = "";
    String status = "";
    if (clientDetailVO != null)
    {
        String lastName = clientDetailVO.getLastName();
        if (lastName == null || lastName.equals(""))
        {
            agentName = clientDetailVO.getCorporateName();
        }
        else
        {
            agentName = clientDetailVO.getLastName() + ", " + clientDetailVO.getFirstName();
        }
    }

    if (agentVO != null)
    {
        status = agentVO.getAgentStatusCT();

        agentNumber = Util.initString(agentVO.getAgentNumber(), "");

        companyName = Company.findByPK(new Long(agentVO.getCompanyFK())).getCompanyName();
    }
%>

<span id="mainContent" style="border-style:solid; border-width:2;  position:relative; width:100%; top:0; left:0; z-index:0; overflow:visible">
  <table align="right" width="100%" height="5%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td nowrap align="left"> Agent Name:&nbsp;
        <%= agentName%>
      </td>
      <td nowrap align="left"> Agent Number:&nbsp;
	    <%= agentNumber%>
      </td>
      <td nowrap align="left"> Company:&nbsp;
	    <%= companyName %>
      </td>
      <td nowrap align="left"> Status:&nbsp;
	    <%= status%>
      </td>
	</tr>
  </table>
</span>