<%@ page import="demo.spec.Message"%>
<%@ page import="demo.spec.UserAccess"%>
<%@ page import="java.util.List" %>

<head>
    <meta http-equiv="Expires" CONTENT="0">
    <meta http-equiv="Cache-Control" CONTENT="no-cache">
    <meta http-equiv="Pragma" CONTENT="no-cache">
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Message Wall</title>
</head>

<%
    UserAccess current_user = (UserAccess)session.getAttribute("useraccess");
%>

<script>
    
    
    
    
</script>

<body>
    
    <h3>user: <em><%=current_user.getUser()%></em>
        <a href=logout.do>[Close session]</a></h3>

    <h2> <%=current_user.getNumber()%> Messages shown:</h2>

    <table width="50%" border="1" bordercolordark="#000000" bordercolorlight="#FFFFFF" cellpadding="3" cellspacing="0">

        <td width="14%" valign="center" align="middle">
            Message
        </td>

        <td width="14%" valign="center" align="middle">
            Owner
        </td>

        <td width="14%" valign="center" align="middle">
            Click to:
        </td>

        <%
            List<Message> message_list = current_user.getAllMessages();
            //Iterate over all the messges to display them
            int counter = 0;
            if(!message_list.isEmpty()){
                for(Message it_msg : message_list){
                    System.out.println(it_msg.getContent());
                    System.out.println(it_msg.getOwner());

        %>
     <tr> 
        <font size="2" face="Verdana">

        <td width="14%" valign="center" align="middle">
            <%=it_msg.getContent()%>
        </td>

        <td width="14%" valign="center" align="middle">
            <%=it_msg.getOwner()%>
        </td>

        <td width="14%" valign="center" align="middle">
            <form action="delete.do" method="post">
                <input type="hidden"
                       name="current_user"
                       value="<%it_msg.getOwner();%>">
                <input type="submit"
                       name="delete"
                       value="delete">
                <input type="hidden"
                       name="position"
                       value="<%=counter%>">
            </form>
        </td>

        </font> 
    </tr>

    <% }}
    counter++;%>

</table>

</br>

<HR WIDTH="100%" SIZE="2">

<form action="put.do" method=POST>
    New message:<input type=text name=msg size=10>
    <input type=submit value="Send message"></form>

<HR WIDTH="100%" SIZE="2">

<form action="refresh.do" method=POST>
    <input type=submit value="Refresh wall view message"></form>

</body>