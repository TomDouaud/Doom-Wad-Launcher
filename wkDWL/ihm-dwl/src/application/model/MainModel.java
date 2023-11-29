/*
 * ModelePrincipal.java                                    26 oct. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package application.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import application.exception.CreerQuestionException;
import application.exception.DifficulteException;
import application.exception.HomonymeException;
import application.exception.InvalidFormatException;
import application.exception.InvalidNameException;
import application.exception.ReponseException;
import application.vue.AlertBox;

/**
 * Contrôleur principale de l'application .
 * Permet l'interaction entre les classes du modèle et les controleurs.
 * C'est une classe Singleton
 * @author Lucas Descriaud
 * @author François de Saint Palais
 */
public class MainModel {
		

    /**
     * Lie une difficulté à son équivalent en string
     * Ex : 1 -> Facile
     */
    public static final HashMap<Integer, String> INT_DIFFICULTE_TO_LABEL
    = new HashMap<>();

    /**
     * Lie une difficulté à son équivalent numérique
     * Ex : Facile -> 1
     */
    public static final HashMap<String, Integer> LABEL_DIFFICULTE_TO_INT
    = new HashMap<>();
    
    /**
     * Lie un charactere a son code pour le chiffement
     */
    public static final HashMap<Character, Integer> ALPAHABET_TO_INT = new HashMap<>();
    
    /**
     * Lie un charactere a son code pour le chiffement
     */
    public static final HashMap<Integer, Character> INT_TO_ALPHABET = new HashMap<>();
    

    /* L'alphabet personnalisé */
    
    
    private static MainModel modele;
	/** Le nom des fichiers pour la sérialisation */
	private static final String FICHIER_SERIALISATION_CATEGORIE = "donneesCategorie";
	private static final String FICHIER_SERIALISATION_QUESTION = "donneesQuestion";
	
	
    /** Les CSV importés devront séparer leur éléments avec une tabulation */
    public static final char SEPARATEUR_CSV = '\t';

    
    private BanqueCategorie banqueCategorie;
    private BanqueQuestion banqueQuestion;

	private Categorie catgorieAModifier;
    private boolean displayCategoriePane;

    private String pagePrecedente;

    private Partie partie;

    private Question questionAModifier;
    
    private ArrayList<Question> questionAEnvoyer = new ArrayList<Question>();
    private ArrayList<Categorie> categorieAEnvoyer = new ArrayList<Categorie>();

    /**
     * Constructeur du modele principal
     */
    private MainModel() {
    	
    	this.banqueCategorie = deSerialiserCategorie();
    	this.banqueQuestion = deSerialiserQuestion();
    	
    	// Si jamais il ya eu des problèmes de sérialisation des catégories,
    	// on parcours les questions et on ajoute les catégories manquantes
    	for (Question question : this.banqueQuestion.getQuestions()) {
			if (this.getCategoriesLibelleExact(question.getCategorie()) == null) {
				try {
					this.creerCategorie(question.getCategorie());
				} catch (InvalidNameException | HomonymeException e) {
					e.printStackTrace();
				}
			}
		}
    	
        this.partie = new Partie();


        LABEL_DIFFICULTE_TO_INT.put("Facile", 1);
        LABEL_DIFFICULTE_TO_INT.put("Moyen", 2);
        LABEL_DIFFICULTE_TO_INT.put("Difficile", 3);
        LABEL_DIFFICULTE_TO_INT.put("Tous" , 0);

        INT_DIFFICULTE_TO_LABEL.put(1, "Facile");
        INT_DIFFICULTE_TO_LABEL.put(2, "Moyen");
        INT_DIFFICULTE_TO_LABEL.put(3, "Difficile");
        INT_DIFFICULTE_TO_LABEL.put(0 , "Tous");
        

    }

    /**
     * Getter de l'intance du modele principal
     * @return Renvoie l'instance unique de ModelePrincipal
     */
    public static MainModel getInstance(){
        if (MainModel.modele == null) {
            MainModel.modele = new MainModel();
        }
        return modele;
    }

