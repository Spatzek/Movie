/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviecollector.be;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author fauxtistic
 */
public class Movie {
    
    private int id;
    private String name;
    private double rating;
    private String fileLink;
    private Date lastView;
    private List<Category> categories;

    public Movie(String name, double rating, String fileLink, Date lastView) {
        this.name = name;
        this.rating = rating;
        this.fileLink = fileLink;
        this.lastView = lastView;
    }     

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public Date getLastView() {
        return lastView;
    }

    public void setLastView(Date lastView) {
        this.lastView = lastView;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    
    public void addCategory(Category category)
    {
        categories.add(category);
    }
    
    public void removeCategory(Category category)
    {
        categories.remove(category);
    }
    
    @Override
    public String toString() {
        return "Movie{" + "id=" + id + ", name=" + name + ", rating=" + rating + ", fileLink=" + fileLink + ", lastView=" + lastView + '}';
    }
           

        
}
