package project.app.warzone.Features;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    MapFeatures mapFeatures = MapFeatures.getInstance();
    public MapResources mapResources;
    private Map map;

    @BeforeEach
    public void setUp(){
        this.mapResources = new MapResources();
    }

    //File validity check
    @Test
    public void testReadInvalidMapFile() {
        String l_mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "wrong.map";
        Map l_map = new Map();
        l_map = mapFeatures.readMap(l_mapFileName);
        boolean l_fileExists = l_map.fileExists(l_mapFileName);

        assertFalse(l_fileExists); 
    }

    @Test
    public void testReadValidMapFile() {
        String l_mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "Castle.map";
        Map gameMap = mapFeatures.readMap(l_mapFileName);
        assertNotNull(gameMap);
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
    public void testContinentValid() {
        String l_mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "Jtest2.map";

        Map l_map = mapFeatures.readMap(l_mapFileName);
        GameEngine l_gameEngine = new GameEngine(l_map);

        Continent l_continent_pink = l_gameEngine.gameMap.getListOfContinents().get(0);
        List<Node> l_listOfNodesPink = l_gameEngine.gameMap.getNodes();

        java.util.Map<Node, Boolean> l_visitedList = new LinkedHashMap<Node, Boolean>();

        boolean l_isContinentValid_pink = mapFeatures.validateSubGraph(l_continent_pink, l_listOfNodesPink, l_visitedList);

        assertTrue(l_isContinentValid_pink);
    }

    @Test
    public void testContinentInvalid() {
        String l_mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "Jtest2.map";

        Map l_map = mapFeatures.readMap(l_mapFileName);
        GameEngine l_gameEngine = new GameEngine(l_map);

        Continent l_continent_blue = l_gameEngine.gameMap.getListOfContinents().get(1);
        List<Node> l_listOfNodesBlue = l_gameEngine.gameMap.getNodes();

        java.util.Map<Node, Boolean> l_visitedList = new LinkedHashMap<Node, Boolean>();

        boolean l_isContinentValid_blue = mapFeatures.validateSubGraph(l_continent_blue, l_listOfNodesBlue, l_visitedList);

        assertFalse(l_isContinentValid_blue);
    }

    /* 

    @Test
    public void testAttackWithoutBridge() {
        String l_mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "Jtest2.map";
        Map l_map = mapFeatures.readMap(l_mapFileName);
        GameEngine l_gameEngine = new GameEngine(l_map);
        Node countryA = map.getNodes().get(0); // Replace with the specific country node you want to test
        Node countryB = map.getNodes().get(1); // Replace with another country node

        // Ensure that countryA and countryB do not have a direct border connection
        assertFalse(countryA.getBorders().contains(countryB));

        // Attempt an attack, which should fail without a bridge
        boolean attackResult = l_gameEngine.getGamePhase().attack(countryA, countryB, 3, 2);
        assertFalse(attackResult);
    }

    @Test
    public void testAttackWithBridge() {
        String l_mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "Jtest2.map";

        Map l_map = mapFeatures.readMap(l_mapFileName);
        GameEngine l_gameEngine = new GameEngine(l_map);

        Node countryA = map.getNodes().get(0); // Replace with the specific country node you want to test
        Node countryBridge = map.getNodes().get(2); // Replace with a bridge country node
        Node countryB = map.getNodes().get(1); // Replace with another country node

        // Ensure that countryA and countryBridge are directly connected
        assertTrue(countryA.getBorders().contains(countryBridge));
        assertTrue(countryBridge.getBorders().contains(countryA));

        // Ensure that countryBridge and countryB are directly connected
        assertTrue(countryBridge.getBorders().contains(countryB));
        assertTrue(countryB.getBorders().contains(countryBridge));

        // Attempt an attack, which should succeed with the bridge
        boolean attackResult = l_gameEngine.getGamePhase().attack(countryA, countryB, 3, 2);
        assertTrue(attackResult);
    }
    */

}
