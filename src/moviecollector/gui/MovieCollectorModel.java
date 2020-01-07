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
    
    public List<Movie> readAllMovies()
    {
        return movieManager.readAllMovies();
    }
    
    
    
}
