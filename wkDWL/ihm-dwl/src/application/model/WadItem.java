/*
 * WadItem.java                                    
 * made the 21 jan. 2024
 */

package application.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;
import net.mtrop.doom.WadEntry;
import net.mtrop.doom.WadFile;
import net.mtrop.doom.graphics.Palette;
import net.mtrop.doom.graphics.Picture;
import net.mtrop.doom.util.GraphicUtils;

/**
 * The object who represent a wad or a pk3, with all thier needed informations
 * @author Tom Douaud
 */
public class WadItem {
	
	// The array of all the IWAD
	private final static String[] IWAD = {"doom", "doom1", "doom2", "doom2f", "doom64", "tnt", "plutonia", "heretic1", "heretic", "hexen", "hexdd", "strife0", "strife1", "voices", "chex"};
	
	// The default color palette of Doom (for the custom titlepic of the pwad)
	private final int[][] DEFAULT_PALETTE_VALUES =  {{0, 0, 0}, {31, 23, 11}, {23, 15, 7}, {75, 75, 75}, {-1, -1, -1}, {27, 27, 27},  {19, 19, 19}, {11, 11, 11}, {7, 7, 7}, {47, 55, 31}, {35, 43, 15}, {23, 31, 7},  {15, 23, 0}, {79, 59, 43}, {71, 51, 35}, {63, 43, 27}, {-1, -73, -73}, {-9, -85, -85},  {-13, -93, -93},  {-21, -105, -105},  {-25, -113, -113},  {-33, -121, -121},  {-37, 123, 123},  {-45, 115, 115},  {-53, 107, 107},  {-57, 99, 99},  {-65, 91, 91},  {-69, 87, 87},  {-77, 79, 79},  {-81, 71, 71},  {-89, 63, 63},  {-93, 59, 59},  {-101, 51, 51},  {-105, 47, 47},  {-113, 43, 43},  {-117, 35, 35},  {-125, 31, 31},  {127, 27, 27},  {119, 23, 23},  {115, 19, 19},  {107, 15, 15},  {103, 11, 11},  {95, 7, 7},  {91, 7, 7},  {83, 7, 7},  {79, 0, 0},  {71, 0, 0},  {67, 0, 0},  {-1, -21, -33},  {-1, -29, -45},  {-1, -37, -57},  {-1, -45, -69},  {-1, -49, -77},  {-1, -57, -89},  {-1, -65, -101},  {-1, -69, -109},  {-1, -77, -125},  {-9, -85, 123},  {-17, -93, 115},  {-25, -101, 107},  {-33, -109, 99},  {-41, -117, 91},  {-49, -125, 83},  {-53, 127, 79},  {-65, 123, 75},  {-77, 115, 71},  {-85, 111, 67},  {-93, 107, 63},  {-101, 99, 59},  {-113, 95, 55},  {-121, 87, 51},  {127, 83, 47},  {119, 79, 43},  {107, 71, 39},  {95, 67, 35},  {83, 63, 31},  {75, 55, 27},  {63, 47, 23},  {51, 43, 19},  {43, 35, 15},  {-17, -17, -17},  {-25, -25, -25},  {-33, -33, -33},  {-37, -37, -37},  {-45, -45, -45},  {-53, -53, -53},  {-57, -57, -57},  {-65, -65, -65},  {-73, -73, -73},  {-77, -77, -77},  {-85, -85, -85},  {-89, -89, -89},  {-97, -97, -97},  {-105, -105, -105},  {-109, -109, -109},  {-117, -117, -117},  {-125, -125, -125},  {127, 127, 127},  {119, 119, 119},  {111, 111, 111},  {107, 107, 107},  {99, 99, 99},  {91, 91, 91},  {87, 87, 87},  {79, 79, 79},  {71, 71, 71},  {67, 67, 67},  {59, 59, 59},  {55, 55, 55},  {47, 47, 47},  {39, 39, 39},  {35, 35, 35},  {119, -1, 111},  {111, -17, 103},  {103, -33, 95},  {95, -49, 87},  {91, -65, 79},  {83, -81, 71},  {75, -97, 63},  {67, -109, 55},  {63, -125, 47},  {55, 115, 43},  {47, 99, 35},  {39, 83, 27},  {31, 67, 23},  {23, 51, 15},  {19, 35, 11},  {11, 23, 7},  {-65, -89, -113},  {-73, -97, -121},  {-81, -105, 127},  {-89, -113, 119},  {-97, -121, 111},  {-101, 127, 107},  {-109, 123, 99},  {-117, 115, 91},  {-125, 107, 87},  {123, 99, 79},  {119, 95, 75},  {111, 87, 67},  {103, 83, 63},  {95, 75, 55},  {87, 67, 51},  {83, 63, 47},  {-97, -125, 99},  {-113, 119, 83},  {-125, 107, 75},  {119, 95, 63},  {103, 83, 51},  {91, 71, 43},  {79, 59, 35},  {67, 51, 27},  {123, 127, 99},  {111, 115, 87},  {103, 107, 79},  {91, 99, 71},  {83, 87, 59},  {71, 79, 51},  {63, 71, 43},  {55, 63, 39},  {-1, -1, 115},  {-21, -37, 87},  {-41, -69, 67},  {-61, -101, 47},  {-81, 123, 31},  {-101, 91, 19},  {-121, 67, 7},  {115, 43, 0},  {-1, -1, -1},  {-1, -37, -37},  {-1, -69, -69},  {-1, -101, -101},  {-1, 123, 123},  {-1, 95, 95},  {-1, 63, 63},  {-1, 31, 31},  {-1, 0, 0},  {-17, 0, 0},  {-29, 0, 0},  {-41, 0, 0},  {-53, 0, 0},  {-65, 0, 0},  {-77, 0, 0},  {-89, 0, 0},  {-101, 0, 0},  {-117, 0, 0},  {127, 0, 0},  {115, 0, 0},  {103, 0, 0},  {91, 0, 0},  {79, 0, 0},  {67, 0, 0},  {-25, -25, -1},  {-57, -57, -1},  {-85, -85, -1},  {-113, -113, -1},  {115, 115, -1},  {83, 83, -1},  {55, 55, -1},  {27, 27, -1},  {0, 0, -1},  {0, 0, -29},  {0, 0, -53},  {0, 0, -77},  {0, 0, -101},  {0, 0, -125},  {0, 0, 107},  {0, 0, 83},  {-1, -1, -1},  {-1, -21, -37},  {-1, -41, -69},  {-1, -57, -101},  {-1, -77, 123},  {-1, -93, 91},  {-1, -113, 59},  {-1, 127, 27},  {-13, 115, 23},  {-21, 111, 15},  {-33, 103, 15},  {-41, 95, 11},  {-53, 87, 7},  {-61, 79, 0},  {-73, 71, 0},  {-81, 67, 0},  {-1, -1, -1},  {-1, -1, -41},  {-1, -1, -77},  {-1, -1, -113},  {-1, -1, 107},  {-1, -1, 71},  {-1, -1, 35},  {-1, -1, 0},  {-89, 63, 0},  {-97, 55, 0},  {-109, 47, 0},  {-121, 35, 0},  {79, 59, 39},  {67, 47, 27},  {55, 35, 19},  {47, 27, 11},  {0, 0, 83},  {0, 0, 71},  {0, 0, 59},  {0, 0, 47},  {0, 0, 35},  {0, 0, 23},  {0, 0, 11},  {0, 0, 0},  {-1, -97, 67},  {-1, -25, 75},  {-1, 123, -1},  {-1, 0, -1},  {-49, 0, -49},  {-97, 0, -101},  {111, 0, 107},  {-89, 107, 107}};
	private final Palette DEFAULT_PALETTE = getDefaultPalette();
	
