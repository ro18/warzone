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

    public String USER_SELECTED_FILE="europe";


    public Map() {
        nodes = new ArrayList<>();
        listOfContinents = new ArrayList<>();
    }


    
    /** 
     * @return List<Node>
     */
    public List<Node> getNodes() {
        return nodes; // returns all territories in the map
    }


    
    /** 
     * @param continentName
     * @param continentBonus
     */
    public void createContinent(String continentName, Integer continentBonus){
        Continent continent = new Continent(continentName,  continentBonus);
        listOfContinents.add(continent);
 
    }


    
    /** 
     * @param countryName
     * @param continent
     */
    public void createAndInsertCountry(String countryName, Continent continent){
        Country country = new Country(countryName,continent);
        nodes.add(new Node(country));
    }

    
    /** 
     * @return List<Continent>
     */
    public List<Continent> getListOfContinents() {
        return listOfContinents;
    }


    
    /** 
     * @param mainCountry
     * @param addBorders
     */
    public void addEdgesOfCountry(Node mainCountry , List<Node> addBorders)
    {
        mainCountry.addBorderTerritories(addBorders);
    }

    
    
    /** 
     * @return String
     */
    public String getMapDirectory(){
        return System.getProperty("user.dir")+"/src/main/java/project/app/warzone/Utilities/Maps";
    }


    
    /** 
     * @return String
     */
    public String get_USER_SELECTED_FILE() {
        return USER_SELECTED_FILE;
      }
    
    
    
    /** 
     * @param newFile
     */
    public void set_USER_SELECTED_FILE(String newFile) {
    this.USER_SELECTED_FILE = newFile;
    }


    
    /** 
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
