<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.utility.Util,
                 edit.common.CodeTableWrapper,
                 security.*, 
                 java.util.*"%>

<%
    //  Get the codetable listings for the pull downs
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] maskRestrictionCTs = codeTableWrapper.getCodeTableEntries("MASKRESTRICTION");

    //  Get the security profile and its masks
    SecurityProfile securityProfile = (SecurityProfile) request.getAttribute("securityProfile");

    String securityProfilePK = null;

    String usernameMaskPK = "";
    String passwordMaskPK = "";
    String passwordMaskVOPK = "";

    //  Initialize the field data
    String usernameMinLength = "";
    String usernameMaxLength = "";
    String usernameMixedCaseCT    = "";
    String usernameAlphaNumCT     = "";
    String usernameSpecialCharsCT = "";

    String passwordMinLength = "";
    String passwordMaxLength = "";
    String passwordMixedCaseCT    = "";
    String passwordAlphaNumCT     = "";
    String passwordSpecialCharsCT = "";
    String passwordExpiration = "";
    String passwordRepeatCycle = "";
    String sessionExpiration = "";
    String maxLoginAttempts = "";
    boolean passwordNoRepeatsStatus = false;

    Set<Mask> masks = null;
    Mask operatorMask = null;
    Mask pMask = null;
    PasswordMask passwordMask = null;

    if (securityProfile != null)
    {
        securityProfilePK = securityProfile.getSecurityProfilePK().toString();

        sessionExpiration = Integer.toString(securityProfile.getSessionTimeoutInMinutes());
        
        maxLoginAttempts = Integer.toString(securityProfile.getMaxLoginAttempts());

        masks = securityProfile.getMasks();

        for (Mask mask : masks)
        {
            if (mask.getMaskTypeCT().equalsIgnoreCase("Operator"))
            {
                operatorMask = mask;
            }
            else if (mask.getMaskTypeCT().equalsIgnoreCase("Password"))
            {
                pMask = mask;
                passwordMask = (PasswordMask) pMask.getPasswordMasks().iterator().next();
            }
        }
    }

    if (operatorMask != null)
    {
        usernameMaskPK = operatorMask.getMaskPK() + "";
        usernameMinLength = Integer.toString(operatorMask.getMinLength());
        usernameMaxLength = Integer.toString(operatorMask.getMaxLength());
        usernameMixedCaseCT = Util.initString(operatorMask.getMixedCaseCT(), "");
        usernameAlphaNumCT = Util.initString(operatorMask.getAlphaNumericCT(), "");
        usernameSpecialCharsCT = Util.initString(operatorMask.getSpecialCharsCT(), "");
    }

    if (pMask != null)
    {
        passwordMaskPK = pMask.getMaskPK() + "";
        passwordMinLength = Integer.toString(pMask.getMinLength());
        passwordMaxLength = Integer.toString(pMask.getMaxLength());
        passwordMixedCaseCT = Util.initString(pMask.getMixedCaseCT(), "");
        passwordAlphaNumCT = Util.initString(pMask.getAlphaNumericCT(), "");
        passwordSpecialCharsCT = Util.initString(pMask.getSpecialCharsCT(), "");
    }

    if (passwordMask != null)
    {
        passwordMaskVOPK = passwordMask.getPasswordMaskPK() + "";
        passwordExpiration = Integer.toString(passwordMask.getExpirationInDays());
        passwordRepeatCycle = Integer.toString(passwordMask.getNumberOfRepeatCycles());
        String passwordRestrictRepeats = passwordMask.getRestrictRepeats();

        if (passwordRestrictRepeats.equalsIgnoreCase("Y"))
        {
            passwordNoRepeatsStatus = true;
        }
    }

    String message = (String) request.getAttribute("message");
    message = (message == null)?"":message;
%>

