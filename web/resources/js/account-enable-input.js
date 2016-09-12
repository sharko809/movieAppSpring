var editName = document.getElementById('editName');
if (editName != null) {
    editName.addEventListener('click', function (event) {
        event.preventDefault();
        document.getElementById('name').removeAttribute('readonly');
    });
}

var editLogin = document.getElementById('editLogin');
if (editLogin != null) {
    editLogin.addEventListener('click', function (event) {
        event.preventDefault();
        document.getElementById('login').removeAttribute('readonly');
    });
}

var editPassword = document.getElementById('editPassword');
if (editPassword != null) {
    editPassword.addEventListener('click', function (event) {
        event.preventDefault();
        document.getElementById('password').removeAttribute('readonly');
    });
}