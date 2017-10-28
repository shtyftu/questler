<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<t:page>
    <jsp:body>
        <table>
            <c:set var="index" value="1"/>
            <c:forEach var="quest" items="${list}">
                <tr>
                    <td>${index}</td>
                    <c:set var="index" value="${index + 1}"/>
                    <td>${quest.name}</td>
                    <td>${quest.state}</td>
                    <td>${quest.time}</td>
                    <td><a href="${quest.actionLink}">${quest.actionName}</a></td>
                </tr>
            </c:forEach>
        </table>
    </jsp:body>
    <jsp:attribute name="footer">
      <p id="copyright">questler-life by shtyftu 2017</p>
    </jsp:attribute>
</t:page>
