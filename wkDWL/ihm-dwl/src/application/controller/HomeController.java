/*
 * HomeController.java 			                     29 november 2023
 */
package application.controller;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import application.model.MainModel;
import application.model.WadItem;
import application.view.AlertBox;
import application.view.ViewManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Controller of the default view.
 * It creates all the method linked to the buttons of the view
 *
 * @author Tom DOUAUD
 */


public class HomeController implements Initializable {

	@FXML
	private ListView<WadItem> listview;

	@FXML
	private TextField searchField;

	@FXML
	private ImageView selectedWadImage;

	@FXML
	private ImageView sortImage;

	@FXML
	private Label selectedWadDescription;

	@FXML
	private ComboBox sourcePortSelecter;

	private ObservableList<WadItem> wadObservableList;

	private MainModel model = MainModel.getInstance();

	private boolean ascOrder = true;

	private WadItem selectedWad;

	private Runtime runtime = Runtime.getRuntime();

	public HomeController()  {

        wadObservableList = FXCollections.observableArrayList();

	    // get the wadBank
		wadObservableList.addAll(model.getWadBank().getWads());

	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	// Créez des éléments de choix avec des icônes TODO
//    	SourcePortListCell GZDoom = new SourcePortListCell(new Image("path/to/icon1.png"), "GZDoom");
//    	SourcePortListCell ZDoom = new SourcePortListCell(new Image("path/to/icon2.png"), "Choice 2");
//    	SourcePortListCell choice3 = new SourcePortListCell(new Image("path/to/icon3.png"), "Choice 3");
    	
    	
    	listview.setItems(wadObservableList);
    	listview.setCellFactory(wadListView -> new WadListViewCell());


    	// Ajout d'un ChangeListener pour détecter la sélection d'un élément
        listview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                  // L'action à effectuer lorsque l'élément est sélectionné
                  try {
                	selectedWad = newValue;
					handleItemSelected(newValue);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
              }
          });
    }



	private void handleItemSelected(WadItem newValue) throws FileNotFoundException {
		selectedWadImage.setImage(new Image(new FileInputStream(newValue.getImage())));
		selectedWadDescription.setText(newValue.toString());

	}

	@FXML
	private void play() {
		if (selectedWad != null) {
			// If the wad is a IWAD
			for (String iwad : WadItem.getIwad()) {
				if (selectedWad.getRawTitle().substring(0, selectedWad.getRawTitle().length() - 4).toLowerCase().equals(iwad)) {
					try {
						runtime.exec(model.getGzDoomFolder() 
								  + " -iwad " + model.getSearchFolder() + "/" + selectedWad.getRawTitle());// TODO virer le stub
					} catch(IOException e) {
						AlertBox.showErrorBox("A error occured : " + e.toString()  + " !");
					}
					return;
				}
			}
			
			try {
				runtime.exec(model.getGzDoomFolder() 
						  + "  " 
						  + model.getSearchFolder() + "/" + selectedWad.getRawTitle() 
						  + " -iwad " + model.getSearchFolder() + "/" + "DOOM.WAD");// TODO virer le stub
			} catch(IOException e) {
				AlertBox.showErrorBox("A error occured : " + e.toString()  + " !");
			}
			
		} else {
			AlertBox.showErrorBox("No wad selected !");
		}
	}
	
	@FXML
	private void settings() {
		ViewManager.loadSettings();
	}

	@FXML
	private void refresh() {
		model.getWadBank().listerFichiersWadPk3(model.getSearchFolder());
		wadObservableList.clear();
		wadObservableList.addAll(model.getWadBank().getWads());

	}

	@FXML
	private void sort() throws FileNotFoundException {

		ascOrder = !ascOrder;
		String filterText = searchField.getText();

		if (ascOrder) {
			// Tri ascendant
			wadObservableList.sort(Comparator.comparing(WadItem::getTitle));
			sortImage.setImage(new Image(new FileInputStream("src\\application\\view\\images\\asc.png")));
		} else {
			// Tri descendant
		    wadObservableList.sort(Comparator.comparing(WadItem::getTitle).reversed());
		    sortImage.setImage(new Image(new FileInputStream("src\\application\\view\\images\\desc.png")));
		}

	    // Mettez à jour la ListView après le tri
	    listview.setItems(null);
	    listview.setItems(wadObservableList.filtered(x -> x.getTitle().contains(filterText)));
	}


	 @FXML
	 private void filter() {
	     // Récupérez la chaîne de caractères du filtre depuis votre interface utilisateur
	     String filterText = searchField.getText();

	     listview.setItems(null);
		 listview.setItems(wadObservableList.filtered(x -> x.getTitle().contains(filterText)));

	  }
	 
	 @FXML
	 private void pasImplemente() {
	     AlertBox.showWarningBox("Pas encore implémenté !");

	  }

}