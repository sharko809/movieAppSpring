<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${movie.movieName}</title>
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
    <link rel="stylesheet" type="text/css" href="/resources/css/movie.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/xs-screen.css">
    <script src="/resources/js/vendor/jquery-3.1.0.min.js" type="text/javascript"></script>
    <script src="/resources/js/redirect-url.js"></script>
    <script src="/resources/js/reset-variables.js"></script>
</head>
<body class="body-style">
<jsp:include page="header.jsp"/>
<div class="padding-top"></div>
<div class="pure-g">
    <div class="pure-u-md-3-4 pure-u-sm-1 centered">
        <c:if test="${user ne null}">
            <c:if test="${user.isAdmin()}">
                <div class="pure-g">
                    <button type="button" class="pure-button" style="margin: 5px 0px 0px 5px;" onclick="window.location = 'admin/editmovie?movieID=${movie.id}'">Edit movie</button>
                </div>
            </c:if>
        </c:if>
        <div id="movie-content" class="pure-u-1 inline-flex" style="margin-top: 15px; margin-bottom: 10px;">
            <div class="pure-u-md-1-3 pure-u-sm-1" style="margin: 5px;">
                <c:choose>
                    <c:when test="${movie.posterURL ne null && !movie.posterURL.isEmpty()}">
                        <img class="pure-img" src="${movie.posterURL}"/>
                    </c:when>
                    <c:otherwise>
                        <img class="pure-img" src="/resources/images/no-poster-available.png"/>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="info-class pure-u-md-2-3 pure-u-sm-1" style="margin: 5px;">
                <div class="pure-u-1">
                    <div>
                        <h4 class="inline">Title: </h4>
                        <span class="remove-link-style">${movie.movieName}</span>
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
                        <span>${movie.rating}</span>
                    </div>
                    <div>
                        <h4 class="inline">Description:</h4>
                        <span>${movie.description}</span>
                    </div>
                </div>

                <div class="pure-u-1 videoWrapper">
                    <iframe src="${movie.trailerURL ne null ? movie.trailerURL : "/resources/images/no-trailer.jpg"}" frameborder="0" allowfullscreen></iframe>
                </div>

            </div>
        </div>
        <div class="pure-u-1" style="margin: 5px;">
            <div class="review-section">
                <c:if test="${reviewError.size() ge 1}">
                    <div id="error-info">
                        <c:forEach items="${reviewError}" var="error">
                            <p class="error-paragraph">${error}</p>
                        </c:forEach>
                    </div>
                </c:if>
                <c:choose>
                    <c:when test="${user eq null}">
                        <div class="pure-u-md-1 pure-u-sm-1">
                            <div style="margin-bottom: 5px; text-align: center">
                                Please, sign in to write reviews.
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <form class="pure-form" method="post" action="/postreview">
                            <div class="pure-u-1 inline-flex">
                                <div class="pure-u-6-8 max-width" style="margin-top: 7px;">
                                    <input id="reviewTitle" class="max-width" type="text" name="reviewTitle" placeholder="Review title. You can SHORTLY describe your impression."/>
                                </div>
                                <div class="pure-u-1-8">
                                    <div class="center-text">
                                        <p>
                                            Rating:
                                        </p>
                                    </div>
                                </div>
                                <div class="pure-u-1-8" style="margin-top: 7px;">
                                    <select id="rating" class="max-width" name="userRating">
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                        <option value="5" selected>5</option>
                                        <option value="6">6</option>
                                        <option value="7">7</option>
                                        <option value="8">8</option>
                                        <option value="9">9</option>
                                        <option value="10">10</option>
                                    </select>
                                </div>
                            </div>
                            <div class="pure-u-1" style="height: 200px; margin-bottom: 5px;">
                                <textarea id="reviewText" style="height: 100%;" class="max-width" name="reviewText" placeholder="Your review"></textarea>
                                <input type="hidden" name="movieID" value="${movie.id}"/>
                                <input type="hidden" id="redirectFrom" name="redirectFrom" value=""/>
                            </div>
                            <div class="pure-u-1 inline-flex">
                                <div>
                                    <button id="submitReview" class="pure-button" type="submit">Post review</button>
                                </div>
                            </div>
                            <c:if test="${review ne null}">
                                <script type="text/javascript">
                                    setReviewInputs('${review.title}','${review.rating}','${review.reviewText}');
                                </script>
                            </c:if>
                        </form>
                    </c:otherwise>
                </c:choose>
                <c:forEach items="${reviews}" var="review">
                    <div class="review pure-u-md-1 max-width" style="margin-bottom: 5px;">
                        <div class="pure-u-md-1 inline-flex max-width" style="margin: 5px;">
                            <div class="pure-u-md-6-8 pure-u-sm-6-12 max-width">
                                <strong>${review.title}</strong> by ${users.get(review.userId)}
                            </div>
                            <div class="pure-u-md-1-8 pure-u-sm-4-12" style="text-align: right; width: 40%;">
                                Posted: ${review.postDate}
                            </div>
                            <div class="pure-u-md-1-8 pure-u-sm-2-12" style="text-align: center; width: 26%; margin-right: 5px;">
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