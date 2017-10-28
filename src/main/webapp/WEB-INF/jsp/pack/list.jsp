<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<t:page>
    <jsp:body>
        <form action="/pack/create" method="post">
            <table>
                <tr>
                    <td>Create new Quest Pack:</td>
                    <td><input type="text" placeholder="Name" name="name" required="required"/></td>
                    <td><a href="/pack/create">Export QuestProto list json file</a></td>
                    <td><input type="submit" value="Create"/></td>
                </tr>
            </table>
        </form>

        <h3>Quest Proto list:</h3>
        <table>
            <tr>
                <td>Name</td>
                <td>Quest Count</td>
            </tr>
            <c:forEach var="pack" items="${list}">
                <tr>
                    <td>${pack.name}</td>
                    <td>${pack.questCount}</td>
                    <td><a href="/pack/edit?packId="${pack.id}>Edit</a></td>
                </tr>
            </c:forEach>
            <tr>
            </tr>
        </table>
    </jsp:body>
    <jsp:attribute name="footer">
      <p id="copyright">questler-life by shtyftu 2017</p>
    </jsp:attribute>
</t:page>
