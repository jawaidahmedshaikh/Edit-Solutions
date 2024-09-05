<%@ page import="edit.common.vo.LogEntryCollectionVO,
                 fission.utility.Util,
                 edit.common.vo.LogEntryVO"%>
<%
    LogEntryCollectionVO logEntryCollectionVO = (LogEntryCollectionVO) request.getAttribute("logEntryCollectionVO");

%>
<%
    StringBuffer xml = new StringBuffer();

    if (logEntryCollectionVO != null) // Test for the existence of the target VOs.
    {
        LogEntryVO[] logEntryVOs = logEntryCollectionVO.getLogEntryVO();

        for (int i = 0; i < logEntryVOs.length; i++) // Loop through the target VOs.
        {
            LogEntryVO currentLogEntryVO = logEntryVOs[i];

            xml.append(Util.marshalVO(currentLogEntryVO));
        }
    }
%>

<xml>
    <%= xml.toString() %>
</xml>