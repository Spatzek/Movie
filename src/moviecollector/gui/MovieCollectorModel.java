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
    
    public boolean createCategory(Category category)
    {
        return categoryManager.createCategory(category);
    }
    
    public List<Category> readAllCategories()
    {
        return categoryManager.readAllCategories();
    }
    
    public boolean updateCategory(Category category)
    {
        return categoryManager.updateCategory(category);
    }
    
    public boolean deleteCategory(Category category)
    {
        return categoryManager.deleteCategory(category);
    }
    
    public List<Movie> readAllCategoryMovies(Category category)
    {
        return categoryManager.readAllCategoryMovies(category);
    }
    
    public boolean createMovie(Movie movie)
    {
        return movieManager.createMovie(movie);
    }
    
    public List<Movie> readAllMovies()
    {
        return movieManager.readAllMovies();
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
    
}
