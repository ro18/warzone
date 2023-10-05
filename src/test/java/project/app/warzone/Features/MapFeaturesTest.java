package project.app.warzone.Features;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Map;
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
        Map l_map = this.mapFeatures.readMap(l_mapFileName);
        GameEngine l_gameEngine = new GameEngine(l_map);
        boolean l_isMapValid = this.mapFeatures.validateEntireGraph(l_gameEngine);
        assertTrue(l_isMapValid);
    }

    @Test
    public void testValidateEntireGraphWithInvalidMap() {
        String l_mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "Jtest2.map";
        Map l_map = this.mapFeatures.readMap(l_mapFileName);
        GameEngine l_gameEngine = new GameEngine(l_map);
        boolean l_isMapValid = this.mapFeatures.validateEntireGraph(l_gameEngine);
        assertFalse(l_isMapValid);   
    }

    @Test
    public void testValidateValidContinent() {
        String l_mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "Castle.map";
        Map l_map = this.mapFeatures.readMap(l_mapFileName);
        GameEngine l_gameEngine = new GameEngine(l_map);
        //boolean l_isContinentValid = this.mapFeatures.validateContinent(l_gameEngine, l_map.getListOfContinents().get(0));
        //assertTrue(l_isContinentValid);
    }

    @Test
    public void testValidateInvalidContinent() {
        String l_mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "Jtest2.map";
        Map l_map = this.mapFeatures.readMap(l_mapFileName);
        GameEngine l_gameEngine = new GameEngine(l_map);
        //boolean l_isContinentValid = this.mapFeatures.validateContinent(l_gameEngine, l_map.getListOfContinents().get(2));
        //assertFalse(l_isContinentValid);
    }
}
