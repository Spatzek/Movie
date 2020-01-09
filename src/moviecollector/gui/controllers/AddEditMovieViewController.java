/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviecollector.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import moviecollector.be.Category;
import moviecollector.be.Movie;
import moviecollector.gui.MovieCollectorModel;

/**
 * FXML Controller class
 *
 * @author fauxtistic
 */
public class AddEditMovieViewController implements Initializable {

    private MovieCollectorModel movieModel;
    private int currentId;
    private ObservableList<Category> currentCats;
    private ObservableList<Category> availableCats;
    
    @FXML
    private TextField titleTextField;
    @FXML
    private Button saveMovieButton;
    @FXML
    private ComboBox<Category> categoryCombobox;
    @FXML
    private Button addCategoryButton;
    @FXML
    private ListView<Category> movieCatListView;
    @FXML
    private Button movieLocationButton;

    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Movie tempMovie = new Movie("", 0, "", null);
        tempMovie.setId(currentId);
        currentCats = FXCollections.observableArrayList(movieModel.readAllMovieCategories(tempMovie));
        availableCats = FXCollections.observableArrayList(movieModel.readAllAvailableCategories(tempMovie));
        categoryCombobox.setItems(availableCats);
        movieCatListView.setItems(currentCats);
        
    }    

    @FXML
    private void handleSaveMovie(ActionEvent event) {
    }

    @FXML
    private void handleAddCategory(ActionEvent event) {
    }

    @FXML
    private void handleMovieLocate(ActionEvent event) {
    }
    
}
