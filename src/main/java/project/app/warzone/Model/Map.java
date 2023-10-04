package project.app.warzone.Model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import project.app.warzone.Utilities.MyFilenameFilter;

/**
 * This class contains all the continents and countries needed for 
 */
@Component
public class Map {

    private List<Node> nodes;
    private List<Continent> listOfContinents;

    public String USER_SELECTED_FILE="europe";      //used for storing userselected file


    /**
     * Constructor for Map
     */
    public Map() {
        nodes = new ArrayList<>();
        listOfContinents = new ArrayList<>();
    }


    
    /**
     * method used for returning all territories 
     * 
     * @return    returns all territories in the map
     */
    public List<Node> getNodes() {
        return nodes;
    }


    
    /**
     * method used for creating continent
     * 
     * @param continentName         continent name to create
     * @param continentBonus        storing bonus value
     */
    public void createContinent(String continentName, Integer continentBonus){
        Continent continent = new Continent(continentName,  continentBonus);
        listOfContinents.add(continent);
 
    }


    
    /** 
     * method used for creating and inserting country
     * 
     * @param countryName           country name to create
     * @param continent             storing continent name
     */
    public void createAndInsertCountry(String countryName, Continent continent){
        Country country = new Country(countryName,continent);
        nodes.add(new Node(country));
    }

    
    /**
     *  method used for returning list of continents
     * 
     * @return               returns list of continents
     */
    public List<Continent> getListOfContinents() {
        return listOfContinents;
    }


    
    /**
     *  method used for adding borders
     * 
     * @param mainCountry       storing main country
     * @param addBorders        storing borders to be added
     */
    public void addEdgesOfCountry(Node mainCountry , List<Node> addBorders)
    {
        mainCountry.addBorderTerritories(addBorders);
    }

    
    
    /** 
     *  method used for returning mapFile location
     * 
     * @return String       returns mapFile location
     */
    public String getMapDirectory(){
        return System.getProperty("user.dir")+"/src/main/java/project/app/warzone/Utilities/Maps";
    }


    
    /** 
     * returns user selected file
     * 
     * @return String          returns user selected file
     */
    public String get_USER_SELECTED_FILE() {
        return USER_SELECTED_FILE;
      }
    
    
    
    /**
     * method for setting user selected file 
     * 
     * @param newFile       used for setting user selected file
     */
    public void set_USER_SELECTED_FILE(String newFile) {
    this.USER_SELECTED_FILE = newFile;
    }


    
    /** 
     * method used to check if file exists
     * 
     * @param p_filename        storing filename to check 
     * @return boolean          returns result
     */
    public boolean fileExists(String p_filename){

        System.out.println(getMapDirectory());
        File l_mapDirectory = new File(getMapDirectory());
        System.out.println("getmapdirect"+l_mapDirectory);
        
        MyFilenameFilter filter = new MyFilenameFilter(p_filename);
        File[] l_matchingFiles = l_mapDirectory.listFiles(filter);

        if(l_matchingFiles != null && l_matchingFiles.length == 1 ){
            return true;
        }
        else{
            return false;
        }
        
    }


    
    /** 
     * @param p_mapFile     storing mapFile name
     */
    public void createNewMapFile(String p_mapFile)
    {
        File newFile = new File(getMapDirectory()+"/"+p_mapFile+".map");
         try {
            // Create the new file
            boolean fileCreated = newFile.createNewFile();
            set_USER_SELECTED_FILE(p_mapFile);
            if (fileCreated) {
                
                System.out.println("New file created successfully: ");
            } else {
                System.err.println("File already exists at: Please try new name");
            }
        } catch (IOException e) {
            System.err.println("An error occurred while creating the file: " + e.getMessage());
        }
    }
    
}
