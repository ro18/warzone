package project.app.warzone.Features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.app.warzone.Model.Attack;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Map;
import project.app.warzone.Utilities.MapResources;

public class StartPhaseTest {
    MapFeatures mapFeatures = MapFeatures.getInstance();
    public MapResources mapResources;
    public GameEngine gameEngine;
    
    @BeforeEach
    public void setUp(){
        this.mapResources = new MapResources();
        // this.mapFeatures = new MapFeatures(mapResources);
    }


    @Test
    public void startUpPhaseValidate(){
        String l_mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "europe.map";
        Map l_map = new Map();
        l_map=mapFeatures.readMap(l_mapFileName);
        GameEngine l_gameEngine = new GameEngine(l_map);
        l_gameEngine.getGamePhase().getClass().getSimpleName();
        assertEquals("Preload", l_gameEngine.getGamePhase().getClass().getSimpleName());
    }



    //checks if savemap is in preload state
    @Test
    public void checkPeloadValidate(){
        Map l_map = new Map();
        GameEngine l_gameEngine = new GameEngine(l_map);
        l_gameEngine.getGamePhase().saveMap();
        String currPhase=l_gameEngine.getGamePhase().getClass().getSimpleName();
        assertEquals("Preload", currPhase);
    }
    
    //checks if game is moving ahead to next phase corretly after preloading
    @Test
    public void checkPostloadValidate(){
        Map l_map = new Map();
        GameEngine l_gameEngine = new GameEngine(l_map);
        l_gameEngine.getGamePhase().loadMap("europe");
        String nextPhase=l_gameEngine.getGamePhase().getClass().getSimpleName();
    
        assertEquals("Postload", nextPhase);
    }
}