<html>

    <head>

        <title>EDITSOLUTIONS - Security</title>

        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <meta http-equiv="Cache-Control" content="no-store">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="0">

        <link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

        <script language="javascript1.2">

            var f = null;

            var message = "<%= message %>";

            function init()
            {
                f = document.theForm;

                if (message.length > 0)
                {
                    alert(message);
                }

                f.passwordNoRepeatsInd.checked = <%= passwordNoRepeatsStatus %>;

            }

            function saveProfile()
            {
                sendTransactionAction("SecurityAdminTran", "saveProfile", "_self");
            }

            function sendTransactionAction(transaction, action, target)
            {
                f.transaction.value = transaction;
                f.action.value = action;

                f.target = target;

                f.submit();
            }

            function selectUsernameMixedCase(selector)
            {
                if (selector.selectedIndex >= 1) // Anything other than "Please Select"
                {
                    f.usernameMixedCasePK.value = selector.options[selector.selectedIndex].value;
                }
            }

            function selectUsernameAlphaNum(selector)
            {
                if (selector.selectedIndex >= 1) // Anything other than "Please Select"
                {
                    f.usernameAlphaNumPK.value = selector.options[selector.selectedIndex].value;
                }
            }

            function selectUsernameSpecialChars(selector)
            {
                if (selector.selectedIndex >= 1) // Anything other than "Please Select"
                {
                    f.usernameSpecialCharsPK.value = selector.options[selector.selectedIndex].value;
                }
            }

            function selectPasswordMixedCase(selector)
            {
                if (selector.selectedIndex >= 1) // Anything other than "Please Select"
                {
                    f.passwordMixedCasePK.value = selector.options[selector.selectedIndex].value;
                }
            }

            function selectPasswordAlphaNum(selector)
            {
                if (selector.selectedIndex >= 1) // Anything other than "Please Select"
                {
                    f.passwordAlphaNumPK.value = selector.options[selector.selectedIndex].value;
                }
            }

            function selectPasswordSpecialChars(selector)
            {
                if (selector.selectedIndex >= 1) // Anything other than "Please Select"
                {
                    f.passwordSpecialCharsPK.value = selector.options[selector.selectedIndex].value;
                }
            }

            function selectPasswordNoRepeats(checkbox)
            {
                if (checkbox.status == true)
                {
                    f.passwordNoRepeatsStatus.value = "true";
                    <% 
                    	passwordNoRepeatsStatus = true;
                   	%>

<%--                Enable repeat cycles text field--%>
                    f.passwordRepeatCycle.disabled = false;
                    f.passwordRepeatCycle.style.visible = false;
                }
                else
                {
                    f.passwordNoRepeatsStatus.value = "false";
                    <% passwordNoRepeatsStatus = false;%>

<%--                Disable repeat cycles text field--%>
                    f.passwordRepeatCycle.disabled = true;
                    f.passwordRepeatCycle.style.visible = true;
                }
            }

        </script>

    </head>

    <body class="mainTheme" onLoad="init()">

    <form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

        <table name="outerTable" width="100%" height="10%" border="0" cellspacing="0" cellpadding="0">

            <tr>
                <td width="33%">
                    <span class="tableHeading"> Username Mask</span><br>
                </td>
                <td width="33%">
                    <span class="tableHeading"> Password Mask</span><br>
                </td>
                <td width="33%">
                    <span class="tableHeading"> Session Info</span><br>
                </td>
            </tr>


            <tr>
                <!-- Username Table -->
                <td>
                    <table class="formData" name="usernameTable" width="100%" height="100%" border="0" cellspacing="5" cellpadding="5">
                        <tr>
                            <td> Min Length </td>
                            <td> <input type="text" name="usernameMinLength" size="3" maxSize="3" value="<%= usernameMinLength %>"> </td>
                        </tr>
                        <tr>
                            <td> Max Length </td>
                            <td> <input type="text" name="usernameMaxLength" value="<%= usernameMaxLength %>"> </td>
                        </tr>
                        <tr>
                            <td> Mixed Case </td>
                            <td>
                                <select name="usernameMixedCaseCT">
                                <%
                                    out.println("<option>Please Select</option>");

                                    if (maskRestrictionCTs != null)
                                    {
                                        for (int i = 0; i < maskRestrictionCTs.length; i++)
                                        {
                                            String code = maskRestrictionCTs[i].getCode();
                                            String codeDesc    = maskRestrictionCTs[i].getCodeDesc();

                                            if (code.equals(usernameMixedCaseCT))
                                            {
                                                out.println("<option selected name=\"id\" value=\"" + code+ "\">" + codeDesc + "</option>");
                                            }
                                            else
                                            {
                                                out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                                            }
                                        }
                                    }
                                %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td> Alpha Numeric </td>
                            <td>
                                <select name="usernameAlphaNumCT">
                                <%
                                    out.println("<option>Please Select</option>");

                                    if (maskRestrictionCTs != null)
                                    {
                                        for (int i = 0; i < maskRestrictionCTs.length; i++)
                                        {
                                            String code = maskRestrictionCTs[i].getCode();
                                            String codeDesc    = maskRestrictionCTs[i].getCodeDesc();

                                            if (code.equals(usernameAlphaNumCT))
                                            {
                                                out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                                            }
                                            else
                                            {
                                                out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                                            }
                                        }
                                    }
                                %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td> Special Chars </td>
                            <td>
                                <select name="usernameSpecialCharsCT">
                                <%
                                    out.println("<option>Please Select</option>");

                                    if (maskRestrictionCTs != null)
                                    {
                                        for (int i = 0; i < maskRestrictionCTs.length; i++)
                                        {
                                            String code = maskRestrictionCTs[i].getCode();
                                            String codeDesc    = maskRestrictionCTs[i].getCodeDesc();

                                            if (code.equals(usernameSpecialCharsCT))
                                            {
                                                out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                                            }
                                            else
                                            {
                                                out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                                            }
                                        }
                                    }
                                %>
                                </select>
                            </td>
                        </tr>
                        <tr class="filler">
                            <td colspan="3">
                            </td>
                        </tr>
                        <tr class="filler">
                            <td colspan="3">
                            </td>
                        </tr>
                        <tr class="filler">
                            <td colspan="3">
                            </td>
                        </tr>
                    </table>
                </td>

                <!-- Password Table -->
                <td>
                    <table class="formData" name="passwordTable" width="100%" height="100%" border="0" cellspacing="5" cellpadding="5">
                        <tr>
                            <td> Min Length </td>
                            <td> <input type="text" name="passwordMinLength" value="<%= passwordMinLength %>"> </td>
                        </tr>
                        <tr>
                            <td> Max Length </td>
                            <td> <input type="text" name="passwordMaxLength" value="<%= passwordMaxLength %>"> </td>
                        </tr>
                        <tr>
                            <td> Mixed Case </td>
                            <td>
                                <select name="passwordMixedCaseCT">
                                <%
                                    out.println("<option>Please Select</option>");

                                    if (maskRestrictionCTs != null)
                                    {
                                        for (int i = 0; i < maskRestrictionCTs.length; i++)
                                        {
                                            String code = maskRestrictionCTs[i].getCode();
                                            String codeDesc    = maskRestrictionCTs[i].getCodeDesc();

                                            if (code.equals(passwordMixedCaseCT))
                                            {
                                                out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                                            }
                                            else
                                            {
                                                out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                                            }
                                        }
                                    }
                                %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td> Alpha Numeric </td>
                            <td>
                                <select name="passwordAlphaNumCT">
                                <%
                                    out.println("<option>Please Select</option>");

                                    if (maskRestrictionCTs != null)
                                    {
                                        for (int i = 0; i < maskRestrictionCTs.length; i++)
                                        {
                                            String code = maskRestrictionCTs[i].getCode();
                                            String codeDesc    = maskRestrictionCTs[i].getCodeDesc();

                                            if (code.equals(passwordAlphaNumCT))
                                            {
                                                out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                                            }
                                            else
                                            {
                                                out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                                            }
                                        }
                                    }
                                %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td> Special Chars </td>
                            <td>
                                <select name="passwordSpecialCharsCT">
                                <%
                                    out.println("<option>Please Select</option>");

                                    if (maskRestrictionCTs != null)
                                    {
                                        for (int i = 0; i < maskRestrictionCTs.length; i++)
                                        {
                                            String code = maskRestrictionCTs[i].getCode();
                                            String codeDesc    = maskRestrictionCTs[i].getCodeDesc();

                                            if (code.equals(passwordSpecialCharsCT))
                                            {
                                                out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                                            }
                                            else
                                            {
                                                out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                                            }
                                        }
                                    }
                                %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td> Expiration</td>
                            <td> <input type="text" name="passwordExpiration" value="<%= passwordExpiration %>"> </td>
                            <td> days </td>
                        </tr>

                        <tr>
                            <td> <input type="checkbox" name="passwordNoRepeatsInd" value="<%= passwordNoRepeatsStatus %>" onClick="selectPasswordNoRepeats(this)"> No repeat passwords</td>

                        </tr>
                        <tr>
                            <td> for </td>
                            <td> <input type="text" name="passwordRepeatCycle" value="<%= passwordRepeatCycle %>"> </td>
                            <td> cycles </td>
                        </tr>
                    </table>
                </td>

                <!-- Session Info Table -->
                <td>
                    <table class="formData" name="sessionTable" width="100%" height="100%" border="0" cellspacing="5" cellpadding="5">
                        <tr>
                            <td valign="top"> Expiration </td>
                            <td valign="top"> <input type="text" name="sessionExpiration" value="<%= sessionExpiration %>"> </td>
                            <td valign="top"> minutes </td>
                        </tr>
                        <tr>
                            <td valign="top"> Max Login Attempts </td>
                            <td valign="top"> <input type="text" name="maxLoginAttempts" value="<%= maxLoginAttempts %>"> </td>
                            <td valign="top">&nbsp;</td>
                        </tr>
                        <tr>
                            <td valign="top" colspan="3">&nbsp;</td>
                        </tr>
                        <tr>
                            <td valign="top" colspan="3">&nbsp;</td>
                        </tr>
                        <tr>
                            <td valign="top" colspan="3">&nbsp;</td>
                        </tr>
                        <tr>
                            <td valign="top" colspan="3">&nbsp;</td>
                        </tr>
                        <tr>
                            <td valign="top" colspan="3">&nbsp;</td>
                        </tr>
                        <tr>
                            <td valign="top" colspan="3">&nbsp;</td>
                        </tr>
                    </table>
                </td>
            </tr>



        </table>    <!-- end outer table -->

        <br><br><br>
        <input type="button" name="save" value=" Save " onClick="saveProfile()">

        <!-- ****** HIDDEN FIELDS ***** //-->
        <input type="hidden" name="transaction" value="">
        <input type="hidden" name="action"      value="">
        <input type="hidden" name="usernameMixedCaseCT" value="<%= usernameMixedCaseCT %>">
        <input type="hidden" name="usernameAlphaNumCT" value="<%= usernameAlphaNumCT %>">
        <input type="hidden" name="usernameSpecialCharsCT" value="<%= usernameSpecialCharsCT %>">
        <input type="hidden" name="passwordMixedCaseCT" value="<%= passwordMixedCaseCT %>">
        <input type="hidden" name="passwordAlphaNumCT" value="<%= passwordAlphaNumCT %>">
        <input type="hidden" name="passwordSpecialCharsCT" value="<%= passwordSpecialCharsCT %>">
        <input type="hidden" name="passwordNoRepeatsStatus" value="<%=passwordNoRepeatsStatus %>">
        <input type="hidden" name="securityProfilePK" value="<%=securityProfilePK %>">
        <input type="hidden" name="usernameMaskPK" value="<%=usernameMaskPK %>">
        <input type="hidden" name="passwordMaskPK" value="<%=passwordMaskPK %>">
        <input type="hidden" name="passwordMaskVOPK" value="<%=passwordMaskVOPK %>">


    </form>

    </body>

</html>



