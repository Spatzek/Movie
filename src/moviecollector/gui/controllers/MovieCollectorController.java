/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviecollector.gui.controllers;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import moviecollector.be.Category;
import moviecollector.be.Movie;
import moviecollector.gui.MovieCollectorModel;



/**
 *
 * @author jonas
 */
public class MovieCollectorController implements Initializable {

    private MovieCollectorModel movieModel = new MovieCollectorModel();
    private Category selectedCategory;
    private boolean filterOn;
    private boolean sortingByTitle;
    private boolean sortingByRating;
    
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
    private ComboBox<Integer> addRatingSelector;
    @FXML
    private ComboBox<Integer> minimumRating;
    @FXML
    private Button searchButton;
    @FXML
    private ComboBox<String> sortCombobox;
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filterOn = false;
        sortingByTitle = false;
        sortingByRating = false;
        addRatingSelector.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        minimumRating.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        sortCombobox.getItems().addAll("Sort by title", "Sort by rating");
        setCategories();
    }    

    @FXML
    private void categorySelected(MouseEvent event) {
        selectedCategory = categoryListView.getSelectionModel().getSelectedItem();
        setCategoryMovies(selectedCategory);
    }

    @FXML
    private void handleDeleteCategory(javafx.event.ActionEvent event) {
        selectedCategory = categoryListView.getSelectionModel().getSelectedItem();
        if (selectedCategory == null)
        {
        showErrorAlert("You must select a category to delete");
        return;
        }
        if (selectedCategory.getId()==1)
        {
            showErrorAlert("This category can not be deleted");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Confirm deletion");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete: " + selectedCategory.getName() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            movieModel.deleteCategory(selectedCategory);
            clear();
            setCategoryMovies(selectedCategory);            
            setCategories();
            
        } else
        {
            alert.close();
        }
    }

    @FXML
    private void handleAddCategory(javafx.event.ActionEvent event) throws IOException {
        Stage primStage = (Stage) categoryListView.getScene().getWindow();
        openWindow(primStage, null, "AddEditCategoryView.fxml", "New Category");
    }

    @FXML
    private void handleEditCategory(javafx.event.ActionEvent event) throws IOException {
        selectedCategory = categoryListView.getSelectionModel().getSelectedItem();
        if (selectedCategory == null)
        {
        showErrorAlert("You must select a category to edit");
        return;
        }
        if (selectedCategory.getId()==1)
        {
            showErrorAlert("This category can not be edited");
            return;
        }
        Stage primStage = (Stage) categoryListView.getScene().getWindow();        
        openWindow(primStage, selectedCategory, "AddEditCategoryView.fxml", "Edit Category");
    }
    
    public void openWindow(Stage primStage, Object obj, String viewFXML, String windowMessage)
    {

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(viewFXML));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            
            if (viewFXML=="AddEditCategoryView.fxml")
            {
                AddEditCategoryController controller = fxmlLoader.getController();
                controller.setController(this);
            }
            else if (viewFXML=="AddEditMovieView.fxml")
            {
                AddEditMovieController controller = fxmlLoader.getController();
                controller.setController(this);
            }
            
            if (obj instanceof Category)
            {
                AddEditCategoryController controller = fxmlLoader.getController();
                controller.setText((Category) obj);                
            } 
            else if (obj instanceof Movie)
            {
                AddEditMovieController controller = fxmlLoader.getController();
                controller.setText((Movie) obj);                
            }

            stage.setTitle(windowMessage);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primStage);
            stage.show();

        } catch (IOException ex)
        {

        }
    }       
        
    private void showErrorAlert(String message)
    {
        Alert errAlert = new Alert(Alert.AlertType.ERROR);
        errAlert.setTitle("Error Dialog");
        errAlert.setHeaderText("ERROR");
        errAlert.setContentText(String.format(message));
        errAlert.showAndWait();
    }
    
    @FXML
    private void handleDeleteMovie(javafx.event.ActionEvent event) {
        Movie movie = movieListView.getSelectionModel().getSelectedItem();
        if (movie == null)
        {
        showErrorAlert("You must select a movie to delete");
        return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Confirm deletion");
        alert.setHeaderText(null);
        alert.setContentText(String.format("%s%n%s%n%s", "Are you sure you want to delete: " + movie.getName() + "?", "Be in mind that this movie will be deleted across all categories.", "To remove categories from the movie, edit the movie instead."));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            movieModel.deleteMovie(movie);  
            movieModel.deleteMovieFromCatMovies(movie);
            setCategoryMovies(selectedCategory);
            
        } else
        {
            alert.close();
        }
    }    
    
    @FXML
    private void handleAddMovie(javafx.event.ActionEvent event) throws IOException {
        Stage primStage = (Stage) movieListView.getScene().getWindow();
        openWindow(primStage, null, "AddEditMovieView.fxml", "New Movie");
    }

    @FXML
    private void handleEditMovie(javafx.event.ActionEvent event) throws IOException {
        Movie movie = movieListView.getSelectionModel().getSelectedItem();
        if (movie == null)
        {
        showErrorAlert("You must select a movie to edit");
        return;
        }        
        Stage primStage = (Stage) movieListView.getScene().getWindow();
        openWindow(primStage, movie, "AddEditMovieView.fxml", "Edit Movie");
    }
    
    @FXML
    private void handlePlayMovie(javafx.event.ActionEvent event) throws IOException {
        Movie movie = movieListView.getSelectionModel().getSelectedItem();
        if (movie == null)
        {
            showErrorAlert("You must select a movie to play");
            return;
        }
        File file = new File(movie.getFileLink());
        Desktop.getDesktop().open(file);
        movie.setLastView(new Date(System.currentTimeMillis()));
        movieModel.updateMovie(movie);
        setCategoryMovies(selectedCategory);
        
    }  

    @FXML
    private void handleAddRating(javafx.event.ActionEvent event) {
        Movie movie = movieListView.getSelectionModel().getSelectedItem();
        double rating = (double) addRatingSelector.getSelectionModel().getSelectedItem();
        if (movie == null)
        {
            showErrorAlert("You must select a movie");
            return;
        }        
        movie.setRating(rating);
        movieModel.updateMovie(movie);
        setCategoryMovies(selectedCategory);
    }
    
    
    public Category getSelectedCategory()
    {
        return selectedCategory;
    }
    
    public void setCategories()
    {
        categoryListView.setItems(FXCollections.observableArrayList(movieModel.readAllCategories()));
    }
    
    public void setCategoryMovies(Category category)
    {
        List<Movie> categoryMovies = (category.getId()!=1) ? movieModel.readAllCategoryMovies(category) : movieModel.readAllMovies();
        movieListView.setItems(FXCollections.observableArrayList(categoryMovies));
    }
    
    private void clear()
    {
        categoryListView.getSelectionModel().clearSelection();
        movieListView.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleClearFilter(javafx.event.ActionEvent event) {
    }

    @FXML
    private void handleSetFilter(javafx.event.ActionEvent event) {
    }

    @FXML
    private void handleSorting(javafx.event.ActionEvent event) {
    }
}
