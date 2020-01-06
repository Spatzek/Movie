/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviecollector.dal.dbmanagers;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import moviecollector.be.Category;
import moviecollector.dal.dbaccess.DBSettings;

/**
 *
 * @author fauxtistic
 */
public class CategoryDBDAO {
    
    private DBSettings dbs;

    public CategoryDBDAO() {
        try {
            dbs = new DBSettings();
        }
        catch (IOException e) {
            
        }
    }
    
    /**
     * Creates the specified category in the database
     *
     * @param category the category to be created
     * @return true if creation performed, else false
     */
    public boolean createCategory(Category category) {

        try (Connection con = dbs.getConnection()) {
            String sql = "INSERT INTO Categories (Name) VALUES (?);";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, category.getName());

            int updatedRows = stmt.executeUpdate();
            return updatedRows > 0;
            

        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Retrieves all categories from the database
     *
     * @return List of all the categories
     */
    public List<Category> readAllCategories() {
        
        try (Connection con = dbs.getConnection()) {
            String sql = "SELECT * FROM Categories;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Category> categories = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                Category cat = new Category(name);
                cat.setId(id);

                categories.add(cat);
            }
            return categories;
        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Updates the specified category in the database
     *
     * @param category the category to be updated
     * @return true if updated performed, else false
     */
    public boolean updateCategory(Category category) {
        
        try (Connection con = dbs.getConnection()) {
            String sql = "UPDATE Categories SET name = ? WHERE id = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, category.getName());
            stmt.setInt(2, category.getId());
            
            int updatedRows = stmt.executeUpdate();
            return updatedRows > 0;
            
        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Deletes a category with the matching ID in the database.
     *
     * @param category the category to be removed
     * @return true if delete performed, else false
     */
    public boolean deleteCategory(Category category) {
        
        try (Connection con = dbs.getConnection()) {
            String sql = "DELETE FROM Categories WHERE id = ?;DELETE FROM CatMovies WHERE categoryId = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, category.getId());
            stmt.setInt(2, category.getId());
            
            int updatedRows = stmt.executeUpdate();
            return updatedRows > 0;
            
        } catch (SQLServerException ex) {
            Logger.getLogger(CategoryDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
//    public static void main (String[] args) throws IOException, SQLException
//    {
//        CategoryDBDAO categoryDbdao = new CategoryDBDAO();
//        
//        categoryDbdao.createCategory(new Category("Horror"));
//        categoryDbdao.createCategory(new Category("Comedy"));
//        
//        List<Category> categories = categoryDbdao.readAllCategories();
//        
//        for (Category cat : categories){
//            System.out.println(cat);
//        }
//        
//    }
}