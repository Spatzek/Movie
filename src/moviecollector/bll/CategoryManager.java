/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviecollector.bll;

import java.util.List;
import moviecollector.be.Category;
import moviecollector.be.Movie;
import moviecollector.dal.dbmanagers.CategoryDBDAO;

/**
 *
 * @author fauxtistic
 */
public class CategoryManager {
    
    private CategoryDBDAO categoryDbdao;

    public CategoryManager() {
        
        categoryDbdao = new CategoryDBDAO();
        
    }
    
    public List<Category> readAllCategories()
    {
        return categoryDbdao.readAllCategories();
    }
    
    public List<Movie> readAllCategoryMovies(Category category)
    {
        return categoryDbdao.readAllCategoryMovies(category);
    }
    
    
    
}
