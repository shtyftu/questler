<%--<div th:fragment="logout" class="logout" sec:authorize="isAuthenticated()">--%>
    <%--Logged in user: <span sec:authentication="name"></span> |--%>
    <%--Roles: <span sec:authentication="principal.authorities"></span>--%>
    <%--<div>--%>
        <%--<form action="/logout" th:action="@{/logout}" method="post">--%>
            <%--<input type="submit" value="Logout" />--%>
        <%--</form>--%>
    <%--</div>--%>
    <table>
        <tr>
            <td><a href="/quest/list">Home</a></td>
            <td><a href="/pack/list">Packs</a></td>
            <td><a href="/proto/list">Protos</a></td>
            <td></td>
        </tr>
    </table>
<%--</div>--%>
