<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page import="edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.BonusProgramVO,
                 fission.utility.Util,
                 java.math.BigDecimal,
                 agent.BonusProgram,
                 edit.common.EDITDate,
                 edit.common.EDITBigDecimal,
                 agent.PremiumLevel"%>
 <%--
  Created by IntelliJ IDEA.
  User: gfrosti
  Date: Nov 12, 2004
  Time: 3:08:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    CodeTableVO[] contractCodeCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("CONTRACTCODE");

    CodeTableVO[] commissionLevelCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("COMMISSIONLEVEL");

//    CodeTableVO[] bonusNameCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("AGENTBONUSPROGRAM");

    CodeTableVO[] yesNoCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("YESNO");

    CodeTableVO[] frequencyCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("BONUSFREQUENCY");

    BonusProgram[] bonusPrograms = (BonusProgram[]) request.getAttribute("bonusPrograms");

    BonusProgram selectedBonusProgram = (BonusProgram) request.getAttribute("selectedBonusProgram");

    PremiumLevel selectedPremiumLevel = (PremiumLevel) request.getAttribute("selectedPremiumLevel");

    // Populates dropdowns. These are not CodeTable entries as they are never used in scripts.
    String[] allSelect = {"All", "Select"};

    String[] allDirect = {"All", "Direct"};

    long bonusProgramPK = Util.initLong(selectedBonusProgram, "bonusProgramPK", 0L);
    String contractCodeCT = (String) Util.initObject(selectedBonusProgram, "contractCodeCT", "");
    String commissionLevelCT = (String) Util.initObject(selectedBonusProgram, "commissionLevelCT", "");
    String bonusName = (String) Util.initObject(selectedBonusProgram, "bonusName", "");
    String produceCheckInd = (String) Util.initObject(selectedBonusProgram, "produceCheckInd", "");
    String frequencyCT = (String) Util.initObject(selectedBonusProgram, "frequencyCT", "");
    EDITDate bonusStartDate = (EDITDate) Util.initObject(selectedBonusProgram, "bonusStartDate", null);
    EDITDate bonusStopDate = (EDITDate) Util.initObject(selectedBonusProgram, "bonusStopDate", new EDITDate(EDITDate.DEFAULT_MAX_DATE));
    EDITDate applicationReceivedStartDate = (EDITDate) Util.initObject(selectedBonusProgram, "applicationReceivedStartDate", null);
    EDITDate applicationReceivedStopDate = (EDITDate) Util.initObject(selectedBonusProgram, "applicationReceivedStopDate", new EDITDate(EDITDate.DEFAULT_MAX_DATE));
    EDITDate premiumStopDate = (EDITDate) Util.initObject(selectedBonusProgram, "premiumStopDate", new EDITDate(EDITDate.DEFAULT_MAX_DATE));
    EDITDate nextCheckDate = (EDITDate) Util.initObject(selectedBonusProgram, "nextCheckDate", null);
    String includeAdditionalPremiumInd = (String) Util.initObject(selectedBonusProgram, "includeAdditionalPremiumInd", "N");
    String baseProgramCompleteInd = (String) Util.initObject(selectedBonusProgram, "baseProgramCompleteInd", "N");
    String specificHierarchyLevelStatus = (String) Util.initObject(selectedBonusProgram, "specificHierarchyLevelStatus", "");
    String specificCommissionProfStatus = (String) Util.initObject(selectedBonusProgram, "specificCommissionProfStatus", "");

    long premiumLevelPK = Util.initLong(selectedPremiumLevel, "premiumLevelPK", 0L);
    EDITBigDecimal issuePremiumLevel = (EDITBigDecimal) Util.initObject(selectedPremiumLevel, "issuePremiumLevel", new EDITBigDecimal());
    EDITBigDecimal productLevelIncreasePercent = (EDITBigDecimal) Util.initObject(selectedPremiumLevel, "productLevelIncreasePercent", new EDITBigDecimal());
    EDITBigDecimal productLevelIncreaseAmount = (EDITBigDecimal) Util.initObject(selectedPremiumLevel, "productLevelIncreaseAmount", new EDITBigDecimal());
    EDITBigDecimal increaseStopAmount = (EDITBigDecimal) Util.initObject(selectedPremiumLevel, "increaseStopAmount", new EDITBigDecimal());

    String activePremiumLevel = selectedPremiumLevel==null?"No":"Yes";
