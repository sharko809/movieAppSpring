(function() {
    $(document).ready(function () {
        var redirectLink = document.getElementById("redirectFrom");
        if (redirectLink != null) {
            redirectLink.value = document.location.href;
        }
    });
})();
