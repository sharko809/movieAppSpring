<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="pure-g header-menu fixed z-index">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/header.css"/>">
    <div class="pure-u-lg-4-5 pure-u-md-11-12 pure-u-sm-1 centered inline-flex">
        <div class="pure-u-1 inline-flex">
            <div id="user-welcome" class="pure-u-md-1-5 pure-u-sm-1-5">
                <div id="menu-sm" class="pure-u-1-2 menu-text">
                    <div class="dropdown">
                        More
                        <div class="dropdown-content">
                            <a class="menu-text text-center" href="<c:url value="/toprated"/>">
                                <p>Top rated</p>
                            </a>
                            <a class="menu-text text-center" href="<c:url value="/movies"/>">
                                <p>Movie list</p>
                            </a>
                            <a class="menu-text" href="<c:url value="/registration"/>">
                                <p>Registration</p>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div id="top-rated" class="pure-u-md-1-5 pure-u-sm-1-5 text-center" style="width: 50%;">
                <a class="menu-text" href="<c:url value="/toprated"/>">
                    <p class="p-item">Top rated</p>
                </a>
            </div>
            <div id="movie-list" class="pure-u-md-1-5 pure-u-sm-1-5 text-center" style="width: 50%;">
                <a class="menu-text" href="<c:url value="/movies"/>">
                    <p class="p-item">Movie list</p>
                </a>
            </div>
            <form class="pure-u-md-2-5 pure-u-sm-2-5 float-search" method="get" action="<c:url value="/search"/>">
                <input id="searchInput" name="searchInput" type="text" class="search-style" maxlength="30"
                       placeholder="Search movie">
            </form>
        </div>
    </div>
</header>