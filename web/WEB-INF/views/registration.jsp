<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<html>
<head>
    <title>Registration</title>
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
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/registration.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/xs-screen.css"/>">
</head>
<body class="body-style">
<jsp:include page="header.jsp"/>
<div class="padding-top"></div>
<div class="pure-g">
    <div class="pure-u-md-1-2 pure-u-sm-5-6 centered">
        <sf:form class="pure-form pure-form-aligned" method="post" modelAttribute="user">
            <h3 class="text-center">Please, fill form below to register</h3>
            <fieldset class="text-center">
                <div class="pure-control-group">
                    <sf:label path="name">Nickname: </sf:label>
                    <sf:input path="name" minlength="1" maxlength="20" placeholder="Name to display"
                              cssErrorClass="error-input" required="true"/>
                </div>
                <div class="pure-control-group">
                    <sf:label path="login">Your email:</sf:label>
                    <sf:input path="login" type="email" minlength="3" maxlength="60" placeholder="E-mail"
                              cssErrorClass="error-input" required="true"/>
                </div>
                <div class="pure-control-group">
                    <sf:label path="password">Enter password: </sf:label>
                    <sf:password path="password" minlength="3" maxlength="15" placeholder="Password"
                                 cssErrorClass="error-input" required="true"/>
                </div>
                <div class="pure-controls">
                    <input class="pure-button" type="submit" value="Register">
                </div>
                <sf:errors path="*" element="p" cssClass="error-info"/>
            </fieldset>
            <a id="login-redirect" class="text-center" href="<c:url value="/"/>">To login page</a>
        </sf:form>
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
</div>
</body>
</html>