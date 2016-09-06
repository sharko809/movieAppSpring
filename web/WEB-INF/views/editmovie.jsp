<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit movie ${movie.movieName}</title>
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
    <link rel="stylesheet" href="/resources/css/movie.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/xs-screen.css">
    <script src="/resources/js/vendor/jquery-3.1.0.min.js" type="text/javascript"></script>
    <script src="/resources/js/admin-redirect-url.js" type="text/javascript"></script>
    <script src="/resources/js/reset-variables.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="padding-top"></div>
<jsp:include page="adminmenu.jsp"/>
<div class="pure-g custom-margin">
    <div class="pure-u-4-5 centered">
        <form id="movie-form" class="pure-form" method="post" action="/admin/editmovie">
            <fieldset>
                <div class="pure-control-group" style="margin-bottom: 5px;">
                    <div class="inline-flex max-width">
                        <input id="title" name="title" type="text" class="pure-input-1-2 max-width" minlength="1" maxlength="30"
                               placeholder="Title" value="${movie.movieName}" style="margin-right: 5px;" required readonly/>
                        <button id="editTitle" type="button" class="pure-button">Edit</button>
                    </div>
                </div>
                <div class="pure-control-group" style="margin-bottom: 5px;">
                    <div class="inline-flex max-width">
                        <input id="director" name="director" type="text" class="pure-input-1-2 max-width" minlength="1" maxlength="30"
                               placeholder="Director" value="${movie.director}" style="margin-right: 5px;" readonly/>
                        <button id="editDirector" type="button" class="pure-button">Edit</button>
                    </div>
                </div>
                <div class="pure-control-group" style="margin-bottom: 5px;">
                    <div class="inline-flex max-width">
                        <input id="releaseDate" name="releaseDate" type="date" class="pure-input-1-2 max-width" min="1890-01-01"
                               max="2150-01-01" placeholder="Release date" value="${movie.releaseDate}" style="margin-right: 5px;" readonly/>
                        <button id="editDate" type="button" class="pure-button">Edit</button>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="pure-control-group" style="margin-bottom: 5px;">
                    <div class="inline-flex max-width">
                        <input id="posterUrl" name="posterUrl" type="url" class="pure-input-1-2 max-width" minlength="7"
                               maxlength="255" placeholder="Poster URL" value="${movie.posterURL}" style="margin-right: 5px;" readonly/>
                        <button id="editPoster" type="button" class="pure-button">Edit</button>
                    </div>
                </div>
                <div class="pure-control-group" style="margin-bottom: 5px;">
                    <div class="inline-flex max-width">
                        <input id="trailerUrl" name="trailerUrl" type="url" class="pure-input-1-2 max-width" minlength="7"
                               maxlength="255" placeholder="Trailer URL" value="${movie.trailerURL}" style="margin-right: 5px;" readonly/>
                        <button id="editTrailer" type="button" class="pure-button">Edit</button>
                    </div>
                </div>
                <div class="pure-control-group" style="margin-bottom: 5px;">
                    <div class="inline-flex max-width">
                                            <textarea id="description" name="description" class="pure-input-1-2 max-width" minlength="5" maxlength="2000"
                                                      placeholder="Description" style="margin-right: 5px;" required readonly>${movie.description}</textarea>
                        <button id="editDescription" type="button" class="pure-button">Edit</button>
                    </div>
                </div>
            </fieldset>
            <c:if test="${updMovie ne null}">
                <script type="text/javascript">
                    setMovieInputs('${updMovie.movieName}', '${updMovie.director}', '${updMovie.releaseDate}', '${updMovie.posterURL}', '${updMovie.trailerURL}', '${updMovie.description}');
                </script>
            </c:if>
            <div class="pure-controls" style="text-align: center;">
                <input type="hidden" name="movieID" value="${movie.id}"/>
                <button type="submit" class="pure-button pure-input-1-2 pure-button-primary">Update movie</button>
            </div>
        </form>
        <div class="pure-u-1 inline-flex">
            <span style="margin: 9px;">Rating: ${movie.rating}</span>
            <form method="post" action="/admin/updrating">
                <input type="hidden" id="redirectFrom" name="redirectFrom" value=""/>
                <input type="hidden" name="movieID" value="${movie.id}"/>
                <button type="submit" class="pure-button" title="Recalculates movie rating" style="width: 100%; overflow: hidden;">Rating</button>
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
                            <c:choose>
                                <c:when test="${users.get(review.userId).isBanned()}">
                                    <strong style="color: red;">${users.get(review.userId).name}</strong>
                                </c:when>
                                <c:otherwise>
                                    ${users.get(review.userId).name}
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="pure-u-md-1-8 pure-u-sm-4-12" style="text-align: right; width: 40%;">
                            Posted: ${review.postDate}
                        </div>
                        <div class="pure-u-md-1-8 pure-u-sm-2-12" style="text-align: center; width: 26%; margin-right: 5px;">
                            Rated: ${review.rating}/10
                        </div>
                </div>
                <div class="pure-u-md-1 max-width inline-flex" style="margin: 5px;">
                    <div class="pure-u-md-5-8 pure-u-sm-5-8 max-width" style="padding-top: 14px;">
                            ${review.reviewText}
                    </div>
                    <div class="pure-u-md-3-8 pure-u-sm-3-8 inline-flex" style="text-align: end; margin-right: 20px;">
                        <form method="post" action="/admin/ban" style="margin: 2px; width: 100%;">
                            <input name="userID" type="hidden" value="${users.get(review.userId).id}"/>
                            <input name="fromMovie" type="hidden" value="fromMovie"/>
                            <input name="movieID" type="hidden" value="${movie.id}"/>
                            <button class="pure-button" type="submit">
                                <c:choose>
                                    <c:when test="${users.get(review.userId).isBanned()}">
                                        Unban
                                    </c:when>
                                    <c:otherwise>
                                        Ban
                                    </c:otherwise>
                                </c:choose>
                            </button>
                        </form>
                        <form method="post" action="/admin/delreview" style="margin: 2px;">
                            <input name="reviewID" type="hidden" value="${review.id}"/>
                            <input name="movieID" type="hidden" value="${movie.id}"/>
                            <button class="pure-button" type="submit">Delete</button>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<script src="/resources/js/editmovie-enable-inputs.js" type="text/javascript"></script>
</body>
</html>
