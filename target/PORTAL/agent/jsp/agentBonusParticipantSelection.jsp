<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page import="fission.utility.Util,
                 java.math.BigDecimal,
                 edit.common.CodeTableWrapper,
                 agent.business.Agent,
                 agent.component.AgentComponent,
                 edit.common.vo.*,
                 agent.ParticipatingAgent,
                 edit.common.EDITBigDecimal,
                 edit.common.EDITDate,
                 edit.common.EDITDateTime,
                 agent.BonusProgram,
                 agent.PremiumLevel,
                 java.util.*,
                 agent.PlacedAgent"%>
<!--
 * User: gfrosti
 * Date: Nov 12, 2004
 * Time: 3:08:17 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%

    CodeTableVO[] yesNoCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("YESNO");

    String responseMessage = (String) request.getAttribute("responseMessage");

    BonusProgram bonusProgram = (BonusProgram) request.getAttribute("selectedBonusProgram");

    String contractCodeCT = (String) Util.initObject(bonusProgram, "contractCodeCT", "");
    String commissionLevelCT = (String) Util.initObject(bonusProgram, "commissionLevelCT", "");
    String bonusName = (String) Util.initObject(bonusProgram, "bonusName", "");

    ParticipatingAgent[] participatingAgents = (ParticipatingAgent[]) request.getAttribute("participatingAgents");

    ParticipatingAgent participatingAgent = (ParticipatingAgent) request.getAttribute("selectedParticipatingAgent");

    String placedAgentPKStr = (String) request.getAttribute("placedAgentPK");

    long participatingAgentPK = Util.initLong(participatingAgent, "participatingAgentPK", 0L);
    EDITBigDecimal bonusTaxableAmount = (EDITBigDecimal) Util.initObject(participatingAgent, "bonusTaxableAmount", new EDITBigDecimal());
    EDITBigDecimal lastCheckAmount = (EDITBigDecimal) Util.initObject(participatingAgent, "lastCheckAmount", new EDITBigDecimal());
    String produceCheckInd = (String) Util.initObject(participatingAgent, "produceCheckInd", null);
    EDITDateTime lastStatementDateTime = (EDITDateTime) Util.initObject(participatingAgent, "lastStatementDateTime", null);
    EDITDateTime lastCheckDateTime = (EDITDateTime) Util.initObject(participatingAgent, "lastCheckDateTime", null);
    String bonusProgramOverrideInd = (String) Util.initObject(participatingAgent, "bonusProgramOverrideInd", "N");

    Long placedAgentPK = null;
    if (participatingAgent != null)
    {
        placedAgentPK = participatingAgent.getPlacedAgent().getPlacedAgentPK();
    }
    else if (placedAgentPKStr != null)
    {
        placedAgentPK = new Long(placedAgentPKStr);
    }

    PlacedAgent placedAgent = null;
    if (placedAgentPK != null)
    {
        placedAgent = PlacedAgent.findByPK(placedAgentPK);
    }

    String agentNumber = "";
    String agentName = "";
    String situation = "";

    if (placedAgent != null)
    {
        agentNumber = Util.initString(placedAgent.getAgentContract().getAgent().getAgentNumber(), "");
        agentName = Util.initString(placedAgent.getClientRole().getClientDetail().getName(), "");
        situation = Util.initString(placedAgent.getSituationCode(), "");
    }

    PremiumLevel premiumLevel = (PremiumLevel) request.getAttribute("selectedPremiumLevel");
    long premiumLevelPK = Util.initLong(premiumLevel, "premiumLevelPK", 0L);

    EDITBigDecimal issuePremiumLevel = (EDITBigDecimal) Util.initObject(premiumLevel, "IssuePremiumLevel", new EDITBigDecimal());
    EDITBigDecimal productLevelIncreasePercent = (EDITBigDecimal) Util.initObject(premiumLevel, "ProductLevelIncreasePercent", new EDITBigDecimal());;
    EDITBigDecimal productLevelIncreaseAmount = (EDITBigDecimal) Util.initObject(premiumLevel, "ProductLevelIncreaseAmount", new EDITBigDecimal());;
    EDITBigDecimal increaseStopAmount = (EDITBigDecimal) Util.initObject(premiumLevel, "IncreaseStopAmount", new EDITBigDecimal());;
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Bonus Program Participant Selection</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>


