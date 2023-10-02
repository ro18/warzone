package project.app.warzone.Model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import project.app.warzone.Utilities.Commands;

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

    public void execute_orders() {
        List<Player> l_players = this.getPlayers();

        while (true) {
            int l_ordersProcessed = 0;

            for (int i = 0; i < l_players.size(); i++) {
                Order order = l_players.get(i).next_order();
                if (order != null) {
                    l_ordersProcessed++;
                    order.execute();
                }
            }

            if (l_ordersProcessed == 0) {
                break;
            }
        }

    }

}
