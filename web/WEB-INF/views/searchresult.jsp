<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Search results</title>
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
    <link rel="stylesheet" type="text/css" href="/resources/css/search.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/pages.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/xs-screen.css">
</head>
<body class="body-style">
<jsp:include page="header.jsp"/>
<div class="padding-top"></div>
<div class="pure-g">
    <div class="pure-u-3-4 centered">
        <div class="pure-u-1">
            <div style="margin: 20px 10px 10px 10px;">
                <strong>Search results for "${searchRequest}":</strong>
            </div>
        </div>
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
                    <div id="empty-set">
                        <p>No matches found for your request... Try something else.</p>
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
                                                <a class="page-link" href="/search?searchInput=${searchRequest}&page=1" style="margin-right: 5px;">First</a>
                                            </p>
                                        </c:if>
                                        <p>
                                            <a class="page-link" href="/search?searchInput=${searchRequest}&page=${currentPage - 1}">Prev</a>
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
                                                        <a class="page-link" href="/search?searchInput=${searchRequest}&page=${i}">${i}</a>
                                                    </p>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </c:if>
                                </c:forEach>
                                <div class="page-number inline-flex">
                                    <c:if test="${currentPage ne numberOfPages}">
                                        <p>
                                            <a class="page-link" href="/search?searchInput=${searchRequest}&page=${currentPage + 1}">Next</a>
                                        </p>
                                        <c:if test="${numberOfPages gt 10}">
                                            <p>
                                                <a class="page-link" href="/search?searchInput=${searchRequest}&page=${numberOfPages}" style="margin-left: 5px;">Last</a>
                                            </p>
                                        </c:if>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <div id="pages-sm" class="pure-g">
                            <div class="pure-u-1 inline-flex">
                                <div class="pure-u-1-4 centered">
                                    <select style="margin-bottom: 10px;" class="page-select" onchange="javascript:goToPage(this, '/search?searchInput=${searchRequest}&page=')">
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
