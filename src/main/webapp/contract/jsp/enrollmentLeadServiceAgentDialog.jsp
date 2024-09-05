<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input"%>
<%@ page import="group.*, client.*, edit.portal.widgettoolkit.*, edit.common.vo.*, edit.common.*, edit.portal.taglib.*, role.*"%>

<jsp:useBean id="enrollmentLeadServiceAgent" class="group.EnrollmentLeadServiceAgent" scope="request"/>

<jsp:useBean id="enrollmentPKStringBean" class="edit.common.ui.StringBean" scope="request"/>

<jsp:useBean id="clientRole" class="role.ClientRole" scope="request"/>

<jsp:useBean id="clientDetail" class="client.ClientDetail" scope="request"/>
       
<%
    // boolean for radio button to know whether to CHECK the leadAgent or serviceAgent radio button
    boolean leadAgentChecked = false;
    
    boolean serviceAgentChecked = false;
    
    boolean regionCTExists = false;
    
    // there is a currently selected EnrollmentLeadServiceAgent
    if (enrollmentLeadServiceAgent.getEnrollmentLeadServiceAgentPK() != null) 
    {
        leadAgentChecked = (clientRole.getRoleTypeCT().equals(ClientRole.ROLETYPECT_AGENT_LEAD));            
        
        serviceAgentChecked = (clientRole.getRoleTypeCT().equals(ClientRole.ROLETYPECT_AGENT_SERVICING)); 
        
        regionCTExists = (enrollmentLeadServiceAgent.getRegionCT() != null);
    }
    
    // ContractCodeCT <select>
    CodeTableVO[] regionVOCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("REGION");
    
    InputSelect regionCTs = new InputSelect(regionVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);    
    
    // Storing this because upon clearing a form, we still need it.
    String enrollmentPK = request.getParameter("enrollmentPK");
    
