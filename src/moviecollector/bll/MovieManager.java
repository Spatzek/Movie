/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviecollector.bll;

import java.util.List;
import moviecollector.be.Category;
import moviecollector.be.Movie;
import moviecollector.dal.dbmanagers.MovieDBDAO;

/**
 *
 * @author fauxtistic
 */
public class MovieManager {
    
    private MovieDBDAO movieDbdao;

    public MovieManager() {
        
        movieDbdao = new MovieDBDAO();
        
    }
    
    public boolean createMovie(Movie movie)
    {
        return movieDbdao.createMovie(movie);
    }
    
    public List<Movie> readAllMovies()
    {
        return movieDbdao.readAllMovies();
    }
    
    public boolean updateMovie(Movie movie)
    {
        return movieDbdao.updateMovie(movie);
    }
    
    public boolean deleteMovie(Movie movie)
    {
        return movieDbdao.deleteMovie(movie);
    }
    
    public boolean deleteMovieFromCatMovies(Movie movie)
    {
        return movieDbdao.deleteMovieFromCatMovies(movie);
    }
    
    public boolean isMovieNameUsed (Movie movie)
    {
        List<Movie> movies = readAllMovies();
        for (Movie mov : movies)
        {
            if (mov.getName().contentEquals(movie.getName()))
            {
                return true;
            }
        }
        return false;
    }
    
    public void saveMovie(Movie movie)
    {
        if (movie.getId()==0)
        {
            createMovie(movie);
            List<Movie> allMovies = readAllMovies();
            Movie newMovie = allMovies.get(allMovies.size()-1);
            List<Category> categories = movie.getCategories();
            for (Category category : categories)
            {
                addMovieCategory(category, newMovie);
            }
        }
        else
        {
            updateMovie(movie);
            List<Category> categories = movie.getCategories();
            deleteMovieFromCatMovies(movie);
            for (Category category : categories)
            {
                addMovieCategory(category, movie);
            }
        }
    }       
    
    public List<Category> readAllMovieCategories(Movie movie)
    {
        return movieDbdao.readAllMovieCategories(movie);
    }
    
    public boolean addMovieCategory(Category category, Movie movie)
    {
        return movieDbdao.addMovieCategory(category, movie);
    }
    
    public boolean removeMovieCategory(Category category, Movie movie)
    {
        return movieDbdao.removeMovieCategory(category, movie);
    }
    
}
