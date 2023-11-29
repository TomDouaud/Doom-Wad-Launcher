/*
 * ViewManager.java 			                     29 november 2023
 */

package application.view;

import java.io.IOException;
import java.util.HashMap;

import application.DWL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Manage the loading and the change og the views
 * 
 * based on work by :
 * @author François de Saint Palais
 */
public class ViewManager {
	
    /** Associates the name of the .fxml file to his scene with the FXMLLoader */
    private static HashMap<String, Scene> scenes;
	
    /**
     * Loads the view
     * @param view (String) the new view like : Example.fxml
     */
    public static void load(String view) {
        DWL dwl = DWL.getInstance();
        try {
            Parent racine 
            = FXMLLoader.load(dwl.getClass().getResource("view/" + view));
            
            scenes.put(view, new Scene(racine));
            
        } catch (IOException e) {
            System.err.println("Can't load : " + view);
        } catch (NullPointerException e) {
            System.err.println("File not found : " + view);
            e.printStackTrace();
        }
    }

    /** 
     * Getter of the Scenes
     * @param view (String) the new view like : Example.fxml
     * @return the value of the scene 
     */
    public static Scene getScene(String vue) {
        return scenes.get(vue);
    }

    /** 
     * Initialize the scenes
     */
    public static void initializeScene() {
        scenes = new HashMap<String, Scene>();
    }
    
    /**
     * Change of the view management
     * @param view (String) the new view like : Example.fxml
     */
    public static void changeView(String view) {
        System.out.println(view);
        DWL.mainStage.setTitle("DWL - " + view.split(".fxml")[0]);
        DWL.mainStage.setScene(ViewManager.getScene(view));
        
        DWL.mainStage.show();
    }

}
