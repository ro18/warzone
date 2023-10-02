package project.app.warzone.Features;


import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import project.app.warzone.Model.GameEngine;



public class PlayerFeaturesTest {

     private GameEngine gameEngine;
     private PlayerFeatures playerFeatures;

    @Before
    public void setUp(){

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
    public void TestremovePlayers(GameEngine gameEngine){
        //get player list before removing
        //get player list after removing
        //check if number is -1 or the name of removed player is in the list
        //use assert and also compare list of player names
     
    }

    //3. check if all players are assigned countries and no player is missing
    @Test
    public void TestassignCountries(GameEngine gameEngine){
        //get player list and assigned country list
        //check if any player's country list is null 
        //use assert null
    }
}
