package project.app.warzone.Features;

import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Player;

import org.assertj.core.api.Assertions;
import org.junit.*;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;


public class PlayerFeaturesTest {

    public List<Player> playerList;
    public String playerName;

    public PlayerFeatures playerFeatures = new PlayerFeatures();;
    public GameEngine gameEngine;

    @Before
    public void setUp(){
        gameEngine = new GameEngine(gameEngine.getGameMap());

    }

    //1. successfully adding players
    @Test
    public void TestaddPlayers(){
        
        demoAddPlayers();

        String[] testPlayerNames = {"prashant","rochelle","aishwarya","anash"};

        assertEquals(testPlayerNames, gameEngine.getPlayers());
    }

    //2. successfully removing players
    @Test
    public void TestremovePlayers(GameEngine gameEngine, Player player){
        
        demoAddPlayers();
        playerFeatures.removePlayers("prashant", gameEngine);

        String[] testPlayerNames = {"rochelle","aishwarya","anash"};

        assertEquals(testPlayerNames, gameEngine.getPlayers());
    }

    //3. check if all players are assigned countries and no player is missing
    @Test
    public void TestassignCountries(GameEngine gameEngine, Player player){
        
        demoAddPlayers();
        playerFeatures.assignCountries(gameEngine);

        for( Player p : gameEngine.d_playersList)
        {
            assertNotNull(p.d_listOfCountriesOwned);
        }
    }

    //4. check if player is assigned reinforcements and no player is remaining to assign reinforcementpool
    @Test
    public void TestinitialReinforcement(GameEngine gameEngine, Player player){
        
        demoAddPlayers();
        playerFeatures.assignCountries(gameEngine);

        for( Player p : gameEngine.d_playersList)
        {
            assertNotNull(p.d_reinforcementPool);
        } 
    }

    //5. player cannot deploy more armies that there is in their reinforcement pool. 
    @Test
    public void TestplayerDeploy(GameEngine gameEngine, Player player){
        
        demoAddPlayers();
        playerFeatures.assignCountries(gameEngine);

        //try deploying larger army than possible should fail.
        //playerFeatures.deployArmies(gameEngine, countryid, amry);
      

    }

    void demoAddPlayers(){
        
        playerFeatures.addPlayers("prashant", gameEngine);
        playerFeatures.addPlayers("rochelle", gameEngine);
        playerFeatures.addPlayers("aishwarya", gameEngine);
        playerFeatures.addPlayers("anash", gameEngine);
    }
}
