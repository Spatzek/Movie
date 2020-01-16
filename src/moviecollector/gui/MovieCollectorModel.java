/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviecollector.gui;

import java.sql.SQLException;
import java.util.List;
import moviecollector.be.Category;
import moviecollector.be.Movie;
import moviecollector.bll.CategoryManager;
import moviecollector.bll.MovieManager;

/**
 *
 * @author fauxtistic
 */
public class MovieCollectorModel {
    
    private CategoryManager categoryManager;
    private MovieManager movieManager;
    

    public MovieCollectorModel() {
        
        categoryManager = new CategoryManager();
        movieManager = new MovieManager();
        
    }
        
    /**
     * Retrieves a list of all categories
     * @return List of categories
     */
    public List<Category> readAllCategories()
    {
        return categoryManager.readAllCategories();
    }    
        
    /**
     * Deletes the specified category from the database
     * @param category
     * @return true if deletion performed, else false
     */
    public boolean deleteCategory(Category category)
    {
        return categoryManager.deleteCategory(category);
    }
    
    /**
     * Retrieves a list of all movies belonging to the category
     * @param category
     * @return List of movies
     */
    public List<Movie> readAllCategoryMovies(Category category)
    {
        return categoryManager.readAllCategoryMovies(category);
    }
    
    /**
     * Retrieves a list of all movies
     * @return List of movies
     */
    public List<Movie> readAllMovies()
    {
        return movieManager.readAllMovies();
    }
    
    /**
     * Retrieves a list of movies meeting the specified requirements
     * @param minRating
     * @param searchTerm
     * @return List of movies
     */
    public List<Movie> readFilteredMovies(double minRating, String searchTerm)
    {
        return movieManager.readFilteredMovies(minRating, searchTerm);
    }
    
    /**
     * Updates the specified movie in the database
     * @param movie
     * @return
     * @throws SQLException 
     */
    public boolean updateMovie(Movie movie) throws SQLException
    {
        return movieManager.updateMovie(movie);
    }
    
    /**
     * Deletes the specified movie from the Movies table in the database
     * @param movie
     * @return true if deletion performed, else false
     */
    public boolean deleteMovie(Movie movie)
    {
        return movieManager.deleteMovie(movie);
    }
    
    /**
     * Deletes the specified movie from the CatMovies table in the database
     * @param movie
     * @return true if deletion performed, else false
     */
    public boolean deleteMovieFromCatMovies(Movie movie)
    {
        return movieManager.deleteMovieFromCatMovies(movie);
    }
    
    /**
     * Checks whether the category's name is already in use by another category
     * @param category
     * @return true if name is in use by another category, else false
     */
    public boolean isCategoryNameUsed(Category category)
    {
        return categoryManager.isCategoryNameUsed(category);
    }
    
    /**
     * Saves addition of or changes to category
     * @param category
     * @return true if performed, else false
     */
    public boolean saveCategory(Category category)
    {
        return categoryManager.saveCategory(category);
    }
    
    /**
     * Checks whether the movie's name is already in use by another movie
     * @param movie
     * @return true if name is in use by another movie, else false
     */
    public boolean isMovieNameUsed(Movie movie)
    {
        return movieManager.isMovieNameUsed(movie);
    }
    
    /**
     * Saves addition of or changes to movie
     * @param movie
     * @throws SQLException 
     */
    public void saveMovie(Movie movie) throws SQLException
    {
        movieManager.saveMovie(movie);
    }
    
    /**
     * Retrieves a list of all categories with which movie is associated
     * @param movie
     * @return List of categories
     */
    public List<Category> readAllMovieCategories(Movie movie)
    {
        return movieManager.readAllMovieCategories(movie);
    }
    
    /**
     * Retrieves a list of movies meeting the specified requirements
     * @param selectedCategories
     * @param minRating
     * @param searchTerm
     * @return List of movies
     */
    public List<Movie> readFilteredCategoryMovies(List<Category> selectedCategories, double minRating, String searchTerm)
    {
        return categoryManager.readFilteredCategoryMovies(selectedCategories, minRating, searchTerm);
    }
    
    /**
     * Retrieves a list of categories not yet associated with movie
     * @param movie
     * @return List of movies
     */
    public List<Category> readAllAvailableCategories(Movie movie)
    {
        List<Category> currentCats = readAllMovieCategories(movie);        
        List<Category> availableCats = readAllCategories();
        
        for (Category movieCat : currentCats)
        {
            for (Category everyCat : availableCats)
            {
                if (movieCat.getId()==everyCat.getId())
                {
                    availableCats.remove(everyCat);
                    break;
                }
            }
        }
        availableCats.remove(0);
        return availableCats;
    }           
    
    /**
     * Retrieves a list of movies meeting the specified requirements
     * @param minRating
     * @param years
     * @return List of movies
     */
    public List<Movie> readBadOldMovies(double minRating, int years)
    {
        return movieManager.readBadOldMovies(minRating, years);
    }
    
    /**
     * Deletes all movies in list
     * @param movies List
     */
    public void deleteBadOldMovies(List<Movie> movies)
    {
        movieManager.deleteBadOldMovies(movies);
    }
    
    
}
