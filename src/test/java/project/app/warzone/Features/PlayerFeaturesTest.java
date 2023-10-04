package project.app.warzone.Features;

import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Map;
import project.app.warzone.Model.Player;
import project.app.warzone.Utilities.MapResources;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PlayerFeaturesTest {

    public List<Player> d_playerList;
    public String d_playerName;

    public PlayerFeatures d_playerFeatures = new PlayerFeatures();;
    public GameEngine d_gameEngine;
    public Map d_gameMap;
    public MapFeatures d_mapFeatures;
    public MapResources d_mapResources;

    @BeforeEach
    public void setUp(){

        this.d_gameEngine = new GameEngine(d_gameMap);
        this.d_gameMap = new Map();
        this.d_mapResources = new MapResources();
        this.d_mapFeatures = new MapFeatures(d_mapResources);

        String p_mapLocation = d_gameEngine.gameMap.getMapDirectory()+"\\europe.map";
        d_gameEngine.gameMap = d_mapFeatures.readMap(p_mapLocation);
        
        d_playerFeatures.addPlayers("prashant", d_gameEngine);
        d_playerFeatures.addPlayers("rochelle", d_gameEngine);
        d_playerFeatures.addPlayers("aishwarya", d_gameEngine);
        d_playerFeatures.addPlayers("anash", d_gameEngine);
        
    }

    @Test
    public void testAddPlayers(){
        
        List<Player> l_testPlayerList =  new ArrayList<>();
        l_testPlayerList.add(new Player(1, "prashant"));
        l_testPlayerList.add(new Player(2, "rochelle"));
        l_testPlayerList.add(new Player(3, "aishwarya"));
        l_testPlayerList.add(new Player(4, "anash"));

        List<String> l_expectedPlayers = new ArrayList<>();

        for (Player player : l_testPlayerList) {
            l_expectedPlayers.add(player.d_playername);
        }

        List<String> l_actualPlayers = new ArrayList<>();

        for (Player l_player : d_gameEngine.d_playersList) {
            l_actualPlayers.add(l_player.d_playername);
        }

        assertEquals(l_expectedPlayers, l_actualPlayers);
    
    }

    @Test
    public void testRemovePlayers(){
        
        d_playerFeatures.removePlayers("prashant", d_gameEngine);

        List<Player> l_testPlayerList =  new ArrayList<>();
        l_testPlayerList.add(new Player(1, "rochelle"));
        l_testPlayerList.add(new Player(2, "aishwarya"));
        l_testPlayerList.add(new Player(3, "anash"));

        List<String> l_expectedPlayers = new ArrayList<>();

        for (Player player : l_testPlayerList) {
            l_expectedPlayers.add(player.d_playername);
        }

        List<String> l_actualPlayers = new ArrayList<>();

        for (Player l_player : d_gameEngine.d_playersList) {
            l_actualPlayers.add(l_player.d_playername);
        }

    }

    @Test
    public void testAssignCountries(){
        
        d_playerFeatures.assignCountries(d_gameEngine);

        for( Player l_player : d_gameEngine.d_playersList)
        {
            assertNotNull(l_player.d_listOfCountriesOwned);
        }

        assertTrue(d_gameEngine.d_playersList.stream().allMatch(player -> !player.d_listOfCountriesOwned.isEmpty()));

    }

    @Test
    public void testInitialReinforcement(){
        
        d_playerFeatures.assignCountries(d_gameEngine);

        for( Player l_player : d_gameEngine.d_playersList)
        {
            assertNotNull(l_player.d_reinforcementPool);
            assertEquals(3, l_player.d_reinforcementPool);
        } 
    }

    @Test
    public void testPlayerDeploy(){
        
        for(int i = 0; i < 4 ; i++)  {         
            Player player = d_gameEngine.d_playersList.get(i);
            player.setTerritories(d_gameEngine.gameMap.getNodes().get(i).getData());

        }
        
        int l_countryId = 2;
        int l_deployArmy = 3;

        for(int i = 0 ; i < 3; i++){
            d_playerFeatures.deployArmies(d_gameEngine, l_countryId + i, l_deployArmy);
        }

        String l_result = d_playerFeatures.deployArmies(d_gameEngine, l_countryId-1, l_deployArmy);

        assertEquals("Orders successfully executed", l_result);      

    }


    @Test
    public void testPlayerDeployGreaterthanReinforcement(){
        
        for(int i = 0; i < 4 ; i++)  {         
            Player player = d_gameEngine.d_playersList.get(i);
            player.setTerritories(d_gameEngine.gameMap.getNodes().get(i).getData());

        }

        int l_countryId = 2;
        int l_deployArmy = 5;

        for(int i = 0 ; i < 3; i++){
            d_playerFeatures.deployArmies(d_gameEngine, l_countryId + i, l_deployArmy);
        }

        String l_result = d_playerFeatures.deployArmies(d_gameEngine, l_countryId-1, l_deployArmy);

        assertEquals("Not enough armies to be deployed. Available armies: 3", l_result);     

    }
}