    /**
     * Vérifie si une catégorie contient des Questions
     * @param categorie La categorie ou les questions sont recherchées
     * @return Revoie true si la categorie contient des questions, false sinon
     */
    public boolean categorieContientQuestion(Categorie categorie) {
        ArrayList<Question> questionExistante = banqueQuestion.getQuestions();
        boolean categorieVide = questionExistante.size() != 0;
        
        for (int i = 0; i < questionExistante.size() && categorieVide; i++) {
            categorieVide = !questionExistante.get(i).getCategorie()
                            .equals(categorie.getNom());
        }
        
        return !categorieVide;
    }

	/**
	 * Vérifie si la catégorie existe
     * @param nom Le nom de la catégorie recherché
     * @return true si la catégorie existe, false sinon
     */
    public boolean categorieExiste(String nom) {
        return banqueCategorie.getCategorieLibelleExact(nom) != null;
    }

	/**
     * Crée une categorie et l'ajoute a la banque de catégorie. Si la création a été
     * un sucés on revoie true
     *
     * @param nom Le nom donnée à la categorie.
     * @return true si la création est un sucés false sinon
     * @throws InvalidNameException Si le nom choisie est invalide
     * @throws HomonymeException    Si la catégorie existe déjà
     */
    public boolean creerCategorie(String nom) throws InvalidNameException, HomonymeException {
        // On propage à l'appelant les éventuelles exceptions liés à la création
        Categorie aAjouter = new Categorie(nom);

        // On propage à l'appelant les éventuelles exceptions liés à la création
        banqueCategorie.ajouter(aAjouter);
        
        return true;
    }

    /**
     * Ajoute une question à la banque de question en appelant le constructeur de
     * question.
     *
     * @param libelle       : libelle de la question
     * @param idCategorie   : catégorie de la question
     * @param difficulte    : difficulté de la question
     * @param reponseFausse : liste de mauvaise réponse
     * @param feedback      : feedback de la question
     * @param reponseJuste  : bonne réponse de la question
     * @return true si la question a été crée, false sinon
     * @throws InvalidNameException 
     * @throws HomonymeException
     * @throws DifficulteException
     */
    public boolean creerQuestion(String libelle, int idCategorie, int difficulte, String reponseJuste,
            ArrayList<String> reponseFausses, String feedback)
            throws CreerQuestionException, InvalidNameException, HomonymeException {

        // La catégorie de la question existera toujours donc aucune vérification d'existence n'est nécessaire
        Categorie categorieQuestion;
        try {
             categorieQuestion = banqueCategorie.getCategorie(idCategorie);
        } catch (IndexOutOfBoundsException e) {
            categorieQuestion = banqueCategorie.categorieGeneral;
        }
        
        // Si une exception apparais on la propage au controlleur appellant
        Question aAjouter = new Question(libelle, categorieQuestion, difficulte,
                reponseJuste, reponseFausses, feedback);

        // Si une exception apparais on la propage au controlleur appellant
        banqueQuestion.ajouter(aAjouter);
        return true;

    }

    /** 
     * Getter de la banque de categorie du modele principal
     * @return La BanqueCategorie 
     */
    public BanqueCategorie getBanqueCategorie() {
        // TODO Je pense que l'on peux changer la visibilité de certaine méthode de
        // Question. Pour éviter les effet de bords
        return banqueCategorie;
    }

    /** 
     * Getter de la banque de question du modele principal
     * @return La BanqueQuestion 
     */
    public BanqueQuestion getBanqueQuestion() {
        // TODO Je pense que l'on peux changer la visibilité de certaine méthode de
        // Question. Pour éviter les effet de bords
        return banqueQuestion;
    }

    /**
     * Getter de la catégorie à modifier
     *  @return valeur de categorieAModifier
     */
    public Categorie getCategorieAModifier() {
        return catgorieAModifier;
    }

    /**
     * Getter de la liste des catégories du modele principal
     * @return Une liste des catégories 
     */
    public ArrayList<Categorie> getCategories() {
        return banqueCategorie.getCategories();
    }

