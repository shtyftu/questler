<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:page>
    <jsp:body>
        <div class="container">
            <c:set var="index" value="1"/>
            <c:forEach var="quest" items="${list}">
                <div class="row">
                    <div class="col">${index}</div>
                    <c:set var="index" value="${index + 1}"/>
                    <div class="col">${quest.name}</div>
                    <div class="col">${quest.state}</div>
                    <div class="col timer" data-time="${quest.time}"></div>
                    <div class="col">
                        <form:form method="POST" action="${quest.actionLink}">
                            <input type="submit" value="${quest.actionName}"/>
                        </form:form>
                    </div>
                </div>
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
