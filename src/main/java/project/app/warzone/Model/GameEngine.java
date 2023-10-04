package project.app.warzone.Model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import project.app.warzone.Utilities.Commands;

/**
 * This class represents the main instance of the Warzone game.
 */
@Component
public class GameEngine {

    public List<Player> d_playersList;
    public Map gameMap;
    public Commands prevUserCommand;

    public GameEngine(Map gameMap) {
        this.gameMap = new Map();
        this.d_playersList = new ArrayList<>();
    }

    public List<Player> getPlayers() {
        return d_playersList; // returns all territories in the map
    }

    public Map getGameMap() {
        return gameMap; // returns all territories in the map
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
                Order order = l_players.get(i).next_order();
                if (order != null) {
                    l_playersOrdersProcessed++;
                    order.execute();
                }
            }

            if (l_playersOrdersProcessed == 0) {
                break;
            }
        }

    }

}
