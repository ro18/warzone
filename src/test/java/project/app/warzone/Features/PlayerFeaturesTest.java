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

    public List<Player> playerList;
    public String playerName;

    public PlayerFeatures playerFeatures = new PlayerFeatures();;
    public GameEngine gameEngine;
    public Map gameMap;
    public MapFeatures mapFeatures;
    public MapResources mapResources;

    @BeforeEach
    public void setUp(){

        this.gameEngine = new GameEngine(gameMap);
        this.gameMap = new Map();
        this.mapResources = new MapResources();
        this.mapFeatures = new MapFeatures(mapResources);

        String p_mapLocation = gameEngine.gameMap.getMapDirectory()+"\\europe.map";
        gameEngine.gameMap = mapFeatures.readMap(p_mapLocation);
        
        playerFeatures.addPlayers("prashant", gameEngine);
        playerFeatures.addPlayers("rochelle", gameEngine);
        playerFeatures.addPlayers("aishwarya", gameEngine);
        playerFeatures.addPlayers("anash", gameEngine);
        
    }

    @Test
    public void TestaddPlayers(){
        
        List<Player> testPlayerList =  new ArrayList<>();
        testPlayerList.add(new Player(1, "prashant"));
        testPlayerList.add(new Player(2, "rochelle"));
        testPlayerList.add(new Player(3, "aishwarya"));
        testPlayerList.add(new Player(4, "anash"));

        List<String> expectedPlayers = new ArrayList<>();

        for (Player player : testPlayerList) {
            expectedPlayers.add(player.d_playername);
        }

        List<String> actualPlayers = new ArrayList<>();

        for (Player player : gameEngine.d_playersList) {
            actualPlayers.add(player.d_playername);
        }

        assertEquals(expectedPlayers, actualPlayers);
    
    }

    @Test
    public void TestremovePlayers(){
        
        playerFeatures.removePlayers("prashant", gameEngine);

        List<Player> testPlayerList =  new ArrayList<>();
        testPlayerList.add(new Player(1, "rochelle"));
        testPlayerList.add(new Player(2, "aishwarya"));
        testPlayerList.add(new Player(3, "anash"));

        List<String> expectedPlayers = new ArrayList<>();

        for (Player player : testPlayerList) {
            expectedPlayers.add(player.d_playername);
        }

        List<String> actualPlayers = new ArrayList<>();

        for (Player player : gameEngine.d_playersList) {
            actualPlayers.add(player.d_playername);
        }

    }

    @Test
    public void TestassignCountries(){
        
        playerFeatures.assignCountries(gameEngine);

        for( Player p : gameEngine.d_playersList)
        {
            assertNotNull(p.d_listOfCountriesOwned);
        }

        assertTrue(gameEngine.d_playersList.stream().allMatch(player -> !player.d_listOfCountriesOwned.isEmpty()));

    }

    @Test
    public void TestinitialReinforcement(){
        
        playerFeatures.assignCountries(gameEngine);

        for( Player p : gameEngine.d_playersList)
        {
            assertNotNull(p.d_reinforcementPool);
            assertEquals(3, p.d_reinforcementPool);
        } 
    }

    @Test
    public void TestplayerDeploy(){
        
        for(int i = 0; i < 4 ; i++)  {         
            Player player = gameEngine.d_playersList.get(i);
            player.setTerritories(gameEngine.gameMap.getNodes().get(i).getData());

        }
        
        int countryid = 2;
        int deployArmy = 3;

        for(int i = 0 ; i < 3; i++){
            playerFeatures.deployArmies(gameEngine, countryid + i, deployArmy);
        }

        String result = playerFeatures.deployArmies(gameEngine, countryid-1, deployArmy);

        assertEquals("Orders successfully executed", result);      

    }


    @Test
    public void TestplayerDeployGreaterthanReinforcement(){
        
        for(int i = 0; i < 4 ; i++)  {         
            Player player = gameEngine.d_playersList.get(i);
            player.setTerritories(gameEngine.gameMap.getNodes().get(i).getData());

        }

        int countryid = 2;
        int deployArmy = 5;

        for(int i = 0 ; i < 3; i++){
            playerFeatures.deployArmies(gameEngine, countryid + i, deployArmy);
        }

        String result = playerFeatures.deployArmies(gameEngine, countryid-1, deployArmy);

        assertEquals("Not enough armies to be deployed. Available armies: 3", result);     

    }
}
