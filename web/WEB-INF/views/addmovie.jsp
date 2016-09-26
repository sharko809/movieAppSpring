<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<html>
<head>
    <title>Add new movie</title>
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
    <link rel="stylesheet" href="<c:url value="/resources/css/admin.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/mainPage.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/movie.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/xs-screen.css"/>">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="padding-top"></div>
<jsp:include page="adminmenu.jsp"/>
<div class="pure-g custom-margin">
    <div class="pure-u-4-5 centered inline-flex">
        <div class="pure-u-md-1-2 pure-u-sm-1-2 centered" style="margin-top: 20px;">
            <sf:form class="pure-form" method="post" modelAttribute="movie">
                <fieldset class="pure-group">
                    <sf:input path="movieName" class="pure-input-1-2" minlength="1" maxlength="60" cssErrorClass="error-input"
                           placeholder="Title" required="true" style="width: 100%;"/>
                    <sf:input path="director" class="pure-input-1-2" minlength="1" maxlength="40" cssErrorClass="error-input"
                           placeholder="Director" style="width: 100%;"/>
                    <sf:input path="releaseDate" type="date" class="pure-input-1-2" min="1890-01-01" cssErrorClass="error-input"
                           max="2150-01-01" placeholder="Release date" style="width: 100%;"/>
                </fieldset>
                <fieldset class="pure-group">
                    <sf:input path="posterURL" type="url" class="pure-input-1-2" cssErrorClass="error-input"
                           minlength="7" maxlength="255" placeholder="Poster URL" style="width: 100%;"/>
                    <sf:input path="trailerURL" type="url" class="pure-input-1-2" minlength="7" cssErrorClass="error-input"
                           maxlength="255" placeholder="Trailer URL" style="width: 100%;"/>
                    <sf:textarea path="description" class="pure-input-1-2" minlength="5" maxlength="2000" cssErrorClass="error-input"
                              placeholder="Description" required="true" style="width: 100%;"/>
                </fieldset>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="pure-button pure-input-1-2 pure-button-primary" style="width: 100%;">
                    Add movie
                </button>
                <sf:errors path="*" element="p" cssClass="error-info"/>
            </sf:form>
        </div>
    </div>
    <div class="pure-u-1 centered">
        <c:if test="${success ne null}">
            <div class="success-info">
                <p>${success}</p>
            </div>
        </c:if>
    </div>
</div>
<script src="<c:url value="/resources/js/vendor/pure/ui.js"/>" type="text/javascript"></script>
</body>
</html>