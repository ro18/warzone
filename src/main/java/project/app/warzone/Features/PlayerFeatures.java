package project.app.warzone.Features;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Component;

import project.app.warzone.Model.GameEngine;
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

           for( Player p : allPlayers){
        
           System.out.println("Player Id:"+p.d_playerid);

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

            while(p_gameEngine.gameMap.getNodes().get(randomCountry).getData().getOwnerId() != 0){
                randomCountry  = l_random.nextInt(p_gameEngine.gameMap.getNodes().size()+1);

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
            for(int i=1 ; i <= players.size() ; i++){
                System.out.println("Player"+i+":"+players.get(i-1).getL_playername());
            }
    }

    
    
    /** 
     * @param gameengine            storing gameEngine
     */
    public void showStats(GameEngine gameengine){
        List<Player> listOfPlayers = gameengine.getPlayers();
        for(Player p : listOfPlayers){
            System.out.println("Player Name:"+p.d_playername+"-PlayerId:"+p.d_playerid);
            System.out.println("Total Armies available per round: "+p.getReinforcementArmies());
            System.out.println("Countries Owned - Armies");
            for(Country t : p.getListOfTerritories()){
                System.out.println(t.getCountryName()+" - "+t.getNumberOfArmies());
            }
            System.out.println("-------------------------------");
        }

    }
    
}