%>
    <script type="text/javascript">
    
        var enrollmentPK = "<%= enrollmentPK %>";
        
        var regionCTExists = <%= regionCTExists %>;
        
        /**
        * Callback from the template-core.jsp
        */
        function postInit()
        {
            f.enrollmentPK.value = enrollmentPK;
            
            if (!regionCTExists) 
            {
                f.regionCT.selectedIndex = 0;
            }
        }
    
        /**
        * Assuming all required information is present, saves the
        * information as a new EnrollmentLeadServiceAgent.
        */
        function saveLeadServiceAgent()
        {
            if (validateForm(f, "REQUIRED"))
            {
                var roleExists = false;
            
                if (f.roleTypeRadioButton[0].checked)
                {
                    f.roleTypeCT.value = "<%= ClientRole.ROLETYPECT_AGENT_LEAD %>"; // a CodeTable value
                    
                    roleExists = true;
                }
                else if (f.roleTypeRadioButton[1].checked)
                {
                    f.roleTypeCT.value = "<%= ClientRole.ROLETYPECT_AGENT_SERVICING %>"; // a CodeTable value
                    
                    roleExists = true;                    
                }
                else
                {
                    alert("The following Fields are required \n\n Role");
                    
                    roleExists = false;
                }
                
                if (roleExists)
                {
                    var selectedRowId = getSelectedRowId("EnrollmentLeadServiceAgentTableModel");
                
                    if (valueIsEmpty(selectedRowId))
                    {
                        sendTransactionAction("CaseDetailTran", "saveEnrollmentLeadServiceAgent", "_self");
                    }
                    else
                    {
                        sendTransactionAction("CaseDetailTran", "updateEnrollmentLeadServiceAgent", "_self");
                    }
                }
            }
        }
        
        /**
        * Pops-up a search dialog to find an Agent# to use for the new EnrollmentLeadServiceAgent.
        */
        function addEnrollmentLeadServiceAgent()
        {
            var width = screen.width * .65;
            
            var height = screen.height * .55;

            openDialog("agentSearchDialog","left=0,top=0,resizable=no,status=yes", width, height);
            
            sendTransactionAction("CaseDetailTran", "addEnrollmentLeadServiceAgent", "agentSearchDialog");
        }
        
        /**
        * Cancels any user-entered data.
        */
        function cancelEnrollmentLeadServiceAgent()
        {
            sendTransactionAction("CaseDetailTran", "cancelEnrollmentLeadServiceAgent", "_self");        
        }
        
        /**
        * Deletes the EnrollmentLeadServiceAgent identified by the currently selected row.
        */
        function deleteEnrollmentLeadServiceAgent()
        {
            var selectedRowId = getSelectedRowId("EnrollmentLeadServiceAgentTableModel");
            
            if (!valueIsEmpty(selectedRowId))
            {
                sendTransactionAction("CaseDetailTran", "deleteEnrollmentLeadServiceAgent", "_self");        
            }
            else
            {
                alert("Please Select an Enrollment Lead Service Agent to Delete");
            }
        }
    
        /**
        * Callback from TableModel. Show the details of the selected row.
        */
        function onTableRowSingleClick(tableId)
        {
            sendTransactionAction("CaseDetailTran", "showEnrollmentLeadServiceAgentDetails", "_self");
        }        
    
    </script>

      <table cellspacing="2" cellpadding="3" border="0" width="100%">
        <tr>
          <td width="25%" align="right" height="29">Agent Name:</td>
          <td width="25%" height="29">
            <input:text name="prettyName" bean="clientDetail" attributesText="readonly='true' REQUIRED" size="30"/>
          </td>
          <td width="25%" height="29" align="right">Agent #:</td>
          <td width="25%" align="left" height="29">
            <input:text name="referenceID" bean="clientRole" attributesText="readonly='true'"/>
          </td>
        </tr>
        <tr>
          <td width="25%" align="right">&nbsp;</td>
          <td width="25%" align="left">
            <input type="radio" name="roleTypeRadioButton" <%= (leadAgentChecked == true)?"CHECKED":"" %>/>
            Lead Agent
          </td>
          <td width="25%" align="left">
            <input type="radio" name="roleTypeRadioButton" align="left" <%= (serviceAgentChecked == true)?"CHECKED":"" %>/>
            Servicing Agent
          </td>
          <td width="25%" align="left">&nbsp;</td>
        </tr>
        <tr>
          <td width="25%" align="right">Region:</td>
          <td width="25%" align="left">
            <input:select bean="enrollmentLeadServiceAgent" name="regionCT"
                          options="<%= regionCTs.getOptions() %>"
                          attributesText="id=\'regionCT\'"/>
          </td>
          <td width="25%" align="right">&nbsp;</td>
          <td width="25%" align="left">&nbsp;</td>
        </tr>
        <tr>
          <td width="25%" align="right">Effective Date:</td>
          <td width="25%" align="left">
                      <input:text name="uIEffectiveDate" bean="enrollmentLeadServiceAgent"
                  attributesText="id='uIEffectiveDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10' REQUIRED"/>
            <a href="javascript:show_calendar('f.uIEffectiveDate', f.uIEffectiveDate.value);"><img
                   src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                   alt="Select a date from the calendar"></a>
          </td>
          <td width="25%" align="right">Termination Date:</td>
          <td width="25%" align="left">
                        <input:text name="uITerminationDate" bean="enrollmentLeadServiceAgent" default="12/31/9999"
                  attributesText="id='uITerminationDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
            <a href="javascript:show_calendar('f.uITerminationDate', f.uITerminationDate.value);"><img
                   src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                   alt="Select a date from the calendar"></a>
          </td>
        </tr>
      </table>
    
        <br>
    
      <table cellspacing="0" cellpadding="0" border="0"
               style="width:100%; height:2%">
          <tr>
            <td width="20%" nowrap="nowrap">
              <input type="button" name="btnAdd" value=" Add  " onclick="addEnrollmentLeadServiceAgent()"/>
              <input type="button" name="btnSave" value="Save " onclick="saveLeadServiceAgent()"/>
              <input type="button" name="btnCancel" value="Cancel" onclick="cancelEnrollmentLeadServiceAgent()"/>
              <input type="button" name="btnDelete" value="Delete" onclick="deleteEnrollmentLeadServiceAgent()"/>
            </td>
            <td width="60%" valign="top" align="center">
              <span class="">Lead Service Agents</span>
            </td>
            <td width="20%" valign="top" align="right">&nbsp;</td>
          </tr>
        </table>
      <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
          <jsp:param name="tableId"
                     value="EnrollmentLeadServiceAgentTableModel"/>
          <jsp:param name="tableHeight" value="50"/>
          <jsp:param name="multipleRowSelect" value="false"/>
          <jsp:param name="singleOrDoubleClick"
                     value="<%= TableModel.PROPERTY_SINGLE_OR_DOUBLE_CLICK_VALUE_SINGLE %>"/>
        </jsp:include>
      <table width="100%">
            <tr>
                <td align="right">
                    <input type="button" value="Close" onclick="window.close()"/>        
                </td>
            <tr>
      </table>
      
      <input:hidden bean="enrollmentPKStringBean" name="enrollmentPK"/>
      <input:hidden bean="enrollmentLeadServiceAgent" name="enrollmentLeadServiceAgentPK"/>
      <input:hidden bean="clientRole" name="clientRolePK"/>
      <input:hidden bean="clientDetail" name="clientDetailPK"/>      
      <input type="hidden" name="roleTypeCT"/>