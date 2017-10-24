<DOCTYPE html>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <html lang="en">
    <head>

        <title>Hello Spring Security</title>
        <meta charset="utf-8"/>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css"
              integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb"
              crossorigin="anonymous"/>

        <%--<!----%>
        <link rel="stylesheet" href="/css/main.css" th:href="@{/css/main.css}"/>

        <c:url value="/css/main.css" var="jstlCss"/>
        <link href="${jstlCss}" rel="stylesheet"/>

    </head>
    <body>
    <%@ include file="../index.jsp" %>

    <div class="container">

        <table>
            <c:set var="index" value="1"/>
            <c:forEach var="quest" items="${questList}">

                <tr>
                    <td>${index}</td>
                    <c:set var="index" value="${index + 1}"/>
                    <td>${quest.name}</td>
                    <td>${quest.state}</td>
                    <td>${quest.time}</td>
                    <td><a href="${quest.actionLink}">${quest.actionName}</a></td>


                </tr>
            </c:forEach>
        </table>
    </div>

    <%--<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>--%>

    </body>

    </html>