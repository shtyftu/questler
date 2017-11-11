<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:page>
    <jsp:body>
        <h3>Your quests:</h3>
        <div class="container">
            <c:forEach var="quest" items="${questList}">
                <div class="row">
                    <div class="col">${quest.name}</div>
                    <div class="col">${quest.scores}</div>
                    <div class="col">${quest.state}</div>
                    <div class="col timer" data-time="${quest.time}"></div>
                    <div class="col">
                        <c:if test="${not empty quest.actionLink}">
                            <form:form method="POST" action="${quest.actionLink}">
                                <input type="submit" class="btn btn-default" value="${quest.actionName}"/>
                            </form:form>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </div>
        <br/>
        <h3>Scoreboards:</h3>
        <div class="container">
            <c:forEach var="scores" items="${scoresList}">
                <div class="row">${scores.packName}</div>
                <c:forEach var="scoreLine" items="${scores.scores}">
                    <div class="row">
                        <div class="col">${scoreLine.key}</div>
                        <div class="col">${scoreLine.value}</div>
                    </div>
                </c:forEach>
                <br>
            </c:forEach>
        </div>
        <script>
          var currentTime = (new Date).getTime(),
              $timers = $(".timer");

          setInterval(function () {
            currentTime += 1000;
            $timers.each(function (index, obj) {
              var $obj = $(obj);
              var timeValue = $obj.attr("data-time");
              if (timeValue) {
                obj.innerHTML = millisToString(timeValue - currentTime);
              }
            });
          }, 1000)
        </script>
    </jsp:body>
</t:page>
