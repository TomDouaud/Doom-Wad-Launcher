/*
 * HomeController.java 			                     29 november 2023
 */
package application.controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.DWL;
import application.model.MainModel;
import application.model.WadBank;
import application.model.WadItem;
import application.view.ViewManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;

/**
 * Controller of the default view.
 * It creates all the method linked to the buttons of the view
 * 
 * @author Tom DOUAUD
 */


public class HomeController implements Initializable {
	
	@FXML
	private ListView<WadItem> listview; // peut etre  ListView<WadItem>
	
	private ObservableList<WadItem> wadObservableList;
	
	private MainModel model = MainModel.getInstance();
	
	public HomeController()  {

        wadObservableList = FXCollections.observableArrayList();

	    // get the wadBank
		wadObservableList.addAll(model.getWadBank().getWads());

	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	  listview.setItems(wadObservableList);
    	  listview.setCellFactory(wadListView -> new WadListViewCell());
    }
	
	@FXML
	private void refresh() {
		System.out.println("Bouton Refresh");
		model.getWadBank().listerFichiersWadPk3(model.getSearchFolder());
		wadObservableList.clear();
		wadObservableList.addAll(model.getWadBank().getWads());
		
	}

}