    /**
     * Récupere les catégories qui ont un libellé recherché
     * @param libellé Le nom de catégorie recherché
     * @return Les catégories qui ont libellé dans leur nom
     */
    public ArrayList<Categorie> getCategoriesLibelle(String libelle) {
		return this.getBanqueCategorie().getCategoriesLibelle(libelle);
	}

    /**
     * Retourne l'indice de la catégorie dans la liste des catégories
     * @param string la catégorie recherchée
     * @return L'indice de la catégorie
     */
    public int getIndice(String string) {
        return banqueCategorie.getIndice(string);
    }

    /**
     * Retourne le nombre de question d'une catégorie
     * @param categorie La catégorie dont on veut connaître le nombre de question
     * @return le nombre de questions présentes dans une catégorie
     */
    public int getNombreQuestionCategorie(Categorie categorie) {
        return getBanqueQuestion().getQuestions(categorie).size();
    }

    /** 
     * Getter du nom de la page précédente
     * @return la page précédente 
     */
    public String getPagePrecendente() {
    	return pagePrecedente;
    }

    /** 
     * Getter de la partie du modèle principal
     * @return la partie 
     */
    public Partie getPartie() {
		return partie;
	}

    /**
     * Getter de la question à modifier
     * @return la question à modifier 
     */
    public Question getQuestionAModifier() {
        return questionAModifier;
    }
    
    /**
     * Renvoie la catégorie qui à exactement le même libellé que celui passé en paramètre
     * @param libelle (String) le libellé recherché sensible à la casse
     * @return la catégorie avec le libellé voulu si elle existe, null sinon
     */
    public Categorie getCategoriesLibelleExact(String libelle) {
        return banqueCategorie.getCategorieLibelleExact(libelle);
    }

    /**
     * Utilisé pour se positionner sur le bon onglet 
     * quand on passe d'Editer à Créer
     * @return displayCategoriePane 
     */
    public boolean isDisplayCategoriePane() {
		return displayCategoriePane;
	}

    /**
     * Modifie la catégorie catgorieAModifier stockée dans le modèle 
     * @param nouveauNom Le nouveau nom de la catégorie
     * @return true si la modification est effective
     * @throws InvalidNameException si le nom choisi est invalide
     * @throws HomonymeException 
     */
    public boolean modifierCategorie(String nouveauNom) throws InvalidNameException, HomonymeException {
        if (getCategoriesLibelleExact(nouveauNom) == null) {
        	catgorieAModifier.setNom(nouveauNom);
        	return true;
        } else {
        	throw new HomonymeException("La categorie est déja présente dans la banque de categorie.");
        }
    }

    /**
     * Modifie la questionAModifier stocké dans le modèle
     *
     * @param libelle        Le nouveau nom de la question
     * @param categorie      La nouvelle categorie de la question
     * @param difficulte     La nouvelle difficulté de la question
     * @param reponseJuste   La nouvelle réponse juste de la question
     * @param reponseFausses Les nouvelles réponses fausses de la question
     * @param feedback       Le nouveau feedback de la question
     * @return true si la question à bien été modifiée
     */
    public boolean modifierQuestion(String libelle, String categorie,
            int difficulte, String reponseJuste,
            ArrayList<String> reponseFausses, String feedback) {
        
        if (getBanqueCategorie().getCategorieLibelleExact(categorie) == null) {
        	try {
				creerCategorie(categorie);
			} catch (InvalidNameException | HomonymeException e) {
				return false;
			}
        }
        // TODO ne pas modifier la question si un des arguments ne sont pas valides, 
        // parce que sinon on peut juste avoir un probleme de difficultée par exemple 
        // et ca va modifier quand meme le nom et le la catégorie
        try {
		     questionAModifier.setLibelle(libelle);
		     questionAModifier.setCategorie(getBanqueCategorie().getCategorieLibelleExact(categorie));
		     questionAModifier.setDifficulte(difficulte);
		     questionAModifier.setBonneReponse(reponseJuste);
		     questionAModifier.setMauvaiseReponse(reponseFausses);
		     questionAModifier.setFeedback(feedback);
		     return true;
	    } catch (InvalidNameException | InvalidFormatException | ReponseException e) {
			return false;
	    }
    }

