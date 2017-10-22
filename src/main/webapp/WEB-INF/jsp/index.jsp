<div th:fragment="logout" class="logout" sec:authorize="isAuthenticated()">
    Logged in user: <span sec:authentication="name"></span> |
    Roles: <span sec:authentication="principal.authorities"></span>
    <div>
        <form action="/logout" th:action="@{/logout}" method="post">
            <input type="submit" value="Logout" />
        </form>
    </div>
</div>
