<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:page>
    <jsp:body>
        <form:form method="POST" action="/pack/save" modelAttribute="pack" onsubmit="disableEmptySelect();">
            <form:hidden path="id"/>
            <h3>Editing Quest Pack "${pack.name}"</h3>

            <h4>Quest protos:</h4>
            <div class="container">
                <c:forEach var="entry" items="${pack.questNamesById}" varStatus="loop">
                    <div class="row">
                        <form:hidden path="questNamesById[${entry.key}]"/>
                        <div class="col">${entry.value}</div>
                        <div class="col">
                            <button>Delete</button>
                        </div>
                    </div>
                </c:forEach>
                <div class="row">
                    <div class="col">
                        <select name="protoIds[-1]" class="quest-select" onchange="onProtoSelect()">
                            <option value=""/>
                            <c:forEach items="${protos}" var="proto">
                                <option value="${proto.key}">${proto.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col">
                        <button style="display: none;">Delete</button>
                    </div>
                </div>
            </div>

            <h4>Users:</h4>
            <div class="container">
                <c:forEach var="entry" items="${pack.userNamesById}" varStatus="loop">
                    <div class="row">
                        <form:hidden path="userNamesById[${entry.key}]"/>
                        <div class="col">${entry.value}</div>
                        <div class="col">
                            <button>Delete</button>
                        </div>
                    </div>
                </c:forEach>
                <div class="row">
                    <div class="col">
                        <select name="invite-input" class="invite-select" onchange="onInviteSelect()">
                            <option value=""/>
                            <c:forEach items="${invites}" var="invite">
                                <option value="${invite.key}">${invite.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col">
                        <button style="display: none;">Delete</button>
                    </div>
                </div>
            </div>

            <div class="container">
                <div class="row">
                    <div class="col">Cancel</div>
                    <div class="col"><input type="submit" value="Save"></div>
                </div>
            </div>
        </form:form>
        <script>
          function onProtoSelect() {
            var needAddRow = true;
            var $questSelect = $(".quest-select");
            $questSelect.each(function (index, obj) {
              $(obj).attr("name", "protoIds[" + index + "]");
              if ($(obj).val()) {
                var $delButton = $(obj).parents(".row").find("button");
                $delButton.show();
              } else {
                needAddRow = false;
              }
            });
            if (needAddRow) {
              var $lastRow = $questSelect.last().parents(".row");
              var $cloneRow = $lastRow.clone().appendTo($lastRow.parent());
              $cloneRow.find("button").hide();
              $cloneRow.find(".quest-select").attr("name", "protoIds[-1]")
            }
          }

          function onInviteSelect() {
            var needAddRow = true;
            var $inviteSelect = $(".invite-select");
            $inviteSelect.each(function (index, obj) {
              $(obj).attr("name", "inviteIds[" + index + "]");
              if ($(obj).val()) {
                var $delButton = $(obj).parents(".row").find("button");
                $delButton.show();
              } else {
                needAddRow = false;
              }
            });
            if (needAddRow) {
              var $lastRow = $inviteSelect.last().parents(".row");
              var $cloneRow = $lastRow.clone().appendTo($lastRow.parent());
              $cloneRow.find("button").hide();
              $cloneRow.find(".invite-select").attr("name", "invite-input")
            }
          }

          function disableEmptySelect() {
            var $emptySelect = $('.quest-select[name="protoIds[-1]"]');
            $emptySelect.attr("disabled", "disabled");
          }
        </script>
    </jsp:body>
</t:page>
