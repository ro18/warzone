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

public class PlayPhaseTest {


    MapFeatures mapFeatures = MapFeatures.getInstance();
    public MapResources mapResources;
    public GameEngine gameEngine;
    public Phase playPhase;
    
    @BeforeEach
    public void setUp(){
        this.mapResources = new MapResources();
        this.mapFeatures = new MapFeatures(mapResources);
    }

    //checks if game allows map editing in the middle of play i.e reinforecemnt phase
    @Test
    public void inAttackPhase(){
            Map l_map = new Map();
            GameEngine l_gameEngine = new GameEngine(l_map);
            l_gameEngine.getGamePhase().loadMap("europe");
            l_gameEngine.setPhase(new Attack(l_gameEngine));
            String nextPhase=l_gameEngine.getGamePhase().getClass().getSimpleName();
        
            assertEquals("Attack", nextPhase);
        }

    //checks if game allows loading new map in the middle of play i.e reinforecemnt phase

    @Test
    public void attackPhaseValidity(){
            Map l_map = new Map();
            GameEngine l_gameEngine = new GameEngine(l_map);
            l_gameEngine.setPhase(new Attack(l_gameEngine));
            l_gameEngine.getGamePhase().loadMap("europe");
            String nexphase = l_gameEngine.getGamePhase().getClass().getSimpleName();
        
            assertEquals("Attack", nexphase); //doesn't allow to go previous phase of Attack phase
        }
    
     @Test
    public void attackPhaseContinuity(){
            Map l_map = new Map();
            GameEngine l_gameEngine = new GameEngine(l_map);
            l_gameEngine.setPhase(new Attack(l_gameEngine));
            l_gameEngine.getGamePhase().loadMap("europe");
            l_gameEngine.getGamePhase().next();
            String nexphase = l_gameEngine.getGamePhase().getClass().getSimpleName();
            assertNotEquals("END", nexphase); //must not end the game after Attack
        }
    

}
