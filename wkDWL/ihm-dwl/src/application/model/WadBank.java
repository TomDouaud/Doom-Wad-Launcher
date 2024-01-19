package application.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// TODO where to put exception imports


public class WadBank {
	
	private ArrayList<WadItem> wads;
    

	public WadBank() {
		wads = new ArrayList<WadItem>();
	}
	
	/**
     * Add a wad to the wadBank
     * @param WadItem the wad to add 
     * @throws HomonymeException if the wad aleready is in the bank
     */
    public void ajouter(File fichier) { //throws HomonymeException {
    	WadItem wad = new WadItem(fichier);
    	wads.add(wad);
    }
	
    public void listerFichiersWadPk3(String cheminDossier) {
    	this.wads = new ArrayList<WadItem>();
        List<File> fichiersWadPk3 = new ArrayList<>();

        // Créez un objet File pour représenter le dossier
        File dossier = new File(cheminDossier);

        // Obtenez la liste des fichiers dans le dossier
        File[] fichiers = dossier.listFiles();

        // Vérifiez chaque fichier pour son extension
        if (fichiers != null) {
            for (File fichier : fichiers) {
                // Vérifiez si le fichier a l'extension .wad ou .pk3
                if (fichier.isFile() && (fichier.getName().toLowerCase().endsWith(".wad") || fichier.getName().toLowerCase().endsWith(".pk3"))) {
                    fichiersWadPk3.add(fichier);
                }
            }
        }
        
        if (!fichiersWadPk3.isEmpty()) {
        	// Affichez la liste des fichiers
        	for (File fichier : fichiersWadPk3) {
        		ajouter(fichier);
        		System.out.println(fichier.getAbsolutePath());
        	}     	
        } else {
        	System.out.println("Aucune wad ou pk3 trouvée !");
        }
    }
    
    /**
     * Permet de récupérer toutes les questions
     * @return Toutes les questions (ArrayList)
     */
    public ArrayList<WadItem> getWads() {
        return wads;
    }
    
    
    /* non javadoc - @see java.lang.Object#toString() */
    @Override
    public String toString() {
        StringBuilder resultat = new StringBuilder();
        resultat.append("Nombre de wad : " + wads.size() + "\n\n");
        for (WadItem wad : wads) {
            resultat.append(wad.toString());
            resultat.append("\n\n"
                            + "--------------------------------------------"
                            + "\n\n");
        }
        return resultat.toString();
    }
}
