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

    public PlayerFeatures playerFeatures;
    public GameEngine gameEngine;

    @Before
    public void setUp(){
        playerFeatures = new PlayerFeatures();
        gameEngine = new GameEngine(gameEngine.getGameMap());

    }

    //1. successfully adding players

    @Test
    public void TestaddPlayers(){
        //get player list
        //assert statement to compare with returned number of players
        //or check if the returned list is null 
        //use assert null

        playerFeatures.addPlayers("prashant", gameEngine);
        playerFeatures.addPlayers("rochelle", gameEngine);
        playerFeatures.addPlayers("aishwarya", gameEngine);
        playerFeatures.addPlayers("anash", gameEngine);

        String[] testPlayerNames = {"prashant","rochelle","aishwarya","anash"};

        assertEquals(testPlayerNames, gameEngine.getPlayers());
    }

    //2. successfully removing players
    @Test
    public void TestremovePlayers(GameEngine gameEngine, Player player){
        //get player list before removing
        //get player list after removing
        //check if number is -1 or the name of removed player is in the list
        //use assert and also compare list of player names

        playerFeatures.addPlayers("prashant", gameEngine);
        playerFeatures.addPlayers("rochelle", gameEngine);
        playerFeatures.addPlayers("aishwarya", gameEngine);
        playerFeatures.addPlayers("anash", gameEngine);

        playerFeatures.removePlayers("prashant", gameEngine);

        String[] testPlayerNames = {"rochelle","aishwarya","anash"};

        assertEquals(testPlayerNames, gameEngine.getPlayers());
    }

    //3. check if all players are assigned countries and no player is missing
    @Test
    public void TestassignCountries(GameEngine gameEngine, Player player){
        //get player list and assigned country list
        //check if any player's country list is null 
        //use assert null

        playerFeatures.addPlayers("prashant", gameEngine);
        playerFeatures.addPlayers("rochelle", gameEngine);
        playerFeatures.addPlayers("aishwarya", gameEngine);
        playerFeatures.addPlayers("anash", gameEngine);

        playerFeatures.assignCountries(gameEngine);

        for( Player p : gameEngine.d_playersList)
        {
            assertNotNull(p.d_listOfCountriesOwned);
        }
    }

    //4. check if player is assigned reinforcements and no player is remaining to assign reinforcementpool
    @Test
    public void TestinitialReinforcement(GameEngine gameEngine, Player player){
        //get player list and assigned reinforcement number
        //check if any player's reinforcement list is null 
        //use assert null

        playerFeatures.addPlayers("prashant", gameEngine);
        playerFeatures.addPlayers("rochelle", gameEngine);
        playerFeatures.addPlayers("aishwarya", gameEngine);
        playerFeatures.addPlayers("anash", gameEngine);

        playerFeatures.assignCountries(gameEngine);

        for( Player p : gameEngine.d_playersList)
        {
            assertNotNull(p.d_reinforcementPool);
        } 
    }

    //5. player cannot deploy more armies that there is in their reinforcement pool. 
    @Test
    public void TestplayerDeploy(GameEngine gameEngine, Player player){

        //add
        //assign country
        //deploy

        playerFeatures.addPlayers("prashant", gameEngine);
        playerFeatures.addPlayers("rochelle", gameEngine);
        playerFeatures.addPlayers("aishwarya", gameEngine);
        playerFeatures.addPlayers("anash", gameEngine);

        playerFeatures.assignCountries(gameEngine);

        //try deploying larger army than possible should fail.
     //   playerFeatures.deployArmies(gameEngine, countryid, amry);



    }
}
