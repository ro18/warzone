package project.app.warzone.Features;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import java.util.Arrays;
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
    
    }

     @Test
    public void bombSelf(){
        
        d_gameEngine.getGamePhase().loadMap("europe");
        String l_res ="1";
        d_gameEngine.getGamePhase().showMap();
        d_gameEngine.getGamePhase().setPlayers("add","rochelle");
        d_gameEngine.getGamePhase().setPlayers("add","numan");
        d_gameEngine.getGamePhase().assignCountries();

        for(int i = 0; i < 2; i++)  {         
            Player player = d_gameEngine.d_playersList.get(i);
            player.setTerritories(d_gameEngine.gameMap.getNodes().get(i).getData());

        }

        int l_countryId = 1;
        int l_deployArmy = 2;
        String l_result = "";
        for(int i = 0 ; i < 2; i++){
             l_result = d_playerFeatures.deployArmies(d_gameEngine, l_countryId++, l_deployArmy);
        }
        
        int countryId =1;
    
        String result = d_playerFeatures.bombCountry(d_gameEngine,countryId);
        assertEquals("You cannot target your own country", result); // since we are expecting armies to be reduced in half

    }


    @Test
    public void testBombCountry() {

        //Map l_map = new Map();
        //GameEngine l_gameEngine = new GameEngine(l_map);
        d_gameEngine.getGamePhase().loadMap("europe");
        String l_res ="1";
        d_gameEngine.getGamePhase().showMap();
        d_gameEngine.getGamePhase().setPlayers("add","rochelle");
        d_gameEngine.getGamePhase().setPlayers("add","numan");
        d_gameEngine.getGamePhase().setPlayers("add","anash");
        d_gameEngine.getGamePhase().assignCountries();

        List<Player> l_listOfPlayers = d_gameEngine.getPlayers();
        for (Player l_p : l_listOfPlayers) {
            int playerCountryId = l_p.getListOfTerritories().get(0).getCountryId();
            d_gameEngine.getGamePhase().reinforce(playerCountryId, 2);
        }
        assertEquals("1", l_res); // since we are expecting armies to be reduced in half


    }

   


}
