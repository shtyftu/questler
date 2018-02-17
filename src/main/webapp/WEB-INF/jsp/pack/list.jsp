<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<t:page>
    <jsp:body>
        <h3>Quest Proto list:</h3>
        <div class="table-responsive">
            <table class="table table-striped">
                <thead class="text-center">
                <tr>
                    <td colspan="2">
                        <h5><b>Create new Quest Pack</b></h5>
                    </td>
                </tr>
                </thead>
                <tbody>
                <form action="/pack/create" method="post">
                    <tr>
                        <td>
                            <input style="width: 100%;" type="text" placeholder="Name" name="name" required="required"/>
                        </td>
                        <td><input type="submit" class="btn btn-success" value="Create"/></td>
                    </tr>
                </form>
                </tbody>
            </table>


            <c:forEach var="pack" items="${list}">
                <table class="table table-striped">
                    <thead class="text-center">
                    <tr>
                        <td colspan="2">
                            <a href="/pack/edit?packId=${pack.id}"><h5><b>${pack.name}</b></h5></a>
                        </td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="entry" items="${pack.protoIdsByQuestName}">
                        <tr>
                            <td>${entry.key}</td>
                            <td>
                                <a href="/pack/edit-quest?id=${entry.value}&packId=${pack.id}">
                                    <button class="btn btn-success">Edit</button>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td class="text-center" colspan="2">
                            <a href="/pack/edit-quest?id=&packId=${pack.id}">
                                <button class="btn btn-info">New quest</button>
                            </a>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </c:forEach>
        </div>
    </jsp:body>
</t:page>
