package project.app.warzone.Features;

import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Player;

import org.junit.*;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;


public class PlayerFeaturesTest {

    public List<Player> playerList;
    public String playerName;

    @Before
    public void setUp(String p_playerName, GameEngine gameEngine){
        playerList = gameEngine.getPlayers();
        playerName = p_playerName;

    }

    //1. successfully adding players

    @Test
    public void TestaddPlayers(GameEngine gameEngine){
        //get player list
        //assert statement to compare with returned number of players
        //or check if the returned list is null 
        //use assert null

        assertNotNull(gameEngine.d_playersList);
    }

    //2. successfully removing players
    @Test
    public boolean TestremovePlayers(GameEngine gameEngine, Player player){
        //get player list before removing
        //get player list after removing
        //check if number is -1 or the name of removed player is in the list
        //use assert and also compare list of player names

        boolean flag = true;
        for (Player p : playerList){
            if(playerName == p.getL_playername()){
                flag = false;
            }
        
        }
        return flag;
        
    }

    //3. check if all players are assigned countries and no player is missing
    @Test
    public void TestassignCountries(GameEngine gameEngine, Player player){
        //get player list and assigned country list
        //check if any player's country list is null 
        //use assert null

        for( Player p : gameEngine.d_playersList)
        {
            assertNotNull(p.d_listOfCountriesOwned);
        }
    }

    //4. check if player is assigned reinforcements and no player is remaining to assign reinforcementpool
    @Test
    public void TestassignReinforcement(GameEngine gameEngine, Player player){
        //get player list and assigned reinforcement number
        //check if any player's reinforcement list is null 
        //use assert null

        for( Player p : gameEngine.d_playersList)
        {
            assertNotNull(p.d_reinforcementPool);
        } 
    }

    
}
