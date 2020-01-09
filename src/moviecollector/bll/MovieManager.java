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
    
    public boolean saveMovie(Movie movie)
    {
        if (movie.getId()==0)
        {
            return createMovie(movie);
        }
        else
        {
            return updateMovie(movie);
        }
    }       
    
}
