package project.app.warzone.Features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Map;
import project.app.warzone.Model.Player;
import project.app.warzone.Utilities.MapResources;

public class PlayPhaseTest {
/*  MapFeatures mapFeatures = MapFeatures.getInstance();
    PlayerFeatures playerFeatures = PlayerFeatures.getInstance();
    public MapResources mapResources;
    public GameEngine gameEngine;
    
    @BeforeEach
    public void setUp(){
        this.mapResources = new MapResources();
        this.mapFeatures = new MapFeatures(mapResources);
        this.playerFeatures = new PlayerFeatures();
    }
*/

    public List<Player> d_playerList;
    public String d_playerName;

    public PlayerFeatures d_playerFeatures = new PlayerFeatures();;
    public GameEngine d_gameEngine;
    public Map d_gameMap;
    public MapFeatures d_mapFeatures;
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
        d_playerFeatures.addPlayers("rochelle", d_gameEngine);
        d_playerFeatures.addPlayers("aishwarya", d_gameEngine);
        d_playerFeatures.addPlayers("anash", d_gameEngine);
        
    }

    //checks if game allows map editing in the middle of play i.e reinforecemnt phase
    @Test
    public void mapEditAfterReinforcePhase(){
               
        for(int i = 0; i < 4 ; i++)  {         
            Player player = d_gameEngine.d_playersList.get(i);
            player.setTerritories(d_gameEngine.gameMap.getNodes().get(i).getData());

        }
        
        int l_countryId = 2;
        int l_deployArmy = 3;

        for(int i = 0 ; i < 3; i++){
            d_playerFeatures.deployArmies(d_gameEngine, l_countryId + i, l_deployArmy);
        }

        d_playerFeatures.deployArmies(d_gameEngine, l_countryId-1, l_deployArmy);//Orders successfully executed
        
        // Now that country is deployed lets try a map command from edit phase
        d_gameEngine.getGamePhase().next();
        d_gameEngine.getGamePhase().next();

        String Phase=d_gameEngine.getGamePhase().getClass().getSimpleName();
        assertNotEquals("Edit",Phase ); // Check if in Map edit Phase ; should not be in edit phase

        
    }
}
