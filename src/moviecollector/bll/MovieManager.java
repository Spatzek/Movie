/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviecollector.bll;

import java.util.List;
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
    
    public List<Movie> readAllMovies()
    {
        return movieDbdao.readAllMovies();
    }
    
    
    
}
