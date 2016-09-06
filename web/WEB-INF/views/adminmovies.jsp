<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Movie management</title>
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
    <link rel="stylesheet" href="/resources/css/pages.css">
    <link rel="stylesheet" href="/resources/css/xs-screen.css">
    <script src="/resources/js/vendor/jquery-3.1.0.min.js" type="text/javascript"></script>
    <script src="/resources/js/admin-redirect-url.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="padding-top"></div>
<jsp:include page="adminmenu.jsp"/>
<div class="pure-g custom-margin">
    <div class="pure-u-md-3-4 pure-u-sm-1 centered">
        <c:choose>
            <c:when test="${movies ne null}">
                <c:choose>
                    <c:when test="${movies.size() ge 1}">
                        <c:forEach items="${movies}" var="movie">
                            <div class="pure-u-1" style="height: 100px; margin: 10px;">
                                <div class="movie-container inline-flex"
                                     style="background-color: #cad2d3; border-radius: 5px; height: inherit;">
                                    <div class="pure-u-lg-11-24 pure-u-sm-2-5" style="margin: 15px; width: 100%;">
                                        <div>
                                            <h4 class="inline">Title: </h4>
                                            <a class="remove-link-style" href="/movies?movieId=${movie.id}">
                                                    ${movie.movieName}
                                            </a>
                                        </div>
                                        <div>
                                            <h4 class="inline">Director: </h4>
                                            <span>${movie.director}</span>
                                        </div>
                                        <div>
                                            <h4 class="inline">Release Date: </h4>
                                            <span>${movie.releaseDate}</span>
                                        </div>
                                        <div>
                                            <h4 class="inline">Rating: </h4>
                                            <c:choose>
                                                <c:when test="${movie.rating le 0.0}">
                                                    <span>Not enough votes</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span>${movie.rating}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="pure-u-lg-11-24 pure-u-sm-2-5" style="width: 40%;">
                                        <c:choose>
                                            <c:when test="${movie.posterURL ne null && !movie.posterURL.isEmpty()}">
                                                <img style="height: 100px; float: right; margin-right: 10px;" class="pure-img"
                                                     src="${movie.posterURL}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <img style="height: 100px; float: right; margin-right: 10px;" class="pure-img"
                                                     src="/resources/images/no-poster-available.png"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="pure-u-lg-2-24 pure-u-sm-1-5" style="margin: 8px; width: 15%;">
                                        <form method="post" action="/admin/updrating">
                                            <input type="hidden" id="redirectFrom" name="redirectFrom" value=""/>
                                            <input type="hidden" name="movieID" value="${movie.id}"/>
                                            <button type="submit" class="pure-button" title="Recalculates movie rating" style="width: 100%; overflow: hidden;">Rating</button>
                                        </form>
                                        <form method="get" action="/admin/editmovie">
                                            <input type="hidden" name="movieID" value="${movie.id}"/>
                                            <button class="pure-button" title="Allows to edit movie data" style="width: 100%; overflow: hidden;">Edit</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="pure-u-1">
                            <div id="empty-set" style="margin-top: 10px;">
                                <p>No movies found</p>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <div class="pure-u-1">
                    <div id="empty-set" style="margin-top: 10px;">
                        <p>No movies found</p>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
        <c:if test="${movies ne null}">
            <c:if test="${movies.size() ge 1 and numberOfPages gt 1}">
                <div class="pure-g">
                    <div class="pure-u-3-4 centered">
                        <div id="pages" class="pure-g">
                            <div class="pure-u centered inline-flex">
                                <div class="page-number inline-flex">
                                    <c:if test="${currentPage ne 1}">
                                        <c:if test="${numberOfPages gt 10}">
                                            <p>
                                                <a class="page-link" href="/admin/managemovies?page=1" style="margin-right: 5px;">First</a>
                                            </p>
                                        </c:if>
                                        <p>
                                            <a class="page-link" href="/admin/managemovies?page=${currentPage - 1}">Prev</a>
                                        </p>
                                    </c:if>
                                </div>
                                <c:forEach begin="${(numberOfPages gt 10) ? (currentPage gt 5 ? (currentPage - 5) : 1) : 1}" end="${(numberOfPages gt 10) ? (currentPage + 5) : numberOfPages}" var="i">
                                    <c:if test="${i le numberOfPages}">
                                        <div class="page-number">
                                            <c:choose>
                                                <c:when test="${currentPage eq i}">
                                                    <p class="page-current">${i}</p>
                                                </c:when>
                                                <c:otherwise>
                                                    <p>
                                                        <a class="page-link" href="/admin/managemovies?page=${i}">${i}</a>
                                                    </p>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </c:if>
                                </c:forEach>
                                <div class="page-number inline-flex">
                                    <c:if test="${currentPage ne numberOfPages}">
                                        <p>
                                            <a class="page-link" href="/admin/managemovies?page=${currentPage + 1}">Next</a>
                                        </p>
                                        <c:if test="${numberOfPages gt 10}">
                                            <p>
                                                <a class="page-link" href="/admin/managemovies?page=${numberOfPages}" style="margin-left: 5px;">Last</a>
                                            </p>
                                        </c:if>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <div id="pages-sm" class="pure-g">
                            <div class="pure-u-1 inline-flex">
                                <div class="pure-u-1-4 centered">
                                    <select style="margin: 10px;" class="page-select" onchange="javascript:goToPage(this, '/admin/managemovies?page=')">
                                        <c:forEach begin="1" end="${numberOfPages}" var="i">
                                            <option ${currentPage eq i ? 'selected' : ''}>${i}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
        </c:if>
    </div>
</div>
<script src="/resources/js/pages-sm.js"></script>
</body>
</html>
