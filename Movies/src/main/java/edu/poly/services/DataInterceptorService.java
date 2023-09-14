package edu.poly.services;

import edu.poly.dao.MovieDAO;
import edu.poly.dao.MovietheaterDAO;
import edu.poly.entity.Movie;
import edu.poly.util.Const;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataInterceptorService implements HandlerInterceptor {
    @Autowired
    MovieDAO movieDAO;
    @Autowired
    MovietheaterDAO theaterDAO;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // Các xử lý trước khi request vào controller
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        try {

            List<Movie> phimhot = movieDAO.findAll().stream().limit(Const.MAX_MOVIE_HOME).collect(Collectors.toList());
            List<Movie> phimbovale = movieDAO.findAll().stream().limit(Const.MAX_MOVIE_HOME / 2)
                    .collect(Collectors.toList());
            List<Movie> phimsapchieu = movieDAO.findAllByReleaseDayGreaterThanEqual(new Date()).stream()
                    .limit(Const.MAX_SIDLE_MOVIE_UPCOMING).collect(Collectors.toList());
            List<Movie> phimmoicapnhat = movieDAO.findAll().stream().limit(Const.MAX_MOVIE_HOME)
                    .collect(Collectors.toList());
            List<Movie> bangxephang = movieDAO.findAll().stream().limit(Const.MAX_MOVIE_RANK)
                    .collect(Collectors.toList());
            List<String> listCategory = movieDAO.findDistinctCategorysMovie();
            List<Integer> listYear = movieDAO.findDistinctReleaseYears();
            List<String> listMovieTheater = theaterDAO.findDistinctMovietheater();

            modelAndView.addObject("phimhot", phimhot);
            modelAndView.addObject("phimbovale", phimbovale);
            modelAndView.addObject("phimsapchieu", phimsapchieu);
            modelAndView.addObject("phimmoicapnhat", phimmoicapnhat);
            modelAndView.addObject("bangxephang", bangxephang);
            modelAndView.addObject("categorysMovie", listCategory);
            modelAndView.addObject("yearsMovie", listYear);
            modelAndView.addObject("movieTheaters", listMovieTheater);

        } catch (Exception e) {
            // TODO: xử lý ngoại lệ
            e.printStackTrace();
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // Các xử lý sau khi request đã được xử lý hoàn tất
    }

}
