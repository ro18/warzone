package project.app.warzone.Features;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Component;

import project.app.warzone.Commands.PlayerCommands;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Order;
import project.app.warzone.Model.Player;
import project.app.warzone.Model.Country;

/**
 * This class stores all the player-related functions in gameplay
 */
@Component
public class PlayerFeatures {


    
    /** 
     * @param allPlayers       list of all players 
     */
    public void showAllAssignments(List<Player> allPlayers){

        for (Player p : allPlayers) {

            System.out.println("Player Id:" + p.d_playerid);

           System.out.println("Countries of "+p.d_playername+":");
           List<Country> listOfTerritories = p.getListOfTerritories();

           for(Country t : listOfTerritories){
              System.out.println(t.getCountryName());

            }
        }

    }


    
    /** 
     * @param p_gameEngine      storing gameEngine
     */
    public void assignCountries(GameEngine p_gameEngine){

        Random l_random = new Random();

        for( Player p : p_gameEngine.getPlayers()){ // logic to have players -1 country

            
            int randomCountry =  l_random.nextInt(p_gameEngine.gameMap.getNodes().size());

            int randomId = l_random.nextInt(p_gameEngine.getPlayers().size()+1);

            while (p_gameEngine.gameMap.getNodes().get(randomCountry).getData().getOwnerId() != 0) {
                randomCountry = l_random.nextInt(p_gameEngine.gameMap.getNodes().size() + 1);

            }             
        
            p.setTerritories(p_gameEngine.gameMap.getNodes().get(randomCountry).getData());

        }
    }



    
    /** 
     * @param p_playerName          storing playername
     * @param gameEngine            storing gameEngine
     */
    public void addPlayers(String p_playerName, GameEngine gameEngine){

        int l_playerCount = gameEngine.getPlayers().size();
        Player player= new Player(l_playerCount++,p_playerName);
        gameEngine.d_playersList.add(player);

    }

    /**
     * @param gameEngine               storing gameEngine
     */
    public void setPlayerIds(GameEngine gameEngine){
        
        int i=1;
        for(Player l_player : gameEngine.getPlayers()){
            
            l_player.setL_playerid(i);
        }




    }

    
    /** 
     * @param p_playerName              storing playername
     * @param gameEngine                storing gameEngine
     */
    public void removePlayers(String p_playerName, GameEngine gameEngine){

        List<Player> playerList = gameEngine.getPlayers();
        Optional<Player> l_playerToRemove= playerList.stream().filter(c->c.getL_playername().equals(p_playerName)).findFirst();
        playerList.remove(l_playerToRemove.get());
        setPlayerIds(gameEngine);        


    }


    
    /** 
     * @param gameEngine            storing gameEngine
     */
    public void printAllPlayers(GameEngine gameEngine){
        System.out.println("Final players of the game are:");
        List<Player> players = gameEngine.getPlayers();
        for (int i = 1; i <= players.size(); i++) {
            System.out.println("Player" + i + ":" + players.get(i - 1).getL_playername());
        }
    }

    
    
    /** 
     * @param gameengine            storing gameEngine
     */
    public void showStats(GameEngine gameengine){
        List<Player> listOfPlayers = gameengine.getPlayers();
        for (Player p : listOfPlayers) {
            System.out.println("Player Name:" + p.d_playername + "-PlayerId:" + p.d_playerid);
            System.out.println("Total Armies available per round: " + p.getReinforcementArmies());
            System.out.println("Countries Owned - Armies");
            for(Country t : p.getListOfTerritories()){
                System.out.println(t.getCountryName()+" - "+t.getNumberOfArmies());
            }
            System.out.println("-------------------------------");
        }

    }

    /**
     * This method is used to deploy armies on a country
     * @param p_gameEngine Instance of the game engine
     * @param p_countryID ID of the country to deploy armies on
     * @param p_armies Number of armies to deploy
     * @return A string containing status of the game.
     */

    public String deployArmies(GameEngine p_gameEngine, int p_countryID, int p_armies) {
        List<Player> l_players = p_gameEngine.getPlayers();
        Player player = p_gameEngine.getPlayers().get(PlayerCommands.d_currentPlayerId);
        Country country = p_gameEngine.gameMap.getNodes().get(p_countryID - 1).getData();

        /**
         * Check if the player has enough armies in the reinforcement pool to deploy
         */

        if (player.getReinforcementArmies() < p_armies) {
            return "Not enough armies to be deployed. Available armies: " + player.getReinforcementArmies();
        }

        /**
         * Check if the country is owned by the player
         */

        Optional<Country> territory = player.d_listOfCountriesOwned.stream()
                .filter(c -> c.getCountryName().equals(country.getCountryName())).findFirst();

        if (!territory.isPresent()) {
            return "Country is not owned by the player";
        }

        Order order = new Order();
        order.setL_numberOfArmies(p_armies);
        order.setL_territory(country);
        player.issue_order(order);

        /**
         * Main Game loop in round robin fashion which checks the reinforcement pool of the player and if it is 0, then
         * ask the next player to deploy armies. If all players have deployed all their armies, then execute the orders
         */

        Boolean l_flag = false;
        int i = PlayerCommands.d_currentPlayerId + 1;

        while (i != PlayerCommands.d_currentPlayerId) {
            if (i == l_players.size()) {
                i = 0;
                continue;
            }

            if (l_players.get(i).getReinforcementArmies() > 0) {
                l_flag = true;
                break;
            }
            i++;
        }

        if (l_flag) {
            PlayerCommands.d_currentPlayerId = i;
            return "Turn of " + l_players.get(i).getL_playername() + " to deploy army";
        } else {
            p_gameEngine.execute_orders();
            return "Orders successfully executed";
        }
    }

}
