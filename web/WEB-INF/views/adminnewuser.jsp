<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create new user</title>
    <link rel="stylesheet" href="/resources/css/vendor/pure/pure-min.css">
    <link rel="stylesheet" href="/resources/css/vendor/pure/base-min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="/resources/css/vendor/pure/grids-responsive-old-ie-min.css">
    <link rel="stylesheet" href="/resources/css/vendor/pure/layouts/side-menu-old-ie.css">
    <![endif]-->
    <!--[if gt IE 8]><!-->
    <link rel="stylesheet" href="/resources/css/vendor/pure/grids-responsive-min.css">
    <link rel="stylesheet" href="/resources/css/vendor/pure/layouts/side-menu.css">
    <!--<![endif]-->
    <link rel="stylesheet" href="/resources/css/mainPage.css">
    <link rel="stylesheet" href="/resources/css/admin.css">
    <link rel="stylesheet" href="/resources/css/adminReg.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/xs-screen.css">
    <script src="/resources/js/reset-variables.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="padding-top"></div>
<jsp:include page="adminmenu.jsp"/>
<div class="pure-g custom-margin">
    <div class="pure-u-md-1-2 pure-u-sm-1-2 centered" style="text-align: center;">
        <form class="pure-form pure-form-aligned" method="post" action="/admin/newuser">
            <fieldset>
                <div class="pure-control-group">
                    <label for="newUserName">Nickname: </label>
                    <input id="newUserName" type="text" name="newUserName" minlength="1" maxlength="20" placeholder="Name to display" required/>
                </div>
                <div class="pure-control-group">
                    <label for="newUserLogin">Your email:</label>
                    <input id="newUserLogin" type="email" name="newUserLogin" minlength="3" maxlength="60" placeholder="E-mail" required/>
                </div>
                <div class="pure-control-group">
                    <label for="newUserPassword">Enter password: </label>
                    <input id="newUserPassword" type="password" name="newUserPassword" minlength="3" maxlength="15" placeholder="Password" required/>
                </div>
                <div class="pure-controls">
                    <label for="isAdmin" class="pure-checkbox" style="margin: 15px;">
                        <input id="isAdmin" name="isAdmin" type="checkbox">Is Admin
                    </label>
                    <input class="pure-button" type="submit" value="Register">
                </div>
            </fieldset>
            <c:if test="${!regUser.name.isEmpty()}">
                <script type="text/javascript">
                    setRegistrationInputs('${regUser.name}', '${regUser.login}');
                </script>
            </c:if>
        </form>
    </div>
    <div class="pure-u-1">
        <c:if test="${result.size() ge 1}">
            <div id="error-info" class="error-info">
                <c:forEach items="${result}" var="r">
                    <p>${r}</p>
                </c:forEach>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>
