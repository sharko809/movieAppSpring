<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="pure-g header-menu fixed z-index">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/header.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/vendor/magnific-popup/magnific-popup.css"/>">
    <script src="<c:url value="/resources/js/vendor/jquery-3.1.0.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/redirect-url.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/header.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/vendor/magnific-popup/jquery.magnific-popup.min.js"/>"></script>
    <script src="<c:url value="/resources/js/login-popup.js"/>"></script>
    <div class="pure-u-lg-4-5 pure-u-md-11-12 pure-u-sm-1 centered inline-flex">
        <div class="pure-u-1 inline-flex">
            <c:choose>
                <c:when test="${pageContext.request.remoteUser eq null}">
                    <div class="pure-g" style="width: 70%;">
                        <div class="pure-u-1-2" style="margin-top: 14px;">
                            <a id="login-ref" class="popup-with-form menu-text" href="#test-form">Login</a>
                        </div>
                        <div class="pure-u-1-2" style="margin-top: 14px;">
                            <a id="registration-ref" class="menu-text"
                               href="<c:url value="/registration"/>">Registration</a>
                        </div>
                    </div>
                    <form id="test-form" class="white-popup-block mfp-hide pure-form pure-form-aligned text-center"
                          style="color: antiquewhite" method="post" action="<c:url value="/login"/>">
                        <h1>Please, fill form below to login</h1>
                        <fieldset style="border:0;">
                            <div class="pure-control-group">
                                <label for="login">Login</label>
                                <input id="login" name="login" type="email" placeholder="E-mail" minlength="3"
                                       maxlength="60" required>
                            </div>
                            <div class="pure-control-group">
                                <label for="password">Password</label>
                                <input id="password" name="password" type="password" placeholder="Password"
                                       minlength="3" maxlength="15" required>
                            </div>
                        </fieldset>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="hidden" id="redirect" name="redirect" value=""/>
                        <button class="pure-button" type="submit" title="Login">Login</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <p id="p-user" class="pure-u-1-2 menu-text p-item">
                        <sec:authentication var="principalId" property="principal.id"/>
                        <a id="loggedInName" class="menu-text" href="<c:url value="/account?id=${principalId}"/>">
                            <sec:authentication var="userRole" property="principal.authorities"/>
                            <c:if test="${userRole eq '[ROLE_ADMIN]'}">
                                <script type="text/javascript">
                                    setAdminColor();
                                </script>
                            </c:if>
                                ${pageContext.request.userPrincipal.principal.userName}
                        </a>
                    </p>
                </c:otherwise>
            </c:choose>
            <div id="user-welcome" class="pure-u-md-3-8 pure-u-sm-3-8 pure-u-md-3-10 max-width">
                <div class="pure-u-1 inline-flex">
                    <div id="menu-sm" class="pure-u-1-2 menu-text">
                        <div class="dropdown">
                            More
                            <div class="dropdown-content">
                                <a class="menu-text text-center" href="<c:url value="/toprated"/>">
                                    <p>
                                        Top rated
                                    </p>
                                </a>
                                <a class="menu-text text-center" href="<c:url value="/movies"/>">
                                    <p>
                                        Movie list
                                    </p>
                                </a>
                                <c:if test="${userRole eq '[ROLE_ADMIN]'}">
                                    <a class="menu-text text-center" href="<c:url value="/admin"/>">
                                        <p>
                                            Admin panel
                                        </p>
                                    </a>
                                </c:if>
                                <sec:authorize access="isAnonymous()">
                                    <a class="popup-with-form menu-text" href="#test-form">
                                        <p>Login</p>
                                    </a>
                                </sec:authorize>
                                <a class="menu-text" href="<c:url value="/registration"/>">
                                    <p>Registration</p>
                                </a>
                            </div>
                        </div>
                    </div>
                    <c:if test="${userRole eq '[ROLE_ADMIN]'}">
                        <a id="admin-link" class="pure-u-1-2 menu-text text-center"
                           href="<c:url value="/admin"/>">
                            <p class="p-item" style="width: 90px; margin-left: 20px; margin-right: 20px;">
                                Admin panel
                            </p>
                        </a>
                    </c:if>
                </div>
            </div>
            <div id="top-rated" class="pure-u-md-1-8 pure-u-sm-1-8 pure-u-md-1-10"
                 style="text-align: center; width: 50%;">
                <a class="menu-text" href="<c:url value="/toprated"/>">
                    <p class="p-item">
                        Top rated
                    </p>
                </a>
            </div>
            <div id="movie-list" class="pure-u-md-1-8 pure-u-sm-1-8 pure-u-md-1-10"
                 style="text-align: center; width: 50%;">
                <a class="menu-text" href="<c:url value="/movies"/>">
                    <p class="p-item">
                        Movie list
                    </p>
                </a>
            </div>
            <form class="pure-u-md-2-8 pure-u-sm-2-8 pure-u-md-2-10 float-search" method="get"
                  action="<c:url value="/search"/>">
                <input id="searchInput" name="searchInput" type="text" class="search-style" maxlength="30"
                       placeholder="Search movie">
            </form>
            <c:if test="${pageContext.request.remoteUser ne null}">
                <div class="pure-u-md-1-8 pure-u-sm-1-8 pure-u-md-1-10 logout">
                    <form method="post" action="<c:url value="/logout"/>">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input title="Logout" type="image" src="<c:url value="/resources/icons/logout-32.ico"/>"
                               alt="Submit"/>
                    </form>
                </div>
            </c:if>
        </div>
    </div>
</header>