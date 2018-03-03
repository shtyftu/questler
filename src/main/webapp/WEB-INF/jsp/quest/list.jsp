<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:page>
    <jsp:body>
        <div class="table-responsive">
            <label class="control-label"><h3>Your quests:</h3></label>
            <table class="table">
                <c:forEach var="quest" items="${questList}">
                    <tr class="quest-row align-middle" data-state="${quest.state}">
                        <form:form method="POST" action="${quest.actionLink}">
                            <td>${quest.name}</td>
                            <td><span class="badge">${quest.scores}</span></td>
                            <td class="timer" data-time="${quest.time}"></td>
                            <td class="text-center">
                                <c:if test="${not empty quest.actionLink}">
                                    <input type="submit" class="btn btn-secondary"
                                           value="${quest.actionName}"/>
                                </c:if>
                                <c:if test="${empty quest.actionLink}">
                                    ${quest.actionName}
                                </c:if>
                            </td>
                        </form:form>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="table-responsive">
            <label class="control-label"><h3>Scoreboards:</h3></label>
            <c:forEach var="scores" items="${scoresList}">
                <table class="table table-striped">
                    <thead class="text-center">
                    <tr>
                        <td colspan="2" class="text-center">
                            <b>${scores.packName}</b>
                        </td>
                    </tr>
                    </thead>
                    <c:forEach var="scoreLine" items="${scores.scores}">
                        <tr>
                            <td>${scoreLine.key}</td>
                            <td>${scoreLine.value}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:forEach>
        </div>


        <script>
          var currentTime = (new Date).getTime(),
              $timers = $(".timer");

          var adjustTime = function () {
            currentTime += 1000;
            $timers.each(function (index, obj) {
              var $obj = $(obj);
              var timeValue = $obj.attr("data-time");
              if (timeValue) {
                obj.innerHTML = millisToString(timeValue - currentTime);
              }
            });
          };
          adjustTime();
          setInterval(adjustTime, 1000);

          $(".quest-row").each(function (index, questRow) {
            var $questRow = $(questRow);
            var state = $questRow.attr("data-state");
            switch (state) {
              case "Available":
                $questRow.addClass("btn-warning");
                break;
              case "DeadlinePanic":
                $questRow.addClass("btn-danger");
                break;
              case "LockedByUser":
                $questRow.addClass("btn-success");
                break;
              case "WaitingTrigger":
                $questRow.addClass("btn-secondary");
                break;
              case "OnCooldown":
                $questRow.addClass("btn-info");
                break;
            }
          })
        </script>
    </jsp:body>
</t:page>
