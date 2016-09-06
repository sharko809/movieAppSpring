<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="pure-g header-menu fixed z-index">
    <link rel="stylesheet" type="text/css" href="/resources/css/header.css">
    <script src="/resources/js/vendor/jquery-3.1.0.min.js" type="text/javascript"></script>
    <script src="/resources/js/redirect-url.js" type="text/javascript"></script>
    <script src="/resources/js/header.js" type="text/javascript"></script>
    <div class="pure-u-lg-4-5 pure-u-md-11-12 pure-u-sm-1 centered inline-flex">
        <div class="pure-u-1 inline-flex">
            <c:choose>
                <c:when test="${user eq null}">
                    <div id="login-form" class="pure-u-md-3-8 pure-u-sm-3-8 max-width">
                        <form class="pure-form inline-flex" method="post" action="/login"
                              style="margin: 5px auto 5px auto;">
                            <input class="pure-input-1-2 input-margin" type="text" name="userLogin" minlength="3"
                                   maxlength="60" placeholder="E-mail" required/><br/>
                            <input class="pure-input-1-2 input-margin" type="password" name="userPassword" minlength="3"
                                   maxlength="15" placeholder="Password" required/><br/>
                            <input type="hidden" id="redirectFrom" name="redirectFrom" value=""/>
                            <input type="image" src="/resources/icons/login-32.ico" alt="Submit"/>
                        </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <div id="user-welcome" class="pure-u-md-3-8 pure-u-sm-3-8 pure-u-md-3-10 max-width">
                        <div class="pure-u-1 inline-flex">
                            <p id="p-user" class="pure-u-1-2 menu-text p-item">
                                <%--<span id="login-text">--%>
                                    <%--Logged in as--%>
                                <%--</span>--%>
                                <a id="loggedInName" class="menu-text" href="/account?id=${user.id}">
                                    <c:if test="${user.isAdmin()}">
                                        <script type="text/javascript">
                                            setAdminColor();
                                        </script>
                                    </c:if>
                                        ${user.name}
                                </a>
                            </p>
                            <div id="menu-sm" class="pure-u-1-2 menu-text">
                                <div class="dropdown">
                                    <%--<span>--%>
                                        More
                                    <%--</span>--%>
                                    <div class="dropdown-content">
                                        <a class="menu-text" style="text-align: center;" href="/toprated">
                                            <p>
                                                Top rated
                                            </p>
                                        </a>
                                        <a class="menu-text" style="text-align: center;" href="/home">
                                            <p>
                                                Movie list
                                            </p>
                                        </a>
                                        <c:if test="${user.isAdmin()}">
                                            <a class="menu-text" style="text-align: center;" href="/admin">
                                                <p>
                                                    Admin panel
                                                </p>
                                            </a>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <c:if test="${user.isAdmin()}">
                                <a id="admin-link" class="pure-u-1-2 menu-text" href="/admin"
                                   style="text-align: center;">
                                    <p class="p-item" style="width: 90px; margin-left: 20px; margin-right: 20px;">
                                        Admin panel
                                    </p>
                                </a>
                            </c:if>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
            <div id="top-rated" class="pure-u-md-1-8 pure-u-sm-1-8 pure-u-md-1-10" style="text-align: center; width: 50%;">
                <a class="menu-text" href="/toprated">
                    <p class="p-item">
                        Top rated
                    </p>
                </a>
            </div>
            <div id="movie-list" class="pure-u-md-1-8 pure-u-sm-1-8 pure-u-md-1-10" style="text-align: center; width: 50%;">
                <a class="menu-text" href="/home">
                    <p class="p-item">
                        Movie list
                    </p>
                </a>
            </div>
            <form class="pure-u-md-2-8 pure-u-sm-2-8 pure-u-md-2-10 float-search" method="get" action="/search">
                <input id="searchInput" name="searchInput" type="text" class="search-style" maxlength="30"
                       placeholder="Search movie">
            </form>
            <c:if test="${user ne null}">
                <div class="pure-u-md-1-8 pure-u-sm-1-8 pure-u-md-1-10 logout">
                    <form method="post" action="/logout">
                        <input title="Logout" type="image" src="/resources/icons/logout-32.ico" alt="Submit"/>
                    </form>
                </div>
            </c:if>
        </div>
    </div>
</header>