/*
 * DWL.java 				                     30 octobre 2023
 * Made by Tom Douaud, based on https://github.com/GroupeSAETPA1/Quiz 
 * (An academic Quiz project made with other students, please check the github page for further information)
 */

package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Main class that instanciates the View and the Controller
 *
 * TODO A enlever à la fin
 * Mettre dans les "VM argument" :
 *  --module-path /path/to/javafx-sdk-21/lib --add-modules javafx.controls,javafx.fxml
 *
 * @author Tom DOUAUD
 */
public class DWL extends Application {

	/**
	 * Main window of the application.
	 * The scene associated will be modified
	 * with the herited class ViewManager
	 * by the user inputs
	 */
	public static Stage mainWindow;

	/**
	 * TODO commenter
	 */
	private ArrayList<String> ressources;

	/**
     * TODO commenter
     */
	private static HashMap<String, Scene> scenes;

    /**
     * TODO commenter cette méthode
     */
	@Override
	public void start(Stage primaryStage) throws IOException {

		ressources = new ArrayList<>();
		scenes = new HashMap<>();

		ressources.add("Acceuil.fxml");
        // TODO Add here the others view to be loader

		for (String element : ressources) {
			try { 
				Parent root = FXMLLoader.load(getClass().getResource("view/" + element));
				scenes.put(element, new Scene(root));
			} catch (IOException e) {
				System.err.println("Can't load : " + element);
				e.printStackTrace();
			}
		}

		 try {
            primaryStage.getIcons().add(new Image("application/view/images/Proto1.png"));
         } catch (Exception e) {
            System.err.println("Error : Can't find the icon");
         }
		primaryStage.setTitle("DWL - Doom Wad Launcher 0.2");
		mainWindow = primaryStage;
		primaryStage.setScene(scenes.get("Main.fxml"));
		mainWindow.setResizable(false);
		primaryStage.show();
	}
	
	/**
	 * Changes the window to the one in parameter
     * @param window (String) the windows name in .fxml
     */
	public static void changerVue(String window) {
		mainWindow.setTitle("DWL - " + window.split(".fxml")[0]);
		mainWindow.setScene(scenes.get(window));
		mainWindow.show();
	}
	
	/**
     * Function called to quit the application
     */
	public static void quit( ) {
	    Platform.exit();
	}

	/**
	 * Main Function that launches JavaFX and the controllers and the models
	 * @param args not used
	 */
	public static void main(String args[]) {
		launch(args);
		// new ControleurPrincipal();	FIXME
	}
}
