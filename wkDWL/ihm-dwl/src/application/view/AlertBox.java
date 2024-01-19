/*
 * AlertBox.java                                    26 oct. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package application.view;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;

/**
 * Classe contenant les différents types d'alertbox
 * 
 * @author Quentin COSTES
 */
public abstract class AlertBox {
    

	/**
	 * Alert box de confirmation 
	 * @param message
	 * @return
	 */
    public static boolean showConfirmationBox(String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Confirmation");

        ButtonType buttonTypeYes = new ButtonType("Valider");
        ButtonType buttonTypeNo = new ButtonType("Annuler");

        alert.getButtonTypes().setAll(buttonTypeNo, buttonTypeYes);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == buttonTypeYes;
    }
    
    /**
     * Alert box pour afficher le succès d'une opération
     * @param message
     */
    public static void showSuccessBox(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Succès");

        alert.showAndWait();
    }

    /**
     * Alert box pour afficher une erreur
     * @param message
     */
    public static void showErrorBox(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Erreur");
        alert.setResizable(true);;

        alert.showAndWait();
    }

    /**
     * Alert box pour afficher un message d'erreur avec une zone de log
     * @param message le message d'erreur 
     * @param log tout le log d'erreur à envoyer
     */
    public static void showLongErrorBox(String message, String log) {
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setHeaderText(null);
    	alert.setContentText(message);
    	alert.setTitle("Erreur");
    	alert.setResizable(false);
    	
    	TextArea logMessage = new TextArea();
    	logMessage.setText(log);
    	alert.getDialogPane().setExpandableContent(logMessage);
    	
    	alert.showAndWait();
    }
    
    /**
     * Alert box pour afficher un message d'avertissement
     * @param message
     */
    public static void showWarningBox(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Attention");
        
        alert.showAndWait();
    }
}