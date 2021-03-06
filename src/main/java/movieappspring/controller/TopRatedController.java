package movieappspring.controller;

import movieappspring.entities.Movie;
import movieappspring.entities.dto.MovieTransferObject;
import movieappspring.entities.util.PagedEntity;
import movieappspring.exception.OnGetNullException;
import movieappspring.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/toprated")
public class TopRatedController {

    private static final Integer OFFSET = 0;
    private static final Integer NUMBER_OF_RECORDS = 10;
    private static final String SORT_BY = "rating";
    private MovieService movieService;

    @Autowired
    public TopRatedController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView topRated() throws OnGetNullException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("toprated");

        PagedEntity pagedMovies = movieService.getMoviesSorted(OFFSET, NUMBER_OF_RECORDS, SORT_BY, true);
        List<Movie> movies = (List<Movie>) pagedMovies.getEntity();
        List<MovieTransferObject> movieTransferObjects = movies.stream()
                .map(MovieTransferObject::new)
                .collect(Collectors.toList());

        modelAndView.addObject("movies", movieTransferObjects);
        return modelAndView;
    }

}
