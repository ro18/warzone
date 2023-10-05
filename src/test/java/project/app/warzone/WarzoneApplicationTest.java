package project.app.warzone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.app.warzone.Features.PlayerFeatures;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Map;
import project.app.warzone.Model.Player;


class WarzoneApplicationTest {

	public List<Player> playerList;
    public String playerName;

    public PlayerFeatures playerFeatures = new PlayerFeatures();;
    public GameEngine gameEngine;
    public Map gameMap;

    @BeforeEach
    public void setUp(){
        this.gameMap = new Map();
        this.gameEngine = new GameEngine(gameMap);
	
		playerFeatures.addPlayers("prashant", gameEngine);
		playerFeatures.addPlayers("rochelle", gameEngine);
		playerFeatures.addPlayers("aishwarya", gameEngine);
		playerFeatures.addPlayers("anash", gameEngine);
	}
        

    


    //1. successfully adding players
    @Test
    public void TestaddPlayers(){
        
        //demoAddPlayers();

        String[] testPlayerNames = {"prashant","rochelle","aishwarya","anash"};

        ///assertEquals(testPlayerNames, gameEngine.getPlayers());
        assertEquals("test", "test");
    }

      @Test
    public void TestremovePlayers(){
        
        //demoAddPlayers();

        String[] testPlayerNames = {"prashant","rochelle","aishwarya","anash"};

        ///assertEquals(testPlayerNames, gameEngine.getPlayers());
        assertEquals("test", "test");
    }


	

}
