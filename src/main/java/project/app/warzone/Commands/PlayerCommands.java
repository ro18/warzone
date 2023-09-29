package project.app.warzone.Commands;

    
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import project.app.warzone.Model.Continent;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Player;
import project.app.warzone.Model.Territory;
import project.app.warzone.Utilities.Commands;


@ShellComponent
public class PlayerCommands {

    public GameEngine gameEngine;
    public String prevUserCommand; 

    public PlayerCommands(GameEngine gameEngine){
        this.gameEngine = gameEngine;
    }


    @ShellMethod(key= "gameplayer", prefix = "-", value="Player can create or remove a player")
    public String gamePlayerAdd(@ShellOption(value="a",defaultValue=ShellOption.NULL, arity = 2) String p_playerNameOne,@ShellOption(value="r", defaultValue=ShellOption.NULL, arity=2) String p_playerNameTwo){

        if(gameEngine.prevUserCommand == Commands.LOADMAP || gameEngine.prevUserCommand == Commands.ADDPLAYER || gameEngine.prevUserCommand == Commands.REMOVEPLAYER){
                if(p_playerNameOne != null && p_playerNameOne != ""){
                    String l_players[] = p_playerNameOne.split(","); 
                    int l_playerOrder =1;
                    for(String l_playerObjects : l_players){
                    System.out.println("player name is : "+l_playerObjects);
                    Player player= new Player(l_playerOrder++, l_playerObjects);
                    gameEngine.d_playersList.add(player);
                }

                List<Player> players = gameEngine.getPlayers();
                for(int i=1 ; i <= players.size() ; i++){
                    System.out.println("Player"+i+":"+players.get(i-1).getL_playername());
                }
                gameEngine.prevUserCommand=Commands.ADDPLAYER;
                return "Players added successfully";
            

                }
                else{

                    String l_players[] = p_playerNameTwo.split(","); 
                    List<Player> playerList = gameEngine.getPlayers();
                    for(String l_player: l_players){
                        Optional<Player> l_playerToRemove= playerList.stream().filter(c->c.getL_playername().equals(l_player)).findFirst();
                        playerList.remove(l_playerToRemove.get());             

                    }

                    List<Player> players = gameEngine.getPlayers();
                    for(int i=1 ; i <= players.size() ; i++){
                        System.out.println("Player"+i+":"+players.get(i-1).getL_playername());
                    }
                    gameEngine.prevUserCommand=Commands.REMOVEPLAYER;
                    return "Players removed successfully";
            
                }                       
                
        }       
        else{
            return "You cannnot add players at this stage.Please enter loadmap command first";
        }

        

        
    }

    @ShellMethod(key= "assigncountries", value="This is used to assign countries to players randomly")
    public String assigncountries(){
        
        List<Continent> continentList= gameEngine.gameMap.getListOfContinents();
        int noOfPlayers = gameEngine.getPlayers().size();
        int noOfContinents = continentList.size();
        int noOfCountries = gameEngine.gameMap.getNodes().size();

        Random l_random = new Random();

        for( Player p : gameEngine.getPlayers()){ // logic to have players -1 country
            int randomCountry = l_random.nextInt(noOfCountries);

            p.setTerritories(gameEngine.gameMap.getNodes().get(randomCountry).getData());

        }

          for( Player p : gameEngine.getPlayers()){
           System.out.println(p.d_playername+":");
           List<Territory> listOfTerritories = p.getListOfTerritories();
           for(Territory t : listOfTerritories){
            System.out.println(t.getTerritoryName());
           }
        }



        


        

        return "countries assigned";

    }

   

    


   
}
