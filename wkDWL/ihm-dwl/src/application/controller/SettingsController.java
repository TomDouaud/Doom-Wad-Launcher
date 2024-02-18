/*
 * SettingsController.java 			                     17 febuary 2024
 */
package application.controller;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.DWL;
import application.model.MainModel;
import application.view.AlertBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;


public class SettingsController implements Initializable {
	
	FileChooser fileChooser = new FileChooser();
	 
	DirectoryChooser directoryChooser = new DirectoryChooser();

	 
	@FXML 
	private TextField gzDoomEXEPath;
	
	@FXML 
	private TextField wadPath;
	
	@FXML
	private void browseWADPath() throws IOException {
		File wadDirectoryPath = browseDirectory();
		if (wadDirectoryPath == null) {
			AlertBox.showErrorBox("You didn't choosed a directory for your WAD !");
		} else {
			wadPath.setText(wadDirectoryPath.toString());
			MainModel.getInstance().setSearchFolder(wadDirectoryPath.toString());
			MainModel.getInstance().getWadBank().listerFichiersWadPk3(wadDirectoryPath.toString());
			DWL.loadAndChangeView("Main.fxml");
		}
	}


	@FXML
	private void browseGZDoom() throws IOException {
		File GzDoomEXE = browseFile();
		if (GzDoomEXE == null) {
			AlertBox.showErrorBox("You didn't choosed a path for the GZDoom executable !");
		} else {
			gzDoomEXEPath.setText(GzDoomEXE.toString());
			MainModel.getInstance().setGzDoomFolder(GzDoomEXE.toString());
		}
	}
	
	private File browseFile() {
		File selectedFile = fileChooser.showOpenDialog(DWL.settingsStage);
		return selectedFile;
	}

	private File browseDirectory() {
		File selectedDirectory = directoryChooser.showDialog(DWL.settingsStage);
		return selectedDirectory;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub c'est ptet ici qu'on va dé-serialiser aussi les paths
		fileChooser.getExtensionFilters().addAll(
			     new FileChooser.ExtensionFilter("Executable Files", "*.exe")
		);
	}

}