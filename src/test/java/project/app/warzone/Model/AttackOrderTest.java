package project.app.warzone.Model;

import project.app.warzone.Commands.PlayerCommands;
import project.app.warzone.Features.MapFeatures;
import project.app.warzone.Features.PlayerFeatures;
import project.app.warzone.Model.Country;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Map;
import project.app.warzone.Model.Player;
import project.app.warzone.Utilities.LogObject;
import project.app.warzone.Utilities.MapResources;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class AttackOrderTest {

    public List<Player> d_playerList;
    public String d_playerName;

    public PlayerFeatures d_playerFeatures = new PlayerFeatures();
    public AttackOrder d_attackOrderObject = new AttackOrder();
    public GameEngine d_gameEngine;
    public Map d_gameMap;
    public MapFeatures d_mapFeatures;
    public List<Player> d_friendlyAlliesList = null;
    public MapResources d_mapResources;
    
    @BeforeEach
    public void setUp(){

        this.d_gameMap = new Map();
        this.d_gameEngine = new GameEngine(d_gameMap); 
        this.d_mapResources = new MapResources();
        this.d_mapFeatures = new MapFeatures(d_mapResources);

        String p_mapLocation = d_gameEngine.gameMap.getMapDirectory()+"//europe.map";
        d_gameEngine.gameMap = d_mapFeatures.readMap(p_mapLocation);
        
        d_playerFeatures.addPlayers("prashant", d_gameEngine);
        d_playerFeatures.addPlayers("aishwarya", d_gameEngine);
        
        
    }



    @Test
    public void testAirlift() {
        d_playerFeatures.assignCountries(d_gameEngine);
        LogObject l_logObject = new LogObject();

           
            Player player = d_gameEngine.d_playersList.get(0);
            player.setTerritories(d_gameEngine.gameMap.getNodes().get(0).getData());

            player.setTerritories(d_gameEngine.gameMap.getNodes().get(1).getData());

    


        Country l_countryIdfrom = d_gameEngine.gameMap.getNodes().get(0).getData();
        String l_result = "";


        Country l_countryIdto = d_gameEngine.gameMap.getNodes().get(1).getData();
        int l_armiestoAirlift = 3;

        d_attackOrderObject.Airlift( l_countryIdfrom, l_countryIdto, l_armiestoAirlift);

        assertEquals(l_result, l_logObject.getD_message());

    }


     @Test
    public void testAirliftToEnemyCountry() {
        d_playerFeatures.assignCountries(d_gameEngine);
        LogObject l_logObject = new LogObject();

           
            Player player = d_gameEngine.d_playersList.get(0);
            player.setTerritories(d_gameEngine.gameMap.getNodes().get(0).getData());

            player.setTerritories(d_gameEngine.gameMap.getNodes().get(1).getData());

    


        Country l_countryIdfrom = d_gameEngine.gameMap.getNodes().get(0).getData();
        String l_result = "Airlift Attack Unsuccessful";
        Country l_countryIdto = d_gameEngine.gameMap.getNodes().get(1).getData();
        int l_armiestoAirlift = 3;

        d_attackOrderObject.Airlift( l_countryIdfrom, l_countryIdto, l_armiestoAirlift);

        assertNotEquals(l_result, l_logObject.getD_message());

    }


    @Test
    public void testAirliftToNegotiatedCountry() {
        d_playerFeatures.assignCountries(d_gameEngine);
        LogObject l_logObject = new LogObject();

           
            Player player = d_gameEngine.d_playersList.get(0);
            player.setTerritories(d_gameEngine.gameMap.getNodes().get(0).getData());

            player.setTerritories(d_gameEngine.gameMap.getNodes().get(1).getData());

    
        Player player1 = d_gameEngine.d_playersList.get(0);
        Player player2 = d_gameEngine.d_playersList.get(1);
        String l_result = Negotiate(player1, player2);

        Country l_countryIdfrom = d_gameEngine.gameMap.getNodes().get(0).getData();
        Country l_countryIdto = d_gameEngine.gameMap.getNodes().get(1).getData();
        int l_armiestoAirlift = 3;

        d_attackOrderObject.Airlift( l_countryIdfrom, l_countryIdto, l_armiestoAirlift);

        assertNotEquals("AirLift Card executed Successfully", l_result);

    }


    @Test
    public void testBlockade() {
        d_playerFeatures.assignCountries(d_gameEngine);
        LogObject l_logObject = new LogObject();

           
        Player player = d_gameEngine.d_playersList.get(0);
        player.setTerritories(d_gameEngine.gameMap.getNodes().get(0).getData());
        player.setTerritories(d_gameEngine.gameMap.getNodes().get(1).getData());

        Country l_countryId = d_gameEngine.gameMap.getNodes().get(1).getData();


        String l_result = "";

        d_attackOrderObject.Blockade( player, l_countryId);

        assertEquals(l_result, l_logObject.getD_message());

    }

    
    /** 
     * @param player1
     * @param player2
     * @return String
     */
    public String Negotiate(Player player1, Player player2) {
        return "";
    }


    @Test
    public void testBlockadeToEnemyCountry() {
        d_playerFeatures.assignCountries(d_gameEngine);
        LogObject l_logObject = new LogObject();

           
        Player player1 = d_gameEngine.d_playersList.get(0);

        Player player2 = d_gameEngine.d_playersList.get(1);


        player1.setTerritories(d_gameEngine.gameMap.getNodes().get(0).getData());
        player1.setTerritories(d_gameEngine.gameMap.getNodes().get(1).getData());


        String l_result = "Blockade Executed Succesfully";
        Country l_countryId = d_gameEngine.gameMap.getNodes().get(1).getData();

        d_attackOrderObject.Blockade( player2, l_countryId);

        assertNotEquals(l_result, l_logObject.getD_message());

    }


     @Test
    public void testNegotiate() {

        d_playerFeatures.assignCountries(d_gameEngine);
        LogObject l_logObject = new LogObject();

        d_gameEngine.getGamePhase().loadMap("europe");
        d_gameEngine.getGamePhase().showMap();
        d_gameEngine.getGamePhase().setPlayers("add","rochelle");
        d_gameEngine.getGamePhase().setPlayers("add","numan");
        d_gameEngine.getGamePhase().assignCountriesForDemo();


        for(Player p: d_gameEngine.d_playersList){

            p.setStrategy(new HumanStrategy(p,d_gameEngine));

        }



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
        
           
        Player player1 = d_gameEngine.d_playersList.get(0);
        Player player2 = d_gameEngine.d_playersList.get(1);
        l_result = Negotiate(player1, player2);
        assertEquals(l_result, l_logObject.getD_message());

    }


    @Test
    public void testNegotiateWithSelf() {

        d_playerFeatures.assignCountries(d_gameEngine);
        LogObject l_logObject = new LogObject();

        d_gameEngine.getGamePhase().loadMap("europe");
        d_gameEngine.getGamePhase().showMap();
        d_gameEngine.getGamePhase().setPlayers("add","rochelle");
        d_gameEngine.getGamePhase().setPlayers("add","numan");
        d_gameEngine.getGamePhase().assignCountriesForDemo();

        for(Player p: d_gameEngine.d_playersList){

            p.setStrategy(new HumanStrategy(p,d_gameEngine));

        }

        for(int i = 0; i < 2; i++)  {         
            Player player = d_gameEngine.d_playersList.get(i);
            player.setTerritories(d_gameEngine.gameMap.getNodes().get(i).getData());

        }

        int l_countryId = 1;
        int l_deployArmy = 2;
        String l_results = "Negotiate Executed Successfully";
        for(int i = 0 ; i < 2; i++){
             d_playerFeatures.deployArmies(d_gameEngine, l_countryId++, l_deployArmy);
        }
        
           
        Player player1 = d_gameEngine.d_playersList.get(0);
        Negotiate(player1, player1);
        assertNotEquals(l_results, l_logObject.getD_message());

    }

    @Test
    public void testAttackonNegotiatedCountry() {


    d_playerFeatures.assignCountries(d_gameEngine);
        LogObject l_logObject = new LogObject();

        d_gameEngine.getGamePhase().loadMap("europe");
        d_gameEngine.getGamePhase().showMap();
        d_gameEngine.getGamePhase().setPlayers("add","rochelle");
        d_gameEngine.getGamePhase().setPlayers("add","numan");
        d_gameEngine.getGamePhase().assignCountriesForDemo();

        for(Player p: d_gameEngine.d_playersList){

            p.setStrategy(new HumanStrategy(p,d_gameEngine));

        }

        for(int i = 0; i < 2; i++)  {         
            Player player = d_gameEngine.d_playersList.get(i);
            player.setTerritories(d_gameEngine.gameMap.getNodes().get(i).getData());

        }

        
           
        Player player1 = d_gameEngine.d_playersList.get(0);
        Player player2 = d_gameEngine.d_playersList.get(1);
        String l_result = Negotiate(player1, player2);

        int l_countryId = 2;
        int l_deployArmy = 3;

        for (int i = 0; i < 3; i++) {
            l_result = d_playerFeatures.deployArmies(d_gameEngine, l_countryId, l_deployArmy);
            assertNotEquals("Attack order successful", l_result);

        }


    }
}
