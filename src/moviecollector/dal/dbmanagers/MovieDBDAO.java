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
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import moviecollector.be.Movie;
import moviecollector.dal.dbaccess.DBSettings;

/**
 *
 * @author fauxtistic
 */
public class MovieDBDAO {
    
    private DBSettings dbs;

    public MovieDBDAO() {
        
        try {
            dbs = new DBSettings();
        }
        catch (IOException e) {
            
        }        
    }
    
    /**
     * Creates the specified movie in the database.
     *
     * @param movie
     * @return True if creation performed, else false
     */    
    public boolean createMovie(Movie movie) {

        try (Connection con = dbs.getConnection()) {
            String sql = "INSERT INTO Movies (name, rating, filelink, lastview) VALUES (?,?,?,?);";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, movie.getName());
            stmt.setDouble(2, movie.getRating());
            stmt.setString(3, movie.getFileLink());
            stmt.setDate(4, movie.getLastView());            

            int updatedRows = stmt.executeUpdate();
            return updatedRows > 0;

        } catch (SQLServerException ex) {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Retrieves all movies in the database and puts them in a list.
     *
     * @return List of all movies
     */    
    public List<Movie> readAllMovies() {
        try (Connection con = dbs.getConnection()) {
            String sql = "SELECT * FROM Movies;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Movie> movies = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Double rating = rs.getDouble("rating");
                String filelink = rs.getString("filelink");
                Date lastview = rs.getDate("lastview");                

                Movie mov = new Movie(name, rating, filelink, lastview);
                mov.setId(id);
                
                movies.add(mov);
            }
            return movies;
        } catch (SQLServerException ex) {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Updates a movie with matching id in the database.
     *
     * @param movie
     * @return True if update performed, else false
     */    
    public boolean updateMovie(Movie movie) {
        try (Connection con = dbs.getConnection()) {
            String sql = "UPDATE Movies SET name = ?, rating = ?, filelink = ?, lastview = ? WHERE id = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, movie.getName());
            stmt.setDouble(2, movie.getRating());
            stmt.setString(3, movie.getFileLink());
            stmt.setDate(4, movie.getLastView());            
            stmt.setInt(5, movie.getId());
            
            int updatedRows = stmt.executeUpdate();
            return updatedRows > 0;
            
        } catch (SQLServerException ex) {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Deletes a movie with matching id from the database.
     *
     * @param movie
     * @return True if deletion performed, else false
     */    
    public boolean deleteMovie(Movie movie) {
        try (Connection con = dbs.getConnection()) {
            String sql = "DELETE FROM Movies WHERE id = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, movie.getId());
            
            int updatedRows = stmt.executeUpdate();
            return updatedRows > 0;
            
        } catch (SQLServerException ex) {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean deleteMovieFromCatMovies(Movie movie) {
        try (Connection con = dbs.getConnection()) {
            String sql = "DELETE FROM CatMovies WHERE id = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, movie.getId());
            
            int updatedRows = stmt.executeUpdate();
            return updatedRows > 0;
            
        } catch (SQLServerException ex) {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MovieDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
//        public static void main (String[] args)
//    {
//        MovieDBDAO movieDbdao = new MovieDBDAO();
//        
//        Date date = new Date(System.currentTimeMillis());
//        
//        Movie movie1 = new Movie("Braveheart", 7.5, "mockfile", date);
//        Movie movie2 = new Movie("Dark Knight", 8.5, "mockfile", date);
//        
//        movieDbdao.createMovie(movie1);
//        movieDbdao.createMovie(movie2);
//        
//        List<Movie> movies = movieDbdao.readAllMovies();
//        
//        for (Movie movie : movies)
//        {
//            System.out.println(movie);
//        }
//        
//        
//        
//        
//    }
    
}
