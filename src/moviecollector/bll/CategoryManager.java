/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviecollector.bll;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import moviecollector.be.Category;
import moviecollector.be.Movie;
import moviecollector.dal.dbmanagers.CategoryDBDAO;

/**
 *
 * @author fauxtistic
 * Creates the class Categorymanager, and calls for the CategoryDBDAO
 */

public class CategoryManager {
    
    private CategoryDBDAO categoryDbdao;

    public CategoryManager() {
        
        categoryDbdao = new CategoryDBDAO();
        
    }
    
    public boolean createCategory(Category category)
    {
        return categoryDbdao.createCategory(category);
    }
    
    public List<Category> readAllCategories()
    {
        return categoryDbdao.readAllCategories();
    }
    
    public boolean updateCategory(Category category)
    {
        return categoryDbdao.updateCategory(category);
    }
    
    public boolean deleteCategory(Category category)
    {
        return categoryDbdao.deleteCategory(category);
    }
    
    public List<Movie> readAllCategoryMovies(Category category)
    {
        return categoryDbdao.readAllCategoryMovies(category);
    }
    
    public List<Movie> readFilteredCategoryMovies(List<Category> selectedCategories, double minRating, String searchTerm)
    {
        List<Movie> movies = new ArrayList<>();
        List<Movie> moviesNoDuplicates = new ArrayList<>();
        int lastId = 0;
        
        for (Category category : selectedCategories)
        {
            movies.addAll(categoryDbdao.readFilteredCategoryMovies(category, minRating, searchTerm));
        }
        
        movies.sort(Comparator.comparing(Movie::getId));
        
        for (Movie movie :  movies)
        {
            if (movie.getId()!=lastId)
            {
                moviesNoDuplicates.add(movie);
                lastId = movie.getId();
            }
        }
        
        return moviesNoDuplicates;
    }
    
    public boolean isCategoryNameUsed (Category category)
    {
        List<Category> categories = readAllCategories();
        for (Category cat : categories)
        {
            if (cat.getName().contentEquals(category.getName()) && cat.getId()!=category.getId())
            {
                return true;
            }
        }
        return false;
    }
    
    public boolean saveCategory(Category category)
    {
        if (category.getId()==0)
        {
            return createCategory(category);
        }
        else
        {
            return updateCategory(category);
        }
    }           
        
}
