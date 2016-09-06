<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Users</title>
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
    <link rel="stylesheet" type="text/css" href="/resources/css/xs-screen.css">
    <script src="/resources/js/vendor/jquery-3.1.0.min.js" type="text/javascript"></script>
    <script src="/resources/js/admin-redirect-url.js" type="text/javascript"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="padding-top"></div>
<jsp:include page="adminmenu.jsp"/>
<div class="pure-g custom-margin">
    <div class="pure-u-3-4  centered">
        <div class="pure-u-1">
            <div class="pure-u-1">
                <div class="inline-flex">
                    <form style="margin: 15px 0 5px 0;" method="post" action="/admin/usersort">
                        <label for="id">ID</label>
                        <input id="id" type="radio" name="sortBy" value="id"
                        ${sortBy eq 'id' ? 'checked' : ''}>
                        <label for="login">Login</label>
                        <input id="login" type="radio" name="sortBy" value="login"
                        ${sortBy eq 'login' ? 'checked' : ''}>
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
                        <input type="hidden" name="redirectFrom" value="">
                        <input type="hidden" name="page" value="${currentPage}">
                        <button class="pure-button" style="padding-bottom: 5px; vertical-align: baseline;"
                                type="submit">Sort
                        </button>
                    </form>
                </div>
            </div>
            <table class="pure-table pure-table-bordered" style="width: 100%; margin: 10px auto 10px auto;">
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
                    <c:when test="${sortedUsers ne null}">
                        <c:if test="${sortedUsers.size() ge 1}">
                            <c:forEach items="${sortedUsers}" var="user">
                                <tr>
                                    <td>${user.id}</td>
                                    <td>${user.login}</td>
                                    <td>${user.name}</td>
                                    <c:choose>
                                        <c:when test="${user.isAdmin()}">
                                            <td>
                                                <form method="post" action="/admin/adminize" style="margin: 0px;">
                                                    <input type="hidden" name="userID" value="${user.id}"/>
                                                    <input type="hidden" name="redirectFrom" value=""/>
                                                    <button class="pure-button max-width"
                                                            style="background-color: #7FFF00;"
                                                            title="Remove admin permissions"
                                                            type="submit">${user.isAdmin()}</button>
                                                </form>
                                            </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>
                                                <form method="post" action="/admin/adminize" style="margin: 0px;">
                                                    <input type="hidden" name="userID" value="${user.id}"/>
                                                    <input type="hidden" name="redirectFrom" value=""/>
                                                    <button class="pure-button max-width" title="Set admin"
                                                            type="submit">${user.isAdmin()}</button>
                                                </form>
                                            </td>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${user.isBanned()}">
                                            <td>
                                                <form method="post" action="/admin/ban" style="margin: 0px;">
                                                    <input type="hidden" name="userID" value="${user.id}"/>
                                                    <input type="hidden" name="redirectFrom" value=""/>
                                                    <button class="pure-button max-width"
                                                            style="background-color: #FFCCCC;"
                                                            title="Unban user"
                                                            type="submit">${user.isBanned()}</button>
                                                </form>
                                            </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>
                                                <form method="post" action="/admin/ban" style="margin: 0px;">
                                                    <input type="hidden" name="userID" value="${user.id}"/>
                                                    <input type="hidden" name="redirectFrom" value=""/>
                                                    <button class="pure-button max-width"
                                                            title="Ban user"
                                                            type="submit">${user.isBanned()}</button>
                                                </form>
                                            </td>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                            </c:forEach>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${users ne null}">
                                <c:choose>
                                    <c:when test="${users.size() ge 1}">
                                        <c:forEach items="${users}" var="user">
                                            <tr>
                                                <td>${user.id}</td>
                                                <td>${user.login}</td>
                                                <td>${user.name}</td>
                                                <c:choose>
                                                    <c:when test="${user.isAdmin()}">
                                                        <td>
                                                            <form method="post" action="/admin/adminize"
                                                                  style="margin: 0px;">
                                                                <input type="hidden" name="userID" value="${user.id}"/>
                                                                <input type="hidden" name="redirectFrom" value=""/>
                                                                <button class="pure-button max-width"
                                                                        style="background-color: #7FFF00;"
                                                                        title="Remove admin permissions"
                                                                        type="submit">${user.isAdmin()}</button>
                                                            </form>
                                                        </td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td>
                                                            <form method="post" action="/admin/adminize"
                                                                  style="margin: 0px;">
                                                                <input type="hidden" name="userID" value="${user.id}"/>
                                                                <input type="hidden" name="redirectFrom" value=""/>
                                                                <button class="pure-button max-width" title="Set admin"
                                                                        type="submit">${user.isAdmin()}</button>
                                                            </form>
                                                        </td>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:choose>
                                                    <c:when test="${user.isBanned()}">
                                                        <td>
                                                            <form method="post" action="/admin/ban" style="margin: 0px;">
                                                                <input type="hidden" name="userID" value="${user.id}"/>
                                                                <input type="hidden" name="redirectFrom" value=""/>
                                                                <button class="pure-button max-width"
                                                                        style="background-color: #FFCCCC;"
                                                                        title="Unban user"
                                                                        type="submit">Unban</button>
                                                            </form>
                                                        </td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td>
                                                            <form method="post" action="/admin/ban" style="margin: 0px;">
                                                                <input type="hidden" name="userID" value="${user.id}"/>
                                                                <input type="hidden" name="redirectFrom" value=""/>
                                                                <button class="pure-button max-width"
                                                                        title="Ban user"
                                                                        type="submit">Ban</button>
                                                            </form>
                                                        </td>
                                                    </c:otherwise>
                                                </c:choose>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan=75% style="text-align: center;">No users found.</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan=75% style="text-align: center;">No users found.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
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
                                                <a class="page-link" href="/admin/users?page=1" style="margin-right: 5px;">First</a>
                                            </p>
                                        </c:if>
                                        <p>
                                            <a class="page-link" href="/admin/users?page=${currentPage - 1}">Prev</a>
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
                                                        <a class="page-link" href="/admin/users?page=${i}">${i}</a>
                                                    </p>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </c:if>
                                </c:forEach>
                                <div class="page-number inline-flex">
                                    <c:if test="${currentPage ne numberOfPages}">
                                        <p>
                                            <a class="page-link" href="/admin/users?page=${currentPage + 1}">Next</a>
                                        </p>
                                        <c:if test="${numberOfPages gt 10}">
                                            <p>
                                                <a class="page-link" href="/admin/users?page=${numberOfPages}" style="margin-left: 5px;">Last</a>
                                            </p>
                                        </c:if>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <div id="pages-sm" class="pure-g">
                            <div class="pure-u-1 inline-flex">
                                <div class="pure-u-1-4 centered">
                                    <select class="page-select" onchange="javascript:goToPage(this, '/admin/users?page=')">
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
