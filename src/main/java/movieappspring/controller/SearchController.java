package movieappspring.controller;

import movieappspring.entities.Movie;
import movieappspring.entities.dto.MovieTransferObject;
import movieappspring.entities.util.PagedEntity;
import movieappspring.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/search")
public class SearchController {

    private static final String DEFAULT_PAGE_AS_STRING = "1";
    /**
     * Not a space. alt+255
     */
    private static final String DEFAULT_SEARCH_INPUT = " ";
    private static final Integer RECORDS_PER_PAGE = 5;
    private MovieService movieService;

    @Autowired
    public SearchController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView searchMovie(@RequestParam(value = "searchInput",
                                    defaultValue = DEFAULT_SEARCH_INPUT) String searchInput,
                                    @RequestParam(value = "page", defaultValue = DEFAULT_PAGE_AS_STRING) Integer page) {
        if (page < 1) {
            return new ModelAndView("redirect:/search?searchInput=");
        }

        ModelAndView modelAndView = new ModelAndView("searchresult");
        PagedEntity pagedMovies = movieService.searchMovies(searchInput, (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
        List<Movie> movies = (List<Movie>) pagedMovies.getEntity();
        List<MovieTransferObject> movieTransferObjects = movies.stream()
                .map(MovieTransferObject::new)
                .collect(Collectors.toList());
        int numberOfRecords = pagedMovies.getNumberOfRecords();
        int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);

        modelAndView.addObject("movies", movieTransferObjects);
        modelAndView.addObject("numberOfPages", numberOfPages);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("searchRequest", searchInput);
        return modelAndView;
    }

}