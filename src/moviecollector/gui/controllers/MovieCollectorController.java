/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviecollector.gui.controllers;

import java.awt.Desktop;
import javafx.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Comparator;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
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
    private List<Category> selectedCategories;    
    private boolean filterOn;
    private String searchTerm;
    private double minRating;
    private boolean sortingByTitle;
    private boolean sortingByRating;
    private static final double DELETION_CANDIDATE_MAX_RATING = 6;
    private static final int DELETION_CANDIDATE_AGE_IN_YEARS = 2;
    
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
        searchTerm = "";
        minRating = 0;
        filterOn = false;
        sortingByTitle = false;
        sortingByRating = false;
        addRatingSelector.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        minimumRating.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        sortCombobox.getItems().addAll("Sort by title", "Sort by rating");        
        categoryListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        setCategories();
        showDeletionSuggestionAlert();
    }    

    /**
     * Categories selected or deselected by clicking with mouse
     * are added to or removed from list of selected categories, 
     * whose associated movies are to be displayed
     * @param event 
     */
    @FXML
    private void categorySelected(MouseEvent event) {
        selectedCategories = categoryListView.getSelectionModel().getSelectedItems();
        setCategoryMovies(selectedCategories);
    }

    /**
     * Deletes single selected category
     * @param event 
     */
    @FXML
    private void handleDeleteCategory(ActionEvent event) {        
        if (selectedCategories == null || selectedCategories.size()>1)
        {
        showErrorAlert("You must select a single category to delete");
        return;
        }
        if (selectedCategories.get(0).getId()==1)
        {
            showErrorAlert("This category can not be deleted");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Confirm deletion");
        alert.setHeaderText(null);
        Category selectedCategory = selectedCategories.get(0);
        alert.setContentText("Are you sure you want to delete: " + selectedCategory.getName() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            movieModel.deleteCategory(selectedCategory);
            clear();
            setCategoryMovies(selectedCategories);            
            setCategories();
            
        } else
        {
            alert.close();
        }
    }

    /**
     * Opens window allowing user to add category
     * @param event
     * @throws IOException 
     */
    @FXML
    private void handleAddCategory(ActionEvent event) throws IOException {
        Stage primStage = (Stage) categoryListView.getScene().getWindow();
        openWindow(primStage, null, "/moviecollector/gui/views/AddEditCategoryView.fxml", "New Category");
    }

    /**
     * Opens window allow user to edit category
     * @param event
     * @throws IOException 
     */
    @FXML
    private void handleEditCategory(ActionEvent event) throws IOException {        
        if (selectedCategories == null || selectedCategories.size()>1)
        {
        showErrorAlert("You must select a single category to edit");
        return;
        }
        if (selectedCategories.get(0).getId()==1)
        {
            showErrorAlert("This category can not be edited");
            return;
        }
        Category selectedCategory = selectedCategories.get(0);
        Stage primStage = (Stage) categoryListView.getScene().getWindow();        
        openWindow(primStage, selectedCategory, "/moviecollector/gui/views/AddEditCategoryView.fxml", "Edit Category");
    }
    
    /**
     * Opens window according to parameters
     * @param primStage
     * @param obj
     * @param viewFXML .fxml file
     * @param windowMessage Title of opened window
     */
    public void openWindow(Stage primStage, Object obj, String viewFXML, String windowMessage)
    {

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(viewFXML));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            
            if (viewFXML=="/moviecollector/gui/views/AddEditCategoryView.fxml")
            {
                AddEditCategoryController controller = fxmlLoader.getController();
                controller.setController(this);
            }
            else if (viewFXML=="/moviecollector/gui/views/AddEditMovieView.fxml")
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
        
    /**
     * Displays dialog box with message
     * @param message 
     */
    private void showErrorAlert(String message)
    {
        Alert errAlert = new Alert(Alert.AlertType.ERROR);
        errAlert.setTitle("Error Dialog");
        errAlert.setHeaderText("ERROR");
        errAlert.setContentText(String.format(message));
        errAlert.showAndWait();
    }
    
    /**
     * Deletes selected movie
     * @param event 
     */
    @FXML
    private void handleDeleteMovie(ActionEvent event) {
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
            setCategoryMovies(selectedCategories);
            
        } else
        {
            alert.close();
        }
    }    
    
    /**
     * Opens window allowing user to add movie
     * @param event
     * @throws IOException 
     */
    @FXML
    private void handleAddMovie(ActionEvent event) throws IOException {
        Stage primStage = (Stage) movieListView.getScene().getWindow();
        openWindow(primStage, null, "/moviecollector/gui/views/AddEditMovieView.fxml", "New Movie");
    }

    /**
     * Opens window allowing user to edit movie
     * @param event
     * @throws IOException 
     */
    @FXML
    private void handleEditMovie(ActionEvent event) throws IOException {
        Movie movie = movieListView.getSelectionModel().getSelectedItem();
        if (movie == null)
        {
        showErrorAlert("You must select a movie to edit");
        return;
        }        
        Stage primStage = (Stage) movieListView.getScene().getWindow();
        openWindow(primStage, movie, "/moviecollector/gui/views/AddEditMovieView.fxml", "Edit Movie");
    }
    
    /**
     * Opens file with selected movie's filepath with default media player
     * @param event
     * @throws IOException
     * @throws SQLException 
     */
    @FXML
    private void handlePlayMovie(ActionEvent event) throws IOException, SQLException {
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
        setCategoryMovies(selectedCategories);
        
    }  

    /**
     * Adds selected rating to selected movie
     * @param event
     * @throws SQLException 
     */
    @FXML
    private void handleAddRating(ActionEvent event) throws SQLException {
        Movie movie = movieListView.getSelectionModel().getSelectedItem();
        double rating = (double) addRatingSelector.getSelectionModel().getSelectedItem();
        if (movie == null)
        {
            showErrorAlert("You must select a movie");
            return;
        }        
        movie.setRating(rating);
        movieModel.updateMovie(movie);
        setCategoryMovies(selectedCategories);        
    }            
    
    public List<Category> getSelectedCategories()
    {
        return selectedCategories;
    }
    
    /**
     * Displays list of all categories in listview
     */
    public void setCategories()
    {
        categoryListView.setItems(FXCollections.observableArrayList(movieModel.readAllCategories()));
    }
    
    /**
     * Displays list of movies belonging to any of selected categories in listview
     * Uses variables set from filter and sort methods to pass to methods which
     * retrieve list of movies according to these
     * @param selectedCategories 
     */
    public void setCategoryMovies(List<Category> selectedCategories)
    {
        boolean isAllCategoriesSelected = false;
        
        for (Category category : selectedCategories)
        {
            if (category.getId()==1)
            {
                isAllCategoriesSelected = true;
                break;
            }
        }
        
        List<Movie> categoryMovies = (isAllCategoriesSelected==true) ? movieModel.readFilteredMovies(minRating, searchTerm) : movieModel.readFilteredCategoryMovies(selectedCategories, minRating, searchTerm);
                
        if(sortingByTitle)
        {
            categoryMovies.sort(Comparator.comparing(Movie::getName));
        }
        else if (sortingByRating)
        {
            categoryMovies.sort(Comparator.comparing(Movie::getRating));
        }
        
        movieListView.setItems(FXCollections.observableArrayList(categoryMovies));
    }
    
    /**
     * Clears selection from both listviews
     */
    private void clear()
    {
        categoryListView.getSelectionModel().clearSelection();
        movieListView.getSelectionModel().clearSelection();
    }

    /**
     * Clears chosen filter and sorting method, resets instance variables
     * @param event 
     */
    @FXML
    private void handleClearFilter(ActionEvent event) {
        filterOn = false;
        sortingByTitle = false;
        sortingByRating = false;
        searchTerm = "";
        minRating = 0;
        searchButton.setText("Set filter");
        searchButton.setTextFill(Color.BLACK);
        filterTitleField.clear();
        minimumRating.getSelectionModel().clearSelection();
        sortCombobox.getSelectionModel().clearSelection();
        if (selectedCategories!=null)
        {
            setCategoryMovies(selectedCategories);
        }
    }

    /**
     * Sets filter according to input from textfield and combobox.
     * @param event 
     */
    @FXML
    private void handleSetFilter(ActionEvent event) {
        searchTerm = (!filterTitleField.getText().isEmpty()) ? filterTitleField.getText() : "";
        minRating = (minimumRating.getSelectionModel().getSelectedItem()!=null) ? minimumRating.getSelectionModel().getSelectedItem() : 0;
        filterOn = true;     
        searchButton.setText("New filter");
        searchButton.setTextFill(Color.RED);
        if (selectedCategories!=null)
        {
            setCategoryMovies(selectedCategories);
        }
    }

    /**
     * Sets sorting method chosen by user
     * @param event 
     */
    @FXML
    private void handleSorting(ActionEvent event) {
        if (sortCombobox.getSelectionModel().getSelectedIndex()==0)
        {
            sortingByRating = false;
            sortingByTitle = true;            
        }
        else if (sortCombobox.getSelectionModel().getSelectedIndex()==1)
        {
            sortingByTitle = false;
            sortingByRating = true;           
        }
        
        if (selectedCategories!=null)
        {
            setCategoryMovies(selectedCategories);
        }
    }
    
    /**
     * When opening the program, dialog box displays list of movies with 
     * rating below specified variable and older than specified number of years,
     * and offers user choice to delete them
     */
    private void showDeletionSuggestionAlert()
    {
        List<Movie> movies = movieModel.readBadOldMovies(DELETION_CANDIDATE_MAX_RATING, DELETION_CANDIDATE_AGE_IN_YEARS);
        if (!movies.isEmpty())
        {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setResizable(true);
        alert.setTitle("Old movies with low ratings");
        alert.setHeaderText(null);
        String movieTitles = "";
        for (Movie movie : movies)
        {
            movieTitles = movieTitles + movie.getName() + ", last seen: " + movie.getLastView() + "\n";
        }
        String warning  = "These movies are rated below " + DELETION_CANDIDATE_MAX_RATING + " and are more than " + DELETION_CANDIDATE_AGE_IN_YEARS + " years old.";
        alert.setContentText(String.format("%s%n%s%n%n%s", warning, "Press ok to delete them, otherwise cancel.", movieTitles));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            movieModel.deleteBadOldMovies(movies);
            
        } else
        {
            alert.close();
        }
    }
    }
}
