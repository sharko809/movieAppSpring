function setLoginInputs(login) {
    var userName = document.getElementById('userName');
    if (userName != null) {
        userName.value = login;
    }
}

function setRegistrationInputs(name, email) {
    var userName = document.getElementById('newUserName');
    var userLogin = document.getElementById('newUserLogin');
    if (userName != null && userLogin != null) {
        userName.value = name;
        userLogin.value = email;
    }
}

function setMovieInputs(title, director, date, posterUrl, trailerUrl, description) {
    var movieTitle = document.getElementById('title');
    var movieDirector = document.getElementById('director');
    var movieReleaseDate = document.getElementById('releaseDate');
    var moviePosterUrl = document.getElementById('posterUrl');
    var movieTrailerUrl = document.getElementById('trailerUrl');
    var movieDescriptionUrl = document.getElementById('description');
    if (movieTitle != null && movieDirector != null && movieReleaseDate != null && moviePosterUrl != null
        && movieTrailerUrl != null && movieDescriptionUrl != null) {
        movieTitle.value = title;
        movieDirector.value = director;
        movieReleaseDate.value = date;
        moviePosterUrl.value = posterUrl;
        movieTrailerUrl.value = trailerUrl;
        movieDescriptionUrl.value = description;
    }
}

function setReviewInputs(title, rating, text) {
    var reviewTitle = document.getElementById('reviewTitle');
    var reviewRating = document.getElementById('rating');
    var reviewText = document.getElementById('reviewText');
    if (reviewTitle != null && reviewRating != null && reviewText != null) {
        reviewTitle.value = title;
        reviewRating.value = rating;
        reviewText.value = text;
    }
}