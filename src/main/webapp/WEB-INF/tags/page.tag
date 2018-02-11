<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%--<%@attribute name="header" fragment="true" %>--%>
<%@attribute name="footer" fragment="true" %>
<html>
<head>
    <%@ include file="../jsp/common/head.jsp" %>
</head>
<body>
<div id="pageheader">
    <%@ include file="../jsp/common/header.jsp" %>
</div>
<div id="body">
    <div class="container main-container">
        <jsp:doBody/>
    </div>
</div>
<div id="pagefooter">
    <jsp:invoke fragment="footer"/>
</div>
</body>
</html>