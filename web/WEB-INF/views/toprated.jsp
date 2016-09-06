<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Top Rated Movies</title>
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
    <link rel="stylesheet" type="text/css" href="/resources/css/xs-screen.css">
</head>
<body class="body-style">
<jsp:include page="header.jsp"/>
<div class="padding-top"></div>
<div class="pure-g">
    <div class="pure-u-3-4 centered">
        <c:choose>
            <c:when test="${movies ne null}">
                <c:choose>
                    <c:when test="${movies.size() ge 1}">
                        <c:forEach items="${movies}" var="movie">
                            <div class="pure-u-1">
                                <div class="movie-container">
                                    <div class="pure-u-lg-1-2 pure-u-sm-1 movie-info">
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
                                        <div>
                                            <h4 class="inline">Description:</h4>
                                            <span>${movie.description}</span>
                                        </div>
                                    </div>
                                    <div class="pure-u-lg-1-2 pure-u-sm-1 movie-trailer">
                                        <iframe width="100%" height="315" src="${movie.trailerURL ne null ? movie.trailerURL : "/resources/images/no-trailer.jpg"}" frameborder="0"
                                                allowfullscreen></iframe>
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
    </div>
</div>
</body>
</html>
