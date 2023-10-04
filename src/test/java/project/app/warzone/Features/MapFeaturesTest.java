package project.app.warzone.Features;
import static org.junit.Assert.assertEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.stringtemplate.v4.compiler.STParser.mapExpr_return;
import project.app.warzone.Features.MapFeatures;


import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Map;


public class MapFeaturesTest {

    public MapFeatures mapFeatures;

    @BeforeEach
    public void setUp(){
        this.mapFeatures = new MapFeatures();
    }
    
    @Test
    public void testValidateEntireGraphWithValidMap() {
        String mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "Castle.map";
        Map l_map = this.mapFeatures.readMap(mapFileName);
        GameEngine l_gameEngine = new GameEngine(l_map);
        boolean l_isMapValid = this.mapFeatures.validateEntireGraph(l_gameEngine);
        assertTrue(l_isMapValid);
    }

    @Test
    public void testValidateEntireGraphWithInvalidMap() {
        String mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "Jtest2.map";
        Map l_map = this.mapFeatures.readMap(mapFileName);
        GameEngine l_gameEngine = new GameEngine(l_map);
        boolean l_isMapValid = this.mapFeatures.validateEntireGraph(l_gameEngine);
        assertFalse(l_isMapValid);   
    }

    @Test
    public void testValidateValidContinent() {
        String mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "Castle.map";
        Map l_map = this.mapFeatures.readMap(mapFileName);
        GameEngine l_gameEngine = new GameEngine(l_map);
        //boolean l_isContinentValid = this.mapFeatures.validateContinent(l_gameEngine, l_map.getListOfContinents().get(0));
        //assertTrue(l_isContinentValid);
    }

    @Test
    public void testValidateInvalidContinent() {
        String mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "Jtest2.map";
        Map l_map = this.mapFeatures.readMap(mapFileName);
        GameEngine l_gameEngine = new GameEngine(l_map);
        //boolean l_isContinentValid = this.mapFeatures.validateContinent(l_gameEngine, l_map.getListOfContinents().get(2));
        //assertFalse(l_isContinentValid);
    }
}
