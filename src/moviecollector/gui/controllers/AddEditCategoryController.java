/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviecollector.gui.controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import moviecollector.be.Category;
import moviecollector.gui.MovieCollectorModel;

/**
 * FXML Controller class
 *
 * @author fauxtistic
 */
public class AddEditCategoryController implements Initializable {

    private MovieCollectorModel movieModel;
    private int currentId;
    private MovieCollectorController movieController;
    
    @FXML
    private TextField categoryTextField;
    @FXML
    private Button saveCategoryButton;
    @FXML
    private Button cancelButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        movieModel = new MovieCollectorModel();
    }    
    /**
     * Saves the category to the category list
     * @param event 
     */
    @FXML
    private void handleSaveCategory(ActionEvent event) {
        String name = categoryTextField.getText();
        
        Category category = new Category(name);
        category.setId(currentId);
        
        if (category.getName().isEmpty())
        {
            showErrorAlert("Please enter a name");
        }
        
        if (movieModel.isCategoryNameUsed(category))
        {
            showErrorAlert("Please enter a name not already in use");
        }
        
        if (!category.getName().isEmpty() && !movieModel.isCategoryNameUsed(category))
        {
            Alert conAlert = new Alert(Alert.AlertType.CONFIRMATION);
            conAlert.initStyle(StageStyle.UTILITY);
            conAlert.setTitle("Confirm change");
            conAlert.setHeaderText(null);            
            conAlert.setContentText(String.format("%s%n%s", "Are you sure you want to save this:", category.getName()));
            Optional<ButtonType> result = conAlert.showAndWait();
            if (result.get() == ButtonType.OK) {
                movieModel.saveCategory(category);
                movieController.setCategories();
                
                handleCancel(event);
            } else {
                conAlert.close();
            }
        }                    
        
        
        
    }
/**
 * 
 * makes an error in case there is a dublication of names
 * @param message 
 */
    private void showErrorAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("ERROR");
        alert.setContentText(String.format(message));
        alert.showAndWait();
    }
    
    @FXML
    private void handleCancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
    public void setController(MovieCollectorController movieController)
    {
        this.movieController = movieController;
    }
    
    public void setText(Category category)
    {
        categoryTextField.setText(category.getName());
        currentId = category.getId();
    }
    
}
