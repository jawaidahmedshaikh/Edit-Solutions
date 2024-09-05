<%
//    response.setContentType("text/xml");

    String[] confirms = (String[]) request.getAttribute("confirms");

    for (int i = 0; i < confirms.length; i++){

        out.println(confirms[i]);
        out.print("<hr>");
    }
%>






