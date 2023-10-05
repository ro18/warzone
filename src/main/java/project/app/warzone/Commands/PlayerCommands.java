package project.app.warzone.Commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import project.app.warzone.Features.PlayerFeatures;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Player;
import project.app.warzone.Utilities.Commands;

/**
 * This class stores all the player-related commands allowed in gameplay
 */
@ShellComponent
public class PlayerCommands {

    public GameEngine d_gameEngine;
    public PlayerFeatures d_playerFeatures;
    public String d_prevUserCommand; 
    public static int d_CurrentPlayerId = 1;

      /**
     * Constructor for Playercommands
     * 
     * @param p_gameEngine            storing gameEngine
     * @param p_playerFeatures        storing playerFeatures
     */
    public PlayerCommands(GameEngine p_gameEngine,PlayerFeatures p_playerFeatures){
        this.d_gameEngine = p_gameEngine;
        this.d_playerFeatures= p_playerFeatures;
    }


    
    
    /**
     * command for add player
     * 
     * @param p_playerNameOne          storing player 1 name
     * @param p_playerNameTwo          storing player 2 name
     * @return                         returns status of adding player
     */
    @ShellMethod(key= "gameplayer", prefix = "-", value="Player can create or remove a player")
    public String gamePlayerAdd(@ShellOption(value="a",defaultValue=ShellOption.NULL, arity = 10 ) String p_playerNameOne,@ShellOption(value="r", defaultValue=ShellOption.NULL, arity=10) String p_playerNameTwo){


        if(d_gameEngine.prevUserCommand == Commands.LOADMAP || d_gameEngine.prevUserCommand == Commands.ADDPLAYER || d_gameEngine.prevUserCommand == Commands.REMOVEPLAYER){
                if(p_playerNameOne != null && p_playerNameOne != ""){
                    String l_players[] = p_playerNameOne.split(","); 
                    int l_i=0;
                    while(l_i<l_players.length){

                        if(l_players[l_i].toString().equals("-add") == false){
                        
                            d_playerFeatures.addPlayers(l_players[l_i],d_gameEngine);
                            
                        }
                        l_i++;
                    }
                    
                    d_playerFeatures.printAllPlayers(d_gameEngine);
                    d_gameEngine.prevUserCommand=Commands.ADDPLAYER;
                    return "Players added successfully";
                    
                }               
                else{

                    String l_players[] = p_playerNameTwo.split(","); 
                    int l_i=0;
                    while(l_i < l_players.length){
                        if(l_players[l_i].toString().equals("-remove") == false){

                            d_playerFeatures.removePlayers(l_players[l_i], d_gameEngine);


                        }
                        l_i++;
                    }
                    d_playerFeatures.printAllPlayers(d_gameEngine);
                    d_gameEngine.prevUserCommand=Commands.REMOVEPLAYER;
                    return "Players removed successfully";
            
                }                       
                
        }       
        else{
            return "You cannnot add players at this stage.Please enter loadmap command first";
        }

    }    
        
        
    

    
    /** 
     * @return String           returns status of assigncountries
     */
    @ShellMethod(key= "assigncountries", value="This is used to assign countries to players randomly")
    public String assigncountries(){

        if(d_gameEngine.getPlayers().size() < 2){
            return "You need atleast 2 players to play the game. Please add more players";
        }
        Player player = d_gameEngine.getPlayers().get(PlayerCommands.d_CurrentPlayerId);
        d_playerFeatures.assignCountries(d_gameEngine);
        System.out.println("Assigned Countries to the players are:");
        d_playerFeatures.showAllAssignments(d_gameEngine.getPlayers());
        //playerFeatures.initializeArmies(gameEngine.getPlayers());
        d_gameEngine.prevUserCommand = Commands.ASSIGNCOUNTRIES;
        return "Assignment of countries is completed. \nNow its turn of player: "+player.getL_playername()+" to deploy armies";

    }


    
    /** 
     * @return String       returns status of showstats
     */
    @ShellMethod(key= "showstats", value="Displays players armies and other details")
    public String showStats(){
        System.out.println("========================================");
        System.out.println("STATS:");
        d_playerFeatures.showStats(d_gameEngine);

        return "STATS COMPLETE";

    }

    /**
     * @param p_countryID               storing country ID
     * @param p_armies                  storing number of armies to deploy
     * @return                          returns status of deploying army
     */
    @ShellMethod(key = "deploy", value = "This is used to deploy armies")
    public String deployArmies(@ShellOption int p_countryID, @ShellOption int p_armies) {
        if(d_gameEngine.prevUserCommand != Commands.ASSIGNCOUNTRIES){
            return "You cannot deploy armies at this stage. Please follow the sequence of commands in the game.";
        }
        return d_playerFeatures.deployArmies(d_gameEngine, p_countryID, p_armies);
    }
}
