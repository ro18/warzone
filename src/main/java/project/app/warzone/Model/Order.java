package project.app.warzone.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Order {
    String d_orderType;
    int d_numberOfArmies;
    Territory d_Territory;

    public String execute(Player p_Player, Order p_order) {
        List<Territory> listOfCountriesOwned = new ArrayList<>();
        if (p_order.d_orderType == "DEPLOY") {
            // check if the player owns the territory
            // if(p_Player.getL_playerid() != p_Territory.getOwnerId())
            // {
            // return "Player does not own the territory";
            // }

            listOfCountriesOwned = p_Player.getlistOfCountriesOwned();
            Optional<Territory> Territory = listOfCountriesOwned.stream()
                    .filter(c -> c.getTerritoryName().equals(p_order.d_Territory.getTerritoryName())).findFirst();

            // check if the player has enough armies to deploy
            if (Territory != null && p_Player.getReinforcementMap() >= p_order.d_numberOfArmies) {
                // Increment the territory army number
                // Decrease the player reinforcement pool by the number of armies
                p_Player.setReinforcementMap(p_Player.getReinforcementMap() - p_order.d_numberOfArmies);
                Territory.get().setNumberOfArmies(Territory.get().getNumberOfArmies() + p_order.d_numberOfArmies);
                return "Deployed armies successfully";
            } else {
                return "Not enough armies to deploy";
            }

        }
        return "Invalid order";
    }
}