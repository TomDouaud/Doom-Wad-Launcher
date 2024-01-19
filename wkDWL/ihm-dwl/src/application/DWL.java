/*
 * DWL.java 				                     29 november 2023
 */

package application;

import java.io.IOException;
import java.util.ArrayList;

import application.model.MainModel;
import application.view.AlertBox;
import application.view.ViewManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Main class of the application who instanciates the view, 
 * the models and the cotrollers
 *
 * TODO A enlever à la fin
 * Mettre dans les "VM argument" :
 *  --module-path /path/to/javafx-sdk-21/lib --add-modules javafx.controls,javafx.fxml
 *
 * @author Tom DOUAUD
 */
public class DWL extends Application {

	/**
	 * The main stage of the application
	 * The scene associated will be modified by the herited class ViewManager
	 * depending of the user's inputs
	 */
	public static Stage mainStage;
	
	private static DWL instance;

	/** The name of all the .fxml files of the application */
	private ArrayList<String> ressources;

    /**
     * Main function who launches the JavaFX window
     * @param args not used
     */
    public static void main(String[] args) {
    	launch(args);
    }
    
    /**
     * Lauches the application
     * @throws InvalidNameException 
     */
	@Override
	public void start(Stage primaryStage) throws IOException {
        instance = this;
		ressources = new ArrayList<String>();
		ViewManager.initializeScene();
		MainModel.getInstance();
		
	    ressources.add("Main.fxml");
		// ressources.add("Editeur.fxml"); TODO where we put the other pages
		
		for (String element : ressources) {
			ViewManager.load(element);
		}

		 try {
		    Image logo 
		    = new Image("application/vue/images/IconePrincipale.png"); // TODO le logo de l'application
		    
            primaryStage.getIcons().add(logo);
         } catch (Exception e) {
            System.err.println("Error : the icon is not found");
         }

		 
		primaryStage.setTitle("DWL - Doom Wad Launcher");
		mainStage = primaryStage;
		primaryStage.setScene(ViewManager.getScene("Main.fxml"));
		mainStage.setResizable(false);
		
		primaryStage.setOnCloseRequest((e) -> {
			try {
			    if (AlertBox.showConfirmationBox("Do you really whant to quit the application ?")) {
			    	// MainModel.getInstance().serialiser();
			        Platform.exit();            
		        } else {
		        	// If the user made a mistake, 
		        	// he can cancel the exit function
		        	e.consume();
		        }
			} catch (InternalError e1) { // | IOException e1) { TODO the serialisation
				e1.printStackTrace();
				System.err.println("Error in the save of the data");
			}
		});
		
		primaryStage.show();
	}
	
	/**
	 * Getter of the instance of this class
	 * @return the instance of this class
	 */
	public static DWL getInstance() {
    	return instance;
    }
	
	/**
	 * Load another view with the ViewManager
	 * @param view (String) the new view like : Example.fxml
	 */
	public static void load(String view) {
		ViewManager.load(view);
	}

    /**
	 * Window change management
     * @param view (String) the new view like : Example.fxml
     */
	public static void changeView(String view) {
		ViewManager.changeView(view);
	}
	
	/**
     * Fuction called by the controlleurs to quit the application
	 * @throws IOException 
	 * @throws InternalError 
     */
	public static void quit() throws InternalError, IOException {
	    if (AlertBox.showConfirmationBox("Do you really whant to quit the application ?")) {
	    	// MainModel.getInstance().serialize(); TODO for the serialisation
	        Platform.exit();            
        }
	}
	
	/**
	 * Reloads a view
	 * @param view (String) the new view like : Example.fxml
	 */
	public static void loadAndChangeView(String view) {
		ViewManager.load(view);
		ViewManager.changeView(view);
	}
	

}
