package project.app.warzone.Features;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Component;

import project.app.warzone.Commands.PlayerCommands;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Order;
import project.app.warzone.Model.OrderMethods;
import project.app.warzone.Model.Player;
import project.app.warzone.Model.ConcreteDeploy;
import project.app.warzone.Model.Country;

/**
 * This class stores all the player-related functions in gameplay
 */
@Component
public class PlayerFeatures {


    
    /** 
     * @param p_allPlayers       list of all players 
     */
    public void showAllAssignments(List<Player> p_allPlayers){

        for (Player l_p : p_allPlayers) {

            System.out.println("Player Id:" + l_p.d_playerid);

           System.out.println("Countries of "+l_p.d_playername+":");
           List<Country> listOfTerritories = l_p.getListOfTerritories();

           for(Country l_t : listOfTerritories){
              System.out.println(l_t.getCountryName());

            }
        }

    }


    
    /** 
     * @param p_gameEngine      storing gameEngine
     */
    public void assignCountries(GameEngine p_gameEngine){

        Random l_random = new Random();

        for( Player l_player : p_gameEngine.getPlayers()){ // logic to have players -1 country

            
            int randomCountry =  l_random.nextInt(p_gameEngine.gameMap.getNodes().size());

            int randomId = l_random.nextInt(p_gameEngine.getPlayers().size()+1);

            while (p_gameEngine.gameMap.getNodes().get(randomCountry).getData().getOwnerId() != 0) {
                randomCountry = l_random.nextInt(p_gameEngine.gameMap.getNodes().size() + 1);

            }             
        
            l_player.setTerritories(p_gameEngine.gameMap.getNodes().get(randomCountry).getData());

        }
    }



    
    /** 
     * @param p_playerName          storing playername
     * @param p_gameEngine            storing gameEngine
     */
    public void addPlayers(String p_playerName, GameEngine p_gameEngine){

            Player player= new Player(p_gameEngine.getPlayers().size() +1,p_playerName);
        p_gameEngine.d_playersList.add(player);

    }

     /**
     * @param p_gameEngine                gameEngine object
     */
    public void setPlayerIds(GameEngine p_gameEngine){
        
        int l_i=p_gameEngine.getPlayers().size() ;
        for(Player l_player : p_gameEngine.getPlayers()){
            
            l_player.setL_playerid(l_i);
        }




    }

    
    /** 
     * @param p_playerName              storing playername
     * @param p_gameEngine                storing gameEngine
     */
    public void removePlayers(String p_playerName, GameEngine p_gameEngine){

        List<Player> l_playerList = p_gameEngine.getPlayers();
        Optional<Player> l_playerToRemove= l_playerList.stream().filter(c->c.getL_playername().equals(p_playerName)).findFirst();
        l_playerList.remove(l_playerToRemove.get());
        setPlayerIds(p_gameEngine);        


    }


    
    /** 
     * @param p_gameEngine            storing gameEngine
     */
    public void printAllPlayers(GameEngine p_gameEngine){
        System.out.println("Final players of the game are:");
        List<Player> l_players = p_gameEngine.getPlayers();
        for (int i = 1; i <= l_players.size(); i++) {
            System.out.println("Player" + i + ":" + l_players.get(i - 1).getL_playername());
        }
    }

    
    
    /** 
     * @param p_gameengine            storing gameEngine
     */
    public void showStats(GameEngine p_gameengine){
        List<Player> l_listOfPlayers  = p_gameengine.getPlayers();
        for (Player l_p : l_listOfPlayers) {
            System.out.println("Player Name:" + l_p.d_playername + "\nPlayerId:" + l_p.d_playerid);
            System.out.println("Total Armies available per round: " + l_p.getReinforcementArmies());
            System.out.println("CountryID - Countries Owned - Armies");
            List<Country> coun = l_p.getListOfTerritories();

            for(Country t : l_p.getListOfTerritories()){
                System.out.println(t.getCountryId()+" - "+t.getCountryName()+" - "+t.getNumberOfArmies());
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

        Player l_player = p_gameEngine.getPlayers().get(PlayerCommands.d_CurrentPlayerId);
        Country l_country = p_gameEngine.gameMap.getNodes().get(p_countryID-1).getData();

        /**
         * Check if the player has enough armies in the reinforcement pool to deploy
         */

        if (l_player.getReinforcementArmies() < p_armies) {
            return "Not enough armies to be deployed. Available armies: " + l_player.getReinforcementArmies();
        }

        /**
         * Check if the country is owned by the player
         */

        Optional<Country> l_territory = l_player.d_listOfCountriesOwned.stream()
                .filter(c -> c.getCountryName().equals(l_country.getCountryName())).findFirst();
       

        if (!l_territory.isPresent()) {
            return "Country is not owned by the player";
        }

        ConcreteDeploy order = new ConcreteDeploy();
        order.setL_numberOfArmies(p_armies);
        order.setL_territory(l_country);
        l_player.issue_order(order);

        /**
         * Main Game loop in round robin fashion which checks the reinforcement pool of the player and if it is 0, then
         * ask the next player to deploy armies. If all players have deployed all their armies, then execute the orders
         */

        Boolean l_flag = false;
        int l_i = PlayerCommands.d_CurrentPlayerId+1;

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
            return "Turn of " + l_players.get(l_i).getL_playername() + " to deploy army";
        } else {
            p_gameEngine.execute_orders();
            return "Orders successfully executed";
        }
    }

}
