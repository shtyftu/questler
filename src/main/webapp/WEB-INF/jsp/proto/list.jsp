<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<t:page>
    <jsp:body>
        <form action="/proto/import" method="post" enctype="multipart/form-data">
            <table>
                <tr>
                    <td><a href="/proto/export">Export QuestProto list json file</a></td>
                </tr>
                <tr>
                    <td>Upload QuestProto list json file:</td>
                    <td><input type="file" name="file"/></td>
                    <td><input type="submit" value="Import"/></td>
                </tr>
            </table>
        </form>

        <h3>Quest Proto list:</h3>
        <table>
            <tr>
                <td>Id</td>
                <td>Status</td>
                <td>Description</td>
            </tr>
            <c:forEach var="quest" items="${list}">
                <tr>
                    <td>${quest.id}</td>
                    <td>${quest.status}</td>
                    <td>${quest.description}</td>
                </tr>
            </c:forEach>
            <tr>
            </tr>
        </table>
    </jsp:body>
</t:page>
