/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviecollector.gui.controllers;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import moviecollector.be.Category;
import moviecollector.be.Movie;
import moviecollector.gui.MovieCollectorModel;



/**
 *
 * @author jonas
 */
public class MovieCollectorController implements Initializable {

    private MovieCollectorModel movieModel = new MovieCollectorModel();
    
    @FXML
    private ListView<Movie> movieListView;
    @FXML
    private ListView<Category> categoryListView;
    @FXML
    private Button deleteCategoryButton;
    @FXML
    private Button addMovieButton;
    @FXML
    private Button deleteMovieButton;
    @FXML
    private Button addCategoryButton;
    @FXML
    private TextField filterTitleField;
    @FXML
    private Button playMovieButton;
    @FXML
    private Button editCategoryButton;
    @FXML
    private Button editMovieButton;
    @FXML
    private Button clearFiltersButton;
    @FXML
    private ComboBox<?> addRatingSelector;
    @FXML
    private Button addRating;
    @FXML
    private ComboBox<?> minimumRating;
    @FXML
    private Button sortByTitle;
    @FXML
    private Button searchEnterMovieTitle;
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        categoryListView.setItems(FXCollections.observableArrayList(movieModel.readAllCategories()));
    }    

    @FXML
    private void categorySelected(MouseEvent event) {
        Category category = categoryListView.getSelectionModel().getSelectedItem();
        List<Movie> categoryMovies = movieModel.readAllCategoryMovies(category);
        movieListView.setItems(FXCollections.observableArrayList(categoryMovies));
    }
    
}