<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";

    var bonusProgramOverrideInd = "<%= bonusProgramOverrideInd %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        checkForResponseMessage();

        formatCurrency();

        setCheckBoxState(f.bonusProgramOverrideIndCheckBox, bonusProgramOverrideInd);

        setOverrideFieldsState();
        
        // Initialize scroll tables
        // initScrollTable(document.getElementById("BonusCriteriaTableModelScrollTable"));
    }

    /**
     * Shows the Details.
     */
    function showParticipantPremiumLevelDetail()
    {
        f.premiumLevelPK.value = getSelectedRowId();
        
        sendTransactionAction("AgentBonusTran", "showParticipantPremiumLevelDetail", "_self");
    }

    /**
     * Shows the dialog to find Participating Agents.
     */
    function showAgentBonusSearchDialog()
    {
        var width = getScreenWidth();

        var height = getScreenHeight() * 0.50;

        openDialog("showAgentBonusSearchDialog", null, width, height);

        sendTransactionAction("AgentBonusTran", "showAgentBonusSearchDialog", "showAgentBonusSearchDialog");
    }

    /**
     * Saves the Participating Agent information.
     */
    function saveParticipatingAgent()
    {
        var message = editForValues();

        if (message != "")
        {
            alert(message);
        }
        else
        {
            setCheckBoxValue(f.bonusProgramOverrideIndCheckBox, f.bonusProgramOverrideInd);

            sendTransactionAction("AgentBonusTran", "saveParticipatingAgent", "_self");
        }
    }

    /**
     * Cancels current Participating Agent edits.
     */
    function cancelParticipatingAgent()
    {
        sendTransactionAction("AgentBonusTran", "cancelParticipatingAgent", "_self");
    }

    /**
     * Deletes the selected Participating Agent.
     */
    function deleteParticipatingAgent()
    {
        var selectedRowId = getSelectedRowId("participatingAgentSummary");

        if (valueIsEmpty(selectedRowId))
        {
            alert("Please Select Participating Agent");
        }
        else
        {
            var shouldDeleteParticipatingAgent = confirm("Delete Participating Agent?");

            if (shouldDeleteParticipatingAgent)
            {
                sendTransactionAction("AgentBonusTran", "deleteParticipatingAgent", "_self");
            }
        }
    }

    /**
     * Shows the detail of the selected Participating Agent.
     */
    function showParticipatingAgentDetail()
    {
        var selectedRowId = getSelectedRowId("participatingAgentSummary");

        f.participatingAgentPK.value = selectedRowId;

        sendTransactionAction("AgentBonusTran", "showParticipatingAgentDetail", "_self");
    }

    /**
     *  Opens the associate products/criteria page.
     */
    function showParticipantProdCriteriaAssoc()
    {
        if (isPremiumLevelSelected())
        {
            var width = .95 * screen.width;
            var height = .75 * screen.height;

            openDialog("participantProdCriteriaAssocDialog","left=0,top=0,resizable=no", width, height);

            sendTransactionAction("AgentBonusTran", "showParticipantProdCriteriaAssoc", "participantProdCriteriaAssocDialog");
        }
    }

    function editForValues()
    {
        var message = "";

        if (f.agentNumber.value == "")
        {
            message = "Please Select An Agent";
        }
        else if (f.produceCheckInd.value == "")
        {
            message = "Please Select A Produce Check Value";
        }

        return message;
    }

    function addPremiumLevel()
    {
        if (isParticipatingAgentSelected())
        {
            sendTransactionAction("AgentBonusTran", "clearParticipantPremiumLevel", "_self");
        }
    }

    function savePremiumLevel()
    {
        if (isParticipatingAgentSelected())
        {
            sendTransactionAction("AgentBonusTran", "saveParticipantPremiumLevel", "_self");
        }
    }

    function cancelPremiumLevel()
    {
        if (isParticipatingAgentSelected())
        {
            sendTransactionAction("AgentBonusTran", "clearParticipantPremiumLevel", "_self");
        }
    }

    function deletePremiumLevel()
    {
        if (isParticipatingAgentSelected())
        {
            if (isPremiumLevelSelected())
            {
                sendTransactionAction("AgentBonusTran", "deleteParticipantPremiumLevel", "_self");
            }
        }
    }

    function isParticipatingAgentSelected()
    {
        if (f.participatingAgentPK.value == 0 || f.participatingAgentPK.value == null)
        {
            alert('Please select Participating Agent');
            return false;
        }
        else
        {
            return true;
        }
    }

    function isPremiumLevelSelected()
    {
        if (f.premiumLevelPK.value == 0)
        {
            alert('Please Select Premium Level');
            return false;
        }

        return true;
    }

    /**
     * If the override check box is checked, all related fields are made editable, otherwise uneditable.
     */
    function setOverrideFieldsState()
    {
        var checkState = false;

        var overrideCheckBox = document.getElementById("bonusProgramOverrideIndCheckBox");

        var premiumLevelFieldSet = document.getElementById("premiumLevelFieldSet");

        if (overrideCheckBox.checked == true)
        {
            checkState = true;

            premiumLevelFieldSet.style.borderColor = "black";
        }
        else
        {
            premiumLevelFieldSet.style.borderColor = "gray";
        }

        f.produceCheckInd.contentEditable = checkState;
        f.issuePremiumLevel.contentEditable = checkState;
        f.productLevelIncreasePercent.contentEditable = checkState;
        f.productLevelIncreaseAmount.contentEditable = checkState;
        f.increaseStopAmount.contentEditable = checkState;
        f.premiumLevelAdd.disabled = !checkState;
        f.premiumLevelSave.disabled = !checkState;
        f.premiumLevelCancel.disabled = !checkState;
        f.premiumLevelDelete.disabled = !checkState;
    }

    /**
      * Compare the state of Override Check Box with Stored value and Set Field States.
      */
    function verifyCheckBoxStateAndFieldValue()
    {
        var overrideCheckBox = document.getElementById("bonusProgramOverrideIndCheckBox");

        if (isParticipatingAgentSelected())
        {
            if (bonusProgramOverrideInd == "N")
            {
                if (overrideCheckBox.checked == true)
                {
                    alert("Please Save the Participant Agent Before adding Premium Level Information");
                }
            }
            else if (bonusProgramOverrideInd == "Y")
            {
                if (overrideCheckBox.checked == false)
                {
                    alert("Please Delete the Premium Level Information (if exists) Before Saving Participant Agent");
                }
            }

            setOverrideFieldsState();
        }
    }
