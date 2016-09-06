<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account info</title>
    <link rel="stylesheet" href="/resources/css/vendor/pure/pure-min.css">
    <link rel="stylesheet" href="/resources/css/vendor/pure/base-min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="/resources/css/vendor/pure/grids-responsive-old-ie-min.css">
    <![endif]-->
    <!--[if gt IE 8]><!-->
    <link rel="stylesheet" href="/resources/css/vendor/pure/grids-responsive-min.css">
    <!--<![endif]-->
    <link rel="stylesheet" type="text/css" href="/resources/css/mainPage.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/account.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/xs-screen.css">
    <script src="/resources/js/vendor/jquery-3.1.0.min.js" type="text/javascript"></script>
    <script src="/resources/js/redirect-url.js" type="text/javascript"></script>
</head>
<body class="body-style">
<jsp:include page="header.jsp"/>
<div class="padding-top"></div>
<div class="pure-g">
    <div class="pure-u-md-3-4 pure-u-sm-1 centered">
        <div class="pure-u-1">
            <c:if test="${user ne null}">
                <form class="pure-form pure-form-aligned" style="text-align: center;" method="post"
                      action="/updaccount">
                    <fieldset>
                        <div class="pure-control-group">
                            <label for="userName">Username: </label>
                            <div class="inline-flex">
                                <input type="text" id="userName" name="userName" minlength="1" maxlength="20"
                                       value="${thisUser.name}" style="margin-right: 5px;" readonly/>
                                <button id="editName" type="button" class="pure-button">Edit</button>
                            </div>
                        </div>
                        <div class="pure-control-group">
                            <label for="userLogin">Login: </label>
                            <div class="inline-flex">
                                <input type="email" id="userLogin" name="userLogin" minlength="3" maxlength="60"
                                       value="${thisUser.login}" style="margin-right: 5px;" readonly/>
                                <button id="editLogin" type="button" class="pure-button">Edit</button>
                            </div>
                        </div>
                        <div class="pure-control-group">
                            <label for="userPassword">Password: </label>
                            <div class="inline-flex">
                                <input type="password" id="userPassword" name="userPassword"
                                       title="If you want to change password - type new one here." minlength="3"
                                       maxlength="15"
                                       placeholder="Leave blank to keep old password" style="margin-right: 5px;"
                                       readonly/>
                                <button id="editPassword" type="button" class="pure-button">Edit</button>
                            </div>
                        </div>
                        <div class="pure-controls">
                            <input type="hidden" id="redirectFrom" name="redirectFrom" value=""/>
                            <button type="submit" class="pure-button">Update account</button>
                        </div>
                    </fieldset>
                </form>
            </c:if>
        </div>
        <div class="pure-u-1">
            <c:if test="${result ne null}">
                <c:forEach items="${result}" var="r">
                    <div id="error-info" class="error-info">
                        <p>${r}</p>
                    </div>
                </c:forEach>
            </c:if>
        </div>
    </div>
</div>
<script src="/resources/js/account-enable-input.js" type="text/javascript"></script>
</body>
</html>
