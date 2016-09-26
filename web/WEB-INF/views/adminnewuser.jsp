<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<html>
<head>
    <title>Create new user</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/vendor/pure/pure-min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/vendor/pure/base-min.css"/>">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="/resources/css/vendor/pure/grids-responsive-old-ie-min.css">
    <link rel="stylesheet" href="/resources/css/vendor/pure/layouts/side-menu-old-ie.css">
    <![endif]-->
    <!--[if gt IE 8]><!-->
    <link rel="stylesheet" href="<c:url value="/resources/css/vendor/pure/grids-responsive-min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/vendor/pure/layouts/side-menu.css"/>">
    <!--<![endif]-->
    <link rel="stylesheet" href="<c:url value="/resources/css/mainPage.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/admin.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/adminReg.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/xs-screen.css"/>">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="padding-top"></div>
<jsp:include page="adminmenu.jsp"/>
<div class="pure-g custom-margin">
    <div class="pure-u-md-1-2 pure-u-sm-1-2 centered" style="text-align: center;">
        <sf:form modelAttribute="user" class="pure-form pure-form-aligned" method="post">
            <fieldset>
                <div class="pure-control-group">
                    <sf:label path="name">Nickname: </sf:label>
                    <sf:input path="name" minlength="1" maxlength="20" placeholder="Name to display" cssErrorClass="error-input" required="true"/>
                </div>
                <div class="pure-control-group">
                    <sf:label path="login">Your email:</sf:label>
                    <sf:input path="login" type="email" minlength="3" maxlength="60" placeholder="E-mail" cssErrorClass="error-input" required="true"/>
                </div>
                <div class="pure-control-group">
                    <sf:label path="password">Enter password: </sf:label>
                    <sf:password path="password" minlength="3" maxlength="15" placeholder="Password" cssErrorClass="error-input" required="true"/>
                </div>
                <div class="pure-controls">
                    <sf:label path="admin" class="pure-checkbox" style="margin: 15px;">
                        <sf:checkbox path="admin"/>Is Admin
                    </sf:label>
                    <input class="pure-button" type="submit" value="Register">
                </div>
            </fieldset>
            <sf:errors path="*" element="p" cssClass="error-info"/>
        </sf:form>
    </div>
    <div class="pure-u-1">
        <c:if test="${fail ne null}">
            <div class="error-info">
                <p>${fail}</p>
            </div>
        </c:if>
        <c:if test="${success ne null}">
            <div class="success-info">
                <p>${success}</p>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>