</script>
<%-- ****************************** END JavaScript ****************************** --%>

<style>
    .borderbottom
    {
        border-bottom-style:solid;
        border-bottom-width:1px;
        border-bottom-color:gray;
    }
</style>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">

<%--    BEGIN Form Content --%>
  <tr>
    <td class="borderbottom" colspan="6" nowrap align="center">
      <font face="" color="#30548E">Contract Code:<i><%= CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("CONTRACTCODE", contractCodeCT) %></i>&nbsp;&nbsp;&nbsp;Commission Level:<i><%= CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("COMMISSIONLEVEL", commissionLevelCT) %></i>&nbsp;&nbsp;&nbsp;Bonus Name:<i><%= bonusName%></i></font>
    </td>
  </tr>

  <tr>
    <td>&nbsp;</td>
    <td align="right" nowrap>Agent #:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="agentNumber" size="15" maxlength="20" value="<%= agentNumber %>" CONTENTEDITABLE="false">
    </td>
    <td align="right" nowrap>Agent Name:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="agentName" size="25" maxlength="20" value="<%= agentName %>" CONTENTEDITABLE="false">
    </td>
    <td>&nbsp;</td>
  </tr>

  <tr>
    <td>&nbsp;</td>
    <td align="right" nowrap>Situation:&nbsp;</td>
    <td align="left" nowrap>
      <input type="text" name="situation" size="15" maxlength="20" value="<%= situation %>" CONTENTEDITABLE="false">
    </td>
    <td align="right" nowrap>
      <span class="requiredField">*</span>&nbsp;Produce Check:&nbsp;
    </td>
    <td align="left" nowrap>
      <select name="produceCheckInd">
        <option value="">Please Select</option>
        <%
            if (yesNoCTs != null)
            {
                for(int i = 0; i < yesNoCTs.length; i++)
                {
                    String currentCodeDesc    = yesNoCTs[i].getCodeDesc();
                    String currentCode        = yesNoCTs[i].getCode();

                    if (currentCode.equalsIgnoreCase(produceCheckInd))
                    {
                        out.println("<option selected name=\"id\" value=\"" + currentCode + "\">" + currentCodeDesc + "</option>");
                    }
                    else
                    {
                        out.println("<option name=\"id\" value=\"" + currentCode + "\">" + currentCodeDesc + "</option>");
                    }
                }
            }
        %>
      </select>
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td align="left">
      Use Overrides:&nbsp;
      <input type="checkbox" id="bonusProgramOverrideIndCheckBox" name="bonusProgramOverrideIndCheckBox" onClick="verifyCheckBoxStateAndFieldValue()">
    </td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td colspan="4">
      <div id="premiumLevelInfoDiv" style="border-style:none; border-width:0;  position:relative; width:100%; height:100%; top:0; left:0;">
      <fieldset id="premiumLevelFieldSet" style="border-style:solid; border-width:1px; border-color:gray">
      <legend align="top"><font color="black">Premium Level Information</font></legend>
      <table id="overridesTable" width="100%" border="0" cellspacing="0" cellpadding="5">
        <tr>
          <td align="right" nowrap width="25%">Issue Premium Level:&nbsp;</td>
          <td align="left" nowrap width="25%">
            <input type="text" name="issuePremiumLevel" size="12" maxlength="20" value="<%= issuePremiumLevel %>" CURRENCY>
          </td>
          <td align="right" nowrap width="25%">Production Level Increase %:&nbsp;</td>
          <td align="left" nowrap width="25%">
            <input type="text" name="productLevelIncreasePercent" size="13" maxlength="10" value="<%= productLevelIncreasePercent %>">
          </td>
        </tr>
        <tr>
          <td align="right" nowrap width="25%">Production Level Increase Amount:&nbsp;</td>
          <td align="left" nowrap width="25%">
            <input type="text" name="productLevelIncreaseAmount" size="20" maxlength="20" value="<%= productLevelIncreaseAmount %>" CURRENCY>
          </td>
          <td align="right" nowrap width="25%">Increase Stop Amount:&nbsp;</td>
          <td align="left" nowrap width="25%">
            <input type="text" name="increaseStopAmount" size="20" maxlength="20" value="<%= increaseStopAmount %>" CURRENCY>
          </td>
        </tr>
        <tr>
          <td nowrap colspan="4">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr valign="top">
                <td nowrap align="left">
                  <input type="button" id="btnPremiumLevelAdd" name="premiumLevelAdd" value="Add" onClick="addPremiumLevel()">
                  <input type="button" id="btnPremiumLevelSave" name="premiumLevelSave" value="Save" onClick="savePremiumLevel()">
                  <input type="button" id="btnPremiumLevelCancel" name="premiumLevelCancel" value="Cancel" onClick="cancelPremiumLevel()">
                  <input type="button" id="btnPremiumLevelDelete" name="premiumLevelDelete" value="Delete" onClick="deletePremiumLevel()">
                </td>
                <td nowrap align="right">
                    <a id="associateProductsCriteriaLink" href="javascript:showParticipantProdCriteriaAssoc()">Associate Products/Criteria</a>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr height="60%">
          <td nowrap colspan="4">
                        <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr class="heading">
                   <td width="25%" nowrap>
                       Premium Level
                   </td>
                   <td width="25%" nowrap>
                       Prod Level Increae Pct
                   </td>
                   <td width="25%" nowrap>
                       Prod Level Increase Amt
                   </td>
                   <td width="25%" nowrap>
                       Increase Stop Amount
                   </td>
               </tr>
           </table>

           <%--Summary--%>
           <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:80%; top:0; left:0;">
               <table id="participantPremiumLevelSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
           <%
                if (participatingAgent != null)
                {
                    PremiumLevel[] premiumLevels =  (PremiumLevel[]) participatingAgent.getPremiumLevels().toArray((new PremiumLevel[participatingAgent.getPremiumLevels().size()])); 
    
                    premiumLevels = (PremiumLevel[]) Util.sortObjects(premiumLevels, new String[] {"getPremiumLevelPK"});
    
                    for (int i = 0; i < premiumLevels.length; i++)
                    {
                       long currentPremiumLevelPK = premiumLevels[i].getPremiumLevelPK().longValue(); 
    
                       String currentIssuePremiumLevel = premiumLevels[i].getIssuePremiumLevel().toString(); 
    
                       String currentProdLevelIncreasePct = premiumLevels[i].getProductLevelIncreasePercent().toString(); 
    
                       String currentProdLevelIncreaseAmt = premiumLevels[i].getProductLevelIncreaseAmount().toString();
    
                       String currentIncreaseStopAmount = premiumLevels[i].getIncreaseStopAmount().toString();
    
                       boolean isSelected = false;
    
                       String className = "default";
    
                       if (currentPremiumLevelPK == premiumLevelPK)
                       {
                           isSelected = true;
    
                           className = "highlighted";
                       }
                       else
                       {
                           className = "default";
                       }
           %>
                   <tr class="<%= className %>" id="<%= currentPremiumLevelPK %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false);showParticipantPremiumLevelDetail()">
                       <td width="25%" nowrap>
                           <%= "<script>document.write(formatAsCurrency(" + currentIssuePremiumLevel + "))</script>" %>
                       </td>
                       <td width="25%" nowrap>
                           <%= currentProdLevelIncreasePct %>
                       </td>
                      <td width="25%" nowrap>
                           <%= "<script>document.write(formatAsCurrency(" + currentProdLevelIncreaseAmt + "))</script>" %>
                       </td>
                       <td width="25%" nowrap>
                           <%= "<script>document.write(formatAsCurrency(" + currentIncreaseStopAmount + "))</script>" %>
                       </td>
                   </tr>
           <%
                   } // end for
                } // end if
           %>
                   <tr class="filler">
                       <td colspan="4">
                           &nbsp;
                       </td>
                   </tr>
               </table>
           </span>
           <%-- ****************************** END Summary Area ****************************** --%>

          </td>
        </tr>
      </table>
      </fieldset>
      </div>
    </td>
    <td>&nbsp;</td>
  </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<br>

