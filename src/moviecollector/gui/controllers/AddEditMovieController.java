/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviecollector.gui.controllers;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import moviecollector.be.Category;
import moviecollector.be.Movie;
import moviecollector.gui.MovieCollectorModel;

/**
 * FXML Controller class
 *
 * @author fauxtistic
 */
public class AddEditMovieController implements Initializable {

    private MovieCollectorModel movieModel;
    private MovieCollectorController movieController;
    private int currentId;
    private Date currentLastView;
    private double currentRating;
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
    @FXML
    private TextField locationTextField;
    @FXML
    private Button removeCategoryButton;
    @FXML
    private Button cancelButton;

    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        movieModel = new MovieCollectorModel();
        Movie tempMovie = new Movie("", 0, "", null);
        tempMovie.setId(currentId);
        currentCats = FXCollections.observableArrayList(movieModel.readAllMovieCategories(tempMovie));
        availableCats = FXCollections.observableArrayList(movieModel.readAllAvailableCategories(tempMovie));
        categoryCombobox.setItems(availableCats);
        movieCatListView.setItems(currentCats);
        
    }    
    
    private void showErrorAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("ERROR");
        alert.setContentText(String.format(message));
        alert.showAndWait();
    }

    @FXML
    private void handleSaveMovie(ActionEvent event) {
        String name = titleTextField.getText();
        String filelink = locationTextField.getText();
        
        Movie movie = new Movie(name, currentRating, filelink, currentLastView);
        movie.setId(currentId);
        movie.setCategories(currentCats);        
                
        if (movie.getName().isEmpty())
        {
            showErrorAlert("Please enter a name");
            return;
        }
        
        // bug to be fixed: if when editing title is changed, and then changed back to original,
        // will interpret as a name in use
        if (movieModel.isMovieNameUsed(movie))
        {
            showErrorAlert("Please enter a name not already in use");
            return;
        }   
        
        if (movie.getFileLink().isEmpty())
        {
            showErrorAlert("Please choose a file location");
            return;
        }
                
        if (!movie.getName().isEmpty() && !movieModel.isMovieNameUsed(movie) && !movie.getFileLink().isEmpty())
        {            
            Alert conAlert = new Alert(Alert.AlertType.CONFIRMATION);
            conAlert.initStyle(StageStyle.UTILITY);
            conAlert.setTitle("Confirm change");
            conAlert.setHeaderText(null);
            String movieString = movie.toString();
            conAlert.setContentText(String.format("%s%n%s", "Are you sure you want to add this:", movieString));
            Optional<ButtonType> result = conAlert.showAndWait();
            if (result.get() == ButtonType.OK) {
                movieModel.saveMovie(movie);
                movieController.setCategoryMovies(movieController.getSelectedCategories());
                
                handleCancel(event);
            } else {
                conAlert.close();
            }
        }              
        
        
        
    }

    @FXML
    private void handleMovieLocate(ActionEvent event) {
        JFileChooser jfc = new JFileChooser();
        FileNameExtensionFilter mp4Filter = new FileNameExtensionFilter(".mp4 Files", "mp4");
        FileNameExtensionFilter mpeg4Filter = new FileNameExtensionFilter(".mpeg4 Files", "mpeg4");        
        jfc.setFileFilter(mp4Filter);
        jfc.setFileFilter(mpeg4Filter);        
        jfc.setAcceptAllFileFilterUsed(false);
        jfc.setCurrentDirectory(new File("."));
        
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();

            if (selectedFile.getAbsolutePath().contains("Movie\\Movie")) {
                Path absolutePath = Paths.get(selectedFile.getAbsolutePath());
                Path pathToProject = Paths.get(System.getProperty("user.dir"));
                Path relativePath = pathToProject.relativize(absolutePath);
                locationTextField.setText(relativePath.toString());
            } else {
                locationTextField.setText(selectedFile.getAbsolutePath());
            }            
            
        }
    }
    
    @FXML
    private void handleAddCategory(ActionEvent event) {
        Category category = categoryCombobox.getSelectionModel().getSelectedItem();
        if (category == null)
        {
            showErrorAlert("Please select a genre to add");
            return;
        }
        availableCats.remove(category);
        currentCats.add(category);
        categoryCombobox.setItems(availableCats);
        movieCatListView.setItems(currentCats);
        
    }

    @FXML
    private void handleRemoveCategory(ActionEvent event) {
        Category category = movieCatListView.getSelectionModel().getSelectedItem();
        if (category == null)
        {
            showErrorAlert("Please select a genre to remove");
            return;
        }        
        currentCats.remove(category);
        availableCats.add(category);
        categoryCombobox.setItems(availableCats);
        movieCatListView.setItems(currentCats);
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
    
    public void setText(Movie movie)
    {
        titleTextField.setText(movie.getName());
        locationTextField.setText(movie.getFileLink());
        currentId = movie.getId();
        currentLastView = movie.getLastView();
        currentRating = movie.getRating();    
        currentCats = FXCollections.observableArrayList(movieModel.readAllMovieCategories(movie));
        availableCats = FXCollections.observableArrayList(movieModel.readAllAvailableCategories(movie));
        movieCatListView.setItems(currentCats);
        categoryCombobox.setItems(availableCats);       
         
    }

    
    
    

    
    
}
