(function () {
    $(document).ready(function () {
        var items = document.getElementsByName('redirect');
        if (items != null) {
            for (var i = 0; i < items.length; i++) {
                items[i].value = document.location.href;
            }
        }
    });
})();
