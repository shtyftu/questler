<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:page>
    <jsp:body>
        <form:form method="POST" action="/pack/save-quest" modelAttribute="questProto">
            <form:hidden path="id"/>
            <input type="hidden" name="packId" value="${packId}"/>
            <h3>Editing the quest ${questProto.name} #${questProto.id}</h3>
            <div class="container">
                <div class="row">
                    <div class="col">Name</div>
                    <div class="col"><form:input path="name" placeholder="" required="required"/></div>
                </div>
                <div class="row">
                    <div class="col">Cooldown</div>
                    <div class="col"><form:input path="cooldown" placeholder="Cooldown" required="required"/></div>
                </div>
                <div class="row">
                    <div class="col">Deadline</div>
                    <div class="col"><form:input path="deadline" placeholder="Deadline" required="required"/></div>
                </div>
                <div class="row">
                    <div class="col">Scores</div>
                    <div class="col"><form:input path="scores" placeholder="Scores" required="required"/></div>
                </div>
                <div class="row">
                    <div class="col">Triggered quest</div>
                    <div class="col"><form:checkbox path="activatedByTrigger" name="scores"/></div>
                </div>
                <div class="row">
                    <div class="col">Next quest</div>
                    <div class="col"><form:select path="nextQuestId" placeholder="">
                        <form:option value="">&nbsp;</form:option>
                        <form:options items="${nextQuest}" />
                    </form:select></div>
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col">Cancel</div>
                    <div class="col"><input type="submit" value="Save"></div>
                </div>
            </div>
        </form:form>
    </jsp:body>
</t:page>
