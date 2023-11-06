package project.app.warzone.Features;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.app.warzone.Model.Continent;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Map;
import project.app.warzone.Model.Node;
import project.app.warzone.Utilities.MapResources;


public class MapFeaturesTest {

    public MapFeatures mapFeatures;
    public MapResources mapResources;

    @BeforeEach
    public void setUp(){
        this.mapResources = new MapResources();
        this.mapFeatures = new MapFeatures(mapResources);
    }
    
    @Test
    public void testValidateEntireGraphWithValidMap() {
        String l_mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "Castle.map";
        Map l_map = new Map();
        GameEngine l_gameEngine = new GameEngine(l_map);
        l_map=mapFeatures.readMap(l_mapFileName);
        boolean l_isMapValid = mapFeatures.validateEntireGraph(l_gameEngine);
        assertTrue(l_isMapValid);
    }

    @Test
    public void testValidateEntireGraphWithInvalidMap() {
        String l_mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "Jtest2.map";
        Map l_map = new Map();
        l_map=mapFeatures.readMap(l_mapFileName);
        
        GameEngine l_gameEngine = new GameEngine(l_map);
        boolean l_isMapValid = this.mapFeatures.validateEntireGraph(l_gameEngine);
        assertFalse(l_isMapValid);   
    }

    @Test
    public void testContinentValidity() {
        String l_mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "Jtest2.map";

        Map l_map = mapFeatures.readMap(l_mapFileName);
        GameEngine l_gameEngine = new GameEngine(l_map);

        Continent l_continent_pink = l_gameEngine.gameMap.getListOfContinents().get(0);
        List<Node> l_listOfNodesPink = l_gameEngine.gameMap.getNodes();

        Continent l_continent_blue = l_gameEngine.gameMap.getListOfContinents().get(1);
        List<Node> l_listOfNodesBlue = l_gameEngine.gameMap.getNodes();

        java.util.Map<Node,Boolean> l_visitedList =  new LinkedHashMap<Node,Boolean>();

        boolean l_isContinentValid_pink = mapFeatures.validateSubGraph(l_continent_pink,l_listOfNodesPink,l_visitedList);


        boolean l_isContinentValid_blue = mapFeatures.validateSubGraph(l_continent_blue, l_listOfNodesBlue,l_visitedList);

        assertTrue(l_isContinentValid_pink);
        assertFalse(l_isContinentValid_blue);
    }

    // @Test
    // public void testValidateInvalidContinent() {
    //     String l_mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "Jtest2.map";
    //     Map l_map = this.mapFeatures.readMap(l_mapFileName);
    //     GameEngine l_gameEngine = new GameEngine(l_map);
    //     //boolean l_isContinentValid = this.mapFeatures.validateContinent(l_gameEngine, l_map.getListOfContinents().get(2));
    //     //assertFalse(l_isContinentValid);
    // }
}