<%-- ****************************** BEGIN Summary Area ****************************** --%>
<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="left" width="33%">
      <input type="button" value=" Add  " onClick="showAgentBonusSearchDialog()">
      <input type="button" value=" Save " onClick="saveParticipatingAgent()">
      <input type="button" value="Cancel" onClick="cancelParticipatingAgent()">
      <input type="button" value="Delete" onClick="deleteParticipatingAgent()">
    </td>
    <td align="left" width="33%">
      <span class="tableHeading">Participating Agents</span>
    </td>
    <td align="right" width="33%">
        &nbsp;
    </td>
  </tr>
</table>

<%-- Column Headings --%>
<table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr class="heading">
    <td width="25%" nowrap>Agent Number</td>
    <td width="25%" nowrap>Agent Name</td>
    <td width="25%" nowrap>Situation</td>
    <td width="25%" nowrap>Stop Date</td>
  </tr>
</table>

<%--Summary--%>
<span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:25%; top:0; left:0;">
  <table id="participatingAgentSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <%
      for (int i = 0; i < participatingAgents.length; i++)
      {
          ParticipatingAgent currentParticipatingAgent = participatingAgents[i];

          long currentParticipatingAgentPK = currentParticipatingAgent.getParticipatingAgentPK().longValue();

          Long currentPlacedAgentFK = currentParticipatingAgent.getPlacedAgent().getPlacedAgentPK();

          PlacedAgent currentPlacedAgent = PlacedAgent.findByPK(currentPlacedAgentFK);

          String currentAgentNumber = currentPlacedAgent.getAgentContract().getAgent().getAgentNumber();

          String currentAgentName = currentPlacedAgent.getClientRole().getClientDetail().getName();

          String currentSituation = Util.initString(currentPlacedAgent.getSituationCode(), "&nbsp;");

          String currentStopDate = Util.initString(currentPlacedAgent.getStopDate() == null ? null : currentPlacedAgent.getStopDate().getFormattedDate(), "&nbsp;");

          boolean isSelected = false;

          boolean isAssociated = false; // (e.g. CompanyStructure to Foo associations)

          String className = null;

          if (currentParticipatingAgentPK == participatingAgentPK)
          {
              isSelected = true;

              className = "highlighted";
          }
          else
          {
              className = "default";
          }
  %>
    <tr class="<%= className %>" id="<%= currentParticipatingAgentPK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false);showParticipatingAgentDetail()">
      <td width="25%" nowrap>
        <%= currentAgentNumber %>
      </td>
      <td width="25%" nowrap>
        <%= currentAgentName %>
      </td>
      <td width="25%" nowrap>
        <%= currentSituation %>
      </td>
      <td width="25%" nowrap>
        <%= currentStopDate %>
      </td>
    </tr>
  <%
      }// end while
  %>
    <tr class="filler">
      <td colspan="4">&nbsp;</td>
    </tr>
  </table>
</span>
<%-- ****************************** END Summary Area ****************************** --%>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td colspan="2" align="right" nowrap>
      <input type="button" name="close" value="Close" onClick="closeWindow()">
    </td>
  </tr>
</table>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="participatingAgentPK" value="<%= participatingAgentPK %>">
<input type="hidden" name="placedAgentPK" value="<%= placedAgentPK %>">
<input type="hidden" name="premiumLevelPK" value="<%= premiumLevelPK %>">
<input type="hidden" name="bonusProgramOverrideInd">

<input type="hidden" name="bonusTaxableAmount" value="<%= bonusTaxableAmount %>">
<input type="hidden" name="lastCheckAmount" value="<%= lastCheckAmount %>">
<input type="hidden" name="lastStatementDateTime" value="<%= lastStatementDateTime %>">
<input type="hidden" name="lastCheckDateTime" value="<%= lastCheckDateTime %>">

<input:hidden name="bonusProgramPK" attributesText="id=\"bonusProgramPK\""/>
<input:hidden name="contractCodeCT" attributesText="id=\"contractCodeCT\""/>
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>