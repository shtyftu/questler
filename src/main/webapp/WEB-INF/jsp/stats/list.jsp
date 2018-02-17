<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<t:page>
    <jsp:body>
        <h3>Quest Stats:</h3>
        <div class="table-responsive">
            <table class="table table-striped">
                <tr>
                    <c:forEach var="durationValue" items="${durations}">
                        <td>
                            <c:if test="${!(durationValue eq duration)}">
                                <a href="/stats/list?duration=${durationValue}">${durationValue}</a>
                            </c:if>
                            <c:if test="${durationValue eq duration}">
                                <p>${durationValue}</p>
                            </c:if>
                        </td>
                    </c:forEach>
                </tr>
            </table>
        </div>
        <div class="table-responsive">
            <c:forEach var="pack" items="${packs}">
                <table class="table table-striped">
                    <thead class="text-center">
                    <tr>
                        <td colspan="4"><h5><b>${pack.name}</b></h5></td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="event" items="${pack.events}">
                        <tr>
                            <td>${event.time}</td>
                            <td>${event.userId}</td>
                            <td>${event.eventType}</td>
                            <td>${event.questName}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:forEach>
        </div>
    </jsp:body>
</t:page>
