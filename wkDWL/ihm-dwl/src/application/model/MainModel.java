/*
 * MainModel.java                                    
 * Based on https://github.com/GroupeSAETPA1/Quiz/blob/main/wkQuiz/ihm-quizz/src/application/modele/ModelePrincipal.java
 * made the 26 oct. 2023
 */

package application.model;


// import application.exception.CreerQuestionException;
import application.view.AlertBox;

/**
 * Main model for the application.
 * Enables interaction between the model classes and the controllers.
 * This is a Singleton class
 * @author Lucas Descriaud
 * @author François de Saint Palais
 */
public class MainModel {
		

    private static MainModel model;
    
	// The folder to search for wads or pk3
    private String searchFolder = "C:\\Users\\douau\\Desktop\\DWL\\TestWad"; // TODO change this
    
	/** the names of the file for the serialisation */
	// private static final String FICHIER_SERIALISATION_CATEGORIE = "donneesCategorie";

	/**
     * Constructor of the main model
     */
    private MainModel() {
    	
    	// TODO where is the serialisation
    	// this.banqueCategorie = deSerialiserCategorie();
    	
    	// Si jamais il ya eu des problèmes de sérialisation des catégories,
    	// on parcours les questions et on ajoute les catégories manquantes
    	// TODO for the handeling of the corruption of the serialisation
    	

    }

    /**
     * Getter of the instance of the main model
     * if the main model is null, it will be created
     * @return the instance of ModelePrincipal
     */
    public static MainModel getInstance(){
        if (MainModel.model == null) {
            MainModel.model = new MainModel();
        }
        return model;
    }
    
    // TODO where we put the getter and the setters
    public String getSearchFolder() {
    	return searchFolder;
    }
    
    public void setSearchFolder(String searchFolder) {
    	this.searchFolder = searchFolder;
    }

    
    /**
     * Méthode de de-serialisation des catégories qui lit le fichier "donneesCategorie"
     * et qui ajoute les catégories dans la banque de catégorie
     * @return une banque de catégorie avec les catégories du fichier si il est valide,
     * sinon une banque vide
     * TODO for the serialisation
     */
//    public BanqueCategorie deSerialiserCategorie() {
//		try {
//			File fichierCategorie = new File(FICHIER_SERIALISATION_CATEGORIE);
//			
//			if (fichierCategorie.isFile() && fichierCategorie.canRead()) {
//				FileInputStream inputFichierCategorie = new FileInputStream(fichierCategorie);
//				
//				ObjectInputStream inCategorie = new ObjectInputStream(inputFichierCategorie);	
//				
//				BanqueCategorie banqueDeserialiseeCategorie = (BanqueCategorie)inCategorie.readObject();
//				inCategorie.close();
//				inputFichierCategorie.close();
//				
//				System.out.println("La banque de Categorie à bien été dé-serialisée !");
//				
//				return banqueDeserialiseeCategorie;
//			} else {
//				AlertBox.showErrorBox("Le fichier de sauvegarde " 
//						+ "des catégories à été corrompu ou supprimé, "
//			            + "les catégories sont réinitialisées");
//				return new BanqueCategorie();
//			}	
//			
//		} catch (ClassNotFoundException | IOException e) {
//			AlertBox.showErrorBox("Le fichier de sauvegarde " 
//								+ "des catégories à été corrompu ou supprimé, "
//					            + "les catégories sont réinitialisées");
//			return new BanqueCategorie();
//		}
//    }
//    
//    /**
//     * Méthode de de-serialisation des questions qui lit le fichier "donneesQuestions"
//     * et qui ajoute les questions dans la banque de question
//     * @return une banque de question avec les question du fichier si il est valide,
//     * sinon une banque vide
//     */
//    public BanqueQuestion deSerialiserQuestion() {
//    	try {
//			File fichierQuestion = new File(FICHIER_SERIALISATION_QUESTION);
//			
//			if (fichierQuestion.isFile() && fichierQuestion.canRead()) {	
//				FileInputStream inputFichierQuestion = new FileInputStream(FICHIER_SERIALISATION_QUESTION);
//				ObjectInputStream inQuestion = new ObjectInputStream(inputFichierQuestion);
//				
//				// Méthode pour dé-serialiser la banque de question
//				BanqueQuestion banqueDeserialiseeQuestion = (BanqueQuestion)inQuestion.readObject();
//				
//				inQuestion.close();
//				inputFichierQuestion.close();
//				
//				System.out.println("La banque de Question à bien été dé-serialisée !");
//				
//				return banqueDeserialiseeQuestion;
//			} else {
//				AlertBox.showErrorBox("Le fichier de sauvegarde des " 
//								    + "questions à été corrompu ou supprimé, "
//			            		    + "les questions sont réinitialisées");
//	   		    return new BanqueQuestion();
//			}
//			
//    	} catch(ClassNotFoundException | IOException e) {
//			AlertBox.showErrorBox("Le fichier de sauvegarde des " 
//				    + "questions à été corrompu ou supprimé, "
//        		    + "les questions sont réinitialisées");
//   		    return new BanqueQuestion();
//        }
//    }
    
}