    /**
     * Pour garder l'instance de la catégorie que l'on veux modifier, on garde
     * l'instance de la catégorie dans cet classe
     *
     * @param catgorieAModifier nouvelle valeur de catgorieAModifier
     */
    public void setCategorieAModifier(Categorie catgorieAModifier) {
        this.catgorieAModifier = catgorieAModifier;
    }

    /**
     * Change la difficulte actuelle pour une nouvelle difficulte rentrée en parametre
     * @param difficulte nouvelle difficulte (de 0 à 3 compris)
     * @throws DifficulteException 
     */
    public void setDifficultePartie(int difficulte) throws DifficulteException {
        partie.setDifficultePartie(difficulte);
    }

    public void setDisplayCategoriePane(boolean displayCategoriePane) {
		this.displayCategoriePane = displayCategoriePane;
	}

    public void setPagePrecedente(String nomPage){
    	this.pagePrecedente = nomPage;
    }

    public void setPartie(Partie partie) {
		this.partie = partie;
	}

    /**
     * Pour garder l'instance de la question que l'on veux modifier, on garde
     * l'instance de la question dans cet classe
     *
     * @param questionAModifier nouvelle valeur pour questionAModifier
     */
    public void setQuestionAModifier(Question questionAModifier) {
        this.questionAModifier = questionAModifier;
    }

	/**
     * Supprimer de la banque de categorie la categorie passé en paramètre. Si la
     * categorie a supprimer est la categorie "Général", la suppression est annulé
     *
     * @param categorieASupprimer
     * @return true si la suppression est un succès false sinon
     */
    public boolean supprimerCategorie(Categorie categorieASupprimer) {
        boolean estSupprimer = false;
        
        if (categorieASupprimer.equals(banqueCategorie.categorieGeneral)) {
            estSupprimer = false;
        
        } else if (banqueCategorie.getCategories().contains(categorieASupprimer)) {
        	/*
        	 *  On parcours la banque des questions et on ajoute toutes les questions
        	 *  qui ont la même catégorie que celle qu'on veut supprimer
        	 *  dans une ArrayList pour après pouvoir les supprimer
        	 *  de la banque de questions
        	 */
        	ArrayList<Question> questionsASupprimer = new ArrayList<>();

        	for (Question question : banqueQuestion.getQuestions()) { 
				if (question.getCategorie().equals(categorieASupprimer.toString())) {
					questionsASupprimer.add(question);
				}
			}
        	banqueQuestion.getQuestions().removeAll(questionsASupprimer);
            estSupprimer = banqueCategorie.getCategories().remove(categorieASupprimer);
        
        } else {
            estSupprimer = false;
        }
        
        return estSupprimer;
    }

	/**
     * Supprime de la banque de question la question passée en paramètre. Si la
     * suppression est un succès alors on revoie true false sinon
     *
     * @param questionASuprimer La question a supprimer
     * @return true si la suppression est un sucés false sinon
     */
    public boolean supprimerQuestion(Question questionASuprimer) {
        banqueQuestion.getQuestions().remove(questionASuprimer);
        return !banqueQuestion.getQuestions().contains(questionASuprimer);
    }
    
    public void serialize() throws IOException {
    	try {
            FileOutputStream fichierCategorie = new FileOutputStream(FICHIER_SERIALISATION_CATEGORIE);
            ObjectOutputStream outCategorie = new ObjectOutputStream(fichierCategorie);
            
            FileOutputStream fichierQuestion = new FileOutputStream(FICHIER_SERIALISATION_QUESTION);
            ObjectOutputStream outQuestion = new ObjectOutputStream(fichierQuestion);
             
            // Méthode pour serialiser la banque de categorie
            outCategorie.writeObject(this.getBanqueCategorie());
            
            // Méthode pour serialiser la banque de question
            outQuestion.writeObject(this.getBanqueQuestion());
             
            outCategorie.close();
            fichierCategorie.close();
            
            System.out.println("La banque de Categorie à bien été serialisée !");
            
            outQuestion.close();
            fichierQuestion.close();
             
            System.out.println("La banque de Question à bien été serialisée !"); 
            
    	} catch(IOException e) {
    		 e.printStackTrace();
    	}
    }
    
