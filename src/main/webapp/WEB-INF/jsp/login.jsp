<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org" xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity4">
<head>
    <title>Hello Spring Security</title>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="/css/main.css" th:href="@{/css/main.css}" />
</head>
<body>


<%@ include file="common/header.jsp" %>

<h3>Login Form</h3>
<br/>
<a href="/register">Login Page</a>
<form action="/login" method="post">
    Email:<input type="text" name="login"/><br/><br/>
    Password:<input type="password" name="password"/><br/><br/>
    <input type="submit" value="login"/>"
</form>

</body>
</html>