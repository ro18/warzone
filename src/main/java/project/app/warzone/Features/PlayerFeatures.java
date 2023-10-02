package project.app.warzone.Features;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Component;

import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Player;
import project.app.warzone.Model.Territory;

@Component
public class PlayerFeatures {

    public void showAllAssignments(List<Player> allPlayers){

           for( Player p : allPlayers){
        
           System.out.println("Player Id:"+p.d_playerid);

           System.out.println("Countries of "+p.d_playername+":");
           List<Territory> listOfTerritories = p.getListOfTerritories();

           for(Territory t : listOfTerritories){
              System.out.println(t.getTerritoryName());

           }
        }
        
    }

    public void assignCountries(GameEngine p_gameEngine){

        Random l_random = new Random();

        for( Player p : p_gameEngine.getPlayers()){ // logic to have players -1 country

            
            int randomCountry =  l_random.nextInt(p_gameEngine.gameMap.getNodes().size());

            // Map<Integer,Boolean> playerIds = new HashMap<Integer,Boolean>();

            // for(int l_i=1;l_i<=p_gameEngine.getPlayers().size();l_i++){
            //     playerIds.put(l_i, false);
            // }

            int randomId = l_random.nextInt(p_gameEngine.getPlayers().size()+1);

            // for(int i=0 ; i< p_gameEngine.getPlayers().size() ;i++){
            //     while(playerIds.get(randomId) == true){
            //         randomId = l_random.nextInt(p_gameEngine.getPlayers().size()+1);

            //     }
            //     p.setL_playerid(randomId);
            //     playerIds.put(randomId, true);

            // }

            while(p_gameEngine.gameMap.getNodes().get(randomCountry).getData().getOwnerId() != 0){
                randomCountry  = l_random.nextInt(p_gameEngine.gameMap.getNodes().size()+1);

            }

            


            
             
        
            p.setTerritories(p_gameEngine.gameMap.getNodes().get(randomCountry).getData());

        }
    }

    public void addPlayers(String p_playerName, GameEngine gameEngine){

        int l_size= gameEngine.getPlayers().size();

        
        //for(String l_playerObjects : p_playerNames){
        Player player= new Player(l_size+1,p_playerName);
        gameEngine.d_playersList.add(player);

        //}

    }

    public void removePlayers(String p_playerName, GameEngine gameEngine){

        List<Player> playerList = gameEngine.getPlayers();
        //for(String l_player: p_playerNames){
            Optional<Player> l_playerToRemove= playerList.stream().filter(c->c.getL_playername().equals(p_playerName)).findFirst();
            playerList.remove(l_playerToRemove.get());             

        //}

    }

    public void printAllPlayers(GameEngine gameEngine){
        System.out.println("Final players of the game are:");
        List<Player> players = gameEngine.getPlayers();
            for(int i=1 ; i <= players.size() ; i++){
                System.out.println("Player"+i+":"+players.get(i-1).getL_playername());
            }
    }

    // public void initializeArmies(GameEngine gameEngine){

    //     List<Player> gamePlayers = new ArrayList<>();
    //     int noOfPlayers = gamePlayers.size();

    //     for(Player p : gamePlayers){
    //         p.initReinforcementArmies(3)
    //     }

    // }

    public void showStats(GameEngine gameengine){
        List<Player> listOfPlayers = gameengine.getPlayers();
        for(Player p : listOfPlayers){
            System.out.println("Player Name:"+p.d_playername+"-PlayerId:"+p.d_playerid);
            System.out.println("Total Armies available per round: "+p.getReinforcementArmies());
            System.out.println("Countries Owned - Armies");
            for(Territory t : p.getListOfTerritories()){
                System.out.println(t.getTerritoryName()+" - "+t.getNumberOfArmies());
            }
            System.out.println("-------------------------------");
        }

    }
    
}
