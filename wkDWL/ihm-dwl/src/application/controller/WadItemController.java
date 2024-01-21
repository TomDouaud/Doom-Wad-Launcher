/*
 * WadItemController.java 			                     21 january 2024
 */
package application.controller;


import java.io.IOException;

import application.DWL;
import application.model.MainModel;
import application.model.WadBank;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.text.Text;

/**
 * Controller of the wad item view.
 * It creates all the method linked to the buttons of the view
 * 
 * @author Tom DOUAUD
 */
public class WadItemController {
	

	private MainModel model = MainModel.getInstance();
	
	@FXML
	private Text wadTitle;
	
	@FXML
	private Text wadDescription;
	
	@FXML
	private Text wadDL;
	
	@FXML
	private Text wadRated;
	
	@FXML
	private Text wadDate;
	
	@FXML
	private Text wadAuthor;
	
	@FXML
	private Image wadImage;
	
	
	 @FXML
	 public void initialize() {
		 
	 }
}
