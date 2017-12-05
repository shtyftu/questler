<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<t:page>
    <jsp:body>
        <h3>Quest Proto list:</h3>
        <div class="container">
            <div class="row">
                <div class="col">Name</div>
                <div class="col"></div>
            </div>
            <form action="/pack/create" method="post">
                <div class="row">
                    <div class="col"><input type="text" placeholder="Name" name="name" required="required"/></div>
                    <div class="col"><input type="submit" value="Create new Quest Pack"/></div>
                </div>
            </form>
        </div>
        <c:forEach var="pack" items="${list}">
            <br>
            <div class="container">
                <div class="row">
                    <div class="col">${pack.name}</div>
                    <div class="col"><a href="/pack/edit?packId=${pack.id}">
                        <button>Edit</button>
                    </a></div>
                </div>
                <br>
                <c:forEach var="entry" items="${pack.protoIdsByQuestName}">
                    <div class="row">
                        <div class="col">
                            <a href="/pack/edit-quest?id=${entry.value}&packId=${pack.id}">${entry.key}</a>
                        </div>
                    </div>
                </c:forEach>
                <div class="row">
                    <div class="col">
                        <a href="/pack/edit-quest?id=&packId=${pack.id}">CREATE NEW QUEST</a>
                    </div>
                </div>
            </div>
        </c:forEach>
        </div>
    </jsp:body>
</t:page>
