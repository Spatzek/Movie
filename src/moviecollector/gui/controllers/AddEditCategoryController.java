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

    @FXML
    private void handleSaveCategory(ActionEvent event) {
        String name = categoryTextField.getText();
        
        Category category = new Category(name);
        category.setId(currentId);
        
        if (!category.getName().isEmpty() || !movieModel.isCategoryNameUsed(category))
        {
            Alert conAlert = new Alert(Alert.AlertType.CONFIRMATION);
            conAlert.initStyle(StageStyle.UTILITY);
            conAlert.setTitle("Confirm change");
            conAlert.setHeaderText(null);
            String songString = category.toString();
            conAlert.setContentText(String.format("%s%n%s", "Are you sure you want to add this:", songString));
            Optional<ButtonType> result = conAlert.showAndWait();
            if (result.get() == ButtonType.OK) {
                movieModel.createCategory(category);
                
                
                handleCancel(event);
            } else {
                conAlert.close();
            }
        }                    
        
        if (category.getName().isEmpty())
        {
            Alert errAlert = new Alert(Alert.AlertType.ERROR);
            errAlert.setTitle("Error Dialog");
            errAlert.setHeaderText("ERROR");
            errAlert.setContentText(String.format("%s%n%s", "Category failed to save or update.", "Please enter a name."));
            errAlert.showAndWait();
        }
        
        if (movieModel.isCategoryNameUsed(category))
        {
            Alert errAlert = new Alert(Alert.AlertType.ERROR);
            errAlert.setTitle("Error Dialog");
            errAlert.setHeaderText("ERROR");
            errAlert.setContentText(String.format("%s%n%s", "Category name is already in use.", "Please enter another name."));
            errAlert.showAndWait();
        }
        
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
    public void setText(Category category)
    {
        categoryTextField.setText(category.getName());
        currentId = category.getId();
    }
    
}
