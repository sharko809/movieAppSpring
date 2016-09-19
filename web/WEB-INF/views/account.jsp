<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<html>
<head>
    <title>Account info</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/vendor/pure/pure-min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/vendor/pure/base-min.css"/>">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="/resources/css/vendor/pure/grids-responsive-old-ie-min.css">
    <![endif]-->
    <!--[if gt IE 8]><!-->
    <link rel="stylesheet" href="<c:url value="/resources/css/vendor/pure/grids-responsive-min.css"/>">
    <!--<![endif]-->
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mainPage.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/account.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/xs-screen.css"/>">
    <script src="<c:url value="/resources/js/vendor/jquery-3.1.0.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/redirect-url.js"/>" type="text/javascript"></script>
</head>
<body class="body-style">
<jsp:include page="header.jsp"/>
<div class="padding-top"></div>
<div class="pure-g">
    <div class="pure-u-md-3-4 pure-u-sm-1 centered">
        <div class="pure-u-1">
            <c:if test="${pageContext.request.remoteUser ne null}">
                <sf:form class="pure-form pure-form-aligned" style="text-align: center;" method="post"
                         commandName="thisUser">
                    <fieldset>
                        <div class="pure-control-group">
                            <sf:label path="name">Username: </sf:label>
                            <div class="inline-flex">
                                <sf:input path="name" style="margin-right: 5px;" cssErrorClass="error-input"
                                          readonly="true"/>
                                <button id="editName" type="button" class="pure-button">Edit</button>
                            </div>
                        </div>
                        <div class="pure-control-group">
                            <sf:label path="login">Login: </sf:label>
                            <div class="inline-flex">
                                <sf:input type="email" path="login" minlength="3" maxlength="60"
                                          cssErrorClass="error-input"
                                          style="margin-right: 5px;" readonly="true"/>
                                <button id="editLogin" type="button" class="pure-button">Edit</button>
                            </div>
                        </div>
                        <div class="pure-control-group">
                            <sf:label path="password">Password: </sf:label>
                            <div class="inline-flex">
                                <sf:password path="password" cssErrorClass="error-input"
                                             title="If you want to change password - type new one here." minlength="3"
                                             maxlength="15"
                                             placeholder="Leave blank to keep old password" style="margin-right: 5px;"
                                             readonly="true"/>
                                <button id="editPassword" type="button" class="pure-button">Edit</button>
                            </div>
                        </div>
                        <div class="pure-controls">
                            <input type="hidden" id="redirectFrom" name="redirectFrom" value=""/>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" class="pure-button">Update account</button>
                        </div>
                        <sf:errors path="*" element="p" cssClass="error-info"/>
                        <c:if test="${success ne null}">
                            <div class="success-info">
                                <p>${success}</p>
                            </div>
                        </c:if>
                    </fieldset>
                </sf:form>
            </c:if>
        </div>
    </div>
</div>
<script src="<c:url value="/resources/js/account-enable-input.js"/>" type="text/javascript"></script>
</body>
</html>