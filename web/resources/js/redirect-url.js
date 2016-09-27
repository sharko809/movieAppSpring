(function() {
    $(document).ready(function () {
        var redirectLink = document.getElementById("redirect");
        if (redirectLink != null) {
            redirectLink.value = document.location.href;
        }
    });
})();
