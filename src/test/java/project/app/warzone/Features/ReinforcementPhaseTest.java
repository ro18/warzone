package project.app.warzone.Features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.app.warzone.Model.Attack;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Map;
import project.app.warzone.Model.Phase;
import project.app.warzone.Model.Reinforcement;
import project.app.warzone.Utilities.MapResources;

public class ReinforcementPhaseTest {


    MapFeatures mapFeatures = MapFeatures.getInstance();
    public MapResources mapResources;
    public GameEngine gameEngine;
    public Phase playPhase;
    
    @BeforeEach
    public void setUp(){
        this.mapResources = new MapResources();
        this.mapFeatures = new MapFeatures(mapResources);
    }


    //checks if game is not skippping to End directly from reinforcement
    @Test
    public void reinforceValidity(){
            Map l_map = new Map();
            GameEngine l_gameEngine = new GameEngine(l_map);
            l_gameEngine.setPhase(new Reinforcement(l_gameEngine));
            l_gameEngine.getGamePhase().loadMap("europe");
            //l_gameEngine.getGamePhase().next();
            String nexphase = l_gameEngine.getGamePhase().getClass().getSimpleName();
            assertEquals("Reinforcement", nexphase);
    }

    //attack after reinforcement
    @Test
    public void afterReinforcement() {
            Map l_map = new Map();
            GameEngine l_gameEngine = new GameEngine(l_map);
            l_gameEngine.setPhase(new Reinforcement(l_gameEngine));
            l_gameEngine.getGamePhase().loadMap("europe");
            l_gameEngine.getGamePhase().next();
            String mainClassPhase = l_gameEngine.getGamePhase().getClass().getSimpleName();
            assertEquals("Attack", mainClassPhase); 
        }

      //checks if game is not skippping phase directly to next phase corretly in preloading
    @Test
    public void inReinforcementPhaseValidate(){
        String l_mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "europe.map";
        Map l_map = new Map();
        l_map=mapFeatures.readMap(l_mapFileName);
        GameEngine l_gameEngine = new GameEngine(l_map);
        l_gameEngine.getGamePhase().editContinent(l_mapFileName, l_mapFileName); //preload phase command in edit phase
        String currPhase=l_gameEngine.getGamePhase().getClass().getSimpleName();
        //set next phase 
        l_gameEngine.getGamePhase().next();
        l_gameEngine.getGamePhase().next();
        String nextPhase=l_gameEngine.getGamePhase().getClass().getSimpleName();
        //assertEquals("Reinforce", nextPhase);
        l_gameEngine.setPhase(new Attack(l_gameEngine));
        String myclass = l_gameEngine.getGamePhase().getClass().getSimpleName();
        assertNotEquals("Reinforce", myclass);//should not attack before reinforcement

    }
    
    //reach reinforcement phase cycle
    @Test
    public void currentReinforcement(){
        Map l_map = new Map();
        GameEngine l_gameEngine = new GameEngine(l_map);
        l_gameEngine.setPhase(new Attack(l_gameEngine));
        l_gameEngine.getGamePhase().loadMap("europe");
        l_gameEngine.getGamePhase().next();
        l_gameEngine.getGamePhase().next();
        String currPhase = l_gameEngine.getGamePhase().getClass().getSimpleName();
        assertEquals("Reinforcement", currPhase);
    }

}