<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<html>
<head>
    <title>${movie.movieName}</title>
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
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/movie.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/xs-screen.css"/>">
    <script src="<c:url value="/resources/js/vendor/jquery-3.1.0.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/redirect-url.js"/>"></script>
    <script src="<c:url value="/resources/js/reset-variables.js"/>"></script>
</head>
<body class="body-style">
<jsp:include page="header.jsp"/>
<div class="padding-top"></div>
<div class="pure-g">
    <div class="pure-u-md-3-4 pure-u-sm-1 centered">
        <c:if test="${pageContext.request.remoteUser ne null}">
            <sec:authentication var="userRole" property="principal.authorities"/>
            <c:if test="${userRole eq '[ROLE_ADMIN]'}">
                <div class="pure-g">
                    <button type="button" class="pure-button" style="margin: 5px 0 0 5px;"
                            onclick="window.location = 'admin/editmovie?movieID=${movieContainer.movie.id}'">Edit movie
                    </button>
                </div>
            </c:if>
        </c:if>
        <div id="movie-content" class="pure-u-1 inline-flex" style="margin-top: 15px; margin-bottom: 10px;">
            <div class="pure-u-md-1-3 pure-u-sm-1" style="margin: 5px;">
                <c:choose>
                    <c:when test="${movieContainer.movie.posterURL ne null && !movieContainer.movie.posterURL.isEmpty()}">
                        <img class="pure-img" src="${movieContainer.movie.posterURL}"/>
                    </c:when>
                    <c:otherwise>
                        <img class="pure-img" src="<c:url value="/resources/images/no-poster-available.png"/>"/>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="info-class pure-u-md-2-3 pure-u-sm-1" style="margin: 5px;">
                <div class="pure-u-1">
                    <div>
                        <h4 class="inline">Title: </h4>
                        <span class="remove-link-style">${movieContainer.movie.movieName}</span>
                    </div>
                    <div>
                        <h4 class="inline">Director: </h4>
                        <span>${movieContainer.movie.director}</span>
                    </div>
                    <div>
                        <h4 class="inline">Release Date: </h4>
                        <span>${movieContainer.movie.releaseDate}</span>
                    </div>
                    <div>
                        <h4 class="inline">Rating: </h4>
                        <span>${movieContainer.movie.rating}</span>
                    </div>
                    <div>
                        <h4 class="inline">Description:</h4>
                        <span>${movieContainer.movie.description}</span>
                    </div>
                </div>
                <div class="pure-u-1 videoWrapper">
                    <iframe src="${movieContainer.movie.trailerURL ne null ? movieContainer.movie.trailerURL : "/resources/images/no-trailer.jpg"}"
                            frameborder="0" allowfullscreen></iframe>
                </div>
            </div>
        </div>
        <div class="pure-u-1" style="margin: 5px;">
            <div class="review-section">
                <c:choose>
                    <c:when test="${pageContext.request.remoteUser eq null}">
                        <div class="pure-u-md-1 pure-u-sm-1">
                            <div style="margin-bottom: 5px; text-align: center">
                                Please, sign in to write reviews.
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <sf:form class="pure-form" method="post" modelAttribute="postedReview">
                            <sf:errors path="*" element="p" cssClass="error-info"/>
                            <div class="pure-u-1 inline-flex">
                                <div class="pure-u-6-8 max-width" style="margin-top: 7px;">
                                    <sf:input path="title" class="max-width" type="text" cssErrorClass="error-input"
                                              placeholder="Review title. You can SHORTLY describe your impression."/>
                                </div>
                                <div class="pure-u-1-8">
                                    <div class="center-text">
                                        <p>
                                            Rating:
                                        </p>
                                    </div>
                                </div>
                                <div class="pure-u-1-8" style="margin-top: 7px;">
                                    <sf:select path="rating" class="max-width" cssErrorClass="error-input">
                                        <sf:option value="1">1</sf:option>
                                        <sf:option value="2">2</sf:option>
                                        <sf:option value="3">3</sf:option>
                                        <sf:option value="4">4</sf:option>
                                        <sf:option value="5" selected="true">5</sf:option>
                                        <sf:option value="6">6</sf:option>
                                        <sf:option value="7">7</sf:option>
                                        <sf:option value="8">8</sf:option>
                                        <sf:option value="9">9</sf:option>
                                        <sf:option value="10">10</sf:option>
                                    </sf:select>
                                </div>
                            </div>
                            <div class="pure-u-1" style="height: 200px; margin-bottom: 5px;">
                                <sf:textarea path="reviewText" style="height: 100%;" class="max-width"
                                             cssErrorClass="error-input" placeholder="Your review"/>
                                <sf:input path="movieId" type="hidden" value="${movieContainer.movie.id}"/>
                                    <%--<input type="hidden" id="redirectFrom" name="redirectFrom" value=""/>--%>
                            </div>
                            <div class="pure-u-1 inline-flex">
                                <div>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <button id="submitReview" class="pure-button" type="submit">Post review</button>
                                </div>
                            </div>
                        </sf:form>
                    </c:otherwise>
                </c:choose>
                <c:forEach items="${movieContainer.reviews}" var="review">
                    <div class="review pure-u-md-1 max-width" style="margin-bottom: 5px;">
                        <div class="pure-u-md-1 inline-flex max-width" style="margin: 5px;">
                            <div class="pure-u-md-6-8 pure-u-sm-6-12 max-width">
                                <strong>${review.title}</strong> by ${movieContainer.users.get(review.userId)}
                            </div>
                            <div class="pure-u-md-1-8 pure-u-sm-4-12" style="text-align: right; width: 40%;">
                                Posted: ${review.postDate}
                            </div>
                            <div class="pure-u-md-1-8 pure-u-sm-2-12"
                                 style="text-align: center; width: 26%; margin-right: 5px;">
                                Rated: ${review.rating}/10
                            </div>
                        </div>
                        <div class="pure-u-md-1 max-width" style="margin: 5px;">
                            <div class="pure-u-md-1 pure-u-sm-1 max-width">
                                    ${review.reviewText}
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>