%>
<%-- ****************************** End Java Code ****************************** --%>

<script>

    var responseMessage = "<%= responseMessage %>";
    var activePremiumLevel = "<%= activePremiumLevel %>";
    var baseProgramCompleteInd = "<%= baseProgramCompleteInd %>";

    function init()
    {
        setCheckBoxState(f.includeAdditionalPremiumIndCheckBox, "<%= includeAdditionalPremiumInd %>");
        setCheckBoxState(f.baseProgramCompleteIndCheckBox, baseProgramCompleteInd);

        setParticipantSelectionState();
    }

    function onTableRowSingleClick(tableId)
    {
        sendTransactionAction("AgentBonusTran", "showAgentBonusPremiumLevelDetail", "_self");
    }

    /**
     * Sets the initial state of the participant drop-downs.
     */
    function setParticipantSelectionState()
    {
        setParticipantSelections(f.specificCommissionProfStatus);
    }

    /**
     * Enables the anchor of the specified Id.
     */
    function enableLink(linkId)
    {
        var anchor = document.getElementById(linkId);

        anchor.disabled = false;
    }

    /**
     * Participants (in general) are filtered by PlacedAgent, Company, and Bonus Level. This function
     * enables/disables the links to add detail to these filters.
     */
    function setParticipantSelections(select)
    {
        var value = select.value;

        var id = select.id;

        if (value == "All")
        {
            document.getElementById(id + "Link").disabled = true;
        }
        else if (value == "Direct")
        {
            document.getElementById(id + "Link").disabled = true;
        }
        else if (value == "Select")
        {
            document.getElementById(id + "Link").disabled = false;
        }
    }

    /**
     *  Opens the participant selection page.
     */
    function showParticipantSelection()
    {
        if (isBonusProgramSelected())
        {
            if (baseProgramCompleteInd == "Y")
            {
                if (activePremiumLevel == "Yes")
                {
                    alert("Please Save Or Cancel Current Premium Level Information Before Advancing To Participant Selection");
                }
                else
                {
                    var width = .95 * screen.width;
                    var height = .85 * screen.height;

                    openDialog("participantSelectionDialog","left=0,top=0,resizable=no", width, height);

                    sendTransactionAction("AgentBonusTran", "showParticipantSelection", "participantSelectionDialog");
                }
            }
            else
            {
                alert("Please Complete the Bonus Program to add Participants");
            }
        }
    }

    /**
     *  Opens the associate products/criteria page.
     */
    function showProductsCriteriaAssociation()
    {
        if (isBonusProgramSelected())
        {
            if (isPremiumLevelSelected())
            {
                var width = .95 * screen.width;
                var height = .75 * screen.height;

                openDialog("productCriteriaAssocDialog","left=0,top=0,resizable=no", width, height);

                sendTransactionAction("AgentBonusTran", "showAgentBonusProdCriteriaAssoc", "productCriteriaAssocDialog");
            }
        }
    }

    function editForValues()
    {
        var message = "";

        if (f.contractCodeCT.value == "")
        {
            message = "Please Select A Contract Code";
        }
        else if (f.commissionLevelCT.value == "")
        {
            message = "Please Select A Commission Level";
        }
        else if (f.bonusName.value == "")
        {
            message = "Please Select A Bonus Name Value";
        }
        else if (f.produceCheckInd.value == "")
        {
            message = "Please Select A Produce Check Value";
        }
        else if (f.frequencyCT.value == "")
        {
            message = "Please Select A Mode";
        }
        else
        {
            try
            {
                validateAndSetBonusProgramValues();
            }
            catch (e)
            {
                message = e;
            }
        }

        return message;
    }

    function validateAndSetBonusProgramValues()
    {
        try
        {
            var bonusStartDate = formatDate(f.bonusStartDateMonth.value, f.bonusStartDateDay.value, f.bonusStartDateYear.value, true);
            var bonusStopDate = formatDate(f.bonusStopDateMonth.value, f.bonusStopDateDay.value, f.bonusStopDateYear.value, false);
            var applicationReceivedStopDate = formatDate(f.applicationReceivedStopDateMonth.value, f.applicationReceivedStopDateDay.value, f.applicationReceivedStopDateYear.value, false);
            var applicationReceivedStartDate = formatDate(f.applicationReceivedStartDateMonth.value, f.applicationReceivedStartDateDay.value, f.applicationReceivedStartDateYear.value, false);
            var premiumStopDate = formatDate(f.premiumStopDateMonth.value, f.premiumStopDateDay.value, f.premiumStopDateYear.value, false);
            var nextCheckDate = formatDate(f.nextCheckDateMonth.value, f.nextCheckDateDay.value, f.nextCheckDateYear.value, false);

            setCheckBoxValue(f.includeAdditionalPremiumIndCheckBox, f.includeAdditionalPremiumInd);
            setCheckBoxValue(f.baseProgramCompleteIndCheckBox, f.baseProgramCompleteInd);

            f.bonusStartDate.value = bonusStartDate;
            f.bonusStopDate.value = bonusStopDate;
            f.applicationReceivedStopDate.value = applicationReceivedStopDate;
            f.applicationReceivedStartDate.value = applicationReceivedStartDate;
            f.nextCheckDate.value = nextCheckDate;
            f.premiumStopDate.value = premiumStopDate;
        }
        catch (e)
        {
            throw e;
        }
    }

    /**
     *  Opens the level selection page.
     */
    function showCommissionProfileSelection()
    {
        if (isBonusProgramSelected())
        {
            var shouldSaveChanges = checkForFormChanges();

            if (!shouldSaveChanges)
            {
                var commissionProfileSelectionLink = document.all.commissionProfileSelectionLink;

                if (commissionProfileSelectionLink.disabled == false)
                {
                    var width = .95 * screen.width;
                    var height = .75 * screen.height;

                    openDialog("commProfileSelectionDialog","left=0,top=0,resizable=no", width, height);

                    sendTransactionAction("AgentBonusTran", "showCommissionProfileSelection", "commProfileSelectionDialog");
                }
            }
        }
    }

    /**
     * Resets the screen and prepares for the addition of a new Premium Level.
     */
    function addPremiumLevel()
    {
        if (isBonusProgramSelected())
        {
            sendTransactionAction("AgentBonusTran", "addAgentBonusPremiumLevel", "_self");
        }
    }

    /**
     * Saves the selected Premium Level adds to Corresponding Bonus Program Selected.
     */
    function savePremiumLevel()
    {
        if (isBonusProgramSelected())
        {
            sendTransactionAction("AgentBonusTran", "saveAgentBonusPremiumLevel", "_self");
        }
    }

    /**
     * Clears the Premium Level selection.
     */
    function cancelPremiumLevel()
    {
        if (isBonusProgramSelected())
        {
            sendTransactionAction("AgentBonusTran", "clearAgentBonusPremiumLevel", "_self");
        }
    }

    /**
     * Deletes the selected Premium Level.
     */
    function deletePremiumLevel()
    {
        if (isBonusProgramSelected())
        {
            if (isPremiumLevelSelected())
            {
                sendTransactionAction("AgentBonusTran", "deleteAgentBonusPremiumLevel", "_self");
            }
        }
    }

    /**
     * Resets the screen and prepares for the addition of a new Bonus Program.
     */
    function addBonusProgram()
    {
        sendTransactionAction("AgentBonusTran", "addBonusProgram", "_self");
    }

    /**
     * Saves the user-entered information
     */
    function saveBonusProgram()
    {
        var message = editForValues();
        var isFormReadyToSubmit = true;

        if (message != "")
        {
            isFormReadyToSubmit = false;
            alert(message);
        }
        else
        {
            if (f.frequencyCT.value != "OneTime")
            {
                if (!valueIsEmpty(f.applicationReceivedStartDateMonth.value) ||
                    !valueIsEmpty(f.applicationReceivedStartDateDay.value) ||
                    !valueIsEmpty(f.applicationReceivedStartDateYear.value))
                {
                    isFormReadyToSubmit = false;
                    alert("Application Received Start Date Cannot Be Entered For Selected Mode");
                }
            }
            else
            {
                if (valueIsEmpty(f.nextCheckDateMonth.value) ||
                    valueIsEmpty(f.nextCheckDateDay.value) ||
                    valueIsEmpty(f.nextCheckDateYear.value))
                {
                    isFormReadyToSubmit = false;
                    alert("Next Check Date Is Required For Selected Mode");
                }
            }
        }

        if (isFormReadyToSubmit)
        {
            disableActionButtons();
            sendTransactionAction("AgentBonusTran", "saveBonusProgram", "_self");
        }
    }

    /**
     * Displays the detail of the selected Bonus Program.
     */
    function showBonusProgramDetail()
    {
        var selectedRowId = getSelectedRowId("bonusProgramSummary");

        f.bonusProgramPK.value = selectedRowId;

        sendTransactionAction("AgentBonusTran", "showBonusProgramDetail", "_self");
    }

    /**
     * Cancels Bonus Program edits.
     */
    function cancelBonusProgram()
    {
        sendTransactionAction("AgentBonusTran", "cancelBonusProgram", "_self");
    }

    /**
     * Deletes the selected Bonus Program.
     */
    function deleteBonusProgram()
    {
        if (isBonusProgramSelected())
        {
            var shouldDelete = window.confirm("Deleting A Bonus Program Will Delete All Associations With Participating Agents (Not The Agent).");

            if (shouldDelete)
            {
                disableActionButtons();

                sendTransactionAction("AgentBonusTran", "deleteBonusProgram", "_self");
            }
        }
    }

    /**
     * While a tranaction is being saved, we want to disable all buttons to prevent double-saves.
     */
    function disableActionButtons()
    {
        document.all.bonusMessage.innerHTML = "<font color='blue' face=''>Processing...<font>";

        document.all.btnPremLevelAdd.style.backgroundColor = "#99BBBB";
        document.all.btnPremLevelSave.style.backgroundColor = "#99BBBB";
        document.all.btnPremLevelCancel.style.backgroundColor = "#99BBBB";
        document.all.btnPremLevelDelete.style.backgroundColor = "#99BBBB";
        document.all.btnAdd.style.backgroundColor = "#99BBBB";
        document.all.btnSave.style.backgroundColor = "#99BBBB";
        document.all.btnCancel.style.backgroundColor = "#99BBBB";
        document.all.btnDelete.style.backgroundColor = "#99BBBB";

        document.all.btnPremLevelAdd.disabled = true;
        document.all.btnPremLevelSave.disabled = true;
        document.all.btnPremLevelCancel.disabled = true;
        document.all.btnPremLevelDelete.disabled = true;
        document.all.btnAdd.disabled = true;
        document.all.btnSave.disabled = true;
        document.all.btnCancel.disabled = true;
        document.all.btnDelete.disabled = true;
    }

    function isBonusProgramSelected()
    {
        if (f.bonusProgramPK.value == 0)
        {
            alert('Please Select Bonus Program');
            return false;
        }

        return true;
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
</script>

<style>
    .borderbottom
    {
        border-bottom-style:solid;
        border-bottom-width:1px;
        border-bottom-color:gray;
    }
</style>
<%-- ****************************** END JavaScript ****************************** --%>

<%-- ****************************** BEGIN Form Data ****************************** --%>

<table class="formdata" width="100%" height="66%" valign="top" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
        <table width="100%" valign="top" border="0" cellspacing="0" cellpadding="0">

          <%--Section One--%>
          <tr>
            <td  class="borderbottom" width="100%">
              <table width="100%" border="0" cellspacing="0" cellpadding="5">
                <tr>
                  <td align="right" nowrap width="40%">
                    <span class="requiredField">*</span>&nbsp;Contract Code:&nbsp;
                  </td>
                  <td align="left" nowrap width="10%">
                    <select name="contractCodeCT">
                      <option value="">Please Select</option>
                      <%
                        if (contractCodeCTs != null)
                        {
                            for(int i = 0; i < contractCodeCTs.length; i++)
                            {
                                String currentCodeDesc    = contractCodeCTs[i].getCodeDesc();
                                String currentCode        = contractCodeCTs[i].getCode();

                                if (currentCode.equalsIgnoreCase(contractCodeCT))
                                {
                                    out.println("<option selected name=\"id\" value=\"" + currentCode+ "\">" + currentCodeDesc + "</option>");
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
                  <td align="right" nowrap width="10%">
                    <span class="requiredField">*</span>&nbsp;Commission Level:&nbsp;
                  </td>
                  <td align="left" nowrap width="40%">
                    <select name="commissionLevelCT">
                      <option value="">Please Select</option>
                      <%
                        if (commissionLevelCTs != null)
                        {
                            for(int i = 0; i < commissionLevelCTs.length; i++)
                            {
                                String currentCodeDesc    = commissionLevelCTs[i].getCodeDesc();
                                String currentCode        = commissionLevelCTs[i].getCode();

                                if (currentCode.equalsIgnoreCase(commissionLevelCT))
                                {
                                    out.println("<option selected name=\"id\" value=\"" + currentCode+ "\">" + currentCodeDesc + "</option>");
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
              </table>
            </td>
          </tr>

          <%--Section Two    --%>
          <tr>
            <td>
              <table width="100%" border="0" cellspacing="0" cellpadding="5">
                <tr>
                  <td align="right" nowrap>
                    <span class="requiredField">*</span>&nbsp;Bonus Name:&nbsp;
                  </td>
                  <td align="left" nowrap>
                  <input type="text" name="bonusName" size="50" maxlength="75" value="<%= bonusName %>">
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
                                    out.println("<option selected name=\"id\" value=\"" + currentCode+ "\">" + currentCodeDesc + "</option>");
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
                  <td align="right" nowrap>
                    <span class="requiredField">*</span>&nbsp;Mode:&nbsp;
                  </td>
                  <td align="left" nowrap>
                    <select name="frequencyCT">
                      <option value="">Please Select</option>
                      <%
                        if (frequencyCTs != null)
                        {
                            for(int i = 0; i < frequencyCTs.length; i++)
                            {
                                String currentCodeDesc    = frequencyCTs[i].getCodeDesc();
                                String currentCode        = frequencyCTs[i].getCode();

                                if (currentCode.equalsIgnoreCase(frequencyCT))
                                {
                                    out.println("<option selected name=\"id\" value=\"" + currentCode+ "\">" + currentCodeDesc + "</option>");
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
                  <td align="right" nowrap>
                    <span class="requiredField">*</span>&nbsp;Bonus Start Date:&nbsp;
                  </td>
                  <td align="left" nowrap>
                    <input type="text" name="bonusStartDateMonth" size="2" maxlength="2" value="<%= bonusStartDate==null?"":bonusStartDate.getFormattedMonth()%>"> /
                    <input type="text" name="bonusStartDateDay" size="2" maxlength="2" value="<%= bonusStartDate==null?"":bonusStartDate.getFormattedDay()  %>"> /
                    <input type="text" name="bonusStartDateYear" size="4" maxlength="4" value="<%= bonusStartDate==null?"":bonusStartDate.getFormattedYear() %>">
                  </td>
                  <td align="right" nowrap>Bonus Stop Date:&nbsp;</td>
                  <td align="left" nowrap>
                    <input type="text" name="bonusStopDateMonth" size="2" maxlength="2" value="<%= bonusStopDate==null?"":bonusStopDate.getFormattedMonth() %>"> /
                    <input type="text" name="bonusStopDateDay" size="2" maxlength="2" value="<%= bonusStopDate==null?"":bonusStopDate.getFormattedDay() %>"> /
                    <input type="text" name="bonusStopDateYear" size="4" maxlength="4" value="<%= bonusStopDate==null?"":bonusStopDate.getFormattedYear() %>">
                  </td>
                  <td align="right" nowrap>Premium Stop Date:&nbsp;</td>
                  <td align="left" nowrap>
                    <input type="text" name="premiumStopDateMonth" size="2" maxlength="2" value="<%= premiumStopDate==null?"":premiumStopDate.getFormattedMonth() %>"> /
                    <input type="text" name="premiumStopDateDay" size="2" maxlength="2" value="<%= premiumStopDate==null?"":premiumStopDate.getFormattedDay() %>"> /
                    <input type="text" name="premiumStopDateYear" size="4" maxlength="4" value="<%= premiumStopDate==null?"":premiumStopDate.getFormattedYear() %>">
                  </td>
                </tr>
                <tr>
                  <td align="right" nowrap>App Rcvd Start Date:&nbsp;</td>
                  <td align="left" nowrap>
                    <input type="text" name="applicationReceivedStartDateMonth" size="2" maxlength="2" value="<%= applicationReceivedStartDate==null?"":applicationReceivedStartDate.getFormattedMonth() %>"> /
                    <input type="text" name="applicationReceivedStartDateDay" size="2" maxlength="2" value="<%= applicationReceivedStartDate==null?"":applicationReceivedStartDate.getFormattedDay() %>"> /
                    <input type="text" name="applicationReceivedStartDateYear" size="4" maxlength="4" value="<%= applicationReceivedStartDate==null?"":applicationReceivedStartDate.getFormattedYear() %>">
                  </td>
                  <td align="right" nowrap>App Rcvd Stop Date:&nbsp;</td>
                  <td align="left" nowrap>
                    <input type="text" name="applicationReceivedStopDateMonth" size="2" maxlength="2" value="<%= applicationReceivedStopDate==null?"":applicationReceivedStopDate.getFormattedMonth() %>"> /
                    <input type="text" name="applicationReceivedStopDateDay" size="2" maxlength="2" value="<%= applicationReceivedStopDate==null?"":applicationReceivedStopDate.getFormattedDay() %>"> /
                    <input type="text" name="applicationReceivedStopDateYear" size="4" maxlength="4" value="<%= applicationReceivedStopDate==null?"":applicationReceivedStopDate.getFormattedYear() %>">
                  </td>
                  <td align="right" nowrap>Next Check Date:&nbsp;</td>
                  <td align="left" nowrap>
                    <input type="text" name="nextCheckDateMonth" size="2" maxlength="2" value="<%= nextCheckDate==null?"":nextCheckDate.getFormattedMonth() %>"> /
                    <input type="text" name="nextCheckDateDay" size="2" maxlength="2" value="<%= nextCheckDate==null?"":nextCheckDate.getFormattedDay() %>"> /
                    <input type="text" name="nextCheckDateYear" size="4" maxlength="4" value="<%= nextCheckDate==null?"":nextCheckDate.getFormattedYear() %>">
                  </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td colspan="2" align="right" nowrap>Include Additional Premium:&nbsp;</td>
                  <td align="left"><input type="checkbox" name="includeAdditionalPremiumIndCheckBox"></td>
                  <td align="right" nowrap>Base Program Complete:&nbsp;</td>
                  <td><input type="checkbox" name="baseProgramCompleteIndCheckBox"></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
    </td>
  </tr>

  <%--Section Three    --%>
  <tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="5">
        <tr>
          <td>
            <fieldset style="border-style:solid; border-width:1px; border-color:gray">
            <legend align="top"><font color="black">Premium Level Information</font></legend>
            <table width="100%" border="0" cellspacing="0" cellpadding="5">
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
                <td colspan="4">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr valign="top">
                      <td nowrap align="left">
                        <input type="button" id="btnPremLevelAdd" name="add" value="Add" onClick="addPremiumLevel()">
                        <input type="button" id="btnPremLevelSave" name="save" value="Save" onClick="savePremiumLevel()">
                        <input type="button" id="btnPremLevelCancel" name="cancel" value="Cancel" onClick="cancelPremiumLevel()">
                        <input type="button" id="btnPremLevelDelete" name="delete" value="Delete" onClick="deletePremiumLevel()">
                      </td>
                      <td nowrap align="right">
                        <a id="associateProductsCriteriaLink" href="javascript:showProductsCriteriaAssociation()">Associate Products/Criteria</a>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr height="60%">
                <td colspan="4">
                  <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
                    <jsp:param name="tableId" value="AgentBonusPremiumLevelTableModel"/>
                    <jsp:param name="tableHeight" value="90"/>
                    <jsp:param name="multipleRowSelect" value="false"/>
                    <jsp:param name="singleOrDoubleClick" value="single"/>
                  </jsp:include>
                </td>
              </tr>
            </table>
            </fieldset>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td valign="top">
      <table width="100%" valign="top" border="0" cellspacing="0" cellpadding="5">
        <tr>
        <td nowrap width="33%" align="left">
          <a id="participantSelectionLink" href="javascript:showParticipantSelection()">Participants</a>
        </td>
        <td nowrap width="34%" align="center">
          Hierarchy Levels:&nbsp;
          <select id="hierarchyLevelSelection" name="specificHierarchyLevelStatus">
          <%
                for(int i = 0; i < allDirect.length; i++)
                {
                    String currentValue = allDirect[i];

                    if (currentValue.equalsIgnoreCase(specificHierarchyLevelStatus))
                    {
                        out.println("<option selected name=\"id\" value=\"" + currentValue+ "\">" + currentValue + "</option>");
                    }
                    else
                    {
                        out.println("<option name=\"id\" value=\"" + currentValue + "\">" + currentValue + "</option>");
                    }
                }
          %>
          </select>
        </td>
        <td nowrap width="33%" align="right">
          Commission Levels:&nbsp;
          <select id="commissionProfileSelection" name="specificCommissionProfStatus" onChange="setParticipantSelections(this)">
          <%
                for(int i = 0; i < allSelect.length; i++)
                {
                    String currentValue = allSelect[i];

                    if (currentValue.equalsIgnoreCase(specificCommissionProfStatus))
                    {
                        out.println("<option selected name=\"id\" value=\"" + currentValue+ "\">" + currentValue + "</option>");
                    }
                    else
                    {
                        out.println("<option name=\"id\" value=\"" + currentValue + "\">" + currentValue + "</option>");
                    }
                }
          %>
          </select>
          <a id="commissionProfileSelectionLink" href="javascript:showCommissionProfileSelection()" disabled>(select)</a>&nbsp;
        </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <span class="requiredField">&nbsp;&nbsp;* = required </span>
    </td>
  </tr>
</table>


<%-- ****************************** END Form Data ****************************** --%>
<br>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<%--Buttons--%>
<table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" width="33%">
            <input type="button" id="btnAdd" value=" Add  " onClick="addBonusProgram()">
            <input type="button" id="btnSave" value=" Save " onClick="saveBonusProgram()">
            <input type="button" id="btnCancel" value="Cancel" onClick="cancelBonusProgram()">
            <input type="button" id="btnDelete" value="Delete" onClick="deleteBonusProgram()">
        </td>
        <td width="33%" id="bonusMessage" align="center">
            <span class="tableHeading">Program Summary</span>
        </td>
        <td align="right" width="33%">
            &nbsp;
        </td>
    </tr>
</table>

<div class="scrollTable" style="padding:0px; margin:0px; border-style: solid; border-width:0; position:relative; width:100%; height:25%; top:0; left:0;">
    <%-- Column Headings --%>
    <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr class="heading">
            <td width="20%" nowrap>
                Bonus Name
            </td>
            <td width="20%" nowrap>
                Contract Code
            </td>
            <td width="20%" nowrap>
                Commission Level
            </td>
            <td width="20%" nowrap>
                Bonus Start Date
            </td>
            <td width="20%" nowrap>
                Bonus Stop Date
            </td>
        </tr>
    </table>

    <%--Summary--%>
    <span class="scrollableContent" style="border-style:solid; border-width:1; background:#BBBBBB; position:relative; width:100%; height:75%; top:0; left:0;">
        <table id="bonusProgramSummary" class="summary" style="width:100%; border-width:0" cellspacing="0" cellpadding="0">
    <%
        if (bonusPrograms != null) // Test for the existence of the target VOs.
        {
            for (int i = 0; i < bonusPrograms.length; i++) // Loop through the target VOs.
            {
                BonusProgram currentBonusProgram = bonusPrograms[i];

                long currentBonusProgramPK = currentBonusProgram.getBonusProgramPK().longValue();

                String currentBonusName = Util.initString(currentBonusProgram.getBonusName(), "&nbsp;");

                String currentContractCodeCT = Util.initString(currentBonusProgram.getContractCodeCT(), "&nbsp");

                String currentCommissionLevelCT = Util.initString(currentBonusProgram.getCommissionLevelCT(), "&nbsp;");

                String currentBonusStartDate = "";
                EDITDate edCurrentBonusStartDate = currentBonusProgram.getBonusStartDate();
                if (edCurrentBonusStartDate != null)
                {
                    currentBonusStartDate = edCurrentBonusStartDate.getFormattedDate();
                }

                String currentBonusStopDate = "";
                EDITDate edCurrentBonusStopDate = currentBonusProgram.getBonusStopDate();
                if (edCurrentBonusStopDate != null)
                {
                    currentBonusStopDate = edCurrentBonusStopDate.getFormattedDate();
                }

                boolean isSelected = false;

                boolean isAssociated = false; // (e.g. CompanyStructure to Foo associations)

                String className = null;

                if (currentBonusProgramPK == bonusProgramPK)
                {
                    isSelected = true;

                    className = "highlighted";
                }
                else
                {
                    className = "default";
                }
    %>
            <tr class="<%= className %>" id="<%= currentBonusProgramPK %>" isAssociated="<%= isAssociated %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(false);showBonusProgramDetail()">
                <td width="20%" nowrap>
                    <%= currentBonusName %>
                </td>
                        <td width="20%" nowrap>
                    <%= CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("CONTRACTCODE", currentContractCodeCT) %>
                </td>
                <td width="20%" nowrap>
                    <%= CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("COMMISSIONLEVEL", currentCommissionLevelCT) %>
                </td>
                <td width="20%" nowrap>
                    <%= currentBonusStartDate %>
                </td>
                <td width="20%" nowrap>
                    <%= currentBonusStopDate %>
                </td>
            </tr>
    <%
            }// end for
        } // end if
    %>
        </table>
     </span>
    </div>


<%-- ****************************** END Summary Area ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="bonusProgramPK" value="<%= bonusProgramPK %>">
<input type="hidden" name="activeContractCodeCT" value="<%= contractCodeCT %>">
<input type="hidden" name="premiumLevelPK" value="<%= premiumLevelPK %>">
<input type="hidden" name="bonusStartDate">
<input type="hidden" name="bonusStopDate">
<input type="hidden" name="applicationReceivedStopDate">
<input type="hidden" name="applicationReceivedStartDate">
<input type="hidden" name="nextCheckDate">
<input type="hidden" name="premiumStopDate">
<input type="hidden" name="includeAdditionalPremiumInd">
<input type="hidden" name="baseProgramCompleteInd">

<%-- ****************************** END Hidden Variables ****************************** --%>
