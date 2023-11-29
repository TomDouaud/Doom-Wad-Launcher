/*
 * AlertBox.java 			                     29 november 2023
 */
package application.view;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * Classe for the different types of AlertBox
 * 
 * based on work by :
 * @author Quentin COSTES
 */
public abstract class AlertBox {
    

	/**
	 * Confirmation alertbox
	 * @param message (String) The message that will be shown to the user
	 * @return the result of the user input
	 */
    public static boolean showConfirmationBox(String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Confirmation");

        ButtonType buttonTypeYes = new ButtonType("Validate");
        ButtonType buttonTypeNo = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == buttonTypeYes;
    }
    
    /**
     * Succes alertbox
     * @param message (String) The message that will be shown to the user
     */
    public static void showSuccessBox(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Sucess");

        alert.showAndWait();
    }

    /**
     * Error alertbox
     * @param message (String) The message that will be shown to the user
     */
    public static void showErrorBox(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Error");
        alert.setResizable(true);;

        alert.showAndWait();
    }

    /**
     * Warning alertbox
     * @param message (String) The message that will be shown to the user
     */
    public static void showWarningBox(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Warning");
        
        alert.showAndWait();
    }
    
    
    
    
}
