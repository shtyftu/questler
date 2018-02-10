<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:page>
    <jsp:body>
        <div class="container main-container">
            <div class="col-sm-14">
                <ul class="list-group">
                    <li class="list-group-item">
                        <label class="control-label"><h3>Your quests:</h3></label>
                        <table class="table table-striped table-bordered">
                            <c:forEach var="quest" items="${questList}">
                                <tr>
                                    <form:form method="POST" action="${quest.actionLink}">
                                        <td>${quest.name}</td>
                                        <td><span class="badge">${quest.scores}</span></td>
                                        <td>${quest.state}</td>
                                        <td class="timer" data-time="${quest.time}"></td>
                                        <td>
                                            <c:if test="${not empty quest.actionLink}">
                                                <input type="submit" class="btn btn-success"
                                                       value="${quest.actionName}"/>
                                            </c:if>
                                        </td>
                                    </form:form>
                                </tr>
                            </c:forEach>
                        </table>
                    </li>
                    <li class="list-group-item">
                        <label class="control-label"><h3>Scoreboards:</h3></label>
                        <c:forEach var="scores" items="${scoresList}">
                            <b>${scores.packName}</b>
                            <table class="table table-striped table-bordered">
                                <c:forEach var="scoreLine" items="${scores.scores}">
                                    <tr>
                                        <td>${scoreLine.key}</td>
                                        <td>${scoreLine.value}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:forEach>
                    </li>
                </ul>
            </div>
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
