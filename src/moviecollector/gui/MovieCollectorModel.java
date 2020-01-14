/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviecollector.gui;

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
        
    public List<Category> readAllCategories()
    {
        return categoryManager.readAllCategories();
    }    
        
    public boolean deleteCategory(Category category)
    {
        return categoryManager.deleteCategory(category);
    }
    
    public List<Movie> readAllCategoryMovies(Category category)
    {
        return categoryManager.readAllCategoryMovies(category);
    }
    
    public List<Movie> readAllMovies()
    {
        return movieManager.readAllMovies();
    }
    
    public List<Movie> readFilteredMovies(double minRating, String searchTerm)
    {
        return movieManager.readFilteredMovies(minRating, searchTerm);
    }
    
    public boolean updateMovie(Movie movie)
    {
        return movieManager.updateMovie(movie);
    }
    
    public boolean deleteMovie(Movie movie)
    {
        return movieManager.deleteMovie(movie);
    }
    
    public boolean deleteMovieFromCatMovies(Movie movie)
    {
        return movieManager.deleteMovieFromCatMovies(movie);
    }
    
    public boolean isCategoryNameUsed(Category category)
    {
        return categoryManager.isCategoryNameUsed(category);
    }
    
    public boolean saveCategory(Category category)
    {
        return categoryManager.saveCategory(category);
    }
    
    public boolean isMovieNameUsed(Movie movie)
    {
        return movieManager.isMovieNameUsed(movie);
    }
    
    public void saveMovie(Movie movie)
    {
        movieManager.saveMovie(movie);
    }
    
    public List<Category> readAllMovieCategories(Movie movie)
    {
        return movieManager.readAllMovieCategories(movie);
    }
    
    public List<Movie> readFilteredCategoryMovies(Category category, double minRating, String searchTerm)
    {
        return categoryManager.readFilteredCategoryMovies(category, minRating, searchTerm);
    }
    
    /**
     * reads the categories
     * @param movie
     * @return 
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
    
    
    
}
