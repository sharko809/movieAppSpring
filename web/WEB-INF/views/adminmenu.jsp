<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav id="layout">
    <a href="#menu" id="menuLink" class="menu-link" style="top: 46px;">
        <!-- Hamburger icon -->
        <span></span>
    </a>
    <div id="menu" class="active" style="top: 46px;">
        <div class="pure-menu">
            <a class="pure-menu-heading" href="<c:url value="/admin"/>">Admin tools</a>
            <ul class="pure-menu-list">
                <li class="menu-item"><a href="<c:url value="/admin/addmovie"/>" class="pure-menu-link">Add movie</a></li>
                <li class="menu-item"><a href="<c:url value="/admin/managemovies"/>" class="pure-menu-link">Manage movies</a></li>
                <li class="menu-item"><a href="<c:url value="/admin/users"/>" class="pure-menu-link">Users</a></li>
                <li class="menu-item"><a href="<c:url value="/admin/newuser"/>" class="pure-menu-link">Create user</a></li>
            </ul>
        </div>
    </div>
</nav>
<script src="<c:url value="/resources/js/vendor/pure/ui.js"/>" type="text/javascript"></script>