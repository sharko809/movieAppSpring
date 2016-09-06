<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav id="layout">
    <a href="#menu" id="menuLink" class="menu-link" style="top: 46px;">
        <!-- Hamburger icon -->
        <span></span>
    </a>
    <div id="menu" class="active" style="top: 46px;">
        <div class="pure-menu">
            <a class="pure-menu-heading" href="/admin">Admin tools</a>
            <ul class="pure-menu-list">
                <li class="menu-item"><a href="/admin/addmovie" class="pure-menu-link">Add movie</a></li>
                <li class="menu-item"><a href="/admin/managemovies" class="pure-menu-link">Manage movies</a></li>
                <li class="menu-item"><a href="/admin/users" class="pure-menu-link">Users</a></li>
                <li class="menu-item"><a href="/admin/newuser" class="pure-menu-link">Create user</a></li>
            </ul>
        </div>
    </div>
</nav>
<script src="/resources/js/vendor/pure/ui.js" type="text/javascript"></script>