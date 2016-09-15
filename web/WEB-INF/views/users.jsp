<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Users</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/vendor/pure/pure-min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/vendor/pure/base-min.css"/>">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="/resources/css/vendor/pure/grids-responsive-old-ie-min.css">
    <link rel="stylesheet" href="/resources/css/vendor/pure/layouts/side-menu-old-ie.css">
    <![endif]-->
    <!--[if gt IE 8]><!-->
    <link rel="stylesheet" href="<c:url value="/resources/css/vendor/pure/grids-responsive-min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/vendor/pure/layouts/side-menu.css"/>">
    <!--<![endif]-->
    <link rel="stylesheet" href="<c:url value="/resources/css/mainPage.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/admin.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/pages.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/usersadmin.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/xs-screen.css"/>">
    <script src="<c:url value="/resources/js/vendor/jquery-3.1.0.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/admin-redirect-url.js"/>" type="text/javascript"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="padding-top"></div>
<jsp:include page="adminmenu.jsp"/>
<div class="pure-g custom-margin">
    <div class="pure-u-3-4 centered">
        <div class="pure-u-1">
            <div class="inline-flex">
                <form id="sort-form" method="get">
                    <label for="id">ID</label>
                    <input id="id" type="radio" name="sortBy" value="id" ${sortBy eq 'id' ? 'checked' : ''}>
                    <label for="login">Login</label>
                    <input id="login" type="radio" name="sortBy" value="login" ${sortBy eq 'login' ? 'checked' : ''}>
                    <label for="username">Name</label>
                    <input id="username" type="radio" name="sortBy" value="username"
                    ${sortBy eq 'username' ? 'checked' : ''}>
                    <label for="isadmin">Admin</label>
                    <input id="isadmin" type="radio" name="sortBy" value="isadmin"
                    ${sortBy eq 'isadmin' ? 'checked' : ''}>
                    <label for="isbanned">Banned</label>
                    <input id="isbanned" type="radio" name="sortBy" value="isbanned"
                    ${sortBy eq 'isbanned' ? 'checked' : ''}>
                    <label for="isDesc">Descending</label>
                    <input id="isDesc" type="checkbox" name="isDesc" value="1" ${isDesc eq '1' ? 'checked' : ''}>
                    <input type="hidden" name="page" value="${currentPage}">
                    <%--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
                    <button id="sort-button" class="pure-button" type="submit">Sort</button>
                </form>
            </div>
        </div>
        <sec:authentication var="cUserId" property="principal.id"/>
        <table id="users-table" class="pure-table pure-table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Login</th>
                <th>Username</th>
                <th>Admin</th>
                <th>Banned</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${users ne null}">
                    <c:choose>
                        <c:when test="${users.size() ge 1}">
                            <c:forEach items="${users}" var="user">
                                <tr>
                                    <td>${user.id}</td>
                                    <td>${user.login}</td>
                                    <td>${user.name}</td>
                                    <td>
                                        <form class="no-margin" method="post" action="<c:url value="/admin/adminize"/>">
                                            <input type="hidden" name="userId" value="${user.id}"/>
                                            <input type="hidden" name="redirect" value=""/>
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <button class="pure-button max-width"
                                                    ${user.isAdmin() ? 'style="background-color: #7FFF00;"' : ''}
                                                    title="${user.isAdmin() ? 'Remove admin permissions' : 'Set admin'}"
                                                    type="submit" ${cUserId eq user.id ? 'disabled' : ''}>
                                                    ${user.isAdmin()}
                                            </button>
                                        </form>
                                    </td>
                                    <td>
                                        <form class="no-margin" method="post" action="<c:url value="/admin/ban"/>">
                                            <input type="hidden" name="userId" value="${user.id}"/>
                                            <input type="hidden" name="redirect" value=""/>
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <button class="pure-button max-width"
                                                    ${user.isBanned() ? 'style="background-color: #FFCCCC;"' : ''}
                                                    title="${user.isBanned() ? 'Unban user' : 'Ban user'}"
                                                    type="submit" ${cUserId eq user.id ? 'disabled' : ''}>
                                                    ${user.isBanned() ?  'Unban' : 'Ban'}
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan=75% class="text-center">No users found.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan=75% class="text-center">No users found.</td>
                    </tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
        <c:set var="sortParam" value="${(sortBy ne null) ? ('sortBy='+=sortBy+='&') : ''}"/>
        <c:set var="descParam" value="${('1' eq isDesc) ? 'isDesc=1&' : ''}"/>
        <c:if test="${users ne null}">
            <c:if test="${users.size() ge 1 and numberOfPages gt 1}">
                <div class="pure-g">
                    <div class="pure-u-3-4 centered">
                        <div id="pages" class="pure-g">
                            <div class="pure-u centered inline-flex">
                                <div class="page-number inline-flex">
                                    <c:if test="${currentPage ne 1}">
                                        <c:if test="${numberOfPages gt 10}">
                                            <p>
                                                <a class="page-link"
                                                   href="<c:url value="/admin/users?${sortParam}${descParam}page=1"/>"
                                                   style="margin-right: 5px;">First</a>
                                            </p>
                                        </c:if>
                                        <p>
                                            <a class="page-link"
                                               href="<c:url value="/admin/users?${sortParam}${descParam}page=${currentPage - 1}"/>">Prev</a>
                                        </p>
                                    </c:if>
                                </div>
                                <c:forEach
                                        begin="${(numberOfPages gt 10) ? (currentPage gt 5 ? (currentPage - 5) : 1) : 1}"
                                        end="${(numberOfPages gt 10) ? (currentPage + 5) : numberOfPages}" var="i">
                                    <c:if test="${i le numberOfPages}">
                                        <div class="page-number">
                                            <c:choose>
                                                <c:when test="${currentPage eq i}">
                                                    <p class="page-current">${i}</p>
                                                </c:when>
                                                <c:otherwise>
                                                    <p>
                                                        <a class="page-link"
                                                           href="<c:url value="/admin/users?${sortParam}${descParam}page=${i}"/>">${i}</a>
                                                    </p>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </c:if>
                                </c:forEach>
                                <div class="page-number inline-flex">
                                    <c:if test="${currentPage ne numberOfPages}">
                                        <p>
                                            <a class="page-link"
                                               href="<c:url value="/admin/users?${sortParam}${descParam}page=${currentPage + 1}"/>">Next</a>
                                        </p>
                                        <c:if test="${numberOfPages gt 10}">
                                            <p>
                                                <a class="page-link"
                                                   href="<c:url value="/admin/users?${sortParam}${descParam}page=${numberOfPages}"/>"
                                                   style="margin-left: 5px;">Last</a>
                                            </p>
                                        </c:if>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <div id="pages-sm" class="pure-g">
                            <div class="pure-u-1 inline-flex">
                                <div class="pure-u-1-4 centered">
                                    <select class="page-select"
                                            onchange="goToPage(this, '/admin/users?${sortParam}${descParam}page=')"
                                            title="Select page">
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
<script src="<c:url value="/resources/js/pages-sm.js"/>"></script>
</body>
</html>