/*
 * WadItemController.java 			                     21 january 2024
 */
package application.controller;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import application.model.MainModel;
import application.model.WadItem;
import application.view.ViewManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.Tooltip;

/**
 * Controller of the wad item view.
 * It creates all the method linked to the buttons of the view
 * 
 * @author Tom DOUAUD
 */
public class WadListViewCell extends ListCell<WadItem> {
	

	private MainModel model = MainModel.getInstance();
	
	@FXML
	private Label wadTitle;
	
	@FXML
	private Label wadDescription;
	
	@FXML
	private Label wadRated;
	
	@FXML
	private Label wadDate;
	
	@FXML
	private Label wadAuthor;
	
	@FXML
	private ImageView wadImage;
	
	@FXML 
	private Pane pane;
	
	private FXMLLoader mLLoader;
	
	
	  @Override
	    protected void updateItem(WadItem wadItem, boolean empty) {
	        super.updateItem(wadItem, empty);

	        if(empty || wadItem == null) {

	            setText(null);
	            setGraphic(null);

	        } else {
	            
	        	
	        	if (mLLoader == null) {
	                mLLoader = new FXMLLoader(getClass().getResource("/application/view/WadItem.fxml"));
	                mLLoader.setController(this);

	                try {
	                    mLLoader.load();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }

	            }
	            


	            wadTitle.setText(wadItem.getTitle());
	            Tooltip titleInformation = new Tooltip(wadTitle.getText());
                Tooltip.install(wadTitle, titleInformation);

	            wadDescription.setText(wadItem.getDescription());
	            Tooltip descriptionInformation = new Tooltip(wadDescription.getText());
	            descriptionInformation.setPrefWidth(300);
	            descriptionInformation.setWrapText(true);
                Tooltip.install(wadDescription, descriptionInformation);
                
	            wadRated.setText(wadItem.getRating());
	            Tooltip ratingInformation = new Tooltip(wadRated.getText());
                Tooltip.install(wadRated, ratingInformation);
                
	            wadDate.setText(wadItem.getRealaseDate());
	            Tooltip dateInformation = new Tooltip(wadDate.getText());
                Tooltip.install(wadDate, dateInformation);
                
	            wadAuthor.setText(wadItem.getAuthor());
	            Tooltip authorInformation = new Tooltip(wadAuthor.getText());
                Tooltip.install(wadAuthor, authorInformation);
                
	            Image image = null;
				try {
					image = new Image(new FileInputStream(wadItem.getImage()));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            wadImage.setImage(image);

	            setText(null);
	            setGraphic(pane);
	        }

	    }
}
