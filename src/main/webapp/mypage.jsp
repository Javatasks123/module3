<%@ page import="java.io.File" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <title>File Browser</title>
    </head>

    <body>

        <div style="position:absolute; top:10px; right:10px;">
            <a href="logout">Выйти</a>
        </div>

        <div>
            <b>${time}</b>
        </div>

        <h2>${currentPath}</h2>

        <%
            File[] files = (File[]) request.getAttribute("files");
            String parent = (String) request.getAttribute("parentPath");
        %>

        <% if (parent != null) { %>
        <a href="files?path=<%=parent.replace("\\","/")%>">⬆ Вверх</a>
        <br><br>
        <% } %>

        <table border="1" cellpadding="5">

        <tr>
            <th>Файл</th>
            <th>Размер</th>
            <th>Дата</th>
        </tr>

        <%
        if (files != null) {
            for (File f : files) {
        %>

        <tr>

        <td>

        <% if (f.isDirectory()) { %>

        <a href="files?path=<%=f.getAbsolutePath().replace("\\", "/")%>">
            📁 <%=f.getName()%>
        </a>

        <% } else { %>

        <a href="files?path=<%=f.getAbsolutePath().replace("\\", "/")%>">
            📄 <%=f.getName()%>
        </a>

        <% } %>

        </td>

        <td>
        <%= f.isFile() ? f.length() + " B" : "-" %>
        </td>

        <td>
        <%= new java.util.Date(f.lastModified()) %>
        </td>

        </tr>

        <%
            }
        }
        %>

        </table>

    </body>
</html>