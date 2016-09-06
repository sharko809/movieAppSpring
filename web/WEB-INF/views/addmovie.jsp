<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add new movie</title>
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
    <link rel="stylesheet" href="/resources/css/admin.css">
    <link rel="stylesheet" href="/resources/css/mainPage.css">
    <link rel="stylesheet" href="/resources/css/movie.css">
    <link rel="stylesheet" href="/resources/css/xs-screen.css">
    <script src="/resources/js/reset-variables.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="padding-top"></div>
<jsp:include page="adminmenu.jsp"/>
<div class="pure-g custom-margin">
    <div class="pure-u-4-5 centered inline-flex">
        <div class="pure-u-md-1-2 pure-u-sm-1-2 centered" style="margin-top: 20px;">
            <form class="pure-form" method="post" action="/admin/addmovie">
                <fieldset class="pure-group">
                    <input id="title" name="title" type="text" class="pure-input-1-2" minlength="1" maxlength="30"
                           placeholder="Title" required style="width: 100%;"/>
                    <input id="director" name="director" type="text" class="pure-input-1-2" minlength="1" maxlength="30"
                           placeholder="Director" style="width: 100%;"/>
                    <input id="releaseDate" name="releaseDate" type="date" class="pure-input-1-2" min="1890-01-01"
                           max="2150-01-01" placeholder="Release date" style="width: 100%;"/>
                </fieldset>
                <fieldset class="pure-group">
                    <input id="posterUrl" name="posterUrl" type="url" class="pure-input-1-2"
                           minlength="7" maxlength="255" placeholder="Poster URL" style="width: 100%;"/>
                    <input id="trailerUrl" name="trailerUrl" type="url" class="pure-input-1-2" minlength="7"
                           maxlength="255" placeholder="Trailer URL" style="width: 100%;"/>
                    <textarea id="description" name="description" class="pure-input-1-2" minlength="5" maxlength="2000"
                              placeholder="Description" required style="width: 100%;"></textarea>
                </fieldset>
                <button type="submit" class="pure-button pure-input-1-2 pure-button-primary" style="width: 100%;">Add
                    movie
                </button>
                <c:if test="${movie ne null}">
                    <script type="text/javascript">
                        setMovieInputs('${movie.movieName}', '${movie.director}','${movie.releaseDate}',
                                '${movie.posterURL}', '${movie.trailerURL}', '${movie.description}');
                    </script>
                </c:if>
            </form>
        </div>
    </div>
    <div class="pure-u-1 centered">
        <c:if test="${result ne null}">
            <div id="error-info" class="error-info">
                <c:forEach items="${result}" var="r">
                    <p>${r}</p>
                </c:forEach>
            </div>
        </c:if>
    </div>
</div>
<script src="/resources/js/vendor/pure/ui.js" type="text/javascript"></script>
</body>
</html>
