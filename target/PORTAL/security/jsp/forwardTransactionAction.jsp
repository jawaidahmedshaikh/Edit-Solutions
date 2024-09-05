<%
    try
    {
        session.removeAttribute("userHasActiveRequest"); // user has finished their request
    }
    catch (Exception e)
    {
        // Squash this error. It's meaningless.
    }%>


<jsp:forward page="/servlet/RequestManager">
    <jsp:param name='transaction' value='<%= (String) request.getAttribute("targetTransaction") %>'/>
    <jsp:param name='action' value='<%= (String) request.getAttribute("targetAction") %>'/>
  </jsp:forward>
