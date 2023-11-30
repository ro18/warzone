package project.app.warzone.Features;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.app.warzone.Model.Attack;
import project.app.warzone.Model.End;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Map;
import project.app.warzone.Model.Phase;
import project.app.warzone.Model.Play;
import project.app.warzone.Utilities.MapResources;

public class AllPlayTest {
    MapFeatures mapFeatures = MapFeatures.getInstance();
    public MapResources mapResources;
    public GameEngine gameEngine;
    public Phase playPhase;
    
    @BeforeEach
    public void setUp(){
        this.mapResources = new MapResources();
        this.mapFeatures = new MapFeatures(mapResources);
    }

    //testsuite to check AttackPlay simulation and laoding of correct phases sequentially
    

    //loadgame first
    @Test
    public void LoadGame(){
        String l_mapFileName = System.getProperty("user.dir") + "/src/main/java/project/app/warzone/Utilities/Maps/" + "europe.map";
        Map l_map = new Map();
        l_map=mapFeatures.readMap(l_mapFileName);
        GameEngine l_gameEngine = new GameEngine(l_map);
        l_gameEngine.getGamePhase().getClass().getSimpleName();
        assertEquals("Preload", l_gameEngine.getGamePhase().getClass().getSimpleName()); //preload is the entry point of game
    }

    //checks if game is moving ahead to next phase corretly after preloading and initializing map and players
    @Test
    public void Initialziers(){
        Map l_map = new Map();
        GameEngine l_gameEngine = new GameEngine(l_map);
        l_gameEngine.getGamePhase().loadMap("europe");
        String nextPhase=l_gameEngine.getGamePhase().getClass().getSimpleName();
    
        assertEquals("Postload", nextPhase);
    }

    //start with attack phase
    @Test
    public void checkAttackLoader(){
            Map l_map = new Map();
            GameEngine l_gameEngine = new GameEngine(l_map);
            l_gameEngine.getGamePhase().loadMap("europe");
            l_gameEngine.setPhase(new Attack(l_gameEngine));
            String nextPhase=l_gameEngine.getGamePhase().getClass().getSimpleName();
            assertEquals("Attack", nextPhase);
    }

      //checks if game allows fortify after attacking
    @Test
    public void fortifyLoader(){
            Map l_map = new Map();
            GameEngine l_gameEngine = new GameEngine(l_map);
            l_gameEngine.getGamePhase().loadMap("europe");
            l_gameEngine.setPhase(new Attack(l_gameEngine));
            l_gameEngine.getGamePhase().next();
            String nextPhase=l_gameEngine.getGamePhase().getClass().getSimpleName();
            assertEquals("Fortify", nextPhase);
    }

    //checks if game allows reinforcement after fortifying
    @Test
    public void reinforceloader(){
            Map l_map = new Map();
            GameEngine l_gameEngine = new GameEngine(l_map);
            l_gameEngine.getGamePhase().loadMap("europe");
            l_gameEngine.setPhase(new Attack(l_gameEngine));
            l_gameEngine.getGamePhase().next();
            l_gameEngine.getGamePhase().next();
            String nextPhase=l_gameEngine.getGamePhase().getClass().getSimpleName();
            assertEquals("Reinforcement", nextPhase);
    }

    //should go back to attackin after reinforcement and all armies are resetup
    @Test
    public void completeAttackCyle(){
            Map l_map = new Map();
            GameEngine l_gameEngine = new GameEngine(l_map);
            l_gameEngine.getGamePhase().loadMap("europe");
            l_gameEngine.setPhase(new Attack(l_gameEngine));
            l_gameEngine.getGamePhase().next();
            l_gameEngine.getGamePhase().next();
            l_gameEngine.getGamePhase().next();
            String nextPhase=l_gameEngine.getGamePhase().getClass().getSimpleName();
            assertEquals("Attack", nextPhase);
    }

    @Test
    public void endGameplayCycle() {
	Map l_map = new Map();
        GameEngine l_gameEngine = new GameEngine(l_map);
        l_gameEngine.getGamePhase().loadMap("europe");
        l_gameEngine.setPhase(new Attack(l_gameEngine));
        String nextPhase=l_gameEngine.getGamePhase().getClass().getSimpleName();
        assertEquals("Attack", nextPhase);
	}

        @Test
     public void endGame() {
	Map l_map = new Map();
        GameEngine l_gameEngine = new GameEngine(l_map);
        l_gameEngine.getGamePhase().loadMap("europe");
        l_gameEngine.getGamePhase().endGame();
        String finalPhase=l_gameEngine.getGamePhase().getClass().getSimpleName();
        assertNotEquals("END", finalPhase);
	}


}