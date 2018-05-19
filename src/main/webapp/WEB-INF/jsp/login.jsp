<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity4">
<head>
    <title>Hello Spring Security</title>
    <meta charset="utf-8"/>
    <link rel="stylesheet" href="/css/main.css" th:href="@{/css/main.css}"/>
</head>
<body>


<%@ include file="common/header.jsp" %>

<h3>Login Form</h3>
<br/>

<form name='loginForm' action="/login-check" method='POST'>
    <div class="table-responsive">

        <table>
            <tr>
                <td>User:</td>
                <td><input type='text' name='login'></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type='password' name='password'/></td>
            </tr>

            <!-- if this is login for update, ignore remember me check -->
            <c:if test="${empty loginUpdate}">
                <tr>
                    <td></td>
                    <td>Remember Me: <input type="checkbox" name="remember-me"/></td>
                </tr>
            </c:if>

            <tr>
                <td colspan='2'>
                    <input name="submit" type="submit" value="submit"/>
                </td>
            </tr>

        </table>
        <div class="table-responsive">

</form>

</body>
</html>