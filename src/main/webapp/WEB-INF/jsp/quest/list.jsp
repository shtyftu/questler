<DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>

    <title>Hello Spring Security</title>
    <meta charset="utf-8" />

    <!-- Access the bootstrap Css like this,
        Spring boot will handle the resource mapping automcatically -->
    <link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

    <%--<!----%>
    <link rel="stylesheet" href="/css/main.css" th:href="@{/css/main.css}" />

    <%--<spring:url value="/css/main.css" var="springCss" />--%>
	<%--<link href="${springCss}" rel="stylesheet" />--%>
	 <%---->--%>
    <c:url value="/css/main.css" var="jstlCss" />
    <link href="${jstlCss}" rel= "stylesheet" />

</head>
<body>
<%@ include file="../index.jsp" %>

<%--<nav class="navbar navbar-inverse">--%>
    <%--<div class="container">--%>
        <%--<div class="navbar-header">--%>
            <%--<a class="navbar-brand" href="#">DONT LIKE</a>--%>
        <%--</div>--%>
        <%--<div id="navbar" class="collapse navbar-collapse">--%>
            <%--<ul class="nav navbar-nav">--%>
                <%--<li class="active"><a href="#">Home</a></li>--%>
                <%--<li><a href="#about">About</a></li>--%>
            <%--</ul>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</nav>--%>
<%----%>
<div class="container">

    <%--<div class="starter-template">--%>
        <%--<h1>STRAIGHT LINES</h1>--%>
        <%--<h2>Message: ${message}</h2>--%>
        <%--</div>--%>
        <table>
            <c:set var="index" value="1"/>
            <c:forEach var="quest" items="${questList}" >

                <tr>
                    <td>${index}</td>
                    <c:set var="index" value="${index + 1}"/>
                    <td>${quest.name}</td>
                    <td>${quest.state}</td>
                    <td>${quest.time}</td>
                    <td><a href="/quest/enable?questId=${quest.id}">Enable</a></td>


                </tr>
            </c:forEach>
        </table>


</div>

<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>