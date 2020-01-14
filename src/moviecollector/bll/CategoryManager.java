/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviecollector.bll;

import java.io.IOException;
import java.sql.SQLException;
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
    
    public boolean isCategoryNameUsed (Category category)
    {
        List<Category> categories = readAllCategories();
        for (Category cat : categories)
        {
            if (cat.getName().contentEquals(category.getName()))
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