    /**
     * Méthode de de-serialisation des catégories qui lit le fichier "donneesCategorie"
     * et qui ajoute les catégories dans la banque de catégorie
     * @return une banque de catégorie avec les catégories du fichier si il est valide,
     * sinon une banque vide
     */
    public BanqueCategorie deSerialiserCategorie() {
		try {
			File fichierCategorie = new File(FICHIER_SERIALISATION_CATEGORIE);
			
			if (fichierCategorie.isFile() && fichierCategorie.canRead()) {
				FileInputStream inputFichierCategorie = new FileInputStream(fichierCategorie);
				
				ObjectInputStream inCategorie = new ObjectInputStream(inputFichierCategorie);	
				
				BanqueCategorie banqueDeserialiseeCategorie = (BanqueCategorie)inCategorie.readObject();
				inCategorie.close();
				inputFichierCategorie.close();
				
				System.out.println("La banque de Categorie à bien été dé-serialisée !");
				
				return banqueDeserialiseeCategorie;
			} else {
				AlertBox.showErrorBox("Le fichier de sauvegarde " 
						+ "des catégories à été corrompu ou supprimé, "
			            + "les catégories sont réinitialisées");
				return new BanqueCategorie();
			}	
			
		} catch (ClassNotFoundException | IOException e) {
			AlertBox.showErrorBox("Le fichier de sauvegarde " 
								+ "des catégories à été corrompu ou supprimé, "
					            + "les catégories sont réinitialisées");
			return new BanqueCategorie();
		}
    }
    
    /**
     * Méthode de de-serialisation des questions qui lit le fichier "donneesQuestions"
     * et qui ajoute les questions dans la banque de question
     * @return une banque de question avec les question du fichier si il est valide,
     * sinon une banque vide
     */
    public BanqueQuestion deSerialiserQuestion() {
    	try {
			File fichierQuestion = new File(FICHIER_SERIALISATION_QUESTION);
			
			if (fichierQuestion.isFile() && fichierQuestion.canRead()) {	
				FileInputStream inputFichierQuestion = new FileInputStream(FICHIER_SERIALISATION_QUESTION);
				ObjectInputStream inQuestion = new ObjectInputStream(inputFichierQuestion);
				
				// Méthode pour dé-serialiser la banque de question
				BanqueQuestion banqueDeserialiseeQuestion = (BanqueQuestion)inQuestion.readObject();
				
				inQuestion.close();
				inputFichierQuestion.close();
				
				System.out.println("La banque de Question à bien été dé-serialisée !");
				
				return banqueDeserialiseeQuestion;
			} else {
				AlertBox.showErrorBox("Le fichier de sauvegarde des " 
								    + "questions à été corrompu ou supprimé, "
			            		    + "les questions sont réinitialisées");
	   		    return new BanqueQuestion();
			}
			
    	} catch(ClassNotFoundException | IOException e) {
			AlertBox.showErrorBox("Le fichier de sauvegarde des " 
				    + "questions à été corrompu ou supprimé, "
        		    + "les questions sont réinitialisées");
   		    return new BanqueQuestion();
        }
    }
    
    /**
     * Ajouter la catégories et toutes ses questions 
     * à la liste des questions à envoyer
     * @param categorie le nom de la catégorie à envoyer
     * @return true si l'ajout est un succès, false sinon
     */
    public boolean ajouterALaSelectionDEnvoie(Categorie categorie) {
        
        categorieAEnvoyer.add(categorie);
        
        for (Question question : banqueQuestion.getQuestions()) {
            if (question.getCategorie().equalsIgnoreCase(categorie.getNom()) 
                && !questionAEnvoyer.contains(question)) {
                
                questionAEnvoyer.add(question);
            }
        }
        return true;
    }

