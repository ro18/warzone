package project.app.warzone.Model;

import java.io.File;
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
        return nodes;
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
        return "src\\main\\java\\project\\app\\warzone\\Utilities\\Maps";

    }

    public String get_USER_SELECTED_FILE() {
        return USER_SELECTED_FILE;
      }
    
      // Setter
      public void set_USER_SELECTED_FILE(String newFile) {
        this.USER_SELECTED_FILE = newFile;
      }

    public boolean fileExists(String p_filename){

        File l_mapDirectory = new File(getMapDirectory());
        MyFilenameFilter filter = new MyFilenameFilter(p_filename);
        File[] l_matchingFiles = l_mapDirectory.listFiles(filter);

        if(l_matchingFiles != null && l_matchingFiles.length == 1 ){
            return true;
        }
        else{
            return false;
        }
        
    }
    
}
