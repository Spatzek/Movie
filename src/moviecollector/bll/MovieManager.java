/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviecollector.bll;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
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
    
    /**
     * Creates specified movie in database
     * @param movie
     * @return
     * @throws SQLException 
     */
    public boolean createMovie(Movie movie) throws SQLException
    {
        return movieDbdao.createMovie(movie);
    }
    
    /**
     * Retrieves a list of all movies
     * @return List of movies
     */
    public List<Movie> readAllMovies()
    {
        return movieDbdao.readAllMovies();
    }
    
    /**
     * Retrieves a list of movies meeting specified requirements
     * @param minRating
     * @param searchTerm
     * @return List of movies
     */
    public List<Movie> readFilteredMovies(double minRating, String searchTerm)
    {
        return movieDbdao.readFilteredMovies(minRating, searchTerm);
    }
    
    /**
     * Updates specified movie in database
     * @param movie
     * @return
     * @throws SQLException 
     */
    public boolean updateMovie(Movie movie) throws SQLException
    {
        return movieDbdao.updateMovie(movie);
    }
    
    /**
     * Deletes specified movie in database from table of all movies
     * @param movie
     * @return true if deletion performed, else false
     */
    public boolean deleteMovie(Movie movie)
    {
        return movieDbdao.deleteMovie(movie);
    }
    
    /**
     * Deletes movie from CatMovies table
     * @param movie
     * @return true if deletion performed, else false
     */
    public boolean deleteMovieFromCatMovies(Movie movie)
    {
        return movieDbdao.deleteMovieFromCatMovies(movie);
    }
    
    /**
     * Checks if movie name is already in use by another movie
     * @param movie whose name is checked
     * @return true if name is in use by another movie, else false
     */
    public boolean isMovieNameUsed (Movie movie)
    {
        List<Movie> movies = readAllMovies();        
        for (Movie mov : movies)
        {
            if (mov.getName().contentEquals(movie.getName()) && mov.getId()!=movie.getId())
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Creates or updates movie depending on whether object already exists in database or not
     * Also creates or updates corresponding categories for movie
     * @param movie
     * @throws SQLException 
     */
    public void saveMovie(Movie movie) throws SQLException
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
    
    /**
     * Retrieves a list of all categories associated with movie
     * @param movie
     * @return List of categories
     */
    public List<Category> readAllMovieCategories(Movie movie)
    {
        return movieDbdao.readAllMovieCategories(movie);
    }
    
    /**
     * Associates specified movie with specified category
     * @param category
     * @param movie
     * @return true is performed, else false
     */
    public boolean addMovieCategory(Category category, Movie movie)
    {
        return movieDbdao.addMovieCategory(category, movie);
    }
    
    /**
     * Removes association between movie and category
     * @param category
     * @param movie
     * @return true if performed, else false
     */
    public boolean removeMovieCategory(Category category, Movie movie)
    {
        return movieDbdao.removeMovieCategory(category, movie);
    }
    
    /**
     * Retrieves a list of movies meeting specified requirements
     * @param minRating
     * @param years
     * @return List of movies
     */
    public List<Movie> readBadOldMovies(double minRating, int years)
    {
        return movieDbdao.readBadOldMovies(minRating, years);
    }             
    
    /**
     * Deletes all movies in list from entire database
     * @param movies List
     */
    public void deleteBadOldMovies(List<Movie> movies)
    {
        for (Movie movie : movies)
        {
            deleteMovie(movie);
            deleteMovieFromCatMovies(movie);
        }
    }       
              
               
        
}