    /**
     * Ajouter la question en parametre 
     * à la liste des question a envoyer
     * @param question le nom de la question à envoyer
     * @return true si l'ajout est un succès, false sinon
     */
    public boolean ajouterALaSelectionDEnvoie(Question question) {
        return questionAEnvoyer.add(question);
    }
    
    /**
     * Retire de la liste questionAEnvoyer,
     * les questions de la catégorie en parametre et 
     * la catégorie de la liste des categories à envoyer
     * @param categorie Le nom de la catégorie qui possede les questions 
     * qu'on veut supprimer de la liste des questions à envoyer 
     * et de la liste des catégories à envoyer
     * @return true si les questions ont été supprimées de la liste
     */
    public boolean supprimerALaSelectionDEnvoie(Categorie categorie) {
        ArrayList<Question> questionARetirer = new ArrayList<Question>();
        
        categorieAEnvoyer.remove(categorie);
        
        for (Question question : questionAEnvoyer) {
            if (question.getCategorie().equalsIgnoreCase(categorie.getNom())) {
                questionARetirer.add(question);
            }
        }
        
        return questionAEnvoyer.removeAll(questionARetirer);
    }
    
    /**
     * Retire de la liste questionAEnvoyer,
     * la question en parametre
     * @param question la question qu'on veut supprimer de la liste 
     * @return true si la question est supprimée de la liste
     */
    public boolean supprimerALaSelectionDEnvoie(Question question) {
        return questionAEnvoyer.remove(question);
    }
    
    /**
     * Sélectionne toute les questions de la banque de question pour pouvoir les envoyer
     */
    public void toutEnvoyer() {
        questionAEnvoyer = banqueQuestion.getQuestions();
    }

    /** 
     * Récupère la liste des questions a envoyer
     * @return questionAEnvoyer(ArrayList) la liste des questions à envoyer
     */
    public ArrayList<Question> getQuestionAEnvoyer() {
        return questionAEnvoyer;
    }

    /** 
     * Vérifie si la catégorie fait partie de la liste des categories à envoyer
     * @return true si la catégorie est dans la liste, false sinon
     */
    public boolean estAEnvoyer(Categorie categorie) {
        return categorieAEnvoyer.contains(categorie);
    }

    /** 
     * Vérifie si la question fait partie de la liste des questions à envoyer
     * @return true si la question est dans la liste, false sinon
     */
    public boolean estAEnvoyer(Question question) {
        return questionAEnvoyer.contains(question);
    }

    /** 
     * Ajoute une question à la banque de question
     * @param question la question à ajouter
     * @throws HomonymeException 
     * @throws InvalidNameException 
     * @throws CreerQuestionException 
     */
    public void ajouterQuestion(Question question) throws InvalidNameException, HomonymeException, CreerQuestionException {
        if (!categorieExiste(question.getCategorie())) {
            creerCategorie(question.getCategorie());
        }
        int indiceCategorie = getIndice(question.getCategorie());
        
        creerQuestion(question.getLibelle(), indiceCategorie, 
                      question.getDifficulte(),question.getReponseJuste(), 
                      question.getMauvaisesReponses(), question.getFeedback());
    }
    
    /**
     * @param categorie
     * @return true si la catégorie est la catégorie General false sinon
     */
    public boolean estGeneral(Categorie categorie) {
        return categorie.equals(banqueCategorie.categorieGeneral);
    }
    
    /**
     * @param nomCategorie
     * @return true si la catégorie est la catégorie General false sinon
     */
    public boolean estGeneral(String nomCategorie) {
        return nomCategorie.equals(banqueCategorie.categorieGeneral.getNom());
    }
    
    
    /**
     * test si la string passée en parametre est contenue dans l'alphabet defini
     * @param aTester
     * @return false si un caractere n'est pas dans l'alphabet true sinon
     */
    public static boolean alphabetOk(String aTester) {
    	for (int i = 0; i < aTester.length(); i++) {
			if (!Chiffrage.ALPAHABET_TO_INT.keySet().contains(aTester.charAt(i))) {
				return false;	
			}
		}
    	return true;
    }
}
