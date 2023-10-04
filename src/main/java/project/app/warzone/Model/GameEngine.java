package project.app.warzone.Model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import project.app.warzone.Utilities.Commands;

/**
 * This class is used to contains all the details needed for game play
 */
@Component
public class GameEngine {


    public List<Player> d_playersList;              //storing player list  
    public Map gameMap;                             //storing gameMap
    public Commands prevUserCommand;                //storing user's previous command


    /**
     * Initializing gameMap and d_playersList
     * 
     * @param gameMap
     */
    public GameEngine(Map gameMap ){
        this.gameMap = new Map(); // this is required
        this.d_playersList = new ArrayList<>();
    } 

    /**
     * used for returning player list
     * 
     * @return
     */
    public List<Player> getPlayers() {
        return d_playersList; // returns player list
    }

    /**
     * used for returning game map
     * 
     * @return
     */
    public Map getGameMap() {
        return gameMap; // returns game map
    }

    
    
}
