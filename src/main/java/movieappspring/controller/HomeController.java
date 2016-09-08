package movieappspring.controller;

import movieappspring.entities.Movie;
import movieappspring.entities.PagedEntity;
import movieappspring.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping(value = "/home")
public class HomeController {

    private static final String DEFAULT_PAGE_AS_STRING = "1";
    private static final Integer RECORDS_PER_PAGE = 5;
    private MovieService movieService;

    @Autowired
    public HomeController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView movie(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE_AS_STRING) Integer page) throws SQLException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");

        if (page <= 0) {
            // TODO some logic
        }

        PagedEntity pagedMovies = movieService.getAllMoviesLimit((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
        List<Movie> movies = (List<Movie>) pagedMovies.getEntity();
        int numberOfRecords = pagedMovies.getNumberOfRecords();
        int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);

        modelAndView.addObject("movies", movies);
        modelAndView.addObject("numberOfPages", numberOfPages);
        modelAndView.addObject("currentPage", page);
        return modelAndView;
    }

}
