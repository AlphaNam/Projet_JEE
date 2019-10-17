<%-- 
    Document   : bienvenue
    Created on : 19 sept. 2019, 10:40:26
    Author     : JAA
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <title>Bienvenue - LSI - M1</title>
    </head>
    <body>
        <c:forEach items="${listeEmplKey}" var="employe">
            ${employe.nom} ${employe.prenom} <br/>
        </c:forEach>
    </body>
</html>
