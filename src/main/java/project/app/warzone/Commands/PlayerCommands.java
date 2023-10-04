package project.app.warzone.Commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import project.app.warzone.Features.PlayerFeatures;
import project.app.warzone.Model.GameEngine;
import project.app.warzone.Utilities.Commands;


@ShellComponent
public class PlayerCommands {

    public GameEngine gameEngine;
    public PlayerFeatures playerFeatures;
    public String prevUserCommand; 

    public PlayerCommands(GameEngine gameEngine,PlayerFeatures playerFeatures){
        this.gameEngine = gameEngine;
        this.playerFeatures= playerFeatures;
    }


    
    /** 
     * @param @ShellOption
     * @param defaultValue=ShellOption.NULL
     * @param p_playerNameOne
     * @param @ShellOption(value="r"
     * @param defaultValue=ShellOption.NULL
     * @param p_playerNameTwo
     * @return String
     */
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
        
        
    

    
    /** 
     * @return String
     */
    @ShellMethod(key= "assigncountries", value="This is used to assign countries to players randomly")
    public String assigncountries(){
        
        playerFeatures.assignCountries(gameEngine);
        System.out.println("Assigned Countries to the players are:");
        playerFeatures.showAllAssignments(gameEngine.getPlayers());
        //playerFeatures.initializeArmies(gameEngine.getPlayers());
        return "Assignment of countries is completed. Enter showStats command to see you armies.";

    }


    
    /** 
     * @return String
     */
    @ShellMethod(key= "showstats", value="Displays players armies and other details")
    public String showStats(){
        System.out.println("========================================");
        System.out.println("STATS:");
        playerFeatures.showStats(gameEngine);

        return "STATS COMPLETE";

    }



}
