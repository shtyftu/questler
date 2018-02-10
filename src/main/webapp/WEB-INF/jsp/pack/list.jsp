<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<t:page>
    <jsp:body>
        <table>
            <form action="/pack/create" method="post">
                <tr>
                    <td><input type="text" placeholder="Name" name="name" required="required"/></td>
                    <td><input type="submit" class="btn btn-success" value="Create new Quest Pack"/></td>
                </tr>
            </form>
        </table>

        <h3>Quest Proto list:</h3>
        <c:forEach var="pack" items="${list}">
            <a href="/pack/edit?packId=${pack.id}">
                <label class="control-label">${pack.name}</label>
            </a>
            <table class="table table-striped table-bordered">
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
                    <td/>
                    <td>
                        <a href="/pack/edit-quest?id=&packId=${pack.id}">
                            <button class="btn btn-info">New quest</button>
                        </a>
                    </td>
                </tr>
            </table>
        </c:forEach>
    </jsp:body>
</t:page>