	// The default image that will be shown if no custom image found
	private final String DEFAULT_IMAGE = "./cache/DOOM.png";
	
	/*
	 * The title, the rawtitle (for example 666epis.wad is raw title for 666 mini-episode),
	 * the description, author, realease date, rating and image (titlepic) of the wad
	 */
	private String title;
	private String rawTitle;
	private String description;
    private String author;
   	private String realaseDate;
    private String rating;
    private String image = DEFAULT_IMAGE;

    // The boolean who switch to true if the wad is not found on https://www.doomworld.com/idgames/
	private boolean notFoundOnID = false;
    
	/**
	 * The constructor of the WadItem, who tries to grab the informations of the wad
	 * @param file (File) the wad file for the informations
	 */
	public WadItem(File file) {
		this.rawTitle = file.getName();
		apiCaller();
		
		// if this is a .pk3, don't try to scrap the titlepic and get the default image
		if (!rawTitle.substring(rawTitle.length() - 4, rawTitle.length()).equals(".pk3")) {
			getWadTitlePic(file);
		} else {
			setImage(DEFAULT_IMAGE); // TODO change the picture to an pk3 picture
		}
		
		System.out.println(getImage());
	}
	
	/**
	 * Tries to get the titlepic of the wad, if none found, 
	 * set the wad image to the default doom titlepic
	 * @param file the wad file to open
	 */
	private void getWadTitlePic(File file) {
		WadFile wad;
		try {
			wad = new WadFile(file.getAbsolutePath());
			Palette pal = getPalette(wad);
			for (WadEntry entry : wad) {
				
				// If a custom titlepic is found, stores it in the cache folder
				if (entry.getName().startsWith("TITLEPIC")) {
					Picture p = wad.getDataAs(entry, Picture.class);
					ImageIO.write(GraphicUtils.createImage(p, pal), "PNG", new File("./cache/", getRawTitle().substring(0, rawTitle.length() - 4) +".png"));
					setImage("./cache/" + getRawTitle() +".png");
				} 
			}
			wad.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Get the color palette of the wad if it has a custom one (Example : Eviternity)
	 * If no palette found, use the default doom one
	 * @param wad the wad to search the palette
	 * @return the color palette if found, otherwise the default one
	 * @throws IOException exception catched by getWadTitlePic
	 */
	private Palette getPalette(WadFile wad) throws IOException {
		Palette pal = null;
		try {
			pal = wad.getDataAs("playpal", Palette.class);
		} catch (Exception e) {
			pal = DEFAULT_PALETTE;
		}
		return pal == null ? DEFAULT_PALETTE : pal;
	}

	/**
	 * Generates the default doom color palette
	 * @return the default doom palette
	 */
	private Palette getDefaultPalette() {
		Palette DEFAULT_PALETTE = new Palette();
		for (int i = 0 ; i < 256; i++) {
			DEFAULT_PALETTE.setColor(i, DEFAULT_PALETTE_VALUES[i][0], DEFAULT_PALETTE_VALUES[i][1], DEFAULT_PALETTE_VALUES[i][2]);
		}
		return DEFAULT_PALETTE;
	}

	/**
	 * Getter of the wad title
	 * @return the wad title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Setter of the wad title
	 * @param title the new wad title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Getter of the wad description
	 * @return the wad description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Setter of the wad description
	 * @param description the new wad description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Getter of the wad image filepath
	 * @return the image filepath
	 */
	public String getImage() {
		return image;
	}
	
	/**
	 * Setter of the wad image filepath
	 * @param imagePath the filepath of the image
	 */
	public void setImage(String imagePath) {
		this.image = imagePath;
	}
	
	/**
	 * Getter of the author of the wad
	 * @return the author of the wad
	 */
	 public String getAuthor() {
			return author;
	}
	 
	/**
	 * Setter of the wad author 
	 * @param author the new author of the wad
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * Getter of the realase date of the wad
	 * @return the wad realase date (YYYY-MM-DD) or "Date unknown"
	 */
	public String getRealaseDate() {
		return realaseDate;
	}
	
	/**
	 * Setter of the realase date of the wad
	 * @param realaseDate the new realase date of the wad
	 */
	public void setRealaseDate(String realaseDate) {
		this.realaseDate = realaseDate;
	}
	
	/**
	 * Getter of the rating of the wad
	 * @return the rating of the wad (0.0 to 5.0) or "Unknown rating"
	 */
	public String getRating() {
		return rating;
	}
	
	/**
	 * Setter of the rating of the wad
	 * @param rating the new rating of the wad
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}
	
	/**
	 * Getter of the raw title of the wad
	 * @return the raw title of the wad
	 */
    public String getRawTitle() {
		return rawTitle;
	}

    /**
     * Setter of the raw title of the wad
     * @param rawTitle the new raw title of the wad
     */
	public void setRawTitle(String rawTitle) {
		this.rawTitle = rawTitle;
	}
	
	/**
	 * Calls the api on various websites to scrape the wad data
	 */
    public void apiCaller() {
        try {
        	// If this is a PWAD, search on multiples databases
        	if (!Arrays.asList(IWAD).contains(this.rawTitle.toLowerCase().substring(0, rawTitle.length() - 4))) {
        		// Try at id games archives
        		callIdgamesArchive();
        		// TODO add more api calls
        		
        		if (notFoundOnID) {
        			setTitle(rawTitle.substring(0, rawTitle.length() - 4));
                	setDescription("No Description Found :-(");
                	setAuthor("Unknown Author");
                	setRating("Not rated");
                	setRealaseDate("Realeased date not found");
        		}
        		
        	} else {
        		System.out.println("c'est une IWAD");
        		// TODO manage the meta data for the differents IWAD
        	}
        	
        } catch (Exception e) {
        	setTitle(rawTitle.substring(0, rawTitle.length() - 4));
        	setDescription("No Description Found :-(");
        	setAuthor("Unknown Author");
        	setRating("Not rated");
        	setRealaseDate("Realeased date not found");
            System.out.println("Pas connecté ! ou erreur : " + e);
        }
    }
	
	/**
	 * Calls the IdgamesArchive api to scrap the data of the wad
	 * @throws Exception
	 */
	private void callIdgamesArchive() throws Exception {
		String curatedTitle =  this.rawTitle.replaceAll("[^a-zA-Z0-9]", " ");
		curatedTitle = curatedTitle.trim();
		curatedTitle = curatedTitle.replaceAll(" ", "_");
		URL url = new URL("https://www.doomworld.com/idgames/api/api.php?action=search&query=" + curatedTitle + "&type=filename&sort=filename&out=json");
 		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
 		conn.setRequestMethod("GET");
 		conn.setRequestProperty("Accept", "application/json");
 		
 		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
 		
 	    StringBuilder reponse = new StringBuilder();
 		String output;
 		while ((output = br.readLine()) != null) {
 			reponse.append(output);
 		}
 		 		
 		conn.disconnect();
 		
 		// The api respond with warning if the wad is not found
 		if (reponse.toString().contains("warning")) {
 			notFoundOnID  = true;
 		} else {
 			
 			// Get the data from the JSON response
 			String scrapTitle = "";
 			try {
 				scrapTitle = reponse.substring(reponse.indexOf("\"title\":\"") + 9, reponse.indexOf("\",\"dir\""));
 		    } catch(IndexOutOfBoundsException e) {
 		    	scrapTitle = getRawTitle();
 		    }
 			setTitle(scrapTitle);
 			
 			String scrapDescription = "";
 			try {
 				scrapDescription = reponse.substring(reponse.indexOf("\"description\":\"") + 15, reponse.indexOf("\",\"rating\""));
 		    } catch(IndexOutOfBoundsException e) {
 		    	scrapDescription = getRawTitle();
 		    }
 			setDescription(scrapDescription);
 			
 			String scrapRealaseDate = "";
 			try {
 				scrapRealaseDate = reponse.substring(reponse.indexOf("\"date\":\"") + 8, reponse.indexOf("\",\"author\""));
 		    } catch(IndexOutOfBoundsException e) {
 		    	scrapRealaseDate = getRawTitle();
 		    }
 			setRealaseDate(scrapRealaseDate);
 			
 			String scrapAuthor = "";
 			try {
 				scrapAuthor = reponse.substring(reponse.indexOf("\"author\":\"") + 10, reponse.indexOf("\",\"email\""));
 		    } catch(IndexOutOfBoundsException e) {
 		    	scrapAuthor = getRawTitle();
 		    }
 			setAuthor(scrapAuthor);
 			
 			String scrapRating = "";
 			try {
 				scrapRating = reponse.substring(reponse.indexOf("\"rating\":") + 9, reponse.indexOf(",\"votes\""));
 		    } catch(IndexOutOfBoundsException e) {
 		    	scrapRating = getRawTitle();
 		    }
 			setRating(scrapRating);
 		}
 	}
	 
	 
	/**
     * Override of the method toString
     * @return a string of the wad information
     */
    @Override
    public String toString() {
    	String toReturn =  
    		   "Wad title : " + this.getTitle()
    		 + "\nWad description : " + this.getDescription()
    		 + "\nWad author : " + this.getAuthor()
    		 + "\nWad realase date : " + this.getRealaseDate()
    		 + "\nWad rating : " + this.getRating();
    	
    	return toReturn; 	
    }
    
}
