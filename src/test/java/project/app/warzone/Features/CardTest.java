package project.app.warzone.Features;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.stringtemplate.v4.compiler.CodeGenerator.primary_return;

import project.app.warzone.Commands.PlayerCommands;
import project.app.warzone.Features.PlayerFeatures;
import project.app.warzone.Features.MapFeatures;
import project.app.warzone.Model.Cards;
import project.app.warzone.Model.ConcreteBomb;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.HumanStrategy;
import project.app.warzone.Model.Map;
import project.app.warzone.Model.Player;
import project.app.warzone.Utilities.LogObject;
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
    public void setUp() {

        this.d_gameMap = new Map();
        this.d_gameEngine = new GameEngine(d_gameMap);
        this.d_mapResources = new MapResources();

    }

    @Test
    public void bombSelf(){
        MapFeatures mapFeatures= MapFeatures.getInstance();
        d_gameEngine.getGamePhase().loadMap("europe");
        d_gameEngine.getGamePhase().showMap(mapFeatures);
        d_gameEngine.getGamePhase().setPlayers("add","rochelle");
        d_gameEngine.getGamePhase().setPlayers("add","numan");
        for(Player p: d_gameEngine.d_playersList){

            p.setStrategy(new HumanStrategy(p,d_gameEngine));

        }
        d_gameEngine.getGamePhase().assignCountries();

        for(int i = 0; i < 2; i++)  {         
            Player player = d_gameEngine.d_playersList.get(i);
            player.setTerritories(d_gameEngine.gameMap.getNodes().get(i).getData());

        }

        for (Player l_p : d_gameEngine.d_playersList) {
            l_p.getListOfTerritories().get(0).setNumberOfArmies(10);;
        }
        
        int countryId =1;

        Player l_player = d_gameEngine.getPlayers().get(PlayerCommands.d_CurrentPlayerId);

        int orderSize = l_player.d_listOfOrders.size();
    
        d_playerFeatures.bombCountry(d_gameEngine,countryId);

        
        assertEquals(0, orderSize); // no order will be added hence ordersize is 0
    }


   
    @Test
    public void testBombCountry() {

        //Map l_map = new Map();
        //GameEngine l_gameEngine = new GameEngine(l_map);
        MapFeatures mapFeatures= MapFeatures.getInstance();
        d_gameEngine.getGamePhase().loadMap("europe");
        String l_res ="1";
        d_gameEngine.getGamePhase().showMap(mapFeatures);
        d_gameEngine.getGamePhase().setPlayers("add","rochelle");
        d_gameEngine.getGamePhase().setPlayers("add","numan");

        for(Player p: d_gameEngine.d_playersList){

            p.setStrategy(new HumanStrategy(p,d_gameEngine));

        }

        d_gameEngine.getGamePhase().assignCountriesForDemo(); 

        List<Player> l_listOfPlayers = d_gameEngine.getPlayers();

        for (Player l_p : l_listOfPlayers) {
            l_p.getListOfTerritories().get(0).setNumberOfArmies(10);;
        }


        Player l_player = l_listOfPlayers.get(1);

        int numArmiesBefore = l_player.getListOfTerritories().get(0).getNumberOfArmies();


        ConcreteBomb concreteBomb = new ConcreteBomb(l_player.getListOfTerritories().get(0));

        concreteBomb.execute();


        int numArmiesAfter = l_player.getListOfTerritories().get(0).getNumberOfArmies();

        assertEquals(numArmiesAfter, numArmiesBefore/2); // since we are expecting armies to be reduced in half


    }
    @Test
    public void testNegotiatedPlayer() {

        d_playerFeatures.assignCountries(d_gameEngine);

        d_gameEngine.getGamePhase().loadMap("europe");
        d_gameEngine.getGamePhase().showMap(new MapFeatures());
        d_gameEngine.getGamePhase().setPlayers("add", "rochelle");
        d_gameEngine.getGamePhase().setPlayers("add", "numan");
        d_gameEngine.getGamePhase().assignCountriesForDemo();


        for(Player p: d_gameEngine.d_playersList){

            p.setStrategy(new HumanStrategy(p,d_gameEngine));

        }

        for (int i = 0; i < 2; i++) {
            Player player = d_gameEngine.d_playersList.get(i);
            player.setTerritories(d_gameEngine.gameMap.getNodes().get(i).getData());

        }



        Player player1 = d_gameEngine.d_playersList.get(0);
        Player player2 = d_gameEngine.d_playersList.get(1);
        String l_result = Negotiate(player1, player2);

        int countryId = 2;

        l_result = d_playerFeatures.bombCountry(d_gameEngine, countryId);
        assertNotEquals("Bomb card executed Successfully", l_result);

    }
































































































































































    private String Negotiate(Player player1, Player player2) {
        return null;
    }

}
