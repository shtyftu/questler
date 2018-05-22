<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<t:page>
    <jsp:body>
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
                            <input name="submit" type="submit" value="Login"/>
                        </td>
                    </tr>

                </table>
            </div>
        </form>

    </jsp:body>
</t:page>
