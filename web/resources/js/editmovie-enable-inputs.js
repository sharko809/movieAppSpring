var editTitle = document.getElementById('editTitle');
if (editTitle != null) {
    editTitle.addEventListener('click', function (event) {
        event.preventDefault();
        var title = document.getElementById('title');
        if (title != null) {
            title.removeAttribute('readonly');
        }
    });
}

var editDirector = document.getElementById('editDirector');
if (editDirector != null) {
    editDirector.addEventListener('click', function (event) {
        event.preventDefault();
        var director = document.getElementById('director');
        if (director != null) {
            director.removeAttribute('readonly');
        }
    });
}

var editDate = document.getElementById('editDate');
if (editDate != null) {
    editDate.addEventListener('click', function (event) {
        event.preventDefault();
        var releaseDate = document.getElementById('releaseDate');
        if (releaseDate != null) {
            releaseDate.removeAttribute('readonly');
        }
    });
}

var editPoster = document.getElementById('editPoster');
if (editPoster != null) {
    editPoster.addEventListener('click', function (event) {
        event.preventDefault();
        var posterUrl = document.getElementById('posterUrl');
        if (posterUrl != null) {
            posterUrl.removeAttribute('readonly');
        }
    });
}

var editTrailer = document.getElementById('editTrailer');
if (editTrailer != null) {
    editTrailer.addEventListener('click', function (event) {
        event.preventDefault();
        var trailerUrl = document.getElementById('trailerUrl');
        if (trailerUrl != null) {
            trailerUrl.removeAttribute('readonly');
        }
    });
}

var editDescription = document.getElementById('editDescription');
if (editDescription != null) {
    editDescription.addEventListener('click', function (event) {
        event.preventDefault();
        var description = document.getElementById('description');
        if (description != null) {
            description.removeAttribute('readonly');
        }
    });
}