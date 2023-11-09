package project.app.warzone.Features;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.app.warzone.Commands.PlayerCommands;
import project.app.warzone.Features.PlayerFeatures;
import project.app.warzone.Model.Cards;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Map;
import project.app.warzone.Model.Player;
import project.app.warzone.Utilities.MapResources;


public class CardTest {

    public List<Player> d_playerList;
    public String d_playerName;
    
    public MapFeatures mapFeatures = MapFeatures.getInstance();
    public PlayerFeatures d_playerFeatures = new PlayerFeatures();;
    public GameEngine d_gameEngine;
    public Map d_gameMap;
    public MapResources d_mapResources;

    @BeforeEach
    public void setUp(){

        this.d_gameMap = new Map();
        this.d_gameEngine = new GameEngine(d_gameMap); 
        this.d_mapResources = new MapResources();
        
        d_playerFeatures.addPlayers("numan", d_gameEngine);
        d_playerFeatures.addPlayers("anash", d_gameEngine);
        d_playerFeatures.addPlayers("rochelle", d_gameEngine);
        d_playerFeatures.addPlayers("aishwarya", d_gameEngine);
        
    }

    @Test
    public void testBombCountry() {

        Map l_map = new Map();
        GameEngine l_gameEngine = new GameEngine(l_map);
        l_gameEngine.getGamePhase().loadMap("europe");

         // trying to create a player and ensure they have a bomber card
        List<Player> l_testPlayerList =  new ArrayList<>();
        l_testPlayerList.add(new Player(1, "rochelle"));
        l_testPlayerList.add(new Player(2, "aishwarya"));

        //asign armies
        d_playerFeatures.assignCountries(d_gameEngine);
        d_playerFeatures.deployArmies(d_gameEngine, 1 , 10);
        d_playerFeatures.deployArmies(d_gameEngine, 2 , 16);

        d_playerFeatures.bombCountry(d_gameEngine, 1);
        int countryId =1;
        // Call the bombCountry method with valid parameters
        String result = d_playerFeatures.bombCountry(d_gameEngine,countryId);

        // Assert that the result is as expected
        assertEquals("Bomb attack executed successfully", result);

        // Add more assertions as needed to check the game state or behavior
    }
}
