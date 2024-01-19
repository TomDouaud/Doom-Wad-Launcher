/*
 * HomeController.java 			                     29 november 2023
 */
package application.controller;


import java.io.IOException;

import application.DWL;
import application.model.MainModel;
import application.model.WadBank;
import javafx.fxml.FXML;

/**
 * Controller of the default view.
 * It creates all the method linked to the buttons of the view
 * 
 * @author Tom DOUAUD
 */


public class HomeController {
	
	private MainModel model = MainModel.getInstance();
	
	
		@FXML
		private void refresh() {
			System.out.println("Bouton Refresh");
			model.getWadBank().listerFichiersWadPk3(model.getSearchFolder());
			System.out.print(model.getWadBank().toString() + "OUA");
		}
	

}
