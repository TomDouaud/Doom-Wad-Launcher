package application.model;

import java.io.File;

import javafx.scene.image.Image;

public class WadItem {
	
	private String title;
    private String description;
    private Image image;
    
	public WadItem(File fichier) {
		this.title = fichier.getName();
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
    
	
	 /**
     * Override de la méthode toString
     * @return une chaine de caractère contenant les informations de la question
     */
    @Override
    public String toString() {
    	String aRetouner =  "Wad title : " + this.getTitle()
    		 + "\nWad description : " + this.getDescription();
    	
    	return aRetouner; 	
    }
    
}
