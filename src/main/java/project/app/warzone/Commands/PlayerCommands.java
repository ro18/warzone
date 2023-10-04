package project.app.warzone.Commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import project.app.warzone.Features.PlayerFeatures;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Player;
import project.app.warzone.Utilities.Commands;


@ShellComponent
public class PlayerCommands {

    public GameEngine gameEngine;
    public PlayerFeatures playerFeatures;
    public String prevUserCommand; 
    public static int d_currentPlayerId = 1;

    public PlayerCommands(GameEngine gameEngine,PlayerFeatures playerFeatures){
        this.gameEngine = gameEngine;
        this.playerFeatures= playerFeatures;
    }


    @ShellMethod(key= "gameplayer", prefix = "-", value="Player can create or remove a player")
    public String gamePlayerAdd(@ShellOption(value="a",defaultValue=ShellOption.NULL, arity = 10 ) String p_playerNameOne,@ShellOption(value="r", defaultValue=ShellOption.NULL, arity=10) String p_playerNameTwo){


        if(gameEngine.prevUserCommand == Commands.LOADMAP || gameEngine.prevUserCommand == Commands.ADDPLAYER || gameEngine.prevUserCommand == Commands.REMOVEPLAYER){
                if(p_playerNameOne != null && p_playerNameOne != ""){
                    String l_players[] = p_playerNameOne.split(","); 
                    int l_i=0;
                    while(l_i<l_players.length){

                        if(l_players[l_i].toString().equals("-add") == false){
                        
                            playerFeatures.addPlayers(l_players[l_i],gameEngine);
                            
                        }
                        l_i++;
                    }
                    
                    playerFeatures.printAllPlayers(gameEngine);
                    gameEngine.prevUserCommand=Commands.ADDPLAYER;
                    return "Players added successfully";
                    
                }               
                else{

                    String l_players[] = p_playerNameTwo.split(","); 
                    int l_i=0;
                    while(l_i < l_players.length){
                        if(l_players[l_i].toString().equals("-remove") == false){

                            playerFeatures.removePlayers(l_players[l_i], gameEngine);


                        }
                        l_i++;
                    }
                    playerFeatures.printAllPlayers(gameEngine);
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
        Player player = gameEngine.getPlayers().get(PlayerCommands.d_currentPlayerId);
        playerFeatures.assignCountries(gameEngine);
        System.out.println("Assigned Countries to the players are:");
        playerFeatures.showAllAssignments(gameEngine.getPlayers());
        //playerFeatures.initializeArmies(gameEngine.getPlayers());
        gameEngine.prevUserCommand = Commands.ASSIGNCOUNTRIES;
        return "Assignment of countries is completed. \nNow its turn of player: "+player.getL_playername()+" to deploy armies";

    }

    @ShellMethod(key= "showstats", value="Displays players armies and other details")
    public String showStats(){
        System.out.println("========================================");
        System.out.println("STATS:");
        playerFeatures.showStats(gameEngine);

        return "STATS COMPLETE";

    }

    @ShellMethod(key = "deploy", value = "This is used to deploy armies")
    public String deployArmies(@ShellOption int p_countryID, @ShellOption int p_armies) {
        if(gameEngine.prevUserCommand != Commands.ASSIGNCOUNTRIES){
            return "You cannot deploy armies at this stage. Please follow the sequence of commands in the game.";
        }
        return playerFeatures.deployArmies(gameEngine, p_countryID, p_armies);
    }
}
