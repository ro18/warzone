package project.app.warzone.Model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import project.app.warzone.Commands.PlayerCommands;
import project.app.warzone.Utilities.Commands;

/**
 * This class represents the main instance of the Warzone game.
 */
@Component
public class GameEngine {


    public List<Player> d_playersList;              //storing player list  
    public Map gameMap;                             //storing gameMap
    public Commands prevUserCommand;                //storing user's previous command


    /**
     * Initializing gameMap and d_playersList
     * 
     * @param gameMap           gameMap instance
     */
    public GameEngine(Map gameMap ){
        this.gameMap = gameMap; // this is required
        this.d_playersList = new ArrayList<>();
    } 

    /**
     * used for returning player list
     * 
     * @return              returns player list
     */
    public List<Player> getPlayers() {
        return d_playersList;
    }

    /**
     * used for returning game map
     * 
     * @return      returns gameMap
     */
    public Map getGameMap() {
        return gameMap;
    }


    public void checkPlayersReinforcements(){

        /**
         * Main Game loop in round robin fashion which checks the reinforcement pool of the player and if it is 0, then
         * ask the next player to deploy armies. If all players have deployed all their armies, then execute the orders
         */
        // p_gameEngine.execute_orders();

        System.out.println("checkPlayersReinforcements");

        Boolean l_flag = false;
        int l_i = PlayerCommands.d_CurrentPlayerId+1;

        List<Player> l_players = getPlayers();


        while (l_i != PlayerCommands.d_CurrentPlayerId) {
            if (l_i == l_players.size()) {
                l_i = 0;
                continue;
            }

            if (l_players.get(l_i).getReinforcementArmies() > 0) {
                l_flag = true;
                break;
            }
            l_i++;
        }

        if (l_flag) {
            PlayerCommands.d_CurrentPlayerId = l_i;
            System.out.println("Turn of " + l_players.get(l_i).getL_playername());


        } else {
            execute_orders();
            System.out.println("Orders successfully executed");
        }

    }



    /**
     * This method is used to execute orders all at once.
     * It does this by calling the next_order() method of each player and executing it in a loop until all orders are processed.
     */
    public void execute_orders() {
        List<Player> l_players = this.getPlayers();

        while (true) {
            int l_playersOrdersProcessed = 0;

            for (int i = 0; i < l_players.size(); i++) {
                


                var obj = l_players.get(i).next_order();
                if (obj!= null) {
                    l_playersOrdersProcessed++;
                    System.out.print("before execute");
                    obj.execute();
                }
            }

            if (l_playersOrdersProcessed == 0) {
                break;
            }
        }

    }

}
