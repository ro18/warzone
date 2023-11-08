package project.app.warzone.Features;

import static org.junit.Assert.assertEquals;
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
import project.app.warzone.Model.Phase;
import project.app.warzone.Utilities.MapResources;

public class PreloadTest {


    MapFeatures mapFeatures = MapFeatures.getInstance();
    public MapResources mapResources;
    public GameEngine gameEngine;
    
    @BeforeEach
    public void setUp(){
        this.mapResources = new MapResources();
        this.mapFeatures = new MapFeatures(mapResources);
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
    //checks if game is moving ahead to next phase corretly after preloading
    @Test
    public void checkPostloadValidate(){
        String l_mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "europe.map";
        Map l_map = new Map();
        l_map=mapFeatures.readMap(l_mapFileName);
        GameEngine l_gameEngine = new GameEngine(l_map);
        l_gameEngine.getGamePhase().loadMap(l_mapFileName);
        l_gameEngine.getGamePhase().next();
        String nextPhase=l_gameEngine.getGamePhase().getClass().getSimpleName();
    
        assertEquals("Postload", nextPhase);
    }
}
