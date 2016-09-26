<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/pure-min.css">
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/base-min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/grids-responsive-old-ie-min.css">
    <![endif]-->
    <!--[if gt IE 8]><!-->
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/grids-responsive-min.css">
    <!--<![endif]-->
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mainPage.css"/>/">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/authPage.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/authHeader.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/xs-screen.css"/>">
    <script src="<c:url value="/resources/js/vendor/jquery-3.1.0.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/reset-login.js"/>" type="text/javascript"></script>
</head>
<body class="body-style">
<jsp:include page="/WEB-INF/views/header.jsp"/>
<div class="padding-top"></div>
<div class="pure-g">
    <div class="pure-u-md-1-2 pure-u-sm-5-6 centered">
        <div id="auth-block" class="pure-u-1 inline-flex">
            <div class="pure-u-md-1-2 pure-u-sm-1 max-width">
                <form class="pure-form pure-form-aligned" method="post" action="<c:url value="/login"/>">
                    <h3 class="text-center">Please, fill form below to login</h3>
                    <fieldset class="text-center">
                        <div class="pure-control-group">
                            <label for="login">E-mail: </label>
                            <input id="login" name="login" type="email" minlength="3" maxlength="60"
                                   placeholder="E-mail" required/>
                        </div>
                        <div class="pure-control-group">
                            <label for="password">Password: </label>
                            <input id="password" name="password" type="password" minlength="3" maxlength="15"
                                   placeholder="Password"
                                   required/>
                        </div>
                        <div class="pure-controls">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input class="pure-button" type="submit" value="Login"/>
                        </div>
                        <input type="hidden" name="regPage" value="regPage"/>
                        <c:if test="${login ne null}">
                            <script type="text/javascript">
                                setLoginInputs('${login}');
                            </script>
                        </c:if>
                    </fieldset>
                </form>
                <div class="pure-u-1">
                    <c:if test="${result != null}">
                        <div id="error-info" class="error-info">
                            <c:forEach items="${result}" var="r">
                                <p>${r}</p>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>
                <a id="reg-redirect" class="text-center" href="<c:url value="/registration"/>">Don't have an account?
                    Register here</a>
            </div>
        </div>
        <div class="pure-u-1">
            <div class="info-block">
                <p>
                    To post reviews and rate movies you need to sign in.
                </p>
                <p>
                    You don't need to register to see movies list, watch trailers and read description.
                </p>
                <p>
                    <a id="home-link" href="<c:url value="/movies"/>">
                        Click this message to proceed to movies list without registration.
                    </a>
                </p>
            </div>
        </div>
    </div>
</div>
</body>
</html>