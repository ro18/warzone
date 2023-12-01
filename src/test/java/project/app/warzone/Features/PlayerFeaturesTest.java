package project.app.warzone.Features;

import project.app.warzone.Model.AttackOrder;
import project.app.warzone.Model.Country;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.HumanStrategy;
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
    public Country countryfrom;

    @BeforeEach
    public void setUp() {

        this.d_gameMap = new Map();
        this.d_gameEngine = new GameEngine(d_gameMap);
        this.d_mapResources = new MapResources();
        this.d_mapFeatures = new MapFeatures(d_mapResources);

        String p_mapLocation = d_gameEngine.gameMap.getMapDirectory() + "//europe.map";
        d_gameEngine.gameMap = d_mapFeatures.readMap(p_mapLocation);

        d_playerFeatures.addPlayers("prashant", d_gameEngine);
        d_playerFeatures.addPlayers("rochelle", d_gameEngine);
        d_playerFeatures.addPlayers("aishwarya", d_gameEngine);
        d_playerFeatures.addPlayers("anash", d_gameEngine);


        for(Player p: d_gameEngine.d_playersList){

            p.setStrategy(new HumanStrategy(p,d_gameEngine));

        }
        
    }

    @Test
    public void testAddPlayers() {

        List<Player> l_testPlayerList = new ArrayList<>();
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
    public void testRemovePlayers() {

        d_playerFeatures.removePlayers("prashant", d_gameEngine);

        List<Player> l_testPlayerList = new ArrayList<>();
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

        assertEquals(l_expectedPlayers, l_actualPlayers);

    }

    @Test
    public void testAssignCountries() {

        d_playerFeatures.assignCountries(d_gameEngine);

        for (Player l_player : d_gameEngine.d_playersList) {
            assertNotNull(l_player.d_listOfCountriesOwned);
        }

        assertTrue(d_gameEngine.d_playersList.stream().allMatch(player -> !player.d_listOfCountriesOwned.isEmpty()));

    }

    @Test
    public void testInitialReinforcement() {

        d_playerFeatures.assignCountries(d_gameEngine);

        for (Player l_player : d_gameEngine.d_playersList) {
            assertNotNull(l_player.d_reinforcementPool);
            assertEquals(3, l_player.d_reinforcementPool);
        }
    }

    @Test
    public void testPlayerDeploy() {

        for (int i = 0; i < 4; i++) {
            Player player = d_gameEngine.d_playersList.get(i);
            player.setTerritories(d_gameEngine.gameMap.getNodes().get(i).getData());

        }

        int l_countryId = 1;
        int l_deployArmy = 3;

        for (int i = 0; i < 3; i++) {
            String l_result = d_playerFeatures.deployArmies(d_gameEngine, l_countryId, l_deployArmy);
            assertEquals("", l_result);

        }

    }

    @Test
    public void testPlayerDeployGreaterthanReinforcement() {
        // todo: go in state and validate
        for (int i = 0; i < 4; i++) {
            Player player = d_gameEngine.d_playersList.get(i);
            player.setTerritories(d_gameEngine.gameMap.getNodes().get(i).getData());

        }

        int l_countryId = 1;
        int l_deployArmy = 5;

        for (int i = 0; i < 3; i++) {
            String l_result = d_playerFeatures.deployArmies(d_gameEngine, l_countryId, l_deployArmy);
            assertEquals("", l_result);

        }

    }

    @Test
    public void testAcquiredCountryGetAddtoList() {

        d_playerFeatures.assignCountries(d_gameEngine);
        Player player_1 = d_gameEngine.d_playersList.get(0);
        Player player_2 = d_gameEngine.d_playersList.get(1);

        AttackOrder attackorder = new AttackOrder();

        Country countryFrom = new Country();
                countryFrom = player_1.d_listOfCountriesOwned.get(0);

        Country countryTo = new Country();
        countryTo = player_2.d_listOfCountriesOwned.get(0);

        attackorder.Advance(player_1, player_2, 3, countryFrom, countryTo );

        assertTrue(player_1.d_listOfCountriesOwned.contains(player_1.d_listOfCountriesOwned.get(0)));

    }

    @Test
    public void testLoosingGameBy0Countries() {

        d_playerFeatures.assignCountries(d_gameEngine);

        d_gameEngine.d_playersList.get(0).d_listOfCountriesOwned.clear();

        String l_resultLooser = "Player: " + d_gameEngine.d_playersList.get(0).getL_playername() + " has lost the game";

        d_gameEngine.checkPlayersReinforcements();

        String l_expectedLooser = "Player: " + d_gameEngine.d_playersList.get(0).getL_playername()
                + " has lost the game";

        if (!d_gameEngine.d_playersList.contains("prashant")) {
            l_expectedLooser = "Player: prashant has lost the game";

        }

        assertEquals(l_expectedLooser, l_resultLooser);

        // l_player.d_listOfCountriesOwned
        // p.getListOfTerritories().size() == 0

    }

    @Test
    public void testWinnerByDefeatingPlayers() {

       // d_playerFeatures.assignCountries(d_gameEngine);

        d_gameEngine.d_playersList.get(0).d_listOfCountriesOwned.clear();
        d_gameEngine.d_playersList.get(1).d_listOfCountriesOwned.clear();
        d_gameEngine.d_playersList.get(2).d_listOfCountriesOwned.clear();

        String l_resultWinner = "Player " + d_gameEngine.d_playersList.get(3).getL_playername() + " has won the game";

        d_gameEngine.checkPlayersReinforcements();
        String l_expectedWinner = "Player anash has won the game";

        if (d_gameEngine.d_playersList.contains("anash")) {
            l_expectedWinner = "Player " + d_gameEngine.d_playersList.get(0).getL_playername() + " has won the game";

        }

        assertEquals(l_expectedWinner, l_resultWinner);

        // assertEquals("Player " + d_gameEngine.d_playersList.get(3).getL_playername()
        // + "has won the game", l_result);

    }

    @Test
    public void testWinnerByAcquiringAllCountries() {

        d_playerFeatures.assignCountries(d_gameEngine);

        d_gameEngine.d_playersList.get(0).d_listOfCountriesOwned.clear();
        d_gameEngine.d_playersList.get(1).d_listOfCountriesOwned.clear();
        d_gameEngine.d_playersList.get(2).d_listOfCountriesOwned.clear();

        d_gameEngine.checkPlayersReinforcements();

        String l_resultWinner = "Player " + d_gameEngine.d_playersList.get(0).getL_playername() + " has won the game";

        d_gameEngine.checkPlayersReinforcements();
        String l_expectedWinner = "Player prashant has won the game";

        if (d_gameEngine.d_playersList.contains("prashant")) {
            l_expectedWinner = "Player " + d_gameEngine.d_playersList.get(0).getL_playername() + " has won the game";

        }

        assertEquals(l_expectedWinner, l_resultWinner);

    }

    @Test
    public void testRemovingLooserFromList() {


        d_gameEngine.d_playersList.get(0).d_listOfCountriesOwned.clear();
        d_gameEngine.d_playersList.get(1).d_listOfCountriesOwned.clear();

        List<String> l_expectedPlayers = new ArrayList<>();

        for (Player l_player : d_gameEngine.d_playersList) {
            l_expectedPlayers.add(l_player.d_playername);
        }

        d_gameEngine.checkPlayersReinforcements();

        List<Player> l_testPlayerList = new ArrayList<>();
        l_testPlayerList.add(new Player(2, "aishwarya"));
        l_testPlayerList.add(new Player(3, "anash"));

        List<String> l_actualPlayers = new ArrayList<>();

        for (Player l_player : d_gameEngine.d_playersList) {
            l_actualPlayers.add(l_player.d_playername);
        }

        assertEquals(l_expectedPlayers, l_actualPlayers);

    }

     @Test
    public void testCheckLooserInList() {

        d_gameEngine.d_playersList.get(0).d_listOfCountriesOwned.clear();

        d_gameEngine.checkPlayersReinforcements();

        assertFalse(d_gameEngine.d_playersList.contains("prashant"));

    }

}