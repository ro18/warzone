package project.app.warzone.Features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Map;
import project.app.warzone.Model.Phase;
import project.app.warzone.Utilities.MapResources;

public class EditPhaseTest {


    MapFeatures mapFeatures = MapFeatures.getInstance();
    public MapResources mapResources;
    public GameEngine gameEngine;
    public Phase playPhase;
    
    @BeforeEach
    public void setUp(){
        this.mapResources = new MapResources();
        this.mapFeatures = new MapFeatures(mapResources);
    }

    //checks if game is not skippping phase directly to next phase corretly in preloading
    @Test
    public void inEditPrePhaseValidate(){
        String l_mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "europe.map";
        Map l_map = new Map();
        l_map=mapFeatures.readMap(l_mapFileName);
        GameEngine l_gameEngine = new GameEngine(l_map);
        l_gameEngine.getGamePhase().editContinent(l_mapFileName, l_mapFileName); //preload phase command in edit phase
        String currPhase=l_gameEngine.getGamePhase().getClass().getSimpleName();
        //assertEquals("Play", currPhase); //Should fail
        assertNotEquals("Play", currPhase);
    }

    //checks if game is not skippping phase directly to next phase corretly in postloading
    @Test
    public void inEditPostPhaseValidate(){
        String l_mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "europe.map";
        Map l_map = new Map();
        l_map=mapFeatures.readMap(l_mapFileName);
        GameEngine l_gameEngine = new GameEngine(l_map);
        l_gameEngine.getGamePhase().loadMap(l_mapFileName); //postload phase command in edit phase
        String currPhase=l_gameEngine.getGamePhase().getClass().getSimpleName();
        //assertEquals("Play", currPhase); //Should fail
        assertNotEquals("End", currPhase);
    }

}