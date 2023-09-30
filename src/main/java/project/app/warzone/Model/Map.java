package project.app.warzone.Model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import project.app.warzone.Utilities.MyFilenameFilter;

@Component
public class Map {

    private List<Node> nodes;
    private List<Continent> listOfContinents;

    public String USER_SELECTED_FILE="europe";


    public Map() {
        nodes = new ArrayList<>();
        listOfContinents = new ArrayList<>();
    }

    public List<Node> getNodes() {
        return nodes; // returns all territories in the map
    }

    public void createContinent(String continentName, Integer continentBonus){
        Continent continent = new Continent(continentName,  continentBonus);
        listOfContinents.add(continent);
 
    }

    public void createAndInsertTerritory(String territoryName, Continent continent){
        Territory territory = new Territory(territoryName,continent);
        nodes.add(new Node(territory));
    }
    public List<Continent> getListOfContinents() {
        return listOfContinents;
    }

    // public void insertATerritory(Territory data) {
    //     nodes.add(new Node(data));
    // }

    // public void insertAndConnectTwoTerritories(Territory dataA, Territory dataB) {
    //     insertATerritory(dataA);
    //     insertATerritory(dataB);
    //     connectTwoNodes(nodes.get(nodes.size() - 1), nodes.get(nodes.size() - 2));
    // }

    // public void connectTwoNodes(Node A, Node B) {
    //     String edgeName = A.getData().getTerritoryName() + B.getData().getTerritoryName();
    //     A.addEdge(edgeName);
    //     B.addEdge(edgeName);
    // }

    public void addEdgesOfTerritory(Node mainTerritory , List<Node> addBorders)
    {
        mainTerritory.addBorderTerritories(addBorders);
    }

    // public boolean areConnected(Node A, Node B) {
    //     String possibleEdge1 = A.getData().getTerritoryName() + B.getData().getTerritoryName();
    //     String possibleEdge2 = B.getData().getTerritoryName() + A.getData().getTerritoryName();

    //     for (String edge : A.getE()) {
    //         if (edge.equals(possibleEdge1) || edge.equals(possibleEdge2)) {
    //             return true;
    //         }
    //     }

    //     return false;
    // }

    public String getMapDirectory(){
        return System.getProperty("user.dir")+"/src/main/java/project/app/warzone/Utilities/Maps";
        //return "/warzone/src/main/java/project/app/warzone/Utilities/Maps";
       // /Users/aishwaryashinde/Desktop/warzone-project/warzone/src/main/java/project/app/warzone/Utilities/Maps
    }

    public String get_USER_SELECTED_FILE() {
        return USER_SELECTED_FILE;
      }
    
      // Setter
      public void set_USER_SELECTED_FILE(String newFile) {
        this.USER_SELECTED_FILE = newFile;
      }

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

    public void createNewMapFile(String p_mapFile)
    {
        //String test=map.getMapDirectory()+"/"+mapFile+".map";
        File newFile = new File(getMapDirectory()+"/"+p_mapFile+".map");
         try {
            // Create the new file
            boolean fileCreated = newFile.createNewFile();
            set_USER_SELECTED_FILE(p_mapFile);
            if (fileCreated) {
                
                System.out.println("New file created at: ");
            } else {
                System.err.println("File already exists at: Please try new name");
            }
        } catch (IOException e) {
            System.err.println("An error occurred while creating the file: " + e.getMessage());
        }
    }
    
}
