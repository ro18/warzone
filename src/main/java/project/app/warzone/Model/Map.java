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
     * @return List<Node>
     */
    public List<Node> getNodes() {
        return nodes; // returns all territories in the map
    }


    
    /**
     * method used for creating continent
     * 
     * @param continentName
     * @param continentBonus
     */
    public void createContinent(String continentName, Integer continentBonus){
        Continent continent = new Continent(continentName,  continentBonus);
        listOfContinents.add(continent);
 
    }


    
    /** 
     * method used for creating and inserting country
     * 
     * @param countryName
     * @param continent
     */
    public void createAndInsertCountry( int countryId,String countryName,Continent continent){
        Country country = new Country(countryId,countryName,continent);
        nodes.add(new Node(country));
    }

    
    /**
     *  method used for returning list of continents
     * 
     * @return List<Continent>
     */
    public List<Continent> getListOfContinents() {
        return listOfContinents;
    }


    
    /**
     *  method used for adding borders
     * 
     * @param mainCountry
     * @param addBorders
     */
    public void addEdgesOfCountry(Node mainCountry , List<Node> addBorders)
    {
        mainCountry.addBorderTerritories(addBorders);
    }

    
    
    /** 
     *  method used for returning mapFile location
     * 
     * @return String
     */
    public String getMapDirectory(){
        return System.getProperty("user.dir")+"/src/main/java/project/app/warzone/Utilities/Maps";
    }


    
    /** 
     * returns user selected file
     * 
     * @return String
     */
    public String get_USER_SELECTED_FILE() {
        return USER_SELECTED_FILE;
      }
    
    
    
    /**
     * used for setting user selected file 
     * 
     * @param newFile
     */
    public void set_USER_SELECTED_FILE(String newFile) {
    this.USER_SELECTED_FILE = newFile;
    }


    
    /** 
     * method used to check if file exists
     * 
     * @param p_filename
     * @return boolean
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
     * @param p_mapFile
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
