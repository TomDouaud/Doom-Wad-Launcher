package application.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// TODO where to put exception imports


public class WadBank {
	
	
    
    public static List<File> listerFichiersWadPk3(String cheminDossier) {
    	
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
        		System.out.println(fichier.getAbsolutePath());
        	}     	
        } else {
        	System.out.println("Aucune wad ou pk3 trouvée !");
        }
        
        return fichiersWadPk3;
    }
}
