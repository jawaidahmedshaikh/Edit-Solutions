<html>

<%
    String templateName = initValue((String) request.getAttribute("templateName"));

%>

<%!
    private String initValue(String value){

        if (value != null){

            return value;
        }
        else {

            return "";
        }
    }

%>
<head>
<title>Save Template</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.contractNumberForm;

		window.resizeTo(getPreferredWidth(), getPreferredHeight());
	}

	function getPreferredWidth() {

		return 1.25 * document.all.table1.offsetWidth;
	}

	function getPreferredHeight() {

		return 1.75 * document.all.table1.offsetHeight;
	}


	function saveTemplate() {

        var templateName = theForm.templateName.value;

        if (templateName == null || templateName.length == 0){

            alert("Please enter a template name.");

            return;
        }

		opener.saveTemplate(templateName);

		window.close();
	}

	function cancelDialog() {

		window.close();
	}

</script>
</head>
<body bgcolor="#DDDDDD" onLoad="init()">
<form name="theForm" onSubmit="saveTemplate(); return false;">
  <table id="table1" width="43%" border="0" cellspacing="0" cellpadding="8" bgcolor="#DDDDDD">

    <tr>
      <td colspan="3" nowrap>Template Name:
        <input type="text" name="templateName" value="<%= templateName %>">
      </td>
    </tr>
    <tr>
      <td>&nbsp;&nbsp;</td>
      <td colspan="2" align="right">
        <input type="button" name="enter" value="Enter" onClick="saveTemplate()">
        <input type="button" name="cancel" value="Cancel" onClick="cancelDialog()">
      </td>
    </tr>
  </table>
</form>
</body>
</html>
