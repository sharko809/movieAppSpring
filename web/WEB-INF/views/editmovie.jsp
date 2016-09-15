<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Edit movie ${movie.movieName}</title>
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
    <link rel="stylesheet" href="<c:url value="/resources/css/movie.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/xs-screen.css"/>">
    <script src="<c:url value="/resources/js/vendor/jquery-3.1.0.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/admin-redirect-url.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/reset-variables.js"/>" type="text/javascript"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="padding-top"></div>
<jsp:include page="adminmenu.jsp"/>
<div class="pure-g custom-margin">
    <div class="pure-u-4-5 centered">
        <sf:form id="movie-form" class="pure-form" method="post" modelAttribute="movie">
            <fieldset>
                <div class="pure-control-group" style="margin-bottom: 5px;">
                    <div class="inline-flex max-width">
                        <sf:input path="movieName" class="pure-input-1-2 max-width" minlength="1"
                                  cssErrorClass="error-input"
                                  maxlength="30" placeholder="Title" style="margin-right: 5px;" required="true"
                                  readonly="true"/>
                        <button id="editTitle" type="button" class="pure-button">Edit</button>
                    </div>
                </div>
                <div class="pure-control-group" style="margin-bottom: 5px;">
                    <div class="inline-flex max-width">
                        <sf:input path="director" class="pure-input-1-2 max-width" minlength="1"
                                  cssErrorClass="error-input"
                                  maxlength="30" placeholder="Director" style="margin-right: 5px;" readonly="true"/>
                        <button id="editDirector" type="button" class="pure-button">Edit</button>
                    </div>
                </div>
                <div class="pure-control-group" style="margin-bottom: 5px;">
                    <div class="inline-flex max-width">
                        <sf:input path="releaseDate" type="date" class="pure-input-1-2 max-width"
                                  cssErrorClass="error-input"
                                  min="1890-01-01" max="2150-01-01" placeholder="Release date"
                                  style="margin-right: 5px;" readonly="true"/>
                        <button id="editDate" type="button" class="pure-button">Edit</button>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="pure-control-group" style="margin-bottom: 5px;">
                    <div class="inline-flex max-width">
                        <sf:input path="posterURL" type="url" class="pure-input-1-2 max-width" minlength="7"
                                  cssErrorClass="error-input"
                                  maxlength="255" placeholder="Poster URL" style="margin-right: 5px;" readonly="true"/>
                        <button id="editPoster" type="button" class="pure-button">Edit</button>
                    </div>
                </div>
                <div class="pure-control-group" style="margin-bottom: 5px;">
                    <div class="inline-flex max-width">
                        <sf:input path="trailerURL" type="url" class="pure-input-1-2 max-width" minlength="7"
                                  cssErrorClass="error-input"
                                  maxlength="255" placeholder="Trailer URL" style="margin-right: 5px;" readonly="true"/>
                        <button id="editTrailer" type="button" class="pure-button">Edit</button>
                    </div>
                </div>
                <div class="pure-control-group" style="margin-bottom: 5px;">
                    <div class="inline-flex max-width">
                        <sf:textarea path="description" class="pure-input-1-2 max-width" minlength="5" maxlength="2000"
                                     cssErrorClass="error-input"
                                     placeholder="Description" style="margin-right: 5px;" required="true"
                                     readonly="true"/>
                        <button id="editDescription" type="button" class="pure-button">Edit</button>
                    </div>
                </div>
            </fieldset>
            <div class="pure-controls" style="text-align: center;">
                <sf:hidden path="id" value="${movie.id}"/>
                <sf:hidden path="rating" value="${movie.rating}"/>
                <input type="hidden" name="redirect" value="">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="pure-button pure-input-1-2 pure-button-primary">Update movie</button>
            </div>
            <sf:errors path="*" element="p" cssClass="error-info"/>
        </sf:form>
        <div class="pure-u-1 inline-flex">
            <span style="margin: 9px;">Rating: ${movie.rating}</span>
            <form method="post" action="<c:url value="/admin/managemovies"/>">
                <input type="hidden" name="redirect" value=""/>
                <input type="hidden" name="movieId" value="${movie.id}"/>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="pure-button" title="Recalculates movie rating"
                        style="width: 100%; overflow: hidden;">Rating
                </button>
            </form>
        </div>
        <div class="pure-u-1">
            <c:if test="${result ne null}">
                <div id="error-info" class="err">
                    <c:forEach items="${result}" var="r">
                        <p>${r}</p>
                    </c:forEach>
                </div>
            </c:if>
        </div>

        <c:forEach items="${reviews}" var="review">
            <div class="review pure-u-md-1 max-width" style="margin-bottom: 5px;">
                <div class="pure-u-md-1 inline-flex max-width" style="margin: 5px;">
                    <div class="pure-u-md-6-8 pure-u-sm-6-12 max-width">
                        <strong>${review.title}</strong>
                            ${(users.get(review.userId).isBanned()) ?
                                    ('<strong style="color: red;">' += users.get(review.userId).name += '</strong>')
                                    : users.get(review.userId).name}
                    </div>
                    <div class="pure-u-md-1-8 pure-u-sm-4-12" style="text-align: right; width: 40%;">
                        Posted: ${review.postDate}
                    </div>
                    <div class="pure-u-md-1-8 pure-u-sm-2-12"
                         style="text-align: center; width: 26%; margin-right: 5px;">
                        Rated: ${review.rating}/10
                    </div>
                </div>
                <div class="pure-u-md-1 max-width inline-flex" style="margin: 5px;">
                    <div class="pure-u-md-5-8 pure-u-sm-5-8 max-width" style="padding-top: 14px;">
                            ${review.reviewText}
                    </div>
                    <sec:authentication var="cUserId" property="principal.id"/>
                    <div class="pure-u-md-3-8 pure-u-sm-3-8 inline-flex" style="text-align: end; margin-right: 20px;">
                        <form method="post" action="<c:url value="/admin/ban"/>" style="margin: 2px; width: 100%;">
                            <input name="userId" type="hidden" value="${users.get(review.userId).id}"/>
                            <input name="redirect" type="hidden" value=""/>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button class="pure-button" type="submit"
                                ${cUserId eq users.get(review.userId).id ? 'disabled' : ''}>
                                    ${(users.get(review.userId).isBanned()) ? 'Unban' : 'Ban'}
                            </button>
                        </form>
                        <form method="post" action="<c:url value="/admin/delreview"/>" style="margin: 2px;">
                            <input name="reviewId" type="hidden" value="${review.id}"/>
                            <input name="redirect" type="hidden" value=""/>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button class="pure-button" type="submit">Delete</button>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<script src="<c:url value="/resources/js/editmovie-enable-inputs.js"/>" type="text/javascript"></script>
</body>
</html>