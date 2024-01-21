/*
 * HomeController.java 			                     29 november 2023
 */
package application.controller;


import java.io.IOException;

import application.DWL;
import application.model.MainModel;
import application.model.WadBank;
import application.view.ViewManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;

/**
 * Controller of the default view.
 * It creates all the method linked to the buttons of the view
 * 
 * @author Tom DOUAUD
 */


public class HomeController {
	
	@FXML
	private ListView listView;
	
	private MainModel model = MainModel.getInstance();
	

    @FXML
    public void initialize() {
    	// appel des factories
//    	listView
    	
    	// Chargez chaque élément à partir du fichier FXML et ajoutez-le à la liste
        for (int i = 0; i < model.getWadBank().getWads().size(); i++) {
            try {
                // LE CODE ICI POUR CHARGER UNE PAR UNE LES VIEWS ET LEUR CONTROLLEURS
                FXMLLoader loader = new FXMLLoader(DWL.class.getResource("view/WadItem.fxml"));
            	Parent wadItem = loader.load();
            	
            	
            	// Vous pouvez accéder au contrôleur pour définir les valeurs
                WadItemController controller = loader.getController();
                
                listView.getItems().add(wadItem);
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
	
	@FXML
	private void refresh() {
		System.out.println("Bouton Refresh");
		model.getWadBank().listerFichiersWadPk3(model.getSearchFolder());
		System.out.print(model.getWadBank().toString() + "OUA");
	}
}