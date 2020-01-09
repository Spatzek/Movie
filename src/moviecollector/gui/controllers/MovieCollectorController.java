/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviecollector.gui.controllers;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
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
        List<Movie> categoryMovies = (category.getId()!=1) ? movieModel.readAllCategoryMovies(category) : movieModel.readAllMovies();
        movieListView.setItems(FXCollections.observableArrayList(categoryMovies));
    }

    @FXML
    private void handleDeleteCategory(javafx.event.ActionEvent event) {
        Category category = categoryListView.getSelectionModel().getSelectedItem();
        if (category.getId()==1)
        {
            showUneditableAlert();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Confirm delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete: " + category.getName() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            movieModel.deleteCategory(category);            
            
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
        Category category = categoryListView.getSelectionModel().getSelectedItem();
        if (category == null)
        {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("ERROR");
        alert.setContentText(String.format("You must select a category before you can edit"));
        alert.showAndWait();
        return;
        }
        if (category.getId()==1)
        {
            showUneditableAlert();
            return;
        }
        Stage primStage = (Stage) categoryListView.getScene().getWindow();        
        openWindow(primStage, category, "AddEditCategoryView.fxml", "Edit Category");
    }
    
    public void openWindow(Stage primStage, Object obj, String viewFXML, String windowMessage)
    {

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(viewFXML));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            if (obj instanceof Category)
            {
                AddEditCategoryController controller = fxmlLoader.getController();
                controller.setText((Category) obj);
            } 
//            else if (obj instanceof Movie)
//            {
//                AddEditMovieController controller = fxmlLoader.getController();
//                controller.setText((Movie) obj);
//            }

            stage.setTitle(windowMessage);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primStage);
            stage.show();

        } catch (IOException ex)
        {

        }
    }
    
    private void showUneditableAlert()
    {
        Alert errAlert = new Alert(Alert.AlertType.ERROR);
        errAlert.setTitle("Error Dialog");
        errAlert.setHeaderText("ERROR");
        errAlert.setContentText(String.format("This category can not be deleted or edited"));
        errAlert.showAndWait();
    }
}
