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
        <link rel="stylesheet" href="/css/main.css" th:href="@{/css/main.css}"/>

        <c:url value="/css/main.css" var="jstlCss"/>
        <link href="${jstlCss}" rel="stylesheet"/>

    </head>
    <body>
    <%--<%@ include file="../index.jsp" %>--%>

    <div class="container">
        <form action="/list/import" method="post" enctype="multipart/form-data">
            <table>
                <tr>
                    <td>
                        Upload QuestList json file:
                    </td>
                </tr>
                <tr>

                    <td>
                        <input type="file" name="file"/>
                    </td>
                    <td>
                        <input type="submit" value="Import"/>
                    </td>
                </tr>
            </table>
        </form>

        <h2>All available QuestLists:</h2>
        <table>
            <%--<c:set var="index" value="1"/>--%>
            <c:forEach var="questList" items="${list}">
                <tr>
                        <%--<td>${index}</td>--%>
                        <%--<c:set var="index" value="${index + 1}"/>--%>
                    <td>${questList.id}</td>
                    <td>${questList.name}</td>
                    <td><a href="/list/export?listId=${questList.id}">Export</a></td>
                </tr>
                <%--<c:forEach var="quest" items="${questList.quests}">--%>
                <tr>
                    XYZ
                        <%--${quest}--%>
                </tr>
                <%--</c:forEach>--%>
                <tr>
                </tr>
            </c:forEach>
        </table>
    </div>

    <%--<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>--%>

    </body>

    </html>