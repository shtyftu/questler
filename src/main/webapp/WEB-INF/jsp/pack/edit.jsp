<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:page>
    <jsp:body>
        <form action="/pack/save" method="post">
            <input type="hidden" name="packId" value="${pack.id}">
            <h3>Editing Quest Pack "${pack.name}"</h3>
            <table>
                <c:forEach var="entry" items="${pack.questNameById}" varStatus="loop">
                    <tr>
                            <%--<form:input path="sections[0]" />--%>
                        <input type="hidden" name="q[${loop.index}].id" value="${entry.key}"/>
                        <td>${loop.index}</td>
                        <td>${entry.value}</td>
                        <td><button>Delete</button></td>
                            <%--<td><a href="/pack/edit?packId="${pack.id}>Edit</td>--%>
                    </tr>
                </c:forEach>
                <tr>
                    <td></td>
                    <td>
                        <form:select path="q[-1].protoId">
                            <form:options items="${protos}" itemValue="key" itemLabel="value" />
                        </form:select>
                    </td>
                    <td>
                        <button>New</button>
                    </td>
                </tr>
                <input type="submit">
            </table>
        </form>
    </jsp:body>
    <jsp:attribute name="footer">
      <p id="copyright">questler-life by shtyftu 2017</p>
    </jsp:attribute>
</t:page>
